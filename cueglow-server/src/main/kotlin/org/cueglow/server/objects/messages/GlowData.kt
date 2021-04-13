package org.cueglow.server.objects.messages

import com.beust.klaxon.Json
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import org.cueglow.server.json.KlaxonArtNetAddressResult
import org.cueglow.server.json.KlaxonDmxAddressResult
import org.cueglow.server.objects.ArtNetAddress
import org.cueglow.server.objects.DmxAddress
import java.util.*

/**
 * Different types for the data property in [GlowMessage]. Which specific subtype to use depends on the event property.
 */
sealed class GlowData {
    data class Subscribe(val stream: String) : GlowData()
    data class Unsubscribe(val stream: String) : GlowData()
    data class StreamInitialState(val stream: String, val streamUpdateId: Int) : GlowData() // TODO Add Stream Content classes
    data class StreamUpdate(val stream: String, val streamUpdateId: Int) : GlowData() // TODO Add Stream Content classes
    data class RequestStreamData(val stream: String) : GlowData()
    data class Error(@Json(index=0) val errorName: String, @Json(index=1) val errorDescription: String): GlowData()

    data class AddFixtures(val fixtures: List<AddFixtureData>): GlowData()
    data class FixturesAdded(val uuids : List<UUID>): GlowData()
    data class UpdateFixture(
        val uuid: UUID,
        val fid: Int? = null,
        val name: String? = null,
        @KlaxonArtNetAddressResult
        val universe: Result<ArtNetAddress?, Unit> = Err(Unit),
        @KlaxonDmxAddressResult
        val address: Result<DmxAddress?, Unit> = Err(Unit)
    ): GlowData()
    data class DeleteFixtures(val uuids : List<UUID>): GlowData()
    data class FixtureTypeAdded(val fixtureTypeId : UUID): GlowData()
    data class DeleteFixtureTypes(val fixtureTypeIds : List<UUID>): GlowData()
}


data class AddFixtureData(
    val fid: Int,
    val name: String,
    val fixtureTypeId: UUID,
    val dmxMode: String,
    val universe: ArtNetAddress?,
    val address: DmxAddress?,
)







