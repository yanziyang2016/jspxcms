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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jspxcms.common.web.Servlets;
import com.jspxcms.core.constant.Constants;
import com.jspxcms.core.domain.ModelField;
import com.jspxcms.core.domain.Product;
import com.jspxcms.core.domain.ProductClassify;
import com.jspxcms.core.service.ProductClassifyService;
import com.jspxcms.core.service.ProductService;

/**
 * UserController
 * 
 * @author liufang
 * 
 */
@Controller
@RequestMapping("/core/product")
public class ProductController {
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

	@RequiresPermissions("core:product:list")
	@RequestMapping("list.do")
	public String list(@PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable,
			HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Map<String, String[]> params = Servlets.getParamValuesMap(request, Constants.SEARCH_PREFIX);
		logger.info("Product --- params"+params.toString());
		Page<Product> pagedList = service.findPage( params, pageable);
		for(Product Product: pagedList.getContent()){
				Product.setOneClassifyName(productClassifyService.get(Product.getOneClassifyId()).getClassifyName());	
				Product.setStatusName(Product.getStatus()==0?"下架":"上架");
				Product.setProductproName(Product.getProductpro()==1?"虚拟商品":"实物商品");
				Product.setTwoClassifyName(productClassifyService.get(Product.getTwoClassifyId()).getClassifyName());
		}
		modelMap.addAttribute("pagedList", pagedList);
		return "core/product/product_list";
	}
	
	@RequiresPermissions("core:product:edit")
	@RequestMapping("edit.do")
	public String edit(Integer id, Integer position,
			@PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable, HttpServletRequest request,
			org.springframework.ui.Model modelMap) {
		logger.info(new Date()+"-order:edit-"+id);
		Product bean = service.get(id);
		ModelField field = null;
		logger.info(new Date()+"-order:edit-"+id);
		modelMap.addAttribute("bean", bean);
		modelMap.addAttribute("field", field);
		modelMap.addAttribute("oneClassifyId", bean.getOneClassifyId()==null?-1:bean.getOneClassifyId());
		modelMap.addAttribute("twoClassifyId", bean.getTwoClassifyId()==null?-1:bean.getTwoClassifyId());
		modelMap.addAttribute(OPRT, EDIT);
		return "core/product/product_form";
	}
	
	@RequiresPermissions("core:product:create")
	@RequestMapping("create.do")
	public String create(Integer id, Integer position,
			@PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable, HttpServletRequest request,
			org.springframework.ui.Model modelMap) {
		if (id != null) {
			Product bean = service.get(id);
			modelMap.addAttribute("bean", bean);
		}
		modelMap.addAttribute("oneClassifyId", -1);
		modelMap.addAttribute("twoClassifyId", -1);
		modelMap.addAttribute(OPRT, CREATE);
		return "core/product/product_form";
	}

	@RequiresPermissions("core:product:save")
	@RequestMapping("save.do")
	public String save(Product bean,String redirect,HttpServletRequest request, RedirectAttributes ra) {
		logger.info(new Date()+"-:update PeriodCount -"+bean.getPeriodCount());
		logger.info(new Date()+"-:update stock -"+bean.getStock());
		logger.info(new Date()+"-:update-"+bean.getId());
		bean.setPublishDate(new Date());
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

	@RequiresPermissions("core:product:update")
	@RequestMapping("update.do")
	public String update(@ModelAttribute("bean") Product bean,String redirect, HttpServletRequest request, RedirectAttributes ra) {
		logger.info(new Date()+"-:update-"+bean.getId());
		bean.setPublishDate(new Date());
		service.update(bean);
		ra.addFlashAttribute(MESSAGE, SAVE_SUCCESS);
		if (Constants.REDIRECT_LIST.equals(redirect)) {
			return "redirect:list.do";
		} else {
			ra.addAttribute("id", bean.getId());
			return "redirect:edit.do";
		}
	}

	@RequiresPermissions("core:product:delete")
	@RequestMapping("delete.do")
	public String delete(Integer[] ids, HttpServletRequest request, RedirectAttributes ra) {
		for(Integer id:ids){
			logger.info(new Date()+"-product:delete-"+id);
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
		Page<ProductClassify> ProductClassifyPage= productClassifyService.findPage(params, temp);
		return VideoResultController.toJson(ProductClassifyPage.getContent());
	}
	
	@RequestMapping(value = "twoClassifyList",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String twoClassifyList(@RequestParam int id) {
		Pageable temp = new PageRequest(0, 20);  
		Map<String, String[]> params = new HashMap<String, String[]>();
		params.put("EQ_beforeClassifyId", new String[]{""+id});			
		Page<ProductClassify> ProductClassifyPage= productClassifyService.findPage(params, temp);
		return VideoResultController.toJson(ProductClassifyPage.getContent());
	}




	@Autowired
	private ProductService service;
	
	@Autowired
	private ProductClassifyService productClassifyService;
	
	
}
