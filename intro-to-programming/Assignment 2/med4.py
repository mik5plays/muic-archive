# Assignment 2, Task 5
# Name: Theeradon Sarawek
# Collaborators: -
# Time Spent: 20 minutes

p: int = int(input())
q: int = int(input())
r: int = int(input())
s: int = int(input())

biggest = max(p, q, r, s)
smallest = min(p, q, r, s)
sum = p + q + r + s

median = (sum - biggest - smallest) / 2
print(median)

