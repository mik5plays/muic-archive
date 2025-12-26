def charHistogram(filename: str):
    word_lst = []
    with open(filename, "r") as file:
        for line in file.readlines():
            for char in line.strip():
                if char.lower() in "abcdefghijklmnopqrstuvwxyz":
                    word_lst.append(char.lower())
    lowered = sorted(word_lst)
    container: dict = {}
    for i in lowered:
        if i in 'abcdefghijklmnopqrstuvwxyz':
            if i in container:
                container[i] += 1
            else:
                container[i] = 1

    lst = []
    for i in container:
        lst.append([(container.get(i)),i])
    length = len(lst)
    for i in range(length):
        for j in lst:
            if i > length - 1:
                break
            if lst[i][0] == j[0]:
                if lst[i][1] not in j:
                    j.append(lst[i][1])
                    lst.pop(i)
                    length -= 1

    ans = sorted(lst)

    for i in range(len(ans)):
        ans[i][0]=str(ans[i][0])
    real_ans=[]
    for i in ans:
        real_ans.append(sorted(i))
    final_ans=[]
    for i in range(1,len(real_ans)+1):
        final_ans.append(real_ans[-i])
    for i in final_ans:
        if len(i) >= 2:
            for x in range(1,len(i)):
                print(i[x],'+'*int(i[0]))
        else:
            print(i[1],'+'*int(i[0]))

charHistogram("charHist.txt")
