grid = \
[["r","a","w","b","i","t"],
["x","a","y","z","c","h"],
["p","q","b","e","i","e"],
["t","r","s","b","o","g"],
["u","w","x","v","i","t"],
["n","m","r","w","o","t"]]


def diagonal(grid: list):
    diag = []
    x = len(grid[0])
    for i in range(x):
        word = ""
        word2 = ""
        y = []
        for j in range(i+1):
            word += grid[i-j][j]
            y.append(x-j-1)
        z = y[::-1]
        for k in range(len(y)):
            word2 += grid[y[k]][z[k]]
        diag.append(word)
        diag.append(word2)
    return diag

grid = \
[["r","a","w","b","i","t"],
["x","a","y","z","c","h"],
["p","q","b","e","i","e"],
["t","r","s","b","o","g"],
["u","w","x","v","i","t"],
["n","m","r","w","o","t"]]

def diagonal2(grid: list):
    diag = []
    x = list(range(len(grid[0])))
    y = x[::-1]
    for num in range(1, len(grid[0])+1):
        a, b, c, d = "", "", "", "" #The letters on each diagonal
        for i in range(num):
            a += grid[y[num-i-1]][x[i]] #Top left to bottom right #1
            b += grid[x[i]][y[num-i-1]] #Top left to bottom right #2

            c += grid[x[num-i-1]][x[i]] #Bottom left to top right #1
            d += grid[y[i]][y[num-i-1]] # Bottom left to top right #2
        diag.append(a)
        diag.append(b)
        diag.append(c)
        diag.append(d)
    return diag

print(diagonal2(grid))


