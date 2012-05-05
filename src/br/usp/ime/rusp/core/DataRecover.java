package br.usp.ime.rusp.core;

import java.util.List;

public interface DataRecover<T> {

	public void afterRecovery(List<T> data);
	
}
