# Assignment 6, Task 2
# Name: Theeradon Sarawek
# Collaborators: -
# Time Spent: 30 minutes

def passwordOK(password: str) -> bool:
    isLowercase: bool = False
    isNum: bool = False
    isUpperCase: bool = False
    isSpecial: bool = False
    isAppropriateLength: bool = False

    for chr in password:
        if ord("a") <= ord(chr) <= ord("z"):
            isLowercase = True
        if ord("0") <= ord(chr) <= ord("9"):
            isNum = True
        if ord("A") <= ord(chr) <= ord("Z"):
            isUpperCase = True
        if chr in "$#@%!":
            isSpecial = True

    if 6 <= len(password) <= 12:
        isAppropriateLength = True

    for i in range(0, len(password)-2):
        if password[i] == password[i+1] == password[i+2]:
            return False

    return isLowercase and isNum and isUpperCase and isSpecial and isAppropriateLength

assert passwordOK('ABd1234@1') == True
assert passwordOK('f#9') == False
assert passwordOK('Abbbc1!f') == False
