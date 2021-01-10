package org.cueglow.server.objects

class DmxAddress(number: Short) {
    private val number: Short

    init {
        if (number in 1..512) {
            this.number = number
        } else {
            TODO("Errorhandling is WIP")
        }
    }
}