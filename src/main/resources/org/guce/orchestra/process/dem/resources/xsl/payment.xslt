<xsl:stylesheet exclude-result-prefixes="xs" version="1.0"
    xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output encoding="UTF-8" indent="yes" method="xml"/>
    <xsl:template match="/">
        <DOCUMENT>
            <xsl:attribute name="xsi:noNamespaceSchemaLocation" namespace="http://www.w3.org/2001/XMLSchema-instance">C:/Users/LISSOUCK/Documents/NetBeansProjects/guce/guce-vt1/src/main/resources/org/guce/orchestra/process/vt1/schemas/vt1.xsd</xsl:attribute>
            <xsl:for-each select="DOCUMENT">
                <xsl:variable name="var1_resultof_first" select="MESSAGE"/>
                <xsl:variable name="var2_resultof_first" select="REFERENCE_DOSSIER"/>
                <xsl:variable name="var3_resultof_first" select="ROUTAGE"/>
                <TYPE_DOCUMENT>
                    <xsl:value-of select="string(TYPE_DOCUMENT)"/>
                </TYPE_DOCUMENT>
                <MESSAGE>
                    <TYPE_MESSAGE>
                        <xsl:value-of select="string($var1_resultof_first/TYPE_MESSAGE)"/>
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
                        <xsl:value-of select="string($var2_resultof_first/REFERENCE_GUCE)"/>
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
                <CONTENT>
                    <xsl:for-each select="CONTENT/PAIEMENT">
                        <PAIEMENT>
                            <FACTURE>
                                <xsl:for-each select="FACTURE/REFERENCE_FACTURE">
                                    <REFERENCE_FACTURE>
                                    <xsl:value-of select="string(.)"/>
                                    </REFERENCE_FACTURE>
                                </xsl:for-each>
                                <xsl:for-each select="FACTURE/DATE_FACTURATION">
                                    <DATE_FACTURATION>
                                    <xsl:value-of select="string(.)"/>
                                    </DATE_FACTURATION>
                                </xsl:for-each>
                                <xsl:for-each select="FACTURE/DETAIL_FACTURES">
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
                                <xsl:for-each select="FACTURE/MONTANT_HT">
                                    <MONTANT_HT>
                                    <xsl:value-of select="string(.)"/>
                                    </MONTANT_HT>
                                </xsl:for-each>
                                <xsl:for-each select="FACTURE/MONTANT_TVA">
                                    <MONTANT_TVA>
                                    <xsl:value-of select="string(.)"/>
                                    </MONTANT_TVA>
                                </xsl:for-each>
                                <xsl:for-each select="FACTURE/MONTANT_TTC">
                                    <MONTANT_TTC>
                                    <xsl:value-of select="string(.)"/>
                                    </MONTANT_TTC>
                                </xsl:for-each>
                                <xsl:for-each select="FACTURE/AUTRE_MONTANT">
                                    <AUTRE_MONTANT>
                                    <xsl:value-of select="string(.)"/>
                                    </AUTRE_MONTANT>
                                </xsl:for-each>
                                <xsl:for-each select="FACTURE/TYPE_FACTURE">
                                    <TYPE_FACTURE>
                                    <xsl:value-of select="string(.)"/>
                                    </TYPE_FACTURE>
                                </xsl:for-each>
                            </FACTURE>
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
                </CONTENT>
            </xsl:for-each>
        </DOCUMENT>
    </xsl:template>
</xsl:stylesheet>
