var loginModule = angular.module("LoginModule",[]);
loginModule.controller("LoginCtrl",function($scope, $http,$state,$modal,$log,$rootScope,$stateParams,$location,$timeout,dataFactory) { 
     var userName = $scope.userName;
     var passWord = $scope.passWord;
     var watchUserName = $scope.$watch('userName',function(newValue,oldValue, $scope){
        
        if(newValue) {
        	userName = newValue;
        }
    });
     
     var watchPassword = $scope.$watch('passWord',function(newVal, oldVal, $scope) {
    	 if(newVal) {
    		 passWord = newVal;
    	 }
     })

   $timeout(function() {
	   //密码默认为空
        $scope.userName = null;
        $scope.passWord = null;
   },1000);
     
    $scope.login = function() {
    	if(!passWord){
    		return;
    	} 
    	
    	if(!userName) {
    		return;
    	}
    	
        var param = {
        	passWord:passWord, 
    		userName:userName
    		}
    	dataFactory.getData('login/doLogin', 'POST', param).then(function(val) {
    		console.log(val.data.code)
    		switch(val.data.code) {
    		case 'emptyUserName':
    			$scope.loginForm.userName.$error.emptyUserName = !0;
	    		break;
    		case 'emptyPwd':
    			$scope.loginForm.passWord.$error.emptyPwd = !0;
	    		  break;
    		case 'userNotExist':
    			$scope.loginForm.userName.$error.userNotExist = !0;
	    		  break;
    		case 'pwdErr':
    			$scope.loginForm.passWord.$error.pwdErr = !0;
    			 break;
    		case '200':
    			$(".welcomePage").addClass('hide-force');
        		$location.path("/0");
        		break;
    		default:
    			$scope.loginForm.userName.$error.serverErr = !0;
    		     break;
    		}
    		
    		/*$(".welcomePage").addClass('hide-force');
    		$location.path("/0")*/
			 return;
    	});
    }
//获取模态框的值
var changeInfoData = {};
var data = $rootScope.changePassData = {};
	data.phone = '';
	data.idNo = '';
	data.passWord = '';
	data.confirmPassWord = '';
 

    // actions是行为的意思
    var actions = $rootScope.actions = {};
//    actions.changeOk = function () {
//    	var changeInfo = {};
//        changeInfo.idNo = data.idNo;
//        changeInfo.passWord = data.passWord;
//        changeInfo.confirmPassWord = data.confirmPassWord;
//        changeInfo.phone = data.phone;
//        changeInfoData = changeInfo;
//        console.log(changeInfo)
//    }
    
//弹框封装
$scope.forgetPass = function() {
	        var modalInstance = $modal.open({
	            templateUrl : 'changePass.html',//script标签中定义的id
	            controller : changePassInstanceCtrl,//modal对应的Controller
	            resolve : {
	            	changeInfoData : function() { 
	                		 return changeInfoData;
	                	 }
	                }
	            });
	        
	        modalInstance.opened.then(function() {// 模态窗口打开之后执行的函数  
	            console.log('modal is opened');  
	        });  
	        modalInstance.result.then(function(result) {
	        	alert(333);
	            console.log(result);  
	        }, function(reason) {  
	            console.log(reason);// 点击空白区域，总会输出backdrop  
	            // click，点击取消，则会暑促cancel  
	            $log.info('Modal dismissed at: ' + new Date());  
	        });
	    }

       var changePassInstanceCtrl = function($scope,$modalInstance,changeInfoData) { 
    	   $scope.actions.changeOk = function() { 
		        alert('修改成功');
		        $modalInstance.close();  
		    };  
		    $scope.cancel = function() {  
		        $modalInstance.dismiss('cancel');  
		    };  
      };  
      
      $scope.regist = function() {
    	  var modalInstance = $modal.open({
	            templateUrl : 'regist.html',//script标签中定义的id
	            controller : registInstanceCtrl,//modal对应的Controller
	            resolve : {
	                data : function() {//data作为modal的controller传入的参数
	                     return data;//用于传递数据
	                }
	            }
	        })
	        
	        modalInstance.opened.then(function() {// 模态窗口打开之后执行的函数  
	            console.log('modal is opened');  
	        });  
	        modalInstance.result.then(function(result) {  
	            console.log(result);  
	        }, function(reason) {  
	            console.log(reason);// 点击空白区域，总会输出backdrop  
	            // click，点击取消，则会暑促cancel  
	            $log.info('Modal dismissed at: ' + new Date());  
	        });
      }
      
      var registInstanceCtrl = function($scope, $modalInstance) {  
		    $scope.ok = function() { 
		    	alert('regist');
		        $modalInstance.close();  
		    };  
		    $scope.cancel = function() {  
		        $modalInstance.dismiss('cancel');  
		    };  
    };  
})

