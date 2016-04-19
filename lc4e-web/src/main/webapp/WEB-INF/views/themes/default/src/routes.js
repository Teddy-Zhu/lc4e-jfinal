/**
 * Created by teddyzhu on 15/12/6.
 */
module.exports = {
    '/': {
        component: require('./views/index.vue')
    },
    "/all": {
        name: 'index',
        component: require('./views/index.vue')
    },
    "/a/*area": {
        name: 'area',
        component: require('./views/area.vue')
    },
    "/t/*thread": {
        name: 'thread',
        component: require('./views/thread.vue')
    },
    "/SignIn": {
        name: 'signin',
        component: require('./views/signin.vue')
    },
    "/SignUp": {
        name: 'signup',
        component: require('./views/signup.vue')
    },
    '*': {
        component: require('./views/index.vue')
    }
};