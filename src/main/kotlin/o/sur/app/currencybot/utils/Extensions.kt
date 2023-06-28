package o.sur.app.currencybot.utils

import org.slf4j.Logger
fun Logger.error(function: () -> String) {
    if (isErrorEnabled) this.error(function.invoke())
}

fun Logger.debug(function: () -> String) {
    if (isDebugEnabled) this.debug(function.invoke())
}

fun Logger.info(function: () -> String) {
    if (isInfoEnabled) this.info(function.invoke())
}

fun Logger.warn(function: () -> String) {
    if (isWarnEnabled) this.warn(function.invoke())
}
