package com.jspxcms.core.web.back;

import java.util.Date;
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
import org.springframework.data.domain.PageRequest;
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
import com.jspxcms.core.domain.VideoList;
import com.jspxcms.core.domain.VideoOne;
import com.jspxcms.core.domain.VideoResult;
import com.jspxcms.core.domain.VideoThree;
import com.jspxcms.core.domain.VideoTwo;
import com.jspxcms.core.service.VideoFourService;
import com.jspxcms.core.service.VideoResultService;
import com.jspxcms.core.service.VideoTwoService;
//import com.jspxcms.core.web.fore.NodeVideoController;

import antlr.collections.List;
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
		
		try {
//			 String videoData = "";
//			    videoData= 	GetLocalFile.readTxtFileNoSpace(id+".txt");
				Map<String, Class> classMap = new HashMap<String, Class>();  
				  
				classMap.put("r", VideoTwo.class);  
				classMap.put("videos", VideoThree.class);
				classMap.put("urls", VideoFour.class);
				String[] videoURLArr=new String[6];
				videoURLArr[0] = "http://info.lm.tv.sohu.com/a.do?qd=12441&c=101&p=1&s=2000&inc=1";
				videoURLArr[1] = "http://info.lm.tv.sohu.com/a.do?qd=12441&c=115&p=1&s=2000&inc=1";
				videoURLArr[2] = "http://info.lm.tv.sohu.com/a.do?qd=12441&c=106&p=1&s=2000&inc=1";
				videoURLArr[3] = "http://info.lm.tv.sohu.com/a.do?qd=12441&c=121&p=1&s=2000&inc=1";
				videoURLArr[4] = "http://info.lm.tv.sohu.com/a.do?qd=12441&c=122&p=1&s=2000&inc=1";
				videoURLArr[5] = "http://info.lm.tv.sohu.com/a.do?qd=12441&c=112&p=1&s=2000&inc=1";
				int c = 0;
				for(int i=0;i<videoURLArr.length;i++){
				    JSONObject videoOnejsonObj = JSONObject.fromObject(getUrlContentTest(videoURLArr[i]));
//					JSONObject videoOnejsonObj = JSONObject.fromObject(videoData);
					VideoOne videoOne =(VideoOne)videoOnejsonObj.toBean(videoOnejsonObj,VideoOne.class,classMap);//指定转换的类型，但仍需要强制转化-成功
					if(videoOne!=null){
						c  = c + Integer.valueOf(videoOne.getC());
						for(VideoTwo videoTwo:videoOne.getR()){
							if(videoTwo.getCname().equals("电视剧")||videoTwo.getCname().equals("综艺")||videoTwo.getCname().equals("动漫")){
								if(videoTwoService.getbyAid(videoTwo.getAid()).size()>0){
									videoTwo.setOneClassifyId(-1);
									videoTwo.setTwoClassifyId(-1);
									videoTwoService.save(videoTwo);
								}else{
									c--;
								}
							}else{
								videoTwoService.save(videoTwo);
							}
							
							if(videoTwo.getVideos()!=null&&videoTwo.getVideos().size()>0){
								for(VideoThree videoThree:videoTwo.getVideos()){
									for(VideoFour videoFour:videoThree.getUrls()){
										if(videoFour.getName().length()<4){
											if(videoFourService.getbyvid(videoFour.getVid()).size()>0){
												videoFourService.save(videoFour);	
											}
											
										}
										
									}
								}
							}
						}
					}					
					
				}
				
//				videoMain();
//				videoList();

				VideoResult videoResult = new VideoResult();
				videoResult.setCount(c);
				videoResult.setResult(1);
				videoResult.setImportDate(new Date());
				service.save(videoResult);
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
			 String videoData = "";
			    videoData= 	GetLocalFile.readTxtFileNoSpace(id+".txt");
				Map<String, Class> classMap = new HashMap<String, Class>();  
				  
				classMap.put("r", VideoTwo.class);  
				classMap.put("videos", VideoThree.class);
				classMap.put("urls", VideoFour.class);
//				String videoURL="";
				
				JSONObject videoOnejsonObj = JSONObject.fromObject(videoData);
				VideoOne videoOne =(VideoOne)videoOnejsonObj.toBean(videoOnejsonObj,VideoOne.class,classMap);//指定转换的类型，但仍需要强制转化-成功
				for(VideoTwo videoTwo:videoOne.getR()){
						videoTwoService.save(videoTwo);
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
	
//	public  void videoMain(){
//		Pageable pageable = new PageRequest(0, 4,Direction.DESC, "vmid");  
//		Map<String, String[]> params = new HashMap<String, String[]>();
//		String[] ds ={"电视剧"};
//		String[] zy ={"综艺"};
//		String[] yl ={"娱乐"};
//		String[] yy ={"音乐"};
//		String[] xw ={"新闻"};
//		String[] dm ={"动漫"};
//		params.put("CONTAIN_cname", ds);			
//		Page<VideoTwo> pagedList = videoTwoService.findPage( params, pageable);
//		NodeVideoController.videoMain.setDsList(pagedList.getContent());
//		
//		params.clear();			
//		params.put("CONTAIN_cname", zy);
//		pagedList = videoTwoService.findPage( params, pageable);
//		NodeVideoController.videoMain.setZyList(pagedList.getContent());
//		
//		params.clear();			
//		params.put("CONTAIN_cname", yl);
//		pagedList = videoTwoService.findPage( params, pageable);
//		NodeVideoController.videoMain.setYlList(pagedList.getContent());
//		
//		params.clear();			
//		params.put("CONTAIN_cname", yy);
//		pagedList = videoTwoService.findPage( params, pageable);
//		NodeVideoController.videoMain.setYyList(pagedList.getContent());
//		
//		params.clear();			
//		params.put("CONTAIN_cname", xw);
//		pagedList = videoTwoService.findPage( params, pageable);
//		NodeVideoController.videoMain.setXwList(pagedList.getContent());
//		
//		params.clear();			
//		params.put("CONTAIN_cname", dm);
//		pagedList = videoTwoService.findPage( params, pageable);
//		NodeVideoController.videoMain.setDmList(pagedList.getContent());
//		
//	}
//	
//	public  void videoList(){
//		
//		
//		Pageable pageable = new PageRequest(0, 20,Direction.DESC, "vmid");  
//		Map<String, String[]> params = new HashMap<String, String[]>();
//		params.put("CONTAIN_cname", new String[]{"电视剧"});	
//		Page<VideoTwo> pagedList = videoTwoService.findPage( params, pageable);
//		VideoList videoListDs = new VideoList();
//		videoListDs.setCurrpage(0);
//		videoListDs.setIsfirst(1);
//		if(pagedList.getTotalPages()<2){
//			videoListDs.setIslast(1);
//		}else{
//			videoListDs.setIslast(0);
//		}
//		videoListDs.setTotalcount(pagedList.getTotalElements());
//		videoListDs.setTotalpage(pagedList.getTotalPages());
//		videoListDs.setVideoList(pagedList.getContent());
//		NodeVideoController.videoListMap.put("电视剧", videoListDs);
//		
//		VideoList videoListYl = new VideoList();
//		params.clear();
//		params.put("CONTAIN_cname", new String[]{"娱乐"});	
//		pagedList = videoTwoService.findPage( params, pageable);
//		if(pagedList.getTotalPages()<2){
//			videoListYl.setIslast(1);
//		}else{
//			videoListYl.setIslast(0);
//		}
//		videoListYl.setTotalcount(pagedList.getTotalElements());
//		videoListYl.setTotalpage(pagedList.getTotalPages());
//		videoListYl.setVideoList(pagedList.getContent());
//		NodeVideoController.videoListMap.put("娱乐", videoListYl);
//		
//		VideoList videoListXw = new VideoList();
//		params.clear();
//		params.put("CONTAIN_cname", new String[]{"新闻"});	
//		pagedList = videoTwoService.findPage( params, pageable);
//		if(pagedList.getTotalPages()<2){
//			videoListXw.setIslast(1);
//		}else{
//			videoListXw.setIslast(0);
//		}
//		videoListXw.setTotalcount(pagedList.getTotalElements());
//		videoListXw.setTotalpage(pagedList.getTotalPages());
//		videoListXw.setVideoList(pagedList.getContent());
//		NodeVideoController.videoListMap.put("新闻", videoListXw);
//		
//		VideoList videoListYy = new VideoList();
//		params.clear();
//		params.put("CONTAIN_cname", new String[]{"音乐"});	
//		pagedList = videoTwoService.findPage( params, pageable);
//		if(pagedList.getTotalPages()<2){
//			videoListYy.setIslast(1);
//		}else{
//			videoListYy.setIslast(0);
//		}
//		videoListYy.setTotalcount(pagedList.getTotalElements());
//		videoListYy.setTotalpage(pagedList.getTotalPages());
//		videoListYy.setVideoList(pagedList.getContent());
//		NodeVideoController.videoListMap.put("音乐", videoListYy);
//		
//		VideoList videoListDm= new VideoList();
//		params.clear();
//		params.put("CONTAIN_cname", new String[]{"动漫"});	
//		pagedList = videoTwoService.findPage( params, pageable);
//		if(pagedList.getTotalPages()<2){
//			videoListDm.setIslast(1);
//		}else{
//			videoListDm.setIslast(0);
//		}
//		videoListDm.setTotalcount(pagedList.getTotalElements());
//		videoListDm.setTotalpage(pagedList.getTotalPages());
//		videoListDm.setVideoList(pagedList.getContent());
//		NodeVideoController.videoListMap.put("动漫", videoListDm);
//		
//		VideoList videoListZy= new VideoList();
//		params.clear();
//		params.put("CONTAIN_cname", new String[]{"综艺"});	
//		pagedList = videoTwoService.findPage( params, pageable);
//		if(pagedList.getTotalPages()<2){
//			videoListZy.setIslast(1);
//		}else{
//			videoListZy.setIslast(0);
//		}
//		videoListZy.setTotalcount(pagedList.getTotalElements());
//		videoListZy.setTotalpage(pagedList.getTotalPages());
//		videoListZy.setVideoList(pagedList.getContent());
//		NodeVideoController.videoListMap.put("综艺", videoListZy);
//		
//	}
	
	public static void main(String args[]){
		
		int c = 0 ;
		c--;
		System.out.println(c);
		
	}
	
	
}
