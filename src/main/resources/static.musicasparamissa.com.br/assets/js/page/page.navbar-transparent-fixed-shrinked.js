  $(window).scroll(function() {
      if ($(document).scrollTop() < 92) {
          $('#header').removeClass('shrinked').addClass('tr-nav');
          $('#nav').addClass('navbar-transparent');

      } else {
          $('#header').addClass('shrinked').removeClass('tr-nav');
          $('#nav').removeClass('navbar-transparent');
      }
  });

  $('#focus-input').focus(function() {
      $('#nav').addClass('search-focus-navbar');
  });

  $('#focus-input').blur(function() {
      $('#nav').removeClass('search-focus-navbar');
  });
