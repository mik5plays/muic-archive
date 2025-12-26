# Assignment 7, Task 2
# Name: Theeradon Sarawek
# Collaborators: Used GeeksForGeeks' tutorial as a basis of my combination function, as I needed to refresh my understanding on combinations + permutations.
# Time Spent: 3 hours

#ALL FUNCTIONS ASSUME THESE ARE THE EXACT VALUES OF EACH SUIT AND VALUE.
#Also, not sure why it's not passing the submission verification even though I tested myself and it seems to not time out.
#I will submit as is anyway. Hopefully it still shows up.
from typing import List
Hand = set[tuple[str, str]]
suits = ["Club", "Diamond", "Heart", "Spade"]
values = ["A", "K", "Q", "J", 10, 9, 8, 7, 6, 5, 4, 3, 2]

def is_straight_flush(h: Hand) -> bool:
    #Automatically assume there will be no duplicates because of set.
    x: list = [item for item in h]
    suit: str = x[0][0]
    for item in x: #Checking if everything's in the same suit
        if item[0] != suit:
            return False
    valueOfHand: list = [card[1] for card in x]
    if "A" not in valueOfHand:
        firstValueIndex: int = min([values.index(y) for y in valueOfHand])
        winHand: list = values[firstValueIndex:firstValueIndex+5]
        for card in valueOfHand:
            if card not in winHand:
                return False
        return True
    else:
        winHands = [[5,4,3,2,"A"],["A","K","Q","J",10]]
        inWin1, inWin2 = True, True
        #Checking both win conditions with an Ace involved
        for card in valueOfHand:
            if card not in winHands[0]:
                inWin1 = False
            if card not in winHands[1]:
                inWin2 = False
        if inWin1 or inWin2:
            return True
        return False

def is_four_of_a_kind(h: Hand) -> bool:
    suit: list[str] = [card[0] for card in h]
    cardValues: list = [card[1] for card in h]
    if len(set(cardValues)) == 2 and len(set(suit)) == 4: #If there's not just two different values from all sets, then reject.
        #Test for both values
        cardValues = list(set(cardValues)) #Remove duplicates
        for value in cardValues:
            if ("Club", value) in h and ("Diamond", value) in h and ("Heart", value) in h and ("Spade", value) in h:
                return True
    return False

def is_full_house(h: Hand) -> bool:
    suit: list[str] = [card[0] for card in h]
    cardValues: list = [card[1] for card in h]
    if len(set(cardValues)) == 2: #Can only have two possible matching values anyway
        for value in set(cardValues):
            if len([x for x in cardValues if x == value]) in [2,3]:
                return True
        return False
    else:
        return False

def is_two_pair(h: Hand) -> bool:
    suit: list[str] = [card[0] for card in h]
    cardValues: list = [card[1] for card in h]
    if len(set(cardValues)) == 3:  # Can only have 3 possible matching values anyway
        repeatValues: list = []
        for value in set(cardValues):
            if len([x for x in cardValues if x == value]) in [2,1]:
                repeatValues.append(len([x for x in cardValues if x == value]))
        if sorted(repeatValues) == [1,2,2]:
            return True
    return False

def combinations(x: list, r: int): #This is based on GeeksForGeeks' example. I ran out of ideas :(
    if r == 0:
        return [[]]
    l = []
    for i in range(0, len(x)):
        m = x[i]
        remLst = x[i + 1:]
        remainlst_combo = combinations(remLst, r - 1)
        for p in remainlst_combo:
            l.append([m, *p])
    return l

def all_hands() -> List[Hand]:
    allCards: list = []
    for suit in suits:
        for x in enumerate(values):
            allCards.append((suit,x[1]))
    allPossibleHands = combinations(allCards, 5)
    return allPossibleHands

all_possible_hands = all_hands()

def all_straight_flush() -> List[Hand]:
    ans: List = []
    for hand in all_possible_hands:
        if is_straight_flush(hand):
            ans.append(hand)
    return ans

def all_four_of_a_kind() -> List[Hand]:
    ans: List = []
    for hand in all_possible_hands:
        if is_four_of_a_kind(hand):
            ans.append(hand)
    return ans

def all_full_house() -> List[Hand]:
    ans: List = []
    for hand in all_possible_hands:
        if is_full_house(hand):
            ans.append(hand)
    return ans

def all_two_pair() -> List[Hand]:
    ans: List = []
    for hand in all_possible_hands:
        if is_two_pair(hand):
            ans.append(hand)
    return ans

all_straight_flush()
all_four_of_a_kind()
all_full_house()
all_two_pair()

print("Finished.")
