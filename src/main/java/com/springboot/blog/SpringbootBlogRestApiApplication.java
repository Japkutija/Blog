package com.springboot.blog;

import com.springboot.blog.entity.Role;
import com.springboot.blog.repository.RoleRepository;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
@SpringBootApplication
@OpenAPIDefinition(
        info = @Info
                (
                        title = "Spring Boot Blog REST API",
                        version = "1.0",
                        description = "Spring Boot Blog REST API",
                        contact = @Contact(
                                name = "Jasmin",
                                email = "jasko.derv@gmail.com",
                                url = "https://wwww.mywebsite.com"
                        ),
                        license = @License(
                                name = "Apache 2.0",
                                url = "http://www.mywebsite.com/license"
                        )
                ),
        externalDocs = @ExternalDocumentation(
                description = "Spring Boot Blog REST API Documentation",
                url = "https://github.com/Japkutija/Blog"
        )

)
public class SpringbootBlogRestApiApplication implements CommandLineRunner {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootBlogRestApiApplication.class, args);
    }

    private final RoleRepository roleRepository;
    
    @Override
    public void run(String... args) throws Exception {
        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");
        roleRepository.save(adminRole);

        Role userRole = new Role();
        userRole.setName("ROLE_USER");
        roleRepository.save(userRole);
    }
}
