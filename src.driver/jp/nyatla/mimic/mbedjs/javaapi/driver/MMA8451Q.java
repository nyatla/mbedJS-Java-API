/**
 * 加速度センサMMA8451Q
 * http://cache.freescale.com/files/sensors/doc/data_sheet/MMA8451Q.pdf?fpsp=1
 */
package jp.nyatla.mimic.mbedjs.javaapi.driver;

import jp.nyatla.mimic.mbedjs.MbedJsException;
import jp.nyatla.mimic.mbedjs.Vector3d;
import jp.nyatla.mimic.mbedjs.javaapi.I2C;
import jp.nyatla.mimic.mbedjs.javaapi.Mcu;
import jp.nyatla.mimic.mbedjs.javaapi.PinName;
import jp.nyatla.mimic.mbedjs.javaapi.driver.utils.DriverBaseClass;

public class MMA8451Q extends DriverBaseClass
{
	public final static int I2C_ADDRESS=0x98;
	private final I2C _i2c;
	private final int _addr;
	/** I2Cを内部生成したか*/
	private final boolean _is_attached;
	private float toInt14f(byte[] i_v){
	    int t = ((0x000000ff & i_v[0]) << 6) | ((0x000000ff & i_v[1]) >> 2);
	    if (t > 16383/2){
	    	t -= 16383;
	    }
	    return t/4096f;
	}	
	/**
	 * 既存のI2Cに追加する場合
	 * @param i_i2c I2Cインスタンス
	 * @param i_address I2Cアドレス
	 * @throws MbedJsException MbedJS例外
	 */
	public MMA8451Q(I2C i_i2c,int i_address) throws MbedJsException
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
	public MMA8451Q(Mcu i_mcu, int sda, int scl, int i_address) throws MbedJsException
	{
		this._is_attached=true;
		this._i2c=new I2C(i_mcu, sda, scl);
		this._addr=i_address;
		this._i2c.frequency(100000);
		this._initDevice();
	}
	private void _initDevice() throws MbedJsException
	{
		this._i2c.write(this._addr,new byte[]{0x2a,0x01},false);
	}

	public void dispose() throws MbedJsException{
		if(this._is_attached){
			this._i2c.dispose();
		}
	}
	/**
	 * デバイスIDの確認
	 * @return デバイスID
	 * @throws MbedJsException MbedJS例外
	 */
	public int getWhoAmI() throws MbedJsException
	{
		this._i2c.write(this._addr,new byte[]{0x0d},true);
		I2C.ReadResult ret=this._i2c.read(this._addr,1,false);
		return ret.data[0]&0xffffffff;
	};
	private float getXX(byte i_reg) throws MbedJsException
	{
		this._i2c.write(this._addr,new byte[]{i_reg},true);
		I2C.ReadResult ret=this._i2c.read(this._addr,2,false);
		return toInt14f(ret.data);
	};
	/**
	 * X方向の加速度
	 * @return X方向の加速度
	 * @throws MbedJsException MbedJS例外
	 */
	public float getAccX() throws MbedJsException
	{	
		return this.getXX((byte)0x01);
	};
	/**
	 * Y方向の加速度
	 * @return Y方向の加速度
	 * @throws MbedJsException MbedJS例外
	 */
	public float getAccY() throws MbedJsException
	{	
		return this.getXX((byte)0x03);
	};
	/**
	 * Z方向の加速度
	 * @return Z方向の加速度
	 * @throws MbedJsException MbedJS例外
	 */
	public float getAccZ() throws MbedJsException
	{	
		return this.getXX((byte)0x05);
	};
	/**
	 * 全方位の加速度を取得
	 * @return 全方位の加速度{x,y,z}
	 * @throws MbedJsException MbedJS例外
	 */
	public Vector3d getAccAllAxis() throws MbedJsException
	{
		return new Vector3d(
			this.getXX((byte)0x01),
			this.getXX((byte)0x03),
			this.getXX((byte)0x05)
		);
	}
	// テストケース
	public static void main(String args[]){
		try {
			Mcu mcu=new Mcu("10.0.0.2");
			MMA8451Q a=new MMA8451Q(mcu,PinName.p28,PinName.p27,I2C_ADDRESS);
			System.out.println("whoami="+a.getWhoAmI());
			System.out.println("x="+a.getAccX());
			System.out.println("y="+a.getAccY());
			System.out.println("z="+a.getAccZ());
			System.out.println("all="+a.getAccAllAxis());
			mcu.close();
			System.out.println("done");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
