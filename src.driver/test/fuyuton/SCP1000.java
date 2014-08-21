/**
 * @section LICENSE
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 * @section DESCRIPTION
 * Library to interface with the SCP1000 pressure and temperature sensor.
 */



/**
 * Class to interface with the SCP1000 pressure and temperature sensor.
 */


package test.fuyuton;


import jp.nyatla.mimic.mbedjs.MbedJsException;
import jp.nyatla.mimic.mbedjs.javaapi.Mcu;
import jp.nyatla.mimic.mbedjs.javaapi.PinName;
import jp.nyatla.mimic.mbedjs.javaapi.SPI;
import jp.nyatla.mimic.mbedjs.javaapi.DigitalOut;
import jp.nyatla.mimic.mbedjs.javaapi.DigitalIn;
import jp.nyatla.mimic.mbedjs.javaapi.driver.DriverBaseClass;

/**
 * http://mbed.org/cookbook/SCP1000-Pressure-Sensor/ をmbedJS-Javaに移植したものです。
 *
 * @author fuyuton
 *
 */
public class SCP1000 extends DriverBaseClass
{
	private Mcu _mcu;

	private final SPI _spi;
	private final DigitalOut _cs;
	private final DigitalIn _drdy;
	private final boolean _is_attached;
	
	private final static int REG_OPERATION = 0x03;
	private final static int MODE_HIRESO = 0x0A;
	private final static int REG_RSTR = 0x06;
	private final static int RST_SOFTRESET = 0x01;
	private final static int REG_PRESSURE = 0x1F;   //Pressure 3 MSB
	private final static int REG_PRESSURE_LSB = 0x20; //Pressure 16 LSB
	private final static int REG_TEMP = 0x21;       //16 bit temp

   	/**
   	 * 既存のSPIに追加する場合
   	 * @param i_spi SPI
   	 * @param i_cs_pin Chip Select pin
     * @param i_drdy_pin DataReady pin
   	 * @throws MbedJsException
   	 */
   	public SCP1000(SPI i_spi, int i_cs_pin, int i_drdy_pin) throws MbedJsException
   	{
   		this._is_attached=false;
   		this._spi=i_spi;
		this._cs=new DigitalOut(_mcu, i_cs_pin);
		this._drdy=new DigitalIn(_mcu, i_drdy_pin);
   		this._initDevice();
   	}
        /**
         * Constructor.
         * MCUから直接生成する場合
         * 秋月版はPDがGNDにつながっているため、Power managementしないモードになる
         * @param i_mcu
         * @param i_mosi_pin SPI MOSI pin
         * @param i_miso_pin SPI MISO pin
         * @param i_sclk_pin SPI SCLK pin
         * @param i_cs_pin Chip select pin
         * @param i_drdy_pin DataReady pin
         * @throws MbedJsException
         */
	public SCP1000(Mcu i_mcu, int i_mosi_pin, int i_miso_pin, int i_sclk_pin, int i_cs_pin, int i_drdy_pin)
			throws MbedJsException {
		this._cs=new DigitalOut(i_mcu, i_cs_pin);
		this._drdy=new DigitalIn(i_mcu, i_drdy_pin);


		this._is_attached=true;
		this._spi=new SPI(i_mcu, i_mosi_pin, i_miso_pin, i_sclk_pin);
   		this._spi.frequency(500000); // the fastest of the sensor
   		this._spi.format(8, 0); // duda son dos palabras de 8 bits?
   		this._initDevice();
	}

	private void _initDevice() throws MbedJsException
	{
		this._cs.write(1);
    	this.sleep_ms(60);
   		this.write_register(REG_RSTR,RST_SOFTRESET);
   		this.sleep_ms(90);
    	write_register(REG_OPERATION,MODE_HIRESO);
	}

	public void dispose() throws MbedJsException
    {
		if(this._is_attached)
    	{
			this._spi.dispose();
    	}

    }

	/**
	* 気圧の読み込み
	* @throws MbedJsException
	*/
	public float readPressure() throws MbedJsException
	{
		do{
			//DRDYがHIGH(1)になるまで待つ
			sleep_ms(1);
		}while(this._drdy.read() != 1);
		int pressure_msb = read_register(REG_PRESSURE);
		pressure_msb &= 0x07;	
		int pressure_lsb = read_register16(REG_PRESSURE_LSB);
		pressure_lsb &= 0x0000ffff;
		int pressure = ((pressure_msb<<16)| pressure_lsb);
		float pressure_f = pressure/4.0f/100.0f;
		return pressure_f;
	}

    /**
     * 温度の読み込み
     * @throws MbedJsException
     */
	public float readTemperature() throws MbedJsException
	{
		do{
			//DRDYがHIGH(1)になるまで待つ
			sleep_ms(1);
		}while(this._drdy.read() != 1);
		float temp_in = read_register16(REG_TEMP);
		temp_in /= 20;
		return temp_in;
	}

	/**
	 * 8bitレジスタの読み込み
	 * @param i_register_name
	 * @throws MbedJsException
	 */
	private int read_register(int i_register_name) throws MbedJsException
	{
		i_register_name <<= 2;
		i_register_name &= 0xFC;
		this._cs.write(0); //Select SPI device
		this._spi.write(i_register_name); //Send register location
		int register_value = _spi.write(0x00);
		this._cs.write(1);
		return register_value;
	}

	/**
	 * 8bitレジスタの書き込み
	 * @param i_register_name
	 * @param i_register_value
	 * @throws MbedJsException
	 */
	private void write_register(int i_register_name, int i_register_value) throws MbedJsException
	{
		i_register_name &= 0x0ff;
		i_register_value &= 0x0ff;
		i_register_name <<= 2;
		i_register_name |= 0x02; // Write command
		i_register_name &= 0x0fe;
		this._cs.write(0); //Select SPI device
		this._spi.write(i_register_name); //Send register location
		this._spi.write(i_register_value); //Send value to record into register
		this._cs.write(1);
	}

	/**
	 * 16bitレジスタの読み込み
	 * @param i_register_name
	 * @throws MbedJsException
	 */
	private int read_register16(int i_register_name) throws MbedJsException
	{
		i_register_name &= 0x000000ff;
    	i_register_name <<= 2;
    	i_register_name &= 0x0FC; //Read command
    	this._cs.write(0); //Select SPI Device
    	this._spi.write(i_register_name); //Write byte to device
    	int in_byte1 = this._spi.write(0x00);
    	int in_byte2 = this._spi.write(0x00);
    	this._cs.write(1);
    	int in_word= (in_byte1<<8) | (in_byte2);
    	in_word &= 0x0000ffff;
   		return(in_word);
	}

	/**
	 * テストケース
	 * @param args
	 */
	public static void main(String args[])
	{
		try
		{
			Mcu mcu=new Mcu("192.168.1.39");
			SCP1000 a=new SCP1000(mcu, PinName.p11, PinName.p12, PinName.p13, PinName.p14, PinName.p15);
			System.out.println("Temperature: " + a.readTemperature()+"℃");
			System.out.println("Pressure: " + a.readPressure() + "hPa");
			mcu.close();
			System.out.println("done");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}


