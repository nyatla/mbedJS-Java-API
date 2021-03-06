package jp.nyatla.mimic.mbedjs.javaapi.driver;

import jp.nyatla.mimic.mbedjs.MbedJsException;
import jp.nyatla.mimic.mbedjs.javaapi.I2C;
import jp.nyatla.mimic.mbedjs.javaapi.Mcu;
import jp.nyatla.mimic.mbedjs.javaapi.PinName;
import jp.nyatla.mimic.mbedjs.javaapi.driver.utils.DriverBaseClass;

/**
 * http://mbed.org/users/chris/code/LM75B/ をmbedJS-Javaに移植したものです
 * 
 * @author hara.shinichi@gmail.com
 * 
 */
public class LM75B extends DriverBaseClass
{
	public final static int I2C_ADDRESS=0x90; 
	private final I2C _i2c;
	private final int _addr;
	/** I2Cを内部生成したか*/
	private final boolean _is_attached;
	/**
	 * 既存のI2Cに追加する場合
	 * @param i_i2c I2Cインスタンス
	 * @param i_address I2Cアドレス
	 * @throws MbedJsException MbedJS例外
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
	 * @param i_mcu MCUインスタンス
	 * @param sda SDAピン
	 * @param scl SCLピン
	 * @param i_address I2Cアドレス
	 * @throws MbedJsException MbedJS例外
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
	/**
	 * 温度の読み込み
	 * @return　温度[deg]
	 * @throws MbedJsException MbedJS例外
	 */
	public float read() throws MbedJsException{
		byte[] str = { 0x00 };
		I2C.ReadResult rr = null;
		this._i2c.write(this._addr, str, false);
		rr = this._i2c.read(this._addr, 2, false);
		float ret= (((rr.data[0]& 0x0ff) << 8) | (rr.data[1]& 0x0ff)) / 256.0f;
		return ret;
	}
	
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