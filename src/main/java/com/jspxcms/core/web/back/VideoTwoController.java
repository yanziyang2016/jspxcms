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
import com.jspxcms.core.domain.VideoTwo;
import com.jspxcms.core.service.VideoTwoService;

/**
 * UserController
 * 
 * @author liufang
 * 
 */
@Controller
@RequestMapping("/core/video_two")
public class VideoTwoController {
	private static final Logger logger = LoggerFactory.getLogger(VideoTwoController.class);

	@RequiresPermissions("core:video_two:list")
	@RequestMapping("list.do")
	public String list(@PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable,
			HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Map<String, String[]> params = Servlets.getParamValuesMap(request, Constants.SEARCH_PREFIX);
		logger.info("VideoTwo --- params"+params.toString());
		Page<VideoTwo> pagedList = service.findPage( params, pageable);
		modelMap.addAttribute("pagedList", pagedList);
		return "core/video_two/video_two_list";
	}
	
	@RequiresPermissions("core:video_two:edit")
	@RequestMapping("edit.do")
	public String edit(Integer id, Integer position,
			@PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable, HttpServletRequest request,
			org.springframework.ui.Model modelMap) {
		logger.info(new Date()+"-order:edit-"+id);
		VideoTwo bean = service.get(id);
		logger.info(new Date()+"-order:edit-"+id);
		modelMap.addAttribute("bean", bean);
		modelMap.addAttribute(OPRT, EDIT);
		return "core/video_two/video_two_form";
	}
	
	@RequiresPermissions("core:video_two:create")
	@RequestMapping("create.do")
	public String create(Integer id, Integer position,
			@PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable, HttpServletRequest request,
			org.springframework.ui.Model modelMap) {
		if (id != null) {
			VideoTwo bean = service.get(id);
			modelMap.addAttribute("bean", bean);
		}
		modelMap.addAttribute(OPRT, CREATE);
		return "core/video_two/video_two_form";
	}

	@RequiresPermissions("core:video_two:save")
	@RequestMapping("save.do")
	public String save(VideoTwo bean,String redirect,HttpServletRequest request, RedirectAttributes ra) {
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

	@RequiresPermissions("core:video_two:update")
	@RequestMapping("update.do")
	public String update(@ModelAttribute("bean") VideoTwo bean,String redirect, HttpServletRequest request, RedirectAttributes ra) {
		logger.info(new Date()+"-order:update-"+bean.getId());
		VideoTwo temp = service.get(Integer.valueOf(bean.getId()));
		temp.setTitle(bean.getTitle());
		temp.setArea(bean.getArea());
		temp.setYear(bean.getYear());
		temp.setCname(bean.getCname());
		temp.setScore(bean.getScore());
		temp.setDt(bean.getDt());
		temp.setmA(bean.getmA());
		temp.setDesc(bean.getDesc());
		temp.setBigPic(bean.getBigPic());
		service.update(temp);
		ra.addFlashAttribute(MESSAGE, SAVE_SUCCESS);
		if (Constants.REDIRECT_LIST.equals(redirect)) {
			return "redirect:list.do";
		} else {
			ra.addAttribute("id", bean.getId());
			return "redirect:edit.do";
		}
	}

	@RequiresPermissions("core:video_two:delete")
	@RequestMapping("delete.do")
	public String delete(Integer[] ids, HttpServletRequest request, RedirectAttributes ra) {
		for(Integer id:ids){
			logger.info(new Date()+"-video_two:delete-"+id);
			service.delete(id);
		}
		ra.addFlashAttribute(MESSAGE, DELETE_SUCCESS);
		return "redirect:list.do";
	}




	@Autowired
	private VideoTwoService service;
	
	
}
