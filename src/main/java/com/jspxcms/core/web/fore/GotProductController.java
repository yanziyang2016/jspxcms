package com.jspxcms.core.web.fore;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jspxcms.common.web.Anchor;
import com.jspxcms.common.web.Validations;
import com.jspxcms.core.constant.Constants;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.service.CommentService;
import com.jspxcms.core.service.InfoQueryService;
import com.jspxcms.core.service.InfoService;
import com.jspxcms.core.service.NodeQueryService;
import com.jspxcms.core.service.SensitiveWordService;
import com.jspxcms.core.service.UserService;
import com.jspxcms.core.service.VoteMarkService;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.ForeContext;
import com.jspxcms.core.support.Response;
import com.jspxcms.core.support.SiteResolver;
import com.octo.captcha.service.CaptchaService;

/**
 * GotProductController
 * 
 * @author liufang
 * 
 */
@Controller
public class GotProductController {
	public static final String LIST_TEMPLATE = "sys_member_gotproduct_list.html";
	public static final String ALL_TEMPLATE = "sys_allorder.html";
	public static final String TPL_PREFIX = "sys_gotproduct_this";
	public static final String TPL_SUFFIX = ".html";
	public static final String TEMPLATE = TPL_PREFIX + TPL_SUFFIX;
	protected final static Logger logger = LoggerFactory.getLogger(GotProductController.class);
	@RequestMapping(value = { "/my/gotproduct.jspx",
			Constants.SITE_PREFIX_PATH + "/my/gotproduct.jspx" })
	public String index(Integer page, HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		Site site = Context.getCurrentSite();
		Map<String, Object> data = modelMap.asMap();
		logger.info("GotProductController data -----"+data.toString());
		ForeContext.setData(data, request);
		ForeContext.setPage(data, page);
		return site.getTemplate(LIST_TEMPLATE);
	}
	@RequestMapping("/gotproduct_this.jspx")
	public String view(String ftype, Integer fid, Integer page,
			HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		return view(null, ftype, fid, page, request, response, modelMap);
	}

	@RequestMapping(Constants.SITE_PREFIX_PATH + "/gotproduct_this.jspx")
	public String view(@PathVariable String siteNumber, String ftype,
			Integer fid, Integer page, HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		siteResolver.resolveSite(siteNumber);
		Response resp = new Response(request, response, modelMap);
		List<String> messages = resp.getMessages();
		Site site = Context.getCurrentSite();
		if (StringUtils.isBlank(ftype)) {
			ftype = "Info";
		}
//		if (!Validations.notNull(fid, messages, "fid")) {
//			return resp.post(401);
//		}
		Object bean = service.getEntity(ftype, fid);
		if (!Validations.exist(bean, messages, "CommentObject", ftype + fid)) {
			return resp.post(451);
		}
		Anchor anchor = (Anchor) bean;
		// Site site = ((Siteable) bean).getSite();
		modelMap.addAttribute("anchor", anchor);
		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		ForeContext.setPage(data, page);
		return site.getTemplate(TEMPLATE);
	}
	@RequestMapping("/gotallproduct.jspx")
	public String gotallproduct(String ftype, Integer fid, Integer page,
			HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		return gotallproduct(null, ftype, fid, page, request, response, modelMap);
	}
	@RequestMapping(Constants.SITE_PREFIX_PATH + "/gotallproduct.jspx")
	public String gotallproduct(@PathVariable String siteNumber, String ftype,
			Integer fid, Integer page, HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		siteResolver.resolveSite(siteNumber);
		Response resp = new Response(request, response, modelMap);
		List<String> messages = resp.getMessages();
		Site site = Context.getCurrentSite();
		if (StringUtils.isBlank(ftype)) {
			ftype = "Info";
		}
		Object bean = service.getEntity(ftype, fid);
		if (!Validations.exist(bean, messages, "CommentObject", ftype + fid)) {
			return resp.post(451);
		}
		Anchor anchor = (Anchor) bean;
		modelMap.addAttribute("anchor", anchor);
		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		ForeContext.setPage(data, page);
		return site.getTemplate(ALL_TEMPLATE);
	}
	
	@Autowired
	private SiteResolver siteResolver;
	@Autowired
	private CaptchaService captchaService;
	@Autowired
	private SensitiveWordService sensitiveWordService;
	@Autowired
	private VoteMarkService voteMarkService;
	@Autowired
	private UserService userService;
	@Autowired
	private CommentService service;

	
}
