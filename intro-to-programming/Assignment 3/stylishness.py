# Assignment 3, Task 6
# Name: Theeradon Sarawek
# Collaborators:
# Time Spent: 5 minutes

def got_table(you:int, date:int) -> str:
    if (you >= 8 and date > 2) or (date >= 8 and you > 2):
        return "yes"
    elif you <= 2 or date <= 2:
        return "no"
    return "maybe"

