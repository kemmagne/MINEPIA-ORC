package org.guce.orchestra.process.dem.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.guce.orchestra.core.OrchestraEbxmlMessage;
import org.guce.orchestra.process.dem.DEMConstants;
import org.guce.orchestra.util.OrchestraEbxmlFields;
import org.guce.orchestra.util.OrchestraEbxmlUtility;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author NGC
 */
public class TransformUtils {

    public byte[] transform(byte[] o, String file) throws ParserConfigurationException, TransformerConfigurationException, SAXException, IOException, TransformerException {
        DocumentBuilderFactory factory1 = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = factory1.newDocumentBuilder();
        InputStream p001Stream = new ByteArrayInputStream(o);
        org.w3c.dom.Document doc = db.parse(p001Stream);
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer t = factory.newTransformer(new StreamSource(getClass().getResourceAsStream(DEMConstants.XSL_RESSOURCE_PATH + file)));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        t.transform(new DOMSource(doc), new StreamResult(out));
        return out.toByteArray();
    }

    public byte[] transform(byte[] o, String file, Map<String, Object> map) throws ParserConfigurationException, TransformerConfigurationException, SAXException, IOException, TransformerException {
        DocumentBuilderFactory factory1 = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = factory1.newDocumentBuilder();
        InputStream p001Stream = new ByteArrayInputStream(o);
        org.w3c.dom.Document doc = db.parse(p001Stream);
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer t = factory.newTransformer(new StreamSource(getClass().getResourceAsStream(DEMConstants.XSL_RESSOURCE_PATH + file)));
        for (Map.Entry<String, Object> entrySet : map.entrySet()) {
            String key = entrySet.getKey();
            Object value = entrySet.getValue();
            t.setParameter(key, value);
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        t.transform(new DOMSource(doc), new StreamResult(out));
        return out.toByteArray();
    }

    public OrchestraEbxmlMessage transform(OrchestraEbxmlMessage source, String action, String xslFile, Map<String, Object> xslParmas, OrchestraEbxmlFields... exceptFields) throws ParserConfigurationException, TransformerConfigurationException, SAXException, IOException, TransformerException, SOAPException {
        Set<OrchestraEbxmlFields> fields = new HashSet<>(Arrays.asList(exceptFields));
        OrchestraEbxmlMessage response = OrchestraEbxmlUtility.copyWithout(source, fields);
        byte[] data = source.getMainContentByte();
        DocumentBuilderFactory factory1 = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = factory1.newDocumentBuilder();
        InputStream p001Stream = new ByteArrayInputStream(data);
        org.w3c.dom.Document doc = db.parse(p001Stream);
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer t = factory.newTransformer(new StreamSource(getClass().getResourceAsStream(DEMConstants.XSL_RESSOURCE_PATH + xslFile)));
        for (Map.Entry<String, Object> entrySet : xslParmas.entrySet()) {
            String key = entrySet.getKey();
            Object value = entrySet.getValue();
            t.setParameter(key, value);
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        t.transform(new DOMSource(doc), new StreamResult(out));
        DataSource ds = new org.guce.orchestra.io.ByteArrayDataSource(out.toByteArray());
        DataHandler dataHandler = new DataHandler(ds);
        response.addPayloadContainer(dataHandler, action, action);
        response.setDossier(source.getDossier());
        return response;
    }

    public String getValue(OrchestraEbxmlMessage ebxmlMessage, String path) throws Exception {

        byte[] data = ebxmlMessage.getMainContentByte();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = factory.newDocumentBuilder();
        Document document = db.parse(new ByteArrayInputStream(data));
        XPath xpath = XPathFactory.newInstance().newXPath();

        String value = (String) xpath.compile(path).evaluate(document, XPathConstants.STRING);

        return value;
    }

}
