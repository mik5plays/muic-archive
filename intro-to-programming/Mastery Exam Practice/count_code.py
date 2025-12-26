def count_code(msg:str):
    code = 0
    for i in range(0, len(msg) - 3):
        if msg[i] == 'c' and msg[i+1] == 'o' and msg[i+3] == 'e':
            code += 1
    return code

print(count_code('codecooecoppccccome'))