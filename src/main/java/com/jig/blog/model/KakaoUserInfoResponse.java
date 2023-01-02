package com.jig.blog.model;

import lombok.Data;

@Data
public class KakaoUserInfoResponse {
	private long id;
	private String connected_at;
	private KakaoAccount kakao_account;
	private Properties properties;

	@Data
	public class KakaoAccount {
		private Boolean profile_needs_agreement; // 사용자 동의 시 프로필 정보(닉네임/프로필 사진) 제공 가능 // 필요한 동의 항목: 프로필 정보(닉네임/프로필 사진)
		private Boolean profile_nickname_needs_agreement; // 사용자 동의 시 닉네임 제공 가능 // 필요한 동의 항목: 닉네임
		private Boolean profile_image_needs_agreement; // 사용자 동의 시 프로필 사진 제공 가능 // 필요한 동의 항목: 프로필 사진
		private Profile profile; // 프로필 정보 // 필요한 동의 항목: 프로필 정보(닉네임/프로필 사진), 닉네임, 프로필 사진
		private  Boolean name_needs_agreement; // 사용자 동의 시 카카오계정 이름 제공 가능 // 필요한 동의 항목: 이름
		private String name; // 카카오계정 이름 // 필요한 동의 항목: 이름
		public boolean has_email;
		private Boolean email_needs_agreement; // 사용자 동의 시 카카오계정 대표 이메일 제공 가능 // 필요한 동의 항목: 카카오계정(이메일)
		private Boolean is_email_valid; // 이메일 유효 여부 (true: 유효한 이메일, false: 이메일이 다른 카카오계정에 사용돼 만료) // 필요한 동의 항목: 카카오계정(이메일)
		private Boolean is_email_verified; // 이메일 인증 여부 ( true: 인증된 이메일, false: 인증되지 않은 이메일 // 필요한 동의 항목: 카카오계정(이메일)
		private String email; // 카카오계정 대표 이메일 // 필요한 동의 항목: 카카오계정(이메일) 비고:이메일 사용 시 주의사항

		@Data
		public class Profile {
			private String nickname;
			private String thumbnail_image_url;
			private String profile_image_url;
			private String is_default_image;
		}

	}

	@Data
	public class Properties {
		public String nickname;
		public String profile_image;
		public String thumbnail_image;
	}
}
