package cz.fi.muni.pa165.service.config;

import cz.fi.muni.pa165.PersistenceApplicationContext;
import cz.fi.muni.pa165.dto.MeterLogDTO;
import cz.fi.muni.pa165.dto.SmartMeterDTO;
import cz.fi.muni.pa165.entity.MeterLog;
import cz.fi.muni.pa165.entity.SmartMeter;
import cz.fi.muni.pa165.service.facade.MeterLogFacadeImpl;
import cz.fi.muni.pa165.service.facade.PortalUserFacadeImpl;
import cz.fi.muni.pa165.service.facade.SmartMeterFacadeImpl;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(PersistenceApplicationContext.class)
@ComponentScan(basePackageClasses={MeterLogFacadeImpl.class, SmartMeterFacadeImpl.class, PortalUserFacadeImpl.class })
public class ServiceConfiguration {


    @Bean
    public Mapper dozer(){
        DozerBeanMapper dozer = new DozerBeanMapper();
        dozer.addMapping(new DozerCustomConfig());
        return dozer;
    }

    /**
     * Custom config for Dozer if needed
     * @author nguyen
     *
     */
    public class DozerCustomConfig extends BeanMappingBuilder {
        @Override
        protected void configure() {
            mapping(MeterLog.class, MeterLogDTO.class);
            mapping(SmartMeter.class, SmartMeterDTO.class);
        }
    }

}