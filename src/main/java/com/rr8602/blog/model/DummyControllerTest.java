package com.rr8602.blog.model;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rr8602.blog.repository.UserRepository;

import jakarta.transaction.Transactional;

// html 파일이 아니라 data를 리턴해주는 controller = RestController
@RestController
public class DummyControllerTest {

	@Autowired // 클래스가 메모리에 뜰 때, userRepository도 같이 뜬다. (의존성 주입 (DI))
	private UserRepository userRepsitory;

	// save함수는 id를 전달하지 않으면 insert를 해주고
	// save함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
	// save함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 한다
	// email, password

	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepsitory.deleteById(id);
		} catch (EmptyResultDataAccessException e) { // 최고 부모인 Exception 써도 됨
			return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
		}
		return "삭제 되었습니다. id : " + id;
	}

	@Transactional // 함수 종료시에 자동 commit이 됨
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) { // jon 데이터를 요청 => Java
																					// Object(messageConverter의 Jackson
																					// 라이브러리로 변환해서 받아줘요
		System.out.println("id : " + id);
		System.out.println("password : " + requestUser.getPassword());
		System.out.println("email : " + requestUser.getEmail());

		User user = userRepsitory.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());

		// userRepsitory.save(user);

		// 더티 체킹 (JPA 캐쉬에서 변경감지 -> DB를 수정 해준다 (영속성 컨텍스트))
		return null;
	}

	// http://localhost:8000/blog/dummy/user/
	@GetMapping("/dummy/users")
	public List<User> list() {
		return userRepsitory.findAll(); // 전체 리턴
	}

	// 한 페이지당 2건에 데이터를 리턴받아 볼 예정
	@GetMapping("/dummy/user")
	public List<User> pageList(
			@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC) org.springframework.data.domain.Pageable pageable) {
		Page<User> pagingUser = userRepsitory.findAll(pageable);
		List<User> users = pagingUser.getContent();

		if (pagingUser.isFirst()) {

		}

		return users;
	}

	// {id} 주소로 파라메터를 전달 받을 수 있음
	// http://localhost:8000/blog/dummy/user/5
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		// user/4를 찾으면 내가 DB에서 못찾아오게 되면 user가 null이 될 것 아냐?
		// 그럼 return null이 리턴이 되자나.. 그럼 프로그램에 문제가 있지 않겠니?
		// Optional로 너의 User 객체를 감싸서 가져올테니 null인지 아닌지 판단해서 return 해

		// 람다식
		// User user = userRepository.findById(id).orElseThrow(()->{
		// return new IllegalArgumentException("해당 사용자는 없습니다.");
		// });
		User user = userRepsitory.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				return new IllegalArgumentException("해당 유저는 없습니다.");
			}
		});
		// 요청 : 웹 브라우저
		// user 객체는 = 자바 오브젝트
		// 변환 (웹 브라우저가 이해할 수 있는 데이터) -> json (Gson 라이브러리)
		// 스프링부터 = MessageConverter가 응답시에 자동 작동
		// 만약에 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson 라이브러리를 호출해서
		// user 오브젝트를 json으로 변환해서 브라우저에게 던져줌
		return user;
	}

	// http://localhost:8000/blog/dummy/join (요청)
	// http의 body에 username, password, email 데이터를 가지고 (요청)
	@PostMapping("/dummy/join")
	public String join(User user) { // key=value (약속된 규칙)]
		System.out.println("id : " + user.getId());
		System.out.println("username : " + user.getUsername());
		System.out.println("password : " + user.getPassword());
		System.out.println("email : " + user.getEmail());
		System.out.println("role : " + user.getRole());
		System.out.println("createDate : " + user.getCreateDate());

		user.setRole(RoleType.USER);
		userRepsitory.save(user);
		return "회원가입이 완료되었습니다.";
	}
}
