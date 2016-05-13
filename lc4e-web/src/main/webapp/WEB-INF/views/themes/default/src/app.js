/**
 * Created by teddyzhu on 15/12/5.
 */
var Vue = require('vue'),
    Vueplugins = {
        VueRouter: require('vue-router'),
        VueResource: require('vue-resource'),
        VueWaves: require('vue-waves'),
        VueSmoothScroll: require('vue-smoothscroll')
       // VueValidate: require('./semantic/form.js')
    };
for (var index in Vueplugins) {
    Vue.use(Vueplugins[index]);
}

var router = new Vueplugins.VueRouter({
    history: true
});

router.beforeEach(function (transition) {
    window.SmoothScroll.destroy();
    transition.next();
});

router.afterEach(function (transition) {
    window.SmoothScroll.run();
});

router.map(require('./routes.js'));

Vue.config.debug = false;
Vue.config.silent = true;
router.start(require("./app.vue"), '#app');
