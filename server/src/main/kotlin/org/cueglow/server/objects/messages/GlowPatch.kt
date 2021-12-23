package org.cueglow.server.objects.messages

import com.beust.klaxon.Json
import org.cueglow.server.gdtf.GdtfWrapper
import org.cueglow.server.patch.PatchFixture

/** An immutable copy of the CueGlow Patch. Can be serialized.  */
data class GlowPatch(@Json(index=0) val fixtures: List<PatchFixture>, @Json(index=1) val fixtureTypes: List<GdtfWrapper>)