package jp.nyatla.mimic.mbedjs.javaapi.driver;

import jp.nyatla.mimic.mbedjs.*;
import jp.nyatla.mimic.mbedjs.javaapi.*;

public class Adafruit_8x8martix extends Adafruit_GFX 
{
	public void sleep_ms(long i_ms) throws MbedJsException
	{
		try {
			this.wait(i_ms);
		} catch (InterruptedException e) {
			throw new MbedJsException(e);
		}
		return;
	}
	public Adafruit_8x8martix(I2C i_i2c , int i_address) 
	{
		super(i_i2c, i_address , 8,8);
		// TODO Auto-generated constructor stub
	}
	public Adafruit_8x8martix(Mcu i_mcu, int i_sda, int i_scl,int i_address) throws MbedJsException 
	{
		super(i_mcu, i_sda, i_scl, i_address,8,8 );
	}
	
	public void drawPixel(int x , int y , short color)
	{
		  if ((y < 0) || (y >= 8)) return;
		  if ((x < 0) || (x >= 8)) return;
		 
		  int tmp;
		 // check rotation, move pixel around if necessary
		  switch (this.getRotation()) {
		  case 1:
		    tmp = x;
		    x=y;y=tmp;
		    x = 8 - x - 1;
		    break;
		  case 2:
		    x = 8 - x - 1;
		    y = 8 - y - 1;
		    break;
		  case 3:
		    tmp = x;
		    x=y;y=tmp;
		    y = 8 - y - 1;
		    break;
		  }
		 
		  // wrap around the x
		  x += 7;
		  x %= 8;
		 
		 
		  if (color==1) {
		    displaybuffer[y] |= 1 << x;
		  } else {
		    displaybuffer[y] &= ~(1 << x);
		  }
	}
	
	public static void main(String args[]) throws MbedJsException   
	{
		Mcu mcu= new Mcu("10.0.0.2");
		Adafruit_8x8martix matrix = new Adafruit_8x8martix(mcu,PinName.p28,PinName.p27,0x70);
		matrix.begin();
	    while(true) {
	        matrix.clear();
	        for (int i = 0; i < 8; i++) {
	          for (int j = 0; j < 8; j++) {
	              matrix.drawPixel(i, j, Adafruit_LEDBackpack.LED_ON);  
	          }
	        }
	        matrix.writeDisplay();  // write the changes we just made to the display
	        matrix.sleep_ms(60000);
	    }
	}
}
