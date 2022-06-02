<template>
  <div>
    <section id="main-section">
      <div id="main-map"></div>
      <article id="main-list">
        <div id="filter-container">
          <b-input
            type="text"
            class="form_control"
            name="keyword"
            v-model="keyword"
            @keyup.enter="requestHouseList"
            placeholder="동 이름, 아파트 이름으로 검색"
          />
          <button type="submit" id="filter-btn">
            <b-icon
              class="detail_press_like"
              @click="requestHouseList()"
              icon="search"
            ></b-icon>
          </button>
        </div>
        <div id="list-item-container">
          <div v-if="houselist.length" id="my-table">
            <div
              class="item_house"
              v-for="(i, index) in houselist"
              :key="index"
            >
              <div class="item_box" @click="moveDetail(i.no)">
                <img class="test" src="../../assets/bedroom.jpg" />
                <div class="list-item-detail">
                  <h5>{{ i.dealAmount }}</h5>
                  <h3>{{ i.aptName }}</h3>
                  <div class="list-item-detail-info">
                    <label class="label"
                      >{{ i.area }}평 - {{ i.floor }}층
                    </label>
                    <label class="label">{{ i.dongName }} 아파트</label>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div v-else class="no_item_textcenter">
            해당 지역에 등록된 매물이 없습니다.
          </div>

          <b-pagination
            class="pagination"
            v-model="currentPage"
            align="center"
            :per-page="1"
            :total-rows="totalPage"
            :last-number="true"
            @page-click.prevent="pageClick"
            aria-controls="my-table"
          ></b-pagination>
        </div>
      </article>
    </section>
    <br /><br /><br /><br /><br />
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      map: null,
      keyword: "",
      size: 30,
      currentPage: 1,
      totalPage: "",
      totalCount: "",
      houselist: [],

      markers: [],
    };
  },
  computed: {
    rows() {
      return this.houselist.length;
    },
  },
  created() {
    console.log(this.$route.params.keyword);
    if (this.$route.params.keyword) {
      this.keyword = this.$route.params.keyword;
    }
    this.requestHouseList();
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        "http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=appkey&libraries=services,clusterer,drawing";
      document.head.appendChild(script);
    }
  },
  methods: {
    pageClick: function (button, page) {
      this.currentPage = page;
      this.requestPagenation(page);
    },
    requestPagenation: function (page) {
      axios
        .get("http://localhost:7779/house/list", {
          params: {
            keyword: this.keyword,
            size: this.size,
            page: page,
          },
        })
        .then((resp) => {
          console.log(resp);
          this.houselist = resp.data.content;
          this.makeMarker();
        });
    },
    initMap() {
      var mapContainer = document.getElementById("main-map"), // 지도를 표시할 div
        mapOption = {
          center: new kakao.maps.LatLng(37.5666805, 126.9784147), // 지도의 중심좌표
          level: 7, // 지도의 확대 레벨
        };

      this.map = new kakao.maps.Map(mapContainer, mapOption);
    },
    moveDetail(no) {
      let accessToken = sessionStorage.getItem("accessToken");
      if (accessToken) {
        this.$router.push({ path: "/house/detail/" + no });
      } else {
        alert("로그인 후에 이용해주세요.");
        this.$router.push({ path: "/member/login" });
      }
    },
    requestHouseList() {
      axios
        .get("http://localhost:7779/house/list", {
          params: {
            keyword: this.keyword,
            size: this.size,
            page: 1,
          },
        })
        .then((resp) => {
          console.log(resp);
          this.totalPage = resp.data.totalPage;
          this.totalCount = resp.data.totalCount;
          this.houselist = resp.data.content;
          this.makeMarker();
        });
    },
    makeMarker() {
      // 마커를 표시할 위치와 title 객체 배열입니다
      let positions = [
        {
          title: "",
          latlng: new kakao.maps.LatLng(33.450705, 126.570677),
        },
      ];

      for (let i = 0; i < this.houselist.length; i++) {
        positions.push({
          title: this.houselist[i].dealAmount,
          latlng: new kakao.maps.LatLng(
            this.houselist[i].lat,
            this.houselist[i].lng
          ),
        });
      }

      console.log("전" + this.markers[0]);
      var mapContainer = document.getElementById("main-map"), // 지도를 표시할 div
        mapOption = {
          center: new kakao.maps.LatLng(
            this.houselist[0].lat,
            this.houselist[0].lng
          ), // 지도의 중심좌표
          level: 6, // 지도의 확대 레벨
        };

      this.map = new kakao.maps.Map(mapContainer, mapOption);
      this.removeMarker();
      console.log("후" + this.markers[0]);
      for (let j = 0; j < positions.length; j++) {
        // 마커를 생성합니다
        this.markers = new kakao.maps.Marker({
          map: this.map, // 마커를 표시할 지도
          position: positions[j].latlng, // 마커를 표시할 위치
          title: positions[j].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
        });
        this.markers.setMap(this.map);
      }
    },
    removeMarker() {
      for (var i = 0; i < this.markers.length; i++) {
        this.markers[i].setMap(null);
      }
      this.markers = [];
    },
    likeItem(no) {
      console.log(no);
    },
  },
};
</script>

<style scope>
section {
  width: 100%;
  /* display: flex; */
}
#main-section {
  display: flex;
  height: 93vh;
}
#main-map {
  width: 70%;
  height: 100%;
}
#main-list {
  width: 30%;
  height: 100%;
}

#main-list {
  display: flex;
  flex-direction: column;
  border-left: 1px solid lightgray;
}
#main-list h1 {
  padding: 1rem 1rem;
}

#filter-container {
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 0px 15px 15px 15px;
}

.form_control {
  height: 100%;
  width: 80% !important;
  border: 2px solid #85af4b !important;
  border-top-left-radius: 8px;
  border-bottom-left-radius: 8px;
}

#filter-btn {
  height: 100%;
  border: none;
  width: 20% !important;
  border-top-right-radius: 8px !important;
  border-bottom-right-radius: 8px !important;
  background-color: #85af4b;
  color: #fff;
  font-size: 1.2rem;
  font-weight: bold;
  text-align: center;
}

.test {
  width: 120px;
  height: 100%;
  background-color: rgb(255, 255, 255);
}

.item_box {
  display: flex;
  width: 100%;
  height: 150px;
  padding: 1rem 1rem;
  position: relative;
}
.item_box:hover {
  box-shadow: 5px 10px 30px rgba(0, 0, 0, 0.2);
  border-radius: 10px;
}

#list-item-container {
  height: 100%;
  overflow-y: auto;
}
.list-item-detail {
  margin-left: 0.5rem;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.list-item-detail h5 {
  color: rgb(34, 34, 34);
  flex-wrap: wrap;
  font-family: SpoqaHanSans;
  font-size: 20px;
  font-weight: 800;
  line-height: 15px;
  text-align: left;
}
.list-item-detail h3 {
  color: rgb(34, 34, 34);
  flex-wrap: wrap;
  font-family: SpoqaHanSans;
  font-size: 15px;
  font-weight: 700;
  line-height: 15px;
  text-align: left;
}
.list-item-detail-info {
  color: rgb(34, 34, 34);
  font-family: SpoqaHanSans;
  font-size: 14px;
  line-height: 18px;
  text-align: left;
  display: flex;
  flex-direction: column;
}
.list-item-detail-info label {
  width: 100%;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  line-height: 2;
}
.list-item-detail-info label:last-child {
  color: rgb(117, 117, 117);
}

.pagination {
  margin: 10px auto 20px auto;
}
.label {
  text-align: left;
  width: 100%;
  display: inline-block;
}
</style>
