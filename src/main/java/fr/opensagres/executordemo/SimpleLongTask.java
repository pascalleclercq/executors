package fr.opensagres.executordemo;

public class SimpleLongTask implements LongTask {

	public void doALongTask(Thread caller) throws Exception {
		simulateBottleNeck();
	}

	private volatile int counter = 0;

	private synchronized void simulateBottleNeck() throws Exception {
		Thread.sleep(20);
		simulateRandomCrash();
	}

	private void simulateRandomCrash() {
		counter++;
		if (counter % 1000 == 0)
			throw new NullPointerException();
	}
}
