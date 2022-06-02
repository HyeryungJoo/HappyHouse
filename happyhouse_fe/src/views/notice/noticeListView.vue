<template>
  <div class="notice_content">
    <br /><br />
    <br /><br />
    <h1><b>공지사항</b></h1>
    <br />
    <b-btn @click="regist()" class="bbtn">글쓰기</b-btn><br />
    <br />
    <div v-if="notice.length">
      <table class="table table-hover" id="bootstrap-table my-table">
        <colgroup>
          <col style="width: 10%" />
          <col style="width: 70%" />
          <col style="width: 20%" />
        </colgroup>
        <thead>
          <tr>
            <th>번호</th>
            <th>제목</th>
            <th>작성시간</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(n, index) in notice" :key="index">
            <td @click="moveView(n.nnum)">{{ index + 1 }}</td>
            <td @click="moveView(n.nnum)">{{ n.ntitle }}</td>
            <td @click="moveView(n.nnum)">{{ n.ncreatetime }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <div v-else class="text-center">게시글이 없습니다.</div>
    <br />
    <b-pagination
      class="pagination"
      v-model="currentPage"
      align="center"
      :per-page="1"
      :total-rows="totalPage"
      :last-number="true"
      @page-click.prevent="pageClick"
      aria-controls="my-table"
    ></b-pagination
    ><br /><br /><br /><br /><br />
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      notice: [],
      size: 10,
      totalPage: "",
      totalCount: "",
      currentPage: 1,
      perPage: 10,
    };
  },
  computed: {
    rows() {
      return this.notice.length;
    },
  },
  created() {
    console.log("init currentPage : " + this.currentPage);
    axios
      .get("http://localhost:7779/notice", {
        params: {
          size: this.size,
          page: this.currentPage,
        },
      })
      .then((resp) => {
        console.log(resp);
        this.notice = resp.data.content;
        this.totalPage = resp.data.totalPage;
        this.totalCount = resp.data.totalCount;
        console.log(this.totalPage);
      });
  },
  methods: {
    moveView(nnum) {
      let accessToken = sessionStorage.getItem("accessToken");
      if (accessToken) {
        this.$router.push({ path: "/notice/detail/" + nnum });
      } else {
        alert("로그인 후에 이용해주세요.");
      }
    },
    regist() {
      let accessToken = sessionStorage.getItem("accessToken");
      if (accessToken && accessToken == "ADMIN") {
        this.$router.push({ path: "/notice/register" });
      } else {
        alert("관리자만 작성이 가능합니다.");
      }
    },
    pageClick: function (button, page) {
      this.currentPage = page;
      this.requestPagenation(page);
    },
    requestPagenation: function (page) {
      axios
        .get("http://localhost:7779/notice", {
          params: {
            size: this.size,
            page: page,
          },
        })
        .then((resp) => {
          console.log(resp);
          this.notice = resp.data.content;
        });
    },
  },
};
</script>

<style>
.notice_content {
  width: 60%;
  margin: auto;
  text-align: center !important;
}

.bbtn {
  float: right;
}
</style>
