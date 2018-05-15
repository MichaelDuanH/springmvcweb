<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>    

<html>
  <head>
     <meta http-equiv="Content-Type" content="text/html; charset=utf-8"> 
      <jsp:include page="base/jsp/base.jsp"/>
    <title>用户管理系统</title>
    <style type="text/css">
       .div-inline{
         display:inline;
       }
    </style>
  </head>
  <!--  style="width:100%;height:auto;" -->
  <body class="easyui-layout">
	<div  data-options="region:'west',title:'系统导航菜单',split:true,iconCls:'icon-side-tree'" style="width:200px;height:100px;padding:10px">
	   <div class="easyui-accordion" data-options="fit:true,border:false" style="height:100px;">
	            <div title="系统工具" style="height:100px;padding:10px;"data-options = "selected:true">
					<ul id="treeId"></ul>
				</div>
				<div title="附属功能" style="padding:10px;">
					<ul id="worldId"></ul>
				</div>
	   </div>
	</div>
    <div id="mainPanle" data-options="region:'center',fit:true,title:'系统主界面',iconCls:'icon-view-list'">
		<div id="tabId" data-options="fit:true,border:false,plain:true"  class="easyui-tabs">  
	      <div title="首页" style="padding:0px;display:none;" data-options="closable:true"> 
	        <body style="text-align:center">
	          <img style="text-align:center" />
	          <center>
	               <img id="picId" src="" width="1259" height="855"/>
	          </certer> 
	    </div> 
    </div> 
  </div>

<script type="text/javascript">
window.timout = '${timeout}'/360;
var parentId;
if(!parentId) {
	 parentId = 2;
}
 $('#treeId').tree({
	    url: '${pageContext.request.contextPath}/main/module/getModelTree?parentId=' + parentId,
	    animate:true,
	    method:"GET",
	    loadFilter: function(data){
	        console.log(data);
	        var arr = data.rows;
	    	return JSON.parse(arr);
	    },
	    onClick:function(node) {  
	    	var baseUrl = "${pageContext.request.contextPath}/";
            var title = node.text;  
            if(title == '系统退出') {
            	logOut();
         	    return;
            } else if(node.actionUrl && node.actionUrl.indexOf('.jsp') > 0) {
            	addTab(title,baseUrl + node.actionUrl)
            } else if (!node.actionUrl){
            	return;
            } else {
            	window.open(baseUrl + node.actionUrl,"_blank",'width=600,height=400');
            	return;
            }
           }
 }); 
 
 function addTab(title, url){                  
      content = '<iframe scrolling="auto" frameborder="0"  src="' + url+ '" style="width:100%;height:100%;"></iframe>';  
      if(!$("#tabId").tabs('exists', title)){  
          $("#tabId").tabs("add", {  
	       title : title,  
	       closable : true,  
	       content : content,  
	       width: $('#mainPanle').width() ,  
	       height: $('#mainPanle').height(),  
       });                   
    } else {  
         $('#tabId').tabs('select', title);  
     }            
   }  
 
 window.checkLoginPopupShow = false;
 window.checkLogin = function() {
     if (window.checkLoginPopupShow === false) {
         $.ajax({
       	  type : "POST",
             url : 'login/doCheckLogin',
         	  dataType : "json",
             cache : false,
             success : function(resp) {
                 if (resp.rows == "0") {
                     window.checkLoginPopupShow = true;
                     $.messager.alert('提示', '您的会话已超时，请重新登录后操作！', 'info', function() {
                         window.location.href = '${pageContext.request.contextPath}/';
                     });
                 }
             },
             error : function(resp, status, xhr) {
                 window.checkLoginPopupShow = true;
                 $.messager.alert('提示', '您已经长时间没有操作，请重新登录', 'info', function() {
               	  window.location.href = '${pageContext.request.contextPath}/';
                 });
             }
         });
     }
 }
 
  try {
     setInterval("checkLogin()", timout*1000);
    } catch(e) {
  }
    
    $.ajax({
		type : "POST",
		url : 'pic/showPic',
		dataType : "json",
		success : function(data) {
			if(data.total == 200) {
				$("#picId").attr('src',data.code + data.rows);
				return;
			}
			$.messager.alert('错误', data.rows);
			return;
		}
	}); 
    
    function logOut() {
   	 $.ajax({
   			type : "POST",
   			dataType : "json",
               cache : false,
   			url : 'toLogout',
   			success : function(data) {
   				console.log(data);
   			    $.messager.alert('提示', '您确定要退出系统吗？', 'info', function() {
   			    	if (data.code == 'exit') {
      					 window.location.href = '${pageContext.request.contextPath}/main/doLogout';
      					 return;
      				   }
   	            });
   				return;
   			}
   		}); 
   }
   </script>
  </body>
</html>