<xsl:stylesheet exclude-result-prefixes="xs" version="1.0"
    xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output encoding="UTF-8" indent="yes" method="xml"/>
    <xsl:param name="param_document_code"/>
    <xsl:param name="param_message_type_code"/>
    <xsl:param name="param_reference_guce"/>
    <xsl:param name="param_type_facture"/>
    <xsl:template match="/">
        <DOCUMENT>
            <xsl:attribute name="xsi:noNamespaceSchemaLocation" namespace="http://www.w3.org/2001/XMLSchema-instance">C:/Users/LISSOUCK/Documents/NetBeansProjects/guce/guce-ccs/src/main/resources/org/guce/orchestra/process/ccs/schemas/ccs.xsd</xsl:attribute>
            <xsl:for-each select="DOCUMENT">
                <xsl:variable name="var1_resultof_first" select="MESSAGE"/>
                <xsl:variable name="var2_resultof_first" select="REFERENCE_DOSSIER"/>
                <xsl:variable name="var3_resultof_first" select="ROUTAGE"/>
                <TYPE_DOCUMENT>
                    <xsl:value-of select="$param_document_code"/>
                </TYPE_DOCUMENT>
                <MESSAGE>
                    <TYPE_MESSAGE>
                        <xsl:value-of select="$param_message_type_code"/>
                    </TYPE_MESSAGE>
                    <DATE_EMISSION>
                        <xsl:value-of select="string($var1_resultof_first/DATE_EMISSION)"/>
                    </DATE_EMISSION>
                    <ETAT>
                        <xsl:value-of select="string($var1_resultof_first/ETAT)"/>
                    </ETAT>
                    <NUMERO_MESSAGE>
                        <xsl:value-of select="string($var1_resultof_first/NUMERO_MESSAGE)"/>
                    </NUMERO_MESSAGE>
                    <NUMERO_MESSAGE_ORIGINE>
                        <xsl:value-of select="string($var1_resultof_first/NUMERO_MESSAGE_ORIGINE)"/>
                    </NUMERO_MESSAGE_ORIGINE>
                    <DATE_EMISSION_MSG_ORIGINE>
                        <xsl:value-of select="string($var1_resultof_first/DATE_EMISSION_MSG_ORIGINE)"/>
                    </DATE_EMISSION_MSG_ORIGINE>
                </MESSAGE>
                <REFERENCE_DOSSIER>
                    <NUMERO_DOSSIER>
                        <xsl:value-of select="string($var2_resultof_first/NUMERO_DOSSIER)"/>
                    </NUMERO_DOSSIER>
                    <NUMERO_DEMANDE>
                        <xsl:value-of select="string($var2_resultof_first/NUMERO_DEMANDE)"/>
                    </NUMERO_DEMANDE>
                    <SERVICE>
                        <xsl:value-of select="string($var2_resultof_first/SERVICE)"/>
                    </SERVICE>
                    <REFERENCE_GUCE>
                        <xsl:value-of select="$param_type_facture"/>
                    </REFERENCE_GUCE>
                    <REFERENCE_SIAT>
                        <xsl:value-of select="string($var2_resultof_first/REFERENCE_SIAT)"/>
                    </REFERENCE_SIAT>
                    <DATE_CREATION>
                        <xsl:value-of select="string($var2_resultof_first/DATE_CREATION)"/>
                    </DATE_CREATION>
                    <SI>
                        <xsl:value-of select="string($var2_resultof_first/SI)"/>
                    </SI>
                </REFERENCE_DOSSIER>
                <ROUTAGE>
                    <EMETTEUR>
                        <xsl:value-of select="string($var3_resultof_first/EMETTEUR)"/>
                    </EMETTEUR>
                    <DESTINATAIRE>
                        <xsl:value-of select="string($var3_resultof_first/DESTINATAIRE)"/>
                    </DESTINATAIRE>
                </ROUTAGE>
                <xsl:for-each select="ERREURS">
                    <ERREURS>
                        <xsl:for-each select="ERREUR">
                            <ERREUR>
                                <xsl:for-each select="CODE_ERREUR">
                                    <CODE_ERREUR>
                                    <xsl:value-of select="string(.)"/>
                                    </CODE_ERREUR>
                                </xsl:for-each>
                                <xsl:for-each select="LIBELLE_ERREUR">
                                    <LIBELLE_ERREUR>
                                    <xsl:value-of select="string(.)"/>
                                    </LIBELLE_ERREUR>
                                </xsl:for-each>
                                <xsl:for-each select="REFERENCE_DONNEE">
                                    <REFERENCE_DONNEE>
                                    <xsl:value-of select="string(.)"/>
                                    </REFERENCE_DONNEE>
                                </xsl:for-each>
                            </ERREUR>
                        </xsl:for-each>
                    </ERREURS>
                </xsl:for-each>
                <xsl:for-each select="CONTENT">
                    <CONTENT>
                        <xsl:for-each select="CODE_BUREAU">
                            <CODE_BUREAU>
                                <xsl:value-of select="string(.)"/>
                            </CODE_BUREAU>
                        </xsl:for-each>
                        <xsl:for-each select="NUMERO_DEM_MINCOMMERCE">
                            <NUMERO_DEM_MINCOMMERCE>
                                <xsl:value-of select="string(.)"/>
                            </NUMERO_DEM_MINCOMMERCE>
                        </xsl:for-each>
                        <xsl:for-each select="DATE_DEM_MINCOMMERCE">
                            <DATE_DEM_MINCOMMERCE>
                                <xsl:value-of select="string(.)"/>
                            </DATE_DEM_MINCOMMERCE>
                        </xsl:for-each>
                        <xsl:for-each select="EXP_DATE_MIN">
                            <EXP_DATE_MIN>
                                <xsl:value-of select="string(.)"/>
                            </EXP_DATE_MIN>
                        </xsl:for-each>
                        
                         <xsl:for-each select="OPERATOR_NAME">
                                    <OPERATOR_NAME>
                                    <xsl:value-of select="string(.)"/>
                                    </OPERATOR_NAME>
                          </xsl:for-each>
                         
                         
                         

                         <xsl:for-each select="DECLARATIONS">
                            <DECLARATIONS>
                                <xsl:for-each select="DECLARATION">
                                    <DECLARATION>
                                        <xsl:for-each select="DEM_DECLARATION">
                                               <DEM_DECLARATION>
                                                 <xsl:value-of select="string(.)"/>
                                             </DEM_DECLARATION>
                                      </xsl:for-each>
                                  </DECLARATION>
                             </xsl:for-each>
                         </DECLARATIONS>
                      </xsl:for-each>
                      
                      
                       <xsl:for-each select="DECLARATIONS">
                            <DECLARATIONS>
                                <xsl:for-each select="DECLARATION">
                                    <DECLARATION>
                                        <xsl:for-each select="DEM_DECLARATION">
                                               <DEM_DECLARATION>
                                                 <xsl:value-of select="string(.)"/>
                                             </DEM_DECLARATION>
                                      </xsl:for-each>
                                  </DECLARATION>
                             </xsl:for-each>
                         </DECLARATIONS>
                      </xsl:for-each>
                        
                        
                        
                        <xsl:for-each select="CLIENT">
                            <CLIENT>
                                <xsl:for-each select="NUMERO_CONTRIBUABLE">
                                    <NUMERO_CONTRIBUABLE>
                                    <xsl:value-of select="string(.)"/>
                                    </NUMERO_CONTRIBUABLE>
                                </xsl:for-each>
                                <xsl:for-each select="RAISON_SOCIALE">
                                    <RAISON_SOCIALE>
                                    <xsl:value-of select="string(.)"/>
                                    </RAISON_SOCIALE>
                                </xsl:for-each>
                                <xsl:for-each select="AGREMENT_COMMERCE">
                                    <AGREMENT_COMMERCE>
                                    <xsl:for-each select="NUMERO_INSCRIPTION_FICHIER">
                                    <NUMERO_INSCRIPTION_FICHIER>
                                    <xsl:value-of select="string(.)"/>
                                    </NUMERO_INSCRIPTION_FICHIER>
                                    </xsl:for-each>
                                    <xsl:for-each select="DATE_OBTENTION">
                                    <DATE_OBTENTION>
                                    <xsl:value-of select="string(.)"/>
                                    </DATE_OBTENTION>
                                    </xsl:for-each>
                                    <xsl:for-each select="DATE_VALIDITE">
                                    <DATE_VALIDITE>
                                    <xsl:value-of select="string(.)"/>
                                    </DATE_VALIDITE>
                                    </xsl:for-each>
                                    <xsl:for-each select="NUMERO_CARTE_COMMERCANT">
                                    <NUMERO_CARTE_COMMERCANT>
                                    <xsl:value-of select="string(.)"/>
                                    </NUMERO_CARTE_COMMERCANT>
                                    </xsl:for-each>
                                    </AGREMENT_COMMERCE>
                                </xsl:for-each>
                                <xsl:for-each select="AGREMENT_METIER">
                                    <AGREMENT_METIER>
                                    <xsl:for-each select="TYPE_AGREMENT">
                                    <TYPE_AGREMENT>
                                    <xsl:value-of select="string(.)"/>
                                    </TYPE_AGREMENT>
                                    </xsl:for-each>
                                    <xsl:for-each select="NUMERO_AGREMENT">
                                    <NUMERO_AGREMENT>
                                    <xsl:value-of select="string(.)"/>
                                    </NUMERO_AGREMENT>
                                    </xsl:for-each>
                                    <xsl:for-each select="DATE_AGREMENT">
                                    <DATE_AGREMENT>
                                    <xsl:value-of select="string(.)"/>
                                    </DATE_AGREMENT>
                                    </xsl:for-each>
                                    <xsl:for-each select="DATE_VALIDITE">
                                    <DATE_VALIDITE>
                                    <xsl:value-of select="string(.)"/>
                                    </DATE_VALIDITE>
                                    </xsl:for-each>
                                    </AGREMENT_METIER>
                                </xsl:for-each>
                                <xsl:for-each select="PERMIS">
                                    <PERMIS>
                                    <xsl:for-each select="NUMERO_PERMIS">
                                    <NUMERO_PERMIS>
                                    <xsl:value-of select="string(.)"/>
                                    </NUMERO_PERMIS>
                                    </xsl:for-each>
                                    <xsl:for-each select="DATE_OBTENTION">
                                    <DATE_OBTENTION>
                                    <xsl:value-of select="string(.)"/>
                                    </DATE_OBTENTION>
                                    </xsl:for-each>
                                    <xsl:for-each select="DATE_VALIDITE">
                                    <DATE_VALIDITE>
                                    <xsl:value-of select="string(.)"/>
                                    </DATE_VALIDITE>
                                    </xsl:for-each>
                                    <xsl:for-each select="TYPE">
                                    <TYPE>
                                    <xsl:value-of select="string(.)"/>
                                    </TYPE>
                                    </xsl:for-each>
                                    </PERMIS>
                                </xsl:for-each>
                                <xsl:for-each select="PROFESSION">
                                    <PROFESSION>
                                    <xsl:value-of select="string(.)"/>
                                    </PROFESSION>
                                </xsl:for-each>
                                <xsl:for-each select="NUMERO_REGISTRE_COMMERCE">
                                    <NUMERO_REGISTRE_COMMERCE>
                                    <xsl:value-of select="string(.)"/>
                                    </NUMERO_REGISTRE_COMMERCE>
                                </xsl:for-each>
                                <xsl:for-each select="CNI">
                                    <CNI>
                                    <xsl:value-of select="string(.)"/>
                                    </CNI>
                                </xsl:for-each>
                                <xsl:for-each select="ADRESSE">
                                    <ADRESSE>
                                    <xsl:for-each select="ADRESSE1">
                                    <ADRESSE1>
                                    <xsl:value-of select="string(.)"/>
                                    </ADRESSE1>
                                    </xsl:for-each>
                                    <xsl:for-each select="ADRESSE2">
                                    <ADRESSE2>
                                    <xsl:value-of select="string(.)"/>
                                    </ADRESSE2>
                                    </xsl:for-each>
                                    <xsl:for-each select="BP">
                                    <BP>
                                    <xsl:value-of select="string(.)"/>
                                    </BP>
                                    </xsl:for-each>
                                    <xsl:for-each select="PAYS_ADRESSE">
                                    <PAYS_ADRESSE>
                                    <xsl:for-each select="CODE_PAYS">
                                    <CODE_PAYS>
                                    <xsl:value-of select="string(.)"/>
                                    </CODE_PAYS>
                                    </xsl:for-each>
                                    <xsl:for-each select="NOM_PAYS">
                                    <NOM_PAYS>
                                    <xsl:value-of select="string(.)"/>
                                    </NOM_PAYS>
                                    </xsl:for-each>
                                    </PAYS_ADRESSE>
                                    </xsl:for-each>
                                    <xsl:for-each select="VILLE">
                                    <VILLE>
                                    <xsl:value-of select="string(.)"/>
                                    </VILLE>
                                    </xsl:for-each>
                                    <xsl:for-each select="EMAIL">
                                    <EMAIL>
                                    <xsl:value-of select="string(.)"/>
                                    </EMAIL>
                                    </xsl:for-each>
                                    <xsl:for-each select="SITE_WEB">
                                    <SITE_WEB>
                                    <xsl:value-of select="string(.)"/>
                                    </SITE_WEB>
                                    </xsl:for-each>
                                    </ADRESSE>
                                </xsl:for-each>
                                <xsl:for-each select="TELEPHONE_FIXE">
                                    <TELEPHONE_FIXE>
                                    <xsl:for-each select="INDICATIF_PAYS">
                                    <INDICATIF_PAYS>
                                    <xsl:value-of select="string(.)"/>
                                    </INDICATIF_PAYS>
                                    </xsl:for-each>
                                    <xsl:for-each select="NUMERO">
                                    <NUMERO>
                                    <xsl:value-of select="string(.)"/>
                                    </NUMERO>
                                    </xsl:for-each>
                                    </TELEPHONE_FIXE>
                                </xsl:for-each>
                                <xsl:for-each select="TELEPHONE_MOBILE">
                                    <TELEPHONE_MOBILE>
                                    <xsl:for-each select="INDICATIF_PAYS">
                                    <INDICATIF_PAYS>
                                    <xsl:value-of select="string(.)"/>
                                    </INDICATIF_PAYS>
                                    </xsl:for-each>
                                    <xsl:for-each select="NUMERO">
                                    <NUMERO>
                                    <xsl:value-of select="string(.)"/>
                                    </NUMERO>
                                    </xsl:for-each>
                                    </TELEPHONE_MOBILE>
                                </xsl:for-each>
                                <xsl:for-each select="FAX">
                                    <FAX>
                                    <xsl:for-each select="INDICATIF_PAYS">
                                    <INDICATIF_PAYS>
                                    <xsl:value-of select="string(.)"/>
                                    </INDICATIF_PAYS>
                                    </xsl:for-each>
                                    <xsl:for-each select="NUMERO">
                                    <NUMERO>
                                    <xsl:value-of select="string(.)"/>
                                    </NUMERO>
                                    </xsl:for-each>
                                    </FAX>
                                </xsl:for-each>
                            </CLIENT>
                        </xsl:for-each>
                        <xsl:for-each select="MARCHANDISES">
                            <MARCHANDISES>
                                <xsl:for-each select="MARCHANDISE">
                                    <MARCHANDISE>
                                    <xsl:for-each select="AMM">
                                    <AMM>
                                    <xsl:value-of select="string(.)"/>
                                    </AMM>
                                    </xsl:for-each>
                                    <xsl:for-each select="DESCRIPTION">
                                    <DESCRIPTION>
                                    <xsl:value-of select="string(.)"/>
                                    </DESCRIPTION>
                                    </xsl:for-each>
                                    <xsl:for-each select="VALEUR_FOB_DEVISE">
                                    <VALEUR_FOB_DEVISE>
                                    <xsl:value-of select="string(number(string(.)))"/>
                                    </VALEUR_FOB_DEVISE>
                                    </xsl:for-each>
                                    <xsl:for-each select="LINE_NUMBER">
                                    <LINE_NUMBER>
                                    <xsl:value-of select="string(floor(number(string(.))))"/>
                                    </LINE_NUMBER>
                                    </xsl:for-each>
                                    <xsl:for-each select="MODE_EMBALAGE">
                                    <MODE_EMBALAGE>
                                    <xsl:value-of select="string(.)"/>
                                    </MODE_EMBALAGE>
                                    </xsl:for-each>
                                    <xsl:for-each select="NOMBRE_COLIS">
                                    <NOMBRE_COLIS>
                                    <xsl:value-of select="string(floor(number(string(.))))"/>
                                    </NOMBRE_COLIS>
                                    </xsl:for-each>
                                    <xsl:for-each select="QUANTITE">
                                    <QUANTITE>
                                    <xsl:value-of select="string(number(string(.)))"/>
                                    </QUANTITE>
                                    </xsl:for-each>
                                    <xsl:for-each select="NOM_COMMERCIALE">
                                    <NOM_COMMERCIALE>
                                    <xsl:value-of select="string(.)"/>
                                    </NOM_COMMERCIALE>
                                    </xsl:for-each>
                                    <xsl:for-each select="UNITE">
                                    <UNITE>
                                    <xsl:value-of select="string(.)"/>
                                    </UNITE>
                                    </xsl:for-each>
                                    <xsl:for-each select="VALEUR_CFA">
                                    <VALEUR_CFA>
                                    <xsl:value-of select="string(number(string(.)))"/>
                                    </VALEUR_CFA>
                                    </xsl:for-each>
                                    <xsl:for-each select="VOLUME">
                                    <VOLUME>
                                    <xsl:value-of select="string(number(string(.)))"/>
                                    </VOLUME>
                                    </xsl:for-each>
                                    <xsl:for-each select="POIDS">
                                    <POIDS>
                                    <xsl:value-of select="string(number(string(.)))"/>
                                    </POIDS>
                                    </xsl:for-each>
                                    <xsl:for-each select="CODE_TARIF">
                                    <CODE_TARIF>
                                    <xsl:for-each select="CODE_NSH">
                                    <CODE_NSH>
                                    <xsl:value-of select="string(.)"/>
                                    </CODE_NSH>
                                    </xsl:for-each>
                                    <xsl:for-each select="DESIGNATION">
                                    <DESIGNATION>
                                    <xsl:value-of select="string(.)"/>
                                    </DESIGNATION>
                                    </xsl:for-each>
                                    </CODE_TARIF>
                                    </xsl:for-each>
                                    </MARCHANDISE>
                                </xsl:for-each>
                            </MARCHANDISES>
                        </xsl:for-each>
                        <xsl:for-each select="PAIEMENT">
                            <PAIEMENT>
                                <xsl:for-each select="FACTURE">
                                    <FACTURE>
                                    <xsl:for-each select="REFERENCE_FACTURE">
                                    <REFERENCE_FACTURE>
                                    <xsl:value-of select="string(.)"/>
                                    </REFERENCE_FACTURE>
                                    </xsl:for-each>
                                    <xsl:for-each select="DATE_FACTURATION">
                                    <DATE_FACTURATION>
                                    <xsl:value-of select="string(.)"/>
                                    </DATE_FACTURATION>
                                    </xsl:for-each>
                                    <xsl:for-each select="DETAIL_FACTURES">
                                    <DETAIL_FACTURES>
                                    <xsl:for-each select="DETAIL_FACTURE">
                                    <DETAIL_FACTURE>
                                    <xsl:for-each select="NUMERO_LIGNE">
                                    <NUMERO_LIGNE>

                                    <xsl:value-of select="string(.)"/>
                                    </NUMERO_LIGNE>
                                    </xsl:for-each>
                                    <xsl:for-each select="CODE_ARTICLE">
                                    <CODE_ARTICLE>

                                    <xsl:value-of select="string(.)"/>
                                    </CODE_ARTICLE>
                                    </xsl:for-each>
                                    <xsl:for-each select="LIBELLE_ARTICLE">
                                    <LIBELLE_ARTICLE>

                                    <xsl:value-of select="string(.)"/>
                                    </LIBELLE_ARTICLE>
                                    </xsl:for-each>
                                    <xsl:for-each select="MONTANT_HT">
                                    <MONTANT_HT>

                                    <xsl:value-of select="string(.)"/>
                                    </MONTANT_HT>
                                    </xsl:for-each>
                                    <xsl:for-each select="MONTANT_TVA">
                                    <MONTANT_TVA>

                                    <xsl:value-of select="string(.)"/>
                                    </MONTANT_TVA>
                                    </xsl:for-each>
                                    <xsl:for-each select="MONTANT_TTC">
                                    <MONTANT_TTC>

                                    <xsl:value-of select="string(.)"/>
                                    </MONTANT_TTC>
                                    </xsl:for-each>
                                    </DETAIL_FACTURE>
                                    </xsl:for-each>
                                    </DETAIL_FACTURES>
                                    </xsl:for-each>
                                    <xsl:for-each select="MONTANT_HT">
                                    <MONTANT_HT>
                                    <xsl:value-of select="string(.)"/>
                                    </MONTANT_HT>
                                    </xsl:for-each>
                                    <xsl:for-each select="MONTANT_TVA">
                                    <MONTANT_TVA>
                                    <xsl:value-of select="string(.)"/>
                                    </MONTANT_TVA>
                                    </xsl:for-each>
                                    <xsl:for-each select="MONTANT_TTC">
                                    <MONTANT_TTC>
                                    <xsl:value-of select="string(.)"/>
                                    </MONTANT_TTC>
                                    </xsl:for-each>
                                    <xsl:for-each select="AUTRE_MONTANT">
                                    <AUTRE_MONTANT>
                                    <xsl:value-of select="string(.)"/>
                                    </AUTRE_MONTANT>
                                    </xsl:for-each>
                                    <xsl:for-each select="TYPE_FACTURE">
                                    <TYPE_FACTURE>
                                       <xsl:value-of select="$param_type_facture"/>
                                    </TYPE_FACTURE>
                                    </xsl:for-each>
                                    </FACTURE>
                                </xsl:for-each>
                                <xsl:for-each select="SIGNATAIRE">
                                    <SIGNATAIRE>
                                    <xsl:for-each select="NOM">
                                    <NOM>
                                    <xsl:value-of select="string(.)"/>
                                    </NOM>
                                    </xsl:for-each>
                                    <xsl:for-each select="QUALITE">
                                    <QUALITE>
                                    <xsl:value-of select="string(.)"/>
                                    </QUALITE>
                                    </xsl:for-each>
                                    <xsl:for-each select="LIEU">
                                    <LIEU>
                                    <xsl:value-of select="string(.)"/>
                                    </LIEU>
                                    </xsl:for-each>
                                    <xsl:for-each select="DATE">
                                    <DATE>
                                    <xsl:value-of select="string(.)"/>
                                    </DATE>
                                    </xsl:for-each>
                                    <xsl:for-each select="SOCIETE">
                                    <SOCIETE>
                                    <xsl:value-of select="string(.)"/>
                                    </SOCIETE>
                                    </xsl:for-each>
                                    </SIGNATAIRE>
                                </xsl:for-each>
                                <xsl:for-each select="ENCAISSEMENT">
                                    <ENCAISSEMENT>
                                    <xsl:for-each select="NUMERO_RECU">
                                    <NUMERO_RECU>
                                    <xsl:value-of select="string(.)"/>
                                    </NUMERO_RECU>
                                    </xsl:for-each>
                                    <xsl:for-each select="NATURE">
                                    <NATURE>
                                    <xsl:value-of select="string(.)"/>
                                    </NATURE>
                                    </xsl:for-each>
                                    <xsl:for-each select="DATE_ENCAISSEMENT">
                                    <DATE_ENCAISSEMENT>
                                    <xsl:value-of select="string(.)"/>
                                    </DATE_ENCAISSEMENT>
                                    </xsl:for-each>
                                    <xsl:for-each select="MONTANT">
                                    <MONTANT>
                                    <xsl:value-of select="string(.)"/>
                                    </MONTANT>
                                    </xsl:for-each>
                                    <xsl:for-each select="OBSERVATIONS">
                                    <OBSERVATIONS>
                                    <xsl:value-of select="string(.)"/>
                                    </OBSERVATIONS>
                                    </xsl:for-each>
                                    <xsl:for-each select="CANAL_ENCAISSEMENT">
                                    <CANAL_ENCAISSEMENT>
                                    <xsl:value-of select="string(.)"/>
                                    </CANAL_ENCAISSEMENT>
                                    </xsl:for-each>
                                    </ENCAISSEMENT>
                                </xsl:for-each>
                                <xsl:for-each select="FICHE_RECETTE">
                                    <FICHE_RECETTE>
                                    <xsl:for-each select="NUMERO_ROLE">
                                    <NUMERO_ROLE>
                                    <xsl:value-of select="string(.)"/>
                                    </NUMERO_ROLE>
                                    </xsl:for-each>
                                    <xsl:for-each select="IMPUTATION">
                                    <IMPUTATION>
                                    <xsl:value-of select="string(.)"/>
                                    </IMPUTATION>
                                    </xsl:for-each>
                                    <xsl:for-each select="ARTICLE">
                                    <ARTICLE>
                                    <xsl:value-of select="string(.)"/>
                                    </ARTICLE>
                                    </xsl:for-each>
                                    <xsl:for-each select="NUMERO_QUITTANCE">
                                    <NUMERO_QUITTANCE>
                                    <xsl:value-of select="string(.)"/>
                                    </NUMERO_QUITTANCE>
                                    </xsl:for-each>
                                    <xsl:for-each select="NATURE_RECETTE_RUBRIQUE">
                                    <NATURE_RECETTE_RUBRIQUE>
                                    <xsl:value-of select="string(.)"/>
                                    </NATURE_RECETTE_RUBRIQUE>
                                    </xsl:for-each>
                                    </FICHE_RECETTE>
                                </xsl:for-each>
                                <xsl:for-each select="PARTIE_VERSANTE">
                                    <PARTIE_VERSANTE>
                                    <xsl:for-each select="NUMERO_CONTRIBUABLE">
                                    <NUMERO_CONTRIBUABLE>
                                    <xsl:value-of select="string(.)"/>
                                    </NUMERO_CONTRIBUABLE>
                                    </xsl:for-each>
                                    <xsl:for-each select="RAISON_SOCIALE">
                                    <RAISON_SOCIALE>
                                    <xsl:value-of select="string(.)"/>
                                    </RAISON_SOCIALE>
                                    </xsl:for-each>
                                    </PARTIE_VERSANTE>
                                </xsl:for-each>
                                <xsl:for-each select="CHARGEUR">
                                    <CHARGEUR>
                                    <xsl:for-each select="NUMERO_CONTRIBUABLE">
                                    <NUMERO_CONTRIBUABLE>
                                    <xsl:value-of select="string(.)"/>
                                    </NUMERO_CONTRIBUABLE>
                                    </xsl:for-each>
                                    <xsl:for-each select="RAISON_SOCIALE">
                                    <RAISON_SOCIALE>
                                    <xsl:value-of select="string(.)"/>
                                    </RAISON_SOCIALE>
                                    </xsl:for-each>
                                    <xsl:for-each select="ADRESSE">
                                    <ADRESSE>
                                    <xsl:for-each select="ADRESSE1">
                                    <ADRESSE1>
                                    <xsl:value-of select="string(.)"/>
                                    </ADRESSE1>
                                    </xsl:for-each>
                                    <xsl:for-each select="ADRESSE2">
                                    <ADRESSE2>
                                    <xsl:value-of select="string(.)"/>
                                    </ADRESSE2>
                                    </xsl:for-each>
                                    <xsl:for-each select="BP">
                                    <BP>
                                    <xsl:value-of select="string(.)"/>
                                    </BP>
                                    </xsl:for-each>
                                    <xsl:for-each select="PAYS_ADRESSE">
                                    <PAYS_ADRESSE>
                                    <xsl:for-each select="CODE_PAYS">
                                    <CODE_PAYS>

                                    <xsl:value-of select="string(.)"/>
                                    </CODE_PAYS>
                                    </xsl:for-each>
                                    <xsl:for-each select="NOM_PAYS">
                                    <NOM_PAYS>

                                    <xsl:value-of select="string(.)"/>
                                    </NOM_PAYS>
                                    </xsl:for-each>
                                    </PAYS_ADRESSE>
                                    </xsl:for-each>
                                    <xsl:for-each select="VILLE">
                                    <VILLE>
                                    <xsl:value-of select="string(.)"/>
                                    </VILLE>
                                    </xsl:for-each>
                                    <xsl:for-each select="EMAIL">
                                    <EMAIL>
                                    <xsl:value-of select="string(.)"/>
                                    </EMAIL>
                                    </xsl:for-each>
                                    <xsl:for-each select="SITE_WEB">
                                    <SITE_WEB>
                                    <xsl:value-of select="string(.)"/>
                                    </SITE_WEB>
                                    </xsl:for-each>
                                    </ADRESSE>
                                    </xsl:for-each>
                                    <xsl:for-each select="TELEPHONE_FIXE">
                                    <TELEPHONE_FIXE>
                                    <xsl:for-each select="INDICATIF_PAYS">
                                    <INDICATIF_PAYS>
                                    <xsl:value-of select="string(.)"/>
                                    </INDICATIF_PAYS>
                                    </xsl:for-each>
                                    <xsl:for-each select="NUMERO">
                                    <NUMERO>
                                    <xsl:value-of select="string(.)"/>
                                    </NUMERO>
                                    </xsl:for-each>
                                    </TELEPHONE_FIXE>
                                    </xsl:for-each>
                                    <xsl:for-each select="TELEPHONE_MOBILE">
                                    <TELEPHONE_MOBILE>
                                    <xsl:for-each select="INDICATIF_PAYS">
                                    <INDICATIF_PAYS>
                                    <xsl:value-of select="string(.)"/>
                                    </INDICATIF_PAYS>
                                    </xsl:for-each>
                                    <xsl:for-each select="NUMERO">
                                    <NUMERO>
                                    <xsl:value-of select="string(.)"/>
                                    </NUMERO>
                                    </xsl:for-each>
                                    </TELEPHONE_MOBILE>
                                    </xsl:for-each>
                                    <xsl:for-each select="FAX">
                                    <FAX>
                                    <xsl:for-each select="INDICATIF_PAYS">
                                    <INDICATIF_PAYS>
                                    <xsl:value-of select="string(.)"/>
                                    </INDICATIF_PAYS>
                                    </xsl:for-each>
                                    <xsl:for-each select="NUMERO">
                                    <NUMERO>
                                    <xsl:value-of select="string(.)"/>
                                    </NUMERO>
                                    </xsl:for-each>
                                    </FAX>
                                    </xsl:for-each>
                                    </CHARGEUR>
                                </xsl:for-each>
                                <xsl:for-each select="BENEFICIAIRE">
                                    <BENEFICIAIRE>
                                    <xsl:for-each select="CODE">
                                    <CODE>
                                    <xsl:value-of select="string(.)"/>
                                    </CODE>
                                    </xsl:for-each>
                                    <xsl:for-each select="LIBELLE">
                                    <LIBELLE>
                                    <xsl:value-of select="string(.)"/>
                                    </LIBELLE>
                                    </xsl:for-each>
                                    </BENEFICIAIRE>
                                </xsl:for-each>
                            </PAIEMENT>
                        </xsl:for-each>
                        <xsl:for-each select="SIGNATAIRE">
                            <SIGNATAIRE>
                                <xsl:for-each select="NOM">
                                    <NOM>
                                    <xsl:value-of select="string(.)"/>
                                    </NOM>
                                </xsl:for-each>
                                <xsl:for-each select="QUALITE">
                                    <QUALITE>
                                    <xsl:value-of select="string(.)"/>
                                    </QUALITE>
                                </xsl:for-each>
                                <xsl:for-each select="LIEU">
                                    <LIEU>
                                    <xsl:value-of select="string(.)"/>
                                    </LIEU>
                                </xsl:for-each>
                                <xsl:for-each select="DATE">
                                    <DATE>
                                    <xsl:value-of select="string(.)"/>
                                    </DATE>
                                </xsl:for-each>
                            </SIGNATAIRE>
                        </xsl:for-each>
                        <xsl:for-each select="DECISION_ORGANISME">
                            <DECISION_ORGANISME>
                                <xsl:for-each select="CODE">
                                    <CODE>
                                    <xsl:value-of select="string(.)"/>
                                    </CODE>
                                </xsl:for-each>
                                <xsl:for-each select="LIBELLE">
                                    <LIBELLE>
                                    <xsl:value-of select="string(.)"/>
                                    </LIBELLE>
                                </xsl:for-each>
                                <xsl:for-each select="OBSERVATION">
                                    <OBSERVATION>
                                    <xsl:value-of select="string(.)"/>
                                    </OBSERVATION>
                                </xsl:for-each>
                            </DECISION_ORGANISME>
                        </xsl:for-each>
                        <xsl:for-each select="PIECES_JOINTES">
                            <PIECES_JOINTES>
                                <xsl:for-each select="PIECE_JOINTE">
                                    <PIECE_JOINTE>
                                    <xsl:for-each select="TYPE_PJ">
                                    <TYPE_PJ>
                                    <xsl:value-of select="string(.)"/>
                                    </TYPE_PJ>
                                    </xsl:for-each>
                                    <xsl:for-each select="LIBELLE_PJ">
                                    <LIBELLE_PJ>
                                    <xsl:value-of select="string(.)"/>
                                    </LIBELLE_PJ>
                                    </xsl:for-each>
                                    </PIECE_JOINTE>
                                </xsl:for-each>
                            </PIECES_JOINTES>
                        </xsl:for-each>
                    </CONTENT>
                </xsl:for-each>
            </xsl:for-each>
        </DOCUMENT>
    </xsl:template>
</xsl:stylesheet>
