package org.guce.orchestra.process.atm;

public final class ATMConstants {

    /**
     * Code de la procedure
     */
    public static final String PROCESS_CODE = "atm";

    /**
     * Dossier contenant les fichiers xsl pour la transformation des messages
     */
    public static final String XSL_RESSOURCE_PATH = "/org/guce/orchestra/process/atm/resources/xsl/";
    /**
     * Dossier contenant les xsd pour la validation des messages
     */
    public static final String SCHEMA_RESSOURCE_PATH = "/org/guce/orchestra/process/atm/resources/schemas/";

    /**
     * Demande
     */
    public static final String ATM01 = "ATM01";
    /**
     * Reponse Complement d'information
     */
    public static final String ATM11 = "ATM11";
    /**
     * Rejet demande
     */
    public static final String ATM03 = "ATM03";

     /**
     * Rejet de la demande de Modification
     */
    public static final String ATM03R = "ATM03R";
    
    /**
     * Complement d'information
     */
    public static final String ATM02 = "ATM02";
    /**
     * Accord ATM
     */
    public static final String ATM04 = "ATM04";
    /**
     * Notification de facture
     */
    public static final String ATM601 = "ATM601";
    /**
     * Notification de paiement
     */
    public static final String ATM602 = "ATM602";
    /**
     * Demande de modification
     */
    public static final String ATM09 = "ATM09";
    
    
    /**
     * Demande de renouvellement
     */
    public static final String ATM01R = "ATM01R";
    
    /**
     * Document modifie
     */
    public static final String ATM10 = "ATM10";

    /**
     * Document renouvellee
     */
    public static final String ATM10R = "ATM10R";

    public static final String INIT_ACTION_PROPERTY = "atm.init.action";

    public static final String ATM_BILL_TYPE = "process.payment.bill.type";

    private ATMConstants() {
    }

}
