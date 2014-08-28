/**
 * このソースコードは以下の仕様書を元に製作しました
 * http://strawberry-linux.com/pub/HMC6352.pdf
 * http://strawberry-linux.com/pub/hmc6352-manual.pdf
 * 
 * Created by hara.shinichi@gmail.com
 */

package test.hara41;

import jp.nyatla.mimic.mbedjs.MbedJsException;
import jp.nyatla.mimic.mbedjs.javaapi.I2C;
import jp.nyatla.mimic.mbedjs.javaapi.Mcu;
import jp.nyatla.mimic.mbedjs.javaapi.PinName;
import jp.nyatla.mimic.mbedjs.javaapi.driver.utils.DriverBaseClass;

public class HMC6352 extends DriverBaseClass{
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
	 * @param i_sda
	 * @param i_scl
	 * @param i_address
	 * @throws MbedJsException
	 */
	public HMC6352(Mcu i_mcu, int i_sda, int i_scl, int i_address) throws MbedJsException
	{
		this._is_attached=true;
		this._i2c=new I2C(i_mcu, i_sda, i_scl);
		this._addr=i_address;
		this._i2c.frequency(10000);
	}
	public void dispose() throws MbedJsException{
		if(this._is_attached){
			this._i2c.dispose();
		}
	}
	public void writeEEPROM(byte i_address ,byte i_data) throws MbedJsException
	{
		byte[] str = {0x77 , i_address, i_data};
		this._i2c.write(this._addr, str, false);
		this.sleep_ms(1);
	}
	public byte readEEPROM(byte i_address) throws MbedJsException
	{
		byte[] str = {0x72 , i_address};
		this._i2c.write(this._addr, str, false);
		this.sleep_ms(1);
		I2C.ReadResult rr = this._i2c.read(this._addr, 1, false);
		return rr.data[0];
	}
	public void writeRAMRegister(byte i_address , byte i_data) throws MbedJsException
	{
		byte[] str = {0x47 ,i_address , i_data};
		this._i2c.write(this._addr, str, false);
		this.sleep_ms(1);
	}
	public byte readRAMRegister(byte i_address) throws MbedJsException
	{
		byte[] str = {0x67 , i_address};
		this._i2c.write(this._addr, str, false);
		this.sleep_ms(1);
		I2C.ReadResult rr = this._i2c.read(this._addr, 1, false);
		return rr.data[0];
	}
	
	public void enterSleepMode() throws MbedJsException
	{
		byte [] data ={0x53};
		this._i2c.write(this._addr, data, false);	
		this.sleep_ms(1);
	}
	public void exitSleepMode() throws MbedJsException
	{
		byte [] data ={0x57};
		this._i2c.write(this._addr, data, false);
		this.sleep_ms(1);
	}

	public void updateBridgeOffsets() throws MbedJsException
	{
		byte [] data ={0x4f};
		this._i2c.write(this._addr, data, false);	
		this.sleep_ms(6);
	}

	public void enterUserCalibraationMode() throws MbedJsException
	{
		byte [] data ={0x43};
		this._i2c.write(this._addr, data, false);	
		this.sleep_ms(1);
	}

	public void exitUserCalibrationMode() throws MbedJsException
	{
		byte [] data ={0x45};
		this._i2c.write(this._addr, data, false);	
		this.sleep_ms(14);
	}

	public void saveOpModeToEEPROM() throws MbedJsException
	{
		byte [] data ={0x4c};
		this._i2c.write(this._addr, data, false);
		this.sleep_ms(1);
	}
	public float getData() throws MbedJsException
	{
		byte [] data ={0x41};
		this._i2c.write(this._addr, data, false);
		this.sleep_ms(6);
		I2C.ReadResult rr = this._i2c.read(this._addr, 2, false);
		float retval = (((rr.data[0]& 0x0ff) << 8) | (rr.data[1]& 0x0ff)) /10.0f;
		return retval;
	}
	/* 他のクラスと同じ名前のデータ取得関数*/
	public float read() throws MbedJsException
	{
		return this.getData();
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
