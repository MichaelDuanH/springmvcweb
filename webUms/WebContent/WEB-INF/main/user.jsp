<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>    

<html>
  <head>
     <meta http-equiv="Content-Type" content="text/html; charset=utf-8"> 
     <jsp:include page="base/jsp/base.jsp"/>
    <title>用户管理系统</title>
    <link href="main/i/logo.ico" type="image/x-icon" rel="icon"/>
    <style type="text/css">
       .div-inline{
         display:inline;
       }
       .easyui-layout {
         width:1500px;
         height:auto;
       }
       
       .easyui-panel {
         width:80%;
         padding:10px;
       }
       
       .div-inline input {
         width: 190px;
         height:25px; 
         text-indent: 10px;
       }
       
       #userDataId {
         width:80%;
         height:760px
       }
       
       #entityDialog {
         text-align: center;
         width: 580px; 
         height:auto
       }
       
    </style>
  </head>
  <body class="easyui-layout">
		<div data-options="fit:true,border:false,plain:true">  
	        <div id="conditionDiv" class="easyui-panel" data-options="region:'north',collapsible:false, minHeight:48">
			<div id="searchCondition">
				<form id="queryForm">
					<div class="div-inline">
						<span>工号  </span><input class="easyui-validatebox textbox" name="userCode" id="userCode"  placeholder="按照工号查询">
						&nbsp;&nbsp;<span>姓名  </span><input class="easyui-validatebox textbox" name="userName" id="userName" placeholder="按照姓名查询">
					</div>
					<div  id="query-toolbar" class="div-inline">
						<a href="javascript:void(0)" class="easyui-linkbutton" 
							iconCls="icon-search" plain="true" onClick="onSearch()"> <span>查询</span>
						</a> 
				   </div>
				</form>
			</div>
	      </div>
	      <div title="DataGrid">
	         <table id="userDataId" class="easyui-datagrid" data-options="region:'center'"></table>
	      </div> 
        </div> 
  <div id="datagrid-toolbar">
			<a onClick="onAdd()"   data-options="iconCls:'icon-add',plain:true," href="javascript:void(0)" class="easyui-linkbutton"> 
				<span>新增</span>
			</a> 
			<a onClick="onEdit()"  data-options="iconCls:'icon-edit',plain:true," href="javascript:void(0)" class="easyui-linkbutton"> 
				<span>修改</span>
			</a> 
			<a onClick="onDel()"  data-options="iconCls:'icon-delete',plain:true," href="javascript:void(0)" class="easyui-linkbutton"> 
				<span>删除</span>
			</a> 
			<a onClick="onUploadExcel()"  data-options="iconCls:'icon-export1',plain:true," href="javascript:void(0)" class="easyui-linkbutton"> 
				<span>导入</span>
			</a>
			<a onClick="onDownExcel()"  data-options="iconCls:'icon-export1',plain:true," href="javascript:void(0)" class="easyui-linkbutton"> 
				<span>导出</span>
			</a>
			<a onClick="onBatchDel()"  data-options="iconCls:'icon-export1',plain:true," href="javascript:void(0)" class="easyui-linkbutton"> 
				<span>清空数据</span>
			</a>
			<a onClick="onProduceOrgData()"  data-options="iconCls:'icon-export1',plain:true," href="javascript:void(0)" class="easyui-linkbutton"> 
				<span>生成处理数据</span>
			</a>  
			<a onClick="reCalc()"  data-options="iconCls:'icon-export1',plain:true," href="javascript:void(0)" class="easyui-linkbutton"> 
				<span>重新计算</span>
			</a> 
			
  </div> 
  <!-- 弹出框工具栏 -->
  <div id="dialog-toolbar">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					onClick="onSave()" data-options="iconCls:'icon-save',plain:true">
					<span>保存</span>
				</a>
				<a href="javascript:void(0)" class="easyui-linkbutton"
					onClick="onReset()" data-options="iconCls:'icon-undo',plain:true">
					<span>重置</span>
				</a>
  </div>
  <!--Excel导入-->
  <div id="excel-dialog-toolbar">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					onClick="onSaveExcel()" data-options="iconCls:'icon-save',plain:true">
					<span>保存</span>
				</a>
				<a href="javascript:void(0)" class="easyui-linkbutton"
					onClick="onResetExcel()" data-options="iconCls:'icon-undo',plain:true">
					<span>重置</span>
				</a>
  </div>

  <div id="entityDialog" 
			    modal="true"
				maximizable="false"
				data-options="toolbar: '#dialog-toolbar',closed:true">
				<form id="entityForm">
					<table
						style="width: 98%; margin-top: 10px; margin-left: 10px; margin-bottom: 20px;">
						<tr>
							<td class="fieldlabel" style="width: 20px">
							<span>网点</span> 
							</td>
							<td align="left" style="width: 50px">
							<input type="text" name="deptCode" id="deptCode" class="easyui-validatebox textbox"
								style="width: 130px" required>
							</td>
							<td class="fieldlabel" style="width: 60px">
							  <span>工号</span> 
							</td>
							<td align="left" style="width: 50px">
							<input type="text" name="userCode" id="userCodeId" class="easyui-validatebox textbox"
								style="width: 130px" required>
							</td>
						</tr>
						<tr>
							<td class="fieldlabel" style="width: 60px">
							  <span>转计提</span> 
							</td>
							<td align="left" style="width: 50px">
								<select id="isCommis" name="isCommis" class="easyui-combobox">
								   <option value = "">--请选择--</option>
								   <option value="1">是</option>
								   <option value="0">否</option>
								</select>
							</td>
							<td class="fieldlabel" style="width: 60px">
							  <span>日期</span> 
							</td>
							<td align="left" style="width: 40px">
							     <input id="commisDate" name="commisDateStr" type="text" class="easyui-datebox" required="required">
							</td>
						</tr>
			   </table>
	   </form>
