# Assignment 5, Task 1
# Name: Theeradon Sarawek
# Collaborators:
# Time Spent: 10 minutes

#The pattern is that it adds numbers based on the previous list.

def loveTri(n: int) -> list[list[int]]:
    outputList = [[1]] #Assuming n is already at least 1 since it has to be more than 0
    for i in range(1, n):
        x: list[int] = [outputList[i-1][-1]]
        for j in range(len(outputList[i-1])):
            x.append(x[j] + outputList[i-1][j])
        outputList.append(x)
    return outputList

# from pprint import pprint
# pprint(loveTri(2))



