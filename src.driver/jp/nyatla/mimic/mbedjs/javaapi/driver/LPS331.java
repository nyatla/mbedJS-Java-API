package jp.nyatla.mimic.mbedjs.javaapi.driver;

import jp.nyatla.mimic.mbedjs.MbedJsException;
import jp.nyatla.mimic.mbedjs.javaapi.I2C;
import jp.nyatla.mimic.mbedjs.javaapi.Mcu;
import jp.nyatla.mimic.mbedjs.javaapi.PinName;

public class LPS331{
	// Pressure configuration values.
	public final static int PRESSURE_AVG_1  =0x00;
	public final static int PRESSURE_AVG_2  =0x01;
	public final static int PRESSURE_AVG_4  =0x02;
	public final static int PRESSURE_AVG_8  =0x03;
	public final static int PRESSURE_AVG_16 =0x04;
	public final static int PRESSURE_AVG_32 =0x05;
	public final static int PRESSURE_AVG_64 =0x06;
	public final static int PRESSURE_AVG_128=0x07;
	public final static int PRESSURE_AVG_256=0x08;
	public final static int PRESSURE_AVG_384=0x09;
	public final static int PRESSURE_AVG_512=0x0a;
	 
	// Temperature configuration values.
	public final static int TEMP_AVG_1      =0x00;
	public final static int TEMP_AVG_2      =0x01;
	public final static int TEMP_AVG_4      =0x02;
	public final static int TEMP_AVG_8      =0x03;
	public final static int TEMP_AVG_16     =0x04;
	public final static int TEMP_AVG_32     =0x05;
	public final static int TEMP_AVG_64     =0x06;
	public final static int TEMP_AVG_128    =0x07;
	
	// Data Rate                                   Pressure / Temperature 
	public final static int DATARATE_ONESHOT =0x00;    // OneShot    OneShot
	public final static int DATARATE_1HZ     =0x01;    // 1Hz        1Hz
	public final static int DATARATE_7HZ     =0x02;    // 7Hz        1Hz
	public final static int DATARATE_12_5HZ  =0x03;    // 12.5Hz     1Hz
	public final static int DATARATE_25HZ    =0x04;    // 25Hz       1Hz
	public final static int DATARATE_7HZ_T   =0x05;    // 7Hz        7Hz
	public final static int DATARATE_12_5HZ_T=0x06;   // 12.5Hz     12.5Hz
	public final static int DATARATE_25HZ_T  =0x07;    // 25Hz       25Hz (*)
	// (*) Not allowed with PRESSURE_AVG_512 & TEMP_AVG_128.
//	     More information , see datasheet.
	 
	// I2C Address.
	public final static int I2C_ADDRESS_SA0_H=0xba; 
	public final static int I2C_ADDRESS_SA0_L=0xb8;
	private final I2C _i2c;
	private final int _addr;
	/** I2Cを内部生成したか*/
	private final boolean _is_attached;
	private int _ctrlreg1;
	/**
	 * 既存のI2Cに追加する場合
	 * @param i_i2c
	 * @param i_address
	 * @throws MbedJsException
	 */
	public LPS331(I2C i_i2c,int i_address) throws MbedJsException
	{
		this._is_attached=false;
		this._i2c=i_i2c;
		this._addr=i_address;
		this._initDevice();
	}
	/**
	 * Mcuから直接生成する場合
	 * @param i_mcu
	 * @param sda
	 * @param scl
	 * @param i_address
	 * @throws MbedJsException
	 */
	public LPS331(Mcu i_mcu, int sda, int scl, int i_address) throws MbedJsException
	{
		this._is_attached=true;
		this._i2c=new I2C(i_mcu, sda, scl);
		this._addr=i_address;
		this._i2c.frequency(10000);
		this._initDevice();
	}
	private void _initDevice() throws MbedJsException
	{
		this._ctrlreg1 = 0x20;
	}

	public void dispose() throws MbedJsException{
		if(this._is_attached){
			this._i2c.dispose();
		}
	}
	 
