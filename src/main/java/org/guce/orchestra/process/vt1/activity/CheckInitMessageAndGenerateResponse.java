/**
 *
 */
package org.guce.orchestra.process.vt1.activity;

import org.guce.orchestra.core.OrchestraEbxmlMessage;
import org.guce.orchestra.process.vt1.VT1Constants;
import org.guce.orchestra.process.vt1.Request;
import org.guce.orchestra.process.vt1.util.MessageActionValidator;

/**
 * @author Koufana Crepin
 *
 */
public class CheckInitMessageAndGenerateResponse extends CustomActivity {

    private String[] toPartyIds;

    private boolean transformBefore = false;

    public CheckInitMessageAndGenerateResponse() {

    }

    public Object execute(Request request, OrchestraEbxmlMessage ebxmlMessage) throws Exception {
        MessageActionValidator.validates(ebxmlMessage, VT1Constants.VT101, VT1Constants.VT111);
        toPartyIds = new String[]{request.getRecieverPartner()};
        request.setInitMessageId(ebxmlMessage.getMessageId());

        this.transformBefore = true;
        Object response = this.generateResponse(ebxmlMessage);
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

}
