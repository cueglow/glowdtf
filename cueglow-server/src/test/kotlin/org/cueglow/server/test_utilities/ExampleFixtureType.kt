package org.cueglow.server.test_utilities

import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.unwrap
import org.apache.logging.log4j.LogManager
import org.awaitility.Awaitility
import org.awaitility.pollinterval.FibonacciPollInterval.fibonacci
import org.cueglow.server.gdtf.GdtfWrapper
import org.cueglow.server.gdtf.parseGdtf
import org.cueglow.server.json.toJsonString
import org.cueglow.server.objects.ArtNetAddress
import org.cueglow.server.objects.DmxAddress
import org.cueglow.server.patch.PatchFixture
import java.io.InputStream
import java.time.Duration
import java.util.*

val logger = LogManager.getLogger()

/** Provides Example [GdtfWrapper] and an example [PatchFixture] for tests. **/
object ExampleFixtureType {
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

    val esprite_fixture2 = esprite_fixture.copy(
        uuid = UUID.randomUUID(),
        fid = 2,
        name = "exampleFixture2",
        address = DmxAddress(100)
    )

    val channelLayoutTestGdtf = fixtureTypeFromGdtfResource("ChannelLayoutTest/Test@Channel_Layout_Test@v1_first_try.gdtf", this.javaClass)

    val channelLayoutTestGdtfFixture = PatchFixture(
        UUID.fromString("6c0e661f-058e-4331-b673-db836aefc9cb"),
        10,
        "channelLayoutTest1",
        channelLayoutTestGdtf.fixtureTypeId,
        "Mode 1",
        ArtNetAddress(2),
        DmxAddress(1)
    )

    val rigStateTestGdtf = fixtureTypeFromGdtfResource("RigStateTest/Test@FixtureStateTest@version2.gdtf", this.javaClass)

    // additional: Global settings for Awaitility
    init {
        Awaitility.setDefaultPollInterval(fibonacci())
        Awaitility.setDefaultTimeout(Duration.ofSeconds(2))
    }
}

fun fixtureTypeFromGdtfResource(exampleGdtfFileName: String, cls: Class<*>): GdtfWrapper {
    val exampleGdtfInputStream: InputStream =
        cls.classLoader.getResourceAsStream(exampleGdtfFileName) ?: throw Error("inputStream is Null")
    val parsedExampleGdtf = parseGdtf(exampleGdtfInputStream)
        .mapError { logger.error(it.toJsonString()); it }
        .unwrap()
    return GdtfWrapper(parsedExampleGdtf)
}