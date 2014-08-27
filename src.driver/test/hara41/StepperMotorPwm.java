package test.hara41;

import jp.nyatla.mimic.mbedjs.MbedJsException;
import jp.nyatla.mimic.mbedjs.javaapi.DigitalOut;
import jp.nyatla.mimic.mbedjs.javaapi.Mcu;
import jp.nyatla.mimic.mbedjs.javaapi.PinName;
import jp.nyatla.mimic.mbedjs.javaapi.PwmOut;
import jp.nyatla.mimic.mbedjs.javaapi.driver.StepperMotor;
import jp.nyatla.mimic.mbedjs.javaapi.driver.utils.DriverBaseClass;

public class StepperMotorPwm extends DriverBaseClass {

	private Mcu _mcu;
	private DigitalOut CtrlPinA;
	private DigitalOut CtrlPinB;
	//private BusOut bus;
	private PwmOut In1;
	private PwmOut In2;
	private PwmOut In3;
	private PwmOut In4;
	private int[] mstep = {0xc,0x6,0x3,0x9};
	private int[] pstep = {0x9,0x3,0x6,0xc};
	/**
	 * Hブリッジ2系統使用したステッピングモータドライバ
	 * @param i_mcu　mcu
	 * @param i_ctrlA A相のイネーブル線(D9)
	 * @param i_ctrlB B相のイネーブル線(D10)
	 * @param i_in1 入力１(D8)
	 * @param i_in2 入力２(D12)
	 * @param i_in3 入力３(D11)
	 * @param i_in4 入力４(D13)
	 * @throws MbedJsException
	 */
	public StepperMotorPwm(Mcu i_mcu,int i_ctrlA,int i_ctrlB ,
			int i_in1,int i_in2,int i_in3, int i_in4) throws MbedJsException
	{
		this._mcu = i_mcu;
		this.CtrlPinA = new DigitalOut(this._mcu,i_ctrlA);
		this.CtrlPinB = new DigitalOut(this._mcu,i_ctrlB);
		//this.bus = new BusOut(this.mcu,i_in1,i_in2,i_in3,i_in4);
		this.In1 = new PwmOut(this._mcu , i_in1);
		this.In2 = new PwmOut(this._mcu , i_in2);
		this.In3 = new PwmOut(this._mcu , i_in3);
		this.In4 = new PwmOut(this._mcu , i_in4);
		/*
		this.In1.period_us(100);
		this.In2.period_us(100);
		this.In3.period_us(100);
		this.In4.period_us(100);
		*/
		this.CtrlPinA.write(1);
		this.CtrlPinB.write(1);
		
	}
	private void write(int i_step) throws MbedJsException
	{
		if((i_step & 0x8)!=0) this.In1.write(0.00001f); else this.In1.write(0.0f); 
		if((i_step & 0x4)!=0) this.In2.write(0.00001f); else this.In2.write(0.0f);
		if((i_step & 0x2)!=0) this.In3.write(0.00001f); else this.In3.write(0.0f);
		if((i_step & 0x1)!=0) this.In4.write(0.00001f); else this.In4.write(0.0f);
		
	}
	/**
	 * モータの回転
	 * @param i_step 回転するステップ数と方向(+/-)
	 * @param i_wait_ms １ステップごとに挿入するウェイト
	 * @throws MbedJsException
	 */
	public void rotate(int i_step , int i_wait_ms) throws MbedJsException
	{
		int s = Math.abs(i_step);
		if(i_step>0){
			for (int j=0;j<s;j++)
			{
				this.write(mstep[j%4]);
				this.sleep_ms(i_wait_ms);
			}
		}else{
			for (int j=0;j<s;j++)
			{
				this.write(pstep[j%4]);
				this.sleep_ms(i_wait_ms);
			}
		}
		
	}
	/**
	 * モータの制御を止める
	 * @throws MbedJsException
	 */
	public void close() throws MbedJsException
	{
		this.CtrlPinA.write(0);
		this.CtrlPinB.write(0);
		//this.bus.write(0);
	}
	public void dispose() throws MbedJsException
	{
		this.close();
	}
	public static void main(String[] args) throws MbedJsException {
		Mcu mcu =new Mcu("10.0.0.2");
		StepperMotor sm = new StepperMotor(mcu,PinName.p22,PinName.p23,
				PinName.p21,PinName.p25,PinName.p24,PinName.p26);
		sm.rotate(200, 20);
		sm.close();
		mcu.close();
		System.out.println("done");
		
	}
}
