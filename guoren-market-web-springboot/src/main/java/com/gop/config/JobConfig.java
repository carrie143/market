//package com.gop.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
//import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
//import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
//import com.gop.config.properties.ZookeeperConfig;
//
//@Configuration
//@EnableConfigurationProperties(ZookeeperConfig.class)
//public class JobConfig {
//	@Autowired
//	private ZookeeperConfig ZookeeperConfig;
//
//	@Bean(value="regCenter")
//	public CoordinatorRegistryCenter createRegistryCenter() {
//		CoordinatorRegistryCenter regCenter = new ZookeeperRegistryCenter(
//				new ZookeeperConfiguration(ZookeeperConfig.getNameServcers(), "checkSendJob"));
//		regCenter.init();
//		return regCenter;
//	}
//
//}
