fun main()
{
    val h = 4

    for (i in 1..h)
    {
        for (k in 1..(2 * i - 1))
            print("*")
        println()
    }
}