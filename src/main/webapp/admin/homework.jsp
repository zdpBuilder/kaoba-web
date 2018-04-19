<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>作业管理</title>
    <link rel="stylesheet" href="../plugins/layui2.x/css/layui.css"  media="all">
    <link rel="stylesheet" href="../css/treestyle.css">
	<link rel="stylesheet" href="../plugins/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">    
</head>
<body class="body">
<input type="hidden" id="courseId" />
	<div class="layui-fluid">
		<div class="layui-row layui-col-space10">
			<div class="layui-col-md2">
				<fieldset class="layui-elem-field">
					<legend style="font-size: 12px;">课程导航树</legend>
					<div class="layui-field-box">
						<div class="layui-row layui-col-space10">
							<div class="layui-col-md12">
								<div id="tree" class="ztree"></div>
							</div>
						</div>
					</div>
				</fieldset>
			</div>

			<div class=" layui-col-md10">
			<fieldset id="menu_func_div" class="layui-elem-field">
								<legend style="font-size: 12px;">作业列表</legend>
								<div class="layui-field-box">
									<!-- 操作按钮区域 -->
									<div class="my-btn-box" style="margin-bottom: -10px;">
										<span class="fl"> <a class="layui-btn layui-btn-sm"
											id="btn-add-photo"><i class="layui-icon">&#xe67c;</i>上传图片</a>
											  <a class="layui-btn layui-btn-sm"
											id="btn-refresh" data-type="refresh"><i
												class="layui-icon">&#x1002;</i>刷新</a>
										</span>

										<div class="layui-input-inline">
											<label style="height: 26px; font-size: 15px;">上传日期</label> <input
												readonly="readonly" name="startDate" id="startDate"
												style="height: 22px; font-size: 12px;" /> <input
												readonly="readonly" name="endDate" id="endDate"
												style="height: 22px; font-size: 12px;" />
										</div>
										<button class="layui-btn layui-btn-sm" id="btn-search"
											style="vertical-align: top; font-size: 10px;">
											<i class="layui-icon" style="font-size: 14px;">&#xe615;</i>&nbsp;查询
										</button>
									</div>
									<!-- 表格内容区域 -->
							 	 <div class="layui-col-md12 layui-col-space1" style="margin-top:30px">
									  <ul id="imagesList" style="float:left;" ></ul>
									</div> 											
								</div>
							</fieldset>
			</div>
		</div>
	</div>

