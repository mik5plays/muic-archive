# Assignment 5, Task 4
# Name: Theeradon Sarawek
# Collaborators:
# Time Spent: 25 minutes

def parse_time(line: str) -> int:
    line = line.split(';') #We do some splitting.
    line = line[1] #Taking only the time part.
    line = line.split(':') #Taking the hours and minutes part.
    line = line[1].split(',') #Splitting the two apart.

    #Minutes, then seconds -> Converting to seconds
    total = 0
    total += int(line[0]) * 60 #Minutes to seconds
    if line[1][0] == '0':
        total += int(line[1][1]) #Ignoring the first 0 since its less than 10 seconds.
    else:
        total += int(line[1])
    return total


def parse_dist(line: str) -> float:
    line = line.split(';') #We do some more splitting.
    line = line[2] #Taking only the distance part.
    line = line.split(':') #Taking the km part.
    line = float(line[1]) #Converting to float.
    return line

def jogging_average(activities: list[str]) -> float:
    distance = 0
    time = 0
    for activity in activities:
        x = activity.split(';')
        if x[0].lower() == 'jogging': #Only taking jogging
            distance += parse_dist(activity) * 1000 #km to m
            time += parse_time(activity) #already converted
    return distance/time
    #It returns the exact value, to be most precise.

def jogging_averageTest():
    assert round(jogging_average(["jogging;time:40,11;distance:6","jogging;time:36,25;distance:5.3"]), 7) == 2.4586597 #Meeting the example requirements

jogging_averageTest() #Passed.




