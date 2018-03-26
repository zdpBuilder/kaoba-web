<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>老师题库管理</title>
    <link rel="stylesheet" href="../plugins/layui2.x/css/layui.css">
    <link rel="stylesheet" href="../css/treestyle.css">
	<link rel="stylesheet" href="../plugins/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">    
</head>
<body class="body">

<div class="layui-fluid">  
  <div class="layui-row layui-col-space10">
    <div class="layui-col-md2">
		<fieldset class="layui-elem-field">
         <legend style="font-size:12px;">课程导航树</legend>
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
    
    <div class="layui-col-md10">
		<fieldset id="menu_func_div" class="layui-elem-field">
	         <legend style="font-size:12px;">题库列表</legend>
	         <div class="layui-field-box">
			    <!-- 操作按钮区域 -->
		        <div class="my-btn-box" style="margin-bottom:-10px;">
		            <span class="fl">
		                <a class="layui-btn layui-btn-sm" id="btn-add"><i class="layui-icon"></i>新增</a>
		                <a class="layui-btn layui-btn-sm" id="btn-add-batch"><i class="layui-icon"></i>批量新增</a>
		                <a class="layui-btn layui-btn-sm" id="btn-delete-all"><i class="layui-icon"></i>删除</a>
		                <a class="layui-btn layui-btn-sm" id="btn-refresh" data-type="refresh"><i class="layui-icon">&#x1002;</i>刷新</a>
		            </span>
		            <div class="fr">
		                <span class="layui-form-label" style="font-size: 12px;line-height:12px;">搜索条件</span>
		                <div class="layui-input-inline">
		                    <input type="text" autocomplete="off" id="keywords" name="keywords" title="支持模糊查询" placeholder="题目名称 " class="layui-input" style="width:150px;height:26px;font-size:12px;"/>
		                </div>
		                <button class="layui-btn layui-btn-sm" id="btn-search" style="font-size: 10px;"><i class="layui-icon" style="font-size: 16px;">&#xe615;</i>&nbsp;查询</button>
		            </div>
		        </div>
		        <!-- 表格内容区域 -->
			    <div class="layui-col-md12 layui-col-space1">
					<table class="layui-hide" id="layTable" lay-filter="tableFilter"></table>
			    </div>
			  </div>
		</fieldset>
    </div>
  </div>
</div>

<script type="text/javascript" src="../plugins/ztree/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="../plugins/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="../plugins/layui2.x/layui.js"></script>
<script src="../plugins/layui2.x/layer/layer.min.js" type="text/javascript"></script>
<script type="text/javascript">
var table;
var currPageNum = 1;//当前页码
var leftNodeId = 1;//左侧选中的节点id

