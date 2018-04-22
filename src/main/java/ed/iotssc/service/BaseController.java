package ed.iotssc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {
	
	@Autowired 
	BaseService service;
	
	@Autowired
	RSSIRepository rssiRepo;
	
    @Autowired 
    LocationRepository rep;
	
	@RequestMapping(value = "/path", method = RequestMethod.POST)
	public String path(@RequestBody RSSIObject obj) { 
		
		rssiRepo.save(new RSSI(obj.getMac(), obj.getRssi(), obj.getTimestamp()));
		for (RSSI rssi : rssiRepo.findAll()) {
			System.out.println(rssi);
		}
		return obj.getMac();
	}
	
	@RequestMapping(value = "/hello")
	public Location path() { 
//        double[][] indoorPositions = {{31.257474, 121.620974}, 
//        		{31.260217, 121.621095}, 
//        		{31.259148, 121.623835}};
//        
//        double[] distance = {0.228, 0.123, 0.187};
//
//        NonLinearLeastSquaresSolver solver = new NonLinearLeastSquaresSolver(new TrilaterationFunction(indoorPositions, distance), new LevenbergMarquardtOptimizer());
//        LeastSquaresOptimizer.Optimum optimum = solver.solve();
//
//        double[] array = optimum.getPoint().toArray();
//        return array[0] + " " + array[1];
		List<Location> findAll = rep.findAll();
		Location location = findAll.get(findAll.size()-1);
		return location;
	}
	
	@RequestMapping(value = "/test")
	public String test() { 
		return "Hello World";
	}

}
