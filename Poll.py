class Poll():

    def __init__(self, pollName):
        self.pollName = pollName
        self.questions = []

    def addQuestion(self, question):
        self.questions.append(question)
