package org.cueglow.server.objects


class InvalidGdtfException: Exception {
    constructor(): super()
    constructor(message: String): super(message)
    constructor(message: String, cause: Throwable): super(message, cause)
    constructor(cause: Throwable): super(cause)
}

class UnsupportedGdtfException: Exception {
    constructor(): super()
    constructor(message: String): super(message)
    constructor(message: String, cause: Throwable): super(message, cause)
    constructor(cause: Throwable): super(cause)
}

fun Exception.getChainedMessages(): String {
    val stringBuilder= StringBuilder()
    var exception: Throwable? = this
    var message: String?
    // terminates when the root cause is reached, i.e. exception.cause == null
    while(true) {
        message = exception!!.message
        stringBuilder.append(message)
        exception = exception.cause
        if (exception != null) {stringBuilder.append(": ")} else {break}
    }
    return stringBuilder.toString()
}