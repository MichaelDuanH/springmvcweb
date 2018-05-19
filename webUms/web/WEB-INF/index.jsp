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
    <div role="form" class="form-horizontal" id="loginForm">
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
    </div>
    
        <div class="modal" id="my-modal-alert">  
        <div class="modal-dialog">  
            <div class="modal-content">  
                <div class="modal-header">  
                    <button type="button" class="close" data-dismiss="modal">  
                        <span aria-hidden="true">×</span><span class="sr-only">Close</span>  
                    </button>  
                    <h4 class="modal-title" id="modal-title">提示</h4><span id="num"></span>  
                </div>  
                <!--/*modal-header*/-->  
                <div class="modal-body">  
                    <div id="modal_message"><span id="message">modal_message</span></div>  
                </div>  
                <!--/*modal-body*/-->  
                <div class="modal-footer" id="modal-footer">  
                    <button type="button" class="btn btn-default" data-dismiss="modal">确定</button>  
                </div>  
            </div>  
        </div>  
      </div>
   </div> 
  <script>
  var vm = new Vue({
	  el:"#vueApp",
	  data:{
		  loginTitle:"登录",
		  logname:"",
		  password:"",
		  logyzm:"",
		  verCode:"",
		  timeOut:'${base.code}'
	  },
	  mounted:function(){
		  $.ajax({
				type : "get",
				url : 'getIpAdr',
				success : function(data) {
					this.ip = data.trim();
					$("#codePic").attr("src","http://"+ eval(this.ip) + ":8080/webUms/imageCode?flag="+Math.random());
				}
		  });
	  },
	  methods:{
		  
		  getPic:function() { 
			  $.ajax({
					type : "get",
					url : 'getIpAdr',
					success : function(data) {
						 $("#codePic").attr("src","http://"+ eval(data) + ":8080/webUms/imageCode?flag="+Math.random());
					}
			  });
		  },
		  changeVeryfy:function() {
			  $.ajax({
					type : "get",
					url : 'verCode',
					success : function(data) {
						/* $("#logyzm").val(data); */
						this.verCode = data;
					}
				});
			 this.getPic();
		  }, 
		  clearContent:function(ths) {
			  ths.logname="";
			  ths.password="";
			  ths.logyzm="";
		  },
		  showMsg:function(msg) {
			  $("#my-modal-alert").modal("toggle");  
	          $(".modal-backdrop").remove(); 
	          $("#message").text(msg);  
		  },
		  dologin:function() {
			  var that = this;
			  if(!that.logyzm ||  !that.logname || !that.password) {
				    that.showMsg("请正确填写表单格式");
				    that.clearContent(that);
		            return;
			  }
			 
			  $.ajax({
					type : 'POST',
					headers: {'cookies' : document.cookie },
					url : 'login/doLogin',
					dataType : 'json',
					contentType: 'application/json',
					data:JSON.stringify({
						  'inputVerCode':that.logyzm,
						  'userName':that.logname,
						  'passWord':that.password,
						  'loginId':that.uuid(10,70)}),
					success : function(data) {
						if(data.code == '200') {
							  var param = (data.total)*360
							  window.location.href="main/mainPage?total="+param+'&pv='+'${pageContext.request.getSession().getId()}';
						} else if(data.code == 'userNotExist') {
							  that.changeVeryfy();
							  that.showMsg("用户不存在");
							  that.clearContent(that);
							  
						} else if (data.code == 'pwdErr') {
							  that.changeVeryfy();
							  that.showMsg("密码错误");
							  that.clearContent(that);
						} else if (data.code == 'validErr') {
							  that.changeVeryfy();
							  that.showMsg("验证码错误");
							  that.clearContent(that);
						}
						
					}
				})
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
/*   if(!$.cookie("logname")) {
	  $.cookie("logname", $("#logname").val(), {path: "/", expires: 7, secure:true});
　　} */
  </script>

 </body>  
</html>