/**
 * Created by teddyzhu on 15/12/5.
 */

var Vue = require('vue'),
    Vueplugins = {
        VueRouter: require('vue-router'),
        VueResource: require('vue-resource')
    };
for (var index in Vueplugins) {
    Vue.use(Vueplugins[index]);
}

Vue.transition('fade', {

    beforeEnter: function (el) {
       console.log("beforeEnter");
    },
    enter: function (el) {
        console.log("enter");
    },
    afterEnter: function (el) {
        console.log("afterEnter");
    },
    enterCancelled: function (el) {
        console.log("enterCancelled");
    },

    beforeLeave: function (el) {
        console.log("beforeLeave");
    },
    leave: function (el) {
        console.log("leave");
    },
    afterLeave: function (el) {
        console.log("afterLeave");
    },
    leaveCancelled: function (el) {
        console.log("leaveCancelled");
    }
});

var router = new Vueplugins.VueRouter({
    history: false
});

router.map(require('./routes'));

Vue.config.debug = false;
Vue.config.silent = true;
router.start(require("./app.vue"), '#app');
