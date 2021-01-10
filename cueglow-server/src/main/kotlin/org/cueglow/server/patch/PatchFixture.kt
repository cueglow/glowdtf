package org.cueglow.server.patch

import org.cueglow.server.objects.ArtNetAddress
import org.cueglow.server.objects.DmxAddress
import java.util.*

data class PatchFixture(
    val uuid: UUID,
    var fid: Int,
    var name: String,
    val fixtureTypeId: UUID,
    val dmxMode: String,
    var universe: ArtNetAddress,
    var address: DmxAddress,
) {
    override fun hashCode() = this.uuid.hashCode()
}

class Patch(
    val patchFixtureList: HashMap<UUID, PatchFixture>,
    val fixtureTypeList: HashMap<UUID, GDTF>,
) {
//    TODO
//    setters that call stream update handler
//    via singleton/static websocket stuff
}


//FixtureTypeList: GDTF[]





