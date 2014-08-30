package test.hara41;

import jp.nyatla.mimic.mbedjs.*;
import jp.nyatla.mimic.mbedjs.javaapi.*;
import jp.nyatla.mimic.mbedjs.javaapi.driver.utils.DriverBaseClass;

public class L6470 extends DriverBaseClass{
	public final static int PARAM_ABS_POS = 0x01;
	public final static int LENGTH_ABS_POS = 22;
	public final static int PARAM_ACC = 0x05;
	public final static int LENGTH_ACC = 12;
	public final static int PARAM_DEC = 0x06;
	public final static int LENGTH_DEC = 12;
	public final static int PARAM_MAX_SPEED = 0x07;
	public final static int LENGTH_MAX_SPEED = 10;
	public final static int PARAM_MIN_SPEED = 0x08;
	public final static int LENGTH_MIN_SPEED = 13;
	private final SPI spi;
    private final DigitalOut cs;
    public L6470(Mcu i_mcu , int i_mosi_pin, int i_miso_pin, int i_sclk_pin,
    		int i_cs) throws MbedJsException
    {
    	this.spi = new SPI(i_mcu,i_mosi_pin,i_miso_pin, i_sclk_pin);// 50ms
    	this.spi.frequency(1000000);
    	this.spi.format(8, 3);
    	
    	this.cs = new DigitalOut(i_mcu , i_cs);
    	
    	this.init();
    	//this.setParam2(PARAM_ABS_POS, 0x3fff38, LENGTH_ABS_POS);
    	/*
    	int[] s0 = {0x3f ,0xff , 0x38};
    	this.setParam(PARAM_ABS_POS,s0, LENGTH_ABS_POS);
    	int ret = this.getParam(PARAM_ABS_POS, LENGTH_ABS_POS);
    	
    	System.out.println(String.format("%1$x", ret));
    	*/
    	/*
    	this.sendByte(0x69);
    	this.sendByte(0x00);
    	this.sendByte(0x07);
    	this.sendByte(0xD0);
    	*/
    	this.goTo_dir(0, -2000);//ok
    	//this.run(1, 100); // ok
    	//this.move(1, 400); 
    	//this.goTo(100); // ok
    }
    public void goTo_dir(int i_dir , int i_abs_pos) throws MbedJsException
    {
     	int[] value = new int[4]; 
    	value[0] = 0x68 | i_dir;
    	value[1] = (0xff0000 & i_abs_pos)>>16;
    	value[2] = (0x00ff00 & i_abs_pos)>>8;
    	value[3] = (0x0000ff & i_abs_pos);
    	this.sendRecive(value, 4);
    }
    public void goTo(int i_abs_pos) throws MbedJsException
    {
     	int[] value = new int[4]; 
    	value[0] = 0x60 ;
    	value[1] = (0xff0000 & i_abs_pos)>>16;
    	value[2] = (0x00ff00 & i_abs_pos)>>8;
    	value[3] = (0x0000ff & i_abs_pos);
    	this.sendRecive(value, 4);
    
    }
    public void move(int i_dir , int i_n_step) throws MbedJsException
    {
     	int[] value = new int[4]; 
    	value[0] = 0x40 | i_dir;
    	value[1] = (0xff0000 & i_n_step)>>16;
    	value[2] = (0x00ff00 & i_n_step)>>8;
    	value[3] = (0x0000ff & i_n_step);
    	this.sendRecive(value, 4);
    	
    }
    public void run(int i_dir,int i_spd) throws MbedJsException
    {
    	int[] value = new int[4]; 
    	value[0] = 0x50 | i_dir;
    	value[1] = (0xff0000 & i_spd)>>16;
    	value[2] = (0x00ff00 & i_spd)>>8;
    	value[3] = (0x0000ff & i_spd);
    	this.sendRecive(value, 4);
    	
    }
    private void init() throws MbedJsException
    {
       	this.cs.write(1);
       	this.resetDevice(); // ok
    	
        int ret;	
    	int [] s1 = {0x03 , 0xe8};
    	this.setParam(L6470.PARAM_ACC, s1, L6470.LENGTH_ACC);
    	
    	int [] s2 = {0x03 , 0xe8};
    	this.setParam(L6470.PARAM_DEC, s2, L6470.LENGTH_DEC);
    	
    	
    	int [] s3 = {0x00 , 0x23};
    	this.setParam(L6470.PARAM_MAX_SPEED, s3 , L6470.LENGTH_MAX_SPEED);
    	
    	int [] s4 = {0x00 ,0x00};
    	this.setParam(L6470.PARAM_MIN_SPEED, s4 , L6470.LENGTH_MIN_SPEED);
    	
  	
    }
    private void view_ret(int[]ret)
    {
    	int value = 0;
    	for (int i=0;i<ret.length;i++)
    	{
    		//System.out.println(String.format("%1$x", ret[i]));
    		value = (value<<8)|ret[i];
    	}
    	System.out.println(String.format("%1$x", value));
    }
    
