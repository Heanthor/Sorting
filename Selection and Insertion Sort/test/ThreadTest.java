package test;
/**
 * Test class for threads and notifying.
 * @author Reed
 *
 */
public class ThreadTest implements Runnable {
	boolean ready = false;
	static Object o = new Object();
	String value = "";

	public static void main(String[] args) {
		ThreadTest t = new ThreadTest();

		Thread th = new Thread(t);
		th.start();
		//Delay producer
		for (int i = 0; i < 1000000; i++) {
			
		}
		t.produce(t.value);
	}

	private void produce(String s) {
		synchronized(o) {
			while (ready) {
				try {
					o.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			value += s;
			ready = true;
			o.notifyAll();
		}
	}

	@Override
	public void run() {
		System.out.println(consume());
	}

	private String consume() {
		synchronized(o) {
			while (!ready) {
				System.out.println("wait");
				try {
					o.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("Consumed");
			String s = value;
			ready = false;
			o.notifyAll();
			return s;
		}
	}
}
