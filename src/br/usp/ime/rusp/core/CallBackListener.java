package br.usp.ime.rusp.core;

public interface CallBackListener {

	public void beforeStart();
	
	public void afterEnd();
	
	public void sucess();
	
	public void fail(Exception e);
	
	
}
