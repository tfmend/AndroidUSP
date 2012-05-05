package br.usp.ime.rusp.core;

import java.util.List;

public interface JSONMapper<T> {

	public void read(String jsonString) throws Exception;
	public List<T> getObjects() throws Exception;
	
	
}
