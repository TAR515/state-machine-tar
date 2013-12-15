package ro.tar.statemachine.transition;

import ro.tar.statemachine.condition.ConditionInterface;
import ro.tar.statemachine.state.AbstractState;

public abstract class AbstractTransition implements Cloneable {

	protected String transactionName = null;

	protected Object originObject = null;

	protected AbstractState fromState = null;

	protected AbstractState toState = null;

	protected ConditionInterface condition = null;

	public AbstractTransition(String transactionName, AbstractState fromState,
			AbstractState toState) {
		this.transactionName = transactionName;
		this.fromState = (AbstractState) fromState;
		this.toState = (AbstractState) toState;
		this.condition = new ConditionInterface() {
			@Override
			public boolean condition(Object myObject) {
				return true;
			}
		};
	}

	public AbstractTransition(String transactionName, AbstractState fromState,
			AbstractState toState, ConditionInterface condition) {
		this.transactionName = transactionName;
		this.fromState = (AbstractState) fromState;
		this.toState = (AbstractState) toState;
		this.condition = condition;
	}

	public AbstractTransition(AbstractTransition transaction) {
		this.transactionName = transaction.transactionName;
		this.fromState = transaction.fromState;
		this.toState = transaction.toState;
		this.condition = transaction.condition;
		this.originObject = transaction.originObject;
	}
	
	public abstract void executeTransition();

	public String getTransitionName() {
		return this.transactionName;
	}

	public void setOrigin(Object originObject) {
		this.originObject = originObject;
	}

	public void setCondition(ConditionInterface condition) {
		this.condition = condition;
	}

	public AbstractState getFromState() {
		return this.fromState;
	}

	public AbstractState getToState() {
		return this.toState;
	}

	public void setFromState(AbstractState fromState) {
		this.fromState = fromState;
	}

	public void setToState(AbstractState toState) {
		this.toState = toState;
	}

	public ConditionInterface getCondition() {
		return condition;
	}

	public AbstractTransition clone(AbstractState fromState,
			AbstractState toState) {
		final AbstractTransition clone = this.clone();

		clone.fromState = fromState;
		clone.toState = toState;
		return clone;
	}

	public AbstractTransition clone(AbstractState fromState,
			AbstractState toState, ConditionInterface condition) {
		final AbstractTransition clone = this.clone();

		clone.fromState = fromState;
		clone.toState = toState;
		clone.condition = condition;

		return clone;
	}

	@Override
	public AbstractTransition clone() {
		final AbstractTransition clone;
		try {
			clone = (AbstractTransition) super.clone();
		} catch (CloneNotSupportedException ex) {
			throw new RuntimeException("superclass messed up");
		}

		return clone;
	}

	@Override
	public String toString() {
		return "AbstractTransition [transactionName=" + transactionName
				+ ", fromState=" + fromState.getStateName() + ", toState="
				+ toState.getStateName() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((originObject == null) ? 0 : originObject.hashCode());
		result = prime * result
				+ ((transactionName == null) ? 0 : transactionName.hashCode());
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
		AbstractTransition other = (AbstractTransition) obj;
		if (originObject == null) {
			if (other.originObject != null)
				return false;
		} else if (!originObject.equals(other.originObject))
			return false;
		if (transactionName == null) {
			if (other.transactionName != null)
				return false;
		} else if (!transactionName.equals(other.transactionName))
			return false;
		return true;
	}

}
