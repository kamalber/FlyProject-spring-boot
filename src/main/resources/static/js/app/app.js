var monApp=angular.module('monApp', ['ngRoute','ngStorage','chart.js','moment-picker','xs.ui.wizard'])
.value('XS_WIZARD_TEMPLATE_DIR', '/views'); 
monApp.constant('urls', {
    BASE: 'http://localhost:8080/',
    USER_SERVICE_API : 'http://localhost:8080/api/user/',

    POST_SERVICE_API : 'http://localhost:8080/posts/',
    TYPECATEGORY_SERVICE_API : 'http://localhost:8080/typeCategorys/',
    CATEGORY_SERVICE_API:   'http://localhost:8080/categorys/',
    CATEGORYITEM_SERVICE_API:   'http://localhost:8080/categoryItems/',
    COMMENT_SERVICE_API: 'http://localhost:8080/comments/',
    ACOUNT_SERVICE_API: 'http://localhost:8080/acount/'
});



monApp.config(['$routeProvider','$httpProvider', function($routeProvider, $httpProvider) {
  $routeProvider
  .when('/login', {
		templateUrl : 'views/user/login.html',
		controller : 'AcountController',
		controllerAs: 'ctrl'
	})
	.when('/register', {
		templateUrl : 'views/user/register.html',
		controller : 'AcountController',
		controllerAs: 'ctrl'
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


monApp.run( function($rootScope,AuthSession, $location) {
    // register listener to watch route changes
    $rootScope.$on( "$routeChangeStart", function(event, next, current) {
//      if (AuthSession.connected == null) {
//    	  if(current.templateUrl !="/login" &&  current.templateUrl !="/register"){
//    		  $location.path( "/login" );
//    	  }
//        
//      }         

    });
 })