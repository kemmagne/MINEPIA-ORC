package org.guce.orchestra.process.dem;

import java.io.Serializable;
import java.util.Map;
import org.apache.log4j.Logger;
import org.guce.orchestra.core.OrchestraEbxmlMessage;
import org.guce.orchestra.core.ServiceUtility;
import org.guce.orchestra.entity.Dossier;
import org.guce.orchestra.util.bpm.AbstractRequest;
import org.guce.orchestra.entity.Process;

public class Request extends AbstractRequest implements Serializable {

    /**
     * Ne jamais modifiez cette variable
     */
    private static final long serialVersionUID = 8351380533868067167L;

    public static final String IN_MESSAGE_PREFIX = "dem.message.in.";

    public static final String INIT_MESSAGE = "dem.message.init";

    public static final String OUT_MESSAGE_PREFIX = "dem.message.out.";

    public static final String RECORD_PAYED = "dem.recordPayed";

    public Request(OrchestraEbxmlMessage ebxmlMessage) {
        super(ebxmlMessage);
    }

    public Request(Dossier dossier) {
        super(dossier);
    }

    @Override
    protected void init(OrchestraEbxmlMessage ebxmlMessage, Map<String, ? extends Object> params) {
        Dossier dossier = ebxmlMessage.getDossier();
        dossier.setFileType(Dossier.FILE_TYPE_EDI);
        String processId = ebxmlMessage.getService();
        Process process = ServiceUtility.getProcessFacade().find(processId);
        dossier.setProperties(process.getParams());
        dossier.setProperty(IN_MESSAGE_PREFIX + ebxmlMessage.getAction(), ebxmlMessage.getMessageId());
        dossier.setProperty(INIT_MESSAGE, ebxmlMessage.getMessageId());
        dossier.setProperty(RECORD_PAYED, "false");
        dossier.setProperty(DEMConstants.INIT_ACTION_PROPERTY, ebxmlMessage.getAction());
    }

    @Override
    public String getInitMessageId() {
        String requestMessageId = getDossier().getProperty(INIT_MESSAGE);
	return requestMessageId;
    }

    public Logger getLogger() {
        return Logger.getLogger(getClass());
    }

    public String getExporterPartner() {
        return getDossier().getInitializer().getId();
    }
    public String getImportateurPartner() {
        return getDossier().getInitializer().getId();
    }

    public String getRecieverPartner() {
        String id = getDossier().getProperty("dem.partner.receiver");
        return id;
    }

    public String getRecordPayed() {
        String id = getDossier().getProperty(RECORD_PAYED);
        return id;
    }

    public String getInitAction() {
        return getDossier().getProperty(DEMConstants.INIT_ACTION_PROPERTY);
    }

}
