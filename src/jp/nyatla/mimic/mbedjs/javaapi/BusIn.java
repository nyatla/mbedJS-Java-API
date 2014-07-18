/*
 * Copyright 2014 R.Iizuka
 * http://nyatla.jp/mimic/
 * nyatla39@gmail.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.nyatla.mimic.mbedjs.javaapi;

import jp.nyatla.mimic.mbedjs.JsonRpcResult;
import jp.nyatla.mimic.mbedjs.JsonRpcUtils;
import jp.nyatla.mimic.mbedjs.McuBindClass;
import jp.nyatla.mimic.mbedjs.MiMicJsException;
/**
 * BusInクラスです。
 * <a href="https://mbed.org/handbook/BusIn">mbed::BusIn</a>と同等の機能を持ちます。
 */
public class BusIn extends McuBindClass
{
	/**
	 * 
	 * @param i_mcu
	 * @param i_pins
	 * バスを構成するピン識別子。最大16個までのpin番号を指定できます。
	 * @throws MiMicJsException
	 */
	public BusIn(Mcu i_mcu,int... i_pins) throws MiMicJsException
	{
		super(i_mcu,"mbedJS:BusIn");
		String p;
		if(i_pins.length==0){
			p=JsonRpcUtils.toUint32String(PinName.NC);
		}else{
			p=JsonRpcUtils.toUint32String(i_pins[0]);
		}
		for(int i=1;i<i_pins.length;i++){
			p+=","+JsonRpcUtils.toUint32String(i_pins[i]);
		}
		for(int i=i_pins.length;i<16;i++){
			p+=","+JsonRpcUtils.toUint32String(PinName.NC);
		}
		JsonRpcResult r=this.rawRpc("_new1",p);
		if(r.isError()){
			throw new MiMicJsException();
		}
		this.setRemoteInstance(r.getInt32(0));
	}
	public int read() throws MiMicJsException
	{
		JsonRpcResult r=this.classRpc("read");
		if(r.isError()){
			throw new MiMicJsException();
		}
		return r.getInt32(0);
	}
	public void mode(int i_pin_mode) throws MiMicJsException
	{
		JsonRpcResult r=this.classRpc("mode",Integer.toString(i_pin_mode));
		if(r.isError()){
			throw new MiMicJsException();
		}
		return;
	}
/*
	public static void main(String args[]){
		try {
			Mcu mcu=new Mcu("192.168.128.39");
			BusIn a=new BusIn(mcu,PinName.P0_21,PinName.P0_22);
			a.mode(PinMode.OpenDrain);
			System.out.println(a.read());
			mcu.close();
			System.out.println("done");
		} catch (MiMicJsException e) {
			e.printStackTrace();
		}
	}
*/		
}