</div>	

  <div id="entityExcel" style="text-align: left; width: 580px; height: auto" modal="true"
	 maximizable="false" data-options="toolbar:'#excel-dialog-toolbar',closed:true">
		 <form id="importExcelId"  method="post" enctype="multipart/form-data">  
              <input id="uploadExcel" name="uploadExcelName" class="easyui-filebox" style="width:400px" data-options="prompt:'请选择文件...'"> <br/>
              <span id="downTempId">下载模板 :<a href="">用户信息导入模板</a></span>
         </form>   
  </div>
  
    <div id="errorEntityExcel" style="text-align: center; width: 880px; height: auto" modal="true"
	 maximizable="false" data-options="closed:true">
		 <div title="ErrorDataGrid">
	         <table id="errorDataId" class="easyui-datagrid" style="width:99%;height:300px" data-options="region:'center'"></table>
	      </div>   
  </div>
<script type="text/javascript">
   window.contextPath = "${contextPath}";
   var hostArr = window.location.host;

   function logout() {
	   window.location.href='doLogout'
   }
   
   $("#entityDialog").css('display','none');
   
   $("#entityExcel").css('display','none');
   
   $("#errorEntityExcel").css('display','none');
   
   $("#excel-dialog-toolbar").css('display','none');
   
   $("#dialog-toolbar").css('display','none');
   
   $("#datagrid-toolbar").prop("hidden",true); 
