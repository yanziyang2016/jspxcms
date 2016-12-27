package com.jspxcms.core.security;



public class RedisUtil {
	 public static void main(String args[]){
	    	RedisHelperTest redisHelper = new RedisHelperTest();
	        redisHelper.addr = "182.92.7.57";
	        redisHelper.port = "6379";
	        redisHelper.auth = "test123";
	        redisHelper.set("222", "test");
	    	
	    }
}
