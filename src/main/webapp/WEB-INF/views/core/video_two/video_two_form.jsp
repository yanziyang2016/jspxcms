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
  <span class="c-position"><s:message code="video.manage"/> - <s:message code="${oprt=='edit' ? 'edit' : 'create'}"/></span>
</div>
<form id="validForm" action="${oprt=='edit' ? 'update' : 'save'}.do" method="post">
<tags:search_params/>
<f:hidden name="oid" value="${bean.vmid}"/>
<f:hidden name="position" value="${position}"/>
<input type="hidden" id="redirect" name="redirect" value="edit"/>
<table border="0" cellpadding="0" cellspacing="0" class="in-tb margin-top5">
  <tr>
    <td colspan="4" class="in-opt">
			<shiro:hasPermission name="core:video_two:delete">
			<div class="in-btn"><input type="button" value="<s:message code="delete"/>" onclick="if(confirmDelete()){location.href='delete.do?ids=${bean.vmid}&${searchstring}';}"<c:if test="${oprt=='create' || bean.vmid le 1}"> disabled="disabled"</c:if>/></div>
			</shiro:hasPermission>
			<div class="in-btn"></div>
			<div class="in-btn"></div>
			<div class="in-btn"><input type="button" value="<s:message code="return"/>" onclick="location.href='list.do?${searchstring}';"/></div>
      <div style="clear:both;"></div>
    </td>
  </tr>
  <tr>
    <td class="in-lab" width="15%">名称:</td>
    <td class="in-ctt" width="85%" colspan="3"><f:text name="title" value="${bean.title}" style="width:730px;"/></td>
    
  </tr>
  <tr>
    <td class="in-lab" width="15%">地区:</td>
    <td class="in-ctt" width="35%"><f:text name="area" value="${bean.area}" style="width:180px;"/></td>
    <td class="in-lab" width="15%">上映时间:</td>
    <td class="in-ctt" width="35%"><f:text name="year" value="${bean.year}" maxlength="100" style="width:180px;"/></td>
  </tr>
  <tr>
 	 <td class="in-lab" width="15%">视频源分类:</td>
    <td class="in-ctt" width="35%"><f:text name="cname" value="${bean.cname}" maxlength="100" style="width:180px;"/></td>
    <td class="in-lab" width="15%">评分:</td>
    <td class="in-ctt" width="35%"><f:text name="score" value="${bean.score}" style="width:180px;"/></td>
  </tr>
  <tr>
 	 <td class="in-lab" width="15%">本站一级分类:</td>
    <td class="in-ctt" width="35%">
	    <select id="oneClassifyId" name="oneClassifyId">
	   		 <option value="-1"></option>
	    </select>
    </td>
    <td class="in-lab" width="15%">本站二级分类:</td>
    <td class="in-ctt" width="35%">
	    <select id="twoClassifyId" name="twoClassifyId">
	   		 <option value="-1"></option>
	    </select>
    </td>
  </tr>
   <tr>
    <td class="in-lab" width="15%">导演:</td>
    <td class="in-ctt" width="85%" colspan="3">
    	<f:text name="dt" value="${bean.dt}" maxlength="200" style="width:730px;"/>
    </td>
  </tr>
   <tr>
    <td class="in-lab" width="15%">主要演员:</td>
    <td class="in-ctt" width="85%" colspan="3">
    	<f:text name="mA" value="${bean.mA}" maxlength="200" style="width:730px;"/>
    </td>
  </tr>
  <tr>
    <td class="in-lab" width="15%">剧情介绍:</td>
    <td class="in-ctt" width="85%" colspan="3"><f:textarea name="desc" value="${bean.desc}" maxlength="555" style="width:730px;height:200px;"/></td>
  </tr>
   <tr>
    <td class="in-lab" width="15%">封面图片:</td>
    <td class="in-ctt" width="85%" colspan="3"><f:textarea name="bigPic" value="${bean.bigPic}" maxlength="555" style="width:730px;height:50px;"/>
    <input type="hidden" name="id" value="${bean.vmid}" /></td>
  </tr>

  <tr>
    <td colspan="4" class="in-opt">
      <div class="in-btn"><input type="submit" value="<s:message code="save"/>"/></div>
      <div class="in-btn"><input type="submit" value="<s:message code="saveAndReturn"/>" onclick="$('#redirect').val('list');"/></div>
      <div style="clear:both;"></div>
    </td>
  </tr>
</table>
</form>
<script  type="text/javascript">
var oneClassifyId=${oneClassifyId};
var twoClassifyId=${twoClassifyId};
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
    			$("#oneClassifyId").append('<option value="'+data[i].id+'">'+data[i].videoClassifyName+'</option>');
    	    }
    		$("#oneClassifyId").val(oneClassifyId);
    	}
	    
    }  ,
    error : function() { 
    	//alert(222);
    }
}); 
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
    			$("#twoClassifyId").append('<option value="'+data[i].id+'">'+data[i].videoClassifyName+'</option>');
    	    }
    		$("#twoClassifyId").val(twoClassifyId);
    	}
    }  ,
    error : function() { 
    	//alert(222);
    }
});
$("#oneClassifyId").change(function(){
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
	    	$("#twoClassifyId").empty();
	    	if(data.length>0){
	    		for(var i=0;i<data.length;i++){
	    			$("#twoClassifyId").append('<option value="'+data[i].id+'">'+data[i].videoClassifyName+'</option>');
	    	    }
	    		$("#twoClassifyId").val(twoClassifyId);
	    	}else{
	    		$("#twoClassifyId").append('<option value="-1"></option>');
	    	}
	    }  ,
	    error : function() { 
	    	//alert(222);
	    }
	});
});
</script>
</body>
</html>