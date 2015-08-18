/**
 * Created by teddy on 2015/8/5.
 */
'use strict';
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
    $('#signInForm').parseForm();
    $('#signIn').on('click', function () {
        $('#signInForm').Lc4eSubmit({
            url: '/member/signin', success: function () {
                window.location.href = "/";
            }
        });
    });
    $('#reset').on('click', function () {
        $('#signInForm').resetForm();
    });

    $('#user\\.password').next('.eye.icon').on({
        mousedown: function () {
            $(this).prev('input').attr('type', 'text');
        },
        mouseup: function () {
            $(this).prev('input').attr('type', 'password');
        }
    });

    $('.ui.icon.header > .users.icon').Lc4eHover('scaleSpin');
});