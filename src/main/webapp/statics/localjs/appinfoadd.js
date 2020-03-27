$(function(){  
	//动态加载所属平台列表
	var platformId = $("#platformId");
	$.ajax({
		url:"datadictionarylist.json",
		data:{tcode:'APP_PLATFORM'},
		dataType:"json", //ajax接口（请求url）返回的数据类型
		success:function(data) { //data：返回数据（json对象）
			platformId.html("");
			var options = "<option value=\"\">--请选择--</option>";
			for(var i = 0; i < data.length; i++){
				options += "<option value=\""+data[i].valueId+"\">"+data[i].valueName+"</option>";
			}
			platformId.html(options);
		},
		error:function(data){//当访问时候，404，500 等非200的错误状态码
			alert("加载平台列表失败！" + data);
		}
	});  
	//动态加载一级分类列表
	var clevel1 = $("#categoryLevel1");
	$.ajax({
		type:"GET",//请求类型
		url:"categorylevellist.json",//请求的url
		data:{pid:null},//请求参数
		dataType:"json",//ajax接口（请求url）返回的数据类型
		success:function(data){//data：返回数据（json对象）
			clevel1.html("");
			var options = "<option value=\"\">--请选择--</option>";
			for(var i = 0; i < data.length; i++){
				options += "<option value=\""+data[i].id+"\">"+data[i].categoryName+"</option>";
			}
			clevel1.html(options);
		},
		error:function(data){//当访问时候，404，500 等非200的错误状态码
			alert("加载一级分类列表失败！");
		}
	});  
	//动态加载二级分类列表
	clevel1.change(function(){
		var categoryLevel1 = $("#categoryLevel1").val();
		var clevel2 = $("#categoryLevel2");
		if(categoryLevel1 !== '' && categoryLevel1 != null){
			$.ajax({
				//type:"GET",//请求类型
				url:"categorylevellist.json",//请求的url
				data:{pid:categoryLevel1},//请求参数
				dataType:"json",//ajax接口（请求url）返回的数据类型
				success:function(data){//data：返回数据（json对象）
					clevel2.html("");
					var options = "<option value=\"\">--请选择--</option>";
					for(var i = 0; i < data.length; i++){
						options += "<option value=\""+data[i].id+"\">"+data[i].categoryName+"</option>";
					}
					clevel2.html(options);
				},
				error:function(data){//当访问时候，404，500 等非200的错误状态码
					alert("加载二级分类失败！");
				}
			});
		}else{
			clevel2.html("");
			var options2 = "<option value=\"\">--请选择--</option>";
			clevel2.html(options2);
		}
		var level3 = $("#categoryLevel3");
		level3.html("");
		var options = "<option value=\"\">--请选择--</option>";
		level3.html(options);
	});
	//动态加载三级分类列表
	$("#categoryLevel2").change(function(){
		var categoryLevel2 = $("#categoryLevel2").val();
		var clevel3 = $("#categoryLevel3");
		if(categoryLevel2 !== '' && categoryLevel2 != null){
			$.ajax({
				//type:"GET",//请求类型
				url:"categorylevellist.json",//请求的url
				data:{pid:categoryLevel2},//请求参数
				dataType:"json",//ajax接口（请求url）返回的数据类型
				success:function(data){//data：返回数据（json对象）
					clevel3.html("");
					var options = "<option value=\"\">--请选择--</option>";
					for(var i = 0; i < data.length; i++){
						options += "<option value=\""+data[i].id+"\">"+data[i].categoryName+"</option>";
					}
					clevel3.html(options);
				},
				error:function(data){//当访问时候，404，500 等非200的错误状态码
					alert("加载三级分类失败！");
				}
			});
		}else{
			clevel3.html("");
			var options = "<option value=\"\">--请选择--</option>";
			clevel3.html(options);
		}
	});
	
	$("#APKName").bind("blur",function(){
		//ajax后台验证--APKName是否已存在
		$.ajax({
			type:"GET", //请求类型
			url:"apkexist.json", //请求的url
			data:{APKName:$("#APKName").val()}, //请求参数
			dataType:"json", //ajax接口（请求url）返回的数据类型
			success:function(data){ //data：返回数据（json对象）
				if(data.APKName === "empty"){ //参数APKName为空，错误提示
					alert("APKName不能为空！");
				}else if(data.APKName === "exist"){ //账号不可用，错误提示
					alert("该APKName已存在，不能使用！");
				}else if(data.APKName === "noexist"){ //账号可用，正确提示
					alert("该APKName可以使用！");
				}
			},
			error:function(data){ //当访问时候，404，500 等非200的错误状态码
				alert("请求错误！" + data);
			}
		});
	});

});
      
      
      