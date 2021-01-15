from Question import *
from Config import *
from Student import *
from Poll import *
from PollReport import *
from os import listdir
from os.path import isfile, join
from QuestionAndGivenAnswer import *
import xlrd as xlrd
import csv
import datetime as dt
import re


class PollAnalyzer:
    def __init__(self):
        self.students = []
        self.questions = []
        self.polls = []
        self.pollReports = []
        self.config = Config()

    def startAnalyzer(self):
        self.readStudent()
        self.readAnswerKeys()
        self.readPollReports()

    def readStudent(self):
        f = xlrd.open_workbook(self.config.studentListDirectory)
        sheet = f.sheet_by_index(0)
        for i in range(13, sheet.nrows):
            try:
                int(sheet.cell_value(i, 2))
                newstudent = Student(sheet.cell_value(i, 2), sheet.cell_value(i, 4), sheet.cell_value(i, 7))
                self.students.append(newstudent)
            except ValueError:
                continue

    def createQuestion(self, questionText, correctAnswer):
        for question in self.questions:
            if question.questionText == questionText:
                return question
        newQuestion = Question(questionText, correctAnswer)
        self.questions.append(newQuestion)
        return newQuestion

    def readAnswerKeys(self):
        filesInPath = [f for f in listdir(self.config.answerKeyDirectory) if
                       isfile(join(self.config.answerKeyDirectory, f))]
        for fileName in filesInPath:
            path = self.config.answerKeyDirectory + "/" + fileName
            answerKey = xlrd.open_workbook(path)
            sheet = answerKey.sheet_by_index(0)
            poll = Poll(sheet.cell_value(0, 0))
            for i in range(1, sheet.nrows):
                newQuestion = self.createQuestion(sheet.cell_value(i, 0), sheet.cell_value(i, 1))
                poll.addQuestion(newQuestion)
            self.polls.append(poll)

    def readPollReports(self):
        # find all files in given path.
        filesInPath = [f for f in listdir(self.config.pollResultDirectory) if
                       isfile(join(self.config.pollResultDirectory, f))]

        # read files one by one.
        for pollReportFile in filesInPath:
            path = self.config.pollResultDirectory + "/" + pollReportFile

            self.reformatFile(path)

            # read rows one by one
            with open(path, 'r') as file:
                reader = csv.DictReader(file)
                pollKey = 1
                questionKey = 1
                i = 0
                j = 0
                prev = " "
                questionList = dict()
                answerList = dict()
                questionsAndGivenAnswersList = dict()
                date = " "
                date, pollKey = self.readPollReportRow(answerList, date, i, pollKey, prev, questionList,
                                                       questionsAndGivenAnswersList, reader)
                date = dt.datetime.strptime(date, "%b %d, %Y %H:%M:%S")
                pollsInPollReport = self.findPoll(pollKey, questionList)

            self.addNewPollReport(date, pollsInPollReport, questionsAndGivenAnswersList)
            print()

    def readPollReportRow(self, answerList, date, i, pollKey, prev, questionList, questionsAndGivenAnswersList, reader):
        for row in reader:
            date = row["Submitted Date/Time"]
            questionKey = 1
            if i == 0:
                prev = row[None]
            if not row[None][0] in prev:
                prev = row[None]
                pollKey += 1
            i += 1
            j = 0

            for q in row[None]:
                q = self.removeSpecialCharacters(q)
                if j % 2 == 0:
                    questionList[str(pollKey) + "." + str(questionKey)] = q

                else:
                    answerList[row["User Name"] + " " + str(pollKey) + "." + str(questionKey)] = q
                    names = row["User Name"].upper()
                    names = names.split()
                    for i in range(0, len(names)):
                        names[i] = self.reformatName(names[i])
                    questionAndGivenAnswer = self.createQuestionAndGivenAnswer(studentName=names,
                                                                               questionText=questionList[
                                                                                   str(pollKey) + "." + str(
                                                                                       questionKey)],
                                                                               givenAnswer=q)
                    if not questionAndGivenAnswer is None:
                        questionsAndGivenAnswersList.setdefault(pollKey, []).append(questionAndGivenAnswer)
                    questionKey += 1
                j += 1
        return date, pollKey

    def findPoll(self, pollKey, questionList):
        pollsInPollReport = []
        currentPoll = ""
        for key in range(0, pollKey):
            for poll in self.polls:
                flag = True
                for q in range(0, len(questionList)):
                    if q > len(poll.questions) - 1:
                        break
                    questionListKey = str(key + 1) + "." + str(q + 1)
                    if not poll.questions[q].questionText == questionList[questionListKey]:
                        flag = False
                        break
                if flag:
                    currentPoll = poll
                    pollsInPollReport.append(currentPoll)
        return pollsInPollReport

    def addNewPollReport(self, date, pollsInPollReport, questionsAndGivenAnswersList):
        for counter in range(0, len(pollsInPollReport)):
            pollReport = PollReport(pollsInPollReport[counter], date, questionsAndGivenAnswersList[counter + 1])
            pollReport.splitPollReportRows()
            self.pollReports.append(pollReport)

    def createQuestionAndGivenAnswer(self, studentName, questionText, givenAnswer):
        # find student
        student = self.findStudent(studentName)

        # find question
        question = self.findQuestion(questionText)

        if (not student is None) and (not question is None):
            questionAndGivenAnswer = QuestionAndGivenAnswer(student, question, givenAnswer)
            # check question
            questionAndGivenAnswer.checkQuestion()
            return questionAndGivenAnswer
        return

    def findStudent(self, studentName):
        flag = False
        countOfNames = len(studentName)
        for student in self.students:
            name = str(student.firstName + student.lastName).upper()
            name = self.reformatName(name)
            nameCounter = 0
            for stName in studentName:
                if stName in name:
                    nameCounter += 1
                else:
                    break

                if nameCounter == countOfNames:
                    flag = True
                    return student
        if not flag:
            print(studentName)
        return

    def findQuestion(self, questionText):
        for question in self.questions:
            if question.questionText == questionText:
                return question
        return

    def reformatName(self, name):
        name = name.replace("Ö", "O")
        name = name.replace("Ü", "U")
        name = name.replace("İ", "I")
        name = name.replace("Ş", "S")
        name = name.replace("Ç", "C")
        name = name.replace("Ğ", "G")
        name = name.replace(".", "")
        name = name.replace(" ", "")
        name = name.replace("1", "")
        name = name.replace("2", "")
        name = name.replace("3", "")
        name = name.replace("4", "")
        name = name.replace("5", "")
        name = name.replace("6", "")
        name = name.replace("7", "")
        name = name.replace("8", "")
        name = name.replace("9", "")
        name = name.replace("0", "")
        name = name.replace("@SOMEMAILCOM", "")
        return name

    def reformatFile(self, path):
        file1 = open(path, 'r')
        Lines = file1.readlines()
        line = Lines[0]

        chars = list(line)

        if chars[len(chars) - 2] == ",":
            chars[len(chars) - 2] = ""

        line = "".join(chars)

        Lines[0] = line

        file1 = open(path, 'w')
        file1.writelines(Lines)
        file1.close()

    def removeSpecialCharacters(self, cellValue):
        text = re.sub(r"[\r\n\t\x07\x0b]", "", cellValue)
        return text
