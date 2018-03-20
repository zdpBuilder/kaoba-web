<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Bootstrap Admin</title>
<link rel="stylesheet" type="text/css"
	href="common/lib/bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css"
	href="common/stylesheets/theme.css">
<link rel="stylesheet"
	href="common/lib/font-awesome/css/font-awesome.css">
<script src="common/lib/jquery-1.7.2.min.js" type="text/javascript"></script>
<style type="text/css">
#line-chart {
	height: 300px;
	width: 800px;
	margin: 0px auto;
	margin-top: 1em;
}

.brand {
	font-family: georgia, serif;
}

.brand .first {
	color: #ccc;
	font-style: italic;
}

.brand .second {
	color: #fff;
	font-weight: bold;
}
</style>
</head>

<body class="">
	<div class="content">
		<ul class="breadcrumb">
			<li><a href="index.html">Home</a> <span class="divider">/</span></li>
			<li class="active">Users</li>
		</ul>

		<div class="container-fluid">
			<div class="row-fluid">

				<div class="btn-toolbar">
					<button class="btn btn-primary">
						<i class="icon-plus"></i> New User
					</button>
					<button class="btn">Import</button>
					<button class="btn">Export</button>
					<div class="btn-group"></div>
				</div>
				<div class="well">
					<table class="table">
						<thead>
							<tr>
								<th>#</th>
								<th>First Name</th>
								<th>Last Name</th>
								<th>Username</th>
								<th style="width: 26px;"></th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>1</td>
								<td>Mark</td>
								<td>Tompson</td>
								<td>the_mark7</td>
								<td><a href="user.html"><i class="icon-pencil"></i></a> <a
									href="#myModal" role="button" data-toggle="modal"><i
										class="icon-remove"></i></a></td>
							</tr>
							<tr>
								<td>2</td>
								<td>Ashley</td>
								<td>Jacobs</td>
								<td>ash11927</td>
								<td><a href="user.html"><i class="icon-pencil"></i></a> <a
									href="#myModal" role="button" data-toggle="modal"><i
										class="icon-remove"></i></a></td>
							</tr>
							<tr>
								<td>3</td>
								<td>Audrey</td>
								<td>Ann</td>
								<td>audann84</td>
								<td><a href="user.html"><i class="icon-pencil"></i></a> <a
									href="#myModal" role="button" data-toggle="modal"><i
										class="icon-remove"></i></a></td>
							</tr>
							<tr>
								<td>4</td>
								<td>John</td>
								<td>Robinson</td>
								<td>jr5527</td>
								<td><a href="user.html"><i class="icon-pencil"></i></a> <a
									href="#myModal" role="button" data-toggle="modal"><i
										class="icon-remove"></i></a></td>
							</tr>
							<tr>
								<td>5</td>
								<td>Aaron</td>
								<td>Butler</td>
								<td>aaron_butler</td>
								<td><a href="user.html"><i class="icon-pencil"></i></a> <a
									href="#myModal" role="button" data-toggle="modal"><i
										class="icon-remove"></i></a></td>
							</tr>
							<tr>
								<td>6</td>
								<td>Chris</td>
								<td>Albert</td>
								<td>cab79</td>
								<td><a href="user.html"><i class="icon-pencil"></i></a> <a
									href="#myModal" role="button" data-toggle="modal"><i
										class="icon-remove"></i></a></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="pagination">
					<ul>
						<li><a href="#">Prev</a></li>
						<li><a href="#">1</a></li>
						<li><a href="#">2</a></li>
						<li><a href="#">3</a></li>
						<li><a href="#">4</a></li>
						<li><a href="#">Next</a></li>
					</ul>
				</div>

				<div class="modal small hide fade" id="myModal" tabindex="-1"
					role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">×</button>
						<h3 id="myModalLabel">Delete Confirmation</h3>
					</div>
					<div class="modal-body">
						<p class="error-text">
							<i class="icon-warning-sign modal-icon"></i>Are you sure you want
							to delete the user?
						</p>
					</div>
					<div class="modal-footer">
						<button class="btn" data-dismiss="modal" aria-hidden="true">Cancel</button>
						<button class="btn btn-danger" data-dismiss="modal">Delete</button>
					</div>
				</div>

				<footer>
					<hr>
					<p class="pull-right">
						Collect from <a href="http://www.cssmoban.com/" title="网页模板"
							target="_blank">网页模板</a>
					</p>
					<p>
						&copy; 2012 <a href="#" target="_blank">Portnine</a>
					</p>
				</footer>
			</div>
		</div>
	</div>
	<script src="common/lib/bootstrap/js/bootstrap.js"></script>
	<script type="text/javascript">
        $("[rel=tooltip]").tooltip();
        $(function() {
            $('.demo-cancel-click').click(function(){return false;});
        });
    </script>
</body>
</html>


