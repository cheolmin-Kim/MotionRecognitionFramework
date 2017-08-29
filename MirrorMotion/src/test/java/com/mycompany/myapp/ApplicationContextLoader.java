package com.mycompany.myapp;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

//기본 러너를 스프링이 지원하는 SpringUnit4ClassRunner로 바꿈
@RunWith(SpringJUnit4ClassRunner.class)

//스프링 설정파일 설정
@ContextConfiguration({
		"file:WebContent/WEB-INF/spring/applicationContext.xml",
		"file:WebContent/WEB-INF/spring/dispatcher-servlet.xml"
})

//Web설정에 맞도록 설정
//@WebAppConfiguration()은 웹 컨텐트가 src/main/webapp/web-inf밑에 존재 
//@WebAppConfiguration("WebContent")=> web-inf가 WebContent라는 폴더에 있다고 알려줌
@WebAppConfiguration("WebContent")
public class ApplicationContextLoader {
	
}
