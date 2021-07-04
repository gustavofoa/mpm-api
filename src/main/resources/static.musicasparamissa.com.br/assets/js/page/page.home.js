$(document).ready(function() {

    //WAYPOINT - JQUERY COUNTTO//
    $('#count-wrapper').waypoint(function() {
        $('.title-count').countTo();
    }, {
        offset: '80%',
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
        paginationSpeed: 500,
        autoPlay: 4000,
        items: 2,
        itemsCustom: [
            [0, 2],
            [450, 2]
        ]

    });

    //SLIDER CLIENTS
    $("#owl-clients").owlCarousel({
        paginationSpeed: 400,
        autoPlay: 5500,
        items: 4,
        pagination: false,
        itemsCustom: [
            [0, 3],
            [450, 4]
        ]

    });

});
