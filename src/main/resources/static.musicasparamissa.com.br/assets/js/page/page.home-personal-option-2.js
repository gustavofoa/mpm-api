$(document).ready(function() {

    // MIXITUP PORTFOLIO
    $(function() {
        // Instantiate MixItUp:
        $('#container-mixitup').mixItUp();
    });

    //JQUERY KNOB//
    $(function() {
        $(".dial").knob();
    });
    //WAYPOINT - JQUERY KNOB JS (DIAL) //
    $('#dial-wrapper').waypoint(function() {

        $({
            value: 20
        }).animate({
            value: 70
        }, {
            duration: 1000,
            easing: 'swing',
            step: function() {
                $('.dial-1').val(Math.ceil(this.value)).trigger('change');
            }
        });

        $({
            value: 25
        }).animate({
            value: 80
        }, {
            duration: 1000,
            easing: 'swing',
            step: function() {
                $('.dial-2').val(Math.ceil(this.value)).trigger('change');
            }
        });

        $({
            value: 30
        }).animate({
            value: 40
        }, {
            duration: 1000,
            easing: 'swing',
            step: function() {
                $('.dial-3').val(Math.ceil(this.value)).trigger('change');
            }
        });

        $({
            value: 40
        }).animate({
            value: 60
        }, {
            duration: 1000,
            easing: 'swing',
            step: function() {
                $('.dial-4').val(Math.ceil(this.value)).trigger('change');
            }
        });

    }, {

        offset: '70%',
        triggerOnce: true

    });


    // MAGNIFIC POPUP
    $('#container-mixitup').magnificPopup({
        delegate: 'a', // child items selector, by clicking on it popup will open
        type: 'image'
            // other options
    });

});
