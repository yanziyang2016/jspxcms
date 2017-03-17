package com.jspxcms.core.web.fore;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jspxcms.common.web.Servlets;
import com.jspxcms.common.web.Validations;
import com.jspxcms.core.constant.Constants;
import com.jspxcms.core.domain.Info;
import com.jspxcms.core.domain.InfoDetail;
import com.jspxcms.core.domain.Node;
import com.jspxcms.core.domain.Org;
import com.jspxcms.core.domain.Product;
import com.jspxcms.core.domain.ProductClassify;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.domain.User;
import com.jspxcms.core.domain.VideoFour;
import com.jspxcms.core.domain.VideoList;
import com.jspxcms.core.domain.VideoTwo;
import com.jspxcms.core.service.InfoDetailService;
import com.jspxcms.core.service.InfoQueryService;
import com.jspxcms.core.service.NodeBufferService;
import com.jspxcms.core.service.NodeQueryService;
import com.jspxcms.core.service.ProductClassifyService;
import com.jspxcms.core.service.ProductService;
import com.jspxcms.core.service.SiteService;
import com.jspxcms.core.service.VideoFourService;
import com.jspxcms.core.service.VideoTwoService;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.ForeContext;
import com.jspxcms.core.support.Response;
import com.jspxcms.core.support.SiteResolver;
import com.jspxcms.core.web.back.UserController;
import com.jspxcms.core.web.back.VideoResultController;

/**
 * NodeController
 * 
 * @author liufang
 * 
 */
