package com.rr8602.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// 사용자가 요청 -> 응답 (HTML 파일) : @Controller

// RestController : 사용자가 요청 -> 응답 (Data)

@RestController
public class HttpController {
	private static final String TAG="HttpController: ";
	
	// localhost:8080/blog/http/lombok
	@GetMapping("/http/lombok") 
	public String lombokTest() {
		Member m=Member.builder().username("rr8602").password("1234").email("rr8602@naver.com").build();
		System.out.println(TAG+"getter : "+m.getUsername());
		m.setUsername("8602");
		System.out.println(TAG+"setter : "+m.getUsername());
		return "lombok test 완료";
	}
	
	// 인터넷 브라우저 요청은 무조건 get 요청 밖에 할 수 없다.
	// http://localhost:8080/http/get (select)
	@GetMapping("/http/get") 
	public String getTest(Member m) { // 쿼리스트링 : URL의 뒤에 입력 데이터를 함께 제공하는 가장 단순한 데이터 전달 방법
		// Spring이 id=1&username=ssar&password=1234&Email=ssar@nate.com 를 Member에 넣어줌
		return "get 요청 : "+m.getId()+","+m.getUsername()+","+m.getPassword()+","+m.getEmail();
	}
	
	// http://localhost:8080/http/post (insert)
	@PostMapping("/http/post") // text/plain(raw - 평문), application/json(MessageConverter가 m에 자동 매핑 시켜줌(스프링부트)
	public String postTest(@RequestBody Member m) { // @RequestBody는 post에서만 사용
		return "post 요청 : "+m.getId()+","+m.getUsername()+","+m.getPassword()+","+m.getEmail();
	}
	// http://localhost:8080/http/put (update)
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		return "put 요청"+m.getId()+","+m.getUsername()+","+m.getPassword()+","+m.getEmail();
	}
	// http://localhost:8080/http/delete (delete)
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청";
	}
}
