package jp.nyatla.mimic.mbedjs.javaapi.driver;

import jp.nyatla.mimic.mbedjs.*;
import jp.nyatla.mimic.mbedjs.javaapi.*;

/**
 * http://mbed.org/users/chris/code/LM75B/ をmbedJS-Javaに移植したものです
 * 
 * @author hara4_000
 * 
 */
public class LM75B
{
	public final static int I2C_ADDRESS=0x90; 
	private final I2C _i2c;
	private final int _addr;
	/** I2Cを内部生成したか*/
	private final boolean _is_attached;
	/**
	 * 既存のI2Cに追加する場合
	 * @param i_i2c
	 * @param i_address
	 * @throws MbedJsException
	 */
	public LM75B(I2C i_i2c,int i_address) throws MbedJsException
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
	public LM75B(Mcu i_mcu, int sda, int scl, int i_address) throws MbedJsException
	{
		this._is_attached=true;
		this._i2c=new I2C(i_mcu, sda, scl);
		this._addr=i_address;
		this._i2c.frequency(10000);
		this._initDevice();
	}
	private void _initDevice() throws MbedJsException
	{
		byte[] str = { 0x01, 0x00 };
		this._i2c.write(this._addr,str, false);
	}

	public void dispose() throws MbedJsException{
		if(this._is_attached){
			this._i2c.dispose();
		}
	}

	public float read() throws MbedJsException{
		byte[] str = { 0x00 };
		I2C.ReadResult rr = null;
		this._i2c.write(this._addr, str, false);
		rr = this._i2c.read(this._addr, 2, false);
		float ret;
		ret = ((rr.data[0] << 8) | rr.data[1]) / 256.0f;
		return ret;
	}
	/**
	 * テストケース
	 * @param args
	 */
	public static void main(String args[]){
		try {
			Mcu mcu=new Mcu("192.168.128.39");
			LM75B a=new LM75B(mcu,PinName.p28,PinName.p27,0x90);
			System.out.println(a.read());
			mcu.close();
			System.out.println("done");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}