<template>
  <div class="logup_container">
    <h2>회원가입</h2>
    <div class="input_group">
      <b-form-group
        class="logup_input"
        label-for="input-1"
        valid-feedback="Thank you!"
        :invalid-feedback="invalidNAMEFeedback"
        :state="stateName"
      >
        <b-form-input
          v-model="logup.name"
          :state="stateName"
          placeholder="username"
          trim
        ></b-form-input>
      </b-form-group>

      <b-form-group
        class="logup_input"
        label-for="input-1"
        valid-feedback="Thank you!"
        :invalid-feedback="invalidIDFeedback"
        :state="stateID"
      >
        <b-form-input
          v-model="logup.id"
          :state="stateID"
          placeholder="ID"
          trim
        ></b-form-input>
      </b-form-group>

      <b-form-group
        class="logup_input"
        label-for="input-1"
        valid-feedback="Thank you!"
        :invalid-feedback="invalidPWFeedback"
        :state="statePW"
      >
        <b-form-input
          v-model="logup.pw"
          type="password"
          :state="statePW"
          placeholder="PW"
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
          v-model="logup.pwcheck"
          :state="statePWCheck"
          type="password"
          placeholder="PW check"
          trim
        ></b-form-input>
      </b-form-group>

      <b-form-group
        class="logup_input"
        label-for="input-1"
        valid-feedback="Thank you!"
        :invalid-feedback="invalidEMAILFeedback"
        :state="stateEmail"
      >
        <b-form-input
          v-model="logup.email"
          placeholder="email"
          type="email"
          :state="stateEmail"
          trim
        ></b-form-input>
      </b-form-group>

      <b-form-group
        class="logup_input"
        label-for="input-1"
        valid-feedback="Thank you!"
        :invalid-feedback="invalidPHONEFeedback"
        :state="statePhone"
      >
        <b-form-input
          v-model="logup.phone"
          :state="statePhone"
          placeholder="phone number"
          trim
        ></b-form-input>
      </b-form-group>
    </div>

    <b-button @click="memberLogup" v-on:keyup.enter="memberLogup"
      >가입하기</b-button
    >
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      logup: {
        name: "",
        id: "",
        pw: "",
        pwcheck: "",
        email: "",
        phone: "",
      },
    };
  },
  computed: {
    stateName() {
      return this.logup.name.length > 0;
    },
    stateID() {
      return this.logup.id.length >= 5;
    },
    statePW() {
      return this.logup.pw.length >= 5;
    },
    statePWCheck() {
      return (
        this.logup.pwcheck.length > 0 && this.logup.pwcheck == this.logup.pw
      );
    },
    stateEmail() {
      return this.logup.email.length > 0;
    },
    statePhone() {
      return this.logup.phone.length == 11;
    },
    invalidNAMEFeedback() {
      if (this.logup.name.length > 0) {
        return "Please enter something.";
      }
      return "Please enter something.";
    },
    invalidIDFeedback() {
      if (this.logup.id.length > 0) {
        return "5자 이상 입력해주세요.";
      }
      return "Please enter something.";
    },
    invalidPWFeedback() {
      if (this.logup.pw.length > 0) {
        return "5자 이상 입력해주세요.";
      }
      return "Please enter something.";
    },
    invalidPWCheckFeedback() {
      if (this.logup.pw != this.logup.pwcheck) {
        return "입력하신 비밀번호와 다릅니다.";
      }
      return "Please enter something.";
    },
    invalidEMAILFeedback() {
      if (this.logup.email.length > 0) {
        return "Please enter something.";
      }
      return "Please enter something.";
    },
    invalidPHONEFeedback() {
      if (this.logup.phone.length != 11) {
        return "(- 제외)전체 번호를 입력해주세요.";
      }
      return "Please enter something.";
    },
  },
  methods: {
    memberLogup() {
      console.log(this.logup.name);
      console.log(this.logup.id);
      console.log(this.logup.pw);
      console.log(this.logup.email);
      console.log(this.logup.phone);

      axios
        .post("http://localhost:7779/member", {
          name: this.logup.name,
          appId: this.logup.id,
          password: this.logup.pw,
          email: this.logup.email,
          mobile: this.logup.phone,
        })
        .then((resp) => {
          console.log(resp);
          // 등록 성공 메세지 출력
          alert("회원가입을 완료했습니다. 로그인해주세요!");
          // 목록 페이지로 이동하기
          this.moveHome();
        });
    },
    moveHome() {
      this.$router.push({ path: "/" });
    },
  },
};
</script>

<style>
.logup_container {
  width: 60%;
  margin: auto;
  justify-content: center;
  align-items: center;
  text-align: center;
}
.logup_input {
  width: 300px;
  margin: 10px auto;
}
</style>
