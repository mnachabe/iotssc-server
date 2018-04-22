package ed.iotssc.service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;

@Service
public class BaseService {
	
	@Autowired
	RSSIRepository rep;
	private PriorityQueue<RSSI> maxHeap;
	
	private static HashMap<String, LatLng> cache; 
	private static HashMap<String, Integer> rssi0; 
	
	static { 
		cache = new HashMap<>();
		rssi0 = new HashMap<>();
		
		//Initialize Latitude and Longitude
		cache.put("ED-23-C0-D8-75-CD", new LatLng(55.9444578385393,-3.1866151839494705));
		cache.put("E7-31-1A-8E-B6-D7", new LatLng(55.94444244275808,-3.18672649562358860));
		cache.put("C7-BC-91-9B-2D-17", new LatLng(55.94452336441765,-3.1866540759801865));
		cache.put("EC-75-A5-ED-88-51", new LatLng(55.94452261340533,-3.1867526471614838));
		cache.put("FE-12-DE-F2-C9-43", new LatLng(55.94448393625199,-3.1868280842900276));
		cache.put("C0-3B-5C-FA-00-B8", new LatLng(55.94449050761571,-3.1866483762860294));
		cache.put("E0-B8-3A-2F-80-2A", new LatLng(55.94443774892113,-3.1867992505431175));
		cache.put("F1-55-76-CB-0C-F8", new LatLng(55.944432116316044,-3.186904862523079));
		cache.put("F1-7F-B1-78-EA-3D", new LatLng(55.94444938963575,-3.1869836524128914));
		cache.put("FD-81-85-98-88-62", new LatLng(55.94449107087541,-3.186941407620907));
		
		//Initialize RSSI0 values
		rssi0.put("ED-23-C0-D8-75-CD", -80);
		rssi0.put("E7-31-1A-8E-B6-D7", -92);
		rssi0.put("C7-BC-91-9B-2D-17", -59);
		rssi0.put("EC-75-A5-ED-88-51", -100);
		rssi0.put("FE-12-DE-F2-C9-43", -95);
		rssi0.put("C0-3B-5C-FA-00-B8", -58);
		rssi0.put("E0-B8-3A-2F-80-2A", -98);
		rssi0.put("F1-55-76-CB-0C-F8", -97);
		rssi0.put("F1-7F-B1-78-EA-3D", -86);
		rssi0.put("FD-81-85-98-88-62", -99);
	}

	public double[] trilaterate() {
		String[] macs = {"ED-23-C0-D8-75-CD", "E7-31-1A-8E-B6-D7", "C7-BC-91-9B-2D-17", "EC-75-A5-ED-88-51", "FE-12-DE-F2-C9-43", "C0-3B-5C-FA-00-B8", "E0-B8-3A-2F-80-2A", "F1-55-76-CB-0C-F8", "F1-7F-B1-78-EA-3D", "FD-81-85-98-88-62"};
		maxHeap = new PriorityQueue<>(new Comparator<RSSI>() {

			@Override
			public int compare(RSSI arg0, RSSI arg1) {
				return -1 * (int) (arg0.getTimestamp()-arg1.getTimestamp());
			}
		});
		
		for(String mac : macs) {
			List<RSSI> findByMac = rep.findByMac(mac);
			try {
				RSSI rssi = findByMac.get(findByMac.size()-1);
				maxHeap.add(rssi);
			} catch(Exception e) {
//				e.printStackTrace();
			}
		}
		
		if(maxHeap.size() < 3) {
			double[] result = {0, 0};
			return result;
		} else {		
			RSSI val1 = maxHeap.poll();
			double dist1 = Math.pow(10, (rssi0.get(val1.getMacAddress())-val1.getRssi()))/(Math.pow(10, 2));
			RSSI val2 = maxHeap.poll();
			double dist2 = Math.pow(10, (rssi0.get(val2.getMacAddress())-val1.getRssi()))/(Math.pow(10, 2));
			RSSI val3 = maxHeap.poll();
			double dist3 = Math.pow(10, (rssi0.get(val3.getMacAddress())-val1.getRssi()))/(Math.pow(10, 2));
			
	        double[][] indoorPositions = {{cache.get(val1.getMacAddress()).getLatitude(), cache.get(val1.getMacAddress()).getLongitude()}, 
	        		{cache.get(val2.getMacAddress()).getLatitude(), cache.get(val2.getMacAddress()).getLongitude()}, 
	        		{cache.get(val3.getMacAddress()).getLatitude(), cache.get(val3.getMacAddress()).getLongitude()}};
	        
	        double[] distance = {dist1, dist2, dist3};
	
	        NonLinearLeastSquaresSolver solver = new NonLinearLeastSquaresSolver(new TrilaterationFunction(indoorPositions, distance), new LevenbergMarquardtOptimizer());
	        LeastSquaresOptimizer.Optimum optimum = solver.solve();
	
	        return optimum.getPoint().toArray();
		}
	}
	
}
