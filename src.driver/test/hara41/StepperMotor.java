package test.hara41;

import jp.nyatla.mimic.mbedjs.*;
import jp.nyatla.mimic.mbedjs.javaapi.*;
import jp.nyatla.mimic.mbedjs.javaapi.driver.utils.DriverBaseClass;

public class StepperMotor extends DriverBaseClass{

	private Mcu mcu;
	private DigitalOut CtrlPinA;
	private DigitalOut CtrlPinB;
	/*
	private DigitalOut In1;
	private DigitalOut In2;
	private DigitalOut In3;
	private DigitalOut In4;
	*/
	private BusOut bus;
	public StepperMotor(Mcu i_mcu) throws MbedJsException
	{
		this.mcu = i_mcu;
		this.CtrlPinA = new DigitalOut(this.mcu,PinName.p16);
		this.CtrlPinB = new DigitalOut(this.mcu,PinName.p17);
		/*
		this.In1 = new DigitalOut(this.mcu ,PinName.p15 );
		this.In2 = new DigitalOut(this.mcu ,PinName.p19 );
		this.In3 = new DigitalOut(this.mcu ,PinName.p18 );
		this.In4 = new DigitalOut(this.mcu ,PinName.p20 );
		*/
		this.bus = new BusOut(this.mcu,PinName.p15,PinName.p19,PinName.p18,PinName.p20);
		
		this.CtrlPinA.write(1);
		this.CtrlPinB.write(1);
		
		int[] mstep = {0xc,0x6,0x3,0x9};
		for (int j=0;j<200;j++)
		{
			this.bus.write(mstep[j%4]);
			this.sleep_ms(10);
		}
		/*
		for (int i=0;i<8;i++){
			this.bus.write(0xc);
			this.sleep_ms(1);
			this.bus.write(0x6);
			this.sleep_ms(1);
			this.bus.write(0x3);
			this.sleep_ms(1);
			this.bus.write(0x9);
			this.sleep_ms(1);
			
			//4			
			this.In1.write(1);
			this.In2.write(1);
			this.In3.write(0);
			this.In4.write(0);		
			this.sleep_ms(10);

			//3
			this.In1.write(0);
			this.In2.write(1);
			this.In3.write(1);
			this.In4.write(0);
			this.sleep_ms(10);
			//2
			this.In1.write(0);
			this.In2.write(0);
			this.In3.write(1);
			this.In4.write(1);
			this.sleep_ms(10);
			
			//1
			this.In1.write(1);
			this.In2.write(0);
			this.In3.write(0);
			this.In4.write(1);
			this.sleep_ms(10);
			
		}
		*/
		
	}
	public void close() throws MbedJsException
	{
		this.CtrlPinA.write(0);
		this.CtrlPinB.write(0);
		this.bus.write(0);
	
	
	}
	public void dispose() throws MbedJsException
	{
		this.CtrlPinA.write(0);
		this.CtrlPinB.write(0);
	
	
	}
	public static void main(String[] args) throws MbedJsException {
		Mcu mcu =new Mcu("10.0.0.2");
		StepperMotor sm = new StepperMotor(mcu);
		sm.close();
		mcu.close();
		System.out.println("done");
		
	}
}
