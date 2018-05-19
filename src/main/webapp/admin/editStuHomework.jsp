<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
//课程id
String homeworkId= request.getParameter("homeworkId");
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
				<input type="hidden" name="homeworkId" value="<%=homeworkId %>" id="homeworkId"/>
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
<img alt="" style="display:none" id="displayimg" src="" />
<script type="text/javascript" src="../plugins/ztree/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="../plugins/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="../plugins/layui2.x/layui.js"></script>
<script type="text/javascript" src="../js/index.js"></script>
<!--  课程导航树-->
<script type="text/javascript">
//图片放大查看 
function ZoomPhoto(name, url) {
		layui.use('layer', function(){
			var $ = layui.jquery
			, layer = layui.layer;
    $("#displayimg").attr("src", url);
    var height = $("#displayimg").height();
    var width = $("#displayimg").width();
    layer.open({
        type: 1,
        title: false,
        closeBtn: 1,
        shadeClose: true,
        area: [width + 'px', height + 'px'], //宽高
        content: "<img alt=" + name + " title=" + name + " src=" + url + " />"
    });
		
		});
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
    	    		async:false,
    	    		data:{
    	    			'homeworkId':$("#homeworkId").val(),  
    	    			'studentId':$("#stuId").val()
    	    		},  
    	    		success:function(res){
    	
    					if(res !=null){
    						var resData = res.data;
    						if(resData!=null){		
    							var preffix = '${pageContext.request.contextPath}/upload_files/homework_photo/'; 			   				    	
      						    layui.each(resData, function(index, item){				
      	    			           lis.push(
					      	    			'<li style="display:inline; margin:10px;">'+ '<a href="javascript:ZoomPhoto('+index+',\''+preffix+item+'\')"><img src="'+preffix+item+'" width="150" height="150" style="margin:20px;"/></a>'
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
      //加载图片
	  getPhoto();
       //页面刷新
       $("#btn-refresh").click(function(){ 		
    		//将数据清空
    		$('#imagesList').html("");
    		//重新渲染图片数据
    		getPhoto();
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