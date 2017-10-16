//package com.jspxcms.core.web.fore;
//
//import java.io.UnsupportedEncodingException;
//import java.net.URLDecoder;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort.Direction;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.jspxcms.common.web.Servlets;
//import com.jspxcms.common.web.Validations;
//import com.jspxcms.core.constant.Constants;
//import com.jspxcms.core.domain.Info;
//import com.jspxcms.core.domain.InfoDetail;
//import com.jspxcms.core.domain.Node;
//import com.jspxcms.core.domain.Org;
//import com.jspxcms.core.domain.Site;
//import com.jspxcms.core.domain.User;
//import com.jspxcms.core.domain.VideoFour;
//import com.jspxcms.core.domain.VideoList;
//import com.jspxcms.core.domain.VideoMain;
//import com.jspxcms.core.domain.VideoTwo;
//import com.jspxcms.core.service.InfoDetailService;
//import com.jspxcms.core.service.InfoQueryService;
//import com.jspxcms.core.service.NodeBufferService;
//import com.jspxcms.core.service.NodeQueryService;
//import com.jspxcms.core.service.SiteService;
//import com.jspxcms.core.service.VideoFourService;
//import com.jspxcms.core.service.VideoTwoService;
//import com.jspxcms.core.support.Context;
//import com.jspxcms.core.support.ForeContext;
//import com.jspxcms.core.support.Response;
//import com.jspxcms.core.support.SiteResolver;
//import com.jspxcms.core.web.back.UserController;
//import com.jspxcms.core.web.back.VideoResultController;
//
///**
// * NodeController
// * 
// * @author liufang
// * 
// */
//@Controller
//public class NodeVideoController {
//	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
//	public static VideoMain videoMain = new VideoMain() ;
//	public static Map<String, VideoList> videoListMap = new HashMap<String, VideoList>();
//	//主页工程使用
//	@RequestMapping(value = { "/", "/index.jspx" })
//	public String index(HttpServletRequest request,
//			HttpServletResponse response, org.springframework.ui.Model modelMap) {
//		
//		if(videoMain.getDmList()==null||videoMain.getDmList().size()==0){			
//			videoMain();			
//		}
//		
//		logger.info("NodeVideoController---index1---");
//		return index(null, request, response, modelMap);
//	}
//	//主页工程使用
//	@RequestMapping(value = Constants.SITE_PREFIX_PATH + ".jspx")
//	public String index(@PathVariable String siteNumber,
//			HttpServletRequest request, HttpServletResponse response,
//			org.springframework.ui.Model modelMap) {
//		if(!videoListMap.containsKey("电视剧")){
//			videoList();
//		}
//		
//		logger.info("NodeVideoController---index2---");
//		siteResolver.resolveSite(siteNumber);
//		Site site = Context.getCurrentSite();
//		Node node = query.findRoot(site.getId());
//		modelMap.addAttribute("node", node);
//		modelMap.addAttribute("text", node.getText());
//
//		ForeContext.setData(modelMap.asMap(), request);
//		return "/1/default/indexVideo.html";
//	}
//	
//		@RequestMapping(value = { "/dsl{p:[0-9]+}.jspx" })
//		public String dslist(HttpServletRequest request,@PathVariable Integer p,
//				HttpServletResponse response, org.springframework.ui.Model modelMap) {
//			logger.info("NodeVideoController---dslist1---"+p);
//			return dslist(null, p,request, response, modelMap);
//		}
//		@RequestMapping(value = Constants.SITE_PREFIX_PATH + "/dsl{p:[0-9]+}.jspx")
//		public String dslist(@PathVariable String siteNumber,@PathVariable Integer p,
//				HttpServletRequest request, HttpServletResponse response,
//				org.springframework.ui.Model modelMap) {
//			logger.info("NodeVideoController---dslist2---"+p);
//			siteResolver.resolveSite(siteNumber);
//			Site site = Context.getCurrentSite();
//			Node node = query.findRoot(site.getId());
//			modelMap.addAttribute("node", node);
//			modelMap.addAttribute("text", node.getText());
//			modelMap.addAttribute("type", "电视剧");
//			ForeContext.setData(modelMap.asMap(), request);
//			return "/1/default/list_video_new.html";
//		}
//		
//		@RequestMapping(value = { "/yll{p:[0-9]+}.jspx" })
//		public String yllist(HttpServletRequest request,@PathVariable Integer p,
//				HttpServletResponse response, org.springframework.ui.Model modelMap) {
//			logger.info("NodeVideoController---yllist1---"+p);
//			return yllist(null, p,request, response, modelMap);
//		}
//		@RequestMapping(value = Constants.SITE_PREFIX_PATH + "/yll{p:[0-9]+}.jspx")
//		public String yllist(@PathVariable String siteNumber,@PathVariable Integer p,
//				HttpServletRequest request, HttpServletResponse response,
//				org.springframework.ui.Model modelMap) {
//			logger.info("NodeVideoController---yllist2---"+p);
//			siteResolver.resolveSite(siteNumber);
//			Site site = Context.getCurrentSite();
//			Node node = query.findRoot(site.getId());
//			modelMap.addAttribute("node", node);
//			modelMap.addAttribute("text", node.getText());
//			modelMap.addAttribute("type", "娱乐");
//			ForeContext.setData(modelMap.asMap(), request);
//			return "/1/default/list_video_new.html";
//		}
//		
//		
//		@RequestMapping(value = { "/zyl{p:[0-9]+}.jspx" })
//		public String zylist(HttpServletRequest request,@PathVariable Integer p,
//				HttpServletResponse response, org.springframework.ui.Model modelMap) {
//			logger.info("NodeVideoController---zylist1---"+p);
//			return zylist(null, p,request, response, modelMap);
//		}
//		@RequestMapping(value = Constants.SITE_PREFIX_PATH + "/zyl{p:[0-9]+}.jspx")
//		public String zylist(@PathVariable String siteNumber,@PathVariable Integer p,
//				HttpServletRequest request, HttpServletResponse response,
//				org.springframework.ui.Model modelMap) {
//			logger.info("NodeVideoController---zylist2---"+p);
//			siteResolver.resolveSite(siteNumber);
//			Site site = Context.getCurrentSite();
//			Node node = query.findRoot(site.getId());
//			modelMap.addAttribute("node", node);
//			modelMap.addAttribute("text", node.getText());
//			modelMap.addAttribute("type", "综艺");
//			ForeContext.setData(modelMap.asMap(), request);
//			return "/1/default/list_video_new.html";
//		}
//		
//		
//		@RequestMapping(value = { "/yyl{p:[0-9]+}.jspx" })
//		public String yylist(HttpServletRequest request,@PathVariable Integer p,
//				HttpServletResponse response, org.springframework.ui.Model modelMap) {
//			logger.info("NodeVideoController---yylist1---"+p);
//			return yylist(null, p,request, response, modelMap);
//		}
//		@RequestMapping(value = Constants.SITE_PREFIX_PATH + "/yyl{p:[0-9]+}.jspx")
//		public String yylist(@PathVariable String siteNumber,@PathVariable Integer p,
//				HttpServletRequest request, HttpServletResponse response,
//				org.springframework.ui.Model modelMap) {
//			logger.info("NodeVideoController---yylist2---"+p);
//			siteResolver.resolveSite(siteNumber);
//			Site site = Context.getCurrentSite();
//			Node node = query.findRoot(site.getId());
//			modelMap.addAttribute("node", node);
//			modelMap.addAttribute("text", node.getText());
//			modelMap.addAttribute("type", "音乐");
//			ForeContext.setData(modelMap.asMap(), request);
//			return "/1/default/list_video_new.html";
//		}
//		
//		@RequestMapping(value = { "/dml{p:[0-9]+}.jspx" })
//		public String dmlist(HttpServletRequest request,@PathVariable Integer p,
//				HttpServletResponse response, org.springframework.ui.Model modelMap) {
//			logger.info("NodeVideoController---dmlist1---"+p);
//			return dmlist(null, p,request, response, modelMap);
//		}
//		@RequestMapping(value = Constants.SITE_PREFIX_PATH + "/dml{p:[0-9]+}.jspx")
//		public String dmlist(@PathVariable String siteNumber,@PathVariable Integer p,
//				HttpServletRequest request, HttpServletResponse response,
//				org.springframework.ui.Model modelMap) {
//			logger.info("NodeVideoController---dmlist2---"+p);
//			siteResolver.resolveSite(siteNumber);
//			Site site = Context.getCurrentSite();
//			Node node = query.findRoot(site.getId());
//			modelMap.addAttribute("node", node);
//			modelMap.addAttribute("text", node.getText());
//			modelMap.addAttribute("type", "动漫");
//			ForeContext.setData(modelMap.asMap(), request);
//			return "/1/default/list_video_new.html";
//		}
//		
//		@RequestMapping(value = { "/xwl{p:[0-9]+}.jspx" })
//		public String xwlist(HttpServletRequest request,@PathVariable Integer p,
//				HttpServletResponse response, org.springframework.ui.Model modelMap) {
//			logger.info("NodeVideoController---xwlist1---"+p);
//			return xwlist(null, p,request, response, modelMap);
//		}
//		@RequestMapping(value = Constants.SITE_PREFIX_PATH + "/xwl{p:[0-9]+}.jspx")
//		public String xwlist(@PathVariable String siteNumber,@PathVariable Integer p,
//				HttpServletRequest request, HttpServletResponse response,
//				org.springframework.ui.Model modelMap) {
//			logger.info("NodeVideoController---xwlist2---"+p);
//			siteResolver.resolveSite(siteNumber);
//			Site site = Context.getCurrentSite();
//			Node node = query.findRoot(site.getId());
//			modelMap.addAttribute("node", node);
//			modelMap.addAttribute("text", node.getText());
//			modelMap.addAttribute("type", "新闻");
//			ForeContext.setData(modelMap.asMap(), request);
//			return "/1/default/list_video_new.html";
//		}
//		
//		@RequestMapping(value = { "/dt.jspx" })
//		public String dtlist(HttpServletRequest request,@RequestParam String p,
//				HttpServletResponse response, org.springframework.ui.Model modelMap) {
//			logger.info("NodeVideoController---dtlist1---"+p);
//			return dtlist(null, p,request, response, modelMap);
//		}
//		@RequestMapping(value = Constants.SITE_PREFIX_PATH + "/dt.jspx")
//		public String dtlist(@PathVariable String siteNumber,@RequestParam String p,
//				HttpServletRequest request, HttpServletResponse response,
//				org.springframework.ui.Model modelMap) {
//			try {
//				p = URLDecoder.decode(p,"UTF-8");
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
//			logger.info("NodeVideoController---dtlist2---"+p);
//			siteResolver.resolveSite(siteNumber);
//			Site site = Context.getCurrentSite();
//			Node node = query.findRoot(site.getId());
//			modelMap.addAttribute("node", node);
//			modelMap.addAttribute("text", node.getText());
//			modelMap.addAttribute("type", p);
//			ForeContext.setData(modelMap.asMap(), request);
//			return "/1/default/list_video_dt.html";
//		}
//		
//		@RequestMapping(value = { "/ma.jspx" })
//		public String malist(HttpServletRequest request,@RequestParam String p,
//				HttpServletResponse response, org.springframework.ui.Model modelMap) {
//			logger.info("NodeVideoController---dtlist1---"+p);
//			return malist(null, p,request, response, modelMap);
//		}
//		@RequestMapping(value = Constants.SITE_PREFIX_PATH + "/ma.jspx")
//		public String malist(@PathVariable String siteNumber,@RequestParam String p,
//				HttpServletRequest request, HttpServletResponse response,
//				org.springframework.ui.Model modelMap) {
//			try {
//				p = URLDecoder.decode(p,"UTF-8");
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
//			logger.info("NodeVideoController---dtlist2---"+p);
//			siteResolver.resolveSite(siteNumber);
//			Site site = Context.getCurrentSite();
//			Node node = query.findRoot(site.getId());
//			modelMap.addAttribute("node", node);
//			modelMap.addAttribute("text", node.getText());
//			modelMap.addAttribute("type", p);
//			ForeContext.setData(modelMap.asMap(), request);
//			return "/1/default/list_video_ma.html";
//		}
//		
//		@RequestMapping(value = { "/info_video{id:[0-9]+}.jspx" })
//		public String info_video(HttpServletRequest request,@PathVariable Integer id,
//				HttpServletResponse response, org.springframework.ui.Model modelMap) {
//			logger.info("NodeVideoController---info_video---"+id);
//			return info_video(null, id,request, response, modelMap);
//		}
//		@RequestMapping(value = Constants.SITE_PREFIX_PATH + "/info_video{p:[0-9]+}.jspx")
//		public String info_video(@PathVariable String siteNumber,@PathVariable Integer id,
//				HttpServletRequest request, HttpServletResponse response,
//				org.springframework.ui.Model modelMap) {
//			logger.info("NodeVideoController---info_video---"+id);
//			siteResolver.resolveSite(siteNumber);
//			Site site = Context.getCurrentSite();
//			Node node = query.findRoot(site.getId());
//			modelMap.addAttribute("node", node);
//			modelMap.addAttribute("text", node.getText());
//			VideoTwo videoTwo= videoTwoService.get(id);
//			Info info = infoQueryService.getByVideoId(id);
//			logger.info("NodeVideoController---info is null---"+(info==null));
//			if(info==null){
//				info = new Info();
//				info.setVideo_id(id);
//				Org org = new Org();
//				org.setId(1);
//				info.setOrg(org);
//				User creator = new User();
//				creator.setId(1);
//				info.setCreator(creator);
//				info.setSite(site);
//				info.setNode(node);
//				info.setPublishDate(new Date());
//				info.setComments(0);
//				info.setDiggs(0);
//				info.setScore(0);
//				info.setStatus("A");
//				info.setHtmlStatus("0");
//				info.setDownloads(0);
//				info.setViews(0);
//				info.setWithImage(false);
//				info.setPriority(0);
//				info = infoQueryService.save(info);
//				logger.info("info---id--"+info.getId());
//				InfoDetail detail = new InfoDetail();
//				detail.setTitle(videoTwo.getTitle());
//				detail.setStrong(false);
//				detail.setEm(false);
//				infoDetailService.save(detail,info);
//			}
//			if(videoTwo.getAid()!=null&&videoTwo.getAid().length()>0&&videoFourService.findAllByAid(videoTwo.getAid()).size()>0){
//				modelMap.addAttribute("isaid", 1);
//			}else{
//				modelMap.addAttribute("isaid", 0);
//			}
//			videoTwo.setDesc(videoTwo.getDesc().replaceAll("\r|\n", ""));
//			modelMap.addAttribute("info", info);
//			modelMap.addAttribute("videoTwo", videoTwo);
//			ForeContext.setData(modelMap.asMap(), request);
//			return "/1/default/info_video_new.html";
//		}
//
//	@RequestMapping("/node/{id:[0-9]+}.jspx")
//	public String node(@PathVariable Integer id, HttpServletRequest request,
//			HttpServletResponse response, org.springframework.ui.Model modelMap) {
//		logger.info("NodeVideoController---1---"+id);
//		return node(null, id, 1, request, response, modelMap);
//	}
//
//	@RequestMapping(Constants.SITE_PREFIX_PATH + "/node/{id:[0-9]+}.jspx")
//	public String node(@PathVariable String siteNumber,
//			@PathVariable Integer id, HttpServletRequest request,
//			HttpServletResponse response, org.springframework.ui.Model modelMap) {
//		logger.info("NodeVideoController---2---"+id);
//		return node(siteNumber, id, 1, request, response, modelMap);
//	}
//
//	@RequestMapping("/node/{id:[0-9]+}_{page:[0-9]+}.jspx")
//	public String node(@PathVariable Integer id, @PathVariable Integer page,
//			HttpServletRequest request, HttpServletResponse response,
//			org.springframework.ui.Model modelMap) {
//		logger.info("NodeVideoController---4---"+id);
//		return node(null, id, page, request, response, modelMap);
//	}
//	
//	@RequestMapping("/f{id:[0-9]+}.jspx")
//	public String testnode(@PathVariable Integer id, HttpServletRequest request,
//			HttpServletResponse response, org.springframework.ui.Model modelMap) {
//		logger.info("NodeVideoController ------ test---1---"+id);
//		return node(null, id, 1, request, response, modelMap);
//	}
//	
//
//
//	@RequestMapping(Constants.SITE_PREFIX_PATH
//			+ "/node/{id:[0-9]+}_{page:[0-9]+}.jspx")
//	public String node(@PathVariable String siteNumber,
//			@PathVariable Integer id, @PathVariable Integer page,
//			HttpServletRequest request, HttpServletResponse response,
//			org.springframework.ui.Model modelMap) {
//		logger.info("NodeVideoController---3---"+id);
//		Node node = query.get(id);
//		siteResolver.resolveSite(siteNumber, node);
//		Site site = Context.getCurrentSite();
//		Response resp = new Response(request, response, modelMap);
//		List<String> messages = resp.getMessages();
//		if (!Validations.exist(node, messages, "Node", id)) {
//			return resp.badRequest();
//		}
//		if (!node.getSite().getId().equals(site.getId())) {
//			site = node.getSite();
//			Context.setCurrentSite(site);
//		}
//		String linkUrl = node.getLinkUrl();
//		if (StringUtils.isNotBlank(linkUrl)) {
//			return "redirect:" + linkUrl;
//		}
//		modelMap.addAttribute("node", node);
//		modelMap.addAttribute("text", node.getText());
//
//		Map<String, Object> data = modelMap.asMap();
//		ForeContext.setData(data, request);
//		ForeContext.setPage(data, page, node);
//		String template = Servlets.getParam(request, "template");
//		logger.info("NodeVideoController---3---template--"+template);
//		logger.info("NodeVideoController---3---node.getTemplate()--"+node.getTemplate());
//		if (StringUtils.isNotBlank(template)) {
//			return template;
//		} else {
//			return node.getTemplate();
//		}
//	}
//	
//
//
//	@ResponseBody
//	@RequestMapping(value = { "/node_views/{id:[0-9]+}.jspx",
//			Constants.SITE_PREFIX_PATH + "/node_views/{id:[0-9]+}.jspx" })
//	public String views(@PathVariable Integer id) {
//		logger.info("NodeVideoController---node_views---"+id);
//		return Integer.toString(bufferService.updateViews(id));
//	}
//	
//	
//
//	@RequestMapping(value = "/videoall.jspx",produces = "text/html;charset=UTF-8")
//	@ResponseBody
//	public String videoAll(@RequestParam int id) {
//		
//		try {
//			logger.info("videoAll------"+id);
//		
//			
//			return VideoResultController.toJson(videoMain);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return VideoResultController.toJson("fail");
//		}
//	}
//	
//	@RequestMapping(value = "/videolist.jspx",produces = "text/html;charset=UTF-8")
//	@ResponseBody
//	public String videoList(@RequestParam String type,@RequestParam String area,@RequestParam String year,@RequestParam String title,@RequestParam int page) {
//		VideoList videoList = new VideoList();
//		logger.info("type-----------------"+type);
//		if(area.equals("全部")&&year.equals("全部")&&(title==null||title.length()==0)&&page==0){
//			videoList = videoListMap.get(type);
//			logger.info("contains-----------------"+type+"--"+videoListMap.containsKey(type));
//			
//		}else{
//			Pageable pageable = new PageRequest(page, 20,Direction.DESC, "vmid");  
//			Map<String, String[]> params = new HashMap<String, String[]>();
//			if(type!=null&&type.length()>0){
//				params.put("CONTAIN_cname", new String[]{type});	
//			}
//			if(area!=null&&area.length()>0&&!area.equals("全部")){
//				params.put("CONTAIN_area", new String[]{area});	
//			}
//			if(title!=null&&title.length()>0){
//				params.put("CONTAIN_title", new String[]{title});	
//			}
////			EQ, LIKE, CONTAIN, STARTWITH, ENDWITH, GT, LT, GTE, LTE, IN
//			if(year!=null&&year.length()>0&&!year.equals("全部")){
//				if(year.equals("2017")){
//					params.put("EQ_year", new String[]{"2017"});	
//				}else if(year.equals("2016")){
//					params.put("EQ_year", new String[]{"2016"});
//				}else if(year.equals("2015-2010")){
//					params.put("LTE_year", new String[]{"2015"});
//					params.put("GTE_year", new String[]{"2010"});
//				}else if(year.equals("00年代")){
//					params.put("LTE_year", new String[]{"2009"});
//					params.put("GTE_year", new String[]{"2000"});
//				}else if(year.equals("90年代")){
//					params.put("LTE_year", new String[]{"1999"});
//					params.put("GTE_year", new String[]{"1990"});
//				}else if(year.equals("80年代")){
//					params.put("LTE_year", new String[]{"1989"});
//					params.put("GTE_year", new String[]{"1980"});
//				}else if(year.equals("更早")){
//					params.put("LTE_year", new String[]{"1979"});
//				}
//			}
//			Page<VideoTwo> pagedList = videoTwoService.findPage( params, pageable);
//			
//			videoList.setCurrpage(page);
//			if(page==0||pagedList.getTotalPages()<2){
//				videoList.setIsfirst(1);
//			}else{
//				videoList.setIsfirst(0);
//			}
//			if(page+1==pagedList.getTotalPages()||pagedList.getTotalPages()<2){
//				videoList.setIslast(1);
//			}else{
//				videoList.setIslast(0);
//			}
//			videoList.setTotalcount(pagedList.getTotalElements());
//			videoList.setTotalpage(pagedList.getTotalPages());
//			videoList.setVideoList(pagedList.getContent());
//			logger.info("videolist------page---"+page);
//			logger.info("videolist------pagedList.getTotalPages()---"+pagedList.getTotalPages());
//			logger.info("videolist------pagedList.getTotalElements()---"+pagedList.getTotalElements());
//		}
//		
//	
//		return VideoResultController.toJson(videoList);
//	}
//	
//	@RequestMapping(value = "/videolistdt.jspx",produces = "text/html;charset=UTF-8")
//	@ResponseBody
//	public String videolistdt(@RequestParam String type,@RequestParam String area,@RequestParam String year,@RequestParam String title,@RequestParam int page) {
//		VideoList videoList = new VideoList();
//		logger.info("type--------before---------"+type);
//		try {
//			type = URLDecoder.decode(type,"UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		logger.info("type--------after---------"+type);
//		Pageable pageable = new PageRequest(page, 20,Direction.DESC, "vmid");  
//		Map<String, String[]> params = new HashMap<String, String[]>();
//		if(type!=null&&type.length()>0){
//			params.put("CONTAIN_dt", new String[]{type});	
//		}
//		if(area!=null&&area.length()>0&&!area.equals("全部")){
//			params.put("CONTAIN_area", new String[]{area});	
//		}
//		if(title!=null&&title.length()>0){
//			params.put("CONTAIN_title", new String[]{title});	
//		}
////			EQ, LIKE, CONTAIN, STARTWITH, ENDWITH, GT, LT, GTE, LTE, IN
//		if(year!=null&&year.length()>0&&!year.equals("全部")){
//			if(year.equals("2017")){
//				params.put("EQ_year", new String[]{"2017"});	
//			}else if(year.equals("2016")){
//				params.put("EQ_year", new String[]{"2016"});
//			}else if(year.equals("2015-2010")){
//				params.put("LTE_year", new String[]{"2015"});
//				params.put("GTE_year", new String[]{"2010"});
//			}else if(year.equals("00年代")){
//				params.put("LTE_year", new String[]{"2009"});
//				params.put("GTE_year", new String[]{"2000"});
//			}else if(year.equals("90年代")){
//				params.put("LTE_year", new String[]{"1999"});
//				params.put("GTE_year", new String[]{"1990"});
//			}else if(year.equals("80年代")){
//				params.put("LTE_year", new String[]{"1989"});
//				params.put("GTE_year", new String[]{"1980"});
//			}else if(year.equals("更早")){
//				params.put("LTE_year", new String[]{"1979"});
//			}
//		}
//		Page<VideoTwo> pagedList = videoTwoService.findPage( params, pageable);
//		
//		videoList.setCurrpage(page);
//		if(page==0||pagedList.getTotalPages()<2){
//			videoList.setIsfirst(1);
//		}else{
//			videoList.setIsfirst(0);
//		}
//		if(page+1==pagedList.getTotalPages()||pagedList.getTotalPages()<2){
//			videoList.setIslast(1);
//		}else{
//			videoList.setIslast(0);
//		}
//		videoList.setTotalcount(pagedList.getTotalElements());
//		videoList.setTotalpage(pagedList.getTotalPages());
//		videoList.setVideoList(pagedList.getContent());
//		logger.info("videolist------page---"+page);
//		logger.info("videolist------pagedList.getTotalPages()---"+pagedList.getTotalPages());
//		logger.info("videolist------pagedList.getTotalElements()---"+pagedList.getTotalElements());
//		
//	
//		return VideoResultController.toJson(videoList);
//	}
//	
//	@RequestMapping(value = "/videolistma.jspx",produces = "text/html;charset=UTF-8")
//	@ResponseBody
//	public String videolistma(@RequestParam String type,@RequestParam String area,@RequestParam String year,@RequestParam String title,@RequestParam int page) {
//		VideoList videoList = new VideoList();
//		logger.info("type--------before---------"+type);
//		try {
//			type = URLDecoder.decode(type,"UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		logger.info("type--------after---------"+type);
//		Pageable pageable = new PageRequest(page, 20,Direction.DESC, "vmid");  
//		Map<String, String[]> params = new HashMap<String, String[]>();
//		if(type!=null&&type.length()>0){
//			params.put("CONTAIN_mA", new String[]{type});	
//		}
//		if(area!=null&&area.length()>0&&!area.equals("全部")){
//			params.put("CONTAIN_area", new String[]{area});	
//		}
//		if(title!=null&&title.length()>0){
//			params.put("CONTAIN_title", new String[]{title});	
//		}
////			EQ, LIKE, CONTAIN, STARTWITH, ENDWITH, GT, LT, GTE, LTE, IN
//		if(year!=null&&year.length()>0&&!year.equals("全部")){
//			if(year.equals("2017")){
//				params.put("EQ_year", new String[]{"2017"});	
//			}else if(year.equals("2016")){
//				params.put("EQ_year", new String[]{"2016"});
//			}else if(year.equals("2015-2010")){
//				params.put("LTE_year", new String[]{"2015"});
//				params.put("GTE_year", new String[]{"2010"});
//			}else if(year.equals("00年代")){
//				params.put("LTE_year", new String[]{"2009"});
//				params.put("GTE_year", new String[]{"2000"});
//			}else if(year.equals("90年代")){
//				params.put("LTE_year", new String[]{"1999"});
//				params.put("GTE_year", new String[]{"1990"});
//			}else if(year.equals("80年代")){
//				params.put("LTE_year", new String[]{"1989"});
//				params.put("GTE_year", new String[]{"1980"});
//			}else if(year.equals("更早")){
//				params.put("LTE_year", new String[]{"1979"});
//			}
//		}
//		Page<VideoTwo> pagedList = videoTwoService.findPage( params, pageable);
//		
//		videoList.setCurrpage(page);
//		if(page==0||pagedList.getTotalPages()<2){
//			videoList.setIsfirst(1);
//		}else{
//			videoList.setIsfirst(0);
//		}
//		if(page+1==pagedList.getTotalPages()||pagedList.getTotalPages()<2){
//			videoList.setIslast(1);
//		}else{
//			videoList.setIslast(0);
//		}
//		videoList.setTotalcount(pagedList.getTotalElements());
//		videoList.setTotalpage(pagedList.getTotalPages());
//		videoList.setVideoList(pagedList.getContent());
//		logger.info("videolist------page---"+page);
//		logger.info("videolist------pagedList.getTotalPages()---"+pagedList.getTotalPages());
//		logger.info("videolist------pagedList.getTotalElements()---"+pagedList.getTotalElements());
//		
//	
//		return VideoResultController.toJson(videoList);
//	}
//	
//	@RequestMapping(value = "/videofourlist.jspx",produces = "text/html;charset=UTF-8")
//	@ResponseBody
//	public String videofourlist(@RequestParam String aid) {
//		List<VideoFour> videoFourList =  videoFourService.findAllByAid(aid);
//		return VideoResultController.toJson(videoFourList);
//	}
//	
//	@RequestMapping(value = "/videorefresh.jspx",produces = "text/html;charset=UTF-8")
//	@ResponseBody
//	public String videorefresh(@RequestParam String id) {
//		videoMain();
//		videoList();
//		return VideoResultController.toJson("ok");
//	}
//	
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
//		videoMain.setDsList(pagedList.getContent());
//		
//		params.clear();			
//		params.put("CONTAIN_cname", zy);
//		pagedList = videoTwoService.findPage( params, pageable);
//		videoMain.setZyList(pagedList.getContent());
//		
//		params.clear();			
//		params.put("CONTAIN_cname", yl);
//		pagedList = videoTwoService.findPage( params, pageable);
//		videoMain.setYlList(pagedList.getContent());
//		
//		params.clear();			
//		params.put("CONTAIN_cname", yy);
//		pagedList = videoTwoService.findPage( params, pageable);
//		videoMain.setYyList(pagedList.getContent());
//		
//		params.clear();			
//		params.put("CONTAIN_cname", xw);
//		pagedList = videoTwoService.findPage( params, pageable);
//		videoMain.setXwList(pagedList.getContent());
//		
//		params.clear();			
//		params.put("CONTAIN_cname", dm);
//		pagedList = videoTwoService.findPage( params, pageable);
//		videoMain.setDmList(pagedList.getContent());
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
//		videoListMap.put("电视剧", videoListDs);
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
//		videoListMap.put("娱乐", videoListYl);
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
//		videoListMap.put("新闻", videoListXw);
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
//		videoListMap.put("音乐", videoListYy);
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
//		videoListMap.put("动漫", videoListDm);
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
//		videoListMap.put("综艺", videoListZy);
//		
//	}
//	
//
//	@Autowired
//	private SiteResolver siteResolver;
//	@Autowired
//	private SiteService siteService;
//	@Autowired
//	private NodeBufferService bufferService;
//	@Autowired
//	private NodeQueryService query;
//	
//	@Autowired
//	private VideoFourService videoFourService;
//	@Autowired
//	private InfoQueryService infoQueryService;
//	@Autowired
//	private InfoDetailService infoDetailService;
//	@Autowired
//	private  VideoTwoService videoTwoService;
//
//	
//	
//	
//	
//}
//
