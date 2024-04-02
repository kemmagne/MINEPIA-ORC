package org.guce.orchestra.process.atm;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;
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
import org.guce.orchestra.core.OrchestraMuleMessage;
import org.guce.orchestra.core.ProcessAction;
import org.guce.orchestra.core.ProcessProcessing;
import org.guce.orchestra.entity.Charger;
import org.guce.orchestra.entity.Dossier;
import org.guce.orchestra.entity.Endpoint;
import org.guce.orchestra.entity.Partner;
import org.guce.orchestra.entity.Process;
import org.mule.api.MuleMessage;
import org.w3c.dom.Document;

public class ATMProcessing extends AbstractProcessProcessing implements ProcessProcessing {

    @Override
    public String dossierNumber(OrchestraEbxmlMessage ebxmlMessage) {
        String xpathExp = "/DOCUMENT/REFERENCE_DOSSIER/NUMERO_DOSSIER/text()";
        return this.dossierNumber(ebxmlMessage, xpathExp);
    }

    @Override
    public String processingType(OrchestraEbxmlMessage ebxml, boolean isInbound, Map params) {
        String action = ebxml.getAction();
        if ((action.equals(ATMConstants.ATM01) && isInbound) || ((action.equals(ATMConstants.ATM11) && !isInbound))) {
            return ATMConstants.ATM01;
        } else if (action.equals(ATMConstants.ATM01)
                || action.equals(ATMConstants.ATM02)
                || action.equals(ATMConstants.ATM04)) {
            return ATMConstants.ATM04;
        } else if (ATMConstants.ATM09.equals(action) && isInbound) {
            return ATMConstants.ATM09;
        } else if (ATMConstants.ATM09.equals(action)
                || action.equals(ATMConstants.ATM03)
                || ATMConstants.ATM10.equals(action)) {
            return ATMConstants.ATM10;
        }

        return null;
    }
    
    protected String getChargerPath(){
        return "/DOCUMENT/CONTENT/CLIENT/";
    }

    @Override
    public Dossier newDossier(OrchestraEbxmlMessage ebxmlMessage) {

        Dossier dossier = null;

        String action = ebxmlMessage.getAction();
        if (ATMConstants.ATM01.equals(action) || ATMConstants.ATM09.equals(action)) {
            dossier = new Dossier();
            try {
                Dossier parent = this.getParent(ebxmlMessage, "/DOCUMENT/REFERENCE_DOSSIER/NUMERO_DEMANDE");
                dossier.setParent(parent);
                dossier.setFileType(Dossier.FILE_TYPE_EDI);
            } catch (Exception ex) {
                Logger.getLogger(getClass()).error(ex.getMessage(), ex);
            }
            Charger charger = new Charger();
            try {
                byte[] data = ebxmlMessage.getMainContentByte();
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = factory.newDocumentBuilder();
                Document document = db.parse(new ByteArrayInputStream(data));
                XPath xpath = XPathFactory.newInstance().newXPath();
                String nom = (String) xpath.compile(getChargerPath()+"/RAISON_SOCIALE").evaluate(document, XPathConstants.STRING);
                String niu = (String) xpath.compile(getChargerPath()+"/NUMERO_CONTRIBUABLE").evaluate(document, XPathConstants.STRING);
                String adresse1 = (String) xpath.compile(getChargerPath()+"/ADRESSE/ADRESSE1").evaluate(document, XPathConstants.STRING);
                String adresse2 = (String) xpath.compile(getChargerPath()+"/ADRESSE/ADRESSE2").evaluate(document, XPathConstants.STRING);
                String adresse3 = (String) xpath.compile(getChargerPath()+"/ADRESSE/ADRESSE3").evaluate(document, XPathConstants.STRING);
                String bp = (String) xpath.compile(getChargerPath()+"/ADRESSE/BP").evaluate(document, XPathConstants.STRING);
                String ville = (String) xpath.compile(getChargerPath()+"/ADRESSE/VILLE").evaluate(document, XPathConstants.STRING);
                String codePays = (String) xpath.compile(getChargerPath()+"/ADRESSE/PAYS_ADRESSE/CODE_PAYS").evaluate(document, XPathConstants.STRING);
                String faxCode = (String) xpath.compile(getChargerPath()+"/FAX/INDICATIF_PAYS").evaluate(document, XPathConstants.STRING);
                String faxNumber = (String) xpath.compile(getChargerPath()+"/FAX/NUMERO").evaluate(document, XPathConstants.STRING);
                String fixeCode = (String) xpath.compile(getChargerPath()+"/TELEPHONE_FIXE/INDICATIF_PAYS").evaluate(document, XPathConstants.STRING);
                String fixNumber = (String) xpath.compile(getChargerPath()+"/TELEPHONE_FIXE/NUMERO").evaluate(document, XPathConstants.STRING);
                String mobileCode = (String) xpath.compile(getChargerPath()+"/TELEPHONE_MOBILE/INDICATIF_PAYS").evaluate(document, XPathConstants.STRING);
                String mobileNumber = (String) xpath.compile(getChargerPath()+"/TELEPHONE_MOBILE/NUMERO").evaluate(document, XPathConstants.STRING);
                String email = (String) xpath.compile(getChargerPath()+"/ADRESSE/EMAIL").evaluate(document, XPathConstants.STRING);

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
                Logger.getLogger(getClass()).error(null, e);
            }

            dossier.setCharger(charger);
        }

        return dossier;
    }

