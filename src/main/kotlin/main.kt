import kotlin.math.roundToInt

const val FIVE_PERC_DISCOUNT = 0.05
const val ONE_PERC_DISCOUNT = 0.01
const val UPTO_THOUSAND_DISC = 10000.0
var isMeloman = false
var flag = true
val arrOfRubl = arrayOf("рубль", "рубля", "рублей")
val arrOfKopeiki = arrayOf("копейка", "копейки", "копеек")

fun main() {
    while (flag) {
        println("Введите сумму покупки в рублях")
        val originalPrice = readLine()?.toInt()
        println("Введите сумму предыдущих покупок в рублях")
        val amountOfPrevPurch = readLine()?.toInt()
        isMeloman = true
        flag = if (originalPrice != null && originalPrice > 0) {
            calculateTotalDiscount(originalPrice, amountOfPrevPurch, isMeloman)
            false
        } else {
            println("Введённое значение суммы перевода имеет нулевое либо отрицательное значение. Попробуйте заново")
            true
        }
    }

}

fun calculateTotalDiscount(originPrice: Int, amountOfPrevPurch: Int?, isMeloman: Boolean) {
    val originPriceInKop = originPrice * 100
    println("Сумма покупки составила " + endUtil(originPrice, arrOfRubl))
    if (amountOfPrevPurch in 0..1000)
        if (!isMeloman) println("Скидки нет")
        else calcDiscont(originPrice, ONE_PERC_DISCOUNT)
    else if (amountOfPrevPurch in 1001..10000)
        if (!isMeloman) {
            calcDiscont(originPrice, UPTO_THOUSAND_DISC)
        } else {
            calcDiscont(originPrice, UPTO_THOUSAND_DISC)
            calcDiscont(originPrice, ONE_PERC_DISCOUNT)

        }
    else if (amountOfPrevPurch != null) {
        if (amountOfPrevPurch > 10000)
            if (!isMeloman) calcDiscont(originPriceInKop, FIVE_PERC_DISCOUNT)
            else {
                val firstDisc = calcDiscont(originPriceInKop, FIVE_PERC_DISCOUNT)
                val secDisc = calcDiscont(firstDisc, ONE_PERC_DISCOUNT)
                printRes(FIVE_PERC_DISCOUNT, firstDisc)
                printRes(ONE_PERC_DISCOUNT, secDisc)
            }
        else
            println("Что-то пошло не так")
    }
}

fun calcDiscont(amountInKop: Int, disc: Double): Int {
    val discount = (amountInKop * disc).roundToInt()
    val discPrice = amountInKop - discount
    val discPrice2 = amountInKop - UPTO_THOUSAND_DISC
    return if (disc == ONE_PERC_DISCOUNT || disc == FIVE_PERC_DISCOUNT) discPrice
    else discPrice2.toInt()
}

fun printRes(disc: Double, numberKop: Int) {
    val rub = endUtil(numberKop / 100, arrOfRubl)
    val kop = endUtil(numberKop % 100, arrOfKopeiki)
    val discon = disc * 100
    println("после применения $discon % скидки $rub $kop ")
}

fun endUtil(T: Int, arrsome: Array<String>): String {
    return if (T % 10 == 1 && T % 100 != 11) T.toString() + " " + arrsome[0]
    else if (T % 10 == 2 && T % 100 != 12 || T % 10 == 3 && T % 100 != 13 || T % 10 == 4 && T % 100 != 14) T.toString() + " " + arrsome[1]
    else T.toString() + " " + arrsome[2]
}
