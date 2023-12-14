/**
 *
 */
package org.guce.orchestra.process.dem.activity;

import hk.hku.cecid.ebms.pkg.PayloadContainer;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
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
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.guce.orchestra.core.OrchestraEbxmlMessage;
import org.guce.orchestra.core.ServiceUtility;
import org.guce.orchestra.dao.util.Generator;
import org.guce.orchestra.entity.Dossier;
import org.guce.orchestra.handler.OrchestraMessageClassifier;
import org.guce.orchestra.handler.OrchestraSignalMessageGenerator;
import org.guce.orchestra.process.payment.PAYMENTConstants;
import org.guce.orchestra.process.dem.DEMConstants;
import org.guce.orchestra.process.dem.Request;
import org.guce.orchestra.process.dem.util.MessageActionValidator;
import org.guce.orchestra.util.OrchestraEbxmlFields;
import org.guce.orchestra.util.OrchestraEbxmlUtility;
import org.guce.orchestra.util.ProcessUtility;
import org.jboss.logging.Logger;
import org.mule.util.StringUtils;
import org.w3c.dom.Document;

/**
 * @author NGC
 *
 */
public class CheckInitMessageAndGenerateResponse extends CustomActivity {

    private String[] toPartyIds;

    private boolean transformBefore = false;

    public CheckInitMessageAndGenerateResponse() {

    }

    public Object execute(Request request, OrchestraEbxmlMessage ebxmlMessage) throws Exception {
        MessageActionValidator.validates(ebxmlMessage, DEMConstants.DEM01, DEMConstants.DEM11, DEMConstants.DEM09);
        toPartyIds = new String[]{request.getRecieverPartner()};
        request.setInitMessageId(ebxmlMessage.getMessageId());
        Dossier dossier = ServiceUtility.getDossierFacade().find(request.getDossierId());
        request.setDossier(dossier);
        ebxmlMessage.setDossier(dossier);
        this.transformBefore = true;
        Map<String, Object> response = (Map<String, Object>) this.generateResponse(ebxmlMessage);
        if (DEMConstants.DEM01.equals(ebxmlMessage.getAction())) {
            OrchestraEbxmlMessage aperak = OrchestraSignalMessageGenerator.generateAcknowledgment(ebxmlMessage, ebxmlMessage.getMessageId(), OrchestraMessageClassifier.ACTION_ACKNOWLEDGMENT);
            response = new HashMap<>();
            List<OrchestraEbxmlMessage> list = new ArrayList<>();
            list.add(aperak);
            response.put("action", ebxmlMessage.getAction());
            response.put("payment", "true");
            Logger.getLogger(this.getClass().getName()).info("START CREATION MESSAGE PAIEMENT DEM");
            OrchestraEbxmlMessage demPayment = createPaymentMessage(request, ebxmlMessage);
            ((Map<String, Object>) response).put("demPayment", demPayment);
            list.add((OrchestraEbxmlMessage) createNotificationMessage(request, ebxmlMessage, DEMConstants.DEM601));
            ((Map<String, Object>) response).put("notification601", list);
            Logger.getLogger(this.getClass().getName()).info("END CREATION MESSAGE PAIEMENT DEM");
        }
        request.update();
        return response;
    }

    @Override
    public String[] toPartyIds() {
        return this.toPartyIds;
    }

    @Override
    public boolean shouldTransformBefore() {
        return this.transformBefore;
    }

