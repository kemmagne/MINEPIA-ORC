package org.guce.orchestra.process.vt1;

import hk.hku.cecid.ebms.pkg.MessageHeader.PartyId;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.guce.orchestra.core.OrchestraEbxmlMessage;
import org.guce.orchestra.core.ServiceUtility;
import org.guce.orchestra.entity.Dossier;
import org.guce.orchestra.entity.Process;
import org.guce.orchestra.util.ProcessConfigurationUtil;
import org.guce.orchestra.util.bpm.AbstractRequest;

public class Request extends AbstractRequest implements Serializable{
	
	/**
	 * Ne jamais modifiez cette variable
	 */
	private static final long serialVersionUID = 8351380533868067167L;

	public static final String IN_MESSAGE_PREFIX = "vt1.message.in.";

	public static final String INIT_MESSAGE = "vt1.message.init";
	
	public static final String OUT_MESSAGE_PREFIX = "vt1.message.out.";
	
	public Request(OrchestraEbxmlMessage ebxmlMessage) {
		super(ebxmlMessage);
	}

	@Override
	protected void init(OrchestraEbxmlMessage ebxmlMessage,Map<String, ? extends Object> params) {
		Dossier dossier= ebxmlMessage.getDossier();
		String processId = ebxmlMessage.getService();
            //ProcessConfigurationUtil pcl = ProcessConfigurationUtil.getInstance(processId );
            Process process = ServiceUtility.getProcessFacade().find(processId);
		dossier.setProperties(process.getParams());
		dossier.setProperty(IN_MESSAGE_PREFIX+ebxmlMessage.getAction(), ebxmlMessage.getMessageId());
		dossier.setProperty(INIT_MESSAGE, ebxmlMessage.getMessageId());
//		Iterator it = ebxmlMessage.getToPartyIds();
//		String interId = ((PartyId)it.next()).getId();
//		dossier.setProperty("vt1.partner.banque",interId);
	}
	
	@Override
	public String getInitMessageId() {
		return INIT_MESSAGE;
	}
	
	public Logger getLogger(){
		return Logger.getLogger(getClass());
	}
	
	public String getImportateurPartner() {
		return getDossier().getInitializer().getId();
	}
	
	public String getDouanePartner(){
		String id = getDossier().getProperty("vt1.partner.douane");
		return id;
	}
        public String getRecieverPartner(){
		String id = getDossier().getProperty("vt1.partner.receiver");
		return id;
	}
	
	
}
