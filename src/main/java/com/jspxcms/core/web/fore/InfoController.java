package com.jspxcms.core.web.fore;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jspxcms.common.file.FileHandler;
import com.jspxcms.common.file.LocalFileHandler;
import com.jspxcms.common.util.JsonMapper;
import com.jspxcms.common.web.PathResolver;
import com.jspxcms.common.web.Servlets;
import com.jspxcms.core.constant.Constants;
import com.jspxcms.core.domain.Info;
import com.jspxcms.core.domain.MemberGroup;
import com.jspxcms.core.domain.Node;
import com.jspxcms.core.domain.Order;
import com.jspxcms.core.domain.Org;
import com.jspxcms.core.domain.ProductRecord;
import com.jspxcms.core.domain.PublishPoint;
import com.jspxcms.core.domain.ScoreBoard;
import com.jspxcms.core.domain.ScoreItem;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.domain.User;
import com.jspxcms.core.domain.UserRecord;
import com.jspxcms.core.service.InfoBufferService;
import com.jspxcms.core.service.InfoQueryService;
import com.jspxcms.core.service.OrderService;
import com.jspxcms.core.service.RecordService;
import com.jspxcms.core.service.ScoreBoardService;
import com.jspxcms.core.service.ScoreItemService;
import com.jspxcms.core.service.SiteService;
import com.jspxcms.core.service.UserRecordService;
import com.jspxcms.core.service.UserService;
import com.jspxcms.core.service.VoteMarkService;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.ForeContext;
import com.jspxcms.core.support.Response;
import com.jspxcms.core.support.SiteResolver;
import com.jspxcms.core.support.TitleText;

/**
 * InfoController
 * 
 * @author liufang
 * 
 */
@Controller
public class InfoController {
	protected final static Logger logger = LoggerFactory.getLogger(InfoController.class);
	@RequestMapping("/info/{id:[0-9]+}.jspx")
	public String info(@PathVariable Integer id,
			HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		logger.info("InfoController---4---"+id);
		return info(null, id, 1, request, response, modelMap);
	}

	@RequestMapping("/info/{id:[0-9]+}_{page:[0-9]+}.jspx")
	public String info(@PathVariable Integer id,
			@PathVariable Integer page, HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		logger.info("InfoController---3---"+id);
		return info(null, id, page, request, response, modelMap);
	}

	@RequestMapping(Constants.SITE_PREFIX_PATH + "/info/{id:[0-9]+}.jspx")
	public String info(@PathVariable String siteNumber,
			@PathVariable Integer id, HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		logger.info("InfoController---2---"+id);
		return info(siteNumber, id, 1, request, response, modelMap);
	}

