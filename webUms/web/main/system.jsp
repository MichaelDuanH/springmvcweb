<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="java.util.*" %>
<%@ page import="com.ums.common.util.*" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>    

<html>
  <head>
     <meta http-equiv="Content-Type" content="text/html;charset=utf-8"> 
    <%-- <jsp:include page="base/jsp/base.jsp"/> --%>
<!--     <link href="js/upload/example/css/default.css" rel="stylesheet" type="text/css" /> -->
    <link href="js/upload/uploadify.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="js/upload/jquery-1.3.2.min.js"></script>
    <script type="text/javascript" src="js/upload/swfobject.js"></script>
    <script type="text/javascript" src="http://pv.sohu.com/cityjson?ie=utf-8"></script>
    <script type="text/javascript" src="js/upload/jquery.uploadify.v2.1.0.js"></script>
    <link href="main/i/logo.ico" type="image/x-icon" rel="icon"/>
<script type="text/javascript">
     $(document).ready(function() {
	              $("#uploadify").uploadify({
	                  'uploader':'js/upload/uploadify.swf',
	                  'script':'upload',
	                  'cancelImg':'js/upload/cancel.png',
	                  'folder':'img',
	                  'queueID':'fileQueue',
	                  'buttonText':"browse",
	                  'simUploadLimit':3,
	                  'queueSizeLimit':3, 
	                  'fileExt': '*.jpg;*.png;',
	                  'auto':false,
	                  'multi':true,
					  'onComplete' : function(event,qId,fileObjInfo,returnData) {
						  var fileJsonObj = JSON.parse(returnData);
						    var fileName = fileJsonObj.rows;
						    var newPath = 'img/';
					        $("#pic_div").html('<img src="http://localhost:8080/TestProject/main/img/'+fileName+'">');
					  },
	                  'onCancel':function() {
	                	  alert('cancle this file');
	                  },
	                  'onError':function() {
	                	  alert('error')
	                  }
	              });
	         });
     </script>
  </head>
  <body style="width:1500px;height:auto;">
    <h1 style="text-align:left"> 系统属性  </h1>
    <div>
       <ul style="list-style:none;">
          <li>
             <div>当前登陆用户  : ${user.userCode}</div>
          </li>
          <li>
             <div>登录时间  : ${user.createTm}</div> 
          </li>
          <li>
            <span style="float:left;">当前系统时间  : </span> <div id="currTimeId"> <%-- <%=DateUtil.dateToTimeStam(new Date())%> --%></div>
          </li>
         <!--  <li>
          <span>首页图片上传</span>
              <input type="hidden" name="photo" id="photoId" value="">
               <div id="pic_div">  
               </div>  
                <div id="fileQueue"></div>
	           <input type="file" name="uploadify" id="uploadify" />
	           <p>
			      <a href="javascript:$('#uploadify').uploadifyUpload();">上传</a>|
                  <a href="javascript:$('#uploadify').uploadifyClearQueue()">取消上传</a>
	           </p>
          </li> -->
          <li>
              <span style="float:left;">操作文档下载  : </span>
              <span id="showDocId">
                  <a href="">系统操作指引</a>
              </span>
          </li>
          <li>
           <span style="float:left;">当前所在城市  :</span><div id="localCity"></div>
          </li>
          <li>
           <span style="float:left;">当前客户端IP :</span><div id="localIP"></div>
          </li>
          <li>
              <div>系统被登陆的次数 :${user.logTimes}</div>
          </li>
       </ul>
    </div>
    <script>
    function toYYYYMMDDHHMMSS(date) {
    	if (date) {
    		var yy = date.getFullYear();
    		var mm = date.getMonth() + 1;
    		var dd = date.getDate();
    		var hrs = date.getHours();
    		var min = date.getMinutes();
    		var sec = date.getSeconds();
    		return yy+'-'+(mm<10?'0'+mm:mm)+'-'+(dd<10?'0'+dd:dd)+' '+(hrs<10?'0'+hrs:hrs)+':'+(min<10?'0'+min:min)+':'+(sec<10?'0'+sec:sec);
    	}
    }
    
    
    function updateTime() {
    	var t=new Date();
    	document.getElementById("currTimeId").innerHTML=toYYYYMMDDHHMMSS(t);
    	var s = setTimeout(updateTime,1000)
    }
    
    updateTime();
    
   /*  setInterval(function(){
    	var t=new Date();
        document.getElementById("currTimeId").innerHTML=toYYYYMMDDHHMMSS(t);
    },1000); */
    
    document.getElementById("localCity").innerHTML =  returnCitySN["cname"]; // returnCitySN["cip"]+','+returnCitySN["cname"]
    document.getElementById("localIP").innerHTML =  returnCitySN["cip"]; 
    $("#showDocId a").attr("href","${pageContext.request.contextPath}/main/pic/showDoc");
    </script>
  </body>
</html>