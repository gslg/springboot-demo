package com.example.springboot.mybatisdemo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

/**
 * Created by liuguo on 2017/8/1.
 */
@SpringBootApplication
// same as @Configuration @EnableAutoConfiguration @ComponentScan
public class SpringBootMybatisApp {

    /*@RequestMapping("/")
    public String home(){
        return "Hello World!";
    }
*/
    public static void main(String[] args) {
        SpringApplication.run(SpringBootMybatisApp.class,args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx){
       /* return new CommandLineRunner() {
            @Override
            public void run(String... strings) throws Exception {

            }
        };*/
        return  args -> {
            System.out.println("看看Spring Boot生成的bean:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }
        };
    }
}
