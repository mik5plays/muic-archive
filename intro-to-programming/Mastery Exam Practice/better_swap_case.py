def better_swap_case(st: str) -> str:
    ans: str = ""
    for letter in st:
        if letter.isalpha():
            if ord("a") <= ord(letter) <= ord("z"):
                if letter in ['a','e','i','o','u']:
                    ans += letter
                else:
                    ans += letter.upper()
            elif ord("A") <= ord(letter) <= ord("Z"):
                if letter in ['A', 'E', 'I', 'O', 'U']:
                    ans += letter
                else:
                    ans += letter.lower()
        else:
            ans += " "
    return ans

def test_better_swap_case():
    assert better_swap_case('WbS') == 'wBs'
    assert better_swap_case('XUOfuN') == 'xUOFun'
    assert better_swap_case('OpFuod') == 'OPfuoD'
    assert better_swap_case('pGHa') == 'Pgha'
    assert better_swap_case('uMHruZY7') == 'umhRuzy '
    assert better_swap_case('EkbrEO:=l') == 'EKBREO  L'
    assert better_swap_case('I)oQ0ZLCJ') == 'I oq zlcj'
    assert better_swap_case('iO5^uia') == 'iO  uia'
    assert better_swap_case('v6q`uiI2') == 'V Q uiI '
    assert better_swap_case('E2;RoF~3') == 'E  rof  '

test_better_swap_case()

