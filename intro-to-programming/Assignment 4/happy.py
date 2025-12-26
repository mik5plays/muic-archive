# Assignment 4, Task 6
# Name: Theeradon Sarawek
# Collaborators:
# Time Spent: 20 minutes

def sumOfDigitsSquared(n: int) -> int:
    n = [int(x)**2 for x in list(str(n))]
    return sum(n) #There's probably a better way of doing it so it's more easily explainable

def isHappy(n: int) -> bool:
    sum: int = sumOfDigitsSquared(n)
    while sum not in [1,4]:
        sum = sumOfDigitsSquared(sum)
    if sum == 1:
        return True
    if sum == 4:
        return False

def kThHappy(k:int) -> int:
    happyTh: int = 0
    curr: int = 1
    while True:
        if isHappy(curr):
            happyTh += 1
            if happyTh == k:
                return curr
        curr += 1

