/**
 * Created by teddyzhu on 15/12/5.
 */

var Vue = require('vue'),
    Vueplugins = {
        VueRouter: require('vue-router'),
        VueResource: require('vue-resource'),
        SeTransition:require('./semantic/transition.js')
    };
for(var index in Vueplugins){
    Vue.use(Vueplugins[index]);
}

var router = new Vueplugins.VueRouter({
    history: false
});

router.map(require('./routes'));

Vue.config.debug = true;
router.start(require("./app.vue"), '#lc4eApp');
