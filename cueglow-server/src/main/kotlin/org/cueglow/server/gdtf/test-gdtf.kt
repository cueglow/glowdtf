package org.cueglow.server.gdtf

import org.xml.sax.Attributes
import org.xml.sax.InputSource
import org.xml.sax.XMLReader
import javax.xml.bind.JAXBContext
import java.io.File
import java.io.FileInputStream
import java.lang.Thread.*
import javax.xml.parsers.SAXParserFactory
import javax.xml.transform.sax.SAXSource
import java.lang.Thread.currentThread

// A small test in parsing GDTF XML
// It was manually extracted from the gdtf file


// hack from http://cooljavablogs.blogspot.com/2008/08/how-to-instruct-jaxb-to-ignore.html
// to fix namespace not being defined in GDTF-file, but in Schema
class XMLNameSpaceFilter(arg0: XMLReader): org.xml.sax.helpers.XMLFilterImpl(arg0) {
    override fun startElement(uri: String?, localName: String?, qName: String?, atts: Attributes?) {
        super.startElement("http://schemas.gdtf-share.com/device", localName, qName, atts)
    }
}


fun main() {
    val jc = JAXBContext.newInstance("com.gdtf_share.schemas.device")
    val unmarshaller = jc.createUnmarshaller()

    // create custom XMLReader
    val factory = SAXParserFactory.newInstance()
    val reader = factory.newSAXParser().xmlReader

    // The filter class is set to the correct namespace by default
    val xmlFilter = XMLNameSpaceFilter(reader)
    reader.setContentHandler(unmarshaller.unmarshallerHandler)
    val rootDir = currentThread().contextClassLoader?.getResource("")?.file
    println(rootDir)
    val inStream = FileInputStream(File(rootDir, "../../../../src/main/kotlin/org/cueglow/server/gdtf/description.xml"))
    val source = SAXSource(xmlFilter, InputSource(inStream))
    val collection = unmarshaller.unmarshal(source)
    println("The baby is alive and well! It's a GDTF object!")
    println("Look at it. It's beautiful: ")
    println(collection)
}
