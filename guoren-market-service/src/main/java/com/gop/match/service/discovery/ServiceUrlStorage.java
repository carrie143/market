package com.gop.match.service.discovery;

import java.util.Set;

public interface ServiceUrlStorage {

	public Set<String> getServiceUrl(String symbol);

	public void removeUrl(String symbol, String url);

	public void addtUrl(String symbol, String url);

	public void addtUrlS(String symbol, Set<String> localUrls);

}