	@RequestMapping(Constants.SITE_PREFIX_PATH
			+ "/info/{id:[0-9]+}_{page:[0-9]+}.jspx")
	public String info(@PathVariable String siteNumber,
			@PathVariable Integer id, @PathVariable Integer page,
			HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		logger.info("InfoController---1---"+id);
		User userfore = Context.getCurrentUser();
		logger.info("InfoController---userfore---"+userfore);
		Info info = query.get(id);
		siteResolver.resolveSite(siteNumber, info);
		Response resp = new Response(request, response, modelMap);
		if (info == null) {
			return resp.badRequest("Info not found: " + id);
		}
		if (!info.isNormal()) {
			return resp.forbidden();
		}
		Collection<MemberGroup> groups = Context.getCurrentGroups(request);
		Collection<Org> orgs = Context.getCurrentOrgs(request);
		if (!info.isViewPerm(groups, orgs)) {
			User user = Context.getCurrentUser();
			if (user != null) {
				return resp.forbidden();
			} else {
				return resp.unauthorized();
			}
		}
		if (info.isLinked()) {
			return "redirect:" + info.getLinkUrl();
		}
		Node node = info.getNode();
		List<TitleText> textList = info.getTextList();
		TitleText infoText = TitleText.getTitleText(textList, page);
		String title = infoText.getTitle();
		String text = infoText.getText();
		modelMap.addAttribute("info", info);
		modelMap.addAttribute("node", node);
		modelMap.addAttribute("title", title);
		modelMap.addAttribute("text", text);
		
		logger.info("userfore------"+userfore);	
		
		if(userfore==null){
			User temp = new User();
			temp.setId(-1);
			temp.setYuanBao(0);
			temp.setMemStatus(1);
			modelMap.addAttribute("userfore", temp);
		}else{
			if(info.getTemplate().equals("/1/default/info_news.html")){
				
				int record = userRecordService.findRecordByUserAndInfo(userfore.getId(), id);
				logger.info("info_news----------info id---"+id+"--user id --"+userfore.getId()+"--record--"+record);
				if(record==0){
					UserRecord userRecord = new UserRecord();
					userRecord.setInfoId(id);
					userRecord.setUserId(userfore.getId());
					userRecord.setRecordDate(new Date());
					userRecordService.save(userRecord);
					userfore.setYuanBao(userfore.getYuanBao()+3);
					userService.updateUserOnly(userfore);
				}
			}
			modelMap.addAttribute("userfore", userfore);
		}
		
		int infoPeriod = 1;
		if(info.getTemplate().contains("info_product.html")){
			logger.info("info.getTemplate()------"+info.getTemplate());	
			//商品申请记录数
			int recordCount = recordService.findCountByInfoId(info.getId());
			logger.info("recordCount------"+recordCount);	
			if(recordCount>0){
				//当前期
				infoPeriod = recordService.findPeriodByInfoId(info.getId());
				int  stock = Integer.valueOf(info.getCustomsValueNew("stock"));//库存
				if(stock>0){
					int  periodCount = Integer.valueOf(info.getCustomsValueNew("periodCount"));//每期申请数
					int crecordCount = recordService.findCountByInfoIdAndPeriod(id,infoPeriod);//当期申请数
					if(crecordCount==periodCount){//当期已经申请满
						infoPeriod = infoPeriod + 1;
					}
				}
				logger.info("infoPeriod------"+infoPeriod);	
			}
		}
		modelMap.addAttribute("infoPeriod", infoPeriod);
		Page<String> pagedList = new PageImpl<String>(Arrays.asList(text),
				new PageRequest(page - 1, 1), textList.size());
		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		ForeContext.setPage(data, page, info, pagedList);
		logger.info("InfoController------"+pagedList);
		String template = Servlets.getParam(request, "template");
		logger.info("InfoController---smallImage---"+info.getCustomsValueNew("smallImage"));
		logger.info("InfoController---front---template---"+info.getTemplate());
		if (StringUtils.isNotBlank(template)) {
			return template;
		} else {
			return info.getTemplate();
		}
	}
	@RequestMapping("/viewvideo.jspx")
	public void viewvideo(Integer id, HttpServletRequest request,String type,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		viewvideo(null,id, request, type,response, modelMap);
	}
	@RequestMapping(Constants.SITE_PREFIX_PATH + "/viewvideo.jspx")
	public void viewvideo(@PathVariable String siteNumber, Integer id,HttpServletRequest request,String type,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		try {
			
			User userfore = Context.getCurrentUser();
			if(userfore!=null){
				
				int record = userRecordService.findRecordByUserAndVideo(userfore.getId(), id,type);
				logger.info("viewvideo----------info id---"+id+"--user id --"+userfore.getId()+"--record--"+record);
				if(record==0){
					UserRecord userRecord = new UserRecord();
					userRecord.setInfoId(id);
					userRecord.setUserId(userfore.getId());
					userRecord.setRecordDate(new Date());
					userRecord.setType(type);
					userRecordService.save(userRecord);
					userfore.setYuanBao(userfore.getYuanBao()+4);
					userService.updateUserOnly(userfore);
					Servlets.writeHtml(response,"领取成功!" );
				}else{
					Servlets.writeHtml(response,"您已经进行领取，请不要重复领取!" );
				}
			}else{
				Servlets.writeHtml(response,"请登陆后进行领取!" );
			}
		} catch (Exception e) {
			logger.error("viewvideo----------"+e.toString());
			Servlets.writeHtml(response,"服务器异常，请联系管理员" );
		}
		
	}

	@RequestMapping("/freeget.jspx")
	public void freeget(Integer id, Integer infoPeriod, HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		freeget(null, id, infoPeriod, request, response, modelMap);
	}
	
