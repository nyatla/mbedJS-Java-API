package jp.nyatla.mimic.mbedjs.javaapi.driver;

import jp.nyatla.mimic.mbedjs.MbedJsException;
import jp.nyatla.mimic.mbedjs.javaapi.I2C;
import jp.nyatla.mimic.mbedjs.javaapi.Mcu;

public class Adafruit_LEDBackpack {
	
	private final static byte LED_ON =1;
	private final static byte LED_OFF =0;
	 
	private final static byte LED_RED =1;
	private final static byte LED_YELLOW =2;
	private final static byte LED_GREEN =3;
	 
	 
	 
	private final static short HT16K33_BLINK_CMD =0x80;
	private final static short HT16K33_BLINK_DISPLAYON =0x01;
	private final static byte HT16K33_BLINK_OFF =0;
	private final static byte HT16K33_BLINK_2HZ  =1;
	private final static byte HT16K33_BLINK_1HZ  =2;
	private final static byte HT16K33_BLINK_HALFHZ  =3;
	 
	private final static short HT16K33_CMD_BRIGHTNESS =0x0E;
	
	private final I2C _i2c;
	private final int i2c_addr;
	/** I2Cを内部生成したか*/
	private final boolean _is_attached;
	
	public byte[] displaybuffer; // 8 
	
	public Adafruit_LEDBackpack(I2C i_i2c,int i_address)
	{
		this._is_attached=false;
		this._i2c=i_i2c;
		this.i2c_addr=i_address;
	}

	public Adafruit_LEDBackpack(Mcu i_mcu, int sda, int scl,int i_address) throws MbedJsException
	{
		this._is_attached=true;
		this._i2c=new I2C(i_mcu, sda, scl);
		this.i2c_addr=i_address;
		this._i2c.frequency(1000);
	}
	public void begin(byte i_addr)
	{
		  this.i2c_addr = i_addr << 1;
		 
		  byte[] foo= new byte[1];
		  foo[0] = 0x21;
		 
		  this._i2c.write(this.i2c_addr, foo, true);  // turn on oscillator
		 
		  this.blinkRate(this.HT16K33_BLINK_OFF);
		  
		  this.setBrightness(15); // max brightness
	}
	public void setBrightness(byte i_bright) throws MbedJsException
	{
		  if (i_bright > 15) i_bright = 15;
		  byte c = (byte) (0xE0 | i_bright);
		  byte[] foo= new byte[1];
		  foo[0] = c;
		  this._i2c.write(this.i2c_addr, foo, false); 
		
	}
	public void blinkRate(byte i_rate) throws MbedJsException
	{
		  if (i_rate > 3) i_rate = 0; // turn off if not sure
		  short c = (short) (this.HT16K33_BLINK_CMD | this.HT16K33_BLINK_DISPLAYON | (i_rate << 1));
		  byte[] foo = new byte[1];
		  foo[0] = (byte) c;
		  this._i2c.write(this.i2c_addr, foo, false);
	}
	public void writeDisplay() throws MbedJsException
	{
		  byte[] foo=new byte[17];
		  foo[0] = 0x00;
		  int j = 0;
		  for (short i=1; i<=16; i+=2) {
		    int x = this.displaybuffer[j] & 0xFF;
		    foo[i] = (byte) x;
		    int x2 = this.displaybuffer[j] >> 8;
		    foo[i+1] = (byte) x2;
		    j++;
		  }
		  this._i2c.write(this.i2c_addr, foo, false);
	}
	public void clear()
	{
		for (short i=0; i<8; i++) {
			this.displaybuffer[i] = 0;
		}
	}
	public void init(byte a)
	{
		
	}
	 
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
