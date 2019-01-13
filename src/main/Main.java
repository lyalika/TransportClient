package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import me.vyara.transporthotspot.entities.*;
import me.vyara.transporthotspot.api.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
	public static void main(String[] args) throws ClientProtocolException, IOException, ParseException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGetBusLines = new HttpGet("https://www.sofiatraffic.bg/interactivecard/lines/1");
//		HttpGet httpGetTramLines = new HttpGet("https://www.sofiatraffic.bg/interactivecard/lines/2");
//		HttpGet httpGetTrolleyLines = new HttpGet("https://www.sofiatraffic.bg/interactivecard/lines/3");
		CloseableHttpResponse busLines = httpclient.execute(httpGetBusLines);
//		CloseableHttpResponse tramLines = httpclient.execute(httpGetTramLines);
//		CloseableHttpResponse trolleyLines = httpclient.execute(httpGetTrolleyLines);
		List<Line> lineList = new ArrayList<>();
		
		try {
		    HttpEntity entity1 = busLines.getEntity();
		    InputStream is = entity1.getContent();
		    
		    StringBuilder textBuilder = new StringBuilder();
		    try (Reader reader = new BufferedReader(new InputStreamReader
		      (is, Charset.forName(StandardCharsets.UTF_8.name())))) {
		        int c = 0;
		        while ((c = reader.read()) != -1) {
		            textBuilder.append((char) c);
		        }
		    }
		    // do something useful with the response body
		    // and ensure it is fully consumed
		    
		    Pattern p = Pattern.compile("line([0-9]+)\">(.+?)<\\/label>");
		    Matcher m = p.matcher(textBuilder);
		    
		    while(m.find()) {
		    	String lineId = m.group(1);
		    	String lineNumber = m.group(2);
		    	lineList.add(new Line(Long.parseLong(lineId), 1, "А", lineNumber));
		    }
		    
		    EntityUtils.consume(entity1);
		} finally {
		    busLines.close();
		}
		
		HttpGet httpGetTramLines = new HttpGet("https://www.sofiatraffic.bg/interactivecard/lines/2");
		CloseableHttpResponse tramLines = httpclient.execute(httpGetTramLines);
		
		try {
		    HttpEntity entity1 = tramLines.getEntity();
		    InputStream is = entity1.getContent();
		    
		    StringBuilder textBuilder = new StringBuilder();
		    try (Reader reader = new BufferedReader(new InputStreamReader
		      (is, Charset.forName(StandardCharsets.UTF_8.name())))) {
		        int c = 0;
		        while ((c = reader.read()) != -1) {
		            textBuilder.append((char) c);
		        }
		    }
		    // do something useful with the response body
		    // and ensure it is fully consumed
		    
		    Pattern p = Pattern.compile("line([0-9]+)\">(.+?)<\\/label>");
		    Matcher m = p.matcher(textBuilder);
		    
		    while(m.find()) {
		    	String lineId = m.group(1);
		    	String lineNumber = m.group(2);
		    	lineList.add(new Line(Long.parseLong(lineId), 2, "Тм", lineNumber));
		    }
		    
		    EntityUtils.consume(entity1);
		} finally {
		    tramLines.close();
		}
		
		HttpGet httpGetTrolleyLines = new HttpGet("https://www.sofiatraffic.bg/interactivecard/lines/3");
		CloseableHttpResponse trolleyLines = httpclient.execute(httpGetTrolleyLines);
		
		try {
		    HttpEntity entity1 = trolleyLines.getEntity();
		    InputStream is = entity1.getContent();
		    
		    StringBuilder textBuilder = new StringBuilder();
		    try (Reader reader = new BufferedReader(new InputStreamReader
		      (is, Charset.forName(StandardCharsets.UTF_8.name())))) {
		        int c = 0;
		        while ((c = reader.read()) != -1) {
		            textBuilder.append((char) c);
		        }
		    }
		    // do something useful with the response body
		    // and ensure it is fully consumed
		    
		    Pattern p = Pattern.compile("line([0-9]+)\">(.+?)<\\/label>");
		    Matcher m = p.matcher(textBuilder);
		    
		    while(m.find()) {
		    	String lineId = m.group(1);
		    	String lineNumber = m.group(2);
		    	lineList.add(new Line(Long.parseLong(lineId), 3, "Тб", lineNumber));
		    }
		    
		    EntityUtils.consume(entity1);
		} finally {
		    trolleyLines.close();
		}
		
