<template>
  <div class="main_banner">
    <img src="../assets/main1.jpg" alt="" style="display: none" />
    <img src="../assets/main2.jpg" alt="" style="display: none" />
    <img src="../assets/main3.jpg" alt="" style="display: none" />
    <b-carousel
      id="carousel-fade"
      style="text-shadow: 0px 0px 2px #000"
      no-animation
      interval="3000"
      indicators
    >
      <b-carousel-slide
        class="slide_img"
        img-src="http://localhost:8080/img/main1.d5332b8d.jpg"
      ></b-carousel-slide>
      <b-carousel-slide
        class="slide_img"
        img-src="http://localhost:8080/img/main2.e8d3336b.jpg"
      ></b-carousel-slide>
      <b-carousel-slide
        class="slide_img"
        img-src="http://localhost:8080/img/main3.8cdf1aae.jpg"
      ></b-carousel-slide>
    </b-carousel>
    <br />
    <section class="section-container">
      <h1>어떤 집을 찾고 있나요?</h1>
      <div
        id="section-input-container"
        action="<%=request.getContextPath()%>/house/search"
        method="post"
      >
        <b-input
          type="text"
          class="form-control"
          id="searchvalue"
          name="keyword"
          v-model="keyword"
          @keyup.enter="requestHouseList(keyword)"
          placeholder="동 이름, 아파트 이름으로 검색"
        />
        <button @click="requestHouseList" id="filter-btn">
          <b-icon class="detail_press_like" icon="search"></b-icon>
        </button>
      </div>
      <div style="height: 80px"></div>
    </section>

    <br /><br />
    <h3 class="h3">| 최근 등록 된 매물</h3>
    <vue-glide
      @click="movePage"
      v-model="active"
      perView="4"
      autoplay="true"
      animationDuration="3000"
      bound="true"
    >
      <vue-glide-slide v-for="(c, index) in current" :key="index">
        <!-- <div class="col-xs-12 col-md-4"> -->
        <b-card
          class="card_current"
          img-src="http://localhost:8080/img/home.34efcd26.jpg"
          img-alt="Image"
          img-top
          @click="gotoDetail(c.no)"
        >
          <div class="title_card">{{ c.aptName }}</div>
          <b-card-text>
            <div>{{ c.dongName }}</div>
            <div>{{ c.dealAmount }}</div>
          </b-card-text>
          <template #footer>
            <small class="text-muted"
              >{{ c.dealYear }}. {{ c.dealMonth }}. {{ c.dealDay }}</small
            >
          </template>
        </b-card>
      </vue-glide-slide>
      <template slot="control"><br /> </template>
    </vue-glide>
    <br /><br /><br />
    <h3 class="h3">| 많이 찜한 매물</h3>
    <vue-glide
      @click="movePage"
      v-model="active"
      perView="4"
      autoplay="true"
      animationDuration="3000"
      bound="true"
    >
      <vue-glide-slide v-for="(l, index) in like" :key="index">
        <!-- <div class="col-xs-12 col-md-4"> -->
        <b-card
          class="card_current"
          img-src="http://localhost:8080/img/home.34efcd26.jpg"
          img-alt="Image"
          img-top
          @click="gotoDetail(l.no)"
          ><div class="title_card">{{ l.aptName }}</div>
          <b-card-text class="card_text">
            <div class="card_content">
              <div>{{ l.dongName }}</div>
              <div>{{ l.dealAmount }}</div>
            </div>
            <div class="card_content">
              <div class="card_rank">{{ index + 1 }}</div>
            </div>
          </b-card-text>
          <template #footer>
            <small class="text-muted">하트 {{ l.likeCount }}개</small>
          </template>
        </b-card>
      </vue-glide-slide>
      <template slot="control"><br /> </template>
    </vue-glide>
    <br /><br /><br />
    <h3 class="h3">| 차트</h3>
    <br />
    <div>
      <div id="chart">
        <router-link class="router" to="countAmount">월 매물 수</router-link>
        <span class="blank" /> | <span class="blank" />
        <router-link class="router" to="maxAmountByDong"
          >매물 많은 동네</router-link
        >
        <span class="blank" /> | <span class="blank" />
        <router-link class="router" to="maxAmountByGu"
          >매물 많은 구</router-link
        >
        <span class="blank" /> | <span class="blank" />
        <router-link class="router" to="byPrice">가격 비교</router-link>
        <br /><br />
      </div>
      <router-view></router-view>
    </div>
    <br /><br /><br /><br /><br />
  </div>
