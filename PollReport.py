from PollReportRow import *


class PollReport:
    def __init__(self, poll, date, questionsAndGivenAnswers):
        self.poll = poll
        self.date = date
        self.questionsAndGivenAnswers = questionsAndGivenAnswers
        self.pollReportRows = []

    def splitPollReportRows(self):
        pollReportRow = PollReportRow()
        for i in range(1, len(self.questionsAndGivenAnswers)):
            prevName = self.questionsAndGivenAnswers[i - 1].student.firstName + self.questionsAndGivenAnswers[
                i - 1].student.lastName
            currentName = self.questionsAndGivenAnswers[i].student.firstName + self.questionsAndGivenAnswers[
                i].student.lastName

            temp = self.questionsAndGivenAnswers[i]
            if prevName in currentName:
                if i - 1 == 0:
                    pollReportRow.setStudent(self.questionsAndGivenAnswers[i - 1].student)
                    pollReportRow.addNewQuestionAndGivenAnswer(self.questionsAndGivenAnswers[i - 1])
                pollReportRow.setStudent(temp.student)
                pollReportRow.addNewQuestionAndGivenAnswer(temp)
            else:
                self.pollReportRows.append(pollReportRow)
                pollReportRow = PollReportRow()
                pollReportRow.setStudent(temp.student)
                pollReportRow.addNewQuestionAndGivenAnswer(temp)