//注册
var regModule = angular.module("RegModule",[]);
regModule.controller("RegCtrl",function($scope, $http,$state, $rootScope,$stateParams,$location,$timeout,dataFactory) {
	$scope.regModuleName = '欢迎注册';
	var regForm = {};
	var mobile = $scope.mobile
	var regFormData = $rootScope.regForm = {};
	var registerBeanData = $rootScope.registerBean = {};
	var validCode = registerBeanData.validationCode;
	//短信验证码
	registerBeanData.code = '';
	//短信等待时间
	registerBeanData.remindTime = '';
	
	regFormData.regCaptcha = '';
	//电话
	$scope.mobile = '';
	//监听验证码
    var watchValidationCode = $scope.$watch('registerBean.validationCode',function(newValue,oldValue, $scope){
        if(newValue) {
        	var param = {
        		code:newValue
        	};
        	console.log(param)
        	 dataFactory.getData('../validateCode', 'POST', param).then(function(val) {
        			switch(val.data.code) {
        			case 'validaError':
        				$scope.regForm.regCaptcha.$error.validaError = true;
        	    		break;
        			case 'validate200':
        				$scope.regForm.regCaptcha.$error.validaError = false;
        	    		break;
        			}
        			 return;
        		});
        }
    });
    
	$scope.clearServerError = function() {
		
	}
	$scope.sendType = 'default';
	$scope.isMobileReg = true;
	$scope.RefreshCaptcha = function() {
		$('#reg-refresh-captcha').attr('src',"../imageCode?sid=" + new Date().getTime());
	}
	$scope.SendSMSCode = function() {
		$('#reg-refresh-captcha').attr('src',"../imageCode?sid=" + new Date().getTime());
		if($scope.regForm.regCaptcha.$error.validaError||regForm.regCaptcha.$error.minlength||regForm.regCaptcha.$error.maxlength||!regForm.regCaptcha.$focused) {
			$scope.regForm.regCaptcha.$error.validaError = true;
		}
	}
	//提交数据组装
	$scope.SubmitRegister = function() {
		$location.path("/validate/:account");
		$scope.isMobileReg = false;
	}

	
	
	    //actions是行为的意思
	    var actions = $rootScope.actions = {};
//	    actions.changeOk = function () {
//	      var changeInfo = {};
//	        changeInfo.idNo = data.idNo;
//	        changeInfo.passWord = data.passWord;
//	        changeInfo.confirmPassWord = data.confirmPassWord;
//	        changeInfo.phone = data.phone;
//	        changeInfoData = changeInfo;
//	        console.log(changeInfo)
//	    }

	
});

//找回密码
regModule.controller("FindCtrl",function($scope, $http,$state, $rootScope,$stateParams,$location,$timeout,dataFactory) {
	$scope.regModuleName = '找回密码';
});