    @Override
    public Object afterSended(MuleMessage muleMessage, String messageId, Map params) throws Exception {
        String stage = ApplicationManager.getInstance().getAppConfig().getDevellopmentStage();
        if (!stage.equals(ApplicationConfig.DEVELOPMENT_STAGE)) {
            return muleMessage;
        }
        return muleMessage;
    }

    @Override
    public String messageSubject(OrchestraEbxmlMessage ebxmlMessage) {
        return null;
    }

    @Override
    public Object getEndpoint(Process process, Partner partner, String direction, HashMap<String, Object> params) {

        Object id = null;

        if (direction.equals(Endpoint.OUTBOUND)) {
            String partnerListProperty = "atm.partner.siat.list";
            String groupListProperty = "atm.partner_group.siat.list";
            String endpointProperty = "atm.endpoint.siat";
            String epId = this.getEndpointFromParams(process, partner, partnerListProperty, groupListProperty, endpointProperty);
            if (StringUtils.isBlank(epId)) {
                partnerListProperty = "atm.partner.web.list";
                groupListProperty = "atm.partner_group.web.list";
                endpointProperty = "atm.endpoint.web";
                epId = this.getEndpointFromParams(process, partner, partnerListProperty, groupListProperty, endpointProperty);
            }
            id = epId;
        }

        if (id == null) {
            id = super.getEndpoint(process, partner, direction, params);
        }

        return id;
    }

    @Override
    public Object afterBPMSProcessed(MuleMessage muleMessage, String messageId, Map params) throws Exception {
        OrchestraMuleMessage orginalPayload = (OrchestraMuleMessage) muleMessage.getOriginalPayload();
        OrchestraEbxmlMessage originalEbxmlMessage = orginalPayload.getEbxmlMessage();
        return originalEbxmlMessage;
    }

    @Override
    public Object beforeBPMSProcessing(MuleMessage muleMessage, OrchestraEbxmlMessage ebxmlMessage, boolean initProcess, Map params) throws Exception {
        if (initProcess) {
            Request request = new Request(ebxmlMessage);
            muleMessage.setInvocationProperty("request", request);
        } else {
            Request request = new Request(ebxmlMessage.getDossier());
            muleMessage.setInvocationProperty("request", request);
        }
        return super.beforeBPMSProcessing(muleMessage, ebxmlMessage, initProcess, params);
    }

    @Override
    public Object execute(ProcessAction action) throws Exception {
        return super.execute(action);
    }

}
