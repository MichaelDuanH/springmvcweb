var routerApp = angular.module('routerApp', ['ui.router','ui.bootstrap','ui.tree','ngGrid', 'BookListModule', 'BookDetailModule','LoginModule','RegModule','TreeModule','FactoryModule','DialogModule']);
/**
 * 由于整个应用都会和路由打交道，所以这里把$state和$stateParams这两个对象放到$rootScope上，方便其它地方引用和注入。
 * 这里的run方法只会在angular启动的时候运行一次。
 * @param  {[type]} $rootScope
 * @param  {[type]} $state
 * @param  {[type]} $stateParams
 * @return {[type]}
 */
routerApp.run(function($rootScope, $state, $stateParams) {
    $rootScope.$state = $state;
    $rootScope.$stateParams = $stateParams;
    //$rootScope.$on('$stateChangeStart',function(event, toState, toParams, fromState, fromParams) {
    // 如果用户不存在
    //alert(toState);
    // if(!$rootScope.userInfo){
        //event.preventDefault();// 取消默认跳转行为
        //$state.go("index",{from:fromState.name,w:'notLogin'});//跳转到登录界面
    // }
 // });
});






/**
 * 配置路由。
 * 注意这里采用的是ui-router这个路由，而不是ng原生的路由。
 * ng原生的路由不能支持嵌套视图，所以这里必须使用ui-router。
 * @param  {[type]} $stateProvider
 * @param  {[type]} $urlRouterProvider
 * @return {[type]}
 */
routerApp.config(function($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('/login');
	$stateProvider
        .state('index', {
            url: '/login',
            views: {
                '': {
                    templateUrl: 'tpls/home.html'
                },
                'main@index': {
                    templateUrl: 'tpls/loginForm.html'
                }
            }
        }).
        state('reg',{
        	url:'/reg',
        	controller: "RegCtrl",
			templateUrl: "RegView.html"
        }).state("validate-user", {
			url: "/validate/:account",
			controller: "BindCtrl",
			templateUrl: "BindView.html"
		}).state("validate-mail", {
			url: "/bind/:account",
			controller: "BindMailCtrl",
			templateUrl: "BindMailView.html"
		}).state("set-pwd", {
			url: "/active/:account",
			controller: "SetCtrl",
			templateUrl: "SetView.html"
		}).state("find-pwd", {
			url: "/find/:account",
			controller: "FindCtrl",
			templateUrl: "FindView.html"
		}).state("reset", {
			url: "/reset/:account",
			controller: "ResetCtrl",
			templateUrl: "ResetView.html"
		}).state("user-set-pwd", {
			url: "/reset/:account",
			controller: "ResetCtrl",
			templateUrl: "ResetView.html"
		}).state("admin-set-pwd", {
			url: "/reset/:account",
			controller: "ResetCtrl",
			templateUrl: "ResetView.html"
		})
        .state('info',{
        	url:'/introduction',
        	views:{
        		'main@info': {
                    templateUrl: 'tpls/introduction.html'
                }
        	}
        })
        .state('booklist', {
            url: '/{bookType:[0-9]{1,4}}',
            views: { //注意这里的写法，当一个页面上带有多个ui-view的时候如何进行命名和视图模板的加载动作
                '': {
                    templateUrl: 'tpls/bookList.html'
                },
                'booktype@booklist': {
                    templateUrl: 'tpls/bookType.html'
                },
                'bookgrid@booklist': {
                    templateUrl: 'tpls/bookGrid.html'
                }
            }
        })
        .state('addbook', {
            url: '/addbook',
            templateUrl: 'tpls/addBookForm.html'
        })
        .state('bookdetail', {
            url: '/bookdetail/:bookId', //注意这里在路由中传参数的方式
            templateUrl: 'tpls/bookDetail.html'
        })
});



