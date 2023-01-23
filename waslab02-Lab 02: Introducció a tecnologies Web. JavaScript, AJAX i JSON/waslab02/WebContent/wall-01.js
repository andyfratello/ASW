var baseURI = "http://localhost:8080/waslab02";
var tweetsURI = baseURI+"/tweets";

var req;
var tweetBlock = "	<div id='tweet_{0}' class='wallitem'>\n\
	<div class='likes'>\n\
	<span class='numlikes'>{1}</span><br /> <span\n\
	class='plt'>people like this</span><br /> <br />\n\
	<button onclick='{5}Handler(\"{0}\")'>{5}</button>\n\
	<br />\n\
	</div>\n\
	<div class='item'>\n\
	<h4>\n\
	<em>{2}</em> on {4}\n\
	</h4>\n\
	<p>{3}</p>\n\
	</div>\n\
	</div>\n";

String.prototype.format = function() {
	var args = arguments;
	return this.replace(/{(\d+)}/g, function(match, number) { 
		return typeof args[number] != 'undefined'
			? args[number]
		: match
		;
	});
};

function likeHandler(tweetID) {
	var target = 'tweet_' + tweetID;
	var uri = tweetsURI+ "/" + tweetID +"/likes";
	// e.g. to like tweet #6 we call http://localhost:8080/waslab02/tweets/6/like

	req = new XMLHttpRequest();
	req.open('POST', uri, /*async*/true);
	req.onload = function() { 
		if (req.status == 200) { // 200 OK
			document.getElementById(target).getElementsByClassName("numlikes")[0].innerHTML = req.responseText;
		}
	};
	req.send(/*no params*/null);
}

function deleteHandler(tweetID) {
	req = new XMLHttpRequest();
	var token = localStorage.getItem("token" + tweetID);
	req.open('DELETE', tweetsURI + "/" + tweetID, /*async*/true);
	req.setRequestHeader("Authorization", token);
	req.onload = function() { 
		if (req.status == 200) { // 200 OK
			var element = document.getElementById("tweet_" + tweetID);
			localStorage.removeItem("token" + tweetID);
			localStorage.removeItem("id" + tweetID);
			element.remove();
			document.getElementById(target).getElementsByClassName("numlikes")[0].innerHTML = req.responseText;
		}
	};
	req.send(/*no params*/null);
}

function getTweetHTML(tweet, action) {  // action :== "like" xor "delete"
	var dat = new Date(tweet.date);
	var dd = dat.toDateString()+" @ "+dat.toLocaleTimeString();
	return tweetBlock.format(tweet.id, tweet.likes, tweet.author, tweet.text, dd, action);

}

function getTweets() {
	req = new XMLHttpRequest(); 
	req.open("GET", tweetsURI, true); 
	req.onload = function() {
		if (req.status == 200) { // 200 OK
			var tweet_list = req.responseText;
			var tweetsJSON = JSON.parse(tweet_list);
			var tweetHTML = "";
			for (var i = 0; i < tweetsJSON.length; ++i) {
				var tt = tweetsJSON[i];
				if (localStorage.getItem("id" + tt.id) != null && tt.id == localStorage.getItem("id" + tt.id)) {
					tweetHTML += getTweetHTML(tt, "delete");
				} else {
					tweetHTML += getTweetHTML(tt, "like");
				}
			}
			document.getElementById("tweet_list").innerHTML = tweetHTML;
		}
	};
	req.send(null); 
};


function tweetHandler() {
	var author = document.getElementById("tweet_author").value;
	var text = document.getElementById("tweet_text").value;
	
	
	req = new XMLHttpRequest();
	req.open('POST', tweetsURI, /*async*/true);
	req.onload = function() { 
		if (req.status == 200) { // 200 OK
			var nt = JSON.parse(req.responseText);
			var id = nt.id;
			var token = nt.token;
			localStorage.setItem("id" + id, id);
			localStorage.setItem("token" + id, token);
			var tweetHTML = getTweetHTML(nt, "delete");
			document.getElementById("tweet_list").innerHTML = tweetHTML + document.getElementById("tweet_list").innerHTML;
		}
	};
	req.setRequestHeader("Content-Type","application/json");
	var JSONString = JSON.stringify({author: author, text: text});
	req.send(JSONString);
	
	/*var mes1 = "Someone ({0}) wants to insert a new tweet ('{1}'),\n but this feature is not implemented yet!";
	alert(mes1.format(author, text));*/

	// clear form fields
	document.getElementById("tweet_author").value = "";
	document.getElementById("tweet_text").value = "";

};

//main
function main() {
	document.getElementById("tweet_submit").onclick = tweetHandler;
	getTweets();
};
