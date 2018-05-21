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
								  <div style="margin:10px">
						            <input type="text" id="nodeName" name="nodeName" maxlength="30" style="font-size:12px;width:120px;height:22px;border:1px solid #e2e2e2" />
						            <button title="添加节点" id="addNode" class="layui-btn layui-btn-xs layui-btn-normal">添加</button>
					              </div>
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

										<!-- <div class="layui-input-inline">
											<label style="height: 26px; font-size: 15px;">上传日期</label> <input
												readonly="readonly" name="startDate" id="startDate"
												style="height: 22px; font-size: 12px;" /> <input
												readonly="readonly" name="endDate" id="endDate"
												style="height: 22px; font-size: 12px;" />
										</div>
										<button class="layui-btn layui-btn-sm" id="btn-search"
											style="vertical-align: top; font-size: 10px;">
											<i class="layui-icon" style="font-size: 14px;">&#xe615;</i>&nbsp;查询
										</button> -->
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
  //选择作业的id
  var homeworkId = null;
  //选择作业的课程id
  var courseId = null;
  //作业照片数组
  var photoUrls =null;
/*   //每页显示多少张图片
  var pageLimit = 4;
  //搜索条件开始时间
  var startDate = '';
  //结束时间
  var endDate = ''; */
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
			edit:{
				enable: true,
				editNameSelectAll: true,
				showRenameBtn: showRenameBtn,
				renameTitle: "编辑分类",
				removeTitle: "删除分类",
				showRemoveBtn: showRemoveBtn,
			},
			callback:{
				beforeRemove: zTreeBeforeRemove,
				onRemove: zTreeOnRemove,
				onRename: zTreeOnRename,
				onClick: zTreeOnClick
			},
			async:{
				enable: true,
				type: "post",
				url : "${pageContext.request.contextPath}/homework/homeworkTreeList",
	            autoParam : [ "id" ],
				dataFilter: ajaxDataFilter
			}
			,
			view: {
				fontCss : {color:"#000"}
			}
		}; 
	
		//是否显示编辑按钮
		function showRenameBtn(treeId, treeNode){
			//根节点不允许编辑
			if(treeNode.id=="1"){
				return false;
			}else{
				return true;
			}
		}
		//是否显示删除按钮
		function showRemoveBtn(treeId, treeNode){
			//根节点不允许删除
			if(treeNode.id=="0"){
				return false;
			}else{
				return true;
			}
		} 
	   
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
		
		//删除前判断
		function zTreeBeforeRemove(treeId, treeNode) {
			
			var isP = treeNode.isParent;
			if(isP){
				//alert(treeNode.tId);
				layer.tips('不能删除父节点！', '#tree', {
					  tips: [1, '#F90'],
					  time: 1000
					});
				return false;
			}else{
				return confirm("确认删除作业 -- " + treeNode.name + " 吗？");
			}
		}
		
		//删除节点
		function zTreeOnRemove(event, treeId, treeNode) {
			$.post("${pageContext.request.contextPath}/homework/deleteHomeworkNode", { "id": treeNode.id },
			   		function(data){
				      if(data !=null){
				    	  if(data.data=="ok"){
				    	 		layer.msg('删除成功！', {icon: 1,time: 1000});
				    	 		//异步刷新
				    	 		//zTreeObj.reAsyncChildNodes(null, "refresh");
				     	}
				      }
			     		
				});
		}
		
		//修改节点名称
		function zTreeOnRename(event, treeId, treeNode, isCancel) {
			if(!isCancel){
				$.post("${pageContext.request.contextPath}/homework/editHomeworkNode", { "id": treeNode.id,"name":treeNode.name },
						   function(data){
					       if(data !=null){
					    	   if(data.data=="ok"){
							    	 //异步刷新
							    	 //zTreeObj.reAsyncChildNodes(null, "refresh");
							     }
					       }
						     
				});
			}
		}
		
		
		//树节点点击事件
		function zTreeOnClick(event, treeId, treeNode) {
			
			homeworkId = treeNode.id;
			courseId = treeNode.pid;
			renderPhotoData();
		}
		
		//设备分类树初始化
		var zTreeObj = $.fn.zTree.init($("#tree"), setting, []);
		
		//添加节点
		$("#addNode").click(function(){
			var nodes = zTreeObj.getSelectedNodes();
			var courseId = "0";//被增加节点的父pId
			
			var nodeNameToAdd = $("#nodeName").val();
			if($.trim(nodeNameToAdd)==""){
				$("#nodeName").focus();
				layer.tips('作业名称不能为空！', '#nodeName', {
					  tips: [1, '#F90'], //还可配置颜色
					  time: 1000
				});
				return false;
			}
			
			if(nodes[0]){
				if(nodes[0].pid == "0"){
					courseId = nodes[0].id;
				}else{
					layer.msg('请正确选择科目！', {time: 1000}); //1s后自动关闭
					return;
				}
				
			} else{
				layer.msg('请正确选择科目！', {time: 1000}); //1s后自动关闭
				return;
			}
			
			$.post("${pageContext.request.contextPath}/homework/addHomeworkNode", { "courseId": courseId, "name": nodeNameToAdd },
				   function(data){
				     if(data!=""){
				    	 var record = data.data;
				    	 //增加新增的节点
				    	 var newAddNode = [{"id":record.id, "pid":record.pid,"name":nodeNameToAdd}];
						 zTreeObj.addNodes(nodes[0], newAddNode, true);
				    	 //异步刷新后选中当前选择节点
				    	 //zTreeObj.reAsyncChildNodes(null, "refresh");
						 $("#nodeName").val("");
						 $("#nodeName").focus();
				     }
			});
		});
  });

  function renderPhotoData(){
	    //将数据清空
		$('#imagesList').html("");
		//分页参数初始化
		//initPageParam();
		//重新渲染图片数据
		getPhoto();
  }
  //分页参数初始化
 /*  function initPageParam(){
      //每页显示多少张图片
      pageLimit = 6;
      //搜索条件开始时间
      var startDate = '';
      //结束时间
      var endDate = '';
      $('#startDate').val('');
	  $('#endDate').val('');
  } */
  
  //请求照片数据
  function getPhoto(){
	  $.ajax({
  		url:'${pageContext.request.contextPath}/homework/getHomeworkPhoto',
  		method:'post',
  		data:{
  			'homeworkId':homeworkId
  		},
  		success:function(res){
  			var result = res.data;
  			if(result !=null){
  			    photoUrls = result.photoUrl.split(",");
  				var photoUrl="";
  				var htmlString="";
  				$('#imagesList').html("");
  				var preffix = '${pageContext.request.contextPath}/upload_files/homework_photo/'; 
  				for(var i=0;i<photoUrls.length;i++){
  					photoUrl = photoUrls[i];
  					htmlString='<li style="display:inline; margin:10px;">'
  					+ '<img src="'+preffix+photoUrls[i]+'" width="150" height="150" style="margin:20px;"/>'
  	    			+' <button  class="layui-btn layui-btn-sm" onclick="delPhoto('+i+','+result.id+')" >'
   	    			+'   <i class="layui-icon">&#xe640;</i>'
   	    			+'</button>'      	    
  	    			+'</li>';
  	    			$('#imagesList').append(htmlString);
  				}
  			}
  		}
	  });
  }
  
    //请求分页数据
    /* function getPhotoFlow(){
    	
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
    	    		url:'${pageContext.request.contextPath}/homework/getHomeworkPhoto',
    	    		method:'post',
    	    		data:{
    	    			'homeworkId':homeworkId,
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
    							var photoUrls = resData[0].photoUrl.split(",");
    							for(var i=0;i<photoUrls.length;i++){
    								lis.push(
					      	    			'<li style="display:inline; margin:10px;">'+ '<img src="'+preffix+photoUrls[i]+'" width="150" height="150" style="margin:20px;"/>'
					      	    			+' <button  class="layui-btn layui-btn-sm" onclick="delPhoto('+photoUrls[i]+','+resData[0].id+')" >'
					       	    			+'   <i class="layui-icon">&#xe640;</i>'
					       	    			+'</button>'      	    
					      	    			+'</li>');
    							}
      						  
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
    	
    } */
    

  //删除照片
  function delPhoto(photoUrlsIndex,id){
      var photoUrl = photoUrls[photoUrlsIndex];
	  $.post('${pageContext.request.contextPath}/homework/deleteHomeworkPhoto',
			{'homeworkId':id,'photoUrl':photoUrl},
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
		  url:'${pageContext.request.contextPath}/homework/uploadHomeworkPhoto',  //请求地址
		  method:'post',  //请求方法
		  accept: 'images', //允许上传的文件类型 图片
		  size: 10000, //最大允许上传的文件大小KB
		  multiple: true,
		  before:function(){
			  if(homeworkId !="" && homeworkId !=null){
				  this.data = {'homeworkId':homeworkId}; //设置上传参数
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