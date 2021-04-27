package cz.fi.muni.pa165.sampleData;

import cz.fi.muni.pa165.service.config.ServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.PostConstruct;
import java.io.IOException;

@Configuration
@Import(ServiceConfiguration.class)
@ComponentScan(basePackageClasses = {SampleDataFacadeImpl.class})
public class SampleDataConfiguration {

    final static Logger log = LoggerFactory.getLogger(SampleDataConfiguration.class);

    @Autowired
    private SampleDataFacade sampleDataFacade;

    @PostConstruct
    public void dataLoading() throws IOException {
        log.debug("dataLoading()");
        sampleDataFacade.loadData();
    }
}