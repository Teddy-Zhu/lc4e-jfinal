/**
 * Created by teddyzhu on 15/12/18.
 */

module.exports = function (Vue, options) {
    var duration = (options || {}).duration || 1000,
        transitions = {
            defineEmphasis: function (name, duration) {
                return {
                    css: false,
                    beforeEnter: function (el) {
                        $(el).show();
                    },

                    enter: function (el, done) {
                        $(el).transition(name, duration, done);

                        return function() {
                            $(el).transition('stop');
                        }
                    },

                    leave: function (el, done) {
                        $(el)
                            .transition('reset')
                            .transition(name, duration, done)
                            .hide();

                        return function() {
                            $(el).transition('stop');
                        }
                    }
                };
            },
            defineAppearance: function (name, duration) {

                return {
                    css: false,
                    enter: function (el, done) {
                        $(el)
                            .transition('reset')
                            .transition(name + ' in', duration, done);

                        return function() {
                            $(el).transition('stop');
                        }
                    },

                    leave: function (el, done) {
                        $(el)
                            .transition('reset')
                            .transition(name + ' out', duration, done);

                        return function() {
                            $(el).transition('stop');
                        }
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
        var definition = transitions.defineAppearance(animation, duration);
        var id = animation.split(' ').join('-');

        Vue.transition(id, definition);
    });
};
