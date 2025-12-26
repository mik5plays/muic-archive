# Assignment 3, Task 2
# Name: Theeradon Sarawek
# Collaborators:
# Time Spent: 10 minutes

def price(vol: int) -> float:
    if 0 < vol < 20:
        return (vol*18) + 250
    elif 20 <= vol <= 100:
        return (vol*18) + (10*vol)
    elif vol > 100:
        return (vol*18)*0.99
    else:
        return 0
