from typing import List, Dict
def winner(records: List[Dict[str,int]]) -> str:
    wins: dict = {}
    for round in records:
        x = list(round.items())
        roundWinner = max(x, key=lambda y: y[1])[0]
        if roundWinner not in wins:
            wins[roundWinner] = 1
        else:
            wins[roundWinner] += 1
    mostWins = max(list(wins.items()), key=lambda y: y[1])
    if len([x for x in wins if wins[x] == mostWins[1]]) > 1:
        return ""
    else:
        return mostWins[0]

# Chess
assert winner([{'A':2,'B':1}]) == 'A'
assert winner([{'A':3,'B':0},{'A':1,'C':2}]) == ''
assert winner([{'A':3,'B':0},{'A':1,'C':2},{'B':0,'C':3}]) == 'C'
assert winner([{'A':3,'B':0},{'A':1,'C':2},{'B':0,'D':3},{'A':2,'D':1}]) == 'A'
assert winner([{'A':3,'B':0},{'B':2,'C':1},{'C':2,'D':1},{'A':0,'D':3}]) == ''