	public int whoami() throws MbedJsException
	{
	    return (this._read((byte)0x0f) & 0x0ff);
	}
	 
	public boolean isAvailable() throws MbedJsException
	{
	    if(this.whoami() == 0xbb) {
	    	return true;
	    }
	    
	    return false;
	}
	 
	public void setResolution(int i_pressure_avg,int i_temp_avg) throws MbedJsException
	{
		this._write((byte)0x10, (byte)(((i_temp_avg & 0x07) << 4) | (i_pressure_avg & 0x0f)));
	}
	 
	public void setActive(boolean i_is_active) throws MbedJsException
	{
	    if(i_is_active) {
	        this._ctrlreg1 |= 0x80;
	    } else {
	    	this._ctrlreg1 &= ~0x80;
	    }
	 
	    this._write((byte)0x20,(byte)this._ctrlreg1);
	}
	 
	public void setDataRate(int i_datarate) throws MbedJsException
	{
	    int d= i_datarate & 0x07;
	    
	    this._ctrlreg1 &= ~(0x07 << 4);
	    this._ctrlreg1 |= d << 4;
	    
	    this._write((byte)0x20,(byte)this._ctrlreg1);
	}
	 
	    
	public float getPressure() throws MbedJsException
	{
	    byte[] data=this._read_multibyte((byte)0x28,3);
	    
	    float pressure  = (data[0]&0x0ff)|((data[1]&0x0ff) << 8)|((data[2]&0x0ff) << 16);
	    return pressure/4096.0f;
	}
	 
	public float getTemperature() throws MbedJsException
	{
		byte[] b=this._read_multibyte((byte)0x2b, 2);
		int temp=((0x0ff & b[1])<<8)|(b[0] & 0x0ff);	    
//	    temp  = data[0];
//	    temp |= data[1] << 8;
	    if((temp&0x00008000)!=0){
	    	temp|=0xffff8000;
	    }
	    return (float)(42.5 + temp / 480.0);
	}
	 
	 
	private void _write(byte i_subaddress, byte i_data) throws MbedJsException
	{
//	    _i2c.start();
//	    _i2c.write(_address);
//	    _i2c.write(subaddress);
//	    _i2c.write(data);
//	    _i2c.stop();
		this._i2c.write(this._addr,new byte[]{i_subaddress,i_data},false);
		
	}
	 
	private byte _read(byte i_subaddress) throws MbedJsException
	{
//	    int result = 0;
//	    _i2c.start();
//	    _i2c.write(_address);
//	    _i2c.write(subaddress);
//	    
//	    _i2c.start();
//	    _i2c.write(_address | 1);
//	    result = _i2c.read(0);
//	    
//	    _i2c.stop();
	    this._i2c.write(this._addr,new byte[]{i_subaddress},false);
	    I2C.ReadResult rs=this._i2c.read(this._addr,1,false);
	    return rs.data[0];
	}
	 
	public byte[] _read_multibyte(byte i_startsubaddress,int i_count) throws MbedJsException
	{
//	    _i2c.start();
//	    _i2c.write(_address);
//	    _i2c.write(startsubaddress | 0x80);
//	    
//	    _i2c.start();
//	    _i2c.write(_address | 1);
//	 
//	    for(int i = 0; i < count; i++) {
//	        data[i] = _i2c.read((i == count - 1) ? 0 : 1);
//	    }
//	    
//	    _i2c.stop();
	    this._i2c.write(this._addr,new byte[]{(byte)(i_startsubaddress|0x80)},false);
	    I2C.ReadResult rs=this._i2c.read(this._addr,i_count,false);
	    return rs.data;		
	}
	/**
	 * テストケース
	 * @param args
	 */
	public static void main(String args[]){
		try {
			Mcu mcu=new Mcu("192.168.128.39");
			LPS331 a=new LPS331(mcu,PinName.p28,PinName.p27,0x90);
			System.out.println("Temperture:"+a.getTemperature());
			System.out.println("Pressure:"+a.getPressure());
			mcu.close();
			System.out.println("done");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
