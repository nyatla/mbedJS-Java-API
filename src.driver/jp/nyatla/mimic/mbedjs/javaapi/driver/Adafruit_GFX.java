/**
**********************************
This is a our graphics core library, for all our displays. 
We'll be adapting all the
existing libaries to use this core to make updating, support 
and upgrading easier!
 
Adafruit invests time and resources providing this open source code, 
please support Adafruit and open-source hardware by purchasing 
products from Adafruit!
 
Written by Limor Fried/Ladyada  for Adafruit Industries.  
BSD license, check license.txt for more information
All text above must be included in any redistribution
****************************************
 
 
   Modified by Neal Horman 7/14/2012 for use in LPC1768
 
*/

package jp.nyatla.mimic.mbedjs.javaapi.driver;

import jp.nyatla.mimic.mbedjs.*;
import jp.nyatla.mimic.mbedjs.javaapi.*;

public class Adafruit_GFX extends Adafruit_LEDBackpack
{
	private final static int  font[] = {
	    0x00, 0x00, 0x00, 0x00, 0x00,   
	    0x3E, 0x5B, 0x4F, 0x5B, 0x3E,     
	    0x3E, 0x6B, 0x4F, 0x6B, 0x3E,     
	    0x1C, 0x3E, 0x7C, 0x3E, 0x1C, 
	    0x18, 0x3C, 0x7E, 0x3C, 0x18, 
	    0x1C, 0x57, 0x7D, 0x57, 0x1C, 
	    0x1C, 0x5E, 0x7F, 0x5E, 0x1C, 
	    0x00, 0x18, 0x3C, 0x18, 0x00, 
	    0xFF, 0xE7, 0xC3, 0xE7, 0xFF, 
	    0x00, 0x18, 0x24, 0x18, 0x00, 
	    0xFF, 0xE7, 0xDB, 0xE7, 0xFF, 
	    0x30, 0x48, 0x3A, 0x06, 0x0E, 
	    0x26, 0x29, 0x79, 0x29, 0x26, 
	    0x40, 0x7F, 0x05, 0x05, 0x07, 
	    0x40, 0x7F, 0x05, 0x25, 0x3F, 
	    0x5A, 0x3C, 0xE7, 0x3C, 0x5A, 
	    0x7F, 0x3E, 0x1C, 0x1C, 0x08, 
	    0x08, 0x1C, 0x1C, 0x3E, 0x7F, 
	    0x14, 0x22, 0x7F, 0x22, 0x14, 
	    0x5F, 0x5F, 0x00, 0x5F, 0x5F, 
	    0x06, 0x09, 0x7F, 0x01, 0x7F, 
	    0x00, 0x66, 0x89, 0x95, 0x6A, 
	    0x60, 0x60, 0x60, 0x60, 0x60, 
	    0x94, 0xA2, 0xFF, 0xA2, 0x94, 
	    0x08, 0x04, 0x7E, 0x04, 0x08, 
	    0x10, 0x20, 0x7E, 0x20, 0x10, 
	    0x08, 0x08, 0x2A, 0x1C, 0x08, 
	    0x08, 0x1C, 0x2A, 0x08, 0x08, 
	    0x1E, 0x10, 0x10, 0x10, 0x10, 
	    0x0C, 0x1E, 0x0C, 0x1E, 0x0C, 
	    0x30, 0x38, 0x3E, 0x38, 0x30, 
	    0x06, 0x0E, 0x3E, 0x0E, 0x06, 
	    0x00, 0x00, 0x00, 0x00, 0x00, 
	    0x00, 0x00, 0x5F, 0x00, 0x00, 
	    0x00, 0x07, 0x00, 0x07, 0x00, 
	    0x14, 0x7F, 0x14, 0x7F, 0x14, 
	    0x24, 0x2A, 0x7F, 0x2A, 0x12, 
	    0x23, 0x13, 0x08, 0x64, 0x62, 
	    0x36, 0x49, 0x56, 0x20, 0x50, 
	    0x00, 0x08, 0x07, 0x03, 0x00, 
	    0x00, 0x1C, 0x22, 0x41, 0x00, 
	    0x00, 0x41, 0x22, 0x1C, 0x00, 
	    0x2A, 0x1C, 0x7F, 0x1C, 0x2A, 
	    0x08, 0x08, 0x3E, 0x08, 0x08, 
	    0x00, 0x80, 0x70, 0x30, 0x00, 
	    0x08, 0x08, 0x08, 0x08, 0x08, 
	    0x00, 0x00, 0x60, 0x60, 0x00, 
	    0x20, 0x10, 0x08, 0x04, 0x02, 
	    0x3E, 0x51, 0x49, 0x45, 0x3E, 
	    0x00, 0x42, 0x7F, 0x40, 0x00, 
	    0x72, 0x49, 0x49, 0x49, 0x46, 
	    0x21, 0x41, 0x49, 0x4D, 0x33, 
	    0x18, 0x14, 0x12, 0x7F, 0x10, 
	    0x27, 0x45, 0x45, 0x45, 0x39, 
	    0x3C, 0x4A, 0x49, 0x49, 0x31, 
	    0x41, 0x21, 0x11, 0x09, 0x07, 
	    0x36, 0x49, 0x49, 0x49, 0x36, 
	    0x46, 0x49, 0x49, 0x29, 0x1E, 
	    0x00, 0x00, 0x14, 0x00, 0x00, 
	    0x00, 0x40, 0x34, 0x00, 0x00, 
	    0x00, 0x08, 0x14, 0x22, 0x41, 
	    0x14, 0x14, 0x14, 0x14, 0x14, 
	    0x00, 0x41, 0x22, 0x14, 0x08, 
	    0x02, 0x01, 0x59, 0x09, 0x06, 
	    0x3E, 0x41, 0x5D, 0x59, 0x4E, 
	    0x7C, 0x12, 0x11, 0x12, 0x7C, 
	    0x7F, 0x49, 0x49, 0x49, 0x36, 
	    0x3E, 0x41, 0x41, 0x41, 0x22, 
	    0x7F, 0x41, 0x41, 0x41, 0x3E, 
	    0x7F, 0x49, 0x49, 0x49, 0x41, 
	    0x7F, 0x09, 0x09, 0x09, 0x01, 
	    0x3E, 0x41, 0x41, 0x51, 0x73, 
	    0x7F, 0x08, 0x08, 0x08, 0x7F, 
	    0x00, 0x41, 0x7F, 0x41, 0x00, 
	    0x20, 0x40, 0x41, 0x3F, 0x01, 
	    0x7F, 0x08, 0x14, 0x22, 0x41, 
	    0x7F, 0x40, 0x40, 0x40, 0x40, 
	    0x7F, 0x02, 0x1C, 0x02, 0x7F, 
	    0x7F, 0x04, 0x08, 0x10, 0x7F, 
	    0x3E, 0x41, 0x41, 0x41, 0x3E, 
	    0x7F, 0x09, 0x09, 0x09, 0x06, 
	    0x3E, 0x41, 0x51, 0x21, 0x5E, 
	    0x7F, 0x09, 0x19, 0x29, 0x46, 
	    0x26, 0x49, 0x49, 0x49, 0x32, 
	    0x03, 0x01, 0x7F, 0x01, 0x03, 
	    0x3F, 0x40, 0x40, 0x40, 0x3F, 
	    0x1F, 0x20, 0x40, 0x20, 0x1F, 
	    0x3F, 0x40, 0x38, 0x40, 0x3F, 
	    0x63, 0x14, 0x08, 0x14, 0x63, 
	    0x03, 0x04, 0x78, 0x04, 0x03, 
	    0x61, 0x59, 0x49, 0x4D, 0x43, 
	    0x00, 0x7F, 0x41, 0x41, 0x41, 
	    0x02, 0x04, 0x08, 0x10, 0x20, 
	    0x00, 0x41, 0x41, 0x41, 0x7F, 
	    0x04, 0x02, 0x01, 0x02, 0x04, 
	    0x40, 0x40, 0x40, 0x40, 0x40, 
	    0x00, 0x03, 0x07, 0x08, 0x00, 
	    0x20, 0x54, 0x54, 0x78, 0x40, 
	    0x7F, 0x28, 0x44, 0x44, 0x38, 
	    0x38, 0x44, 0x44, 0x44, 0x28, 
	    0x38, 0x44, 0x44, 0x28, 0x7F, 
	    0x38, 0x54, 0x54, 0x54, 0x18, 
	    0x00, 0x08, 0x7E, 0x09, 0x02, 
	    0x18, 0xA4, 0xA4, 0x9C, 0x78, 
	    0x7F, 0x08, 0x04, 0x04, 0x78, 
	    0x00, 0x44, 0x7D, 0x40, 0x00, 
	    0x20, 0x40, 0x40, 0x3D, 0x00, 
	    0x7F, 0x10, 0x28, 0x44, 0x00, 
	    0x00, 0x41, 0x7F, 0x40, 0x00, 
	    0x7C, 0x04, 0x78, 0x04, 0x78, 
	    0x7C, 0x08, 0x04, 0x04, 0x78, 
	    0x38, 0x44, 0x44, 0x44, 0x38, 
	    0xFC, 0x18, 0x24, 0x24, 0x18, 
	    0x18, 0x24, 0x24, 0x18, 0xFC, 
	    0x7C, 0x08, 0x04, 0x04, 0x08, 
	    0x48, 0x54, 0x54, 0x54, 0x24, 
	    0x04, 0x04, 0x3F, 0x44, 0x24, 
	    0x3C, 0x40, 0x40, 0x20, 0x7C, 
	    0x1C, 0x20, 0x40, 0x20, 0x1C, 
	    0x3C, 0x40, 0x30, 0x40, 0x3C, 
	    0x44, 0x28, 0x10, 0x28, 0x44, 
	    0x4C, 0x90, 0x90, 0x90, 0x7C, 
	    0x44, 0x64, 0x54, 0x4C, 0x44, 
	    0x00, 0x08, 0x36, 0x41, 0x00, 
	    0x00, 0x00, 0x77, 0x00, 0x00, 
	    0x00, 0x41, 0x36, 0x08, 0x00, 
	    0x02, 0x01, 0x02, 0x04, 0x02, 
	    0x3C, 0x26, 0x23, 0x26, 0x3C, 
	    0x1E, 0xA1, 0xA1, 0x61, 0x12, 
	    0x3A, 0x40, 0x40, 0x20, 0x7A, 
	    0x38, 0x54, 0x54, 0x55, 0x59, 
	    0x21, 0x55, 0x55, 0x79, 0x41, 
	    0x21, 0x54, 0x54, 0x78, 0x41, 
	    0x21, 0x55, 0x54, 0x78, 0x40, 
	    0x20, 0x54, 0x55, 0x79, 0x40, 
	    0x0C, 0x1E, 0x52, 0x72, 0x12, 
	    0x39, 0x55, 0x55, 0x55, 0x59, 
	    0x39, 0x54, 0x54, 0x54, 0x59, 
	    0x39, 0x55, 0x54, 0x54, 0x58, 
	    0x00, 0x00, 0x45, 0x7C, 0x41, 
	    0x00, 0x02, 0x45, 0x7D, 0x42, 
	    0x00, 0x01, 0x45, 0x7C, 0x40, 
	    0xF0, 0x29, 0x24, 0x29, 0xF0, 
	    0xF0, 0x28, 0x25, 0x28, 0xF0, 
	    0x7C, 0x54, 0x55, 0x45, 0x00, 
	    0x20, 0x54, 0x54, 0x7C, 0x54, 
	    0x7C, 0x0A, 0x09, 0x7F, 0x49, 
	    0x32, 0x49, 0x49, 0x49, 0x32, 
	    0x32, 0x48, 0x48, 0x48, 0x32, 
	    0x32, 0x4A, 0x48, 0x48, 0x30, 
	    0x3A, 0x41, 0x41, 0x21, 0x7A, 
	    0x3A, 0x42, 0x40, 0x20, 0x78, 
	    0x00, 0x9D, 0xA0, 0xA0, 0x7D, 
	    0x39, 0x44, 0x44, 0x44, 0x39, 
	    0x3D, 0x40, 0x40, 0x40, 0x3D, 
	    0x3C, 0x24, 0xFF, 0x24, 0x24, 
	    0x48, 0x7E, 0x49, 0x43, 0x66, 
	    0x2B, 0x2F, 0xFC, 0x2F, 0x2B, 
	    0xFF, 0x09, 0x29, 0xF6, 0x20, 
	    0xC0, 0x88, 0x7E, 0x09, 0x03, 
	    0x20, 0x54, 0x54, 0x79, 0x41, 
	    0x00, 0x00, 0x44, 0x7D, 0x41, 
	    0x30, 0x48, 0x48, 0x4A, 0x32, 
	    0x38, 0x40, 0x40, 0x22, 0x7A, 
	    0x00, 0x7A, 0x0A, 0x0A, 0x72, 
	    0x7D, 0x0D, 0x19, 0x31, 0x7D, 
	    0x26, 0x29, 0x29, 0x2F, 0x28, 
	    0x26, 0x29, 0x29, 0x29, 0x26, 
	    0x30, 0x48, 0x4D, 0x40, 0x20, 
	    0x38, 0x08, 0x08, 0x08, 0x08, 
	    0x08, 0x08, 0x08, 0x08, 0x38, 
	    0x2F, 0x10, 0xC8, 0xAC, 0xBA, 
	    0x2F, 0x10, 0x28, 0x34, 0xFA, 
	    0x00, 0x00, 0x7B, 0x00, 0x00, 
	    0x08, 0x14, 0x2A, 0x14, 0x22, 
	    0x22, 0x14, 0x2A, 0x14, 0x08, 
	    0xAA, 0x00, 0x55, 0x00, 0xAA, 
	    0xAA, 0x55, 0xAA, 0x55, 0xAA, 
	    0x00, 0x00, 0x00, 0xFF, 0x00, 
	    0x10, 0x10, 0x10, 0xFF, 0x00, 
	    0x14, 0x14, 0x14, 0xFF, 0x00, 
	    0x10, 0x10, 0xFF, 0x00, 0xFF, 
	    0x10, 0x10, 0xF0, 0x10, 0xF0, 
	    0x14, 0x14, 0x14, 0xFC, 0x00, 
	    0x14, 0x14, 0xF7, 0x00, 0xFF, 
	    0x00, 0x00, 0xFF, 0x00, 0xFF, 
	    0x14, 0x14, 0xF4, 0x04, 0xFC, 
	    0x14, 0x14, 0x17, 0x10, 0x1F, 
	    0x10, 0x10, 0x1F, 0x10, 0x1F, 
	    0x14, 0x14, 0x14, 0x1F, 0x00, 
	    0x10, 0x10, 0x10, 0xF0, 0x00, 
	    0x00, 0x00, 0x00, 0x1F, 0x10, 
	    0x10, 0x10, 0x10, 0x1F, 0x10, 
	    0x10, 0x10, 0x10, 0xF0, 0x10, 
	    0x00, 0x00, 0x00, 0xFF, 0x10, 
	    0x10, 0x10, 0x10, 0x10, 0x10, 
	    0x10, 0x10, 0x10, 0xFF, 0x10, 
	    0x00, 0x00, 0x00, 0xFF, 0x14, 
	    0x00, 0x00, 0xFF, 0x00, 0xFF, 
	    0x00, 0x00, 0x1F, 0x10, 0x17, 
	    0x00, 0x00, 0xFC, 0x04, 0xF4, 
	    0x14, 0x14, 0x17, 0x10, 0x17, 
	    0x14, 0x14, 0xF4, 0x04, 0xF4, 
	    0x00, 0x00, 0xFF, 0x00, 0xF7, 
	    0x14, 0x14, 0x14, 0x14, 0x14, 
	    0x14, 0x14, 0xF7, 0x00, 0xF7, 
	    0x14, 0x14, 0x14, 0x17, 0x14, 
	    0x10, 0x10, 0x1F, 0x10, 0x1F, 
	    0x14, 0x14, 0x14, 0xF4, 0x14, 
	    0x10, 0x10, 0xF0, 0x10, 0xF0, 
	    0x00, 0x00, 0x1F, 0x10, 0x1F, 
	    0x00, 0x00, 0x00, 0x1F, 0x14, 
	    0x00, 0x00, 0x00, 0xFC, 0x14, 
	    0x00, 0x00, 0xF0, 0x10, 0xF0, 
	    0x10, 0x10, 0xFF, 0x10, 0xFF, 
	    0x14, 0x14, 0x14, 0xFF, 0x14, 
	    0x10, 0x10, 0x10, 0x1F, 0x00, 
	    0x00, 0x00, 0x00, 0xF0, 0x10, 
	    0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 
	    0xF0, 0xF0, 0xF0, 0xF0, 0xF0, 
	    0xFF, 0xFF, 0xFF, 0x00, 0x00, 
	    0x00, 0x00, 0x00, 0xFF, 0xFF, 
	    0x0F, 0x0F, 0x0F, 0x0F, 0x0F, 
	    0x38, 0x44, 0x44, 0x38, 0x44, 
	    0x7C, 0x2A, 0x2A, 0x3E, 0x14, 
	    0x7E, 0x02, 0x02, 0x06, 0x06, 
	    0x02, 0x7E, 0x02, 0x7E, 0x02, 
	    0x63, 0x55, 0x49, 0x41, 0x63, 
	    0x38, 0x44, 0x44, 0x3C, 0x04, 
	    0x40, 0x7E, 0x20, 0x1E, 0x20, 
	    0x06, 0x02, 0x7E, 0x02, 0x02, 
	    0x99, 0xA5, 0xE7, 0xA5, 0x99, 
	    0x1C, 0x2A, 0x49, 0x2A, 0x1C, 
	    0x4C, 0x72, 0x01, 0x72, 0x4C, 
	    0x30, 0x4A, 0x4D, 0x4D, 0x30, 
	    0x30, 0x48, 0x78, 0x48, 0x30, 
	    0xBC, 0x62, 0x5A, 0x46, 0x3D, 
	    0x3E, 0x49, 0x49, 0x49, 0x00, 
	    0x7E, 0x01, 0x01, 0x01, 0x7E, 
	    0x2A, 0x2A, 0x2A, 0x2A, 0x2A, 
	    0x44, 0x44, 0x5F, 0x44, 0x44, 
	    0x40, 0x51, 0x4A, 0x44, 0x40, 
	    0x40, 0x44, 0x4A, 0x51, 0x40, 
	    0x00, 0x00, 0xFF, 0x01, 0x03, 
	    0xE0, 0x80, 0xFF, 0x00, 0x00, 
	    0x08, 0x08, 0x6B, 0x6B, 0x08,
	    0x36, 0x12, 0x36, 0x24, 0x36, 
	    0x06, 0x0F, 0x09, 0x0F, 0x06, 
	    0x00, 0x00, 0x18, 0x18, 0x00, 
	    0x00, 0x00, 0x10, 0x10, 0x00, 
	    0x30, 0x40, 0xFF, 0x01, 0x01, 
	    0x00, 0x1F, 0x01, 0x01, 0x1E, 
	    0x00, 0x19, 0x1D, 0x17, 0x12, 
	    0x00, 0x3C, 0x3C, 0x3C, 0x3C, 
	    0x00, 0x00, 0x00, 0x00, 0x00, 
	};
	private static final short BLACK = 0;
	private static final short WHITE = 1;
	private int _BV(int i_bit)
	{
		return (1<<(i_bit));
	}
	 
