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
 $('#treeId').tree({
	    url: 'main/module/getModelTree',
	    animate:true,
	    loadFilter: function(data){
	    	console.log('------------data----------');
	    	console.log(data);
				return data;
	    },
	    onClick:function(node) {  
    /*         var title = node.text;  
               if (title=="关于系统") {  
                    var url = "${pageContext.request.contextPath}/main/about.jsp" ;                  
                    addTab(title, url);  
               } else if (title=="用户配置") {  
                    var url = "${pageContext.request.contextPath}/main/user.jsp" ;      
                    addTab(title, url);  
               } else if (title=="系统属性") {  
                    var url = "${pageContext.request.contextPath}/main/system.jsp?total="+"${timeout}"/360 ;     
                    addTab(title, url);  
               } else if (title == "系统退出") {
            	   var url = "${pageContext.request.contextPath}/main/doLogout";
            	   window.location.href = url;
               } else if (title=="系统设置") {
            	   var url = "${pageContext.request.contextPath}/main/mainPage";
               } else if (title == "修改密码") {
            	   var url = "${pageContext.request.contextPath}/main/password.jsp";
            	   addTab(title, url);
               } else {  
               	   $.messager.alert('提示', '功能模块未开发');
               }  */
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
   </script>
  </body>
</html>