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
		            
		             <div  class="layui-input-inline">
                       <label style="height:26px;font-size:15px;">上传日期</label>
                       <input readonly="readonly" name="startDate" id="startDate" style="height:22px;font-size:12px;" />
                       <input readonly="readonly" name="endDate" id="endDate"  style="height:22px;font-size:12px;" />
		              </div>
		              <button class="layui-btn layui-btn-sm" id="btn-search" style="vertical-align:top;font-size: 10px;"><i class="layui-icon" style="font-size: 14px;">&#xe615;</i>&nbsp;查询</button>
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
  //声明全局变量
  //选择的章节id
  var courseId = null;
  //从后台获取的图片数据
  var pagePhoto = null;
  //本章节下，所有作业的总数
  var pageCount = 0;
  //当前显示的页码
  var pageCurr = 1;
  //当前页面的图片总数
  var pageSize = -1;
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
			//将数据清空
			$('#homeworkPhoto').html("");
			//分页参数初始化
			initPageParam();
			//重新渲染图片数据
			reloadPhoto();
		}
		
		//设备分类树初始化
		var zTreeObj = $.fn.zTree.init($("#tree"), setting, []);
  });

  //分页参数初始化
  function initPageParam(){
	  
	  pagePhoto = null;
      //本章节下，所有作业的总数
      pageCount = 0;
      //当前显示的页码
      pageCurr = 1;
      //当前页面的图片总数
      pageSize = -1;
      //每页显示多少张图片
      pageLimit = 4;
      //搜索条件开始时间
      var startDate = '';
      //结束时间
      var endDate = '';
      $('#startDate').val('');
	  $('#endDate').val('');
  }
  //重新渲染图片数据
	function reloadPhoto(){
	  	//得到照片信息
	  	getPhoto();
	  	//分页
	  	pageList();
	}
	
    //请求分页数据
    function getPhoto(){
    	//删除图片后，页码的变化
    	if(pageSize == 0){
    		if(pageCurr !=1){
    			pageCurr--;
    		} 
    		//如果第一页的数据被删空，说明此章节下午作业，pagePhoto置空
    		if(pageCurr == 1){
    			pagePhoto = null;
    			$('#homeworkPhoto').html("暂无数据");
    			return;
    		}
    	}
    	//向后台请求数据
    	$.ajax({
    		url:'${pageContext.request.contextPath}/teacher/getHomeworkPhoto',
    		method:'post',
    		data:{
    			'courseId':courseId,
    			'page':pageCurr,
    			'limit':pageLimit,
    			'startDate':startDate,
    			'endDate':endDate
    		},
    		async:false,
    		success:function(res){
				var result = res.data;
				if(result !=null){
					//给分页数据总量赋值
					pageCount = res.data.count;
					pagePhoto = result.data;
					pageSize = pagePhoto.length;
					console.info(pagePhoto);
				}
    		}
    	});
    }
    
    //生成分页数据
    function renderPhoto(){
    	var preffix = '${pageContext.request.contextPath}/upload_files/homework_photo/';
		var string ='';
		var image= '';
		var photoUrl='';
		var operationButton = '';
		var j =1;
		if(pagePhoto !=null){
			for(var i=0;i<pagePhoto.length;i++){
				
				if(i %2 ==0){
					string = '<div id="photo'+j+'" style="margin-top:20px"></div>';
					$('#homeworkPhoto').append(string);
					j++;
				}
				operationButton ='<div style="display:inline">'
	                +'<span><a onclick="delPhoto('+pagePhoto[i].id+')" class="layui-btn style="font-size:10px;"><i class="layui-icon">&#xe640;</i>删除</a></span>'
	                +'</div>';
				photoUrl = preffix + pagePhoto[i].photoUrl;
				string ='<div style="margin-right:10px;display:inline">';
				image = '<img width="40%" height="35%" src='+photoUrl+' />';
				string = string + image + operationButton +'</div>';
				$('#photo'+(j-1)).append(string);
			}
		} 
		
    }
    
  //分页
  function pageList(){
	  layui.use(['laypage', 'layer'], function(){
		  var laypage = layui.laypage
		  ,layer = layui.layer;
		  if(pagePhoto !=null){
			//调用layui分页
			  laypage.render({
			    elem: 'homeworkPhoto' //绑定元素
			    ,count: pageCount //数据总数
			    ,limit:pageLimit  //每页数据
			    ,curr:pageCurr  //当前页码
			    ,layout: ['count', 'prev', 'page', 'next', 'limit', 'skip'] //使用的模板
			    ,limits:[4,8,16]  //设置每页显示的条数
			    ,pre:'上一页'  //设置上一页按钮
			    ,next:'下一页' //设置下一页按钮
			    ,jump: function(obj,first){
			    	//如果不是初始渲染数据，则需向后台请求分页数据
			    	if(!first){
			    		pageCurr = obj.curr;
			    		pageLimit = obj.limit;
			    		getPhoto();
			    	}
			    	//生成数据
			    	renderPhoto();
			    }
			  });
		  }
		  
	  });
  }
     
  //删除照片
  function delPhoto(id){
	  $.post('${pageContext.request.contextPath}/teacher/deleteHomework',
			{'homeworkId':id},
			function(res){
				if(res.data == 1){
					layer.msg('删除成功！', {time: 1000}); //1s后自动关闭
					pageSize--;
					reloadPhoto();
				} else{
					layer.msg('删除失败！', {time: 1000}); //1s后自动关闭
				}
			
			}
			);
  }
  
  //上传图片
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
      
      //搜索
      $('#btn-search').click(function(){
    	  startDate = $('#startDate').val();
    	  endDate = $('#endDate').val();
    	  if(startDate !='' && endDate !=''){
    		  if(startDate<endDate){
    			  $('#homeworkPhoto').html("");
    			  pagePhoto = null;
    			  reloadPhoto();
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
				  //如果此时的pageSize为0，当上传一个之后，应该初始化pageSize=-1
				  if(pageSize == 0){
					  pageSize = -1;
				  }
				  //重新渲染
				  reloadPhoto();
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