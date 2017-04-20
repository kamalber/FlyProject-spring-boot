var monApp=angular.module('monApp', ['ngRoute','ngStorage','chart.js','moment-picker']);
monApp.constant('urls', {
    BASE: 'http://localhost:8080/',
    USER_SERVICE_API : 'http://localhost:8080/api/user/',
    POST_SERVICE_API : 'http://localhost:8080/posts',
    TYPECATEGORY_SERVICE_API : 'http://localhost:8080/typeCategorys/',
});

monApp.config(['$routeProvider', function($routeProvider) {
  $routeProvider
     .when('/posts', {templateUrl: 'views/post/index.html',
    	 controller  : 'Controller/index'})
     .when('/user', {
	    	 templateUrl: 'views/user/list.html',
	    	 controller:'UserController',
	    	 controllerAs:'ctrl',
	    	})
	  .when('/typeCategorys', {
	    	 templateUrl: 'views/category/index.html',
	    	 controller:'CategorysController',
	    	 controllerAs:'ctrl',
	    	})  	
	 .when('/stat', {
	    	 templateUrl: 'views/statistics/stat.html',
	    	 controller:'StatsController'
	    	})   		
     .otherwise({redirectTo: '/'});
}]);

