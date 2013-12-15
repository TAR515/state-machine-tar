package ro.tar.statemachine;

import java.util.ArrayList;
import java.util.List;

import ro.tar.statemachine.state.AbstractState;
import ro.tar.statemachine.transition.AbstractTransition;

import com.google.common.base.Preconditions;

public abstract class AbstractStateMachine implements StateMachineInterface {

	protected List<AbstractState> states = new ArrayList<>();

	protected List<AbstractTransition> transitions = new ArrayList<>();

	protected Object originObject = null;

	protected AbstractState currentState = null;

	public AbstractStateMachine() {

	}

	public void init(Object originObject) {
		this.originObject = originObject;
	}

	public AbstractStateMachine(Object originObject) {
		this.originObject = originObject;
	}

	public void addState(AbstractState state) {
		Preconditions.checkArgument(state != null,
				"The given state is null! Please give a valid state.");
		Preconditions.checkArgument(
				!checkIfStateAllreadyExists(state),
				"The name of states must be uniq!");

		if (this.states.isEmpty()) {
			this.currentState = state;
			state.initState(this.originObject);
		}

		this.states.add((AbstractState) state);
	}

	private boolean checkIfStateAllreadyExists(AbstractState state) {
		for (AbstractState current : this.states) {
			if (current.getStateName().equals(state.getStateName())) {
				return true;
			}
		}

		return false;
	}

	public void addTransition(AbstractTransition transition) {
		Preconditions
				.checkArgument(transition != null,
						"The given transition is null! Please give a valid transition.");

		this.transitions.add((AbstractTransition) transition);
		((AbstractTransition) transition).setOrigin(this.originObject);

		if (this.states.contains(transition.getFromState())) {
			int index = this.states.indexOf(transition.getFromState());
			this.states.get(index).addTranstition(transition);
		} else {
			System.out.println("ERROR FROM STATE NOT EXISTS");
		}
	}

	public boolean executeTransition(String transitionName) {
		AbstractTransition transition = null;

		for (AbstractTransition current : getValidTransitions()) {
			if (current.getTransitionName().equals(transitionName)) {
				transition = current;
				break;
			}
		}

		Preconditions
				.checkArgument(
						transition != null,
						"The given transition ("
								+ transitionName
								+ ") is not interpreted for the current state. Current state: "
								+ this.currentState.getStateName());

		if (transition.getCondition().condition(this.originObject)) {

			transition.getFromState().exitState(this.originObject);
			transition.executeTransition();
			transition.getToState().enterState(this.originObject);
			this.currentState = (AbstractState) transition.getToState();
			return true;
		}

		return false;
	}
	
	public void executeTransitionWithoutCondition(String transitionName) {
		
	}

	public List<AbstractTransition> getValidTransitions() {
		List<AbstractTransition> validTransitions = new ArrayList<>();

		for (AbstractTransition current : this.transitions) {
			if (current.getFromState().equals(this.currentState)) {
				validTransitions.add(current);
			}
		}

		return validTransitions;
	}

	public void addCloneTransition(AbstractTransition transition,
			AbstractState fromState, AbstractState toState) {
		Preconditions
				.checkArgument(transition != null,
						"The given transition is null! Plese give a valid transition.");

		AbstractTransition iTransition = ((AbstractTransition) transition)
				.clone();
		iTransition.setFromState((AbstractState) fromState);
		iTransition.setToState((AbstractState) toState);

		this.transitions.add((AbstractTransition) iTransition);
		((AbstractTransition) iTransition).setOrigin(this.originObject);

		if (this.states.contains(iTransition.getFromState())) {
			int index = this.states.indexOf(iTransition.getFromState());
			this.states.get(index).addTranstition(iTransition);
		} else {
			System.out.println("ERROR FROM STATE NOT EXISTS");
		}
	}

	public List<AbstractTransition> getTransition(String transitionName) {
		Preconditions
				.checkArgument(transitionName != null,
						"The given transitionName is null! Please give a valid String.");

		List<AbstractTransition> gotTransitions = new ArrayList<AbstractTransition>();

		for (AbstractTransition current : this.transitions) {
			if (current.getTransitionName().equals(transitionName)) {
				gotTransitions.add(current);
			}
		}

		return gotTransitions;
	}

	public AbstractState getState(String stateName) {
		Preconditions.checkArgument(stateName != null,
				"The given stateName is null! Please give a valid String.");

		for (AbstractState current : this.states) {
			if (current.getStateName().equals(stateName)) {
				return current;
			}
		}

		return null;
	}

	public AbstractState getCurrentState() {
		return this.currentState;
	}

	public List<AbstractTransition> getAllTransitions() {
		return this.transitions;
	}

	public List<AbstractState> getAllStates() {
		return this.states;
	}

	@Override
	public String toString() {
		return "AbstractStateMachine [states=" + states + ", transitions="
				+ transitions + ", originObject=" + originObject
				+ ", currentState=" + currentState + "]";
	}

}
