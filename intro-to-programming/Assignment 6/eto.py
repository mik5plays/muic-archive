# Assignment 6, Task 4
# Name: Theeradon Sarawek
# Collaborators: -
# Time Spent: 10 minutes

def eto(lst: list[int]) -> list[int]:
    if len(lst) == 0:
        return lst
    else:
        if lst[0]%2 == 0: #If even,
            return [lst[0]] + eto(lst[1:])
        else: #If odd,
            return eto(lst[1:]) + [lst[0]]

print(eto([8,-2,3,-3,-1,5,8,-1,5]))

