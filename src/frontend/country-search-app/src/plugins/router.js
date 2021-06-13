import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: "/",
            name: 'home',
            component: () => import('../views/Homepage.vue')
        },
        {
            path: "/success",
            name: 'success',
            component: () => import('../views/Success.vue')
        },
        {
            path: "/error",
            name: 'error',
            component: () => import('../views/Error.vue')
        }

    ],
    mode: "history"
});
