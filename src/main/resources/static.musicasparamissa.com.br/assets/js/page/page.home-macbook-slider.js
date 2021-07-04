$(document).ready(function() {

    //WAYPOINT - JQUERY COUNTTO PROGRESS BAR//
    $('#count-wrapper').waypoint(function() {
        $('.title-count').countTo();
    }, {
        offset: '80%',
        triggerOnce: true
    });

    // Clients Slider
    $("#owl-clients").owlCarousel({
        autoPlay: 3000,
        items: 4,
        pagination: false,
        itemsCustom: [
            [0, 3],
            [450, 4]
        ]

    });

});
