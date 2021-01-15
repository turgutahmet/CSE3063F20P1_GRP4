class QuestionAndGivenAnswer:

    def __init__(self, student, question, givenAnswer):
        self.student = student
        self.question = question
        self.givenAnswer = givenAnswer
        self.isCorrect = False

    def checkQuestion(self):
        if self.question.correctAnswer in self.givenAnswer:
            self.isCorrect = True
        self.question.updateAnswer(self.givenAnswer)
