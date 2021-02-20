package org.cueglow.server.objects

import org.cueglow.server.patch.PatchFixture
import java.util.*

sealed class GlowData

data class GlowDataSubscribe(val stream: String) : GlowData()
data class GlowDataUnsubscribe(val stream: String) : GlowData()
data class GlowDataStreamInitialState(val stream: String, val streamUpdateId: Int) : GlowData() // TODO Add Stream Content classes
data class GlowDataStreamUpdate(val stream: String, val streamUpdateId: Int) : GlowData() // TODO Add Stream Content classes
data class GlowDataRequestStreamData(val stream: String) : GlowData()
data class GlowDataError(val error: GlowError): GlowData()

data class GlowDataAddFixture(val fixture: PatchFixture): GlowData() // TODO Requires diskussion regarding needed/optinal values
data class GlowDataFixturesAdded(val uuids : Array<UUID>): GlowData()
data class GlowDataUpdateFixture(val uuid: UUID): GlowData() // TODO Requires diskussion regarding needed/optinal values
data class GlowDataDeleteFixtures(val uuids : Array<UUID>): GlowData()
data class GlowDataFixtureTypeAdded(val fixtureTypeId : UUID): GlowData()
data class GlowDataDeleteFixtureTypes(val uuids : Array<UUID>): GlowData()