	public Adafruit_GFX(I2C i_i2c,int i_address , int i_width , int i_height)  throws MbedJsException
	{
		super(i_i2c, i_address);
		this._init(i_width, i_height);
		
	}
	public Adafruit_GFX(Mcu i_mcu, int i_sda, int i_scl,int i_address,
			int i_width , int i_height) throws MbedJsException {
		super(i_mcu, i_sda, i_scl, i_address);
		this._init(i_width, i_height);
		
	}
	private void _init(int i_width, int i_height) throws MbedJsException
	{
		this._rawWidth=i_width;
        this._rawHeight=i_height;
        this._width=i_width;
        this._height = i_height;
        this.cursor_x = 0;
        this.cursor_y = 0;
        this.textcolor = Adafruit_GFX.WHITE;
        this.textbgcolor = Adafruit_GFX.BLACK;
        this.textsize = 1;
        this.rotation = 0;
        this.wrap =true;
	}
	public void drawPixel(int i_x , int i_y , short i_color) throws MbedJsException
	{
		
	}
	public void invertDisplay(boolean i_invert) throws MbedJsException
	{
		
	}
	int _putc(byte i_value) throws MbedJsException
	{
		return writeChar(i_value);}
	int _getc(){ 
		return -1;}
	
	public void drawLine(short i_x0 , short i_y0, short i_x1 , short i_y1,short i_color) throws MbedJsException
	{
	    //short steep = Math.abs(i_y1 - i_y0) > Math.abs(i_x1 - i_x0);
	    boolean steep = Math.abs(i_y1 - i_y0) > Math.abs(i_x1 - i_x0);
	    

    	short t;
	    if (steep)
	    {
	    	//swap(i_x0,i_y0);
	    	t = i_x0;
	    	i_x0 = i_y0;
	    	i_y0 = t;
	    	
	    	// swap(i_x1,i_y1);
	        t = i_x1;
	    	i_x1 = i_y1;
	    	i_y1 = t;
	    	
	    }
	    
	    if (i_x0 > i_x1)
	    {
	    	//swap(i_x0,i_x1);
	    	t =i_x0;
	    	i_x0 = i_x1;
	    	i_x1= t;
	    	
	    	// swap(i_y0,i_y1);
	    	t = i_y0;
	    	i_y0 = i_y1;
	    	i_y1 = t;
	    }
	    
	    short dx, dy;
	    dx = (short) (i_x1 - i_x0);
	    dy = (short) Math.abs(i_y1 - i_y0);
	    
	    short err = (short) (dx / 2);
	    short ystep;
	    
	    if (i_y0 < i_y1)
	        ystep = 1;
	    else
	        ystep = -1;
	    
	    for (; i_x0<=i_x1; i_x0++)
	    {
	        if (steep)
	            drawPixel(i_y0, i_x0, i_color);
	        else
	            drawPixel(i_x0, i_y0, i_color);
	 
	        err -= dy;
	        if (err < 0)
	        {
	            i_y0 += ystep;
	            err += dx;
	        }
	    }		
	}
	public void drawFastVLine(short i_x, short i_y, int i_height, short i_color) throws MbedJsException
	{
	    // stupidest version - update in subclasses if desired!
	    this.drawLine(i_x, i_y, i_x, (short) (i_y+i_height-1), i_color);
	}
	public void drawFastHLine(short i_x, short i_y, short i_w, short i_color) throws MbedJsException
	{
	    // stupidest version - update in subclasses if desired!
		this.drawLine(i_x, i_y, (short) (i_x+i_w-1), i_y, i_color);
	}
	public void drawRect(short i_x, short i_y, short i_w, short i_h, short i_color) throws MbedJsException
	{
		this.drawFastHLine(i_x, i_y, i_w, i_color);
		this.drawFastHLine(i_x, (short) (i_y+i_h-1), i_w, i_color);
		this.drawFastVLine(i_x, i_y, i_h, i_color);
		this.drawFastVLine((short) (i_x+i_w-1), i_y, i_h, i_color);		
	}
	public void fillRect(short i_x, short i_y, int i_w, int i_h, short i_color) throws MbedJsException
	{
	    // stupidest version - update in subclasses if desired!
	    for (short i=i_x; i<i_x+i_w; i++)
	    	this.drawFastVLine(i, i_y, i_h, i_color); 
	}
	public void fillScreen(short i_color) throws MbedJsException
	{
		this.fillRect((short)0, (short)0, _width, _height, i_color);
	}
	 
