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
		// There should be as many chopsticks as philosophers
		// Initially they are all on the table, so they are initialized to false
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
		// Busy wait if the surrounding chopsticks are already held
		while (chopsticks[piTID % chopsticks.length] || chopsticks[(piTID + 1) % chopsticks.length]) {
			try {
				wait();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}

		}

		// Otherwise pick them both up, notify all other threads
		chopsticks[piTID % chopsticks.length] = true;
		chopsticks[(piTID + 1) % chopsticks.length] = true;
		notifyAll();

	}


	/**
	 * When a given philosopher's done eating, they put the chopstiks/forks down
	 * and let others know they are available.
	 */
	public synchronized void putDown(final int piTID)
	{
		chopsticks[piTID % chopsticks.length] = false;
		chopsticks[(piTID + 1) % chopsticks.length] = false;
		notifyAll();
	}

	/**
	 * Only one philopher at a time is allowed to philosophy
	 * (while she is not eating).
	 */
	public synchronized void requestTalk()
	{
		// Busy wait if any other philosopher is already talking
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
