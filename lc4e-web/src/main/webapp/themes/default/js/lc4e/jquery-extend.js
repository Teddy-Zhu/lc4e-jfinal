/*!
 * Lc4e Javascript Library
 * author zhuxi - v1.0.0 (2015-04-09T11:23:51+0800 Created)
 * update 2015-9-13 20:08:53
 * http://www.lc4e.com/ | Released under MIT license
 * 
 * Include jquery (http://jquery.com/) semantic-ui (http://semantic-ui.com/)
 * datetimepicker (http://xdsoft.net/jqplugins/datetimepicker/)
 */

$.lc4e = $.lc4e || {};
$.extend($.lc4e, {
    version: '1.0',
    init: function (options) {
        var query = arguments[0],
            methodInvoked = (typeof query == 'string'),
            queryArguments = [].slice.call(arguments, 1);
        if (methodInvoked) {
            $.lc4e[query].run();
        } else {
            $.lc4e[query][queryArguments['method']].call();
        }
    },
    convertArray: function (obj, value, newValue) {
        if (!$.isPlainObject(obj)) {
            return obj;
        } else if ($.isPlainObject(value)) {
            var replaceArray = [];
            for (var v in value) {
                replaceArray.push(v);
            }
            for (var p in obj) {
                if (replaceArray.indexOf(obj[p]) > -1) {
                    obj[p] = value[obj[p]];
                }
            }
            return obj;
        } else if (newValue) {
            for (var p in obj) {
                if (obj[p] == value) {
                    obj[p] = newValue;
                }
            }
            return obj;
        } else {
            return obj;
        }
    },
    removeArray: function (obj, array) {
        if (!$.isPlainObject(obj)) {
            return obj;
        } else if ($.isArray(array)) {
            for (var i = 0, len = array.length; i < len; i++) {
                delete obj[array[i]];
            }
            return obj;
        } else if (typeof array === 'string') {
            delete obj[array];
        } else {
            return obj;
        }
    },
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
    HighlightedDate: function (date, desc, style) {
        return {
            date: date,
            desc: desc,
            style: style
        };
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
/*  fix for ie8- */
if (!window.getComputedStyle) {
    window.getComputedStyle = function (el, pseudo) {
        this.el = el;
        this.getPropertyValue = function (prop) {
            var re = /(\-([a-z]){1})/g;
            if (prop === 'float') {
                prop = 'styleFloat';
            }
            if (re.test(prop)) {
                prop = prop.replace(re, function (a, b, c) {
                    return c.toUpperCase();
                });
            }
            return el.currentStyle[prop] || null;
        };
        return this;
    };
}
if (!Array.prototype.indexOf) {
    Array.prototype.indexOf = function (obj, start) {
        var i, j;
        for (i = (start || 0), j = this.length; i < j; i += 1) {
            if (this[i] === obj) {
                return i;
            }
        }
        return -1;
    };
}
Date.prototype.countDaysInMonth = function () {
    return new Date(this.getFullYear(), this.getMonth() + 1, 0).getDate();
};
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
Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S": this.getMilliseconds()
    };
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4
            - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1
                ? o[k]
                : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
};

Date.parseDate = function (a, c) {
    return new Date(Date.parse(a.replace(/-/g, '/').replace(/T/g, ' '))).format(c);
};

Date.prototype.isLeapYear = function () {
    var a = this.getFullYear();
    return ((a & 3) == 0 && (a % 100 || (a % 400 == 0 && a)));
};

String.escape = function (a) {
    return a.replace(/('|\\)/g, "\\$1");
};
String.leftPad = function (d, b, c) {
    var a = String(d);
    if (c == null) {
        c = " ";
    }
    while (a.length < b) {
        a = c + a;
    }
    return a;
};

//mousewheel
var toFix = ['wheel', 'mousewheel', 'DOMMouseScroll', 'MozMousePixelScroll'],
    toBind = ( 'onwheel' in document || document.documentMode >= 9 ) ?
        ['wheel'] : ['mousewheel', 'DomMouseScroll', 'MozMousePixelScroll'],
    slice = Array.prototype.slice,
    nullLowestDeltaTimeout, lowestDelta;

if ($.event.fixHooks) {
    for (var i = toFix.length; i;) {
        $.event.fixHooks[toFix[--i]] = $.event.mouseHooks;
    }
}

var special = $.event.special.mousewheel = {
    version: '3.1.12',

    setup: function () {
        if (this.addEventListener) {
            for (var i = toBind.length; i;) {
                this.addEventListener(toBind[--i], handler, false);
            }
        } else {
            this.onmousewheel = handler;
        }
        // Store the line height and page height for this particular element
        $.data(this, 'mousewheel-line-height', special.getLineHeight(this));
        $.data(this, 'mousewheel-page-height', special.getPageHeight(this));
    },

    teardown: function () {
        if (this.removeEventListener) {
            for (var i = toBind.length; i;) {
                this.removeEventListener(toBind[--i], handler, false);
            }
        } else {
            this.onmousewheel = null;
        }
        // Clean up the data we added to the element
        $.removeData(this, 'mousewheel-line-height');
        $.removeData(this, 'mousewheel-page-height');
    },

    getLineHeight: function (elem) {
        var $elem = $(elem),
            $parent = $elem['offsetParent' in $.fn ? 'offsetParent' : 'parent']();
        if (!$parent.length) {
            $parent = $('body');
        }
        return parseInt($parent.css('fontSize'), 10) || parseInt($elem.css('fontSize'), 10) || 16;
    },

    getPageHeight: function (elem) {
        return $(elem).height();
    },

    settings: {
        adjustOldDeltas: true, // see shouldAdjustOldDeltas() below
        normalizeOffset: true  // calls getBoundingClientRect for each event
    }
};

$.fn.extend({
    mousewheel: function (fn) {
        return fn ? this.bind('mousewheel', fn) : this.trigger('mousewheel');
    },

    unmousewheel: function (fn) {
        return this.unbind('mousewheel', fn);
    }
});


function handler(event) {
    var orgEvent = event || window.event,
        args = slice.call(arguments, 1),
        delta = 0,
        deltaX = 0,
        deltaY = 0,
        absDelta = 0,
        offsetX = 0,
        offsetY = 0;
    event = $.event.fix(orgEvent);
    event.type = 'mousewheel';

    if ('detail' in orgEvent) {
        deltaY = orgEvent.detail * -1;
    }
    if ('wheelDelta' in orgEvent) {
        deltaY = orgEvent.wheelDelta;
    }
    if ('wheelDeltaY' in orgEvent) {
        deltaY = orgEvent.wheelDeltaY;
    }
    if ('wheelDeltaX' in orgEvent) {
        deltaX = orgEvent.wheelDeltaX * -1;
    }

    if ('axis' in orgEvent && orgEvent.axis === orgEvent.HORIZONTAL_AXIS) {
        deltaX = deltaY * -1;
        deltaY = 0;
    }

    delta = deltaY === 0 ? deltaX : deltaY;

    if ('deltaY' in orgEvent) {
        deltaY = orgEvent.deltaY * -1;
        delta = deltaY;
    }
    if ('deltaX' in orgEvent) {
        deltaX = orgEvent.deltaX;
        if (deltaY === 0) {
            delta = deltaX * -1;
        }
    }

    if (deltaY === 0 && deltaX === 0) {
        return;
    }

    if (orgEvent.deltaMode === 1) {
        var lineHeight = $.data(this, 'mousewheel-line-height');
        delta *= lineHeight;
        deltaY *= lineHeight;
        deltaX *= lineHeight;
    } else if (orgEvent.deltaMode === 2) {
        var pageHeight = $.data(this, 'mousewheel-page-height');
        delta *= pageHeight;
        deltaY *= pageHeight;
        deltaX *= pageHeight;
    }

    absDelta = Math.max(Math.abs(deltaY), Math.abs(deltaX));

    if (!lowestDelta || absDelta < lowestDelta) {
        lowestDelta = absDelta;

        if (shouldAdjustOldDeltas(orgEvent, absDelta)) {
            lowestDelta /= 40;
        }
    }

    if (shouldAdjustOldDeltas(orgEvent, absDelta)) {
        delta /= 40;
        deltaX /= 40;
        deltaY /= 40;
    }
    delta = Math[delta >= 1 ? 'floor' : 'ceil'](delta / lowestDelta);
    deltaX = Math[deltaX >= 1 ? 'floor' : 'ceil'](deltaX / lowestDelta);
    deltaY = Math[deltaY >= 1 ? 'floor' : 'ceil'](deltaY / lowestDelta);
    if (special.settings.normalizeOffset && this.getBoundingClientRect) {
        var boundingRect = this.getBoundingClientRect();
        offsetX = event.clientX - boundingRect.left;
        offsetY = event.clientY - boundingRect.top;
    }

    event.deltaX = deltaX;
    event.deltaY = deltaY;
    event.deltaFactor = lowestDelta;
    event.offsetX = offsetX;
    event.offsetY = offsetY;

    event.deltaMode = 0;

    args.unshift(event, delta, deltaX, deltaY);

    if (nullLowestDeltaTimeout) {
        clearTimeout(nullLowestDeltaTimeout);
    }
    nullLowestDeltaTimeout = setTimeout(nullLowestDelta, 200);

    return ($.event.dispatch || $.event.handle).apply(this, args);
}

function nullLowestDelta() {
    lowestDelta = null;
}

function shouldAdjustOldDeltas(orgEvent, absDelta) {
    return special.settings.adjustOldDeltas && orgEvent.type === 'mousewheel' && absDelta % 120 === 0;
}

$.easing.jswing = $.easing.swing;
$.extend($.easing, {
    def: "easeOutQuad",
    swing: function(e, f, a, h, g) {
        return $.easing[$.easing.def](e, f, a, h, g)
    },
    easeInQuad: function(e, f, a, h, g) {
        return h * (f /= g) * f + a
    },
    easeOutQuad: function(e, f, a, h, g) {
        return -h * (f /= g) * (f - 2) + a
    },
    easeInOutQuad: function(e, f, a, h, g) {
        if ((f /= g / 2) < 1) {
            return h / 2 * f * f + a
        }
        return -h / 2 * ((--f) * (f - 2) - 1) + a
    },
    easeInCubic: function(e, f, a, h, g) {
        return h * (f /= g) * f * f + a
    },
    easeOutCubic: function(e, f, a, h, g) {
        return h * ((f = f / g - 1) * f * f + 1) + a
    },
    easeInOutCubic: function(e, f, a, h, g) {
        if ((f /= g / 2) < 1) {
            return h / 2 * f * f * f + a
        }
        return h / 2 * ((f -= 2) * f * f + 2) + a
    },
    easeInQuart: function(e, f, a, h, g) {
        return h * (f /= g) * f * f * f + a
    },
    easeOutQuart: function(e, f, a, h, g) {
        return -h * ((f = f / g - 1) * f * f * f - 1) + a
    },
    easeInOutQuart: function(e, f, a, h, g) {
        if ((f /= g / 2) < 1) {
            return h / 2 * f * f * f * f + a
        }
        return -h / 2 * ((f -= 2) * f * f * f - 2) + a
    },
    easeInQuint: function(e, f, a, h, g) {
        return h * (f /= g) * f * f * f * f + a
    },
    easeOutQuint: function(e, f, a, h, g) {
        return h * ((f = f / g - 1) * f * f * f * f + 1) + a
    },
    easeInOutQuint: function(e, f, a, h, g) {
        if ((f /= g / 2) < 1) {
            return h / 2 * f * f * f * f * f + a
        }
        return h / 2 * ((f -= 2) * f * f * f * f + 2) + a
    },
    easeInSine: function(e, f, a, h, g) {
        return -h * Math.cos(f / g * (Math.PI / 2)) + h + a
    },
    easeOutSine: function(e, f, a, h, g) {
        return h * Math.sin(f / g * (Math.PI / 2)) + a
    },
    easeInOutSine: function(e, f, a, h, g) {
        return -h / 2 * (Math.cos(Math.PI * f / g) - 1) + a
    },
    easeInExpo: function(e, f, a, h, g) {
        return (f == 0) ? a : h * Math.pow(2, 10 * (f / g - 1)) + a
    },
    easeOutExpo: function(e, f, a, h, g) {
        return (f == g) ? a + h : h * (-Math.pow(2, -10 * f / g) + 1) + a
    },
    easeInOutExpo: function(e, f, a, h, g) {
        if (f == 0) {
            return a
        }
        if (f == g) {
            return a + h
        }
        if ((f /= g / 2) < 1) {
            return h / 2 * Math.pow(2, 10 * (f - 1)) + a
        }
        return h / 2 * (-Math.pow(2, -10 * --f) + 2) + a
    },
    easeInCirc: function(e, f, a, h, g) {
        return -h * (Math.sqrt(1 - (f /= g) * f) - 1) + a
    },
    easeOutCirc: function(e, f, a, h, g) {
        return h * Math.sqrt(1 - (f = f / g - 1) * f) + a
    },
    easeInOutCirc: function(e, f, a, h, g) {
        if ((f /= g / 2) < 1) {
            return -h / 2 * (Math.sqrt(1 - f * f) - 1) + a
        }
        return h / 2 * (Math.sqrt(1 - (f -= 2) * f) + 1) + a
    },
    easeInElastic: function(f, h, e, l, k) {
        var i = 1.70158;
        var j = 0;
        var g = l;
        if (h == 0) {
            return e
        }
        if ((h /= k) == 1) {
            return e + l
        }
        if (!j) {
            j = k * 0.3
        }
        if (g < Math.abs(l)) {
            g = l;
            var i = j / 4
        } else {
            var i = j / (2 * Math.PI) * Math.asin(l / g)
        }
        return -(g * Math.pow(2, 10 * (h -= 1)) * Math.sin((h * k - i) * (2 * Math.PI) / j)) + e
    },
    easeOutElastic: function(f, h, e, l, k) {
        var i = 1.70158;
        var j = 0;
        var g = l;
        if (h == 0) {
            return e
        }
        if ((h /= k) == 1) {
            return e + l
        }
        if (!j) {
            j = k * 0.3
        }
        if (g < Math.abs(l)) {
            g = l;
            var i = j / 4
        } else {
            var i = j / (2 * Math.PI) * Math.asin(l / g)
        }
        return g * Math.pow(2, -10 * h) * Math.sin((h * k - i) * (2 * Math.PI) / j) + l + e
    },
    easeInOutElastic: function(f, h, e, l, k) {
        var i = 1.70158;
        var j = 0;
        var g = l;
        if (h == 0) {
            return e
        }
        if ((h /= k / 2) == 2) {
            return e + l
        }
        if (!j) {
            j = k * (0.3 * 1.5)
        }
        if (g < Math.abs(l)) {
            g = l;
            var i = j / 4
        } else {
            var i = j / (2 * Math.PI) * Math.asin(l / g)
        }
        if (h < 1) {
            return -0.5 * (g * Math.pow(2, 10 * (h -= 1)) * Math.sin((h * k - i) * (2 * Math.PI) / j)) + e
        }
        return g * Math.pow(2, -10 * (h -= 1)) * Math.sin((h * k - i) * (2 * Math.PI) / j) * 0.5 + l + e
    },
    easeInBack: function(e, f, a, i, h, g) {
        if (g == undefined) {
            g = 1.70158
        }
        return i * (f /= h) * f * ((g + 1) * f - g) + a
    },
    easeOutBack: function(e, f, a, i, h, g) {
        if (g == undefined) {
            g = 1.70158
        }
        return i * ((f = f / h - 1) * f * ((g + 1) * f + g) + 1) + a
    },
    easeInOutBack: function(e, f, a, i, h, g) {
        if (g == undefined) {
            g = 1.70158
        }
        if ((f /= h / 2) < 1) {
            return i / 2 * (f * f * (((g *= (1.525)) + 1) * f - g)) + a
        }
        return i / 2 * ((f -= 2) * f * (((g *= (1.525)) + 1) * f + g) + 2) + a
    },
    easeInBounce: function(e, f, a, h, g) {
        return h - jQuery.easing.easeOutBounce(e, g - f, 0, h, g) + a
    },
    easeOutBounce: function(e, f, a, h, g) {
        if ((f /= g) < (1 / 2.75)) {
            return h * (7.5625 * f * f) + a
        } else {
            if (f < (2 / 2.75)) {
                return h * (7.5625 * (f -= (1.5 / 2.75)) * f + 0.75) + a
            } else {
                if (f < (2.5 / 2.75)) {
                    return h * (7.5625 * (f -= (2.25 / 2.75)) * f + 0.9375) + a
                } else {
                    return h * (7.5625 * (f -= (2.625 / 2.75)) * f + 0.984375) + a
                }
            }
        }
    },
    easeInOutBounce: function(e, f, a, h, g) {
        if (f < g / 2) {
            return $.easing.easeInBounce(e, f * 2, 0, h, g) * 0.5 + a
        }
        return $.easing.easeOutBounce(e, f * 2 - g, 0, h, g) * 0.5 + h * 0.5 + a
    }
});
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
                if (options.hasOwnProperty('onHide') && typeof options.onHide === 'function') {
                    $module.data('onHide', options.onHide);
                }
                options.onHide = function (dimmable) {
                    if (typeof   $module.data('onHide') === 'function') {
                        $module.data('onHide').call($module, dimmable);
                    }
                    $module.dimmer('destroy');
                };
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
                if (typeof module[query] === 'function') {
                    module[query].call();
                } else {
                    $module.dimmer(query, queryArguments);
                }

            },
            destroy: function () {
                $module.dimmer('destroy');
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
        blurring: false,
        transition: 'fade',
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
                if (typeof query.onHidden === 'function') {
                    $module.data('onHidden', options.onHidden);
                }
                options.onHidden = function (modal) {
                    if (typeof  $module.data('onHidden') === 'function') {
                        $module.data('onHidden').call($module, modal);
                    }
                    $modal.modal('destroy').remove();
                    module.destroy();
                };
                $modal = $($.fn.Lc4eModal.settings.template.modal(options));

                $modal.append($.fn.Lc4eModal.settings.template.close(options))
                    .append($.fn.Lc4eModal.settings.template.title(options))
                    .append($.fn.Lc4eModal.settings.template.content(options))
                    .append($.fn.Lc4eModal.settings.template.button(options));
                $(options['context']).append($modal);
                $modal.modal(options);
                $modal.data(namespace, module);
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
            instance.invoke(query);
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
        stand: '',
        basic: 'basic'
    },
    size: {
        small: 'small',
        large: 'large',
        full: 'fullscreen',
        long: 'long scrolling',
        tiny: 'tiny'
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
        blurring: false,
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
                css: 'basic ok'
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
                $context.removeClass('fullStars').removeData(namespace);
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
$.fn.form.settings = $.extend(true, {
    rules: {
        remote: function (value, text) {
            var $this = $(this),
                id = $this.attr('id'),
                name = $this.attr('name'),
                data = {},
                $form = $(this.context),
                result = true;
            data[name] = $.trim(value);
            if (data[name]) {
                $.Lc4eAjax({
                    url: text,
                    data: data,
                    async: false,
                    success: function (data) {
                        if (data && !data.result) {
                            $form.data('validate', false).data('errorInfos')[id] = data.message;
                        } else {
                            delete  $form.data('validate', false).data('errorInfos')[id];
                        }
                        result = !!data.result;
                    }
                });
            }
            return result;
        }
    }
}, $.fn.form.settings);
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
                $form.data('validate', true).data('ignore', []);
                var data = {
                    onValid: function () {
                        var $this = $(this), $field = $this.closest('.field');
                        $field.removeClass('error').addClass('success');
                    },
                    onInvalid: function (errors) {
                        var $this = $(this), $field = $this.closest('.field');
                        $field.removeClass('success');
                        $field.popup({
                            position: 'right center',
                            target: $field,
                            revalidate: false,
                            on: 'manual',
                            html: '<div class="nobr">' + errors.join('</div>\n<div class="nobr">') + '</div>',
                            onShow: function () {
                                this.css('max-width', 'none');
                            }
                        }).popup('show');
                        setTimeout(function () {
                            $field.popup('hide')
                        }, 2000)
                    }
                }, validate = {};

                $field.each(function () {
                    var $this = $(this),
                        rules = $this.attr('data-rules'),
                        name = $this.attr('name'),
                        optional = $this.attr('data-optional'),
                        ignore = $this.attr('data-ignore');
                    rules ? (rules = new Function("return " + rules)()) : (rules = []);
                    optional ? (optional = true) : (optional = false);
                    ignore ? (ignore = true) : (ignore = false);
                    validate[name] = {
                        identifier: name,
                        rules: rules
                    };
                    optional && (validate[name]['optional'] = true);
                    ignore && $form.data('ignore').push(name);
                });
                data["on"] = $form.attr('observe-on') ? $form.attr('observe-on') : "blur";
                data["fields"] = validate;
                data["keyboardShortcuts"] = false;
                $form.form(data);
                module.bindEvent();
            },
            bindEvent: function () {
                $form.find('button.lc4eSubmit').off('click').on('click', function (event) {
                    var $this = $(this);
                    $this.removeClass('loading').prop('disabled', false);
                    $form.Lc4eForm('submit', $.extend(true, {
                        success: function () {
                            $this.removeClass('loading').prop('disabled', false);
                            window.location.href = "/";
                        },
                        error: function () {
                            $this.removeClass('loading').prop('disabled', false);
                        }
                    }, query));
                });
                $form.find('button.lc4eReset').off('click').on('click', function (event) {
                    var $this = $(this);
                    $this.addClass('loading').prop('disabled', true);
                    $form.Lc4eForm('reset');
                    $this.removeClass('loading').prop('disabled', false);
                });
                $form.find('input.fieldValue:not([type="checkbox"]):not([type="radio"]):last').on('keydown', function (e) {
                    if (e.which == 13 || e.keyCode == 13) {
                        $form.find('button.lc4eSubmit').trigger('click');
                    }
                });
            },
            reset: function () {
                $form.data('validate', true).popup('hide');
                $field.each(function () {
                    var $this = $(this);
                    $this.closest('.field').removeClass('success');
                });
                $form.form('reset');
            },
            submit: function (options) {
                options = $.extend(true, $.fn.Lc4eForm.settings.config, options);
                if ($form.hasClass('isSubmiting')) {
                    return;
                } else {
                    $form.addClass('isSubmiting');
                }
                options.start.call($form);
                if (!$form.hasClass('validating') && $form.form('is valid')) {
                    $form.popup('hide');
                    $.Lc4eAjax({
                            url: options.url ? options.url : $form.attr('data-url'),
                            data: $.lc4e.convertArray($.lc4e.removeArray($form.form('get values'), $form.data('ignore')), {
                                'on': true,
                                'off': false
                            }),
                            showLoad: $form.attr('data-loading'),
                            success: function (data) {
                                $.Lc4eResolveMessage(data, options.success, options.error);
                            },
                            complete: function () {
                                options.complete.call($form);
                                $form.removeClass('isSubmiting');
                            }
                        }
                    )
                }
                else {
                    $form.removeClass('isSubmiting');

                    options.complete.call($form);
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
        start: function () {
        },
        complete: function () {
        },
        success: function () {
        },
        error: function () {
        }
    }
};
$.fn.Lc4eHover = function (css) {
    return this.each(function () {
        $(this).addClass('animated allAnimation');
        $(this).hover(function () {
            $(this).addClass(css);
        }, function () {
            $(this).removeClass(css);
        })
    })
};
$.fn.Lc4eFocusBlur = function (css) {
    return this.each(function () {
        var $this = $(this);
        $this.on('focus', function () {
            $.requestAnimationFrame(function () {
                $this.addClass(css);
            })
        }).on('blur', function () {
            $.requestAnimationFrame(function () {
                $this.removeClass(css);
            })
        });
    })
};
$.fn.Lc4eScroller = function (percent) {
    var query = arguments[0],
        methodInvoked = (typeof query == 'string'),
        namespace = $.fn.Lc4eScroller.settings.namespace,
        queryArguments = arguments[1];
    return this.each(function () {
        var $scroller = $(this),
            module;
        module = {
            create: function () {
                var move = 0,
                    timebox,
                    parentHeight,
                    height,
                    scrollbar,
                    scroller,
                    maximumOffset = 100,
                    start = false,
                    startY = 0,
                    startTop = 0,
                    h1 = 0,
                    touchStart = false,
                    startTopScroll = 0,
                    calcOffset = function () {
                    },
                    pointerEventToXY = function (e) {
                        var out = {x: 0, y: 0},
                            touch;
                        if (e.type === 'touchstart' || e.type === 'touchmove' || e.type === 'touchend' || e.type === 'touchcancel') {
                            touch = e.originalEvent.touches[0] || e.originalEvent.changedTouches[0];
                            out.x = touch.clientX;
                            out.y = touch.clientY;
                        } else if (e.type === 'mousedown' || e.type === 'mouseup' || e.type === 'mousemove' || e.type === 'mouseover' || e.type === 'mouseout' || e.type === 'mouseenter' || e.type === 'mouseleave') {
                            out.x = e.clientX;
                            out.y = e.clientY;
                        }
                        return out;
                    };
                timebox = $scroller.children().eq(0);
                parentHeight = $scroller[0].clientHeight;
                height = timebox[0].offsetHeight;
                scrollbar = $('<div class="datetimepicker_scrollbar"></div>');
                scroller = $('<div class="datetimepicker_scroller"></div>');
                scrollbar.append(scroller);

                $scroller.addClass('datetimepicker_scroller_box').append(scrollbar);
                calcOffset = function calcOffset(event) {
                    var offset = pointerEventToXY(event).y - startY + startTopScroll;
                    if (offset < 0) {
                        offset = 0;
                    }
                    if (offset + scroller[0].offsetHeight > h1) {
                        offset = h1 - scroller[0].offsetHeight;
                    }
                    $scroller.trigger('scroll_element.' + namespace, [maximumOffset ? offset / maximumOffset : 0]);
                };

                scroller
                    .on('touchstart.' + namespace + ' mousedown.' + namespace, function (event) {
                        if (!parentHeight) {
                            $scroller.trigger('resize_scroll.' + namespace, [percent]);
                        }

                        startY = pointerEventToXY(event).y;
                        startTopScroll = parseInt(scroller.css('margin-top'), 10);
                        h1 = scrollbar[0].offsetHeight;

                        if (event.type === 'mousedown') {
                            if (document) {
                                $(document.body).addClass('datetimepicker_noselect');
                            }
                            $([document.body, window]).on('mouseup.' + namespace, function arguments_callee() {
                                $([document.body, window]).off('mouseup.' + namespace, arguments_callee)
                                    .off('mousemove.' + namespace, calcOffset)
                                    .removeClass('datetimepicker_noselect');
                            });
                            $(document.body).on('mousemove.' + namespace, calcOffset);
                        } else {
                            touchStart = true;
                            event.stopPropagation();
                            event.preventDefault();
                        }
                    })
                    .on('touchmove', function (event) {
                        if (touchStart) {
                            event.preventDefault();
                            calcOffset(event);
                        }
                    })
                    .on('touchend touchcancel', function (event) {
                        touchStart = false;
                        startTopScroll = 0;
                    });

                $scroller
                    .on('scroll_element.' + namespace, function (event, percentage) {
                        if (!parentHeight) {
                            $scroller.trigger('resize_scroll.' + namespace, [percentage, true]);
                        }
                        percentage = percentage > 1 ? 1 : (percentage < 0 || isNaN(percentage)) ? 0 : percentage;

                        scroller.css('margin-top', maximumOffset * percentage);

                        setTimeout(function () {
                            timebox.css('marginTop', -parseInt((timebox[0].offsetHeight - parentHeight) * percentage, 10));
                        }, 10);
                    })
                    .on('resize_scroll.' + namespace, function (event, percentage, noTriggerScroll) {
                        var percent, sh;
                        parentHeight = $scroller[0].clientHeight;
                        height = timebox[0].offsetHeight;
                        percent = parentHeight / height;
                        sh = percent * scrollbar[0].offsetHeight;
                        if (percent > 1) {
                            scroller.hide();
                        } else {
                            scroller.show();
                            scroller.css('height', parseInt(sh > 10 ? sh : 10, 10));
                            maximumOffset = scrollbar[0].offsetHeight - scroller[0].offsetHeight;
                            if (noTriggerScroll !== true) {
                                $scroller.trigger('scroll_element.' + namespace, [percentage || Math.abs(parseInt(timebox.css('marginTop'), 10)) / (height - parentHeight)]);
                            }
                        }
                    });

                $scroller.on('mousewheel', function (event) {
                    var top = Math.abs(parseInt(timebox.css('marginTop'), 10));

                    top = top - (event.deltaY * 20);
                    if (top < 0) {
                        top = 0;
                    }

                    $scroller.trigger('scroll_element.' + namespace, [top / (height - parentHeight)]);
                    event.stopPropagation();
                    return false;
                });

                $scroller.on('touchstart', function (event) {
                    start = pointerEventToXY(event);
                    startTop = Math.abs(parseInt(timebox.css('marginTop'), 10));
                });

                $scroller.on('touchmove', function (event) {
                    if (start) {
                        event.preventDefault();
                        var coord = pointerEventToXY(event);
                        $scroller.trigger('scroll_element.' + namespace, [(startTop - (coord.y - start.y)) / (height - parentHeight)]);
                    }
                });

                $scroller.on('touchend touchcancel', function (event) {
                    start = false;
                    startTop = 0;
                });
            },
            hide: function () {
                $scroller.find('.datetimepicker_scrollbar').hide();
            },
            exist: function () {
                return $scroller.length > 0 && $scroller.hasClass('.datetimepicker_scroller_box')
            }
        };
        if (!module.exist()) {
            module.create();
        }
        $scroller.trigger('resize_scroll.' + namespace, [percent]);
    });
};
$.fn.Lc4eScroller.settings = {
    namespace: 'Lc4eScroller'
};
$.fn.Lc4eDateTimePicker = function (opt) {
    var query = arguments[0],
        KEYMAP = $.fn.Lc4eDateTimePicker.settings.KEY,
        methodInvoked = (typeof query == 'string'),
        queryArguments = arguments[1];
    $(document)
        .off('keydown.ctrl keyup.ctrl')
        .on('keydown.ctrl', function (e) {
            if (e.keyCode === KEYMAP.CTRLKEY) {
                KEYMAP.ctrlDown = true;
            }
        })
        .on('keyup.ctrl', function (e) {
            if (e.keyCode === KEYMAP.CTRLKEY) {
                KEYMAP.ctrlDown = false;
            }
        });
    return this.each(function () {
        var options = ($.isPlainObject(opt) || !opt) ? $.extend(true, {}, $.fn.Lc4eDateTimePicker.settings.config, opt) : $.extend(true, {}, $.fn.Lc4eDateTimePicker.settings.config),
            namespace = $.fn.Lc4eDateTimePicker.settings.namespace,
            scrollnamespace = $.fn.Lc4eScroller.settings.namespace,
            $module = $(this),
            $datetimepicker = $module.data(namespace),
            lazyInitTimer = 0,
            module;
        module = {
            lazyInit: function ($input) {
                $input
                    .on('open.' + namespace + ' focusin.' + namespace + ' mousedown.' + namespace, function initOnActionCallback(event) {
                        if ($input.is(':disabled') || $input.data(namespace)) {
                            return;
                        }
                        clearTimeout(lazyInitTimer);
                        lazyInitTimer = setTimeout(function () {

                            if (!$input.data(namespace)) {
                                module.createDateTimePicker($input);
                            }
                            $input
                                .off('open.' + namespace + ' focusin.' + namespace + ' mousedown.' + namespace, initOnActionCallback)
                                .trigger('open.' + namespace);
                        }, 0);
                    });
            },
            createDateTimePicker: function () {
                $datetimepicker = $('<div class="datetimepicker datetimepicker_noselect"></div>');
                var copyright = $('<div class="datetimepicker_copyright"><a target="_blank" href="http://www.lc4e.com/">lc4e</a></div>'),
                    datepicker = $('<div class="datepicker active"></div>'),
                    mounth_picker = $('<div class="mounthpicker"><button type="button" class="datetimepicker_prev"><i class="chevron left icon"></i></button><button type="button" class="datetimepicker_today_button"><i class="home icon"></i></button>' +
                        '<div class="datetimepicker_label datetimepicker_month"><span></span><i></i></div>' +
                        '<div class="datetimepicker_label datetimepicker_year"><span></span><i></i></div>' +
                        '<button type="button" class="datetimepicker_next"><i class="chevron right icon"></i></button></div>'),
                    calendar = $('<div class="datetimepicker_calendar ui small compact table seven column"></div>'),
                    timepicker = $('<div class="datetimepicker_timepicker active"><button type="button" class="datetimepicker_prev"></button><div class="datetimepicker_time_box"></div><button type="button" class="datetimepicker_next"></button></div>'),
                    timeboxparent = timepicker.find('.datetimepicker_time_box').eq(0),
                    timebox = $('<div class="datetimepicker_time_variant"></div>'),
                    buttons = $('<div class="ui buttons"></div>'),
                    applyButton = $('<button type="button" class="ui button">Save</button>'),
                    clearButton = $('<button type="button" class="ui button">Clear</button>'),
                    monthselect = $('<div class="datetimepicker_select datetimepicker_monthselect"><div></div></div>'),
                    yearselect = $('<div class="datetimepicker_select datetimepicker_yearselect"><div></div></div>'),
                    triggerAfterOpen = false,
                    Lc4eDatetime,
                    xchangeTimer,
                    timerclick,
                    current_time_index,
                    setPos,
                    timer = 0,
                    timer1 = 0,
                    _datetimepicker_datetime;

                if (options.id) {
                    $datetimepicker.attr('id', options.id);
                }
                if (options.style) {
                    $datetimepicker.attr('style', options.style);
                }
                if (options.weeks) {
                    $datetimepicker.addClass('datetimepicker_showweeks');
                }
                if (options.rtl) {
                    $datetimepicker.addClass('datetimepicker_rtl');
                }

                $datetimepicker.addClass('datetimepicker_' + options.theme);
                $datetimepicker.addClass(options.className);

                mounth_picker
                    .find('.datetimepicker_month span')
                    .after(monthselect);
                mounth_picker
                    .find('.datetimepicker_year span')
                    .after(yearselect);

                mounth_picker
                    .find('.datetimepicker_month,.datetimepicker_year')
                    .on('mousedown.' + namespace, function (event) {
                        var select = $(this).find('.datetimepicker_select').eq(0),
                            val = 0,
                            top = 0,
                            visible = select.is(':visible'),
                            items,
                            i;

                        mounth_picker
                            .find('.datetimepicker_select')
                            .hide();
                        if (_datetimepicker_datetime.currentTime) {
                            val = _datetimepicker_datetime.currentTime[$(this).hasClass('datetimepicker_month') ? 'getMonth' : 'getFullYear']();
                        }

                        select[visible ? 'hide' : 'show']();
                        for (items = select.find('div.datetimepicker_option'), i = 0; i < items.length; i += 1) {
                            if (items.eq(i).data('value') === val) {
                                break;
                            } else {
                                top += items[0].offsetHeight;
                            }
                        }

                        select.Lc4eScroller(top / (select.children()[0].offsetHeight - (select[0].clientHeight)));
                        event.stopPropagation();
                        return false;
                    });

                mounth_picker
                    .find('.datetimepicker_select')
                    .Lc4eScroller()
                    .on('mousedown.' + namespace, function (event) {
                        event.stopPropagation();
                        event.preventDefault();
                    })
                    .on('mousedown.' + namespace, '.datetimepicker_option', function (event) {

                        if (_datetimepicker_datetime.currentTime === undefined || _datetimepicker_datetime.currentTime === null) {
                            _datetimepicker_datetime.currentTime = _datetimepicker_datetime.now();
                        }

                        var year = _datetimepicker_datetime.currentTime.getFullYear();
                        if (_datetimepicker_datetime && _datetimepicker_datetime.currentTime) {
                            _datetimepicker_datetime.currentTime[$(this).parent().parent().hasClass('datetimepicker_monthselect') ? 'setMonth' : 'setFullYear']($(this).data('value'));
                        }

                        $(this).parent().parent().hide();

                        $datetimepicker.trigger('xchange.' + namespace);
                        if (options.onChangeMonth && $.isFunction(options.onChangeMonth)) {
                            options.onChangeMonth.call($datetimepicker, _datetimepicker_datetime.currentTime, $datetimepicker.data('input'));
                        }

                        if (year !== _datetimepicker_datetime.currentTime.getFullYear() && $.isFunction(options.onChangeYear)) {
                            options.onChangeYear.call($datetimepicker, _datetimepicker_datetime.currentTime, $datetimepicker.data('input'));
                        }
                    });

                $datetimepicker.setOptions = function (_options) {
                    var highlightedDates = {},
                        getCaretPos = function (input) {
                            try {
                                if (document.selection && document.selection.createRange) {
                                    var range = document.selection.createRange();
                                    return range.getBookmark().charCodeAt(2) - 2;
                                }
                                if (input.setSelectionRange) {
                                    return input.selectionStart;
                                }
                            } catch (e) {
                                return 0;
                            }
                        },
                        setCaretPos = function (node, pos) {
                            node = (typeof node === "string" || node instanceof String) ? document.getElementById(node) : node;
                            if (!node) {
                                return false;
                            }
                            if (node.createTextRange) {
                                var textRange = node.createTextRange();
                                textRange.collapse(true);
                                textRange.moveEnd('character', pos);
                                textRange.moveStart('character', pos);
                                textRange.select();
                                return true;
                            }
                            if (node.setSelectionRange) {
                                node.setSelectionRange(pos, pos);
                                return true;
                            }
                            return false;
                        },
                        isValidValue = function (mask, value) {
                            var reg = mask
                                .replace(/([\[\]\/\{\}\(\)\-\.\+]{1})/g, '\\$1')
                                .replace(/_/g, '{digit+}')
                                .replace(/([0-9]{1})/g, '{digit$1}')
                                .replace(/\{digit([0-9]{1})\}/g, '[0-$1_]{1}')
                                .replace(/\{digit[\+]\}/g, '[0-9_]{1}');
                            return (new RegExp(reg)).test(value);
                        };
                    options = $.extend(true, {}, options, _options);

                    if (_options.allowTimes && $.isArray(_options.allowTimes) && _options.allowTimes.length) {
                        options.allowTimes = $.extend(true, [], _options.allowTimes);
                    }

                    if (_options.weekends && $.isArray(_options.weekends) && _options.weekends.length) {
                        options.weekends = $.extend(true, [], _options.weekends);
                    }

                    if (_options.highlightedDates && $.isArray(_options.highlightedDates) && _options.highlightedDates.length) {
                        $.each(_options.highlightedDates, function (index, value) {
                            var splitData = $.map(value.split(','), $.trim),
                                exDesc,
                                hDate = $.lc4e.HighlightedDate(Date.parseDate(splitData[0], options.formatDate), splitData[1], splitData[2]), // date, desc, style
                                keyDate = hDate.date.format(options.formatDate);
                            if (highlightedDates[keyDate] !== undefined) {
                                exDesc = highlightedDates[keyDate].desc;
                                if (exDesc && exDesc.length && hDate.desc && hDate.desc.length) {
                                    highlightedDates[keyDate].desc = exDesc + "\n" + hDate.desc;
                                }
                            } else {
                                highlightedDates[keyDate] = hDate;
                            }
                        });

                        options.highlightedDates = $.extend(true, [], highlightedDates);
                    }

                    if (_options.highlightedPeriods && $.isArray(_options.highlightedPeriods) && _options.highlightedPeriods.length) {
                        highlightedDates = $.extend(true, [], options.highlightedDates);
                        $.each(_options.highlightedPeriods, function (index, value) {
                            var splitData = $.map(value.split(','), $.trim),
                                dateTest = Date.parseDate(splitData[0], options.formatDate), // start date
                                dateEnd = Date.parseDate(splitData[1], options.formatDate),
                                desc = splitData[2],
                                hDate,
                                keyDate,
                                exDesc,
                                style = splitData[3];

                            while (dateTest <= dateEnd) {
                                hDate = $.lc4e.HighlightedDate(dateTest, desc, style);
                                keyDate = dateTest.format(options.formatDate);
                                dateTest.setDate(dateTest.getDate() + 1);
                                if (highlightedDates[keyDate] !== undefined) {
                                    exDesc = highlightedDates[keyDate].desc;
                                    if (exDesc && exDesc.length && hDate.desc && hDate.desc.length) {
                                        highlightedDates[keyDate].desc = exDesc + "\n" + hDate.desc;
                                    }
                                } else {
                                    highlightedDates[keyDate] = hDate;
                                }
                            }
                        });

                        options.highlightedDates = $.extend(true, [], highlightedDates);
                    }

                    if (_options.disabledDates && $.isArray(_options.disabledDates) && _options.disabledDates.length) {
                        options.disabledDates = $.extend(true, [], _options.disabledDates);
                    }

                    if (_options.disabledWeekDays && $.isArray(_options.disabledWeekDays) && _options.disabledWeekDays.length) {
                        options.disabledWeekDays = $.extend(true, [], _options.disabledWeekDays);
                    }

                    if ((options.open || options.opened) && (!options.inline)) {
                        $module.trigger('open.' + namespace);
                    }

                    if (options.inline) {
                        triggerAfterOpen = true;
                        $datetimepicker.addClass('datetimepicker_inline');
                        $module.after($datetimepicker).hide();
                    }

                    if (options.inverseButton) {
                        options.next = 'datetimepicker_prev';
                        options.prev = 'datetimepicker_next';
                    }

                    if (options.datepicker) {
                        datepicker.addClass('active');
                    } else {
                        datepicker.removeClass('active');
                    }

                    if (options.timepicker) {
                        timepicker.addClass('active');
                    } else {
                        timepicker.removeClass('active');
                    }

                    if (options.value) {
                        _datetimepicker_datetime.setCurrentTime(options.value);
                        if ($module && $module.val) {
                            $module.val(_datetimepicker_datetime.str);
                        }
                    }

                    if (isNaN(options.dayOfWeekStart)) {
                        options.dayOfWeekStart = 0;
                    } else {
                        options.dayOfWeekStart = parseInt(options.dayOfWeekStart, 10) % 7;
                    }

                    if (!options.timepickerScrollbar) {
                        timeboxparent.Lc4eScroller('hide');
                    }

                    if (options.minDate && /^[\+\-](.*)$/.test(options.minDate)) {
                        options.minDate = _datetimepicker_datetime.strToDateTime(options.minDate).format(options.formatDate);
                    }

                    if (options.maxDate && /^[\+\-](.*)$/.test(options.maxDate)) {
                        options.maxDate = _datetimepicker_datetime.strToDateTime(options.maxDate).format(options.formatDate);
                    }

                    applyButton.toggle(options.showApplyButton);
                    clearButton.toggle(options.showClearButton);
                    mounth_picker
                        .find('.datetimepicker_today_button')
                        .css('visibility', !options.todayButton ? 'hidden' : 'visible');

                    mounth_picker
                        .find('.' + options.prev)
                        .css('visibility', !options.prevButton ? 'hidden' : 'visible');

                    mounth_picker
                        .find('.' + options.next)
                        .css('visibility', !options.nextButton ? 'hidden' : 'visible');

                    if (options.mask) {
                        $module.off('keydown.' + namespace);

                        if (options.mask === true) {
                            options.mask = options.format
                                .replace(/Y/g, '9999')
                                .replace(/F/g, '9999')
                                .replace(/m/g, '19')
                                .replace(/d/g, '39')
                                .replace(/H/g, '29')
                                .replace(/i/g, '59')
                                .replace(/s/g, '59');
                        }

                        if ($.type(options.mask) === 'string') {
                            if (!isValidValue(options.mask, $module.val())) {
                                $module.val(options.mask.replace(/[0-9]/g, '_'));
                            }

                            $module.on('keydown.' + namespace, function (event) {
                                var val = this.value,
                                    key = event.which,
                                    pos,
                                    digit;

                                if (((key >= KEYMAP.KEY0 && key <= KEYMAP.KEY9) || (key >= KEYMAP._KEY0 && key <= KEYMAP._KEY9)) || (key === KEYMAP.BACKSPACE || key === KEYMAP.DEL)) {
                                    pos = getCaretPos(this);
                                    digit = (key !== KEYMAP.BACKSPACE && key !== KEYMAP.DEL) ? String.fromCharCode((KEYMAP._KEY0 <= key && key <= KEYMAP._KEY9) ? key - KEYMAP.KEY0 : key) : '_';

                                    if ((key === KEYMAP.BACKSPACE || key === KEYMAP.DEL) && pos) {
                                        pos -= 1;
                                        digit = '_';
                                    }

                                    while (/[^0-9_]/.test(options.mask.substr(pos, 1)) && pos < options.mask.length && pos > 0) {
                                        pos += (key === KEYMAP.BACKSPACE || key === KEYMAP.DEL) ? -1 : 1;
                                    }

                                    val = val.substr(0, pos) + digit + val.substr(pos + 1);
                                    if ($.trim(val) === '') {
                                        val = options.mask.replace(/[0-9]/g, '_');
                                    } else {
                                        if (pos === options.mask.length) {
                                            event.preventDefault();
                                            return false;
                                        }
                                    }

                                    pos += (key === KEYMAP.BACKSPACE || key === KEYMAP.DEL) ? 0 : 1;
                                    while (/[^0-9_]/.test(options.mask.substr(pos, 1)) && pos < options.mask.length && pos > 0) {
                                        pos += (key === KEYMAP.BACKSPACE || key === KEYMAP.DEL) ? -1 : 1;
                                    }

                                    if (isValidValue(options.mask, val)) {
                                        this.value = val;
                                        setCaretPos(this, pos);
                                    } else if ($.trim(val) === '') {
                                        this.value = options.mask.replace(/[0-9]/g, '_');
                                    } else {
                                        $module.trigger('error_input.' + namespace);
                                    }
                                } else {
                                    if (([KEYMAP.AKEY, KEYMAP.CKEY, KEYMAP.VKEY, KEYMAP.ZKEY, KEYMAP.YKEY].indexOf(key) !== -1 &&
                                        KEYMAP.ctrlDown) ||
                                        [KEYMAP.ESC, KEYMAP.ARROWUP, KEYMAP.ARROWDOWN, KEYMAP.ARROWLEFT, KEYMAP.ARROWRIGHT,
                                            KEYMAP.F5, KEYMAP.CTRLKEY, KEYMAP.TAB, KEYMAP.ENTER].indexOf(key) !== -1) {
                                        return true;
                                    }
                                }

                                event.preventDefault();
                                return false;
                            });
                        }
                    }
                    if (options.validateOnBlur) {
                        $module
                            .off('blur' + namespace)
                            .on('blur.' + namespace, function () {
                                if (options.allowBlank && !$.trim($(this).val()).length) {
                                    $(this).val(null);
                                    $datetimepicker.data('datetimepicker_datetime').empty();
                                } else if (!Date.parseDate($(this).val(), options.format)) {
                                    var splittedHours = +([$(this).val()[0], $(this).val()[1]].join('')),
                                        splittedMinutes = +([$(this).val()[2], $(this).val()[3]].join(''));

                                    // parse the numbers as 0312 => 03:12
                                    if (!options.datepicker && options.timepicker && splittedHours >= 0 && splittedHours < 24 && splittedMinutes >= 0 && splittedMinutes < 60) {
                                        $(this).val([splittedHours, splittedMinutes].map(function (item) {
                                            return item > 9 ? item : '0' + item;
                                        }).join(':'));
                                    } else {
                                        $(this).val((_datetimepicker_datetime.now()).format(options.format));
                                    }

                                    $datetimepicker.data('datetimepicker_datetime').setCurrentTime($(this).val());
                                } else {
                                    $datetimepicker.data('datetimepicker_datetime').setCurrentTime($(this).val());
                                }

                                $datetimepicker.trigger('changedatetime.' + namespace);
                            });
                    }
                    options.dayOfWeekStartPrev = (options.dayOfWeekStart === 0) ? 6 : options.dayOfWeekStart - 1;

                    $datetimepicker
                        .trigger('xchange.' + namespace)
                        .trigger('afterOpen.' + namespace);
                };

                $datetimepicker
                    .data('options', options)
                    .on('mousedown.' + namespace, function (event) {
                        event.stopPropagation();
                        event.preventDefault();
                        yearselect.hide();
                        monthselect.hide();
                        return false;
                    });

                //scroll_element = timepicker.find('.datetimepicker_time_box');
                timeboxparent.append(timebox);
                timeboxparent.Lc4eScroller();

                $datetimepicker.on('afterOpen.' + namespace, function () {
                    timeboxparent.Lc4eScroller();
                });

                $datetimepicker
                    .append(datepicker)
                    .append(timepicker);

                if (options.withoutCopyright !== true) {
                    $datetimepicker
                        .append(copyright);
                }
                buttons.append(applyButton)
                    .append(clearButton);
                datepicker
                    .append(mounth_picker)
                    .append(calendar)
                    .append(buttons);

                $(options.parentID)
                    .append($datetimepicker);

                Lc4eDatetime = function () {
                    var _this = this;
                    _this.now = function (norecursion) {
                        var d = new Date(),
                            date,
                            time;

                        if (!norecursion && options.defaultDate) {
                            date = _this.strToDateTime(options.defaultDate);
                            d.setFullYear(date.getFullYear());
                            d.setMonth(date.getMonth());
                            d.setDate(date.getDate());
                        }

                        if (options.yearOffset) {
                            d.setFullYear(d.getFullYear() + options.yearOffset);
                        }

                        if (!norecursion && options.defaultTime) {
                            time = _this.strtotime(options.defaultTime);
                            d.setHours(time.getHours());
                            d.setMinutes(time.getMinutes());
                        }
                        return d;
                    };

                    _this.isValidDate = function (d) {
                        if (Object.prototype.toString.call(d) !== "[object Date]") {
                            return false;
                        }
                        return !isNaN(d.getTime());
                    };

                    _this.setCurrentTime = function (dTime) {
                        _this.currentTime = (typeof dTime === 'string') ? _this.strToDateTime(dTime) : _this.isValidDate(dTime) ? dTime : _this.now();
                        $datetimepicker.trigger('xchange.' + namespace);
                    };

                    _this.empty = function () {
                        _this.currentTime = null;
                    };

                    _this.getCurrentTime = function (dTime) {
                        return _this.currentTime;
                    };

                    _this.nextMonth = function () {

                        if (_this.currentTime === undefined || _this.currentTime === null) {
                            _this.currentTime = _this.now();
                        }

                        var month = _this.currentTime.getMonth() + 1,
                            year;
                        if (month === 12) {
                            _this.currentTime.setFullYear(_this.currentTime.getFullYear() + 1);
                            month = 0;
                        }

                        year = _this.currentTime.getFullYear();

                        _this.currentTime.setDate(
                            Math.min(
                                new Date(_this.currentTime.getFullYear(), month + 1, 0).getDate(),
                                _this.currentTime.getDate()
                            )
                        );
                        _this.currentTime.setMonth(month);

                        if (options.onChangeMonth && $.isFunction(options.onChangeMonth)) {
                            options.onChangeMonth.call($datetimepicker, _datetimepicker_datetime.currentTime, $datetimepicker.data('input'));
                        }

                        if (year !== _this.currentTime.getFullYear() && $.isFunction(options.onChangeYear)) {
                            options.onChangeYear.call($datetimepicker, _datetimepicker_datetime.currentTime, $datetimepicker.data('input'));
                        }

                        $datetimepicker.trigger('xchange.' + namespace);
                        return month;
                    };

                    _this.prevMonth = function () {

                        if (_this.currentTime === undefined || _this.currentTime === null) {
                            _this.currentTime = _this.now();
                        }

                        var month = _this.currentTime.getMonth() - 1;
                        if (month === -1) {
                            _this.currentTime.setFullYear(_this.currentTime.getFullYear() - 1);
                            month = 11;
                        }
                        _this.currentTime.setDate(
                            Math.min(
                                new Date(_this.currentTime.getFullYear(), month + 1, 0).getDate(),
                                _this.currentTime.getDate()
                            )
                        );
                        _this.currentTime.setMonth(month);
                        if (options.onChangeMonth && $.isFunction(options.onChangeMonth)) {
                            options.onChangeMonth.call($datetimepicker, _datetimepicker_datetime.currentTime, $datetimepicker.data('input'));
                        }
                        $datetimepicker.trigger('xchange.' + namespace);
                        return month;
                    };

                    _this.getWeekOfYear = function (datetime) {
                        var onejan = new Date(datetime.getFullYear(), 0, 1);
                        return Math.ceil((((datetime - onejan) / 86400000) + onejan.getDay() + 1) / 7);
                    };

                    _this.strToDateTime = function (sDateTime) {
                        var tmpDate = [], timeOffset, currentTime;

                        if (sDateTime && sDateTime instanceof Date && _this.isValidDate(sDateTime)) {
                            return sDateTime;
                        }

                        tmpDate = /^(\+|\-)(.*)$/.exec(sDateTime);
                        if (tmpDate) {
                            tmpDate[2] = Date.parseDate(tmpDate[2], options.formatDate);
                        }
                        if (tmpDate && tmpDate[2]) {
                            timeOffset = tmpDate[2].getTime() - (tmpDate[2].getTimezoneOffset()) * 60000;
                            currentTime = new Date((_this.now(true)).getTime() + parseInt(tmpDate[1] + '1', 10) * timeOffset);
                        } else {
                            currentTime = sDateTime ? Date.parseDate(sDateTime, options.format) : _this.now();
                        }

                        if (!_this.isValidDate(currentTime)) {
                            currentTime = _this.now();
                        }

                        return currentTime;
                    };

                    _this.strToDate = function (sDate) {
                        if (sDate && sDate instanceof Date && _this.isValidDate(sDate)) {
                            return sDate;
                        }

                        var currentTime = sDate ? Date.parseDate(sDate, options.formatDate) : _this.now(true);
                        if (!_this.isValidDate(currentTime)) {
                            currentTime = _this.now(true);
                        }
                        return currentTime;
                    };

                    _this.strtotime = function (sTime) {
                        if (sTime && sTime instanceof Date && _this.isValidDate(sTime)) {
                            return sTime;
                        }
                        var currentTime = sTime ? Date.parseDate(sTime, options.formatTime) : _this.now(true);
                        if (!_this.isValidDate(currentTime)) {
                            currentTime = _this.now(true);
                        }
                        return currentTime;
                    };

                    _this.str = function () {
                        return _this.currentTime.format(options.format);
                    };
                    _this.currentTime = this.now();
                };

                _datetimepicker_datetime = new Lc4eDatetime();

                applyButton.on('click', function (e) {//pathbrite
                    e.preventDefault();
                    $datetimepicker.data('changed', true);
                    _datetimepicker_datetime.setCurrentTime(getCurrentValue());
                    $module.val(_datetimepicker_datetime.str());
                    $datetimepicker.trigger('close.' + namespace);
                });
                clearButton.on('click', function (e) {
                    e.preventDefault();
                    $module.val('');
                });
                mounth_picker
                    .find('.datetimepicker_today_button')
                    .on('mousedown.' + namespace, function () {
                        $datetimepicker.data('changed', true);
                        _datetimepicker_datetime.setCurrentTime(0);
                        $datetimepicker.trigger('afterOpen.' + namespace);
                    }).on('dblclick.' + namespace, function () {
                    var currentDate = _datetimepicker_datetime.getCurrentTime(), minDate, maxDate;
                    currentDate = new Date(currentDate.getFullYear(), currentDate.getMonth(), currentDate.getDate());
                    minDate = _datetimepicker_datetime.strToDate(options.minDate);
                    minDate = new Date(minDate.getFullYear(), minDate.getMonth(), minDate.getDate());
                    if (currentDate < minDate) {
                        return;
                    }
                    maxDate = _datetimepicker_datetime.strToDate(options.maxDate);
                    maxDate = new Date(maxDate.getFullYear(), maxDate.getMonth(), maxDate.getDate());
                    if (currentDate > maxDate) {
                        return;
                    }
                    $module.val(_datetimepicker_datetime.str());
                    $module.trigger('change');
                    $datetimepicker.trigger('close.' + namespace);
                });
                mounth_picker
                    .find('.datetimepicker_prev,.datetimepicker_next')
                    .on('mousedown.' + namespace, function () {
                        var $this = $(this),
                            timer = 0,
                            stop = false;

                        (function arguments_callee1(v) {
                            if ($this.hasClass(options.next)) {
                                _datetimepicker_datetime.nextMonth();
                            } else if ($this.hasClass(options.prev)) {
                                _datetimepicker_datetime.prevMonth();
                            }
                            if (options.monthChangeSpinner) {
                                if (!stop) {
                                    timer = setTimeout(arguments_callee1, v || 100);
                                }
                            }
                        }(500));

                        $([document.body, window]).on('mouseup.' + namespace, function arguments_callee2() {
                            clearTimeout(timer);
                            stop = true;
                            $([document.body, window]).off('mouseup.' + namespace, arguments_callee2);
                        });
                    });

                timepicker
                    .find('.datetimepicker_prev,.datetimepicker_next')
                    .on('mousedown.' + namespace, function () {
                        var $this = $(this),
                            timer = 0,
                            stop = false,
                            period = 110;
                        (function arguments_callee4(v) {
                            var pheight = timeboxparent[0].clientHeight,
                                height = timebox[0].offsetHeight,
                                top = Math.abs(parseInt(timebox.css('marginTop'), 10));
                            if ($this.hasClass(options.next) && (height - pheight) - options.timeHeightInTimePicker >= top) {
                                timebox.css('marginTop', '-' + (top + options.timeHeightInTimePicker) + 'px');
                            } else if ($this.hasClass(options.prev) && top - options.timeHeightInTimePicker >= 0) {
                                timebox.css('marginTop', '-' + (top - options.timeHeightInTimePicker) + 'px');
                            }
                            timeboxparent.trigger('scroll_element.' + scrollnamespace, [Math.abs(parseInt(timebox.css('marginTop'), 10) / (height - pheight))]);
                            period = (period > 10) ? 10 : period - 10;
                            if (!stop) {
                                timer = setTimeout(arguments_callee4, v || period);
                            }
                        }(500));
                        $([document.body, window]).on('mouseup.' + namespace, function arguments_callee5() {
                            clearTimeout(timer);
                            stop = true;
                            $([document.body, window])
                                .off('mouseup.' + namespace, arguments_callee5);
                        });
                    });

                xchangeTimer = 0;
                // base handler - generating a calendar and timepicker
                $datetimepicker
                    .on('xchange.' + namespace, function (event) {
                        clearTimeout(xchangeTimer);
                        xchangeTimer = setTimeout(function () {

                            if (_datetimepicker_datetime.currentTime === undefined || _datetimepicker_datetime.currentTime === null) {
                                _datetimepicker_datetime.currentTime = _datetimepicker_datetime.now();
                            }

                            var table = '',
                                start = new Date(_datetimepicker_datetime.currentTime.getFullYear(), _datetimepicker_datetime.currentTime.getMonth(), 1, 12, 0, 0),
                                i = 0,
                                j,
                                today = _datetimepicker_datetime.now(),
                                maxDate = false,
                                minDate = false,
                                hDate,
                                day,
                                d,
                                y,
                                m,
                                w,
                                classes = [],
                                customDateSettings,
                                newRow = true,
                                time = '',
                                h = '',
                                line_time,
                                description;

                            while (start.getDay() !== options.dayOfWeekStart) {
                                start.setDate(start.getDate() - 1);
                            }

                            table += '<table><thead><tr>';

                            if (options.weeks) {
                                table += '<th></th>';
                            }

                            for (j = 0; j < 7; j += 1) {
                                table += '<th class="center aligned">' + options.i18n[options.lang].dayOfWeek[(j + options.dayOfWeekStart) % 7] + '</th>';
                            }

                            table += '</tr></thead>';
                            table += '<tbody>';

                            if (options.maxDate !== false) {
                                maxDate = _datetimepicker_datetime.strToDate(options.maxDate);
                                maxDate = new Date(maxDate.getFullYear(), maxDate.getMonth(), maxDate.getDate(), 23, 59, 59, 999);
                            }

                            if (options.minDate !== false) {
                                minDate = _datetimepicker_datetime.strToDate(options.minDate);
                                minDate = new Date(minDate.getFullYear(), minDate.getMonth(), minDate.getDate());
                            }

                            while (i < _datetimepicker_datetime.currentTime.countDaysInMonth() || start.getDay() !== options.dayOfWeekStart || _datetimepicker_datetime.currentTime.getMonth() === start.getMonth()) {
                                classes = [];
                                i += 1;

                                day = start.getDay();
                                d = start.getDate();
                                y = start.getFullYear();
                                m = start.getMonth();
                                w = _datetimepicker_datetime.getWeekOfYear(start);
                                description = '';

                                classes.push('datetimepicker_date');

                                if (options.beforeShowDay && $.isFunction(options.beforeShowDay.call)) {
                                    customDateSettings = options.beforeShowDay.call($datetimepicker, start);
                                } else {
                                    customDateSettings = null;
                                }

                                if ((maxDate !== false && start > maxDate) || (minDate !== false && start < minDate) || (customDateSettings && customDateSettings[0] === false)) {
                                    classes.push('datetimepicker_disabled');
                                } else if (options.disabledDates.indexOf(start.format(options.formatDate)) !== -1) {
                                    classes.push('datetimepicker_disabled');
                                } else if (options.disabledWeekDays.indexOf(day) !== -1) {
                                    classes.push('datetimepicker_disabled');
                                }

                                if (customDateSettings && customDateSettings[1] !== "") {
                                    classes.push(customDateSettings[1]);
                                }

                                if (_datetimepicker_datetime.currentTime.getMonth() !== m) {
                                    classes.push('datetimepicker_other_month');
                                }

                                if ((options.defaultSelect || $datetimepicker.data('changed')) && _datetimepicker_datetime.currentTime.format(options.formatDate) === start.format(options.formatDate)) {
                                    classes.push('datetimepicker_current');
                                }

                                if (today.format(options.formatDate) === start.format(options.formatDate)) {
                                    classes.push('datetimepicker_today');
                                }

                                if (start.getDay() === 0 || start.getDay() === 6 || options.weekends.indexOf(start.format(options.formatDate)) !== -1) {
                                    classes.push('datetimepicker_weekend');
                                }

                                if (options.highlightedDates[start.format(options.formatDate)] !== undefined) {
                                    hDate = options.highlightedDates[start.format(options.formatDate)];
                                    classes.push(hDate.style === undefined ? 'datetimepicker_highlighted_default' : hDate.style);
                                    description = hDate.desc === undefined ? '' : hDate.desc;
                                }

                                if (options.beforeShowDay && $.isFunction(options.beforeShowDay)) {
                                    classes.push(options.beforeShowDay(start));
                                }

                                if (newRow) {
                                    table += '<tr>';
                                    newRow = false;
                                    if (options.weeks) {
                                        table += '<th>' + w + '</th>';
                                    }
                                }

                                table += '<td data-date="' + d + '" data-month="' + m + '" data-year="' + y + '"' + ' class="datetimepicker_date datetimepicker_day_of_week' + start.getDay() + ' ' + classes.join(' ') + '" title="' + description + '">' +
                                    '<div>' + d + '</div>' +
                                    '</td>';

                                if (start.getDay() === options.dayOfWeekStartPrev) {
                                    table += '</tr>';
                                    newRow = true;
                                }

                                start.setDate(d + 1);
                            }
                            table += '</tbody></table>';

                            calendar.html(table);

                            mounth_picker.find('.datetimepicker_label span').eq(0).text(options.i18n[options.lang].months[_datetimepicker_datetime.currentTime.getMonth()]);
                            mounth_picker.find('.datetimepicker_label span').eq(1).text(_datetimepicker_datetime.currentTime.getFullYear());

                            // generate timebox
                            time = '';
                            h = '';
                            m = '';
                            line_time = function line_time(h, m) {
                                var now = _datetimepicker_datetime.now(), optionDateTime, current_time;
                                now.setHours(h);
                                h = parseInt(now.getHours(), 10);
                                now.setMinutes(m);
                                m = parseInt(now.getMinutes(), 10);
                                optionDateTime = new Date(_datetimepicker_datetime.currentTime);
                                optionDateTime.setHours(h);
                                optionDateTime.setMinutes(m);
                                classes = [];
                                if ((options.minDateTime !== false && options.minDateTime > optionDateTime) || (options.maxTime !== false && _datetimepicker_datetime.strtotime(options.maxTime).getTime() < now.getTime()) || (options.minTime !== false && _datetimepicker_datetime.strtotime(options.minTime).getTime() > now.getTime())) {
                                    classes.push('datetimepicker_disabled');
                                }
                                if ((options.minDateTime !== false && options.minDateTime > optionDateTime) || ((options.disabledMinTime !== false && now.getTime() > _datetimepicker_datetime.strtotime(options.disabledMinTime).getTime()) && (options.disabledMaxTime !== false && now.getTime() < _datetimepicker_datetime.strtotime(options.disabledMaxTime).getTime()))) {
                                    classes.push('datetimepicker_disabled');
                                }

                                current_time = new Date(_datetimepicker_datetime.currentTime);
                                current_time.setHours(parseInt(_datetimepicker_datetime.currentTime.getHours(), 10));
                                current_time.setMinutes(Math[options.roundTime](_datetimepicker_datetime.currentTime.getMinutes() / options.step) * options.step);

                                if ((options.initTime || options.defaultSelect || $datetimepicker.data('changed')) && current_time.getHours() === parseInt(h, 10) && (options.step > 59 || current_time.getMinutes() === parseInt(m, 10))) {
                                    if (options.defaultSelect || $datetimepicker.data('changed')) {
                                        classes.push('datetimepicker_current');
                                    } else if (options.initTime) {
                                        classes.push('datetimepicker_init_time');
                                    }
                                }
                                if (parseInt(today.getHours(), 10) === parseInt(h, 10) && parseInt(today.getMinutes(), 10) === parseInt(m, 10)) {
                                    classes.push('datetimepicker_today');
                                }
                                time += '<div class="datetimepicker_time ' + classes.join(' ') + '" data-hour="' + h + '" data-minute="' + m + '">' + now.format(options.formatTime) + '</div>';
                            };

                            if (!options.allowTimes || !$.isArray(options.allowTimes) || !options.allowTimes.length) {
                                for (i = 0, j = 0; i < (options.hours12 ? 12 : 24); i += 1) {
                                    for (j = 0; j < 60; j += options.step) {
                                        h = (i < 10 ? '0' : '') + i;
                                        m = (j < 10 ? '0' : '') + j;
                                        line_time(h, m);
                                    }
                                }
                            } else {
                                for (i = 0; i < options.allowTimes.length; i += 1) {
                                    h = _datetimepicker_datetime.strtotime(options.allowTimes[i]).getHours();
                                    m = _datetimepicker_datetime.strtotime(options.allowTimes[i]).getMinutes();
                                    line_time(h, m);
                                }
                            }

                            timebox.html(time);

                            opt = '';
                            i = 0;

                            for (i = parseInt(options.yearStart, 10) + options.yearOffset; i <= parseInt(options.yearEnd, 10) + options.yearOffset; i += 1) {
                                opt += '<div class="datetimepicker_option ' + (_datetimepicker_datetime.currentTime.getFullYear() === i ? 'datetimepicker_current' : '') + '" data-value="' + i + '">' + i + '</div>';
                            }
                            yearselect.children().eq(0)
                                .html(opt);

                            for (i = parseInt(options.monthStart, 10), opt = ''; i <= parseInt(options.monthEnd, 10); i += 1) {
                                opt += '<div class="datetimepicker_option ' + (_datetimepicker_datetime.currentTime.getMonth() === i ? 'datetimepicker_current' : '') + '" data-value="' + i + '">' + options.i18n[options.lang].months[i] + '</div>';
                            }
                            monthselect.children().eq(0).html(opt);
                            $($datetimepicker)
                                .trigger('generate.' + namespace);
                        }, 10);
                        event.stopPropagation();
                    })
                    .on('afterOpen.' + namespace, function () {
                        if (options.timepicker) {
                            var classType, pheight, height, top;
                            if (timebox.find('.datetimepicker_current').length) {
                                classType = '.datetimepicker_current';
                            } else if (timebox.find('.datetimepicker_init_time').length) {
                                classType = '.datetimepicker_init_time';
                            }
                            if (classType) {
                                pheight = timeboxparent[0].clientHeight;
                                height = timebox[0].offsetHeight;
                                top = timebox.find(classType).index() * options.timeHeightInTimePicker + 1;
                                if ((height - pheight) < top) {
                                    top = height - pheight;
                                }
                                timeboxparent.trigger('scroll_element.' + scrollnamespace, [parseInt(top, 10) / (height - pheight)]);
                            } else {
                                timeboxparent.trigger('scroll_element.' + scrollnamespace, [0]);
                            }
                        }
                    });

                timerclick = 0;
                calendar
                    .on('click.' + namespace, 'td', function (xdevent) {
                        xdevent.stopPropagation();  // Prevents closing of Pop-ups, Modals and Flyouts in Bootstrap
                        timerclick += 1;
                        var $this = $(this),
                            currentTime = _datetimepicker_datetime.currentTime;

                        if (currentTime === undefined || currentTime === null) {
                            _datetimepicker_datetime.currentTime = _datetimepicker_datetime.now();
                            currentTime = _datetimepicker_datetime.currentTime;
                        }

                        if ($this.hasClass('datetimepicker_disabled')) {
                            return false;
                        }

                        currentTime.setDate(1);
                        currentTime.setFullYear($this.data('year'));
                        currentTime.setMonth($this.data('month'));
                        currentTime.setDate($this.data('date'));

                        $datetimepicker.trigger('select.' + namespace, [currentTime]);

                        $module.val(_datetimepicker_datetime.str());
                        if ((timerclick > 1 || (options.closeOnDateSelect === true || (options.closeOnDateSelect === false && !options.timepicker))) && !options.inline) {
                            $datetimepicker.trigger('close.' + namespace);
                        }

                        if (options.onSelectDate && $.isFunction(options.onSelectDate)) {
                            options.onSelectDate.call($datetimepicker, _datetimepicker_datetime.currentTime, $datetimepicker.data('input'), xdevent);
                        }

                        $datetimepicker.data('changed', true);
                        $datetimepicker.trigger('xchange.' + namespace);
                        $datetimepicker.trigger('changedatetime.' + namespace);
                        setTimeout(function () {
                            timerclick = 0;
                        }, 200);
                    });

                timebox
                    .on('click.' + namespace, 'div', function (xdevent) {
                        xdevent.stopPropagation();
                        var $this = $(this),
                            currentTime = _datetimepicker_datetime.currentTime;

                        if (currentTime === undefined || currentTime === null) {
                            _datetimepicker_datetime.currentTime = _datetimepicker_datetime.now();
                            currentTime = _datetimepicker_datetime.currentTime;
                        }

                        if ($this.hasClass('datetimepicker_disabled')) {
                            return false;
                        }
                        currentTime.setHours($this.data('hour'));
                        currentTime.setMinutes($this.data('minute'));
                        $datetimepicker.trigger('select.' + namespace, [currentTime]);

                        $datetimepicker.data('input').val(_datetimepicker_datetime.str());

                        if (options.inline !== true && options.closeOnTimeSelect === true) {
                            $datetimepicker.trigger('close.' + namespace);
                        }

                        if (options.onSelectTime && $.isFunction(options.onSelectTime)) {
                            options.onSelectTime.call($datetimepicker, _datetimepicker_datetime.currentTime, $datetimepicker.data('input'), xdevent);
                        }
                        $datetimepicker.data('changed', true);
                        $datetimepicker.trigger('xchange.' + namespace);
                        $datetimepicker.trigger('changedatetime.' + namespace);
                    });


                datepicker
                    .on('mousewheel.' + namespace, function (event) {
                        if (!options.scrollMonth) {
                            return true;
                        }
                        if (event.deltaY < 0) {
                            _datetimepicker_datetime.nextMonth();
                        } else {
                            _datetimepicker_datetime.prevMonth();
                        }
                        return false;
                    });

                $module
                    .on('mousewheel.' + namespace, function (event) {
                        if (!options.scrollInput) {
                            return true;
                        }
                        if (!options.datepicker && options.timepicker) {
                            current_time_index = timebox.find('.datetimepicker_current').length ? timebox.find('.datetimepicker_current').eq(0).index() : 0;
                            if (current_time_index + event.deltaY >= 0 && current_time_index + event.deltaY < timebox.children().length) {
                                current_time_index += event.deltaY;
                            }
                            if (timebox.children().eq(current_time_index).length) {
                                timebox.children().eq(current_time_index).trigger('mousedown');
                            }
                            return false;
                        }
                        if (options.datepicker && !options.timepicker) {
                            datepicker.trigger(event, [event.deltaY, event.deltaX, event.deltaY]);
                            if ($module.val) {
                                $module.val(_datetimepicker_datetime.str());
                            }
                            $datetimepicker.trigger('changedatetime.' + namespace);
                            return false;
                        }
                    });

                $datetimepicker
                    .on('changedatetime.' + namespace, function (event) {
                        if (options.onChangeDateTime && $.isFunction(options.onChangeDateTime)) {
                            var $input = $datetimepicker.data('input');
                            options.onChangeDateTime.call($datetimepicker, _datetimepicker_datetime.currentTime, $input, event);
                            delete options.value;
                            $input.trigger('change');
                        }
                    })
                    .on('generate.' + namespace, function () {
                        if (options.onGenerate && $.isFunction(options.onGenerate)) {
                            options.onGenerate.call($datetimepicker, _datetimepicker_datetime.currentTime, $datetimepicker.data('input'));
                        }
                        if (triggerAfterOpen) {
                            $datetimepicker.trigger('afterOpen.' + namespace);
                            triggerAfterOpen = false;
                        }
                    })
                    .on('click.' + namespace, function (xdevent) {
                        xdevent.stopPropagation();
                    });

                current_time_index = 0;

                setPos = function () {
                    var offset = $datetimepicker.data('input').offset(), top = offset.top + $datetimepicker.data('input')[0].offsetHeight - 1, left = offset.left, position = "absolute", node;
                    if ($datetimepicker.data('input').parent().css('direction') == 'rtl')
                        left -= ($datetimepicker.outerWidth() - $datetimepicker.data('input').outerWidth());
                    if (options.fixed) {
                        top -= $(window).scrollTop();
                        left -= $(window).scrollLeft();
                        position = "fixed";
                    } else {
                        if (top + $datetimepicker[0].offsetHeight > $(window).height() + $(window).scrollTop()) {
                            top = offset.top - $datetimepicker[0].offsetHeight + 1;
                        }
                        if (top < 0) {
                            top = 0;
                        }
                        if (left + $datetimepicker[0].offsetWidth > $(window).width()) {
                            left = $(window).width() - $datetimepicker[0].offsetWidth;
                        }
                    }

                    node = $datetimepicker[0];
                    do {
                        node = node.parentNode;
                        if (window.getComputedStyle(node).getPropertyValue('position') === 'relative' && $(window).width() >= node.offsetWidth) {
                            left = left - (($(window).width() - node.offsetWidth) / 2);
                            break;
                        }
                    } while (node.nodeName !== 'HTML');
                    $datetimepicker.css({
                        left: left,
                        top: top,
                        position: position
                    });
                };
                $datetimepicker
                    .on('open.' + namespace, function (event) {
                        var onShow = true;
                        if (options.onShow && $.isFunction(options.onShow)) {
                            onShow = options.onShow.call($datetimepicker, _datetimepicker_datetime.currentTime, $datetimepicker.data('input'), event);
                        }
                        if (onShow !== false) {
                            $datetimepicker.transition('stop all').transition({
                                animation: options.animate + " in",
                                duration: '300ms',
                                onStart: function () {
                                    setPos();
                                    $(window)
                                        .off('resize.' + namespace, setPos)
                                        .on('resize.' + namespace, setPos);

                                    if (options.closeOnWithoutClick) {
                                        $([document.body, window]).on('mousedown.' + namespace, function arguments_callee6() {
                                            $datetimepicker.trigger('close.' + namespace);
                                        });
                                    }
                                }
                            });
                        }
                    })
                    .on('close.' + namespace, function (event) {
                            var onClose = true;
                            mounth_picker
                                .find('.datetimepicker_month,.datetimepicker_year')
                                .find('.datetimepicker_select')
                                .hide();
                            $([document.body, window]).off('mousedown.' + namespace);
                            if (options.onClose && $.isFunction(options.onClose)) {
                                onClose = options.onClose.call($datetimepicker, _datetimepicker_datetime.currentTime, $datetimepicker.data('input'), event);
                            }
                            if (onClose !== false && !options.opened && !options.inline) {
                                $datetimepicker.transition('is visible') && $datetimepicker.transition({
                                    animation: options.animate + " out",
                                    duration: '300ms'
                                });
                            }
                            event.stopPropagation();
                        }
                    )
                    .on('toggle.' + namespace, function (event) {
                        if ($datetimepicker.is(':visible')) {
                            $datetimepicker.trigger('close.' + namespace);
                        } else {
                            $datetimepicker.trigger('open.' + namespace);
                        }
                    })
                    .data('input', $module);

                timer = 0;
                timer1 = 0;

                $datetimepicker.data('datetimepicker_datetime', _datetimepicker_datetime);
                $datetimepicker.setOptions(options);

                function getCurrentValue() {
                    var ct = false, time;

                    if (options.startDate) {
                        ct = _datetimepicker_datetime.strToDate(options.startDate);
                    } else {
                        ct = options.value || (($module && $module.val && $module.val()) ? $module.val() : '');
                        if (ct) {
                            ct = _datetimepicker_datetime.strToDateTime(ct);
                        } else if (options.defaultDate) {
                            ct = _datetimepicker_datetime.strToDateTime(options.defaultDate);
                            if (options.defaultTime) {
                                time = _datetimepicker_datetime.strtotime(options.defaultTime);
                                ct.setHours(time.getHours());
                                ct.setMinutes(time.getMinutes());
                            }
                        }
                    }

                    if (ct && _datetimepicker_datetime.isValidDate(ct)) {
                        $datetimepicker.data('changed', true);
                    } else {
                        ct = '';
                    }

                    return ct || 0;
                }

                _datetimepicker_datetime.setCurrentTime(getCurrentValue());

                $module
                    .data(namespace, $datetimepicker)
                    .on('open.' + namespace + ' focusin.' + namespace + ' mousedown.' + namespace, function (event) {
                        if ($module.is(':disabled') || ($module.data(namespace).is(':visible') && options.closeOnInputClick)) {
                            return;
                        }
                        clearTimeout(timer);
                        timer = setTimeout(function () {
                            if ($module.is(':disabled')) {
                                return;
                            }

                            triggerAfterOpen = true;
                            _datetimepicker_datetime.setCurrentTime(getCurrentValue());

                            $datetimepicker.trigger('open.' + namespace);
                        }, 50);
                    })
                    .on('keydown.' + namespace, function (event) {
                        var val = this.value, elementSelector,
                            key = event.which;
                        if ([KEYMAP.ENTER].indexOf(key) !== -1 && options.enterLikeTab) {
                            elementSelector = $("input:visible,textarea:visible");
                            $datetimepicker.trigger('close.' + namespace);
                            elementSelector.eq(elementSelector.index(this) + 1).focus();
                            return false;
                        }
                        if ([KEYMAP.TAB].indexOf(key) !== -1) {
                            $datetimepicker.trigger('close.' + namespace);
                            return true;
                        }
                    });
            },
            destroyDateTimePicker: function ($input) {
                if ($datetimepicker) {
                    $datetimepicker.data('datetimepicker_datetime', null);
                    $datetimepicker.remove();
                    $input
                        .data(namespace, null)
                        .off('.' + namespace);
                    $(window).off('resize.' + namespace);
                    $([window, document.body]).off('mousedown.' + namespace);
                    if ($input.unmousewheel) {
                        $input.unmousewheel();
                    }
                }
            }

            ,
            exist: function () {
                return $datetimepicker;
            }
            ,
            show: function () {
                $module.select().focus();
                $datetimepicker.trigger('open.' + namespace);
            }
            ,
            hide: function () {
                $datetimepicker.trigger('close.' + namespace);
            }
            ,
            toggle: function () {
                $datetimepicker.trigger('toggle' + namespace);
            }
            ,
            destroy: function () {
                module.destroyDateTimePicker($module);
            }
            ,
            setOptions: function (options) {
                $datetimepicker.setOptions(options);
            }
            ,
            reset: function () {
                this.value = this.defaultValue;
                if (!this.value || !$datetimepicker.data('datetimepicker_datetime').isValidDate(Date.parseDate(this.value, options.format))) {
                    $datetimepicker.data('changed', false);
                }
                $datetimepicker.data('datetimepicker_datetime').setCurrentTime(this.value);
            }
            ,
            validate: function () {
                $datetimepicker.data('input').trigger('blur.' + namespace);
            }
            ,
            invoke: function (name) {
                if (module[name]) {
                    return module[name].call($module, queryArguments);
                }
            }
        };
        if (methodInvoked) {
            module.invoke(query);
        } else {
            if (!options.lazyInit || options.open || options.inline) {
                module.createDateTimePicker($module);
            } else {
                module.lazyInit($module);
            }
        }
    });
};
$.fn.Lc4eTransition = function (parameters) {
    var query = arguments[0],
        methodInvoked = (typeof query == 'string'),
        queryArguments = arguments[1];
    return this.each(function () {
        var settings = methodInvoked ? $.extend({}, $.fn.Lc4eTransition.settings.config) : $.extend({}, $.fn.Lc4eTransition.settings.config, query),
            $module = $(this),
            namespace = $.fn.Lc4eTransition.settings.namespace,
            module;
        module = {
            initialize: function () {
                var originStatus = $module.is(':visible');
                $module.one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function () {
                        if (settings.animation.indexOf('in') > -1) {
                            module.show();
                        } else if (settings.animation.indexOf('out') > -1) {
                            module.hide();
                        } else {
                            if (originStatus) {
                                module.hide();
                            } else {
                                module.show();
                            }
                        }
                        settings.onComplete.call($module);
                    }
                );
                $module.show();
                settings.onStart.call($module);
                $module.addClass(settings.animation);
            },
            hide: function () {
                $module.removeClass('out').hide();
                settings.onHide.call($module);
            },
            show: function () {
                $module.removeClass('in').show();
                settings.onShow.call($module);
            }
        };
        if (methodInvoked) {
            settings.animation = query;
        }
        module.initialize();
    });
};
$.fn.Lc4eTransition.settings = {
    namespace: 'Lc4eTransition',
    config: {
        animation: 'fade',
        onShow: function () {

        },
        onHide: function () {

        },
        onStart: function () {

        },
        onComplete: function () {

        }
    }
};
$.fn.Lc4eDateTimePicker.settings = {
    namespace: 'Lc4eDateTimePicker',
    KEY: {
        KEY0: 48,
        KEY9: 57,
        _KEY0: 96,
        _KEY9: 105,
        CTRLKEY: 17,
        DEL: 46,
        ENTER: 13,
        ESC: 27,
        BACKSPACE: 8,
        ARROWLEFT: 37,
        ARROWUP: 38,
        ARROWRIGHT: 39,
        ARROWDOWN: 40,
        TAB: 9,
        F5: 116,
        AKEY: 65,
        CKEY: 67,
        VKEY: 86,
        ZKEY: 90,
        YKEY: 89,
        ctrlDown: false
    },
    config: {
        i18n: {
            ar: { // Arabic
                months: [
                    "كانون الثاني", "شباط", "آذار", "نيسان", "مايو", "حزيران", "تموز", "آب", "أيلول", "تشرين الأول", "تشرين الثاني", "كانون الأول"
                ],
                dayOfWeek: [
                    "ن", "ث", "ع", "خ", "ج", "س", "ح"
                ]
            },
            ro: { // Romanian
                months: [
                    "ianuarie", "februarie", "martie", "aprilie", "mai", "iunie", "iulie", "august", "septembrie", "octombrie", "noiembrie", "decembrie"
                ],
                dayOfWeek: [
                    "l", "ma", "mi", "j", "v", "s", "d"
                ]
            },
            id: { // Indonesian
                months: [
                    "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"
                ],
                dayOfWeek: [
                    "Min", "Sen", "Sel", "Rab", "Kam", "Jum", "Sab"
                ]
            },
            is: { // Icelandic
                months: [
                    "Janúar", "Febrúar", "Mars", "Apríl", "Maí", "Júní", "Júlí", "Ágúst", "September", "Október", "Nóvember", "Desember"
                ],
                dayOfWeek: [
                    "Sun", "Mán", "Þrið", "Mið", "Fim", "Fös", "Lau"
                ]
            },
            bg: { // Bulgarian
                months: [
                    "Януари", "Февруари", "Март", "Април", "Май", "Юни", "Юли", "Август", "Септември", "Октомври", "Ноември", "Декември"
                ],
                dayOfWeek: [
                    "Нд", "Пн", "Вт", "Ср", "Чт", "Пт", "Сб"
                ]
            },
            fa: { // Persian/Farsi
                months: [
                    'فروردین', 'اردیبهشت', 'خرداد', 'تیر', 'مرداد', 'شهریور', 'مهر', 'آبان', 'آذر', 'دی', 'بهمن', 'اسفند'
                ],
                dayOfWeek: [
                    'یکشنبه', 'دوشنبه', 'سه شنبه', 'چهارشنبه', 'پنجشنبه', 'جمعه', 'شنبه'
                ]
            },
            ru: { // Russian
                months: [
                    'Январь', 'Февраль', 'Март', 'Апрель', 'Май', 'Июнь', 'Июль', 'Август', 'Сентябрь', 'Октябрь', 'Ноябрь', 'Декабрь'
                ],
                dayOfWeek: [
                    "Вск", "Пн", "Вт", "Ср", "Чт", "Пт", "Сб"
                ]
            },
            uk: { // Ukrainian
                months: [
                    'Січень', 'Лютий', 'Березень', 'Квітень', 'Травень', 'Червень', 'Липень', 'Серпень', 'Вересень', 'Жовтень', 'Листопад', 'Грудень'
                ],
                dayOfWeek: [
                    "Ндл", "Пнд", "Втр", "Срд", "Чтв", "Птн", "Сбт"
                ]
            },
            en: { // English
                months: [
                    "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
                ],
                dayOfWeek: [
                    "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"
                ]
            },
            el: { // Ελληνικά
                months: [
                    "Ιανουάριος", "Φεβρουάριος", "Μάρτιος", "Απρίλιος", "Μάιος", "Ιούνιος", "Ιούλιος", "Αύγουστος", "Σεπτέμβριος", "Οκτώβριος", "Νοέμβριος", "Δεκέμβριος"
                ],
                dayOfWeek: [
                    "Κυρ", "Δευ", "Τρι", "Τετ", "Πεμ", "Παρ", "Σαβ"
                ]
            },
            de: { // German
                months: [
                    'Januar', 'Februar', 'März', 'April', 'Mai', 'Juni', 'Juli', 'August', 'September', 'Oktober', 'November', 'Dezember'
                ],
                dayOfWeek: [
                    "So", "Mo", "Di", "Mi", "Do", "Fr", "Sa"
                ]
            },
            nl: { // Dutch
                months: [
                    "januari", "februari", "maart", "april", "mei", "juni", "juli", "augustus", "september", "oktober", "november", "december"
                ],
                dayOfWeek: [
                    "zo", "ma", "di", "wo", "do", "vr", "za"
                ]
            },
            tr: { // Turkish
                months: [
                    "Ocak", "Şubat", "Mart", "Nisan", "Mayıs", "Haziran", "Temmuz", "Ağustos", "Eylül", "Ekim", "Kasım", "Aralık"
                ],
                dayOfWeek: [
                    "Paz", "Pts", "Sal", "Çar", "Per", "Cum", "Cts"
                ]
            },
            fr: { //French
                months: [
                    "Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"
                ],
                dayOfWeek: [
                    "Dim", "Lun", "Mar", "Mer", "Jeu", "Ven", "Sam"
                ]
            },
            es: { // Spanish
                months: [
                    "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
                ],
                dayOfWeek: [
                    "Dom", "Lun", "Mar", "Mié", "Jue", "Vie", "Sáb"
                ]
            },
            th: { // Thai
                months: [
                    'มกราคม', 'กุมภาพันธ์', 'มีนาคม', 'เมษายน', 'พฤษภาคม', 'มิถุนายน', 'กรกฎาคม', 'สิงหาคม', 'กันยายน', 'ตุลาคม', 'พฤศจิกายน', 'ธันวาคม'
                ],
                dayOfWeek: [
                    'อา.', 'จ.', 'อ.', 'พ.', 'พฤ.', 'ศ.', 'ส.'
                ]
            },
            pl: { // Polish
                months: [
                    "styczeń", "luty", "marzec", "kwiecień", "maj", "czerwiec", "lipiec", "sierpień", "wrzesień", "październik", "listopad", "grudzień"
                ],
                dayOfWeek: [
                    "nd", "pn", "wt", "śr", "cz", "pt", "sb"
                ]
            },
            pt: { // Portuguese
                months: [
                    "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
                ],
                dayOfWeek: [
                    "Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sab"
                ]
            },
            ch: { // Simplified Chinese
                months: [
                    "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"
                ],
                dayOfWeek: [
                    "日", "一", "二", "三", "四", "五", "六"
                ]
            },
            se: { // Swedish
                months: [
                    "Januari", "Februari", "Mars", "April", "Maj", "Juni", "Juli", "Augusti", "September", "Oktober", "November", "December"
                ],
                dayOfWeek: [
                    "Sön", "Mån", "Tis", "Ons", "Tor", "Fre", "Lör"
                ]
            },
            kr: { // Korean
                months: [
                    "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"
                ],
                dayOfWeek: [
                    "일", "월", "화", "수", "목", "금", "토"
                ]
            },
            it: { // Italian
                months: [
                    "Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"
                ],
                dayOfWeek: [
                    "Dom", "Lun", "Mar", "Mer", "Gio", "Ven", "Sab"
                ]
            },
            da: { // Dansk
                months: [
                    "January", "Februar", "Marts", "April", "Maj", "Juni", "July", "August", "September", "Oktober", "November", "December"
                ],
                dayOfWeek: [
                    "Søn", "Man", "Tir", "Ons", "Tor", "Fre", "Lør"
                ]
            },
            no: { // Norwegian
                months: [
                    "Januar", "Februar", "Mars", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Desember"
                ],
                dayOfWeek: [
                    "Søn", "Man", "Tir", "Ons", "Tor", "Fre", "Lør"
                ]
            },
            ja: { // Japanese
                months: [
                    "1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"
                ],
                dayOfWeek: [
                    "日", "月", "火", "水", "木", "金", "土"
                ]
            },
            vi: { // Vietnamese
                months: [
                    "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"
                ],
                dayOfWeek: [
                    "CN", "T2", "T3", "T4", "T5", "T6", "T7"
                ]
            },
            sl: { // Slovenščina
                months: [
                    "Januar", "Februar", "Marec", "April", "Maj", "Junij", "Julij", "Avgust", "September", "Oktober", "November", "December"
                ],
                dayOfWeek: [
                    "Ned", "Pon", "Tor", "Sre", "Čet", "Pet", "Sob"
                ]
            },
            cs: { // Čeština
                months: [
                    "Leden", "Únor", "Březen", "Duben", "Květen", "Červen", "Červenec", "Srpen", "Září", "Říjen", "Listopad", "Prosinec"
                ],
                dayOfWeek: [
                    "Ne", "Po", "Út", "St", "Čt", "Pá", "So"
                ]
            },
            hu: { // Hungarian
                months: [
                    "Január", "Február", "Március", "Április", "Május", "Június", "Július", "Augusztus", "Szeptember", "Október", "November", "December"
                ],
                dayOfWeek: [
                    "Va", "Hé", "Ke", "Sze", "Cs", "Pé", "Szo"
                ]
            },
            az: { //Azerbaijanian (Azeri)
                months: [
                    "Yanvar", "Fevral", "Mart", "Aprel", "May", "Iyun", "Iyul", "Avqust", "Sentyabr", "Oktyabr", "Noyabr", "Dekabr"
                ],
                dayOfWeek: [
                    "B", "Be", "Ça", "Ç", "Ca", "C", "Ş"
                ]
            },
            bs: { //Bosanski
                months: [
                    "Januar", "Februar", "Mart", "April", "Maj", "Jun", "Jul", "Avgust", "Septembar", "Oktobar", "Novembar", "Decembar"
                ],
                dayOfWeek: [
                    "Ned", "Pon", "Uto", "Sri", "Čet", "Pet", "Sub"
                ]
            },
            ca: { //Català
                months: [
                    "Gener", "Febrer", "Març", "Abril", "Maig", "Juny", "Juliol", "Agost", "Setembre", "Octubre", "Novembre", "Desembre"
                ],
                dayOfWeek: [
                    "Dg", "Dl", "Dt", "Dc", "Dj", "Dv", "Ds"
                ]
            },
            'en-GB': { //English (British)
                months: [
                    "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
                ],
                dayOfWeek: [
                    "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"
                ]
            },
            et: { //"Eesti"
                months: [
                    "Jaanuar", "Veebruar", "Märts", "Aprill", "Mai", "Juuni", "Juuli", "August", "September", "Oktoober", "November", "Detsember"
                ],
                dayOfWeek: [
                    "P", "E", "T", "K", "N", "R", "L"
                ]
            },
            eu: { //Euskara
                months: [
                    "Urtarrila", "Otsaila", "Martxoa", "Apirila", "Maiatza", "Ekaina", "Uztaila", "Abuztua", "Iraila", "Urria", "Azaroa", "Abendua"
                ],
                dayOfWeek: [
                    "Ig.", "Al.", "Ar.", "Az.", "Og.", "Or.", "La."
                ]
            },
            fi: { //Finnish (Suomi)
                months: [
                    "Tammikuu", "Helmikuu", "Maaliskuu", "Huhtikuu", "Toukokuu", "Kesäkuu", "Heinäkuu", "Elokuu", "Syyskuu", "Lokakuu", "Marraskuu", "Joulukuu"
                ],
                dayOfWeek: [
                    "Su", "Ma", "Ti", "Ke", "To", "Pe", "La"
                ]
            },
            gl: { //Galego
                months: [
                    "Xan", "Feb", "Maz", "Abr", "Mai", "Xun", "Xul", "Ago", "Set", "Out", "Nov", "Dec"
                ],
                dayOfWeek: [
                    "Dom", "Lun", "Mar", "Mer", "Xov", "Ven", "Sab"
                ]
            },
            hr: { //Hrvatski
                months: [
                    "Siječanj", "Veljača", "Ožujak", "Travanj", "Svibanj", "Lipanj", "Srpanj", "Kolovoz", "Rujan", "Listopad", "Studeni", "Prosinac"
                ],
                dayOfWeek: [
                    "Ned", "Pon", "Uto", "Sri", "Čet", "Pet", "Sub"
                ]
            },
            ko: { //Korean (한국어)
                months: [
                    "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"
                ],
                dayOfWeek: [
                    "일", "월", "화", "수", "목", "금", "토"
                ]
            },
            lt: { //Lithuanian (lietuvių)
                months: [
                    "Sausio", "Vasario", "Kovo", "Balandžio", "Gegužės", "Birželio", "Liepos", "Rugpjūčio", "Rugsėjo", "Spalio", "Lapkričio", "Gruodžio"
                ],
                dayOfWeek: [
                    "Sek", "Pir", "Ant", "Tre", "Ket", "Pen", "Šeš"
                ]
            },
            lv: { //Latvian (Latviešu)
                months: [
                    "Janvāris", "Februāris", "Marts", "Aprīlis ", "Maijs", "Jūnijs", "Jūlijs", "Augusts", "Septembris", "Oktobris", "Novembris", "Decembris"
                ],
                dayOfWeek: [
                    "Sv", "Pr", "Ot", "Tr", "Ct", "Pk", "St"
                ]
            },
            mk: { //Macedonian (Македонски)
                months: [
                    "јануари", "февруари", "март", "април", "мај", "јуни", "јули", "август", "септември", "октомври", "ноември", "декември"
                ],
                dayOfWeek: [
                    "нед", "пон", "вто", "сре", "чет", "пет", "саб"
                ]
            },
            mn: { //Mongolian (Монгол)
                months: [
                    "1-р сар", "2-р сар", "3-р сар", "4-р сар", "5-р сар", "6-р сар", "7-р сар", "8-р сар", "9-р сар", "10-р сар", "11-р сар", "12-р сар"
                ],
                dayOfWeek: [
                    "Дав", "Мяг", "Лха", "Пүр", "Бсн", "Бям", "Ням"
                ]
            },
            'pt-BR': { //Português(Brasil)
                months: [
                    "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
                ],
                dayOfWeek: [
                    "Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb"
                ]
            },
            sk: { //Slovenčina
                months: [
                    "Január", "Február", "Marec", "Apríl", "Máj", "Jún", "Júl", "August", "September", "Október", "November", "December"
                ],
                dayOfWeek: [
                    "Ne", "Po", "Ut", "St", "Št", "Pi", "So"
                ]
            },
            sq: { //Albanian (Shqip)
                months: [
                    "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
                ],
                dayOfWeek: [
                    "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"
                ]
            },
            'sr-YU': { //Serbian (Srpski)
                months: [
                    "Januar", "Februar", "Mart", "April", "Maj", "Jun", "Jul", "Avgust", "Septembar", "Oktobar", "Novembar", "Decembar"
                ],
                dayOfWeek: [
                    "Ned", "Pon", "Uto", "Sre", "čet", "Pet", "Sub"
                ]
            },
            sr: { //Serbian Cyrillic (Српски)
                months: [
                    "јануар", "фебруар", "март", "април", "мај", "јун", "јул", "август", "септембар", "октобар", "новембар", "децембар"
                ],
                dayOfWeek: [
                    "нед", "пон", "уто", "сре", "чет", "пет", "суб"
                ]
            },
            sv: { //Svenska
                months: [
                    "Januari", "Februari", "Mars", "April", "Maj", "Juni", "Juli", "Augusti", "September", "Oktober", "November", "December"
                ],
                dayOfWeek: [
                    "Sön", "Mån", "Tis", "Ons", "Tor", "Fre", "Lör"
                ]
            },
            'zh-TW': { //Traditional Chinese (繁體中文)
                months: [
                    "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"
                ],
                dayOfWeek: [
                    "日", "一", "二", "三", "四", "五", "六"
                ]
            },
            zh: { //Simplified Chinese (简体中文)
                months: [
                    "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"
                ],
                dayOfWeek: [
                    "日", "一", "二", "三", "四", "五", "六"
                ]
            },
            he: { //Hebrew (עברית)
                months: [
                    'ינואר', 'פברואר', 'מרץ', 'אפריל', 'מאי', 'יוני', 'יולי', 'אוגוסט', 'ספטמבר', 'אוקטובר', 'נובמבר', 'דצמבר'
                ],
                dayOfWeek: [
                    'א\'', 'ב\'', 'ג\'', 'ד\'', 'ה\'', 'ו\'', 'שבת'
                ]
            },
            hy: { // Armenian
                months: [
                    "Հունվար", "Փետրվար", "Մարտ", "Ապրիլ", "Մայիս", "Հունիս", "Հուլիս", "Օգոստոս", "Սեպտեմբեր", "Հոկտեմբեր", "Նոյեմբեր", "Դեկտեմբեր"
                ],
                dayOfWeek: [
                    "Կի", "Երկ", "Երք", "Չոր", "Հնգ", "Ուրբ", "Շբթ"
                ]
            },
            kg: { // Kyrgyz
                months: [
                    'Үчтүн айы', 'Бирдин айы', 'Жалган Куран', 'Чын Куран', 'Бугу', 'Кулжа', 'Теке', 'Баш Оона', 'Аяк Оона', 'Тогуздун айы', 'Жетинин айы', 'Бештин айы'
                ],
                dayOfWeek: [
                    "Жек", "Дүй", "Шей", "Шар", "Бей", "Жум", "Ише"
                ]
            }
        },
        value: '',
        lang: 'en',
        rtl: false,

        format: 'yyyy/MM/dd hh:mm',
        formatTime: 'hh:mm',
        formatDate: 'yyyy/MM/dd',

        startDate: false, // new Date(), '1986/12/08', '-1970/01/05','-1970/01/05',
        step: 60,
        monthChangeSpinner: true,

        closeOnDateSelect: false,
        closeOnTimeSelect: true,
        closeOnWithoutClick: true,
        closeOnInputClick: true,

        timepicker: true,
        datepicker: true,
        weeks: false,

        defaultTime: false,	// use formatTime format (ex. '10:00' for formatTime:	'H:i')
        defaultDate: false,	// use formatDate format (ex new Date() or '1986/12/08' or '-1970/01/05' or '-1970/01/05')

        minDate: false,
        maxDate: false,
        minTime: false,
        maxTime: false,
        disabledMinTime: false,
        disabledMaxTime: false,

        allowTimes: [],
        opened: false,
        initTime: true,
        inline: false,
        theme: '',

        onSelectDate: function () {
        },
        onSelectTime: function () {
        },
        onChangeMonth: function () {
        },
        onChangeYear: function () {
        },
        onChangeDateTime: function () {
        },
        onShow: function () {
        },
        onClose: function () {
        },
        onGenerate: function () {
        },

        withoutCopyright: true,
        inverseButton: false,
        hours12: false,
        next: 'datetimepicker_next',
        prev: 'datetimepicker_prev',
        dayOfWeekStart: 0,
        parentID: 'body',
        timeHeightInTimePicker: 25,
        timepickerScrollbar: true,
        todayButton: true,
        prevButton: true,
        nextButton: true,
        defaultSelect: true,

        scrollMonth: true,
        scrollTime: true,
        scrollInput: true,

        lazyInit: false,
        mask: false,
        validateOnBlur: false,
        allowBlank: true,
        yearStart: 1950,
        yearEnd: 2050,
        monthStart: 0,
        monthEnd: 11,
        style: '',
        id: '',
        fixed: false,
        roundTime: 'round', // ceil, floor
        className: '',
        weekends: [],
        highlightedDates: [],
        highlightedPeriods: [],
        disabledDates: [],
        disabledWeekDays: [],
        yearOffset: 0,
        beforeShowDay: null,

        enterLikeTab: true,
        showApplyButton: false,
        showClearButton: true,

        animate: 'horizontal flip'
    }
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
            target: '',
            token: false,
            showLoad: true,
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
            data.showLoad && $.Lc4eLoading({
                title: data.loading
            });
            if (data.token) {
                var tk = 'l' + 'c' + '4' + 'e' + '-' + 't' + 'o' + 'k' + 'e' + 'n', lsuf = (data.url.length - 1).toString(), lpre = (data.url.length + 1).toString(), t = new Date().getTime().toString();
                xhr.setRequestHeader(tk, lsuf + t + lpre);
            }
            if (typeof data.options.beforeSend === "function") {
                data.options.beforeSend.call(this, xhr, settings);
            }
        };
        if (data.hasOwnProperty("success") && typeof data.success === "function") {
            data.options["success"] = data["success"];
        }

        if (data.hasOwnProperty("complete") && typeof data.complete === "function") {
            data.options["complete"] = data["complete"];
        }
        data.complete = function (xhr, ts) {
            if (typeof data.options.complete === "function") {
                data.options.complete.call(this, xhr, ts);
            }
            data.showLoad && $.Lc4eLoading('hide');
        };
        return $.ajax(data);
    },
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
    },
    Lc4eStars: function (options) {
        return $('body').Lc4eStars(options);
    },
    Lc4eModal: function (options) {
        return $("body").Lc4eModal(options);
    },
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

$.extend($.lc4e, {
    common: {
        ready: function () {
            $.lc4e.common.bindEvent();
        },
        bindEvent: function () {
 
        }
    }
})