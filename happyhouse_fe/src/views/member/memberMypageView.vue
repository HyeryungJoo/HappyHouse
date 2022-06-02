<template>
  <div class="mypage_container">
    <b-tabs content-class="mt-3">
      <b-tab title="내 정보" active>
        <div class="profile_container">
          <div class="profile_group">
            <div class="profile_img_section">
              <img class="profile_img" src="../../assets/resume.png" alt="" />
            </div>
            <div class="updown"></div>
            <div class="myinfo_section">
              <div class="info_row">
                <div class="info_title">이름</div>
                <div class="info_content">{{ myinfo.name }}</div>
              </div>
              <div class="info_row">
                <div class="info_title">ID</div>
                <div class="info_content">{{ myinfo.id }}</div>
              </div>
              <div class="info_row">
                <div class="info_title">E-MAIL</div>
                <div class="info_content">{{ myinfo.email }}</div>
              </div>
              <div class="info_row">
                <div class="info_title">PHONE</div>
                <div class="info_content">{{ myinfo.phone }}</div>
              </div>
            </div>
          </div>
        </div>
        <div class="btn_group">
          <b-btn class="modifyUser" @click="modifyGetInfo()">
            회원정보 수정</b-btn
          >
          <a href="#" class="deleteUser" @click="openDeleteModal()">회원탈퇴</a>
        </div>
        <div class="like_area_container">
          <div v-if="!myLikeArea_gu">
            <h3 class="noLikeArea_text main_content">
              아직 관심동네가 없으신가요?
            </h3>
            <div class="noLikeArea_text sub_content">관심동네 설정하기</div>
            <div class="dropbox_Area">
              <select name="gu" v-model="selectgu">
                <option value="0" selected>-- 구 선택 --</option>
                <option
                  v-for="(g, index) in gu"
                  :key="index"
                  :value="g.gugunName"
                >
                  {{ g.gugunName }}
                </option>
              </select>
              <span class="blank"></span>
              <select name="dong" v-model="selectdong">
                <option value="0" selected>-- 동 선택 --</option>
                <option
                  v-for="(d, index) in dong"
                  :key="index"
                  :value="d.dongName"
                >
                  {{ d.dongName }}
                </option>
              </select>
              <span class="blank"></span>
              <b-btn @click="registerLikeArea">설정</b-btn>
            </div>
          </div>
          <div v-else>
            <b-btn class="myLikeArea_gu">{{ myLikeArea_gu }}</b-btn>
            <span class="blank"></span>
            <b-btn class="myLikeArea_dong">{{ myLikeArea_dong }}</b-btn>
            <span class="blank"></span>
            <b-btn class="modify_likearea" @click="modify_likearea">
              수정하기
            </b-btn>
          </div>
        </div>
      </b-tab>
      <b-tab title="내 관심 매물">
        <div v-if="myinfo_house.length">
          <table class="table table-hover" id="bootstrap-table">
            <colgroup>
              <col style="width: 20%" />
              <col style="width: 40%" />
              <col style="width: 20%" />
              <col style="width: 20%" />
            </colgroup>
            <thead>
              <tr>
                <th>번호</th>
                <th>매물 명</th>
                <th>동</th>
                <th>가격</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(h, index) in myinfo_house" :key="index">
                <td @click="moveHouseView(h.no)">{{ index + 1 }}</td>
                <td @click="moveHouseView(h.no)">{{ h.aptName }}</td>
                <td @click="moveHouseView(h.no)">{{ h.dongName }}</td>
                <td @click="moveHouseView(h.no)">{{ h.dealAmount }}</td>
              </tr>
            </tbody>
          </table>
        </div>
        <div v-else class="text-center">찜한 매물이 없습니다.</div>
      </b-tab>
      <b-tab title="내 Q&A">
        <div v-if="myinfo_question.length">
          <table class="table table-hover" id="bootstrap-table">
            <colgroup>
              <col style="width: 10%" />
              <col style="width: 40%" />
              <col style="width: 10%" />
              <col style="width: 10%" />
              <col style="width: 30%" />
            </colgroup>
            <thead>
              <tr>
                <th>번호</th>
                <th>제목</th>
                <th>동</th>
                <th>작성자</th>
                <th>작성시간</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(q, index) in myinfo_question" :key="index">
                <td @click="moveQnAView(q.qnum)">{{ index + 1 }}</td>
                <td @click="moveQnAView(q.qnum)">{{ q.qtitle }}</td>
                <td @click="moveQnAView(q.qnum)">{{ q.qdong }}</td>
                <td @click="moveQnAView(q.qnum)">{{ q.qmid }}</td>
                <td @click="moveQnAView(q.qnum)">{{ q.qcreatetime }}</td>
              </tr>
            </tbody>
          </table>
        </div>
        <div v-else class="text-center">등록한 질문이 없습니다.</div>
      </b-tab>
    </b-tabs>

    <!-- 컴포넌트 MyModal -->
    <Modal @close="closeModifyModal" v-if="modalModify">
      <!-- default 슬롯 콘텐츠 -->
      <div class="modify_section">
        <div class="input_group">
          <b-form-group
            class="logup_input"
            label-for="input-1"
            :invalid-feedback="invalidNAMEFeedback"
            :state="stateName"
          >
            <b-form-input
              v-model="myinfo_modify.name"
              :state="stateName"
              placeholder="username"
              trim
            ></b-form-input>
          </b-form-group>

          <b-form-group
            class="logup_input"
            label-for="input-1"
            :invalid-feedback="invalidIDFeedback"
            valid-feedback="ID는 변경할 수 없습니다."
            :state="stateID"
          >
            <b-form-input
              v-model="myinfo_modify.id"
              :state="stateID"
              placeholder="ID"
              disabled
            ></b-form-input>
          </b-form-group>

          <b-form-group
            class="logup_input"
            label-for="input-1"
            :invalid-feedback="invalidPWFeedback"
            :state="statePW"
          >
            <b-form-input
              v-model="myinfo_modify.newpw"
              type="password"
              :state="statePW"
              placeholder="새 비밀번호"
              trim
            ></b-form-input>
          </b-form-group>

          <b-form-group
            class="logup_input"
            label-for="input-1"
            valid-feedback="일치합니다!"
            :invalid-feedback="invalidPWCheckFeedback"
            :state="statePWCheck"
          >
            <b-form-input
              v-model="myinfo_modify.newpwcheck"
              :state="statePWCheck"
              type="password"
              placeholder="새비밀번호 확인"
              trim
            ></b-form-input>
          </b-form-group>

          <b-form-group
            class="logup_input"
            label-for="input-1"
            :invalid-feedback="invalidEMAILFeedback"
            :state="stateEmail"
          >
            <b-form-input
              v-model="myinfo_modify.email"
              placeholder="email"
              :state="stateEmail"
              trim
            ></b-form-input>
          </b-form-group>

          <b-form-group
            class="logup_input"
            label-for="input-1"
            :invalid-feedback="invalidPHONEFeedback"
            :state="statePhone"
          >
            <b-form-input
              :state="statePhone"
              v-model="myinfo_modify.phone"
              placeholder="phone number"
              trim
            ></b-form-input>
          </b-form-group>
        </div>
      </div>
      <hr color="#e8ebed" size="3.5px" />
      <div></div>
      <!-- /default -->
      <!-- footer 슬롯 콘텐츠 -->
      <template slot="footer">
        <div class="write_answer_submit">
          <b-button class="answer_submit" @click="modifyMyInfo" type="submit"
            >수정</b-button
          >
        </div>
      </template>
      <!-- footer -->
    </Modal>
    <!-- 컴포넌트 MyModal -->
    <Modal @close="closeDeleteModal" v-if="modalDelete">
      <!-- default 슬롯 콘텐츠 -->
      <div class="delete_section">
        <div>회원님의 정보가 모두 사라지게 됩니다.</div>
        <div>탈퇴하시겠습니까?</div>
      </div>
      <hr color="#e8ebed" size="3.5px" />
      <!-- /default -->
      <!-- footer 슬롯 콘텐츠 -->
      <template slot="footer">
        <div class="delete_submit">
          <b-button
            class="delete_submit_cancel"
            @click="closeDeleteModal"
            type="submit"
            >취소</b-button
          >
          <span class="blank"></span>
          <b-button
            class="answer_submit_delete"
            @click="deleteUser"
            type="submit"
            >확인</b-button
          >
        </div>
      </template>
      <!-- footer -->
    </Modal>
  </div>
