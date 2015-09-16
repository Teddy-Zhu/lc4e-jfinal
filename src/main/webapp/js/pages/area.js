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
                var $topicItems = $('#topicItems'),
                    $topicSetting = $topicItems.find('.topicSetting'),
                    $topicPopup = $topicItems.find('>.item .ui.fluid.image img');
                $('#sortTopic').dropdown().dropdown('set selected', $topicItems.attr('data-sort'));
                $topicSetting.dropdown();
                $topicPopup.popup();
            }
        }
    });
    $.lc4e.area.ready();
});