n, nums = 1, []
while n!=0: n = int(input("Dame un numero: ")); nums.append(n) if n!=0 else 0
print(len(nums), min(nums), max(nums), sum(nums), sum(nums)/len(nums))
