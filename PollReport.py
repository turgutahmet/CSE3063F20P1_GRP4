from PollReportRow import *


class PollReport:
    def __init__(self, poll, date, questionsAndGivenAnswers):
        self.poll = poll
        self.date = date
        self.questionsAndGivenAnswers = questionsAndGivenAnswers
        self.pollReportRows = []

    def splitPollReportRows(self):
        pollReportRow = PollReportRow()
        prevName = self.questionsAndGivenAnswers[0].student.firstName + self.questionsAndGivenAnswers[0].student.lastName
        for i in range(0, len(self.questionsAndGivenAnswers)):
            currentName = self.questionsAndGivenAnswers[i].student.firstName + self.questionsAndGivenAnswers[i].student.lastName

            temp = self.questionsAndGivenAnswers[i]
            if prevName in currentName:
                if i - 1 == 0:
                    pollReportRow.setStudent(self.questionsAndGivenAnswers[i - 1].student)
                    pollReportRow.addNewQuestionAndGivenAnswer(self.questionsAndGivenAnswers[i - 1])
                pollReportRow.setStudent(temp.student)
                pollReportRow.addNewQuestionAndGivenAnswer(temp)
            else:
                prevName = self.questionsAndGivenAnswers[i].student.firstName + self.questionsAndGivenAnswers[i].student.lastName
                self.pollReportRows.append(pollReportRow)
                pollReportRow = PollReportRow()
                pollReportRow.setStudent(temp.student)
                pollReportRow.addNewQuestionAndGivenAnswer(temp)

            if i == len(self.questionsAndGivenAnswers) - 1:
                self.pollReportRows.append(pollReportRow)
