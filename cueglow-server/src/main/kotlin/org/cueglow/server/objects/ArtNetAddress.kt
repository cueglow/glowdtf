package org.cueglow.server.objects

class ArtNetAddress(number: Short) {
    private val number: Short

    init {
        if (number in 0..32_767) {
            this.number = number
        } else {
            TODO("Errorhandling is WIP")
        }
    }
}