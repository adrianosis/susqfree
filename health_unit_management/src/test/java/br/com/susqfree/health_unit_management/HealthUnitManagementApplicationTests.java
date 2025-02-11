package br.com.susqfree.health_unit_management;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class HealthUnitManagementApplicationTests {
	@Test
	void contextLoads() {
	}

	@Test
	void main() {
		HealthUnitManagementApplication.main(new String[]{});
	}

}
