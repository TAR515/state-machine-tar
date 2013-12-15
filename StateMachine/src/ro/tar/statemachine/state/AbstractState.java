package ro.tar.statemachine.state;

import java.util.ArrayList;
import java.util.List;

import ro.tar.statemachine.transition.AbstractTransition;

public abstract class AbstractState {

	protected String stateName = null;

	protected List<AbstractTransition> transtions = new ArrayList<>();

	public AbstractState(String stateName) {
		this.stateName = stateName;
	}

	/**
	 * Override this method if this is the initial state and u want to
	 * initialize your object.
	 */
	public void initState(Object myObject) {
	}

	public void enterState(Object myObject) {
	}

	public void exitState(Object myObject) {
	}

	public void addTranstition(AbstractTransition transition) {
		this.transtions.add((AbstractTransition) transition);
	}
	
	public List<AbstractTransition> getTransitions() {
		return this.transtions;
	}

	public String getStateName() {
		return stateName;
	}

	@Override
	public String toString() {
		String transitionNames = "[";

		for (AbstractTransition current : this.transtions) {
			transitionNames += current.getTransitionName() + " ";
		}

		transitionNames += "]";

		return "AbstractState [stateName=" + stateName + ", transtions="
				+ transitionNames + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((stateName == null) ? 0 : stateName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractState other = (AbstractState) obj;
		if (stateName == null) {
			if (other.stateName != null)
				return false;
		} else if (!stateName.equals(other.stateName))
			return false;
		return true;
	}

}
