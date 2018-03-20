<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head> 
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>新增老师信息</title>
    <link rel="stylesheet" href="../plugins/layui2.x/css/layui.css">
    <link rel="stylesheet" href="../css/style.css">
    <style>
    	.layui-input{height:26px;font-size:12px;width:180px;}
    	.layui-form-select{font-size:12px;width:180px;}
    	dd{line-height:26px;font-size:12px;width:140px;}
    	.layui-elem-field legend{font-size:12px;}
    	.layui-form-radio *{line-height:26px;font-size:12px;}
    </style>
</head>
<body class="body">

<form class="layui-form" action="">  

 <fieldset class="layui-elem-field">
  <legend>基本信息</legend>
  		<div class="layui-form-item" style="margin-bottom:3px;">
	        <label class="layui-form-label" style="font-size:12px;line-height:10px;">账号</label>
	        <div class="layui-input-inline">
	            <input type="text" name="loginId" id="loginId" lay-verify="required" placeholder="必填项" autocomplete="off" 
	            class="layui-input layui-form-danger" style="height:26px;font-size:12px;">
	        </div>
	        
	        <label class="layui-form-label" style="font-size:12px;line-height:10px;">姓名</label>
	         <div class="layui-input-inline">
	            <input type="text" name="teaName" id="teaName" lay-verify="required" placeholder="必填项" autocomplete="off"
	                   class="layui-input layui-form-danger" style="height:26px;font-size:12px;">
	        </div>
        </div>
     </fieldset>
    <div class="layui-form-item" style="text-align: center;margin-top:10px;">
        <button class="layui-btn layui-btn-sm layui-btn-normal" lay-submit="" lay-filter="addForm">保存</button>
        &nbsp;&nbsp;
        <button class="layui-btn layui-btn-sm layui-btn-normal" id="close">取消</button>
    </div>
</form>

<script src="../plugins/layui2.x/layui.js" charset="utf-8"></script>
<script type="text/javascript">
    layui.use(['form', 'layedit', 'laydate', 'element','upload'], function () {
    var form = layui.form
            , layer = layui.layer
            , layedit = layui.layedit
            , laydate = layui.laydate
            , element = layui.element
            , upload = layui.upload;
        
    var $ = layui.jquery;
  //重新渲染表单
  function renderForm(){
	  form.render();
  }
  //保存按钮
  form.on('submit(addForm)', function (data) {
	  
        var formJson = data.field;
       	$.ajax({
   			method: "post",
   			url:"${pageContext.request.contextPath}/teacher/save",
   			data: formJson,
   			async:false,
   			success:function(result){
   				var data = result.data;
               	if(data>0){
               		//关闭窗口 并给父页面传值
                       var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                       parent.layer.msg('添加成功！', {title:'提示消息',icon: 1, time: 1500}); //1s后自动关闭);             
                       parent.reloadTable(1);
                       parent.layer.close(index);
               	}else{ 
               		 var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                       parent.layer.msg('添加失败！', {title:'提示消息',icon: 2, time: 1500}); //1s后自动关闭);                          
                       parent.layer.close(index);
               	}
   			},
   			error: function(){			
   				 var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                    parent.layer.msg('服务器异常！', {title:'提示消息',icon: 0, time: 1500}); //1s后自动关闭);                          
                    parent.layer.close(index);	
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