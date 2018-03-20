<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
//用户id 
String teacheIds = request.getParameter("idStr");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>课程授权</title>
     <link rel="stylesheet" href="../plugins/layui2.x/css/layui.css">
    <link rel="stylesheet" href="../css/style.css">
    <style>
    	.layui-input{height:26px;}
    </style>
</head>
<body class="body">

<form class="layui-form" action="">
<input type="hidden" id="teacheIds" value="<%=teacheIds %>"/>
    
    <table id="courseTable">
    </table>
    <div class="layui-form-item" style="text-align: center;margin-top:10px;">
        <button class="layui-btn layui-btn-sm layui-btn-normal" lay-submit="" lay-filter="addForm">授权</button>
        &nbsp;&nbsp;
        <button class="layui-btn layui-btn-sm layui-btn-normal" id="close">取消</button>
    </div>
</form>

<script src="../plugins/layui2.x/layui.js" charset="utf-8"></script>
<script type="text/javascript">
// layui方法
layui.use(['table', 'form','layer'], function () {
	
    // 操作对象
    var table = layui.table;
    var layer = layui.layer;
    var form = layui.form;
    var $ = layui.jquery;

    //请求课程列表 
    $.ajax({
    	method:"post",
    	data:{
    		"page":0,
    		"keywords":"",
    		"limit":0
    	},
    	url:"../course/list",
    	async:false,
    	success:function(result){
			result = result.data;
			if(result){
				$("#courseTable").html("");
				var string='';
				for(var i=0;i<result.length;i++){
					if(i%6 ==0){
						string +='<tr align="left">';
					}
					string +='<td style="font-size:12px;">'
					       +'<input type="checkbox" name="courseName" value="'+result[i].id+'" title="'+result[i].teaName+'" lay-skin="primary" /></td>';
				}
				$("#courseTable").html(string);
				
				form.render();
			}
		}
    });
    //将已有权限显示
	 $.ajax({
	  	method:"post",
	  	data:{
	  		"teacherId":$('#teacheIds').val()
	  	},
	  	url:"../teacher/showTeacherCourse",
	  	async:false,
	  	success:function(result){
		result = result.data;
		if(result !=null){
			var courseIds = result.courseId.split(",");
			for(var i=0;i<courseIds.length;i++){
				$('input[type="checkbox"]').each(function(){
					if($(this).val()==courseIds[i]){
						$(this).attr("checked","checked");
						return;
					}
				});
			}
			form.render();
			
		}
	}
 });
    
    
    //授权按钮
    form.on('submit(addForm)', function (data) {
    	//获取老师id
    	var teacherIds = $('#teacheIds').val();
    	//获取课程id
    	var courseIds ="";
    	$('input[type="checkbox"]:checked').each(function(){
    		courseIds +=$(this).val()+",";
    	});
    	courseIds = courseIds.substring(0,courseIds.length-1);
    	//teacherIds = teacherIds.substring(0,teacherIds.length-1);
    	
    	console.info("courseIds:"+courseIds);
    	console.info("teacherIds:"+teacherIds);
    	
    	
    	//向后台提交
    	$.ajax({
       			method: "post",
       			url:"../teacher/grantCourse",
       			data: {
       				"courseIds":courseIds,
       				"teacherIds":teacherIds
       			},
       			async:false,
       			success:function(result){
       				result = result.data;
        			console.info(result);
        			if(result>0){
        				//关闭窗口 并给父页面传值
                        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                        parent.layer.msg('授权成功！', {title:'提示消息',icon: 1, time: 1500}); //1s后自动关闭);
                        parent.reloadTable(1);
                        parent.layer.close(index);
        			}
                   	
       			}
        });  
    
    });

   //关闭窗口按钮
    $("#close").click(function(){
    	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    	parent.layer.close(index);
    });

});


</script>

</body>
</html>