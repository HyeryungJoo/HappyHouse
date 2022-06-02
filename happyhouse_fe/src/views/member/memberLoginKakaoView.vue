<template>
  <div></div>
</template>

<script>
import axios from "axios";
import { getKakaoToken } from "@/services/login";
export default {
  data() {
    return {
      REST_API_KEY: "REST_API_KEY",
      REDIRECT_URI: "http://localhost:8080/member/kakao",
      accessToken: null,
    };
  },
  mounted() {
    if (!this.$route.query.code) this.kakaoLogin();
    else {
      console.log("getKakaoAccessToken route");
      this.getKakaoAccessToken(this.$route.query.code);
    }
  },
  kakaoLogin() {
    const params = {
      redirectUri: "http://localhost:8080/member/kakao",
    };
    window.Kakao.Auth.authorize(params);
  },
  async getKakaoAccessToken(code) {
    console.log("카카오 인증 코드" + this.$route.query.code);
    sessionStorage.setItem("kakaoToken", this.$route.query.code);

    const path = "/tokenApi/oauth/token";
    console.log("getKakaoAccessToken");
    const { data } = await getKakaoToken(this.$route.query.code);
    console.log("카카오 인증 코드", data.access_token);
    var kakaoToken = sessionStorage.getItem(
      "kakaoToken",
      this.$route.query.code
    );
    console.log("kakaoToken" + kakaoToken);
    let getToken = axios.create({
      baseUrl: path,
    });
    getToken.defaults.headers.common["Access-Control-Allow-Origin"] = "*";
    getToken.defaults.headers.common["Access-Control-Allow-Methods"] =
      "GET,POST,PUT,DELETE,OPTIONS";
    getToken.defaults.headers.common["Content-Type"] =
      "application/x-www-form-urlencoded;charset=utf-8";
    getToken.defaults.params = {
      grant_type: "authorization_code",
      client_id: this.REST_API_KEY,
      redirect_uri: this.REDIRECT_URI,
      code: code,
    };
    await getToken
      .post(path)
      .then((res) => {
        if (res.data.access_token) {
          this.access_token = res.data.access_token;
          axios
            .post("http://localhost:7779/member/login/kakao", {
              kakaoToken: this.accessToken,
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
        } else {
          throw "오류가 발생했습니다. 다시 시도해주세요";
        }
      })
      .catch(() => {
        this.kakaoLogin();
      });
  },
  moveHome() {
    this.$router.push({ path: "/" });
    this.$router.go();
  },
};
</script>
