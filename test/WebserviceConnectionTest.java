


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.usp.ime.rusp.core.JSONMapper;
import br.usp.ime.rusp.core.RemoteComment;
import br.usp.ime.rusp.core.RemoteCommentJSONMapper;
import br.usp.ime.rusp.core.RemoteRecommendation;
import br.usp.ime.rusp.core.RemoteRecommendationJSONMapper;
import br.usp.ime.rusp.core.WebserviceClient;
import br.usp.ime.rusp.enumerations.OnlineServices;
import br.usp.ime.rusp.enumerations.RU;

public class WebserviceConnectionTest {

	static String serverAddress = "http://192.168.1.2:8084/";
	WebserviceClient webserviceClient;
	
	@Before
	public void setup() {
		
		webserviceClient = new WebserviceClient();
		
	}
	
	@Test
	public void getStatus() {
		
		try {
			
			JSONMapper<RemoteRecommendation> mapper = new RemoteRecommendationJSONMapper();
			webserviceClient.writeToMapper(OnlineServices.GET_STATUS.getUrlService(serverAddress), mapper);
			List<RemoteRecommendation> result =  mapper.getObjects();
		    assertNotNull(result);
		    
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	    
	}
	
	@Test
	public void timeRecommender() {
		
		try {
			
			JSONMapper<RemoteRecommendation> mapper = new RemoteRecommendationJSONMapper();
			webserviceClient.writeToMapper(OnlineServices.GET_TIME_RECOMMENDATIONS.getUrlService(serverAddress), mapper);
			List<RemoteRecommendation> result =  mapper.getObjects();
		    assertNotNull(result);
		    
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	    
	}
	
	@Test
	public void ruRecommender() {
		
		try {
			
			JSONMapper<RemoteRecommendation> mapper = new RemoteRecommendationJSONMapper();
			webserviceClient.writeToMapper(OnlineServices.GET_RU_RECOMMENDATIONS.getUrlService(serverAddress), mapper);
			List<RemoteRecommendation> result =  mapper.getObjects();
		    assertNotNull(result);
		    
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	    
	}
	
	@Test
	public void getComments() {
		
		try {
			
			JSONMapper<RemoteComment> mapper = new RemoteCommentJSONMapper();
			webserviceClient.writeToMapper(OnlineServices.GET_COMMENTS.getUrlService(serverAddress)+
					RU.FISICA.getName(), mapper);
			List<RemoteComment> result =  mapper.getObjects();
		    assertNotNull(result);
		    
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	    
	}
	
	@Test
	public void sendComment() {
		
	}
	
}
