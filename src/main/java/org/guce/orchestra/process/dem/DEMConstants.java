package org.guce.orchestra.process.dem;

public final class DEMConstants {

    /**
     * Code de la procedure
     */
    public static final String PROCESS_CODE = "dem";

    /**
     * Dossier contenant les fichiers xsl pour la transformation des messages
     */
    public static final String XSL_RESSOURCE_PATH = "/org/guce/orchestra/process/dem/resources/xsl/";
    /**
     * Dossier contenant les xsd pour la validation des messages
     */
    public static final String SCHEMA_RESSOURCE_PATH = "/org/guce/orchestra/process/dem/resources/schemas/";

    /**
     * Demande
     */
    public static final String DEM01 = "DEM01";
    /**
     * Reponse Complement d'information
     */
    public static final String DEM11 = "DEM11";
    /**
     * Rejet demande
     */
    public static final String DEM03 = "DEM03";
    /**
     * Complement d'information
     */
    public static final String DEM02 = "DEM02";
    /**
     * Accord DEM
     */
    public static final String DEM04 = "DEM04";
    /**
     * Notification de facture
     */
    public static final String DEM601 = "DEM601";
    /**
     * Notification de paiement
     */
    public static final String DEM602 = "DEM602";
    /**
     * Demande de modification
     */
    public static final String DEM09 = "DEM09";
    /**
     * Document modifie
     */
    public static final String DEM10 = "DEM10";

    public static final String INIT_ACTION_PROPERTY = "dem.init.action";

    public static final String DEM_BILL_TYPE = "process.payment.bill.type";

    private DEMConstants() {
    }

}