	@RequestMapping(Constants.SITE_PREFIX_PATH + "/freeget.jspx")
	public void freeget(@PathVariable String siteNumber, Integer id,
			Integer infoPeriod, HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		try {
			Info info = query.get(id);
			logger.info(id+"---"+infoPeriod+"---"+(infoPeriod+id)+"--info -- periodCount"+info.getCustomsValueNew("periodCount"));
			int  periodCount = Integer.valueOf(info.getCustomsValueNew("periodCount"));//每期申请数
			int  stock = Integer.valueOf(info.getCustomsValueNew("stock"));//库存
			int recordCount = recordService.findCountByInfoIdAndPeriod(id,infoPeriod);//当期期申请数
			User userfore = Context.getCurrentUser();
			if(stock==0){//无库存
				Servlets.writeHtml(response,"商品已被全部领取，请等待补充库存后领取!" );
			}else if(userfore.getYuanBao()<100){
				Servlets.writeHtml(response,"本次领取需要100元宝，您元宝数目不够，不能进行领取!" );
			}
			else{
				
				ProductRecord bean = new ProductRecord();
				bean.setAddDate(new Date());
				bean.setInfoId(id);
				bean.setInfoPeriod(infoPeriod);
				bean.setPeriodNo(recordCount+1);
				bean.setUserId(userfore.getId());
				if(recordCount==periodCount){//当期领取完毕
					Servlets.writeHtml(response,"当期商品已被全部领取，请进行下一期领取!" );
				}else if(recordCount+1==periodCount){//当期最后一个领取
					
					bean = recordService.save(bean);
					Order order = new Order();
					int orderprono = 1+(int)(Math.random()*periodCount);//幸运序列
					int  luckuser= recordService.findByProInFo(orderprono,id,infoPeriod);
					StringBuffer orderNo = new StringBuffer();
					for(int i=0;i<8;i++){
						orderNo.append((1+(int)(Math.random()*9)));
					}
					order.setInfoId(id);
					order.setInfoPeriod(infoPeriod);
					order.setUserId(luckuser);
					order.setOrderDate(new Date());
					order.setStatus(1);
					order.setPeriodNo(orderprono);
					order.setOrderNo(orderNo.toString());
					order = orderService.save(order);
					Servlets.writeHtml(response,"恭喜您领取成功!" );
				}else{//当期其它时间领取
					bean = recordService.save(bean);
					Servlets.writeHtml(response,"恭喜您领取成功!" );
				}
			}
			
			
		} catch (Exception e) {
			logger.error("freeget----------"+e.toString());
			Servlets.writeHtml(response,"服务器异常，请联系管理员" );
		}
		
	}

	@RequestMapping("/info_download.jspx")
	public String download(Integer id, HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap)
			throws IOException {
		return download(null, id, request, response, modelMap);
	}

	@RequestMapping(Constants.SITE_PREFIX_PATH + "/info_download.jspx")
	public String download(@PathVariable String siteNumber, Integer id,
			HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) throws IOException {
		Info info = query.get(id);
		siteResolver.resolveSite(siteNumber);
		Response resp = new Response(request, response, modelMap);
		infoBufferService.updateDownloads(id);
		String path = info.getFile();
		if (StringUtils.isBlank(path)) {
			return resp.notFound();
		}
		String normalPath = FilenameUtils.normalize(path, true);
		PublishPoint point = info.getSite().getUploadsPublishPoint();
		FileHandler fileHandler = point.getFileHandler(pathResolver);
		String urlPrefix = point.getUrlPrefix();
		if (StringUtils.startsWith(normalPath, urlPrefix)
				&& fileHandler instanceof LocalFileHandler) {
			LocalFileHandler lfHandler = (LocalFileHandler) fileHandler;
			normalPath = normalPath.substring(urlPrefix.length());
			File file = lfHandler.getFile(normalPath);
			if (!file.exists()) {
				return null;
			}
			Servlets.setDownloadHeader(response, file.getName());
			response.setContentLength((int) file.length());
			OutputStream output = response.getOutputStream();
			FileUtils.copyFile(file, output);
			output.flush();
			return null;
		} else {
			response.sendRedirect(path);
			return null;
		}
	}

	@RequestMapping(value = "/info_views.jspx")
	public void views(Integer id, HttpServletRequest request,
			HttpServletResponse response) {
		views(null, id, request, response);
	}

	@RequestMapping(Constants.SITE_PREFIX_PATH + "/info_views.jspx")
	public void views(@PathVariable String siteNumber, Integer id,
			HttpServletRequest request, HttpServletResponse response) {
		siteResolver.resolveSite(siteNumber);
		if (id == null) {
			Servlets.writeHtml(response, "0");
			return;
		}
		if (query.get(id) == null) {
			Servlets.writeHtml(response, "0");
			return;
		}
		String result = Integer.toString(bufferService.updateViews(id));
		Servlets.writeHtml(response, result);
	}

