package com.jspxcms.core.domain;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;


import net.sf.json.JSONObject;



public class TestJson {
//	public static Gson gsonBuilderWithExpose = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
//			.setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	public static void main(String[] args) throws Exception {

//		String json = "{\"name\":\"reiz\"}";
//	    JSONObject jsonObj = JSONObject.fromObject(json);
//	    System.out.println(jsonObj.getString("name"));
	    String videoData = "";
//	    videoData= 	GetLocalFile.readTxtFileNoSpace("新闻.txt");
	    videoData= 	GetLocalFile.readTxtFileNoSpace("电视.txt");
		Map<String, Class> classMap = new HashMap<String, Class>();  
		  
		classMap.put("r", VideoTwo.class);  
		classMap.put("videos", VideoThree.class);
		classMap.put("urls", VideoFour.class);
//		String dianshiURL="http://info.lm.tv.sohu.com/a.do?qd=12441&c=101&p=1&s=20&inc=1";
		
//	    JSONObject videoOnejsonObj = JSONObject.fromObject(getUrlContentTest(dianshiURL));
		JSONObject videoOnejsonObj = JSONObject.fromObject(videoData);
		VideoOne videoOne =(VideoOne)videoOnejsonObj.toBean(videoOnejsonObj,VideoOne.class,classMap);//指定转换的类型，但仍需要强制转化-成功
		System.out.println(videoOne.getC());
		System.out.println(videoOne.getR().get(0).getVideos().get(0).getDomain());
		System.out.println(videoOne.getR().get(0).getVideos().get(0).getUrls().get(0).getUrl());
	}
	
	public static String getUrlContentTest( String urlStr) throws Exception {
	    String httpUrl =  urlStr;
	    System.out.println("request httpurl is " + httpUrl);
	    CloseableHttpClient client = HttpClientBuilder.create().build();
	    HttpGet get = new HttpGet(httpUrl);
	    String result = null;
	    HttpResponse response = null;



//	    logger.info("http submit get reuqest, call http interface, execute command");
	    response = client.execute(get);
//	    logger.info("http submit get reuqest, call http interface, get feedback data : " + response);

	    HttpEntity entity = response.getEntity();
	    if(entity != null && entity.toString() != "") {
	        result = EntityUtils.toString(entity, "UTF-8");
//	        logger.info("Json String feedback data : " + result);
	        return result;
	    } else {
	        throw new Exception("数据为空");
	    }
	}
	

}
