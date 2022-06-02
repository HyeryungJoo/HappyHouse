<template>
  <div class="price_area">
    <b-btn @click="reload">서울특별시 </b-btn>
    <span class="blank"></span>
    <select name="gu" v-model="selectgu">
      <option value="0" selected>-- 구 선택 --</option>
      <option v-for="(g, index) in gu" :key="index" :value="g.gugunName">
        {{ g.gugunName }}
      </option></select
    ><span class="blank"></span>
    <select name="dong" v-model="selectdong">
      <option value="0" selected>-- 동 선택 --</option>
      <option v-for="(d, index) in dong" :key="index" :value="d.dongName">
        {{ d.dongName }}
      </option></select
    ><span class="blank"></span>
    <select name="areameter" v-model="selectarea">
      <option value="-1">-- 평 선택 --</option>
      <option value="0">-- 10평 이하 --</option>
      <option value="33">-- 10평 대 --</option>
      <option value="66">-- 20평 대 --</option>
      <option value="99">-- 30평 대 --</option>
      <option value="132">-- 40평 이상 --</option></select
    ><span class="blank"></span> <b-btn @click="found">확인하기</b-btn>
    <br /><br />

    <LineChartGenerator
      :chart-options="chartOptions"
      :chart-data="chartData"
      :chart-id="chartId"
      :dataset-id-key="datasetIdKey"
      :plugins="plugins"
      :css-classes="cssClasses"
      :styles="styles"
      :width="width"
      :height="height"
    />

    <!-- <div v-if="find"> -->
    <div class="price_content">
      서울 시, {{ selectgu }}, {{ selectdong }}의 {{ housearea }} 매물 정보는
      다음과 같습니다.
    </div>
    <br />
    <div class="table_three">
      <div class="table_si">
        <h3>서울시</h3>
        <br />
        <!-- <div v-if="resultSeoul.length"> -->
        <table class="table table-hover" id="bootstrap-table">
          <colgroup>
            <col style="width: 50%" />
            <col style="width: 50%" />
          </colgroup>
          <tr>
            <th>최고가</th>
            <td>{{ resultSeoul.max }}만원</td>
          </tr>
          <tr>
            <th>최저가</th>
            <td>{{ resultSeoul.min }}만원</td>
          </tr>
          <tr>
            <th>평균가</th>
            <td>{{ resultSeoul.avg }}만원</td>
          </tr>
          <tr>
            <th>평당 평균 매매가</th>
            <td>{{ per }}만원</td>
          </tr>
          <tr>
            <th>평균 평수</th>
            <td>약 {{ h_per }}평</td>
          </tr>
          <tr>
            <th>가장 오래된 매물 년도</th>
            <td>{{ resultSeoul.bmin }}년</td>
          </tr>
          <tr>
            <th>가장 최근 매물 년도</th>
            <td>{{ resultSeoul.bmax }}년</td>
          </tr>
          <tr>
            <th>총 매물 수</th>
            <td>{{ resultSeoul.count }}개</td>
          </tr>
        </table>
      </div>
      <br />
      <div class="table_gu">
        <h3>{{ selectgu }}</h3>
        <br />
        <!-- <div v-if="resultGu.length"> -->
        <table class="table table-hover" id="bootstrap-table">
          <colgroup>
            <col style="width: 50%" />
            <col style="width: 50%" />
          </colgroup>
          <tr>
            <th>최고가</th>
            <td>{{ resultGu.max }}만원</td>
          </tr>
          <tr>
            <th>최저가</th>
            <td>{{ resultGu.min }}만원</td>
          </tr>
          <tr>
            <th>평균가</th>
            <td>{{ resultGu.avg }}만원</td>
          </tr>
          <tr>
            <th>평당 평균 매매가</th>
            <td>{{ pergu }}만원</td>
          </tr>
          <tr>
            <th>평균 평수</th>
            <td>약 {{ h_pergu }}평</td>
          </tr>
          <tr>
            <th>가장 오래된 매물 년도</th>
            <td>{{ resultGu.bmin }}년</td>
          </tr>
          <tr>
            <th>가장 최근 매물 년도</th>
            <td>{{ resultGu.bmax }}년</td>
          </tr>
          <tr>
            <th>총 매물 수</th>
            <td>{{ resultGu.count }}개</td>
          </tr>
        </table>
      </div>

      <br />
      <div class="table_dong">
        <h3>{{ selectdong }}</h3>
        <br />
        <!-- <div v-if="resultDong.length"> -->
        <table class="table table-hover" id="bootstrap-table">
          <colgroup>
            <col style="width: 50%" />
            <col style="width: 50%" />
          </colgroup>
          <tr>
            <th>최고가</th>
            <td>{{ resultDong.max }}만원</td>
          </tr>
          <tr>
            <th>최저가</th>
            <td>{{ resultDong.min }}만원</td>
          </tr>
          <tr>
            <th>평균가</th>
            <td>{{ resultDong.avg }}만원</td>
          </tr>
          <tr>
            <th>평당 평균 매매가</th>
            <td>{{ perdong }}만원</td>
          </tr>
          <tr>
            <th>평균 평수</th>
            <td>약 {{ h_perdong }}평</td>
          </tr>
          <tr>
            <th>가장 오래된 매물 년도</th>
            <td>{{ resultDong.bmin }}년</td>
          </tr>
          <tr>
            <th>가장 최근 매물 년도</th>
            <td>{{ resultDong.bmax }}년</td>
          </tr>
          <tr>
            <th>총 매물 수</th>
            <td>{{ resultDong.count }}개</td>
          </tr>
        </table>
      </div>
    </div>
    <br /><br />
    <!-- </div> -->
  </div>
