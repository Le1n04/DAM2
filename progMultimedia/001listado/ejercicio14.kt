fun main()
{
    val lista = listOf("campamento", "cardinales", "polen", null, "hoja", "hermana", "relatividad", null)
    
    for (cadena in lista)
        if (cadena != null)
            println("La longitud de \"$cadena\" es: ${cadena.length}")
}
