var UnrealEstate = {
    init : function() {
        jQuery('input, textarea').placeholder();
        jQuery(".main-nav > ul").tinyNav({
            active: 'active',
            header: 'Navigation'
        });
        jQuery('.l_tinynav1').addClass('hidden-phone');
        jQuery('#tinynav1').addClass('visible-phone');

        jQuery('.custom-tooltip').tooltip({
            'selector': '',
            'placement': 'bottom'
        });
        jQuery('.range-example').slider({
            min : 0,
            max : 100,
            value : new Array(29, 78),
            handle : 'square'
        });
        jQuery('.custom-multiple-select').mCustomScrollbar({
            theme : "dark-thick"
        });
        jQuery('.location-finder .left-side').mCustomScrollbar({
            theme : "dark-thick"
        });
        jQuery('.header-buttons .profile').click(function() {
            var $this = $(this);
            if($('#hidden-header').hasClass('open')) {
                if($('.header-buttons .contact').hasClass('closed')) {
                    $this.addClass('closed').attr('data-original-title', 'Close');
                    $('.header-buttons .contact').removeClass('closed').attr('data-original-title', 'Go to Contact Form');
                    $('#hidden-header .contact-form').fadeOut('fast', function() {
                        $('#hidden-header .profile-form').fadeIn('fast');
                    });
                } else {
                    $this.removeClass('closed');
                    $this.attr('data-original-title', 'Go to My Profile');
                    $('#hidden-header .profile-form').hide();
                    UnrealEstate.closeHiddenHeader();
                }
            } else {
                $this.addClass('closed');
                $this.attr('data-original-title', 'Close');
                $('#hidden-header .profile-form').show();
                UnrealEstate.openHiddenHeader();
            }
        });
        jQuery('.header-buttons .contact').click(function() {
            var $this = $(this);
            if($('#hidden-header').hasClass('open')) {
                if($('.header-buttons .profile').hasClass('closed')) {
                    $this.addClass('closed').attr('data-original-title', 'Close');
                    $('.header-buttons .profile').removeClass('closed').attr('data-original-title', 'Go to My Profile');
                    $('#hidden-header .profile-form').fadeOut('fast', function() {
                        $('#hidden-header .contact-form').fadeIn('fast');
                    });
                } else {
                    $this.removeClass('closed');
                    $this.attr('data-original-title', 'Go to Contact Form');
                    $('#hidden-header .contact-form').hide();
                    UnrealEstate.closeHiddenHeader();
                }
            } else {
                $this.addClass('closed');
                $this.attr('data-original-title', 'Close');
                $('#hidden-header .contact-form').show();
                UnrealEstate.openHiddenHeader();
            }
        });
        jQuery('.selectpicker').selectpicker();
        jQuery('.testimonial-box').flexslider({
            'controlNav': false,
            'directionNav' : false,
            "touch": true,
            "animation": "fade",
            "animationLoop": true,
            "slideshow" : true
        });
        jQuery('.testimonial-box .next').click(function(){
            $('.testimonial-box').flexslider("next");
            return false;
        });
        jQuery('.testimonial-box .prev').click(function(){
            $('.testimonial-box').flexslider("prev");
            return false;
        });
        jQuery('.featured-item').hover(function() {
            if(!jQuery(this).hasClass('featured-list')) {
                jQuery(this).find('.bottom').slideDown('fast');
//                jQuery(this).find('.bubble').fadeIn('fast');
                jQuery(this).parent().find('.price-wrapper').slideUp('fast');
                jQuery(this).parent().find('.star-rating').slideUp('fast');
            }
        }, function() {
            if(!jQuery(this).hasClass('featured-list')) {
                jQuery(this).find('.bottom').slideUp('fast');
//                jQuery(this).find('.bubble').fadeOut('fast');
                jQuery(this).parent().find('.price-wrapper').slideDown('fast');
                jQuery(this).parent().find('.star-rating').slideDown('fast');
            }
        });
        jQuery('.featured-items-slider').flexslider({
            'controlNav': false,
            'directionNav' : false,
            "touch": true,
            "animation": "fade",
            "animationLoop": true,
            "slideshow" : false
        });
        jQuery('.featured-items .next').click(function(){
            $('.featured-items-slider').flexslider("next");
            return false;
        });
        jQuery('.featured-items .prev').click(function(){
            $('.featured-items-slider').flexslider("prev");
            return false;
        });
        jQuery('.homepage-slider').flexslider({
            'controlNav': false,
            'directionNav' : false,
            "touch": true,
            "animation": "fade",
            "animationLoop": true,
            "slideshow" : true
        });
        jQuery('#homepage-slider .next').click(function(){
            $('.homepage-slider').flexslider("next");
            return false;
        });
        jQuery('#homepage-slider .prev').click(function(){
            $('.homepage-slider').flexslider("prev");
            return false;
        });
        UnrealEstate.createAboutSlider();
        UnrealEstate.createMultipleSelect();
        UnrealEstate.positionButtonSlider();
        jQuery('.location-finder .button-slider').click(UnrealEstate.toggleLocationFinder);
        jQuery('.location-finder article').click(function() {
            var tag = jQuery(this).attr('rel');
            var title = jQuery(this).find('h3').text();
            var address = jQuery(this).find('p').text();
            var html = '<strong>'+title+'</strong> <br />'+address;
            $('#map_canvas').gmap3({
                exec: {
                    tag : tag,
                    all:"true",
                    func: function(data){
                        // data.object is the google.maps.Marker object
                        data.object.setIcon("img/black-marker.png");

                        var map = $('#map_canvas').gmap3("get"),
                            infowindow = $('#map_canvas').gmap3({get:{name:"infowindow"}});
                        if (infowindow){
                            infowindow.open(map, data.object);
                            infowindow.setContent(html);
                        } else {
                            $('#map_canvas').gmap3({
                                infowindow:{
                                    anchor: data.object,
                                    options:{content: html}
                                }
                            });
                        }


                    }
                }
            });
        });
        jQuery('.location-finder article').hover(function() {
            var tag = jQuery(this).attr('rel');
            $('#map_canvas').gmap3({
                exec: {
                    tag : tag,
                    all:"true",
                    func: function(data){
                        // data.object is the google.maps.Marker object
                        data.object.setIcon("img/orange-marker.png")
                    }
                }
            });
        }, function() {
            var tag = jQuery(this).attr('rel');
            $('#map_canvas').gmap3({
                exec: {
                    tag : tag,
                    all:"true",
                    func: function(data){
                        // data.object is the google.maps.Marker object
                        data.object.setIcon("img/blue-marker.png")
                    }
                }
            });
        });
    },
    toggleLocationFinder : function() {
        var $this = jQuery(this);
        if($this.hasClass('expanded')) {
            jQuery('.location-finder .left-side').animate({
                marginLeft : "-40%"
            }, {
                duration : 500,
                queue : false
            });
            jQuery('.location-finder .right-side').animate({
                width : "100%"
            }, {
                duration : 550,
                queue : false,
                complete : function() {
                    $('#map_canvas').gmap3({trigger:"resize"});
                }
            });
            $this.removeClass('expanded');
        } else {
            jQuery('.location-finder .left-side').animate({
                marginLeft : "0px"
            }, {
                duration : 550,
                queue : false
            });
            jQuery('.location-finder .right-side').animate({
                width : "60%"
            }, {
                duration : 500,
                queue : false,
                complete : function() {
                    $('#map_canvas').gmap3({trigger:"resize"});
                }
            });
            $this.addClass('expanded');
        }
        return false;
    },
    positionButtonSlider : function() {
        var top = jQuery('.location-finder').height() / 2 - jQuery('.location-finder .button-slider').height() / 2;
        jQuery('.location-finder .button-slider').css('top', top+'px');
    },
    createAboutSlider : function() {
      jQuery('.description-controls a').click(function() {
          if(!jQuery(this).hasClass('active')) {
              var rel = jQuery(this).attr('rel');
              jQuery('.description-controls a.active').removeClass('active');
              jQuery(this).addClass('active');
              jQuery('.description-box[rel="'+rel+'"]').slideDown({
                  duration : 'slow',
                  complete : function() {
                      jQuery(this).addClass('open');
                  }
              });
              jQuery('.description-box.open').slideUp("slow").removeClass('open');
          }
          return false;
      });
    },
    createMultipleSelect : function() {
        $('.custom-multiple-select-filters .all').click(function() {
            var $this = $(this);
            $('.custom-multiple-select li').each(function() {
                $(this).addClass('selected');
            });
            $('.custom-multiple-select select option').each(function() {
                $(this).attr('selected', true);
            });
            $('.custom-multiple-select-filters .active').removeClass('active');
            setTimeout(function() {
                $this.addClass('active');
            }, 100);
            return false;
        });
        $('.custom-multiple-select-filters .none').click(function() {
            var $this = $(this);
            $('.custom-multiple-select li').each(function() {
                $(this).removeClass('selected');
            });
            $('.custom-multiple-select select option').each(function() {
                $(this).removeAttr('selected');
            });
            $('.custom-multiple-select-filters .active').removeClass('active');
            setTimeout(function() {
                $this.addClass('active');
            }, 100);
            return false;
        });
        $('.custom-multiple-select-filters .featured').click(function() {
            var $this = $(this);
            $('.custom-multiple-select li.featured').each(function() {
                $(this).addClass('selected');
                var rel = $(this).attr('rel');
                $('.custom-multiple-select select option[rel="'+rel+'"]').attr('selected', true);
            });
            $('.custom-multiple-select-filters .active').removeClass('active');
            setTimeout(function() {
                $this.addClass('active');
            }, 100);
            return false;
        });


        $('.custom-multiple-select li').click(function() {
            var $this = $(this);
            var rel = $this.attr('rel');
            if ($this.hasClass('selected')) {
                $this.removeClass('selected');
                $('.custom-multiple-select select option[rel="'+rel+'"]').removeAttr('selected');
            } else {
                $this.addClass('selected');
                $('.custom-multiple-select select option[rel="'+rel+'"]').attr('selected', true);
            }
        });
    },
    openHiddenHeader : function() {
        $('#hidden-header').animate({
            marginTop : 0
        }, {
            duration : 500,
            queue : false,
            complete : function() {
                $(this).addClass('open');
            }
        });
    },
    closeHiddenHeader : function() {
        $('#hidden-header').animate({
            marginTop : "-409px"
        }, {
            duration : 500,
            queue : false,
            complete : function() {
                $(this).removeClass('open');
            }
        });
    }
}

jQuery(document).ready(function() {
    UnrealEstate.init();
});
jQuery(window).resize(function() {

});