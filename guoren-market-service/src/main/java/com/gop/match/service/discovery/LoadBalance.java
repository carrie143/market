package com.gop.match.service.discovery;

import java.util.Set;

public interface LoadBalance {

	public String loadBalance(Set<String> urls);

}
