from typing import Tuple, List
from pprint import pprint
Board = List[List[str]]

def right(board: Board) -> Tuple[bool, Board]:
    #Step one: Move right.
    newBoard: Board = []
    for row in board:
        temp = [x for x in row if x != " "]
        temp = temp[::-1]
        rowTemp = [" "," "," "," "]
        for i in range(1,5):
            if i <= len(temp):
                rowTemp[-i] = temp[i-1]
        newBoard.append(rowTemp)

    #Step two: Merge adjacent tiles
    for row in newBoard:
        count = -1
        while count > -4:
            if row[count] == row[count-1] and row[count] != " ":
                row[count] = " "
                row[count-1] = str(int(row[count-1]) * 2)
                count -= 2
            else:
                count -= 1

    #Step three: Step one again
    for j in range(len(newBoard)):
        temp = [x for x in newBoard[j] if x != " "]
        temp = temp[::-1]
        rowTemp = [" "," "," "," "]
        for i in range(1,5):
            if i <= len(temp):
                rowTemp[-i] = temp[i-1]
        newBoard[j] = rowTemp

    #Step four, check if board has changed
    boardChanged: bool = False
    for i in range(len(newBoard)):
        if newBoard[i] != board[i]:
            boardChanged = True

    return boardChanged, newBoard

def left(board: Board):
    #Step one: Move left
    newBoard: board = []
    for row in board:
        temp = [x for x in row if x != " "]
        rowTemp = [" ", " ", " ", " "]
        for i in range(4):
            if i < len(temp):
                rowTemp[i] = temp[i]
        newBoard.append(rowTemp)

    pprint(newBoard)
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

def up(board: Board):
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
    pprint(newBoard)

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
    pprint(newBoard)
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

def down(board: Board):
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

boardExample = [['2','4',' ','8'],
                ['2',' ','2','16'],
                ['16','2',' ','32'],
                ['32','128','16','8']]
board2 = [['2','4','16','8'],
                [' ',' ','16','16'],
                [' ','2','4','32'],
                ['32','128','16','8']]
# pprint(right(boardExample))
pprint(down(board2))


