# Assignment 3, Task 3
# Name: Theeradon Sarawek
# Collaborators:
# Time Spent: 10 minutes

def kthDigit(x:int, b:int, k:int) -> int:
    result: int = 0
    for i in range(k+1):
        result = x%b
        x = x//b
    return result

print(kthDigit(987,16,2))



