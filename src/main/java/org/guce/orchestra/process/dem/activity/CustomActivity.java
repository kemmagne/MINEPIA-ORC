package org.guce.orchestra.process.dem.activity;

import hk.hku.cecid.ebms.pkg.PayloadContainer;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.commons.io.IOUtils;
import org.guce.orchestra.core.OrchestraEbxmlMessage;
import org.guce.orchestra.handler.OrchestraMessageClassifier;
import org.guce.orchestra.io.ByteArrayDataSource;
import org.guce.orchestra.process.dem.DEMConstants;
import org.guce.orchestra.util.OrchestraEbxmlFields;
import org.guce.orchestra.util.OrchestraEbxmlUtility;
import org.guce.orchestra.util.bpm.ValidatorUtil;
import org.w3c.dom.Document;

public abstract class CustomActivity {

    public Object generateResponse(OrchestraEbxmlMessage ebxmlMessage) throws Exception {
        URL url = getClass().getResource(DEMConstants.SCHEMA_RESSOURCE_PATH + "dem.xsd");
        String[] toPartyIds = toPartyIds();
        boolean transformBefore = shouldTransformBefore();
        OrchestraEbxmlMessage aperak;
        byte[] transformData = null;
        if (transformBefore) {
            //Transformation du message avant la validation
            transformData = this.getDocument(ebxmlMessage);
            InputStream is = new ByteArrayInputStream(transformData);
            aperak = ValidatorUtil.validateWithSchema(ebxmlMessage, is, url);
            IOUtils.closeQuietly(is);
        } else {
            aperak = ValidatorUtil.validateWithSchema(ebxmlMessage, url);
        }
        try {
            ebxmlMessage.addAckRequested(false);
        } catch (Exception e) {
        }
        //OrchestraEbxmlMessage aperak = OrchestraSignalMessageGenerator.generateAcknowledgment(ebxmlMessage, ebxmlMessage.getMessageId(), OrchestraMessageClassifier.ACTION_ACKNOWLEDGMENT);
        Map<String, Object> result = new HashMap<>();
        result.put("action", ebxmlMessage.getAction());
        if (aperak.getAction().equals(OrchestraMessageClassifier.ACTION_APERAK_C)) {
            result.put("action", OrchestraMessageClassifier.ACTION_APERAK_C);
            result.put("messages", aperak);
        } else if (toPartyIds != null) {
            List<OrchestraEbxmlMessage> list = new ArrayList<>();
            list.add(aperak);
            Set<OrchestraEbxmlFields> fields = new HashSet<>();
            fields.add(OrchestraEbxmlFields.ToPartyIds);
            fields.add(OrchestraEbxmlFields.ConversationId);
            fields.add(OrchestraEbxmlFields.MessageId);
            fields.add(OrchestraEbxmlFields.TimeStamp);
            fields.add(OrchestraEbxmlFields.RefToMessageId);
            List<PayloadContainer> payloads = new ArrayList<>();
            if (transformBefore) {
                fields.add(OrchestraEbxmlFields.PayloadContainers);
                Iterator<PayloadContainer> iterator = ebxmlMessage.getPayloadContainers();
                if (iterator.hasNext()) {
                    //avoid the first payload : ie The main content
                    iterator.next();
                }
                while (iterator.hasNext()) {
                    PayloadContainer payload = iterator.next();
                    payloads.add(payload);
                }
            }
            //fields.add(OrchestraEbxmlFields.CpaId);
            OrchestraEbxmlMessage response = OrchestraEbxmlUtility.copyWithout(ebxmlMessage, fields);
            if (transformBefore) {
                DataSource ds = new ByteArrayDataSource(transformData);
                DataHandler dh = new DataHandler(ds);
                response.addPayloadContainer(dh, ebxmlMessage.getAction(), null);
                for (PayloadContainer payload : payloads) {
                    response.addPayloadContainer(payload.getDataHandler(), payload.getContentId(), null);
                }
            }
            response.getMessageHeader().setRefToMessageId(ebxmlMessage.getMessageId());
            for (String toPartyId : toPartyIds) {
                //OrchestraEbxmlMessage response = OrchestraEbxmlUtility.copyWithout(ebxmlMessage, fields );
                response.getMessageHeader().addToPartyId(toPartyId);
                //response.getMessageHeader().setRefToMessageId(ebxmlMessage.getMessageId());
            }
            list.add(response);
            result.put("messages", list);
        } else {
            result.put("action", aperak.getAction());
            result.put("messages", aperak);
        }
        result.put("messageid", ebxmlMessage.getMessageId());
        return result;
    }

    public abstract String[] toPartyIds();

    public abstract boolean shouldTransformBefore();

    private byte[] getDocument(OrchestraEbxmlMessage ebxmlMessage) throws Exception {
        byte[] docByte = ebxmlMessage.getMainContentByte();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputStream is = new ByteArrayInputStream(docByte);
        Document document = db.parse(is);
        InputStream xslIs = getClass().getResourceAsStream(DEMConstants.XSL_RESSOURCE_PATH + "dem.xslt");
        Transformer tr = TransformerFactory.newInstance().newTransformer(new StreamSource(xslIs));
        tr.setOutputProperty(OutputKeys.INDENT, "yes");
        tr.setOutputProperty(OutputKeys.METHOD, "xml");
        tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        //tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "roles.dtd");
        tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        // send DOM to file
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        tr.transform(new DOMSource(document), new StreamResult(out));
        IOUtils.closeQuietly(is);
        IOUtils.closeQuietly(xslIs);
        byte[] data = out.toByteArray();
        IOUtils.closeQuietly(out);
        return data;
    }
}
