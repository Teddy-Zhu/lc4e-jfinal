/**
 * Created by teddy on 2015/8/30.
 */
$(function () {
    $.lc4e.index = {
        ready: function () {
            var $article = $("#articlelist"), $articleItems = $('#articleItems'), $announce = $('#announce');

            $announce.shape();

            $('#sortTopic').dropdown().dropdown('set selected', $articleItems.attr('data-sort'));

            $.requestAnimationFrame(function () {
                $articleItems.find('>.item').
                    transition({
                        animation: 'scale in',
                        duration: 250,
                        interval: 90,
                        onComplete: function () {
                            $articleItems.find('>.item .ui.fluid.image img').popup();
                        }
                    });
            });


            $('#prePage,#nextPage,#ft_next,#ft_prev').on('click', function () {
                var page = parseInt($articleItems.attr("data-page")) + 1;
                $.Lc4eAjax({
                    url: "/?p=" + page,
                    data: {art: true},
                    target: '#articlelist>.ui.divided.items',
                    pjax: true,
                    loading: 'loading articles',
                    animate: function () {
                        $.requestAnimationFrame(function () {
                            $articleItems.find('>.item').
                                transition({
                                    animation: 'fade up in',
                                    duration: 250,
                                    interval: 90,
                                    onComplete: function () {
                                        $articleItems.find('>.item .ui.fluid.image img').popup();
                                    }
                                });
                        });
                    },
                    success: function (data) {
                        $articleItems.attr("data-page", page);
                    }
                })
            });

            function shape() {
                $announce.shape('flip down');
                setTimeout(shape, 10000);
            }

            setTimeout(shape, 10000);
        }
    };
    $.lc4e.index.ready();
});