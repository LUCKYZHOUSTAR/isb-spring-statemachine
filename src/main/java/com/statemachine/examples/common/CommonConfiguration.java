package com.statemachine.examples.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.event.*;
import org.springframework.statemachine.transition.TransitionKind;

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
    }

    static class TestEventListener implements ApplicationListener<StateMachineEvent>{

        @Override
        public void onApplicationEvent(StateMachineEvent event) {
            if (event instanceof OnStateEntryEvent) {
                OnStateEntryEvent e = (OnStateEntryEvent)event;
                if(e != null && e.getState()!= null)
                    log.info("Entry state " + e.getState().getId());
            }
            else if (event instanceof OnStateExitEvent) {
                OnStateExitEvent e = (OnStateExitEvent)event;
                if(e != null && e.getState()!= null)
                    log.info("Exit state " + e.getState().getId());
            }
            else if (event instanceof OnTransitionEvent) {
                OnTransitionEvent e = (OnTransitionEvent)event;
                if (e.getTransition().getKind() == TransitionKind.INTERNAL) {
                    if(e.getTransition() != null && e.getTransition().getSource() != null){
                        log.info("Internal transition source=" + e.getTransition().getSource().getId());
                    }
                }
                else if(e.getTransition().getKind() == TransitionKind.EXTERNAL){
                    if(e.getTransition() != null && e.getTransition().getSource() != null){
                        log.info("External transition source=" + e.getTransition().getSource().getId());
                    }
                }
                else if(e.getTransition().getKind() == TransitionKind.LOCAL){
                    if(e.getTransition() != null && e.getTransition().getSource() != null){
                        log.info("Local transition source=" + e.getTransition().getSource().getId());
                    }
                }
                else if(e.getTransition().getKind() == TransitionKind.INITIAL){
                    if(e.getTransition() != null && e.getTransition().getSource() != null){
                        log.info("Initial transition source=" + e.getTransition().getSource().getId());
                    }
                }
            }
        }
    }
}
