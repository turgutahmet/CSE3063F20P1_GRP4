from PollReportRow import *
from QuestionInPollReport import *


class PollReport:
    def __init__(self, poll, date, questionsAndGivenAnswers):
        self.poll = poll
        self.date = date
        self.questionsInPollReport = []
        self.questionsAndGivenAnswers = questionsAndGivenAnswers
        self.pollReportRows = []

    # Checks all QuestionAndGivenAnswer objects in questionsAndGivenAnswers list and regroup of them according to students.
    def splitPollReportRows(self):
        pollReportRow = PollReportRow()
        prevName = self.questionsAndGivenAnswers[0].student.firstName + self.questionsAndGivenAnswers[
            0].student.lastName
        for i in range(0, len(self.questionsAndGivenAnswers)):
            currentName = self.questionsAndGivenAnswers[i].student.firstName + self.questionsAndGivenAnswers[
                i].student.lastName

            temp = self.questionsAndGivenAnswers[i]
            if prevName in currentName:
                pollReportRow.setStudent(temp.student)
                pollReportRow.addNewQuestionAndGivenAnswer(temp)
            else:
                prevName = self.questionsAndGivenAnswers[i].student.firstName + self.questionsAndGivenAnswers[
                    i].student.lastName
                self.pollReportRows.append(pollReportRow)
                pollReportRow = PollReportRow()
                pollReportRow.setStudent(temp.student)
                pollReportRow.addNewQuestionAndGivenAnswer(temp)

            if i == len(self.questionsAndGivenAnswers) - 1:
                self.pollReportRows.append(pollReportRow)

    # Fills QuestionsInPollReport list.
    def fillQuestionsInPollReport(self):
        for pollReportRow in self.pollReportRows:
            for questionAndGivenAnswer in pollReportRow.questionsAndGivenAnswers:
                questionInPollReport = self.findQuestionInPollReport(questionAndGivenAnswer.question)
                questionInPollReport.updateAnswer(questionAndGivenAnswer.givenAnswer.split(";"))

    # Look fillQuestionsInPollReport list if there is match with passed argument returns it, otherwise create new QuestionInPollReport object and returns it.
    def findQuestionInPollReport(self, question):
        for questionInPollReport in self.questionsInPollReport:
            if questionInPollReport.question == question:
                return questionInPollReport
        newQuestionInPollReport = QuestionInPollReport(question)
        self.questionsInPollReport.append(newQuestionInPollReport)
        return newQuestionInPollReport
