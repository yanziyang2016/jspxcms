<%@ tag pageEncoding="UTF-8"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fnx" uri="http://java.sun.com/jsp/jstl/functionsx"%><%@ taglib prefix="s" uri="http://www.springframework.org/tags" %><%@ taglib prefix="f" uri="http://www.jspxcms.com/tags/form"%><%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ attribute name="field" type="java.lang.Object" required="true" rtexprvalue="true"%>
<%@ attribute name="bean" type="java.lang.Object" required="true" rtexprvalue="true"%>
    	
    	<c:set var="c_name"><c:out value="${field}"/></c:set>
    	<c:set var="c_value"><c:out value="${bean}"/></c:set>
			<div id="editor_introduce"></div>
		  <script type="text/javascript">
		  var ueditor_introduce;
		var introduce_text='${c_value}';
				 introduce_text=introduce_text.replace(/&lt;/g, "<");
				 introduce_text=introduce_text.replace(/&gt;/g, ">");
				 introduce_text=introduce_text.replace(/&#034;/g, "'");
		  function create_ueditor_introduce(content) {
			  $("#editor_introduce").append("<script id='${c_name}' name='${c_name}' type='text/plain'><\/script>");
		    ueditor_introduce = UE.getEditor("${c_name}",{
		    	toolbars: window.UEDITOR_CONFIG.toolbars_Standard,
		      imageUrl:"${ctx}${cmscp}/core/upload_image.do?ueditor=true",
		      wordImageUrl:"${ctx}${cmscp}/core/upload_image.do?ueditor=true",
		      fileUrl:"${ctx}${cmscp}/core/upload_file.do;jsessionid=<%=request.getSession().getId()%>?ueditor=true",
		      videoUrl:"${ctx}${cmscp}/core/upload_video.do;jsessionid=<%=request.getSession().getId()%>?ueditor=true",
		      catcherUrl:"${ctx}${cmscp}/core/get_remote_image.do?ueditor=true",
		      imageManagerUrl:"${ctx}${cmscp}/core/image_manager.do",
		      getMovieUrl:"${ctx}${cmscp}/core/get_movie.do",
		    	localDomain:['${!empty GLOBAL.uploadsDomain ? GLOBAL.uploadsDomain : ""}']
		    });
		    ueditor_introduce.ready(function() {
		    	ueditor_introduce.setContent(content);
		    });
		  }
		  function delete_ueditor_introduce() {
			  var content = toMarkdown(ueditor_introduce.getContent());
			  UE.delEditor("${c_name}");
				$("#${c_name}").remove();
				$('.edui-default').remove();
			  return content;
		  }
		  var editormd_introduce;
		  function create_editormd_introduce(markdown) {
			  $("#editor_introduce").append("<div id='${c_name}'></div>");
			  editormd_introduce = editormd("${c_name}", {
				  name : "${c_name}_markdown",
				  markdown : markdown,
			    toolbarIcons: editormd.toolbar_Standard,
					width:"100%",
					height:360,
					watch : false,
					lineNumbers : false,
					styleActiveLine : true,
					styleSelectedText : true,
					codeFold : false,
					placeholder : "",
					syncScrolling : "single",
					path : "${ctx}/static/vendor/editormd/lib/",
					saveHTMLToTextarea : true,
					imageUpload : true,
					imageFormats : [ "jpg", "jpeg", "gif", "png", "bmp", "webp" ],
					imageUploadURL : "${ctx}/cmscp/core/upload_image.do?editormd=true",
					onfullscreen : function() {
						this.watch();
					},
					onfullscreenExit : function() {
						this.unwatch();
					},
					onload : function() {
						base64UploadPlugin(this, "${ctx}/cmscp/core/upload_image.do");
					}
				});
		  }
		  function delete_editormd_introduce() {
			  var content = editormd_introduce.getHTML();
			  editormd_introduce.editor.remove();
			  return content;
		  }
			  	create_ueditor_introduce(introduce_text);
		  </script>
 		