</template>

<script>
export default {
  data() {
    return {
      active: 0,
      keyword: "",
    };
  },
  created() {
    this.$store.dispatch("getCurrent");
    this.$store.dispatch("getHouseLike");
  },
  computed: {
    current() {
      return this.$store.state.current;
    },
    like() {
      return this.$store.state.like;
    },
  },
  methods: {
    gotoDetail(no) {
      let accessToken = sessionStorage.getItem("accessToken");
      if (accessToken) {
        this.$router.push({ path: "/house/detail/" + no });
      } else {
        alert("로그인 후에 이용해주세요.");
        this.$router.push({ path: "/member/login" });
      }
    },
    requestHouseList() {
      console.log(this.keyword);
      if (this.keyword != "") {
        this.$router.push({
          name: "HouseMapSearchView",
          params: { keyword: this.keyword },
        });
      }
    },
  },
};
</script>

<style>
.w-100 {
  width: 100% !important;
  height: 100% !important;
}

.main_banner {
  width: 60%;
  margin: auto;
}
.h3 {
  color: #274f35;
}
.slide_img {
  height: 450px !important;
  margin-top: 80px;
}

.card_current {
  width: 220px !important;
  box-shadow: rgb(0 0 0 / 10%) 0px 10px 20px;
  overflow: hidden;
}
.section-container {
  display: flex;
  justify-content: center;
  flex-direction: column;
  align-items: center;
}
.section-container h1 {
  position: absolute !important;
  z-index: 999;
  top: 45%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 30px;
  background-color: rgba(255, 255, 255, 0.5);
  padding: 1rem 2rem;
  border-radius: 50px;
  color: #274f35;
  font-weight: 700;
  /* color: #fff; */

  /* font-family: 'Black Han Sans', sans-serif; */
}
#main-btn-container {
  display: flex;
  width: 100%;
  justify-content: center;
  background-color: rgba(0, 0, 0, 0.65);
  margin-bottom: 26px;
  align-items: center;
}

.main-btn {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 48px;
  font-size: 17px;
  font-stretch: normal;
  font-style: normal;
  line-height: 1.35;
  letter-spacing: normal;
  color: rgb(255, 255, 255);
  width: 100%;
  padding: 0 8px;
  z-index: 10;
}

#searchvalue {
  width: 100% !important;
  height: 100% !important;
  border: 2px solid #85af4b !important;
  border-top-left-radius: 8px;
  border-bottom-left-radius: 8px;
  text-align: center;
}
#section-input-container {
  height: 60px;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

#section-input-container input:focus {
  outline: none;
}

#filter-btn {
  width: 66px;
  height: 100%;
  border: none;
  border-top-right-radius: 8px !important;
  border-bottom-right-radius: 8px !important;
  background-color: #85af4b;
  color: #fff;
  font-size: 1.2rem;
  font-weight: bold;
  text-align: center;
}

.router {
  text-decoration: none;
  color: lightslategrey;
  font-size: 20px;
  font-weight: 600;
}

.router:hover {
  text-decoration: none;
  color: lightgrey;
  font-weight: 700;
}

#chart {
  color: lightslategrey;
  font-size: 20px;
  font-weight: 300;
}

.card_text {
  flex: 1 1 auto;
  padding: 1rem 1rem;
  display: inline-block;
  width: 200px !important;
}

.title_card {
  font-weight: 600;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis !important;
  word-wrap: break-word !important;
}

.card_text {
  display: flex;
  justify-content: space-between;
}

.card_rank {
  font-size: 40px;
  font-weight: 800;
  margin-top: 25px;
  color: #fff;
  -webkit-text-stroke-width: 2px;
  -webkit-text-stroke-color: lightslategrey;
}
</style>
