# Assignment 4, Task 3
# Name: Theeradon Sarawek
# Collaborators:
# Time Spent: 20 minutes

def triangle(k:int) -> None:
    for i in range(k):
        x = ['#']*(2*k - 1)
        a = len(x)//2 #The middle
        for j in range(a-i, a+i+1):
            x[j] = '*'
        print("".join(x))

def diamond(k:int) -> None:
    #First half of the diamond (same as usual triangle with bigger border)
    for i in range(k):
        x = ['#']*(2*k + 1)
        a = len(x)//2 #The middle
        for j in range(a-i, a+i+1):
            x[j] = '*'
        print("".join(x))
    #Second half of the diamond (upside down)
    for i in range(k-1,-1,-1):
        x = ['#']*(2*k + 1)
        a = len(x)//2 #The middle
        for j in range(a-i, a+i+1):
            x[j] = '*'
        print("".join(x))

