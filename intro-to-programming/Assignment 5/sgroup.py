# Assignment 5, Task 6
# Name: Theeradon Sarawek
# Collaborators:
# Time Spent: 30 minutes

def separate(data: list[int], k: int) -> list[list[int]]:
    #Finding gaps, I'll assume "data" is already sorted. Also will be editing data.
    returnData = [data]
    for a in range(k-1): #How many lists wanted...
        gap = 0
        largestGapIndex = 0
        largestGapListIndex = 0
        for j in range(len(returnData)):
            for i in range(len(returnData[j]) - 1):
                if returnData[j][i+1] - returnData[j][i] > gap: #This is so that if a same-sized gap is found later on, it will only consider the first (leftmost) one found.
                    gap = returnData[j][i+1] - returnData[j][i]
                    largestGapIndex = i
                    largestGapListIndex = j
        x = []
        for b in range(len(returnData)):
            if b == largestGapListIndex: #Here's where we split the list at the gap.
                x.append(returnData[b][0:largestGapIndex+1:1])
                x.append(returnData[b][largestGapIndex+1::1])
            else:
                x.append(returnData[b])
        returnData = x
    return returnData

def separateTest():
    assert separate([10, 12, 45, 47, 91, 98, 99], 3) == [[10, 12], [45, 47], [91, 98, 99]]

separateTest() #Passed the test.