<script type="text/javascript" src="../plugins/ztree/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="../plugins/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="../plugins/layui2.x/layui.js"></script>
<script type="text/javascript" src="../js/index.js"></script>
<!--  课程导航树-->
<script type="text/javascript">



  //声明全局变量
  //选择的章节id
  var courseId = null; 
  //每页显示多少张图片
  var pageLimit = 4;
  //搜索条件开始时间
  var startDate = '';
  //结束时间
  var endDate = '';
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
			renderPhotoData();
		}
		
		//设备分类树初始化
		var zTreeObj = $.fn.zTree.init($("#tree"), setting, []);
  });

  function renderPhotoData(){
	    //将数据清空
		$('#imagesList').html("");
		//分页参数初始化
		initPageParam();
		//重新渲染图片数据
		getPhoto();
  }
  //分页参数初始化
  function initPageParam(){
      //每页显示多少张图片
      pageLimit = 6;
      //搜索条件开始时间
      var startDate = '';
      //结束时间
      var endDate = '';
      $('#startDate').val('');
	  $('#endDate').val('');
  }
    //请求分页数据
    function getPhoto(){
    	
    	layui.use('flow', function(){
    		  var $ = layui.jquery; //不用额外加载jQuery，flow模块本身是有依赖jQuery的，直接用即可。
    		  var flow = layui.flow;
    		  
    		  flow.load({
    			    elem: '#imagesList' //指定列表容器
   			    	//,isAuto: false
   			        ,isLazyimg: true
    			    ,done: function(page, next){ //到达临界点（默认滚动触发），触发下一页
    			      var lis = [];
    			      //以jQuery的Ajax请求为例，请求下一页数据（注意：page是从2开始返回）   			   
    			      $.ajax({
    	    		url:'${pageContext.request.contextPath}/teacher/getHomeworkPhoto',
    	    		method:'post',
    	    		data:{
    	    			'courseId':courseId,
    	    			'page':page,
    	    			'limit':pageLimit,
    	    			'startDate':startDate,
    	    			'endDate':endDate
    	    		},  
    	    		success:function(res){
    					var result = res.data;
    					if(result !=null){
    						var resData = result.data;
    						if(resData!=null){		
    							var preffix = '${pageContext.request.contextPath}/upload_files/homework_photo/'; 			   				    	
      						    layui.each(resData, function(index, item){				
      	    			           lis.push(
					      	    			'<li style="display:inline; margin:10px;">'+ '<img src="'+preffix+item.photoUrl+'" width="150" height="150" style="margin:20px;"/>'
					      	    			+' <button  class="layui-btn layui-btn-sm" onclick="delPhoto('+item.id+')" >'
					       	    			+'   <i class="layui-icon">&#xe640;</i>'
					       	    			+'</button>'      	    
					      	    			+'</li>');
      	    			                    }); 
      						  
      						  //执行下一页渲染，第二参数为：满足“加载更多”的条件，即后面仍有分页
      	    			        //pages为Ajax返回的总页数，只有当前页小于总页数的情况下，才会继续出现加载更多   	    			
      	    			        next(lis.join(''), page < result.count/pageLimit);    
    						}else{
    							next(lis.join(''), page < 0);
    						}			
    					}else{
							next(lis.join(''), page < 0);
						}			
    	    		}
    	    	 });	    
    		   }
    		});  	    
    	}); 		  
    	
    }
    

  //删除照片
  function delPhoto(id){
	  $.post('${pageContext.request.contextPath}/teacher/deleteHomework',
			{'homeworkId':id},
			function(res){
				if(res.data == 1){
					layer.msg('删除成功！', {time: 1000}); //1s后自动关闭			
					renderPhotoData();
				} else{
					layer.msg('删除失败！', {time: 1000}); //1s后自动关闭
				}
			
			}
			);
  }
  
  
  layui.use(['upload','laydate'],function(){
	  var upload = layui.upload;
	  var laydate = layui.laydate;
	  var $ = layui.jquery;
	  //日期插件加载
      laydate.render({ 
         elem: '#startDate' 
      });
      laydate.render({ 
          elem: '#endDate' 
       });
       
      
    //页面刷新
    	$("#btn-refresh").click(function(){ 		
    		renderPhotoData();
  	});
      //搜索
      $('#btn-search').click(function(){
    	  startDate = $('#startDate').val();
    	  endDate = $('#endDate').val();
    	  if(startDate !='' && endDate !=''){
    		  if(startDate<endDate){
    			//将数据清空
    			$('#imagesList').html("");
    			  getPhoto();
    			  startDate='';
    			  endDate='';	
    		  } else{
    			  layer.msg('请输入合法日期范围！', {time: 1000}); //1s后自动关闭
    			  $('#startDate').val('');
    			  $('#endDate').val('');
    		  }
    	  } else{
    		  layer.msg('请输入日期！', {time: 1000}); //1s后自动关闭
    	  }
      });
	  //上传单个照片
	  upload.render({
		  elem:'#btn-add-photo',  //绑定元素
		  url:'${pageContext.request.contextPath}/teacher/uploadHomeworkPhoto',  //请求地址
		  method:'post',  //请求方法
		  accept: 'images', //允许上传的文件类型 图片
		  size: 10000, //最大允许上传的文件大小KB
		  multiple: true,
		  before:function(){
			  if(courseId !="" && courseId !=null){
				  this.data = {'courseId':courseId}; //设置上传参数
			  }
		  },
		  done:function(res){
			  //请求成功的回调函数
			  //"${pageContext.request.contextPath}/upload_files/supplier_photo/"+url
			  var result = res.data;
			  if(result == 2){
				  layer.msg('请选择章节！', {time: 1000}); //1s后自动关闭
			  } else if(result ==0){
				  layer.msg('上传失败！', {time: 1000}); //1s后自动关闭
			  } else{
				  layer.msg('上传成功！', {time: 1000}); //1s后自动关闭
				  renderPhotoData();
				  
			  }
		  },
		  error:function(){
			  layer.msg('服务器错误！', {time: 1000}); //1s后自动关闭
		  }
		  
	  });	  
});

</script>
</body>
</html>