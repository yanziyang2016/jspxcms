//package com.jspxcms.core.web.fore;
//
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
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.jspxcms.common.web.Servlets;
//import com.jspxcms.common.web.Validations;
//import com.jspxcms.core.constant.Constants;
//import com.jspxcms.core.domain.Node;
//import com.jspxcms.core.domain.Site;
//import com.jspxcms.core.service.NodeBufferService;
//import com.jspxcms.core.service.NodeQueryService;
//import com.jspxcms.core.service.SiteService;
//import com.jspxcms.core.support.Context;
//import com.jspxcms.core.support.ForeContext;
//import com.jspxcms.core.support.Response;
//import com.jspxcms.core.support.SiteResolver;
//import com.jspxcms.core.web.back.UserController;
//
///**
// * NodeController
// * 
// * @author liufang
// * 
// */
//@Controller
//public class NodeController {
//	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
//	//主页工程使用
//	@RequestMapping(value = { "/", "/index.jspx" })
//	public String index(HttpServletRequest request,
//			HttpServletResponse response, org.springframework.ui.Model modelMap) {
//		logger.info("NodeController---index---");
//		return index(null, request, response, modelMap);
//	}
//	//主页工程使用
//	@RequestMapping(value = Constants.SITE_PREFIX_PATH + ".jspx")
//	public String index(@PathVariable String siteNumber,
//			HttpServletRequest request, HttpServletResponse response,
//			org.springframework.ui.Model modelMap) {
//		logger.info("NodeController---0000000000---");
//		siteResolver.resolveSite(siteNumber);
//		Site site = Context.getCurrentSite();
//		Response resp = new Response(request, response, modelMap);
//		List<String> messages = resp.getMessages();
//		Node node = query.findRoot(site.getId());
//		if (!Validations.exist(node, messages, "Node", "root")) {
//			return resp.badRequest();
//		}
//		modelMap.addAttribute("node", node);
//		modelMap.addAttribute("text", node.getText());
//
//		ForeContext.setData(modelMap.asMap(), request);
//		String template = Servlets.getParam(request, "template");
//		if (StringUtils.isNotBlank(template)) {
//			return template;
//		} else {
//			return node.getTemplate();
//		}
//	}
//
//	@RequestMapping("/node/{id:[0-9]+}.jspx")
//	public String node(@PathVariable Integer id, HttpServletRequest request,
//			HttpServletResponse response, org.springframework.ui.Model modelMap) {
//		logger.info("NodeController---1---"+id);
//		return node(null, id, 1, request, response, modelMap);
//	}
//
//	@RequestMapping(Constants.SITE_PREFIX_PATH + "/node/{id:[0-9]+}.jspx")
//	public String node(@PathVariable String siteNumber,
//			@PathVariable Integer id, HttpServletRequest request,
//			HttpServletResponse response, org.springframework.ui.Model modelMap) {
//		logger.info("NodeController---2---"+id);
//		return node(siteNumber, id, 1, request, response, modelMap);
//	}
//
//	@RequestMapping("/node/{id:[0-9]+}_{page:[0-9]+}.jspx")
//	public String node(@PathVariable Integer id, @PathVariable Integer page,
//			HttpServletRequest request, HttpServletResponse response,
//			org.springframework.ui.Model modelMap) {
//		logger.info("NodeController---4---"+id);
//		return node(null, id, page, request, response, modelMap);
//	}
//	
//	@RequestMapping("/f{id:[0-9]+}.jspx")
//	public String testnode(@PathVariable Integer id, HttpServletRequest request,
//			HttpServletResponse response, org.springframework.ui.Model modelMap) {
//		logger.info("NodeController ------ test---1---"+id);
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
//		logger.info("NodeController---3---"+id);
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
//		logger.info("NodeController---3---template--"+template);
//		logger.info("NodeController---3---node.getTemplate()--"+node.getTemplate());
//		if (StringUtils.isNotBlank(template)) {
//			return template;
//		} else {
//			return node.getTemplate();
//		}
//	}
//	
//	@RequestMapping("/vl{id:[0-9]+}.jspx")
//	public String videolist(@PathVariable Integer id, HttpServletRequest request,
//			HttpServletResponse response, org.springframework.ui.Model modelMap) {
//		logger.info("videolist---1---"+id);
//		return videolist(null, id, 1, request, response, modelMap);
//	}
//
//	@RequestMapping(Constants.SITE_PREFIX_PATH
//			+ "/vl{id:[0-9]+}.jspx")
//	public String videolist(@PathVariable String siteNumber,
//			@PathVariable Integer id, @PathVariable Integer page,
//			HttpServletRequest request, HttpServletResponse response,
//			org.springframework.ui.Model modelMap) {
//		logger.info("videolist---3---"+id);
//		return "/1/default/info_test.html";
//	}
//
//	@ResponseBody
//	@RequestMapping(value = { "/node_views/{id:[0-9]+}.jspx",
//			Constants.SITE_PREFIX_PATH + "/node_views/{id:[0-9]+}.jspx" })
//	public String views(@PathVariable Integer id) {
//		logger.info("NodeController---node_views---"+id);
//		return Integer.toString(bufferService.updateViews(id));
//	}
//
//	@Autowired
//	private SiteResolver siteResolver;
//	@Autowired
//	private SiteService siteService;
//	@Autowired
//	private NodeBufferService bufferService;
//	@Autowired
//	private NodeQueryService query;
//}
