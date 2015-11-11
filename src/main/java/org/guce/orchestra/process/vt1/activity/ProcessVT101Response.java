/**
 *
 */
package org.guce.orchestra.process.vt1.activity;

import org.guce.orchestra.core.OrchestraEbxmlMessage;
import org.guce.orchestra.process.vt1.VT1Constants;
import org.guce.orchestra.process.vt1.Request;
import org.guce.orchestra.process.vt1.util.MessageActionValidator;
import org.mule.util.StringUtils;

/**
 * @author ayefou
 *
 */
public class ProcessVT101Response extends CustomActivity {

    private String[] toPartyIds;

    private boolean transformBefore = false;

    /**
     * Liste des action attendus , separer par des virgules
     */
    private String actions;

    public ProcessVT101Response() {

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
        if (ebxmlMessage.getAction().equals(VT1Constants.VT103)) {
            toPartyIds = new String[]{request.getImportateurPartner()};
        } else if (ebxmlMessage.getAction().equals(VT1Constants.VT102)) {
            toPartyIds = new String[]{request.getImportateurPartner()};
        } else if (ebxmlMessage.getAction().equals(VT1Constants.VT104)) {
            toPartyIds = new String[]{request.getImportateurPartner()};
        } else if (ebxmlMessage.getAction().equals(VT1Constants.VT111)) {
            this.transformBefore = true;
            toPartyIds = new String[]{request.getRecieverPartner()};
        }
        Object response = this.generateResponse(ebxmlMessage);
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
