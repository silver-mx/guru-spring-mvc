package guru.springframework;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringmvcApplication {
	
	private static Logger log = LoggerFactory.getLogger(SpringmvcApplication.class);

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(SpringmvcApplication.class, args);

		log.info("Beans ******************");
		log.info("count=" + ctx.getBeanDefinitionCount());
		Arrays.stream(ctx.getBeanDefinitionNames()).forEach(def -> log.info(def));
	}
}
