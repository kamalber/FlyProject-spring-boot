monApp.controller('CompareController',
    ['CompareService', '$scope','$sessionStorage','$timeout','Upload',  function(CompareService, $scope,$sessionStorage,$timeout,Upload) {
    	
        var self = this;
    
 
        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;
        self.compareTols=compareTols;

     
        $scope.uploadFilesPositive = function(file, errFiles) {
            $scope.fp = file;
            $scope.errFile = errFiles && errFiles[0];
            if (file) {
                file.upload = Upload.upload({
                    url: 'acount/uploadFilePositive',
                    data: {file: file}
                });

                file.upload.then(function (response) {
                    $timeout(function () {
                        file.result = response.data;
                    });
                }, function (response) {
                    if (response.status > 0)
                        $scope.errorMsg = response.status + ': ' + response.data;
                }, function (evt) {
                    file.progress = Math.min(100, parseInt(100.0 * 
                                             evt.loaded / evt.total));
                });
            }   
        }

        $scope.uploadFilesNegative = function(file, errFiles) {
            $scope.fn = file;
            $scope.errFile = errFiles && errFiles[0];
            if (file) {
                file.upload = Upload.upload({
                    url: 'acount/uploadFileNegative',
                    data: {file: file}
                });

                file.upload.then(function (response) {
                    $timeout(function () {
                        file.result = response.data;
                    });
                }, function (response) {
                    if (response.status > 0)
                        $scope.errorMsg = response.status + ': ' + response.data;
                }, function (evt) {
                    file.progress = Math.min(100, parseInt(100.0 * 
                                             evt.loaded / evt.total));
                });
            }   
        }


        $scope.uploadFilesNeutral = function(file, errFiles) {
            $scope.ft = file;
            $scope.errFile = errFiles && errFiles[0];
            if (file) {
                file.upload = Upload.upload({
                    url: 'acount/uploadFileNeutral',
                    data: {file: file}
                });

                file.upload.then(function (response) {
                    $timeout(function () {
                        file.result = response.data;
                    });
                }, function (response) {
                    if (response.status > 0)
                        $scope.errorMsg = response.status + ': ' + response.data;
                }, function (evt) {
                    file.progress = Math.min(100, parseInt(100.0 * 
                                             evt.loaded / evt.total));
                });
            }   
        }
  
        
        function compareTols(){
        	CompareService.getCompareTols()
          	 .then(
          	      function(data){
          	    	setDataToBarChart(data);
          	      setDataToDumaxChart(data.dumax); 
        	      setDataToNltkChart(data.nltk);
          	      setDataToGateChart(data.gate);
          	      setDataToCombainingChart(data.combaining);
               	 },function(erroResponse){
          		 console.error('the llist is empty');
                   self.errorMessage = 'Error while cretriving data : ' + errResponse;
                   self.successMessage='';
          	 });
           }
        function setDataToBarChart(data){
        	var dumaxAvareg=
      	  Highcharts.chart('barChartContainer', {
        	    title: {
        	        text: 'sentiments analysis for the query (java)'
        	    },
        	    xAxis: {
        	        categories: ['nltk','datumbox','gate APi','combaining']
        	    },
        	    labels: {
        	        items: [{
        	            html: 'Total',
        	            style: {
        	                left: '50px',
        	                top: '18px',
        	                color: (Highcharts.theme && Highcharts.theme.textColor) || 'black'
        	            }
        	        }]
        	    },
        	    series: [{
        	        type: 'column',
        	        name: 'positive',
        	        data: [data.nltk.Positive,data.dumax.Positive,data.gate.Positive,data.combaining.Positive]
        	    }, {
        	        type: 'column',
        	        name: 'negative',
        	        data: [data.nltk.Negative,data.dumax.Negative,data.gate.Negative,data.combaining.Negative]
        	    }, {
        	        type: 'column',
        	        name: 'neutral',
        	        data: [data.nltk.Neutral,data.dumax.Neutral,data.gate.Neutral,data.combaining.Neutral]
        	    }, {
        	        type: 'spline',
        	        name: 'Average',
        	        data: [data.nltk.Positive,data.dumax.Positive,data.gate.Positive,data.combaining.Positive],
        	        marker: {
        	            lineWidth: 2,
        	            lineColor: Highcharts.getOptions().colors[3],
        	            fillColor: 'white'
        	        }
        	    }]
        	});	
       }
        
         
        
        
        
        function setDataToDumaxChart(data){
        	Highcharts.setOptions({
	    	    colors:['#058DC7', '#50B432', '#ED561B']
	    	});
        	Highcharts.chart('dumaxChart', {

        	    title: {
        	        text: 'Chart.update'
        	    },

        	    subtitle: {
        	        text: 'Plain'
        	    },

        	    xAxis: {
        	        categories: ['neutral', 'positive', 'negative']
        	    },

        	    series: [{
        	        type: 'column',
        	        colorByPoint: true,
        	        data: [data['Neutral'], data['Positive'], data['Negative']],
        	        showInLegend: false
        	    }]

        	});
        }
        function setDataToNltkChart(data){
        	Highcharts.setOptions({
	    	    colors:['#058DC7', '#50B432', '#ED561B']
	    	});
        	Highcharts.chart('nltkChart', {

        	    title: {
        	        text: 'Chart.update'
        	    },

        	    subtitle: {
        	        text: 'Plain'
        	    },

        	    xAxis: {
        	        categories: ['neutral', 'positive', 'negative']
        	    },

        	    series: [{
        	        type: 'column',
        	        colorByPoint: true,
        	        data: [data['Neutral'], data['Positive'], data['Negative']],
        	        showInLegend: false
        	    }]

        	});
        }
        function setDataToGateChart(data){
        	Highcharts.setOptions({
	    	    colors:['#058DC7', '#50B432', '#ED561B']
	    	});
        	Highcharts.chart('gateChart', {

        	    title: {
        	        text: 'Chart.update'
        	    },

        	    subtitle: {
        	        text: 'Plain'
        	    },

        	    xAxis: {
        	        categories: ['neutral', 'positive', 'negative']
        	    },

        	    series: [{
        	        type: 'column',
        	        colorByPoint: true,
        	        data: [data['Neutral'], data['Positive'], data['Negative']],
        	        showInLegend: false
        	    }]

        	});
        }
        function setDataToCombainingChart(data){
        	Highcharts.setOptions({
	    	    colors:['#058DC7', '#50B432', '#ED561B']
	    	});
        	Highcharts.chart('combainingChart', {

        	    title: {
        	        text: 'Chart.update'
        	    },

        	    subtitle: {
        	        text: 'Plain'
        	    },

        	    xAxis: {
        	        categories: ['neutral', 'positive', 'negative']
        	    },

        	    series: [{
        	        type: 'column',
        	        colorByPoint: true,
        	        data: [data['Neutral'], data['Positive'], data['Negative']],
        	        showInLegend: false
        	    }]

        	});
        }
        
    }
 ]);