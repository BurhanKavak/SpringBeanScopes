package scopeSingletonVsPrototype;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {
		
		ApplicationContext ap = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		
		HelloWorld hello = (HelloWorld) ap.getBean("hw");
		
		hello.setName("Merhaba Burhan");
		
		System.out.println("Günaydın " + hello.getName());
		
		HelloWorld hello2 = (HelloWorld) ap.getBean("hw");
		
		System.out.println("İyi Akşamlar " + hello2.getName());
		
		if (hello == hello2) {
			System.out.println("hello ve hello2 birbirine eşittir");
		} else {
			System.out.println("hello ve hello2 birbirine eşit değiller");
		}
		
		System.out.println("hello'nun adresi : " + hello);
		System.out.println("hello2'nin adresi : " + hello2);
		
	}

}
