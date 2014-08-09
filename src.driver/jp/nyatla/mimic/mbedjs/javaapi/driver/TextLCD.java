/* mbed TextLCD Library, for a 4-bit LCD based on HD44780
 * Copyright (c) 2007-2010, sford, http://mbed.org
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
/**
 * modified by hara41
 * modified by nyatla
 */
package jp.nyatla.mimic.mbedjs.javaapi.driver;

import jp.nyatla.mimic.mbedjs.MbedJsException;
import jp.nyatla.mimic.mbedjs.javaapi.BusOut;
import jp.nyatla.mimic.mbedjs.javaapi.DigitalOut;
import jp.nyatla.mimic.mbedjs.javaapi.Mcu;
import jp.nyatla.mimic.mbedjs.javaapi.PinName;


/**
 * このプログラムはhttp://mbed.org/users/simon/code/TextLCD/
 * をmbedJS-Javaに移植したものです。
 *
 */
public class TextLCD extends DriverBaseClass
{
	public final static int LCD16x2	=1;
	public final static int LCD16x2B=2;
	public final static int LCD20x2	=3;
	public final static int LCD20x4	=4;
	

	private Mcu _mcu;
	private DigitalOut _rs;
	private DigitalOut _e;
	private BusOut _d;
	private int _lcd_type;
	private int _column;
	private int _row;
	/**
	 * A TextLCD interface for driving 4-bit HD44780-based LCDs
	 * LCDインスタンスを生成します。
	 * @param i_mcu
	 * @param i_rs_pin
	 * Instruction/data control line
	 * @param i_ee_pin
	 * Enable line (clock)
	 * @param i_d0_pin
	 * Data lines for using as a 4-bit interface(0)
	 * @param i_d1_pin
	 * Data lines for using as a 4-bit interface(1)
	 * @param i_d2_pin
	 * Data lines for using as a 4-bit interface(2)
	 * @param i_d3_pin
	 * Data lines for using as a 4-bit interface(3)
	 * @param i_lcd_type LCD
	 */
	public TextLCD(Mcu i_mcu , int i_rs_pin, int i_ee_pin,
	int i_d0_pin, int i_d1_pin, int i_d2_pin, int i_d3_pin,int i_lcd_type) throws MbedJsException
	{
		this._lcd_type = i_lcd_type;
		this._mcu = i_mcu;
		this._rs = new DigitalOut(_mcu , i_rs_pin);
		this._e  = new DigitalOut(_mcu , i_ee_pin);
		this._d  = new BusOut(_mcu , i_d0_pin, i_d1_pin, i_d2_pin, i_d3_pin);
		
		this._e.write(1);
		this._rs.write(0);
		
		this.sleep_ms(15);
		for(int i=0 ; i<3 ; i++){
			writeByte(0x3);
			this.sleep_ms(2);
		}
		writeByte(0x2);
		this.sleep_ms(1);
		
		writeCommand(0x28);
		writeCommand(0x0C);
		writeCommand(0x6);
		cls();
		
	}
	public void dispose() throws MbedJsException
	{
		this._rs.dispose();
		this._e.dispose();
		this._d.dispose();
	}
	public int puts(String i_s) throws MbedJsException
	{
		int i=0;
		for(;i<i_s.length();i++){
			int v=i_s.charAt(i);
			this.putc((v>255)?'?':v);
		}
		return i;
	}
	/**
	 * キャラクタを1文字表示する
	 * @param i_ch 文字
	 * @return
	 * @throws MbedJsException 
	 */
	public int putc(int i_ch) throws MbedJsException
	{
		// 改行処理
		if(i_ch == '\n'){
			this._column = 0;
			this._row ++;
			if(this._row >= this.rows()){
				this._row = 0;
			}
		}else{
			this.character(this._column , this._row , i_ch);
			this._column++;
			if(this._column >= this.columns()){
				this._column = 0;
				this._row ++ ;
				if(this._row >= this.rows()){
					this._row = 0;
				}
			}
		}
		return i_ch;
	}
	/**
	 * カーソルの位置を移動する
	 * @param i_column 
	 * @param i_row
	 */
	public void locate(int i_column , int i_row){
		this._column = i_column;
		this._row = i_row;
	}
	/**
	 * 画面をクリアする
	 * @throws MbedJsException 
	 */
	public void cls() throws MbedJsException
	{
		this.writeCommand(0x01);
		this.sleep_ms(1);
		this.locate( 0 , 0);
		
	}
	/**
	 * 行
	 * @return
	 */
	public int rows()
	{
		switch(this._lcd_type){
		case LCD20x4:
			return 4;
		case LCD16x2:
		case LCD16x2B:
		case LCD20x2:
		default:
			return 2;
		}
	}
	/**
	 * 列
	 * @return
	 * @throws MbedJsException 
	 */
	public int columns(){
		switch(this._lcd_type){
		case LCD20x4:
		case LCD20x2:
			return 20;
		case LCD16x2:
		case LCD16x2B:
		default:
			return 16;
		}
	}
	/**
	 * 書き込むアドレスを決定する
	 * @param i_column
	 * @param i_row
	 * @return
	 */
	int address(int i_column , int i_row){
		switch (this._lcd_type){
		case LCD20x4:
			switch(i_row){
			case 0:
				return 0x80 + i_column;
			case 1:
				return 0xc0 + i_column;
			case 2:
				return 0x94 + i_column;
			case 3:
				return 0xd4 + i_column;
			}
		case LCD16x2B:
			return 0x80 + (i_row * 40) +i_column;
		case LCD16x2:
		case LCD20x2:
			return 0x80+(i_row * 0x40) +i_column;
		}
		return 0x80 + (i_row*0x40) + i_column;
	}
	/**
	 * 文字を表示する
	 * @param i_column 行 
	 * @param i_row 列
	 * @param i_ch 文字
	 * @throws MbedJsException 
	 */
	protected void character(int i_column , int i_row , int i_ch) throws MbedJsException
	{
		int a = this.address(i_column , i_row);
		this.writeCommand(a);
		this.writeData(i_ch);
	}
	/**
	 * 1バイト書き込み
	 * @param i_value
	 * @throws MbedJsException 
	 */
	protected void writeByte(int i_value) throws MbedJsException
	{
		this._d.write(i_value >> 4);
		this.sleep_ms(1);
		
		this._e.write(0);
		this.sleep_ms(1);
		
		this._e.write(1);
		this._d.write(i_value >> 0);
		this.sleep_ms(1);
		
		this._e.write(0);
		this.sleep_ms(1);
		
		this._e.write(1);
	
	}
	/**
	 * コマンドを送る
	 * @param i_command
	 * @throws MbedJsException 
	 */
	protected void writeCommand(int i_command) throws MbedJsException
	{
		this._rs.write(0);
		this.writeByte(i_command);
	}
	/**
	 * データを送る
	 * @param i_data
	 * @throws MbedJsException 
	 */
	protected void writeData(int i_data) throws MbedJsException
	{
		this._rs.write(1);
		this.writeByte(i_data);
	}
	
	public static void main(String args[]) throws MbedJsException
	{
		Mcu mcu = new Mcu("10.0.0.2");
		TextLCD lcd = new TextLCD(mcu , PinName.p24, PinName.p26,
				PinName.p27, PinName.p28, PinName.p29, PinName.p30,TextLCD.LCD16x2);
		lcd.putc('T');
		lcd.putc('E');
		lcd.putc('S');
		lcd.putc('T');
		lcd.puts("STRING");
	
		mcu.close();
		System.out.println("done");
		
	}
}
