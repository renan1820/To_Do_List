package com.renan.portifolio.to_dolist.util

const val EMPTY_STRING = ""

fun <T> tryCatchErrorReturn(
    tag: String = EMPTY_STRING,
    runTry: () -> T,
    runCatch: () -> T
): T = try {
    runTry.invoke()
} catch (e: Exception) {
    if (tag.isNotEmpty()) {
        CustomLogger.e(tag, e.message, e)
    }
    runCatch.invoke()
}

fun tryCatchError(
    tag: String = EMPTY_STRING,
    run: () -> Unit
) {
    try {
        run.invoke()
    } catch (e: Exception) {
        if (tag.isNotEmpty()) {
            CustomLogger.e(tag, e.message, e)
        }
    }
}