//树结构
var treeModule = angular.module("TreeModule",[]);
treeModule.controller("TreeCtrl",function($scope) { 
	$scope.treeOptions = {
	        accept: function(sourceNodeScope, destNodesScope, destIndex) {
	            return true;
	        }
	    }
	
$scope.data = [
	           {
	                 "id": 1,
	                 "title": "node1",
	                 "nodes": [
	                   {
	                     "id": 11,
	                     "title": "node1.1",
	                     "nodes": [
	                       {
	                         "id": 111,
	                         "title": "node1.1.1",
	                         "nodes": [{"id": 111,
	       	                            "title": "node1.1.1"}]
	                       }
	                     ]
	                   },
	                   {
	                     "id": 12,
	                     "title": "node1.2",
	                     "nodes": []
	                   }
	                 ]
	               },
	               {
	                 "id": 2,
	                 "title": "node2",
	                 "nodrop": true,
	                 "nodes": [
	                   {
	                     "id": 21,
	                     "title": "node2.1",
	                     "nodes": []
	                   },
	                   {
	                     "id": 22,
	                     "title": "node2.2",
	                     "nodes": []
	                   }
	                 ]
	               },
	               {
	                 "id": 3,
	                 "title": "node3",
	                 "nodes": [
	                   {
	                     "id": 31,
	                     "title": "node3.1",
	                     "nodes": []
	                   }
	                 ]
	               }
	             ];
	
	$scope.init=function(){
        $scope.getTreeList();
    }
 
    $scope.getTreeList=function(){ 
        OrganizationService.getOrgTreeList().then(function(result) {
            $scope.angularTreeList=[];
            //查询树
            $scope.orgTypeList=result;  
            //创建根节点
            var root={};
            root.name="法制机构";
            root.level=0;
            root.sequence=1;
            root.orgTypeId=-1;
            root.children=$scope.orgTypeList;
            $scope.angularTreeList.push(root);
            $scope.treeOptions.data=$scope.angularTreeList;
        });
    }
     
    //新建节点
    $scope.newSubItem = function (item) {
        var name=window.prompt("请输入节点的名称");
        if(name==""||name==undefined) {
            return;
        }
        if(item.orgTypeId==undefined) {
            item.orgTypeId=-1;
        }
        var json={"level":parseInt(item.level)+1,"name":name,"parentTypeId":item.orgTypeId,"children":[]};
        if(item.children==null||item.children.length==0){
            item.children=[];
            json.sequence=1;
        }else{
            var maxSequence=parseInt(item.children[item.children.length-1].sequence);
            json.sequence=maxSequence+1;
        }
        OrganizationService.saveOrgType(json).then(function(result) {
//            item.children.push(json);
            $scope.init();
        });           
    };
     
    //编辑节点
    $scope.editItem=function(item){
//        var target=$("#div_"+item.orgTypeId);
        if(item.isEdit==undefined||item.isEdit==false){
            item.isEdit=true;  
//            $(target).removeAttr("ui-tree-handle");     
        }else if(item.isEdit==true){
            item.isEdit=false;  
//            $(target).attr("ui-tree-handle",'');
            var json={"orgTypeId":item.orgTypeId,"name":item.name}
            OrganizationService.updateOrgType(json).then(function(result) {
//              $scope.init();
            });
        }
    }
     
    //删除节点
    $scope.removeItem=function(item){
      var json={};
      json.orgTypeId=item.orgTypeId;
      json.status=0;
      OrganizationService.updateOrgType(json).then(function(result) {
//        $scope.init();
      });
    }
     
    $scope.treeOptions = {
        //拖拽操作 拖拽后会返回callback beforeDrop函数，我们可以重写这个函数，拖拽本质上是顺序的改变和层级的改变，所以是一个update操作
        beforeDrop : function (e) {
           var source = e.source.nodeScope.item;
           var destRoot = e.dest.nodesScope.item ;
           if(destRoot==undefined){
               return $q.reject();
           }
           var destIndex=e.dest.index;
           var dest=e.dest.nodesScope.item.children[destIndex];
           if(dest==undefined){
               return $q.reject();
           }
           if (source.parentTypeId!=dest.parentTypeId) {       
               return $q.reject();
           }
           var sourceSeq=source.sequence;
           var destSeq=dest.sequence;
           source.sequence=destSeq;
           dest.sequence=sourceSeq;
           OrganizationService.updateOrgType(source).then(function(result) {

           });
           OrganizationService.updateOrgType(dest).then(function(result) {
     
           });
           return;
        }
   };
 });



/**
 * 这里是书籍列表模块
 * @type {[type]}
 */