	@RequestMapping(value = "/info_views/{id:[0-9]+}.jspx")
	public void views(@PathVariable Integer id,
			@RequestParam(defaultValue = "true") boolean isUpdate,
			HttpServletRequest request, HttpServletResponse response) {
		views(null, id, isUpdate, request, response);
	}

	@RequestMapping(Constants.SITE_PREFIX_PATH + "/info_views/{id:[0-9]+}.jspx")
	public void views(@PathVariable String siteNumber,
			@PathVariable Integer id,
			@RequestParam(defaultValue = "true") boolean isUpdate,
			HttpServletRequest request, HttpServletResponse response) {
		siteResolver.resolveSite(siteNumber);
		Info info = query.get(id);
		if (info == null) {
			Servlets.writeHtml(response, "0");
			return;
		}
		Integer views;
		if (isUpdate) {
			views = bufferService.updateViews(id);
		} else {
			views = info.getBufferViews();
		}
		String result = Integer.toString(views);
		Servlets.writeHtml(response, result);
	}

	@RequestMapping("/info_comments/{id:[0-9]+}.jspx")
	public void comments(@PathVariable Integer id, HttpServletRequest request,
			HttpServletResponse response) {
		comments(null, id, request, response);
	}

	@RequestMapping(Constants.SITE_PREFIX_PATH
			+ "/info_comments/{id:[0-9]+}.jspx")
	public void comments(@PathVariable String siteNumber,
			@PathVariable Integer id, HttpServletRequest request,
			HttpServletResponse response) {
		siteResolver.resolveSite(siteNumber);
		Info info = query.get(id);
		int comments;
		if (info != null) {
			comments = info.getBufferComments();
		} else {
			comments = 0;
		}
		String result = Integer.toString(comments);
		Servlets.writeHtml(response, result);
	}

	@RequestMapping("/info_downloads/{id:[0-9]+}.jspx")
	public void downloads(@PathVariable Integer id, HttpServletRequest request,
			HttpServletResponse response) {
		downloads(null, id, request, response);
	}

	@RequestMapping(Constants.SITE_PREFIX_PATH
			+ "/info_downloads/{id:[0-9]+}.jspx")
	public void downloads(@PathVariable String siteNumber,
			@PathVariable Integer id, HttpServletRequest request,
			HttpServletResponse response) {
		siteResolver.resolveSite(siteNumber);
		Info info = query.get(id);
		int downloads;
		if (info != null) {
			downloads = info.getBufferDownloads();
		} else {
			downloads = 0;
		}
		String result = Integer.toString(downloads);
		Servlets.writeHtml(response, result);
	}

	@RequestMapping("/info_digg.jspx")
	public void digg(Integer id, HttpServletRequest request,
			HttpServletResponse response) {
		digg(null, id, request, response);
	}

	@RequestMapping(Constants.SITE_PREFIX_PATH + "/info_digg.jspx")
	public void digg(@PathVariable String siteNumber, Integer id,
			HttpServletRequest request, HttpServletResponse response) {
		siteResolver.resolveSite(siteNumber);
		if (id == null) {
			Servlets.writeHtml(response, "0");
			return;
		}
		Info info = query.get(id);
		if (info == null) {
			Servlets.writeHtml(response, "0");
			return;
		}
		Integer userId = Context.getCurrentUserId();
		String ip = Servlets.getRemoteAddr(request);
		String cookie = Site.getIdentityCookie(request, response);
		if (userId != null) {
			if (voteMarkService.isUserVoted(Info.DIGG_MARK, id, userId, null)) {
				Servlets.writeHtml(response, "0");
				return;
			}
		} else if (voteMarkService.isCookieVoted(Info.DIGG_MARK, id, cookie,
				null)) {
			Servlets.writeHtml(response, "0");
			return;
		}
		String result = Integer.toString(bufferService.updateDiggs(id, userId,
				ip, cookie));
		Servlets.writeHtml(response, result);
	}

	@RequestMapping("/info_bury.jspx")
	public void bury(Integer id, HttpServletRequest request,
			HttpServletResponse response) {
		bury(null, id, request, response);
	}

