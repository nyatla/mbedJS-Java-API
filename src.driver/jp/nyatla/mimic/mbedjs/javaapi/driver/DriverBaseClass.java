package jp.nyatla.mimic.mbedjs.javaapi.driver;

import jp.nyatla.mimic.mbedjs.MbedJsException;

public class DriverBaseClass
{
	/**
	 * ms単位のスリープ
	 * @param i_ms
	 * @throws MbedJsException
	 */
	protected synchronized void sleep_ms(long i_ms) throws MbedJsException
	{
		try {
			this.wait(i_ms);
		} catch (InterruptedException e) {
			throw new MbedJsException(e);
		}
		return;
	}
}
