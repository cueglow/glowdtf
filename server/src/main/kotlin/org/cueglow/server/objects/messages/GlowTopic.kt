package org.cueglow.server.objects.messages

enum class GlowTopic(val string: String) {
    PATCH("patch"),
    RIG_STATE("rigState");

    override fun toString() = string

    companion object {
        // lookup topic by topic string
        private val map = values().associateBy(GlowTopic::string)
        fun fromString(string: String) = map[string]
    }
}