/**
 * 
 */
package jp.nyatla.mimic.mbedjs.javaapi.driver;

import jp.nyatla.mimic.mbedjs.MbedJsException;
import jp.nyatla.mimic.mbedjs.javaapi.*;
import java.util.*;

/**
 * このプログラムはhttp://mbed.org/users/simon/code/TextLCD/
 * をmbedJS-Javaに移植したものです。
 * 
 * @author hara4_000
 *
 */
public class TextLCD{
	public enum LCDType {
		LCD16x2,
		LCD16x2B,
		LCD20x2,
		LCD20x4
	}
	
	private void sleep_ms(long i_ms){
		
		Date d1 = new Date();
		Date d2 = new Date();
		while(d2.getTime() < (d1.getTime()+i_ms)){
			d2 = new Date();
		}
		return;
	}
	private Mcu _mcu;
	private DigitalOut _rs;
	private DigitalOut _e;
	private BusOut _d;
	private LCDType _type;
	private int _column;
	private int _row;
	/**
	 * LCDディスプレイの表示
	 * @param i_mcu
	 * @param i_rs
	 * @param i_ee
	 * @param i_d0
	 * @param i_d1
	 * @param i_d2
	 * @param i_d3
	 * @param i_type LCD機種
	 */
	public TextLCD(Mcu i_mcu , int i_rs, int i_ee,
	int i_d0, int i_d1, int i_d2, int i_d3,LCDType i_type) throws MbedJsException
	{
		this._type = i_type;
		this._mcu = i_mcu;
		this._rs = new DigitalOut(_mcu , i_rs);
		this._e  = new DigitalOut(_mcu , i_ee);
		this._d  = new BusOut(_mcu , i_d0, i_d1, i_d2, i_d3);
		
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
			if(this._row >= rows()){
				this._row = 0;
			}
		}else{
			character(this._column , this._row , i_ch);
			this._column++;
			if(this._column >= columns()){
				this._column = 0;
				this._row ++ ;
				if(this._row >= rows()){
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
		writeCommand(0x01);
		this.sleep_ms(1);
		locate( 0 , 0);
		
	}
	/**
	 * 行
	 * @return
	 */
	public int rows()
	{
		switch(this._type){
		case LCD20x4:
			return 4;
		case LCD16x2:
		case LCD16x2B:
		case LCD20x2:
			return 2;
		}
		return 0;
	}
	/**
	 * 列
	 * @return
	 */
	public int columns(){
		switch(this._type){
		case LCD20x4:
		case LCD20x2:
			return 20;
		case LCD16x2:
		case LCD16x2B:
			return 16;
		}
		return 0;
	}
	/**
	 * 書き込むアドレスを決定する
	 * @param i_column
	 * @param i_row
	 * @return
	 */
	int address(int i_column , int i_row){
		switch (this._type){
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
	void character(int i_column , int i_row , int i_ch) throws MbedJsException
	{
		int a = address(i_column , i_row);
		writeCommand(a);
		writeData(i_ch);
	}
	/**
	 * 1バイト書き込み
	 * @param i_value
	 * @throws MbedJsException 
	 */
	void writeByte(int i_value) throws MbedJsException
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
	void writeCommand(int i_command) throws MbedJsException
	{
		this._rs.write(0);
		writeByte(i_command);
	}
	/**
	 * データを送る
	 * @param i_data
	 * @throws MbedJsException 
	 */
	void writeData(int i_data) throws MbedJsException
	{
		this._rs.write(1);
		writeByte(i_data);
	}
	
	public static void main(String args[]) throws MbedJsException
	{
		
		Mcu mcu = new Mcu("10.0.0.2");
		TextLCD lcd = new TextLCD(mcu , PinName.p24, PinName.p26,
				PinName.p27, PinName.p28, PinName.p29, PinName.p30,LCDType.LCD16x2 );
		lcd.sleep_ms(1000);
		lcd.putc('T');
		lcd.putc('E');
		lcd.putc('S');
		lcd.putc('T');
	
		mcu.close();
		System.out.println("done");
		
	}
}
