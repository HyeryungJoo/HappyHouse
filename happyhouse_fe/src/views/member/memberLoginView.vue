<template>
  <!-- Login Section Begin -->
  <section class="login_spad">
    <div class="login__form">
      <h3>Login</h3>
      <div class="login__form">
        <div class="input__item">
          <b-icon icon="person" class="icon_login"></b-icon>
          <b-form-input
            class="input"
            type="text"
            v-model="login.id"
            placeholder="ID"
          ></b-form-input>
        </div>
        <div class="input__item">
          <b-icon icon="lock" class="icon_login"></b-icon>
          <b-form-input
            class="input"
            type="password"
            v-model="login.pw"
            placeholder="Password"
          ></b-form-input>
        </div>
        <b-btn class="login_btn" @click="onSubmit"> Login Now </b-btn>
      </div>
    </div>
    <div class="updown"></div>
    <div class="login__social__links">
      <div class="kakao" @click="kakaoLogin">
        <img src="../../assets/kakao_login_medium_wide.png" alt="" />
      </div>
      <div class="account_section">
        <div>Forgot Your Password?</div>
        <b-btn @click="openFindPWModal()" class="primary-btn find-pw-btn"
          >비밀번호 찾기</b-btn
        >
      </div>
      <div class="account_section">
        <div>Dont’t Have An Account?</div>
        <b-btn @click="moveLogupView" class="primary-btn register-btn"
          >회원가입</b-btn
        >
      </div>
    </div>
    <!-- 컴포넌트 MyModal -->
    <Modal @close="closeFindPWModal" v-if="modalFindPW">
      <!-- default 슬롯 콘텐츠 -->
      <div class="modify_section">
        <div class="input_group">
          <b-input
            class="input_find"
            v-model="myinfo_check.name"
            placeholder="username"
          ></b-input>

          <b-input
            class="input_find"
            v-model="myinfo_check.id"
            placeholder="ID"
          ></b-input>

          <b-input
            class="input_find"
            v-model="myinfo_check.phone"
            placeholder="phone number"
          ></b-input>
        </div>
      </div>
      <hr color="#e8ebed" size="3.5px" />
      <div></div>
      <!-- /default -->
      <!-- footer 슬롯 콘텐츠 -->
      <template slot="footer">
        <div class="write_answer_submit">
          <b-button class="answer_submit" @click="checkMyInfo" type="submit"
            >회원 인증</b-button
          >
        </div>
      </template>
      <!-- footer -->
    </Modal>

    <!-- 컴포넌트 MyModal -->
    <Modal @close="closeChangePWModal" v-if="modalChangePW">
      <!-- default 슬롯 콘텐츠 -->
      <div>인증에 성공했습니다. 새 비밀번호를 설정해주세요.</div>
      <div class="modify_section">
        <b-form-group
          class="logup_input"
          label-for="input-1"
          :invalid-feedback="invalidPWFeedback"
          :state="statePW"
        >
          <b-form-input
            v-model="mypw_change.newpw"
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
            v-model="mypw_change.newpwcheck"
            :state="statePWCheck"
            type="password"
            placeholder="새비밀번호 확인"
            trim
          ></b-form-input>
        </b-form-group>
      </div>
      <hr color="#e8ebed" size="3.5px" />
      <div></div>
      <!-- /default -->
      <!-- footer 슬롯 콘텐츠 -->
      <template slot="footer">
        <div class="write_answer_submit">
          <b-button class="answer_submit" @click="changePW" type="submit"
            >비밀번호 설정</b-button
          >
        </div>
      </template>
      <!-- footer -->
    </Modal>
  </section>
  <!-- Login Section End -->
</template>

<script>
import axios from "axios";
import { getKakaoToken } from "@/services/login";
import Modal from "./memberFindPWModal.vue";

