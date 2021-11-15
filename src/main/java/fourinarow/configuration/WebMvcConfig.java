package fourinarow.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import fourinarow.interceptor.AuthInterceptor;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	
	@Bean
    AuthInterceptor AuthInterceptor() {
         return new AuthInterceptor();
    }

    // Static Resource Config
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(AuthInterceptor())
        	.addPathPatterns("/api/???Tictactoe")
        	.addPathPatterns("/api/me/**");

        //Example
        /*registry.addInterceptor(new AdminInterceptor())
                .addPathPatterns("/admin/*")
                .excludePathPatterns("/admin/oldLogin");*/
    }

}
