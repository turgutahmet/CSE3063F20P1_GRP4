import json


class Config:

    def __init__(self):
        data = self.readJson()
        self.pollReportDirectory = data["poll report directory"]
        self.answerKeyDirectory = data["answer key directory"]
        self.studentListDirectory = data["student list directory"]

    @staticmethod
    def readJson():
        with open('config.json') as data:
            data = json.load(data)
            return data