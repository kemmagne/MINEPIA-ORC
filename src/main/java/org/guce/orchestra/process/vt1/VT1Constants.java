package org.guce.orchestra.process.vt1;

public class VT1Constants {

     /**
         * Code de la procedure
         */
        public static final String PROCESS_CODE="vt1";

        /**
         * Dossier contenant les fichiers xsl pour la transformation des messages
         */
	public static final String XSL_RESSOURCE_PATH = "/org/guce/orchestra/process/vt1/xsl/";
        /**
         * Dossier contenant les xsd pour la validation des messages
         */
	
	public static final String SCHEMA_RESSOURCE_PATH = "/org/guce/orchestra/process/vt1/schemas/";

	/**
	 * Demande
	 */
	public static final String VT101 = "VT101";
        /**
         * Reponse Complement d'information
         */
	public static final String VT111 = "VT111";
	/**
	 * Rejet demande
	 */
	public static final String VT103 = "VT103";
	/**
	 * Complement d'information
	 */
	public static final String VT102 = "VT102";
	/**
	 * Accord facture
	 */
	public static final String VT104 = "VT104";
    
	public static final String VT1601 = "VT1601";
    
	public static final String VT1602 = "VT1602";
    
}
