import business.Facture;
import ch.hearc.ged.JSONUtilities;
import metier.FactureHandler;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import service.APICaller;

import java.net.HttpURLConnection;
import java.util.List;


public class Application {
    public static void main(String[] args) {

        FactureHandler factureHandler = new FactureHandler("secrétariat", "secrétariat");

        List<Facture> list = factureHandler.getFactureNonIncluse();

        System.out.println(list);



//        String token = null;
//        String baseUrl = "http://157.26.83.80:2240";
//        String followUpURL = "/token";
//        String url = baseUrl + followUpURL;
//        String request = "grant_type=password&username=direction2&password=direction";
//        JSONUtilities.RequestMethod requestMethodPost = JSONUtilities.RequestMethod.POST;
//        JSONUtilities.RequestMethod requestMethodGet = JSONUtilities.RequestMethod.GET;
//
//        //GET TOKEN
//        System.out.println("getting token");
//        HttpURLConnection connection;
//        String response = null;
//        try{
//            connection = JSONUtilities.write(url, requestMethodPost, request, token);
//            response = JSONUtilities.read(connection);
//        }catch (Exception e) {
//            System.out.println(e);
//        }
//        //READ TOKEN
//        try{
//            JSONObject obj = new JSONObject(response);
//            token = obj.getString("access_token");
//        }catch (JSONException jsonException){
//            System.out.println(jsonException);
//        }
//
//        //DO A SEARCH
//        System.out.println("doign a search");
//        followUpURL = "/api/search/fulltext";
//        url = baseUrl + followUpURL;
//        request = "{\"searchPattern\":\"*\",\"contentTypeIDs\":\"25\"}";
//        try{
//            connection = JSONUtilities.write(url, requestMethodPost, request, token);
//            response = JSONUtilities.read(connection);
//        }catch (Exception e){
//            System.out.println(e);
//        }
    }
}
