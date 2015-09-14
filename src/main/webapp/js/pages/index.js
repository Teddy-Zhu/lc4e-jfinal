/**
 * Created by teddy on 2015/8/30.
 */
$(function () {
    $.lc4e.index = {
        ready: function () {
            $.lc4e.index.bindEvent();
            $.lc4e.index.run();
        },
        run: function () {
        },
        bindEvent: function () {
            var $articleItems = $('#articleItems'),
                $announce = $('#announce');

            $announce.shape();
            $('#sortTopic').dropdown().dropdown('set selected', $articleItems.attr('data-sort'));

            $('#prePage,#nextPage,#ft_next,#ft_prev').on('click', function () {
                var $items = $('#articleItems'), page = parseInt($items.attr("data-page")) + 1;
                $.Lc4eAjax({
                    url: "/?p=" + page,
                    data: {art: true},
                    target: '#articlelist>.ui.divided.items',
                    pjax: true,
                    loading: 'loading articles',
                    animate: function ($target) {
                        $target.
                            transition({
                                animation: 'fade down in',
                                duration: 250,
                                interval: 90,
                                onComplete: function () {
                                    $target.find('>.item .ui.fluid.image img').popup();
                                }
                            });
                    },
                    success: function (data) {
                        $('#articleItems').attr("data-page", page);
                    }
                })
            });

            setTimeout(function () {
                $.lc4e.index.shape($announce);
            }, 10000);
        },
        shape: function ($announce) {
            $announce.shape('flip down');
            setTimeout(function () {
                $.lc4e.index.shape($announce)
            }, 10000);
        }
    };
    $.lc4e.index.ready();
});