	public void drawCircle(short i_x0, short i_y0, short i_r, short i_color) throws MbedJsException
	{
		short f = (short) (1 - i_r);
		short ddF_x =1;
		short ddF_y =(short) (-2 * i_r);
		short x = 0;
		short y = i_r;
		
		this.drawPixel(i_x0 , (short) (i_y0+i_r) , i_color);
		this.drawPixel(i_x0 , (short) (i_y0-i_r) , i_color);
		this.drawPixel((short) (i_x0+i_r) , i_y0 , i_color);
		this.drawPixel((short) (i_x0-i_r) , i_y0 , i_color);
		
		while (x<y)
		{
			if(f>=0)
			{
				y--;
				ddF_y += 2;
				f +=ddF_y;
			}
			x++;
			ddF_x += 2;
			f += ddF_x;
			
			this.drawPixel(i_x0 + x, i_y0 + y, i_color);
			this.drawPixel(i_x0 - x, i_y0 + y, i_color);
			this.drawPixel(i_x0 + x, i_y0 - y, i_color);
			this.drawPixel(i_x0 - x, i_y0 - y, i_color);
			this.drawPixel(i_x0 + y, i_y0 + x, i_color);
			this.drawPixel(i_x0 - y, i_y0 + x, i_color);
			this.drawPixel(i_x0 + y, i_y0 - x, i_color);
			this.drawPixel(i_x0 - y, i_y0 - x, i_color);
		}
		
	}
	public void drawCircleHelper(short i_x0, short i_y0, short i_r, byte i_cornername, short i_color) throws MbedJsException
	{
	    short f     = (short) (1 - i_r);
	    short ddF_x = 1;
	    short ddF_y = (short) (-2 * i_r);
	    short x     = 0;
	    short y     = i_r;
	    
	    while (x<y)
	    {
	        if (f >= 0)
	        {
	            y--;
	            ddF_y += 2;
	            f += ddF_y;
	        }
	        x++;
	        ddF_x += 2;
	        f += ddF_x;
	        
	        
	        if ((i_cornername & 0x4) !=0)
	        {
	        	this.drawPixel(i_x0 + x, i_y0 + y, i_color);
	        	this.drawPixel(i_x0 + y, i_y0 + x, i_color);
	        } 
	 
	        if ((i_cornername & 0x2) !=0)
	        {
	        	this.drawPixel(i_x0 + x, i_y0 - y, i_color);
	        	this.drawPixel(i_x0 + y, i_y0 - x, i_color);
	        }
	 
	        if ((i_cornername & 0x8)!=0)
	        {
	        	this.drawPixel(i_x0 - y, i_y0 + x, i_color);
	        	this.drawPixel(i_x0 - x, i_y0 + y, i_color);
	        }
	        
	        if ((i_cornername & 0x1)!=0)
	        {
	        	this.drawPixel(i_x0 - y, i_y0 - x, i_color);
	        	this.drawPixel(i_x0 - x, i_y0 - y, i_color);
	        }
	    }
	}
	public void fillCircle(short i_x0, short i_y0, short i_r, short i_color) throws MbedJsException
	{
		this.drawFastVLine(i_x0,(short)( i_y0-i_r),(short)( 2*i_r+1), i_color);
		this.fillCircleHelper(i_x0, i_y0, i_r,(byte) 3, (short)0, i_color);
	}
	public void fillCircleHelper(short i_x0, short i_y0, short i_r, byte i_cornername, short i_delta, short i_color) throws MbedJsException
	{
		short f     = (short) (1 - i_r);
		short ddF_x = 1;
		short ddF_y = (short) (-2 * i_r);
		short x     = 0;
		short y     = i_r;
	    
	    while (x<y)
	    {
	        if (f >= 0)
	        {
	            y--;
	            ddF_y += 2;
	            f += ddF_y;
	        }
	        x++;
	        ddF_x += 2;
	        f += ddF_x;
	        
	        if ((i_cornername & 0x1) !=0)
	        {
	        	this.drawFastVLine((short)(i_x0+x), (short)(i_y0-y),(short)( 2*y+1+i_delta), i_color);
	        	this.drawFastVLine((short)(i_x0+y), (short)(i_y0-x), (short)(2*x+1+i_delta), i_color);
	        }
	 
	        if ((i_cornername & 0x2) != 0)
	        {
	        	this.drawFastVLine((short)(i_x0-x), (short)(i_y0-y), (short)(2*y+1+i_delta), i_color);
	        	this.drawFastVLine((short)(i_x0-y), (short)(i_y0-x), (short)(2*x+1+i_delta), i_color);
	        }
	    }
	}
	 
