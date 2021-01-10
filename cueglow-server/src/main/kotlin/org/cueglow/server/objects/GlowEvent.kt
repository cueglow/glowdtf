package org.cueglow.server.objects

enum class GlowEvent(val eventDescriptor: String, val eventType: GlowEventType) {

    SUBSCRIBE("subscribe", GlowEventType.CLIENT),
    UNSUBSCRIBE("unsubscribe", GlowEventType.CLIENT),
    STREAMINITIALSTATE("streamInitialState", GlowEventType.SERVER),
    STREAMUPDATE("streamUpdate", GlowEventType.SERVER),
    REQUESTSTREAMDATA("requestStreamData", GlowEventType.CLIENT),
    ERROR("error", GlowEventType.SERVER),

    ADDFIXTURES("addFixtures", GlowEventType.CLIENT),
    FIXTURESADDED("fixturesAdded", GlowEventType.SERVER),
    UPDATEFIXTURES("updateFixtures", GlowEventType.CLIENT),
    DELETEFIXTURES("deleteFixtures", GlowEventType.CLIENT),
    FIXTURETYPEADDED("fixtureTypeAdded", GlowEventType.SERVER),
    DELETEFIXTURETYPES("deleteFixtureTypes", GlowEventType.CLIENT),;


    companion object {
        // Provide methode to lookup event enum by eventDescriptor string
        private val map = values().associateBy(GlowEvent::eventDescriptor)
        fun fromDiscriptor(eventDescriptor: String) = map[eventDescriptor]
    }

}



enum class GlowEventType {
    CLIENT, SERVER
}