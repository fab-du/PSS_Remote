package de.hsmannheim.cryptolocal;
import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
@ImportResource({ "classpath:security.xml" })
@EnableAutoConfiguration
public class Application extends SpringBootServletInitializer{

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		//servletContext.getSessionCookieConfig().
		//servletContext.addFilter(filterName, filterClass)
		servletContext.setInitParameter("filter", "");
		super.onStartup(servletContext);
	}
	
	
	
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}	
	
	public static void main( String[] args ) throws Exception{
		SpringApplication.run(Application.class, args);	
		Application.makeUploadDir();
	}
	
	public static void makeUploadDir(){
		File file = new File("uploads");
		
		if( !file.exists()){
			if( file.mkdir()){
				System.out.println("Make upload dir");
			}
			else{
				System.out.println("Upload dir already exists");
			}
		}
	}
}
