import json


class Config:

    def __init__(self):
        data = self.readJson()
        self.pollReportDirectory = data["poll report directory"]
        self.answerKeyDirectory = data["answer key directory"]
        self.answerKeyTxtDirectory = data["answer key txt directory"]
        self.studentListDirectory = data["student list directory"]
        self.attendanceChartsDirectory = data["attendance charts"]
        self.pollChartsDirectory = data["poll charts"]
        self.outputPollReportsDirectory = data["output poll reports"]
        self.attendanceReportsDirectory = data["attendance reports"]
        self.generalStatisticsDirectory = data["general statistics"]
        self.studentsPollReports = data["student statistics"]
        self.anomaliesDirectory = data["anomalies directory"]

    @staticmethod
    def readJson():
        with open('config.json') as data:
            data = json.load(data)
            return data
