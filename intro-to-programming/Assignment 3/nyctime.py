# Assignment 3, Task 6
# Name: Theeradon Sarawek
# Collaborators:
# Time Spent: 15 minutes

def nycHour(bkkHour: int) -> str:
    result = bkkHour - 11
    if result < 0: #Goes back to the previous day..
        result = 24 + result
    if 12 <= result <= 23:
        if result != 12:
            return f"{result-12}pm"
        return "12pm"
    elif 0 <= result < 12:
        if result != 0:
            return f"{result}am"
        return "12am"


