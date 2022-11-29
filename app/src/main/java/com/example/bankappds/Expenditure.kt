package com.example.bankappds

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


enum class Ecategory {
    FOOD,
    ENTERTAIN,
    SHOPPING,
    HOBBY,
    HEALTH,
    FINANCE,
    HOME,
    ETC
}

@Parcelize
data class Expenditure(
    var year: Int = 0,
    var month: Int = 0,
    val day: Int,
    val expense: Int,
    val category: Ecategory?,
    val memo: String): Parcelable

data class ExpMap(
    var key: String = "", var value: MutableList<Expenditure> = mutableListOf()
)

