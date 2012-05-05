package br.usp.ime.rusp.core;

import java.util.List;

import br.usp.ime.rusp.enumerations.RU;

public interface RUConnection {

	public void setCurrentRU(RU ru);
	
	public void sendComment(RemoteComment comment,CallBackListener callBackListener) throws Exception;
	
	public List<RemoteComment> getComments(CallBackListener callBackListener) throws Exception;
	
	public List<RemoteRecommendation> getRURecommendations(CallBackListener callBackListener) throws Exception;
	
	public List<RemoteRecommendation> getTimeRecommendation(CallBackListener callBackListener) throws Exception;

	public List<RemoteRecommendation> getStatus(CallBackListener backListener)  throws Exception;	
	
}
