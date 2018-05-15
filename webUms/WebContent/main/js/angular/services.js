var factoryService = angular.module("FactoryModule",[]);
factoryService.factory('dataFactory',function($http, $q) {
	    var factory = {};  
	    factory.getData = function(url, method, params) {  
	     var defer = $q.defer();
	    $http({
                url:url,  
                method:method,
                data:params, 
	    }).then(function successCallback(response) { 
	             defer.resolve(response);
	            },
	            function errorCallback(response) {
	                defer.reject(response);
	            });  
	           return defer.promise;
	          };  
	     return factory;
});



