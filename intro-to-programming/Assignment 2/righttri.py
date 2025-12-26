# Assignment 2, Task 7
# Name: Theeradon Sarawek
# Collaborators: -
# Time Spent: 10 mins

x: float = float(input())
y: float = float(input())
z: float = float(input())

# print( (x**2 == y**2 + z**2) or (y**2 == x**2 + z**2) or (z**2 ==  x**2 + y**2) ) <-- Old method, I adhered to what the question suggested instead.
print( (abs(x**2 - (y**2 + z**2)) < 1e-7) or (abs(y**2 - (x**2 + z**2)) < 1e-7) or (abs(z**2 - (x**2 + y**2)) < 1e-7))

#Practically brute forcing: Just trying all the combinations possible. If none of them works then none of the sides make a right triangle.