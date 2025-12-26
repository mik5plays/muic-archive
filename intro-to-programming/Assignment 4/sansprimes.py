# Assignment 4, Task 7
# Name: Theeradon Sarawek
# Collaborators:
# Time Spent: 15 minutes

def is_prime(n:int) -> bool:
    if n < 2:
        return False
    #Gonna try every number from 1 to itself. If it doesn't reach a "score" of 3 (divisible by 1, itself, and 1 other number), it is a prime.
    numDivs: int = 0
    for i in range(1, n+1):
        if n%i == 0:
            numDivs += 1
    if numDivs < 3:
        return True
    return False

def sans_primes(numbers: list[int]) -> list[int]:
    followingPrime: bool = False
    for i in range(len(numbers)):
        if is_prime(numbers[i]):
            if not followingPrime:
                followingPrime = True
            numbers[i] = "" #Blank placeholder
        elif not is_prime(numbers[i]):
            if followingPrime:
                numbers[i] = "" #Blank placeholder
            followingPrime = False
    #Removing the placeholder for the return value.
    return [x for x in numbers if x != ""]


