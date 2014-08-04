package jp.nyatla.mimic.mbedjs.javaapi.driver;

import jp.nyatla.mimic.mbedjs.*;
import jp.nyatla.mimic.mbedjs.javaapi.*;

/**
 * http://mbed.org/users/chris/code/LM75B/ をmbedJS-Javaに移植したものです
 * 
 * @author hara4_000
 * 
 */
public class LM75B {
	private Mcu _mcu;
	private I2C i2c;
	private int addr;

	public LM75B(Mcu mcu, int sda, int scl, int address)
	{
		_mcu = mcu;
		addr = address;
		byte[] str = { 0x01, 0x00 };
		try {
			i2c = new I2C(_mcu, sda, scl);
			i2c.frequency(1000);
			i2c.write(addr, str, false);
		} catch (MbedJsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void dispose() {
		try {
			i2c.stop();
		} catch (MbedJsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public float read() {
		byte[] str = { 0x00 };
		I2C.ReadResult rr = null;
		try {
			i2c.write(addr, str, false);
			rr = i2c.read(addr, 2, false);
		} catch (MbedJsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		float ret;
		ret = ((rr.data[0] << 8) | rr.data[1]) / 256.0f;

		return ret;
	}
}