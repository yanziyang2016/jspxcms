package com.jspxcms.core.web.back;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jspxcms.common.web.Servlets;
import com.jspxcms.core.constant.Constants;
import com.jspxcms.core.domain.GetLocalFile;
import com.jspxcms.core.domain.VideoFour;
import com.jspxcms.core.domain.VideoOne;
import com.jspxcms.core.domain.VideoResult;
import com.jspxcms.core.domain.VideoThree;
import com.jspxcms.core.domain.VideoTwo;
import com.jspxcms.core.service.VideoFourService;
import com.jspxcms.core.service.VideoResultService;
import com.jspxcms.core.service.VideoTwoService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/core/video_result")
public class VideoResultController {
	private static final Logger logger = LoggerFactory.getLogger(VideoResultController.class);
	private static ObjectMapper objectMapper = new ObjectMapper();
	@RequiresPermissions("core:video_result:list")
	@RequestMapping("list.do")
	public String list(@PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable,
			HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Map<String, String[]> params = Servlets.getParamValuesMap(request, Constants.SEARCH_PREFIX);
		logger.info("VideoResult --- params"+params.toString());
		Page<VideoResult> pagedList = service.findPage( params, pageable);
		modelMap.addAttribute("pagedList", pagedList);
		return "core/video_result/video_result_list";
	}

	@RequestMapping("videocollect.do")
	@ResponseBody
	public String videoCollect(@RequestParam int id) {
		logger.info("videoCollect --- id---"+id);
		try {
			 String videoData = "";
			    videoData= 	GetLocalFile.readTxtFileNoSpace(id+".txt");
				Map<String, Class> classMap = new HashMap<String, Class>();  
				  
				classMap.put("r", VideoTwo.class);  
				classMap.put("videos", VideoThree.class);
				classMap.put("urls", VideoFour.class);
//				String videoURL="";
				
//			    JSONObject videoOnejsonObj = JSONObject.fromObject(getUrlContentTest(videoURL));
				JSONObject videoOnejsonObj = JSONObject.fromObject(videoData);
				VideoOne videoOne =(VideoOne)videoOnejsonObj.toBean(videoOnejsonObj,VideoOne.class,classMap);//指定转换的类型，但仍需要强制转化-成功
				
				for(VideoTwo videoTwo:videoOne.getR()){
					VideoTwo temptwo = videoTwoService.getbyid(videoTwo.getId());
					if(temptwo==null){
						videoTwoService.save(videoTwo);
					}
					if(videoTwo.getVideos()!=null&&videoTwo.getVideos().size()>0){
						for(VideoThree videoThree:videoTwo.getVideos()){
							for(VideoFour videoFour:videoThree.getUrls()){
								if(videoFour.getName().length()<4){
									VideoFour tempfour = videoFourService.getbyvid(videoFour.getVid());
									if(tempfour==null){
										videoFourService.save(videoFour);	
									}
									
								}
								
							}
						}
					}
				}
//				VideoResult videoResult = new VideoResult();
//				videoResult.setCount(100);
//				videoResult.setResult(1);
//				videoResult.setImportDate(new Date());
//				service.save(videoResult);
				return toJson("success");
		} catch (Exception e) {
			e.printStackTrace();
			return toJson("fail");
		}
	}
	
	
	@RequestMapping("videocollectall.do")
	@ResponseBody
	public String videoCollectall(@RequestParam int id,@RequestParam int s,@RequestParam int istart) {
		
		try {
				Map<String, Class> classMap = new HashMap<String, Class>();  
				  
				classMap.put("r", VideoTwo.class);  
				classMap.put("videos", VideoThree.class);
				classMap.put("urls", VideoFour.class);
				String videoURL="";
				videoURL="http://info.lm.tv.sohu.com/a.do?qd=12441&c="+id+"&p=1&s=20&inc=0";
			    JSONObject videoOnejsonObj = JSONObject.fromObject(getUrlContentTest(videoURL));
				VideoOne videoOne =(VideoOne)videoOnejsonObj.toBean(videoOnejsonObj,VideoOne.class,classMap);//指定转换的类型，但仍需要强制转化-成功
				int p = Integer.valueOf(videoOne.getC())/s+2;
				logger.info("videocollectall --- id---"+id+"---s---"+s+"---p---"+p);
				for(int i=istart;i<p;i++){
					videoURL="http://info.lm.tv.sohu.com/a.do?qd=12441&c="+id+"&p="+i+"&s="+s+"&inc=0";
					videoOnejsonObj = JSONObject.fromObject(getUrlContentTest(videoURL));
					videoOne =(VideoOne)videoOnejsonObj.toBean(videoOnejsonObj,VideoOne.class,classMap);//指定转换的类型，但仍需要强制转化-成功
					
					for(VideoTwo videoTwo:videoOne.getR()){
							videoTwoService.save(videoTwo);
							if(videoTwo.getVideos()!=null&&videoTwo.getVideos().size()>0){
								for(VideoThree videoThree:videoTwo.getVideos()){
									for(VideoFour videoFour:videoThree.getUrls()){
										if(videoFour.getName().length()<4){
												videoFourService.save(videoFour);	
										}
										
									}
								}
							}
					}
				}
				
				return toJson("success");
		} catch (Exception e) {
			e.printStackTrace();
			return toJson("fail");
		}
	}
	
	
	
	public static String getUrlContentTest( String urlStr) throws Exception {
	    String httpUrl =  urlStr;
	    logger.info("request httpurl is " + httpUrl);
	    CloseableHttpClient client = HttpClientBuilder.create().build();
	    HttpGet get = new HttpGet(httpUrl);
	    String result = null;
	    HttpResponse response = null;
	    response = client.execute(get);

	    HttpEntity entity = response.getEntity();
	    if(entity != null && entity.toString() != "") {
	        result = EntityUtils.toString(entity, "UTF-8");
	        return result;
	    } else {
	        throw new Exception("数据为空");
	    }
	}

	 public static String toJson(Object obj)
	   {
	     try
	     {
	       return objectMapper.writeValueAsString(obj); } catch (Exception e) {
	     }
	     return null;
	   }
	 


	@Autowired
	private VideoResultService service;
	@Autowired
	private VideoTwoService videoTwoService;
	@Autowired
	private VideoFourService videoFourService;
	
	
}
