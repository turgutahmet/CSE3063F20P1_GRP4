import xlwt

class PollOutput:
    def __init__(self):
        pass


    def creatingAttendanceFile(self,attendance,date):
        wb = xlwt.Workbook()  # create empty workbook object
        newsheet = wb.add_sheet('Attendance')  # sheet name can not be longer than 32 characters
        newsheet.write(0, 0, 'Student Number')
        newsheet.write(0, 1, 'First Name')
        newsheet.write(0, 2, 'Last Name')
        newsheet.write(0, 3, 'attendance rate')
        newsheet.write(0, 4, 'attendance percentage')
        j = 1
        for i in attendance.keys():
            newsheet.write(j, 0, i.studentNumber)
            newsheet.write(j, 1, i.firstName)
            newsheet.write(j, 2, i.lastName)
            newsheet.write(j, 3, 'attended ' + str(len(attendance[i])) + ' of ' + str(len(date)) + ' courses')
            newsheet.write(j, 4, str(round(len(attendance[i]) / len(date) * 100)) + '%')
            j += 1
        wb.save("./outputs/"+'Attendance.xls')


    def creatCalculateSuccessRate(self,PollName,numberOfQuestions,s_list):
        wb = xlwt.Workbook()
        newsheet = wb.add_sheet(PollName)
        newsheet.write(0, 0, 'Student Number')
        newsheet.write(0, 1, 'First Name')
        newsheet.write(0, 2, 'Last Name')
        j=3
        for i in range(0,numberOfQuestions):
            newsheet.write(0, j, 'Q'+str(i+1))
            j+=1
        newsheet.write(0, j, 'number of questions')
        newsheet.write(0, j+1, 'success rate')
        newsheet.write(0, j+2, 'success percentage')
        print(j)
        k=1
        for i in s_list.keys():
            newsheet.write(k,0,i.studentNumber)
            newsheet.write(k,1,i.firstName)
            newsheet.write(k,2,i.lastName)
            if len(s_list[i])<numberOfQuestions:
                for x in range(len(s_list[i])-2):
                    newsheet.write(k, 3+x, s_list[i][x])
                for n in range(len(s_list[i])-2,numberOfQuestions):
                    newsheet.write(k, 3 + n,'-')
                newsheet.write(k, j, numberOfQuestions)
                if s_list[i][-1]==0:
                    newsheet.write(k, j + 1,'-')
                    newsheet.write(k, j + 2, '-')
                else:
                    newsheet.write(k, j + 1, s_list[i][-2])
                    newsheet.write(k, j + 2, s_list[i][-1])
            else:
                for x in range(numberOfQuestions):
                    newsheet.write(k, 3 + x, s_list[i][x])
                newsheet.write(k, j, numberOfQuestions)
                newsheet.write(k, j+1, s_list[i][-2])
                newsheet.write(k, j+2, s_list[i][-1])
            k+=1

        wb.save("./outputs/"+PollName+'.xls')

    def outputSum(self, s_total_list):
        wb = xlwt.Workbook()
        new_sheet = wb.add_sheet("student_scores")
        new_sheet.write(0, 0, 'Student Number')
        new_sheet.write(0, 1, 'First Name')
        new_sheet.write(0, 2, 'Last Name')
        j = 3
        i = 0
        for s in s_total_list.values():
            if i == 0:
                for i in range(0, len(s), 2):
                    new_sheet.write(0, j, s[i])
                    j += 1
                i += 1

        k = 1
        for i in s_total_list.keys():
            j = 3
            new_sheet.write(k, 0, i.studentNumber)
            new_sheet.write(k, 1, i.firstName)
            new_sheet.write(k, 2, i.lastName)
            for r in range(1, len(s_total_list[i]), 2):
                new_sheet.write(k, j, s_total_list[i][r])
                j += 1
            k += 1

        wb.save("./outputs/"+"student_scores" + '.xls')