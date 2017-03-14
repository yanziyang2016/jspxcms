package com.jspxcms.core.web.back;

import static com.jspxcms.core.constant.Constants.CREATE;
import static com.jspxcms.core.constant.Constants.DELETE_SUCCESS;
import static com.jspxcms.core.constant.Constants.EDIT;
import static com.jspxcms.core.constant.Constants.MESSAGE;
import static com.jspxcms.core.constant.Constants.OPRT;
import static com.jspxcms.core.constant.Constants.SAVE_SUCCESS;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jspxcms.common.web.Servlets;
import com.jspxcms.core.constant.Constants;
import com.jspxcms.core.domain.ProductClassify;
import com.jspxcms.core.service.ProductClassifyService;

/**
 * UserController
 * 
 * @author liufang
 * 
 */
@Controller
@RequestMapping("/core/product_classify")
public class ProductClassifyController {
	private static final Logger logger = LoggerFactory.getLogger(ProductClassifyController.class);

	@RequiresPermissions("core:product_classify:list")
	@RequestMapping("list.do")
	public String list(@PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable,
			HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Map<String, String[]> params = Servlets.getParamValuesMap(request, Constants.SEARCH_PREFIX);
		logger.info("ProductClassify --- params"+params.toString());
		Page<ProductClassify> pagedList = service.findPage( params, pageable);
		modelMap.addAttribute("pagedList", pagedList);
		return "core/product_classify/product_classify_list";
	}
	
	@RequiresPermissions("core:product_classify:edit")
	@RequestMapping("edit.do")
	public String edit(Integer id, Integer position,
			@PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable, HttpServletRequest request,
			org.springframework.ui.Model modelMap) {
		logger.info(new Date()+"-order:edit-"+id);
		ProductClassify bean = service.get(id);
		Pageable temp = new PageRequest(0, 20);  
		Map<String, String[]> params = new HashMap<String, String[]>();
		params.put("EQ_sourceId", new String[]{"1"});			
		Page<ProductClassify> productClassifyPage= service.findPage(params, temp);
		logger.info("-ProductClassify-"+productClassifyPage.getContent().size());
		modelMap.addAttribute("productClassifyList", productClassifyPage.getContent());
		modelMap.addAttribute("bean", bean);
		modelMap.addAttribute(OPRT, EDIT);
		return "core/product_classify/product_classify_form";
	}
	
	@RequiresPermissions("core:product_classify:create")
	@RequestMapping("create.do")
	public String create(Integer id, Integer position,
			@PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable, HttpServletRequest request,
			org.springframework.ui.Model modelMap) {
		if (id != null) {
			ProductClassify bean = service.get(id);
			modelMap.addAttribute("bean", bean);
		}
		Pageable temp = new PageRequest(0, 20);  
		Map<String, String[]> params = new HashMap<String, String[]>();
		params.put("EQ_sourceId", new String[]{"1"});			
		Page<ProductClassify> productClassifyPage= service.findPage(params, temp);
		logger.info("-ProductClassify-"+productClassifyPage.getContent().size());
		modelMap.addAttribute("productClassifyList", productClassifyPage.getContent());
		modelMap.addAttribute(OPRT, CREATE);
		return "core/product_classify/product_classify_form";
	}

	@RequiresPermissions("core:product_classify:save")
	@RequestMapping("save.do")
	public String save(ProductClassify bean,String redirect,HttpServletRequest request, RedirectAttributes ra) {
	
		if(bean.getBeforeClassifyId()==0){
			bean.setBeforeClassifyName("商品");
		}else{
			ProductClassify beanBefore = service.get(bean.getBeforeClassifyId());
			bean.setBeforeClassifyName(beanBefore.getClassifyName());
		}
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

	@RequiresPermissions("core:product_classify:update")
	@RequestMapping("update.do")
	public String update(@ModelAttribute("bean") ProductClassify bean,String redirect, HttpServletRequest request, RedirectAttributes ra) {
		logger.info(new Date()+"-library:update-"+bean.getId());
		if(bean.getBeforeClassifyId()==0){
			bean.setBeforeClassifyName("商品");
		}else{
			ProductClassify beanBefore = service.get(bean.getBeforeClassifyId());
			bean.setBeforeClassifyName(beanBefore.getClassifyName());
		}
		service.update(bean);
		ra.addFlashAttribute(MESSAGE, SAVE_SUCCESS);
		if (Constants.REDIRECT_LIST.equals(redirect)) {
			return "redirect:list.do";
		} else {
			ra.addAttribute("id", bean.getId());
			return "redirect:edit.do";
		}
	}

	@RequiresPermissions("core:product_classify:delete")
	@RequestMapping("delete.do")
	public String delete(Integer[] ids, HttpServletRequest request, RedirectAttributes ra) {
		for(Integer id:ids){
			logger.info(new Date()+"-product_classify:delete-"+id);
			service.delete(id);
		}
		ra.addFlashAttribute(MESSAGE, DELETE_SUCCESS);
		return "redirect:list.do";
	}




	@Autowired
	private ProductClassifyService service;
	
	
}
