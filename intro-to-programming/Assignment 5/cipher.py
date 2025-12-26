# Assignment 5, Task 3
# Name: Theeradon Sarawek
# Collaborators:
# Time Spent: 25 minutes

def enc(msg: str, key: int) -> str:
    msgStr = list(msg) #splitting the string to make it easier.
    output = []
    if len(msgStr)%key == 0:
        x = int(len(msgStr)/key)
    else:
        x = ((len(msgStr)//key) + 1)
    for i in range(x):
        output.append([])
    appendIndex = 0
    strIndex = 0
    outputIndex = 0
    while strIndex < len(msgStr):
        output[outputIndex].append(msgStr[strIndex])
        strIndex += 1
        appendIndex += 1
        if appendIndex == key:
            outputIndex += 1
            appendIndex = 0
    for i in range(appendIndex, key): #Filling the rest of the space
        output[-1].append(None)
    outputStr = []
    for i in range(key):
        for j in range(len(output)):
            if output[j][i] is not None:
                outputStr.append(output[j][i])
    return "".join(outputStr)

def encTest():
    assert enc("abc",2) == 'acb'
    assert enc("monosodium glutamate", 7)=='mitouanmmo asgtoledu'
    assert enc("polylogarithmicsubexponential", 3)=='pygimseonaolatiuxntllorhcbpei'

encTest() #It passed.

# pprint(enc("monosodium glutamate", 7))




