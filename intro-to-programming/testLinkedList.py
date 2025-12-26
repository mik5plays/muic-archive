# from __future__ import annotations
#
# class Node:
#     def __init__(self, value: int):
#         self.value = value
#         self.next = None
#
# def add(head: Node, value: Node):
#     temp = head
#     while temp.next is not None:
#         temp = temp.next
#     temp.next = value
#
# def remove(head: Node, value: int):
#     temp = head
#     prev = head
#     while temp is not None:
#         if temp.value == value:
#             if temp != head:
#                 prev.next = temp.next
#                 return
#             else:
#
#         prev = temp
#         temp = temp.next
#     print("Not found!")
#
# def output(head: Node):
#     temp = head
#     output = []
#     while temp is not None:
#         output.append(temp.value)
#         temp = temp.next
#     print(output)
#
# linked = Node(10)
# add(linked, Node(15))
# add(linked, Node(20))
# add(linked, Node(3))
# add(linked, Node(5))
# remove(linked, 105)
# output(linked)

from typing import Optional
class ListNode:
    def __init__(self, val=0, next=None):
        self.val = val
        self.next = next

class Solution:
    def addTwoNumbers(self, l1: Optional[ListNode], l2: Optional[ListNode]) -> Optional[ListNode]:
        num1: str = ""
        num2: str = ""
        temp = l1
        while temp is not None:
            num1 += str(temp.val)
            temp = temp.next
        temp = l2
        while temp is not None:
            num2 += str(temp.val)
            temp = temp.next
        num1, num2 = num1[::-1], num2[::-1]
        result = int(num1) + int(num2)
        result = str(result)[::-1]
        ans = ListNode(int(result[0]))
        temp = ans
        for i in range(1, len(result)):
            temp.next = ListNode(int(result[i]))
            temp = temp.next
        return ans

def traverse(x: ListNode):
    temp = x
    while temp is not None:
        print(temp.val)
        temp = temp.next

test = ListNode(2)
test.next = ListNode(4)
test.next.next = ListNode(3)

test2 = ListNode(5)
test2.next = ListNode(6)
test2.next.next = ListNode(4)

x = Solution()

y = x.addTwoNumbers(test,test2)
traverse(y)