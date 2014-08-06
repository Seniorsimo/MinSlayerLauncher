/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moduli;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author simone
 */
public class LoginManager {
    private String username;
    private String minecraftName;
    private String password;
    private String accessToken;
    private String clientToken;
    
    public LoginManager(){
        
    }
    
    public boolean keyLogin(){
        boolean error = false;
        
        JSONObject obj = new JSONObject();
        obj.put("accessToken", accessToken);
        obj.put("clientToken", clientToken);
        
        String urlParameters = obj.toJSONString();
        URL url;
        HttpURLConnection connection = null;
        try {
            //Create connection
            url = new URL("https://authserver.mojang.com/refresh");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            //Get Response	
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();

            //creo un json parser per leggere la risposta
            JSONParser parser = new JSONParser();
            JSONObject array = (JSONObject) parser.parse(response.toString());
            JSONObject profile = (JSONObject) array.get("selectedProfile");
            minecraftName = (String)profile.get("name");

            //access e client token
            accessToken = (String)array.get("accessToken");
            clientToken = (String)array.get("clientToken");
            
        } catch (Exception e) { //stampo eventuali errori
            e.printStackTrace();
            error = true;
        } finally { //chiudo la connessione
            if (connection != null) {
                connection.disconnect();
            }
        }
        
        return error;
    }
    
    public boolean passLogin(){
        boolean error = false;
        
        JSONObject obj = new JSONObject();
        HashMap agent = new HashMap();
        agent.put("name", "Minecraft");
        agent.put("version", new Integer(1));
        obj.put("agent", agent);
        obj.put("username", username);
        obj.put("password", password);
        
        String urlParameters = obj.toJSONString();
        URL url;
        HttpURLConnection connection = null;
        try {
            //Create connection
            url = new URL("https://authserver.mojang.com/authenticate");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            //Get Response	
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();

            //creo un json parser per leggere la risposta
            JSONParser parser = new JSONParser();
            JSONObject array = (JSONObject) parser.parse(response.toString());
            JSONObject profile = (JSONObject) array.get("selectedProfile");
            minecraftName = (String)profile.get("name");

            //access e client token
            accessToken =(String)array.get("accessToken");
            clientToken =(String)array.get("clientToken");
            
        } catch (Exception e) { //stampo eventuali errori
            e.printStackTrace();    
            error = true;
        } finally { //chiudo la connessione
            if (connection != null) {
                connection.disconnect();
            }
        }
        return error;
    }

    public String getUsername() {
        return username;
    }

    public String getMinecraftName() {
        return minecraftName;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getClientToken() {
        return clientToken;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setClientToken(String clientToken) {
        this.clientToken = clientToken;
    }
    
}
