from AnswerKey import *
from Config import *
import xlsxwriter
import re


class AnswerKeyConverter:

    def __init__(self):
        self.config = Config()

    def convert(self, path):
        with open(path, "r") as fp:
            lines = fp.readlines()
            flag = 0
            polls = list()
            poll = list()
            pollsAndQuestions = dict()
            for i in range(len(lines)):
                if "Poll " in lines[i]:
                    if flag == 1:
                        polls.append(poll)
                    poll = list()
                    poll.append(lines[i])
                    flag = 1
                    continue
                poll.append(lines[i])
                if i == len(lines) - 1:
                    polls.append(poll)

            for poll in polls:
                pollsQuestionsAndAnswers = dict()
                # Get poll name and number of questions
                line = list(poll)[0]
                line = line.split(":")
                line = str(line[1]).split("\t")
                pollName = line[0]
                question = ""
                answers = list()

                for i in range(1, len(poll)):

                    if poll[i] != "\n":
                        poll[i] = self.removeSpecialCharacters(poll[i])
                        if "( Single Choice)" in poll[i]:
                            temp = poll[i].split(" ( Single Choice)")
                            poll[i] = temp[0]
                            questionHead = poll[i].find(".")
                            question = poll[i][questionHead + 2:]
                            answers = list()
                            continue
                        if "( Multiple Choice)" in poll[i]:
                            temp = poll[i].split(" ( Multiple Choice)")
                            poll[i] = temp[0]
                            questionHead = poll[i].find(".")
                            question = poll[i][questionHead + 2:]
                            answers = list()
                            continue

                    if poll[i] != "\n":
                        poll[i] = poll[i].split(": ")
                        answer = poll[i][1]
                        answers.append(answer)
                    else:
                        answer = list()
                    pollsQuestionsAndAnswers[str(question)] = answers
                pollsAndQuestions[pollName] = pollsQuestionsAndAnswers
            answerKeys = list()
            for pollName in pollsAndQuestions:
                answerKey = AnswerKey(pollName)
                for question in pollsAndQuestions[pollName]:
                    answerOfQuestion = ""
                    for answer in pollsAndQuestions[pollName][question]:
                        answerOfQuestion += answer + ";"
                    answerOfQuestion = answerOfQuestion[0:len(answerOfQuestion) - 1]
                    answerKey.addQuestionAndAnswer(question, answerOfQuestion)
                answerKeys.append(answerKey)

            for answerKey in answerKeys:
                self.writeToXls(answerKey)

    def writeToXls(self, answerKey):
        workbook = xlsxwriter.Workbook(self.config.answerKeyDirectory + "/" + answerKey.pollName + ".XLS")
        worksheet = workbook.add_worksheet()

        worksheet.write(0, 0, answerKey.pollName)

        row = 1
        for i in answerKey.questionsAndAnswers:
            worksheet.write(row, 0, i)
            worksheet.write(row, 1, answerKey.questionsAndAnswers[i])
            row += 1
        workbook.close()

    def removeSpecialCharacters(self, cellValue):
        text = re.sub(r"[\r\n\t\x07\x0b]", "", cellValue)
        return text
