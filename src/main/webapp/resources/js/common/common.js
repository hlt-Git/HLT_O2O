// 更换验证码
function changeVerifyCode(img) {
    img.src="../Kaptcha?" + Math.floor(Math.random()*100);
}

/*//获取url中的shopId
function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null) {
        $.toast('获取Id成功！');
		return decodeURIComponent(r[2]);
	}
    $.toast('获取Id失败！');
	return '';
}*/


/*//获取url中的shopId
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        $.toast('获取Id成功！');
        return unescape(r[2]);
    }
    $.toast('获取Id失败！');
    return '';
}*/


/*//获取url中的shopId
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null){
        $.toast('获取Id成功！');
        return unescape(r[2]);
    }
    $.toast('获取Id失败！');
    return null;
}*/

function getQueryString(variable)
{
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        if(pair[0] == variable) {
            // $.toast('获取Id成功！');
            return pair[1];}
    }
    // $.toast('获取Id失败！');
    return(false);
}