<template>
  <div class="max_amount_gu">
    <div class="max_amount_gu_area">
      <div class="chart">
        <Pie
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
      <div class="max_amount_gu_table">
        <br />
        <h3>매물 수가 많은 구 TOP5</h3>
        <table class="table table-hover" id="bootstrap-table">
          <thead>
            <tr>
              <th>순서</th>
              <th>구</th>
              <th>매물 수</th>
            </tr>
          </thead>
          <tr v-for="(item, index) in chartData.datasets[0].data" :key="index">
            <td>{{ index + 1 }}</td>
            <td>{{ chartData.labels[index] }}</td>
            <td>{{ item }}건</td>
          </tr>
        </table>
        <div class="max_amount_gu_content">
          가장 매물이 많은 구는 {{ max_gu }}로 {{ max }}건입니다.
        </div>
      </div>
      <br /><br /><br /><br /><br /><br /><br /><br /><br /><br />
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { Pie } from "vue-chartjs/legacy";

import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  ArcElement,
  CategoryScale,
} from "chart.js";

ChartJS.register(Title, Tooltip, Legend, ArcElement, CategoryScale);

export default {
  name: "PieChart",
  components: {
    Pie,
  },
  props: {
    chartId: {
      type: String,
      default: "pie-chart",
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
      max_gu: "",
      max: 0,
      chartData: {
        labels: [],
        datasets: [
          {
            backgroundColor: [
              "#3c54aeaa",
              "#ea4023b2",
              "#9853e1bc",
              "#85af4bab",
              "#dfba33b9",
            ],
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
    var max = 0;
    var max_gu = "";

    axios.get("http://localhost:7779/house/maxAmountByGu").then((resp) => {
      resp.data.forEach((element) => {
        if (element.count > max) {
          max = element.count;
          max_gu = element.gugunName;
        }

        this.chartData.labels.push(element.gugunName);
        this.chartData.datasets[0].data.push(element.count);
      });

      this.max = max;
      this.max_gu = max_gu;
    });
  },
};
</script>

<style>
.max_amount_gu_area {
  display: flex;
  height: 500px;
}

.max_amount_gu_table {
  margin-left: 50px;
}

.max_amount_gu_content {
  color: #274f35;
  font-size: 20px;
  font-weight: 700;
  margin-top: 30px;
  padding: 5px;
  background: #eeeded;
  box-shadow: rgb(0 0 0 / 10%) 0px 10px 20px;
  border: 1px solid #274f35;
  border-radius: 10px;
}
</style>
