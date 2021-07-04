$(window).scroll(function() {
    if ($(document).scrollTop() < 92) {
        $('#header').removeClass('shrinked').addClass('tr-nav');
        $('#nav').addClass('navbar-transparent');

    } else {
        $('#header').addClass('shrinked').removeClass('tr-nav');
        $('#nav').removeClass('navbar-transparent');
    }
});

$('#focus-input').focus(function() {
    $('#nav').addClass('search-focus-navbar');
});

$('#focus-input').blur(function() {
    $('#nav').removeClass('search-focus-navbar');
});

//Smooth Scroll//
smoothScroll.init();
/*  CLOSE NAVBAR DROPDOWN WHEN LINK CLICKED ON MOBILE
/*===================================================================================*/
// call jRespond and add breakpoints
var jRes = jRespond([{
    label: 'handheld',
    enter: 0,
    exit: 767
}]);

// register enter and exit functions for a single breakpoint
jRes.addFunc({
    breakpoint: 'handheld',
    enter: function() {
        $('.navbar-collapse a').click(function() {
            $(".navbar-collapse").collapse('hide');
        });
    }
});
