# Assignment 6, Task 6
# Name: Theeradon Sarawek
# Collaborators: -
# Time Spent: 4:00 hrs
# This version is for the 4x4 grid given in the example (i.e the original game)
# ----------------------------------

from typing import List, Tuple

Board = List[List[str]]

# Checks whether a given board has any
# possible move left. If no more moves,
# return True. Otherwise return False.
def isGameOver(board: Board) -> bool:
    movesLeft: bool = False
    # Gonna check every possible element in the list, starting from each row.
    # There may be redundancies (repeated checks), but it's easier to understand the code if they are left in.
    # Might remove them later.

    check = False
    for row in board:
        for item in row:
            if item == " ":
                check = True
            if item == "2048": #Win condition
                return True
    if check:
        return False
    #If there's a blank tile anywhere, then a move can be made.

    firstRow = board[0]
    secondRow = board[1]
    thirdRow = board[2]
    forthRow = board[3]

    if secondRow[0] == firstRow[0] or firstRow[1] == firstRow[0]:
        movesLeft = True
    if firstRow[1] == firstRow[2] or firstRow[3] == firstRow[2]:
        movesLeft = True
    if secondRow[3] == firstRow[3]:
        movesLeft = True

    # Second row, slightly different checks because considering up and down as well.
    if secondRow[0] == firstRow[0] or secondRow[0] == secondRow[1] or secondRow[0] == thirdRow[0]:
        movesLeft = True
    if secondRow[1] == secondRow[2] or secondRow[1] == firstRow[1] or secondRow[1] == thirdRow[1]:
        movesLeft = True
    if secondRow[2] == secondRow[3] or secondRow[2] == firstRow[2] or secondRow[2] == thirdRow[2]:
        movesLeft = True
    if secondRow[3] == firstRow[3] or secondRow[3] == thirdRow[3]:
        movesLeft = True

    # Third row, similar idea to second row
    if thirdRow[0] == secondRow[0] or thirdRow[0] == thirdRow[1] or thirdRow[0] == forthRow[0]:
        movesLeft = True
    if thirdRow[1] == thirdRow[2] or thirdRow[1] == secondRow[1] or thirdRow[1] == forthRow[1]:
        movesLeft = True
    if thirdRow[2] == thirdRow[3] or thirdRow[2] == secondRow[2] or thirdRow[2] == forthRow[2]:
        movesLeft = True
    if thirdRow[3] == secondRow[3] or thirdRow[3] == forthRow[3]:
        movesLeft = True

    # Forth row, Similar idea to first row
    if thirdRow[0] == forthRow[0] or forthRow[1] == forthRow[0]:
        movesLeft = True
    if forthRow[1] == forthRow[2] or forthRow[3] == forthRow[2]:
        movesLeft = True
    if thirdRow[3] == forthRow[3]:
        movesLeft = True

    return not movesLeft

# Returns a tuple (changed, new_board)
# where:
#  changed - a boolean indicating if
#            the board has changed.
#  new_board - the board after the user
#              presses the 'Up' key.
def doKeyUp(board: Board) -> Tuple[bool, Board]:
    #Step one: Move tiles upwards
    newBoard: Board = []
    for x in range(4):
        newBoard.append([" "," "," "," "])
    for i in range(4):
        temp: List[str] = []
        for j in range(4):
            if board[j][i] != " ":
                temp.append(board[j][i])
        for k in range(4):
            if k < len(temp):
                newBoard[k][i] = temp[k]

    #Step two: Merge identical tiles
    for i in range(4):
        count = 0
        while count < 3:
            if newBoard[count][i] == newBoard[count+1][i] and newBoard[count][i] != " ":
                newBoard[count][i] = str(int(newBoard[count+1][i])*2)
                newBoard[count+1][i] = " "
                count += 2
            else:
                count += 1

    #Step three: Move up again
    nb2 = []
    for x in range(4):
        nb2.append([" "," "," "," "])
    for i in range(4):
        temp: List[str] = []
        for j in range(4):
            if newBoard[j][i] != " ":
                temp.append(newBoard[j][i])
        for k in range(4):
            if k < len(temp):
                nb2[k][i] = temp[k]

    #Step four: Check if tile has changed
    boardChanged: bool = False
    for i in range(len(newBoard)):
        if nb2[i] != board[i]:
            boardChanged = True

    return boardChanged, nb2

# Returns a tuple (changed, new_board)
# where:
#  changed - a boolean indicating if
#            the board has changed.
#  new_board - the board after the user
#              presses the 'Down' key.


