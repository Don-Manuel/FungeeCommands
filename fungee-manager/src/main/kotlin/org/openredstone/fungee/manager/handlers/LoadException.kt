package org.openredstone.fungee.manager.handlers

class LoadException : Exception {
    internal constructor(message: String?, cause: Throwable?) : super(message, cause)
    internal constructor(message: String?) : super(message)
    internal constructor(cause: Throwable?) : super(cause)
}
