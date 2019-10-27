(function() {
	
	
	function init() {
	    console.log(calculateMoney);
	    document.querySelector('#submit').addEventListener('click', calculateMoney);
	    
	    document.getElementById('e6').addEventListener('click', showeval);
	   
//	    document.querySelector('').addEventListener('click', loadFavoriteItems);
//	    document.querySelector('').addEventListener('click', loadRecommendedItems);
	    
	  }
	
	
	function calculateMoney(){
		//showMessage('Calculating Results');
		
//		inputvalue

		//showMessage('Calculating Results');
	    //input
	    var inputvalue = document.getElementById("inputvalue");
	    var positions = document.getElementById("positions");
	    var date = document.getElementById("date");
		// The request parameters
	    
	    var params = "positions="+positions;
	    var data = null;
	    
	    console.log(positions.value);
	    var url = "search?positions="+positions.value;//WORK~50,AAPL~10,SNAP~40";
	    url+="&date="+date.value;
	    //console.log('calculateMoney');

	    
	    //ajax
	    ajax('GET', url, data, res => {
	      console.log(res);
	      var result = JSON.parse(res);
	      if ('level' in result) {
	        var l = result.level;
	        
	      } else {
	        console.warn('failed.');
	      }
	      //loadNearbyItems();
	      money.placeholder=l * inputvalue.value;
	    }, function(err) {console.log(err);});
	    
	    
	    //output
	    var money = document.getElementById("displayProf");
	   
	    money.placeholder="calculating...";

	}
	
	function showMessage(msg) {
		 var money = document.getElementById("displayProf");
		   
		 money.placeholder="Calculating...";

		
	}
	
	function showeval(){
		var ticker = document.getElementById("ticker");
		
		var url = "search?position="+ticker.value+"~100";//WORK;
		var data =null;
		//var date = new Date();
		url+="&date="+"20191027";
		    
		var calculate = document.getElementById("calculate");
		ajax('POST', url, data, res => {
		      console.log(res);
		      var result = JSON.parse(res);
		      if ('val' in result) {
		        var l = result.val;
		        
			    calculate.innerHTML=l;
		        
		      } else {
		        console.warn('failed.');
		      }
		      
		    }, function(err) {console.log(err);});
		
		calculate.innerHTML="Evaluating...";
	}

	
	
	
	
	
	  /**
	   * AJAX helper
	   *
	   * @param method - GET|POST|PUT|DELETE
	   * @param url - API end point
	   * @param data - request pay-load data
	   * @param successCallback - Successful callback function
	   * @param errorCallback - Error callback function
	   */
	  function ajax(method, url, data, successCallback, errorCallback) {
	    var xhr = new XMLHttpRequest();

	    xhr.open(method, url, true);

	    console.log('doh!');
	    xhr.onload = function() {
	      if (xhr.status === 200) {
	        successCallback(xhr.responseText);
	      } else {
	        errorCallback();
	      }
	    };

	    xhr.onerror = function() {
	      console.error("The request couldn't be completed.");
	      errorCallback();
	    };

	    if (data === null) {
	      xhr.send();
	    } else {
	      xhr.setRequestHeader("Content-Type",
	        "application/json;charset=utf-8");
	      xhr.send(data);
	    }
	  }
	  
	  init();
	
})();