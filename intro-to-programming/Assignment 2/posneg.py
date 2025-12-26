# Assignment 2, Task 2
# Name: Theeradon Sarawek
# Collaborators: -
# Time Spent: ~10 mins

a: int = int(input())
b: int = int(input())
negative: bool = input().lower() == 'true'

c: bool = ( ((a > 0 and b < 0) or (a < 0 and b > 0)) and not negative) or ( (a < 0 and b < 0) and negative)
print(c)