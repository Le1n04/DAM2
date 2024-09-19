x = input("Introduce dos numeros enteros separados por ';': ").split(";")
x = [int(y) for y in x]; print("suma=" , sum(x), "\nresta=", x[0]-x[1], "\nmultiplicacion=", x[0]*x[1], "\ndivision=", x[0]/x[1])
