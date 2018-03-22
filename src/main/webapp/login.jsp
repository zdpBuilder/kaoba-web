<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<title>登录</title>
		<link rel="stylesheet" href="plugins/layui/css/layui.css" media="all" />
		<link rel="stylesheet" href="css/login.css" />
	</head>

	<body class="beg-login-bg">
		<div class="beg-login-box">
			<header>
				<h1>考吧后台登录</h1>
			</header>
			<div class="beg-login-main">
				<form class="layui-form" method="post">
					<div class="layui-form-item">
						<label class="beg-login-icon">
                        <i class="layui-icon">&#xe612;</i>
                    </label>
						<input type="text" name="loginId" lay-verify="required" autocomplete="off" placeholder="这里输入登录账号" class="layui-input">
					</div>
					<div class="layui-form-item">
						<label class="beg-login-icon">
                        <i class="layui-icon">&#xe642;</i>
                    </label>
						<input type="password" name="password" lay-verify="required" autocomplete="off" placeholder="这里输入密码" class="layui-input">
					</div>
					<div class="layui-form-item">
						    <div class="layui-input-inline" style="margin-left:50px;">
						      <input type="radio" name="status" value="1" title="管理员" checked="">
						      <input type="radio" name="status" value="2" title="老师">
						    </div>
						<div class="beg-pull-right" style="margin-right:100px;">
							<button class="layui-btn layui-btn-primary" lay-submit="" lay-filter="login">
                            <i class="layui-icon">&#xe650;</i> 登录
                        </button>
						</div>
						<div class="beg-clear"></div>
					</div>
				</form>
			</div>
			<footer>
				<p>ITF4 © 545226478@qq.com</p>
			</footer>
		</div>
		<script type="text/javascript" src="plugins/layui/layui.js"></script>
		<script>
			layui.use(['layer', 'form'], function() {
				var layer = layui.layer,
					$ = layui.jquery,
					form = layui.form();
					
				form.on('submit(login)',function(data){
				    var formJson = data.field;
					$.ajax({
			   			method: "post",
			   			url:"${pageContext.request.contextPath}/login",
			   			data: formJson,
			   			success:function(result){	 	  			 		
                       	var datas = result.data;
                       	if(datas){
	               	if(datas.loginStatus==1){
	               		  
			               		location.href='index.jsp';        		
			               	}else if(datas.loginStatus==2){
			               		location.href='indexTeacher.jsp'; 
			               	}else{ 
			               	 setTimeout(function () {                			                       
			                   layer.msg("用户名或密码错误！",{title:'提示消息',icon: 2, time: 1500});   
                         }, 2400);
			               	}
                       
                       	}else{
                        	 setTimeout(function () {                			                       
  			                   layer.msg("用户名或密码错误！",{title:'提示消息',icon: 2, time: 1500});   
                           }, 2400);
                       	}
			   			
			   			},
			   			error: function(){			
			                  layer.msg('服务器异常！', {title:'提示消息',icon: 0, time: 1500}); //1s后自动关闭);                          
			                  return;
			   			}  	
			           });  
				
					 return false; 
				});
			});
		</script>
	</body>

</html>