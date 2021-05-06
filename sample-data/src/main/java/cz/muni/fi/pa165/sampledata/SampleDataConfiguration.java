package cz.muni.fi.pa165.sampledata;

import cz.fi.muni.pa165.service.config.ServiceConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;
import java.io.IOException;


@Configuration
@Import(ServiceConfiguration.class)
@ComponentScan(basePackageClasses = {SampleDataLoadingFacadeImpl.class})
public class SampleDataConfiguration {

    final static Logger log = LoggerFactory.getLogger(SampleDataConfiguration.class);

    final
    SampleDataLoadingFacade sampleDataLoadingFacade;

    @Autowired
    public SampleDataConfiguration(SampleDataLoadingFacade sampleDataLoadingFacade) {
        this.sampleDataLoadingFacade = sampleDataLoadingFacade;
    }

    @PostConstruct
    public void dataLoading() throws IOException {
        log.debug("dataLoading()");
        sampleDataLoadingFacade.loadData();
    }
}