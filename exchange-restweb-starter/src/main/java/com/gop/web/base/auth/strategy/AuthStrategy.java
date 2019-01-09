package com.gop.web.base.auth.strategy;

import com.gop.web.base.auth.AuthContext;

public interface AuthStrategy {

	public void pre(AuthContext authContext);

	public void after(AuthContext authContext, Throwable throwable);

	public boolean match(AuthContext authContext);
}
