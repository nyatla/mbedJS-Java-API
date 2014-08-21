package test.hara41;

import jp.nyatla.mimic.mbedjs.*;
import jp.nyatla.mimic.mbedjs.javaapi.*;
import jp.nyatla.mimic.mbedjs.javaapi.driver.utils.DriverBaseClass;

public class StepperMotor extends DriverBaseClass{

	private Mcu mcu;
	private DigitalOut CtrlPinA;
	private DigitalOut CtrlPinB;
	private BusOut bus;
	private int[] mstep = {0xc,0x6,0x3,0x9};
	private int[] pstep = {0x9,0x3,0x6,0xc};
	
	public StepperMotor(Mcu i_mcu,int i_ctrlA,int i_ctrlB ,
			int i_in1,int i_in2,int i_in3, int i_in4) throws MbedJsException
	{
		this.mcu = i_mcu;
		this.CtrlPinA = new DigitalOut(this.mcu,i_ctrlA);
		this.CtrlPinB = new DigitalOut(this.mcu,i_ctrlB);
		this.bus = new BusOut(this.mcu,i_in1,i_in2,i_in3,i_in4);
		
		this.CtrlPinA.write(1);
		this.CtrlPinB.write(1);
	}
	public void rotate(int i_step , int i_wait_ms) throws MbedJsException
	{
		int s = Math.abs(i_step);
		if(i_step>0){
			for (int j=0;j<s;j++)
			{
				this.bus.write(mstep[j%4]);
				this.sleep_ms(i_wait_ms);
			}
		}else{
			for (int j=0;j<s;j++)
			{
				this.bus.write(pstep[j%4]);
				this.sleep_ms(i_wait_ms);
			}
		}
		
	}
	public void close() throws MbedJsException
	{
		this.CtrlPinA.write(0);
		this.CtrlPinB.write(0);
		this.bus.write(0);
	}
	public void dispose() throws MbedJsException
	{
		this.close();
	}
	public static void main(String[] args) throws MbedJsException {
		Mcu mcu =new Mcu("10.0.0.2");
		StepperMotor sm = new StepperMotor(mcu,PinName.p16,PinName.p17,
				PinName.p15,PinName.p19,PinName.p18,PinName.p20);
		sm.rotate(200, 20);
		sm.close();
		mcu.close();
		System.out.println("done");
		
	}
}
