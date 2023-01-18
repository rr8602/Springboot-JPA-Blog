package com.rr8602.blog.model;

import java.sql.Timestamp;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// ORM -> Java(다른언어) Object -> 테이블로 매핑해주는 기술

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더 패턴! 멤버 변수가 많아지더라도 어떤 값을 어떤 필드에 넣는지 코드를 통해 확인할 수 있고, 필요한 값만 집어넣는 것이 가능
@Entity // User 클래스가 MySQL에 테이블이 생성이 된다.
// @DynamicInsert // insert 시에 null인 필드를 제외시켜 준다
public class User {
	
	@Id // Primary key
	@GeneratedValue(strategy=GenerationType.IDENTITY) // 프로젝트랑 연결된 DB의 넘버링 전략을 따라감(IDENTITY == MySQL : Auto_increment)
	private int id; // 시퀀스, auto_increment
	
	@Column(nullable = false, length = 30, unique = true)
	private String username; // 아이디
	
	@Column(nullable = false, length = 100) // 패스워드 => 해쉬 (비밀번호 암호화)
	private String password;

	@Column(nullable = false, length = 50)
	private String email;
	
	// @ColumnDefault("user")
	// DB는 Role Type이라는게 없다.
	@Enumerated(EnumType.STRING)
	private RoleType role; // Enum을 쓰는게 좋다. (데이터에 도메인(범위)을 줄 수 있다.) // ADMIN, USER
	
	@CreationTimestamp // 시간이 자동으로 입력
	private Timestamp createDate;
}
