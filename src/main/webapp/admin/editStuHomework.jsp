<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
//课程id
String courseId= request.getParameter("courseId");
//学生id
String stuId= request.getParameter("stuId");
String stuGrade=request.getParameter("stuGrade");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>批改作业</title>
    <link rel="stylesheet" href="../plugins/layui2.x/css/layui.css"  media="all">
    <link rel="stylesheet" href="../css/treestyle.css">
	<link rel="stylesheet" href="../plugins/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">    
</head>
<body class="body">
	<div class="layui-fluid">
		<div class="layui-row layui-col-space10">
			<div class=" layui-col-md10"  style="margin-left:-5px;" >
			<fieldset id="menu_func_div" class="layui-elem-field">
								<legend style="font-size: 12px;">作业</legend>
								<div class="layui-field-box">
									<!-- 操作按钮区域 -->
									<div class="my-btn-box" style="margin-bottom: -10px; text-align:center;">									
										<div class="layui-input-inline">
											<label style="height: 26px; font-size: 15px;">上传日期</label> 
											<input
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
			<div class="layui-col-md2" >
				<fieldset class="layui-elem-field">
					<legend style="font-size: 12px;">学生成绩</legend>
				<form class="layui-form" action="">
				<input type="hidden" name="courseId" value="<%=courseId %>" id="courseId"/>
                <input type="hidden" name="stuId" value="<%=stuId %>" id="stuId"/>
  		<div class="layui-form-item" style="margin-bottom:3px;">
	        <label class="layui-form-label" style="font-size:12px;line-height:10px;">学生成绩</label>
	        <div class="layui-input-inline" style="padding-left:5px;">
	            <input type="text" value="<%=stuGrade %>" name="stuGrade" id="stuGrade" lay-verify="required" placeholder="必填项" autocomplete="off" 
	            class="layui-input layui-form-danger" style="height:26px;font-size:12px;">
	        </div>    
        </div>   
    <div class="layui-form-item" style="text-align: center;margin-top:10px;">
        <button class="layui-btn layui-btn-sm layui-btn-normal" lay-submit="" lay-filter="addForm">保存</button>
        &nbsp;&nbsp;
        <button class="layui-btn layui-btn-sm layui-btn-normal" id="close">取消</button>
    </div>
</form>	
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
  //搜索条件开始时间
  var startDate = '';
  //结束时间
  var endDate = '';
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
   			        ,isLazyimg: false
    			    ,done: function(page, next){ //到达临界点（默认滚动触发），触发下一页
    			      var lis = [];
    			      //以jQuery的Ajax请求为例，请求下一页数据（注意：page是从2开始返回）   			   
    			      $.ajax({
    	    		url:'${pageContext.request.contextPath}/homework/getStuHomeworkPhoto',
    	    		method:'post',
    	    		data:{
    	    			'courseId':$("#courseId").val(),  
    	    			'studentId':$("#stuId").val(),
    	    			'startDate':startDate,
    	    			'endDate':endDate
    	    		},  
    	    		success:function(res){
    	
    					if(res !=null){
    						var resData = res.data;
    						if(resData!=null){		
    							var preffix = '${pageContext.request.contextPath}/upload_files/homework_photo/'; 			   				    	
      						    layui.each(resData, function(index, item){				
      	    			           lis.push(
					      	    			'<li style="display:inline; margin:10px;">'+ '<img src="'+preffix+item+'" width="150" height="150" style="margin:20px;"/>'
					      	    			+'</li>');
      	    			                    }); 
      						  
      						  //执行下一页渲染，第二参数为：满足“加载更多”的条件，即后面仍有分页
      	    			        //pages为Ajax返回的总页数，只有当前页小于总页数的情况下，才会继续出现加载更多   	    			
      	    			        next(lis.join(''), page <0);    
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
    
  layui.use(['form','upload','laydate'],function(){
	  var upload = layui.upload;
	  var laydate = layui.laydate;
	 var  form = layui.form;
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
      
      //保存按钮
      form.on('submit(addForm)', function (data) {
        var formJson = data.field;
        	$.ajax({
    			method: "post",
    			url:"../homework/save",
    			data: formJson,
    			async:false,
    			success:function(result){
    				var data = result.data;
                	if(data>0){
                		//关闭窗口 并给父页面传值
                        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                        parent.layer.msg('修改成功！', {title:'提示消息',icon: 1, time: 1500}); //1s后自动关闭);
                    	parent.reloadTable(1);	                       
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