	public void drawTriangle(short i_x0, short i_y0, short i_x1, short i_y1,
			short i_x2, short i_y2, short i_color) throws MbedJsException
	{
		 
		this.drawLine(i_x0, i_y0, i_x1, i_y1, i_color);
		this.drawLine(i_x1, i_y1, i_x2, i_y2, i_color);
		this.drawLine(i_x2, i_y2, i_x0, i_y0, i_color);
	}
	public void fillTriangle(short i_x0, short i_y0, short i_x1, short i_y1,
			short i_x2, short i_y2, short i_color) throws MbedJsException
	{
	    short a, b, y, last;
	    
	    short t=0;
	    // Sort coordinates by Y order (y2 >= y1 >= y0)
	    if (i_y0 > i_y1)
	        //swap(y0, y1);
	    	t = i_y0;
	    	i_y0 = i_y1;
	    	i_y1 = t;
	    	//swap(x0, x1);
	    	t=i_x0;
	    	i_x0 = i_x1;
	    	i_x1 = t;
	    if (i_y1 > i_y2)
	        //swap(y2, y1);
	    	t= i_y2 ;
	    	i_y2 = i_y1;
	    	i_y1 = t;
	    	//swap(x2, x1);
	    	t = i_x2;
	    	i_x2 = i_x1;
	    	i_x1 = t;
	    	
	    if (i_y0 > i_y1)
	        //swap(y0, y1);
	    	t = i_y0;
	    	i_y0 = i_y1;
	    	i_y1 = t;
	    	
	    	//swap(x0, x1);
	    	t = i_x0;
	    	i_x0 = i_x1;
	    	i_x1 = t;
	    
	    if(i_y0 == i_y2)
	    { // Handle awkward all-on-same-line case as its own thing
	        a = b = i_x0;
	        if(i_x1 < a)
	            a = i_x1;
	        else if(i_x1 > b)
	            b = i_x1;
	            
	        if(i_x2 < a)
	            a = i_x2;
	        else if(i_x2 > b) b = i_x2;
	        	this.drawFastHLine(a, i_y0, (short) (b-a+1), i_color);
	        return;
	    }
	 
	    short
	        dx01 = (short) (i_x1 - i_x0),
	        dy01 = (short) (i_y1 - i_y0),
	        dx02 = (short) (i_x2 - i_x0),
	        dy02 = (short) (i_y2 - i_y0),
	        dx12 = (short) (i_x2 - i_x1),
	        dy12 = (short) (i_y2 - i_y1),
	        sa   = 0,
	        sb   = 0;
	 
	    // For upper part of triangle, find scanline crossings for segments
	    // 0-1 and 0-2.  If y1=y2 (flat-bottomed triangle), the scanline y1
	    // is included here (and second loop will be skipped, avoiding a /0
	    // error there), otherwise scanline y1 is skipped here and handled
	    // in the second loop...which also avoids a /0 error here if y0=y1
	    // (flat-topped triangle).
	    if(i_y1 == i_y2)
	        last = i_y1;   // Include y1 scanline
	    else
	        last = (short) (i_y1-1); // Skip it
	 
	    for(y=i_y0; y<=last; y++)
	    {
	        a   = (short) (i_x0 + sa / dy01);
	        b   = (short) (i_x0 + sb / dy02);
	        sa += dx01;
	        sb += dx02;
	        /* longhand:
	        a = x0 + (x1 - x0) * (y - y0) / (y1 - y0);
	        b = x0 + (x2 - x0) * (y - y0) / (y2 - y0);
	        */
	        if(a > b){
	            //swap(a,b);
	        	t=a;
	        	a=b;
	        	b=t;
	        }
	        this.drawFastHLine(a, y, (short)(b-a+1), i_color);
	    }
	 
	    // For lower part of triangle, find scanline crossings for segments
	    // 0-2 and 1-2.  This loop is skipped if y1=y2.
	    sa = (short) (dx12 * (y - i_y1));
	    sb = (short) (dx02 * (y - i_y0));
	    for(; y<=i_y2; y++)
	    {
	        a   = (short) (i_x1 + sa / dy12);
	        b   = (short) (i_x0 + sb / dy02);
	        sa += dx12;
	        sb += dx02;
	        /* longhand:
	        a = x1 + (x2 - x1) * (y - y1) / (y2 - y1);
	        b = x0 + (x2 - x0) * (y - y0) / (y2 - y0);
	        */
	        if(a > b){
	            //swap(a,b);
	        	t=a;
	        	a=b;
	        	b=t;
	        }
	        this.drawFastHLine(a, y, (short) (b-a+1), i_color);
	    }
	}
	public void drawRoundRect(short i_x, short i_y, short i_width, short i_height, short i_r, short i_color) throws MbedJsException
	{
		// smarter version
		this.drawFastHLine((short)(i_x+i_r)  , i_y    , (short)(i_width-2*i_r), i_color); // Top
		this.drawFastHLine((short)(i_x+i_r) ,(short)( i_y+i_height-1),(short)( i_width-2*i_r), i_color); // Bottom
		this.drawFastVLine(  i_x    ,(short)( i_y+i_r)  ,(short)( i_height-2*i_r), i_color); // Left
		this.drawFastVLine((short)(  i_x+i_width-1),(short)( i_y+i_r ) ,(short)( i_height-2*i_r), i_color); // Right
	    // draw four corners
		this.drawCircleHelper((short)(i_x+i_r)    ,(short)( i_y+i_r)    , i_r,(byte) 1, i_color);
		this.drawCircleHelper((short)(i_x+i_width-i_r-1),(short)( i_y+i_r)    , i_r,(byte) 2, i_color);
		this.drawCircleHelper((short)(i_x+i_width-i_r-1),(short)( i_y+i_height-i_r-1), i_r,(byte) 4, i_color);
		this.drawCircleHelper((short)(i_x+i_r)    ,(short)( i_y+i_height-i_r-1), i_r,(byte) 8, i_color);
	}
	public void fillRoundRect(short i_x, short i_y, short i_w, short i_h, short i_r, short i_color) throws MbedJsException
	{
	    // smarter version
		this.fillRect((short)(i_x+i_r), i_y,(short)( i_w-2*i_r), i_h, i_color);
	    
	    // draw four corners
		this.fillCircleHelper((short)(i_x+i_w-i_r-1),(short)( i_y+i_r), i_r, (byte)1,(short)( i_h-2*i_r-1), i_color);
		this.fillCircleHelper((short)(i_x+i_r    ),(short)( i_y+i_r), i_r, (byte)2,(short)( i_h-2*i_r-1), i_color);
	}
	 
