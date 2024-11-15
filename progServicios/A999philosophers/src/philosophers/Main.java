package philosophers;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class Main
{
	// defining the number of philosophers  
	static int philosopher = 5;
	// initializing an array of philosophers with the number of philosophers  
	static Philosopher philosophers[] = new Philosopher[philosopher];
	// initializing an array of chopsticks with the number of philosophers  
	static Chopstick chopsticks[] = new Chopstick[philosopher];

	static class Chopstick
	{
		// creating a constructor of the Semaphore class that accepts the number of permits  
		public Semaphore mutex = new Semaphore(1);

		// the method grabs the chopstick  
		void grab()
		{
			try
			{
				// acquires a permit from the semaphore  
				mutex.acquire();
			}
			catch (Exception e)
			{
				e.printStackTrace(System.out);
			}
		}

		// release the chopstick  
		void release()
		{
			// releases an acquire a permit and increases the number of available permits by one  
			mutex.release();
		}

		// checks if the chopstick is free or not  
		boolean isFree()
		{
			// the method returns the current number of permits available in the semaphore  
			// returns true if available permits is greater than 0, else returns false  
			return mutex.availablePermits() > 0;
		}
	} // end of Chopstick class

	static class Philosopher extends Thread
	{
		public int number;
		// represents left chopstick  
		public Chopstick leftchopstick;
		// represents right chopstick  
		public Chopstick rightchopstick;

		// creating a constructor of the Philosopher class  
		Philosopher(int num, Chopstick left, Chopstick right)
		{
			number = num;
			leftchopstick = left;
			rightchopstick = right;
		}

		public void run()
		{
			while (true)
			{
				// if philosopher number is even, grab right chopstick first to avoid deadlock
				if (number % 	 2 == 0)
				{
					rightchopstick.grab();
					System.out.println("Philosopher " + (number + 1) + " grabs right chopstick.");
					leftchopstick.grab();
					System.out.println("Philosopher " + (number + 1) + " grabs left chopstick.");
				}
				else
				{
					leftchopstick.grab();
					System.out.println("Philosopher " + (number + 1) + " grabs left chopstick.");
					rightchopstick.grab();
					System.out.println("Philosopher " + (number + 1) + " grabs right chopstick.");
				}
				// hunger philosopher starts eating   
				eat();
				// releases left and right chopsticks when philosopher is not hungry  
				leftchopstick.release();
				System.out.println("Philosopher " + (number + 1) + " releases left chopstick.");
				rightchopstick.release();
				System.out.println("Philosopher " + (number + 1) + " releases right chopstick.");
			} // end of while loop
		} // end of run() method

		// the method invokes after grabbing both the chopsticks (left and right)  
		void eat()
		{
			try
			{
				// determines the pseudorandom number between 0 to 2000 that represents the sleep time in milliseconds  
				int sleepTime = ThreadLocalRandom.current().nextInt(0, 2000);
				System.out.println("Philosopher " + (number + 1) + " eats for " + sleepTime + "ms");
				// sleeps the thread for a specified time
				Thread.sleep(sleepTime);
			}
			catch (Exception e)
			{
				e.printStackTrace(System.out);
			}
		} // end of eat() method
	}

	public static void main(String args[])
	{
		// loop iterates over chopsticks  
		for (int i = 0; i < philosopher; i++)
			chopsticks[i] = new Chopstick();
		// loop iterates over philosophers  
		for (int i = 0; i < philosopher; i++)
		{
			philosophers[i] = new Philosopher(i, chopsticks[i], chopsticks[(i + 1) % philosopher]);
			// begins the execution of the thread  
			philosophers[i].start();
		} // end of for loop
		while (true)
		{
			try
			{
				// thread sleep for 2000ms  
				Thread.sleep(2000);
				// check for deadlock condition  
				boolean deadlock = true;
				// for each loop iterates over chopsticks  
				for (Chopstick cs : chopsticks)
				{
					// checks if chopstick is free or not   
					if (cs.isFree())
					{
						deadlock = false;
						break;
					} // end of if
				} // end of for loop
				// deadlock occurs if sleep time is 2000ms it means each philosopher is eating  
				if (deadlock)
				{
					Thread.sleep(2000);
					System.out.println("Everyone Eats");
					break;
				} // end of if
			}
			catch (Exception e)
			{
				e.printStackTrace(System.out);
			}
		}
		System.out.println("Exit The Program!");
		System.exit(0);
	}
}
