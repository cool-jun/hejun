// 返回显示所有信息界面
$(function(){
	$("#back").on("click",function(){
		window.location.href = "showAllAppInfo";
	});
});

// 检查app版本是否唯一
$("#versionNo").bind("blur", function () {
    var versionNo = $("#versionNo").val();
    var appId = $("#appId").val();
    if (versionNo !== '' && versionNo != null) {
        $.ajax({
            url:"checkVersionNo",
            data:{
                appId:appId,
                versionNo:versionNo
            },
            dataType:"json",
            success:function (data) {
                if (data === 1) {
                    alert("版本号未重复可以用");
                } else {
                    alert("版本号重复");
                }
            },
            error:function () {
                alert("检查版本号错误");
            }
        })
    }
});
      
      
      