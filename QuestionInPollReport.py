class QuestionInPollReport:
    def __init__(self, question):
        self.question = question
        self.answers = dict()

    def appendAnswer(self, givenAnswer):
        self.answers[givenAnswer] = 1

    def updateAnswer(self, givenAnswer):
        for ga in givenAnswer:
            if ga in self.answers:
                self.answers[ga] += 1
            else:
                self.appendAnswer(ga)