</template>

<script>
import axios from "axios";
import Modal from "./memberMyInfoModifyModal.vue";

export default {
  components: { Modal },
  data: function () {
    return {
      modalModify: false,
      modalDelete: false,

      myinfo: {
        mno: "",
        name: "",
        id: "",
        email: "",
        phone: "",
      },
      myinfo_modify: {
        name: "",
        id: "",
        newpw: "",
        newpwcheck: "",
        email: "",
        phone: "",
      },
      myLikeArea_gu: "",
      myLikeArea_dong: "",
      gu: [],
      dong: [],
      selectgu: "0",
      selectdong: "0",
      myinfo_house: [
        {
          no: "",
          aptCode: "",
          aptName: "",
          dongCode: "",
          dongName: "",
          lat: "",
          lng: "",
          dealAmount: "",
          dealYear: "",
          dealMonth: "",
          dealDay: "",
          area: "",
          floor: "",
          type: "",
        },
      ],
      myinfo_question: [
        {
          qnum: "",
          qmid: "",
          qtitle: "",
          qdong: "",
          qcreatetime: "",
        },
      ],
    };
  },

  created() {
    // 내정보 가져오기 + 관심 동네도 가져오기
    // 서버에 회원 정보 요청 (accessToken 담아서)
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
        this.myinfo.mno = resp.data.memberId;
        this.myinfo.name = resp.data.name;
        this.myinfo.id = resp.data.appId;
        this.myinfo.email = resp.data.email;
        this.myinfo.phone = resp.data.mobile;
        this.myinfo.myLikeArea_gu = resp.data.mLikeGu;
        this.myinfo.myLikeArea_dong = resp.data.mLikeDong;
        axios
          .get("http://localhost:7779/member/" + this.myinfo.mno)
          .then((resp) => {
            console.log(resp);
            this.myLikeArea_gu = resp.data.mLikeGu;
            this.myLikeArea_dong = resp.data.mLikeDong;
          });

        // 매물 no로 매물 정보(List)들 조회
        axios
          .get("http://localhost:7779/house/search/like/" + this.myinfo.mno)
          .then((resp) => {
            console.log(resp);
            this.myinfo_house = resp.data;
          });

        // 내 QnA 조회
        axios
          .get("http://localhost:7779/question/search/mylist/" + this.myinfo.id)
          .then((resp) => {
            console.log(resp);
            this.myinfo_question = resp.data;
          });
      });

    axios.get("http://localhost:7779/gudong").then((resp) => {
      console.log(resp);
      this.gu = resp.data;
    });
    //
    //
    // 회원 no로 매물no 조회
    axios.get("http://localhost:7779/gudong").then((resp) => {
      console.log(resp);
      this.gu = resp.data;
    });
  },
  watch: {
    //구 선택 시
    selectgu: function (selectgu) {
      //--구 선택--인경우
      if (this.selectgu == 0) {
        console.log("--구선택--");
        alert("구를 선택해 주세요.");
        axios.get("http://localhost:7779/question").then((resp) => {
          console.log(resp);
          this.question = resp.data;
          this.dong = [];
        });
      }
      console.log(selectgu);
      //제대로 선택 시
      if (this.selectgu != 0) {
        axios
          .get("http://localhost:7779/gudong/" + this.selectgu)
          .then((resp) => {
            console.log("구선택");
            console.log(resp);
            this.dong = resp.data;
          });
      }
      this.selectdong = "0";
    },

    //동 선택 시
    selectdong: function (selectdong) {
      //--동 선택--인 경우
      if (this.selectdong == 0) {
        console.log("--동선택--");
        alert("동을 선택해 주세요.");
        axios.get("http://localhost:7779/question").then((resp) => {
          console.log(resp);
          this.question = resp.data;
        });
      }

      //제대로 선택 시
      if (this.selectdong != 0) {
        console.log("동선택");
        console.log(selectdong);
      }
    },
  },
  computed: {
    stateName() {
      return this.myinfo_modify.name.length > 0;
    },
    stateID() {
      return this.myinfo_modify.id.length >= 5;
    },
    statePW() {
      return this.myinfo_modify.newpw.length >= 5;
    },
    statePWCheck() {
      return (
        this.myinfo_modify.newpwcheck.length > 0 &&
        this.myinfo_modify.newpwcheck == this.myinfo_modify.newpw
      );
    },
    stateEmail() {
      return this.myinfo_modify.email.length > 0;
    },
    statePhone() {
      return this.myinfo_modify.phone.length == 11;
    },
    invalidNAMEFeedback() {
      if (this.myinfo_modify.name.length > 0) {
        return "Please enter something.";
      }
      return "Please enter something.";
    },
    invalidIDFeedback() {
      if (this.myinfo_modify.id.length > 0) {
        return "5자 이상 입력해주세요.";
      }
      return "Please enter something.";
    },
    invalidPWFeedback() {
      if (this.myinfo_modify.newpw.length > 0) {
        return "5자 이상 입력해주세요.";
      }
      return "Please enter something.";
    },
    invalidPWCheckFeedback() {
      if (this.myinfo_modify.newpw != this.myinfo_modify.newpwcheck) {
        return "입력하신 비밀번호와 다릅니다.";
      }
      return "Please enter something.";
    },
    invalidEMAILFeedback() {
      if (this.myinfo_modify.email.length > 0) {
        return "Please enter something.";
      }
      return "Please enter something.";
    },
    invalidPHONEFeedback() {
      if (this.myinfo_modify.phone.length != 11) {
        return "(- 제외)전체 번호를 입력해주세요.";
      }
      return "Please enter something.";
    },
  },
  methods: {
    openModifyModal() {
      this.modalModify = true;
    },
    closeModifyModal() {
      this.modalModify = false;
    },
    openDeleteModal() {
      this.modalDelete = true;
    },
    closeDeleteModal() {
      this.modalDelete = false;
    },
    modifyGetInfo() {
      this.openModifyModal();
      this.myinfo_modify.name = this.myinfo.name;
      this.myinfo_modify.id = this.myinfo.id;
      this.myinfo_modify.email = this.myinfo.email;
      this.myinfo_modify.phone = this.myinfo.phone;
    },
    modifyMyInfo() {
      let accessToken = sessionStorage.getItem("accessToken");
      axios
        .put(
          "http://localhost:7779/member/",
          {
            memberId: this.myinfo.mno,
            name: this.myinfo_modify.name,
            email: this.myinfo_modify.email,
            mobile: this.myinfo_modify.phone,
          },
          {
            headers: {
              Authorization: `Bearer ${accessToken}`,
            },
          }
        )
        .then((resp) => {
          console.log(resp);
        });
      if (this.myinfo_modify.newpw) {
        //비밀번호만 수정
        axios
          .patch(
            "http://localhost:7779/member/password",
            {
              memberId: this.myinfo.mno,
              password: this.myinfo_modify.newpw,
              checkPassword: this.myinfo_modify.newpwcheck,
            },
            {
              headers: {
                Authorization: `Bearer ${accessToken}`,
              },
            }
          )
          .then((resp) => {
            console.log(resp);
          });
      }
      alert("수정이 완료되었습니다.");
      this.closeModifyModal();
      this.$router.go();
    },
    registerLikeArea() {
      console.log(this.selectgu);
      console.log(this.selectdong);
      console.log(this.myinfo.mno);
      axios
        .post("http://localhost:7779/member/registerlikearea", {
          mLikeGu: this.selectgu,
          mLikeDong: this.selectdong,
          mno: this.myinfo.mno,
        })
        .then((resp) => {
          console.log(resp);
          alert("수정이 완료되었습니다.");
          this.$router.go();
        });
    },
    modify_likearea() {
      this.myLikeArea_gu = "";
    },
    deleteUser() {
      let accessToken = sessionStorage.getItem("accessToken");
      axios
        .delete("http://localhost:7779/member", {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        })
        .then((resp) => {
          console.log(resp);
          sessionStorage.removeItem("accessToken");
          this.closeDeleteModal();
          this.$router.push({ path: "/" });
          this.$router.go();
        });
    },
    moveQnAView(qnum) {
      this.$router.push({ path: "/qna/detail/" + qnum });
    },
    moveHouseView(no) {
      this.$router.push({ path: "/house/detail/" + no });
    },
  },
};
</script>

