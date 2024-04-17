/**
 *
 */
package org.guce.orchestra.process.atm.activity;

import hk.hku.cecid.piazza.commons.activation.ByteArrayDataSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.guce.orchestra.core.OrchestraEbxmlMessage;
import org.guce.orchestra.dao.util.Generator;
import org.guce.orchestra.entity.Dossier;
import org.guce.orchestra.process.atm.ATMConstants;
import org.guce.orchestra.process.atm.Request;
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
import org.guce.orchestra.core.ServiceUtility;
import org.guce.orchestra.dao.MessageFacadeLocal;
import org.guce.orchestra.entity.Message;
import org.guce.orchestra.handler.OrchestraMessageClassifier;
import org.guce.orchestra.process.atm.util.MessageActionValidator;
import org.guce.orchestra.util.OrchestraEbxmlFields;
import org.guce.orchestra.util.OrchestraEbxmlUtility;
import org.guce.orchestra.util.ProcessUtility;
import org.mule.util.StringUtils;
import org.w3c.dom.Document;

/**
 * @author NGC
 *
 */
public class ProcessResponse extends CustomActivity {

    private String[] toPartyIds;

    private boolean transformBefore = true;

    /**
     * Liste des action attendus , separer par des virgules
     */
    private String actions;

    public ProcessResponse() {

    }

    public Object updatePaymentState(Request request) {
        request.getDossier().setProperty(Request.RECORD_PAYED, "true");
        request.update();
        return request;
    }

    public Object createPaymentResponse(Request request, OrchestraEbxmlMessage ebxmlMessage) {
        return null;
    }

    public Object createPaymentInitMessage(Request request, OrchestraEbxmlMessage ebxmlMessage) throws Exception {
        if (actions != null) {
            String[] actionTabs = StringUtils.splitAndTrim(actions, ",");
            if (actionTabs.length == 1) {
                MessageActionValidator.validate(ebxmlMessage, actionTabs[0]);
            } else {
                MessageActionValidator.validates(ebxmlMessage, actionTabs);
            }
        }
        Set<OrchestraEbxmlFields> fields = new HashSet<>();
        //fields.add(OrchestraEbxmlFields.ToPartyIds);
        //fields.add(OrchestraEbxmlFields.FromPartyIds);
        fields.add(OrchestraEbxmlFields.ConversationId);
        fields.add(OrchestraEbxmlFields.MessageId);
        fields.add(OrchestraEbxmlFields.TimeStamp);
        fields.add(OrchestraEbxmlFields.Action);
        fields.add(OrchestraEbxmlFields.Service);

        OrchestraEbxmlMessage message = OrchestraEbxmlUtility.copyWithout(ebxmlMessage, fields);
        message.getMessageHeader().setService("payment");
        message.getMessageHeader().setAction("PAY601");
        message.getMessageHeader().setMessageId(Generator.generateMessageID());

        Dossier file = ProcessUtility.createSubFile(request.getDossier(), message, null);
        message.setDossier(file);
        file.setFileType(Dossier.FILE_TYPE_EDI);
        request.update();
        HashMap map = new HashMap(2);
        map.put("message", message);
        map.put("action", "PAY601");
        return map;
    }

