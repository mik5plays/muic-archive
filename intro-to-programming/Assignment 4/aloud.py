# Assignment 4, Task 4
# Name: Theeradon Sarawek
# Collaborators:
# Time Spent: 25 minutes

def readAloud(lst: list[int]) -> list[int]:
    returnList = []
    for i in range(len(lst)):
        if i == 0:
            curNum = lst[0]
            freq = 1
        else:
            if lst[i] == curNum:
                freq += 1
            else:
                returnList.append(freq)
                returnList.append(curNum)
                curNum = lst[i]
                freq = 1
            if i == len(lst) - 1: #The last number so has to be added to the list
                returnList.append(freq)
                returnList.append(curNum)
    return returnList

