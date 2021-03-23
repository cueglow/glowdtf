package org.cueglow.server.gdtf

import com.github.michaelbull.result.unwrap
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.InputStream

fun fixtureTypeFromGdtfResource(exampleGdtfFileName: String, cls: Class<*>): FixtureType {
    val exampleGdtfInputStream: InputStream =
        cls.classLoader.getResourceAsStream(exampleGdtfFileName) ?: throw Error("inputStream is Null")
    val parsedExampleGdtf = parseGdtf(exampleGdtfInputStream).unwrap()
    return FixtureType(parsedExampleGdtf)
}

internal class ChannelLayoutTest {
    private val exampleFixtureType =
        fixtureTypeFromGdtfResource("ChannelLayoutTest/Test@Channel_Layout_Test@v1_first_try.gdtf", this.javaClass)

    @Test
    fun testFindingAbstractGeometries() {
        val abstractGeometries = findAbstractGeometries(exampleFixtureType.gdtf.fixtureType.geometries)
        assertEquals(1, abstractGeometries.size)
        assertEquals("AbstractElement", abstractGeometries[0].name)
        assertEquals("Element 1", abstractGeometries[0].referencedBy[0].name)
        assertEquals("Element 2", abstractGeometries[0].referencedBy[1].name)
    }

    @Test
    fun testComplexChannelLayout() {
        // expected channel layout for mode 1 (can also be seen in Screenshots in Resources,
        //      screenshotted because there is currently a bug in the Share that does not allow it to be opened again in
        //      the builder)
        val expectedChannelLayout = listOf(
            // Break 1
            listOf(
                "Main_Dimmer",
                null,
                "Element 1 -> AbstractElement_Pan",
                "Element 1 -> AbstractElement_Tilt",
                "Element 2 -> AbstractElement_Pan",
                "Element 2 -> AbstractElement_Tilt",
                "Element 1 -> AbstractElement_Zoom",
                "Element 2 -> AbstractElement_Zoom",
                "Element 1 -> AbstractElement_Focus1",
                null,
                "Element 2 -> AbstractElement_Focus1",
            ),
            // Break 2
            listOf(
                "Main_XYZ_X (1/2)",
                "Main_XYZ_X (2/2)",
                "Main_XYZ_Y (2/3)",
                "Main_XYZ_Y (1/3)",
                "Main_XYZ_Y (3/3)",
                "Element 1 -> AbstractElement_XYZ_Z (1/2)",
                "Element 1 -> AbstractElement_XYZ_Z (2/2)",
                "Element 2 -> AbstractElement_XYZ_Z (1/2)",
                "Element 2 -> AbstractElement_XYZ_Z (2/2)",
            ),
        )
        assertEquals("Test", exampleFixtureType.manufacturer)
        assertEquals("Channel Layout Test", exampleFixtureType.name)
        assertEquals("Mode 1", exampleFixtureType.modes[0].name)
        assertEquals(20, exampleFixtureType.modes[0].channelCount)
        assertEquals(expectedChannelLayout, exampleFixtureType.modes[0].channelLayout)
    }


}