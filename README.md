HttpJsonExchange Minimalistic Library
===============================

HttpJsonExchange is a simple Java library that simplifies
exchange of information between Java clients and Http Servers.

It mainly transfers text data using JSON format while trying to adhere
to the HTTP standards as much as possible.

Licence
===============================
HttpJSONExchange  makes use of the JSON library hosted here 
https://code.google.com/p/org-json-java/downloads/list
which is released under GPL open source licence,thus HttpJSONExchange
is released under GPL open source licence.


 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.



Usage
================================

To use it simply create an HttpJSONExchange object then invoke its instance
methods to send GET and POST requests, retrieving the result as JSONObject 
in each case.Make sure to pass additional information such as request headers 
and request parameters if there are any needed.


```Java

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
                "http://api.openweathermap.org/data/2.5/weather",
                parameters,
                null
        );

        //4.Do something with the result such as pulling off info from the returned JSON object
        System.out.println(jsonResponse.toString());


    }

``` 

You can also create the maps of headers and parameters before creating the HttpJSONExchange object 
and pass them in the second form of the constructor that takes arguments or set them using accessor methods.