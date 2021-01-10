from Question import *
from Config import *
from Student import *
from Poll import *
import xlrd as xlrd
class PollAnalyzer():
    def __init__(self):
        self.students=[]
        self.questions=[]
        self.polls=[]
        self.pollResults=[]
        self.config= Config()

    def readStudent(self):
        f=xlrd.open_workbook(self.config.studentListDirectory)
        sheet = f.sheet_by_index(0)
        for i in range(13,sheet.nrows):
            try:
                int(sheet.cell_value(i, 2))
                newstudent=Student(sheet.cell_value(i, 2),sheet.cell_value(i, 4),sheet.cell_value(i, 7))
                self.students.append(newstudent)
            except ValueError:
                continue




