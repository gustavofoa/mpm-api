$(document).ready(function() {
    //PORTFOLIO ITEMS SLIDER //
    $("#owl-portfolio-item").owlCarousel({
        autoPlay: 3000,
        navigationText: ["", ""],
        navigation: true,
        pagination: false,
        itemsCustom: [
            [0, 1],
            [768, 5]
        ]

    });
});
