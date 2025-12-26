# Assignment 7, Task 4
# Name: Theeradon Sarawek
# Collaborators: -
# Time Spent: 1 hour


from __future__ import annotations
import csv

class Bid:
    def __init__(self, bid_id, bidder_id, auction):
        self.bid_id = bid_id
        self.bidder_id = bidder_id
        self.auction = auction

    def __repr__(self):
        return f"Bid({self.bid_id}, {self.bidder_id}, {self.auction})"

    def __str__(self):
        return f"Bid ID: {self.bid_id} | Bidder ID: {self.bidder_id} | Auction Name: {self.auction}"

    def __gt__(self, other: Bid):
        return self.bid_id > other.bid_id

    def __lt__(self, other: Bid):
        return self.bid_id < other.bid_id

    def __ge__(self, other: Bid):
        return self.bid_id >= other.bid_id

    def __le__(self, other: Bid):
        return self.bid_id <= other.bid_id

    def __eq__(self, other: Bid):
        return self.bid_id == other.bid_id

class Auction:
    def __init__(self, auction):
        self.auction = auction
        self.winner = None
        self.price: float = 1.0

    def __repr__(self):
        return f"Auction(Price = {self.price} | Winner = {self.winner})"

    def placeBid(self, bidder_id):
        self.price += 1.5
        self.winner = bidder_id

def CSV2List(csvFilename: str) -> list[Bid]:
    #Assuming that the csv file has three columns in this order: bid_id, bidder_id, auction
    with open(csvFilename, "r") as file:
        ans = []
        fileLines = []
        for x in csv.reader(file):
            fileLines.append(x)

        for line in fileLines[1:]:
            bid = Bid(line[0], line[1], line[2])
            ans.append(bid)
        return sorted(ans, key=lambda x: int(x.bid_id))

def mostPopularAuction(bidList: list[Bid]) -> set[str]:
    biddersPerAuction: dict = {}
    for bid in bidList:
        if bid.auction in biddersPerAuction.keys():
            biddersPerAuction[bid.auction].append(bid.bidder_id)
        else:
            biddersPerAuction[bid.auction] = [bid.bidder_id]

    for auction in biddersPerAuction: #We want distinct bidders
        biddersPerAuction[auction] = set(biddersPerAuction[auction])

    mostPopular = max(biddersPerAuction, key=lambda x: len(biddersPerAuction[x]))

    ans: set = set()
    for key in biddersPerAuction:
        if len(biddersPerAuction[key]) == len(biddersPerAuction[mostPopular]):
            ans.add(key)

    return ans

def auctionWinners(bidList: list[Bid]) -> dict[str, Auction]:
    auctions: dict = {}
    #Doing the bids
    for bid in bidList:
        if bid.auction not in auctions.keys():
            auctions[bid.auction] = Auction(bid.auction)
        auctions[bid.auction].placeBid(bid.bidder_id)

    return auctions



