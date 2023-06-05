package metier;

import business.ContentType;
import business.Facture;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import service.APICaller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FactureHandler {
    private static final String CONTENTTYPENAME = "Factures";
    private Integer contentTypeFacture;
    private APICaller caller;

    private final ObjectMapper mapper = new ObjectMapper();

    //Compared to the other constructor, this automatically connect a user and checks for the contentType Number, which is subject to change.
    //The other constructor is there in case we ever need a non-specific constructor.
    public FactureHandler(String username, String password){
        caller = new APICaller(username, password);
        getContentTypeNumber();
    }

    private FactureHandler(){}
    private void getContentTypeNumber(){
        String JSONString = caller.makeGetCall("/api/content-type/list", null);

        try {
            List<ContentType> contentTypeList = mapper.readValue(JSONString, new TypeReference<List<ContentType>>() {});
            for (ContentType type:
                 contentTypeList) {
                if (type.getText().equals(CONTENTTYPENAME)){
                    this.contentTypeFacture = type.getId();
                    break;
                }
            }

        }catch (IOException ioException){
            System.out.println(ioException);
        }
    }

    public List<Facture> getFactureNonIncluse(){
        String JSONanswer = caller.makePostCall("/api/search/advanced", "{\"searchPattern\": \"Etat|l01|Validée|list\",\"contentTypeIDs\": \"" + contentTypeFacture+  "\"}");
        JSONArray array;
        List<Facture> retour = new ArrayList<>();
        try {
            //This pulls a list of all needed factures by selecting those who haven't yet been designated has "pulled"
            //A non-pulled facture has its general state set to Validée, where as a pulled one has its general state set to "ValidéeEnAttente"
            //While it works, this means the factures here in the return have their general state still set to Validée, as this how they are pulled.
            //Thus, the factures we're working with here aren't really "up to date" compared to the GED, but still have the needed infos we might need.
            //Their only difference is their state.
            array = new JSONArray(JSONanswer);
            for (int i = 0;i<array.length();i++){
                JSONObject factureJSON = array.getJSONObject(i);
                Facture tempFacture = new Facture();

                //This is to get the ID of the facture.
                //While it exists within the rests of the info, it's much more practical to have it here
                Integer id = factureJSON.getInt("ObjectID");
                tempFacture.setFactureID(id);
                Map<String, Object> infoFacture = mapper.readValue(factureJSON.toString(), new TypeReference<Map<String, Object>>() {});

                //This is to get the document
                String answerDoc = caller.makeGetCall("/api/document/" + tempFacture.getFactureID() + "/attachment", null);
                JSONObject doc = new JSONObject(answerDoc);
                tempFacture.setDocument(doc.getString("File").getBytes());

                //Finally, we add all the rest of the infos as a gigantic Map. It does contains all the metadata and such, so it works I guess ?
                //This doesn't feel very clean but yeah.
                tempFacture.setFacture(infoFacture);
                retour.add(tempFacture);
            }
            //Once this VV execute, the factures on the GED have their general state set to ValidéeEnAttente, and will not be pulled again.
            //If testing, it is better to comment this line, otherwise you'll need to re-add new doc every time, as the other one won't be considered valid for the search performed.
            validateFacture(retour);
            //If executed a second time here, this will set their "HasBeenPaid" state as "Yes"
            //And if executed a third time, it will archive them, setting their general state to "Archivée"
        }catch (JSONException e){
            System.out.println(e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonParseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return retour;
    }

    private void validateFacture(List<Facture> factures){
        for (Facture facture : factures){
            caller.makePostCall("/api/flow/validate/" + facture.getFactureID(), null);
        }
    }
}
