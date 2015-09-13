/**
 * Created by teddy on 2015/8/30.
 */
$(function () {
    $.lc4e.index = {
        variables: {
            $article: $("#articlelist"),
            $header: $('#attachedHeader'),
            $articleItems: $('#articleItems'),
            $announce: $('#announce')
        },
        ready: function () {
            $.lc4e.index.bindEvent();
            $.lc4e.index.run();
        },
        run: function () {
            $.lc4e.index.animateArticle();
        },
        bindEvent: function () {
            var $header = $.lc4e.index.variables.$header,
                $articleItems = $.lc4e.index.variables.$articleItems,
                $announce = $.lc4e.index.variables.$announce;

            $announce.shape();
            $('#sortTopic').dropdown().dropdown('set selected', $header.attr('data-sort'));

            $('#prePage,#nextPage,#ft_next,#ft_prev').on('click', function () {
                var page = parseInt($header.attr("data-page")) + 1;
                $.Lc4eAjax({
                    url: "/?p=" + page,
                    data: {art: true},
                    target: '#articlelist>.ui.divided.items',
                    pjax: true,
                    loading: 'loading articles',
                    animate: function () {
                        $.lc4e.index.animateArticle();
                    },
                    success: function (data) {
                        $articleItems.attr("data-page", page);
                    }
                })
            });

            setTimeout(function () {
                $.lc4e.index.shape()
            }, 10000);
        },
        animateArticle: function () {
            $.requestAnimationFrame(function () {
                $.lc4e.index.variables.$articleItems.find('>.item').
                    transition({
                        animation: 'scale in',
                        duration: 250,
                        interval: 90,
                        onComplete: function () {
                            $.lc4e.index.variables.$articleItems.find('>.item .ui.fluid.image img').popup();
                        }
                    });
            });
        },
        shape: function () {
            $.lc4e.index.$announce.shape('flip down');
            setTimeout(function () {
                $.lc4e.index.shape()
            }, 10000);
        }
    };
    $.lc4e.index.ready();
});