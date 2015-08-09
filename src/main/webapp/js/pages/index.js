$(function () {
    $.lc4e.index = {
        ready: function () {
            $('#menu .ui.dropdown.item').dropdown({
                action: "nothing",
                transition: "horizontal flip",
                on: 'click'
            });
            $('#searchSite').on('focus', function () {
                $(this).addClass('expended');
            }).on('blur', function () {
                $(this).removeClass('expended')
            });

            $('#expendHeader').on('click', function () {
                $('#menu').toggleClass('expended');
            });

            $('#menu .column div:first a').on('click', function () {
                $('#menu>.column>.allmenus').transition({
                    animation: "fly down",
                    duration: 500,
                    onComplete: function () {
                        $('#menu>.column>.allmenus').toggleClass('menuhidden').removeClass("transition visible hidden").attr('style', '');
                    }
                });
            });

            $('#config-tool-options .angle.double.left.icon').on('click', function () {
                if ($($('#config-tool-options .ui.animated.selection.list:not(.hidden)').transition('fade left').attr('data-parent')).transition('fade left').attr('id') == 'menu1') {
                    $(this).addClass('transition hidden');
                }
            });

            $("#config-tool-options .ui.list .item[data-target^='#']").on('click', function () {
                $(this).parent().transition('fade left');
                $($(this).attr('data-target')).transition('fade left');
                $('#config-tool-options .angle.double.left.icon').removeClass('transition hidden');
            });

            $('#config-tool-cog').on('click', function () {
                $('#config-tool').toggleClass('closed');
            });
            var timerIn, timerOut;
            $('html').visibility({
                offset: -5,
                observeChanges: false,
                once: false,
                continuous: false,
                onTopPassed: function () {
                    clearTimeout(timerIn);
                    $.requestAnimationFrame(function () {
                        $('#menu').addClass('fixed');
                    });
                    timerIn = setTimeout(function () {
                        $.requestAnimationFrame(function () {
                            $('#GTTop').transition('swing down in');
                        })
                    }, 300);
                },
                onTopPassedReverse: function () {
                    clearTimeout(timerOut);
                    $.requestAnimationFrame(function () {
                        $('#menu').removeClass('fixed');

                    });
                    timerOut = setTimeout(function () {
                        $.requestAnimationFrame(function () {
                            $('#GTTop').transition('swing down out');
                        })
                    }, 300);
                }
            });


            $('#userItem img.ui.image').popup({
                position: 'bottom left',
                transition: "fade right",
                popup: $('#userCardPop'),
                exclusive: false,
                hideOnScroll: false,
                on: 'click',
                closable: true
            });

            $('#config-tool-options .ui.checkbox').checkbox();

            $('#fixFooter').checkbox({
                onChange: function (e) {
                    $('#content').toggleClass('footerFixed');
                    $('.ui.footer').toggleClass('fixed');
                }
            });


            $('#colorBackground').checkbox({
                onChange: function (e) {
                    $.Lc4eStars();
                }
            });
            $('#boxedLayout').checkbox({
                onChange: function (e) {
                    $('#articlelist').toggleClass('nobox');
                }
            });

            $('#announce').shape();

            $.Lc4eAjax({
                url: "/?p=" + parseInt($("#articlelist>.ui.divided.items").attr("page")),
                target: '#articlelist>.ui.divided.items',
                pjax: true,
                success: function (data) {
                    $.requestAnimationFrame(function () {
                        $('#articlelist>.ui.divided.items>.item').
                            transition({
                                animation: 'fade up in',
                                duration: 500,
                                interval: 80,
                                onComplete: function () {
                                    $('#articlelist>.ui.divided.items>.item').find('.ui.fluid.image img').popup();
                                }
                            });
                    })

                }
            });

            $.get('/TopHots').done(function (data) {
                $('#todayHot>.ui.divided.items,#yesterdayHot>.ui.divided.items').empty().append(data);
                $('#todayHot>.ui.divided.items>.item,#yesterdayHot>.ui.divided.items').
                    transition({
                        animation: 'fade right',
                        duration: 100,
                        interval: 50
                    })
            });

            $('#GTTop').on('click', function (e) {
                $('body').animatescroll({
                    scrollSpeed: 500
                });
            });

            $('#prePage,#nextPage').on('click', function () {
                var page = parseInt($("#articlelist>.ui.divided.items").attr("page")) + 1;
                $("#articlelist>.ui.divided.items").attr("page", page);
                $.Lc4eAjax({
                    url: "/?p=" + page,
                    target: '#articlelist>.ui.divided.items',
                    pjax: true,
                    success: function (data) {
                        $.requestAnimationFrame(function () {
                            $('#articlelist>.ui.divided.items>.item').
                                transition({
                                    animation: 'fade up in',
                                    duration: 500,
                                    interval: 100,
                                    onComplete: function () {
                                        $('#articlelist>.ui.divided.items>.item').find('.ui.fluid.image img').popup();
                                    }
                                });
                        });
                    }
                })
            });

            setInterval(function () {
                $('#announce').shape('flip down');
            }, 4000);

        }
    };
    $.lc4e.index.ready();
});
