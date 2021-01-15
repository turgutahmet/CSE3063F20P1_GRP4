class Question:
    def __init__(self, questionText, correctAnswer):
        self.questionText = questionText
        self.correctAnswer = correctAnswer
        self.answers = dict()

    def appendAnswer(self, givenAnswer):
        self.answers[givenAnswer] = 1

    def updateAnswer(self, givenAnswer):
        if givenAnswer in self.answers:
            self.answers[givenAnswer] += 1
        else:
            self.appendAnswer(givenAnswer)