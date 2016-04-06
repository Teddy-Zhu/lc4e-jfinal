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
    "/a/*area": {
        component: require('./views/area.vue')
    },
    "/t/*thread": {
        component: require('./views/thread.vue')
    },
    '*': {
        component: require('./views/index.vue')
    }
};