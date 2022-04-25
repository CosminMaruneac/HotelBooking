package ro.usv.booking.configurations.jpa;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ro.usv.booking.configurations.jpa.repo.CustomJpaRepository;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = {"ro.usv.booking"}, repositoryBaseClass = CustomJpaRepository.class)
public class JpaConfig {
}