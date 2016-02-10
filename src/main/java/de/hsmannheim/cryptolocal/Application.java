package de.hsmannheim.cryptolocal;
import java.io.File;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@ComponentScan
@EnableScheduling
public class Application extends SpringBootServletInitializer{

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
