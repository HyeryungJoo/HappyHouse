<template>
  <div class="qna_container">
    <div id="qna-map"></div>
    <div class="overflow-auto">
      <b-btn class="register_qna" @click="regist">글쓰기</b-btn>
      <div v-if="question.length" id="my-table">
        <div class="item" v-for="(q, index) in question" :key="index">
          <div class="item_QnA" @click="moveView(q.qnum)">
            <h5 id="qtitle">{{ q.qtitle }}</h5>
            <div class="id_createtime">
              <div id="qmid">{{ q.qmid }}</div>
              <div id="qtime">{{ q.qcreatetime }}</div>
            </div>
            <div class="detail_address">{{ q.qDetailAddress }}</div>
          </div>
        </div>
      </div>
      <div v-else class="no_item_textcenter">게시글이 없습니다.</div>

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
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "app",
  data() {
    return {
      question: [],
      size: 10,
      totalPage: "",
      totalCount: "",
      currentPage: 1,
      perPage: 10,

      map: null,
      markers: [],
      now: [],
    };
  },
  created() {
    axios
      .get("http://localhost:7779/question", {
        params: {
          size: this.size,
          page: this.currentPage,
        },
      })
      .then((resp) => {
        console.log(resp);
        this.question = resp.data.content;
        this.totalPage = resp.data.totalPage;
        this.totalCount = resp.data.totalCount;
        this.makeMarker();
      });
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

    // if (!("geolocation" in navigator)) {
    //   return;
    // }

    // // get position
    // navigator.geolocation.getCurrentPosition(
    //   (pos) => {
    //     this.latitude = pos.coords.latitude;
    //     this.longitude = pos.coords.longitude;

    //   },
    //   (err) => {
    //     alert(err.message);
    //   }
    // );
  },
  computed: {
    rows() {
      return this.question.length;
    },
  },
  methods: {
    initMap() {
      // 지도 생성
      var mapContainer = document.getElementById("qna-map"), // 지도를 표시할 div
        mapOption = {
          center: new kakao.maps.LatLng(37.5666805, 126.9784147), // 지도의 중심좌표
          level: 7, // 지도의 확대 레벨
        };

      this.map = new kakao.maps.Map(mapContainer, mapOption);
      //placeOverlay.setMap(this.map);
      // 마커 생성
      var marker = new kakao.maps.Marker({
        position: this.map.getCenter(),
      });
      marker.setMap(this.map);

      this.displayMarker([[this.latitude, this.longitude]]);
    },
    displayMarker(markerPositions) {
      if (this.now.length > 0) {
        this.now.forEach((marker) => marker.setMap(null));
      }

      const positions = markerPositions.map(
        (position) => new kakao.maps.LatLng(...position)
      );

      if (positions.length > 0) {
        // this.markers = positions.map(
        //   (position) =>
        //     new kakao.maps.Marker({
        //       map: this.map,
        //       position,
        //     })
        // );

        const bounds = positions.reduce(
          (bounds, latlng) => bounds.extend(latlng),
          new kakao.maps.LatLngBounds()
        );

        this.map.setBounds(bounds);
      }
    },
    regist() {
      localStorage.setItem("위도", this.selectLat);
      localStorage.setItem("경도", this.selectLng);
      let accessToken = sessionStorage.getItem("accessToken");
      if (accessToken) {
        this.$router.push({ path: "/qna/register" });
      } else {
        alert("로그인 후에 이용해주세요.");
      }
    },
    moveView(qnum) {
      let accessToken = sessionStorage.getItem("accessToken");
      if (accessToken) {
        this.$router.push({ path: "/qna/detail/" + qnum });
      } else {
        alert("로그인 후에 이용해주세요.");
      }
    },
    pageClick(button, page) {
      this.currentPage = page;
      this.requestPagenation(page);
    },
    requestPagenation(page) {
      axios
        .get("http://localhost:7779/question", {
          params: {
            size: this.size,
            page: page,
          },
        })
        .then((resp) => {
          console.log(resp);
          this.question = resp.data.content;
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

      for (let i = 0; i < this.question.length; i++) {
        positions.push({
          title: this.question[i].qtitle,
          latlng: new kakao.maps.LatLng(
            this.question[i].qLat,
            this.question[i].qLng
          ),
        });
      }

      console.log("전" + this.markers[0]);
      var mapContainer = document.getElementById("qna-map"), // 지도를 표시할 div
        mapOption = {
          center: new kakao.maps.LatLng(
            this.question[0].qLat,
            this.question[0].qLng
          ), // 지도의 중심좌표
          level: 4, // 지도의 확대 레벨
        };

      this.map = new kakao.maps.Map(mapContainer, mapOption);
      this.removeMarker();
      console.log("후" + this.markers[0]);
      for (let j = 0; j < positions.length; j++) {
        // 마커를 생성합니다
        console.log(positions[j].title);
        this.markers = new kakao.maps.Marker({
          map: this.map, // 마커를 표시할 지도
          position: positions[j].latlng, // 마커를 표시할 위치
          content: positions[j].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
        });
        this.markers.setMap(this.map);

        // 마커에 표시할 인포윈도우를 생성합니다
        var infowindow = new kakao.maps.InfoWindow({
          content: positions[j].title, // 인포윈도우에 표시할 내용
        });

        // 마커에 mouseover 이벤트와 mouseout 이벤트를 등록합니다
        // 이벤트 리스너로는 클로저를 만들어 등록합니다
        // for문에서 클로저를 만들어 주지 않으면 마지막 마커에만 이벤트가 등록됩니다
        kakao.maps.event.addListener(
          this.markers,
          "mouseover",
          makeOverListener(this.map, this.markers, infowindow)
        );
        kakao.maps.event.addListener(
          this.markers,
          "mouseout",
          makeOutListener(infowindow)
        );
      }

      // 인포윈도우를 표시하는 클로저를 만드는 함수입니다
      function makeOverListener(map, marker, infowindow) {
        return function () {
          infowindow.open(map, marker);
        };
      }

      // 인포윈도우를 닫는 클로저를 만드는 함수입니다
      function makeOutListener(infowindow) {
        return function () {
          infowindow.close();
        };
      }
    },
    removeMarker() {
      for (var i = 0; i < this.markers.length; i++) {
        this.markers[i].setMap(null);
      }
      this.markers = [];
    },
  },
};
</script>

<style>
.qna_container {
  width: 100%;
  display: flex;
  height: 93vh;
}

#qna-map {
  width: 80%;
  height: 100%;
}

