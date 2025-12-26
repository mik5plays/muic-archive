# Assignment 7, Task 3
# Name: Theeradon Sarawek
# Collaborators: -
# Time Spent: 20 minutes

class DataFrame:
    def __init__(self):
        self.items: list[float] = []

    def add(self, x: int or float or tuple or list):
        if isinstance(x, int) or isinstance(x, float):
            self.items.append(x)
        elif isinstance(x, tuple) or isinstance(x, list):
            for item in x:
                self.items.append(item)

    def mean(self):
        return sum(self.items) / len(self.items)

    def percentile(self, r):
        sortedList: list = sorted(self.items)
        nth = int(((r/100) * len(sortedList)) // 1)
        return sortedList[nth]

    def mode(self):
        return max(set(self.items), key=self.items.count)

    def sd(self):
        #Based on the formula given in the Assignment
        sum_of_squares: float = 0
        for number in self.items:
            sum_of_squares += (number - self.mean())**2
        return (sum_of_squares / len(self.items))**0.5

    def get(self):
        return self.items


x = DataFrame()
x.add([4,2,8,7,3,1,5])
print(x.percentile(98))
