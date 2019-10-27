(function() {
	
	
	function init() {
	    
	    document.querySelector('#submit').addEventListener('click', calculateMoney);
	    
	    //document.getElementById('#e1').addEventListener('click', calculateMoney);
	   
//	    document.querySelector('').addEventListener('click', loadFavoriteItems);
//	    document.querySelector('').addEventListener('click', loadRecommendedItems);
	    
	  }
	
	
	function calculateMoney(){
		showMessage('Calculating Results');
		
//		inputvalue

		showMessage('Calculating Results');
	    //input
	    var inputvalue = document.getElementById("inputvalue");
	    var positions = document.getElementById("positions");
	    var date = document.getElementById("date");
		// The request parameters
	    var url = './search';
	    var params = "positions="+positions;
	    var data = null;

	    
	    //ajax
	    ajax('POST', url, data, 
	     function(res) {
	    	console.log(res);
	      var result = JSON.parse(res);
	      if ('level' in result) {
	        var l = result.level;
	        
	      } else {
	        console.warn('Getting location by IP failed.');
	      }
	      //loadNearbyItems();
	      money.placeholder=l;
	    });
	    
	    
	    //output
	    var money = document.getElementById("displayProf");
	   
	    money.placeholder="hahahah";

	}
	
	function showMessage(msg) {
		 var money = document.getElementById("displayProf");
		   
		 money.placeholder="Calculating...";
	    //var itemList = document.querySelector('#item-list');
//	    itemList.innerHTML = '<p class="notice"><i class="fa fa-spinner fa-spin"></i> ' +
//	      msg + '</p>';
		
	}
	
	function showeval(){
		window.alert("calcuate money");
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