@Controller
public class NodeProductController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	//主页工程使用
	@RequestMapping(value = { "/", "/index.jspx" })
	public String index(HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		
		logger.info("NodeProductController---index1---");
		return index(null, request, response, modelMap);
	}
	//主页工程使用
	@RequestMapping(value = Constants.SITE_PREFIX_PATH + ".jspx")
	public String index(@PathVariable String siteNumber,
			HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		logger.info("NodeProductController---index2---");
		siteResolver.resolveSite(siteNumber);
		Site site = Context.getCurrentSite();
		Node node = query.findRoot(site.getId());
		modelMap.addAttribute("node", node);
		modelMap.addAttribute("text", node.getText());
		
		Pageable pageable = new PageRequest(0, 5,Direction.DESC, "id");  
		Map<String, String[]> params = new HashMap<String, String[]>();
		params.put("EQ_sourceId", new String[]{"1"});	
		Page<ProductClassify> pageProductClassifylist = productClassifyService.findPage( params, pageable);
		List<ProductClassify> productClassifylist = pageProductClassifylist.getContent();
		
		Pageable pageableAll = new PageRequest(0, 20,Direction.DESC, "id");  
		Map<String, String[]> paramsAll = new HashMap<String, String[]>();
		paramsAll.put("EQ_sourceId", new String[]{"1"});	
		Page<ProductClassify> pageProductClassifylistAll = productClassifyService.findPage( paramsAll, pageableAll);
		List<ProductClassify> productClassifylistAll = pageProductClassifylistAll.getContent();
		
		List<Product> productListFro = new ArrayList<Product>();
		
		for(ProductClassify productClassify : productClassifylistAll){
			Pageable pageableTemp = new PageRequest(0, 5,Direction.DESC, "id");  
			Map<String, String[]> paramsTemp = new HashMap<String, String[]>();
			paramsTemp.put("EQ_oneClassifyId", new String[]{""+productClassify.getId()});	
			Page<Product> pageProductlist = productService.findPage( paramsTemp, pageableTemp);
			List<Product> productList = pageProductlist.getContent();
			if(productList.size()>0){
				productListFro.add(productList.get(0));
			}
			productClassify.setProductList(productList);
		}
		
		
	
		
		modelMap.addAttribute("productlist", productListFro);
		modelMap.addAttribute("productClassifylist", productClassifylist);
		modelMap.addAttribute("productClassifylistAll", productClassifylistAll);

		ForeContext.setData(modelMap.asMap(), request);
		return "/1/default/index_product.html";
	}
	
	@RequestMapping(value = { "/list_product{id:[0-9]+}.jspx" })
	public String list_product(HttpServletRequest request,@PathVariable Integer id,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		logger.info("NodeProductController---list_product---"+id);
		return list_product(null, id,request, response, modelMap);
	}
	@RequestMapping(value = Constants.SITE_PREFIX_PATH + "/list_product{p:[0-9]+}.jspx")
	public String list_product(@PathVariable String siteNumber,@PathVariable Integer id,
			HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		logger.info("NodeProductController---list_product---"+id);
		siteResolver.resolveSite(siteNumber);
		Site site = Context.getCurrentSite();
		Node node = query.findRoot(site.getId());
		modelMap.addAttribute("node", node);
		modelMap.addAttribute("text", node.getText());
		
		ProductClassify productClassify = productClassifyService.get(id);
		Pageable pageableOne = new PageRequest(0, 20,Direction.DESC, "id");  
		Map<String, String[]> paramsOne = new HashMap<String, String[]>();
		paramsOne.put("EQ_sourceId", new String[]{"1"});	
		Page<ProductClassify> pageProductClassifylistOne = productClassifyService.findPage( paramsOne, pageableOne);
		List<ProductClassify> productClassifylistOne = pageProductClassifylistOne.getContent();
		
		Pageable pageableTwo = new PageRequest(0, 20,Direction.DESC, "id");  
		Map<String, String[]> paramsTwo = new HashMap<String, String[]>();
		paramsTwo.put("EQ_beforeClassifyId", new String[]{""+productClassify.getId()});	
		Page<ProductClassify> pageProductClassifylistTwo = productClassifyService.findPage( paramsTwo, pageableTwo);
		List<ProductClassify> productClassifylistTwo = pageProductClassifylistTwo.getContent();
		
		modelMap.addAttribute("productClassify", productClassify);
		modelMap.addAttribute("productClassifylistOne", productClassifylistOne);
		modelMap.addAttribute("productClassifylistTwo", productClassifylistTwo);
	
		ForeContext.setData(modelMap.asMap(), request);
		return "/1/default/list_product_new.html";
	}
		
		
		@RequestMapping(value = { "/info_video{id:[0-9]+}.jspx" })
		public String info_video(HttpServletRequest request,@PathVariable Integer id,
				HttpServletResponse response, org.springframework.ui.Model modelMap) {
			logger.info("NodeProductController---info_video---"+id);
			return info_video(null, id,request, response, modelMap);
		}
		@RequestMapping(value = Constants.SITE_PREFIX_PATH + "/info_video{p:[0-9]+}.jspx")
		public String info_video(@PathVariable String siteNumber,@PathVariable Integer id,
				HttpServletRequest request, HttpServletResponse response,
				org.springframework.ui.Model modelMap) {
			logger.info("NodeProductController---info_video---"+id);
			siteResolver.resolveSite(siteNumber);
			Site site = Context.getCurrentSite();
			Node node = query.findRoot(site.getId());
			modelMap.addAttribute("node", node);
			modelMap.addAttribute("text", node.getText());
			VideoTwo videoTwo= videoTwoService.get(id);
			Info info = infoQueryService.getByVideoId(id);
			logger.info("NodeProductController---info is null---"+(info==null));
			if(info==null){
				info = new Info();
				info.setVideo_id(id);
				Org org = new Org();
				org.setId(1);
				info.setOrg(org);
				User creator = new User();
				creator.setId(1);
				info.setCreator(creator);
				info.setSite(site);
				info.setNode(node);
				info.setPublishDate(new Date());
				info.setComments(0);
				info.setDiggs(0);
				info.setScore(0);
				info.setStatus("A");
				info.setHtmlStatus("0");
				info.setDownloads(0);
				info.setViews(0);
				info.setWithImage(false);
				info.setPriority(0);
				info = infoQueryService.save(info);
				logger.info("info---id--"+info.getId());
				InfoDetail detail = new InfoDetail();
				detail.setTitle(videoTwo.getTitle());
				detail.setStrong(false);
				detail.setEm(false);
				infoDetailService.save(detail,info);
			}
			if(videoTwo.getAid()!=null&&videoTwo.getAid().length()>0&&videoFourService.findAllByAid(videoTwo.getAid()).size()>0){
				modelMap.addAttribute("isaid", 1);
			}else{
				modelMap.addAttribute("isaid", 0);
			}
			videoTwo.setDesc(videoTwo.getDesc().replaceAll("\r|\n", ""));
			modelMap.addAttribute("info", info);
			modelMap.addAttribute("videoTwo", videoTwo);
			ForeContext.setData(modelMap.asMap(), request);
			return "/1/default/info_video_new.html";
		}

	@RequestMapping("/node/{id:[0-9]+}.jspx")
	public String node(@PathVariable Integer id, HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		logger.info("NodeProductController---1---"+id);
		return node(null, id, 1, request, response, modelMap);
	}

	@RequestMapping(Constants.SITE_PREFIX_PATH + "/node/{id:[0-9]+}.jspx")
	public String node(@PathVariable String siteNumber,
			@PathVariable Integer id, HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		logger.info("NodeProductController---2---"+id);
		return node(siteNumber, id, 1, request, response, modelMap);
	}

	@RequestMapping("/node/{id:[0-9]+}_{page:[0-9]+}.jspx")
	public String node(@PathVariable Integer id, @PathVariable Integer page,
			HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		logger.info("NodeProductController---4---"+id);
		return node(null, id, page, request, response, modelMap);
	}
	
	@RequestMapping("/f{id:[0-9]+}.jspx")
	public String testnode(@PathVariable Integer id, HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		logger.info("NodeProductController ------ test---1---"+id);
		return node(null, id, 1, request, response, modelMap);
	}
	


	@RequestMapping(Constants.SITE_PREFIX_PATH
			+ "/node/{id:[0-9]+}_{page:[0-9]+}.jspx")
	public String node(@PathVariable String siteNumber,
			@PathVariable Integer id, @PathVariable Integer page,
			HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		logger.info("NodeProductController---3---"+id);
		Node node = query.get(id);
		siteResolver.resolveSite(siteNumber, node);
		Site site = Context.getCurrentSite();
		Response resp = new Response(request, response, modelMap);
		List<String> messages = resp.getMessages();
		if (!Validations.exist(node, messages, "Node", id)) {
			return resp.badRequest();
		}
		if (!node.getSite().getId().equals(site.getId())) {
			site = node.getSite();
			Context.setCurrentSite(site);
		}
		String linkUrl = node.getLinkUrl();
		if (StringUtils.isNotBlank(linkUrl)) {
			return "redirect:" + linkUrl;
		}
		modelMap.addAttribute("node", node);
		modelMap.addAttribute("text", node.getText());

		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		ForeContext.setPage(data, page, node);
		String template = Servlets.getParam(request, "template");
		logger.info("NodeProductController---3---template--"+template);
		logger.info("NodeProductController---3---node.getTemplate()--"+node.getTemplate());
		if (StringUtils.isNotBlank(template)) {
			return template;
		} else {
			return node.getTemplate();
		}
	}
	


	@ResponseBody
	@RequestMapping(value = { "/node_views/{id:[0-9]+}.jspx",
			Constants.SITE_PREFIX_PATH + "/node_views/{id:[0-9]+}.jspx" })
	public String views(@PathVariable Integer id) {
		logger.info("NodeProductController---node_views---"+id);
		return Integer.toString(bufferService.updateViews(id));
	}
	
	

	@RequestMapping(value = "/videoall.jspx",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String videoAll(@RequestParam int id) {
		
		try {
			logger.info("videoAll------"+id);
		
			
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return VideoResultController.toJson("fail");
		}
	}
	
	@RequestMapping(value = "/videolist.jspx",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String videoList(@RequestParam String type,@RequestParam String area,@RequestParam String year,@RequestParam String title,@RequestParam int page) {
		VideoList videoList = new VideoList();
		logger.info("type-----------------"+type);
		if(area.equals("全部")&&year.equals("全部")&&(title==null||title.length()==0)&&page==0){
			
		}else{
			Pageable pageable = new PageRequest(page, 20,Direction.DESC, "vmid");  
			Map<String, String[]> params = new HashMap<String, String[]>();
			if(type!=null&&type.length()>0){
				params.put("CONTAIN_cname", new String[]{type});	
			}
			if(area!=null&&area.length()>0&&!area.equals("全部")){
				params.put("CONTAIN_area", new String[]{area});	
			}
			if(title!=null&&title.length()>0){
				params.put("CONTAIN_title", new String[]{title});	
			}
//			EQ, LIKE, CONTAIN, STARTWITH, ENDWITH, GT, LT, GTE, LTE, IN
			if(year!=null&&year.length()>0&&!year.equals("全部")){
				if(year.equals("2017")){
					params.put("EQ_year", new String[]{"2017"});	
				}else if(year.equals("2016")){
					params.put("EQ_year", new String[]{"2016"});
				}else if(year.equals("2015-2010")){
					params.put("LTE_year", new String[]{"2015"});
					params.put("GTE_year", new String[]{"2010"});
				}else if(year.equals("00年代")){
					params.put("LTE_year", new String[]{"2009"});
					params.put("GTE_year", new String[]{"2000"});
				}else if(year.equals("90年代")){
					params.put("LTE_year", new String[]{"1999"});
					params.put("GTE_year", new String[]{"1990"});
				}else if(year.equals("80年代")){
					params.put("LTE_year", new String[]{"1989"});
					params.put("GTE_year", new String[]{"1980"});
				}else if(year.equals("更早")){
					params.put("LTE_year", new String[]{"1979"});
				}
			}
			Page<VideoTwo> pagedList = videoTwoService.findPage( params, pageable);
			
			videoList.setCurrpage(page);
			if(page==0||pagedList.getTotalPages()<2){
				videoList.setIsfirst(1);
			}else{
				videoList.setIsfirst(0);
			}
			if(page+1==pagedList.getTotalPages()||pagedList.getTotalPages()<2){
				videoList.setIslast(1);
			}else{
				videoList.setIslast(0);
			}
			videoList.setTotalcount(pagedList.getTotalElements());
			videoList.setTotalpage(pagedList.getTotalPages());
			videoList.setVideoList(pagedList.getContent());
			logger.info("videolist------page---"+page);
			logger.info("videolist------pagedList.getTotalPages()---"+pagedList.getTotalPages());
			logger.info("videolist------pagedList.getTotalElements()---"+pagedList.getTotalElements());
		}
		
	
		return VideoResultController.toJson(videoList);
	}
	
	@RequestMapping(value = "/videolistdt.jspx",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String videolistdt(@RequestParam String type,@RequestParam String area,@RequestParam String year,@RequestParam String title,@RequestParam int page) {
		VideoList videoList = new VideoList();
		logger.info("type--------before---------"+type);
		try {
			type = URLDecoder.decode(type,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		logger.info("type--------after---------"+type);
		Pageable pageable = new PageRequest(page, 20,Direction.DESC, "vmid");  
		Map<String, String[]> params = new HashMap<String, String[]>();
		if(type!=null&&type.length()>0){
			params.put("CONTAIN_dt", new String[]{type});	
		}
		if(area!=null&&area.length()>0&&!area.equals("全部")){
			params.put("CONTAIN_area", new String[]{area});	
		}
		if(title!=null&&title.length()>0){
			params.put("CONTAIN_title", new String[]{title});	
		}
//			EQ, LIKE, CONTAIN, STARTWITH, ENDWITH, GT, LT, GTE, LTE, IN
		if(year!=null&&year.length()>0&&!year.equals("全部")){
			if(year.equals("2017")){
				params.put("EQ_year", new String[]{"2017"});	
			}else if(year.equals("2016")){
				params.put("EQ_year", new String[]{"2016"});
			}else if(year.equals("2015-2010")){
				params.put("LTE_year", new String[]{"2015"});
				params.put("GTE_year", new String[]{"2010"});
			}else if(year.equals("00年代")){
				params.put("LTE_year", new String[]{"2009"});
				params.put("GTE_year", new String[]{"2000"});
			}else if(year.equals("90年代")){
				params.put("LTE_year", new String[]{"1999"});
				params.put("GTE_year", new String[]{"1990"});
			}else if(year.equals("80年代")){
				params.put("LTE_year", new String[]{"1989"});
				params.put("GTE_year", new String[]{"1980"});
			}else if(year.equals("更早")){
				params.put("LTE_year", new String[]{"1979"});
			}
		}
		Page<VideoTwo> pagedList = videoTwoService.findPage( params, pageable);
		
		videoList.setCurrpage(page);
		if(page==0||pagedList.getTotalPages()<2){
			videoList.setIsfirst(1);
		}else{
			videoList.setIsfirst(0);
		}
		if(page+1==pagedList.getTotalPages()||pagedList.getTotalPages()<2){
			videoList.setIslast(1);
		}else{
			videoList.setIslast(0);
		}
		videoList.setTotalcount(pagedList.getTotalElements());
		videoList.setTotalpage(pagedList.getTotalPages());
		videoList.setVideoList(pagedList.getContent());
		logger.info("videolist------page---"+page);
		logger.info("videolist------pagedList.getTotalPages()---"+pagedList.getTotalPages());
		logger.info("videolist------pagedList.getTotalElements()---"+pagedList.getTotalElements());
		
	
		return VideoResultController.toJson(videoList);
	}
	
	@RequestMapping(value = "/videolistma.jspx",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String videolistma(@RequestParam String type,@RequestParam String area,@RequestParam String year,@RequestParam String title,@RequestParam int page) {
		VideoList videoList = new VideoList();
		logger.info("type--------before---------"+type);
		try {
			type = URLDecoder.decode(type,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		logger.info("type--------after---------"+type);
		Pageable pageable = new PageRequest(page, 20,Direction.DESC, "vmid");  
		Map<String, String[]> params = new HashMap<String, String[]>();
		if(type!=null&&type.length()>0){
			params.put("CONTAIN_mA", new String[]{type});	
		}
		if(area!=null&&area.length()>0&&!area.equals("全部")){
			params.put("CONTAIN_area", new String[]{area});	
		}
		if(title!=null&&title.length()>0){
			params.put("CONTAIN_title", new String[]{title});	
		}
//			EQ, LIKE, CONTAIN, STARTWITH, ENDWITH, GT, LT, GTE, LTE, IN
		if(year!=null&&year.length()>0&&!year.equals("全部")){
			if(year.equals("2017")){
				params.put("EQ_year", new String[]{"2017"});	
			}else if(year.equals("2016")){
				params.put("EQ_year", new String[]{"2016"});
			}else if(year.equals("2015-2010")){
				params.put("LTE_year", new String[]{"2015"});
				params.put("GTE_year", new String[]{"2010"});
			}else if(year.equals("00年代")){
				params.put("LTE_year", new String[]{"2009"});
				params.put("GTE_year", new String[]{"2000"});
			}else if(year.equals("90年代")){
				params.put("LTE_year", new String[]{"1999"});
				params.put("GTE_year", new String[]{"1990"});
			}else if(year.equals("80年代")){
				params.put("LTE_year", new String[]{"1989"});
				params.put("GTE_year", new String[]{"1980"});
			}else if(year.equals("更早")){
				params.put("LTE_year", new String[]{"1979"});
			}
		}
		Page<VideoTwo> pagedList = videoTwoService.findPage( params, pageable);
		
		videoList.setCurrpage(page);
		if(page==0||pagedList.getTotalPages()<2){
			videoList.setIsfirst(1);
		}else{
			videoList.setIsfirst(0);
		}
		if(page+1==pagedList.getTotalPages()||pagedList.getTotalPages()<2){
			videoList.setIslast(1);
		}else{
			videoList.setIslast(0);
		}
		videoList.setTotalcount(pagedList.getTotalElements());
		videoList.setTotalpage(pagedList.getTotalPages());
		videoList.setVideoList(pagedList.getContent());
		logger.info("videolist------page---"+page);
		logger.info("videolist------pagedList.getTotalPages()---"+pagedList.getTotalPages());
		logger.info("videolist------pagedList.getTotalElements()---"+pagedList.getTotalElements());
		
	
		return VideoResultController.toJson(videoList);
	}
	
	@RequestMapping(value = "/videofourlist.jspx",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String videofourlist(@RequestParam String aid) {
		List<VideoFour> videoFourList =  videoFourService.findAllByAid(aid);
		return VideoResultController.toJson(videoFourList);
	}

	

	@Autowired
	private SiteResolver siteResolver;
	@Autowired
	private SiteService siteService;
	@Autowired
	private NodeBufferService bufferService;
	@Autowired
	private NodeQueryService query;
	
	@Autowired
	private VideoFourService videoFourService;
	@Autowired
	private InfoQueryService infoQueryService;
	@Autowired
	private InfoDetailService infoDetailService;
	@Autowired
	private  VideoTwoService videoTwoService;
	
	@Autowired
	private ProductService productService;

	@Autowired
	private ProductClassifyService productClassifyService;
	
	
	
}

