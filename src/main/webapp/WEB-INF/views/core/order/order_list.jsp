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
  <span class="c-position">订单管理 - <s:message code="list"/></span>
	<span class="c-total">(<s:message code="totalElements" arguments="${pagedList.totalElements}"/>)</span>
</div>
<form action="list.do" method="get">
	<fieldset class="c-fieldset">
    <legend><s:message code="search"/></legend>
	  <label class="c-lab">订单号: <input type="text" name="search_CONTAIN_orderNo" value="${search_CONTAIN_orderNo[0]}" style="width:120px;"/></label>
	
	 <label class="c-lab">订单状态:
      <select name="search_EQ_status">
        <option value=""><s:message code="allSelect"/></option>
        <option value="1"<c:if test="${'1' eq search_EQ_status[0]}"> selected="selected"</c:if>>未处理</option>
        <option value="2"<c:if test="${'2' eq search_EQ_status[0]}"> selected="selected"</c:if>>已发货</option>
        <option value="3"<c:if test="${'3' eq search_EQ_status[0]}"> selected="selected"</c:if>>订单完毕</option>
      </select>
    </label>
	  <label class="c-lab"><input type="submit" value="<s:message code="search"/>"/></label>
  </fieldset>
</form>
<form method="post">
<tags:search_params/>
<table id="pagedTable" border="0" cellpadding="0" cellspacing="0" class="ls-tb margin-top5">
  <thead id="sortHead" pagesort="<c:out value='${page_sort[0]}' />" pagedir="${page_sort_dir[0]}" pageurl="list.do?page_sort={0}&page_sort_dir={1}&${searchstringnosort}">
  <tr class="ls_table_th">
    <th width="30"><s:message code="operate"/></th>
    <th width="30"><span class="ls-sort" pagesort="orderNo">订单号</span></th>
    <th width="130"><span class="ls-sort" pagesort="infoName">商品名</span></th>
    <th width="30"><span class="ls-sort" pagesort="productPro">商品性质</span></th>
    <th width="35"><span class="ls-sort" pagesort="userName">用户姓名</span></th>
    <th width="30"><span class="ls-sort" pagesort="mobile">用户电话</span></th>
    <th width="40" ><span class="ls-sort" pagesort="status">订单状态</span></th>
   <th width="30" ><span class="ls-sort" pagesort="orderDate">订单日期</span></th>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="bean" varStatus="status" items="${pagedList.content}">
  <tr>
    <td align="center">
		<shiro:hasPermission name="core:order:edit">
		     <a id="edit_opt_${bean.id}" href="edit.do?id=${bean.id}&position=${pagedList.number*pagedList.size+status.index}&${searchstring}" class="ls-opt"><s:message code="edit"/></a>
		</shiro:hasPermission>
		<shiro:hasPermission name="core:order:delete">
			<a href="delete.do?id=${bean.id}&${searchstring}" onclick="return confirmDelete();" class="ls-opt"><s:message code="delete"/></a>  
	    </shiro:hasPermission>
     </td>
    <td>${bean.orderNo}</td>
    <td>${bean.infoName}(第${bean.infoPeriod}期)</td>
    <td >${bean.productPro}</td>
    <td >    ${bean.userName}    </td>
    <td >${bean.mobile}</td>
    <td >
     <c:if test="${bean.status==1}">未处理</c:if>
     <c:if test="${bean.status==2}">已发货</c:if>
     <c:if test="${bean.status==3}">订单完毕</c:if>
    </td>
    <td >${bean.orderDate}</td>
   
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