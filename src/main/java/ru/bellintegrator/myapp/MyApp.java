package ru.bellintegrator.myapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import ru.bellintegrator.myapp.ModelDAO.BankModelDAO.*;


/**
 * Created by MADmitriev on 23.05.2017.
 */
@ImportResource("spring_mvc_config.xml")
@SpringBootApplication
public class MyApp {
    public static void main(String[] args) {
        SpringApplication.run(MyApp.class, args);
    }

    @Bean
    public BankCardDAO bankCardDAO(){
        return new BankCardDAO();
    }

    @Bean
    public CardRequestDAO cardRequestDAO(){
        return new CardRequestDAO();
    }

    @Bean
    public AccountBaseDAO accountBaseDAO(){
        return new AccountBaseDAO();
    }

    @Bean
    public OwnerBaseDAO ownerBaseDAO(){
        return new OwnerBaseDAO();
    }

    @Bean
    public OwnerIdDAO ownerIdDAO(){
        return new OwnerIdDAO();
    }
}
