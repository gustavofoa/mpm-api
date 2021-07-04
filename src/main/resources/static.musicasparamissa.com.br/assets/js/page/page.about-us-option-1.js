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
        offset: '80%',
        triggerOnce: true
    });
    //Clients Slider
    $("#owl-clients-v2").owlCarousel({
        paginationSpeed: 400,
        autoPlay: 3000,
        pagination: false,
        itemsCustom: [
            [0, 3],
            [450, 6]
        ]

    });

    $(function () {
      $('[data-toggle="tooltip"]').tooltip();
    });

});
