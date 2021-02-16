package org.cueglow.server.gdtf

import com.github.michaelbull.result.*
import org.cueglow.server.objects.GlowError
import org.xml.sax.Attributes
import org.xml.sax.InputSource
import org.xml.sax.XMLReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.util.*
import java.util.zip.ZipInputStream
import javax.xml.bind.JAXBContext
import javax.xml.parsers.SAXParserFactory
import javax.xml.transform.sax.SAXSource
import org.cueglow.gdtf.GDTF
import org.cueglow.server.patch.Patch

/**
 * Handler for new GDTF
 *
 * Parses it and adds it to the Patch
 */
fun handleNewGdtf(inputStream: InputStream): Result<UUID, GlowError> {
    val parseResult = parseGdtf(inputStream)

    val parsedGdtf = parseResult.getOrElse {return Err(it)}
    val wrapper = GdtfWrapper(parsedGdtf)

    Patch.putFixtureType(wrapper)

    return Ok(wrapper.fixtureTypeId)
}

fun parseGdtf(inputStream: InputStream): Result<GDTF, GlowError> {
    val zipInputStream = ZipInputStream(inputStream)

    while (true) {
        val entry = zipInputStream.nextEntry
        if (entry.name == "description.xml") {
            break
        }
        if (entry == null) {
            TODO("return error - no description xml found")
        }
    }

    val jc = JAXBContext.newInstance("org.cueglow.gdtf")
    val unmarshaller = jc.createUnmarshaller()

    // TODO add validation here somewhere
    // XSD and Schematron?
    // possible while parsing?

    // TODO unsafe null handling
    val collection = unmarshaller.unmarshal(zipInputStream) as? GDTF ?: TODO()

    return Ok(collection)
}