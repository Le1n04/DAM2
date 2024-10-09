fun main()
{
    val h = 4

    for (i in 1..h)
    {
        for (j in 1..h-i)
            print(" ")
        for (k in 1..(2 * i - 1))
            print("*")
        for (j in 1..h-i)
            print(" ")
        println()
    }
}