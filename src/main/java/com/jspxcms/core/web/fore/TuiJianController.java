package com.jspxcms.core.web.fore;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jspxcms.core.constant.Constants;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.service.InfoQueryService;
import com.jspxcms.core.service.InfoService;
import com.jspxcms.core.service.NodeQueryService;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.ForeContext;

/**
 * ContributeController
 * 
 * @author liufang
 * 
 */
@Controller
public class TuiJianController {
	public static final String LIST_TEMPLATE = "sys_member_tuiguang_list.html";
	protected final static Logger logger = LoggerFactory.getLogger(TuiJianController.class);
	@RequestMapping(value = { "/my/tuiguang.jspx",
			Constants.SITE_PREFIX_PATH + "/my/tuiguang.jspx" })
	public String index(Integer page, HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		Site site = Context.getCurrentSite();
		Map<String, Object> data = modelMap.asMap();
		logger.info("TuiJianController data -----"+data.toString());
		ForeContext.setData(data, request);
		ForeContext.setPage(data, page);
		return site.getTemplate(LIST_TEMPLATE);
	}

	
}
