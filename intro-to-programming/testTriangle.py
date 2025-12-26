def triangle(k: int) -> None:
    for i in range(1, k + 1):
        num_hashtag = k - i
        num_star = 2 * i - 1
        print("#" * num_hashtag + "x" * num_star + "#" * num_hashtag)

def diamond(k: int) -> None:
    for i in range(2, k + 1):
        print("#" * (k - i) + "x"*(2 * i - 1) + "#" * (k - i))

    for i in range(k, 0, -1):
        print("#" * (k - i) + "x"*(2 * i - 1) + "#" * (k - i))

diamond(3)