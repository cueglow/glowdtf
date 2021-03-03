package org.cueglow.server.patch

import com.github.michaelbull.result.unwrap
import org.cueglow.server.gdtf.GdtfWrapper
import org.cueglow.server.gdtf.parseGdtf
import org.cueglow.server.objects.ArtNetAddress
import org.cueglow.server.objects.DmxAddress
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.InputStream
import java.util.*

internal class PatchTest {
    private val exampleGdtfFileName = "Robe_Lighting@Robin_Esprite@20112020v1.7.gdtf"
    private val inputStream: InputStream = javaClass.classLoader.getResourceAsStream(exampleGdtfFileName) ?:
    throw Error("inputStream is Null")
    private val parsedGdtf = parseGdtf(inputStream).unwrap()
    private val exampleFixtureType = GdtfWrapper(parsedGdtf)

    val exampleFixture = PatchFixture(1, "", exampleFixtureType,
        "mode1", ArtNetAddress.tryFrom(1).unwrap(), DmxAddress.tryFrom(1).unwrap())

    @Test
    fun patchFixtureList() {
        val patch = Patch()
        assertTrue(patch.getFixtures().isEmpty())

        // TODO should be impossible to add fixtures where the fixtureType is not part of the Patch

        patch.putFixture(exampleFixture)

        assertEquals(1, patch.getFixtures().size)
        assertEquals(exampleFixture, patch.getFixtures()[exampleFixture.uuid])

        patch.removeFixture(exampleFixture.uuid)

        assertTrue(patch.getFixtures().isEmpty())
    }

    @Test
    fun patchFixtureTypeList() {
        val patch = Patch()
        assertTrue(patch.getFixtureTypes().isEmpty())

        patch.putFixtureType(exampleFixtureType)

        assertEquals(1, patch.getFixtureTypes().size)
        assertEquals(exampleFixtureType, patch.getFixtureTypes()[exampleFixtureType.fixtureTypeId])

        patch.putFixture(exampleFixture)
        assertEquals(1, patch.getFixtures().size)

        patch.removeFixtureType(exampleFixtureType.fixtureTypeId)

        assertTrue(patch.getFixtureTypes().isEmpty())

        // associated fixture should also be deleted
        assertTrue(patch.getFixtures().isEmpty())
    }

    @Test
    fun testNextFreeFid() {
        val patch = Patch()

        patch.putFixtureType(exampleFixtureType)

        val fidList = arrayOf(10,11,12,20,21,23)
        fidList.shuffle(kotlin.random.Random(42))

        fidList.forEach { patch.putFixture(
            PatchFixture(
            fid = it,
            name = "a_name",
            fixtureType = exampleFixtureType,
            dmxMode = "mode1",
            universe = ArtNetAddress.tryFrom(1).unwrap(),
            address = DmxAddress.tryFrom(1).unwrap()
        ))}

        assertEquals(1, patch.nextFreeFid())
        assertEquals(3, patch.nextFreeFid(3))
        assertEquals(13, patch.nextFreeFid(10))
        assertEquals(13, patch.nextFreeFid(12))
        assertEquals(19, patch.nextFreeFid(19))
        assertEquals(22, patch.nextFreeFid(20))
        assertEquals(22, patch.nextFreeFid(21))
        assertEquals(22, patch.nextFreeFid(22))
        assertEquals(24, patch.nextFreeFid(23))
        assertEquals(24, patch.nextFreeFid(24))
    }

    @Test
    fun testFindGapAtOrAfter() {
        val exampleArray = arrayOf(10,11,12,20,21,23)
        exampleArray.shuffle(kotlin.random.Random(42))
        val exampleList = exampleArray.asList()

        // gapSize 1
        assertEquals(1, nextGap(exampleList, 1))
        assertEquals(3, nextGap(exampleList, 3))
        assertEquals(13, nextGap(exampleList, 10))
        assertEquals(13, nextGap(exampleList, 12))
        assertEquals(19, nextGap(exampleList, 19))
        assertEquals(22, nextGap(exampleList, 20))
        assertEquals(22, nextGap(exampleList, 21))
        assertEquals(22, nextGap(exampleList, 22))
        assertEquals(24, nextGap(exampleList, 23))
        assertEquals(24, nextGap(exampleList, 24))

        // bigger gapSize
        assertEquals(1, nextGap(exampleList, 1, 9))
        assertEquals(24, nextGap(exampleList, 1, 10))
        assertEquals(13, nextGap(exampleList, 12, 7))
        assertEquals(24, nextGap(exampleList, 12, 8))
        assertEquals(18, nextGap(exampleList, 18, 2))
        assertEquals(24, nextGap(exampleList, 18, 3))
        assertEquals(24, nextGap(exampleList, 23, 9))
        assertEquals(24, nextGap(exampleList, 24, 9))
    }

    // TODO
    // test that stream updates are called
}