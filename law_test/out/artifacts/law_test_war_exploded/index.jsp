<!DOCTYPE html>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html lang="en">

<head>
<title>Home</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta charset="utf-8">
<meta name="keywords" content="" />
<style src="antd.css"></style>

<!-- 加载 React。-->
  <!-- 注意: 部署时，将 "development.js" 替换为 "production.min.js"。-->
  <script src="https://unpkg.com/react@16/umd/react.development.js" crossorigin></script>
  <script src="https://unpkg.com/react-dom@16/umd/react-dom.development.js" crossorigin></script>
  <script src="https://unpkg.com/babel-standalone@6/babel.min.js"></script>
  <!-- 加载我们的 React 组件。-->

<script type="application/x-javascript">
	addEventListener("load", function () {
		setTimeout(hideURLbar, 0);
	}, false);

	function hideURLbar() {
		window.scrollTo(0, 1);
	}
</script>
<!-- bootstrap-css -->
<link href="css/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
<!--// bootstrap-css -->
<!-- css -->
<link rel="stylesheet" href="css/style.css" type="text/css" media="all" />
<!--// css -->
<!-- font-awesome icons -->
<link href="css/font-awesome.css" rel="stylesheet">
<!-- //font-awesome icons -->
<!-- font -->
<link href="http://fonts.googleapis.com/css?family=Roboto:100,100i,300,300i,400,400i,500,500i,700,700i,900,900i" rel="stylesheet">
<!-- //font -->
</head>

<body onload="initPage();">
<!-- banner -->
<div class="banner" id="home">
	<!-- agileinfo-dot -->
	<div class="agileinfo-dot">
		<div class="head">
			<div class="navbar-top">
				<!-- Brand and toggle get grouped for better mobile display -->
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
							<span class="sr-only">Toggle navigation</span>
							<span class="icon-bar"></span>
							<span class="icon-bar"></span>
							<span class="icon-bar"></span>
						  </button>
					<div class="navbar-brand logo ">
						<h1><a href="index.html"> Legal Adviser <i class="fa fa-balance-scale" aria-hidden="true"></i></a></h1>
					</div>

				</div>

				<!-- Collect the nav links, forms, and other content for toggling -->
				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav link-effect-4" id="menu_div">
						<li class="active"><a href="index.html" data-hover="Home">Home</a> </li>
						<li><a href="#about" class="scroll">About </a> </li>
						<li><a href="#features" class="scroll">Features </a> </li>
						<li><a href="#services" class="scroll">Practice Areas</a> </li>
						<li><a href="#news" class="scroll">News</a></li>
						<li><a href="#team" class="scroll">Team</a></li>
						<li><a href="#contact" class="scroll">Contact</a></li>
					</ul>
				</div>
				<!-- /.navbar-collapse -->
			</div>
		</div>
		<div class="w3layouts-banner-slider">
			<div class="container">
				<div class="slider">
					<div class="callbacks_container">
						<ul class="rslides callbacks callbacks1" id="slider4">
							<li>
								<div class="agileits-banner-info">
									<h3>International
										<div class="border"></div> <span>Law Firm</span></h3>
									<div class="w3-button">
										<div class="w3ls-button">
											<a href="#about" class="scroll hvr-shutter-out-vertical">About</a>
										</div>
										<div class="w3l-button">
											<a href="#contact" class="scroll hvr-shutter-out-vertical">Contact</a>
										</div>
										<div class="clearfix"> </div>
									</div>
								</div>
							</li>
							<li>
								<div class="agileits-banner-info">
									<h3>Welcome
										<div class="border"></div> <span>to the Legal Adviser</span></h3>
									<div class="w3-button">
										<div class="w3ls-button">
											<a href="#about" class="scroll hvr-shutter-out-vertical">About</a>
										</div>
										<div class="w3l-button">
											<a href="#contact" class="scroll hvr-shutter-out-vertical">Contact</a>
										</div>
										<div class="clearfix"> </div>
									</div>
								</div>
							</li>
							<li>
								<div class="agileits-banner-info">
									<h3>International
										<div class="border"></div> <span>Law Firm</span></h3>
									<div class="w3-button">
										<div class="w3ls-button">
											<a href="#about" class="scroll hvr-shutter-out-vertical">About</a>
										</div>
										<div class="w3l-button">
											<a href="#contact" class="scroll hvr-shutter-out-vertical">Contact</a>
										</div>
										<div class="clearfix"> </div>
									</div>
								</div>
							</li>
						</ul>
					</div>
					<div class="clearfix"> </div>

				</div>
			</div>
		</div>
	</div>
	<!-- //agileinfo-dot -->
