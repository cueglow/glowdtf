package org.cueglow.server.gdtf

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.cueglow.gdtf.GDTF
import org.cueglow.server.objects.messages.GdtfUnmarshalError
import org.cueglow.server.objects.messages.GlowError
import org.cueglow.server.objects.messages.MissingDescriptionXmlInGdtfError
import java.io.InputStream
import java.util.zip.ZipInputStream
import jakarta.xml.bind.JAXBContext
import jakarta.xml.bind.UnmarshalException
import javax.xml.validation.Schema
import javax.xml.XMLConstants
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.SchemaFactory

object GdtfSchema {
    private val schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
    // Since this is the XSD for GDTF version 1.1 and GDTF is not backwards compatible,
    // this only supports GDTF 1.1 which is equal to DIN 15800:2020-07.
    private val inputStream = javaClass.classLoader.getResourceAsStream("gdtf/gdtf.xsd")
    val schema: Schema? = schemaFactory.newSchema(StreamSource(inputStream))
}

fun parseGdtf(inputStream: InputStream): Result<GDTF, GlowError> {
    val zipInputStream = ZipInputStream(inputStream)

    // Advance zipInputStream until current entry is description.xml
    do {
        val entry = zipInputStream.nextEntry ?: return Err(MissingDescriptionXmlInGdtfError())
    } while (entry.name != "description.xml")

    val jc = JAXBContext.newInstance("org.cueglow.gdtf")
    val unmarshaller = jc.createUnmarshaller()

    // Schema Validation
    unmarshaller.schema = GdtfSchema.schema

    // TODO Additional Validation may be possible through Schematron in the future
    // Please track the progress of https://github.com/mvrdevelopment/spec/pull/64

    return try {
        val collection = unmarshaller.unmarshal(zipInputStream) as? GDTF ?: throw ClassCastException("Unmarshalled GDTF cannot be cast to GDTF class")
        Ok(collection)
    } catch (e: UnmarshalException) {
        Err(GdtfUnmarshalError(e.cause?.localizedMessage ?: ""))
    }
}