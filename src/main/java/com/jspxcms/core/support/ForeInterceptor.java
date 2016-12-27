package com.jspxcms.core.support;

import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jspxcms.core.domain.Global;
import com.jspxcms.core.domain.MemberGroup;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.domain.User;
import com.jspxcms.core.domain.UserStatus;
import com.jspxcms.core.security.ShiroUser;
import com.jspxcms.core.service.GlobalService;
import com.jspxcms.core.service.MemberGroupService;
import com.jspxcms.core.service.SiteService;
import com.jspxcms.core.service.UserService;
import com.jspxcms.core.service.UserStatusService;

/**
 * ForeInterceptor
 * 
 * @author liufang
 * 
 */
public class ForeInterceptor implements HandlerInterceptor {
	protected final static Logger logger = LoggerFactory.getLogger(ForeInterceptor.class);
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
//		logger.info("ForeInterceptor----------111111");
		Site site = null;
		Global global = globalService.findUnique();
		// URL不带域名就不识别域名识别。
		if (global.getWithDomain()) {
			site = siteService.findByDomain(request.getServerName());
			
		}
//		logger.info("ForeInterceptor----------222222222");
		if (site == null) {
			site = siteService.findDefault();
		}
		if (site == null) {
			throw new CmsException("site.error.siteNotFound");
		}
		Context.setCurrentSite(site);
//		logger.info("ForeInterceptor----------33333333333");
		ShiroUser shiroUser = null;
		Subject subject = SecurityUtils.getSubject();
		Object principal = subject.getPrincipal();
		// 用户登录信息，允许记住用户。
//		principal = null;
		if (principal != null) {
			if (principal instanceof ShiroUser) {
				shiroUser = (ShiroUser) principal;
			} else {
				logger.info("---------------");
				subject.logout();
			}
		}
		logger.info("ForeInterceptor----------"+(shiroUser != null));
		String macAddress = request.getHeader("User-Agent")+ request.getRemoteAddr();
		UserStatus userStatus = userStatusService.getByMacAddress(macAddress);
		if(userStatus!=null){
			if(userStatus.getStatus()==1){
				shiroUser = new ShiroUser(userStatus.getUserId(), userStatus.getUserName());
				userStatus.setLastDate(new Date());				
				userStatusService.save(userStatus);
			}else{
				shiroUser = null;
			}
			
		}
		if (shiroUser != null) {
			User user = userService.get(shiroUser.id);
			Context.setCurrentUser(user);
			Context.setCurrentGroup(request, user.getGroup());
			Context.setCurrentGroups(request, user.getGroups());
			Context.setCurrentOrg(request, user.getOrg());
			Context.setCurrentOrgs(request, user.getOrgs());
			
//			if(userStatus == null){
//				userStatus = new UserStatus();
//				userStatus.setLastDate(new Date());
//				userStatus.setMacAddress(macAddress);
//				userStatus.setStatus(1);
//				userStatus.setUserId(shiroUser.id);
//				userStatus.setUserName(user.getUsername());
//				userStatusService.save(userStatus);
//			}else{
//				userStatus.setStatus(1);
//				userStatus.setLastDate(new Date());
//				
//				userStatusService.save(userStatus);
//			}
		} else {
			MemberGroup anon = memberGroupService.getAnonymous();
			Context.setCurrentGroup(request, anon);
			Context.setCurrentGroups(request,
					Arrays.asList(new MemberGroup[] { anon }));
			// 未登录，组织为空
		}
		return true;
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		Context.resetCurrentSite();
		Context.resetCurrentUser();
	}

	private SiteService siteService;
	private UserStatusService userStatusService;	
	private GlobalService globalService;
	private UserService userService;
	private MemberGroupService memberGroupService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	@Autowired
	public void setUserStatusService(UserStatusService userStatusService) {
		this.userStatusService = userStatusService;
	}

	@Autowired
	public void setMemberGroupService(MemberGroupService memberGroupService) {
		this.memberGroupService = memberGroupService;
	}

	@Autowired
	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}

	@Autowired
	public void setGlobalService(GlobalService globalService) {
		this.globalService = globalService;
	}
}
