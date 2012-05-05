package br.usp.ime.rusp.core;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import android.content.Context;
import br.usp.ime.rusp.enumerations.RU;

public class OfflineRUConnection implements RUConnection {

	private static OfflineRUConnection INSTANCE;

	public static synchronized OfflineRUConnection getInstance(Context context) {

		if (INSTANCE == null) {
			INSTANCE = new OfflineRUConnection(context);
		}

		return INSTANCE;

	}
	
	private final Context context;
	public List<RemoteRecommendation> rurecommendations = new ArrayList<RemoteRecommendation>();
	public List<RemoteRecommendation> timerecommendations = new ArrayList<RemoteRecommendation>();
	public List<RemoteRecommendation> statusrecommendations = new ArrayList<RemoteRecommendation>();
	private RU currentRU;
	
	private OfflineRUConnection(Context context) {
		this.context = context;
	}

	@Override
	public void sendComment(RemoteComment comment,
			CallBackListener callBackListener) throws Exception {
		
		OffLineDataHelper helper = OffLineDataHelper.getInstance(context);
		helper.saveComment(comment);
		
	}

	@Override
	public List<RemoteRecommendation> getRURecommendations(
			CallBackListener callBackListener) throws Exception {
		return rurecommendations;
	}

	@Override
	public List<RemoteRecommendation> getTimeRecommendation(
			CallBackListener callBackListener) throws Exception {
		return timerecommendations;
	}

	@Override
	public List<RemoteComment> getComments(CallBackListener callBackListener)
			throws Exception {
		Calendar cal = GregorianCalendar.getInstance();
		for ( int i = 0; i < 10 ; i++ ) {
			cal.roll(Calendar.MINUTE, false);
		}
		
		List<RemoteComment> comments = OffLineDataHelper.getInstance(context).getCommentsAfter(cal.getTime());
		OffLineDataHelper.getInstance(context).clearTable();
		return comments;
		
	}

	@Override
	public List<RemoteRecommendation> getStatus(CallBackListener backListener)
			throws Exception {

		return statusrecommendations;
		
	}

	@Override
	public void setCurrentRU(RU ru) {
		currentRU = ru;
		
	}
	
	
}
