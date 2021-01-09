package org.cueglow.server.objects

enum class GlowEvent(eventDiscriptor: String, eventType: GlowEventType) {
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
    DELETEFIXTURETYPES("deleteFixtureTypes", GlowEventType.CLIENT),


}

enum class GlowEventType() {
    CLIENT, SERVER
}