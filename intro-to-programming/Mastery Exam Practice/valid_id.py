def valid_id(idnum: str) -> bool:
    c = 0
    for i in range(len(idnum) - 1):
        c += int(idnum[i]) * (15 - i)
    a = (11 - c%11)%10
    return int(idnum[-1]) == a

def test_valid_id():
    assert (valid_id('180628621489758') == True)
    assert (valid_id('606228772259487') == False)
    assert (valid_id('729921769655535') == True)
    assert (valid_id('171009540283809') == False)
    assert (valid_id('123045607890126') == True)
    assert (valid_id('648986931487827') == False)

test_valid_id()