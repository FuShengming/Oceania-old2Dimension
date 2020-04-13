package app.ass;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import soundsystem.CDPlayer;
import soundsystem.CDPlayerConfig;

public class MyAnnotationApp {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(CDPlayerConfig.class);
        CDPlayer player = ctx.getBean("CDPlayer", CDPlayer.class);
        player.play();
    }
}