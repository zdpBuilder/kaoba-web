<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
String id = request.getParameter("id");
%>
<!DOCTYPE html>  
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>修改题库</title>
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
 		<input type="hidden" name="id" value="<%=id%>">
  <legend>试题</legend>
  		<div class="layui-form-item" style="margin-bottom:3px;">
        <label class="layui-form-label" style="font-size:12px;line-height:10px;">试题名称:</label>
        <div class="layui-input-inline">
            <input type="text" name="singledbTitle" id="singledbTitle" lay-verify="required" placeholder="必填项" autocomplete="off"
                   class="layui-input layui-form-danger" style="height:26px;font-size:12px;">
        </div>
        <label class="layui-form-label" style="font-size:12px;line-height:10px;">选项A</label>
        <div class="layui-input-inline">
            <input type="text" name="singledbOptiona" id="singledbOptiona" lay-verify="required" placeholder="必填项" autocomplete="off" 
            class="layui-input layui-form-danger " style="height:26px;font-size:12px;">
        </div>
        </div>         
        <div class="layui-form-item" style="margin-bottom:3px;">
        <label class="layui-form-label" style="font-size:12px;line-height:10px;">选项B</label>
        <div class="layui-input-inline">
            <input type="text" name="singledbOptionb" id="singledbOptionb" lay-verify="required" placeholder="必填项" autocomplete="off"
                   class="layui-input layui-form-danger" style="height:26px;font-size:12px;">
        </div>
        <label class="layui-form-label" style="font-size:12px;line-height:10px;">选项C</label>
        <div class="layui-input-inline">
            <input type="text" name="singledbOptionc" id="singledbOptionc" lay-verify="required" placeholder="必填项" autocomplete="off" 
            class="layui-input layui-form-danger " style="height:26px;font-size:12px;">
        </div>
        </div>
        
         <div class="layui-form-item" style="margin-bottom:3px;">
        <label class="layui-form-label" style="font-size:12px;line-height:10px;">选项D</label>
        <div class="layui-input-inline">
            <input type="text" name="singledbOptiond" id="singledbOptiond" lay-verify="required" placeholder="必填项" autocomplete="off"
                   class="layui-input layui-form-danger" style="height:26px;font-size:12px;">
        </div>
        </div>
        </fieldset>
        <fieldset class="layui-elem-field">
        <legend>答案</legend>
  		<div class="layui-form-item" style="margin-bottom:3px;">
        <label class="layui-form-label" style="font-size:12px;line-height:10px;">答案:</label>
        <div class="layui-input-inline">
            <input type="text" name="answerdbKey" id="answerdbKey" lay-verify="required" placeholder="必填项" autocomplete="off"
                   class="layui-input layui-form-danger" style="height:26px;font-size:12px;">
        </div>
        <label class="layui-form-label" style="font-size:12px;line-height:10px;">详解:</label>
        <div class="layui-input-inline">
            <input type="text" name="answerdbDetail" id="answerdbDetail" lay-verify="required" placeholder="必填项" autocomplete="off" 
            class="layui-input layui-form-danger " style="height:26px;font-size:12px;">
        </div>
        </div>         
        <div class="layui-form-item" style="margin-bottom:3px;">
        <label class="layui-form-label" style="font-size:12px;line-height:10px;">图片:</label>
        <div class="layui-input-inline">
            <input type="text" name="answerdbDetailPicPath" id="answerdbDetailPicPath" lay-verify="" placeholder="" autocomplete="off"
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
layui.use(['form', 'upload','layedit', 'laydate', 'element'], function () {
	
    var form = layui.form
            , layer = layui.layer
            , layedit = layui.layedit
            , laydate = layui.laydate
            , element = layui.element
            , upload = layui.upload;
    var $ = layui.jquery;

  //表单元素赋值
  var id = <%=id %>;
  $.ajax({
		method: "post",
		data : {"id":id},
		url:"../courseVsSingleDb/showSingleDb",
	
		success:function(result){
			data = result.data;
			if(result){
				$("#singledbTitle").val(data.ee.singledbTitle);
				$("#singledbOptiona").val(data.ee.singledbOptiona);
				$("#singledbOptionb").val(data.ee.singledbOptionb);
				$("#singledbOptionc").val(data.ee.singledbOptionc);
				$("#singledbOptiond").val(data.ee.singledbOptiond);
				
				$("#answerdbKey").val(data.answerdb.answerdbKey);
				$("#answerdbDetail").val(data.answerdb.answerdbDetail);
				$("#answerdbDetailPicPath").val(data.answerdb.answerdbDetailPicPath);
				
			}
		}
  });   
    //保存按钮
      form.on('submit(addForm)', function (data) {
        var formJson = data.field;
        	$.ajax({
    			method: "post",
    			url:"../courseVsSingleDb/save",
    			data: formJson,
    			async:false,
    			success:function(result){
    				var data = result.data;
                	if(data>0){
                		 var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                         parent.layer.msg('修改成功！', {title:'提示消息',icon: 1, time: 1500}); //1s后自动关闭);
                         parent.reloadTable(1);
                         parent.layer.close(index);
                	}
                	
    			},
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