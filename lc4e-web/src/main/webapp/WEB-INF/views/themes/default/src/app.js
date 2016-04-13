/**
 * Created by teddyzhu on 15/12/5.
 */

var Vue = require('vue'),
    Vueplugins = {
        VueRouter: require('vue-router'),
        VueResource: require('vue-resource'),
        VueWaves: require('vue-waves')
    };
for (var index in Vueplugins) {
    Vue.use(Vueplugins[index]);
}

var router = new Vueplugins.VueRouter({
    history: true
});

router.map(require('./routes.js'));

Vue.config.debug = false;
Vue.config.silent = true;
router.start(require("./app.vue"), '#app');
