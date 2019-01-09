package com.gop.user.facade;

public interface AdministratorsFacade {
	

	/**
	 * 校验用户登录密码
	 * 
	 * @return
	 */
	public boolean checkLoginPwd(String account, String pwd);

	public boolean checkLoginPwd(int uid, String loginPassword);
	
	/**
	 * 校验用户谷歌验证码
	 * @return
	 */
	public void checkManagerGoogleCode(Integer adminId, String mobile, String code);
}
