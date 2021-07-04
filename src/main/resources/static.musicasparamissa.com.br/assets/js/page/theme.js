$(document).ready(function() {

    //stopPropagation//
    $(document).on('click', '.stop-prop', function(e) {
        e.stopPropagation();
    });

    //Footer click event to scroll to top
    $('#scroll-top').click(function() {
        $('html, body').animate({
            scrollTop: 0
        }, 880);
        return false;
    });

    //Accordion//
    $('.panel-acc .panel-heading a[data-toggle="collapse"], .accordion-group .accordion-heading a[data-toggle="collapse"]').on('click', function() {
        $(this).removeClass('acc-on');
        $(this).addClass('acc-on');
    });

});
