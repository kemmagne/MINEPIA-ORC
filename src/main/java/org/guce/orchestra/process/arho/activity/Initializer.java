package org.guce.orchestra.process.arho.activity;

import org.guce.orchestra.core.OrchestraEbxmlMessage;
import org.guce.orchestra.process.arho.Request;

public class Initializer{
	
	public Request init(OrchestraEbxmlMessage ebxmlMessage) throws Exception{
		OrchestraEbxmlMessage ebxml = ebxmlMessage;
		Request request = new Request(ebxml);
                
		return request;
	}
	
}
