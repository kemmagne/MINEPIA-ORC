package org.guce.orchestra.process.vt1;

import hk.hku.cecid.ebms.pkg.MessageHeader;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import javax.activation.DataHandler;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.apache.commons.lang.StringUtils;

import org.apache.log4j.Logger;
import org.guce.orchestra.core.AbstractProcessProcessing;
import org.guce.orchestra.core.ApplicationConfig;
import org.guce.orchestra.core.ApplicationManager;
import org.guce.orchestra.core.OrchestraEbxmlMessage;
import org.guce.orchestra.core.ProcessAction;
import org.guce.orchestra.core.ProcessProcessing;
import org.guce.orchestra.core.ServiceUtility;
import org.guce.orchestra.entity.Charger;
import org.guce.orchestra.entity.Dossier;
import org.guce.orchestra.entity.Endpoint;
import org.guce.orchestra.entity.Partner;
import org.guce.orchestra.entity.Process;
import org.guce.orchestra.ext.siat.binding.DOCUMENT;
import org.guce.orchestra.handler.OrchestraMessageClassifier;
import org.guce.orchestra.handler.OrchestraSignalMessageGenerator;
import org.guce.orchestra.io.ByteArrayDataSource;
import org.guce.orchestra.util.JAXBUtil;
import org.mule.api.MuleMessage;
import org.w3c.dom.Document;

public class VT1Processing extends AbstractProcessProcessing implements ProcessProcessing {

    public static int SIMULATION_COUNT = 0;

    public VT1Processing() {

    }

    @Override
    public String dossierNumber(OrchestraEbxmlMessage ebxmlMessage) {
        String xpathExp = "/DOCUMENT/REFERENCE_DOSSIER/NUMERO_DOSSIER/text()";
        return this.dossierNumber(ebxmlMessage, xpathExp);
    }

    @Override
    public String processingType(OrchestraEbxmlMessage ebxml, boolean isInbound, Map params) {
        String action = ebxml.getAction();
        if ((action.equals(VT1Constants.VT101) && isInbound) || ((action.equals(VT1Constants.VT111) && !isInbound))) {
            return VT1Constants.VT101;
        } else if (action.equals(VT1Constants.VT101)
                || action.equals(VT1Constants.VT103)
                || action.equals(VT1Constants.VT102)
                || action.equals(VT1Constants.VT104)) {
            return VT1Constants.VT104;
        }
        return null;
    }

