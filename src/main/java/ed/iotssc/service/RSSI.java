package ed.iotssc.service;

import org.springframework.data.annotation.Id;

public class RSSI {

	@Id
	public String id;
	
	public String macAddress;
	
	public int rssi;
	
	private long timestamp;
	
	public RSSI() {}
	
	public RSSI(String macAddress, int rssi, long timestamp) {
	    this.macAddress = macAddress;
	    this.rssi = rssi;
	    this.timestamp = timestamp;
	}
	
	@Override
	public String toString() {
	    return String.format(
	            "RSSI[id=%s, macAddress='%s', rssi='%d', timestamp='%d']",
	            id, macAddress, rssi, timestamp);
    }

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public int getRssi() {
		return rssi;
	}

	public void setRssi(int rssi) {
		this.rssi = rssi;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	

}
