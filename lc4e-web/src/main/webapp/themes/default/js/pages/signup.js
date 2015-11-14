/**
 * Created by teddy on 2015/8/30.
 */
$(function () {
    $.lc4e.signup = {
        ready: function () {
            $.lc4e.signup.run();
            $.lc4e.signup.bindEvent();
        },
        run: function () {

        },
        bindEvent: function () {
            var $form = $('#signUpForm');
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
            $form.Lc4eForm({
                success: function () {
                    window.location.href = "/";
                },
                error: function () {
                },
                complete: function () {
                }
            });
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
                var $extendPanel = $('#extendPanel');
                if (!$extendPanel.transition('is animating')) {
                    var $icon = $(this).find('>i.icon');
                    $extendPanel.transition('horizontal flip');
                    if ($icon.hasClass('add')) {
                        $icon.removeClass('add').addClass('minus');
                    } else {
                        $icon.removeClass('minus').addClass('add');
                    }
                }
            });

            $('#extend\\.birth').Lc4eDateTimePicker({
                dayOfWeekStart: 1,
                timepicker: false,
                format: 'yyyy-MM-dd',
                showApplyButton: true
            });
        }
    };
    $.lc4e.signup.ready();
});