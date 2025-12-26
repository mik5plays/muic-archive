# Assignment 2, Task 1
# Name: Theeradon Sarawek
# Collaborators: -
# Time Spent: ~25 minutes

s = input()
t = input()

#Step 1
s = s.lower()
t = t.upper()

s = list(s)
t = list(t)

#Step 2
for i in range(len(s)):
    if s[i] == 's':
        s[i] = 'a'
    if s[i] == 'l':
        s[i] = 'm'
#Step 3
for i in range(len(t)):
    if t[i] in ['P','O','I','N']:
        t[i] = 'T'
#Step 4
temp = s[0]
s[0] = t[0]
t[0] = temp

#Step 5
middleThirdT = []
for i in range(int(len(t)/3), int((len(t)/3) * 2)):
    middleThirdT.append(t[i])
middleThirdT = middleThirdT[::-1] #Reversing the list so I can understand the code easier
sIndex = len(s) - 1
for i in range(len(middleThirdT)):
    s[sIndex] = middleThirdT[i]
    sIndex -= 1

#Step 6
result = "".join(s+t)

print(result) #Outputs the result

# print(result == "TemmoworTSTYhHTSTSTYTHTT")
# ^ I checked with the example answer (returned True, so I assume it works (hopefully) )