function reloadTable(currPageNum){
	//刷新表格内容
	table.reload('equipmentTypeTable', {
	  page: {
		  curr: currPageNum //从当前页开始
	  }
	  ,where: {
		//传参
	    pid: leftNodeId ,
	    keywords: $("#keywords").val()
	  }
	});
}
    // layui方法
    layui.use(['table', 'layer'], function () {

        // 操作对象
        table = layui.table;
        var layer = layui.layer;
        var $ = layui.jquery;
        
        // 表格渲染
        table.render({
		     elem: '#layTable'
		    ,cellMinWidth: 80//自适应列宽
		    ,cols: [[ 
		       //{type:'numbers' ,title: '序号'},
		       {type: 'checkbox'}
		       
		     // ,{field: 'deafultPhotoUrl', title: '默认图片',align: 'center', templet: '#setDefaultPhoto'}
		      ,{field: 'singledbTitle', title: '<span style="color:#000;font-weight:bold;">题目</span>',align: 'center'}
		      ,{field: 'singledbOptiona', title: '<span style="color:#000;font-weight:bold;">选项A</span>',align: 'center'}
		      ,{field: 'singledbOptionb', title: '<span style="color:#000;font-weight:bold;">选项B</span>',align: 'center'}
		      ,{field: 'singledbOptionc', title: '<span style="color:#000;font-weight:bold;">选项C</span>',align: 'center'}
		      ,{field: 'singledbOptiond', title: '<span style="color:#000;font-weight:bold;">选项D</span>',align: 'center'}
		      ,{field: '', title: '<span style="color:#000;font-weight:bold;">操作</span>',align: 'center', width:200 ,toolbar: '#toolbar'}
		    ]]
        	,url:'${pageContext.request.contextPath}/courseVsSingleDb/list'
        	,id: 'equipmentTypeTable'
        	,where: { pid:1 }//查询传参
		    //,skin: 'line' //表格风格
		    ,even: true  //隔行换色
		    ,size: 'sm' //小尺寸的表格
		    ,page: true  //开启分页
		    ,done: function(res, curr, count){
		        //如果是异步请求数据方式，res即为你接口返回的信息。
		        //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
		        //console.log(res);
		        //得到当前页码
		        //console.log(curr);
		        currPageNum = curr;
		        //得到数据总量
		       // console.log(count);
		        
		      }
		  });
        
      	//监听表格复选框选择
        table.on('checkbox(tableFilter)', function(obj){
          //console.log(obj);
        });
        
      	//添加
      	$("#btn-add").click(function(){
      		layer.open({
      		  type: 2 //Page层类型
      		  ,area: ['680px', '388px']
      		  ,title:  ['新增题库', 'font-size:13px;font-weight:bold;height:38px;color:#000;background-color:#E5E5E5;']
      		  ,shade: 0.6 //遮罩透明度
      		  ,fixed: true //位置固定
      		  ,maxmin: false //开启最大化最小化按钮
      		  ,anim: 5 //0-6的动画形式，-1不开启
      		  ,content: 'addEquipmentType.jsp?pid='+leftNodeId
      		});
      		
      	});
      	//批量删除
		$("#btn-delete-all").click(function(){
			
			var checkStatus = table.checkStatus('equipmentTypeTable')
		    var data = checkStatus.data;//选中数据
		    //layer.alert(JSON.stringify(data));
		    
		    if(data.length>0){
		    	var idStr = "";
		    	for(var i=0;i<data.length;i++){
		    		idStr = data[i].id + "," + idStr;
		    	}
		    	layer.confirm('确认删除 '+data.length+' 条设备分类信息？', {
	      	    	  title: "确认消息", //标题
	      	    	  btnAlign: 'c',
	      	    	  btn: ['确认','取消'] //按钮
	      	    	}, function(){
	      	    		//单条删除
	      	    		$.ajax({
	        	  			method: "post",
	        	  			url:"${pageContext.request.contextPath}/courseVsSingleDb/deleteBatch",
	        	  			data:{"eeIds":idStr},
	        	  			success:function(result){
	        	  				if(result.data==1){
	        		  				layer.msg('删除 '+data.length+' 条成功！', {time: 1000}); //1s后自动关闭
	      	    					
	        		  				//刷新表格内容
	        		  		        table.reload('equipmentTypeTable', {
	        		  		          page: {
	        		  		            curr: currPageNum //从当前页开始
	        		  		          }
	        		  		          ,where: {
	        		  		        	//传参
	        		  		        	pid: leftNodeId,
	        		  		            keywords: $("#keywords").val()
	        		  		          }
	        		  		        });
	        		  				
	        	  				}else{
	        	  					layer.msg('删除失败！', {time: 1000}); //1s后自动关闭
	        	  				}
	        	  	        }
	        			});
	      	    	   
	      	    	}, function(){
	      	    	  //取消
	      	    	});
		    }else{	    	
		    	layer.msg('至少选中一条数据！', {time: 1000});  //1s后自动关闭
		    }
			
      	});
      	
      	
		//页面刷新
      	$("#btn-refresh").click(function(){
      		//清空页面刷新条件
      		$("#keywords").val("");
      		//页面刷新
      		table.reload('equipmentTypeTable', {
      		  page: {
      		    curr: 1 //重新从第 1 页开始
      		  }
      		  ,where: {
      			//查询传参
      		   keywords: ""
      		  }
      		});
    	});
		
      //批量添加 
      	$("#btn-add-batch").click(function(){
      		layer.open({
      		  type: 2 //Page层类型
      		  ,area: ['200px', '100px']
      		  ,title:  ['批量新增试题', '']
      		  ,shade: 0.6 //遮罩透明度
      		  ,fixed: true //位置固定
      		  ,maxmin: false //开启最大化最小化按钮
      		  ,anim: 5 //0-6的动画形式，-1不开启
      		  ,content: 'uploadDBExcel.jsp?pid='+leftNodeId
      	   });
      	});
      	
      	//多条件查询
      	$("#btn-search").click(function(){
      		//alert($("#keywords").val());
      		//表格查询
      		table.reload('equipmentTypeTable', {
      		  page: {
      		    curr: 1 //重新从第 1 页开始
      		  }
      		  ,where: {
      			//传参
      		   pid: 1,
      		   keywords: $("#keywords").val()
      		  }
      		});
      	});
      	
      	
      	//toolBar操作列监听
      	 table.on('tool(tableFilter)', function(obj){
      		var data = obj.data;
      	    if(obj.event === 'del'){
      	      //layer.msg('ID：'+ data.id + ' 的删除操作');
      	    	layer.confirm('确认删除此设备分类信息？', {
      	    	  title: "确认消息", //标题
      	    	  btnAlign: 'c',
      	    	  btn: ['确认','取消'] //按钮
      	    	}, function(){
      	    		//单条删除
      	    		//console.info(table);
      	    		$.ajax({
        	  			method: "post",
        	  			url:"${pageContext.request.contextPath}/courseVsSingleDb/deleteBatch",
        	  			data:{"eeIds":data.id},
        	  			success:function(result){
        	  				if(result.data==1){
        	  					layer.msg('删除成功！', {time: 1000}); //1s后自动关闭
      	    					//console.info(obj);
        		  				//$(obj.tr).fadeOut();
        		  				//刷新表格内容
        		  		        table.reload('equipmentTypeTable', {
        		  		          page: {
        		  		        	curr: currPageNum //从当前页开始
        		  		          }
        		  		          ,where: {
        		  		        	//传参
        		  		        	pid: leftNodeId,
        		  		        	keywords: $("#keywords").val()
        		  		          }
        		  		        });
        		  				
        	  				}else{
        	  					layer.msg('删除失败！', {time: 1000}); //1s后自动关闭
        	  				}
        	  	        }
        			});
      	    	  
      	    	}, function(){
      	    	  //取消
      	    	});
      	      
      	    }
      	  	if(obj.event === 'edit'){
      	      //编辑操作
      	  	  //layer.msg('ID：'+ data.id + ' 的编辑操作');
      	      layer.open({
        		  type: 2 //Page层类型
        		  ,area: ['680px', '388px']
        		  ,title:  ['编辑设备分类', '']
        		  ,shade: 0.6 //遮罩透明度
        		  ,fixed: true //位置固定
        		  ,maxmin: false //开启最大化最小化按钮
        		  ,anim: 5 //0-6的动画形式，-1不开启
        		  ,content: 'editEquipmentType.jsp?id='+data.id
        	   });
      	      
      	    }
      	  if(obj.event === 'query'){
      	      //编辑操作
      	  	  //layer.msg('ID：'+ data.id + ' 的查看操作');
      	      layer.open({
        		  type: 2 //Page层类型
        		  ,area: ['680px', '388px']
        		  ,title:  ['编辑设备分类', '']
        		  ,shade: 0.6 //遮罩透明度
        		  ,fixed: true //位置固定
        		  ,maxmin: false //开启最大化最小化按钮
        		  ,anim: 5 //0-6的动画形式，-1不开启
        		  ,content: 'showAnswer.jsp?id='+data.id
        	   });
      	      
      	    }
      	 });
      	
      	
      	
    });