	public void drawBitmap(short i_x, short i_y, byte[] i_bitmap, short i_w, short i_h, short i_color) throws MbedJsException
	{
	    for (short j=0; j<i_h; j++)
	    {
	        for (short i=0; i<i_w; i++ )
	        {
	            if ((i_bitmap[i + (j/8)*i_w] & _BV(j%8))!=0)
	                drawPixel(i_x+i, i_y+j, i_color);
	        }
	    }
	}
	public void drawChar(short i_x, short y, byte i_c, short i_color, short i_bg, byte i_size) throws MbedJsException
	{
	    if(
	            (i_x >= this._width) || // Clip right
	            (y >= this._height) || // Clip bottom
	            ((i_x + 5 * i_size - 1) < 0) || // Clip left
	            ((y + 8 * i_size - 1) < 0) // Clip top
	            )
	        return;
	        
	        for (short i=0; i<6; i++ )
	        {
	            short line = 0;
	     
	            if (i == 5) 
	                line = 0x0;
	            else 
	                line = (short)font[(i_c*5)+i];
	                
	            for (short j = 0; j<8; j++)
	            {
	                if ((line & 0x1) != 0)
	                {
	                    if (i_size == 1) // default size
	                    	this.drawPixel(i_x+i, y+j, i_color);
	    //#ifdef WANT_ABSTRACTS
	                    else // big size
	                    	this.fillRect((short)(i_x+(i*i_size)), (short)(y+(j*i_size)), i_size, i_size, i_color);
	    //#endif
	                }
	                else if (i_bg != i_color)
	                {
	                    if (i_size == 1) // default size
	                    	this.drawPixel(i_x+i, y+j, i_bg);
	    //#ifdef WANT_ABSTRACTS
	                    else // big size
	                    	this.fillRect((short)(i_x+i*i_size), (short)(y+j*i_size), i_size, i_size, i_bg);
	    //#endif
	                }
	                line >>= 1;
	            }
	        }
	}
	public int writeChar(byte i_ch) throws MbedJsException
	{
	    if (i_ch == '\n')
	    {
	    	this.cursor_y += this.textsize*8;
	        this.cursor_x = 0;
	    }
	    else if (i_ch == '\r')
	    	this.cursor_x = 0;
	    else
	    {
	        drawChar(this.cursor_x, this.cursor_y,
	        		i_ch, this.textcolor, this.textbgcolor, this.textsize);
	        this.cursor_x += this.textsize*6;
	        if (this.wrap && (this.cursor_x > (this._width - this.textsize*6)))
	        {
	        	this.cursor_y += this.textsize*8;
	            this.cursor_x = 0;
	        }
	    }
	    return 1;
	}
	 