</div>
<!-- //banner -->
<!-- modal -->
<div class="modal about-modal fade" id="myModal" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title"><i class="fa fa-tint" aria-hidden="true"></i>Oil <span>Production</span></h4>
			</div>
			<div class="modal-body">
				<div class="agileits-w3layouts-info">
					<img src="images/1.jpg" alt="" />
					<p>Duis venenatis, turpis eu bibendum porttitor, sapien quam ultricies tellus, ac rhoncus risus odio eget nunc. Pellentesque
						ac fermentum diam. Integer eu facilisis nunc, a iaculis felis. Pellentesque pellentesque tempor enim, in dapibus turpis
						porttitor quis. Suspendisse ultrices hendrerit massa. Nam id metus id tellus ultrices ullamcorper. Cras tempor massa
						luctus, varius lacus sit amet, blandit lorem. Duis auctor in tortor sed tristique. Proin sed finibus sem</p>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- //modal -->
<!-- about -->
<div class="about" id="about">
	<div class="container">
		<div class="w3l-about-grids_inner">
			<div class="col-md-6 w3ls-about-left">
				<h2>Welcome to the Legal Adviser</h2>
				<h6>Lorem ipsum dolor sit amet,Phasellus dapibus felis elit, sed accumsan arcu gravida vitae.</h6>
				<p>Phasellus dapibus felis elit, sed accumsan arcu gravida vitae. Nullam aliquam erat at lectus ullamcorper, nec interdum
					neque hendrerit.Lorem ipsum dolor sit amet,Phasellus dapibus felis elit, sed accumsan arcu gravida vitae</p>
				<p>Lorem ipsum dolor sit amet,Phasellus dapibus felis elit, sed accumsan arcu gravida vitae. Nullam aliquam erat at lectus
					ullamcorper, nec interdum neque hendrerit.</p>
				<a href="#contact" class="scroll hvr-shutter-out-vertical">Contact</a>
			</div>
			<div class="col-md-6 w3ls-about-right">
				<img src="images/ab.jpg" alt=" " class="img-responsive">
			</div>
			<div class="clearfix"> </div>
		</div>
	</div>
