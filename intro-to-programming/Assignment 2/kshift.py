# Assignment 2, Task 3
# Name: Theeradon Sarawek
# Collaborators: -
# Time Spent: 20 mins

st: str = input()
k: int = int(input())

pos = k % len(st) #Finding modulo so that it's easier for bigger values of K
stShift = st[len(st)-pos:len(st)] + st[0:len(st)-pos]

print(stShift)
#It works so I will ask no further questions.
