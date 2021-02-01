from Question import *
from Config import *
from Student import *
from Poll import *
from PollReport import *
from Analyzer import *
from os import listdir
from os.path import isfile, join
from QuestionAndGivenAnswer import *
from AnswerKeyConverter import *
import xlrd as xlrd
import csv
import datetime as dt
import re
import logging
import sys


class PollAnalyzer:
    def __init__(self):
        self.students = []  # All student in the system.
        self.questions = []  # All questions in the system.
        self.polls = []  # All polls in the system.
        self.pollReports = []  # All poll reports in the system.
        self.config = Config()  # Stores paths.

    def startAnalyzer(self):
        # Logger configuration
        logging.basicConfig(filename='log.txt', filemode='a', format='%(asctime)s - %(levelname)s - %(message)s',
                            datefmt='%d-%b-%y %H:%M:%S')
        logger = logging.getLogger()
        logger.setLevel(logging.INFO)

        stdoutHandler = logging.StreamHandler(sys.stdout)

        logger.addHandler(stdoutHandler)

        self.readStudent()
        logger.info("Students have been read.")
        self.readAnswerKeys()
        logger.info("Answer keys have been read.")
        self.readPollReports()
        logger.info("Poll reports have been read.")
        self.updatePollReports()
        analyzer = Analyzer(self.students, self.pollReports)
        analyzer.startAnalyzing(logger)
        logger.info("Analyzing has been finished.")

    def readStudent(self):  # Reads all students in student list which stored in config.studentListDirectory path
        filesInPath = [f for f in listdir(self.config.studentListDirectory) if
                       isfile(join(self.config.studentListDirectory, f))]
        f = xlrd.open_workbook(self.config.studentListDirectory + "/" + filesInPath[0])
        sheet = f.sheet_by_index(0)
        for i in range(0, sheet.nrows):
            try:
                int(sheet.cell_value(i, 2))
                newstudent = Student(sheet.cell_value(i, 2), sheet.cell_value(i, 4), sheet.cell_value(i, 7))
                self.students.append(newstudent)
            except ValueError:
                continue

    def readAnswerKeys(self):  # Reads all answer keys which stored in config.answerKeyDirectory path
        filesInPath = [f for f in listdir(self.config.answerKeyTxtDirectory) if
                       isfile(join(self.config.answerKeyTxtDirectory, f))]
        converter = AnswerKeyConverter()
        for file in filesInPath:
            converter.convert(self.config.answerKeyTxtDirectory + file)

        filesInPath = [f for f in listdir(self.config.answerKeyDirectory) if
                       isfile(join(self.config.answerKeyDirectory, f))]
        for fileName in filesInPath:
            path = self.config.answerKeyDirectory + "/" + fileName
            answerKey = xlrd.open_workbook(path)
            sheet = answerKey.sheet_by_index(0)
            poll = Poll(sheet.cell_value(0, 0))  # For each answer key file create a new Poll object.
            for i in range(1, sheet.nrows):
                # For each question in answer key file create a Question object.
                newQuestion = self.createQuestion(sheet.cell_value(i, 0), sheet.cell_value(i, 1))
                poll.addQuestion(newQuestion)
            self.polls.append(poll)

    def createQuestion(self, questionText, correctAnswer):
        questionText = self.removeSpecialCharacters(questionText)  # Remove escape characters in questionText.
        # Iterate all questions in PollAnalyzer.
        for question in self.questions:
            # If given question already exists in questions list return that question.
            if question.questionText == questionText:
                return question
        # If it isn't exist create new Question object and return it.
        newQuestion = Question(questionText, correctAnswer)
        self.questions.append(newQuestion)
        return newQuestion

    def readPollReports(self):  # Reads all poll reports which stored in config.pollReportDirectory path.
        # Find all files in given path.
        filesInPath = [f for f in listdir(self.config.pollReportDirectory) if
                       isfile(join(self.config.pollReportDirectory, f))]

        # Read files one by one.
        for pollReportFile in filesInPath:
            path = self.config.pollReportDirectory + "/" + pollReportFile
            self.reformatFile(path)  # Reformat file.
            # Read rows one by one.
            with open(path, 'r', encoding="utf-8") as file:
                reader = csv.DictReader(file)
                pollKey = 1  # Count of polls in that poll reports file.
                i = 0
                prev = " "
                questionList = dict()  # Stores all questions in this poll report.
                answerList = dict()  # Stores all answers in this poll report.
                questionsAndGivenAnswersList = dict()  # Stores all QuestionAndGivenAnswer objects in this poll report.
                date = " "
                # Read rows in that poll report file one by one.
                date, pollKey = self.readPollReportRow(answerList, date, i, pollKey, prev, questionList,
                                                       questionsAndGivenAnswersList, reader, pollReportFile)
                date = str(dt.datetime.strptime(date, "%b %d, %Y %H:%M:%S")).split()[0]
                # Find each poll's identity in that poll report file.
                pollsInPollReport = self.findPoll(pollKey, questionList)
            # Create and add new PollReport object into pollReports.
            self.addNewPollReport(date, pollsInPollReport, questionsAndGivenAnswersList)

    def readPollReportRow(self, answerList, date, i, pollKey, prev, questionList, questionsAndGivenAnswersList, reader,
                          pollReportFile):
        for row in reader:
            date = row["Submitted Date/Time"]  # Find date of that poll report.
            questionKey = 1

            # Check that is there more than one poll in that poll report file.
            if i == 0:
                prev = row[None]
            if not row[None][0] in prev:
                prev = row[None]
                pollKey += 1
            i += 1
            j = 0

            # Iterate questions and given answers to these questions.
            for q in row[None]:
                q = self.removeSpecialCharacters(q)  # Remove escape characters in questionText.
                if j % 2 == 0:
                    # Add questionText into questionList.
                    questionList[str(pollKey) + "." + str(questionKey)] = q
                else:
                    # Add given answer into answerList.
                    answerList[row["User Name"] + " " + str(pollKey) + "." + str(questionKey)] = q

                    # Reformat student's name.
                    names = row["User Name"].upper()
                    names = names.split()
                    for i in range(0, len(names)):
                        names[i] = self.reformatName(names[i])

                    #   Create new QuestionAndGivenAnswer object related to student, read question and given answer.
                    questionAndGivenAnswer = self.createQuestionAndGivenAnswer(studentName=names,
                                                                               questionText=questionList[
                                                                                   str(pollKey) + "." + str(
                                                                                       questionKey)],
                                                                               givenAnswer=q,
                                                                               pollReportFile=pollReportFile)
                    #   Append returned QuestionAndGivenAnswer object into questionsAndGivenAnswersList.
                    if not questionAndGivenAnswer is None:
                        questionsAndGivenAnswersList.setdefault(pollKey, []).append(questionAndGivenAnswer)
                    questionKey += 1
                j += 1
        return date, pollKey

    # Finds identities of polls' in poll report file.
    def findPoll(self, pollKey, questionList):
        pollsInPollReport = []
        for key in range(0, pollKey):
            for poll in self.polls:
                counter = 0
                for q in range(0, len(questionList)):
                    questionListKey = str(key + 1) + "." + str(q + 1)
                    for pollQ in poll.questions:
                        try:
                            if pollQ.questionText.replace(" ", "") == questionList[questionListKey].replace(" ", ""):
                                counter += 1
                        except:
                            continue
                # If that poll's all questions have matched with a poll's which is stored in polls list, questions
                # set identity of that poll.
                if counter == len(poll.questions):
                    currentPoll = poll
                    pollsInPollReport.append(currentPoll)
        return pollsInPollReport

    # Adds new PollReport object into pollReports list.
    def addNewPollReport(self, date, pollsInPollReport, questionsAndGivenAnswersList):
        for counter in range(0, len(pollsInPollReport)):
            pollReport = PollReport(pollsInPollReport[counter], date, questionsAndGivenAnswersList[counter + 1])
            self.pollReports.append(pollReport)

    # Creates new QuestionAndGivenAnswer object and return it.
    def createQuestionAndGivenAnswer(self, studentName, questionText, givenAnswer, pollReportFile):
        # Find student in students list.
        student = self.findStudent(studentName)

        if student is None:
            data = str(studentName) + " " + pollReportFile
            with open(self.config.anomaliesDirectory + pollReportFile + ".json", 'w', encoding='utf-8') as f:
                json.dump(data, f, ensure_ascii=False, indent=4)

        # Find question in questions list.
        question = self.findQuestion(questionText)

        if (not student is None) and (not question is None):
            # Create and return new QuestionAndGivenAnswer object.
            questionAndGivenAnswer = QuestionAndGivenAnswer(student, question, givenAnswer)
            return questionAndGivenAnswer
        return

    # Finds Student object in students list related to passed studentName argument and return it.
    def findStudent(self, studentName):
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
                    return student
        return

    # Find Question object in question list related with passed questionText argument and return it.
    def findQuestion(self, questionText):
        for question in self.questions:
            if question.questionText.replace(" ", "") == questionText.replace(" ", ""):
                return question
        # It can be attendance question.
        attendancePoll = Poll("Attendance Poll")
        attendanceQuestion = self.createQuestion(questionText, "Yes;No")
        attendancePoll.addQuestion(attendanceQuestion)
        self.polls.append(attendancePoll)
        return attendanceQuestion

    # Update all PollReport objects in pollReports list.
    def updatePollReports(self):
        for pollReport in self.pollReports:
            pollReport.splitPollReportRows()
            pollReport.fillQuestionsInPollReport()

    # Reformat passed name argument.
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
        if "@" in name:
            name = name.split("@")
            name = name[0]
        return name

    # Reformat file which is passed path as argument.
    def reformatFile(self, path):
        file = open(path, 'r', encoding="utf-8")
        Lines = file.readlines()

        for i in range(0, len(Lines)):
            if "#" in Lines[i]:
                break
            Lines[i] = ""

        file = open(path, 'w', encoding="utf-8")
        file.writelines(Lines)
        file.close()

        file1 = open(path, 'r', encoding="utf-8")
        Lines = file1.readlines()
        line = Lines[0]
        chars = list(line)
        if chars[len(chars) - 2] == ",":
            chars[len(chars) - 2] = ""
        line = "".join(chars)
        Lines[0] = line

        for i in range(1, len(Lines)):
            line = Lines[i]
            chars = list(line)
            if chars[len(chars) - 2] == " ":
                chars[len(chars) - 2] = ""
            line = "".join(chars)
            Lines[i] = line

        file1 = open(path, 'w', encoding="utf-8")
        file1.writelines(Lines)
        file1.close()

    # Removes escape characters in passed argument.
    def removeSpecialCharacters(self, cellValue):
        text = re.sub(r"[\r\n\t\x07\x0b]", "", cellValue)
        return text
