package test.hara41;

import jp.nyatla.mimic.mbedjs.*;
import jp.nyatla.mimic.mbedjs.javaapi.*;
import jp.nyatla.mimic.mbedjs.javaapi.driver.utils.DriverBaseClass;

public class StepperMotor extends DriverBaseClass{

	private Mcu mcu;
	private DigitalOut CtrlPinA;
	private DigitalOut CtrlPinB;
	private DigitalOut In1;
	private DigitalOut In2;
	private DigitalOut In3;
	private DigitalOut In4;
	
	public StepperMotor(Mcu i_mcu) throws MbedJsException
	{
		this.mcu = i_mcu;
		this.CtrlPinA = new DigitalOut(this.mcu,PinName.p22);
		this.CtrlPinB = new DigitalOut(this.mcu,PinName.p23);
		this.In1 = new DigitalOut(this.mcu ,PinName.p21 );
		this.In2 = new DigitalOut(this.mcu ,PinName.p24 );
		this.In3 = new DigitalOut(this.mcu ,PinName.p25 );
		this.In4 = new DigitalOut(this.mcu ,PinName.p26 );
		
		this.CtrlPinA.write(1);
		this.CtrlPinB.write(1);
		
		this.sleep_ms(1000);
		for (int i=0;i<1;i++){
			this.In1.write(1);
			this.In2.write(0);
			this.In3.write(0);
			this.In4.write(1);
			this.sleep_ms(1000);
			
			this.In1.write(0);
			this.In2.write(0);
			this.In3.write(1);
			this.In4.write(1);
			this.sleep_ms(1000);
			
			this.In1.write(0);
			this.In2.write(1);
			this.In3.write(1);
			this.In4.write(0);
			this.sleep_ms(1000);
			
			this.In1.write(1);
			this.In2.write(1);
			this.In3.write(0);
			this.In4.write(0);
			this.sleep_ms(1000);
			
		}
		
		
	}
	public void close() throws MbedJsException
	{
		this.CtrlPinA.write(0);
		this.CtrlPinB.write(0);
		this.In1.write(0);
		this.In2.write(0);
		this.In3.write(0);
		this.In4.write(0);
	
	}
	public void dispose() throws MbedJsException
	{
		this.CtrlPinA.write(0);
		this.CtrlPinB.write(0);
		this.In1.write(0);
		this.In2.write(0);
		this.In3.write(0);
		this.In4.write(0);
	
	}
	public static void main(String[] args) throws MbedJsException {
		Mcu mcu =new Mcu("10.0.0.2");
		StepperMotor sm = new StepperMotor(mcu);
		sm.close();
		mcu.close();
		System.out.println("done");
		
	}
}
