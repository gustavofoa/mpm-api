$(document).ready(function() {
    //Clients Slider
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
