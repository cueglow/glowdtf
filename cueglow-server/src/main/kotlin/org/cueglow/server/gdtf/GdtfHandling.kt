package org.cueglow.server.gdtf

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.getOrElse
import org.cueglow.gdtf.GDTF
import org.cueglow.server.objects.GlowError
import org.cueglow.server.patch.Patch
import java.io.InputStream
import java.util.*
import java.util.zip.ZipInputStream
import javax.xml.bind.JAXBContext
import java.io.File

import javax.xml.XMLConstants
import javax.xml.validation.Schema

import javax.xml.validation.SchemaFactory




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
        if (entry?.name == "description.xml") {
            break
        }
        if (entry == null) {
            TODO("return error - no description xml found")
        }
    }

    val jc = JAXBContext.newInstance("org.cueglow.gdtf")
    val unmarshaller = jc.createUnmarshaller()

    // Schema Validation
    val sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
    // Since this is the XSD for GDTF version 1.1 and GDTF is not backwards compatible,
    // this only supports GDTF 1.1 which is equal to DIN 15800:2020-07.
    val gdtfSchema: Schema = sf.newSchema(File("src/main/schema/gdtf.xsd"))
    unmarshaller.schema = gdtfSchema

    // TODO Additional Validation may be possible through Schematron in the future
    // Please track the progress of https://github.com/mvrdevelopment/spec/pull/64

    // TODO unsafe null handling
    val collection = unmarshaller.unmarshal(zipInputStream) as? GDTF ?: TODO()

    return Ok(collection)
}