package ed.iotssc.service;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface RSSIRepository extends MongoRepository<RSSI, String> {
	
	  @Query(value="{ 'macAddress' : ?0 }", fields="{ 'macAddress' : 1, 'rssi' : 1, 'timestamp':1}")
	  List<RSSI> findByMac(String macAddress);
	
}
