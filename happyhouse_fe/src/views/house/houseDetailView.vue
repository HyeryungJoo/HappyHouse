<template>
  <div class="house_detail_container">
    <img src="../../assets/living-room.jpg" alt="" style="display: none" />
    <img src="../../assets/architecture.jpg" alt="" style="display: none" />
    <img src="../../assets/kitchen.jpg" alt="" style="display: none" />
    <div class="image_and_detailinfo_section">
      <b-carousel
        id="carousel-1"
        v-model="slide"
        controls
        indicators
        background="#ababab"
        style="text-shadow: 1px 1px 2px #333"
        @sliding-start="onSlideStart"
        @sliding-end="onSlideEnd"
      >
        <!-- Text slides with image -->
        <b-carousel-slide
          class="detail_slide_img"
          img-src="http://localhost:8080/img/living-room.ae51a153.jpg"
        ></b-carousel-slide>

        <!-- Slides with custom text -->
        <b-carousel-slide
          class="detail_slide_img"
          img-src="http://localhost:8080/img/architecture.5cc99f98.jpg"
        >
        </b-carousel-slide>

        <!-- Slides with image only -->
        <b-carousel-slide
          class="detail_slide_img"
          img-src="http://localhost:8080/img/kitchen.c94ca0a7.jpg"
        ></b-carousel-slide>
      </b-carousel>

      <div class="detail_section">
        <div class="price_info">
          <h4 class="detail_h4">가격정보</h4>
          <div class="info_rows">
            <div class="info_title">가격</div>
            <span class="blank"></span>
            <div class="content">{{ houseinfo.dealAmount }}</div>
          </div>
        </div>
        <div class="detail_info">
          <h4 class="detail_h4">상세정보</h4>
          <div class="info_rows">
            <div class="info_title">건물이름</div>
            <span class="blank"></span>
            <div class="content">{{ houseinfo.aptName }}</div>
          </div>
          <hr />
          <div v-if="houseinfo.type == 1" class="info_rows">
            <div class="info_title">건물종류</div>
            <span class="blank"></span>
            <div class="content">아파트</div>
          </div>
          <div v-else class="info_rows">
            <div class="info_title">건물종류</div>
            <span class="blank"></span>
            <div class="content">다세대 주택</div>
          </div>
          <hr />
          <div class="info_rows">
            <div class="info_title">해당층</div>
            <span class="blank"></span>
            <div class="content">{{ houseinfo.floor }}층</div>
          </div>
          <hr />
          <div class="info_rows">
            <div class="info_title">면적</div>
            <span class="blank"></span>
            <div class="content">{{ houseinfo.area }}평</div>
          </div>
          <hr />
          <div class="info_rows">
            <div class="info_title">매물등록일</div>
            <span class="blank"></span>
            <div class="content">
              {{ houseinfo.dealYear }}.{{ houseinfo.dealMonth }}.{{
                houseinfo.dealDay
              }}
            </div>
          </div>
        </div>
        <div class="around_facility_info">
          <h4 class="detail_h4">주변시설 정보</h4>
          <div class="map_wrap">
            <div
              id="map"
              style="width: 100%; height: 500px; margin-bottom: 60px"
            ></div>
            <ul id="category">
              <li id="SW8" data-order="0">
                <!-- <span class="category_bg bank"></span> -->
                지하철
              </li>
              <li id="SC4" data-order="1">
                <!-- <span class="category_bg mart"></span> -->
                학교
              </li>
              <li id="CT1" data-order="2">
                <!-- <span class="category_bg pharmacy"></span> -->
                문화시설
              </li>
              <li id="PO3" data-order="3">
                <!-- <span class="category_bg oil"></span> -->
                공공기관
              </li>
              <li id="HP8" data-order="4">
                <!-- <span class="category_bg cafe"></span> -->
                병원
              </li>
              <li id="MT1" data-order="5">
                <!-- <span class="category_bg store"></span> -->
                대형마트
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
    <div class="fixed_box_section">
      <div class="box_fixed house_info">
        <div class="box_title">
          <div class="house_no">매물번호 {{ no }}</div>
          <h5 class="title_price">{{ houseinfo.dealAmount }}</h5>
        </div>
        <div class="box_content">
          <div class="heart">
            <img src="../../assets/like.png" alt="" />
            <div class="content">{{ houseinfo.likeCount }}개</div>
          </div>
          <div class="heart">
            <img src="../../assets/place.png" alt="" />
            <div class="content">{{ houseinfo.dongName }}</div>
          </div>
          <div class="heart">
            <img src="../../assets/area.png" alt="" />
            <div class="content">{{ houseinfo.area }}평</div>
          </div>
          <div class="heart">
            <img src="../../assets/stairs.png" alt="" />
            <div class="content">{{ houseinfo.floor }}층</div>
          </div>
        </div>
        <div class="detail_address">{{ detail_address }}</div>
        <div class="detail_address">{{ houseinfo.aptName }}</div>
        <hr />
        <div class="press_like_border">
          <b-icon
            class="detail_press_like"
            v-if="!like"
            @click="likeHouseDeal"
            icon="heart"
          ></b-icon>
          <b-icon
            class="detail_press_like"
            v-else
            @click="dislikeHouseDeal"
            icon="heart-fill"
          ></b-icon>
        </div>
      </div>
      <div class="box_fixed2 goto_area">
        <div class="content">
          <div class="goto_area_content">해당 매물의 지역이 궁금하신가요?</div>
          <div class="goto_area">동네 생활 보러가기</div>
        </div>
        <b-icon
          class="arrow_right"
          @click="moveQna()"
          icon="arrow-right"
        ></b-icon>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      placeOverlay: null,
      contentNode: null,
      markers: [],
      currCategory: null,
      map: null,
      ps: null,
      id: "",
      content: "",
      isZero: false,
      no: "",
      slide: 0,
      sliding: null,
      detail_address: "",
      houseinfo: {},
      mno: "",
      like: false,
      lat: "",
      lng: "",
    };
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
  created() {
    console.log("houseDeatail");
    this.no = this.$route.params.no;

    // 회원 정보 가져오기 mno
    // mno와 hno로 서버요청보내서 회원이 찜한 것인지 확인
    // 맞으면 하트 채우기 아니면 비우기
    axios.get("http://localhost:7779/house/search/" + this.no).then((resp) => {
      console.log(resp);
      this.houseinfo = resp.data;
      this.lat = resp.data.lat;
      this.lng = resp.data.lng;

      axios
        .get(
          "https://dapi.kakao.com/v2/local/geo/coord2address.json?x=" +
            this.houseinfo.lng +
            "&y=" +
            this.houseinfo.lat +
            "&input_coord=WGS84",
          {
            headers: {
              Authorization: "KakaoAK",
            },
          }
        )
        .then((res) => {
          console.log(res.data);
          this.detail_address = res.data.documents[0].address.address_name;
        });
    });
  },
  methods: {
    appearHeart() {
      this.like = true;
    },
    disapperHeart() {
      this.like = false;
    },
    onSlideStart(slide) {
      this.sliding = true;
      slide;
    },
    onSlideEnd(slide) {
      this.sliding = false;
      slide;
    },
    likeHouseDeal() {
      let accessToken = sessionStorage.getItem("accessToken");
      console.log(accessToken);
      axios
        .get("http://localhost:7779/member", {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        })
        .then((resp) => {
          console.log(resp);
          this.mno = resp.data.memberId;

          axios
            .post(
              "http://localhost:7779/house/search/like/" +
                this.mno +
                "/" +
                this.houseinfo.no
            )
            .then((resp) => {
              console.log(resp);
              // 등록 성공 메세지 출력
              alert("관심매물에 추가하셨습니다.");
            });
        });
      this.appearHeart();
      // 하트 채우기
    },
    dislikeHouseDeal() {
      let accessToken = sessionStorage.getItem("accessToken");
      console.log(accessToken);
      axios
        .get("http://localhost:7779/member", {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        })
        .then((resp) => {
          console.log(resp);
          this.mno = resp.data.memberId;

          axios
            .delete(
              "http://localhost:7779/house/search/like/" +
                this.mno +
                "/" +
                this.houseinfo.no
            )
            .then((resp) => {
              console.log(resp);
              // 등록 성공 메세지 출력
              alert("관심매물을 해제했습니다.");
            });
        });
      // 하트비우기
      this.disapperHeart();
    },
    initMap() {
      this.placeOverlay = new kakao.maps.CustomOverlay({
        position: new kakao.maps.LatLng(37.566826, 126.9786567),
        // content:
        //   '<div class ="label"><span class="left"></span><span class="center">카카오!</span><span class="right"></span></div>',
        zIndex: 1,
      });
      this.contentNode = document.createElement("div");
      this.markers = []; // 마커를 담을 배열입니다
      this.currCategory = ""; // 현재 선택된 카테고리를 가지고 있을 변수입니다

      var mapContainer = document.querySelector("#map"), // 지도를 표시할 div
        mapOption = {
          center: new kakao.maps.LatLng(this.lat, this.lng), // 지도의 중심좌표
          level: 5, // 지도의 확대 레벨
        };
      this.map = new kakao.maps.Map(mapContainer, mapOption);
      this.ps = new kakao.maps.services.Places(this.map);

      // 지도에 idle 이벤트를 등록합니다
      kakao.maps.event.addListener(this.map, "idle", this.searchPlaces);

      // 커스텀 오버레이의 컨텐츠 노드에 css class를 추가합니다
      this.contentNode.className = "placeinfo_wrap";

      // 커스텀 오버레이의 컨텐츠 노드에 mousedown, touchstart 이벤트가 발생했을때
      // 지도 객체에 이벤트가 전달되지 않도록 이벤트 핸들러로 kakao.maps.event.preventMap 메소드를 등록합니다
      this.addEventHandle(
        this.contentNode,
        "mousedown",
        kakao.maps.event.preventMap
      );
      this.addEventHandle(
        this.contentNode,
        "touchstart",
        kakao.maps.event.preventMap
      );
      // // 커스텀 오버레이 컨텐츠를 설정합니다
      this.placeOverlay.setMap(this.map);
      // 각 카테고리에 클릭 이벤트를 등록합니다

      this.addCategoryClickEvent();

      var mark = new kakao.maps.Marker({
        map: this.map,
        position: new kakao.maps.LatLng(this.lat, this.lng),
      });
      console.log(mark);
    },
    addEventHandle(target, type, callback) {
      if (target.addEventListener) {
        target.addEventListener(type, callback);
      } else {
        target.attachEvent("on" + type, callback);
      }
    },
    searchPlaces() {
      if (!this.currCategory) {
        return;
      }

      // 커스텀 오버레이를 숨깁니다
      this.placeOverlay.setMap(null);

      // 지도에 표시되고 있는 마커를 제거합니다
      this.removeMarker();

      this.ps.categorySearch(this.currCategory, this.placesSearchCB, {
        useMapBounds: true,
      });
    },
    placesSearchCB(data, status, pagination) {
      console.log(data + " " + status + " " + pagination);
      if (status === kakao.maps.services.Status.OK) {
        // 정상적으로 검색이 완료됐으면 지도에 마커를 표출합니다
        this.isZero = false;
        this.displayPlaces(data);
      } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
        console.log("no result");
        this.isZero = true;
      } else if (status === kakao.maps.services.Status.ERROR) {
        // 에러로 인해 검색결과가 나오지 않은 경우 해야할 처리가 있다면 이곳에 작성해 주세요
        console.log("error");
      }
    },
    displayPlaces(places) {
      // 몇번째 카테고리가 선택되어 있는지 얻어옵니다
      // 이 순서는 스프라이트 이미지에서의 위치를 계산하는데 사용됩니다
      var order = document
        .getElementById(this.currCategory)
        .getAttribute("data-order");

      let _this = this;
      for (var i = 0; i < places.length; i++) {
        // 마커를 생성하고 지도에 표시합니다
        var marker = this.addMarker(
          new kakao.maps.LatLng(places[i].y, places[i].x),
          order
        );
        // 마커와 검색결과 항목을 클릭 했을 때
        // 장소정보를 표출하도록 클릭 이벤트를 등록합니다
        (function (marker, place) {
          kakao.maps.event.addListener(marker, "click", function () {
            console.log("displayPlaceInfo : " + place.place_name);
            this.content =
              '<div class="placeinfo">' +
              '   <a class="title" href="' +
              place.place_url +
              '" target="_blank" title="' +
              place.place_name +
              '">' +
              place.place_name +
              "</a>";

            if (place.road_address_name) {
              this.content +=
                '    <span title="' +
                place.road_address_name +
                '">' +
                place.road_address_name +
                "</span>" +
                '  <span class="jibun" title="' +
                place.address_name +
                '">(지번 : ' +
                place.address_name +
                ")</span>";
            } else {
              this.content +=
                '    <span title="' +
                place.address_name +
                '">' +
                place.address_name +
                "</span>";
            }

            this.content +=
              '    <span class="tel">' +
              place.phone +
              "</span>" +
              "</div>" +
              '<div class="after"></div>';

            this.contentNode = document.createElement("div");
            this.contentNode.innerHTML = this.content;
            console.log("this.contentNode:" + this.contentNode.innerHTML);

            _this.placeOverlay.setPosition(
              new kakao.maps.LatLng(place.y, place.x)
            );
            _this.placeOverlay.setContent(this.contentNode);
            console.log("overlay position", place.y, place.x);
            _this.placeOverlay.setMap(_this.map);
            this.displayPlaceInfo(place);
          });
        })(marker, places[i]);
      }
    },
    addMarker(position) {
      var imageSrc =
          "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png", // 마커 이미지 url, 스프라이트 이미지를 씁니다
        imageSize = new kakao.maps.Size(24, 35), // 마커 이미지의 크기
        imgOptions = {
          // spriteSize: new kakao.maps.Size(72, 208), // 스프라이트 이미지의 크기
          // spriteOrigin: new kakao.maps.Point(46, 36), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
          offset: new kakao.maps.Point(11, 28), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        },
        markerImage = new kakao.maps.MarkerImage(
          imageSrc,
          imageSize,
          imgOptions
        ),
        marker = new kakao.maps.Marker({
          position: position, // 마커의 위치
          image: markerImage,
        });

      marker.setMap(this.map); // 지도 위에 마커를 표출합니다
      this.markers.push(marker); // 배열에 생성된 마커를 추가합니다

      return marker;
    },
    removeMarker() {
      for (var i = 0; i < this.markers.length; i++) {
        this.markers[i].setMap(null);
      }
      this.markers = [];
    },
    addCategoryClickEvent() {
      var category = document.getElementById("category"),
        children = category.children;

      for (var i = 0; i < children.length; i++) {
        children[i].onclick = this.onClickCategory;
      }
    },
    onClickCategory(a) {
      console.log("onclickcategory");
      // var id = this.id;
      // var className = this.className;
      console.log(a);
      console.log(a.target.getAttribute("id"));
      //========================this.id = a.target.getAttribute("id");
      this.id = a.target.id;
      //========================var className = "";
      var className = a.target.className;

      this.placeOverlay.setMap(null);

      if (className === "on") {
        this.currCategory = "";
        this.changeCategoryClass();
        this.removeMarker();
      } else {
        this.currCategory = this.id;
        this.changeCategoryClass(this);
        this.searchPlaces();
      }
    },
    changeCategoryClass(el) {
      var category = document.getElementById("category"),
        children = category.children,
        i;

      for (i = 0; i < children.length; i++) {
        children[i].className = "";
      }

      if (el) {
        el.className = "on";
      }
    },
    moveQna() {
      this.$router.push({ path: "/qna" });
    },
  },
};
</script>

