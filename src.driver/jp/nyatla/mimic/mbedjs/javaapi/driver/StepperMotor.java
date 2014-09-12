/**
 * Hブリッジ２系統を使ったステッピングモータ
 * 検証ドライバ：L293D
 * 
 */
package jp.nyatla.mimic.mbedjs.javaapi.driver;

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
	/**
	 * Hブリッジ2系統使用したステッピングモータドライバ
	 * @param i_mcu　mcu
	 * @param i_ctrlA A相のイネーブル線
	 * @param i_ctrlB B相のイネーブル線
	 * @param i_in1 入力１
	 * @param i_in2 入力２
	 * @param i_in3 入力３
	 * @param i_in4 入力４
	 * @throws MbedJsException
	 */
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
				this.bus.write(mstep[j%4]);
				//this.sleep_ms(1);
				//this.bus.write(0);
				this.sleep_ms(i_wait_ms);
			}
		}else{
			for (int j=0;j<s;j++)
			{
				this.bus.write(pstep[j%4]);
				//this.sleep_ms(1);
				//this.bus.write(0);
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
		this.bus.write(0);
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
