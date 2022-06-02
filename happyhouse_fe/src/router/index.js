import Vue from "vue";
import VueRouter from "vue-router";
import HomeView from "../views/HomeView.vue";

import NewsView from "../views/newsView.vue";

import RegionView from "../views/regionView.vue";

import NoticeView from "../views/noticeView.vue";
import NoticeListView from "../views/notice/noticeListView.vue";
import NoticeDetailView from "../views/notice/noticeDetailView.vue";
import NoticeRegisterView from "../views/notice/noticeRegisterView.vue";
import NoticeModifyView from "../views/notice/noticeModifyView.vue";

import QnaView from "../views/qnaView.vue";
import QnaRegisterView from "../views/qna/qnaRegisterView.vue";
import QnaDetailView from "../views/qna/qnaDetailView.vue";
import QnaListView from "../views/qna/qnaListView.vue";
import QnaModifyView from "../views/qna/qnaModifyView.vue";

import MemberView from "../views/memberView.vue";
import MemberLoginView from "../views/member/memberLoginView.vue";
import MemberLogupView from "../views/member/memberLogupView.vue";
import MemberMypageView from "../views/member/memberMypageView.vue";
import MemberLoginKakaoView from "../views/member/memberLoginKakaoView.vue";

import HouseView from "../views/houseView.vue";
import HouseMapSearchView from "../views/house/houseMapSearchView.vue";
import HouseDetailView from "../views/house/houseDetailView.vue";

import CountAmount from "../views/chart/countAmountView.vue";
import MaxAmountByDong from "../views/chart/maxAmountByDongView.vue";
import MaxAmountByGu from "../views/chart/maxAmountByGuView.vue";
import ByPrice from "../views/chart/byPriceView.vue";

import AlarmView from "../views/alarmView.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "home",
    component: HomeView,
    redirect: "/countAmount",
    children: [
      {
        path: "countAmount",
        name: "CountAmount",
        component: CountAmount,
      },
      {
        path: "maxAmountByDong",
        name: "MaxAmountByDong",
        component: MaxAmountByDong,
      },
      {
        path: "maxAmountByGu",
        name: "MaxAmountByGu",
        component: MaxAmountByGu,
      },
      {
        path: "byPrice",
        name: "ByPrice",
        component: ByPrice,
      },
    ],
  },
  {
    path: "/news",
    name: "NewsView",
    component: NewsView,
  },
  {
    path: "/alarm",
    name: "AlarmView",
    component: AlarmView,
  },
  {
    path: "/house",
    name: "HouseView",
    component: HouseView,
    redirect: "/house/search",
    children: [
      {
        path: "search",
        name: "HouseMapSearchView",
        component: HouseMapSearchView,
      },
      {
        path: "detail/:no",
        name: "HouseDetailView",
        component: HouseDetailView,
      },
    ],
  },
  {
    path: "/region",
    name: "RegionView",
    component: RegionView,
  },
  {
    path: "/notice",
    name: "NoticeView",
    component: NoticeView,
    redirect: "/notice/list",
    children: [
      {
        path: "list",
        name: "NoticeListView",
        component: NoticeListView,
      },
      {
        path: "detail/:nnum",
        name: "NoticeDetailView",
        component: NoticeDetailView,
      },
      {
        path: "register",
        name: "NoticeRegisterView",
        component: NoticeRegisterView,
      },
      {
        path: "modify/:nnum",
        name: "NoticeModifyView",
        component: NoticeModifyView,
      },
    ],
  },
  {
    path: "/qna",
    name: "QnaView",
    component: QnaView,
    redirect: "/qna/list",
    children: [
      {
        path: "list",
        name: "QnaListView",
        component: QnaListView,
      },
      {
        path: "register",
        name: "QnaRegisterView",
        component: QnaRegisterView,
      },
      {
        path: "detail/:qnum",
        name: "QnaDetailView",
        component: QnaDetailView,
      },
      {
        path: "modify/:qnum",
        name: "QnaModifyView",
        component: QnaModifyView,
      },
    ],
  },
  {
    path: "/member",
    name: "MemberView",
    component: MemberView,
    redirect: "/member/login",
    children: [
      {
        path: "login",
        name: "MemberLoginView",
        component: MemberLoginView,
      },
      {
        path: "logup",
        name: "MemberLogupView",
        component: MemberLogupView,
      },
      {
        path: "mypage",
        name: "MemberMypageView",
        component: MemberMypageView,
      },
      {
        path: "kakao",
        name: "MemberLoginKakaoView",
        component: MemberLoginKakaoView,
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
  // scrollBehavior(to, from, savedPosition) {
  //   console.log(to, from, savedPosition);
  //   return { x: 0, y: 0 };
  // },
});

export default router;
