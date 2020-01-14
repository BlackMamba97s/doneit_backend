package com.sini.doneit;

import com.sini.doneit.model.Category;
import com.sini.doneit.repository.CategoryJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@SpringBootApplication
public class DoneitApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoneitApplication.class, args);
    }

    @Component
    class DemoCommandLineRunner implements CommandLineRunner {

        @Autowired
        private CategoryJpaRepository categoryJpaRepository;

        @Override
        public void run(String... args) throws Exception {
            if(categoryJpaRepository.findAll().size() == 0) {
                categoryJpaRepository.save(new Category("Ripetizioni", 9));
                categoryJpaRepository.save(new Category("Riparazione PC", 3));
                categoryJpaRepository.save(new Category("Faccende domestiche", 3));
            }
        }
    }

}
