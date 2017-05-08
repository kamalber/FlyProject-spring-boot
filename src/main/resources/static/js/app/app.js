var monApp=angular.module('monApp', ['ngRoute','ngStorage','chart.js','moment-picker',]);
monApp.constant('urls', {
    BASE: 'http://localhost:8080/',
    USER_SERVICE_API : 'http://localhost:8080/api/user/',

    POST_SERVICE_API : 'http://localhost:8080/posts/',
    TYPECATEGORY_SERVICE_API : 'http://localhost:8080/typeCategorys/',
    CATEGORY_SERVICE_API:   'http://localhost:8080/categorys/',
    CATEGORYITEM_SERVICE_API:   'http://localhost:8080/categoryItems/',
    COMMENT_SERVICE_API: 'http://localhost:8080/comments/'
});

monApp.config(['$routeProvider','$httpProvider', function($routeProvider, $httpProvider) {
  $routeProvider
  .when('/login', {
		templateUrl : 'views/user/login.html',
		controller : 'navigation',
		controllerAs: 'controller'
	})
	
     .when('/posts', {
    	 templateUrl: 'views/post/index.html',
    	 controller:'PostController',
    	 controllerAs:'ctrl',
    	})
     .when('/posts/:id', {
    	 templateUrl: 'views/post/show.html',
    	 controller  : 'PostController',
    	 controllerAs:'ctrl',})
    	 

     .when('/user', {
	    	 templateUrl: 'views/user/list.html',
	    	 controller:'UserController',
	    	 controllerAs:'ctrl',
	    	})
	    	
	  	
	  .when('/typeCategorys', {
	    	 templateUrl: 'views/categoryType/index.html',
	    	 controller:'CategoryTypeController',
	    	 controllerAs:'ctrl',
	    	})  
	  .when('/categorys', {
	    	 templateUrl: 'views/category/index.html',
	    	 controller:'CategoryController',
	    	 controllerAs:'ctrl',
	    	})  
	  .when('/categoryItem', {
	    	 templateUrl: 'views/categoryItem/index.html',
	    	 controller:'CategoryItemController',
	    	 controllerAs:'ctrl',
	        })   	
	 .when('/stat', {
	    	 templateUrl: 'views/statistics/stat.html',
	    	 controller:'StatsController',
	    	 controllerAs:'ctrl',
	    	})   		
     .otherwise({redirectTo: '/'});
  $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
}]);

//var angular = require('angular');
//var LoginController = require("./LoginController");
//
//var login = angular.module('login', ['ngRoute']);
//login.controller("LoginController", LoginController);
//login.service("login", require("./LoginApi"));
//
//module.exports = login;
//login.config(['$routeProvider', function($routeProvider) {
//	  $routeProvider
//	  
//	     .when('/login', {
//	    	 templateUrl: '/views/user/login.html',
//	    	 controller:'LoginController',
//	    	 controllerAs:'ctrl',
//	    	})
//}]);
