def pp(ch: str) -> None:
    print(ch, end='')
def a():
    pp('a')
def b():
    pp('b')
def c():
    a()
    pp('c')
def d():
    pp('d')
    b()
def e():
    c()
    pp('e')
    d()

e()
a()