select {
  color: rgb(0, 0, 0);
  background-color: #ffffff;
  border-color: #85af4b !important;
  display: inline-block;
  font-weight: 400;
  text-align: center;
  border: 2px solid transparent;
  border-radius: 5px;
  padding: 0.5rem 0.75rem;
  vertical-align: middle;
  transition: color 0.15s ease-in-out, background-color 0.15s ease-in-out,
    border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
}

/* 테이블 */
.item {
  text-align: center;
}

.overflow-auto {
  overflow: auto !important;
  width: 20% !important;
}

.item_QnA {
  text-align: left !important;
  padding: 1rem 1rem;
  width: 100%;
  height: 120px;
  position: relative;
}
.item_QnA:hover {
  box-shadow: 5px 10px 30px rgba(0, 0, 0, 0.2);
  border-radius: 10px;
}
.no_item_textcenter {
  font-size: 15px;
  font-weight: 600;
  color: lightslategray;
  margin: auto;
  text-align: center;
}

#qtitle {
  color: #85af4b;
  font-weight: 700;
}

.id_createtime {
  display: flex;
  color: grey;
}

#qmid {
  margin-right: 10px;
}

.register_qna {
  margin-left: 100px;
  margin-bottom: 20px;
  width: 200px;
}
/* 페이징 처리 */
.pagination {
  margin: 0 auto;
}

.page-item.active .page-link {
  z-index: 3;
  color: #fff !important;
  background-color: #85af4b !important;
  border-color: #85af4b !important;
  /* background-color: #0d6efd; */
  /* border-color: #0d6efd; */
}

.page-link {
  position: relative;
  display: block;
  color: #85af4b !important;
  text-decoration: none;
  background-color: #fff;
  border: 1px solid #dee2e6;
  transition: color 0.15s ease-in-out, background-color 0.15s ease-in-out,
    border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
}
</style>
