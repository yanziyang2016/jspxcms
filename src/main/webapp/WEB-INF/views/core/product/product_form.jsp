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
  <span class="c-position"><s:message code="product.manage"/> - <s:message code="${oprt=='edit' ? 'edit' : 'create'}"/></span>
</div>
<form id="validForm" action="${oprt=='edit' ? 'update' : 'save'}.do" method="post">
<tags:search_params/>
<f:hidden name="id" value="${bean.id}"/>
<f:hidden name="position" value="${position}"/>
<input type="hidden" id="redirect" name="redirect" value="edit"/>
<table border="0" cellpadding="0" cellspacing="0" class="in-tb margin-top5">
  <tr>
    <td colspan="4" class="in-opt">
			<shiro:hasPermission name="core:product:delete">
			<div class="in-btn"><input type="button" value="<s:message code="delete"/>" onclick="if(confirmDelete()){location.href='delete.do?ids=${bean.id}&${searchstring}';}"<c:if test="${oprt=='create' || bean.id le 1}"> disabled="disabled"</c:if>/></div>
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
    <td class="in-lab" width="15%">供货价格:</td>
    <td class="in-ctt" width="35%"><f:text name="price" value="${bean.price}" style="width:180px;"/></td>
    <td class="in-lab" width="15%">销售价格:</td>
    <td class="in-ctt" width="35%"><f:text name="marketPrice" value="${bean.marketPrice}" maxlength="100" style="width:180px;"/></td>
  </tr>
  
  <tr>
    <td class="in-lab" width="15%">标题图:</td>
    <td class="in-ctt" width="85%" colspan="3">
          <tags:image_upload name="smallImage" value="${bean.smallImage}" width="200" height="200" />
    </td>
    
  </tr>
  
  <tr>
    <td class="in-lab" width="15%">正文图:</td>
    <td class="in-ctt" width="85%" colspan="3">
          <tags:image_upload name="largeImage" value="${bean.largeImage}" width="480" height="480" />
    </td>
    
  </tr>
  
 
  
  
  <tr>
    <td class="in-lab" width="15%">商品库存:</td>
    <td class="in-ctt" width="35%"><f:text name="stock" value="${bean.stock}" style="width:180px;"/></td>
    <td class="in-lab" width="15%">每期申请数:</td>
    <td class="in-ctt" width="35%"><f:text name="periodCount" value="${bean.periodCount}" maxlength="100" style="width:180px;"/></td>
  </tr>
  <tr>
 	 <td class="in-lab" width="15%">商品性质:</td>
      <td class="in-ctt" width="35%">
	    <select id="productpro" name="productpro">
	   		 <option value="1">虚拟商品</option>
	   		 <option value="2">实物商品</option>
	    </select>
    </td>
    <td class="in-lab" width="15%">商品状态:</td>
    <td class="in-ctt" width="35%">
	    <select id="status" name="status">
	   		 <option value="0">下架</option>
	   		 <option value="1">上架</option>
	    </select>
    </td>
  </tr>
  <tr>
 	 <td class="in-lab" width="15%">一级分类:</td>
    <td class="in-ctt" width="35%">
	    <select id="oneClassifyId" name="oneClassifyId">
	   		 <option value="-1"></option>
	    </select>
    </td>
    <td class="in-lab" width="15%">二级分类:</td>
    <td class="in-ctt" width="35%">
	    <select id="twoClassifyId" name="twoClassifyId">
	   		 <option value="-1"></option>
	    </select>
    </td>
  </tr>
  <tr>
    <td colspan="4" class="in-opt">
	    <div>商品介绍：</div>
		<div>
		 <tags:feild_introduce bean="${bean.introduce}" field="introduce"/>
		</div>
	</td>
</tr>
 <tr>
    <td colspan="4" class="in-opt">
	    <div>规格参数：</div>
		<div>
		 <tags:feild_specification bean="${bean.specification}" field="specification"/>
		</div>
	</td>
</tr>
 <tr>
    <td colspan="4" class="in-opt">
	    <div>包装清单：</div>
		<div>
		 <tags:feild_packingList bean="${bean.packingList}" field="packingList"/>
		</div>
	</td>
</tr>
 <tr>
    <td colspan="4" class="in-opt">
	    <div>售后服务：</div>
		<div>
		 <tags:feild_services bean="${bean.services}" field="services"/>
		</div>
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
	
</form>
<script  type="text/javascript">
$(function() {
	$("#validForm").validate({
		submitHandler: function(form) {
			if($("#oneClassifyId").val()==-1) {
				alert("一级分类未设置");
				return;
			}
			form.submit();
		}
	});
});
function imgCrop(name) {
	if($("#"+name).val()=="") {alert("<s:message code='noImageToCrop'/>");return;}
	Cms.imgCrop("../../commons/img_area_select.do",name);
}

var oneClassifyId=${oneClassifyId};
var twoClassifyId=${twoClassifyId};
var productpro=${productpro};
var status=${status};
$("#productpro").val(productpro);
$("#status").val(status);
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
    			$("#twoClassifyId").append('<option value="'+data[i].id+'">'+data[i].classifyName+'</option>');
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
	    			$("#twoClassifyId").append('<option value="'+data[i].id+'">'+data[i].classifyName+'</option>');
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