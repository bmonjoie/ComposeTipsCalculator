package be.xzan.composetipscalculator.entity

class TipsEntry(val amount: Float = 0f, val percent: Int = 18) {
    val tips: Float
        get() = amount / 100 * percent
    val total: Float
        get() = amount + tips

    fun copy(amount: Float = this.amount, percent: Int = this.percent) = TipsEntry(amount, percent)
}