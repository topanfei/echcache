package com.pf.ehcache;

import java.util.List;

import com.pf.entity.User;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EhcacheUtil {

	public static void main(String[] args) {
		//1.创建缓存管理器
		CacheManager cacheManager = CacheManager.create(EhcacheUtil.class.getClassLoader().getResourceAsStream("ehcache.xml"));
		//2.获取缓存对象
		Cache cache = cacheManager.getCache("myCache");
		//3.创建元素
		Element element = new Element("name", "张三");
		//4.将创建的元素添加到缓存
		cache.put(element);
		//5.获取缓存
		System.out.println("缓存的大小="+cache.getSize());
		Element targetElement = cache.get("name");
		System.out.println("targetElement="+targetElement);
		System.out.println("value="+targetElement.getObjectValue());
		//6.删除元素
		cache.remove("name");
		
		//7.重新添加几个元素
		User u1 = new User(1001, "张三");
		User u2 = new User(1002, "李四");
		Element e1 = new Element("u1", u1);
		Element e2 = new Element("u2", u2);
		cache.put(e1);
		cache.put(e2);
		System.out.println("缓存的大小="+cache.getSize());
		System.out.println("开始循环缓存中的元素...");
		//获取缓存中的值
		@SuppressWarnings("rawtypes")
		List keys = cache.getKeys();
		if(keys != null) {
			for (Object key : keys) {
				Element tempElement = cache.get(key);
				User user =(User) tempElement.getObjectValue();
				System.out.println("key="+key.toString());
				System.out.println("value="+user.toString());
			}
		}
		
		//8.刷新缓存
		cache.flush();
		
		//9.关闭缓存管理器
		cacheManager.shutdown();
	}
}
