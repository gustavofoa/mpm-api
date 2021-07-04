$(document).ready(function() {

    //WAYPOINT - JQUERY COUNTTO PROGRESS BAR//
    var bar = $('.progress-bar');
    $('#progress-bar-count').waypoint(function() {
        $('.countto-bar').countTo();
        $(bar).each(function() {
            bar_width = $(this).attr('aria-valuenow');
            $(this).width(bar_width + '%');
        });
    }, {
        offset: '70%',
        triggerOnce: true
    });


    //MAGINFIC POPUP
    // $('#owl-our-work').magnificPopup({
    //     delegate: 'a', // child items selector, by clicking on it popup will open
    //     type: 'image'
    //         // other options
    // });

    //SLIDER OUR WORK
    $("#owl-our-work").owlCarousel({
        autoPlay: 3000, //Set AutoPlay to 3 seconds
        items: 2,
        itemsCustom: [
            [0, 2],
            [450, 4]
        ]

    });

});
