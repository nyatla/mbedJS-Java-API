package test.hara41;

import jp.nyatla.mimic.mbedjs.*;
import jp.nyatla.mimic.mbedjs.javaapi.*;
import jp.nyatla.mimic.mbedjs.javaapi.driver.utils.DriverBaseClass;

public class ACM1602NI extends DriverBaseClass{
    private static final int i2c_addr = 0x50 << 1;
    private static final int i2c_bit_wait_us = 20;
    private static final int i2c_command_wait_ms = 4;
 
    private static final int display_columns = 16;
    private static final int display_rows = 2;
 
    private final I2C _i2c;
    private int _column, _row;
 

    public static final int I2C_SUCCESS =0;
	public static final int I2C_FAILURE =1;

	private final boolean _is_attached;
	private final int _addr;
	public ACM1602NI(Mcu i_mcu,int i_sda, int i_scl,int i_address) throws MbedJsException {

		this._is_attached=true;
		this._i2c=new I2C(i_mcu, i_sda, i_scl);
		this._addr=i_address & 0xff;
		this._i2c.frequency(10000);
		this.init();
	}
 
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
	private void wait_us(int i_time) throws MbedJsException
	{
		this.sleep_ms(1);
	}
 
	private void init() throws MbedJsException {
	    this.writeCommand(0x01);
	    this.wait_ms(i2c_command_wait_ms);
	    this.writeCommand(0x38);
	    this.wait_ms(i2c_command_wait_ms);
	    this.writeCommand(0x0f);
	    this.wait_ms(i2c_command_wait_ms);
	    this.writeCommand(0x06);
	    this.wait_ms(i2c_command_wait_ms);
	    this.locate(0, 0);
	}
	 
	private int writeBytes(char[]i_data, int i_length, boolean i_repeated) throws MbedJsException {
	    this.wait_us(i2c_bit_wait_us);
	    this._i2c.start();
	    for (int i = 0; i < i_length; i++) {
	    	this.wait_us(i2c_bit_wait_us);
	        if (this._i2c.write(i_data[i]) != 1) {
	        	this.wait_us(i2c_bit_wait_us);
	        	this._i2c.stop();
	            return I2C_FAILURE;
	        }
	    }
	    if (!i_repeated) {
	    	this.wait_us(i2c_bit_wait_us);
	    	this._i2c.stop();
	    }
	    return I2C_SUCCESS;
	}
 
	public void character(int i_column, int i_row, int i_c) throws MbedJsException {
	    this.writeCommand(address(i_column, i_row));
	    this.writeData(i_c);
	}
 
	public void cls() throws MbedJsException {
	    this.writeCommand(0x01);
	    this.wait_ms(i2c_command_wait_ms);
	    this.locate(0, 0);
	}
 
	public void locate(int i_column, int i_row) {
	    this._column = i_column;
	    this._row = i_row;
	}
 
	public int _putc(int i_value) throws MbedJsException {
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
	 
	public int _getc() {
	    return -1;
	}
 
	private void writeCommand(int i_command) throws MbedJsException {
		char cmd ;
		cmd = (char) (i_command & 0xff);
	    char[] bs=new char[]{ (char) this._addr, 0x00, cmd };
	    this.writeBytes(bs, 3 , false);
	}
	 
	private void writeData(int i_data) throws MbedJsException {
		char dat;
		dat = (char) (i_data & 0xff);
	    char[] bs = new char[]{ (char) this._addr, 0x80, dat };
	    this.writeBytes(bs, 3 , false);
	}
 
	private int address(int i_column, int i_row) {
	    return 0x80 + i_row * 0x40 + i_column;
	}
 
	private int columns() {
	    return display_columns;
	}
 
	private int rows() {
		return display_rows;
	}
}
