package cz.fi.muni.pa165.service.config;

import cz.fi.muni.pa165.PersistenceApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.modelmapper.ModelMapper;

@Configuration
@Import(PersistenceApplicationContext.class)
@ComponentScan(basePackages = { "cz.fi.muni.pa165.facade" })
public class BeanMappingConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        return mapper;
    }
}