    public OrchestraEbxmlMessage createPaymentMessage(Request request, OrchestraEbxmlMessage ebxmlMessage) throws Exception {
        Set<OrchestraEbxmlFields> fields = new HashSet<>();
        fields.add(OrchestraEbxmlFields.PayloadContainers);
        fields.add(OrchestraEbxmlFields.ConversationId);
        fields.add(OrchestraEbxmlFields.MessageId);
        fields.add(OrchestraEbxmlFields.TimeStamp);
        fields.add(OrchestraEbxmlFields.Action);
        fields.add(OrchestraEbxmlFields.Service);
        fields.add(OrchestraEbxmlFields.FromPartyIds);

        OrchestraEbxmlMessage message = OrchestraEbxmlUtility.copyWithout(ebxmlMessage, fields);
        message.getMessageHeader().addFromPartyId(request.getImportateurPartner());
        byte[] data = ebxmlMessage.getMainContentByte();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = factory.newDocumentBuilder();
        Document document = db.parse(new ByteArrayInputStream(data));
        TransformerFactory transformFactory = TransformerFactory.newInstance();
        InputStream xslIs = getClass().getResourceAsStream(DEMConstants.XSL_RESSOURCE_PATH + "payment.xslt");
        Transformer t = transformFactory.newTransformer(new StreamSource(xslIs));
        t.setParameter("param_document_code", PAYMENTConstants.PAY601);
        t.setParameter("param_service_code", PAYMENTConstants.PROCESS_ID);
        t.setParameter("param_numero_dossier", request.getDossier().getNumeroDossier() + "PAY");
        t.setParameter("param_numero_demande", request.getDossier().getNumeroDossier());
        if (StringUtils.isNotEmpty(ebxmlMessage.getMessageHeader().getConversationId())) {
            t.setParameter("param_reference_guce", ebxmlMessage.getMessageHeader().getConversationId());
        }
        if (ebxmlMessage.getDossier() != null && StringUtils.isNotEmpty(ebxmlMessage.getDossier().getProperty(DEMConstants.DEM_BILL_TYPE))) {
            t.setParameter("param_type_facture", ebxmlMessage.getDossier().getProperty(DEMConstants.DEM_BILL_TYPE));
        }else{
            t.setParameter("param_type_facture", "DEM_"+request.getRecieverPartner());
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        t.transform(new DOMSource(document), new StreamResult(out));
        DataSource ds = new org.guce.orchestra.io.ByteArrayDataSource(out.toByteArray());

        message.getMessageHeader().setService("payment");
        message.getMessageHeader().setAction(PAYMENTConstants.PAY601);
        message.getMessageHeader().setMessageId(Generator.generateMessageID());
        DataHandler dh = new DataHandler(ds);
        message.addPayloadContainer(dh, PAYMENTConstants.PAY601, null);

        Dossier file = ProcessUtility.createSubFile(request.getDossier(), message, null);
        message.setDossier(file);
        file.setFileType(Dossier.FILE_TYPE_EDI);
        request.update();
        return message;
    }

    public OrchestraEbxmlMessage createNotificationMessage(Request request, OrchestraEbxmlMessage ebxmlMessage, String notificationCode) throws Exception {
        Set<OrchestraEbxmlFields> fields = new HashSet<>();
        fields.add(OrchestraEbxmlFields.PayloadContainers);
        fields.add(OrchestraEbxmlFields.ConversationId);
        fields.add(OrchestraEbxmlFields.ToPartyIds);
        fields.add(OrchestraEbxmlFields.FromPartyIds);
        fields.add(OrchestraEbxmlFields.MessageId);
        fields.add(OrchestraEbxmlFields.TimeStamp);
        fields.add(OrchestraEbxmlFields.Action);
        fields.add(OrchestraEbxmlFields.Service);
        OrchestraEbxmlMessage message = OrchestraEbxmlUtility.copyWithout(ebxmlMessage, fields);
        byte[] data = ebxmlMessage.getMainContentByte();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = factory.newDocumentBuilder();
        Document document = db.parse(new ByteArrayInputStream(data));
        TransformerFactory transformFactory = TransformerFactory.newInstance();
        InputStream xslIs = getClass().getResourceAsStream(DEMConstants.XSL_RESSOURCE_PATH + "dem601.xslt");
        Transformer t = transformFactory.newTransformer(new StreamSource(xslIs));
        t.setParameter("param_document_code", notificationCode);
        t.setParameter("param_message_type_code", notificationCode);
        if (StringUtils.isNotEmpty(ebxmlMessage.getMessageHeader().getConversationId())) {
            t.setParameter("param_reference_guce", ebxmlMessage.getMessageHeader().getConversationId());
        }
        if (ebxmlMessage.getDossier() != null && StringUtils.isNotEmpty(ebxmlMessage.getDossier().getProperty(DEMConstants.DEM_BILL_TYPE))) {
            t.setParameter("param_type_facture", ebxmlMessage.getDossier().getProperty(DEMConstants.DEM_BILL_TYPE));
        }else{
            t.setParameter("param_type_facture", "DEM_"+request.getRecieverPartner());
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        t.transform(new DOMSource(document), new StreamResult(out));
        DataSource ds = new org.guce.orchestra.io.ByteArrayDataSource(out.toByteArray());
        DataHandler dh = new DataHandler(ds);
        message.getMessageHeader().addToPartyId(request.getDossier().getInitializer().getId());
        message.getMessageHeader().addFromPartyId("GUCE");
        message.getMessageHeader().setService("dem");
        message.getMessageHeader().setAction(notificationCode);
        message.setDossier(request.getDossier());
        message.getMessageHeader().addToPartyId(request.getRecieverPartner());
        message.addPayloadContainer(dh, notificationCode, null);
        // on intègre les PJs
        Iterator<PayloadContainer> it = ebxmlMessage.getPayloadContainers();
        if (!it.hasNext()) {
            Logger.getLogger(getClass().getName()).error(String.format("The payload container is empty for message ", ebxmlMessage));
            return null;
        }
        it.next();
        while (it.hasNext()) {
            PayloadContainer at = it.next();
            DataHandler pj_dh = at.getDataHandler();
            String name = at.getContentId();
            message.addPayloadContainer(pj_dh, name, null);
        }
        return message;
    }
}
