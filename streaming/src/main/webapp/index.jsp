<!DOCTYPE html>
<html lang="en-us">
<head>
	<title>Tutorial Academy</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="theme-color" content="#157878">
	<link rel="stylesheet" type="text/css" href="css/mkhplayer.default.css"/>
	<link rel="stylesheet" type="text/css" href="css/main.css"/>
</head>
<body>

	<div class="header">
	  <h1>Tutorial Academy<br>REST media streaming with Jersey2</h1>
	</div>
	<br>
    <section class="main-content">
		<video id='video' preload="metadata" width="100%">
			<source src="http://localhost:8080/streaming/webapi/stream">
		</video>
		<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="js/jquery.mkhplayer.js"></script>
		<script type="text/javascript">
			$(document).ready(function(){
				$('video').mkhPlayer();
			});
		</script>
		<footer class="site-footer">
          	<span class="site-footer-owner">
          		The <a href="https://tutorial-academy.com/rest-jersey2-resume-video-streaming">tutorial</a> can be found here.
          	</span>
        	<span class="site-footer-credits">
        		This code is available at <a href="https://github.com/maltesander/rest-jersey2-resume-video-streaming">GitHub</a>.
        	</span>
      	</footer>
	</section>
</body>
</html>