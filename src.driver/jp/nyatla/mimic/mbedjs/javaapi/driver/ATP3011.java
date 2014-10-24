/** ATP3011 class
 *
 * AquesTalk pico LSI I2C interface
 * http://mbed.org/users/va009039/code/ATP3011/
 */
/**
 * modified by hara.shinichi@gmail.com
 */
package jp.nyatla.mimic.mbedjs.javaapi.driver;

import jp.nyatla.mimic.mbedjs.*;
import jp.nyatla.mimic.mbedjs.javaapi.*;
import jp.nyatla.mimic.mbedjs.javaapi.driver.utils.DriverBaseClass;

public class ATP3011 extends DriverBaseClass{
	/**
	 * 7bit I2Cアドレスです。
	 */
	public final static int I2C_ADDRESS=(0x2E); 
	public final static int AQTK_STARTUP_WAIT_MS = 80;
	public final static int AQTK_POLL_WAIT_MS = 10;
	private long _last_call_in_msec;
	 
	private final I2C _i2c;
	private final int _addr;
	/** I2Cを内部生成したか*/
	private final boolean _is_attached;
	/**
	 * 既存のI2Cをインスタンスに追加する場合
	 * @param i_i2c I2Cインスタンス
	 * @param i_address I2Cアドレス
	 * @throws MbedJsException MbedJS例外
	 */
	public ATP3011(I2C i_i2c,int i_address) throws MbedJsException
	{
		this._is_attached=false;
		this._i2c=i_i2c;
		this._addr=i_address;
		this._initDevice();
	}
	/**
	 * Mcuから直接インスタンスを生成する場合
	 * @param i_mcu MCUインスタンス
	 * @param sda SDAピン
	 * @param scl SCLピン
	 * @param i_address　I2Cアドレス
	 * @throws MbedJsException　MbedJS例外
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
		this._last_call_in_msec=System.currentTimeMillis();
	}

	public void dispose() throws MbedJsException{
		if(this._is_attached){
			this._i2c.dispose();
		}
	}

	/**
	 * デバイスの検出
	 * @param i_timeout_ms タイムアウトする時間[ms]
	 * @return true:デバイスが存在
	 * @throws MbedJsException　MbedJS例外
	 */
	public boolean isActive(int i_timeout_ms) throws MbedJsException
	{
	    sleep_ms(ATP3011.AQTK_STARTUP_WAIT_MS);
	    long start=System.currentTimeMillis();
	    do{
	        if (this._i2c.write(this._addr,new byte[]{0x00}, false) == 0) {
	    	    this._last_call_in_msec=System.currentTimeMillis();
	            return true;
	        }
	        sleep_ms(ATP3011.AQTK_POLL_WAIT_MS);
	    }while(System.currentTimeMillis()-start<i_timeout_ms);
	    return false;
	}
    /**
     * 発声
     * @param i_msg 発声する文字列
     * @throws MbedJsException　MbedJS例外
     */
	public void synthe(byte[] i_msg) throws MbedJsException
	{
	    while(this.isBusy()){
	        this.sleep_ms(AQTK_POLL_WAIT_MS);
	    }
	    this.write(i_msg);
	    this.write(new byte[]{'\r'});
	}
	/**
	 * コマンドの書き込み
	 * @param i_msg 書き込む文字列
	 * @throws MbedJsException　MbedJS例外
	 */
	public void write(byte[] i_msg) throws MbedJsException
	{
	    this._i2c.write(this._addr, i_msg, false);
	    this._last_call_in_msec=System.currentTimeMillis();
	}
	/**
	 * ビジー状態のチェック
	 * @return true:ビジー
	 * @throws MbedJsException　MbedJS例外
	 */
	public boolean isBusy() throws MbedJsException
	{
		//最終呼び出し時刻チェック
		long now=System.currentTimeMillis();
		if(now-this._last_call_in_msec<AQTK_POLL_WAIT_MS){
			return true;
		}	
		this._last_call_in_msec=now;
		//I2C通信
		I2C.ReadResult rr= this._i2c.read(_addr, 1, false);
	    byte c = rr.data[0];
	    if (c  != 0) {
	        return false;
	    }
	    return c == '*' || c == 0xff;
	}
	
	public static void main(String[] args) throws MbedJsException {
		Mcu mcu = new Mcu("10.0.0.2");
		ATP3011 talk = new ATP3011(mcu,PinName.P0_10 , PinName.P0_11,ATP3011.I2C_ADDRESS<<1);
		for(int n=1 ; ; n++)
		{
			String str = String.format("ohayou", n);
			byte[] msg;
			msg = str.getBytes();
			talk.synthe(msg);
		}
	}

}