<style>
.container_container {
  min-height: 75vh !important;
  margin: 0px auto !important;
  /* height: 100% !important; */
  width: 100% !important;
}

.mypage_container {
  margin: auto;
  width: 50%;
}

.nav-tabs .nav-link {
  margin-bottom: -1px;
  background: none;
  border: 1px solid transparent;
  border-top-left-radius: 0.25rem;
  border-top-right-radius: 0.25rem;
}
.nav-link {
  display: block;
  padding: 0.5rem 1rem;
  color: #274f35 !important;
  font-weight: 700;
  text-decoration: none;
  transition: color 0.15s ease-in-out, background-color 0.15s ease-in-out,
    border-color 0.15s ease-in-out;
}

.main_content {
  color: #274f35;
  font-weight: 700;
}

.sub_content {
  color: #000000;
  font-weight: 600;
}

.profile_container {
  width: 100%;
  height: 20%;
  box-shadow: 5px 10px 20px rgba(150, 150, 150, 0.2);
  border-radius: 10px;
  border: 1px solid rgba(135, 130, 130, 0.18);

  display: flex;
  justify-content: space-between;
  align-items: center;
}

.profile_group {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  margin: auto;
}

.profile_img_section {
  width: 40%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.myinfo_section {
  width: 60%;
  justify-content: center;
  align-items: center;
}
.info_row {
  margin: 20px auto;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 300px;
  background-color: #85af4b;
  padding: 5px;
  border-radius: 20px;

  text-align: center;
}

.info_title {
  width: 50% !important;
  padding: 5px;
}

.info_content {
  width: 50%;
  background-color: #ffffff;
  border-radius: 20px;
  padding: 5px;
}

.profile_img {
  height: 100px;
  width: 100px;
  margin: auto;
}

.updown {
  border: 2px solid rgb(218, 218, 218);
  width: 0.1px;
  height: 400px;
}

.btn_group {
  position: absolute;
  margin-left: 750px;
  margin-top: -60px;
}

.deleteUser {
  margin-left: 10px;
  text-decoration-line: underline !important ;
  color: rgb(82, 82, 82) !important ;
  font-family: "Mulish", sans-serif;
  font-weight: 400;
  line-height: 25px;
}

.deleteUser :hover {
  margin-left: 10px;
  text-decoration-line: underline !important ;
  color: rgb(82, 82, 82) !important ;
  font-family: "Mulish", sans-serif;
  font-weight: 400;
  line-height: 25px;
}

.like_area_container {
  margin-top: 60px;
}

.btn-secondary {
  color: #fff !important;
  font-weight: 600;
  background-color: #85af4b !important;
  border: 2px solid #85af4b !important;
}

.btn-secondary:hover {
  color: #fff !important;
  font-weight: 600;
  background-color: #274f35 !important;
  border: 2px solid #274f35 !important;
}

.table {
  --bs-table-bg: transparent;
  --bs-table-accent-bg: transparent;
  --bs-table-striped-color: #212529;
  --bs-table-striped-bg: rgba(0, 0, 0, 0.05);
  --bs-table-active-color: #212529;
  --bs-table-active-bg: rgba(0, 0, 0, 0.1);
  --bs-table-hover-color: #212529;
  --bs-table-hover-bg: rgba(0, 0, 0, 0.075);
  width: 150%;
  margin-bottom: 1rem;
  color: #212529;
  vertical-align: top;
  border-color: #dee2e6;
  text-align: center !important;
  align-items: center !important;
}
</style>
