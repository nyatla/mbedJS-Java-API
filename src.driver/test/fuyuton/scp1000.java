/**
 * http://mbed.org/cookbook/SCP1000-Pressure-Sensor/ をmbedJS-Javaに移植したものです。
 * 
 * @auther fuyuton
 *
 */

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
 * #ifndef _SCP1000_H
 * #define _SCP1000_H

 * #include "mbed.h"
 */

/**
 * Class to interface with the SCP1000 pressure and temperature sensor.
 */

package jp.nyatla.mimic.mbedjs.javaapi.driver;

import jp.nyatla.mimic.mbedjs.*;
import jp.nyatla.mimic.mbedjs.javaapi.*;

public class SCP1000
{
	private Mcu _mcu;

       	/* SPI m_spi; */
	private final SPI _spi;

       	/* DigitalOut m_cs; */
	private final DigitalOut _cs;

	/* DRDYとTRIGはINかOUTか不明… */
	private final DigitalOut _drdy;
	private final DigitalOut _trig;

       	private final static char PRESSURE = 0x1F;   //Pressure 3 MSB
       	private final static char PRESSURE_LSB = 0x20; //Pressure 16 LSB
       	private final static char TEMP = 0x21;       //16 bit temp 

	/**
	 * ms単位のスリープ
	 * @param i_ms
	 * @throws MbedJsException
	 */
	private void sleep_ms(long i_ms) throws MbedJsException
	{
		try {
			this.wait(i_ms);
		} catch (InterruptedException e) {
			throw new MbedJsException(e);
		}
		return;
	}

/* public: */
        /**
         * Constructor.
         * MCUから直接生成する場合
	 * @param i_mcu
         * @param i_mosi SPI MOSI pin
         * @param i_miso SPI MISO pin
         * @param i_sclk SPI SCLK pin
         * @param i_cs Chip select pin
	 * @throws MbedJsException
         */
        /* SCP1000(PinName mosi, PinName miso, PinName sclk, PinName cs); */
	public SCP1000(int i_mosi_pin, int i_miso_pin, int i_sclk_pin, int i_cs_pin, int i_drdy_pin, int i_trig_pin)
	{
    		private final static m_cs=1;
		private final static wait_ms=500;

		this._is_attached=true;
		this.spi=new SPI(i_mcu, i_mosi_pin, i_miso_pin, i_sclk_pin);
   		this.spi.frequency(500000); // the fastest of the sensor
    		this.spi.format(8, 0); // duda son dos palabras de 8 bits? 
    		/* wait(0.5); */
    		sleep_ms(wait_ms);
    		//------------------------------------------------
    		// pc.printf("RESET\r\n");
    		write_register(0x06,0x01);
    		/* wait(0.5); */
    		sleep_ms(wait_ms);

    		// pc.printf("Initialize High Resolution Constant Reading Mode\r\n");
    		write_register(0x03,0x0A);
    		/* wait(0.5); */
    		sleep_ms(wait_ms);
	}

        
        public void dispose() throws MbedJsException
	{ 
		if(this._is_attached)
		{
			this.spi.dispose();
		}
		
	}

        /**
         * Read the pressure.
         *
         * @returns The pressure in pascals.
         */
        /** unsigned long readPressure(); */
	public unsigned long readPressure() throws MbedJsException 
	{
    		private unsigned long pressure_msb = read_register(PRESSURE);
    		pressure_msb &= 0x07;
    		private unsigned long pressure_lsb = read_register16(PRESSURE_LSB);
    		private unsigned long pressure = ((pressure_msb<<16)| pressure_lsb);
    		pressure /= 4;
   		 
    		return pressure;
	}
        
        /**
         * Read the temperature.
         *
         * @returns The temperature in Celsius.
         */
        /* float readTemperature(); */
	float readTemperature() throws MbedJsException
	{
		private float temp_in = read_register16(TEMP);
		temp_in /= 20;
		return temp_in;
	}




	char SCP1000::read_register(char i_register_name) throws MbedJsException
	{
    		i_register_name <<=2;
    		i_register_name &= 0xFC;
    		this._cs=0; //Select SPI device
    		this._spi.write(register_name); //Send register location
    		private char register_value=m_spi.write(0x00);
    		_cs=1;
    		return i_register_value;  
	}

	void SCP1000::write_register(char i_register_name, char i_register_value) throws MbedJsException
	{
    		i_register_name <<= 2;
    		i_register_name |= 0x02; //le estamos diciendo que escriba
    		this._cs=0; //Select SPI device
    		this._spi.write(register_name); //Send register location
    		this._spi.write(register_value); //Send value to record into register
    		this._cs=1;
	}

	float SCP1000::read_register16(char i_register_name) throws MbedJsException
	{
    		i_register_name <<= 2;
    		i_register_name &= 0xFC; //Read command
    		this._cs=0; //Select SPI Device
    		this._spi.write(register_name); //Write byte to device
    		private int in_byte1 = this._spi.write(0x00);    
    		private int in_byte2 = this._spi.write(0x00);
    		this._cs=1;
    		private float in_word= (in_byte1<<=8) | (in_byte2);   
    		return(in_word);
	}
}

/* #endif // _SCP1000_H */

