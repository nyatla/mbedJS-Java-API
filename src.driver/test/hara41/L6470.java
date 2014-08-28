package test.hara41;

import jp.nyatla.mimic.mbedjs.*;
import jp.nyatla.mimic.mbedjs.javaapi.*;

public class L6470 {
    private final SPI spi;
    private final DigitalOut cs;
    public L6470(Mcu i_mcu , int i_mosi_pin, int i_miso_pin, int i_sclk_pin,
    		int i_cs) throws MbedJsException
    {
    	this.spi = new SPI(i_mcu,i_mosi_pin,i_miso_pin, i_sclk_pin);// 50ms
    	this.cs = new DigitalOut(i_mcu , i_cs);
    	this.cs.write(1);
    	
    }
    
	public static void main(String[] args) throws MbedJsException {
		// TODO Auto-generated method stub
		Mcu mcu = new Mcu("10.0.0.2");
		L6470 amp = new L6470(mcu ,PinName.p5 , PinName.p6 ,PinName.p7 ,PinName.p8);
	}


}
