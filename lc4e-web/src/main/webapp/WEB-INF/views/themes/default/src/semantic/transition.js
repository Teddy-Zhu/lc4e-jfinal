/**
 * Created by teddyzhu on 15/12/18.
 */

module.exports = function (Vue, options) {
    'use strict';
    var duration = (options || {}).duration || 1000,
        transitions = {
            defineEmphasis: function (name, duration) {
                return {
                    beforeEnter: function (el) {
                    },
                    enter: function (el) {
                        $(el).addClass('animating transition ' + name + ' in', duration, done);
                    },
                    leave: function (el) {
                        $(el).removeClass('in').addClass('out');
                    }
                };
            },
            defineAppearance: function (name, duration) {

                return {
                    enter: function (el) {
                        $(el).addClass('animating transition ' + name + ' in', duration, done);
                    },

                    leave: function (el) {
                        $(el).removeClass('in').addClass('out');
                    }
                };
            }
        },
        emphasis = [
            'flash',
            'shake',
            'pulse',
            'tada',
            'bounce'
        ],
        appearance = [
            'slide up',
            'slide down',
            'vertical flip',
            'horizontal flip',
            'fade',
            'fade up',
            'fade down',
            'scale',
            'drop',
            'browse'
        ];

    emphasis.forEach(function (animation) {
        var definition = transitions.defineEmphasis(animation, duration);

        Vue.transition(animation, definition);
    });

    appearance.forEach(function (animation) {
        var definition = transitions.defineAppearance(animation, duration), id = animation.split(' ').join('-');

        Vue.transition(id, definition);
    });
};
