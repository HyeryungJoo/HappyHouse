<!-- 85af4b 274f35 eeeded 778899 -->
<template>
  <div id="app">
    <header class="header">
      <div class="container">
        <div class="row">
          <div class="col-lg-9">
            <div class="header__nav">
              <nav class="header__menu mobile-menu">
                <div class="header__logo">
                  <router-link to="/">
                    <img
                      class="header_logo"
                      src="./assets/logo_green1.png"
                      alt=""
                      width="150px"
                      height="80px"
                    />
                  </router-link>
                </div>
                <ul>
                  <li>
                    <router-link to="/house">지도로 매물보기</router-link>
                  </li>
                  <li>
                    <router-link to="/region">동네 정보 보기</router-link>
                  </li>
                  <li>
                    <router-link to="/qna">동네 생활 Q&A</router-link>
                  </li>
                  <li><router-link to="/news">부동산 소식</router-link></li>
                  <li><router-link to="/notice">공지사항</router-link></li>
                </ul>
              </nav>
            </div>
          </div>
          <div class="col-lg-2">
            <div class="header__right">
              <router-link to="/alarm"
                ><b-icon icon="bell-fill" class="icon icon_bell"></b-icon
              ></router-link>
              <router-link v-if="!accessToken" to="/member"
                ><b-icon
                  icon="person-fill"
                  class="icon icon_profile"
                  title="로그인"
                ></b-icon
              ></router-link>
              <div class="header_icon" v-else>
                <router-link to="/member/mypage"
                  ><b-icon
                    icon="person-fill"
                    class="icon icon_profile"
                    title="마이 페이지"
                  ></b-icon
                ></router-link>
                <b-icon
                  icon="box-arrow-right"
                  class="icon icon_logout"
                  @click="logout()"
                  title="로그아웃"
                ></b-icon>
              </div>
            </div>
          </div>
        </div>
        <div id="mobile-menu-wrap"></div>
      </div>
    </header>
    <div class="container_container">
      <router-view />
    </div>
    <footer class="footer">
      <!-- <div class="page-up">
        <a href="#" id="scrollToTopButton"
          ><span class="arrow_carrot-up"></span
        ></a>
      </div> -->
      <div class="container">
        <div class="row">
          <div class="col-lg-2">
            <div class="footer__logo">
              <router-link to="/"
                ><img class="footer_img" src="./assets/logo_green1.png" alt=""
              /></router-link>
            </div>
          </div>
          <div class="col-lg-7">
            <div class="footer__nav">
              <div class="col-lg-20">
                <a
                  href="https://www.applyhome.co.kr/ar/ara/selectSubscrptIntroHouseView.do"
                  target="blank"
                  ><img
                    class="b_img"
                    width="130px"
                    height="40px"
                    src="https://static.applyhome.co.kr/images/kabhom/sub/mobilebanner4-2.jpg"
                    alt="" /></a
                ><a href="https://www.lh.or.kr/index.do" target="blank"
                  ><img
                    class="b_img"
                    width="130px"
                    height="40px"
                    src="http://photo.sentv.co.kr/photo/2022/01/10/20220110101558.jpg"
                    alt="" /></a
                ><a href="https://www.i-sh.co.kr/main/index.do" target="blank"
                  ><img
                    class="b_img"
                    width="130px"
                    height="40px"
                    src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMK58CS-myyYG9ZK2ueVwOUMO65uj8pC0HuW31SfPVRHslfTbiGUz1BEHR2AAP8hVUOQ&usqp=CAU"
                    alt="" /></a
                ><a href="http://www.molit.go.kr/portal.do" target="blank"
                  ><img
                    class="b_img"
                    width="130px"
                    height="40px"
                    src="https://blog.kakaocdn.net/dn/baNlNY/btqxoQswEjV/apNmgzvRKiNbsh1WoqYwxK/img.jpg"
                    alt=""
                /></a>
                <a href="https://nhuf.molit.go.kr/" target="blank"
                  ><img
                    class="b_img"
                    width="130px"
                    height="40px"
                    src="https://www.seoulhousing.kr/assets/images/sub/jg_logo15.jpg"
                    alt=""
                /></a>
              </div>
              <ul>
                <li><router-link to="/house">지도로 매물 보기</router-link></li>
                <li><router-link to="/region">동네 정보 보기</router-link></li>
                <li><router-link to="/qna">동네 생활 Q&A</router-link></li>
                <li><router-link to="/news">부동산 소식</router-link></li>
                <li><router-link to="/notice">공지사항</router-link></li>
              </ul>
              <p>&copy; Copyright 2022 HappyHouse All rights reserved</p>
            </div>
          </div>
        </div>
      </div>
    </footer>
  </div>
