package org.cueglow.server.gdtf

import com.gdtf_share.schemas.device.GDTF
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

// hack from http://cooljavablogs.blogspot.com/2008/08/how-to-instruct-jaxb-to-ignore.html
// to fix namespace not being defined in GDTF-file, but in Schema
class XMLNameSpaceFilter(arg0: XMLReader): org.xml.sax.helpers.XMLFilterImpl(arg0) {
    override fun startElement(uri: String?, localName: String?, qName: String?, atts: Attributes?) {
        super.startElement("http://schemas.gdtf-share.com/device", localName, qName, atts)
    }
}

/**
 * Handler for new GDTF
 *
 * Parses it and adds it to the Patch
 */
fun handleNewGdtf(inputStream: InputStream): Result<UUID, GlowError> {
    val parseResult = parseGdtf(inputStream) // handle Err

    // add it to the Patch
    // return right result
}

fun parseGdtf(inputStream: InputStream): Result<GDTF, GlowError> {
    // TODO unzip, currently operates on XML directly
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



    val jc = JAXBContext.newInstance("com.gdtf_share.schemas.device")
    val unmarshaller = jc.createUnmarshaller()

    // create custom XMLReader
    val factory = SAXParserFactory.newInstance()
    val reader = factory.newSAXParser().xmlReader

    // The filter class is set to the correct namespace by default
    val xmlFilter = XMLNameSpaceFilter(reader)
    reader.contentHandler = unmarshaller.unmarshallerHandler

    // TODO add validation here somewhere
    // possible while parsing?

    // TODO does this work? Does it only read the current entry in the zip file?
    val source = SAXSource(xmlFilter, InputSource(zipInputStream))

    // TODO unsafe null handling
    val collection = unmarshaller.unmarshal(source) as? GDTF

    return Ok(collection)
}