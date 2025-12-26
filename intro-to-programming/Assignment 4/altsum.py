# Assignment 4, Task 1
# Name: Theeradon Sarawek
# Collaborators:
# Time Spent: 10 minutes

def altSum(lst: list[int]) -> int:
    sum = lst[0]
    for i in range(1, len(lst)):
        if i % 2 != 0:
            sum += lst[i]
        else:
            sum -= lst[i]
    return sum

# print(altSum([31,4,28,5,71]))