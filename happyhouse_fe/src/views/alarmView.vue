<template>
  <div class="alarm_container">
    <br /><br /><br /><br />
    <h1><b>알림</b></h1>
    <br />
    <div class="mb-1">
      <b-select class="list" v-model="alarm"
        ><option value="0" selected>-- 전체 알림 보기 --</option>
        <option value="0" selected>읽지 않은 알람</option></b-select
      >
      <b-button class="read" @click="showMsgBoxTwo">전체 알림 읽기</b-button>
    </div>
    <br />
    <br /><br />

    <div>
      <b-card title="Q&A 게시판 안내" class="card_alarm">
        <b-card-text>
          최근 Q&A 게시판에 올라온 부적절한 글은 모두 삭제 조치 되었습니다.
        </b-card-text> </b-card
      ><br /><b-card title="서버 오류 복구 안내" class="card_alarm">
        <b-card-text>
          2022.03.15 서버 오류 현상 해결되었습니다. 사이트 정상 이용 가능합니다!
        </b-card-text> </b-card
      ><br /><b-card title="서버 오류 안내" class="card_alarm">
        <b-card-text>
          2022.03.15 오전 서버 오류 관련 복구 중이니 잠시만 기다려 주세요...
        </b-card-text> </b-card
      ><br /><b-card title="금일 시스템 안정화 작업 안내" class="card_alarm">
        <b-card-text>
          금일 (2022.01.26) 시스템 안정화 작업으로 오후 3시부터 5시까지 사이트
          사용이 불가합니다.
        </b-card-text> </b-card
      ><br /><b-card title="개인정보 관련 안내" class="card_alarm">
        <b-card-text>
          회원님의 개인정보 보호를 위하여 주기적으로 비밀번호를 바꿔 주세요.
        </b-card-text> </b-card
      ><br />
    </div>
    <br /><br /><br /><br /><br />
  </div>
</template>

<script>
export default {
  data() {
    return {
      boxTwo: "",
      alarm: "0",
    };
  },
  methods: {
    showMsgBoxTwo() {
      this.boxTwo = "";
      this.$bvModal
        .msgBoxConfirm("읽은 알람은 2주 후 삭제됩니다.", {
          title: "전체 알림을 읽음 처리 하시겠습니까?",
          size: "md",
          buttonSize: "sm",
          okVariant: "primary",
          okTitle: "YES",
          cancelTitle: "NO",
          footerClass: "p-2",
          hideHeaderClose: false,
          centered: true,
        })
        .then((value) => {
          this.boxTwo = value;
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>

<style>
.read {
  float: right;
}

.list {
  float: left;
}

.alarm_container {
  width: 60%;
  margin: auto;
}

.card_alarm {
  margin-bottom: 10px;
  margin-top: 10px;
}

.btn-primary {
  color: #fff;
  background-color: #778899 !important;
  border-color: #778899 !important;
}
</style>
