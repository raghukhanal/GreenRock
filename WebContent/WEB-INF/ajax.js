(function() {
	
	
	function init() {
	    
	    document.querySelector('').addEventListener('click', calculateMoney);
	    document.querySelector('').addEventListener('click', loadNearbyItems);
	    document.querySelector('').addEventListener('click', loadFavoriteItems);
	    document.querySelector('').addEventListener('click', loadRecommendedItems);
	    
	  }
	
	
	function calculateMoney(){
		console.log("calcuate money");
		// The request parameters
	    var url = './search';
	    var params = 'user_id=' + user_id + '&lat=' + lat + '&lon=' + lng;
	    var data = null;
	    
	 // display loading message
	    showMessage('Calculating Results');
	}
	
	function showMessage(msg) {
	    //var itemList = document.querySelector('#item-list');
//	    itemList.innerHTML = '<p class="notice"><i class="fa fa-spinner fa-spin"></i> ' +
//	      msg + '</p>';
		
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