export default {
  components: { Modal },
  data() {
    return {
      modalFindPW: false,
      modalChangePW: false,

      login: {
        id: "",
        pw: "",
      },
      myinfo_check: {
        name: "",
        id: "",
        phone: "",
      },
      mypw_change: {
        newpw: "",
        newpwcheck: "",
      },
      REST_API_KEY: "REST_API_KEY",
      REDIRECT_URI: "http://localhost:8080/member/kakao",
    };
  },
  computed: {
    statePW() {
      return this.mypw_change.newpw.length >= 5;
    },
    statePWCheck() {
      return (
        this.mypw_change.newpwcheck.length > 0 &&
        this.mypw_change.newpwcheck == this.mypw_change.newpw
      );
    },
    invalidPWFeedback() {
      if (this.mypw_change.newpw.length > 0) {
        return "5자 이상 입력해주세요.";
      }
      return "Please enter something.";
    },
    invalidPWCheckFeedback() {
      if (this.mypw_change.newpw != this.mypw_change.newpwcheck) {
        return "입력하신 비밀번호와 다릅니다.";
      }
      return "Please enter something.";
    },
  },
  methods: {
    openFindPWModal() {
      this.modalFindPW = true;
    },
    closeFindPWModal() {
      this.modalFindPW = false;
    },
    openChangePWModal() {
      this.modalChangePW = true;
    },
    closeChangePWModal() {
      this.modalChangePW = false;
    },
    onSubmit(event) {
      event.preventDefault();
      let err = true;
      let msg = "";
      if (this.login.id == "") {
        msg = "ID와 PW를 모두 입력해주세요";
        err = false;
      }
      if (this.login.pw == "") {
        msg = "ID와 PW를 모두 입력해주세요";
        err = false;
      }

      if (err === false) {
        alert(msg);
      }
      // 만약, 내용이 다 입력되어 있다면 registQuestion 호출
      else {
        this.memberLogin();
      }
    },
    memberLogin() {
      console.log("memberLogin");
      console.log(this.login.id);
      console.log(this.login.pw);
      axios
        .post("http://localhost:7779/member/login", {
          appId: this.login.id,
          password: this.login.pw,
        })
        .then((resp) => {
          console.log(resp);
          // 등록 성공 메세지 출력
          sessionStorage.setItem("accessToken", resp.data.accessToken);
          // let aceessToken = sessionStorage.getItem("accessToken");
          alert("로그인에 성공했습니다.");
          // 목록 페이지로 이동하기
          this.moveHome();
        });
    },
    kakaoLogin() {
      const params = {
        redirectUri: "http://localhost:8080/member/kakao",
      };
      window.Kakao.Auth.authorize(params);
    },
    async setKakaoToken() {
      console.log("카카오 인증 코드", this.$route.query.code);
      sessionStorage.setItem("kakaoToken", this.$route.query.code);
      const { data } = await getKakaoToken(this.$route.query.code);
      console.log("카카오 인증 코드", data.access_token);
      var kakaoToken = sessionStorage.getItem(
        "kakaoToken",
        this.$route.query.code
      );
      console.log(kakaoToken);
      axios
        .post("http://localhost:7779/member/login/kakao", {
          kakaoToken: data.access_token,
        })
        .then((resp) => {
          console.log(resp);
          // 등록 성공 메세지 출력
          sessionStorage.setItem("accessToken", resp.data.accessToken);
          // let aceessToken = sessionStorage.getItem("accessToken");
          alert("로그인에 성공했습니다.");
          // 목록 페이지로 이동하기
          this.moveHome();
        });
      this.moveHome();
    },
    moveHome() {
      this.$router.push({ path: "/" });
      this.$router.go();
    },
    moveLogupView() {
      this.$router.push({ path: "/member/logup" });
    },
    checkMyInfo() {
      axios
        .get("http://localhost:7779/member/password", {
          params: {
            appId: this.myinfo_check.id,
            name: this.myinfo_check.name,
            mobile: this.myinfo_check.phone,
          },
        })
        .then((resp) => {
          console.log(resp);
          this.closeFindPWModal();
          this.openChangePWModal();
        });
    },
    changePW() {
      console.log(this.myinfo_check.id);
      axios
        .post("http://localhost:7779/member/password", {
          appId: this.myinfo_check.id,
          password: this.mypw_change.newpw,
          checkPassword: this.mypw_change.newpwcheck,
        })
        .then((resp) => {
          console.log(resp);
          alert("변경 완료되었습니다. 로그인 시도해주세요.");
          this.closeChangePWModal();
          this.$router.go();
        });
    },
  },
};
</script>

<style>
.login_spad {
  width: 50%;
  height: 75vh;
  margin: auto;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.login__form h3 {
  margin-bottom: 20px;
}
.login__form {
  margin: auto;
}

.updown {
  width: 0.1px;
  height: 400px;
  color: #eeeded;
}

.login__social__links {
  margin: auto;
}

.input__item {
  display: flex;
  justify-content: center;
  align-items: center;
}

.icon_login {
  margin-right: 10px;
}

.input {
  margin: 10px auto;
  width: 300px !important;
}

.login_btn {
  margin-top: 10px;
  float: right;
}

.kakao {
  margin-bottom: 30px;
}

.account_section {
  margin-top: 20px;
}

.account_section div {
  color: #7c7c7c;
  font-weight: 600;
}

.primary-btn {
  width: 300px;
}

.input_find {
  margin: 30px auto;
}
</style>
