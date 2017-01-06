package com.jspxcms.core.web.back;

import static com.jspxcms.core.constant.Constants.CREATE;
import static com.jspxcms.core.constant.Constants.DELETE_SUCCESS;
import static com.jspxcms.core.constant.Constants.EDIT;
import static com.jspxcms.core.constant.Constants.MESSAGE;
import static com.jspxcms.core.constant.Constants.OPRT;
import static com.jspxcms.core.constant.Constants.SAVE_SUCCESS;

import java.util.Date;
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

import com.jspxcms.common.web.Servlets;
import com.jspxcms.core.constant.Constants;
import com.jspxcms.core.domain.VideoClassify;
import com.jspxcms.core.service.VideoClassifyService;

/**
 * UserController
 * 
 * @author liufang
 * 
 */
@Controller
@RequestMapping("/core/video_classify")
public class VideoClassifyController {
	private static final Logger logger = LoggerFactory.getLogger(VideoClassifyController.class);

	@RequiresPermissions("core:video_classify:list")
	@RequestMapping("list.do")
	public String list(@PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable,
			HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Map<String, String[]> params = Servlets.getParamValuesMap(request, Constants.SEARCH_PREFIX);
		logger.info("VideoClassify --- params"+params.toString());
		Page<VideoClassify> pagedList = service.findPage( params, pageable);
		modelMap.addAttribute("pagedList", pagedList);
		return "core/video_classify/video_classify_list";
	}
	
	@RequiresPermissions("core:video_classify:edit")
	@RequestMapping("edit.do")
	public String edit(Integer id, Integer position,
			@PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable, HttpServletRequest request,
			org.springframework.ui.Model modelMap) {
		logger.info(new Date()+"-order:edit-"+id);
		VideoClassify bean = service.get(id);
		modelMap.addAttribute("bean", bean);
		modelMap.addAttribute(OPRT, EDIT);
		return "core/video_classify/video_classify_form";
	}
	
	@RequiresPermissions("core:video_classify:create")
	@RequestMapping("create.do")
	public String create(Integer id, Integer position,
			@PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable, HttpServletRequest request,
			org.springframework.ui.Model modelMap) {
		if (id != null) {
			VideoClassify bean = service.get(id);
			modelMap.addAttribute("bean", bean);
		}
		modelMap.addAttribute(OPRT, CREATE);
		return "core/video_classify/video_classify_form";
	}

	@RequiresPermissions("core:video_classify:save")
	@RequestMapping("save.do")
	public String save(VideoClassify bean,String redirect,HttpServletRequest request, RedirectAttributes ra) {
	
	
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

	@RequiresPermissions("core:video_classify:update")
	@RequestMapping("update.do")
	public String update(@ModelAttribute("bean") VideoClassify bean,String redirect, HttpServletRequest request, RedirectAttributes ra) {
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

	@RequiresPermissions("core:video_classify:delete")
	@RequestMapping("delete.do")
	public String delete(Integer[] ids, HttpServletRequest request, RedirectAttributes ra) {
		for(Integer id:ids){
			logger.info(new Date()+"-video_classify:delete-"+id);
			service.delete(id);
		}
		ra.addFlashAttribute(MESSAGE, DELETE_SUCCESS);
		return "redirect:list.do";
	}




	@Autowired
	private VideoClassifyService service;
	
	
}
