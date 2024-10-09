fun main()
{
    var num = 1243
    var invertido = 0

    while (num > 0)
    {
        val digito = num % 10
        invertido = invertido * 10 + digito
        num /= 10
    }

    println("El numero invertido es: $invertido")
}
