# Assignment 6, Task 1
# Name: Theeradon Sarawek
# Collaborators: -
# Time Spent: 1 hour

from pprint import pprint
def charHistogram(filename: str) -> None:
    file = open(filename, 'r')
    filename = []
    for x in file.readlines():
        filename.append((x.strip()).lower())
    filename = (" ").join(filename)
    data = {}
    for letter in filename:
        if ord("a") <= ord(letter) <= ord("z"): #Is a lowercase letter...
            if letter not in data.keys():
                data[letter] = 1
            else:
                data[letter] += 1
    data = list(tuple(zip(data.values(), data.keys())))
    data = sorted(data, key=lambda x: x[0], reverse=True)
    secondData = {}
    for item in data:
        if item[0] not in secondData.keys():
            secondData[item[0]] = [item[1]]
        else:
            secondData[item[0]].append(item[1])
    secondData = tuple(zip(secondData.keys(), secondData.values()))
    secondData = sorted(secondData, key=lambda x: x[0], reverse=True)
    for item in secondData:
        foo = sorted(item[1], key=lambda y: ord(y))
        for letter in foo:
            print(f"{letter} {'+'*item[0]}")

    file.close()


# charHistogram("charHist.txt")
# charHistogram("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent ac sem lorem. Integer elementum ultrices purus, sit amet malesuada tortor pharetra ac. Vestibulum sapien nibh, dapibus nec bibendum sit amet, sodales id justo.")

# charHistogram("aaabbcczzddeeeee")''