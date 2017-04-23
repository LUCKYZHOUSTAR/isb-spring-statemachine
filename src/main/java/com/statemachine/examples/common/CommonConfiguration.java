package com.statemachine.examples.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.statemachine.event.OnStateEntryEvent;
import org.springframework.statemachine.event.OnStateExitEvent;
import org.springframework.statemachine.event.OnTransitionEvent;
import org.springframework.statemachine.event.StateMachineEvent;
import org.springframework.statemachine.transition.TransitionKind;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by zn.wang on 17/4/23.
 */
@Configuration
public class CommonConfiguration {
    private final static Log log = LogFactory.getLog(CommonConfiguration.class);

    @Configuration
    static class ApplicationConfig{

        @Bean
        public TestEventListener testEventListener() {
            return new TestEventListener();
        }

        @Bean
        public String stateChartModel(String exampleProjectNamePrefix) throws IOException {
            ClassPathResource model = new ClassPathResource(exampleProjectNamePrefix + File.separator + "statechartmodel.txt");
            InputStream inputStream = model.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            String content = scanner.useDelimiter("\\Z").next();
            scanner.close();
            return content;
        }
    }

    static class TestEventListener implements ApplicationListener<StateMachineEvent>{

        @Override
        public void onApplicationEvent(StateMachineEvent event) {
            if (event instanceof OnStateEntryEvent) {
                OnStateEntryEvent e = (OnStateEntryEvent)event;
                log.info("Entry state " + e.getState().getId());
            } else if (event instanceof OnStateExitEvent) {
                OnStateExitEvent e = (OnStateExitEvent)event;
                log.info("Exit state " + e.getState().getId());
            } else if (event instanceof OnTransitionEvent) {
                OnTransitionEvent e = (OnTransitionEvent)event;
                if (e.getTransition().getKind() == TransitionKind.INTERNAL) {
                    log.info("Internal transition source=" + e.getTransition().getSource().getId());
                }
                else if(e.getTransition().getKind() == TransitionKind.EXTERNAL){
                    log.info("External transition source=" + e.getTransition().getSource().getId());
                }
                else if(e.getTransition().getKind() == TransitionKind.LOCAL){
                    log.info("Local transition source=" + e.getTransition().getSource().getId());
                }
                else if(e.getTransition().getKind() == TransitionKind.INITIAL){
                    log.info("Initial transition source=" + e.getTransition().getSource().getId());
                }
            }
        }
    }
}