</div>
<!-- //about -->
<div class="tlinks">Collect from <a href="http://www.cssmoban.com/" >建站模板</a></div>
<!-- about -->
<div class="why jarallax" id="features">
	<div class="agile-dot">
		<div class="container">
			<div class="about-heading two">
				<h3>Features</h3>
				<p>You Are Always One Step Ahead</p>
			</div>
			<div class="w3l-about-grids">
				<div class="col-md-4 w3ls-about-why-us-agile">
					<div class="agileits-icon-grid">
						<div class="icon-left hvr-radial-out">
							<i class="fa fa-cog" aria-hidden="true"></i>
						</div>
						<div class="icon-right">
							<h5>Great Discount</h5>
							<p>Phasellus dapibus felis elit, sed accumsan arcu gravida vitae. </p>
						</div>
						<div class="clearfix"> </div>
					</div>
					<div class="agileits-icon-grid">
						<div class="icon-left hvr-radial-out">
							<i class="fa fa-heart" aria-hidden="true"></i>
						</div>
						<div class="icon-right">
							<h5>Community Service</h5>
							<p>Phasellus dapibus felis elit, sed accumsan arcu gravida vitae. </p>
						</div>
						<div class="clearfix"> </div>
					</div>
					<div class="agileits-icon-grid">
						<div class="icon-left hvr-radial-out">
							<i class="fa fa-paper-plane" aria-hidden="true"></i>
						</div>
						<div class="icon-right">
							<h5>Great Discount</h5>
							<p>Phasellus dapibus felis elit, sed accumsan arcu gravida vitae. </p>
						</div>
						<div class="clearfix"> </div>
					</div>
				</div>
				<div class="col-md-4 w3ls-about-right">
					<div class="w3ls-about-right-img">

					</div>
				</div>
				<div class="col-md-4 w3ls-about-why-us-agile">
					<div class="agileits-icon-grid">
						<div class="icon-left hvr-radial-out">
							<i class="fa fa-cog" aria-hidden="true"></i>
						</div>
						<div class="icon-right">
							<h5>Community Service</h5>
							<p>Phasellus dapibus felis elit, sed accumsan arcu gravida vitae. </p>
						</div>
						<div class="clearfix"> </div>
					</div>
					<div class="agileits-icon-grid">
						<div class="icon-left hvr-radial-out">
							<i class="fa fa-heart" aria-hidden="true"></i>
						</div>
						<div class="icon-right">
							<h5>Great Discount</h5>
							<p>Phasellus dapibus felis elit, sed accumsan arcu gravida vitae. </p>
						</div>
						<div class="clearfix"> </div>
					</div>
					<div class="agileits-icon-grid">
						<div class="icon-left hvr-radial-out">
							<i class="fa fa-paper-plane" aria-hidden="true"></i>
						</div>
						<div class="icon-right">
							<h5>Community Service</h5>
							<p>Phasellus dapibus felis elit, sed accumsan arcu gravida vitae. </p>
						</div>
						<div class="clearfix"> </div>
					</div>
				</div>
				<div class="clearfix"> </div>
			</div>
		</div>
	</div>
</div>
<!-- //about -->
<!-- services -->
<div class="services" id="services">
	<div class="container">
		<div class="about-heading">
			<h3>Practice Areas</h3>
			<p>You Are Always One Step Ahead</p>
		</div>
		<div class="w3-agileits-services-grids">
			<div class="col-md-4 w3-agileits-services-left">
				<div class="services-info">

				</div>
			</div>
			<div class="col-md-8 w3-agileits-services-right">
				<div class='bar_group'>
					<div class='bar_group__bar thin elastic' label='Criminal Law' value='230'></div>
					<div class='bar_group__bar thin elastic' label='Drug Offence' value='160'></div>
					<div class='bar_group__bar thin elastic' label='Family Law' value='130'></div>
					<div class='bar_group__bar thin elastic' label='Real Estate Law' value='160'></div>
					<div class='bar_group__bar thin elastic' label='Personal Law' value='340'></div>
					<div class='bar_group__bar thin elastic' label='Financial Law' value='290'></div>
				</div>

			</div>
			<div class="clearfix"> </div>
		</div>
	</div>
</div>
<!-- //services -->
<!-- news -->
<div class="gallery" id="news">
	<div class="container">
		<div class="about-heading">
			<h3>Latest News</h3>
			<p>You Are Always One Step Ahead</p>
		</div>
	</div>
	<div class="gallery-grids">
		<div class="gallery-top-grids">
			<div class="col-sm-3 gallery-grids-left">
				<div class="gallery-grid">
					<a href="#">
							<img src="images/g1.jpg" alt="" />
							<div class="g-captn">
								<h4>Legal Adviser</h4>
								<p>12.Apr.2017</p>
							</div>
						</a>
				</div>
				<div class="des_agile_info">
					<h4>Lorem Ipsupm</h4>
					<p>Add Some Short Description</p>
				</div>
			</div>
			<div class="col-sm-3 gallery-grids-left">
				<div class="gallery-grid">
						<a href="#">
							<img src="images/g2.jpg" alt="" />
							<div class="g-captn">
								<h4>Legal Adviser</h4>
								<p>20.May.2017</p>
							</div>
						</a>
				</div>
				<div class="des_agile_info">
					<h4>Amet Dolor</h4>
					<p>Add Some Short Description</p>
				</div>
			</div>
			<div class="col-sm-3 gallery-grids-left">
				<div class="gallery-grid">
					<a href="#">
							<img src="images/g3.jpg" alt="" />
							<div class="g-captn">
								<h4>Legal Adviser</h4>
								<p>22.May.2017</p>
							</div>
						</a>
				</div>
				<div class="des_agile_info">
					<h4>Lorem Ipsupm</h4>
					<p>Add Some Short Description</p>
				</div>
			</div>
			<div class="col-sm-3 gallery-grids-left">
				<div class="gallery-grid">
					<a href="#">
							<img src="images/g4.jpg" alt="" />
							<div class="g-captn">
								<h4>Legal Adviser</h4>
								<p>27.May.2017</p>
							</div>
						</a>
				</div>
				<div class="des_agile_info">
					<h4>Amet Dolor</h4>
					<p>Add Some Short Description</p>
				</div>
			</div>
			<div class="clearfix"> </div>
		</div>
		<div class="clearfix"> </div>
	</div>
