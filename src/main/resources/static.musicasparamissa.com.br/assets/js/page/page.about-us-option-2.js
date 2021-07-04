$(document).ready(function() {
    //Clients Slider //
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
