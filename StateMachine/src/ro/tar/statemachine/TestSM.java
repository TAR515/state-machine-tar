package ro.tar.statemachine;

import ro.tar.statemachine.state.AbstractState;
import ro.tar.statemachine.transition.AbstractTransition;

public class TestSM extends AbstractStateMachine {

	private String k = "SS";

	public TestSM() {
		init(this);

		final AbstractState hungry = new AbstractState("HUNGRY") {

			@Override
			public void initState(Object myObject) {
				TestSM o = (TestSM) myObject;
				o.setK("I am hungry!");
			}

		};

		final AbstractState notHungry = new AbstractState("NOT_HUNGRY"){};
		
		final AbstractState dead = new AbstractState("DEAD"){};


		addState(hungry);
		addState(notHungry);
		addState(dead);

		AbstractTransition eat = new AbstractTransition("EAT", hungry, notHungry) {
			@Override
			public void executeTransition() {
				TestSM o = (TestSM) originObject;
				
				if (this.fromState.equals(hungry)) {
					o.setK("I am not hungry!");
				} else if (this.fromState.equals(notHungry)) {
					o.setK("DEAD");
				}
			}
		};

		AbstractTransition timeEllapsed = new AbstractTransition(
				"TIME_ELLAPSED", notHungry, hungry) {

			@Override
			public void executeTransition() {
				TestSM o = (TestSM) originObject;
				
				if (this.fromState.equals(hungry)) {
					o.setK("DEAD");
				} else if (this.fromState.equals(notHungry)) {
					o.setK("I'm hungry again!");
				}
			}
		};
		
		addTransition(eat);
		addTransition(timeEllapsed);
		addTransition(eat.clone(notHungry, dead));
		addTransition(timeEllapsed.clone(hungry, dead));
		
		System.out.println(getAllStates());

		System.out.println(k);
		executeTransition("EAT");
		System.out.println(k);
		executeTransition("TIME_ELLAPSED");
		System.out.println(k);
		executeTransition("TIME_ELLAPSED");
		System.out.println(k);


	}

	public static void main(String[] args) {
		new TestSM();
	}

	public String getK() {
		return k;
	}

	public void setK(String k) {
		this.k = k;
	}

}