</div>
<!-- //news -->
<!-- team -->
<div class="team" id="team">
	<div class="container">
		<div class="about-heading two">
			<h3>Amazing Lawyers</h3>
			<p>You Are Always One Step Ahead</p>
		</div>
		<div class="team-grids">
			<div class="col-md-3 agileinfo-team-grid">
				<img src="images/t1.jpg" alt="">
				<div class="captn">
					<h4>Peter Parker</h4>
					<p>Lawyer </p>
					<div class="w3l-social">
						<ul>
							<li><a href="#"><i class="fa fa-facebook"></i></a></li>
							<li><a href="#"><i class="fa fa-twitter"></i></a></li>
							<li><a href="#"><i class="fa fa-rss"></i></a></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="col-md-3 agileinfo-team-grid">
				<img src="images/t2.jpg" alt="">
				<div class="captn">
					<h4>Mary Jane</h4>
					<p>Lawyer</p>
					<div class="w3l-social">
						<ul>
							<li><a href="#"><i class="fa fa-facebook"></i></a></li>
							<li><a href="#"><i class="fa fa-twitter"></i></a></li>
							<li><a href="#"><i class="fa fa-rss"></i></a></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="col-md-3 agileinfo-team-grid">
				<img src="images/t3.jpg" alt="">
				<div class="captn">
					<h4>Johan Botha</h4>
					<p>Lawyer</p>
					<div class="w3l-social">
						<ul>
							<li><a href="#"><i class="fa fa-facebook"></i></a></li>
							<li><a href="#"><i class="fa fa-twitter"></i></a></li>
							<li><a href="#"><i class="fa fa-rss"></i></a></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="col-md-3 agileinfo-team-grid">
				<img src="images/t4.jpg" alt="">
				<div class="captn">
					<h4>Steven Wilson</h4>
					<p>Lawyer</p>
					<div class="w3l-social">
						<ul>
							<li><a href="#"><i class="fa fa-facebook"></i></a></li>
							<li><a href="#"><i class="fa fa-twitter"></i></a></li>
							<li><a href="#"><i class="fa fa-rss"></i></a></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="clearfix"> </div>
		</div>
	</div>
</div>
<!-- //team -->
<!-- contact -->
<div class="free_agile_consultation contact">
	<div class="col-md-6 free_agile_consultaion_img">


	</div>
	<div class="col-md-6 free_consultation_agile">
		<h4>Free Consultation</h4>
		<h6>Lorem ipsum dolor sit amet,Phasellus dapibus felis elit, sed accumsan arcu gravida vitae.</h6>
		<form action="#" method="post">
			<div class="contact-left agileits-w3layouts free_w3ls_agile">
				<input type="text" name="First Name" placeholder="First Name" required="">
				<input class="email" name="Last Name" type="text" placeholder="Last Name" required="">
				<input type="text" name="Number" placeholder="Mobile Number" required="">
				<input class="email" name="Email" type="email" placeholder="Email" required="">
				<select class="form-control">
							<option>Practice Areas</option>
							<option value="january">January</option>
							<option value="february">February</option>
							<option value="march">March</option>
							<option value="april">April</option>
							<option value="may">May</option>
							<option>Other</option>
						</select>

				<textarea name="Message" placeholder="Message" required></textarea>
				<input type="submit" value="SEND REQUEST">
			</div>

		</form>
	</div>
	<div class="clearfix"> </div>
