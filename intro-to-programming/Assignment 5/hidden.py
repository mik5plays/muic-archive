# Assignment 5, Task 2
# Name: Theeradon Sarawek
# Collaborators:
# Time Spent: 15 minutes


def is_hidden(s:str ,t:str) -> bool:
        s = s.lower() #Ignore cases
        t = t.lower() #Ignore cases

        foundIndex = 0
        for char in s:
            if foundIndex == len(t): #Exceeds the index, meaning that all characters have already been found in the order.
                return True
            if char == t[foundIndex]:
                foundIndex += 1
        return False

def test_isHidden():
    assert is_hidden("welcometothehotelcalifornia", "melon") == True
    assert is_hidden("welcometothehotelcalifornia", "space") == False
    assert is_hidden("TQ89MnQU3IC7t6", "MUIC") == True
    assert is_hidden("VhHTdipc07", "htc") == True
    assert is_hidden("VhHTdipc07", "hTc") == True
    assert is_hidden("VhHTdipc07", "vat") == False

test_isHidden() #Passed the assertion test
