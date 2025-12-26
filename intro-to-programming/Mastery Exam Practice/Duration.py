from __future__ import annotations

class Duration:
    def __init__(self):
        self.hours = 0
        self.days = 0
        self.weeks = 0

    def extendBy(self, hr: int) -> None:
        potentialDays: int = hr // 24
        # print(potentialDays)
        excessHours: int = hr - (24*potentialDays)
        # print(excessHours)

        for i in range(potentialDays):
            self.days += 1
            if self.days > 6:
                self.weeks += 1
                self.days = 0

        if self.hours + excessHours > 23:
            temp: int = self.hours + excessHours - 24
            self.days += 1
            self.hours = temp
            if self.days > 6:
                self.weeks += 1
                self.days = 0
        else:
            self.hours += excessHours


    def isShorterThan(self, other: Duration) -> bool:
        totalOther: int = other.hours + (other.days*24) + (other.weeks*24*7)
        total: int = self.hours + (self.days*24) + (self.weeks*24*7)
        return total < totalOther

    def __str__(self):
        return f"{self.weeks} weeks, {self.days} days, {self.hours} hours."

a = Duration()
a.extendBy(642)
a.extendBy(165)
print(a)

def check():
    ### duration ###
    eoyfmln = Duration()
    qdrycoi = Duration()
    bvugkhj = Duration()
    eoyfmln.extendBy(145)
    assert (eoyfmln.weeks == 0 and eoyfmln.days == 6 and eoyfmln.hours == 1)
    assert (eoyfmln.isShorterThan(eoyfmln) == False)
    qdrycoi.extendBy(642)
    assert (qdrycoi.weeks == 3 and qdrycoi.days == 5 and qdrycoi.hours == 18)
    assert (qdrycoi.isShorterThan(qdrycoi) == False)
    eoyfmln.extendBy(980)
    assert (eoyfmln.weeks == 6 and eoyfmln.days == 4 and eoyfmln.hours == 21)
    assert (qdrycoi.isShorterThan(eoyfmln) == True)
    qdrycoi.extendBy(165)
    assert (qdrycoi.weeks == 4 and qdrycoi.days == 5 and qdrycoi.hours == 15)
    assert (qdrycoi.isShorterThan(qdrycoi) == False)
    bvugkhj.extendBy(185)
    assert (bvugkhj.weeks == 1 and bvugkhj.days == 0 and bvugkhj.hours == 17)
    assert (eoyfmln.isShorterThan(bvugkhj) == False)
    qdrycoi.extendBy(793)
    assert (qdrycoi.weeks == 9 and qdrycoi.days == 3 and qdrycoi.hours == 16)
    assert (qdrycoi.isShorterThan(eoyfmln) == False)
    eoyfmln.extendBy(172)
    assert (eoyfmln.weeks == 7 and eoyfmln.days == 5 and eoyfmln.hours == 1)
    assert (eoyfmln.isShorterThan(eoyfmln) == False)
    bvugkhj.extendBy(994)
    assert (bvugkhj.weeks == 7 and bvugkhj.days == 0 and bvugkhj.hours == 3)
    assert (eoyfmln.isShorterThan(bvugkhj) == False)
    bvugkhj.extendBy(130)
    assert (bvugkhj.weeks == 7 and bvugkhj.days == 5 and bvugkhj.hours == 13)
    assert (bvugkhj.isShorterThan(qdrycoi) == True)
    eoyfmln.extendBy(254)
    assert (eoyfmln.weeks == 9 and eoyfmln.days == 1 and eoyfmln.hours == 15)
    assert (eoyfmln.isShorterThan(bvugkhj) == False)
    bvugkhj.extendBy(297)
    assert (bvugkhj.weeks == 9 and bvugkhj.days == 3 and bvugkhj.hours == 22)
    assert (bvugkhj.isShorterThan(bvugkhj) == False)
    qdrycoi.extendBy(699)
    assert (qdrycoi.weeks == 13 and qdrycoi.days == 4 and qdrycoi.hours == 19)
    assert (bvugkhj.isShorterThan(qdrycoi) == True)
    qdrycoi.extendBy(412)
    assert (qdrycoi.weeks == 16 and qdrycoi.days == 0 and qdrycoi.hours == 23)
    assert (qdrycoi.isShorterThan(qdrycoi) == False)
    eoyfmln.extendBy(668)
    assert (eoyfmln.weeks == 13 and eoyfmln.days == 1 and eoyfmln.hours == 11)
    assert (qdrycoi.isShorterThan(eoyfmln) == False)
    eoyfmln.extendBy(732)
    assert (eoyfmln.weeks == 17 and eoyfmln.days == 3 and eoyfmln.hours == 23)
    assert (bvugkhj.isShorterThan(eoyfmln) == True)

check()