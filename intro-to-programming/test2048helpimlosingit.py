board =         [['2',' ',' ','8'],
                ['4',' ','2','16'],
                ['4','2',' ','32'],
                ['16','128','16','8']]

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
            newBoard[count-1][i] = str(int(newBoard[count][i]) * 2)
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
        if board[j][i] != " ":
            temp.append(board[j][i])
    temp = temp[::-1]
    for k in range(rows):
        if k < len(temp):
            nb2[-k - 1][i] = temp[k]

from pprint import pprint
pprint(nb2)


