from Question import *
from Config import *
from Student import *
from Poll import *
from PollReport import *
from PollOutput import *
from os import listdir
from os.path import isfile, join
from QuestionAndGivenAnswer import *
from matplotlib import pyplot as plt
import numpy as np
import xlrd as xlrd
import csv
import datetime as dt
import re
import matplotlib.backends.backend_pdf

class PollAnalyzer:
    def __init__(self):
        self.students = []  # All student in the system.
        self.questions = []  # All questions in the system.
        self.polls = []  # All polls in the system.
        self.pollReports = []  # All poll reports in the system.
        self.config = Config()  # Stores paths.
        self.pollOutput=PollOutput()

    def startAnalyzer(self):
        self.readStudent()
        self.readAnswerKeys()
        self.readPollReports()
        self.updatePollReports()
        self.calculateSuccessRate()
        self.attendance()
        self.statisticsGraph()

    def readStudent(self):  # Reads all students in student list which stored in config.studentListDirectory path
        f = xlrd.open_workbook(self.config.studentListDirectory)
        sheet = f.sheet_by_index(0)
        for i in range(13, sheet.nrows):
            try:
                int(sheet.cell_value(i, 2))
                newstudent = Student(sheet.cell_value(i, 2), sheet.cell_value(i, 4), sheet.cell_value(i, 7))
                self.students.append(newstudent)
            except ValueError:
                continue

    def readAnswerKeys(self):  # Reads all answer keys which stored in config.answerKeyDirectory path
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
                pollKey = 1     # Count of polls in that poll reports file.
                i = 0
                prev = " "
                questionList = dict()   # Stores all questions in this poll report.
                answerList = dict()     # Stores all answers in this poll report.
                questionsAndGivenAnswersList = dict()   # Stores all QuestionAndGivenAnswer objects in this poll report.
                date = " "
                # Read rows in that poll report file one by one.
                date, pollKey = self.readPollReportRow(answerList, date, i, pollKey, prev, questionList,
                                                       questionsAndGivenAnswersList, reader)
                date = str(dt.datetime.strptime(date, "%b %d, %Y %H:%M:%S")).split()[0]
                # Find each poll's identity in that poll report file.
                pollsInPollReport = self.findPoll(pollKey, questionList)
            # Create and add new PollReport object into pollReports.
            self.addNewPollReport(date, pollsInPollReport, questionsAndGivenAnswersList)

    def readPollReportRow(self, answerList, date, i, pollKey, prev, questionList, questionsAndGivenAnswersList, reader):
        for row in reader:
            date = row["Submitted Date/Time"]   # Find date of that poll report.
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
                q = self.removeSpecialCharacters(q)     # Remove escape characters in questionText.
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
                                                                               givenAnswer=q)
                    #   Append returned QuestionAndGivenAnswer object into questionsAndGivenAnswersList.
                    if not questionAndGivenAnswer is None:
                        questionsAndGivenAnswersList.setdefault(pollKey, []).append(questionAndGivenAnswer)
                    questionKey += 1
                j += 1
        return date, pollKey

    def attendance(self):
        attendance = dict((i, []) for i in self.students)
        date = []
        dateOfPoll = " "
        condition = 0
        for i in range (0,len(self.pollReports)):
            attendancePoll = 0
            j = self.pollReports[i]
            if i == 0:
                dateOfPoll = j.date
            if i+1 < len(self.pollReports):
                if not dateOfPoll == self.pollReports[i+1].date:
                    dateOfPoll = self.pollReports[i+1].date
                    condition = 1
            else :
                condition = 1
            if not j.date in date:
                date.append(j.date)
            for x in j.questionsAndGivenAnswers:
                if x.student in self.students:
                    if not j.date in attendance[x.student]:
                        attendance[x.student].append(j.date)
            if condition == 1:
                for stu in attendance.keys():
                    for dates in attendance[stu]:
                        if dates == j.date:
                            attendancePoll += 1
                pdfName = "./outputs/attendanceCharts/"+str(j.date)+"_attendance.pdf"
                with matplotlib.backends.backend_pdf.PdfPages(pdfName) as export_pdf:
                    attAnsAndPer = {}
                    attAnsAndPer["Absent " + str(len(self.students) - attendancePoll) ] =  len(self.students) - attendancePoll
                    attAnsAndPer["Attended " + str(attendancePoll)] = attendancePoll
                    plt.pie(attAnsAndPer.values(), labels=attAnsAndPer.keys(), autopct='%.1f%%')
                    export_pdf.savefig()
                    plt.close()
                condition = 0
        attendanceStat = {}
        for i in attendance.keys():
            attendanceStat[i] = (len(date), len(attendance[i]), len(attendance[i]) / len(date) * 100)

        self.pollOutput.creatingAttendanceFile(attendance,date)
    def calculateSuccessRate(self):
        for pr in self.pollReports:
            s_list = dict((i, []) for i in self.students)
            if pr.poll.pollName != 'Attendance Poll':
                for s in self.students:
                    total_right = 0
                    for r in pr.pollReportRows:
                        if r.student == s:
                            for q in r.questionsAndGivenAnswers:
                                if q.isCorrect:
                                    s_list[s].append(1)
                                    total_right += 1
                                else:
                                    s_list[s].append(0)
                    s_list[s].append(str(total_right) + "/" + str(len(pr.poll.questions)))
                    s_list[s].append(((total_right / len(pr.poll.questions)) * 10000) / 100)

                outputName = pr.date + " " + pr.poll.pollName
                self.pollOutput.creatCalculateSuccessRate(outputName,len(pr.poll.questions),s_list)

    def statisticsGraph(self):
        generalDic = {}
        for pr in self.pollReports:
            middleDic = {}
            if not "Attendance" in pr.poll.pollName:
                pdfName = "./outputs/charts/" + str(pr.date) + " " + str(pr.poll.pollName) + ".pdf"
                with matplotlib.backends.backend_pdf.PdfPages(pdfName) as export_pdf:
                    for q in pr.questionsInPollReport:
                        ansAndPer = {}
                        colors = []
                        red = 'red'
                        green = 'lime'
                        total = 0
                        for i in q.answers.values():
                            total += i
                        for ans in q.answers.keys():
                            if q.question.correctAnswer == ans:
                                colors.append(green)
                            else:
                                colors.append(red)
                            ansAndPer[ans] = int((q.answers[ans] / total) * 10000) / 100
                        fig = plt.figure(figsize=(15, 10))
                        middleDic[q.question.questionText] = ansAndPer
                        plt.title(q.question.questionText,fontsize = 10)
                        plt.pie(ansAndPer.values(), labels=ansAndPer.keys(), colors=colors, autopct='%.1f%%')
                        export_pdf.savefig()
                        plt.close()
            generalDic[pr.poll.pollName] = middleDic

    # Finds identities of polls' in poll report file.
    def findPoll(self, pollKey, questionList):
        pollsInPollReport = []
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
                # If that poll's all questions have matched with a poll's which is stored in polls list, questions set identity of that poll.
                if flag:
                    currentPoll = poll
                    pollsInPollReport.append(currentPoll)
        return pollsInPollReport

    # Adds new PollReport object into pollReports list.
    def addNewPollReport(self, date, pollsInPollReport, questionsAndGivenAnswersList):
        for counter in range(0, len(pollsInPollReport)):
            pollReport = PollReport(pollsInPollReport[counter], date, questionsAndGivenAnswersList[counter + 1])
            self.pollReports.append(pollReport)

    # Creates new QuestionAndGivenAnswer object and return it.
    def createQuestionAndGivenAnswer(self, studentName, questionText, givenAnswer):
        # Find student in students list.
        student = self.findStudent(studentName)

        # Find question in questions list.
        question = self.findQuestion(questionText)

        if (not student is None) and (not question is None):
            # Create and return new QuestionAndGivenAnswer object.
            questionAndGivenAnswer = QuestionAndGivenAnswer(student, question, givenAnswer)
            return questionAndGivenAnswer
        return

    # Finds Student object in students list related to passed studentName argument and return it.
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
        return

    # Find Question object in question list related with passed questionText argument and return it.
    def findQuestion(self, questionText):
        for question in self.questions:
            if question.questionText == questionText:
                return question
        return

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
        name = name.replace("@SOMEMAILCOM", "")
        return name

    # Reformat file which is passed path as argument.
    def reformatFile(self, path):
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

