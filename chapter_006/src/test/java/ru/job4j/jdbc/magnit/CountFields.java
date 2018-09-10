package ru.job4j.jdbc.magnit;

import org.junit.Ignore;
import org.junit.Test;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

public class CountFields extends DefaultHandler {

    private int count;
    private BigDecimal sum;

    public CountFields() {
        this.sum = new BigDecimal(0);
        count = 0;
    }

    @Ignore
    @Test
    public void whenTest() throws ParserConfigurationException, SAXException, IOException, TransformerException {
        Config config = new Config();
        config.load("magnit.properties");
        generateRecords(config, 2_000_000);
        saveToXML(config);
        transformXML(config);
        parse(config);
    }

    private static String convertToFileURL(String filename) {
        String path = new File(filename).getAbsolutePath();
        if (File.separatorChar != '/') {
            path = path.replace(File.separatorChar, '/');
        }

        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        return "file:" + path;
    }

    @Override
    public void endDocument() {
        System.out.format("Count of fields: %s\n", count);
        System.out.format("Sum of fields: %s\n", sum);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if ("entry".equals(localName)) {
            int value = Integer.valueOf(attributes.getValue("field"));
            if (value > 0) {
                sum = sum.add(new BigDecimal(value));
                count++;
            }
        }
    }

    private static void generateRecords(Config config, int count) {
        StoreSQL storeSQL = new StoreSQL(config);
        storeSQL.generate(count);
    }

    private static void saveToXML(Config config) {
        StoreSQL storeSQL = new StoreSQL(config);
        StoreXML storeXML = new StoreXML(new File(config.getValue("file.xml")));
        storeXML.save(storeSQL.selectAll());
    }

    private static void transformXML(Config config) throws TransformerException {
        ConvertXSQT convertXSQT = new ConvertXSQT();
        File fileXML = new File(config.getValue("file.xml"));
        File fileXSL = new File(config.getValue("file.xsl"));
        File fileOut = new File(config.getValue("file.result"));
        convertXSQT.convert(fileXML, fileOut, fileXSL);
    }

    private static void parse(Config config) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        SAXParser saxParser = spf.newSAXParser();
        XMLReader xmlReader = saxParser.getXMLReader();
        xmlReader.setContentHandler(new CountFields());
        xmlReader.parse(convertToFileURL(config.getValue("file.result")));
    }


}
