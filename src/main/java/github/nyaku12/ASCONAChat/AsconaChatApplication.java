package github.nyaku12.ASCONAChat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class AsconaChatApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(AsconaChatApplication.class, args);
	}

	@EventListener
	public void onReady(ApplicationReadyEvent event){
		System.out.println("Ready in: " + event.getTimeTaken().toMillis());
	}

}