<style>
.house_detail_container {
  width: 60%;
  margin: auto;
  display: flex;
  justify-content: space-between;
  text-align: left !important;
}

.image_and_detailinfo_section {
  width: 70%;
  height: 100%;
  margin-top: 40px;
  margin-right: 40px;
  position: relative;
  overflow: hidden;
}

.fixed_box_section {
  width: 30%;
  margin-top: 40px;
}

.detail_section {
  margin-top: 40px;
}

.detail_h4 {
  color: #274f35;
  font-weight: 700;
}
.box_fixed {
  position: fixed;
  width: 320px;
  height: 400px;
  padding: 45px;
  margin: 0 auto 24px auto;
  background: rgb(255, 255, 255);
  box-shadow: rgb(0 0 0 / 10%) 0px 10px 20px;
  border: 1px solid rgb(223, 223, 223);
  border-radius: 2px;
}
.box_content {
  display: grid;
  grid-template-columns: 100px 100px;
  grid-row: auto auto;
  grid-column-gap: 8px;
  grid-row-gap: 8px;
  align-items: center;
}
.heart {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.heart img {
  width: 35px;
  height: 35px;
}

.detail_address {
  margin-top: 10px;
}

.box_fixed2 {
  position: fixed;
  top: 550px;
  width: 320px;
  padding: 32px;
  margin: auto;
  background: rgb(255, 255, 255);
  box-shadow: rgb(0 0 0 / 10%) 0px 10px 20px;
  border: 1px solid rgb(223, 223, 223);
  border-radius: 2px;
}

.price_info,
.detail_info {
  margin-bottom: 120px;
}

.around_facility_info {
  margin-bottom: 400px;
}

.detail_slide_img {
  width: 770px;
  height: 440px;
}

.info_rows {
  display: flex;
  align-items: center;
  margin: 16px auto;
}

.info_title {
  width: 160px;
  font-size: 16px;
  line-height: 26px;
  font-weight: 700;
}

.detail_press_like {
  position: relative !important;
  margin: 10px;
  padding: 0px !important;
  vertical-align: -0.4em !important;
  top: 0rem !important;
  right: 0rem !important;
  font-size: 20px;
}

.press_like_border {
  width: fit-content;
  height: fit-content;
  border: 1px solid rgb(223, 223, 223);
  border-radius: 2px;
  margin-top: 20px;
}

.house_no {
  color: lightslategray;
  font-weight: 700;
  margin-bottom: 5px;
}

.title_price {
  color: #85af4b;
  font-weight: 800;
  margin-bottom: 10px;
}

.goto_area_content {
  color: #274f35;
  font-weight: 800;
}

.arrow_right {
  float: right;
  width: 30px;
  height: 30px;
  font-weight: 900;
  background-color: #85af4b;
  color: #fff;
  border-radius: 4px;
}

.arrow_right:hover {
  float: right;
  width: 30px;
  height: 30px;
  font-weight: 900;
  background-color: #274f35;
  color: #fff;
  border-radius: 4px;
}
</style>
