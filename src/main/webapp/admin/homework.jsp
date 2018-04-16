<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>作业管理</title>
    <link rel="stylesheet" href="../plugins/layui2.x/css/layui.css">
    <link rel="stylesheet" href="../css/treestyle.css">
	<link rel="stylesheet" href="../plugins/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">    
</head>
<body class="body">
<input type="hidden" id="courseId" />
<div class="layui-fluid">  
  <div class="layui-row layui-col-space10">
    <div class="layui-col-md2">
		<fieldset class="layui-elem-field">
         <legend style="font-size:12px;">课程导航树</legend>
         <div class="layui-field-box">
         	<div class="layui-row layui-col-space10">
         		<div class="layui-col-md12">
         			<div id="tree" class="ztree"></div>
         		</div>
         	</div>
		  </div>
		  </fieldset>
    </div>
    
    <div class="layui-col-md10">
		<fieldset id="menu_func_div" class="layui-elem-field">
	         <legend style="font-size:12px;">作业列表</legend>
	         <div class="layui-field-box">
			    <!-- 操作按钮区域 -->
		        <div class="my-btn-box" style="margin-bottom:-10px;">
		            <span class="fl">
		                <a class="layui-btn layui-btn-sm" id="btn-add-photo"><i class="layui-icon">&#xe67c;</i>上传图片</a>
		                <a class="layui-btn layui-btn-sm" id="btn-add-photo-batch"><i class="layui-icon">&#xe67c;</i>批量上传图片</a>
		                <a class="layui-btn layui-btn-sm" id="btn-delete-all"><i class="layui-icon"></i>删除</a>
		                <a class="layui-btn layui-btn-sm" id="btn-refresh" data-type="refresh"><i class="layui-icon">&#x1002;</i>刷新</a>
		            </span>
		        </div>
		        <!-- 表格内容区域 -->
			    <div class="layui-col-md12 layui-col-space1" id="homeworkPhoto">
			    </div>
			  </div>
		</fieldset>
        
    </div>
  </div>
</div>

<script type="text/javascript" src="../plugins/ztree/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="../plugins/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="../plugins/layui2.x/layui.js"></script>
<!-- <script src="../plugins/layui2.x/layer/layer.min.js" type="text/javascript"></script> -->
<!--  课程导航树-->
<script type="text/javascript">
  var courseId = null;
  $(document).ready(function(){
	//ZTree树      
		var setting = {
			data: {
				simpleData: {
					enable: true,
					idKey: "id",
					pIdKey: "pid",
					rootPId: 0
				}
			},
			callback:{
				onClick: zTreeOnClick
			},
			async:{
				enable: true,
				type: "post",
				url : "${pageContext.request.contextPath}/courseVsSingleDb/treeList",
	            autoParam : [ "id" ],
				dataFilter: ajaxDataFilter
			}
			,
			view: {
				fontCss : {color:"#000"}
			}
		}; 
		//异步加载数据过滤
		function ajaxDataFilter(treeId, parentNode, responseData) {
		    if (responseData) {
		      for(var i =0; i < responseData.length; i++) {
		        //自动展开关闭
		    	responseData[i].open = false;
				//根节点加特殊图标
		        if(responseData[i].pid == "0"){
		        	//默只展开根节点
		        	responseData[i].open = true;
		        	responseData[i].iconOpen = '${pageContext.request.contextPath}/admin/frame/ztree/css/zTreeStyle/img/diy/1_open.png';
		        	responseData[i].iconClose = '${pageContext.request.contextPath}/admin/frame/ztree/css/zTreeStyle/img/diy/1_close.png';
				}
		      }
		    }
		    return responseData;
		};
		
		//树节点点击事件
		function zTreeOnClick(event, treeId, treeNode) {
			courseId = treeNode.id;
			$('#courseId').val(courseId);
			//显示作业图片
			reloadPhoto(courseId);
		}
		
		//设备分类树初始化
		var zTreeObj = $.fn.zTree.init($("#tree"), setting, []);
  });
  
     //显示作业图片
	function reloadPhoto(courseId){
		$('#homeworkPhoto').html('');
		$.post('${pageContext.request.contextPath}/teacher/getHomeworkPhoto',
				{'courseId':courseId,'page':0,'limit':0},
				function(res){
					var result = res.data;
					if(result !=null){
						var count = res.data.count;
						result = result.data;
						var preffix = '${pageContext.request.contextPath}/upload_files/homework_photo/';
						var string ='';
						var image= '';
						var photoUrl='';
						var operationButton = '';
						var j =1;
						for(var i=0;i<result.length;i++){
							if(i %2 ==0){
								string = '<div id="photo'+j+'" style="margin-top:20px"></div>';
								$('#homeworkPhoto').append(string);
								j++;
							}
							operationButton ='<div>'
				                +'<span><a onclick="editPhoto('+result[i].id+',this)" class="layui-btn style="font-size:10px;"><i class="layui-icon">&#xe642;</i>编辑</a></span>'
				                +'<span><a onclick="delPhoto('+result[i].id+',this)" class="layui-btn style="font-size:10px;"><i class="layui-icon">&#xe640;</i>删除</a></span>'
				                +'</div>';
							photoUrl = preffix + result[i].photoUrl;
							string ='<div style="margin-right:10px;display:inline">';
							image = '<img width="40%" height="35%" src='+photoUrl+' />';
							string = string + image + operationButton +'</div>';
							$('#photo'+(j-1)).append(string);
						}
						page(res); 
					}
		});
	}
  //分页
  function page(res){
	  var result = res.data;
	  //数据总数
	  var count = result.count;
	  //分页数据
	  result = result.data;
	  layui.use(['laypage', 'layer'], function(){
		  var laypage = layui.laypage
		  ,layer = layui.layer;
		  
		  //总页数大于页码总数
		  laypage.render({
		    elem: 'homeworkPhoto'
		    ,count: count //数据总数
		    ,limit:4  //每页数据
		    ,jump: function(obj){
		      console.log(obj);
		    }
		  })
	  });
  }
     
     
  //编辑照片
  function editPhoto(id,obj){
	  
  }
  
  //删除照片
  function delPhoto(id,obj){
	  
  }
  
   
  

