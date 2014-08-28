/**
 * 
 * @author hara.shinichi@gmail.com
 */
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
    	this.spi.format(8, 0);
    	this.cs = new DigitalOut(i_mcu , i_cs);
    	this.cs.write(1);
    	int[] send = {0x00 ,0x23};
    	//this.setParam(0x17, send, 10 );
    	int ret = this.getParam(0x17 , 8);

    	
    }
    private void setParam(int i_param, int[] i_value,int i_len) throws MbedJsException
    {
    	int ret = 0;
    	int retval =0;
    	int value = (i_param & 0x1f);
    	int length = i_len / 8;
    	if(i_len%8!=0){
    		length =length +1;
    	}
    	this.sendByte(value);
    	
    	for(int i=0;i<length ; i++)
    	{
    		System.out.println(String.format(">send: %1$x", i_value[i]));
    		ret =this.sendByte(i_value[i]);
    	}
    }
    private int getParam(int i_param, int i_len) throws MbedJsException
    {
    	int ret = 0;
    	int retval =0;
    	int value = 0x20 | (i_param & 0x1f);
    	int length = i_len / 8;
    	if(i_len%8!=0){
    		length ++;
    	}
    	
    	this.sendByte(value);
    	for (int i=0;i<length ; i++){
    		ret =this.sendByte(0x00);
    		retval =(retval << 8 )| ret;
    	}

    	System.out.println(String.format(">recv: %1$x", retval));
    	return retval;
    }
    public void run(int i_dir,int i_spd)
    {
    	int value = 0x50 | i_dir;
    	
    }
    private int sendByte(int i_value) throws MbedJsException
    {
    	this.cs.write(0);
    	System.out.println(String.format("sendByte-send: %1$x", i_value));
    	int ret = this.spi.write(i_value);
    	this.cs.write(1);
    	
    	System.out.println(String.format("sendByte-recv: %1$x", ret));
    	return ret;
    }
    
	public static void main(String[] args) throws MbedJsException {
		// TODO Auto-generated method stub
		Mcu mcu = new Mcu("10.0.0.2");
		L6470 amp = new L6470(mcu ,PinName.p5 , PinName.p6 ,PinName.p7 ,PinName.p8);
		mcu.close();
		System.out.println("done.");
	}


}
