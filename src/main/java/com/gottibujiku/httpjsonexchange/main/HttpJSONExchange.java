
/*
    A simple Java library that simplifies
    exchange of information between Java clients and Http Servers.

    Copyright (C) 2015, Newton Bujiku, igotti47@gmail.com

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
 */

package com.gottibujiku.httpjsonexchange.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class manages the transfer of text based data over the network
 * by using HTTP protocol and JSON format.
 * 
 * Exposes methods to handle POST and GET requests.
 * 
 * @author Newton Bujiku
 * @since April,2015
 * 
 */
public class HttpJSONExchange {
	private static final char EQUAL = '=';
	private static final char PARAM_SEPARATOR ='&';//used to separate the parameters in the query string
	private static final char QUERY_SEPARATOR ='?';//used to separate the domain and the query string
	private static final String UTF_CHARSET = "UTF-8";
	private String fullDomainName;
	private HashMap<String, String> queryParams;
	private HashMap<String, String> headers;

	public HttpJSONExchange(){

	}

	public HttpJSONExchange(String fullDomainName, HashMap<String, String> queryParams, HashMap<String, String> headers){
		this.fullDomainName = fullDomainName;
		this.queryParams = queryParams;
		this.headers = headers;
	}


	/**
	 * This method sends a GET request to the server.
	 * It prepares the request by creating a valid URL with a query string(if any)
	 * then adds the headers and then open a connection to the server; retrieves
	 * response and return result as a JSON Object
	 * 
	 * @return a JSON object containing the response             
	 */
	public JSONObject sendGETRequest(){

		/* 1. Form the query string by concatenating param names and their values
		 * 2. Add HTTP headers to the request and set request method to GET
		 * 3. Open stream and read server response
		 * 
		 */
        if(fullDomainName ==null){
            throw new NullPointerException("No URL provided! Please provide one!");//No domain name was provided
        }
		JSONObject jsonResponse = null;
		URL url = null;//a url object to hold a complete URL
		try {
			url = new URL(fullDomainName + QUERY_SEPARATOR + getFormatedQueryString(queryParams));//a complete URL 
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection = addHeadersToRequest(headers, connection);//add headers to connection and get modified instance
			connection.setRequestProperty("Accept-Charset", UTF_CHARSET);//accept the given encoding
			//a call to connection.connect() is superfluous since connect will be called
			//implicitly when the stream is opened
			connection.setRequestMethod("GET");
			String jsonString = getServerResponse(connection);
			connection.disconnect();
			jsonResponse = new JSONObject(jsonString);//change the response into a json object		


		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;//return if the URL is malformed
		} catch (IOException e) {
			e.printStackTrace();
			return null;//if failed to open a connection
		} catch (JSONException e) {
			e.printStackTrace();
			return null;//invalid JSON response
		}

		return jsonResponse;


	}


	/**
	 * This method sends a GET request to the server.
	 * It prepares the request by creating a valid URL with a query string(if any)
	 * then adds the headers and then open a connection to the server; retrieves
	 * response and return result as a JSON Object
	 * 
	 * 
	 * @param fullDomainName a fully qualified domain name including the protocol
	 * @param queryParams    parameters to be sent in the query string
	 * @param headers        additional headers
	 * @return a JSON object containing the response             
	 */

	public JSONObject sendGETRequest(String fullDomainName, HashMap<String, String> queryParams, HashMap<String, String> headers){

		/* 1. Form the query string by concatenating param names and their values
		 * 2. Add HTTP headers to the request and set request method to GET
		 * 3. Open stream and read server response
		 * 
		 */
        if(fullDomainName ==null){
            throw new NullPointerException("No URL provided! Please provide one!");//No domain name was provided
        }
		JSONObject jsonResponse = null;
		URL url = null;//a url object to hold a complete URL
		try {
			url = new URL(fullDomainName + QUERY_SEPARATOR + getFormatedQueryString(queryParams));//a complete URL 
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection = addHeadersToRequest(headers, connection);//add headers to connection and get modified instance
			connection.setRequestProperty("Accept-Charset", UTF_CHARSET);//accept the given encoding
			//a call to connection.connect() is superfluous since connect will be called
			//implicitly when the stream is opened
			connection.setRequestMethod("GET");
			String jsonString = getServerResponse(connection);
			connection.disconnect();
			jsonResponse = new JSONObject(jsonString);//change the response into a json object		


		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;//return if the URL is malformed
		} catch (IOException e) {
			e.printStackTrace();
			return null;//if failed to open a connection
		} catch (JSONException e) {
			e.printStackTrace();
			return null;//invalid JSON response
		}

		return jsonResponse;


	}