</script>
<script type="text/javascript">

</script>

<script type="text/javascript">
var courseId = $('#courseId').val();
layui.use('upload',function(){
	  var upload = layui.upload;
	  var $ = layui.jquery;
	  //上传单个照片
	  upload.render({
		  elem:'#btn-add-photo',  //绑定元素
		  url:'${pageContext.request.contextPath}/teacher/uploadHomeworkPhoto',  //请求地址
		  method:'post',  //请求方法
		  accept: 'images', //允许上传的文件类型 图片
		  size: 10000, //最大允许上传的文件大小KB
		  before:function(){
			  if(courseId !="" && courseId !=null){
				  this.data = {'courseId':courseId}; //设置上传参数
			  }
		  },
		  done:function(res){
			  //请求成功的回调函数
			  //"${pageContext.request.contextPath}/upload_files/supplier_photo/"+url
			  var result = res.data;
			  console.info(result);
			  if(result == 2){
				  layer.msg('请选择章节！', {time: 1000}); //1s后自动关闭
			  } else if(result ==0){
				  layer.msg('上传失败！', {time: 1000}); //1s后自动关闭
			  } else{
				  layer.msg('上传成功！', {time: 1000}); //1s后自动关闭
			  }
		  },
		  error:function(){
			  
		  }
		  
	  });
	  
	  
	  //上传多个照片
	  upload.render({
		  elem:'#btn-add-photo-batch',  //绑定元素
		  url:'${pageContext.request.contextPath}/teacher/uploadHomeworkPhoto',  //请求地址
		  method:'post',  //请求方法
		  accept: 'images', //允许上传的文件类型 图片
		  size: 10000, //最大允许上传的文件大小KB
		  multiple:true,//开启多个文件上传
		  before:function(){
			  if(courseId !="" && courseId !=null){
				  this.data = {'courseId':courseId}; //设置上传参数
			  }
		  },
		  done:function(res){
			  //请求成功的回调函数
			  //"${pageContext.request.contextPath}/upload_files/supplier_photo/"+url
			  var result = res.data;
			  console.info(result);
			  if(result == 2){
				  layer.msg('请选择章节！', {time: 1000}); //1s后自动关闭
			  } else if(result ==0){
				  layer.msg('上传失败！', {time: 1000}); //1s后自动关闭
			  } else{
				  layer.msg('上传成功！', {time: 1000}); //1s后自动关闭
			  }
		  },
		  error:function(){
			  
		  }
		  
	  });
});
</script>
</body>
</html>