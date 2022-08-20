/**
 *
 */
package org.guce.orchestra.process.vt1.activity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.guce.orchestra.core.OrchestraEbxmlMessage;
import org.guce.orchestra.dao.util.Generator;
import org.guce.orchestra.entity.Dossier;
import org.guce.orchestra.process.vt1.VT1Constants;
import org.guce.orchestra.process.vt1.Request;
import org.guce.orchestra.process.vt1.util.MessageActionValidator;
import org.guce.orchestra.util.OrchestraEbxmlFields;
import org.guce.orchestra.util.OrchestraEbxmlUtility;
import org.guce.orchestra.util.ProcessUtility;
import org.mule.util.StringUtils;

/**
 * @author ayefou
 *
 */
public class ProcessResponse extends CustomActivity {

    private String[] toPartyIds;

    private boolean transformBefore = false;

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
        Set<OrchestraEbxmlFields> fields = new HashSet<OrchestraEbxmlFields>();
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
        java.util.Map<String, Object> response = new HashMap<String, Object>();
        if (ebxmlMessage.getAction().equals(VT1Constants.VT103)) {
            toPartyIds = new String[]{request.getImportateurPartner()};
        } else if (ebxmlMessage.getAction().equals(VT1Constants.VT102)) {
            toPartyIds = new String[]{request.getImportateurPartner()};
        } else if (ebxmlMessage.getAction().equals(VT1Constants.VT104)) {
            toPartyIds = new String[]{request.getImportateurPartner()};
        } else if (ebxmlMessage.getAction().equals(VT1Constants.VT111)) {
            this.transformBefore = true;
            toPartyIds = new String[]{request.getRecieverPartner()};
        }else if (ebxmlMessage.getAction().equals(VT1Constants.VT1601)) {
            toPartyIds = new String[]{request.getRecieverPartner()};
        } else if (ebxmlMessage.getAction().equals(VT1Constants.VT1602)) {
            toPartyIds = new String[]{request.getRecieverPartner()};
        }
        response = (java.util.Map<String, Object>)  this.generateResponse(ebxmlMessage);
        if (ebxmlMessage.getAction().equals(VT1Constants.VT1601)) {            
            Set<OrchestraEbxmlFields> fields = new HashSet<OrchestraEbxmlFields>();
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
            response.put("message", message);
        }
        return response;
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
}
