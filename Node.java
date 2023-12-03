//header files
import java.io.*;

public class Node {
	
	private static final double EARTH_RADIUS = 6371;
	
	//variables to use in this class
	private String name;
	private double lat;
	private double lon;
	
	
	//constructors
	public Node(){
		
	}
	public Node(String name, double lat, double lon) {
		
		//initializing the variables
		this.name= name;
		this.lat = lat;
		this.lon = lon;
		
	}
	
	//setters
	public void setName(String name){
		
		//set name of the city
		this.name = name;
	}
	public void setLat(double lat) {
		
		//set lat of the city
		this.lat = lat;
	}
	public void setLon(double lon) {
		
		//set lon of the city
		this.lon = lon;
	}
	
	//getters
	public String getName() {
		
		//get name of the city
		return name;
	}
	public double getLat(){
		
		//get lat of the city
		return lat;
	}
	public double getLon(){
		
		//get lon of the city
		return lon;
	}
	
	//edit city info
	public void userEdit()throws NumberFormatException, IOException {
		
		//get new city name
		System.out.print("   Name: ");
		String newName = Pro3_samireez.jIn.readLine(); //calling bufferedreader object from the main class file
		setName(newName);
		
		//get new lat
		double newLat = Pro3_samireez.getDouble("   latitude: ", -90, 90);
		setLat(newLat);
		
		//get new lon
		double newLon = Pro3_samireez.getDouble("   longitude: ", -180, 180);
		setLon(newLon);
	}
	
	
	//print method to print city data
	public void print() {
		
		System.out.printf("%-15s | %10f | %10f\n", name, lat, lon);
		
	}
	
	//method to calculate distance(cost) betwn cities using Haversine formula
	public static double distance(Node i, Node j) {
		
		double latDiff = Math.toRadians(j.lat) - Math.toRadians(i.lat);
		double lonDiff = Math.toRadians(j.lon) - Math.toRadians(i.lon);
		
		double a = (Math.sin(latDiff/2) * Math.sin(latDiff/2)) + (Math.cos(Math.toRadians(i.lat)) * Math.cos(Math.toRadians(j.lat)) * (Math.sin(lonDiff/2) * Math.sin(lonDiff/2)));
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		
		return EARTH_RADIUS * c;
	}
	
}
