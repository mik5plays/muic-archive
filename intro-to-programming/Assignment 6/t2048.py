# Assignment 6, Task 6
# Name: Theeradon Sarawek
# Collaborators: -
# Time Spent: 5:00 hrs
# This version is for the flexible grid.
#
# ----------------------------------

from typing import List, Tuple

Board = List[List[str]]

# Checks whether a given board has any
# possible move left. If no more moves,
# return True. Otherwise return False.

boardExample = [['2','4','16','8'],
                ['16','8','2','16'],
                ['8','2','4','32'],
                ['32','128','16','8']]

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
    rows = len(board)
    columns = len(board[0])

    for row in range(1, rows):
        for column in range(1, columns):
            if row == rows - 1: #The final row
                if column == 1:
                    if board[row][column-1] in [board[row-1][column-1]]:
                        return False
                if board[row][column] in [board[row-1][column], board[row][column-1]]:
                    return False
            else:
                if column == 1:
                    if board[row][column-1] in [board[row-1][column-1], board[row+1][column-1]]:
                        return False
                if row == 1:
                    if board[row-1][column] in [board[row-1][column-1]]:
                        return False
                if board[row][column] in [board[row-1][column], board[row][column-1], board[row+1][column]]:
                    return False


    return True

print(isGameOver(boardExample))

# Returns a tuple (changed, new_board)
# where:
#  changed - a boolean indicating if
#            the board has changed.
#  new_board - the board after the user
#              presses the 'Up' key.
def doKeyUp(board: Board) -> Tuple[bool, Board]:
    #Step one: Move tiles upwards
    rows = len(board)
    columns = len(board[0])
    newBoard: Board = []

    for x in range(rows):
        temp = []
        for y in range(columns):
            temp.append(" ")
        newBoard.append(temp)

    for i in range(columns):
        temp: List[str] = []
        for j in range(rows):
            if board[j][i] != " ":
                temp.append(board[j][i])
        for k in range(rows):
            if k < len(temp):
                newBoard[k][i] = temp[k]

    #Step two: Merge identical tiles
    for i in range(columns):
        count = 0
        while count < rows-1:
            if newBoard[count][i] == newBoard[count+1][i] and newBoard[count][i] != " ":
                newBoard[count][i] = str(int(newBoard[count+1][i])*2)
                newBoard[count+1][i] = " "
                count += 2
            else:
                count += 1

    #Step three: Move up again

    nb2: List[str] = []
    for x in range(rows):
        temp = []
        for y in range(columns):
            temp.append(" ")
        nb2.append(temp)

    for i in range(columns):
        temp: List[str] = []
        for j in range(rows):
            if newBoard[j][i] != " ":
                temp.append(newBoard[j][i])
        for k in range(rows):
            if k < len(temp):
                nb2[k][i] = temp[k]

    #Step four: Check if tile has changed
    boardChanged: bool = False
    for i in range(rows):
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
    columns = len(board[0])
    rows = len(board)
    newBoard = []

    from pprint import pprint

    for x in range(rows):
        temp = []
        for y in range(columns):
            temp.append(" ")
        newBoard.append(temp)

    for i in range(columns):
        temp: list[str] = []
        for j in range(rows):
            if board[j][i] != " ":
                temp.append(board[j][i])
        temp = temp[::-1]
        for k in range(rows):
            if k < len(temp):
                newBoard[-k - 1][i] = temp[k]

    for i in range(columns):
        count = -1
        while count > -rows:
            if newBoard[count][i] == newBoard[count - 1][i] and newBoard[count][i] != " ":
                newBoard[count - 1][i] = str(int(newBoard[count][i]) * 2)
                newBoard[count][i] = " "
                count -= 2
            else:
                count -= 1

    nb2 = []
    for x in range(rows):
        temp = []
        for y in range(columns):
            temp.append(" ")
        nb2.append(temp)

    for i in range(columns):
        temp: list[str] = []
        for j in range(rows):
            if newBoard[j][i] != " ":
                temp.append(newBoard[j][i])
        temp = temp[::-1]
        for k in range(rows):
            if k < len(temp):
                nb2[-k - 1][i] = temp[k]

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
    rows = len(board)
    columns = len(board[0])

    for row in board:
        temp = [x for x in row if x != " "]
        rowTemp = [" "]*columns
        for i in range(columns):
            if i < len(temp):
                rowTemp[i] = temp[i]
        newBoard.append(rowTemp)

    #Step two: Merge adjacent tiles
    for row in newBoard:
        count = 0
        while count < columns-1:
            if row[count] == row[count+1] and row[count] != " ":
                row[count] = str(int(row[count])*2)
                row[count+1] = " "
                count += 2
            else:
                count += 1

    #Step three: Move left again
    for row in newBoard:
        temp = [x for x in row if x != " "]
        rowTemp = [" "]*columns
        for i in range(columns):
            if i < len(temp):
                rowTemp[i] = temp[i]

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
    rows = len(board)
    columns = len(board[0])

    for row in board:
        temp = [x for x in row if x != " "]
        temp = temp[::-1]
        rowTemp = [" "]*columns
        for i in range(1, columns):
            if i <= len(temp):
                rowTemp[-i] = temp[i - 1]
        newBoard.append(rowTemp)

    # Step two: Merge adjacent tiles
    for row in newBoard:
        count = -1
        while count > -(columns):
            if row[count] == row[count - 1] and row[count] != " ":
                row[count] = " "
                row[count - 1] = str(int(row[count - 1]) * 2)
                count -= 2
            else:
                count -= 1

    # Step three: Step one again
    for j in range(rows):
        temp = [x for x in newBoard[j] if x != " "]
        temp = temp[::-1]
        rowTemp = [" "]*columns
        for i in range(1, columns):
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


# boardExample = [['2','4',' ','8'],
#                 ['16',' ','2','16'],
#                 ['16','2',' ','32'],
#                 ['32','128','16','8']]
#
# print(emptyPos(boardExample))