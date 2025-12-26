# Assignment 2, Task 4
# Name: Theeradon Sarawek
# Collaborators: -
# Time Spent: 20 minutes

a: int = int(input())
b: int = int(input())

diff = abs(a-b)
mp = (a+b) * 0.5
print(f"{int(mp - (0.5 * diff))} {int(mp + (0.5 * diff))}")