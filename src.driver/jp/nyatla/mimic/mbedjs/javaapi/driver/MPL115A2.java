/**
 * original source code
 * http://mbed.org/users/yamaguch/code/MPL115A2/file/d77bd4340924/
 */
/**
 * modified by nyatla nyatla39@gmail.com
 */
package jp.nyatla.mimic.mbedjs.javaapi.driver;

import jp.nyatla.mimic.mbedjs.MbedJsException;
import jp.nyatla.mimic.mbedjs.javaapi.I2C;
import jp.nyatla.mimic.mbedjs.javaapi.Mcu;
import jp.nyatla.mimic.mbedjs.javaapi.driver.utils.DriverBaseClass;

public class MPL115A2 extends DriverBaseClass {
	/**
	 * 7bitアドレスです。mbedSDKに指定するときは(0x60<<1)を指定してください。
	 */
	public final static int I2C_ADDRESS = 0x60;
	private final I2C _i2c;
	private final int _addr;
	/** I2Cを内部生成したか */
	private final boolean _is_attached;
	private static float int2float(byte ch, byte cl, int i_nbits, int fbits, int zpad)
	{
		//16bit->32bit intに変換
		int v = ((ch & 0x0ff) << 8) | (cl & 0x0ff);
		if ((v&0x8000) != 0){
			v = 0xffff8000|v;
		}
		//未使用ビットの削除(sign付Rシフト)
		v = v >> (16-i_nbits);
		int d = 1 << (fbits + zpad);
		return (float)v / d;
	}
	
	public MPL115A2(I2C i_i2c, int i_address) throws MbedJsException {
		this._is_attached = false;
		this._i2c = i_i2c;
		this._addr = i_address;
		this._initDevice();
	}

	/**
	 * Mcuから直接生成する場合
	 * 
	 * @param i_mcu
	 * @param sda
	 * @param scl
	 * @param i_address
	 * @throws MbedJsException
	 */
	public MPL115A2(Mcu i_mcu, int sda, int scl, int i_address)
			throws MbedJsException {
		this._is_attached = true;
		this._i2c = new I2C(i_mcu, sda, scl);
		this._addr = i_address;
		this._i2c.frequency(10000);
		this._initDevice();
	}
	private void _initDevice() throws MbedJsException {
		//nothing to do
	}
	public void dispose() throws MbedJsException{
		if(this._is_attached){
			this._i2c.dispose();
		}
	}	
	/**
	 * センサからの生バイト列を返します。
	 * @return
	 * @throws MbedJsException
	 */
	public byte[] readRaw() throws MbedJsException{
		// start AD conversions
		this._i2c.write(this._addr, new byte[] { 0x12, 0x01 }, false);
		// omit checking !=0
		this.sleep_ms(1);
		this._i2c.write(this._addr, new byte[] { 0x00 }, true);
		// omit checking !=0

		// compensation
		I2C.ReadResult r = _i2c.read(this._addr, 12, false);
		// omit checking !=0
		return r.data;
	}
	/**
	 * 気温と湿度を得ます。
	 * @return
	 * [0] - 気圧(kPa)
	 * [1] - 温度(°C)
	 * @throws MbedJsException 
	 */
	public float[] read() throws MbedJsException
	{
		byte[] data=this.readRaw();
        int padc = ((data[0]&0x0ff) << 2) | ((data[1]&0x0ff) >> 6);
        int tadc = ((data[2]&0x0ff) << 2) | ((data[3]&0x0ff) >> 6);
        float a0 = int2float(data[4],data[5], 16, 3, 0);
        float b1 = int2float(data[6],data[7], 16, 13, 0);
        float b2 = int2float(data[8],data[9], 16, 14, 0);
        float c12 = int2float(data[10],data[11], 14, 13, 9);
 
        float pcomp = a0 + (b1 + c12 * tadc) * padc + (b2) * tadc;
        return new float[]{pcomp * 650f / 1023f + 500f,(25f + ((tadc - 498.0f) / -5.35f))};
	}
	/**
	 * 温度センサの値を返します。
	 * @return float - The local pressure in °C 
	 * @throws MbedJsException 
	 **/
	public float getTemperature() throws MbedJsException
	{
	    return this.read()[1];
	}
	/**
	 * 気圧センサの値を返します。
	 * @return
	 * @throws MbedJsException
	 */
	public float getPressure() throws MbedJsException
	{
	    return this.read()[0];
	}

	public static void main(String args[]) {
		byte data[] = {0x66,(byte) 0x80,0x7e,(byte) 0xc0,
				0x3e,(byte) 0xce,
				(byte) 0xb3,(byte) 0xf9,
				(byte) 0xc5,0x17,
				0x33,(byte) 0xc8,0x00,0x00,0x00};
        int padc = ((data[0]&0x0ff) << 2) | ((data[1]&0x0ff) >> 6);
        int tadc = ((data[2]&0x0ff) << 2) | ((data[3]&0x0ff) >> 6);
        float a0 = int2float(data[4],data[5], 16, 3, 0);
        float b1 = int2float(data[6],data[7], 16, 13, 0);
        float b2 = int2float(data[8],data[9], 16, 14, 0);
        float c12 = int2float(data[10],data[11], 14, 13, 9);
 
        float pcomp = a0 + (b1 + c12 * tadc) * padc + (b2) * tadc;
        System.out.println(pcomp * 650f / 1023f + 500f);
        System.out.println(25f + ((tadc - 498.0f) / -5.35f));

		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
