def removeElement(array, val):
    length = len(array) - 1
    k: int = 0

    for i in range(length):
        if array[i] == val:
            temp = array[i]
            array[i] = array[length]
            array[length] = temp
            k += 1
            length -= 1

    return array

test = [2,3,3,2,1,4,8,2]
print(removeElement(test, 2))
