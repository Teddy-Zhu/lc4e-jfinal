/**
 * Created by teddy on 2015/8/26.
 */
$(function () {
    $('#captchaimg').on('click', function () {
        if (!$(this).transition('is animating')) {
            $(this).transition({
                animation: 'vertical flip out',
                onComplete: function () {
                    $('#captchaimg').attr('src', '/captcha?rand=' + new Date().getTime())
                        .transition({
                            animation: 'vertical flip in',
                            displayType: false
                        });
                },
                duration: '500ms',
                displayType: false
            })
        }
    });
    $('#signUpForm').Lc4eForm();
    $('#user\\.password,#user\\.repassword').next('.eye.icon').on({
        mousedown: function () {
            $(this).prev('input').attr('type', 'text');
        },
        mouseup: function () {
            $(this).prev('input').attr('type', 'password');
        }
    });

    $('.ui.icon.header > .icon').Lc4eHover('scaleSpin');

    $('#extendDes').on('click', function () {
        if (!$('#extendPanel').transition('is animating')) {
            var $icon = $(this).find('>i.icon');
            $('#extendPanel').transition('horizontal flip');
            if ($icon.hasClass('add')) {
                $icon.removeClass('add').addClass('minus');
            } else {
                $icon.removeClass('minus').addClass('add');
            }
        }
    });

    $('#extend\\.birth').Lc4eDateTimePicker({
        dayOfWeekStart: 1,
        lang: 'ch',
        rtl: true,
        showApplyButton: true
    });
});