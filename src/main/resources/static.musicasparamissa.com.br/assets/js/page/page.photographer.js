$(document).ready(function() {

    //BACKSTRETCH //
    $(".intro-photographer").backstretch([
        "assets/images/photographer/backstretch/4.jpg",
        "assets/images/photographer/backstretch/1.jpg",
        "assets/images/photographer/backstretch/2.jpg",
        "assets/images/photographer/backstretch/3.jpg"
    ], {
        duration: 4500,
        fade: 850
    });

    //MAGINFIC POPUP
    $('#container-mixitup').magnificPopup({
        delegate: 'a', // child items selector, by clicking on it popup will open
        type: 'image'
            // other options
    });


    // MIXITUP PORTFOLIO
    $(function() {
        // Instantiate MixItUp:
        $('#container-mixitup').mixItUp();
    });

    //WAYPOINT - JQUERY COUNTTO PROGRESS BAR//
    var bar = $('.progress-bar');
    $('#progress-bar-count').waypoint(function() {
        $('.countto-bar').countTo();
        $(bar).each(function() {
            bar_width = $(this).attr('aria-valuenow');
            $(this).width(bar_width + '%');
        });
    }, {
        offset: '80%',
        triggerOnce: true
    });

    //Footer click event to scroll to top
    $('#scroll-top').click(function() {
        $('html, body').animate({
            scrollTop: 0
        }, 880);
        return false;
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
});
