from __future__ import annotations
class Tree:
    def __init__(self, value):
        self.left = None
        self.right = None
        self.value = value

    def insert(self, x: Tree):
        if x.value < self.value:
            if self.left is None:
                self.left = x
            else:
                self.left.insert(x)
        elif x.value > self.value:
            if self.right is None:
                self.right = x
            else:
                self.right.insert(x)
        else:
            self.value = x.value

def traverse(x: Tree):
    if x.left != None:
        traverse(x.left)
    print(x.value)
    if x.right != None:
        traverse(x.right)

def preOrder(x: Tree):
    if x == None:
        return
    print(x.value)
    preOrder(x.left)
    preOrder(x.right)

x = Tree(10)
x.insert(Tree(5))
x.insert(Tree(12))
traverse(x)
print("\n")
preOrder(x)



