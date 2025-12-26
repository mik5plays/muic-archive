# Assignment 6, Task 5
# Name: Theeradon Sarawek
# Collaborators: -
# Time Spent: 20 minutes
from pprint import pprint
def mealCal(meal: list[str], recipes: list[str], db: list[str]) -> float:
    #Start off with splitting the dietary stuff
    dbDict: dict = {}
    for item in db:
        item = item.split(':')
        dbDict[item[0]] = item[1].split(',')
    recipeDict: dict = {}
    for item in recipes:
        items = item.split(':')
        ingredients = items[1].split(',')
        y = [] #Total ingredients per recipe
        for x in ingredients:
            x = x.split('*')
            for i in range(int(x[1])):
                y.append(x[0])
        recipeDict[items[0]] = y

    calories: float = 0.0
    for food in meal:
        for ingredient in recipeDict[food]:
            x = dbDict[ingredient]
            calories += float(x[0])*4 + float(x[1])*4 + float(x[2])*9

    return calories

db = ["Cabbage:4,2,0", "Carrot:9,1,5", "Fatty Pork:431,1,5",
"Pineapple:7,1,0", "Steak Meat:5,20,10", "Rabbit Meat:7,2,20"]

meal = ["T-Bone", "T-Bone", "Green Salad1"]

recipes = ["Pork Stew:Cabbage*5,Carrot*1,Fatty Pork*10",
"Green Salad1:Cabbage*10,Carrot*2,Pineapple*5",
"T-Bone:Carrot*2,Steak Meat*1"]

print(mealCal(meal,recipes,db))
#>> 1290.0, we ball.
