<template>
  <div class="register-container">
    <br /><br />
    <h1><b>공지사항 등록</b></h1>
    <br />
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
    };
  },
  methods: {
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
        this.registNotice();
      }
    },
    registNotice() {
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
            .post("http://localhost:7779/notice", {
              aid: this.mid,
              ntitle: this.form.title,
              ncontent: this.form.text,
            })
            .then((resp) => {
              console.log(resp);
              // 등록 성공 메세지 출력
              alert("등록이 완료되었습니다.");
              // 목록 페이지로 이동하기
              this.moveList();
            });
        });
      console.log("registNotice");
    },
    moveList() {
      this.$router.push({ path: "/notice/list" });
    },
  },
};
</script>

<style></style>