</template>

<script>
export default {
  data: function () {
    return {
      accessToken: "",
    };
  },
  created() {
    //sessionStorage.deleteItem("accessToken");
    let accessToken = sessionStorage.getItem("accessToken");
    this.accessToken = accessToken;
    console.log(this.accessToken);
  },
  methods: {
    logout() {
      sessionStorage.removeItem("accessToken");
      alert("로그아웃 하셨습니다.");
      this.$router.push({ path: "/" });
      this.$router.go();
    },
  },
};
</script>

<style>
.b_img {
  margin-right: 10px;
  margin-bottom: 20px;
}

.fcenter {
  vertical-align: center;
}

#app {
  margin: 0px !important;
  max-height: 100vh;
}

.container {
  margin: 0px auto !important;
  height: 100% !important;
  width: 100% !important;
}

.container_container {
  min-height: 100vh;
  margin: 0px auto !important;
  /* height: 100% !important; */
  width: 100% !important;
}

* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
  text-decoration: none;
  list-style: none;
}

html,
body {
  height: 100%;
  font-family: "Mulish", sans-serif;
  -webkit-font-smoothing: antialiased;
  background: #ffffff;
}

h1,
h2,
h3,
h4,
h5,
h6 {
  margin: 0;
  color: #111111;
  font-weight: 400;
  font-family: "Mulish", sans-serif;
}

h1 {
  font-size: 70px;
}

h2 {
  font-size: 36px;
}

h3 {
  font-size: 30px;
}

h4 {
  font-size: 24px;
}

h5 {
  font-size: 18px;
}

h6 {
  font-size: 16px;
}

p {
  font-size: 15px;
  font-family: "Mulish", sans-serif;
  color: #3d3d3d;
  font-weight: 400;
  line-height: 25px;
  margin: 0 0 15px 0;
}

img {
  max-width: 100%;
}

input:focus,
select:focus,
button:focus,
textarea:focus {
  outline: none;
}

a:hover,
a:focus {
  text-decoration: none;
  outline: none;
  /* color: #ffffff !important ; */
}

ul,
ol {
  padding: auto;
  margin: auto 0;
  width: fit-content;
}

/*---------------------
  Helper CSS
-----------------------*/

div.container {
  width: 100%;
  margin: auto !important;
  padding: 0px !important;
}

.section-title {
  margin-bottom: 30px;
}

.section-title h4,
.section-title h5 {
  color: #ffffff;
  font-weight: 600;
  line-height: 21px;
  text-transform: uppercase;
  padding-left: 20px;
  position: relative;
  font-family: "Oswald", sans-serif;
}

.set-bg {
  background-repeat: no-repeat;
  background-size: cover;
  background-position: top center;
}

.spad {
  padding-top: 100px;
  padding-bottom: 100px;
}

.text-white h1,
.text-white h2,
.text-white h3,
.text-white h4,
.text-white h5,
.text-white h6,
.text-white p,
.text-white span,
.text-white li,
.text-white a {
  color: #fff;
}

/* buttons */

.primary-btn {
  display: inline-block;
  font-size: 13px;
  font-weight: 700;
  text-transform: uppercase;
  text-decoration: none;
  outline: none;
  color: #ffffff;
  letter-spacing: 2px;
}

.primary-btn span {
  font-size: 18px;
  margin-left: 5px;
  position: relative;
  top: 3px;
}

.site-btn {
  font-size: 13px;
  color: #ffffff;
  background: #545454;
  font-weight: 700;
  border: none;
  border-radius: 2px;
  letter-spacing: 2px;
  text-transform: uppercase;
  display: inline-block;
  padding: 12px 30px;
}
/*---------------------
  Header
-----------------------*/

