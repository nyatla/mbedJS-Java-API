/* An I2C text LCD library for Displaytronic ACM1602NI-FLW-FBW-M01
 * Copyright 2013, 2014, Takuo WATANABE (wtakuo)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/* original source code
 * http://mbed.org/users/takuo/code/ACM1602NI/
 * 
 * modified by hara41 hara.shinichi@gmail.com
 */


package jp.nyatla.mimic.mbedjs.javaapi.driver;

import jp.nyatla.mimic.mbedjs.*;
import jp.nyatla.mimic.mbedjs.javaapi.*;
import jp.nyatla.mimic.mbedjs.javaapi.driver.utils.DriverBaseClass;
/**
 * I2C接続のLCDディスプレイACM1602NI
 * @author hara.shinichi@gmail.com
 *
 */
public class ACM1602NI extends DriverBaseClass{
	//7bitI2Cアドレスです。I2C_ADDRESS<<1を指定してください。
	public final static int I2C_ADDRESS = 0x50;
//    private static final int I2C_BIT_WAIT_US = 20;
    private static final int I2C_COMMAND_WAIT_MS = 4;
 
    private static final int DISPLAY_COLUMNS = 16;
    private static final int DISPLAY_ROWS = 2;
 
    private final I2C _i2c;
    private int _column, _row;
 


	private final boolean _is_attached;
	private final int _addr;
	/**
	 * Mcuから直接インスタンスを生成する場合のコンストラクタ
	 * @param i_mcu MCUインスタンス
	 * @param i_sda SDAピン
	 * @param i_scl SCLピン
	 * @param i_address I2Cアドレス
	 * @throws MbedJsException MbedJS例外
	 */
	public ACM1602NI(Mcu i_mcu,int i_sda, int i_scl,int i_address) throws MbedJsException {

		this._is_attached=true;
		this._i2c=new I2C(i_mcu, i_sda, i_scl);
		this._addr=i_address & 0xff;
		this._i2c.frequency(10000);
		this.init();
	}
	/**
	 * 既存のI2Cをインスタンスに追加する場合のコンストラクタ
	 * @param i_i2c I2Cインスタンス
	 * @param i_address I2Cアドレス
	 * @throws MbedJsException MbedJS例外
	 */
	public ACM1602NI(I2C i_i2c , int i_address) throws MbedJsException {
		this._is_attached=false;
		this._i2c=i_i2c;
		this._addr=i_address & 0xff;
		this.init();
	}
	public void dispose() throws MbedJsException{
		if(this._is_attached){
			this._i2c.dispose();
		}
	}
	private void wait_ms(int i_time) throws MbedJsException
	{
		this.sleep_ms(i_time);
	}
//	private void wait_us(int i_time) throws MbedJsException
//	{
//		this.sleep_ms(1);
//	}
 
	private void init() throws MbedJsException {
	    this.writeCommand(0x01);
	    this.wait_ms(I2C_COMMAND_WAIT_MS);
	    this.writeCommand(0x38);
	    this.wait_ms(I2C_COMMAND_WAIT_MS);
	    this.writeCommand(0x0f);
	    this.wait_ms(I2C_COMMAND_WAIT_MS);
	    this.writeCommand(0x06);
	    this.wait_ms(I2C_COMMAND_WAIT_MS);
	    this.locate(0, 0);
	}
/*	 
	private int writeBytes(byte[]i_data, int i_length, boolean i_repeated) throws MbedJsException {
	    this.wait_us(I2C_BIT_WAIT_US);
	    this._i2c.start();
	    for (int i = 0; i < i_length; i++) {
	    	this.wait_us(I2C_BIT_WAIT_US);
	        if (this._i2c.write(i_data[i]&0x0ff) != 1) {
	        	this.wait_us(I2C_BIT_WAIT_US);
	        	this._i2c.stop();
	            return I2C_FAILURE;
	        }
	    }
	    if (!i_repeated) {
	    	this.wait_us(I2C_BIT_WAIT_US);
	    	this._i2c.stop();
	    }
	    return I2C_SUCCESS;
	}
*/
	/**
	 * 1文字を表示する
	 * @param i_column　行
	 * @param i_row　列
	 * @param i_c　文字
	 * @throws MbedJsException MbedJS例外
	 */
	public void character(int i_column, int i_row, int i_c) throws MbedJsException {
	    this.writeCommand(address(i_column, i_row));
	    this.writeData(i_c);
	}
	/**
	 * スクリーンをクリアする
	 * @throws MbedJsException MbedJS例外
	 */
	public void cls() throws MbedJsException {
	    this.writeCommand(0x01);
	    this.wait_ms(I2C_COMMAND_WAIT_MS);
	    this.locate(0, 0);
	}
	/**
	 * カーソルを任意の位置に移動する
	 * @param i_column 行
	 * @param i_row 列
	 */
	public void locate(int i_column, int i_row) {
	    this._column = i_column;
	    this._row = i_row;
	}
	/**
	 * 文字列を表示する
	 * @param i_s 文字列
	 * @return　文字数
	 * @throws MbedJsException MbedJS例外
	 */
	public int puts(String i_s) throws MbedJsException
	{
		int i=0;
		for(;i<i_s.length();i++){
			int v=i_s.charAt(i);
			this._putc((v>255)?'?':v);
		}
		return i;
	}
	private int _putc(int i_value) throws MbedJsException
	{
	    if (i_value == '\n') {
	    	this._column = 0;
	    	this._row = (this._row + 1) % this.rows();
	    }
	    else {
	    	this.character(this._column, this._row, i_value);
	    	this._column++;
	        if (this._column >= this.columns()) {
	        	this._column = 0;
	        	this._row = (this._row + 1) % this.rows();
	        }
	    }
	    return i_value;
	}
	 
//	private int _getc() {
//	    return -1;
//	}
 
	private void writeCommand(int i_command) throws MbedJsException
	{
		byte cmd = (byte) (i_command & 0x0ff);
//		byte[] bs=new byte[]{ (byte) this._addr, 0x00,cmd};
//	    this.writeBytes(bs, 3 , false);
		byte[] bs=new byte[]{0x00,cmd};
		this._i2c.write(this._addr,bs,false);
	}
	 
	private void writeData(int i_data) throws MbedJsException {
		byte dat= (byte) (i_data & 0x0ff);
//		byte[] bs = new byte[]{ (byte) this._addr, 0x80, dat };
//	    this.writeBytes(bs, 3 , false);
		byte[] bs = new byte[]{(byte)0x80, dat};
		this._i2c.write(this._addr,bs,false);
	}
 
	private int address(int i_column, int i_row) {
	    return 0x80 + i_row * 0x40 + i_column;
	}
 
	private int columns() {
	    return DISPLAY_COLUMNS;
	}
 
	private int rows() {
		return DISPLAY_ROWS;
	}
}
