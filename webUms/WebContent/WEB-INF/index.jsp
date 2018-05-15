<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">  
<html>  
<head>  
<title>首页</title>  
<script type="text/javascript" src="main/js/easyui/jquery.min.js"></script>
<script type="text/javascript" src="main/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="main/js/easyui/jquery.cookie.js"></script>
<script type="text/javascript" src="main/js/vue/vue.min.js"></script>
<script type="text/javascript" src="main/js/vue/vue.js"></script>
<script type="text/javascript" src="main/js/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="main/js/bootstrap/js/bootstrap.js"></script>
<link href="main/js/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="main/js/easyui/style/themes/icon.css" rel="stylesheet" type="text/css" />
<link href="main/js/easyui/style/themes/default/easyui.css" rel="stylesheet" type="text/css" />
<link href="main/js/easyui/style/easyui.css" rel="stylesheet" type="text/css" />
<link href="main/i/logo.ico" type="image/x-icon" rel="icon"/>
<style type="text/css">
  .blurry {
      padding-left: 3cm;
      width:30px;
      height:10px;
  }
 
</style>
</head>  
 <body>  
   <div id="vueApp">
    <div class="easyui-window" :title="loginTitle"  style="width:400px;padding:20px 70px 50px 70px;">  
        <div style="margin-bottom:10px">  
            <input class="easyui-textbox" id="logname" style="width:100%;height:30px;padding:12px" data-options="prompt:'登录用户名',iconCls:'icon-man',iconWidth:38">  
        </div> 
        <div style="margin-bottom:20px">  
            <input class="easyui-textbox" id="logpass" type="password" style="width:100%;height:30px;padding:12px" data-options="prompt:'登录密码',iconCls:'icon-lock',iconWidth:38">  
        </div>  
        <div style="margin-bottom:10px;">  
            <input class="easyui-textbox" type="text" id="logyzm" style="width:50%;height:30px;padding:12px;float:right;"> 
            <a href="javascript:;"  @click="getPic();" >
                <img id="codePic"  style="float:right;"/>
            </a>  
        </div>  
        <div>  
          <a href="javascript:;" onclick="dologin()" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" style="padding:5px 0px;width:100%;">  
              <span style="font-size:14px;">{{loginTitle}}</span>  
          </a> 
          <span><img id="qrCode" style="float:right; margin-botton:10px;" /></span>
        </div> 
        
    </div>
  </div>   
  <script type="text/javascript">
  
  var vm = new Vue({
	  el:"#vueApp",
	  data:{
		  loginTitle:"登录"
	  }
  })
  window.timeOut = '${base.code}';
  changeVeryfy();
  var verCode;
  function changeVeryfy() {
	  $.ajax({
			type : "get",
			url : 'verCode',
			success : function(data) {
				console.log(data);
				$("#verCode").val(data);
				verCode = data;
			}
		});
	  getPic();
  }
  
  var ip;
  $.ajax({
		type : "get",
		url : 'getIpAdr',
		success : function(data) {
			console.log(data);
			ip = data.trim();
			$("#codePic").attr("src","http://"+eval(data)+":8080/webUms/imageCode?flag="+Math.random());
			$("#qrCode").attr("src","http://"+eval(data)+":8080/webUms/getQrImgCode?flag="+Math.random());
		}
  });
 
  
  function getPic() {   
      $("#codePic").attr("src","http://"+ eval(ip) + ":8080/webUms/imageCode?flag="+Math.random());   
  };  
  
  
 
  function dologin () {
	  var inputVerCode = $('#logyzm').val();
	  var userName = $('#logname').val();
	  var passWord = $('#logpass').val();
	  if(!inputVerCode ||  !logname || !logpass) {
		  $.messager.alert('提示','请验证填写表单是否完整') ;
		  return;
	  }
	  var data = {};
	  data.inputVerCode = inputVerCode;
	  data.verCode = verCode;
	  data.userName = userName;
	  data.passWord = passWord;
	  $.ajax({
			type : 'POST',
			headers: {'cookies' : document.cookie },
			url : 'login/doLogin',
			dataType : 'json',
			contentType: 'application/json',
			data:JSON.stringify({
				  'inputVerCode':inputVerCode,
				  'verCode':verCode,
				  'userName':userName,
				  'passWord':passWord,
				  'loginId':uuid(10,70)}),
			success : function(data) {
				if(data.code == '200') {
					  var param = (data.total)*360
					  window.location.href="main/mainPage?total="+param+'&pv='+'${pageContext.request.getSession().getId()}';
					/*   $.messager.alert('提示', data.rows); */
				} else if(data.code == 'userNotExist'){
					$.messager.alert('错误', data.rows);
					changeVeryfy();
				} else if (data.code == 'pwdErr') {
					$.messager.alert('错误',data.rows);
					changeVeryfy();
				} else if (data.code == 'validErr') {
					$.messager.alert('错误', data.rows);
					changeVeryfy();
				}
				
				return;
			}
		});
  }
  
  function uuid(len, radix) {
	     var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
	     var uuid = [], i;
	     radix = radix || chars.length;
	  
	     if (len) {
	       // Compact form
	       for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random()*radix];
	     } else {
	       // rfc4122, version 4 form
	       var r;
	  
	       // rfc4122 requires these characters
	       uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
	       uuid[14] = '4';
	  
	       // Fill in random data.  At i==19 set the high bits of clock sequence as
	       // per rfc4122, sec. 4.1.5
	       for (i = 0; i < 36; i++) {
	         if (!uuid[i]) {
	           r = 0 | Math.random()*16;
	           uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
	         }
	       }
	     }
	  
	     return uuid.join('');
	 }
  
  if(!$.cookie("logname")) {
	  $.cookie("logname", $("#logname").val(), {path: "/", expires: 7, secure:true});
　　}
  </script>
 </body>  
</html>