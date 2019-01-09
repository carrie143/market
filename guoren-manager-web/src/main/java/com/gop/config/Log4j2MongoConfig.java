package com.gop.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.gop.code.consts.CommonCodeConst;
import com.gop.exception.AppException;
import com.gop.util.ResourceUtils;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Log4j2MongoConfig {

	private static String hostports;
	private static String maxConnect;
	private static String maxWaitThread;
	private static String maxTimeOut;
	private static String maxWaitTime;
	private static String username;
	private static String password;
	private static String database;

	public static MongoClient mongoClient() {
		MongoClient mongoClient = null;
		try {
			
			hostports = ResourceUtils.get("mongodbForLog","mongodb.hostports");
			username = ResourceUtils.get("mongodbForLog","mongodb.username");
			password = ResourceUtils.get("mongodbForLog","mongodb.password");
			database = ResourceUtils.get("mongodbForLog","mongodb.database");
			maxConnect = ResourceUtils.get("mongodbForLog","mongodb.maxConnect");
			maxWaitThread = ResourceUtils.get("mongodbForLog","mongodb.maxWaitThread");
			maxTimeOut = ResourceUtils.get("mongodbForLog","mongodb.maxTimeOut");
			maxWaitTime = ResourceUtils.get("mongodbForLog","mongodb.maxWaitTime");
			

			MongoClientOptions.Builder build = new MongoClientOptions.Builder();
			build.connectionsPerHost(Integer.valueOf(maxConnect));
			build.threadsAllowedToBlockForConnectionMultiplier(Integer.valueOf(maxWaitThread));
			build.connectTimeout(Integer.valueOf(maxTimeOut) * 1000);
			build.maxWaitTime(Integer.valueOf(maxWaitTime) * 1000);
			MongoClientOptions options = build.build();

			List<ServerAddress> addrs = new ArrayList<ServerAddress>();
			List<MongoCredential> credentials = new ArrayList<MongoCredential>();
			for (String hostport : hostports.split(",")) {
				if (StringUtils.isBlank(hostport)) {
					continue;
				}
				hostport = hostport.trim();

				ServerAddress serverAddress = new ServerAddress(hostport.split(":")[0],
						Integer.valueOf(hostport.split(":")[1]));
				addrs.add(serverAddress);
				MongoCredential credential = MongoCredential.createScramSha1Credential(username, database,
						password.toCharArray());
				credentials.add(credential);
			}

			mongoClient = new MongoClient(addrs, credentials, options);

			log.info("mongodb client: mongodb客户端创建成功");
		} catch (Exception e) {
			log.error("mongodb client: mongodb客户端创建失败",e);
			throw new AppException(CommonCodeConst.SERVICE_ERROR,"获取mongoClient失败");
		}
		return mongoClient;
	}

}
