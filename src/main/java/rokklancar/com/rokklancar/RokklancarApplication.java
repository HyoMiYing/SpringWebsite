package rokklancar.com.rokklancar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import rokklancar.com.rokklancar.services.AWSS3Service;

@SpringBootApplication
public class RokklancarApplication {
	private static ApplicationContext applicationContext;

	public static void main(String[] args) {
		applicationContext = SpringApplication.run(RokklancarApplication.class, args);
		displayAllBeans();
		if (!System.getProperty("user.name").equals("rok"))
		{
			System.out.println("CALLING AWS");
			AWSS3Service.downloadAudiobookFile();
		}
	}

	public static void displayAllBeans() {
		String[] allBeanNames = applicationContext.getBeanDefinitionNames();
		for(String beanName : allBeanNames) {
			System.out.println(beanName);
		}
	}

}
