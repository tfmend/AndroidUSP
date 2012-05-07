


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

public class JsonMapperTest {

	String jsonComment;
	String jsonRecommendation;
	
	@Before
	public void setup() {
		jsonComment = "[{\"bandejao\":\"fisica\",\"texto\":\"10.0\",\"hora\":\"00:00\",\"fila\":\"SEM_FILA\"}]";
		jsonRecommendation = "[{\"bandejao\":\"fisica\",\"index\":\"10.0\",\"hora\":\"00:00\",\"fila\":\"SEM_FILA\"}]";
	}
	
	@Test
	public void recommendation() {
		
		try {
			
		    JSONMapper<RemoteRecommendation> mapper = new RemoteRecommendationJSONMapper();
		    mapper.read(jsonRecommendation);
		    List<RemoteRecommendation> result = mapper.getObjects();
		    assertNotNull(result);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	    
	}
	
	@Test
	public void comment() {
		
		try {
		    JSONMapper<RemoteComment> mapper = new RemoteCommentJSONMapper();
		    mapper.read(jsonComment);
		    List<RemoteComment> result = mapper.getObjects();
		    assertNotNull(result);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	    
	}
	
	
	
}
