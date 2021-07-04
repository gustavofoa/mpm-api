/**
 * Description: js for the Musics Star Ratings.
 */
 (function($, window, document, undefined){

 	$.fn.starratings = function(options)
 	{
 		$.fn.starratings.options = $.extend({
 			ajaxurl   : null,
 			starsurl  : null,
 			grs       : false,
 			msg       : 'Rate this post',
 			fuelspeed : 400,
 			thankyou  : 'Thank you for rating.',
 			error_msg : 'An error occured.',
 			tooltip   : true,
 			tooltips  : {
 				0 : {
 					tip   : "Poor",
 					color : "red"
 				},
 				1 : {
 					tip   : "Fair",
 					color : "brown"
 				},
 				2 : {
 					tip   : "Average",
 					color : "orange"
 				},
 				3 : {
 					tip   : "Good",
 					color : "blue"
 				},
 				4 : {
 					tip   : "Excellent",
 					color : "green"
 				}
 			}
 		}, $.fn.starratings.options, options ? options : {});


        var me = this;
        var Objs = [];
        $.get( $.fn.starratings.options.starsurl, function( stars ) {

            $.fn.starratings.options.stars = stars;

            //Update list with stars
            var starElements = document.querySelectorAll('[data-star]');

            me.each(function(){
                Objs.push($(this));
                if(starElements.length == 0)
                    $.fn.starratings.animate($(this));
            });

 		    $.fn.starratings.fetch(Objs, 0, '0%', $.fn.starratings.options.msg, true);

            if(starElements != null)
                starElements.forEach(function(item, index){
                    item.style.width = stars[item.attributes['data-star'].value].r+'%';
                });

        });

 		return this.each(function(){});

     };

 	$.fn.starratings.animate = function(obj)
 	{
 		if(!obj.hasClass('disabled'))
 		{
 			if($.fn.starratings.options.stars){
 			    var legend = $('.sr-legend', obj).html();
                var fuel = $.fn.starratings.options.stars[obj.attr('data-id')].r+'%';//$('.sr-fuel', obj).css('width');
                $('.sr-fuel', obj).stop(true,true).animate({'width':fuel}, $.fn.starratings.options.fuelspeed);
                $('.sr-legend', obj).html(legend);

                $('.sr-stars a', obj).hover( function(){
                    if(obj.hasClass('disabled'))
                        return;
                    var stars = $(this).attr('href').split('#')[1];
                    if($.fn.starratings.options.tooltip!=0)
                    {
                        if($.fn.starratings.options.tooltips[stars-1]!=null)
                        {
                            $('.sr-legend', obj).html('<span style="color:'+$.fn.starratings.options.tooltips[stars-1].color+'">'+$.fn.starratings.options.tooltips[stars-1].tip+'</span>');
                        }
                        else
                        {
                            $('.sr-legend', obj).html(legend);
                        }
                    }
                    $('.sr-fuel', obj).stop(true,true).css('width', '0%');
                    $('.sr-stars a', obj).each(function(index, element) {
                        var a = $(this),
                            s = a.attr('href').split('#')[1];
                        if(parseInt(s)<=parseInt(stars))
                        {
                            $('.sr-stars a', obj).stop(true, true);
                            a.hide().addClass('sr-star').addClass('orange').fadeIn('fast');
                        }
                    });
                }, function(){
                    if(obj.hasClass('disabled'))
                      return;
                    $('.sr-stars a', obj).removeClass('sr-star').removeClass('orange');
                    if($.fn.starratings.options.tooltip!=0) $('.sr-legend', obj).html(legend);
                    $('.sr-fuel', obj).stop(true,true).animate({'width':fuel}, $.fn.starratings.options.fuelspeed);
                }).unbind('click').click( function(){
                    return $.fn.starratings.click(obj, $(this).attr('href').split('#')[1]);
                });
            }
        }
        else
        {
            $('.sr-stars a', obj).unbind('click').click( function(){ return false; });
        }

 	};

 	$.fn.starratings.update = function(obj, per, legend, disable, is_fetch)
 	{
 		if(disable==true)
 		{
 			$('.sr-fuel', obj).removeClass('yellow').addClass('orange');
 		}
 		$('.sr-fuel', obj).stop(true, true).animate({'width':per}, $.fn.starratings.options.fuelspeed, 'linear', function(){
 			if(disable==true)
 			{
 				obj.addClass('disabled');
 				$('.sr-stars a', obj).unbind('hover');
 			}
 			if(!$.fn.starratings.options.grs || !is_fetch)
 			{
 				$('.sr-legend', obj).stop(true,true).hide().html(legend?legend:$.fn.starratings.options.msg).fadeIn('slow', function(){
 					$.fn.starratings.animate(obj);
 				});
 			}
 			else
 			{
 				$.fn.starratings.animate(obj);
 			}
 		});
 	};

 	$.fn.starratings.click = function(obj, stars)
 	{
 		$('.sr-stars a', obj).unbind('hover').unbind('click').removeClass('sr-star').removeClass('orange').click( function(){ return false; });

 		var legend = $('.sr-legend', obj).html(),
 			fuel = $('.sr-fuel', obj).css('width');

 		$.fn.starratings.fetch(obj, stars, fuel, legend, false);

 		return false;
 	};

 	$.fn.starratings.fetch = function(obj, stars, fallback_fuel, fallback_legend, is_fetch)
 	{
 	    if(window.location.href.indexOf('/musica/') < 0)
 	        return;
 		var postids = [];
 		$.each(obj, function(){
 			postids.push($(this).attr('data-id'));
 		});
 		$.ajax({
 			url: $.fn.starratings.options.ajaxurl+postids,
 			data: 'stars='+stars,
 			type: "post",
 			dataType: "json",
 			beforeSend: function(xhr, settings) {
 				$('.sr-fuel', obj).animate({'width':'0%'}, $.fn.starratings.options.fuelspeed);
 				if(stars)
 				{
 					$('.sr-legend', obj).fadeOut('fast', function(){
 						$('.sr-legend', obj).html('<span style="color: green">'+$.fn.starratings.options.thankyou+'</span>');
 					}).fadeIn('slow');
 				}
                function getCookie(name) {
                    var cookieValue = null;
                    if (document.cookie && document.cookie != '') {
                        var cookies = document.cookie.split(';');
                        for (var i = 0; i < cookies.length; i++) {
                            var cookie = jQuery.trim(cookies[i]);
                            // Does this cookie string begin with the name we want?
                            if (cookie.substring(0, name.length + 1) == (name + '=')) {
                                cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                                break;
                            }
                        }
                    }
                    return cookieValue;
                }
                if (!(/^http:.*.do/.test(settings.url) || /^https:.*/.test(settings.url))) {
                    // Only send the token to relative URLs i.e. locally.
                    xhr.setRequestHeader("X-CSRFToken", getCookie('csrftoken'));
                }
 			},
 			success: function(response){
 				$.each(obj, function(){
 					var current = $(this),
 						current_id = current.attr('data-id');
                    if(response[current_id]){
                        if(response[current_id].success)
                        {
                            $.fn.starratings.update(current, response[current_id].fuel+'%', response[current_id].legend, response[current_id].disable, is_fetch);
                        }
                        else
                        {
                            $.fn.starratings.update(current, fallback_fuel, fallback_legend, false, is_fetch);
                        }
                    }
 				});
 			},
 			complete: function(){

 			},
 			error: function(e){
 				$('.sr-legend', obj).fadeOut('fast', function(){
 					$('.sr-legend', obj).html('<span style="color: red">'+$.fn.starratings.options.error_msg+'</span>');
 				}).fadeIn('slow', function(){
 					$.fn.starratings.update(obj, fallback_fuel, fallback_legend, false, is_fetch);
 				});
 			}
 		});
 	};

 	$.fn.starratings.options = {
 		ajaxurl   : starratings_config_js.ajaxurl,
 		starsurl  : starratings_config_js.starsurl,
 		func      : starratings_config_js.func,
 		grs       : starratings_config_js.grs,
 		tooltip   : starratings_config_js.tooltip,
 		tooltips  : starratings_config_js.tooltips,
 		msg       : starratings_config_js.msg,
 		fuelspeed : starratings_config_js.fuelspeed,
 		thankyou  : starratings_config_js.thankyou,
 		error_msg : starratings_config_js.error_msg
 	};

 })(jQuery, window, document);

 jQuery(document).ready( function($){
 	$('.star-ratings').starratings();
 });