    public void view_ret(int i_param, int[] i_value,int i_len) throws MbedJsException
    {
    	int value = (i_param & 0x1f);
    	int length = i_len / 8;
    	if(i_len%8!=0){
    		length =length +1;
    	}
    	
    	// 書き込み対策、2度書き込む
    	for (int j=0;j<2;j++){
    		this.sendByte(value);
    	
	    	for(int i=0;i<length ; i++)
	    	{
	    		//System.out.println(String.format(">send: %1$x", i_value[i]));
	    		this.sendByte(i_value[i]);
	    	}
    	}
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
    		//System.out.println(String.format(">send: %1$x", i_value[i]));
    		ret =this.sendByte(i_value[i]);
    	}
    }
    public int getParam(int i_param, int i_len) throws MbedJsException
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
	
		System.out.println(String.format("getParam: %1$x", retval));
		return retval;
	}

    private int[] sendRecive(int[] i_value, int i_length) throws MbedJsException
    {    	
    	int [] retval = new int[i_length]; 
    	
    	for(int i=0;i<i_length;i++){
    		retval[i] = this.sendByte(i_value[i]);    		
    	}
    	// 書き込み対策　2度書き込む
    	for(int i=0;i<i_length;i++){
    		retval[i] = this.sendByte(i_value[i]);    		
    	}
    	return retval;
    }
    

    public void resetDevice() throws MbedJsException
    {
    	int []str = {0xc0};
    	this.sendRecive(str, 1);
    }
    
    private int sendByte(int i_value) throws MbedJsException
    {
    	this.sleep_ms(1);
    	this.cs.write(0);
    	this.sleep_ms(1);
    	
    	//System.out.println(String.format("sendByte-send: %1$x", i_value));
    	int ret = this.spi.write(i_value);

    	this.sleep_ms(1);
    	this.cs.write(1);
    	this.sleep_ms(1);
    	
    	//System.out.println(String.format("sendByte-recv: %1$x", ret));
    	return ret;

    	
    }

    
	public static void main(String[] args) throws MbedJsException {
		// TODO Auto-generated method stub
		Mcu mcu = new Mcu("10.0.0.2");
		L6470 amp = new L6470(mcu ,PinName.p5 , PinName.p6 ,PinName.p7 ,PinName.p8);
		mcu.close();
		System.out.println("done.");
	}
	/*
    public void setParam2(int i_param, int i_value,int i_len) throws MbedJsException
    {
    	// 送信文字列の数決定
    	int length = i_len / 8;
    	if(i_len%8!=0){
    		length =length +1;
    	}
    	int[] value = new int[length];
    	int tmp = i_value;
    	//送信データの分割
    	for(int i=length;i>0;i--){
    		value[i-1] = tmp & 0xff;
    		tmp = tmp >> 8;
    	}
    	view_ret(value);
    	
    	this.sendRecive(value,length);
    	
    }
    */

}
