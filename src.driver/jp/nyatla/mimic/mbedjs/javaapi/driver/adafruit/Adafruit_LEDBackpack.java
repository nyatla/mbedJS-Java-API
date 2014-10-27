/*************************************************** 
  This is a library for our I2C LED Backpacks
 
  Designed specifically to work with the Adafruit LED Matrix backpacks 
  ----> http://www.adafruit.com/products/
  ----> http://www.adafruit.com/products/
 
  These displays use I2C to communicate, 2 pins are required to 
  interface. There are multiple selectable I2C addresses. For backpacks
  with 2 Address Select pins: 0x70, 0x71, 0x72 or 0x73. For backpacks
  with 3 Address Select pins: 0x70 thru 0x77
 
  Adafruit invests time and resources providing this open source code, 
  please support Adafruit and open-source hardware by purchasing 
  products from Adafruit!
 
  Written by Limor Fried/Ladyada for Adafruit Industries.  
  BSD license, all text above must be included in any redistribution
 ****************************************************/
 /*
 *  Modified by Luiz Hespanha (http://www.d3.do) 8/16/2013 for use in LPC1768
 */
/**
 * ported by hara.shinichi@gmail.com
 */
package jp.nyatla.mimic.mbedjs.javaapi.driver.adafruit;

import jp.nyatla.mimic.mbedjs.MbedJsException;
import jp.nyatla.mimic.mbedjs.javaapi.I2C;
import jp.nyatla.mimic.mbedjs.javaapi.Mcu;
import jp.nyatla.mimic.mbedjs.javaapi.driver.utils.DriverBaseClass;

public abstract class Adafruit_LEDBackpack extends DriverBaseClass{
	
	public final static int LED_ON =1;
	public final static int LED_OFF =0;
	 
	public final static int LED_RED =1;
	public final static int LED_YELLOW =2;
	public final static int LED_GREEN =3;
	 
	private final static int HT16K33_BLINK_CMD =0x80;
	private final static int HT16K33_BLINK_DISPLAYON =0x01;
	private final static int HT16K33_BLINK_OFF =0;
	private final static int HT16K33_BLINK_2HZ  =1;
	private final static int HT16K33_BLINK_1HZ  =2;
	private final static int HT16K33_BLINK_HALFHZ  =3;
	private final static int HT16K33_CMD_BRIGHTNESS =0x0E;
	
	private final I2C _i2c;
	private int i2c_addr;
	/** I2Cを内部生成したか*/
	private final boolean _is_attached;
	
	protected final short[] displaybuffer= new short[8]; // 8 
	/**
	 * 既存のI2Cに追加する場合
	 * @param 
	 * i_i2c I2Cインスタンス
	 * @param 
	 * i_address I2Cアドレス
	 */	
	public Adafruit_LEDBackpack(I2C i_i2c,int i_address) 
	{
		//I2Cの設定
		this._is_attached=false;
		this._i2c=i_i2c;
		this.i2c_addr=i_address;
		
		this.i2c_addr = i_address & 0xff;
		
	}
	/**
	 * Mcuから直接生成する場合
	 * @param 
	 * i_mcu MCUインスタンス
	 * @param 
	 * i_sda SDAピン
	 * @param 
	 * i_scl SCLピン
	 * @param 
	 * i_address I2Cアドレス
	 * @throws 
	 * MbedJsException MbedJS例外
	 */
	public Adafruit_LEDBackpack(Mcu i_mcu, int i_sda, int i_scl,int i_address)throws MbedJsException
	{
		this._is_attached=true;
		this._i2c=new I2C(i_mcu, i_sda, i_scl);
		this.i2c_addr=i_address;
		this._i2c.frequency(10000);
		
		this.i2c_addr = i_address;
	}
	public void dispose() throws MbedJsException{
		if(this._is_attached){
			this._i2c.dispose();
		}
	}
	/**
	 * 初期化
	 * @throws 
	 * MbedJsException MbedJS例外
	 */
	public void begin() throws MbedJsException
	{		 
		  byte[] foo= new byte[]{0x21};
		 
		  this._i2c.write(this.i2c_addr, foo, false);  // turn on oscillator	 
		  this.blinkRate(HT16K33_BLINK_OFF);
		  this.setBrightness(15); // max brightness
	}
	/**
	 * ブライトネス(明るさ)の設定
	 * @param 
	 * i_bright 明るさ
	 * @throws 
	 * MbedJsException MbedJS例外
	 */
	public void setBrightness(int i_bright) throws MbedJsException
	{
		  if (i_bright > 15){
			  i_bright = 15;
		  }
		  byte c = (byte) (0xE0 | i_bright);
		  byte[] foo= new byte[1];
		  foo[0] = c;
		  this._i2c.write(this.i2c_addr, foo, false); 
		
	}
	/**
	 * 点滅間隔の設定
	 * @param 
	 * i_rate 点滅間隔
	 * @throws 
	 * MbedJsException MbedJS例外
	 */
	public void blinkRate(int i_rate) throws MbedJsException
	{
		  if (i_rate > 3){
			  i_rate = 0; // turn off if not sure
		  }
		  int c = (HT16K33_BLINK_CMD | HT16K33_BLINK_DISPLAYON | (i_rate << 1));
		  byte[] foo = new byte[1];
		  foo[0] = (byte) c;
		  this._i2c.write(this.i2c_addr, foo, false);
	}
	/**
	 * ディスプレイに表示する
	 * @throws 
	 * MbedJsException MbedJS例外
	 */
	public void writeDisplay() throws MbedJsException
	{
		  byte[] foo=new byte[17];
		  foo[0] = 0x00;
		  int j = 0;
		  for (short i=1; i<=16; i+=2) {
			foo[i] = (byte)(this.displaybuffer[j] & 0x0FF);
		    foo[i+1] = (byte)((this.displaybuffer[j] >>> 8)& 0x0FF);
		    j++;
		  }
		  this._i2c.write(this.i2c_addr, foo, false);
	}
	/**
	 * 表示のクリア
	 */
	public void clear()
	{
		for (short i=0; i<8; i++) {
			this.displaybuffer[i] = 0;
		}
	}
}
