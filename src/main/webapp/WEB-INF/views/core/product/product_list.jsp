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
	var statusName = $("input[name='ids']:checkbox:checked").parent().find("input[name='statusName']").val();
	if(statusName=="上架"){
		alert("请先下架，再进行编辑");
		return false;
	}
	var id = $("input[name='ids']:checkbox:checked").val();
	location.href=$(opt+id).attr("href");
}
function editinfo(opt,id,statusName) {
	if(statusName=="上架"){
		alert("请先下架，再进行编辑");
		return false;
	}
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
function optShangjia(form) {
	if(Cms.checkeds("ids")==0) {
		alert("<s:message code='pleaseSelectRecord'/>");
		return false;
	}
	if(!confirm("全部选中商品上架？")) {
		return false;
	}
	form.action='shangjia.do';
	form.submit();
	return true;
}
function optXiajia(form) {
	if(Cms.checkeds("ids")==0) {
		alert("<s:message code='pleaseSelectRecord'/>");
		return false;
	}
	if(!confirm("全部选中商品下架？")) {
		return false;
	}
	form.action='xiajia.do';
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
  <span class="c-position"><s:message code="product.manage"/> - <s:message code="list"/></span>
	<span class="c-total">(<s:message code="totalElements" arguments="${pagedList.totalElements}"/>)</span>
</div>
<form action="list.do" method="get">
	<fieldset class="c-fieldset">
    <legend><s:message code="search"/></legend>
	  <label class="c-lab">商品名称: <input type="text" name="search_CONTAIN_title" value="${search_CONTAIN_title[0]}" style="width:120px;"/></label>
      <label class="c-lab">商品一级分类:
      <select id="oneClassifyId" name="search_EQ_oneClassifyId">
        <option value=""><s:message code="allSelect"/></option>
        <option value="-1">未设置</option>
      </select>
      <label class="c-lab">商品二级分类:
      <select id="twoClassifyId" name="search_EQ_twoClassifyId">
        <option value=""><s:message code="allSelect"/></option>
        <option value="-1">未设置</option>
      </select>
    </label>
	  <label class="c-lab"><input type="submit" value="<s:message code="search"/>"/></label>
  </fieldset>
</form>
<form method="post">
<tags:search_params/>
<div class="ls-bc-opt">
	<shiro:hasPermission name="core:product:create">
	<div class="ls-btn"><input type="button" value="<s:message code="create"/>" onclick="location.href='create.do?${searchstring}';"/></div>
	<div class="ls-btn"></div>
	</shiro:hasPermission>
	<shiro:hasPermission name="core:product:edit">
	<div class="ls-btn"><input type="button" value="<s:message code="edit"/>" onclick="return optSingle('#edit_opt_');"/></div>
	</shiro:hasPermission>
	<div class="ls-btn"><input type="button" value="<s:message code="delete"/>" onclick="return optDelete(this.form);"/></div>
	<div class="ls-btn"><input type="button" value="上架" onclick="return optShangjia(this.form);"/></div>
	<div class="ls-btn"><input type="button" value="下架" onclick="return optXiajia(this.form);"/></div>
	<div style="clear:both"></div>
</div>
<table id="pagedTable" border="0" cellpadding="0" cellspacing="0" class="ls-tb margin-top5">
  <thead id="sortHead" pagesort="<c:out value='${page_sort[0]}' />" pagedir="${page_sort_dir[0]}" pageurl="list.do?page_sort={0}&page_sort_dir={1}&${searchstringnosort}">
  <tr class="ls_table_th">
    <th width="25"><input type="checkbox" onclick="Cms.check('ids',this.checked);"/></th>
    <th width="130"><s:message code="operate"/></th>
    <th width="30" class="ls-th-sort"><span class="ls-sort" pagesort="id">ID</span></th>
    <th class="ls-th-sort"><span class="ls-sort" pagesort="title">商品名称</span></th>
    <th class="ls-th-sort"><span class="ls-sort" pagesort="productpro">商品性质</span></th>
    <th class="ls-th-sort"><span class="ls-sort" pagesort="price">供货价格</span></th>
    <th class="ls-th-sort"><span class="ls-sort" pagesort="marketPrice">销售价格</span></th>
    <th class="ls-th-sort"><span class="ls-sort" pagesort="stock">商品库存</span></th>
    <th class="ls-th-sort"><span class="ls-sort" pagesort="periodCount">每期申请数</span></th>
    <th class="ls-th-sort"><span class="ls-sort" pagesort="status">商品状态</span></th>
    <th class="ls-th-sort"><span class="ls-sort" pagesort="oneClassifyId">商品一级分类</span></th>
    <th class="ls-th-sort"><span class="ls-sort" pagesort="twoClassifyId">商品二级分类</span></th>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="bean" varStatus="status" items="${pagedList.content}">
  <tr<shiro:hasPermission name="core:product_classify:edit"> ondblclick="location.href=$('#edit_opt_${bean.id}').attr('href');"</shiro:hasPermission>>
    <td><input type="checkbox" name="ids" value="${bean.id}"/><input type="hidden" name="statusName" value="${bean.statusName}" /></td>
    <td align="center">
		<shiro:hasPermission name="core:product_classify:edit">
		      <a id="edit_opt_${bean.id}" onclick="return editinfo('#edit_opt_','${bean.id}','${bean.statusName}');" href="edit.do?id=${bean.id}&position=${pagedList.number*pagedList.size+status.index}&${searchstring}" class="ls-opt"><s:message code="edit"/></a>
		</shiro:hasPermission>
		<shiro:hasPermission name="core:product_classify:delete">
			<a href="delete.do?ids=${bean.id}&${searchstring}" onclick="return confirmDelete();" class="ls-opt"><s:message code="delete"/></a>
 		</shiro:hasPermission>
     </td>
    <td>${bean.id}</td>
    <td align="center">${bean.title}</td>
     <td align="center">${bean.productproName}</td>
     <td align="center">${bean.price}</td>
     <td align="center">${bean.marketPrice}</td>
      <td align="center">${bean.stock}</td>
       <td align="center">${bean.periodCount}</td>
        <td align="center" >${bean.statusName}</td>
    <td align="center">${bean.oneClassifyName}</td>
    <td align="center">${bean.twoClassifyName}</td>
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
<script type="text/javascript">
//""<option value="sohu"<c:if test="${'sohu' eq search_EQ_sc[0]}"> selected="selected"</c:if>>搜狐</option>

var oneClassifyId=${search_EQ_oneClassifyId[0]}+"";
var twoClassifyId=${search_EQ_twoClassifyId[0]}+"";
$.ajax({    
    url:'oneClassifyList',  
    data:{    
             id :1
    },    
    type:'post',    
    cache:false,  
    async:false,  
    dataType:'json',    
    success:function(data) {   
    	if(data.length>0){
    		for(var i=0;i<data.length;i++){
    			$("#oneClassifyId").append('<option value="'+data[i].id+'">'+data[i].classifyName+'</option>');
    	    }
    		$("#oneClassifyId").val(typeof(oneClassifyId.length)=='undefined'?"":oneClassifyId);
    	}
	    
    }  ,
    error : function() { 
    	//alert(222);
    }
}); 
if(typeof(oneClassifyId.length)!='undefined'){
	$.ajax({    
	    url:'twoClassifyList',  
	    data:{    
	             id :oneClassifyId
	    },    
	    type:'post',    
	    cache:false,  
	    async:false,  
	    dataType:'json',    
	    success:function(data) {   
	    	if(data.length>0){
	    		for(var i=0;i<data.length;i++){
	    			$("#twoClassifyId").append('<option value="'+data[i].id+'">'+data[i].classifyName+'</option>');
	    	    }
	    		$("#twoClassifyId").val(typeof(twoClassifyId.length)=='undefined'?"":twoClassifyId);
	    	}
	    	
	    	if(twoClassifyId==-1){
	    		$("#twoClassifyId").val(-1);
	    	}
	    }  ,
	    error : function() { 
	    	//alert(222);
	    }
	});
}

$("#oneClassifyId").change(function(){
	$("#twoClassifyId").empty();
	$("#twoClassifyId").append('<option value=""><s:message code="allSelect"/></option>');
	if($("#oneClassifyId").val().length>0){
		$.ajax({    
		    url:'twoClassifyList',  
		    data:{    
		             id :$("#oneClassifyId").val()
		    },    
		    type:'post',    
		    cache:false,  
		    async:false,  
		    dataType:'json',    
		    success:function(data) { 
		    	if(data.length>0){
		    		for(var i=0;i<data.length;i++){
		    			$("#twoClassifyId").append('<option value="'+data[i].id+'">'+data[i].classifyName+'</option>');
		    	    }
		    	}
		    }  ,
		    error : function() { 
		    	//alert(222);
		    }
		});
	}
});


</script>
</body>
</html>