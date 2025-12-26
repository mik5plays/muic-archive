# Assignment 6, Task 3
# Name: Theeradon Sarawek
# Collaborators: -
# Time Spent: 20 minutes

def keepTabs(actions: list[str]) -> dict[str, int]:
    ans: dict = {}
    for action in actions:
        if "++" in action or "--" in action:
            name = ""
            for i in range(len(action)):
                if action[i] == "+" or action[i] == "-":
                    break
                name += action[i]
            if "++" in action:
                if name in ans.keys():
                    ans[name] += 1
                else:
                    ans[name] = 1
            if "--" in action:
                if name in ans.keys():
                    ans[name] -= 1
                else:
                    ans[name] = -1
        if "->" in action:
            name = ""
            for i in range(len(action)):
                if action[i:i+2] == "->":
                    name2 = action[i+2:]
                    break
                name += action[i]
            if name2 not in ans.keys():
                ans[name2] = 0
            if name not in ans.keys():
                ans[name] = 0
            ans[name2] += ans[name]
            ans[name] = 0
    ans2 = {}
    for key, value in ans.items():
        if value != 0:
            ans2[key] = value
    return ans2

actions = ["Jim++", "John--", "Jeff++", "Jim++", "John--", "John->Jeff",
"Jeff--", "June++", "Home->House"]

print(keepTabs(actions))

#