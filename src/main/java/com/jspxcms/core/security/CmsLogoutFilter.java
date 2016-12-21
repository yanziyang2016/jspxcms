package com.jspxcms.core.security;

import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jspxcms.common.web.Servlets;
import com.jspxcms.core.constant.Constants;
import com.jspxcms.core.domain.UserStatus;
import com.jspxcms.core.service.OperationLogService;
import com.jspxcms.core.service.UserStatusService;
import com.jspxcms.core.support.LocalMac;

public class CmsLogoutFilter extends LogoutFilter {
	/**
	 * 返回URL
	 */
	public static final String FALLBACK_URL_PARAM = "fallbackUrl";
	/**
	 * 后台路径
	 */
	private String backUrl = Constants.CMSCP + "/";
	private String backRedirectUrl = Constants.BACK_SUCCESS_URL;
	
	private Logger logger = LoggerFactory
			.getLogger(CmsLogoutFilter.class);
	private UserStatusService userStatusService;	
	
	@Autowired
	public void setUserStatusService(UserStatusService userStatusService) {
		this.userStatusService = userStatusService;
	}

	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response)
			throws Exception {
		Subject subject = getSubject(request, response);
		Object principal = subject.getPrincipal();
		String ip = Servlets.getRemoteAddr(request);
		boolean result = super.preHandle(request, response);
		String macAddress = ((HttpServletRequest) request).getHeader("User-Agent")+ request.getRemoteAddr();
		logger.info("macAddress---------------");
		UserStatus userStatus = userStatusService.getByMacAddress(macAddress);
		if(userStatus!=null){
			userStatusService.delete(userStatus.getId());
		}
		if (principal != null) {
			logService.logout(ip, principal.toString());
		}
		return result;
	}

	protected String getRedirectUrl(ServletRequest req, ServletResponse resp,
			Subject subject) {
		HttpServletRequest request = (HttpServletRequest) req;
		String redirectUrl = request.getParameter(FALLBACK_URL_PARAM);
		if (StringUtils.isBlank(redirectUrl)) {
			if (request.getRequestURI().startsWith(
					request.getContextPath() + backUrl)) {
				redirectUrl = getBackRedirectUrl();
			} else {
				redirectUrl = getRedirectUrl();
			}
		}
		return redirectUrl;
	}

	public String getBackRedirectUrl() {
		return backRedirectUrl;
	}

	public void setBackRedirectUrl(String backRedirectUrl) {
		this.backRedirectUrl = backRedirectUrl;
	}

	public String getBackUrl() {
		return backUrl;
	}

	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}

	private OperationLogService logService;

	@Autowired
	public void setOperationLogService(OperationLogService logService) {
		this.logService = logService;
	}
}

