# Assignment 3, Task 5
# Name: Theeradon Sarawek
# Collaborators:
# Time Spent: 20 minutes

def phoneWord2Num(word: str) -> int:
    result: str = ""
    result += check(word[0])
    result += check(word[1])
    result += check(word[2])
    result += check(word[3])
    result += check(word[4])
    result += check(word[5])
    result += check(word[6])
    return int(result)


def check(letter: str) -> str:
    letter = letter.lower()
    if letter in 'abc':
        return "2"
    if letter in 'def':
        return "3"
    if letter in 'ghi':
        return "4"
    if letter in 'jkl':
        return "5"
    if letter in 'mno':
        return "6"
    if letter in 'pqrs':
        return "7"
    if letter in 'tuv':
        return "8"
    if letter in 'wxyz':
        return "9"
    else: #In case the letter does not fall in any category..
        return "0"

