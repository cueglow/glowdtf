package org.cueglow.server.patch

import com.github.michaelbull.result.unwrap
import org.cueglow.server.gdtf.GdtfWrapper
import org.cueglow.server.gdtf.parseGdtf
import org.cueglow.server.objects.ArtNetAddress
import org.cueglow.server.objects.DmxAddress
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.io.InputStream
import java.util.*

internal class PatchFixtureTest {
    private val exampleGdtfFileName = "Robe_Lighting@Robin_Esprite@20112020v1.7.gdtf"
    private val inputStream: InputStream = javaClass.classLoader.getResourceAsStream(exampleGdtfFileName) ?:
    throw Error("inputStream is Null")
    private val parsedGdtf = parseGdtf(inputStream).unwrap()
    private val exampleFixtureType = GdtfWrapper(parsedGdtf)
    // TODO
    // replace with proper tests of observer callbacks
    // currently only tests that callbacks get called at all, which proves the data is observable
    @Test
    fun checkObserversThrowTodoWhenChangingProperties() {
        val exampleFixture = PatchFixture(1, "", exampleFixtureType,
            "mode1", ArtNetAddress.tryFrom(1).unwrap(), DmxAddress.tryFrom(1).unwrap())
        assertThrows(NotImplementedError::class.java) {exampleFixture.fid = 2}
        assertThrows(NotImplementedError::class.java) {exampleFixture.name = "new name"}
        assertThrows(NotImplementedError::class.java) {exampleFixture.universe = ArtNetAddress.tryFrom(2).unwrap()}
        assertThrows(NotImplementedError::class.java) {exampleFixture.address = DmxAddress.tryFrom(2).unwrap()}
    }
}