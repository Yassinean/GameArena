import View.JoueurUI;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // Retrieve the JoueurUI bean
        JoueurUI joueurUI = (JoueurUI) context.getBean("JoueurUI", JoueurUI.class);

        // Start the menu interaction
        joueurUI.showMenu();
    }
}