package org.yassine;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.yassine.service.impl.GameServiceImp;
import org.yassine.service.impl.PlayerServiceImp;
import org.yassine.view.ConsoleUI;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // Load Spring application context
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // Retrieve the ConsoleUI bean
        ConsoleUI consoleUI = (ConsoleUI) context.getBean("consoleUI");

        // Start the main menu
        consoleUI.showMainMenu();
    }
}