    @Override
    public Dossier newDossier(OrchestraEbxmlMessage ebxmlMessage) {
        Dossier dossier = null;
        String action = ebxmlMessage.getAction();
        if (action.equals(VT1Constants.VT101)) {
            dossier = new Dossier();
            Charger charger = new Charger();
            try {
                byte[] data = ebxmlMessage.getMainContentByte();
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = factory.newDocumentBuilder();
                Document document = db.parse(new ByteArrayInputStream(data));
                XPath xpath = XPathFactory.newInstance().newXPath();

                String nom = (String) xpath.compile("/DOCUMENT/CONTENT/CLIENT/RAISON_SOCIALE").evaluate(document, XPathConstants.STRING);
                String niu = (String) xpath.compile("/DOCUMENT/CONTENT/CLIENT/NUMERO_CONTRIBUABLE").evaluate(document, XPathConstants.STRING);
                String adresse1 = (String) xpath.compile("/DOCUMENT/CONTENT/CLIENT/ADRESSE/ADRESSE1").evaluate(document, XPathConstants.STRING);
                String adresse2 = (String) xpath.compile("/DOCUMENT/CONTENT/CLIENT/ADRESSE/ADRESSE2").evaluate(document, XPathConstants.STRING);
                String adresse3 = (String) xpath.compile("/DOCUMENT/CONTENT/CLIENT/ADRESSE/ADRESSE3").evaluate(document, XPathConstants.STRING);
                String bp = (String) xpath.compile("/DOCUMENT/CONTENT/CLIENT/ADRESSE/BP").evaluate(document, XPathConstants.STRING);
                String ville = (String) xpath.compile("/DOCUMENT/CONTENT/CLIENT/ADRESSE/VILLE").evaluate(document, XPathConstants.STRING);
                String codePays = (String) xpath.compile("/DOCUMENT/CONTENT/CLIENT/ADRESSE/PAYS_ADRESSE/CODE_PAYS").evaluate(document, XPathConstants.STRING);
                String faxCode = (String) xpath.compile("/DOCUMENT/CONTENT/CLIENT/FAX/INDICATIF_PAYS").evaluate(document, XPathConstants.STRING);
                String faxNumber = (String) xpath.compile("/DOCUMENT/CONTENT/CLIENT/FAX/NUMERO").evaluate(document, XPathConstants.STRING);
                String fixeCode = (String) xpath.compile("/DOCUMENT/CONTENT/CLIENT/TELEPHONE_FIXE/INDICATIF_PAYS").evaluate(document, XPathConstants.STRING);
                String fixNumber = (String) xpath.compile("/DOCUMENT/CONTENT/CLIENT/TELEPHONE_FIXE/NUMERO").evaluate(document, XPathConstants.STRING);
                String mobileCode = (String) xpath.compile("/DOCUMENT/CONTENT/CLIENT/TELEPHONE_MOBILE/INDICATIF_PAYS").evaluate(document, XPathConstants.STRING);
                String mobileNumber = (String) xpath.compile("/DOCUMENT/CONTENT/CLIENT/TELEPHONE_MOBILE/NUMERO").evaluate(document, XPathConstants.STRING);
                String email = (String) xpath.compile("/DOCUMENT/CONTENT/CLIENT/ADRESSE/EMAIL").evaluate(document, XPathConstants.STRING);

                charger.setName(nom);
                charger.setNiu(niu);
                charger.setAdress(adresse1);
                charger.setAdress1(adresse2);
                charger.setAdress2(adresse3);
                charger.setPoBox(bp);
                charger.setTown(ville);
                charger.setCountry(codePays);
                try {
                    charger.setFaxCode(Short.valueOf(faxCode));
                } catch (Exception e) {
                }
                charger.setFaxNumber(faxNumber);
                try {
                    charger.setPhoneFixCode(Short.valueOf(fixeCode));
                } catch (Exception e) {
                }
                charger.setPhoneFix(fixNumber);
                try {
                    charger.setMobilePhoneCode(Short.valueOf(mobileCode));
                } catch (Exception e) {
                }
                charger.setMobilePhone(mobileNumber);
                charger.setEmail(email);

                this.fixCharger(charger);
            } catch (Exception e) {
                Logger.getLogger(getClass()).warn(null, e);
            }
            dossier.setCharger(charger);
        }
        return dossier;
    }

    @Override
    public Object afterSended(MuleMessage muleMessage, String messageId, Map params) throws Exception {
        OrchestraEbxmlMessage ebxml = this.getEbxmlMessage(messageId);
        String stage = ApplicationManager.getInstance().getAppConfig().getDevellopmentStage();
        if (!stage.equals(ApplicationConfig.DEVELOPMENT_STAGE)) {
            return muleMessage;
        }

        if (ebxml.getAction().equals(VT1Constants.VT101)) {
            this.processVT101(ebxml, params);
        }
        return muleMessage;
    }

    @Override
    public String messageSubject(OrchestraEbxmlMessage ebxmlMessage) {
        return null;
    }

