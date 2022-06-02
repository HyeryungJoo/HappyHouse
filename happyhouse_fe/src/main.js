import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import VueGlide from "vue-glide-js";
import "vue-glide-js/dist/vue-glide.css";

import { BootstrapVue, IconsPlugin } from "bootstrap-vue";

import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";

import { CarouselPlugin } from "bootstrap-vue";
import { BCarousel } from "bootstrap-vue";

Vue.component("b-carousel", BCarousel);
Vue.use(CarouselPlugin);
Vue.config.productionTip = false;

Vue.use(BootstrapVue);
Vue.use(IconsPlugin);
window.Kakao.init("");
Vue.use(VueGlide);

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
