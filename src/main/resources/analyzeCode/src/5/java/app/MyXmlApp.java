package app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import soundsystem.CDPlayer;

public class MyXmlApp {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("META-INF/spring/soundsystem.xml");
        CDPlayer player = ctx.getBean("CDPlayer", CDPlayer.class);
        player.play();
    }
}