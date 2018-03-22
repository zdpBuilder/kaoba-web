<%@ page language="java"  contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>修改登录密码信息</title>
     <link rel="stylesheet" href="../plugins/layui2.x/css/layui.css">
    <link rel="stylesheet" href="../css/style.css">
    <style>
    	.layui-input{height:26px;}
    </style>
</head>
<body class="body">

<form class="layui-form" action="">
    <input type="hidden" name="id" id="id" value="${CurrentLoginUserInfo.id}"/>
   <div class="layui-form-item" style="margin-bottom:0px;">
            <label class="layui-form-label" style="font-size:12px;line-height:10px;">姓名</label>
            <div class="layui-input-inline">
                <input  id="userName" disabled  value="${CurrentLoginUserInfo.teaName}" class="layui-input" style="width:160px;background-color:#F2F2F2;">
            </div>
        </div>
   <div class="layui-form-item" style="margin-bottom:0px;">
            <label class="layui-form-label" style="font-size:12px;line-height:10px;">新密码</label>
            <div class="layui-input-inline">
                <input id="newPassFirst" type="password" value="" placeholder="输入新密码" lay-verify="required|password"  class="layui-input pwd" style="width:160px;">
            </div>
        </div>
        <div class="layui-form-item" style="font-size:12px;line-height:10px;">
            <label class="layui-form-label" style="font-size:12px;line-height:10px;">确认密码</label>
            <div class="layui-input-inline">
                <input id="newPassSecond" type="password" name="teaPassword" value="" placeholder="确认新密码" lay-verify="required|password" class="layui-input pwd" style="width:160px;">
            </div>
        </div> 
     
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

      //重新渲染表单
      function renderForm(){
    	  form.render();
      }
      
      //密码验证
         $('#newPassSecond').blur(function(){
     	if($('#newPassFirst').val()!=$('#newPassSecond').val()){
     		layer.msg("两次密码输入不相同，请重新输入！",{icon: 5,time:1500});
     		$('#newPassFirst').val("");
     		$('#newPassSecond').val("");
     		$('#newPassFirst').focus();
     	}
     });
     <%--  //表单元素赋值
      var userId = <%=id %>;
      $.ajax({
			method: "post",
			data : {"id":userId},
			url:"../user/show",
		
			success:function(result){
				result = result.data;
				if(result){
					$("#userName").val(result.userName);	
				}
			}
      });    --%>
        //保存按钮
          form.on('submit(addForm)', function (data) {
            var formJson = data.field;
            	$.ajax({
        			method: "post",
        			url:"../teacher/save",
        			data: formJson,
        			async:false,
        			success:function(result){
        				var data = result.data;
                    	if(data.id>0){  
                    		//关闭窗口 并给父页面传值
                            var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                            parent.layer.msg('修改成功！请重新登录！', {title:'提示消息',icon: 1, time: 1500}); //1s后自动关闭);         
            	            top.location.href='../logout';  
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