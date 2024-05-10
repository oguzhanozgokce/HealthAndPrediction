package com.oguzhanozgokce.healthandprediction.common

import java.util.Locale

fun String.capitalizeWords(): String {
    return split(" ").joinToString(" ") {
        it.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.ROOT
            ) else it.toString()
        }
    }
}



