package com.gop.common;

public interface CheckCodeService {

	public String SaveUserSendCode(String userName);

	public String SaveUserSendCode(int uid, String account);

	public String getUserAccount(int uid);

	public boolean checkUserCode(String userName, String code);

	public boolean checkUserCode(int uid, String code);

	public void delete(Integer userId);

	public void delete(String userAccount);

}
