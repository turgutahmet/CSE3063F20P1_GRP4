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
