monApp.controller('StatsController',
    ['$scope',  function($scope) {

    	  $scope.labels = ['2006', '2007', '2008', '2009', '2010', '2011', '2012'];
    	  $scope.series = ['Series A', 'Series B','Series d'];
    	  $scope.data = [
    	    [65, 59, 80, 81, 56, 55, 40],
    	    [28, 48, 40, 19, 86, 27, 90],
    	    [22, 38, 30, 39, 86, 27, 90]
    	  ];
    	  
    	  $scope.labelsPie = ["Download Sales", "In-Store Sales", "Mail-Order Sales"];
    	  $scope.dataPie = [300, 500, 100];
    }]);
$('#myTab a').click(function (e) {
	 e.preventDefault();
	 $(this).tab('show');
});

$(function () {
$('#myTab a:last').tab('show');
})