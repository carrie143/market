package com.gop.fork;

import com.gop.domain.ForkUserReceiveCandy;
import com.gop.domain.enums.ForkUserReceiveCandyStatus;
/**
 * 
 * @author sunhaotian
 *
 */

public interface ForkUserReceiveCandyService {

	public ForkUserReceiveCandy getForkUserReceiveCandyByUidAndAssetCode(Integer uid,String forkAssetCode,String targetAssetCode);
	
	public void receiveForkUserReceiveCandy(Integer uid,String forkAssetCode,String targetAssetCode);  
	
	public int updateForkUserReceiveCandyStatusById(Integer id,ForkUserReceiveCandyStatus beginStatus,ForkUserReceiveCandyStatus endStatus);
}
