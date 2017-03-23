package api;


import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonStructure;
import javax.json.stream.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * Created by torresrl on 15/03/2017.
 */
public class subject extends connect {

    public subject(String subjectCode){
        //super("http://date.jsontest.com");
        super("https://api.ntnu.no/rest/v/public/emne/?entityKey=emnekode:194_" + subjectCode + "_1");
    }


    public String getEvaluation(){
        String evaluation = "";
        try{
            //JsonObject jobj = reader.readObject();
            System.out.println();

        } catch (Exception ex){
            System.out.print("problem api -> subject -> getEvaluation");
            ex.printStackTrace();
        }

        return evaluation;
    }


    public String getDescription(){
        String description = "";

        return description;
    }

    private String findDesApi(){
        String des = "";
        try{

            JsonObject jobj = reader.readObject();

            /*

            JsonArray emneMatrix = jobj.getJsonArray("emne");
            JsonArray emne = emneMatrix.getJsonArray(0);
            JsonObject emneObj = emne.getJsonObject(0);
            JsonArray emneinfoType = emneObj.getJsonArray("emneinfoType");
            JsonObject desObj = emneinfoType.getJsonObject(6);
            des += desObj.getString("infotekstOriginal");


            System.out.println(des);*/

        } catch (Exception ex){
            System.out.println("problem api -> subject -> findDesApi");
            ex.printStackTrace();
        }
        return des;
    }
/*
    public void test(){
        try{

            System.out.println("Time: " + jobj.getJsonString("time"));
            System.out.println("Elapsed: " + jobj.getJsonNumber("milliseconds_since_epoch") + " miliseconds since epoch");
            System.out.println("Date: " + jobj.getJsonString("date"));
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

*/


    //http://javabeat.net/json-processing-javaee-7-object-model-api/
    //http://javabeat.net/java-json-api-jsr-353/
    //https://api.ntnu.no/rest/v/public/emne/?entityKey=emnekode:194_TDT4105_1
    //https://api.ntnu.no/api.html



    public static void main(String[] args) {

        subject e = new subject("TDT4140");
        e.findDesApi();
        //subject test = new subject("");
        //test.test();


    }

}
