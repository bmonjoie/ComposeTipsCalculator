package be.xzan.composetipscalculator.entity

data class TipsEntry(var amount: Float = 0f, var percent: Int = 18) {
    val tips: Float
        get() = amount / 100 * percent
    val total: Float
        get() = amount + tips
}