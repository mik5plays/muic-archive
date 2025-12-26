# Assignment 2, Task 6
# Name: Theeradon Sarawek
# Collaborators: -
# Time Spent: 15 mins

hour: int = int(input())
minute: int = int(input())
meowing:bool = input().lower() == 'true'

isTrouble = (hour == 6 and minute < 30 and meowing) or (hour < 6 and meowing) or (hour == 21 and minute >= 1 and meowing) or (hour > 21 and meowing)

print(isTrouble)