    private void processVT101(OrchestraEbxmlMessage ebxml, Map params) throws Exception {
        //org.guce.orchestra.entity.Process process = ServiceUtility.getProcessFacade().find(ebxml.getService());
        String fromPartyId = ((MessageHeader.PartyId) ebxml.getToPartyIds().next()).getId();
        String toPartyId = ((MessageHeader.PartyId) ebxml.getFromPartyIds().next()).getId();
        /*/Properties prop = new Properties();
         prop.load(new StringReader(process.getParams()));
         String toPartyId = prop.getProperty("vtp.partner.signatory");*/
        if (toPartyId.equals(fromPartyId)) {
            return;
        }
        String conversationId = ebxml.getConversationId();
        String service = ebxml.getService();
        SIMULATION_COUNT = SIMULATION_COUNT + 1;
        int type = SIMULATION_COUNT % 3;
        String action = VT1Constants.VT104;
        if (type == 2) {
            action = VT1Constants.VT102;
        } else if (type == 0) {
            action = VT1Constants.VT103;
        }
        OrchestraEbxmlMessage ebxmlResponse = new OrchestraEbxmlMessage(fromPartyId, toPartyId, conversationId, service, action);

        DOCUMENT document = (DOCUMENT) JAXBUtil.unmarshall(ebxml.getMainContentByte(), DOCUMENT.class);
        if (document.getROUTAGE() == null) {
            document.setROUTAGE(new DOCUMENT.ROUTAGE());
        }
        document.setTYPEDOCUMENT(action);
        document.getROUTAGE().setEMETTEUR(fromPartyId);
        document.getROUTAGE().setDESTINATAIRE(toPartyId);

        String contentId = UUID.randomUUID().toString();
        DataHandler dataHandler = new DataHandler(new ByteArrayDataSource(JAXBUtil.marshall(document, true)));
        ebxmlResponse.addPayloadContainer(dataHandler, contentId, null);

        try {
            ebxml.addAckRequested(false);
        } catch (Exception e) {
        }
        OrchestraEbxmlMessage ack = OrchestraSignalMessageGenerator.generateAcknowledgmentAction(ebxml, OrchestraMessageClassifier.ACTION_APERAK_K);
        File ackFile = new File(ApplicationManager.getInstance().getAppConfig().getIncommingMessageDirectory(), "APERAK_K." + System.currentTimeMillis() + "." + ebxml.getConversationId() + ".xml");
        OutputStream ackOut = new FileOutputStream(ackFile);
        ack.writeTo(ackOut);
        ackOut.close();
        try {
            Thread.sleep(10 * 1000);
        } catch (Exception e) {
        }

        File file = new File(ApplicationManager.getInstance().getAppConfig().getIncommingMessageDirectory(), action + "." + System.currentTimeMillis() + "." + ebxml.getConversationId() + ".xml");
//        File file = new File(ApplicationManager.getInstance().getAppConfig().getUntransformedMessageDirectory(), action + "." + System.currentTimeMillis() + "." + ebxml.getConversationId() + ".xml");
        OutputStream out = new FileOutputStream(file);
        ebxmlResponse.writeTo(out);
        out.close();
        try {
            Thread.sleep(10 * 1000);
        } catch (Exception e) {
            
        }

    }

    @Override
    public Object getEndpoint(Process process, Partner partner,
            String direction, HashMap<String, Object> params) {
        Object id = null;
        if (direction.equals(Endpoint.OUTBOUND)) {
            String partnerListProperty = "vt1.partner.siat.list";
            String groupListProperty = "vt1.partner_group.siat.list";
            String endpointProperty = "vt1.endpoint.siat";
            String epId = this.getEndpointFromParams(process, partner, partnerListProperty, groupListProperty, endpointProperty);
            if (StringUtils.isBlank(epId)) {
                partnerListProperty = "vt1.partner.web.list";
                groupListProperty = "vt1.partner_group.web.list";
                endpointProperty = "vt1.endpoint.web";
                epId = this.getEndpointFromParams(process, partner, partnerListProperty, groupListProperty, endpointProperty);
            }
            id = epId;
        }
        if (id == null) {
            id = super.getEndpointId(process, partner, direction, params);
        }
        return id;
    }

    @Override
    public Object execute(ProcessAction action) throws Exception {
        return super.execute(action);
    }
}
