/**
 * Original source code is here.
 * http://mbed.org/users/luizhespanha/code/Adafruit_LEDBackpack/
 */
package jp.nyatla.mimic.mbedjs.javaapi.driver.adafruit;

import jp.nyatla.mimic.mbedjs.*;
import jp.nyatla.mimic.mbedjs.javaapi.*;

public class Adafruit_8x8matrix extends Adafruit_GFX 
{

	public Adafruit_8x8matrix(I2C i_i2c , int i_address) 
	{
		super(i_i2c, i_address , 8,8);
	}
	public Adafruit_8x8matrix(Mcu i_mcu, int i_sda, int i_scl,int i_address) throws MbedJsException 
	{
		super(i_mcu, i_sda, i_scl, i_address,8,8 );
	}
	
	public void drawPixel(int i_x , int i_y , int i_color)
	{
		  if ((i_y < 0) || (i_y >= 8)) return;
		  if ((i_x < 0) || (i_x >= 8)) return;
		 
		  int tmp;
		 // check rotation, move pixel around if necessary
		  switch (this.getRotation()) {
		  case 1:
			  // swap(x,y);
		    tmp = i_x;
		    i_x=i_y;i_y=tmp;
		    i_x = 8 - i_x - 1;
		    break;
		  case 2:
		    i_x = 8 - i_x - 1;
		    i_y = 8 - i_y - 1;
		    break;
		  case 3:
			  //swap(x,y);
		    tmp = i_x;
		    i_x=i_y;i_y=tmp;
		    i_y = 8 - i_y - 1;
		    break;
		  }
		 
		  // wrap around the x
		  i_x += 7;
		  i_x %= 8;
		 
		 
		  if (i_color==1) {
		    displaybuffer[i_y] |= 1 << i_x;
		  } else {
		    displaybuffer[i_y] &= ~(1 << i_x);
		  }
	}
	
	public static void main(String args[]) throws MbedJsException   
	{
		Mcu mcu= new Mcu("10.0.0.2");
		Adafruit_8x8matrix matrix = new Adafruit_8x8matrix(mcu,PinName.p28,PinName.p27,(0x70<<1));
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
