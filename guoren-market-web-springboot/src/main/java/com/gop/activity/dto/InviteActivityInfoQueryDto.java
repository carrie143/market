package com.gop.activity.dto;

import java.util.List;

import com.gop.domain.InviteActivityRewardConfig;
import com.gop.domain.InviteUserRewardRecord;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InviteActivityInfoQueryDto {
	
	private String inviteCode;
	
	private List<InviteUserRewardRecord> inviteUserRewardRecords;
	
	private List<InviteActivityRewardConfig> inviteActivityRewardConfigs;
	
	private Integer inviteCount;
	
	private boolean isReceiveReward;
	
}
