package org.cueglow.server.patch

import com.beust.klaxon.Json
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import org.cueglow.server.json.KlaxonArtNetAddressUpdate
import org.cueglow.server.json.KlaxonDmxAddressUpdate
import org.cueglow.server.objects.ArtNetAddress
import org.cueglow.server.objects.DmxAddress
import java.util.*

/**
 * Encodes the update logic for a [PatchFixture]. If [fid] or [name] or null,
 * it means the corresponding value shall not be updated. Since null is a valid value for
 * [universe] and [address], their updates are wrapped in a Result type, where Ok stands for
 * "update to this value" and the Err(Unit) stands for "leave the existing value".
 */
data class PatchFixtureUpdate(
    @Json(index = 0)
    val uuid: UUID,
    @Json(index = 1, serializeNull = false)
    val fid: Int? = null,
    @Json(index = 2, serializeNull = false)
    val name: String? = null,
    @KlaxonArtNetAddressUpdate
    @Json(index = 3)
    val universe: Result<ArtNetAddress?, Unit> = Err(Unit),
    @KlaxonDmxAddressUpdate
    @Json(index = 4)
    val address: Result<DmxAddress?, Unit> = Err(Unit),
)