$(document).ready(function() {

    // MIXITUP PORTFOLIO
    $(function(){
        $('#container-mixitup').mixItUp();
    });
    // MAGNIFIC POPUP
	$('#container-mixitup').magnificPopup({
	  delegate: 'a', // child items selector, by clicking on it popup will open
	  type: 'image'
	  // other options
	});

});
