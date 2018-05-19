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
<link href="main/i/logo.ico" type="image/x-icon" rel="icon"/>
<style type="text/css">
   #loginForm {
       vertical-align: middle;
       margin-top: 150px;
       margin-left:350px;
       height: 240px;
   }
</style>
</head>  
 <body>  
   <div id="vueApp">
    <form role="form" class="form-horizontal" id="loginForm">
      <div class="form-group">
           <label for="logname" class="col-sm-2 control-label">用户</label>
	           <div class="col-sm-10" style="width:430px">
	              <input type="text" class="form-control" id="logname" v-model="logname" placeholder="请输入用户名">
	          </div>
      </div>
      <div class="form-group">
      
           <label for="password" class="col-sm-2 control-label">密码</label>
	           <div class="col-sm-10" style="width:430px">
	              <input type="password" class="form-control" v-model="password" id="password" placeholder="请输入密码">
	          </div>
      </div>
      <div class="form-group">
       <label for="logyzm" class="col-sm-2 control-label">验证码</label>
       <div class="col-sm-7" style="width:300px">
          <input  type="text" class="form-control" id="logyzm" v-model="logyzm"> 
       </div>
       <div class="col-sm-1">
         <a href="javascript:;"  @click="getPic();" >
                <img id="codePic"/>
            </a>  
       </div>
            
      </div>
      <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button @click="dologin()" class="btn btn-default">{{loginTitle}}</button>
        </div>
    </div>
    </form>
  </div>   
  <script type="text/javascript">
  
  var vm = new Vue({
	  el:"#vueApp",
	  data:{
		  loginTitle:"登录",
		  logname:"",
		  password:"",
		  logyzm:"",
		  timeOut:'${base.code}',
		  ip:""
	  },
	  methods:{
		  getIpAdr:function() {
			  $.ajax({
					type : "get",
					url : 'getIpAdr',
					success : function(data) {
						this.ip = data.trim();
						$("#codePic").attr("src","http://"+eval(this.ip)+":8080/webUms/imageCode?flag="+Math.random());
					}
			  });
		  },
		  changeVeryfy:function(){
			  $.ajax({
					type : "get",
					url : 'verCode',
					success : function(data) {
						$("#logyzm").val(data);
						verCode = data;
					}
				});
			  this.getPic();
		  },
		  getPic:function() {  
			  var that = this;
			  if(!that.ip) {
				  $.ajax({
						type : "get",
						url : 'getIpAdr',
						success : function(data) {
							that.ip = data.trim();
						}
				  });
			  }
		      $("#codePic").attr("src","http://"+ eval(that.ip) + ":8080/webUms/imageCode?flag="+Math.random());   
		  },
		  dologin:function() {
			  if(!this.logyzm ||  !this.logname || !this.password) {
				  $.messager.alert('提示','请验证填写表单是否完整') ;
				  return;
			  }
			  var data = {};
			  var that = this;
			  $.ajax({
					type : 'POST',
					headers: {'cookies' : document.cookie },
					url : 'login/doLogin',
					dataType : 'json',
					contentType: 'application/json',
					data:JSON.stringify({
						  'inputVerCode':this.logyzm,
						  'userName':this.logname,
						  'passWord':this.password,
						  'loginId':this.uuid(10,70)}),
					success : function(data) {
						if(data.code == '200') {
							  var param = (data.total)*360
							  window.location.href="main/mainPage?total="+param+'&pv='+'${pageContext.request.getSession().getId()}';
							/*   $.messager.alert('提示', data.rows); */
						} else if(data.code == 'userNotExist'){
							$.messager.alert('错误', data.rows);
							that.changeVeryfy();
						} else if (data.code == 'pwdErr') {
							$.messager.alert('错误',data.rows);
							that.changeVeryfy();
						} else if (data.code == 'validErr') {
							$.messager.alert('错误', data.rows);
							that.changeVeryfy();
						}
						return;
					}
				});
		  },
		  
		  uuid:function(len, radix) {
			     var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
			     var uuid = [], i;
			     radix = radix || chars.length;
			     if (len) {
			       for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random()*radix];
			     } else {
			       var r;
			       uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
			       uuid[14] = '4';
			  
			       for (i = 0; i < 36; i++) {
			         if (!uuid[i]) {
			           r = 0 | Math.random()*16;
			           uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
			         }
			       }
			     }
			     return uuid.join('');
			 }
	  }
  })
  vm.getPic();
  vm.getIpAdr();
  if(!$.cookie("logname")) {
	  $.cookie("logname", $("#logname").val(), {path: "/", expires: 7, secure:true});
　　}
  </script>
 </body>  
</html>