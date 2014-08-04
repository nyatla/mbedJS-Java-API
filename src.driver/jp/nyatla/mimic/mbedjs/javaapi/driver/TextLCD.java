/**
 * 
 */
package jp.nyatla.mimic.mbedjs.javaapi.driver;

import jp.nyatla.mimic.mbedjs.MbedJsException;
import jp.nyatla.mimic.mbedjs.javaapi.*;

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
	private Mcu _mcu;
	private DigitalOut _rs;
	private DigitalOut _e;
	private BusOut _d;
	private LCDType _type;
	private int _column;
	private int _row;
	/**
	 * LCDディスプレイの表示
	 * @param mcu
	 * @param rs
	 * @param ee
	 * @param d0
	 * @param d1
	 * @param d2
	 * @param d3
	 * @param type LCD機種
	 * @throws InterruptedException 
	 */
	public TextLCD(Mcu mcu , int rs, int ee,
	int d0, int d1, int d2, int d3,LCDType type) throws MbedJsException, InterruptedException
	{
		_type = type;
		_mcu = mcu;
		_rs = new DigitalOut(_mcu , rs);
		_e  = new DigitalOut(_mcu , ee);
		_d  = new BusOut(_mcu , d0, d1, d2, d3);
		
		_e.write(1);
		_rs.write(0);
		
		Thread.sleep(15);
		
		for(int i=0 ; i<3 ; i++){
			writeByte(0x3);
			Thread.sleep(2);
		}
		writeByte(0x2);
		Thread.sleep(1);
		
		writeCommand(0x28);
		writeCommand(0x0C);
		writeCommand(0x6);
		cls();
		
	}
	public void dispose() throws MbedJsException
	{
		_rs.write(0);
		_e.write(0);
		
	}
	/**
	 * キャラクタを1文字表示する
	 * @param c 文字
	 * @return
	 * @throws InterruptedException 
	 * @throws MbedJsException 
	 */
	public int putc(int c) throws MbedJsException, InterruptedException
	{
		// 改行処理
		if(c == '\n'){
			_column = 0;
			_row ++;
			if(_row >= rows()){
				_row = 0;
			}
		}else{
			character(_column , _row , c);
			_column++;
			if(_column >= columns()){
				_column = 0;
				_row ++ ;
				if(_row >= rows()){
					_row = 0;
				}
			}
		}
		return c;
	}
	/**
	 * カーソルの位置を移動する
	 * @param column 
	 * @param row
	 */
	public void locate(int column , int row){
		_column = column;
		_row = row;
	}
	/**
	 * 画面をクリアする
	 * @throws InterruptedException 
	 * @throws MbedJsException 
	 */
	public void cls() throws InterruptedException, MbedJsException
	{
		writeCommand(0x01);
		Thread.sleep(1);
		locate( 0 , 0);
		
	}
	/**
	 * 行
	 * @return
	 */
	public int rows()
	{
		switch(_type){
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
		switch(_type){
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
	 * @param column
	 * @param row
	 * @return
	 */
	int address(int column , int row){
		switch (_type){
		case LCD20x4:
			switch(row){
			case 0:
				return 0x80 + column;
			case 1:
				return 0xc0 + column;
			case 2:
				return 0x94 + column;
			case 3:
				return 0xd4 + column;
			}
		case LCD16x2B:
			return 0x80 + (row * 40) +column;
		case LCD16x2:
		case LCD20x2:
			return 0x80+(row * 0x40) +column;
		}
		return 0x80 + (row*0x40) + column;
	}
	/**
	 * 文字を表示する
	 * @param column 行 
	 * @param row 列
	 * @param c 文字
	 * @throws InterruptedException 
	 * @throws MbedJsException 
	 */
	void character(int column , int row , int c) throws MbedJsException, InterruptedException
	{
		int a = address(column , row);
		writeCommand(a);
		writeData(c);
	}
	/**
	 * 1バイト書き込み
	 * @param value
	 * @throws MbedJsException 
	 * @throws InterruptedException 
	 */
	void writeByte(int value) throws MbedJsException, InterruptedException
	{
		_d.write(value >> 4);
		Thread.sleep(1);
		_e.write(0);
		Thread.sleep(1);
		_e.write(1);
		_d.write(value >> 0);
		Thread.sleep(1);
		_e.write(0);
		Thread.sleep(1);
		_e.write(1);
	
	}
	/**
	 * コマンドを送る
	 * @param command
	 * @throws InterruptedException 
	 * @throws MbedJsException 
	 */
	void writeCommand(int command) throws MbedJsException, InterruptedException
	{
		_rs.write(0);
		writeByte(command);
	}
	/**
	 * データを送る
	 * @param data
	 * @throws MbedJsException 
	 * @throws InterruptedException 
	 */
	void writeData(int data) throws MbedJsException, InterruptedException{
		_rs.write(1);
		writeByte(data);
	}
	
	public static void main(String args[]) throws MbedJsException, InterruptedException
	{
		Mcu mcu = new Mcu("10.0.0.2");
		TextLCD lcd = new TextLCD(mcu , PinName.p24, PinName.p26,
				PinName.p27, PinName.p28, PinName.p29, PinName.p30,LCDType.LCD16x2 );
		lcd.putc('T');
		lcd.putc('E');
		lcd.putc('S');
		lcd.putc('T');
	
		mcu.close();
		System.out.println("done");
	}
}
