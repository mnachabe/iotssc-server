package ed.iotssc.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {

    @Autowired 
    BaseService service;
    
    @Autowired 
    LocationRepository rep;

    @Scheduled(fixedRate = 3000)
    public void scheduledTask() {
        double[] trilaterate = service.trilaterate();
        rep.save(new Location(trilaterate[0], trilaterate[1], System.currentTimeMillis()));
        PrintWriter pw = null;
        try {
        	String fileName = "/var/www/html/output.csv";
	        File f = new File(fileName);
	        f = new File(fileName);
	        StringBuilder sb = new StringBuilder();
	        if(!f.exists()) {
					pw = new PrintWriter(new File(fileName));
					sb.append("lat");
					sb.append(',');
					sb.append("lon");
					sb.append(',');
					sb.append("timestamp");
					sb.append("\r\n");
	        } else {
	        	pw = new PrintWriter(new FileOutputStream(new File(fileName), true));
	        }
	        
	        sb.append(String.valueOf(trilaterate[0]));
	        sb.append(',');
	        sb.append(String.valueOf(trilaterate[1]));
	        sb.append(',');
	        sb.append(String.valueOf(System.currentTimeMillis()));
	        sb.append("\r\n");
			
			pw.write(sb.toString());
	        
	        
        } catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pw != null)
				pw.close();
		}
       
    }
}