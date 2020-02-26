import Vue from 'vue';
import VueRouter from 'vue-router';

import "./polyfill";
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'
import '@mdi/font/css/materialdesignicons.css'
import '../css/zuliaui.css';
import moment from 'moment';
import 'es7-object-polyfill';

Vue.use(VueRouter);

Vue.use(Vuetify);

window.Vue = Vue;
window.Vuetify = Vuetify;
window.VueRouter = VueRouter;
window.Moment = moment;