package com.rr8602.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rr8602.blog.model.User;

// DAO
// 자동으로 bean 등록이 된다.
// @Repository 생략 가능
public interface UserRepository extends JpaRepository<User, Integer> {
	// JPA Naming 전략
	// select * from user where username=? and password=?;
	User findByUsernameAndPassword(String username, String password);

//	@Query(value="select * from user where username=? and password=?;", nativeQuery=true)
//	User login(String username, String password);
}