/*   $("#entityDialog").dialog('open') */

  $('#userDataId').datagrid({
      toolbar:'#datagrid-toolbar',
      url : "user/queryUserPage",
      pagination : true,
      pageSize : 10,
      pagePosition : 'top',
      rownumbers : true,
      frozenColumns : [[{
          field : 'userId',
          checkbox : true
      }]],
      fitColumns:true,
      singleSelect:false, 
      columns : [[
		{
           field : 'deptCode',
           width : 6,
           title : '网点代码'
       }, {
           field : 'userCode',
           width : 6,
           title : '工号'
       },{
           field : 'userName',
           width : 6,
           title : '姓名'
       },{
    	  field :'isCommis',
    	  width : 5,
    	  title: '是否转计提',
    	  formatter:function(data) {
    		  if(data == '0') {
    			  data = '否'; 
    		  } else if (data == '1') {
    			  data = '是';
    		  } 
    		  return data;
    	  }
       },{
           field : 'commisDateStr',
           width : 6,
           title : '转计提日期'
       },{
    	  field :'personTypeCode',
    	  width : 6,
    	  title: '人员类型',
    	  formatter:function(data) {
    		  if(data == 'A') {
    			  data = '类型1'; 
    		  } else if (data == 'W') {
    			  data = '类型2';
    		  } 
    		  return data;
    	  }
       },{
    	   field:'remark',
    	   width: 10,
    	   title:'备注'
       },{
    	  field :'sync',
    	  width : 7,
    	  title: '是否同步',
    	  formatter:function(data) {
    		  if(data == '0') {
    			  data = '未同步'; 
    		  } else if (data == '1') {
    			  data = '已同步';
    		  } else {
    			  data = '未同步'; 
    		  }
    		  return data;
    	  }
       },]],
      onBeforeLoad:function(param) {
          if (!$("#queryForm").form('validate')) {
              return false;
          }
          
          $.extend(param, $("#queryForm").serializeJSON());
             return true;
      },
      onLoadSuccess: function (data) {
          if (data.total == 0) {
        		$.messager.alert('提示', '没有查询到数据','info');
        		return;
          }
      },
      onLoadError : function(xhr,status,error) {
			$.messager.progress('close');
			$.messager.alert('错误',error|| '服务器已断开连接，请检查','warning');}
   });
   

  
  
  var ip;
  $.ajax({
		type : "get",
		url : '../getIpAdr',
		success : function(data) {
			console.log(data);
			ip = data.trim();
			$("#downTempId a").attr("href","http://"+ (hostArr.split(':')[0] == 'localhost'?'localhost':eval(data)) + ":8080/TestProject/main/user/downLoadExcelTemplate");
		}
  });
  
	window.onAdd = function() {
		 window.isEditing = false;
	        $("#entityDialog").dialog({
	            title : '新增'
	        }).dialog('open');
	        onReset();
	        editRow = undefined;
	}
	
	window.onReset = function() {
		$('#entityForm').form('reset');
		if (window.isEditing && window.editRow != undefined
				&& window.editRow != null) {
			var formData = $.extend({}, window.editRow);
			console.log(formData);
			$('#entityForm').form('load', formData);
		}
	}
	
	window.onEdit = function() {
		var selectedRow = getSelectedRow('#userDataId');
		if (selectedRow) {
			window.isEditing = true;
			window.editRow = selectedRow;
			$("#entityDialog").dialog({
				title : '修改'
			}).dialog('open');
			onReset();
		}
	}
	
	//保存按钮触发
    window.onSave = function() {
    	var obj = {};
    	var url = null;
        if ($("#entityForm").form("validate")) {
        	 var user = $("#entityForm").serializeJSON();
        }
        if (window.isEditing) {
			obj.userId = window.editRow.userId;
			url = "user/updateUser";
		} else {
			obj.userId = null;
			url = "user/saveUser";
		}
		doSave(obj, url);
		return false;
    }
	
    function queryFormParam(fileId) {
    	return $(fileId).serializeJSON();
    }
	
	window.onSaveExcel = function() {
		 var fileName= $('#uploadExcel').filebox('getValue');  
		 if(!fileName) {
			 $.messager.alert('提示','请选择上传文件！','info');  
			 return;
		 }
		 var filePrefix = /\.[^\.]+$/.exec(fileName); 
		 if(filePrefix != '.xls') {
			 $.messager.alert('提示','请选择xls格式文件！','info');   
             $('#uploadExcel').filebox('setValue','');  
             return;
		 }
	
		  $.messager.progress({
   			title : "提示",
   			msg : "请稍等..."
   		   });
         
         $("#importExcelId").form('submit',{
        	  url : 'user/importExcel',
              onSubmit:function(param) {
            	  param = queryFormParam('#importExcelId');
              },
        	  success : function(data) { //上传成功后的回调。
        		$.messager.progress('close');
        		 $("#entityExcel").dialog({
     	            title : '导入'
     	        }).dialog('close');
        		 $("#excel-dialog-toolbar").css('display','none');
        		 $("#entityExcel").css('display','none');
        		//var obj = {'data':data};
        		if(data) {
        			   data = JSON.parse(data);
        			   delete data.code;
        			   console.log(data);
        			   if(typeof(data.rows) == 'string') {
        				   $.messager.alert("提示",data.rows); 
        				   onSearch();
        			   } else {
        				   $.messager.alert('提示', '你有'+data.rows.length+"条数据出现错误，请检查后重新导入", 'info', function() {
        					   $("#errorEntityExcel").dialog({
                  		            title : '错误数据'
                  		          }).dialog('open');
               				   
               				   for( var i = 0; i<data.rows.length;i++) {
               					   $('#errorDataId').datagrid('loadData', {
                         					total : data.rows.length,
                         					rows : data.rows
                         		  });
               			       }
               				onSearch();
       					});
        			 }
        		}
        	  },
        	  error : function(data) {
        		$.messager.progress('close');
        		 $("#entityExcel").dialog({
     	            title : '导入'
     	        }).dialog('close');
        		 $("#excel-dialog-toolbar").css('display','none');
        		 $("#entityExcel").css('display','none');
        	    $.messager.alert("提示",data.rows);
        	  }
         })
         
	}
	
	   $("#errorDataId").datagrid({
		      pagination : false,
		      pagePosition : 'top',
		      rownumbers : false,
		      frozenColumns : [[{
		          field : 'userId',
		          checkbox : true
		      }]],
		      fitColumns:true,
		      columns : [[
				{
		           field : 'deptCode',
		           width : 10,
		           title : '网点代码'
		       },{
		           field : 'userCode',
		           width : 10,
		           title : '工号'
		       },{
		           field : 'userName',
		           width : 10,
		           title : '姓名'
		       },{
		           field : 'isCommis',
		           width : 10,
		           title : '是否转计提'
		       },{
		           field : 'commisDate',
		           width : 10,
		           title : '转计提日期'
		       },{
		    	   field:'errorMsg',
		    	   width: 50,
		    	   styler:function(){
					   return 'background-color:#fafacf;color:red;';
				},
		    	   title:'错误信息'
		       }
		       ]],
		   });
	
	window.onResetExcel = function() {
		
	}
	
	function formateDate(date) {
		if(date) {
			var y = date.getFullYear();
			var m = date.getMonth() + 1;
			var d = date.getDate();
			return y + "-" +(m<10?"0" + m:m) + "-" + (d<10?"0" + d:d);
		}
	}
	
	// 保存操作
	function doSave(obj,url) {
		obj.deptCode = $("#deptCode").val();
		obj.userCode= $("#userCodeId").val();
		obj.isCommis = $('#isCommis option:selected').val();
		obj.commisDateStr= $("#commisDate").val();
		if(!obj.deptCode
				|| !obj.userCode || !obj.isCommis || !obj.commisDateStr) {
			$.messager.alert('错误', '请检查表单是否填写完整');
			return;
		}
		console.log(obj);
		$.messager.progress({
			title : "提示",
			msg : "请稍等..."
		});
		$.ajax({
			url : url,
			data : obj,
			type : 'post',
			dataType : 'json',
			success : function(resp, status, xhr) {
				$.messager.progress('close');
				if (resp.code=='200') {
					$("#entityDialog").dialog('close');
					$.messager.alert('提示', '保存成功', 'info', function() {
						onSearch();
					});
				} else {
					$.messager.alert('异常', resp.rows);
				}
			},
			error : function(xhr, status, error) {
				$.messager.progress('close');
				$.messager.alert('错误', error || '提醒','warning');
			}
		});
	}
	
	window.onDel = function() {
		var selectedRow = getSelectedRow('#userDataId', true);
		if (selectedRow) {
			$.messager.confirm('提示','确定要删除数据吗?',function(result) {
				if (result) {
						var obj = {};
						obj.id = [];
						for (var i = 0; i < selectedRow.length; i++) {
							obj.id[i]= selectedRow[i].userId;
						}
						$.messager.progress({
							title : '提示',
							msg : '请稍后...'
						});
						$.ajax({
							url : 'user/delUser',
							data : obj,
							type : "post",
							dataType : "json",
							success : function(resp) {
								$.messager.progress('close');
								if (resp.code == '200') {
								   onSearch();
								} else {
									$.messager.alert('错误',resp.rows,'warning');
								}
							},
							error : function(xhr, status,error) {
								$.messager.progress('close');
								$.messager.alert('错误',error|| '服务器已断开连接，请检查','warning');}
							});
						}
                 });
		}
	}
	
	window.onBatchDel = function() {
			$.messager.confirm('提示','确定要清空所有数据吗? 数据清空后不可恢复',function(result) {
				if (result) {
						$.messager.progress({
							title : '提示',
							msg : '请稍后...'
						});
						
						$.ajax({
							url : 'user/batchDelUser',
							data : {'batch':'true'},
							type : "post",
							dataType : "json",
							success : function(resp) {
								$.messager.progress('close');
								if (resp.code == '200') {
								   onSearch();
								} else {
									$.messager.alert('错误',resp.rows,'warning');
								}
							},
							error : function(xhr, status,error) {
								$.messager.progress('close');
								$.messager.alert('错误',error|| '服务器已断开连接，请检查','warning');}
							});
						}
                 });
	}
	
	window.onProduceOrgData = function() {
		$.messager.confirm('提示','确定要生成原始数据吗，每次只能生成100条? ',function(result) {
			if (result) {
					$.messager.progress({
						title : '提示',
						msg : '请稍后...'
					});
					
					$.ajax({
						url : 'user/onProduceOrgData',
						data : {'produce':'true'},
						type : "post",
						dataType : "json",
						success : function(resp) {
							$.messager.progress('close');
							if (resp.code == '200') {
								$.messager.alert('提示', '数据创建成功', 'info', function() {
									onSearch();
								});
							} else {
								$.messager.alert('错误',resp.rows,'warning');
							}
						},
						error : function(xhr, status,error) {
							$.messager.progress('close');
							$.messager.alert('错误',error|| '服务器已断开连接，请检查','warning');}
						});
					}
             });
	}
	
	window.reCalc = function() {
		$.messager.confirm('提示','确定要重算吗？ ',function(result) {
			if (result) {
					$.messager.progress({
						title : '提示',
						msg : '请稍后...'
					});
					$.ajax({
						url : 'user/reCalc',
						data : {'calc':'true'},
						type : "post",
						dataType : "json",
						success : function(resp) {
							$.messager.progress('close');
							if (resp.code == '200') {
								$.messager.alert('提示', '数据开始重算', 'info', function() {
									onSearch();
								});
							} else {
								$.messager.alert('错误',resp.rows,'warning');
							}
						},
						error : function(xhr, status,error) {
							$.messager.progress('close');
							$.messager.alert('错误',error|| '服务器已断开连接，请检查','warning');}
						});
					}
             });
	}
	
	
	
	window.onUploadExcel = function() {
	        $("#entityExcel").dialog({
	            title : '导入'
	        }).dialog('open');
	        onResetExcel();
	}
	
	window.onDownExcel = function() {
		$.messager.progress({
			title : '提示',
			msg : '请稍后...'
		});
		var param = $("#queryForm").serializeJSON();
		var userCode = param.userCode;
		var userName = param.userName;
		window.location = "${pageContext.request.contextPath}/main/user/exportUser?userCode="+ userCode + "&userName="+userName;		
		$.messager.progress('close');
	}
	
	// 获取datagrid一行数据
	function getSelectedRow(selector, multiple) {
		var selectedRows = $(selector).datagrid('getSelections');
		multiple = (typeof (multiple) == "undefined") ? false : multiple;
		if (selectedRows.length == 0) {
			showPromptMsg(multiple ? '请选择至少一条数据' : '请选择一条数据');
			return false;
		} else if (!multiple && selectedRows.length > 1) {
			showPromptMsg('请选择一条数据');
			return false;
		} else {
			return multiple ? selectedRows : selectedRows[0];
		}
	}
	
	function showPromptMsg(msg) {
	        $.messager.alert('提示', msg, 'warning');
	}
	
	function onSearch() {
	    $('#userDataId').datagrid('clearSelections').datagrid('load', {})
	}
   </script>
  </body>
</html>