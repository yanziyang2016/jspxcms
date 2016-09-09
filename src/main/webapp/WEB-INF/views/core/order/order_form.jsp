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

</script>
</head>
<body class="c-body">
<jsp:include page="/WEB-INF/views/commons/show_message.jsp"/>
<c:set var="usernameExist"><s:message code="user.username.exist"/></c:set>
<div class="c-bar margin-top5">
  <span class="c-position">订单管理 - <s:message code="${oprt=='edit' ? 'edit' : 'create'}"/></span>
</div>
<form id="validForm" action="${oprt=='edit' ? 'update' : 'save'}.do" method="post">
<tags:search_params/>
<f:hidden name="oid" value="${bean.id}"/>
<f:hidden name="position" value="${position}"/>
<input type="hidden" id="id" name="id" value="${bean.id}"/>
<input type="hidden" id="redirect" name="redirect" value="edit"/>
<table border="0" cellpadding="0" cellspacing="0" class="in-tb margin-top5">
  <tr>
    <td colspan="4" class="in-opt">
			
		  <div class="in-btn"><input type="button" value="<s:message code="return"/>" onclick="location.href='list.do?${searchstring}';"/></div>
	      <div class="in-btn"><input type="submit" value="<s:message code="save"/>"/></div>
	      <div class="in-btn"><input type="submit" value="<s:message code="saveAndReturn"/>" onclick="$('#redirect').val('list');"/></div>
    </td>
  </tr>
  <tr>
    <td class="in-lab" width="10%">订单号:</td>
    <td class="in-ctt" width="40%"><f:text name="orderNo" value="${bean.orderNo}" maxlength="64" readonly="readonly" style="width:180px;"/></td>
     <td class="in-lab" width="10%">用户地址:</td>
    <td class="in-ctt" width="40%"><f:text name="userAddress" value="${bean.userAddress}" readonly="readonly"  maxlength="64" style="width:380px;"/></td>
    
  </tr>
  <tr>
   <td class="in-lab" width="10%">商品ID:</td>
    <td class="in-ctt" width="40%"><f:text name="infoId" value="${bean.infoId}" readonly="readonly"  maxlength="64" style="width:180px;"/></td>
    
   <td class="in-lab" width="10%">商品名:</td>
    <td class="in-ctt" width="40%"><f:text name="infoName" value="${bean.infoName}" maxlength="64" readonly="readonly" style="width:380px;"/></td>
    
  </tr>
  <tr>
    <td class="in-lab" width="10%">商品性质:</td>
    <td class="in-ctt" width="40%"><f:text name="productPro" value="${bean.productPro}" maxlength="64" readonly="readonly" style="width:180px;"/></td>
     <td class="in-lab" width="10%">商品期数:</td>
    <td class="in-ctt" width="40%"><f:text name="infoPeriod" value="${bean.infoPeriod}" maxlength="64" readonly="readonly" style="width:180px;"/></td>
  </tr>
  <tr>
   <td class="in-lab" width="10%">用户姓名:</td>
    <td class="in-ctt" width="40%"><f:text name="userName" value="${bean.userName}" maxlength="64" readonly="readonly" style="width:180px;"/></td>
    <td class="in-lab" width="10%">用户电话:</td>
    <td class="in-ctt" width="40%"><f:text name="mobile" value="${bean.mobile}" maxlength="64" readonly="readonly" style="width:180px;"/></td>
    
  </tr>
 <tr>
    <td class="in-lab" width="10%">订单状态:</td>
    <td class="in-ctt" width="40%">
    	<select name="status">
    		<f:option value="1" selected="${bean.status}" default="0">未处理</f:option>
    		<f:option value="2" selected="${bean.status}">已发货</f:option>
    		<f:option value="3" selected="${bean.status}">订单完毕</f:option>
    	</select>
   	</td>
    <td class="in-lab" width="10%">订单时间:</td>
    <td class="in-ctt" width="40%"><input type="text" name="orderDate" readonly="readonly"  value="<fmt:formatDate value="${bean.orderDate}" pattern="yyyy-MM-dd'T'HH:mm:ss"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-ddTHH:mm:ss'});" style="width:180px;"/></td>
  </tr>
  <tr>
   <td class="in-lab" width="10%">物流单号:</td>
    <td class="in-ctt" width="40%"><f:text name="logisticsNo" value="${bean.logisticsNo}" maxlength="64" style="width:180px;"/></td>
    <td class="in-lab" width="15%">物流公司:</td>
    <td class="in-ctt" width="40%"><f:text name="logisticsName" value="${bean.logisticsName}" maxlength="64"  style="width:180px;"/></td>
    
  </tr>
  

  
</table>
</form>
</body>
</html>