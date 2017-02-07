package com.jspxcms.core.web.back;

import static com.jspxcms.core.constant.Constants.CREATE;
import static com.jspxcms.core.constant.Constants.DELETE_SUCCESS;
import static com.jspxcms.core.constant.Constants.EDIT;
import static com.jspxcms.core.constant.Constants.MESSAGE;
import static com.jspxcms.core.constant.Constants.OPRT;
import static com.jspxcms.core.constant.Constants.SAVE_SUCCESS;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jspxcms.common.web.Servlets;
import com.jspxcms.core.constant.Constants;
import com.jspxcms.core.domain.VideoClassify;
import com.jspxcms.core.domain.VideoTwo;
import com.jspxcms.core.service.VideoClassifyService;
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
	public String list(@PageableDefault(sort = "vmid", direction = Direction.DESC) Pageable pageable,
			HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Map<String, String[]> params = Servlets.getParamValuesMap(request, Constants.SEARCH_PREFIX);
		logger.info("VideoTwo --- params"+params.toString());
		Page<VideoTwo> pagedList = service.findPage( params, pageable);
		for(VideoTwo videoTwo: pagedList.getContent()){
			if(videoTwo.getOneClassifyId()==null||videoTwo.getOneClassifyId()==-1){
				videoTwo.setOneClassifyName("未设置");
			}else{
				videoTwo.setOneClassifyName(videoClassifyService.get(videoTwo.getOneClassifyId()).getVideoClassifyName());	
			}
			
			if(videoTwo.getTwoClassifyId()==null||videoTwo.getTwoClassifyId()==-1){
				videoTwo.setTwoClassifyName("未设置");
			}else{
				videoTwo.setTwoClassifyName(videoClassifyService.get(videoTwo.getTwoClassifyId()).getVideoClassifyName());
			}
		}
		modelMap.addAttribute("pagedList", pagedList);
		return "core/video_two/video_two_list";
	}
	
	@RequiresPermissions("core:video_two:edit")
	@RequestMapping("edit.do")
	public String edit(Integer id, Integer position,
			@PageableDefault(sort = "vmid", direction = Direction.DESC) Pageable pageable, HttpServletRequest request,
			org.springframework.ui.Model modelMap) {
		logger.info(new Date()+"-order:edit-"+id);
		VideoTwo bean = service.get(id);
		logger.info(new Date()+"-order:edit-"+id);
		modelMap.addAttribute("bean", bean);
		modelMap.addAttribute("oneClassifyId", bean.getOneClassifyId()==null?-1:bean.getOneClassifyId());
		modelMap.addAttribute("twoClassifyId", bean.getTwoClassifyId()==null?-1:bean.getTwoClassifyId());
		modelMap.addAttribute(OPRT, EDIT);
		return "core/video_two/video_two_form";
	}
	
	@RequiresPermissions("core:video_two:create")
	@RequestMapping("create.do")
	public String create(Integer id, Integer position,
			@PageableDefault(sort = "vmid", direction = Direction.DESC) Pageable pageable, HttpServletRequest request,
			org.springframework.ui.Model modelMap) {
		if (id != null) {
			VideoTwo bean = service.get(id);
			modelMap.addAttribute("bean", bean);
		}
		modelMap.addAttribute("oneClassifyId", -1);
		modelMap.addAttribute("twoClassifyId", -1);
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
		temp.setOneClassifyId(bean.getOneClassifyId());
		temp.setTwoClassifyId(bean.getTwoClassifyId());
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

	@RequestMapping(value = "oneClassifyList",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String oneClassifyList(@RequestParam int id) {
		Pageable temp = new PageRequest(0, 20);  
		Map<String, String[]> params = new HashMap<String, String[]>();
		params.put("EQ_sourceId", new String[]{"1"});			
		Page<VideoClassify> videoClassifyPage= videoClassifyService.findPage(params, temp);
		return VideoResultController.toJson(videoClassifyPage.getContent());
	}
	
	@RequestMapping(value = "twoClassifyList",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String twoClassifyList(@RequestParam int id) {
		Pageable temp = new PageRequest(0, 20);  
		Map<String, String[]> params = new HashMap<String, String[]>();
		params.put("EQ_sourceClassifyId", new String[]{""+id});			
		Page<VideoClassify> videoClassifyPage= videoClassifyService.findPage(params, temp);
		return VideoResultController.toJson(videoClassifyPage.getContent());
	}




	@Autowired
	private VideoTwoService service;
	
	@Autowired
	private VideoClassifyService videoClassifyService;
	
	
}
