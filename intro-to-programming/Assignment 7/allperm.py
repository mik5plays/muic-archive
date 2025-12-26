# Assignment 7, Task 1
# Name: Theeradon Sarawek
# Collaborators: -
# Time Spent: 20 minutes

def all_perm(n: int) -> set[tuple[int, ...]]:
    if n == 1:
        return set({(1,)})
    else:
        prevPerm = all_perm(n-1)
        x: set[tuple] = set()
        for perm in prevPerm:
            for i in range(n):
                y = list(perm)
                y.insert(i, n)
                x.add(tuple(y))

        return x

assert sorted(all_perm(3)) == sorted({(3,1,2),(1,3,2),(1,2,3),(3,2,1),(2,3,1),(2,1,3)})