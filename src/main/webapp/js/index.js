require.config({
    paths: {
        "jquery": "/plugins/jquery/jquery.min",
        "semantic": "/plugins/semantic-ui/semantic.min",
        "lc4e": "/js/lc4e/jquery-extend",
        "se-accordion": "/plugins/semantic-ui/components/accordion.min",
        "se-api": "/plugins/semantic-ui/components/api.min",
        "se-breadcrumb": "/plugins/semantic-ui/components/breadcrumb.min",
        "se-checkbox": "/plugins/semantic-ui/components/checkbox.min",
        "se-dimmer": "/plugins/semantic-ui/components/dimmer.min",
        "se-form": "/plugins/semantic-ui/components/form.min",
        "se-dropdown": "/plugins/semantic-ui/components/dropdown.min",
        "se-modal": "/plugins/semantic-ui/components/modal.min",
        "se-nag": "/plugins/semantic-ui/components/nag.min",
        "se-popup": "/plugins/semantic-ui/components/popup.min",
        "se-progress": "/plugins/semantic-ui/components/progress.min",
        "se-rating": "/plugins/semantic-ui/components/rating.min",
        "se-search": "/plugins/semantic-ui/components/search.min",
        "se-shape": "/plugins/semantic-ui/components/shape.min",
        "se-sidebar": "/plugins/semantic-ui/components/sidebar.min",
        "se-site": "/plugins/semantic-ui/components/site.min",
        "se-state": "/plugins/semantic-ui/components/state.min",
        "se-sticky": "/plugins/semantic-ui/components/sticky.min",
        "se-tab": "/plugins/semantic-ui/components/tab.min",
        "se-table": "/plugins/semantic-ui/components/table.min",
        "se-transition": "/plugins/semantic-ui/components/transition.min",
        "se-video": "/plugins/semantic-ui/components/video.min",
        "se-visibility": "/plugins/semantic-ui/components/visibility.min"
    },
    shim: {
        'lc4e': ['jquery', 'semantic'],
        'semantic': ['jquery'],
        "se-accordion": ['jquery'],
        "se-api": ['jquery'],
        "se-breadcrumb": ['jquery'],
        "se-checkbox": ['jquery'],
        "se-dimmer": ['jquery'],
        "se-form": ['jquery'],
        "se-dropdown": ['jquery'],
        "se-modal": ['jquery'],
        "se-nag": ['jquery'],
        "se-popup": ['jquery'],
        "se-progress": ['jquery'],
        "se-rating": ['jquery'],
        "se-search": ['jquery'],
        "se-shape": ['jquery'],
        "se-sidebar": ['jquery'],
        "se-site": ['jquery'],
        "se-state": ['jquery'],
        "se-sticky": ['jquery'],
        "se-tab": ['jquery'],
        "se-table": ['jquery'],
        "se-transition": ['jquery'],
        "se-video": ['jquery'],
        "se-visibility": ['jquery']
    }
});

require(['jquery', 'lc4e', 'semantic'], function ($) {
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
            var timer;
            $('#menuheader').visibility({
                offset: -1,
                continuous: true,
                onTopPassed: function () {
                    $.requestAnimationFrame(function () {
                        $('#menu').addClass('fixed');
                    })
                },
                onTopPassedReverse: function () {
                    clearTimeout(timer);
                    timer = setTimeout(function () {
                        $.requestAnimationFrame(function () {
                            $('#menu').removeClass('fixed');
                        })
                    }, 300);
                }
            });
            $('#GTTop').visibility({
                offset: -1,
                once: false,
                continuous: false,
                onTopPassed: function () {
                    $.requestAnimationFrame(function () {
                        $('#GTTop').transition('swing down');
                    });
                },
                onTopPassedReverse: function () {
                    $.requestAnimationFrame(function () {
                        $('#GTTop').transition('swing down');
                    });
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
                    $('#articlelist>.ui.divided.items>.item').
                        transition({
                            animation: 'slide up',
                            duration: 500,
                            interval: 50,
                            onComplete: function () {
                                $('#articlelist>.ui.divided.items>.item').find('.ui.fluid.image img').popup();
                            }
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
                $('html').animatescroll({
                    scrollSpeed: 1000,
                    easing: 'easeOutBounce'
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
                        $('#articlelist>.ui.divided.items>.item').
                            transition({
                                animation: 'slide up',
                                duration: 500,
                                interval: 50,
                                onComplete: function () {
                                    $('#articlelist>.ui.divided.items>.item').find('.ui.fluid.image img').popup();
                                }
                            })
                    }
                })
            });

            setInterval(function () {
                $('#announce').shape('flip down');
            }, 4000);

        }
    };
    $(function () {
        $.lc4e.index.ready();
    })
})