/**
 * Class Monitor
 * To synchronize dining philosophers.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Monitor
{
	/*
	 * ------------
	 * Data members
	 * ------------
	 */

	 private boolean someoneTalking;
	 private boolean[] chopsticks;



	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers)
	{
		someoneTalking = false;
		chopsticks = new boolean[piNumberOfPhilosophers]; // Initialize chopsticks array
		for (int i = 0; i < piNumberOfPhilosophers; i++) {
			chopsticks[i] = false;
		}
	}

	/*
	 * -------------------------------
	 * User-defined monitor procedures
	 * -------------------------------
	 */

	/**
	 * Grants request (returns) to eat when both chopsticks/forks are available.
	 * Else forces the philosopher to wait()
	 */


	public synchronized void pickUp(final int piTID)
	{
		while (!chopsticks[piTID] && !chopsticks[(piTID + 1) % chopsticks.length]) {
			try {
				wait();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}

		}
		chopsticks[piTID] = true;
		chopsticks[(piTID + 1) % chopsticks.length] = true;
		notifyAll();

	}


	/**
	 * When a given philosopher's done eating, they put the chopstiks/forks down
	 * and let others know they are available.
	 */
	public synchronized void putDown(final int piTID)
	{
		chopsticks[piTID] = false;
		chopsticks[(piTID + 1) % chopsticks.length] = false;
		notifyAll();
	}

	/**
	 * Only one philopher at a time is allowed to philosophy
	 * (while she is not eating).
	 */
	public synchronized void requestTalk()
	{

		while (someoneTalking) {
			try {
				wait();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		someoneTalking = true;
		notifyAll();
	}

	/**
	 * When one philosopher is done talking stuff, others
	 * can feel free to start talking.
	 */
	public synchronized void endTalk()
	{
		someoneTalking = false;
		notifyAll();
	}

	/**
	 * Grants request (returns) to eat when both chopsticks/forks are available.
	 * Else forces the philosopher to wait()
	 */

}

// EOF
