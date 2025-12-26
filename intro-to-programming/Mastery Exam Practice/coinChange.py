def coinChange(v: int) -> list[int]:
    total = 0
    ans = []
    while True:
        total += 10
        if total > v:
            total -= 10
            break
        ans.append(10)
    while True:
        total += 5
        if total > v:
            total -= 5
            break
        ans.append(5)
    while True:
        total += 2
        if total > v:
            total -= 2
            break
        ans.append(2)
    while True:
        total += 1
        if total > v:
            total -= 1
            break
        ans.append(1)
    return ans

def coinChange_test():
    assert coinChange(38) == [10, 10, 10, 5, 2, 1]
    assert coinChange(19) == [10, 5, 2, 2]
    assert coinChange(11) == [10, 1]
    assert coinChange(5) == [5]
    assert coinChange(3) == [2, 1]

coinChange_test()