</div>
<!-- //contact -->
<!-- offer -->
<div class="jarallax offer">
	<div class="agile-dot">
		<div class="container">
			<div class="about-heading offer-heading">
				<h3>People We’ve Helped</h3>
				<p>You Are Always One Step Ahead</p>
			</div>
			<div class="w3ls-offer-slider">
				<div class="slider">
					<div class="callbacks_container">
						<ul class="rslides callbacks callbacks1" id="slider3">
							<li>
								<div class="agileinfo-offer-grid">
									<h4><i class="fa fa-quote-left" aria-hidden="true"></i></h4>
									<h5>Gary Growles</h5>
									<p>Aenean volutpat auctor ultrices. Aliquam ac turpis ultrices, porta enim at, interdum velit. Ut ligula justo, sodales
										eu nisi a, molestie dictum leo. Sed blandit porta ante.</p>
								</div>
							</li>
							<li>
								<div class="agileinfo-offer-grid">
									<h4><i class="fa fa-quote-left" aria-hidden="true"></i></h4>
									<h5>Ivan Stepaniv</h5>
									<p>Vestibulum euismod porttitor interdum. Vivamus porta consectetur nulla porttitor hendrerit. Pellentesque ultrices
										augue ut nibh feugiat, nec egestas orci cursus.</p>
								</div>
							</li>
							<li>
								<div class="agileinfo-offer-grid">
									<h4><i class="fa fa-quote-left" aria-hidden="true"></i></h4>
									<h5>Jorge Lopez</h5>
									<p>Aenean volutpat auctor ultrices. Aliquam ac turpis ultrices, porta enim at, interdum velit. Ut ligula justo, sodales
										eu nisi a, molestie dictum leo. Sed blandit porta ante.</p>
								</div>
							</li>
						</ul>
					</div>

				</div>
			</div>
		</div>
	</div>
</div>
<!-- //offer -->
<!-- contact -->
<div class="contact" id="contact">
	<div class="container">
		<div class="about-heading">
			<h3>Contact Us</h3>
			<p>You Are Always One Step Ahead</p>
		</div>
		<div class="w3l-about-grids">
			<div class="contact-info">
				<ul>
					<li><i class="fa fa-location-arrow" aria-hidden="true"></i>
						<h5>Address<span>Honey 4, 8305, San Francisco</span></h5>
					</li>
					<li><i class="fa fa-phone" aria-hidden="true"></i>
						<h5>Call Us<span>+1 (100)222-0-33</span></h5>
					</li>
					<li><i class="fa fa-envelope-o" aria-hidden="true"></i>
						<h5>Mail Us<span><a href="mailto:info@example.com">info@example.com</a></span></h5>
					</li>
				</ul>
			</div>
			<div class="contact-w3ls-row">
				<form action="#" method="post">
					<div class="col-md-5 col-sm-5 contact-right agileits-w3layouts">
						<textarea name="Message" placeholder="Message" required></textarea>
					</div>
					<div class="col-md-7 col-sm-7 contact-left agileits-w3layouts">
						<input type="text" name="First Name" placeholder="First Name" required="">
						<input class="email" name="Last Name" type="text" placeholder="Last Name" required="">
						<input type="text" name="Number" placeholder="Mobile Number" required="">
						<input class="email" name="Email" type="email" placeholder="Email" required="">
						<input type="submit" value="SUBMIT">
					</div>
					<div class="clearfix"> </div>
				</form>
			</div>
		</div>
	</div>
</div>
<!-- //contact -->
<!-- map -->
<!---<div class="agileits-w3layouts-map">
	<iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d100949.24429313939!2d-122.44206553967531!3d37.75102885910819!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x80859a6d00690021%3A0x4a501367f076adff!2sSan+Francisco%2C+CA%2C+USA!5e0!3m2!1sen!2sin!4v1472190196783"
		class="map" allowfullscreen=""></iframe>
