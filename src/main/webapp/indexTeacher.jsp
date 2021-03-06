﻿<%@ page language="java"  contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<title>考吧管理端系统</title>
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="format-detection" content="telephone=no">

		<link rel="stylesheet" href="plugins/layui/css/layui.css" media="all" />
		<link rel="stylesheet" href="css/global.css" media="all">
		<link rel="stylesheet" href="plugins/font-awesome/css/font-awesome.min.css">

	</head>

	<body>
		<div class="layui-layout layui-layout-admin" style="border-bottom: solid 5px #1aa094;">
			<div class="layui-header header header-demo" style="background-color: #393D49">
				<div class="layui-main">
					<div class="admin-login-box">
                        <!--logo-->
						<a class="logo" style="left: 0;" href="">
							<span style="font-size: 22px;">考吧管理端系统</span>
						</a>
						<div class="admin-side-toggle">
							<i class="fa fa-bars" aria-hidden="true"></i>
						</div>
						<div class="admin-side-full">
							<i class="fa fa-life-bouy" aria-hidden="true"></i>
						</div>
					</div>
					<ul class="layui-nav admin-header-item">
						<li class="layui-nav-item" >
							<a href="javascript:;" class="admin-header-user"  style="padding-left:80px;">
					<!--	<img src="images/0.jpg" />-->
								<span>${CurrentLoginUserInfo.teaName}</span>
							</a>
							<dl class="layui-nav-child">
								<dd>
									<a  id="editTeacherDatas"><i class="fa fa-user-circle" aria-hidden="true"></i> 个人资料</a>
								</dd>
								<dd>
									<a  id="editTeacherPassword"><i class="fa fa-user-circle" aria-hidden="true"></i>修改密码</a>
								</dd>
								<!-- <dd>
									<a href="javascript:;"><i class="fa fa-gear" aria-hidden="true"></i> 设置</a>
								</dd> -->
								<!-- <dd id="lock">
									<a href="javascript:;">
										<i class="fa fa-lock" aria-hidden="true" style="padding-right: 3px;padding-left: 1px;"></i> 锁屏 (Alt+L)
									</a>
								</dd> -->
								<dd>
									<a href="/kaoba/logout"><i class="fa fa-sign-out" aria-hidden="true"></i> 注销</a>
								</dd>
							</dl>
						</li>
					</ul>
					<!-- <ul class="layui-nav admin-header-item-mobile">
						<li class="layui-nav-item">
							<a href="login.html"><i class="fa fa-sign-out" aria-hidden="true"></i> 注销</a>
						</li>
					</ul> -->
				</div>
			</div>
            <!--左侧导航栏-->
			<div class="layui-side layui-bg-black" id="admin-side">

				<div class="layui-side-scroll" id="admin-navbar-side" lay-filter="side"></div>
			</div>
            <!--默认首页-->
			<div class="layui-body" style="bottom: 0;border-left: solid 2px #1AA094;" id="admin-body">
				<div class="layui-tab admin-nav-card layui-tab-brief" lay-filter="admin-tab">
					<ul class="layui-tab-title">
						<li class="layui-this">
							<i class="fa fa-dashboard" aria-hidden="true"></i>
							<cite>首页</cite>
						</li>
					</ul>
					<div class="layui-tab-content" style="min-height: 150px; padding: 5px 0 0 0;">
						<div class="layui-tab-item layui-show">
							<iframe src="admin/tree_db_course.jsp"></iframe>
						</div>
					</div>
				</div>
			</div>
            <!--系统下部-->
			<div class="layui-footer footer footer-demo" id="admin-footer">
				<div class="layui-main">
					<p>2018 &copy;
						<a href="">ITF4</a> LGPL license
					</p>
				</div>
			</div>
			<div class="site-tree-mobile layui-hide">
				<i class="layui-icon">&#xe602;</i>
			</div>
			<div class="site-mobile-shade"></div>
			
			<!--锁屏模板 start-->
			<!-- <script type="text/template" id="lock-temp">

				<div class="admin-header-lock" id="lock-box">
					<div class="admin-header-lock-img">
						<img src="images/0.jpg"/>
					</div>
					<div class="admin-header-lock-name" id="lockUserName">beginner</div>
					<input  type="text" class="admin-header-lock-input" value="输入密码解锁.." name="lockPwd" id="lockPwd" />
					<button class="layui-btn layui-btn-small" id="unlock">解锁</button>
				</div>
			</script> -->
			<!--锁屏模板 end -->
			
			<script type="text/javascript" src="plugins/layui/layui.js"></script>
			<script src="js/indexTea.js"></script>
			<script>
				layui.use('layer', function() {
					var $ = layui.jquery,
						layer = layui.layer;
					//修改当前用户个人资料
			      	$("#editTeacherDatas").click(function(){
			      		layer.open({
			      		  type: 2 //Page层类型
			      		  ,area: ['680px', '388px']
			      		  ,title:  ['个人资料', '']
			      		  ,shade: 0.6 //遮罩透明度
			      		  ,fixed: true //位置固定
			      		  ,maxmin: false //开启最大化最小化按钮
			      		  ,anim: 5 //0-6的动画形式，-1不开启
			      		  ,content: 'teacher/editTeacher.jsp'
			      	   });
			      	});
					//修改当前用户秘密
			      	$("#editTeacherPassword").click(function(){
			      		layer.open({
			      		  type: 2 //Page层类型
			      		  ,area: ['390px', '233px']
			      		  ,title:  ['修改密码', '']
			      		  ,shade: 0.6 //遮罩透明度
			      		  ,fixed: true //位置固定
			      		  ,maxmin: false //开启最大化最小化按钮
			      		  ,anim: 5 //0-6的动画形式，-1不开启
			      		  ,content: 'teacher/eidtTeacherPassword.jsp'
			      	   });
			      	});
                  
					
				});
			</script>
		</div>
	</body>

</html>