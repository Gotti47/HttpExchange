package com.gottibujiku.httpjsonexchange.example;

import com.gottibujiku.httpjsonexchange.main.HttpJSONExchange;
import org.json.JSONObject;

import java.util.HashMap;

/**
 *A simple class to show how to use this library
 *
 * @author Newton Bujiku
 * @since 2015
 */
public class Example {

    public static void main(String[] argv){
        //http://api.openweathermap.org/data/2.5/weather?q=Dar,tz
        //This example retrieves JSON weather data of Dar Es Salaam,Tanzania
        //using openweathermap APIs

        //1.Create an HttpJSONExchange object
        HttpJSONExchange httpExchange = new HttpJSONExchange();

        //2.Create a HashMap of parameters i.e we need to have the URL below
        // http://api.openweathermap.org/data/2.5/weather?q=Dar,tz

        HashMap<String,String> parameters = new HashMap<>();

        //add the parameters to the map
        parameters.put("q","Dar,tz");

        //3.Send a GET request
        //passing a complete URL as a String including a scheme(http/https) and a domain name
        //pass the parameters map as a second argument and null if there are no additional request headers
        JSONObject jsonResponse = httpExchange.sendGETRequest(
               null, //"http://api.openweathermap.org/data/2.5/weather",
                parameters,
                null
        );

        //4.Do something with the result such as pulling off info from the returned JSON object
        System.out.println(jsonResponse.toString());


    }
}
