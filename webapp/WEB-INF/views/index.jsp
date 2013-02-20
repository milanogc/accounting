<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Bootstrap 101 Template</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="webjars/bootstrap/2.3.0/css/bootstrap.min.css" rel="stylesheet" media="screen">
<style>
	body {
		padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
	}
</style>
</head>
<body>
	<div class="navbar navbar-inverse navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="brand" href="#">Accounting</a>
				<div class="nav-collapse collapse">
					<ul class="nav">
						<li class="active"><a href="#">Home</a></li>
						<li><a href="#about">About</a></li>
						<li><a href="#contact">Contact</a></li>
					</ul>
				</div>
				<!--/.nav-collapse -->
			</div>
		</div>
	</div>

	<div class="container">
		<h1>Bootstrap starter template</h1>
		<p>
			Use this document as a way to quick start any new project.<br>
			All you get is this message and a barebones HTML document.
		</p>
		
		<ul class="nav nav-tabs">
			<li class="active"><a href="#home" data-toggle="tab">Accounts</a></li>
			<li><a href="#profile" data-toggle="tab">Journal Transactions</a></li>
			<li><a href="#messages" data-toggle="tab">Messages</a></li>
			<li><a href="#settings" data-toggle="tab">Settings</a></li>
		</ul>
		
		<div class="tab-content">
			<div class="tab-pane active" id="home">
				<ul id="accounts">
					
				</ul>
			</div>
			<div class="tab-pane" id="profile">...</div>
			<div class="tab-pane" id="messages">...</div>
			<div class="tab-pane" id="settings">...</div>
		</div>
	</div>
	<!-- /container -->
	
	<script src="webjars/jquery/1.9.0/jquery.min.js"></script>
	<script src="webjars/underscorejs/1.4.2/underscore-min.js"></script>
	<script src="webjars/bootstrap/2.3.0/js/bootstrap.min.js"></script>
	<script src="webjars/backbonejs/0.9.9/backbone.min.js"></script>
	<script src="webjars/spin-js/1.2.7/spin.min.js"></script>
	<script src="webjars/spin-js/1.2.7/jquery.spin.js"></script>
	<script src="resources/js/index.js"></script>
	<script type="text/template" id="account-template">
		{{ name }}
	</script>
</body>
</html>