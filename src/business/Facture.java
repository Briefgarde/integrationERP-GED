package business;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class Facture {
    private Map<String, Object> facture;

    private Integer factureID;

    private byte[] document;

    public Facture() {
        super();
    }

    public Map<String, Object> getFacture() {
        return facture;
    }

    public Integer getFactureID() {
        return factureID;
    }

    public void setFactureID(Integer factureID) {
        this.factureID = factureID;
    }

    public void setFacture(Map<String, Object> facture) {
        this.facture = facture;
    }

    public byte[] getDocument() {
        return document;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }



    //Not all getter are provided, only the ones

    public String getNomFournisseur(){
        for (Map field: (ArrayList<LinkedHashMap>) this.getFacture().get("Fields")) {
            if (field.get("Code").equals("Fournisseur")){
                return (String) field.get("Value");
            }
        }
        return null;
    }

    //I'm not sure if the setter should even exist in this application, as it is more of a transitory state than anything,
    // not meant to be worked on here
    //I'm leaving them more as a proof I could do it ? Just in case ? idk
    public void setNomFournisseur(String nomFournisseur){
        for (Map field: (ArrayList<LinkedHashMap>) this.getFacture().get("Fields")) {
            if (field.get("Code").equals("Fournisseur")){
                field.replace("Value", nomFournisseur);
            }
        }
    }


    public String getState(){
        for (Map field: (ArrayList<LinkedHashMap>) this.getFacture().get("Fields")) {
            if (field.get("Code").equals("Etat")){
                return (String) field.get("Value");
            }
        }
        return null;
    }

    //As State isn't supposed to be touched directly, the setMethod for this attribute is private.
    private void setState(String state){
        for (Map field: (ArrayList<LinkedHashMap>) this.getFacture().get("Fields")) {
            if (field.get("Code").equals("Etat")){
                field.replace("Value", state);
            }
        }
    }

    public Double getMontant(){
        for (Map field: (ArrayList<LinkedHashMap>) this.getFacture().get("Fields")) {
            if (field.get("Code").equals("F_montant_total")){
                return Double.parseDouble((String) field.get("Value"));
            }
        }
        return null;
    }

    public String getNumeroFacture(){
        for (Map field: (ArrayList<LinkedHashMap>) this.getFacture().get("Fields")) {
            if (field.get("Code").equals("F_numero_de_facture")){
                return (String) field.get("Value");
            }
        }
        return null;
    }
    public Date getDateCreation(){
        for (Map field: (ArrayList<LinkedHashMap>) this.getFacture().get("Fields")) {
            if (field.get("Code").equals("F_date_de_creation_d")){
                return new Date((String) field.get("Value")) ;
            }
        }
        return null;
    }

    public Date getDatePayable(){
        for (Map field: (ArrayList<LinkedHashMap>) this.getFacture().get("Fields")) {
            if (field.get("Code").equals("F_date_payable")){
                return new Date((String) field.get("Value")) ;
            }
        }
        return null;
    }
}