	public short width() throws MbedJsException { 
		return (short)this._width;
	}
	public short height() throws MbedJsException { 
		return (short)this._height; 
	}
	 
	public void setCursor(short i_x, short i_y) throws MbedJsException 
	{ 
		this.cursor_x = i_x;
		this.cursor_y = i_y; 
	}
	public void setTextSize(byte i_size) throws MbedJsException {
		this.textsize = (i_size > 0) ? i_size : 1; 
	}
	public void setTextColor(short i_color) throws MbedJsException {
		this.textcolor = i_color; 
		this.textbgcolor = i_color; 
	}
	public void setTextColor(short i_col, short i_bg) throws MbedJsException { 
		this.textcolor = i_col; 
		this.textbgcolor = i_bg; 
		}
	public void setTextWrap(boolean i_wrap) throws MbedJsException {
		this.wrap = i_wrap; }
	 
	public void setRotation(byte i_x) throws MbedJsException
	{
	    i_x %= 4;  // cant be higher than 3
	    this.rotation = i_x;
	    switch (i_x)
	    {
	        case 0:
	        case 2:
	            this._width = this._rawWidth;
	            this._height = this._rawHeight;
	            break;
	        case 1:
	        case 3:
	        	this._width = this._rawHeight;
	            this._height = this._rawWidth;
	            break;
	    }
	}
	public short getRotation()  throws MbedJsException
	{ rotation %= 4; return rotation; }
	 
	protected int  _rawWidth, _rawHeight;   // this is the 'raw' display w/h - never changes
	protected int  _width, _height; // dependent on rotation
	protected short  cursor_x, cursor_y;
	protected short textcolor, textbgcolor;
	protected byte  textsize;
	protected byte  rotation;
	protected boolean  wrap; // If set, 'wrap' text at right edge of display

}