def doKeyDown(board: Board) -> Tuple[bool, Board]:
    #Step one: Move down
    newBoard: Board = []
    for x in range(4):
        newBoard.append([" "," "," "," "])
    for i in range(4):
        temp: List[str] = []
        for j in range(4):
            if board[j][i] != " ":
                temp.append(board[j][i])
        temp = temp[::-1]
        for k in range(1,5):
            if k <= len(temp):
                newBoard[-k][i] = temp[k-1]

    #Step two: Merge adjacent tiles
    for i in range(4):
        count = -1
        while count > -4:
            if newBoard[count][i] == newBoard[count-1][i] and newBoard[count][i] != " ":
                newBoard[count-1][i] = str(int(newBoard[count][i])*2)
                newBoard[count][i] = " "
                count -= 2
            else:
                count -= 1

    #Step three: Move down again
    nb2: Board = []
    for x in range(4):
        nb2.append([" "," "," "," "])
    for i in range(4):
        temp: List[str] = []
        for j in range(4):
            if newBoard[j][i] != " ":
                temp.append(newBoard[j][i])
        temp = temp[::-1]
        for k in range(1,5):
            if k <= len(temp):
                nb2[-k][i] = temp[k-1]

    #Step four, check if board has changed
    boardChanged: bool = False
    for i in range(len(nb2)):
        if nb2[i] != board[i]:
            boardChanged = True

    return boardChanged, nb2

# Returns a tuple (changed, new_board)
# where:
#  changed - a boolean indicating if
#            the board has changed.
#  new_board - the board after the user
#              presses the 'Left' key.


def doKeyLeft(board: Board) -> Tuple[bool, Board]:
    #Step one: Move left
    newBoard: board = []
    for row in board:
        temp = [x for x in row if x != " "]
        rowTemp = [" ", " ", " ", " "]
        for i in range(4):
            if i < len(temp):
                rowTemp[i] = temp[i]
        newBoard.append(rowTemp)

    #Step two: Merge adjacent tiles
    for row in newBoard:
        count = 0
        while count < 3:
            if row[count] == row[count+1] and row[count] != " ":
                row[count] = str(int(row[count])*2)
                row[count+1] = " "
                count += 2
            else:
                count += 1

    #Step three: Move left again
    for j in range(len(newBoard)):
        temp = [x for x in newBoard[j] if x != " "]
        rowTemp = [" ", " ", " ", " "]
        for i in range(4):
            if i <= len(temp)-1:
                rowTemp[i] = temp[i]
        newBoard[j] = rowTemp

    #Step four, check if board has changed
    boardChanged: bool = False
    for i in range(len(newBoard)):
        if newBoard[i] != board[i]:
            boardChanged = True

    return boardChanged, newBoard


# Returns a tuple (changed, new_board)
# where:
#  changed - a boolean indicating if
#            the board has changed.
#  new_board - the board after the user
#              presses the 'Right' key.
def doKeyRight(board: Board) -> Tuple[bool, Board]:
    # Step one: Move right.
    newBoard: Board = []
    for row in board:
        temp = [x for x in row if x != " "]
        temp = temp[::-1]
        rowTemp = [" ", " ", " ", " "]
        for i in range(1, 5):
            if i <= len(temp):
                rowTemp[-i] = temp[i - 1]
        newBoard.append(rowTemp)

    # Step two: Merge adjacent tiles
    for row in newBoard:
        count = -1
        while count > -4:
            if row[count] == row[count - 1] and row[count] != " ":
                row[count] = " "
                row[count - 1] = str(int(row[count - 1]) * 2)
                count -= 2
            else:
                count -= 1

    # Step three: Step one again
    for j in range(len(newBoard)):
        temp = [x for x in newBoard[j] if x != " "]
        temp = temp[::-1]
        rowTemp = [" ", " ", " ", " "]
        for i in range(1, 5):
            if i <= len(temp):
                rowTemp[-i] = temp[i - 1]
        newBoard[j] = rowTemp

    # Step four, check if board has changed
    boardChanged: bool = False
    for i in range(len(newBoard)):
        if newBoard[i] != board[i]:
            boardChanged = True

    return boardChanged, newBoard


# Returns a list of tuples (row, col)
# indicating where the empty spots are
def emptyPos(board: Board) -> List[Tuple[int, int]]:
    ans = []
    for i in range(4):
        for j in range(4):
            if board[i][j] == " ":
                ans.append((i,j))
    return ans


boardExample = [['2','4',' ','8'],
                ['16',' ','2','16'],
                ['16','2',' ','32'],
                ['32','128','16','8']]

print(emptyPos(boardExample))