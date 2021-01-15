package org.cueglow.server.patch

import com.github.michaelbull.result.unwrap
import org.cueglow.server.objects.ArtNetAddress
import org.cueglow.server.objects.DmxAddress
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.util.*

internal class PatchFixtureTest {
    // TODO
    // replace with proper tests of observer callbacks
    @Test
    fun checkObserversThrowTodoWhenChangingProperties() {
        val exampleFixture = PatchFixture(1, "", UUID.randomUUID(),
            "1ch", ArtNetAddress.tryFrom(1).unwrap(), DmxAddress.tryFrom(1).unwrap())
        assertThrows(NotImplementedError::class.java) {exampleFixture.fid = 2}
        assertThrows(NotImplementedError::class.java) {exampleFixture.name = "new name"}
        assertThrows(NotImplementedError::class.java) {exampleFixture.universe = ArtNetAddress.tryFrom(2).unwrap()}
        assertThrows(NotImplementedError::class.java) {exampleFixture.address = DmxAddress.tryFrom(2).unwrap()}
    }
}