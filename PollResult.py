import csv
import pandas as pd

with open('pollReports/CSE3063_20201123_Mon_zoom_PollReport.csv', 'r') as file:
    reader = csv.DictReader(file)
    poll = [ ]
    x=1
    y=1
    prev=" "
    i=0
    questionList={ }
    j = 0;
    for row in reader:
        y = 1
        if i == 0:
            prev = row[None]
        if not row[None][0] in prev:
            prev = row[None]
            x += 1

        i += 1
        j = 0

        for q in row[None]:
            if j % 2 == 0:
                questionList[str(x) + "." + str(y)] = q
                y += 1
            j += 1

    for t in questionList:
        print(questionList)

"""
data=pd.read_csv('pollReports/CSE3063_20201123_Mon_zoom_PollReport.csv')

"""