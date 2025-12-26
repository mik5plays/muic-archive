def calculate_summary(scores: list[str]) -> dict:
    # convert all names with scores into a dict
    x = {}

    ans = {}
    for i in scores:
        name, grade = i.split(',')[0], i.split(',')[1:]
        x[name] = grade
    count = 1

    for i in range(len(grade)):

        avg = 0
        nums = []
        ansstr = {}
        temp = {}

        for j in x:
            temp[j] = x[j][i]
            avg += int(x[j][i])
            nums.append(x[j][i])
            maxi = max(nums)

            ansstr['average_score'] = avg / len(x.keys())

        ansstr['top_scorers'] = [key for key in temp if temp[key] == maxi]
        ans[count] = ansstr
        count += 1

    return ans

scores = [
    "Alice,80,75,90,85,95",
    "Bob,88,91,87,82,90",
    "Charlie,75,80,85,95,88",
    "David,92,87,88,95,84"
]

from pprint import pprint
pprint(calculate_summary(scores))