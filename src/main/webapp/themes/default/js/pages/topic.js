/**
 * Created by teddy on 2015/10/13.
 */
$(function () {
    $.lc4e.topic = {
        ready: function () {
            $.lc4e.topic.run();
            $.lc4e.topic.bindEvent();
        },
        run: function () {

        },
        bindEvent: function () {
            var $topicArea = $('#topic');
            $topicArea.find('.ui.card .image').dimmer({
                on: 'hover'
            });
        }
    };
    $.lc4e.topic.ready();
});