</div>--->
<!-- //map -->
<!-- footer -->
<div class="footer">
	<div class="container">
		<div class="agileinfo_footer_bottom">
			<div class="col-md-6 agileinfo_footer_bottom_grid">
				<h6>Links</h6>
				<ul class="tag2 tag_agileinfo">
					<li><a href="#home" class="scroll">Home</a></li>
					<li><a href="#about" class="scroll">About</a></li>
					<li><a href="#features" class="scroll">Features</a></li>
					<li><a href="#team" class="scroll">Team</a></li>
					<li><a href="#news" class="scroll">News</a></li>
					<li><a href="#contact" class="scroll">Contact</a></li>
				</ul>
			</div>
			<div class="col-md-6 agileinfo_footer_bottom_grid">
				<h6>Follow Us</h6>
				<div class="w3l-social">
					<ul>
						<li><a href="#"><i class="fa fa-facebook"></i></a></li>
						<li><a href="#"><i class="fa fa-twitter"></i></a></li>
						<li><a href="#"><i class="fa fa-rss"></i></a></li>
					</ul>
				</div>
			</div>
			<div class="clearfix"> </div>
		</div>
		<div class="agileinfo_footer_bottom1">

			<p>Copyright &copy; 2017.Company name All rights reserved.More Templates <a href="http://www.cssmoban.com/" target="_blank" title="模板之家">模板之家</a> - Collect from <a href="http://www.cssmoban.com/" title="网页模板" target="_blank">网页模板</a></p>
		</div>
	</div>
</div>
<!-- //footer -->
<!-- //footer -->
<script src="js/jquery-2.1.4.min.js"></script>
<script>
	// You can also use "$(window).load(function() {"
	$(function () {
		// Slideshow 4
		$("#slider4").responsiveSlides({
			auto: true,
			pager: true,
			nav: true,
			speed: 500,
			namespace: "callbacks",
			before: function () {
				$('.events').append("<li>before event fired.</li>");
			},
			after: function () {
				$('.events').append("<li>after event fired.</li>");
			}
		});

	});
</script>
<script>
	// You can also use "$(window).load(function() {"
	$(function () {
		// Slideshow 4
		$("#slider3").responsiveSlides({
			auto: true,
			pager: false,
			nav: false,
			speed: 500,
			namespace: "callbacks",
			before: function () {
				$('.events').append("<li>before event fired.</li>");
			},
			after: function () {
				$('.events').append("<li>after event fired.</li>");
			}
		});

	});
</script>
<script src="js/responsiveslides.min.js"></script>
<script src="js/bars.js"></script>
<script src="js/jarallax.js"></script>
<script src="js/SmoothScroll.min.js"></script>
<script type="text/javascript">
	/* init Jarallax */
	$('.jarallax').jarallax({
		speed: 0.5,
		imgWidth: 1366,
		imgHeight: 768
	})
</script>
<script type="text/javascript" src="js/easing.js"></script>
<script type="text/javascript" src="js/move-top.js"></script>
<script type="text/javascript">
	jQuery(document).ready(function ($) {
		$(".scroll").click(function (event) {
			event.preventDefault();
			$('html,body').animate({
				scrollTop: $(this.hash).offset().top
			}, 1000);
		});
	});
</script>
<!-- here stars scrolling icon -->
<script type="text/javascript">
	$(document).ready(function () {
		/*
			var defaults = {
			containerID: 'toTop', // fading element id
			containerHoverID: 'toTopHover', // fading element hover id
			scrollSpeed: 1200,
			easingType: 'linear' 
			};
		*/

		$().UItoTop({
			easingType: 'easeOutQuart'
		});

	});
</script>
<!-- //here ends scrolling icon -->
<script src="js/bootstrap.js"></script>
</body>
<script>
function initPage(){
		getMenu();
	}
	function initMenu(json){
		var list=json.aaData;
		var html="";
		for(var i=0;i<list.length;i++){
			html+="<li><a href="+list[i][1]+" class=\"scroll\">"+list[i][2]+" </a> </li>";
		}
		$("#menu_div").html(html);
	}
	function getMenu(){
		url="query";
		var data = '{"UserName":"'+'<%=request.getParameter("UserName")%>'+'"}';
		var obj=JSON.parse(data);
		$.post(url,obj, function(json){
			initMenu(json);
		})
	}
</script>
</html>