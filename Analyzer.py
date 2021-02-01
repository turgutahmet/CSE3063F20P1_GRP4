from PollOutput import *
from matplotlib import pyplot as plt
import matplotlib.backends.backend_pdf


class Analyzer:

    def __init__(self, students, pollReports):
        self.students = students
        self.pollReports = pollReports
        self.pollOutput = PollOutput()
        self.config = Config()

    def startAnalyzing(self, logger):
        self.attendance()
        logger.info("Attendances have been analyzed.")
        self.calculateSuccessRate()
        logger.info("Students' success rates have been computed.")
        self.statisticsGraph()
        logger.info("Questions' pie charts have been drawn.")

    def attendance(self):
        attendance = dict((i, []) for i in self.students)
        date = []
        dateOfPoll = " "
        condition = 0
        for i in range(0, len(self.pollReports)):
            attendancePoll = 0
            j = self.pollReports[i]
            if i == 0:
                dateOfPoll = j.date
            if i + 1 < len(self.pollReports):
                if not dateOfPoll == self.pollReports[i + 1].date:
                    dateOfPoll = self.pollReports[i + 1].date
                    condition = 1
            else:
                condition = 1
            if not j.date in date:
                date.append(j.date)
            for x in j.questionsAndGivenAnswers:
                if x.student in self.students:
                    if not j.date in attendance[x.student]:
                        attendance[x.student].append(j.date)
            if condition == 1:
                for stu in attendance.keys():
                    for dates in attendance[stu]:
                        if dates == j.date:
                            attendancePoll += 1
                pdfName = self.config.attendanceChartsDirectory + str(j.date) + "_attendance.pdf"
                with matplotlib.backends.backend_pdf.PdfPages(pdfName) as export_pdf:
                    attAnsAndPer = {}
                    attAnsAndPer["Absent " + str(len(self.students) - attendancePoll)] = len(
                        self.students) - attendancePoll
                    attAnsAndPer["Attended " + str(attendancePoll)] = attendancePoll
                    plt.pie(attAnsAndPer.values(), labels=attAnsAndPer.keys(), autopct='%.1f%%',
                            wedgeprops={'linewidth': 1, 'linestyle': 'solid', 'antialiased': True})
                    export_pdf.savefig()
                    plt.close()
                condition = 0
        attendanceStat = {}
        for i in attendance.keys():
            attendanceStat[i] = (len(date), len(attendance[i]), len(attendance[i]) / len(date) * 100)

        self.pollOutput.creatingAttendanceFile(attendance, date)

    def calculateSuccessRate(self):
        s_total_list = dict((i, []) for i in self.students)
        for pr in self.pollReports:
            s_list = dict((i, []) for i in self.students)
            if pr.poll.pollName != 'Attendance Poll':
                for s in self.students:
                    total_right = 0
                    for r in pr.pollReportRows:
                        if r.student == s:
                            for q in r.questionsAndGivenAnswers:
                                if q.isCorrect:
                                    s_list[s].append(1)
                                    total_right += 1
                                else:
                                    s_list[s].append(0)
                    s_total_list[s].append(pr.poll.pollName + " " + pr.date)
                    s_total_list[s].append(((total_right / len(pr.poll.questions)) * 10000) / 100)
                    s_list[s].append(str(total_right) + "/" + str(len(pr.poll.questions)))
                    s_list[s].append(((total_right / len(pr.poll.questions)) * 10000) / 100)

                outputName = pr.date + " " + pr.poll.pollName
                self.pollOutput.creatCalculateSuccessRate(outputName, len(pr.poll.questions), s_list)
        self.pollOutput.outputSum(s_total_list)

    def statisticsGraph(self):
        for pr in self.pollReports:
            if not "Attendance" in pr.poll.pollName:
                pdfName = self.config.pollChartsDirectory + str(pr.date) + " " + str(pr.poll.pollName) + ".pdf"
                with matplotlib.backends.backend_pdf.PdfPages(pdfName) as export_pdf:
                    for q in pr.questionsInPollReport:
                        ansAndPer = {}
                        colors = []
                        red = 'red'
                        green = 'lime'
                        total = 0
                        for i in q.answers.values():
                            total += i
                        for ans in q.answers.keys():
                            if ans in q.question.correctAnswer:
                                colors.append(green)
                            else:
                                colors.append(red)
                            ansAndPer[ans] = int((q.answers[ans] / total) * 10000) / 100
                        fig = plt.figure(figsize=(15, 10))
                        plt.title(q.question.questionText, fontsize=10)
                        plt.pie(ansAndPer.values(), labels=ansAndPer.keys(), colors=colors, autopct='%.1f%%',
                                wedgeprops={'linewidth': 1, 'edgecolor': 'black', 'linestyle': 'solid',
                                            'antialiased': True})
                        export_pdf.savefig()
                        plt.close()