var bookListModule = angular.module("BookListModule", []);
bookListModule.controller('BookListCtrl', function($scope, $http, $state, $stateParams) {
    $scope.filterOptions = {
        filterText: "",
        useExternalFilter: true
    };

    $scope.totalServerItems = 0;
    $scope.pagingOptions = {
        pageSizes: [5, 10, 20],
        pageSize: 5,
        currentPage: 1
    };
    $scope.setPagingData = function(data, page, pageSize) {
        var pagedData = data.slice((page - 1) * pageSize, page * pageSize);
        $scope.books = pagedData;
        $scope.totalServerItems = data.length;
        if (!$scope.$$phase) {
            $scope.$apply();
        }
    };

    //这里可以根据路由上传递过来的bookType参数加载不同的数据
    console.log($stateParams);
    $scope.getPagedDataAsync = function(pageSize, page, searchText) {
        setTimeout(function() {
            var data;
            if (searchText) {
                var ft = searchText.toLowerCase();
                $http.get('main/data/books' + $stateParams.bookType + '.json')
                    .success(function(largeLoad) {
                        data = largeLoad.filter(function(item) {
                            return JSON.stringify(item).toLowerCase().indexOf(ft) != -1;
                        });
                        $scope.setPagingData(data, page, pageSize);
                    });
            } else {
                $http.get('main/data/books' + $stateParams.bookType + '.json')
                    .success(function(largeLoad) {
                        $scope.setPagingData(largeLoad, page, pageSize);
                    });
            }
        }, 100);
    };

    $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);

    $scope.$watch('pagingOptions', function(newVal, oldVal) {
        if (newVal !== oldVal && newVal.currentPage !== oldVal.currentPage) {
            $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
        }
    }, true);
    $scope.$watch('filterOptions', function(newVal, oldVal) {
        if (newVal !== oldVal) {
            $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
        }
    }, true);

    $scope.gridOptions = {
        data: 'books',
        rowTemplate: '<div style="height: 100%"><div ng-style="{ \'cursor\': row.cursor }" ng-repeat="col in renderedColumns" ng-class="col.colIndex()" class="ngCell ">' +
            '<div class="ngVerticalBar" ng-style="{height: rowHeight}" ng-class="{ ngVerticalBarVisible: !$last }"> </div>' +
            '<div ng-cell></div>' +
            '</div></div>',
        multiSelect: false,
        enableCellSelection: true,
        enableRowSelection: false,
        enableCellEdit: true,
        enablePinning: true,
        columnDefs: [{
            field: 'index',
            displayName: '序号',
            width: 60
 /*           pinnable: false,*/
/*            sortable: false*/
        }, {
            field: 'name',
            displayName: '书名',
            width:100
      /*      enableCellEdit: true*/
        }, {
            field: 'author',
            displayName: '作者',
    /*        enableCellEdit: true,*/
            width: 60
        }, {
            field: 'pubTime',
            displayName: '出版日期',
/*            enableCellEdit: true,*/
            width: 120
        }, {
            field: 'price',
            displayName: '定价',
            enableCellEdit: true,
            width: 120,
            cellFilter: 'currency:"￥"'
        }, {
            field: 'bookId',
            displayName: '操作',
 /*           enableCellEdit: false,*/
            sortable: false,
       /*     pinnable: false,*/
            cellTemplate: '<div><a ui-sref="bookdetail({bookId:row.getProperty(col.field)})" id="{{row.getProperty(col.field)}}">详情</a></div>'
        }],
        enablePaging: true,
        showFooter: true,
        totalServerItems: 'totalServerItems',
        pagingOptions: $scope.pagingOptions,
        filterOptions: $scope.filterOptions
    };
});


/**
 * 这里是书籍详情模块
 * @type {[type]}
 */
var bookDetailModule = angular.module("BookDetailModule", []);
bookDetailModule.controller('BookDetailCtrl', function($scope, $http, $state, $stateParams) {
    console.log($stateParams);
    //请模仿上面的代码，用$http到后台获取数据，把这里的例子实现完整
    
});






