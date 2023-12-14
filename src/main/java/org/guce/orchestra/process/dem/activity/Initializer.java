package org.guce.orchestra.process.dem.activity;

import org.guce.orchestra.core.OrchestraEbxmlMessage;
import org.guce.orchestra.process.dem.Request;

public class Initializer {

    public Request init(OrchestraEbxmlMessage ebxmlMessage) throws Exception {
        OrchestraEbxmlMessage ebxml = ebxmlMessage;
        Request request = new Request(ebxml);
        return request;
    }

}
