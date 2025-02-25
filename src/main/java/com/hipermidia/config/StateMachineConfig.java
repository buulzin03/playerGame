package com.hipermidia.config;

import java.util.EnumSet;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import com.hipermidia.model.states.GameEvents;
import com.hipermidia.model.states.GameStates;

@Configuration
@EnableStateMachineFactory
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<GameStates, GameEvents>{
    

    @Override
    public void configure(StateMachineStateConfigurer<GameStates, GameEvents> states) throws Exception {
        states
            .withStates()
            .initial(GameStates.INIT)
            .states(EnumSet.allOf(GameStates.class))
            .end(GameStates.DEFEATED)
            .end(GameStates.ENDING);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<GameStates, GameEvents> transitions) throws Exception {
        transitions
            .withExternal().source(null).target(null).and()
    }
}
