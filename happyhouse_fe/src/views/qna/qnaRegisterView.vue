<template>
  <div class="register-container">
    <br /><br /><br /><br />
    <h1><b>Q&A 등록</b></h1>
    <br /><br />
    <div id="map" style="width: 100%; height: 500px; overflow: hidden"></div>

    <b-form-group id="input-group-1" label-for="input-1">
      <b-form-input
        class="input_title"
        v-model="form.title"
        type="text"
        placeholder="글 제목을 입력해주세요."
        required
      ></b-form-input>

      <b-form-textarea
        id="textarea"
        v-model="form.text"
        placeholder="내용"
        rows="10"
        max-rows="15"
      ></b-form-textarea>
    </b-form-group>

    <div class="form_submit_container">
      <b-form @submit="onSubmit" v-if="show">
        <b-button class="form_submit" type="submit">글 등록</b-button>
      </b-form>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      form: {
        title: "",
        text: "",
      },
      show: true,
      mid: "",
      map: null,
      latitude: 0,
      longitude: 0,
      now: [],
      detail_address: "",
    };
  },
  mounted() {
    if (!("geolocation" in navigator)) {
      return;
    }

    // get position
    navigator.geolocation.getCurrentPosition(
      (pos) => {
        this.latitude = pos.coords.latitude;
        this.longitude = pos.coords.longitude;

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
      (err) => {
        alert(err.message);
      }
    );
  },
  methods: {
    initMap() {
      var container = document.getElementById("map");
      var options = {
        center: new kakao.maps.LatLng(37.566826, 126.9786567),
        level: 7,
      };
      this.map = new kakao.maps.Map(container, options);

      // 지도를 클릭한 위치에 표출할 마커입니다
      var marker = new kakao.maps.Marker({
        // 지도 중심좌표에 마커를 생성합니다
        position: this.map.getCenter(),
      });
      // 지도에 마커를 표시합니다
      marker.setMap(this.map);

      this.displayMarker([[this.latitude, this.longitude]]);

      kakao.maps.event.addListener(this.map, "click", function (mouseEvent) {
        console.log(this.latitude + ", " + this.longitude);
        // 클릭한 위도, 경도 정보를 가져옵니다
        var latlng = mouseEvent.latLng;

        // 마커 위치를 클릭한 위치로 옮깁니다
        marker.setPosition(latlng);

        console.log(
          "선택 위도 :" + latlng.getLat() + " / 경도 :" + latlng.getLng()
        );

        localStorage.clear();
        localStorage.setItem("registerQnalat", latlng.getLat());
        localStorage.setItem("registerQnalng", latlng.getLng());
      });
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
    onSubmit(event) {
      event.preventDefault();

      let err = true;
      let msg = "";
      if (this.form.title == "") {
        msg = "제목과 내용을 모두 입력해주세요";
        err = false;
      }
      if (this.form.text == "") {
        msg = "제목과 내용을 모두 입력해주세요";
        err = false;
      }

      if (err === false) {
        alert(msg);
      }
      // 만약, 내용이 다 입력되어 있다면 registQuestion 호출
      else {
        this.registQuestion();
      }
    },
    moveList() {
      this.$router.push({ path: "/qna/list" });
    },
    registQuestion() {
      var lat = localStorage.getItem("registerQnalat"),
        lng = localStorage.getItem("registerQnalng");
      axios
        .get(
          "https://dapi.kakao.com/v2/local/geo/coord2address.json?x=" +
            lng +
            "&y=" +
            lat +
            "&input_coord=WGS84",
          {
            headers: {
              Authorization: "KakaoAK Authorization",
            },
          }
        )
        .then((res) => {
          console.log(res.data.documents[0]);
          this.detail_address = res.data.documents[0].address.address_name;

          //회원정보 가져오기
          let accessToken = sessionStorage.getItem("accessToken");

          axios
            .get("http://localhost:7779/member", {
              headers: {
                Authorization: `Bearer ${accessToken}`,
              },
            })
            .then((resp) => {
              console.log(resp);
              this.mid = resp.data.appId;
              axios
                .post("http://localhost:7779/question", {
                  qmid: this.mid,
                  qtitle: this.form.title,
                  qcontent: this.form.text,
                  qLat: localStorage.getItem("registerQnalat"),
                  qLng: localStorage.getItem("registerQnalng"),
                  qDetailAddress: this.detail_address,
                })
                .then((resp) => {
                  console.log(resp);
                  // 등록 성공 메세지 출력
                  alert("등록이 완료되었습니다.");
                  // 목록 페이지로 이동하기
                  this.moveList();
                });
            });
        });
    },
  },
};
</script>

<style>
.blank {
  margin-right: 10px;
}

.register-container {
  width: 80%;
  margin: auto;
}

.select_area {
  margin: 30px auto;
}
.m-md-2 {
  width: 160px;
}
.form_submit_container {
  margin: 20px 0px;
  display: flex;
  justify-content: right;
}

.input_title {
  margin: 20px auto;
}

.form_submit {
  width: 150px;
}
</style>
