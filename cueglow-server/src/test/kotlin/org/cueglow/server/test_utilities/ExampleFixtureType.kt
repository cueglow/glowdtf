package org.cueglow.server.test_utilities

import com.github.michaelbull.result.unwrap
import org.cueglow.server.gdtf.GdtfWrapper
import org.cueglow.server.gdtf.parseGdtf
import java.io.InputStream

fun fixtureTypeFromGdtfResource(exampleGdtfFileName: String, cls: Class<*>): GdtfWrapper {
    val exampleGdtfInputStream: InputStream =
        cls.classLoader.getResourceAsStream(exampleGdtfFileName) ?: throw Error("inputStream is Null")
    val parsedExampleGdtf = parseGdtf(exampleGdtfInputStream).unwrap()
    return GdtfWrapper(parsedExampleGdtf)
}

object ExampleFixtureType {
    val esprite = fixtureTypeFromGdtfResource("Robe_Lighting@Robin_Esprite@20112020v1.7.gdtf", this.javaClass)
}