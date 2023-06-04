package service;

import ch.hearc.ged.JSONUtilities;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.net.HttpURLConnection;

public class APICaller {
    private static final String APILINK = "http://157.26.83.80:2240";

    private String followUpUrl = null;

    private String token = null;

    private String request = null;

    private JSONUtilities.RequestMethod requestMethodPost = JSONUtilities.RequestMethod.POST;

    private JSONUtilities.RequestMethod requestMethodGet = JSONUtilities.RequestMethod.GET;

    public APICaller(){}

    public APICaller(String username, String password){
        getToken(username, password);
    }

    public boolean getToken(String username, String password){
        Boolean loginSuccessful = false;
        HttpURLConnection connection;
        String response = null;
        this.followUpUrl = "/token";
        this.request = "grant_type=password&username=" + username + "&password=" + password;
        try{
            connection = JSONUtilities.write(APILINK + followUpUrl, requestMethodPost, request, token);
            response = JSONUtilities.read(connection);
        }catch (Exception e) {
            System.out.println(e);
        }

        try{
            JSONObject obj = new JSONObject(response);
            token = obj.getString("access_token");
            loginSuccessful = true;
        }catch (JSONException jsonException){
            System.out.println(jsonException);
        }

        return loginSuccessful;
    }

    public String makeGetCall(String address, String request){
        this.request = request;
        String JSONAnswer = null;
        if (token == null){
            JSONAnswer = "This doesn't work my good man, you need to login or something";
        }else {
            HttpURLConnection connection;
            this.followUpUrl = address;
            try{
                connection = JSONUtilities.write(APILINK + followUpUrl, requestMethodGet, this.request, token);
                JSONAnswer = JSONUtilities.read(connection);
            }catch (Exception e) {
                System.out.println(e);
            }
        }
        return JSONAnswer;
    }

    public String makePostCall(String address, String request){
        this.request = request;
        String JSONAnswer = null;
        if (token == null){
            JSONAnswer = "This doesn't work my good man, you need to login or something";
        }else {
            HttpURLConnection connection;
            this.followUpUrl = address;
            try{
                connection = JSONUtilities.write(APILINK + followUpUrl, requestMethodPost, this.request, token);
                JSONAnswer = JSONUtilities.read(connection);
            }catch (Exception e) {
                System.out.println(e);
            }
        }
        return JSONAnswer;
    }




    public String getFollowUpUrl() {
        return followUpUrl;
    }

    public void setFollowUpUrl(String followUpUrl) {
        this.followUpUrl = followUpUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
}
