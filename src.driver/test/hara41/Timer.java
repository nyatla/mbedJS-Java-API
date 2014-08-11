package test.hara41;

public class Timer {
	private long start_time;
	private long stop_time;
	private boolean timer_started;
	public Timer()
	{
		this.reset();
	}
	
	public void reset()
	{
		this.timer_started = false;
		this.start_time = 0;
		this.stop_time = 0;
	}
	public void dispose()
	{
		
	}
	public void start()
	{
		this.start_time = System.currentTimeMillis();
		this.timer_started = true;
	}
	public void stop()
	{
		this.stop_time = System.currentTimeMillis();
		this.timer_started = false;
	}
	public float read()
	{
		if(this.timer_started)
		{
			this.stop();
		}
		return (this.stop_time - this.start_time)*1000.0f;
	}
	public long read_ms()
	{
		if(this.timer_started)
		{
			this.stop();
		}
		return (this.stop_time - this.start_time);
	}
	
}
