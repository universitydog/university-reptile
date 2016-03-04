<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>------</title>
</head>

<body>
	<div id="">
		<a id="share1" version="1.0" class="qzOpenerDiv" href="" target="_blank">
			QQ空间分享
		</a>
		<a id="share2" version="1.0" class="qzOpenerDiv" href="" target="_blank">
			分享
		</a>
	</div>
	<div>
    	<script type="text/javascript">
			(function(){
				var p = {
					url:location.href,
					showcount:'1',/*是否显示分享总数,显示：'1'，不显示：'0' */
					desc:'我很喜欢啊!',/*默认分享理由(可选)*/
					summary:'',/*分享摘要(可选)*/
					title:'翁星耀',/*分享标题(可选)*/
					site:'',/*分享来源 如：腾讯网(可选)*/
					pics:'', /*分享图片的路径(可选)*/
					style:'203',
					width:98,
					height:22
				};
				var s = [];
				for(var i in p){
					s.push(i + '=' + encodeURIComponent(p[i]||''));
				}
				var url = "http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?" + s.join('&');
// 				var weiboUrl = "http://service.weibo.com/share/share.php?url=http%3A%2F%2Fopen.weibo.com%2Fsharebutton&type=button&language=zh_cn&searchPic=true&style=number"
				document.getElementById("share1").attributes["href"].value = url;
			})();
			</script>
			<script type="text/javascript">
			(function(){
				var p = {
					url:location.href,
					button:'button',
					title:'翁',
					language:'zh_cn',
					searchPic:'',
					style:'翁星耀',
					width:98,
					height:22
			    };
				var s = [];
				for(var i in p){
					s.push(i + '=' + encodeURIComponent(p[i]||''));
				}
				var weiboUrl = "http://service.weibo.com/share/share.php?" + s.join('&');
				document.getElementById("share2").attributes["href"].value = weiboUrl;
			})();
			</script>
    </div>

</body>
</html>
