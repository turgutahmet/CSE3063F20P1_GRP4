class AnswerKey:
    def __init__(self, pollName):
        self.pollName = pollName
        self.questionsAndAnswers = dict()

    def addQuestionAndAnswer(self, question, answer):
        self.questionsAndAnswers[question] = answer