//		String host = (args.length < 1) ? null : args[0];
//        try {
//            Registry registry = LocateRegistry.getRegistry(host);
//            InterfaceRMI stub = (InterfaceRMI) registry.lookup("transport");
//            stub.updateLines(lineList);
//        } catch (Exception e) {
//            System.err.println("Client exception: " + e.toString());
//            e.printStackTrace();
//        }
		//get stops
		
		HttpGet httpGetStops = new HttpGet("https://www.sofiatraffic.bg/interactivecard/stops/geo");
		CloseableHttpResponse stops = httpclient.execute(httpGetStops);	
		try {
		    HttpEntity entity2 = stops.getEntity();
		    InputStream is = entity2.getContent();
		    
		    StringBuilder textBuilder = new StringBuilder();
		    try (Reader reader = new BufferedReader(new InputStreamReader
		      (is, Charset.forName(StandardCharsets.UTF_8.name())))) {
		        int c = 0;
		        while ((c = reader.read()) != -1) {
		            textBuilder.append((char) c);
		        }
		    }
		    // do something useful with the response body
		    // and ensure it is fully consumed
		    //System.out.println(textBuilder);
		    JSONParser parse = new JSONParser();
		    JSONObject jObject = (JSONObject) parse.parse(textBuilder.toString());
		    
		    JSONArray jsonArray = (JSONArray) jObject.get("features"); // array of stops in json format
		    Iterator it = jsonArray.iterator();
		    while(it.hasNext()) {
		    	JSONObject innerObj = (JSONObject) it.next();
		    	long stopId = (long) innerObj.get("id");
		    	JSONObject propertiesArray = (JSONObject) innerObj.get("properties");
		    	JSONObject geometry = (JSONObject) innerObj.get("geometry");
		    	JSONArray coordinatesObj = (JSONArray) geometry.get("coordinates");
		    	Iterator coordIt = coordinatesObj.iterator();
		    	double coord0 = (double) coordIt.next();
		    	double coord1 = (double) coordIt.next();
		    	String stopName = (String) propertiesArray.get("name");
		    	long stopNumber = (long) propertiesArray.get("code");
		    	JSONArray linesList = (JSONArray) propertiesArray.get("lines_list");
		    	Iterator it2 = linesList.iterator();
		    	List<Line> listOfLines = new ArrayList<>();
		    	while(it2.hasNext()) {
		    		JSONObject line = (JSONObject) it2.next();
		    		long lineId = (long) line.get("id");
		    		String lineName = (String) line.get("name");
		    		long transportType = (long) line.get("transport_id");
		    		String prefixType = (String) line.get("transport_prefix");
		    		Line currentLine = new Line(lineId, transportType, prefixType, lineName);
		    		listOfLines.add(currentLine);
		    	}
		    	Stop currentStop = new Stop(stopId, coord0, coord1, stopNumber, stopName);
		    	
//		    	String host2 = (args.length < 1) ? null : args[0];
//		        try {
//		            Registry registry = LocateRegistry.getRegistry(host2);
//		            InterfaceRMI stub = (InterfaceRMI) registry.lookup("transport");
//		            stub.updateStops(currentStop, listOfLines);
//		        } catch (Exception e) {
//		            System.err.println("Client exception: " + e.toString());
//		            e.printStackTrace();
//		        }
		    }
		    EntityUtils.consume(entity2);
		} finally {
		    stops.close();
		}
		
		HttpGet httpGetStopTimetable = new HttpGet("https://api-arrivals.sofiatraffic.bg/api/v1/arrivals/0388/");
		CloseableHttpResponse stopLines = httpclient.execute(httpGetStopTimetable);	
		try {
		    HttpEntity entity1 = stopLines.getEntity();
		    InputStream is = entity1.getContent();
		    
		    StringBuilder textBuilder = new StringBuilder();
		    try (Reader reader = new BufferedReader(new InputStreamReader
		      (is, Charset.forName(StandardCharsets.UTF_8.name())))) {
		        int c = 0;
		        while ((c = reader.read()) != -1) {
		            textBuilder.append((char) c);
		        }
		    }
		    // do something useful with the response body
		    // and ensure it is fully consumed
		    
		    JSONParser parse = new JSONParser();
		    JSONObject jObject = (JSONObject) parse.parse(textBuilder.toString());
		    String stopCodeAsString = (String) jObject.get("code");
		    long stopCode = Long.parseLong(stopCodeAsString);
		    JSONArray lineArray = (JSONArray) jObject.get("lines");
		    Iterator it = lineArray.iterator();
		    HashMap<String, List<LocalDateTime>> linesMap = new HashMap<>();
		    while(it.hasNext()) {
		    	JSONObject currentLine = (JSONObject) it.next();
		    	String name = (String) currentLine.get("name");
		    	String vehicleType = (String) currentLine.get("vehicle_type");
		    	String code;
		    	if(vehicleType.equals("bus")) {
		    		code = "А";
		    	} else if(vehicleType.equals("trolley")) {
		    		code = "Тб";
		    	} else {
		    		code = "Тм";
		    	}
		    	name = code + name;
		    	JSONArray timesArray = (JSONArray) currentLine.get("arrivals");
		    	Iterator it2 = timesArray.iterator();
		    	List<LocalDateTime> listOfTimes = new ArrayList<>();
		    	while(it2.hasNext()) {
		    		JSONObject oneArrival = (JSONObject) it2.next();
		    		String timeString = (String) oneArrival.get("time");
		    		String[] timeArray = timeString.split(":");
		    		int hours = Integer.parseInt(timeArray[0]);
		    		int minutes = Integer.parseInt(timeArray[1]);
		    		int seconds = Integer.parseInt(timeArray[2]);
		    		LocalDateTime rightNow = LocalDateTime.now();
		    		int year = rightNow.getYear();
		    		int month = rightNow.getMonthValue();
		    		int day = rightNow.getDayOfYear();
		    		LocalDateTime date = LocalDateTime.of(year, month, day, hours, minutes, seconds);
		    		
		    		listOfTimes.add(date);
		    	}
		    	linesMap.put(name, listOfTimes);
		    }
		    
		    String host3 = (args.length < 1) ? null : args[0];
	        try {
	            Registry registry = LocateRegistry.getRegistry(host3);
	            InterfaceRMI stub = (InterfaceRMI) registry.lookup("transport");
	            stub.updateTimetableForStop(stopCode, linesMap);
	            
	        } catch (Exception e) {
	            System.err.println("Client exception: " + e.toString());
	            e.printStackTrace();
	        }
		    
		    EntityUtils.consume(entity1);
		} finally {
		    stopLines.close();
		}
		
		
	}
}
