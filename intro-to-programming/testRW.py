# def reverse(filename: str) -> str:
#     file = open(filename, "r")
#     x = [line.strip() for line in file.readlines()]
#     ans = ""
#     for line in x:
#         for y in line.strip():
#             if y != " ":
#                 ans += y
#     return ans[::-1]
#
#
# print(reverse("test.txt"))

a: int = 1
b: float = 1.5
s: str = 'hi'

# Put your answer here


# Put your answer here
def sqrt(f:float) -> float:
    if f<0:
        raise ValueError('input value cannot be negative')
    return f**0.5

print(reverse("test.txt"))