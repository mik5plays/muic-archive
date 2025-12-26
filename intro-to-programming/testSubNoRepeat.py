def lengthOfLongestSubstring(s: str) -> int:
    if len(s) == 0:
        return 0
    uniqueSubs = []
    for i in range(len(s)):
        ans: str = ""
        for j in range(i, len(s)):
            if s[j] not in ans:
                ans += s[j]
            else:
                uniqueSubs.append(ans)
                ans = ""
                ans += s[j]
            uniqueSubs.append(ans)
    return len(max(uniqueSubs, key=lambda x: len(x)))

print(lengthOfLongestSubstring("asjrgapa"))