$(document).ready(function() {
    //Coming Soon Countdown //
    $('#coming-soon').countdown('2015/11/11').on('update.countdown', function(event) {
        var $this = $(this).html(event.strftime(
            '<div class="coming-soon-wrapper"><span class="coming-soon-time">%D</span> <span class="coming-soon-date">day%!d</span></div> ' + '<div class="coming-soon-wrapper"><span class="coming-soon-time">%H</span> <span class="coming-soon-date">hours</span></div> ' + '<div class="coming-soon-wrapper"><span class="coming-soon-time">%M</span> <span class="coming-soon-date">minutes</span></div> ' + '<div class="coming-soon-wrapper"><span class="coming-soon-time">%S</span> <span class="coming-soon-date">seconds</span></div> '));
    });
});
