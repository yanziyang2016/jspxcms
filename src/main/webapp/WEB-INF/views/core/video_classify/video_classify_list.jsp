<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fnx" uri="http://java.sun.com/jsp/jstl/functionsx"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="f" uri="http://www.jspxcms.com/tags/form"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>Jspxcms管理平台 - Powered by Jspxcms</title>
<jsp:include page="/WEB-INF/views/commons/head.jsp"></jsp:include>
<script type="text/javascript">
$(function() {
	$("#pagedTable").tableHighlight();
	$("#sortHead").headSort();
});
function confirmDelete() {
	return confirm("<s:message code='confirmDelete'/>");
}
function confirmDeletePassword() {
	return confirm("<s:message code='user.confirmDeletePassword'/>");
}
function optSingle(opt) {
	if(Cms.checkeds("ids")==0) {
		alert("<s:message code='pleaseSelectRecord'/>");
		return false;
	}
	if(Cms.checkeds("ids")>1) {
		alert("<s:message code='pleaseSelectOne'/>");
		return false;
	}
	var id = $("input[name='ids']:checkbox:checked").val();
	location.href=$(opt+id).attr("href");
}
function optMulti(form, action, msg) {
	if(Cms.checkeds("ids")==0) {
		alert("<s:message code='pleaseSelectRecord'/>");
		return false;
	}
	if(msg && !confirm(msg)) {
		return false;
	}
	form.action=action;
	form.submit();
	return true;
}
function optDelete(form) {
	if(Cms.checkeds("ids")==0) {
		alert("<s:message code='pleaseSelectRecord'/>");
		return false;
	}
	if(!confirmDelete()) {
		return false;
	}
	form.action='delete.do';
	form.submit();
	return true;
}
function optDeletePassword(form) {
	if(Cms.checkeds("ids")==0) {
		alert("<s:message code='pleaseSelectRecord'/>");
		return false;
	}
	if(!confirmDeletePassword()) {
		return false;
	}
	form.action='delete_password.do';
	form.submit();
	return true;
}
</script>
</head>
<body class="c-body">
<jsp:include page="/WEB-INF/views/commons/show_message.jsp"/>
<div class="c-bar margin-top5">
  <span class="c-position"><s:message code="video.classify"/> - <s:message code="list"/></span>
	<span class="c-total">(<s:message code="totalElements" arguments="${pagedList.totalElements}"/>)</span>
</div>
<form action="list.do" method="get">
	<fieldset class="c-fieldset">
    <legend><s:message code="search"/></legend>
	  <label class="c-lab">分类名称: <input type="text" name="search_CONTAIN_videoClassifyName" value="${search_CONTAIN_videoClassifyName[0]}" style="width:120px;"/></label>
	   <label class="c-lab">上级分类名称: <input type="text" name="search_CONTAIN_beforeClassifyName" value="${search_CONTAIN_beforeClassifyName[0]}" style="width:120px;"/></label>
	  <label class="c-lab">视频等级:
      <select name="search_EQ_sourceId">
        <option value=""><s:message code="allSelect"/></option>
        <option value="1"<c:if test="${'1' eq search_EQ_sourceId[0]}"> selected="selected"</c:if>>一级</option>
        <option value="2"<c:if test="${'2' eq search_EQ_sourceId[0]}"> selected="selected"</c:if>>二级</option>
      </select>
    </label>
	  <label class="c-lab"><input type="submit" value="<s:message code="search"/>"/></label>
  </fieldset>
</form>
<form method="post">
<tags:search_params/>
<div class="ls-bc-opt">
	<shiro:hasPermission name="core:video_classify:create">
	<div class="ls-btn"><input type="button" value="<s:message code="create"/>" onclick="location.href='create.do?${searchstring}';"/></div>
	<div class="ls-btn"></div>
	</shiro:hasPermission>
	<shiro:hasPermission name="core:video_classify:edit">
	<div class="ls-btn"><input type="button" value="<s:message code="edit"/>" onclick="return optSingle('#edit_opt_');"/></div>
	</shiro:hasPermission>
	<div class="ls-btn"><input type="button" value="<s:message code="delete"/>" onclick="return optDelete(this.form);"/></div>
	<div style="clear:both"></div>
</div>
<table id="pagedTable" border="0" cellpadding="0" cellspacing="0" class="ls-tb margin-top5">
  <thead id="sortHead" pagesort="<c:out value='${page_sort[0]}' />" pagedir="${page_sort_dir[0]}" pageurl="list.do?page_sort={0}&page_sort_dir={1}&${searchstringnosort}">
  <tr class="ls_table_th">
    <th width="25"><input type="checkbox" onclick="Cms.check('ids',this.checked);"/></th>
    <th width="130"><s:message code="operate"/></th>
    <th width="30" class="ls-th-sort"><span class="ls-sort" pagesort="id">ID</span></th>
    <th class="ls-th-sort"><span class="ls-sort" pagesort="sourceId">视频等级</span></th>
    <th class="ls-th-sort"><span class="ls-sort" pagesort="beforeClassifyName">上级分类</span></th>
    <th class="ls-th-sort"><span class="ls-sort" pagesort="videoClassifyName">分类名称</span></th>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="bean" varStatus="status" items="${pagedList.content}">
  <tr<shiro:hasPermission name="core:video_classify:edit"> ondblclick="location.href=$('#edit_opt_${bean.id}').attr('href');"</shiro:hasPermission>>
    <td><input type="checkbox" name="ids" value="${bean.id}"/></td>
    <td align="center">
		<shiro:hasPermission name="core:video_classify:edit">
		      <a id="edit_opt_${bean.id}" href="edit.do?id=${bean.id}&position=${pagedList.number*pagedList.size+status.index}&${searchstring}" class="ls-opt"><s:message code="edit"/></a>
		</shiro:hasPermission>
		<shiro:hasPermission name="core:video_classify:delete">
			<a href="delete.do?ids=${bean.id}&${searchstring}" onclick="return confirmDelete();" class="ls-opt"><s:message code="delete"/></a>
 		</shiro:hasPermission>
     </td>
    <td>${bean.id}</td>
    <td align="center">
    <c:if test="${bean.sourceId==1}">一级</c:if><c:if test="${bean.sourceId==2}">二级</c:if>
    </td>
    <td align="center">${bean.beforeClassifyName}</td>
    <td align="center">${bean.videoClassifyName}</td>
  </tr>
  </c:forEach>
  </tbody>
</table>
<c:if test="${fn:length(pagedList.content) le 0}"> 
<div class="ls-norecord margin-top5"><s:message code="recordNotFound"/></div>
</c:if>
</form>
<form action="list.do" method="get" class="ls-page">
	<tags:search_params excludePage="true"/>
  <tags:pagination pagedList="${pagedList}"/>
</form>
</body>
</html>