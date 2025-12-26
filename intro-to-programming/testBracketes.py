test_case = "(a+b}, [a+{b+c]), [a+b)"
test_case2 = "(a+b), [a+{b+c}), [a+b]"

def test(test_case: str):
    startsWith: list = ['(', '{', '[']
    endsWith: list = ['}', ']', ')']

    start: list = []
    ends: list = []

    indexesToIgnore = []
    for i in range(len(test_case)):
        if test_case[i] in startsWith:
            start.append(test_case[i])
        elif test_case[i] in endsWith:
            if len(start) == len(ends) + 2:
                for j in range(i+1, len(test_case)):
                    if test_case[j] in endsWith:
                        ends.append(test_case[j])
                        indexesToIgnore.append(j)
                        break
                ends.append(test_case[i])
            elif i not in indexesToIgnore:
                ends.append(test_case[i])

    print(f"{start}\n{ends}")

test(test_case)

test(test_case2)
