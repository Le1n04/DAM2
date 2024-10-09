def recoger_textos():
    textos = []
    while True:
        texto = input("Introduce un texto (cadena vacía para terminar): ")
        if texto == "":
            break
        textos.append(texto)
    
    print("a) En el orden en que se introdujeron:")
    print(textos)
    
    print("b) En orden alfabético:")
    print(sorted(textos))
    
    print("c) En orden alfabético inverso:")
    print(sorted(textos, reverse=True))

recoger_textos()
