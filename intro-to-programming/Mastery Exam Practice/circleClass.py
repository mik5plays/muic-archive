from math import pi, sqrt
class Point:
    def __init__(self, x:float, y:float):
        self.x = x
        self.y = y

class Circle:
    def __init__(self, x: float, y: float, radius: float):
        self.x = x
        self.y = y
        self.radius = radius

    def area(self):
        return pi * self.radius**2

    def inside(self, p: Point):
        distance = sqrt((p.x-self.x)**2 + (p.y-self.y)**2)
        return distance <= self.radius

x = Point(5,1)
y = Circle(0,0,5)

print(y.inside(x))