.header {
  background: #ffffff;
  height: 70px;
  margin: auto;
  justify-content: center;
  align-items: center;
}

.header .row {
  display: flex;
  justify-content: center;
  align-items: center;
  margin: auto;
}

.header .row .col-lg-9 .header__nav ul {
  display: flex;
  justify-content: center;
  align-items: center;
  margin: auto;
}

.header .row .col-lg-2 {
  display: flex;
  justify-content: center;
  align-items: center;
}

.col-lg-2 .icon {
  color: #000;
  display: flex;
  justify-content: center;
  align-items: center;
}

.header__logo {
  display: inline;
}

.header_logo {
  width: 170px;
  height: 35px;
}

.header__logo a {
  display: inline-block;
}

.header__menu {
  text-align: center;
  display: flex;
  justify-content: center;
  align-items: center;
}

.header__menu ul li {
  list-style: none;
  display: inline-block;
  position: relative;
  margin-right: 16px;
}

.header__menu ul li:active a {
  color: #ffffff;
}

.header__menu ul li a {
  /* color: #ffffff; */
  color: black;
}

.header__menu ul li a:hover {
  /* color: #ffffff; */
  color: lightgrey !important;
}

.header__menu ul li:hover .dropdown {
  top: 62px;
  opacity: 1;
  visibility: visible;
}

.header__menu ul li:hover .dropdown li a {
  background: transparent;
}

.header__menu ul li:last-child {
  margin-right: 0;
}

.header__menu ul li .dropdown {
  position: absolute;
  left: 0;
  top: 82px;
  width: 150px;
  background: #ffffff;
  text-align: left;
  padding: 5px 0;
  z-index: 9;
  opacity: 0;
  visibility: hidden;
  -webkit-transition: all, 0.3s;
  -o-transition: all, 0.3s;
  transition: all, 0.3s;
}

.header__menu ul li .dropdown li {
  display: block;
  margin-right: 0;
}

.header__menu ul li .dropdown li a {
  font-size: 14px;
  color: #111111;
  font-weight: 500;
  padding: 5px 20px;
}

.header__menu ul li a {
  text-decoration: none;
  font-size: 15px;
  color: #2e2e2e;
  display: block;
  font-weight: 700;
  -webkit-transition: all, 0.5s;
  -o-transition: all, 0.5s;
  transition: all, 0.5s;
  padding: 20px;
}

.header__menu ul li a span {
  position: relative;
  font-size: 17px;
  top: 2px;
}

.header__right {
  text-align: right;
  display: flex;
  padding: 20px 0 15px;
}

.header__right a {
  display: inline-block;
  font-size: 18px;
  color: #ffffff;
  margin-right: 30px;
}

.header__right a:last-child {
  margin-right: 0;
}

.slicknav_menu {
  display: none;
}

.header_icon {
  display: flex;
  justify-content: center !important;
  align-content: center !important;
}

.icon_bell {
  width: 20px;
  height: 20px;
}

.icon_profile {
  width: 23px;
  height: 23px;
}

.icon_logout {
  width: 23px;
  height: 23px;
}

/*---------------------
  Footer
-----------------------*/

.footer {
  background: #eeeded;
  padding-top: 60px;
  padding-bottom: 40px;
  position: flex;
  bottom: 0;
  width: 100%;
  color: #fff;
}

.page-up {
  position: absolute;
  left: 50%;
  top: -25px;
  margin-left: -25px;
}

.page-up a {
  display: inline-block;
  font-size: 36px;
  color: #ffffff;
  height: 50px;
  width: 50px;
  background: #a68963;
  line-height: 50px;
  text-align: center;
  border-radius: 50%;
}

.page-up a span {
  position: relative;
  top: 2px;
  left: -1px;
}

.footer__nav {
  text-align: center;
}

.footer__nav ul li {
  list-style: none;
  display: inline-block;
  position: relative;
  margin-right: 40px;
}

.footer__nav ul li:last-child {
  margin-right: 0;
}

.footer__nav ul li a {
  font-size: 15px;
  color: #2e2e2e;
  display: block;
  font-weight: 700;
}

.footer__copyright__text {
  color: #b7b7b7;
  margin-bottom: 0;
  text-align: right;
}

.footer__copyright__text a {
  color: #e53637;
}
</style>
