/*!
 * Lc4e Javascript Library
 * author zhuxi - v1.0.0 (2015-04-09T11:23:51+0800)
 * http://www.lc4e.com/ | Released under MIT license
 * 
 * Include jquery (http://jquery.com/) semantic-ui (http://semantic-ui.com/)
 * animatescroll(http://plugins.compzets.com/animatescroll/)
 */

(function ($) {
    $.lc4e = $.lc4e || {};
    $.extend($.lc4e, {
        version: '1.0',
        Lc4eToDate: {
            unix2human: function (unixtime) {
                var dateObj = new Date(unixtime);
                var UnixTimeToDate = dateObj.getFullYear() + '-' + (dateObj.getMonth() + 1) + '-' + dateObj.getDate() + ' ' + $.lc4e.Lc4eToDate.p(dateObj.getHours()) + ':' + $.lc4e.Lc4eToDate.p(dateObj.getMinutes()) + ':' + $.lc4e.Lc4eToDate.p(dateObj.getSeconds());
                return UnixTimeToDate;
            },
            p: function (s) {
                return s < 10 ? '0' + s : s;
            }
        },
        Lc4ePJAX: {
            support: function () {
                return window.history && window.history.pushState && window.history.replaceState && !navigator.userAgent.match(/(iPod|iPhone|iPad|WebApps\/.+CFNetwork)/) && window.localStorage
            },
            active: false,
            successFunc: {}
        },
        signOut: function () {
            $.Lc4eAjax({
                url: '/SignOut',
                success: function (result) {
                    $.Lc4eResolveMessage(result, function () {
                        window.location.href = "/"
                    });
                }
            })
        }
    });
    /*extend ec6*/
    String.prototype.unix2human = function () {
        return $.lc4e.Lc4eToDate.unix2human(parseInt(this));
    };
    Number.prototype.unix2human = function () {
        return $.lc4e.Lc4eToDate.unix2human(this);
    };

    String.prototype.trimEnd = function (trimStr) {
        if (!trimStr) {
            return this;
        }
        var temp = this;
        while (true) {
            if (temp.substr(temp.length - trimStr.length, trimStr.length) != trimStr) {
                break;
            }
            temp = temp.substr(0, temp.length - trimStr.length);
        }
        return temp;
    };
    /* animate scroll */
    /* defines various easing effects*/
    $.easing['jswing'] = $.easing['swing'];
    $.extend($.easing, {
        def: 'easeOutQuad',
        swing: function (x, t, b, c, d) {
            return $.easing[$.easing.def](x, t, b, c, d);
        },
        easeInQuad: function (x, t, b, c, d) {
            return c * (t /= d) * t + b;
        },
        easeOutQuad: function (x, t, b, c, d) {
            return -c * (t /= d) * (t - 2) + b;
        },
        easeInOutQuad: function (x, t, b, c, d) {
            if ((t /= d / 2) < 1)
                return c / 2 * t * t + b;
            return -c / 2 * ((--t) * (t - 2) - 1) + b;
        },
        easeInCubic: function (x, t, b, c, d) {
            return c * (t /= d) * t * t + b;
        },
        easeOutCubic: function (x, t, b, c, d) {
            return c * ((t = t / d - 1) * t * t + 1) + b;
        },
        easeInOutCubic: function (x, t, b, c, d) {
            if ((t /= d / 2) < 1)
                return c / 2 * t * t * t + b;
            return c / 2 * ((t -= 2) * t * t + 2) + b;
        },
        easeInQuart: function (x, t, b, c, d) {
            return c * (t /= d) * t * t * t + b;
        },
        easeOutQuart: function (x, t, b, c, d) {
            return -c * ((t = t / d - 1) * t * t * t - 1) + b;
        },
        easeInOutQuart: function (x, t, b, c, d) {
            if ((t /= d / 2) < 1)
                return c / 2 * t * t * t * t + b;
            return -c / 2 * ((t -= 2) * t * t * t - 2) + b;
        },
        easeInQuint: function (x, t, b, c, d) {
            return c * (t /= d) * t * t * t * t + b;
        },
        easeOutQuint: function (x, t, b, c, d) {
            return c * ((t = t / d - 1) * t * t * t * t + 1) + b;
        },
        easeInOutQuint: function (x, t, b, c, d) {
            if ((t /= d / 2) < 1)
                return c / 2 * t * t * t * t * t + b;
            return c / 2 * ((t -= 2) * t * t * t * t + 2) + b;
        },
        easeInSine: function (x, t, b, c, d) {
            return -c * Math.cos(t / d * (Math.PI / 2)) + c + b;
        },
        easeOutSine: function (x, t, b, c, d) {
            return c * Math.sin(t / d * (Math.PI / 2)) + b;
        },
        easeInOutSine: function (x, t, b, c, d) {
            return -c / 2 * (Math.cos(Math.PI * t / d) - 1) + b;
        },
        easeInExpo: function (x, t, b, c, d) {
            return (t == 0) ? b : c * Math.pow(2, 10 * (t / d - 1)) + b;
        },
        easeOutExpo: function (x, t, b, c, d) {
            return (t == d) ? b + c : c * (-Math.pow(2, -10 * t / d) + 1) + b;
        },
        easeInOutExpo: function (x, t, b, c, d) {
            if (t == 0)
                return b;
            if (t == d)
                return b + c;
            if ((t /= d / 2) < 1)
                return c / 2 * Math.pow(2, 10 * (t - 1)) + b;
            return c / 2 * (-Math.pow(2, -10 * --t) + 2) + b;
        },
        easeInCirc: function (x, t, b, c, d) {
            return -c * (Math.sqrt(1 - (t /= d) * t) - 1) + b;
        },
        easeOutCirc: function (x, t, b, c, d) {
            return c * Math.sqrt(1 - (t = t / d - 1) * t) + b;
        },
        easeInOutCirc: function (x, t, b, c, d) {
            if ((t /= d / 2) < 1)
                return -c / 2 * (Math.sqrt(1 - t * t) - 1) + b;
            return c / 2 * (Math.sqrt(1 - (t -= 2) * t) + 1) + b;
        },
        easeInElastic: function (x, t, b, c, d) {
            var s = 1.70158;
            var p = 0;
            var a = c;
            if (t == 0)
                return b;
            if ((t /= d) == 1)
                return b + c;
            if (!p)
                p = d * .3;
            if (a < Math.abs(c)) {
                a = c;
                var s = p / 4;
            } else
                var s = p / (2 * Math.PI) * Math.asin(c / a);
            return -(a * Math.pow(2, 10 * (t -= 1)) * Math.sin((t * d - s) * (2 * Math.PI) / p)) + b;
        },
        easeOutElastic: function (x, t, b, c, d) {
            var s = 1.70158;
            var p = 0;
            var a = c;
            if (t == 0)
                return b;
            if ((t /= d) == 1)
                return b + c;
            if (!p)
                p = d * .3;
            if (a < Math.abs(c)) {
                a = c;
                var s = p / 4;
            } else
                var s = p / (2 * Math.PI) * Math.asin(c / a);
            return a * Math.pow(2, -10 * t) * Math.sin((t * d - s) * (2 * Math.PI) / p) + c + b;
        },
        easeInOutElastic: function (x, t, b, c, d) {
            var s = 1.70158;
            var p = 0;
            var a = c;
            if (t == 0)
                return b;
            if ((t /= d / 2) == 2)
                return b + c;
            if (!p)
                p = d * (.3 * 1.5);
            if (a < Math.abs(c)) {
                a = c;
                var s = p / 4;
            } else
                var s = p / (2 * Math.PI) * Math.asin(c / a);
            if (t < 1)
                return -.5 * (a * Math.pow(2, 10 * (t -= 1)) * Math.sin((t * d - s) * (2 * Math.PI) / p)) + b;
            return a * Math.pow(2, -10 * (t -= 1)) * Math.sin((t * d - s) * (2 * Math.PI) / p) * .5 + c + b;
        },
        easeInBack: function (x, t, b, c, d, s) {
            if (s == undefined)
                s = 1.70158;
            return c * (t /= d) * t * ((s + 1) * t - s) + b;
        },
        easeOutBack: function (x, t, b, c, d, s) {
            if (s == undefined)
                s = 1.70158;
            return c * ((t = t / d - 1) * t * ((s + 1) * t + s) + 1) + b;
        },
        easeInOutBack: function (x, t, b, c, d, s) {
            if (s == undefined)
                s = 1.70158;
            if ((t /= d / 2) < 1)
                return c / 2 * (t * t * (((s *= (1.525)) + 1) * t - s)) + b;
            return c / 2 * ((t -= 2) * t * (((s *= (1.525)) + 1) * t + s) + 2) + b;
        },
        easeInBounce: function (x, t, b, c, d) {
            return c - $.easing.easeOutBounce(x, d - t, 0, c, d) + b;
        },
        easeOutBounce: function (x, t, b, c, d) {
            if ((t /= d) < (1 / 2.75)) {
                return c * (7.5625 * t * t) + b;
            } else if (t < (2 / 2.75)) {
                return c * (7.5625 * (t -= (1.5 / 2.75)) * t + .75) + b;
            } else if (t < (2.5 / 2.75)) {
                return c * (7.5625 * (t -= (2.25 / 2.75)) * t + .9375) + b;
            } else {
                return c * (7.5625 * (t -= (2.625 / 2.75)) * t + .984375) + b;
            }
        },
        easeInOutBounce: function (x, t, b, c, d) {
            if (t < d / 2)
                return $.easing.easeInBounce(x, t * 2, 0, c, d) * .5 + b;
            return $.easing.easeOutBounce(x, t * 2 - d, 0, c, d) * .5 + c * .5 + b;
        }
    });

    $.fn.animatescroll = function (options) {

        var opts = $.extend({}, $.fn.animatescroll.defaults, options);
        if (typeof opts.onScrollStart == 'function') {
            opts.onScrollStart.call(this);
        }

        if (opts.element == "html,body") {
            var offset = this.offset().top;
            $(opts.element).stop().animate({
                scrollTop: offset - opts.padding
            }, opts.scrollSpeed, opts.easing);
        } else {
            $(opts.element).stop().animate({
                scrollTop: this.offset().top - this.parent().offset().top + this.parent().scrollTop() - opts.padding
            }, opts.scrollSpeed, opts.easing);
        }

        setTimeout(function () {
            if (typeof opts.onScrollEnd == 'function') {
                opts.onScrollEnd.call(this);
            }
        }, opts.scrollSpeed);
    };

    $.fn.animatescroll.defaults = {
        easing: "swing",
        scrollSpeed: 800,
        padding: 0,
        element: "html,body"
    };

    /*rewrite for semantic ui 2.0+ in 2015/08/16*/
    /*author:zhuxi*/
    $.fn.Lc4eDimmer = function (parameters) {
        var query = arguments[0],
            methodInvoked = (typeof query == 'string'),
            queryArguments = [].slice.call(arguments, 1);
        return this.each(function () {
            var settings = methodInvoked ? $.extend({}, $.fn.Lc4eDimmer.settings.config) : $.extend({}, $.fn.Lc4eDimmer.settings.config, query),
                $module = $(this),
                namespace = $.fn.Lc4eDimmer.settings.namespace,
                $dimmer,
                instance = $module.data(namespace),
                module;
            module = {
                initialize: function () {
                    if (!module.exist()) {
                        module.create(settings);
                    } else {
                        if (settings.dimmerName) {
                            $dimmer = $module.find(settings.selector.dimmer).filter('.' + settings.dimmerName);
                        }
                        else {
                            $dimmer = $module.find(settings.selector.dimmer);
                        }
                    }
                },
                create: function (options) {
                    if (query.hasOwnProperty('onHide') && typeof query.onHide === 'function') {
                        $module.data('onHide', options.onHide);
                        options.onHide = function (dimmable) {
                            $module.data('onHide').call($module, dimmable);
                            $module.dimmer('destroy');
                            module.destroy();
                        }
                    }
                    $dimmer = $($.fn.Lc4eDimmer.settings.template.dimmer(options));

                    $dimmer.append($.fn.Lc4eDimmer.settings.template.content(options));
                    if (options.blurring) {
                        $module.addClass('blurring');
                    }
                    $module.append($dimmer);

                    $module.dimmer(options);
                },
                instantiate: function () {
                    instance = module;
                    $module.data(namespace, instance);
                },
                exist: function () {
                    return $module.dimmer('has dimmer') && $module.data('onHide');
                },
                invoke: function (query) {
                    $module.dimmer(query, queryArguments);
                },
                destroy: function () {
                    if (settings.blurring) {
                        $module.removeClass('blurring');
                    }
                    $module.removeData('onHide').removeData(namespace);
                    settings = null;
                    instance = null;
                }
            };

            if (methodInvoked) {
                if (instance === undefined) {
                    module.initialize();
                }
                module.invoke(query);
            }
            else {
                module.initialize();
                if (settings.autoShow) {
                    $module.dimmer('show');
                }

            }
        });
    };

    $.fn.Lc4eDimmer.settings = {
        namespace: 'Lc4eDimmer',
        type: {
            common: '',
            simple: 'simple',
            page: 'page',
            inverted: 'inverted'
        },
        config: {
            opacity: 'auto',
            variation: false,
            dimmerName: false,
            type: 'common',
            closable: false,
            autoShow: true,
            content: '<div class="ui text loader">Loading</div>',
            on: false,
            blurring: true,
            transition: 'scale',
            useCSS: true,
            onShow: function () {
            },
            onHide: function () {
            },
            onChange: function () {
            },
            selector: {
                dimmer: '> .ui.dimmer',
                content: '.ui.dimmer > .content, .ui.dimmer > .content > .center'
            }
        },
        template: {
            dimmer: function (options) {
                return '<div class="ui '
                    + ($.fn.Lc4eDimmer.settings.type[options.type])
                    + ' dimmer"></div>';
            },
            content: function (options) {
                return '<div class="content">' + (options.content ? options.content : '') + '</div>'
            }
        }
    };

    /*rewrite for semantic ui 2.0+ in 2015/08/16*/
    /*author:zhuxi*/
    $.fn.Lc4eModal = function (parameters) {
        var query = arguments[0],
            methodInvoked = (typeof query == 'string'),
            queryArguments = [].slice.call(arguments, 1);
        return this.each(function () {
            var settings = methodInvoked ? $.extend({}, $.fn.Lc4eModal.settings.config) : $.extend({}, $.fn.Lc4eModal.settings.config, query),
                $module = $(this),
                namespace = $.fn.Lc4eModal.settings.namespace,
                id,
                $modal,
                instance = $module.data(namespace),
                module;
            settings.context = $module;
            module = {
                initialize: function () {
                    id = namespace + $.Lc4eRandom();
                    settings['id'] = id;
                    if (!module.exist()) {
                        module.create(settings);
                    } else {
                        module.instantiate();
                    }
                },
                create: function (options) {
                    if (query.hasOwnProperty('onHidden') && typeof query.onHidden === 'function') {
                        $module.data('onHidden', options.onHidden);
                        options.onHidden = function (modal) {
                            $module.data('onHidden').call($module, modal);
                            $modal.modal('destroy').remove();
                            module.destroy();
                        }
                    }
                    $modal = $($.fn.Lc4eModal.settings.template.modal(options));

                    $modal.append($.fn.Lc4eModal.settings.template.close(options))
                        .append($.fn.Lc4eModal.settings.template.title(options))
                        .append($.fn.Lc4eModal.settings.template.content(options))
                        .append($.fn.Lc4eModal.settings.template.button(options));
                    $(options['context']).append($modal);
                    $modal.modal(options);
                },
                instantiate: function () {
                    instance = module;
                    $module.data(namespace, instance);
                },
                exist: function () {
                    return $modal && !$('#' + id) ? true : false;
                },
                invoke: function (query) {
                    $modal.modal(query, queryArguments);
                },
                destroy: function () {
                    $module.removeData('onHidden').removeData(namespace);
                    settings = null;
                    id = null;
                    instance = null;
                }
            };

            if (methodInvoked) {
                if (instance === undefined) {
                    module.initialize();
                }
                module.invoke(query);
            }
            else {
                module.initialize();
                if (settings.autoShow) {
                    $modal.modal('show');
                }
            }
        });
    };
    $.fn.Lc4eModal.settings = {
        namespace: 'Lc4eModal',
        type: {
            stand: 'standard',
            basic: 'basic'
        },
        size: {
            small: 'small',
            large: 'large',
            full: 'fullscreen',
            long: 'long'
        },
        config: {
            id: '',
            closeIcon: false,
            size: 'small',
            type: 'stand',
            title: 'Message',
            content: 'this is a modal',
            allowMultiple: false,
            transition: 'horizontal flip',
            inverted: false,
            blurring: true,
            autoShow: true,
            context: 'body',
            closable: true, /*forbit closing modal by click dimmer*/
            dimmerSettings: {
                closable: false,
                useCSS: true
            },
            duration: 400,
            queue: false,
            buttons: {
                'Close': {
                    id: '',
                    name: 'Close',
                    icon: '',
                    content: '',
                    css: 'basic close'
                }
            },
            onShow: function () {

            }
            ,
            onVisible: function () {

            }
            ,
            onHide: function () {

            }
            ,
            onHidden: function () {

            }
            ,
            onApprove: function (el) {
                return true;
            }
            ,
            onDeny: function (el) {
                return true;
            }
        },
        template: {
            button: function (options) {
                var html = '<div class="actions">';
                for (var name in options.buttons) {
                    var button = options.buttons[name];
                    html += '<button ' + (button.id ? 'id="' + button.id + '"' : '')
                        + (button.name ? 'name="' + button.name + '"' : '' )
                        + ' class="ui ' + (button.css ? button.css : '' ) + ' button">'
                        + (button.icon ? '<i class="' + button.icon + ' icon"></i>' : '')
                        + (button.content ? button.content : name) + '</button>';
                }
                html += '</div>';
                return html;
            }
            ,
            modal: function (options) {
                return '<div ' + (options.id ? 'id="' + options.id + '"' : '') + ' class="ui ' + $.fn.Lc4eModal.settings.size[options.size] + ' ' + $.fn.Lc4eModal.settings.type[options.type] + ' modal"></div>';
            }
            ,
            title: function (options) {
                return options.title ? ('<div class="header">' + options.title + '</div>' ) : '';
            },
            content: function (options) {
                return '<div class="content">' + options.content + '</div>'
            },
            close: function (options) {
                return options.closeIcon ? '<i class="close icon"></i>' : '';
            }
        }
    };

    $.fn.Lc4eProgress = function (option, data) {
        var query = arguments[0],
            methodInvoked = (typeof query == 'string'),
            queryArguments = [].slice.call(arguments, 1);
        return this.each(function () {
            var settings = methodInvoked ? $.extend({}, $.fn.Lc4eProgress.settings.config) : $.extend({}, $.fn.Lc4eProgress.settings.config, query),
                $module = $(this),
                namespace = $.fn.Lc4eModal.settings.namespace,
                id,
                $progress,
                instance = $module.data(namespace),
                module;
            settings.context = $module;
            module = {
                initialize: function () {
                    id = namespace + $.Lc4eRandom();
                    settings['id'] = id;
                    if (!module.exist()) {
                        module.create(settings);
                    } else {
                        module.instantiate();
                    }
                },
                create: function (options) {
                    if (query.hasOwnProperty('onSuccess') && typeof query.onSuccess === 'function') {
                        $module.data('onSuccess', options.onSuccess);
                        options.onSuccess = function (modal) {
                            $module.data('onSuccess').call($module, modal);
                            $progress.progress('destroy').remove();
                            module.destroy();
                        }
                    }
                    $progress = $($.fn.Lc4eModal.settings.template.progress(options));

                    $progress.progress(options);
                },
                instantiate: function () {
                    instance = module;
                    $module.data(namespace, instance);
                },
                exist: function () {
                    return $progress && !$('#' + id) ? true : false;
                },
                invoke: function (query) {
                    $progress.progress(query, queryArguments);
                },
                destroy: function () {
                    $module.removeData('onHidden').removeData(namespace);
                    settings = null;
                    id = null;
                    instance = null;
                }
            };

            if (methodInvoked) {
                if (instance === undefined) {
                    module.initialize();
                }
                module.invoke(query);
            }
            else {
                module.initialize();
            }
        });
    };
    $.fn.Lc4eProgress.settings = {
        namespace: 'Lc4eProgress',
        size: {
            tiny: 'tiny',
            small: 'small',
            common: '',
            large: 'large',
            big: 'big'
        },
        config: {
            id: '',
            autoSuccess: true,
            indicating: true,
            color: '',
            total: 100,
            size: 'common',
            attach: '', /*top or bottom*/
            onChange: function (percent, value, total) {

            },
            onSuccess: function (total) {

            },
            onActive: function (value, total) {

            },
            onError: function (value, total) {

            },
            onWarning: function (value, total) {

            }
        },
        template: {
            progress: function (options) {
                return '<div class="ui '
                    + (options.indicating ? ' indicating ' : '')
                    + (options.color ? ' ' + options.color + ' ' : '')
                    + ($.fn.Lc4eProgress.settings.size[options.size])
                    + (options.attach ? ' ' + options.attach + ' attach ' : '')
                    + ' progress"><div class="bar"><div class="progress"></div></div>/div>';
            }
        }

    };
    $.fn.Lc4eStars = function (parameters) {
        var query = arguments[0],
            methodInvoked = (typeof query == 'string'),
            queryArguments = arguments[1];
        return this.each(function () {
            var settings = methodInvoked ? $.extend({}, $.fn.Lc4eStars.settings.config) : $.extend({}, $.fn.Lc4eStars.settings.config, query),
                module,
                id,
                $context = $(this),
                namespace = $.fn.Lc4eStars.settings.namespace,
                $lc4eStar = $('#' + $context.data(namespace));
            module = {

                initialize: function () {
                    id = namespace + $.Lc4eRandom();
                    settings['id'] = id;
                    if (!module.exist()) {
                        module.create(settings);
                    }
                },
                create: function () {
                    $context.addClass('fullStars');
                    $lc4eStar = $('<div id="' + id + '" class="inStars"></div>');
                    $lc4eStar.append($.fn.Lc4eStars.settings.template.star(settings.number));
                    $context.append($lc4eStar).data(namespace, id);
                },
                exist: function () {
                    return $('#' + id).length > 0 || $lc4eStar.length > 0;
                },
                invoke: function (name) {
                    if (typeof module[name] === 'function') {
                        module[name].call($context, queryArguments);
                    }
                },
                destroy: function () {
                    $context.removeClass('fullStars').
                        removeData(namespace);
                    $lc4eStar && $lc4eStar.remove();
                }
            };
            if (methodInvoked) {
                if (!$lc4eStar) {
                    module.initialize();
                }
                module.invoke(query);
            }
            else {
                module.initialize();
            }
        });
    };
    $.fn.Lc4eStars.settings = {
        namespace: 'Lc4eStars',
        config: {
            number: 100
        },
        template: {
            star: function (number) {
                var starHtml = [];
                for (var i = 0; i < number; i++) {
                    var s = 2.2 + Math.random();
                    var curR = 800 + Math.random() * 300;
                    starHtml.push('<div class="inStar" style="height:' + s + 'px;width:' + s + 'px; transform-origin: 0 0 ' + curR + 'px;transform:' + 'translate3d(0,0,-' + curR + 'px) rotateY(' + Math.random() * 360 + 'deg) rotateX(' + Math.random() * -50 + 'deg) "/>')
                }
                return starHtml.join('');
            }
        }
    };
    $.fn.Lc4eForm = function (parameters) {
        var query = arguments[0],
            methodInvoked = (typeof query == 'string'),
            queryArguments = arguments[1];
        return this.each(function () {
            var module,
                $form = $(this),
                $field = $form.find('.fieldValue');
            module = {
                initialize: function () {
                    $form.data('fieldsInfo', {});
                    var data = {
                        onValid: function () {
                            $(this).closest('.field').removeClass('error').addClass('success');
                            delete $form.data('fieldsInfo')[$(this).attr('name')];
                        },
                        onInvalid: function () {
                            $(this).closest('.field').removeClass('success');
                            $form.data('fieldsInfo')[$(this).attr('name')] = $(this).attr('prompt') ? $(this).attr('prompt') : $(this).closest('.fieldName').html();
                        }
                    }, validate = {}, $field = $form.find('.fieldValue');
                    $field.each(function () {
                        var $this = $(this),
                            rules = $this.attr('rules'),
                            name = $this.attr('name');
                        rules ? (rules = new Function("return " + rules)()) : (rules = []);
                        validate[name] = {};
                        validate[name]['identifier'] = name;
                        validate[name]['rules'] = rules;
                    });
                    data["on"] = $form.attr('observe-on') ? $form.attr('observe-on') : "blur";
                    data["fields"] = validate;
                    $form.form(data).attr('data-content', "please fill the form").popup({
                        inline: true,
                        position: 'right center',
                        hideOnScroll: false,
                        exclusive: true,
                        closable: false,
                        setFluidWidth: true,
                        on: 'manual',
                        preserve: true,
                        onShow: function (modal) {
                            $(this).find('.content').html($(modal).attr('data-content'));
                        }
                    });
                },
                reset: function () {

                    $form.data('fieldsInfo', {}).popup('hide');

                    $field.each(function () {
                        var $this = $(this);
                        $this.closest('.field').removeClass('success');
                    });
                    $form.form('reset');
                },
                submit: function (options) {
                    options = $.extend(true, $.fn.Lc4eForm.settings.config, options);
                    if ($form.form('is valid')) {
                        $form.popup('hide');
                        $.Lc4eAjax({
                                url: options.url,
                                data: $form.form('get values'),
                                success: function (data) {
                                    $.Lc4eResolveMessage(data, options.success, options.error);
                                }
                            }
                        )
                    } else {
                        var errorinfo = $form.data('fieldsInfo'), content = "";
                        for (var i in errorinfo) {
                            content += i + " is invalid\n";
                        }
                        $form.attr('data-content', content);

                        $form.popup('animate hide', function () {
                            $form.popup('show');
                        });
                    }
                },
                destroy: function () {

                },
                invoke: function (name) {
                    module[name] && module[name].call($form, queryArguments);
                }
            };
            if (methodInvoked) {
                module.invoke(query);
            } else {
                module.initialize();
            }
        });
    };
    $.fn.Lc4eForm.settings = {
        config: {
            success: function () {
            },
            error: function () {
            }
        }
    };
    $.fn.Lc4eHover = function (css) {
        return this.each(function () {
            $(this).addClass('allAnimation');
            $(this).hover(function () {
                $(this).addClass(css);
            }, function () {
                $(this).removeClass(css);
            })
        })
    };
    $.extend({
        Lc4eGetter: function (obj, path) {
            if (!path)
                return obj;
            var keys = path.split('.'), len = keys.length;
            for (var i = 0; i < len; i++) {
                if (obj) {
                    obj = obj[keys[i]];
                }
            }
            return obj;
        },
        /**
         * @return {string}
         * @return {string}
         */
        Lc4eRandomColor: function () {
            return '#' + ('00000' + (Math.random() * 0x1000000 << 0).toString(16)).slice(-6);
        },
        Lc4eLoading: function (parameters) {
            var config = {
                    id: "Lc4eLoading",
                    title: 'Loading',
                    type: 'squirm', //cube /
                    animation: 'vertical flip',
                    number: 5,
                    duration: '1.2s',
                    interval: 0.1
                },
                namespace = 'Lc4eLoading',
                query = arguments[0],
                methodInvoke = (typeof query === 'string'),
                queryArguments = arguments[1],
                $Loading = $('#' + config.id),
                $body = $('body'), module;
            module = {
                initialize: function () {
                    var settingsOld = $body.data(config.id);
                    if ((settingsOld && query.type && settingsOld.type != query.type) || $Loading.length == 0) {
                        module.create();
                    } else {
                        query = $.extend(true, config, query);
                        $body.data(query.id, {options: query});
                    }
                    module.invoke('show');
                },
                exist: function () {
                    return $Loading.length > 0;
                },
                create: function () {
                    query = $.extend(true, config, query);
                    $body.data(query.id, {options: query});
                    if ($Loading.length == 0) {
                        $Loading = $('<div id="' + query.id + '" class="lc4e-loading"><div class="title">' + query.title + '</div><div class="loadingArea"></div></div>');
                    }
                    var $Square = $Loading.find('.loadingArea');
                    $Square.empty();
                    if (module.template[query.type]) {
                        module.template[query.type].call(null, $Square);
                    }
                    $body.append($Loading);
                },
                setTitle: function (content) {
                    $Loading.find('.title').html(content ? content : '');
                },
                show: function () {
                    if ($Loading.data(namespace + 'timer')) {
                        module.hide();
                    }
                    if ($Loading.transition('is animating')) {
                        if ($Loading.hasClass('out')) {
                            $Loading.transition(query.animation + ' in');
                        }
                    } else {
                        if (!$Loading.transition('is visible')) {
                            $Loading.transition(query.animation + ' in');
                        }
                    }
                },
                hide: function () {
                    clearTimeout($Loading.data(namespace + 'timer'));
                    $Loading.data(namespace + 'timer', setTimeout(function () {
                        $Loading.transition(query.animation + ' out');
                    }, 3000));
                }
                ,
                invoke: function (name) {
                    query = $body.data(config.id).options;
                    module.setTitle(query.title);
                    if (typeof module[name] === 'function') {
                        module[name].call($Loading, queryArguments);
                    }
                }
                ,
                destroy: function () {
                    $body.removeData(config.id);
                    $Loading.remove();
                }
                ,
                template: {
                    cube: function ($Square) {
                        $Square.append($('<div class="cube"/>').css({
                                '-webkit-animation-delay': '0s',
                                'animation-delay': '0s'
                            })
                        ).append($('<div class="cube"/>').css({
                                '-webkit-animation-delay': '0.9s',
                                'animation-delay': '0.9s'
                            }));
                    }
                    ,
                    squirm: function ($Square) {
                        for (var i = 0; i < 5; i++) {
                            var delay = i == 0 ? 0 : i * 0.1 - 1.2,
                                $div = $('<div class="squirm"/>').css({
                                    '-webkit-animation-delay': delay + 's',
                                    'animation-delay': delay + 's'
                                });
                            $Square.append($div)
                        }
                    }
                    ,
                    foldingCube: function ($Square) {
                        $Square.append('<div class="foldingCube"><div/></div>')
                            .append($('<div class="foldingCube"><div style="-webkit-animation-delay:0.3s;animation-delay:0.3s"/></div>').css({
                                '-webkit-transform': 'scale(1.1) rotateZ(90deg)',
                                'transform': 'scale(1.1) rotateZ(90deg)'
                            }))
                            .append($('<div class="foldingCube"><div style="-webkit-animation-delay:0.9s;animation-delay:0.9s"/></div>').css({
                                '-webkit-transform': 'scale(1.1) rotateZ(270deg)',
                                'transform': 'scale(1.1) rotateZ(270deg)'
                            }))
                            .append($('<div class="foldingCube"><div style="-webkit-animation-delay:0.6s;animation-delay:0.6s"/></div>').css({
                                '-webkit-transform': 'scale(1.1) rotateZ(180deg)',
                                'transform': 'scale(1.1) rotateZ(180deg)'
                            }));
                    }
                }
            };
            if (methodInvoke) {
                if (module.exist()) {
                    module.invoke(query);
                }
            } else {
                module.initialize();
            }
        },
        Lc4eAjax: function (data) {
            data = $.extend(true, {
                cjson: false,
                pjax: false,
                target: '',
                token: false,
                options: {}
            }, data);
            if (data.cjson) {
                var tdata = data.data;
                tdata = JSON.stringify(tdata);
                data.data = tdata;
                data = $.extend(true, {
                    type: "post",
                    dataType: "json",
                    contentType: "application/json"
                }, data);
            } else if (data.pjax) {
                data = $.extend(true, {
                    type: 'get',
                    dataType: "html"
                }, data);
            } else {
                data = $.extend(true, {
                    type: 'post',
                    dataType: 'json'
                }, data);
            }
            if (data.hasOwnProperty("beforeSend") && typeof data.beforeSend === "function") {
                data.options["beforeSend"] = data["beforeSend"];
            }
            data.beforeSend = function (xhr, settings) {
                $.Lc4eLoading({
                    title: data.loading
                });
                if (data.token) {
                    var tk = 'l' + 'c' + '4' + 'e' + '-' + 't' + 'o' + 'k' + 'e' + 'n', lsuf = (data.url.length - 1).toString(), lpre = (data.url.length + 1).toString(), t = new Date().getTime().toString();
                    xhr.setRequestHeader(tk, lsuf + t + lpre);
                }
                if (data.pjax) {
                    xhr.setRequestHeader('X-PJAX', true);
                }
                if (typeof data.options.beforeSend === "function") {
                    data.options.beforeSend.call(this, xhr, settings);
                }
            };
            if (data.hasOwnProperty("success") && typeof data.success === "function") {
                data.options["success"] = data["success"];
            }

            if (data.pjax && data.target) {
                if (!$.lc4e.Lc4ePJAX.active) {
                    window.onpopstate = function (e) {
                        console.log(e);
                        if (e.state) {
                            $(e.state.data).replaceAll($(e.state.target));
                            document.title = e.state.title;
                            $.lc4e.Lc4ePJAX.successFunc[e.state.target + e.state.url].call();
                            $('body').animatescroll({
                                scrollSpeed: 500
                            });
                        }
                    }
                }
                data.success = function (resValue, textStatus) {
                    $(data.target).html(resValue);
                    var state = {
                        target: data.target,
                        data: $(data.target).prop('outerHTML'),
                        title: document.title,
                        url: $.lc4e.Lc4ePJAX.active ? data.url : window.location.pathname
                    };
                    $.lc4e.Lc4ePJAX.successFunc[state.target + state.url] = data.options.success;
                    if ($.lc4e.Lc4ePJAX.active) {
                        history.pushState(state, document.title, data.url);
                    } else {
                        history.replaceState(state, document.title);
                        $.lc4e.Lc4ePJAX.active = true;
                    }
                    if (typeof data.options.success === "function") {
                        data.options.success.call(this, resValue, textStatus);
                    }
                }

            }
            if (data.hasOwnProperty("complete") && typeof data.complete === "function") {
                data.options["complete"] = data["complete"];
            }
            data.complete = function (xhr, ts) {
                if (typeof data.options.complete === "function") {
                    data.options.complete.call(this, xhr, ts);
                }
                $.Lc4eLoading('hide');
            };
            return $.ajax(data);
        }

        ,
        Lc4eResolveMessage: function (returnVal, success, error) {
            if (returnVal) {
                $.Lc4eModal({
                    type: 'stand',
                    content: returnVal.message,
                    onHide: function () {
                        if (returnVal.result) {
                            success.call();
                        } else {
                            error.call();
                        }
                    }
                })
            }
        },
        /**
         * @return {string}
         */
        Lc4eRandom: function () {
            return (Math.random().toString(16) + '000000000').substr(2, 8);
        }
        ,
        Lc4eStars: function (options) {
            return $('body').Lc4eStars(options);
        }
        ,
        Lc4eModal: function (options) {
            return $("body").Lc4eModal(options);
        }
        ,
        Lc4eDimmer: function (options) {
            if (options) {
                options.type = 'page';
            } else {
                options = {type: 'page'}
            }
            return $('body').Lc4eDimmer(options);
        },
        Lc4eToDate: $.lc4e.Lc4eToDate.unix2human,
        Lc4eProgress: function (option, data) {
            return $("body").Lc4eProgress(option, data);
        },
        requestAnimationFrame: function (callback) {
            var requestAnimation = window.requestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame || window.oRequestAnimationFrame || window.msRequestAnimationFrame || function (callback) {
                    window.setTimeout(callback, 0);
                };
            return requestAnimation(callback);
        }
    });


    $.lc4e.common = function () {
        var $menu = $('#menu'), $configTool = $('#config-tool-options'), $floatTool = $('#floatTools');
        $menu.find('.ui.dropdown.item').dropdown({
            action: "nothing",
            transition: "horizontal flip",
            on: 'click'
        });
        $('#searchSite').on('focus', function () {
            $(this).addClass('expended');
        }).on('blur', function () {
            $(this).removeClass('expended')
        });

        $('#expendHeader').on('click', function () {
            $menu.toggleClass('expended');
        });

        $menu.find('.column div:first a').on('click', function () {
            $menu.find('>.column>.allmenus').transition({
                animation: "fly down",
                duration: 500,
                onComplete: function () {
                    $(this).toggleClass('menuhidden').removeClass("transition visible hidden").attr('style', '');
                }
            });
        });
        $configTool.find('.angle.double.left.icon').on('click', function () {
            if ($($configTool.find('.ui.animated.selection.list:not(.hidden)').transition('fade left').attr('data-parent')).transition('fade left').attr('id') == 'menu1') {
                $(this).addClass('transition hidden');
            }
        });
        $('#config-tool-cog').on('click', function () {
            $('#config-tool').toggleClass('closed');
        });

        $('html').visibility({
            offset: -1,
            observeChanges: false,
            once: false,
            continuous: false,
            onTopPassed: function () {
                $menu.addClass('fixed');
                clearTimeout($floatTool.data('timer'));
                if (!$floatTool.transition('is visible')) {
                    $floatTool.transition('fade left in');
                }
            },
            onTopPassedReverse: function () {
                $menu.removeClass('fixed');
                clearTimeout($floatTool.data('timer'));
                $floatTool.data('timer', setTimeout(function () {
                    console.log('fade out ');
                    $floatTool.transition('fade left out');
                }, 800));

            }
        });


        $('#userItem').find('img.ui.image').popup({
            position: 'bottom center',
            transition: "horizontal flip",
            popup: $('#userCardPop'),
            exclusive: false,
            hideOnScroll: false,
            on: 'click',
            prefer: 'opposite',
            closable: true,
            onVisible: function (model) {
                if ($(window).width() <= 768)
                    $(model).popup('reposition');
            }
        });

        $('#gtop').on('click', function () {
            $.requestAnimationFrame(function () {
                $('html').animatescroll({scrollSpeed: 2000, easing: 'easeOutBounce'})
            });
        });

        $menu.find('.left.menu .logo').Lc4eHover('animated infinite spin');

        $menu.find('[href]').on('click', function (e) {
                var $this = $(this), href = $this.attr('href'), title = $this.attr('title');
                e.preventDefault();
                $.Lc4eAjax({
                    url: href,
                    pjax: true,
                    title: 'loading' + title,
                    data: { //for ie disable cache
                        rand: new Date().getTime()
                    }
                    ,
                    target: '#mainContent',
                    success: function (data) {

                    }
                })
            }
        )
    };
    $.lc4e.common.call();
})
(jQuery);