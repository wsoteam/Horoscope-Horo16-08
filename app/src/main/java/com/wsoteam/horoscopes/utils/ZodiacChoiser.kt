package com.wsoteam.horoscopes.utils

import java.lang.IllegalArgumentException


fun choiceSign(date: String): Int {
    val (day, month) = date.split(".").map { it.toInt() }

    return when (month) {
        1 -> if (day > 20) 10 else 9
        2 -> if (day > 18) 11 else 10
        3 -> if (day > 20) 0 else 11
        4 -> if (day > 20) 1 else 0
        5 -> if (day > 21) 2 else 1
        6 -> if (day > 21) 3 else 2
        7 -> if (day > 22) 4 else 3
        8 -> if (day > 23) 5 else 4
        9 -> if (day > 23) 6 else 5
        10 -> if (day > 22) 7 else 6
        11 -> if (day > 22) 8 else 7
        12 -> if (day > 21) 9 else 8
        else -> throw IllegalArgumentException("ZodiacChoiser error (day = $day, month = $month)")
    }
}
