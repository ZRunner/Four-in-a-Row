package fourinarow;

import static springfox.documentation.builders.PathSelectors.regex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import com.google.common.base.Predicate;

import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//for jsr310 java 8 java.time.*
@EntityScan(basePackageClasses = { Application.class, Jsr310JpaConverters.class })

@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableSwagger2
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

	private static final Logger LOG = LoggerFactory.getLogger(Application.class);

	public Application() {
		super();
		setRegisterErrorPageFilter(false);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("public-api").select().paths(getDocumentedPaths())
				.build();
	}

	/**
	 * Get the location of endpoints which should be documented by Swagger
	 * @return A set of locations
	 */
	private Predicate<String> getDocumentedPaths() {
		return regex("/api/*.*");
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

}
