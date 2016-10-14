/**
 * Created by Administrator on 2016/8/31.
 */
var api = {
    "server": "http://127.0.0.1",
    "port": "8888"
}
function getApiPath(request) {
    return api.server + ":" + api.port + "/" + request ;
}
/*
ajax 模板

$.ajax({
	type: "GET",
	url: getApiPath("login"),
	data: {username:$("#username").val(), password:$("#password").val()},
	dataType: "json",
	success: function(data){
		$('#resText').empty();   //清空resText里面的所有内容
		var html = '';
		$.each(data, function(commentIndex, comment){
			html += '<div class="comment"><h6>' + comment['username']
				+ ':</h6><p class="para"' + comment['content']
				+ '</p></div>';
		});
		$('#resText').html(html);
	}
});

*/