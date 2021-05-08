package org.cueglow.server.objects

import com.github.michaelbull.result.*
import org.cueglow.server.objects.messages.InvalidArtNetNet
import org.cueglow.server.objects.messages.InvalidArtNetSubNet
import org.cueglow.server.objects.messages.InvalidArtNetUniverse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource

class ArtNetAddressTest {
    //  Short Initialization

    @ParameterizedTest
    @ValueSource(shorts = [0.toShort(), 32_767.toShort()])
    fun validShorts(value: Short) {
        val a = ArtNetAddress.tryFrom(value)
        assertTrue(a is Ok)
        assertEquals(value, a.get()?.value)
    }

    @ParameterizedTest
    @ValueSource(shorts = [(-1).toShort(), 32_768.toShort()])
    fun invalidShorts(value: Short) {
        val a = ArtNetAddress.tryFrom(value)
        assertTrue(a is Err)
    }

    //  Int Initialization

    @ParameterizedTest
    @ValueSource(ints = [0, 32_767])
    fun validInts(value: Int) {
        val a = ArtNetAddress.tryFrom(value)
        assertTrue(a is Ok)
        assertEquals(value.toShort(), a.get()?.value)
    }

    @ParameterizedTest
    @ValueSource(ints = [-1, 32_768])
    fun invalidInts(value: Int) {
        val a = ArtNetAddress.tryFrom(value)
        assertTrue(a is Err)
    }

    //  Triple Initialization

    @ParameterizedTest
    @CsvSource(
        "0, 0, 0, 0",
        "1, 0, 0, 1",
        "32767, 127, 15, 15",
        "5298, 20, 11, 2",
        "31908, 124, 10, 4"
    )
    fun validTriples(value: Int, net: Int, subNet: Int, universe: Int) {
        val a = ArtNetAddress.tryFrom(net, subNet, universe).unwrap()
        assertEquals(value.toShort(), a.value)
    }
    @Test
    fun invalidTriples() {
        assertTrue(ArtNetAddress.tryFrom(0, 0, -1).getError() is InvalidArtNetUniverse)
        assertTrue(ArtNetAddress.tryFrom(113, 3, 16).getError() is InvalidArtNetUniverse)

        assertTrue(ArtNetAddress.tryFrom(0, -1, 1).getError() is InvalidArtNetSubNet)
        assertTrue(ArtNetAddress.tryFrom(124, 16, 4).getError() is InvalidArtNetSubNet)

        assertTrue(ArtNetAddress.tryFrom(-1, 15, 15).getError() is InvalidArtNetNet)
        assertTrue(ArtNetAddress.tryFrom(128, 12, 11).getError() is InvalidArtNetNet)
    }

    //  Get Net/Subnet/Universe

    @ParameterizedTest
    @CsvSource(
        "0, 0, 0, 0",
        "1, 0, 0, 1",
        "32767, 127, 15, 15",
        "5298, 20, 11, 2",
        "31908, 124, 10, 4"
    )
    fun netSubnetUniverseGetters(input: Int, net: Int, subNet: Int, universe: Int) {
        val a = ArtNetAddress.tryFrom(input).unwrap()
        assertEquals(net, a.getNet())
        assertEquals(subNet, a.getSubNet())
        assertEquals(universe, a.getUniverse())
    }
}