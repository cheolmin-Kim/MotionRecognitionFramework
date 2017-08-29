// Make home page video wrapper always take 100% width and height
	function fullscreen() {
		var masthead = jQuery('.master-video-wrapper');
		var windowH = jQuery(window).height() - 50;
		var windowW = jQuery(window).width();

		masthead.width(windowW);
		masthead.height(windowH);
	}

	function headerParallax() {
		var st = jQuery(window).scrollTop();
		var headerScroll = jQuery('.master-body');

		if (st < 500) {
			headerScroll.css({
				'opacity' : 1-st/1000,
				'-webkit-filter' : 'blur(' + st/90 + 'px)',
				'-moz-filter' : 'blur(' + st/90 + 'px)',
				'-o-filter' : 'blur(' + st/90 + 'px)',
				'-ms-filter' : 'blur(' + st/90 + 'px)',
				filter : 'blur(' + st/90 + 'px)'
			});
			jQuery('.master-arrow').css('opacity', 0.5-st/250);
			headerScroll.css({
				'-webkit-transform' : 'translateY(' + st/7 + '%)',
				'-ms-transform' : 'translateY(' + st/7 + '%)',
				transform : 'translateY(' + st/7 + '%)'
			});
		}
	}

jQuery(document).ready(function() { 

	jQuery(window).resize(fullscreen);
	jQuery(window).scroll(headerParallax);
	fullscreen();

});

// Make navbar dissapear when scroll down and appear when scroll up

var didScroll;
var lastScrollTop = 0;
var delta = 5;
var navbarHeight = jQuery('#menu').outerHeight();

jQuery(window).scroll(function(event){
    didScroll = true;
});

setInterval(function() {
    if (didScroll) {
        hasScrolled();
        didScroll = false;
    }
}, 200);

function hasScrolled() {
    var st = jQuery(this).scrollTop();
    
    // Make sure they scroll more than delta
    if(Math.abs(lastScrollTop - st) <= delta)
        return;
    
    // If they scrolled down and are past the navbar, add class .nav-up.
    // This is necessary so you never see what is "behind" the navbar.
    if (st > lastScrollTop && st > navbarHeight){
        // Scroll Down
        jQuery('#menu').removeClass('nav-down').addClass('nav-up');
    } else {
        // Scroll Up
        if(st + jQuery(window).height() < jQuery(document).height()) {
            jQuery('#menu').removeClass('nav-up').addClass('nav-down');
        }
    }
    
    lastScrollTop = st;
}