def suma_divisores_propios(n):
    suma = 0
    for i in range(1, n // 2 + 1):
        if n % i == 0:
            suma += i
    return suma

def pares_numeros_amigos(lista1, lista2):
    amigos = []
    for a in lista1:
        for b in lista2:
            if suma_divisores_propios(a) == b and suma_divisores_propios(b) == a:
                amigos.append((a, b))
    return amigos

lista1 = [220, 284, 1184, 1210]
lista2 = [284, 1210, 220, 1184]

pares_amigos = pares_numeros_amigos(lista1, lista2)
print(pares_amigos)
