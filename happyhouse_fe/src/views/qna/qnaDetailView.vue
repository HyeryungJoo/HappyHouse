<template>
  <div id="app">
    <div class="detail-container">
      <div class="question_back">
        <section class="section_question">
          <div class="title">
            <div class="title-left">
              <b-icon
                class="icon-title"
                icon="question-circle"
                aria-hidden="true"
              ></b-icon>
              <h2 class="question_title">{{ question.qtitle }}</h2>
            </div>
            <div class="title-right">
              <b-btn class="modify_btn" @click="moveModifyView(question.qnum)"
                >수정</b-btn
              ><b-btn @click="deletequestion(question.qnum)">삭제</b-btn>
            </div>
          </div>
          <div class="subtitle">
            <h6 class="question_writer">{{ question.qmid }}</h6>
            <h6 class="question_writedate">{{ question.qcreatetime }}</h6>
          </div>
          <hr color="#e8ebed" size="3.5px" />
          <div class="content">{{ question.qcontent }}</div>
        </section>
      </div>
      <div class="answer_back">
        <div v-if="answers.length">
          <section
            class="section_answer"
            v-for="(a, index) in answers"
            :key="index"
          >
            <div class="answer_title">
              <div class="answer_title_left">
                <b-icon
                  class="icon-answer"
                  icon="exclamation-circle"
                  aria-hidden="true"
                ></b-icon>
                <h6 class="answer_writer">{{ a.amid }}</h6>
                <h6 class="answer_writedate">{{ a.createtime }}</h6>
              </div>

              <div class="answer_title_right">
                <b-btn class="answer_btn" @click="selectOneAnswer(a.anum)">
                  수정</b-btn
                ><b-btn class="answer_btn" @click="deleteAnswer(a.anum)">
                  삭제
                </b-btn>
              </div>
            </div>
            <hr color="#e8ebed" size="3.5px" />
            <div class="content">{{ a.acontent }}</div>
          </section>
        </div>

        <div v-else></div>

        <section class="section_write_answer">
          <div class="answer_title">
            <b-icon
              class="icon-answer"
              icon="exclamation-circle"
              aria-hidden="true"
            ></b-icon>
            <h6 class="answer_writer"><b>작성자</b>님, 답변해주세요!</h6>
          </div>
          <hr color="#e8ebed" size="3.5px" />
          <b-form-textarea
            id="textarea"
            v-model="register_answer.text"
            placeholder="답변"
            rows="3"
            max-rows="6"
          ></b-form-textarea>
          <div class="write_answer_submit">
            <b-button
              @click="registerAnswer(question.qnum)"
              class="answer_submit"
              type="submit"
              >답변 등록</b-button
            >
          </div>
        </section>
      </div>
    </div>
    <!-- 컴포넌트 MyModal -->
    <Modal @close="closeModal" v-if="modal">
      <!-- default 슬롯 콘텐츠 -->
      <div class="answer_title">
        <b-icon
          class="icon-answer"
          icon="exclamation-circle"
          aria-hidden="true"
        ></b-icon>
        <h6 class="answer_writer"><b>작성자</b>님, 답변해주세요!</h6>
      </div>
      <hr color="#e8ebed" size="3.5px" />
      <b-form-textarea
        id="textarea"
        v-model="modify_answer.text"
        placeholder="답변"
        rows="3"
        max-rows="6"
      ></b-form-textarea>
      <div></div>
      <!-- /default -->
      <!-- footer 슬롯 콘텐츠 -->
      <template slot="footer">
        <div class="write_answer_submit">
          <b-button
            @click="modifyAnswer(modify_answer.anum)"
            class="answer_submit"
            type="submit"
            >답변 수정</b-button
          >
        </div>
      </template>
      <!-- /footer -->
    </Modal>
  </div>
</template>

<script>
import axios from "axios";
import Modal from "./qnaModifyModal.vue";

