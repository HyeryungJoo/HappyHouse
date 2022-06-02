<template>
  <div class="modify-container">
    <br /><br />
    <h1><b>질문 수정</b></h1>
    <br />
    <b-form-group id="input-group-1" label-for="input-1">
      <b-form-input
        class="input_title"
        v-model="question.qtitle"
        type="text"
        required
      ></b-form-input>

      <b-form-textarea
        id="textarea"
        v-model="question.qcontent"
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
      question: {
        qmid: "",
        qtitle: "",
        qcontent: "",
        qcreatetime: "",
        qdong: "",
      },
    };
  },
  created() {
    let qnum = this.$route.params.qnum;

    console.log("!!", qnum);
    axios.get("http://localhost:7779/question/" + qnum).then((resp) => {
      console.log(resp);
      this.question = resp.data;
    });
  },
  methods: {
    onSubmit(event) {
      event.preventDefault();

      let err = true;
      let msg = "";
      !this.question.qtitle &&
        ((msg = "제목을 입력해주세요"),
        (err = false),
        this.$refs.question.qtitle.focus());
      err &&
        !this.question.qcontent &&
        ((msg = "내용을 입력해주세요"),
        (err = false),
        this.$refs.question.qcontent.focus());

      if (!err) alert(msg);
      // 만약, 내용이 다 입력되어 있다면 registQuestion 호출
      else this.modifyQuestion();
    },
    modifyQuestion() {
      console.log("modifyQuestion");
      let qnum = this.$route.params.qnum;

      console.log("수정값", qnum);
      console.log("수정값", this.question.qtitle);
      console.log("수정값", this.question.qcontent);
      console.log("수정값", this.question.qmid);

      axios
        .put("http://localhost:7779/question/" + qnum, {
          qtitle: this.question.qtitle,
          qcontent: this.question.qcontent,
          qdong: this.question.qdong,
          qmodifywriter: this.question.qmid,
          qnum: this.$route.params.qnum,
        })
        .then((resp) => {
          console.log(resp);
          alert("수정이 완료되었습니다.");
          this.moveList();
        });
    },
    moveList() {
      this.$router.push({ path: "/qna/list" });
    },
    enterToBr(str) {
      return str.replace(/(?:\r\n|\r|\n)/g, "<br />");
    },
  },
};
</script>

<style>
.modify-container {
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
