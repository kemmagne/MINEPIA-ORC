/**
 *
 */
package org.guce.orchestra.process.arho.activity;

import org.guce.orchestra.core.OrchestraEbxmlMessage;
import org.guce.orchestra.process.arho.ARHOConstants;
import org.guce.orchestra.process.arho.Request;
import org.guce.orchestra.process.arho.util.MessageActionValidator;

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
        MessageActionValidator.validates(ebxmlMessage, ARHOConstants.AH001, ARHOConstants.AH011);
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
