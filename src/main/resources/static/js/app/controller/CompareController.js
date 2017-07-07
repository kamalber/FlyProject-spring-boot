monApp.controller('CompareController',
    ['CompareService', '$scope','$sessionStorage','$timeout','Upload',  function(CompareService, $scope,$sessionStorage,$timeout,Upload) {
    	
        var self = this;
    
 
        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;
        self.compareTols=compareTols;
        self.statsShow=false;

     
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
          	    	statsShow=true;
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
        	var nltkAvarage=(data.nltk.Neutral+data.nltk.Positive+data.nltk.Negative)/3;
        	var dumaxAvarage=(data.dumax.Neutral+data.dumax.Positive+data.dumax.Negative)/3;
        	var gateAvarage=(data.gate.Neutral+data.gate.Positive+data.gate.Negative)/3;
        	var combainingAvarage=(data.combaining.Neutral+data.combaining.Positive+data.combaining.Negative)/3;
//        	Highcharts.setOptions({
//	    	    colors:['#058DC7', '#50B432', '#ED561B']
//	    	});
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
        	        name: 'Neutral',
        	        data: [data.nltk.Neutral,data.dumax.Neutral,data.gate.Neutral,data.combaining.Neutral]
        	    }, {
        	        type: 'column',
        	        name: 'Negative',
        	        data: [data.nltk.Negative,data.dumax.Negative,data.gate.Negative,data.combaining.Negative]
        	    }, {
        	        type: 'column',
        	        name: 'Positive',
        	        data: [data.nltk.Positive,data.dumax.Positive,data.gate.Positive,data.combaining.Positive]
        	    },{
        	        type: 'spline',
        	        name: 'Average',
        	        data: [nltkAvarage,dumaxAvarage,gateAvarage,combainingAvarage],
        	        marker: {
        	            lineWidth: 2,
        	            lineColor: Highcharts.getOptions().colors[3],
        	            fillColor: 'white'
        	        }
        	    }]
        	});	
       }
        
         
        
        
        
        function setDataToDumaxChart(data){
//        	Highcharts.setOptions({
//	    	    colors:['#058DC7', '#50B432', '#ED561B']
//	    	});
        	Highcharts.chart('dumaxChart', {

        	    title: {
        	        text: 'Chart.update'
        	    },

        	    subtitle: {
        	        text: 'Plain'
        	    },

        	    xAxis: {
        	        categories: ['neutral', 'negative', 'positive']
        	    },

        	    series: [{
        	        type: 'column',
        	        colorByPoint: true,
        	        data: [data['Neutral'],data['Negative'],data['Positive']],
        	        showInLegend: false
        	    }]

        	});
        }
        function setDataToNltkChart(data){
//        	Highcharts.setOptions({
//	    	    colors:['#058DC7', '#50B432', '#ED561B']
//	    	});
        	Highcharts.chart('nltkChart', {

        	    title: {
        	        text: 'Chart.update'
        	    },

        	    subtitle: {
        	        text: 'Plain'
        	    },

        	    xAxis: {
        	        categories: ['neutral', 'negative', 'positive']
        	    },

        	    series: [{
        	        type: 'column',
        	        colorByPoint: true,
        	        data: [data['Neutral'],data['Negative'],data['Positive']],
        	        showInLegend: false
        	    }]

        	});
        }
        function setDataToGateChart(data){
//        	Highcharts.setOptions({
//	    	    colors:['#058DC7', '#50B432', '#ED561B']
//	    	});
        	Highcharts.chart('gateChart', {

        	    title: {
        	        text: 'Chart.update'
        	    },

        	    subtitle: {
        	        text: 'Plain'
        	    },

        	    xAxis: {
        	        categories: ['neutral', 'negative', 'positive']
        	    },

        	    series: [{
        	        type: 'column',
        	        colorByPoint: true,
        	        data: [data['Neutral'],data['Negative'],data['Positive']],
        	        showInLegend: false
        	    }]

        	});
        }
        function setDataToCombainingChart(data){
//        	Highcharts.setOptions({
//	    	    colors:['#058DC7', '#50B432', '#ED561B']
//	    	});
        	Highcharts.chart('combainingChart', {

        	    title: {
        	        text: 'Chart.update'
        	    },

        	    subtitle: {
        	        text: 'Plain'
        	    },

        	    xAxis: {
        	        categories: ['neutral', 'negative', 'positive']
        	    },

        	    series: [{
        	        type: 'column',
        	        colorByPoint: true,
        	        data: [data['Neutral'],data['Negative'],data['Positive']],
        	        showInLegend: false
        	    }]

        	});
        }
        
    }
 ]);