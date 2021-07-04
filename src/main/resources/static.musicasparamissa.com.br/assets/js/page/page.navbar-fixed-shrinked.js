  $(window).scroll(function() {
      if ($(document).scrollTop() < 92) {
          $('#header').removeClass('shrinked').addClass('tr-nav');
      } else {
          $('#header').addClass('shrinked').removeClass('tr-nav');
      }
  });
