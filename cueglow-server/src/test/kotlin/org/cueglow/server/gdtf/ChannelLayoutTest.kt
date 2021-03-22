package org.cueglow.server.gdtf

import com.github.michaelbull.result.unwrap
import org.junit.jupiter.api.Test
import java.io.InputStream

fun fixtureTypeFromGdtfResource(exampleGdtfFileName: String, cls: Class<*>): FixtureType {
    val exampleGdtfInputStream: InputStream =
        cls.classLoader.getResourceAsStream(exampleGdtfFileName) ?: throw Error("inputStream is Null")
    val parsedExampleGdtf = parseGdtf(exampleGdtfInputStream).unwrap()
    return FixtureType(parsedExampleGdtf)
}

internal class ChannelLayoutTest {
    @Test
    fun robinEspriteChannelLayout() {
        val exampleFixtureType =
            fixtureTypeFromGdtfResource("Robe_Lighting@Robin_Esprite@20112020v1.7.gdtf", this.javaClass)

    }


}