def recoger_numeros():
    numeros = []
    while True:
        num = int(input("Introduce un número (0 para terminar): "))
        if num == 0:
            break
        numeros.append(num)
    
    print("a) En el orden en que se introdujeron:")
    print(numeros)
    
    print("b) En orden creciente:")
    print(sorted(numeros))
    
    print("c) En orden decreciente:")
    print(sorted(numeros, reverse=True))

recoger_numeros()
