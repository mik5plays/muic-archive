# Assignment 4, Task 5
# Name: Theeradon Sarawek
# Collaborators:
# Time Spent: 5 minutes

def allMultiplesOfK(k: int, lst: list[int]) -> bool:
    for num in lst:
        if num % k != 0:
            return False
    return True

