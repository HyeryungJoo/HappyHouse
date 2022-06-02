<template>
  <div class="detail-container">
    <div class="question_back">
      <section class="section_question">
        <div class="title">
          <div class="title-left">
            <b-icon
              class="icon-title"
              icon="exclamation-circle"
              aria-hidden="true"
            ></b-icon>
            <h2 class="question_title">{{ notice.ntitle }}</h2>
          </div>
          <div class="title-right">
            <b-btn class="modify_btn" @click="moveModifyView(notice.nnum)"
              >수정</b-btn
            ><b-btn @click="deletenotice(notice.nnum)">삭제</b-btn>
          </div>
        </div>
        <div class="subtitle">
          <h6 class="question_writer">{{ notice.aid }}</h6>
          <h6 class="question_writedate">{{ notice.ncreatetime }}</h6>
        </div>
        <hr color="#e8ebed" size="3.5px" />
        <div class="content">{{ notice.ncontent }}</div>
      </section>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      notice: {
        nnum: "",
        aid: "",
        ntitle: "",
        ncontent: "",
        ncreatetime: "",

        mid: "",
      },
    };
  },
  created() {
    console.log("create");
    let nnum = this.$route.params.nnum;
    console.log("!!", nnum);
    axios.get("http://localhost:7779/notice/" + nnum).then((resp) => {
      console.log(resp);
      this.notice = resp.data;
    });
  },
  methods: {
    deletenotice(nnum) {
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
          if (this.mid == this.notice.aid) {
            axios
              .delete("http://localhost:7779/notice/" + nnum)
              .then((resp) => {
                console.log(resp);
              });
            alert("삭제되었습니다.");
            this.$router.push("/notice/list");
          } else {
            alert("로그인 후에 이용해주세요.");
          }
        });
    },
    moveModifyView(nnum) {
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
          console.log(resp.data.appId);
          this.mid = resp.data.appId;
          if (this.mid == this.notice.aid) {
            this.$router.push({ path: "/notice/modify/" + nnum });
          } else {
            alert("글의 작성자(관리자)만 수정이 가능합니다.");
          }
        });
    },
  },
};
</script>

<style>
.detail-container {
  width: 70%;
  margin: auto;
}
</style>
