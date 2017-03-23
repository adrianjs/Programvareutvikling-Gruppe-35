package api;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonParser;

import java.net.URL;
import java.io.InputStream;

/**
 * Created by torresrl on 15/03/2017.
 */
public class Connect {
    URL url;
    InputStream in;
    JsonReader reader;




    public Connect(String uri){
        try {
            url = new URL(uri);
            in = url.openStream();
            reader = Json.createReader(in);


        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
