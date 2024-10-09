def es_palindromo(s):
    s = s.replace(" ", "").lower()
    return s == s[::-1]

def comprobar_palindromo():
    texto = input("Introduce un texto: ")
    if es_palindromo(texto):
        print(f'"{texto}" es un palíndromo.')
    else:
        print(f'"{texto}" no es un palíndromo.')

comprobar_palindromo()