</template>

<script>
import { Line as LineChartGenerator } from "vue-chartjs/legacy";
import axios from "axios";
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  LineElement,
  LinearScale,
  CategoryScale,
  PointElement,
} from "chart.js";

ChartJS.register(
  Title,
  Tooltip,
  Legend,
  LineElement,
  LinearScale,
  CategoryScale,
  PointElement
);

export default {
  name: "LineChart",
  components: {
    LineChartGenerator,
  },
  props: {
    chartId: {
      type: String,
      default: "line-chart",
    },
    datasetIdKey: {
      type: String,
      default: "label",
    },
    width: {
      type: Number,
      default: 400,
    },
    height: {
      type: Number,
      default: 400,
    },
    cssClasses: {
      default: "",
      type: String,
    },
    styles: {
      type: Object,
      default: () => {},
    },
    plugins: {
      type: Array,
      default: () => [],
    },
  },
  data() {
    return {
      find: false,
      gu: [],
      dong: [],
      selectgu: 0,
      selectdong: 0,
      selectarea: -1,
      per: "",
      pergu: "",
      perdong: "",
      h_per: "",
      h_pergu: "",
      h_perdong: "",
      resultSeoul: [],
      resultGu: [],
      resultDong: [],
      housearea: "",
      chartData: {
        labels: ["서울시", "선택 구", "선택 동"],
        datasets: [
          {
            label: "최고가",
            backgroundColor: "#274f35aa",
            data: [],
          },
          {
            label: "최저가",
            backgroundColor: "#828289bc",
            data: [],
          },
          {
            label: "평균가",
            backgroundColor: "#778899bc",
            data: [],
          },
          {
            label: "평 당 평균매매가",
            backgroundColor: "#85af4bab",
            data: [],
          },
        ],
      },
      chartOptions: {
        responsive: true,
        maintainAspectRatio: false,
      },
    };
  },
  created() {
    axios.get("http://localhost:7779/gudong").then((resp) => {
      this.gu = resp.data;
    });
  },
  watch: {
    //구 선택 시
    selectgu: function (selectgu) {
      console.log(selectgu);
      //제대로 선택 시
      if (this.selectgu != 0) {
        axios
          .get("http://localhost:7779/gudong/" + this.selectgu)
          .then((resp) => {
            this.dong = resp.data;
          });
      }
      this.selectdong = "0";
    },
  },
  methods: {
    house(h_area) {
      if (h_area == 0) {
        this.housearea = "10평 이하";
      } else if (h_area == 33) {
        this.housearea = "10평 대";
      } else if (h_area == 66) {
        this.housearea = "20평 대";
      } else if (h_area == 99) {
        this.housearea = "30평 대";
      } else if (h_area == 132) {
        this.housearea = "40평 이상";
      }
    },
    found() {
      this.chartData.labels = [];
      // this.chartData.labels = ["서울시", this.selectgu, this.selectdong];
      this.chartData.datasets[0].data = [];

      console.log(
        this.selectgu + " " + this.selectdong + " " + this.selectarea
      );
      var n_selectarea = Number(this.selectarea);
      if (this.selectgu == 0 || this.selectdong == 0 || this.selectarea == -1) {
        alert("구, 동, 평형 수를 선택해 주세요");
      }
      //서울 전체
      if (n_selectarea < 132) {
        this.house(n_selectarea);
        this.chartData.labels.push("서울시");

        axios
          .get("http://localhost:7779/house/seoul", {
            params: { start: n_selectarea, end: n_selectarea + 33 },
          })
          .then((resp) => {
            var per = Math.round(
              resp.data[0].avg / (resp.data[0].avgarea / 3.3)
            );
            this.resultSeoul = resp.data[0];
            console.log(resp.data[0]);
            console.log(this.resultSeoul);
            this.per = per;
            this.h_per = Math.round(resp.data[0].avgarea / 3.3);
            this.chartData.datasets[0].data.push(resp.data[0].max);
            this.chartData.datasets[1].data.push(resp.data[0].min);
            this.chartData.datasets[2].data.push(resp.data[0].avg);
            this.chartData.datasets[3].data.push(per);
          });
      } else {
        this.house(n_selectarea);
        this.chartData.labels.push("서울시");

        axios
          .get("http://localhost:7779/house/seoul40", {
            params: { start: n_selectarea },
          })
          .then((resp) => {
            var per = Math.round(
              resp.data[0].avg / (resp.data[0].avgarea / 3.3)
            );
            this.resultSeoul = resp.data[0];
            this.per = per;
            this.h_per = Math.round(resp.data[0].avgarea / 3.3);
            this.chartData.datasets[0].data.push(resp.data[0].max);
            this.chartData.datasets[1].data.push(resp.data[0].min);
            this.chartData.datasets[2].data.push(resp.data[0].avg);
            this.chartData.datasets[3].data.push(per);
          });
      }

      //구 별로
      if (n_selectarea < 132) {
        this.chartData.labels.push(this.selectgu);

        axios
          .get("http://localhost:7779/house/guper", {
            params: {
              gugunName: this.selectgu,
              start: n_selectarea,
              end: n_selectarea + 33,
            },
          })
          .then((resp) => {
            var per = Math.round(
              resp.data[0].avg / (resp.data[0].avgarea / 3.3)
            );
            this.resultGu = resp.data[0];
            this.pergu = per;
            this.h_pergu = Math.round(resp.data[0].avgarea / 3.3);
            this.chartData.datasets[0].data.push(resp.data[0].max);
            this.chartData.datasets[1].data.push(resp.data[0].min);
            this.chartData.datasets[2].data.push(resp.data[0].avg);
            this.chartData.datasets[3].data.push(per);
          });
      } else {
        this.chartData.labels.push(this.selectgu);

        axios
          .get("http://localhost:7779/house/guper40", {
            params: {
              gugunName: this.selectgu,
              start: n_selectarea,
            },
          })
          .then((resp) => {
            var per = Math.round(
              resp.data[0].avg / (resp.data[0].avgarea / 3.3)
            );
            this.resultGu = resp.data[0];
            this.pergu = per;
            this.h_pergu = Math.round(resp.data[0].avgarea / 3.3);
            this.chartData.datasets[0].data.push(resp.data[0].max);
            this.chartData.datasets[1].data.push(resp.data[0].min);
            this.chartData.datasets[2].data.push(resp.data[0].avg);
            this.chartData.datasets[3].data.push(per);
          });
      }

      //동 별로
      if (n_selectarea < 132) {
        this.chartData.labels.push(this.selectdong);

        axios
          .get("http://localhost:7779/house/dongper", {
            params: {
              dongName: this.selectdong,
              start: n_selectarea,
              end: n_selectarea + 33,
            },
          })
          .then((resp) => {
            var per = Math.round(
              resp.data[0].avg / (resp.data[0].avgarea / 3.3)
            );
            this.resultDong = resp.data[0];
            this.perdong = per;
            this.h_perdong = Math.round(resp.data[0].avgarea / 3.3);
            this.chartData.datasets[0].data.push(resp.data[0].max);
            this.chartData.datasets[1].data.push(resp.data[0].min);
            this.chartData.datasets[2].data.push(resp.data[0].avg);
            this.chartData.datasets[3].data.push(per);
          });
      } else {
        this.chartData.labels.push(this.selectdong);

        axios
          .get("http://localhost:7779/house/dongper40", {
            params: {
              dongName: this.selectdong,
              start: n_selectarea,
            },
          })
          .then((resp) => {
            var per = Math.round(
              resp.data[0].avg / (resp.data[0].avgarea / 3.3)
            );
            this.resultDong = resp.data[0];
            this.perdong = per;
            this.h_perdong = Math.round(resp.data[0].avgarea / 3.3);
            this.chartData.datasets[0].data.push(resp.data[0].max);
            this.chartData.datasets[1].data.push(resp.data[0].min);
            this.chartData.datasets[2].data.push(resp.data[0].avg);
            this.chartData.datasets[3].data.push(per);
          });
      }

      this.find = true;
      // console.log(this.chartData.datasets[0].data);
      console.log("seoul");
      console.log(this.resultSeoul);
      console.log("gu");
      console.log(this.resultGu);
      console.log("dong");
      console.log(this.resultDong);
    },
  },
};
</script>

<style>
.table_three {
  display: flex;
}

.table_si,
.table_gu,
.table_dong {
  width: 30%;
  margin: auto;
}

.price_content {
  width: fit-content;
  margin: 80px auto;
  color: #274f35;
  font-size: 20px;
  font-weight: 700;
  padding: 5px;
  background: #eeeded;
  box-shadow: rgb(0 0 0 / 10%) 0px 10px 20px;
  border: 1px solid #274f35;
  border-radius: 10px;
}
</style>
