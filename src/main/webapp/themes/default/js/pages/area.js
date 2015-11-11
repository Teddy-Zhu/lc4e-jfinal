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
                    $topicPopup = $topicItems.find('>.item .ui.fluid.image img'),
                    sortItem = $topicItems.attr('data-sort'),
                    $sortTopic = $('#sortTopic'),
                    $areaName = $('#areaName');
                $sortTopic.dropdown().dropdown('set selected', sortItem ? sortItem : $sortTopic.find('.scrolling.menu>.item:first').attr('data-value'));
                $topicSetting.dropdown();
                $topicPopup.popup();
                $topicItems.on('click', function (e) {
                    var $target = $(e.target);
                    if ($target.hasClass('content')) {
                        window.location.href = $target.find('a.header').attr('href');
                    }
                });
                $areaName.hover(function () {
                    if (!$areaName.hasClass("animated")) {
                        $areaName.addClass('animated flip').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function () {
                            $areaName.removeClass('animated flip');
                        })
                    }
                }, function () {
                })

            }
        }
    });
    $.lc4e.area.ready();
});