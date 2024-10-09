/*
Escribe un programa en Kotlin que, dada una nota numérica decimal almacenada en una variable,
muestre por pantalla si el alumno tiene un suspenso, aprobado, bien, notable o sobresaliente.
Si el valor de la nota es incorrecto (nota mínima es 1 y la máxima es 10), se mostrará un mensaje de error.
 */

fun main
{
    var nota: Double = 5;

    if (nota < 1 || nota > 10)
    {
        switch (nota)
        {
            case (nota >= 1 && nota < 5):
                println("Suspenso.");
                break;
            case (nota >= 5 && nota < 6):
                println("Aprobado.");
                break;
            case (nota >= 6 && nota < 7):
                println("Bien.");
                break;
            case (nota >= 7 && nota < 9):
                println("Notable.");
                break;
            default:
                println("Sobresaliente.");
        }
    }
}