    public Object execute(Request request, OrchestraEbxmlMessage ebxmlMessage) throws Exception {
        if (actions != null) {
            String[] actionTabs = StringUtils.splitAndTrim(actions, ",");
            if (actionTabs.length == 1) {
                MessageActionValidator.validate(ebxmlMessage, actionTabs[0]);
            } else {
                MessageActionValidator.validates(ebxmlMessage, actionTabs);
            }
        }
        java.util.Map<String, Object> response;
        if (ebxmlMessage.getAction().equals(ATMConstants.ATM03)) {
            toPartyIds = new String[]{request.getImportateurPartner()};
            request.getDossier().setStatus(Dossier.FILE_STATUS_REJECTED);
        }else if (ebxmlMessage.getAction().equals(ATMConstants.ATM03R)) {
            toPartyIds = new String[]{request.getImportateurPartner()};
            request.getDossier().setStatus(Dossier.FILE_STATUS_REJECTED);
        }else if (ebxmlMessage.getAction().equals(ATMConstants.ATM02)) {
            toPartyIds = new String[]{request.getImportateurPartner()};
            request.getDossier().setStatus(Dossier.FILE_STATUS_COMPLEMENT);
        } else if (ebxmlMessage.getAction().equals(ATMConstants.ATM04)) {
            toPartyIds = new String[]{request.getImportateurPartner()};
            request.getDossier().setStatus(Dossier.FILE_STATUS_VALIDATED);
        } else if (ebxmlMessage.getAction().equals(ATMConstants.ATM10)) {
            toPartyIds = new String[]{request.getImportateurPartner()};
            request.getDossier().setStatus(Dossier.FILE_STATUS_VALIDATED);
        }else if (ebxmlMessage.getAction().equals(ATMConstants.ATM10R)) {
            toPartyIds = new String[]{request.getImportateurPartner()};
            request.getDossier().setStatus(Dossier.FILE_STATUS_VALIDATED);
        } else if (ebxmlMessage.getAction().equals(ATMConstants.ATM601)) {
            toPartyIds = new String[]{request.getRecieverPartner(), request.getImportateurPartner()};
        } else if (ebxmlMessage.getAction().equals(ATMConstants.ATM602)) {
            toPartyIds = new String[]{request.getRecieverPartner(), request.getImportateurPartner()};
        }
        response = (java.util.Map<String, Object>) this.generateResponse(ebxmlMessage);
        if (ebxmlMessage.getAction().equals(ATMConstants.ATM601)) {
            Set<OrchestraEbxmlFields> fields = new HashSet<>();
            fields.add(OrchestraEbxmlFields.ConversationId);
            fields.add(OrchestraEbxmlFields.MessageId);
            fields.add(OrchestraEbxmlFields.TimeStamp);
            fields.add(OrchestraEbxmlFields.Action);
            fields.add(OrchestraEbxmlFields.Service);

            OrchestraEbxmlMessage message = OrchestraEbxmlUtility.copyWithout(ebxmlMessage, fields);
            message.getMessageHeader().setService("payment");
            message.getMessageHeader().setAction("PAY601");
            message.getMessageHeader().setMessageId(Generator.generateMessageID());

            Dossier file = ProcessUtility.createSubFile(request.getDossier(), message, null);
            message.setDossier(file);
            file.setFileType(Dossier.FILE_TYPE_EDI);
            request.update();
            response.put("notification601", message);
        } else if (ebxmlMessage.getAction().equals(ATMConstants.ATM602)
                || ebxmlMessage.getAction().equals("PAY602")) { 
            Set<OrchestraEbxmlFields> fields = new HashSet<OrchestraEbxmlFields>();
            fields.clear();
            fields.add(OrchestraEbxmlFields.ToPartyIds);
            fields.add(OrchestraEbxmlFields.FromPartyIds);
            fields.add(OrchestraEbxmlFields.ConversationId);
            fields.add(OrchestraEbxmlFields.MessageId);
            fields.add(OrchestraEbxmlFields.TimeStamp);
            fields.add(OrchestraEbxmlFields.Action);
            fields.add(OrchestraEbxmlFields.Service);
            fields.add(OrchestraEbxmlFields.PayloadContainers);

            OrchestraEbxmlMessage notificationMessage = OrchestraEbxmlUtility.copyWithout(ebxmlMessage, fields);
            byte[] transformData = this.getDocument(ebxmlMessage);
            DataSource ds = new ByteArrayDataSource(transformData);
            DataHandler dh = new DataHandler(ds);
            notificationMessage.addPayloadContainer(dh, ebxmlMessage.getAction(), null);
            notificationMessage.getMessageHeader().setService("atm");
            String action = "PAY602".equals(ebxmlMessage.getAction()) ? ATMConstants.ATM602 : ebxmlMessage.getAction();
            notificationMessage.getMessageHeader().setAction(action);
            notificationMessage.getMessageHeader().addToPartyId(request.getImportateurPartner());
            notificationMessage.getMessageHeader().addFromPartyId("GUCE");
            notificationMessage.getMessageHeader().setConversationId(request.getDossier().getBpmId());
            notificationMessage.getMessageHeader().setMessageId(Generator.generateMessageID());
            notificationMessage.setDossier(request.getDossier());

            //Récupération du message d'initialisation
            MessageFacadeLocal messageF = ServiceUtility.getMessageFacade();
            Message initMessage = messageF.find(request.getInitMessageId());
            OrchestraEbxmlMessage ebxmlInitMessage = new OrchestraEbxmlMessage(new ByteArrayInputStream(initMessage.getData()));
            OrchestraEbxmlMessage atm01Message = createATM01Message(request, ebxmlInitMessage);
            request.update();
            response.put("action", (ebxmlMessage.getMessageType().equals(ATMConstants.ATM01R))?ATMConstants.ATM01R:ATMConstants.ATM01);
            response.put("vtMessage", atm01Message);
            response.put("notification602", notificationMessage);
        }
        
        request.update();
        return response;
    }

    public Object execute(Request request, HashMap payment) throws Exception {
        if (payment != null) {
            List<OrchestraEbxmlMessage> list = (List) payment.get("messages");
            if (list != null && !list.isEmpty()) {
                for (OrchestraEbxmlMessage ebxml : list) {
                    if (ebxml != null && !OrchestraMessageClassifier.isAperak(ebxml)) {
                        return execute(request, ebxml);
                    }
                }
            }
        }
        throw new Exception("Payment response message doesn't content any ebXml");
    }

    @Override
    public String[] toPartyIds() {
        return this.toPartyIds;
    }

    @Override
    public boolean shouldTransformBefore() {
        //return false;
        return this.transformBefore;
    }

    public String getActions() {
        return actions;
    }

    public void setActions(String actions) {
        this.actions = actions;
    }

    private byte[] getDocument(OrchestraEbxmlMessage ebxmlMessage) throws Exception {
        byte[] docByte = ebxmlMessage.getMainContentByte();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputStream is = new ByteArrayInputStream(docByte);
        Document document = db.parse(is);
        InputStream xslIs = getClass().getResourceAsStream(ATMConstants.XSL_RESSOURCE_PATH + "PAY602_to_ATM602.xslt");
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

    public OrchestraEbxmlMessage createATM01Message(Request request, OrchestraEbxmlMessage ebxmlInitMessage) throws Exception {
        Dossier dossier = request.getDossier();
        Set<OrchestraEbxmlFields> fields = new HashSet<>();
        fields.add(OrchestraEbxmlFields.ConversationId);
        fields.add(OrchestraEbxmlFields.MessageId);
        fields.add(OrchestraEbxmlFields.TimeStamp);
        OrchestraEbxmlMessage atm01Message = OrchestraEbxmlUtility.copyWithout(ebxmlInitMessage, fields);
        atm01Message.getMessageHeader().setRefToMessageId(ebxmlInitMessage.getMessageId());
        atm01Message.setDossier(dossier); 
        
        return atm01Message;
    }
}
