/**
 * Created by teddyzhu on 15/12/6.
 */
module.exports = {
    '/': {
        component: require('./views/index.vue')
    },
    "/all": {
        component: require('./views/index.vue')
    },
    "/a/*any": {
        component: require('./views/area.vue')
    },
    '*': {
        component: require('./views/index.vue')
    }
};