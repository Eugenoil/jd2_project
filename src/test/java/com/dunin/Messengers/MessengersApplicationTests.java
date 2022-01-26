package com.dunin.Messengers;

import com.dunin.Messengers.controller.CommentController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MessengersApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private CommentController commentController;

	@Test
	public void shouldReturnDefaultMessage() throws Exception {
		this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Hello guest")));
	}

	@Test
	public void loginDeniedTest() throws Exception {
		this.mockMvc.perform(get("/user"))
				.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("http://localhost/login"));
	}

	@Test
	public void loginSuccessTest() throws Exception {
		this.mockMvc.perform(formLogin().user("admin").password("123"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/"));
	}

	@Test
	public void badCredentials() throws Exception {
		this.mockMvc.perform(post("/login").param("username", "jonh"))
				.andDo(print())
				.andExpect(status().isForbidden());
	}
}
