<template>
  <div class="news_content">
    <br /><br /><br /><br />
    <div class="news_title">
      <h1><b>실시간 부동산 뉴스</b></h1>
    </div>
    <br /><br /><img src="../assets/home.jpg" alt="" style="display: none" />
    <vue-glide v-model="active">
      <vue-glide-slide v-for="(n, index) in news" :key="index">
        <!-- <div class="col-xs-12 col-md-4"> -->
        <b-card
          class="news_card"
          img-src="http://localhost:8080/img/home.34efcd26.jpg"
          img-alt="Image"
          img-top
          ><div class="font_b" v-html="n.title" @click="moveNews(n.link)"></div>
          <br /><br />
          <b-card-text>
            <div
              class="card_news font_n"
              v-html="n.description"
              @click="moveNews(n.link)"
            ></div>
          </b-card-text>
          <template #footer>
            <small class="text-muted">{{ n.pubDate | yyyyMMdd }}</small>
          </template>
        </b-card>
      </vue-glide-slide>
      <template slot="control"
        ><br /><span class="blanks"></span>
        <b-button class="p_btn" data-glide-dir="<<">start</b-button>
        <b-button class="p_btn" data-glide-dir="=0">1</b-button>
        <b-button class="p_btn" data-glide-dir="=3">2</b-button>
        <b-button class="p_btn" data-glide-dir="=6">3</b-button>
        <b-button class="p_btn" data-glide-dir="=9">4</b-button>
        <b-button class="p_btn" data-glide-dir="=12">5</b-button>
        <b-button class="p_btn" data-glide-dir="=12">end</b-button>
      </template>
    </vue-glide>
    <br /><br /><br /><br /><br />
    <div class="news_title">
      <h1><b>서울 아파트 청약 정보</b></h1>
    </div>
    <br /><br />
    <b-calendar
      block
      v-model="value"
      @context="onContext"
      locale="ko-kr"
    ></b-calendar
    ><br /><br /><br /><b-button class="btn_go" @click="go"
      >청약홈 바로가기</b-button
    ><b-button class="btn_go" @click="reload">전체 청약 정보 보기</b-button
    ><br /><br />
    <div v-if="select.length">
      <table class="table table-hover" id="bootstrap-table">
        <thead>
          <tr>
            <th>번호</th>
            <th>이름</th>
            <th>주소</th>
            <th>종류</th>
            <th>청약일</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(r, index) in select" :key="index">
            <td>{{ index + 1 }}</td>
            <td>
              <a :href="r.HMPG_ADRES">{{ r.HOUSE_NM }}</a>
            </td>
            <td>{{ r.HSSPLY_ADRES }}</td>
            <td>
              {{ r.RENT_SECD_NM }}<br />
              ({{ r.HOUSE_DTL_SECD_NM }})
            </td>
            <td>{{ r.SPSPLY_RCEPT_BGNDE }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <div v-else class="text-center">청약 정보가 없습니다.</div>
    <br /><br /><br /><br /><br />
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      date: "",
      realestate: [],
      select: [],
      value: "",
      context: null,
      active: 0,
    };
  },
  methods: {
    moveNews(link) {
      window.open(link);
    },
    go() {
      window.open("https://www.applyhome.co.kr/co/coa/selectMainView.do");
    },
    onContext(ctx) {
      this.context = ctx;
    },
    reload() {
      location.reload();
    },
  },
  watch: {
    value: function (value) {
      this.select = [];
      this.realestate.forEach((element) => {
        if (element.SPSPLY_RCEPT_BGNDE == value) {
          this.select.push(element);
        }
      });
    },
  },
  computed: {
    news() {
      return this.$store.state.news;
    },
  },
  created() {
    this.$store.dispatch("getNews");
    axios
      .get(
        "https://api.odcloud.kr/api/ApplyhomeInfoDetailSvc/v1/getAPTLttotPblancDetail?page=1&perPage=1000&cond%5BSUBSCRPT_AREA_CODE_NM%3A%3AEQ%5D=%EC%84%9C%EC%9A%B8&cond%5BRCRIT_PBLANC_DE%3A%3ALTE%5D=2022-12-31&cond%5BRCRIT_PBLANC_DE%3A%3AGTE%5D=2022-01-01&serviceKey=serviceKey%2BNs7CFUb%2Bxg%3D%3D"
      )
      .then((resp) => {
        this.realestate = resp.data.data;
        this.select = resp.data.data;
        console.log(resp.data.data);
      });
  },
  filters: {
    // filter로 쓸 filter ID 지정
    yyyyMMdd: function (value) {
      // 들어오는 value 값이 공백이면 그냥 공백으로 돌려줌
      if (value == "") return "";

      // 현재 Date 혹은 DateTime 데이터를 javaScript date 타입화
      var js_date = new Date(value);

      // 연도, 월, 일 추출
      var year = js_date.getFullYear();
      var month = js_date.getMonth() + 1;
      var day = js_date.getDate();
      var hour = js_date.getHours();
      var min = js_date.getMinutes();
      var half = "";

      // 월, 일의 경우 한자리 수 값이 있기 때문에 공백에 0 처리
      if (month < 10) {
        month = "0" + month;
      }

      if (day < 10) {
        day = "0" + day;
      }

      if (hour > 11) {
        half = "오후";
        hour -= 12;
      } else if (hour < 12) {
        half = "오전";
      }

      // 최종 포맷 (ex - '2021-10-08')
      return (
        year +
        "년 " +
        month +
        "월 " +
        day +
        "일   " +
        half +
        hour +
        "시 " +
        min +
        "분"
      );
    },
  },
};
</script>

<style>
.blanks {
  text-align: center;
  align-items: center;
  justify-content: center;
  margin-left: 33%;
}
.news_title {
  text-align: center;
}

.p_btn {
  margin-right: 10px;
}
.news_content {
  width: 60%;
  margin: auto;
}

.btn_go {
  float: right;
  margin-left: 10px;
}

td a {
  text-decoration: none;
  color: black;
}

.font_b {
  font-weight: 1000;
  word-break: keep-all;
  display: inline-block;
  white-space: nowrap;
  overflow: hidden;
  width: 95% !important;
  text-overflow: ellipsis;
  font-size: 1.2em;
  text-align: center;
}

.font_n {
  font-weight: 600;
  text-align: center;
}
.news_card {
  width: 350px !important;
  height: 520px !important;
}
.b-calendar .b-calendar-grid {
  padding: auto !important;
  margin: 0px !important;
  width: 100% !important;
  margin: auto !important;
}

.b-calendar output {
  padding: 0rem !important;
  width: 100% !important;
  font-size: 80%;
}

.card_news {
  width: 100% !important;
  flex: 1 1 auto;
  padding: 1rem 0rem;
}

.page_btn {
  width: fit-content !important;
  margin: auto;
}
</style>