	@RequestMapping(Constants.SITE_PREFIX_PATH + "/info_bury.jspx")
	public void bury(@PathVariable String siteNumber, Integer id,
			HttpServletRequest request, HttpServletResponse response) {
		siteResolver.resolveSite(siteNumber);
		if (id == null) {
			Servlets.writeHtml(response, "0");
			return;
		}
		Info info = query.get(id);
		if (info == null) {
			Servlets.writeHtml(response, "0");
			return;
		}
		Integer userId = Context.getCurrentUserId();
		String ip = Servlets.getRemoteAddr(request);
		String cookie = Site.getIdentityCookie(request, response);
		if (userId != null) {
			if (voteMarkService.isUserVoted(Info.DIGG_MARK, id, userId, null)) {
				Servlets.writeHtml(response, "0");
				return;
			}
		} else if (voteMarkService.isCookieVoted(Info.DIGG_MARK, id, cookie,
				null)) {
			Servlets.writeHtml(response, "0");
			return;
		}
		String result = Integer.toString(bufferService.updateBurys(id, userId,
				ip, cookie));
		Servlets.writeHtml(response, result);
	}

	@RequestMapping("/info_diggs/{id:[0-9]+}.jspx")
	public void diggs(@PathVariable Integer id, HttpServletRequest request,
			HttpServletResponse response) {
		diggs(null, id, request, response);
	}

	@RequestMapping(Constants.SITE_PREFIX_PATH + "/info_diggs/{id:[0-9]+}.jspx")
	public void diggs(@PathVariable String siteNumber,
			@PathVariable Integer id, HttpServletRequest request,
			HttpServletResponse response) {
		siteResolver.resolveSite(siteNumber);
		Info info = query.get(id);
		int diggs;
		int burys;
		if (info != null) {
			diggs = info.getBufferDiggs();
			burys = info.getBufferBurys();
		} else {
			diggs = 0;
			burys = 0;
		}
		String result = "[" + diggs + "," + burys + "]";
		Servlets.writeHtml(response, result);
	}

	@RequestMapping("/info_scoring.jspx")
	public void scoring(Integer id, Integer itemId, HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		scoring(null, id, itemId, request, response, modelMap);
	}

	@RequestMapping(Constants.SITE_PREFIX_PATH + "/info_scoring.jspx")
	public void scoring(@PathVariable String siteNumber, Integer id,
			Integer itemId, HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		siteResolver.resolveSite(siteNumber);
		if (id == null || itemId == null) {
			Servlets.writeHtml(response, "0");
			return;
		}
		Info info = query.get(id);
		ScoreItem item = scoreItemService.get(itemId);
		if (info == null || item == null) {
			Servlets.writeHtml(response, "0");
			return;
		}
		Integer userId = Context.getCurrentUserId();
		String ip = Servlets.getRemoteAddr(request);
		String cookie = Site.getIdentityCookie(request, response);
		if (userId != null) {
			if (voteMarkService.isUserVoted(Info.SCORE_MARK, id, userId, null)) {
				Servlets.writeHtml(response, "0");
				return;
			}
		} else if (voteMarkService.isCookieVoted(Info.SCORE_MARK, id, cookie,
				null)) {
			Servlets.writeHtml(response, "0");
			return;
		}
		int score = infoBufferService.updateScore(id, itemId, userId, ip,
				cookie);
		String result = String.valueOf(score);
		Servlets.writeHtml(response, result);
	}
	

	@RequestMapping("/info_score/{id:[0-9]+}.jspx")
	public void score(@PathVariable Integer id, HttpServletRequest request,
			HttpServletResponse response) {
		score(null, id, request, response);
	}

	@RequestMapping(Constants.SITE_PREFIX_PATH + "/info_score/{id:[0-9]+}.jspx")
	public void score(@PathVariable String siteNumber,
			@PathVariable Integer id, HttpServletRequest request,
			HttpServletResponse response) {
		siteResolver.resolveSite(siteNumber);
		List<ScoreBoard> boardList = scoreBoardService.findList(
				Info.SCORE_MARK, id);
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (ScoreBoard board : boardList) {
			map.put(board.getItem().getId().toString(), board.getVotes());
		}
		JsonMapper mapper = new JsonMapper();
		String result = mapper.toJson(map);
		Servlets.writeHtml(response, result);
	}

	@Autowired
	private SiteResolver siteResolver;
	@Autowired
	private SiteService siteService;
	@Autowired
	private ScoreBoardService scoreBoardService;
	@Autowired
	private VoteMarkService voteMarkService;
	@Autowired
	private ScoreItemService scoreItemService;
	@Autowired
	private InfoBufferService bufferService;
	@Autowired
	private InfoQueryService query;
	@Autowired
	private InfoBufferService infoBufferService;
	@Autowired
	private PathResolver pathResolver;
	@Autowired
	private RecordService recordService;
	@Autowired
	private UserRecordService userRecordService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private UserService userService;
	
}
