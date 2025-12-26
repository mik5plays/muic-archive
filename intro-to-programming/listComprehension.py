#1
def mult_ten(n:int):
    return [x*10 for x in range(1,n+1)]

#2
def cubes(n:int):
    return [x**3 for x in range(1,n+1)]

#3
def awesome(names:list):
    return [f"{x} is awesome!" for x in names]

#4
def to_lower(words):
    return [x.lower() for x in words]

#5
def filter_vowels(words): #With a function
    def hasVowel(x):
        for letter in "aeiou":
            if letter in x:
                return True
        return False
    return [word for word in words if not hasVowel(word)]

def filter_vowels_set(words):
    return [x for x in words if set(x) - set("aeiou") == set(x)]

#6
def dict_sum(data: list):
    return [sum(x.values()) for x in data]

#7
def a2z():
    return "".join([chr(x) for x in range(ord("a"),ord("z")+1)])

# print(dict_sum([{"a":5,"b":6},{"a":10,"b":6},{"a":5,"b":51}]))
print(filter_vowels_set(["dog","cat","yyxx","boge","sxmmpx"]))