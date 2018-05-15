<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="java.util.*" %>
<%@ page import="com.ums.common.util.*" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>    
<html>
  <head>
     <meta http-equiv="Content-Type" content="text/html; charset=utf-8"> 
     <jsp:include page="base/jsp/base.jsp"/>
  </head>
  <body>
    <div id="bodyContent">
        <div style="margin-left:400px;margin-top:200px;">
            <form id="pwdBox" action="#" method="post" class="easyui-panel" style="width:400px">
                <div>
                    <div style="padding:10px 80px 20px 60px">
                    <div>
                           <label for="account">帐号</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                           <input class="easyui-validatebox" data-options="required:true,validType:['length[3,10]','noSensitive']" 
                                     type="text" id="username" name="userName" value="${user.userName}"/>
                    </div>
                    &nbsp;
                    <div>
                           <label>旧密码</label>&nbsp;&nbsp;
                           <input class="easyui-validatebox" data-options="required:true,validType:['validatePass']" 
                                     id="oldPassword" type="password" name="pass"/>
                     </div>
                     &nbsp;
                     <div>
                            <label>新密码</label>&nbsp;&nbsp;
                            <input class="easyui-validatebox" required = 'true' validType = 'checkPass' id="newPasswordId" type="password" name="newPass"/>
                     </div>
                     &nbsp;
                     <div>
                            <label>确认:</label>&nbsp;&nbsp;&nbsp;&nbsp;
                            <input class="easyui-validatebox" required = 'true' type="password" validType="equalTo[&quot;#newPasswordId&quot;]"/>
                      </div>
                      &nbsp;
                      <div>
                            <label>&nbsp;</label>
                            <div>
                                <input type="button" onclick="doChangePass()" value="修改密码" class="easyui-linkbutton" style="line-height:26px"/>
                            </div>
                       </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
  
<script type="text/javascript">
    window.pass = '${user.pas}'
   function doChangePass () {
   	    if (!$("#pwdBox").form('validate')) {
   	        return false;
   	    }
   	    console.log($("#pwdBox").serializeJSON());
        $.ajax({
			type : "POST",
			url : 'user/updateUserPass',
			dataType : "json",
			data: $("#pwdBox").serializeJSON(),
			success : function(data) {
				if(data.code == '200') {
					$.messager.alert('提示', data.rows, 'info', function() {
                        window.location.reload();
                    });
					 
					return;
				}
				$.messager.alert('错误', data.rows);
				return;
			}
    	});
    };
</script>
<script type="text/javascript" src="js/validate/validate.js"></script>
</body>
</html>