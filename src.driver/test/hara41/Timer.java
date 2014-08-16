package test.hara41;

public class Timer {
	private long start_time;
	private long stop_time;
	public Timer()
	{
	}
	public void start()
	{
		start_time = System.currentTimeMillis();
	}
	public void stop()
	{
		stop_time = System.currentTimeMillis();
		long diff = stop_time - start_time;
		System.out.println (diff);
	}
}
