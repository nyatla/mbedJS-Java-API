package test.hara41;

import jp.nyatla.mimic.mbedjs.MbedJsException;
import jp.nyatla.mimic.mbedjs.javaapi.I2C;
import jp.nyatla.mimic.mbedjs.javaapi.Mcu;
import jp.nyatla.mimic.mbedjs.javaapi.PinName;
import jp.nyatla.mimic.mbedjs.javaapi.driver.LM75B;

public class HMC6352 {
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
	public HMC6352(I2C i_i2c,int i_address) throws MbedJsException
	{
		this._is_attached=false;
		this._i2c=i_i2c;
		this._addr=i_address;
	}
	/**
	 * Mcuから直接生成する場合
	 * @param i_mcu
	 * @param sda
	 * @param scl
	 * @param i_address
	 * @throws MbedJsException
	 */
	public HMC6352(Mcu i_mcu, int sda, int scl, int i_address) throws MbedJsException
	{
		this._is_attached=true;
		this._i2c=new I2C(i_mcu, sda, scl);
		this._addr=i_address;
		this._i2c.frequency(10000);
	}
	public void dispose() throws MbedJsException{
		if(this._is_attached){
			this._i2c.dispose();
		}
	}
	public float read() throws MbedJsException
	{
		byte [] data ={0x41};
		
		this._i2c.write(this._addr, data, false);
		I2C.ReadResult rr = this._i2c.read(this._addr, 2, false);
		float retval = (((rr.data[0]& 0x0ff) << 8) | (rr.data[1]& 0x0ff)) /1.0f;
		return retval;
	}
	public static void main(String[] args) throws MbedJsException {
		// TODO Auto-generated method stub
		Mcu mcu=new Mcu("10.0.0.2");
		HMC6352 a=new HMC6352(mcu,PinName.p28,PinName.p27,0x80);
		System.out.println(a.read());
		mcu.close();
		System.out.println("done");
	}

}
