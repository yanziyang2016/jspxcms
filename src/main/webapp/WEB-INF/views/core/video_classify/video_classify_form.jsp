<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fnx" uri="http://java.sun.com/jsp/jstl/functionsx"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" uri="http://www.jspxcms.com/tags/form"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>1111111111111111111</title>
<jsp:include page="/WEB-INF/views/commons/head.jsp"></jsp:include>
<script type="text/javascript">
function confirmDelete() {
	return confirm("<s:message code='confirmDelete'/>");
}
</script>
</head>
<body class="c-body">
<jsp:include page="/WEB-INF/views/commons/show_message.jsp"/>
<div class="c-bar margin-top5">
  <span class="c-position"><s:message code="video.classify"/> - <s:message code="${oprt=='edit' ? 'edit' : 'create'}"/></span>
</div>
<form id="validForm" action="${oprt=='edit' ? 'update' : 'save'}.do" method="post" onsubmit="return toVaild()">
<tags:search_params/>
<f:hidden name="oid" value="${bean.id}"/>
<f:hidden name="position" value="${position}"/>
<input type="hidden" id="redirect" name="redirect" value="edit"/>
<table border="0" cellpadding="0" cellspacing="0" class="in-tb margin-top5">
  <tr>
    <td colspan="4" class="in-opt">
			<shiro:hasPermission name="core:user:create">
			<div class="in-btn"><input type="button" value="<s:message code="create"/>" onclick="location.href='create.do?orgId=${org.id}&${searchstring}';"<c:if test="${oprt=='create'}"> disabled="disabled"</c:if>/></div>
			<div class="in-btn"></div>
			</shiro:hasPermission>
			<shiro:hasPermission name="core:user:delete">
			<div class="in-btn"><input type="button" value="<s:message code="delete"/>" onclick="if(confirmDelete()){location.href='delete.do?ids=${bean.id}&${searchstring}';}"<c:if test="${oprt=='create' || bean.id le 1}"> disabled="disabled"</c:if>/></div>
			</shiro:hasPermission>
			<div class="in-btn"></div>
			<div class="in-btn"></div>
			<div class="in-btn"><input type="button" value="<s:message code="return"/>" onclick="location.href='list.do?${searchstring}';"/></div>
      <div style="clear:both;"></div>
    </td>
  </tr>
  <tr>
    <td class="in-lab" width="15%">分类名称:</td>
    <td class="in-ctt" width="35%"><f:text name="videoClassifyName" value="${oprt=='edit' ? (bean.videoClassifyName) : ''}" style="width:180px;"/></td>
    <td class="in-lab" width="15%">上级分类:</td>
    <td class="in-ctt" width="35%">
    
    
    
     <select name="sourceClassifyId" id="sourceClassifyId">
        <f:option value="0" selected="0" >视频</f:option>
        <c:forEach var="videoClassify" items="${videoClassifyList}">
        <f:option value="${videoClassify.id}" selected="${bean.sourceClassifyId}" >${videoClassify.videoClassifyName}</f:option>
        </c:forEach>
      </select>
    
    </td>
  </tr>
  <tr>
    <td class="in-lab" width="15%">视频等级:</td>
    <td class="in-ctt" width="85%" colspan="3">
    	<select id="sourceId" name="sourceId" >
    		<f:option value="1" selected="${bean.sourceId}" >一级</f:option>
    		<f:option value="2" selected="${bean.sourceId}" >二级</f:option>
    	</select> &nbsp;
    	<input type="hidden" name="id" value="${bean.id}" />
    </td>
  </tr>

  <tr>
    <td colspan="4" class="in-opt">
      <div class="in-btn"><input type="submit" value="<s:message code="save"/>"/></div>
      <div class="in-btn"><input type="submit" value="<s:message code="saveAndReturn"/>" onclick="$('#redirect').val('list');"/></div>
      <div style="clear:both;"></div>
    </td>
  </tr>
</table>
<script >
function toVaild(){
	if($("#sourceId").val()=='1'&&$("#sourceClassifyId").val()!='0'){
		alert("上级分类选择错误");
		return false;
	}else	if($("#sourceId").val()=='2'&&$("#sourceClassifyId").val()=='0'){
		alert("上级分类选择错误");
		return false;
	}else{
		return true;
	}
	
}
</script>
</form>
</body>
</html>