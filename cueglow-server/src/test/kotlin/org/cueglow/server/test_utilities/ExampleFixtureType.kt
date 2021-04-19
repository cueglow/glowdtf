package org.cueglow.server.test_utilities

import com.github.michaelbull.result.unwrap
import org.awaitility.Awaitility
import org.awaitility.pollinterval.FibonacciPollInterval.fibonacci
import org.cueglow.server.gdtf.GdtfWrapper
import org.cueglow.server.gdtf.parseGdtf
import org.cueglow.server.objects.ArtNetAddress
import org.cueglow.server.objects.DmxAddress
import org.cueglow.server.patch.PatchFixture
import java.io.InputStream
import java.time.Duration
import java.util.*

fun fixtureTypeFromGdtfResource(exampleGdtfFileName: String, cls: Class<*>): GdtfWrapper {
    val exampleGdtfInputStream: InputStream =
        cls.classLoader.getResourceAsStream(exampleGdtfFileName) ?: throw Error("inputStream is Null")
    val parsedExampleGdtf = parseGdtf(exampleGdtfInputStream).unwrap()
    return GdtfWrapper(parsedExampleGdtf)
}

object ExampleFixtureType {
    init {
        Awaitility.setDefaultPollInterval(fibonacci())
        Awaitility.setDefaultTimeout(Duration.ofSeconds(2))
    }

    val esprite = fixtureTypeFromGdtfResource("Robe_Lighting@Robin_Esprite@20112020v1.7.gdtf", this.javaClass)

    val esprite_fixture = PatchFixture(
        UUID.fromString("91faaa61-624b-477a-a6c2-de00c717b3e6"),
        1,
        "exampleFixture",
        esprite.fixtureTypeId,
        "mode1",
        ArtNetAddress.tryFrom(1).unwrap(),
        DmxAddress.tryFrom(1).unwrap(),
    )
}