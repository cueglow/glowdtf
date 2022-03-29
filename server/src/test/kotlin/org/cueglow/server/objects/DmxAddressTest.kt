package org.cueglow.server.objects

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.get
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class DmxAddressTest {
    @ParameterizedTest
    @ValueSource(shorts = [1.toShort(), 512.toShort()])
    fun validShorts(value: Short) {
        val a = DmxAddress.tryFrom(value)
        assertTrue(a is Ok)
        assertEquals(value, a.get()?.value)
    }

    @ParameterizedTest
    @ValueSource(shorts = [0.toShort(), 513.toShort()])
    fun invalidShorts(value: Short) {
        val a = DmxAddress.tryFrom(value)
        assertTrue(a is Err)
    }

    @ParameterizedTest
    @ValueSource(ints = [1, 512])
    fun validInts(value: Int) {
        val a = DmxAddress.tryFrom(value)
        assertTrue(a is Ok)
        assertEquals(value.toShort(), a.get()?.value)
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 513])
    fun invalidInts(value: Int) {
        val a = DmxAddress.tryFrom(value)
        assertTrue(a is Err)
    }
}