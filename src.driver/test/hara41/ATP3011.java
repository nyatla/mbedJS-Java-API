
/** ATP3011 class
 *
 * AquesTalk pico LSI I2C interface
 * Example:
 * @code
 *      #include "ATP3011.h"
 *      ATP3011 talk(P0_10,P0_11); // I2C sda scl
 *      
 *      int main() {
 *          talk.Synthe("konnichiwa.");
 *          for(int n = 1; ; n++) {
 *              char buf[32];
 *              snprintf(buf, sizeof(buf), "<NUMK VAL=%d>.", n);
 *              talk.Synthe(buf);
 *          }
 *      } 
 * @endcodeß
 *
 */

package test.hara41;

import jp.nyatla.mimic.mbedjs.*;
import jp.nyatla.mimic.mbedjs.javaapi.*;
import jp.nyatla.mimic.mbedjs.javaapi.driver.DriverBaseClass;

public class ATP3011 extends DriverBaseClass{
	public final static int I2C_ADDRESS=(0x2E<<1); 
	public final static int AQTK_STARTUP_WAIT_MS = 80;
	public final static int AQTK_POLL_WAIT_MS = 10;
	 
	private final I2C _i2c;
	private final int _addr;
	private Timer _poll_wait;
	/** I2Cを内部生成したか*/
	private final boolean _is_attached;
	/**
	 * 既存のI2Cに追加する場合
	 * @param i_i2c
	 * @param i_address
	 * @throws MbedJsException
	 */
	public ATP3011(I2C i_i2c,int i_address) throws MbedJsException
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
	public ATP3011(Mcu i_mcu, int sda, int scl, int i_address) throws MbedJsException
	{
		this._is_attached=true;
		this._i2c=new I2C(i_mcu, sda, scl);
		this._addr=i_address;
		this._i2c.frequency(10000);
		this._initDevice();
	}
	private void _initDevice()
	{
	    this._poll_wait.reset();
	    this._poll_wait.start();
	}

	public void dispose() throws MbedJsException{
		if(this._is_attached){
			this._i2c.dispose();
		}
	}

 
	public boolean IsActive(int timeout_ms) throws MbedJsException
	{
	    sleep_ms(ATP3011.AQTK_STARTUP_WAIT_MS);
	    Timer t = new Timer();
	    t.reset();
	    t.start();
	    while(t.read_ms() < timeout_ms) {
	        this._poll_wait.reset();
	        if (this._i2c.write(this._addr,new byte[]{0x00}, false) == 0) {
	            return true;
	        }
	        sleep_ms(ATP3011.AQTK_POLL_WAIT_MS);
	    }
	    return false;
	}
    
public void Synthe(byte[] msg) throws MbedJsException
{
    while(this.IsBusy()) {
        ;
    }
    this.Write(msg);
    this.Write(new byte[]{'\r'});
}
 
public void Write(byte[] msg) throws MbedJsException
{
    this._i2c.write(this._addr, msg, false);    
    this._poll_wait.reset();
}
 
public boolean IsBusy() throws MbedJsException
{
    if (ATP3011.AQTK_POLL_WAIT_MS > this._poll_wait.read_ms()) {
        return true;
    } 
    this._poll_wait.reset();
    I2C.ReadResult rr= this._i2c.read(_addr, 1, false);
    byte c = rr.data[0];
    if (c  != 0) {
        return false;
    }
    return c == '*' || c == 0xff;
}
	
	public static void main(String[] args) throws MbedJsException {
		// TODO Auto-generated method stub
		Mcu mcu = new Mcu("10.0.0.2");
		ATP3011 talk = new ATP3011(mcu,PinName.P0_10 , PinName.P0_11,ATP3011.I2C_ADDRESS);
		for(int n=1 ; ; n++)
		{
			String str = String.format("<NUMK VAL={0}>.", n);
			byte[] msg = new byte[32];
			msg = str.getBytes();
			talk.Synthe(msg);
		}
	}

}
