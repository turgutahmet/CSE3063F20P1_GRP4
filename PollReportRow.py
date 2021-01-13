class PollReportRow:
    def __init__(self):
        self.student = ""
        self.questionsAndGivenAnswers = []

    def setStudent(self, student):
        self.student = student

    def addNewQuestionAndGivenAnswer(self, questionAndGivenAnswer):
        self.questionsAndGivenAnswers.append(questionAndGivenAnswer)