package com.ssafy.happyhouse.client;

public class KakaoProfile {

    public KakaoProfile(Long id, Properties properties, KakaoAccount kakao_account) {
        this.id = id;
        this.properties = properties;
        this.kakao_account = kakao_account;
    }

    private Long id;

    private Properties properties;

    private KakaoAccount kakao_account;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public KakaoAccount getKakao_account() {
        return kakao_account;
    }

    public void setKakao_account(KakaoAccount kakao_account) {
        this.kakao_account = kakao_account;
    }

    public static class Properties {
        private String nickname;

        private String profile_image;

        private String thumbnail_image;

        public Properties(String nickname, String profile_image, String thumbnail_image) {
            this.nickname = nickname;
            this.profile_image = profile_image;
            this.thumbnail_image = thumbnail_image;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getProfile_image() {
            return profile_image;
        }

        public void setProfile_image(String profile_image) {
            this.profile_image = profile_image;
        }

        public String getThumbnail_image() {
            return thumbnail_image;
        }

        public void setThumbnail_image(String thumbnail_image) {
            this.thumbnail_image = thumbnail_image;
        }
    }

    public static class KakaoAccount{
        private String age_range;
        private String gender;

        public KakaoAccount(String age_range, String gender) {
            this.age_range = age_range;
            this.gender = gender;
        }

        public String getAge_range() {
            return age_range;
        }

        public void setAge_range(String age_range) {
            this.age_range = age_range;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }
    }

}