	/**
	 * This method sends a POST request to the server. 
	 * 
	 * @param fullDomainName a fully qualified domain name including the protocol
	 * @param queryParams    parameters to be sent in the query string
	 * @param headers        additional headers
	 * @return a JSON object containing the response             
	 */
	public JSONObject sendPOSTRequest(String fullDomainName, HashMap<String, String> queryParams, HashMap<String, String> headers){

		/* 1. Form the query string by concatenating param names and their values
		 * 2. Add HTTP headers to the request and set request method to POST, open output stream and write the query string
		 * 3. Open input stream and read server response
		 * 
		 */
        if(fullDomainName ==null){
            throw new NullPointerException("No URL provided! Please provide one!");//No domain name was provided
        }
		JSONObject jsonResponse = null;
		URL url = null;//a url object to hold a complete URL
		try {
			url = new URL(fullDomainName);//a complete URL 
			String queryString = getFormatedQueryString(queryParams);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection = addHeadersToRequest(headers, connection);//add headers to connection and get modified instance
			connection.setDoOutput(true);//enables writing over the open connection
			connection.setRequestProperty("Accept-Charset", UTF_CHARSET);//accept the given encoding
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset="+UTF_CHARSET);
			//a call to connection.connect() is superfluous since connect will be called
			//implicitly when the stream is opened
			connection.setRequestMethod("POST");
			//open the connection and send the query string in the request body
			try(PrintWriter writer = new PrintWriter(connection.getOutputStream(),true)){
				writer.print(queryString);//send the query string in the request body
			}	
			String jsonString = getServerResponse(connection);
			connection.disconnect();
			jsonResponse = new JSONObject(jsonString);//change the response into a json object		


		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;//return if the URL is malformed
		} catch (IOException e) {
			e.printStackTrace();
			return null;//if failed to open a connection
		} catch (JSONException e) {
			e.printStackTrace();
			return null;//invalid JSON response
		}

		return jsonResponse;


	}

	
	/**
	 * This method sends a POST request to the server. 
	 * 
	 * @return a JSON object containing the response             
	 */
	public JSONObject sendPOSTRequest(){

		/* 1. Form the query string by concatenating param names and their values
		 * 2. Add HTTP headers to the request and set request method to POST, open output stream and write the query string
		 * 3. Open input stream and read server response
		 * 
		 */

        if(fullDomainName ==null){
            throw new NullPointerException("No URL provided! Please provide one!");//No domain name was provided
        }

		JSONObject jsonResponse = null;
		URL url = null;//a url object to hold a complete URL
		try {
			url = new URL(fullDomainName);//a complete URL 
			String queryString = getFormatedQueryString(queryParams);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection = addHeadersToRequest(headers, connection);//add headers to connection and get modified instance
			connection.setDoOutput(true);//enables writing over the open connection
			connection.setRequestProperty("Accept-Charset", UTF_CHARSET);//accept the given encoding
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset="+UTF_CHARSET);
			//a call to connection.connect() is superfluous since connect will be called
			//implicitly when the stream is opened
			connection.setRequestMethod("POST");
			//open the connection and send the query string in the request body
			try(PrintWriter writer = new PrintWriter(connection.getOutputStream(),true)){
				writer.print(queryString);//send the query string in the request body
			}	
			String jsonString = getServerResponse(connection);
			connection.disconnect();
			jsonResponse = new JSONObject(jsonString);//change the response into a json object		


		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;//return if the URL is malformed
		} catch (IOException e) {
			e.printStackTrace();
			return null;//if failed to open a connection
		} catch (JSONException e) {
			e.printStackTrace();
			return null;//invalid JSON response
		}

		return jsonResponse;


	}
	/**
	 * Adds additional headers to the HTTP Request
	 * 
	 * @param headers A Hashmap of name and value headers
	 * @param connection A connection object used to connect to the server
	 * @return Modified connection object with additional headers(if there were any)
	 */
	private HttpURLConnection addHeadersToRequest(HashMap<String, String> headers, HttpURLConnection connection) {
		Set<String> keys;
		if(headers != null){//check if there were any additional headers
			keys = headers.keySet();//get headers' keys
			//set the headers as request properties
			for(String key : keys){
				connection.setRequestProperty(key, headers.get(key));
			}
		}		
		return connection;
	}

	/**
	 * Connects to the server and retrieves the response as a JSON String
	 * @param connection A connection object used to connect to the server
	 * @return a JSON formatted String from the server
	 * @throws IOException if could not read from the stream
	 */
	private String getServerResponse(HttpURLConnection connection)throws IOException {
		String line;
		StringBuilder stringBuilder = new StringBuilder();
		try(BufferedReader reader =new BufferedReader(new InputStreamReader(connection.getInputStream()))){//open with resources
			//open a stream to read the response) if all was OK
			if( connection.getResponseCode() == HttpURLConnection.HTTP_OK){
				while((line = reader.readLine()) != null){
					//if  the response from the server is not null
					stringBuilder.append(line);

				}
			}

		}
		return stringBuilder.toString();//return a JSON string from the server
	}

	/**
	 * Formats the provided query parameters into a query string
	 * 
	 * @param queryParams A HashMap of the parameter names and values
	 * @return a formatted and encoded query string
	 */
	private String getFormatedQueryString(HashMap<String, String> queryParams) {
		StringBuilder stringBuilder = new StringBuilder();//avoid strings when looping,they are immutable
		if(queryParams != null){//do this if there were parameters given
			Set<String> keys;
			//Loop around the map to form a complete query string
			//Do encoding to escape special characters
			keys = queryParams.keySet();//holds all keys in the map
			String value = null;
			for(String key : keys){
				value = queryParams.get(key);//retrieve parameter value

				try {
					//encode the  key and value from the map to get name&value pairs and append them to the queryBuilder
					stringBuilder.append(
							URLEncoder.encode(key,UTF_CHARSET) + EQUAL +
							URLEncoder.encode(value,UTF_CHARSET) +PARAM_SEPARATOR
							);

				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();

				}

			}
		}
		return stringBuilder.toString();
	}

	public String getFullDomainName() {
		return fullDomainName;
	}

	public void setFullDomainName(String fullDomainName) {
		this.fullDomainName = fullDomainName;
	}

	public HashMap<String, String> getQueryParams() {
		return queryParams;
	}

	public void setQueryParams(HashMap<String, String> queryParams) {
		this.queryParams = queryParams;
	}

	public HashMap<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(HashMap<String, String> headers) {
		this.headers = headers;
	}


}
