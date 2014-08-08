package jp.nyatla.mimic.mbedjs.javaapi.driver;

import jp.nyatla.mimic.mbedjs.javaapi.I2C;

public class Adafruit_LEDBackpack {
	public Adafruit_LEDBackpack(I2C i2c)
	{
		
	}
	
	public void begin(byte _addr)
	{
		
	}
	public void setBrightness(byte b);
	public void blinkRate(byte b);
	public void writeDisplay();
	public void clear();
	 
	public byte[] displaybuffer; // 8 
	 
	public void init(byte a);
	 
	protected I2C _i2c;
	  
	private byte i2c_addr;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
