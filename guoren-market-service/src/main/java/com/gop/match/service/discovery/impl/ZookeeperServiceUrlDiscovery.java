//package com.gop.match.service.discovery.impl;
//
//import java.util.List;
//import java.util.Set;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.TimeUnit;
//import java.util.function.Consumer;
//import java.util.stream.Collector;
//import java.util.stream.Collectors;
//
//import javax.annotation.PreDestroy;
//
//import org.apache.curator.framework.CuratorFramework;
//import org.apache.curator.framework.CuratorFrameworkFactory;
//import org.apache.curator.framework.recipes.cache.ChildData;
//import org.apache.curator.framework.recipes.cache.TreeCache;
//import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
//import org.apache.curator.framework.recipes.cache.TreeCacheListener;
//import org.apache.curator.retry.ExponentialBackoffRetry;
//import org.apache.curator.utils.CloseableUtils;
//import org.apache.zookeeper.CreateMode;
//
//import com.google.common.base.Joiner;
//import com.google.common.base.Splitter;
//import com.google.common.base.Throwables;
//import com.gop.code.consts.CommonCodeConst;
//import com.gop.config.ZkGlobalConstants;
//import com.gop.exception.AppException;
//import com.gop.match.service.discovery.ListenerCallBack;
//import com.gop.match.service.discovery.ServiceUrlDiscovery;
//
//import lombok.Data;
//import lombok.Setter;
//import lombok.extern.slf4j.Slf4j;
//
////TODO:建立zookeeper连接，监听节点，实现getFromDiscovery方法
//@Slf4j
//public class ZookeeperServiceUrlDiscovery implements ServiceUrlDiscovery {
//
//	private Set<ListenerCallBack> onAddCallBack = ConcurrentHashMap.newKeySet();
//	private Set<ListenerCallBack> onRemoveCallBack = ConcurrentHashMap.newKeySet();
//	@Setter
//	private String urls;
//	CuratorFramework curatorFramework;
//	private TreeCache treeCache;
//
//	public void init() {
//		if (urls == null) {
//			throw new IllegalArgumentException("url not null");
//		}
//		String parentZnodePath = ZkGlobalConstants.PARENT_NODE_PATH;
//
//		curatorFramework = CuratorFrameworkFactory.builder().connectString(urls)
//				.retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
//
//		curatorFramework.start();
//
//		try {
//			if (!curatorFramework.blockUntilConnected(1000 * 3, TimeUnit.MILLISECONDS)) {
//				curatorFramework.close();
//				throw new AppException(CommonCodeConst.SERVICE_ERROR, "初始化服务注册异常");
//			}
//		} catch (InterruptedException e1) {
//
//			log.error("连接zk超时");
//			throw new AppException(CommonCodeConst.SERVICE_ERROR, "初始化服务注册异常，连接zk超时");
//		}
//
//		treeCache = new TreeCache(curatorFramework, parentZnodePath);
//		// 设置监听器和处理过程
//		treeCache.getListenable().addListener(new TreeCacheListener() {
//			@Override
//			public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
//				ChildData data = event.getData();
//				ZookeeperNode zookeeperNode = null;
//
//				if (data != null) {
//					log.info("recv info :{}", data.getPath());
//					switch (event.getType()) {
//					case NODE_ADDED:
//
//						zookeeperNode = getNode(data.getPath());
//						if (null == zookeeperNode) {
//							return;
//						}
//						notifyAdd(zookeeperNode.getSymbol(), zookeeperNode.getUrl());
//						break;
//					case NODE_REMOVED:
//						zookeeperNode = getNode(data.getPath());
//						if (null == zookeeperNode) {
//							return;
//						}
//						notifyRemove(zookeeperNode.getSymbol(), zookeeperNode.getUrl());
//						break;
//					case NODE_UPDATED:
//
//						break;
//
//					default:
//						break;
//					}
//				} else {
//
//				}
//			}
//		});
//		// 开始监听
//		try {
//			treeCache.start();
//		} catch (Exception e) {
//			throw new AppException(CommonCodeConst.SERVICE_ERROR, "初始化监听异常");
//		}
//
//	}
//
//	@Data
//	class ZookeeperNode {
//		private String symbol;
//		private String url;
//	}
//
//	private ZookeeperNode getNode(String path) {
//		ZookeeperNode node = new ZookeeperNode();
//		List<String> paths = Splitter.on(ZkGlobalConstants.SEPARATOR).trimResults().omitEmptyStrings()
//				.splitToList(path);
//		if (paths.size() == 3) {
//			node.setSymbol(paths.get(1));
//			node.setUrl(ZkGlobalConstants.transfer2HttpUrl(paths.get(2)));
//		} else {
//			return null;
//		}
//
//		return node;
//	}
//
//	@Override
//	public Set<String> getFromDiscovery(String symbol) {
//
//		String path = Joiner.on(ZkGlobalConstants.SEPARATOR).join(ZkGlobalConstants.PARENT_NODE_PATH, symbol);
//		try {
//			return curatorFramework.getChildren().forPath(path).stream()
//					.map(url -> ZkGlobalConstants.transfer2HttpUrl(url)).collect(Collectors.toSet());
//		} catch (Exception e) {
//			log.error("服务发现异常：{}", e);
//		}
//		return null;
//	}
//
//	@Override
//	public void onAddEvent(ListenerCallBack listenerCallBack) {
//
//		onAddCallBack.add(listenerCallBack);
//	}
//
//	@Override
//	public void OnRemoveEvent(ListenerCallBack listenerCallBack) {
//
//		onRemoveCallBack.add(listenerCallBack);
//	}
//
//	public void notifyAdd(String symbol, String url) {
//		onAddCallBack.forEach(new Consumer<ListenerCallBack>() {
//
//			@Override
//			public void accept(ListenerCallBack t) {
//				t.callBack(symbol, url);
//
//			}
//		});
//	}
//
//	public void notifyRemove(String symbol, String url) {
//		onRemoveCallBack.forEach(new Consumer<ListenerCallBack>() {
//
//			@Override
//			public void accept(ListenerCallBack t) {
//				t.callBack(symbol, url);
//			}
//		});
//	}
//
//	@PreDestroy
//	public void shutdown() {
//		CloseableUtils.closeQuietly(treeCache);
//		CloseableUtils.closeQuietly(curatorFramework);
//	}
//
//}
