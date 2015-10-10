/**
 * Created by teddy on 2015/8/30.
 */
$(function () {
    $.lc4e.signin = {
        ready: function () {
            var $captchaimg = $('#captchaimg');
            $captchaimg.on('click', function () {
                if (!$captchaimg.transition('is animating')) {
                    $captchaimg.transition({
                        animation: 'vertical flip out',
                        onComplete: function () {
                            $captchaimg.attr('src', '/captcha?rand=' + new Date().getTime())
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
            $('#signInForm').Lc4eForm();

            $('#user\\.password,#user\\.repassword').next('.eye.icon').on({
                mousedown: function () {
                    $(this).prev('input').attr('type', 'text');
                },
                mouseup: function () {
                    $(this).prev('input').attr('type', 'password');
                }
            });

            $('.ui.icon.header > .icon').Lc4eHover('scaleSpin');
        }
    };
    $.lc4e.signin.ready();
});