export default {
  components: { Modal },
  data: function () {
    return {
      modal: false,
      question: {
        qnum: "",
        qmid: "",
        qtitle: "",
        qcontent: "",
        qcreatetime: "",
      },
      answer: {
        anum: "",
        amid: "",
        acontent: "",
        createtime: "",
        qnum: "",
      },
      answers: [
        {
          anum: "",
          amid: "",
          acontent: "",
          createtime: "",
          qnum: "",
        },
      ],
      register_answer: {
        text: "",
      },
      modify_answer: {
        anum: "",
        text: "",
      },
      mid: "",
    };
  },
  created() {
    let qnum = this.$route.params.qnum;
    //질문 정보 가져오기
    console.log("!!", qnum);
    axios.get("http://localhost:7779/question/" + qnum).then((resp) => {
      console.log(resp);
      this.question = resp.data;
    });
    // 답변 리스트 가져오기
    axios.get("http://localhost:7779/answer/" + qnum).then((resp) => {
      console.log(resp);
      this.answers = resp.data;
    });
  },
  methods: {
    enterToBr(str) {
      return str.replace(/(?:\r\n|\r|\n)/g, "<br />");
    },
    deletequestion(qnum) {
      axios.delete("http://localhost:7779/question/" + qnum).then((resp) => {
        console.log(resp);
      });
      alert("삭제되었습니다.");
      this.$router.push("/qna/list");
    },
    moveModifyView(qnum) {
      console.log("gotoModify", qnum);
      this.$router.push({ path: "/qna/modify/" + qnum });
    },
    registerAnswer(registerqnum) {
      console.log("registerAnswer", registerqnum);
      console.log("registerAnswer 내용 ", this.register_answer.text);

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
            .post("http://localhost:7779/answer/", {
              amid: this.mid,
              acontent: this.register_answer.text,
              qnum: registerqnum,
            })
            .then((resp) => {
              console.log(resp);
              alert("등록이 완료되었습니다.");
              this.$router.go();
            });
        });
    },
    openModal() {
      this.modal = true;
    },
    closeModal() {
      this.modal = false;
    },
    selectOneAnswer(anum) {
      this.openModal();
      axios.get("http://localhost:7779/answer/search/" + anum).then((resp) => {
        console.log(resp);
        this.modify_answer.text = resp.data.acontent;
        this.modify_answer.anum = resp.data.anum;
      });
    },
    modifyAnswer(modifyanum) {
      console.log("수정값", modifyanum);
      console.log("수정값", this.modify_answer.text);

      axios
        .put("http://localhost:7779/answer/" + modifyanum, {
          acontent: this.modify_answer.text,
          amodifywriter: "testId",
          anum: modifyanum,
        })
        .then((resp) => {
          console.log(resp);
          alert("수정이 완료되었습니다.");
          this.closeModal();
          this.$router.go();
        });
    },
    deleteAnswer(anum) {
      axios.delete("http://localhost:7779/answer/" + anum).then((resp) => {
        console.log(resp);
      });
      alert("삭제되었습니다.");
      this.$router.go();
    },
  },
};
</script>

<style>
#app {
  z-index: 0;
}

.section_question {
  width: 60%;
  margin: auto;
}

.title {
  margin-top: 20px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.answer_title {
  margin-top: 10px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  text-align: center;
}

.answer_title_left {
  margin-top: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.icon-title {
  font-size: 28px;
  margin: 0px 5px;
}

.question_title {
  margin: auto 0px;
  font-weight: 500;
  display: inline;
}

.modify_btn {
  margin-right: 10px;
}

.subtitle {
  display: flex;
  align-items: center;
}

.subtitle .question_writer {
  margin: auto 3px;
  font-weight: 500;
}

.subtitle .question_writedate {
  margin: auto 3px;
  font-weight: 500;
  color: rgb(172, 172, 172);
}
.answer_back {
  background-color: #f8f9fa;
  padding: 10px;
}

.section_answer {
  background-color: white;
  margin: 100px auto;
  padding: 10px;
  width: 60%;
  border-radius: 10px;
  border: 1px solid #e9ecef;
}

.answer_writer {
  margin: auto 0px;
  font-weight: 500;
}

.answer_writedate {
  margin: auto 3px;
  font-weight: 500;
  font-size: 15px;
  color: rgb(172, 172, 172);
}

.section_write_answer {
  background-color: white;
  margin: 100px auto;
  padding: 10px;
  width: 60%;
  border-radius: 10px;
  border: 1px solid #e9ecef;
}

.write_answer_submit {
  display: flex;
  justify-content: right;
}

.answer_submit {
  margin: 10px;
}

.icon-answer {
  font-size: 18px;
  margin: 0px 5px;
}

.answer_btn {
  margin-left: 10px;
}
</style>
