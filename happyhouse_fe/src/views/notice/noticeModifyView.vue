<template>
  <div>
    <b-form-group id="input-group-1" label-for="input-1">
      <b-form-input
        class="input_title"
        v-model="notice.ntitle"
        type="text"
        required
      ></b-form-input>

      <b-form-textarea
        id="textarea"
        v-model="notice.ncontent"
        rows="10"
        max-rows="15"
      ></b-form-textarea>
    </b-form-group>

    <div class="form_submit_container">
      <b-form @submit="onSubmit">
        <b-button class="form_submit" type="submit">수정 완료</b-button>
      </b-form>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data: function () {
    return {
      notice: { aid: "", ntitle: "", ncontent: "", ncreatetime: "" },
      mid: "",
    };
  },
  created() {
    let nnum = this.$route.params.nnum;

    console.log("!!", nnum);
    axios.get("http://localhost:7779/notice/" + nnum).then((resp) => {
      console.log(resp);
      this.notice = resp.data;
    });
  },
  methods: {
    onSubmit(event) {
      event.preventDefault();

      let err = true;
      let msg = "";
      !this.notice.ntitle &&
        ((msg = "제목을 입력해주세요"),
        (err = false),
        this.$refs.notice.ntitle.focus());
      err &&
        !this.notice.ncontent &&
        ((msg = "내용을 입력해주세요"),
        (err = false),
        this.$refs.notice.ncontent.focus());

      if (!err) alert(msg);
      // 만약, 내용이 다 입력되어 있다면 registQuestion 호출
      else this.modifyNotice();
    },
    modifyNotice() {
      console.log("modifyNotice");
      let nnum = this.$route.params.nnum;

      console.log("수정값", nnum);
      console.log("수정값", this.notice.ntitle);
      console.log("수정값", this.notice.ncontent);
      console.log("수정값", this.notice.aid);

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
            .put("http://localhost:7779/notice/" + nnum, {
              ntitle: this.notice.ntitle,
              ncontent: this.notice.ncontent,
              nmodifywriter: this.mid,
              nnum: this.$route.params.nnum,
            })
            .then((resp) => {
              console.log(resp);
              alert("수정이 완료되었습니다.");
              this.moveList();
            });
        });
    },
    moveList() {
      this.$router.push({ path: "/notice/list" });
    },
  },
};
</script>

<style></style>
