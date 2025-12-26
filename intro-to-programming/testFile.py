# def forbidden(a:int, b:int) -> int:
#     if a+b <= 19 and a+b >= 10:
#         return 20
#     else:
#         return a+b

# x = [33, 34, 36, 37, 37, 37, 37, 38, 38, 38, 38, 38, 38, 38]
# for i in range(len(x)):
#     x[i] = (x[i] - 36.92857)**2
#
# print(sum(x) / 13)

# def chosen(lst:list[int]) -> list[int]:
#     mid = int((len(lst) + 1) // 2) - 1
#     return lst[1:mid] + lst[mid+1:-1]
#
# print(chosen([9,3,5,7,1]))

# def test(x: int):
# 	if x > 50:
# 		return "too old"
# 	elif x > 40:
# 		if x == 45:
# 			return "correct"
# 		else:
# 			return "almost"
# 	else:
# 		return "too young"
#
# print(test(4231))

# Put your answer here
# def second_max(lst: list[int]) -> int:
#     maxNo = lst[0]
#     for i in lst:
#         if i > maxNo:
#             maxNo = i
#     ans = [num for num in lst if num != maxNo]
#     return max(ans)
#
#

# Put your answer here
# def sort_by_length(words:list[str]) -> list[str]:
#     length_word_tuples = []
#     for word in words:
#         length_word_tuples.append((len(word), word))
#     length_word_tuples = sorted(length_word_tuples)
#     print(length_word_tuples)
#
# sort_by_length(["meow", "abc", "defge"])

# Put your answer here

# Put your answer here
# def gravity(board: list[list[str]]) -> list[list[str]]:
#     ans = []
#     for x in range(len(board)):
#         ans.append([])
#     for i in range(len(board)):
#         count = 0
#         for j in range(len(board)):
#             if board[j][i] == "1":
#                 count += 1
#         for k in range(len(board)):
#             if k < count:
#                 ans[len(board) - k - 1].append("1")
#             else:
#                 ans[len(board) - k - 1].append(" ")
#     return ans
#
#
# board = [[" "," ","1"," "],
#          [" "," ","1"," "],
#          ["1","1"," "," "],
#          ["1"," "," ","1"]]
#
# from pprint import pprint
# pprint(gravity(board))



# def elem_sum(lst1: list[int], lst2: list[int]) -> list[int]:
#     x = tuple(zip(lst1, lst2))
#     ans = []
#     if len(lst2) > len(lst1):
#         for i in range(len(lst2)):
#             if i < len(x):
#                 ans.append(sum(x[i]))
#             else:
#                 ans.append(lst2[i])
#     else:
#         for i in range(len(lst1)):
#             if i < len(x):
#                 ans.append(sum(x[i]))
#             else:
#                 ans.append(lst1[i])
#     return ans
#
#
# def elem_sum_assert():
#     assert elem_sum([1, 2, 3], [10, 20]) == [11, 22, 3]
#     assert elem_sum([1, 2, 3], [10, 20, 30, 40]) == [11, 22, 33, 40]
#     assert elem_sum([1], [2, 12]) == [3, 12]
#
# elem_sum_assert()

# def trailing_zeros(num:int) -> int:
#     num = str(int(str(num), 10))
#     ans = 0
#     for i in range(len(num)):
#         if num[i] == "0":
#             ans += 1
#         else:
#             ans = 0
#     return ans
#

# def partition(L:list[int],k:int) -> list[list[int]]:
#     ans = []
#     for i in range(0, len(L), k):
#         ans.append(L[i:i+k])
#     return ans
#
# print(partition([1,2,3,4],3))
# print(partition([1,2,3],2))

# Put your answer here
# def mostCommonName(L:list[str]) -> list[str]:
#     x = set(L)
#     y = {}
#     for key in x:
#         y[key] = 0
#     for name in L:
#         y[name] += 1
#     ans = []
#     print(max(y, key=y.get))
#     for name in y:
#         if y[name] == y[max(y, key=y.get)]:
#             ans.append(name)
#     return ans
#
# print(mostCommonName(["Jane", "Aaron", "Jane", "Cindy", "Aaron"]))
# def test_mostCommonName():
#     assert set(mostCommonName(["Jane", "Aaron", "Jane", "Cindy", "Aaron"])) == set(["Aaron", "Jane"])
#     assert set(mostCommonName(["A", "B", "C", "a", "B"])) == set(["B"])
#     assert mostCommonName([]) == []
#
#
# print(mostCommonName(["A", "B", "C", "a", "B"]))

# def mostCommonName(L: list[str]):
#     Lcopy = L[:]
#     nodupe = list(set(Lcopy))
#     print(Lcopy)
#     while sorted(nodupe) != sorted(Lcopy):
#         nodupe = list(set(Lcopy))
#         if len(nodupe) < len(Lcopy):
#             for i in range(len(nodupe)):
#                 if nodupe[i] in Lcopy:
#                     Lcopy.remove(nodupe[i])
#         print(nodupe, Lcopy)
#     # return list(set([x for x in Lcopy if x != ""]))
#     return Lcopy
#
# # print(mostCommonName(["Jane", "Aaron", "Jane", "Cindy", "Aaron"]))
# # print(mostCommonName(["A", "B", "C", "a", "B", "B"]))
#
#
# data = ["ijbol", "ijbol", "eiaif", "elaf", "eaig", "elaf", "elaf", "ijbol"]
# print(mostCommonName(data))

from typing import List


def gravity(board: List[List[str]]) -> List[List[str]]:
    # Transpose the board to work column-wise
    transposed_board = [list(row) for row in zip(*board)]

    # Apply gravity to each column
    for col in transposed_board:
        # Filter out the "1"s
        ones = [cell for cell in col if cell == '1']
        # Fill the remaining cells with "0"
        col[:] = ['0'] * (len(col) - len(ones)) + ones

    # Transpose the board back to the original orientation
    result_board = [list(row) for row in zip(*transposed_board)]

    return result_board


# Example usage
input_board = [
    ['1', '1', '1', '0'],
    ['0', '0', '0', '1'],
    ['0', '1', '1', '0'],
    ['1', '0', '1', '0']
]

result = gravity(input_board)

# Print the result
for row in result:
    print(row)
