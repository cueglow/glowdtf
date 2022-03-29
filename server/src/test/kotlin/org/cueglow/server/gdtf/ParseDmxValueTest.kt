package org.cueglow.server.gdtf

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.getError
import com.github.michaelbull.result.unwrap
import org.cueglow.server.json.toJsonString
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class DmxValueTest {
    @Test
    fun validTestCasesForDmxValue() {
        // zero
        assertEquals(0L, parseDmxValue("0/1", 1).unwrap())
        assertEquals(0L, parseDmxValue("0/2", 1).unwrap())
        assertEquals(0L, parseDmxValue("0/3", 1).unwrap())
        assertEquals(0L, parseDmxValue("0/3", 2).unwrap())
        assertEquals(0L, parseDmxValue("0/3", 3).unwrap())
        assertEquals(0L, parseDmxValue("0/2", 3).unwrap())

        // identity
        assertEquals(255L, parseDmxValue("255/1", 1).unwrap())
        assertEquals(172L, parseDmxValue("172/1", 1).unwrap())
        assertEquals(10789L, parseDmxValue("10789/2", 2).unwrap())

        // cases from DIN Spec
        assertEquals(65535L, parseDmxValue("255/1", 2).unwrap())
        assertEquals(65280L, parseDmxValue("255/1s", 2).unwrap())

        // downconversion from 4 byte
        assertEquals(3419130827L, parseDmxValue("3419130827/4", 4).unwrap())
        assertEquals(13355979L, parseDmxValue("3419130827/4", 3).unwrap())
        assertEquals(52171L, parseDmxValue("3419130827/4", 2).unwrap())
        assertEquals(203L, parseDmxValue("3419130827/4", 1).unwrap())

        // periodic upconversion from 1 byte
        assertEquals(42L, parseDmxValue("42/1", 1).unwrap())
        assertEquals(10794L, parseDmxValue("42/1", 2).unwrap())
        assertEquals(2763306L, parseDmxValue("42/1", 3).unwrap())
        assertEquals(707406378L, parseDmxValue("42/1", 4).unwrap())

        // periodic upconversion from 2 byte
        assertEquals(42423L, parseDmxValue("42423/2", 2).unwrap())
        assertEquals(10860453L, parseDmxValue("42423/2", 3).unwrap())
        assertEquals(2780276151L, parseDmxValue("42423/2", 4).unwrap())

        // shifting upconversion from 1 byte
        assertEquals(234L, parseDmxValue("234/1s", 1).unwrap())
        assertEquals(59904L, parseDmxValue("234/1s", 2).unwrap())
        assertEquals(15335424L, parseDmxValue("234/1s", 3).unwrap())
        assertEquals(3925868544L, parseDmxValue("234/1s", 4).unwrap())
    }

    @Test
    fun failingCases() {
        // no negative
        assertTrue(parseDmxValue("-124/1", 1) is Err)
        // too big for input byte number
        assertTrue(parseDmxValue("256/1", 1) is Err)
        // too big for internal Long value
        assertTrue(parseDmxValue("1029847098720983170298730293402093274008720320202293383/60", 1) is Err)
        // first part not a number
        assertTrue(parseDmxValue("abc/1", 1) is Err)
        // unknown postfix
        assertTrue(parseDmxValue("127/1n", 1) is Err)
        // non-numeric after slash
        assertTrue(parseDmxValue("127/s1", 1) is Err)
        // two slashes
        assertTrue(parseDmxValue("127/1/s", 1) is Err)
        // unknown delimiter in second part
        assertTrue(parseDmxValue("127/1-s", 1) is Err)
        // float
        assertTrue(parseDmxValue("127.234/7", 1) is Err)
    }
}