function deletePost(postId) {
	fetch(contextPath + "/post/" + postId + "/delete", {method: "POST"})
  	 .then((response) => {
    	if (response.status == 200 && response.redirected != true){
    		var element = document.getElementById("post-" + postId);
    		element.parentNode.removeChild(element);
    	}
 	 })
}