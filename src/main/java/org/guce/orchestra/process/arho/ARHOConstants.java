package org.guce.orchestra.process.arho;

public class ARHOConstants {
    
        /**
         * Code de la procedure
         */
        public static final String PROCESS_CODE="arho";

        /**
         * Dossier contenant les fichiers xsl pour la transformation des messages
         */
	public static final String XSL_RESSOURCE_PATH = "/xsl/";
        /**
         * Dossier contenant les xsd pour la validation des messages
         */
	
	public static final String SCHEMA_RESSOURCE_PATH = "/schemas/";

	/**
	 * Demande
	 */
	public static final String AH001 = "AH001";
        /**
         * Reponse Complement d'information
         */
	public static final String AH011 = "AH011";
	/**
	 * Rejet demande
	 */
	public static final String AH003 = "AH003";
	/**
	 * Complement d'information
	 */
	public static final String AH002 = "AH002";
	/**
	 * Accord facture
	 */
	public static final String AH004 = "AH004";
}
