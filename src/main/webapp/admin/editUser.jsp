<%@ page language="java" import ="itf4.kaoba.model.SysUser" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
//flag==0代表 修改用户的信息  flag==1代表修改当前用户的信息
int flag=0;
//当前用户主键id
SysUser currentLoginUser = (SysUser) session.getAttribute("CurrentLoginUserInfo");
int currId =currentLoginUser.getId();
//用户管理主键id
int id = Integer.parseInt(request.getParameter("id"));
if(id==currId){
	flag=1;
}
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>修改用户信息</title>
     <link rel="stylesheet" href="../plugins/layui2.x/css/layui.css">
    <link rel="stylesheet" href="../css/style.css">
    <style>
    	.layui-input{height:26px;}
    </style>
</head>
<body class="body">

<form class="layui-form" action="">
    <input type="hidden" name="id" id="id" value="<%=id%>"/>
    <input type="hidden" name="userPassword" id="userPassword" />
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
	            <input type="text" name="userName" id="userName" lay-verify="required" placeholder="必填项" autocomplete="off"
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

      //重新渲染表单
      function renderForm(){
    	  form.render();
      }
      
      //表单元素赋值
      var userId = <%=id %>;
      $.ajax({
			method: "post",
			data : {"id":userId},
			url:"../user/show",
		
			success:function(result){
				result = result.data;
				if(result){
					$("#loginId").val(result.loginId);
					$("#userName").val(result.userName);
					$("#userPassword").val(result.userPassword);
					
				}
			}
      });   
        //保存按钮
          form.on('submit(addForm)', function (data) {
            var formJson = data.field;
            	$.ajax({
        			method: "post",
        			url:"../user/save",
        			data: formJson,
        			async:false,
        			success:function(result){
        				var data = result.data;
                    	if(data.id>0){
                    		//关闭窗口 并给父页面传值
                            var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                            parent.layer.msg('修改成功！', {title:'提示消息',icon: 1, time: 1500}); //1s后自动关闭);
                        	if(<%=flag%>!=1){
                             parent.reloadTable(1);
                        	}	                       
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