from Config import *
import shutil
from tkinter import *
from tkinter import filedialog
from PollAnalyzer import PollAnalyzer

pollAnalyzer = PollAnalyzer()


class GUI(Frame):
    def __init__(self, parent):
        self.parent = parent
        self.studentList = []
        self.pollList = []
        self.answerkeys = []
        Frame.__init__(self, parent)
        self.labelbar()
        self.getFiles()
        self.start()
        self.pack()

    # creat the lable bar
    def labelbar(self):
        self.frame1 = Frame(self, borderwidth=5, height=10, width=500, bg='white')
        self.frame1.pack(fill=BOTH, expand=TRUE)  # padx=5, pady=5)
        Label(self.frame1, text="Poll Analyzer", bg="black", width=500, font=8, fg="white").pack(
            fill=BOTH, expand=TRUE, padx=5, pady=(5, 10))

    def getFiles(self):
        self.frame2 = Frame(self.frame1, borderwidth=0, relief=GROOVE, bg='white')
        self.frame2.pack(fill=BOTH, expand=TRUE, pady=5, padx=5)
        addStudent = Button(self.frame2, height=2, width=30, text="Upload Student List", command=self.addStudentList,
                            background="SeaGreen", fg="white")
        addStudent.pack(expand=TRUE, padx=5, pady=5)
        addPolls = Button(self.frame2, height=2, width=30, text="Upload Polls", command=self.addPolls,
                          background="SeaGreen", fg="white")
        addPolls.pack(expand=TRUE, padx=5, pady=5)
        addAnswerKey = Button(self.frame2, height=2, width=30, text="Upload Answer key", command=self.addAnswerKey,
                              background="SeaGreen", fg="white")
        addAnswerKey.pack(expand=TRUE, padx=5, pady=5)

        self.frame3 = Frame(self.frame1, borderwidth=0, relief=GROOVE, bg='white')
        self.frame3.pack(fill=BOTH, expand=TRUE, pady=5, padx=5)

    def start(self):
        startAnalyzing = Button(self.frame3, height=2, width=20, text="Start Analyzing",
                                command=pollAnalyzer.startAnalyzer,
                                background="black", fg="white")
        startAnalyzing.pack(expand=TRUE, padx=5, pady=10)

    def addStudentList(self):
        filename = filedialog.askopenfilenames(initialdir="/", title="Select file",
                                               filetypes=(("exel files", "*.xlsx"), ("all files", "*.*")))
        try:
            config = Config()
            for file in filename:
                shutil.move(file, config.studentListDirectory)
        except shutil.Error:
            print("File name not found!!")
        print()

    def addPolls(self):
        filename = filedialog.askopenfilenames(initialdir="/", title="Select file",
                                               filetypes=(("exel files", "*.xlsx"), ("all files", "*.*")))
        try:
            config = Config()
            for file in filename:
                shutil.move(file, config.pollReportDirectory)
        except shutil.Error:
            print("File name not found!!")

    def addAnswerKey(self):
        filename = filedialog.askopenfilenames(initialdir="/", title="Select file",
                                               filetypes=(("exel files", "*.xlsx"), ("all files", "*.*")))
        try:
            config = Config()
            for file in filename:
                shutil.move(file, config.answerKeyTxtDirectory)
        except shutil.Error:
            print("File name not found!!")
