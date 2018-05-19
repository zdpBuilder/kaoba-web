<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>查看作业</title>
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
         		</div>
         	</div>
		  </div>
		  </fieldset>
    </div>
    
    <div class="layui-col-md10">
		<fieldset id="menu_func_div" class="layui-elem-field">
	         <legend style="font-size:12px;">学生列表</legend>
	         <div class="layui-field-box">
			    <!-- 操作按钮区域 -->
		        <div class="my-btn-box" style="margin-bottom:-10px;">
		            <span class="fl">
		                <a class="layui-btn layui-btn-sm" id="btn-refresh" data-type="refresh"><i class="layui-icon">&#x1002;</i>刷新</a>
		            </span>
		             <div class="fr">        
		                <button class="layui-btn layui-btn-sm" id="btn-export" style="font-size: 10px;"><i class="layui-icon" style="font-size: 16px;"></i>&nbsp;成绩导出</button>
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
var leftNodeId = 0;//左侧选中的节点id
var pId =-1;

function reloadTable(currPageNum){
	//刷新表格内容
	table.reload('equipmentTypeTable', {
	  page: {
		  curr: currPageNum //从当前页开始
	  }
	  ,where: {
		//传参
	    homeworkId: leftNodeId,
	  }
	});
}
    // layui方法
    layui.use(['table', 'layer','laydate'], function () {

        // 操作对象
        table = layui.table;
        var layer = layui.layer;
        var $ = layui.jquery;
        var laydate = layui.laydate;
        
        //日期插件加载
        laydate.render({ 
           elem: '#startDate' 
        });
        laydate.render({ 
            elem: '#endDate' 
         });
         
        // 表格渲染
        table.render({
		     elem: '#layTable'
		    ,cellMinWidth: 80//自适应列宽
		    ,cols: [[ 	           
		      {field: 'loginId', title: '<span style="color:#000;font-weight:bold;">学生学号</span>',align: 'center'}
		      ,{field: 'stuName', title: '<span style="color:#000;font-weight:bold;">学生姓名</span>',align: 'center'}
		      ,{field: 'stuGrade', title: '<span style="color:#000;font-weight:bold;">学生成绩</span>',align: 'center'}		 
		      ,{field: '', title: '<span style="color:#000;font-weight:bold;">操作</span>',align: 'center', width:200 ,toolbar: '#toolbar'}
		    ]]
        	,url:'${pageContext.request.contextPath}/homework/getStuList'
        	,id: 'equipmentTypeTable'
        	,where: { 
        		homeworkId:1 
        		}//查询传参
		    //,skin: 'line' //表格风格
		    ,even: true  //隔行换色
		    ,size: 'sm' //小尺寸的表格
		    ,page: true  //开启分页
		    ,done: function(res, curr, count){		    
		        currPageNum = curr;
			        
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
		
     
      	 //成绩导出
      	$("#btn-export").click(function(){
      		if(leftNodeId>0){
      			//验证是否选中子节点
      			var checkFalg=0;
      		$.ajax({
    			method: "post",
    			url:"../courseVsSingleDb/teaCouTreeList",
    			async:false,
    			success:function(result){				
                	if(result.length>0){
                		for(var i=0; i<result.length;i++){
                				if(result[i].id==leftNodeId){
                					checkFalg=1;
                				}
                			}
                		} 
                	} ,             
            }); 
      		if(checkFalg==1){
      		  layer.msg('请选择要导出的作业！', {time: 1000}); //1s后自动关闭
      		  return;
      		}
    		location.href ="${pageContext.request.contextPath}/homework/export?homeworkId="+leftNodeId;		
      	  }else{
    		  layer.msg('请选择要导出的作业！', {time: 1000}); //1s后自动关闭
      	  }
      	});
      	 
      	//toolBar操作列监听
      	 table.on('tool(tableFilter)', function(obj){
      		var data = obj.data;
      		
      	  	if(obj.event === 'edit'){
      	      //编辑操作
      	  	  //layer.msg('ID：'+ data.id + ' 的编辑操作');
      	      layer.open({
        		  type: 2 //Page层类型
        		  ,area: ['100%', '100%']
        		  ,title:  ['批改作业', '']
        		  ,shade: 0.6 //遮罩透明度
        		  ,fixed: true //位置固定
        		  ,maxmin: false //开启最大化最小化按钮 
        		  ,anim: 5 //0-6的动画形式，-1不开启
        		  ,content: 'editStuHomework.jsp?homeworkId='+data.homeworkId+'&stuId='+data.stuId+'&stuGrade='+data.stuGrade
        	   });
      	         
      	    }
      	 });
      	
      	
      	
    });
</script>
<script type="text/html" id="toolbar">
	<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit"><i class="layui-icon">&#xe642;</i>批改作业</a>
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
			enable: false,
			editNameSelectAll: false,
		},
		callback:{
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
	
	//树节点点击事件
	function zTreeOnClick(event, treeId, treeNode) {
		//alert(treeNode.id);
		//$("#nodeName").val(treeNode.name);
		//刷新表格内容
		leftNodeId = treeNode.id;
		pId = treeNode.pid;
        table.reload('equipmentTypeTable', {
          page: {
            curr: 1 //重新从第 1 页开始
          }
          ,where: {
        	//传参
            homeworkId: leftNodeId,     
          }
        });
	}	
	//设备分类树初始化
	var zTreeObj = $.fn.zTree.init($("#tree"), setting, []);
});

</script>
</body>
</html>