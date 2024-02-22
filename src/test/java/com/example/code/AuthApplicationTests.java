package com.example.code;

import com.example.code.model.user.User;
import com.example.code.model.user.UserRole;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CodeRiseAplication.class)
class AuthApplicationTests {

	@Autowired
	private WebTestClient webTestClient;



//testes para cada cenario no cadastro
	@Test
	void testSuccessCadastro() {

		var user = new User("tallia", "tallia@gmail.com", "1307", UserRole.USER);
       webTestClient.post()
			   .uri("/users/cadastro")
			   .bodyValue(user) //envia objeto
			   .exchange()
			   .expectStatus().isCreated();
	}

	@Test
	void testErrorCampoVazioCadastro(){

		var user = new User("", "", "", UserRole.USER);
		webTestClient
				.post()
				.uri("/users/cadastro")
				.bodyValue(user)
				.exchange()
				.expectStatus().isBadRequest();
	}


	@Test
	void testErrorCadastroEmailIguais(){
		var user = new User("tallia", "tallia@gmail.com", "1307", UserRole.USER);

		webTestClient.post()
				.uri("/users/cadastro")
				.bodyValue(user)
				.exchange()
				. expectStatus().isEqualTo(422);
	}

}
