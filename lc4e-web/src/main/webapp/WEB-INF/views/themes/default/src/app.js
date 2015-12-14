/**
 * Created by teddyzhu on 15/12/5.
 */

var Vue = require('vue');
var VueRouter = require('vue-router');
var VueResource = require('vue-resource');
Vue.use(VueRouter);
Vue.use(VueResource);

var router = new VueRouter({
    history: true
});

router.map(require('./routes'));

Vue.config.debug = true;
router.start(require("./app.vue"), '#lc4eApp');
