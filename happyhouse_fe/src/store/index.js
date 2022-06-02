import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);
import MemberLoginView from "../views/member/memberLoginView.vue";
import axios from "axios";
export default new Vuex.Store({
  state: {
    news: [],
    current: [],
    like: [],
  },
  getters: {},
  mutations: {
    GET_NEWS(state, payload) {
      state.news = payload;
    },
    GET_CURRENT(state, payload) {
      state.current = payload;
    },
    GET_LIKE(state, payload) {
      state.like = payload;
    },
  },
  actions: {
    getNews(context) {
      axios.get("http://localhost:7779/news").then((resp) => {
        // this.news = resp.data.items;
        context.commit("GET_NEWS", resp.data.items);
      });
    },
    getCurrent(context) {
      axios.get("http://localhost:7779/house/current").then((resp) => {
        // console.log(resp);
        // this.current = resp.data;
        context.commit("GET_CURRENT", resp.data);
        // });
      });
    },
    getHouseLike(context) {
      axios.get("http://localhost:7779/house/search/like/rank").then((resp) => {
        // console.log(resp);
        // this.current = resp.data;
        context.commit("GET_LIKE", resp.data);
        // });
      });
    },
  },
  modules: {
    MemberLoginView,
  },
  plugins: [
    createPersistedState({
      // 브라우저 종료시 제거하기 위해 localStorage가 아닌 sessionStorage로 변경. (default: localStorage)
      storage: sessionStorage,
    }),
  ],
});
