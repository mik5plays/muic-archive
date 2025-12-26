# Assignment 3, Task 1
# Name: Theeradon Sarawek
# Collaborators:
# Time Spent: 10 minutes

def my_min(p:float,q:float,r:float) -> float:
    if p < q and p < r:
        return p
    elif q < p and q < r:
        return q
    else:
        return r

def my_mean(p:float,q:float,r:float) -> float:
    return (p+q+r)/3

def my_med(p:float,q:float,r:float) -> float:
    if r < p < q or q < p < r:
        return p
    if r < q < p or p < q < r:
        return q
    else:
        return r