</script>
<script type="text/html" id="setDefaultPhoto">
  <!-- 自定义默认图片列列 -->
  <img src="${pageContext.request.contextPath}/img/ee.jpg" style="width:80px;" />
</script>
<script type="text/html" id="toolbar">
	<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="query"><i class="layui-icon">&#xe642;</i>查看</a>
	<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit"><i class="layui-icon">&#xe642;</i>编辑</a>
	<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="del"><i class="layui-icon">&#xe640;</i>删除</a>
</script>

<script type="text/javascript">
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
			showRemoveBtn: showRemoveBtn
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
			url : "${pageContext.request.contextPath}/courseVsSingleDb/treeList",
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
	
	/*ZTree树节点相关操作的回调函数 */
	
	//节点删除前判断
	
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
			return confirm("确认删除设备分类 -- " + treeNode.name + " 吗？");
		}
	}
	
	//删除节点
	function zTreeOnRemove(event, treeId, treeNode) {
		$.post("${pageContext.request.contextPath}/courseVsSingleDb/deleteTreeNode", { "id": treeNode.id },
		   		function(data){
		     		if(data.data=="ok"){
		    	 		layer.msg('删除成功！', {icon: 1,time: 1000});
		    	 		//异步刷新
		    	 		//zTreeObj.reAsyncChildNodes(null, "refresh");
		     	}
			});
	}
	
	//修改节点名称
	function zTreeOnRename(event, treeId, treeNode, isCancel) {
		if(!isCancel){
			$.post("${pageContext.request.contextPath}/courseVsSingleDb/editTreeNode", { "id": treeNode.id,"nodeName":treeNode.name },
					   function(data){
					     if(data.data=="ok"){
					    	 //异步刷新
					    	 //zTreeObj.reAsyncChildNodes(null, "refresh");
					     }
			});
		}
	}
	
	//树节点点击事件
	function zTreeOnClick(event, treeId, treeNode) {
		//alert(treeNode.id);
		//$("#nodeName").val(treeNode.name);
		//刷新表格内容
		leftNodeId = treeNode.id;
        table.reload('equipmentTypeTable', {
          page: {
            curr: 1 //重新从第 1 页开始
          }
          ,where: {
        	//传参
            pid: leftNodeId,
	        keywords: $("#keywords").val()
          }
        });
	}
	
	//设备分类树初始化
	var zTreeObj = $.fn.zTree.init($("#tree"), setting, []);
	
	//添加节点
	$("#addNode").click(function(){
		var nodes = zTreeObj.getSelectedNodes();
		var typeId = "0";//被增加节点的父pId
		if(nodes[0]){
			typeId = nodes[0].id;
		}
		var nodeNameToAdd = $("#nodeName").val();
		if($.trim(nodeNameToAdd)==""){
			$("#nodeName").focus();
			layer.tips('设备分类名称不能为空！', '#nodeName', {
				  tips: [1, '#F90'], //还可配置颜色
				  time: 1000
			});
			return false;
		}
		$.post("${pageContext.request.contextPath}/courseVsSingleDb/addTreeNode", { "pid": typeId, "nodeName": nodeNameToAdd },
			   function(data){
			     if(data!=""){
			    	 var record = data.data;
			    	 //增加新增的节点
			    	 //console.info(record.id);
			    	 var newAddNode = [{"id":record.id, "pid":record.pid,"name":nodeNameToAdd}];
					 zTreeObj.addNodes(nodes[0], newAddNode, true);
			    	 
			    	 //异步刷新后选中当前选择节点
			    	 //zTreeObj.reAsyncChildNodes(null, "refresh");
					 $("#nodeName").val("");
					 $("#nodeName").focus();
			     }
		});
	});
	
