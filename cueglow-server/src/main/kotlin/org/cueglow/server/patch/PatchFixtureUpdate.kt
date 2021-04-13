package org.cueglow.server.patch

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
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
    val uuid: UUID,
    val fid: Int? = null,
    val name: String? = null,
    val universe: Result<ArtNetAddress?, Unit> = Err(Unit),
    val address: Result<DmxAddress?, Unit> = Err(Unit),
)