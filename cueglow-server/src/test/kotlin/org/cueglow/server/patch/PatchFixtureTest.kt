package org.cueglow.server.patch

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.unwrap
import org.cueglow.server.gdtf.FixtureType
import org.cueglow.server.gdtf.parseGdtf
import org.cueglow.server.objects.ArtNetAddress
import org.cueglow.server.objects.DmxAddress
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.InputStream

internal class PatchFixtureTest {
    private val exampleGdtfFileName = "Robe_Lighting@Robin_Esprite@20112020v1.7.gdtf"
    private val inputStream: InputStream = javaClass.classLoader.getResourceAsStream(exampleGdtfFileName) ?:
    throw Error("inputStream is Null")
    private val parsedGdtf = parseGdtf(inputStream).unwrap()
    private val exampleFixtureType = FixtureType(parsedGdtf)

    private val exampleArtNetAddress =  ArtNetAddress.tryFrom(1).unwrap()
    private val exampleDmxAddress = DmxAddress.tryFrom(1).unwrap()

    @Test
    fun constructorBlocksInvalidDmxMode() {
        assertTrue(
        PatchFixture.tryFrom(1, "", exampleFixtureType, "not_a_mode", exampleArtNetAddress, exampleDmxAddress)
            is Err
        )
    }
}