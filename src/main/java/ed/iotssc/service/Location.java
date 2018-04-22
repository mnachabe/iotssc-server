package ed.iotssc.service;

import org.springframework.data.annotation.Id;

public class Location {
	
	@Id
	public String id;
	
	private double latitude; 
	
	private double longitude;
	
	private long timestamp;
	
	public Location(double latitude, double longitude, long timestamp) {
		this.latitude = latitude; 
		this.longitude = longitude;
		this.timestamp = timestamp;
		
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	} 
	
	@Override
	public String toString() {
	    return String.format(
	            "LOCATION[id=%s, lat='%s', lon='%d', timestamp='%d']",
	            id, latitude, longitude, timestamp);
    }
	
}
