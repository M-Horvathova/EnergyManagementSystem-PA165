package cz.fi.muni.pa165.service.config;

import cz.fi.muni.pa165.PersistenceApplicationContext;
import cz.fi.muni.pa165.service.mappers.HouseToDTOMapper;
import cz.fi.muni.pa165.service.mappers.MeterLogMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(PersistenceApplicationContext.class)
@ComponentScan(basePackages = { "cz.fi.muni.pa165.facade" })
public class MapStructMappingConfiguration {

    @Bean
    public HouseToDTOMapper houseToDTOMapper() {
        return Mappers.getMapper(HouseToDTOMapper.class);
    }

    @Bean
    public MeterLogMapper meterLogMapper(){return Mappers.getMapper(MeterLogMapper.class);}
}
