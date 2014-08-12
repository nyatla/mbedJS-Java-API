
/*
Copyright (c) 2010 Donatien Garnier (donatiengar [at] gmail [dot] com)
 
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:
 
The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.
 
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.

https://mbed.org/users/chris/code/TMP102/
*/
/**
 * modified by hara41
 */
/**
 * modified by nyatla
 */
package jp.nyatla.mimic.mbedjs.javaapi.driver;

import jp.nyatla.mimic.mbedjs.*;
import jp.nyatla.mimic.mbedjs.javaapi.*;

public class TMP102 {
	public final static int I2C_ADDRESS=0x90;
	private final static byte TEMP_REG_ADDR=0x00;
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
	public TMP102(I2C i_i2c,int i_address) throws MbedJsException
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
	public TMP102(Mcu i_mcu, int sda, int scl, int i_address) throws MbedJsException
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
	/**
	 * @return 温度
	 * @throws MbedJsException
	 */
	public float read() throws MbedJsException
	{
		byte[] tempRegAddr = { TMP102.TEMP_REG_ADDR };

		this._i2c.write(this._addr, tempRegAddr, false);

		I2C.ReadResult rr = this._i2c.read(this._addr, 2, false);
		// 16bitに変換
		return tempreg2temp(rr.data);
	}
	/**
	 * See http://www.ti.com/lit/ds/symlink/tmp102.pdf
	 * @param i_data
	 * @return
	 */
	private static float tempreg2temp(byte[] i_data){
		// 16bitに変換
		int res = ((int) (i_data[0] & 0x0ff) << 8) | (i_data[1] & 0x0ff);
		// EMビットの値をチェック
		if ((res & 0x01) != 0) {
			// ExtendMode(13bit)
			res = res >> 3;
			//13bit signed int to 32bit signed int
			if((res&0x00001000)!=0){
				res|=0xfffff000;
			}
		} else {
			// Normal(12bit)
			res = res >> 4;
			//12bit signed int to 32bit signed int
			if((res&0x00000800)!=0){
				res|=0xfffff800;
			}
		}
		return (float) ((float) res * 0.0625);
	}
	public static void main(String[] args)
	{
		try {
			System.out.println(tempreg2temp(new byte[]{(byte)0x7f,(byte)0xf0}));
			System.out.println(tempreg2temp(new byte[]{(byte)0x64,(byte)0x00}));
			System.out.println(tempreg2temp(new byte[]{(byte)0xff,(byte)0xc0}));

			System.out.println(tempreg2temp(new byte[]{(byte)0x4b,(byte)0x01}));
			System.out.println(tempreg2temp(new byte[]{(byte)0xff,(byte)0xe1}));
			
			Mcu mcu=new Mcu("10.0.0.2");
			TMP102 a=new TMP102(mcu,PinName.p28,PinName.p27,0x90);
			System.out.println(a.read());
			mcu.close();
			System.out.println("done");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
