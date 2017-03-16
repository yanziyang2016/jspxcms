<%@ tag pageEncoding="UTF-8"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fnx" uri="http://java.sun.com/jsp/jstl/functionsx"%><%@ taglib prefix="s" uri="http://www.springframework.org/tags" %><%@ taglib prefix="f" uri="http://www.jspxcms.com/tags/form"%><%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ attribute name="field" type="java.lang.Object" required="true" rtexprvalue="true"%>
<%@ attribute name="bean" type="java.lang.Object" required="true" rtexprvalue="true"%>
    	
    	<c:set var="c_name"><c:out value="${field}"/></c:set>
    	<c:set var="c_value"><c:out value="${bean}"/></c:set>
			<div id="editor_specification"></div>
		  <script type="text/javascript">
		  var ueditor_specification;
		var specification_text='${c_value}';
		//var specification_text=' ​<p><img src="/uploads/1/image/public/201703/20170316133613_rb309tcidb.jpg" title="test.jpg"/></p>';
				// var specification_text='&lt;p&gt;&lt;img src=&#034;/uploads/1/image/public/201703/20170316133613_rb309tcidb.jpg&#034; title=&#034;test.jpg&#034;/&gt;&lt;/p&gt;';
				 specification_text=specification_text.replace(/&lt;/g, "<");
				 specification_text=specification_text.replace(/&gt;/g, ">");
				 specification_text=specification_text.replace(/&#034;/g, "'");
		  function create_ueditor_specification(content) {
			  $("#editor_specification").append("<script id='${c_name}' name='${c_name}' type='text/plain'><\/script>");
		    ueditor_specification = UE.getEditor("${c_name}",{
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
		    ueditor_specification.ready(function() {
		    	ueditor_specification.setContent(content);
		    });
		  }
		  function delete_ueditor_specification() {
			  var content = toMarkdown(ueditor_specification.getContent());
			  UE.delEditor("${c_name}");
				$("#${c_name}").remove();
				$('.edui-default').remove();
			  return content;
		  }
		  var editormd_specification;
		  function create_editormd_specification(markdown) {
			  $("#editor_specification").append("<div id='${c_name}'></div>");
			  editormd_specification = editormd("${c_name}", {
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
		  function delete_editormd_specification() {
			  var content = editormd_specification.getHTML();
			  editormd_specification.editor.remove();
			  return content;
		  }
			  	create_ueditor_specification(specification_text);
		  </script>
 		
