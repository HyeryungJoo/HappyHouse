<template>
  <div class="area">
    <div><h3>서울시 월 별 매물 수</h3></div>
    <div class="chart_section">
      <div class="chart">
        <Bar
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
      </div>
      <div class="table_countAmount">
        <table class="table table-hover" id="bootstrap-table">
          <colgroup>
            <col style="width: 50%" />
            <col style="width: 50%" />
          </colgroup>
          <thead>
            <tr>
              <th>월</th>
              <th>매물 수</th>
            </tr>
          </thead>
          <tr v-for="(item, index) in chartData.datasets[0].data" :key="index">
            <td>{{ index + 1 }}월</td>
            <td>{{ item }}건</td>
          </tr>
        </table>
      </div>
    </div>
    <br /><br /><br /><br />
    <div class="content_countamount">
      가장 매물이 많은 달은 {{ max_month }}월로 {{ max }}건이고 가장 매물이 적은
      달은 {{ min_month }}월로 {{ min }}건 입니다.
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { Bar } from "vue-chartjs/legacy";
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale,
} from "chart.js";

ChartJS.register(
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale
);
export default {
  name: "BarChart",
  components: { Bar },
  props: {
    chartId: {
      type: String,
      default: "bar-chart",
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
      min: "",
      max: "",
      max_month: "",
      min_month: "",
      chartData: {
        labels: [
          "January",
          "February",
          "March",
          "April",
          "May",
          "June",
          "July",
          "August",
          "September",
          "October",
          "November",
          "December",
        ],
        datasets: [
          {
            backgroundColor: [
              "#274f35aa",
              "#c2bdbdb9",
              "#778899bc",
              "#85af4bab",
            ],
            data: [],
          },
        ],
      },
      chartOptions: {
        responsive: true,
      },
    };
  },
  created() {
    axios.get("http://localhost:7779/house/countByMonth").then((resp) => {
      this.chartData.datasets[0].data = resp.data;

      var max = this.chartData.datasets[0].data[0];
      var min = this.chartData.datasets[0].data[0];
      var max_month = 0;
      var min_month = 0;
      var i = 0;

      this.chartData.datasets[0].data.forEach((element) => {
        i++;
        if (element > max) {
          max = element;
          max_month = i;
        } else if (element < min) {
          min = element;
          min_month = i;
        }
      });
      this.min = min;
      this.max = max;
      this.max_month = max_month;
      this.min_month = min_month;
    });
  },
};
</script>

<style>
.chart_section {
  width: 100%;
  height: 400px;
  float: left;
  margin: 0;
  display: flex;
}

.area {
  width: 100%;
  height: 800px;
  margin: auto;
}

.content_countamount {
  margin-top: 350px;
  color: #274f35;
  font-size: 20px;
  font-weight: 700;
  padding: 5px;
  background: #eeeded;
  box-shadow: rgb(0 0 0 / 10%) 0px 10px 20px;
  border: 1px solid #274f35;
  border-radius: 10px;
}

.table_countAmount {
  width: 25%;
  margin-left: 50px;
}

.table {
  width: 400px;
}
</style>
