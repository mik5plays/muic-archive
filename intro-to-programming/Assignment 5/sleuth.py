# Assignment 5, Task 5
# Name: Theeradon Sarawek
# Collaborators: StackOverflow (?) only for runtime speed testing
# Time Spent: 45 minutes

"""
word_sleuth(grid, words) -> The master function
contains_word(grid, w) -> takes the grid from above and one single word and checks if the grid contains the word w.
make_unique(lst) -> Takes a list of words lst (with possible duplicates) and returns a list of words from lst, each with exactly one copy.
"""

def contains_word(grid: list[list[str]], w: str) -> bool:
    #First test -> Horizontally from left to right ONLY (assuming)
    for row in grid:
        x = "".join(row)
        if w in x:
            return True
    #Second test -> Vertically from top to bottom ONLY (not the other way round)
    for i in range(len(grid[0])): #Here assuming that every row is the same length (as it should be)
        x = []
        for j in range(len(grid)):
            x.append(grid[j][i])
        x = "".join(x)
        if w in x:
            return True

    diag = []
    x = list(range(len(grid[0])))
    y = x[::-1]
    for num in range(1, len(grid[0]) + 1):
        a, b, c, d = "", "", "", ""  # The letters on each diagonal
        for i in range(num):
            a += grid[y[num - i - 1]][x[i]]  # Top left to bottom right #1
            b += grid[x[i]][y[num - i - 1]]  # Top left to bottom right #2

            c += grid[x[num - i - 1]][x[i]]  # Bottom left to top right #1
            d += grid[y[i]][y[num - i - 1]]  # Bottom left to top right #2
        diag.append(a)
        diag.append(b)
        diag.append(c)
        diag.append(d)
    for word in diag:
        if w in word:
            return True

    #No more tests to run. If code reaches this point, assume word isn't in grid.
    return False

def make_unique(lst: list[str]) -> list[str]: #Can probably just replace this with set(x), but I did this assignment before we learned about sets.
    uniqueList = []
    for word in lst:
        if word not in uniqueList:
            uniqueList.append(word)
    return uniqueList

def word_sleuth(grid: list[list[str]], words: list[str]):
    foundWords = []
    for word in words:
        if contains_word(grid, word):
            foundWords.append(word)
    return make_unique(foundWords)

"""
I found that make_unique() might not be needed as contains_word() returns as soon as ONE instance of a word is found. 
Since my word_sleuth() function scans every word in words.
Not sure if this is optimal though. But I used it anyways (Assuming a test case has duplicates in the list "words")
This may also save time.

edit 8/11/2023:
Returned to edit my code so that all diagonals are searched.
Honestly, I forgot what either of these do but it doesn't break the code so I'll leave it here. 
"""

# theGrid = [["r","a","w","b","i","t"],["x","a","y","z","c","h"],["p","q","b","e","i","e"],["t","r","s","b","o","g"],["u","w","x","v","i","t"],["n","m","r","w","o","t"]]
# words = ["bog", "moon", "rabbit", "the", "bit", "raw", "bit"]
#
# print(word_sleuth(theGrid, words))
#
# assert sorted(word_sleuth(theGrid, words)) == sorted(["raw", "bit","rabbit", "bog", "the"])

import timeit, random
grid_size = 75
grid = [[random.choice('ABCDEFGHIJKLMNOPQRSTUVWXYZ')
         for _ in range(grid_size)] for _ in range(grid_size)]


num_words = 50
# Adjust the range for word length as needed
word_length = random.randint(3, 10)
words = [''.join(random.choice('ABCDEFGHIJKLMNOPQRSTUVWXYZ')
                 for _ in range(word_length)) for _ in range(num_words)]

# Time the function using timeit
execution_time = timeit.timeit(
    "print(word_sleuth(grid, words))", setup="from __main__ import word_sleuth, grid, words", number=1)

print(f"Execution time: {execution_time:.6f} seconds")