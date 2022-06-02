<template>
  <div class="region_container">
    <br /><br /><br /><br />
    <h1><b>동네 정보보기</b></h1>
    <br />
    <div class="map_wrap">
      <ul id="category">
        <li id="BK9" data-order="0">
          <!-- <span class="category_bg bank"></span> -->
          은행
        </li>
        <li id="PS3" data-order="1">
          <!-- <span class="category_bg mart"></span> -->
          유치원
        </li>
        <li id="PM9" data-order="2">
          <!-- <span class="category_bg pharmacy"></span> -->
          약국
        </li>
        <li id="FD6" data-order="3">
          <!-- <span class="category_bg oil"></span> -->
          음식점
        </li>
        <li id="CE7" data-order="4">
          <!-- <span class="category_bg cafe"></span> -->
          카페
        </li>
        <li id="CS2" data-order="5">
          <!-- <span class="category_bg store"></span> -->
          편의점
        </li>
      </ul>
      <div id="map" style="width: 100%; height: 500px; overflow: hidden"></div>
    </div>
    <b-btn class="my_location" @click="golocation">내 위치로</b-btn>
  </div>
</template>

<script>
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
      latitude: 0,
      longitude: 0,
      isZero: false,
      now: [],
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
    golocation() {
      location.reload();
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
          center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
          level: 3, // 지도의 확대 레벨
        };
      this.map = new kakao.maps.Map(mapContainer, mapOption);
      this.ps = new kakao.maps.services.Places(this.map);

      this.displayMarker([[this.latitude, this.longitude]]);

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
      // 커스텀 오버레이 컨텐츠를 설정합니다
      // this.placeOverlay.setContent("<div>test</div>");
      this.placeOverlay.setMap(this.map);
      // 각 카테고리에 클릭 이벤트를 등록합니다

      this.addCategoryClickEvent();
    },
    displayMarker(markerPositions) {
      if (this.now.length > 0) {
        this.now.forEach((marker) => marker.setMap(null));
      }

      const positions = markerPositions.map(
        (position) => new kakao.maps.LatLng(...position)
      );

      if (positions.length > 0) {
        this.markers = positions.map(
          (position) =>
            new kakao.maps.Marker({
              map: this.map,
              position,
            })
        );

        const bounds = positions.reduce(
          (bounds, latlng) => bounds.extend(latlng),
          new kakao.maps.LatLngBounds()
        );

        this.map.setBounds(bounds);
      }
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
          "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png",
        imageSize = new kakao.maps.Size(24, 35), // 마커 이미지의 크기
        imgOptions = {
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
  },
};
</script>

<style>
h1,
.h1 {
  font-size: 2.5rem;
  text-align: center;
}

.region_container {
  height: 95vh;
  width: 80%;
  margin: auto;
}

.my_location {
  margin-top: 10px;
  color: #fff !important;
  font-weight: 600;
  background-color: #85af4b !important;
  border: 2px solid #85af4b !important;
  margin-bottom: 20px;
  float: right;
}

.my_location:hover {
  margin-top: 10px;
  color: #fff !important;
  font-weight: 600;
  background-color: #274f35 !important;
  border: 2px solid #274f35 !important;
}

.map_wrap,
.map_wrap * {
  margin: auto;
  padding: 0;
  font-family: "Malgun Gothic", dotum, "돋움", sans-serif;
  font-size: 12px;
}
.map_wrap {
  position: relative;
  width: 100%;
  height: 500px;
}
#category {
  float: left;
  position: absolute;
  top: 30px;
  margin-left: 10px;
  border-radius: 5px;
  border: 1px solid #274f35;
  box-shadow: 0 1px 1px #274f35;
  background: #fff;
  overflow: hidden;
  z-index: 2;
}
#category li {
  float: left;
  list-style: none;
  width: 50px;
  border-right: 1px solid #274f35;
  padding: 6px 0;
  text-align: center;
  cursor: pointer;
}
#category li.on {
  background: #eeeded;
}
#category li:hover {
  background: rgb(227, 239, 224);
  border-left: 1px solid #274f35;
  margin-left: -1px;
}
#category li:last-child {
  margin-right: 0;
  border-right: 0;
}
#category li span {
  display: block;
  margin: 0 auto 3px;
  width: 27px;
  height: 28px;
}
#category li .category_bg {
  background: url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png)
    no-repeat;
}
#category li.on .category_bg {
  background-position-x: -46px;
}
.placeinfo_wrap {
  position: absolute;
  bottom: 28px;
  left: -150px;
  width: 300px;
}
.placeinfo {
  position: relative;
  width: 100%;
  border-radius: 6px;
  border: 1px solid #ccc;
  border-bottom: 2px solid #ddd;
  padding-bottom: 10px;
  background: #fff;
}
.placeinfo:nth-of-type(n) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
.placeinfo_wrap .after {
  content: "";
  position: relative;
  margin-left: -12px;
  left: 50%;
  width: 22px;
  height: 12px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png");
}
.placeinfo a,
.placeinfo a:hover,
.placeinfo a:active {
  color: #fff;
  text-decoration: none;
}
.placeinfo a,
.placeinfo span {
  display: block;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}
.placeinfo span {
  margin: 5px 5px 0 5px;
  cursor: default;
  font-size: 13px;
}
.placeinfo .title {
  font-weight: bold;
  font-size: 14px;
  border-radius: 6px 6px 0 0;
  margin: -1px -1px 0 -1px;
  padding: 10px;
  color: #fff;
  background: #85af4b;
  background: #85af4b
    url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png)
    no-repeat right 14px center;
}
.placeinfo .tel {
  color: #0f7833;
}
.placeinfo .jibun {
  color: #999;
  font-size: 11px;
  margin-top: 0;
}
</style>
