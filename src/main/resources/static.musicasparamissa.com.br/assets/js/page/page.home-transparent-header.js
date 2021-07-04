$(document).ready(function() {
    //WAYPOINT - JQUERY COUNTTO PROGRESS BAR//
    $('#count-wrapper').waypoint(function() {
        $('.title-count').countTo();
    }, {
        offset: '80%',
        triggerOnce: true
    });

    // MIXITUP PORTFOLIO
    $(function() {
        $('#container-mixitup').mixItUp();
    });

    // MAGNIFIC POPUP
    $('#container-mixitup').magnificPopup({
        delegate: 'a', // child items selector, by clicking on it popup will open
        type: 'image'
            // other options
    });

    //GOOGLE MAPS //
    var myLatlng = new google.maps.LatLng(40.6700, -73.9400);
    var mapOptions = {
        zoom: 11,
        scrollwheel: false,
        center: myLatlng,
        styles: [{
            featureType: "landscape",
            stylers: [{
                saturation: -100
            }, {
                lightness: 65
            }, {
                visibility: "on"
            }]
        }, {
            featureType: "poi",
            stylers: [{
                saturation: -100
            }, {
                lightness: 51
            }, {
                visibility: "simplified"
            }]
        }, {
            featureType: "road.highway",
            stylers: [{
                saturation: -100
            }, {
                visibility: "simplified"
            }]
        }, {
            featureType: "road.arterial",
            stylers: [{
                saturation: -100
            }, {
                lightness: 30
            }, {
                visibility: "on"
            }]
        }, {
            featureType: "road.local",
            stylers: [{
                saturation: -100
            }, {
                lightness: 40
            }, {
                visibility: "on"
            }]
        }, {
            featureType: "transit",
            stylers: [{
                saturation: -100
            }, {
                visibility: "simplified"
            }]
        }, {
            featureType: "administrative.province",
            stylers: [{
                visibility: "off"
            }] /**/
        }, {
            featureType: "administrative.locality",
            stylers: [{
                visibility: "off"
            }]
        }, {
            featureType: "administrative.neighborhood",
            stylers: [{
                visibility: "on"
            }] /**/
        }, {
            featureType: "water",
            elementType: "labels",
            stylers: [{
                visibility: "on"
            }, {
                lightness: -25
            }, {
                saturation: -100
            }]
        }, {
            featureType: "water",
            elementType: "geometry",
            stylers: [{
                hue: "#ffff00"
            }, {
                lightness: -25
            }, {
                saturation: -97
            }]
        }]
    };

    var map = new google.maps.Map(document.getElementById("map"), mapOptions);

    // To add the marker to the map, use the 'map' property
    var marker = new google.maps.Marker({
        position: myLatlng,
        map: map,
        title: "Hello World!"
    });


    // Clients Slider
    $("#owl-clients").owlCarousel({

        autoPlay: 4500,
        items: 4,
        pagination: false,
        itemsCustom: [
            [0, 3],
            [450, 4]
        ]

    });


    // validate contact form
    $(function() {
        $('#contact').validate({
            rules: {
                name: {
                    required: true,
                    minlength: 2
                },
                email: {
                    required: true,
                    email: true
                },
                message: {
                    required: true
                }
            },
            messages: {
                name: {
                    required: "Please enter your name",
                    minlength: "your name must consist of at least 2 characters"
                },
                email: {
                    required: "Please enter your email address"
                },
                message: {
                    required: "Please enter your message",
                    minlength: "thats all? really?"
                }
            },
            submitHandler: function(form) {
                $(form).ajaxSubmit({
                    type:"POST",
                    data: $(form).serialize(),
                    url:"contact.php",
                    success: function() {
                        $('#contact').css( "display", 'none');
                        $('#contact :input').attr('disabled', 'disabled');
                        $('#success').fadeIn();

                    },
                    error: function() {
                            $('#contact').css( "display", 'none');
                            $('#contact :input').attr('disabled', 'disabled');
                            $('#error').fadeIn();
                    }
                });
            }
        });
    });


    $(function () {
      $('[data-toggle="tooltip"]').tooltip();
    });

});
