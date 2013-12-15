package ro.tar.statemachine;

import java.util.List;

import ro.tar.statemachine.state.AbstractState;
import ro.tar.statemachine.transition.AbstractTransition;

public interface StateMachineInterface {

	public void addState(AbstractState state);

	public void addTransition(AbstractTransition transition);
	
	public boolean executeTransition(String transitionName);

	public void executeTransitionWithoutCondition(String transitionName);

	public List<AbstractTransition> getValidTransitions();

	public List<AbstractTransition> getTransition(String transitionName);

	public AbstractState getState(String stateName);
	
	public AbstractState getCurrentState();
	
	public List<AbstractTransition> getAllTransitions();
	
	public List<AbstractState> getAllStates();
	
	public void addCloneTransition(AbstractTransition transition,
			AbstractState fromState, AbstractState toState);
}
