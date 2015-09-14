/**
 * Created by teddy on 2015/9/14.
 */
$(function () {
    $.extend($.lc4e, {
        area: {
            ready: function () {
                $.lc4e.area.bindEvent();
                $.lc4e.area.run();
            },
            run: function () {

            },
            bindEvent: function () {
                var $topicItems = $('#topicItems');
                $('#sortTopic').dropdown().dropdown('set selected', $topicItems.attr('data-sort'));
            }
        }
    });
    $.lc4e.area.ready();
});