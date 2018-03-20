<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html> 
<head>
  <meta charset="utf-8">
  <title>批量添加老师</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<link rel="stylesheet" href="../plugins/layui/css/layui.css" media="all" />
</head>
<body>
<div style="margin:8px auto;text-align:center">
<input type="file" name="upfile" lay-type="file" class="layui-upload-file"> 
</div>	
<script type="text/javascript" src="../plugins/layui/layui.js"></script>
<script>
layui.use('upload', function(){
	var index=null;
  layui.upload({
	 
    url: '../teacher/insertTeachersBatch' //上传接口
    	 ,title: '请上传Excel文件'
    		 ,ext: 'xlsx|xls'
    ,before: function(input){
    	    //返回的参数item，即为当前的input DOM对象
    	   // console.log('文件上传中');
    	index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});   
    	  }
    ,success: function(res){ //上传成功后的回调
      if(res>0){
    	  top.layer.close(index);
    	  layer.msg("导入成功！",{icon: 6,time:1500,shade:0.8});
    	  parent.reloadTable(1);
          parent.layer.close(index);
    	  window.setTimeout(function(){
    		  parent.location.reload();
    	  },2000);
      }else{
    	  top.layer.close(index);
    	  layer.msg("导入失败！",{icon: 5,time:1500});
    	  
      }
      
    }
  });
  
  
});
</script>

</body>
</html>