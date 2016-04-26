/**
 * Created by teddyzhu on 16/4/26.
 */
module.exports = function (Vue) {
    'use strict';
    var vIf = Vue.directive('if');

    Vue.directive("form", {
        priority: vIf.priority + 16,
        bind: function () {
            var $form = $(this.el);
            $form.Lc4eForm();
        },
        update: function (old) {

        }
    });

    //
    // Vue.directive("validator", {
    //     bind: function () {
    //         this.el.validator = {validate: false};
    //
    //         console.log(this.el);
    //     },
    //     update: function (old, value) {
    //         if (old) {
    //             this.el.validator.validate = true;
    //             this.el.validator["rules"] = old
    //         }
    //     },
    //     unbind: function () {
    //         delete this.el.validator;
    //     }
    // })
}