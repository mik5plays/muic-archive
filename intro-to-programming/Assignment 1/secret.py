# Assignment 1, Task 6
# Name: Theeradon Sarawek (Mik)
# Collaborators: -
# Time Spent:

M = 183232381**(18323+2381) + 381818183**(18183-3818)

k = int(input("Secret key: "))
k -= 1 #Compensating since Python index starts at 0.
M = str(M)
a = M[k]

M = M[::-1] #Reversing the list.
b = M[k]

print(f"The secret code is {b}{a}")