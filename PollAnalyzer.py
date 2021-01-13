from Question import *
from Config import *
from Student import *
from Poll import *
import xlrd as xlrd
from os.path import isfile, join
from os import listdir

class PollAnalyzer():
    def __init__(self):
        self.students=[]
        self.questions=[]
        self.polls=[]
        self.pollResults=[]
        self.config= Config()

    def readStudent(self):
        f=xlrd.open_workbook(self.config.studentListDirectory)
        sheet = f.sheet_by_index(0)
        for i in range(13,sheet.nrows):
            try:
                int(sheet.cell_value(i, 2))
                newstudent=Student(sheet.cell_value(i, 2),sheet.cell_value(i, 4),sheet.cell_value(i, 7))
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
                    name = row["User Name"].replace(" ", "").upper()
                    name = self.reformatName(name)
                    questionAndGivenAnswer = self.createQuestionAndGivenAnswer(studentName=name,
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
        for student in self.students:
            name = str(student.firstName + student.lastName).replace(" ", "").upper()
            name = self.reformatName(name)
            if name in studentName:
                return student
        return

    def findQuestion(self, questionText):
        for question in self.questions:
            if question.questionText == questionText:
                return question
        return