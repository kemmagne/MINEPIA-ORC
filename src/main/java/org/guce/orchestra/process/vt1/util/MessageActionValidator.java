package org.guce.orchestra.process.vt1.util;

import java.util.Arrays;

import org.guce.orchestra.core.OrchestraEbxmlMessage;
import org.guce.orchestra.exception.MessageValidationException;

public class MessageActionValidator {

    public static void validate(OrchestraEbxmlMessage ebxmlMessage, String action) throws Exception {
        if (!ebxmlMessage.getAction().equals(action)) {
            throw new MessageValidationException("L'action du message attendu est " + action + " , Messare recu : " + ebxmlMessage);
        }
    }

    public static void validates(OrchestraEbxmlMessage ebxmlMessage, String... actions) throws Exception {
        boolean ok = false;
        for (String action : actions) {
            if (ebxmlMessage.getAction().equals(action)) {
                ok = true;
                break;
            }
        }
        if (!ok) {
            throw new MessageValidationException("L'action du message attendu est parmi la liste " + Arrays.toString(actions) + " , Messare recu : " + ebxmlMessage);
        }
    }
}