/*
	var zNodes =[
		{ id:1, pid:0, name:"围控设备",isParent:true},
		{ id:11, pid:1, name:"充气式围油栏"},
		{ id:12, pid:1, name:"快速布防式围油栏"},
		{ id:13, pid:1, name:"固体浮子式围油栏"},
		{ id:14, pid:1, name:"岸滩围油栏"},
		{ id:15, pid:1, name:"防火围油栏"},
		
		{ id:2, pid:0, name:"回收设备",isParent:true},
		{ id:21, pid:2, name:"刷式收油机"},
		{ id:22, pid:2, name:"盘式收油机"},
		{ id:23, pid:2, name:"筒式收油机"},
		{ id:24, pid:2, name:"多功能收油机"},
		{ id:25, pid:2, name:"动态斜面收油机"},
		{ id:26, pid:2, name:"堰式收油机"},
		{ id:27, pid:2, name:"蠕动式/真空式收油机"},
		{ id:28, pid:2, name:"油水分离器(含输油泵)"},
		{ id:29, pid:2, name:"收油网/油拖网"},
		{ id:210, pid:2, name:"绳式收油机"},
		{ id:211, pid:2, name:"臂式收油机"},
		{ id:212, pid:2, name:"自航式收油机"},
		
		{ id:3, pid:0, name:"卸载设备",isParent:true},
		{ id:31, pid:3, name:"刷式收油机"},
		{ id:32, pid:3, name:"盘式收油机"},
		{ id:33, pid:3, name:"筒式收油机"},
		{ id:34, pid:3, name:"多功能收油机"},
		
		{ id:4, pid:0, name:"消油剂喷洒装置" ,isParent:true},
		{ id:41, pid:4, name:"便携式喷洒装置"},
		{ id:42, pid:4, name:"空中喷洒装置"},
		{ id:43, pid:4, name:"船用喷洒装置"},
		

		{ id:5, pid:0, name:"存储装置",isParent:true},
		{ id:51, pid:5, name:"便携式储油罐"},
		{ id:52, pid:5, name:"轻便储油罐"},
		{ id:53, pid:5, name:"浮动油囊"},
		{ id:54, pid:5, name:"自撑式储油罐"},
		
		
		{ id:6, pid:0, name:"清洗装置",isParent:true},
		{ id:61, pid:6, name:"常温清洗机"},
		{ id:62, pid:6, name:"高温清洗机"},
		{ id:63, pid:6, name:"岸滩清洗机"},
		
		
		{ id:7, pid:0, name:"消油剂(溢油分散剂)",isParent:true},
		{ id:71, pid:7, name:"生物降解性消油剂"},
		{ id:72, pid:7, name:"化学消油剂"},
		
		
		{ id:8, pid:0, name:"吸附吸收材料",isParent:true},
		{ id:81, pid:8, name:"吸油毡"},
		{ id:82, pid:8, name:"吸油拖栏"},
		{ id:83, pid:8, name:"化学品吸附材料"},
		
		
		{ id:9, pid:0, name:"防护装备",isParent:true},
		{ id:91, pid:9, name:"头部防护"},
		{ id:92, pid:9, name:"眼部防护"},
		{ id:93, pid:9, name:"听力防护"},
		{ id:94, pid:9, name:"呼吸防护"},
		{ id:95, pid:9, name:"躯干防护"},
		{ id:96, pid:9, name:"手部防护"},
		{ id:97, pid:9, name:"足部防护"},
		{ id:98, pid:9, name:"坠落防护"},
		
		{ id:10, pid:0, name:"运输车辆和起吊装置",isParent:true},
		{ id:101, pid:10, name:"叉车"},
		{ id:102, pid:10, name:"拖车"},
		{ id:103, pid:10, name:"卡车"},
		{ id:104, pid:10, name:"汽车吊"},
		{ id:105, pid:11, name:"其他车辆"},
		
		{ id:11, pid:0, name:"其他装置",isParent:true}
		
	];
*/

});

</script>
</body>
</html>