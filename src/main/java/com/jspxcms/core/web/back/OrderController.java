package com.jspxcms.core.web.back;

import static com.jspxcms.core.constant.Constants.DELETE_SUCCESS;
import static com.jspxcms.core.constant.Constants.EDIT;
import static com.jspxcms.core.constant.Constants.MESSAGE;
import static com.jspxcms.core.constant.Constants.OPRT;
import static com.jspxcms.core.constant.Constants.SAVE_SUCCESS;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jspxcms.common.orm.RowSide;
import com.jspxcms.common.web.Servlets;
import com.jspxcms.core.constant.Constants;
import com.jspxcms.core.domain.MemberGroup;
import com.jspxcms.core.domain.Order;
import com.jspxcms.core.domain.Org;
import com.jspxcms.core.domain.Role;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.domain.User;
import com.jspxcms.core.domain.UserDetail;
import com.jspxcms.core.service.OrderService;
import com.jspxcms.core.support.CmsException;
import com.jspxcms.core.support.Context;

/**
 * UserController
 * 
 * @author liufang
 * 
 */
@Controller
@RequestMapping("/core/order")
public class OrderController {
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

	@RequiresPermissions("core:order:list")
	@RequestMapping("list.do")
	public String list(@PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable,
			HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Map<String, String[]> params = Servlets.getParamValuesMap(request, Constants.SEARCH_PREFIX);
		logger.info("OrderController --- params"+params.toString());
		Page<Order> pagedList = service.findPage( params, pageable);
		modelMap.addAttribute("pagedList", pagedList);
		return "core/order/order_list";
	}
	
	@RequiresPermissions("core:order:edit")
	@RequestMapping("edit.do")
	public String edit(Integer id, Integer position,
			@PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable, HttpServletRequest request,
			org.springframework.ui.Model modelMap) {
		logger.info(new Date()+"-order:edit-"+id);
		Order bean = service.get(id);
//		if (!bean.getOrg().getTreeNumber().startsWith(orgTreeNumber)) {
//			throw new CmsException("error.forbiddenData");
//		}
//		if (user.getRank() > bean.getRank()) {
//			throw new CmsException("error.forbiddenData");
//		}
//		Map<String, String[]> params = Servlets.getParamValuesMap(request, Constants.SEARCH_PREFIX);
//		List<Role> roleList = roleService.findList(site.getId());
//		modelMap.addAttribute("roleList", roleList);
//		List<MemberGroup> groupList = groupService.findRealGroups();
//		modelMap.addAttribute("groupList", groupList);
		modelMap.addAttribute("bean", bean);
//		modelMap.addAttribute("org", bean.getOrg());
//		modelMap.addAttribute("currRank", user.getRank());
//		modelMap.addAttribute("orgTreeNumber", orgTreeNumber);
//		modelMap.addAttribute("side", side);
//		modelMap.addAttribute("position", position);
		modelMap.addAttribute(OPRT, EDIT);
		return "core/order/order_form";
	}

	@RequiresPermissions("core:order:save")
	@RequestMapping("save.do")
	public String save(Order bean,String redirect,HttpServletRequest request, RedirectAttributes ra) {
	
	
		service.save(bean);

		ra.addFlashAttribute(MESSAGE, SAVE_SUCCESS);
			ra.addAttribute("id", bean.getId());
			if (Constants.REDIRECT_LIST.equals(redirect)) {
				return "redirect:list.do";
			}  else {
				ra.addAttribute("id", bean.getId());
				return "redirect:edit.do";
			}
	}

	@RequiresPermissions("core:order:update")
	@RequestMapping("update.do")
	public String update(@ModelAttribute("bean") Order bean,String redirect, HttpServletRequest request, RedirectAttributes ra) {
		logger.info(new Date()+"-order:update-"+bean.getId());
		service.update(bean);
		ra.addFlashAttribute(MESSAGE, SAVE_SUCCESS);
		if (Constants.REDIRECT_LIST.equals(redirect)) {
			return "redirect:list.do";
		} else {
			ra.addAttribute("id", bean.getId());
			return "redirect:edit.do";
		}
	}

	@RequiresPermissions("core:order:delete")
	@RequestMapping("delete.do")
	public String delete(Integer id, HttpServletRequest request, RedirectAttributes ra) {
		logger.info(new Date()+"-order:delete-"+id);
		service.delete(id);
		ra.addFlashAttribute(MESSAGE, DELETE_SUCCESS);
		return "redirect:list.do";
	}




	@Autowired
	private OrderService service;
}
