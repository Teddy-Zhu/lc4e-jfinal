$(function () {
    $.lc4e.index = {
        ready: function () {
            var $configTool = $("#config-tool-options"), $article = $("#articlelist");
            $configTool.find(".ui.list .item[data-target^='#']").on('click', function () {
                $(this).parent().transition('fade left');
                $($(this).attr('data-target')).transition('fade left');
                $configTool.find('.angle.double.left.icon').removeClass('transition hidden');
            });

            $configTool.find('.ui.checkbox').checkbox();

            $('#fixFooter').checkbox({
                onChange: function (e) {
                    $('#content').toggleClass('footerFixed');
                    $('.ui.footer').toggleClass('fixed');
                }
            });

            $('#colorBackground').checkbox({
                onChange: function (e) {
                    if ($('#colorBackground').checkbox('is checked')) {
                        $.Lc4eStars();
                    } else {
                        $.Lc4eStars('destroy');
                    }
                }
            });
            $('#boxedLayout').checkbox({
                onChange: function (e) {
                    $('#articlelist').toggleClass('nobox');
                }
            });

            $('#announce').shape();

            $.Lc4eAjax({
                url: "/?p=" + parseInt($article.find(">.ui.divided.items").attr("page")),
                target: '#articlelist>.ui.divided.items',
                data: {art: true},
                pjax: true,
                loading: 'loading artiles',
                success: function (data) {
                    $article.find('>.ui.divided.items>.item').
                        transition({
                            animation: 'fade up in',
                            duration: 500,
                            interval: 80,
                            onComplete: function () {
                                $article.find('>.ui.divided.items>.item .ui.fluid.image img').popup();
                            }
                        });
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


            $('#prePage,#nextPage').on('click', function () {
                var page = parseInt($article.find(">.ui.divided.items").attr("page")) + 1;
                $article.find(">.ui.divided.items").attr("page", page);
                $.Lc4eAjax({
                    url: "/?p=" + page,
                    data: {art: true},
                    target: '#articlelist>.ui.divided.items',
                    pjax: true,
                    loading: 'loading artiles',
                    success: function (data) {
                        $article.find('>.ui.divided.items>.item').
                            transition({
                                animation: 'fade up in',
                                duration: 500,
                                interval: 100,
                                onComplete: function () {
                                    $article.find('>.ui.divided.items>.item').find('.ui.fluid.image img').popup();
                                }
                            });
                    }
                })
            });
            var interval;
            clearInterval(interval);
            interval = setInterval(function () {
                $('#announce').shape('flip down');
            }, 10000);

        }
    };
    $.lc4e.index.ready();
});
