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
import jp.nyatla.mimic.mbedjs.MbedJsException;

/**
 * BusOutクラスです。
 * <a href="https://mbed.org/handbook/BusOut">mbed::BusOut</a>と同等の機能を持ちます。
 */
public class BusOut extends McuBindClass
{
	/**
	 * 
	 * @param i_mcu
	 * @param i_pins
	 * バスを構成するピン識別子。最大16個までのpin番号を指定できます。
	 * @throws MbedJsException
	 */
	public BusOut(Mcu i_mcu,int... i_pins) throws MbedJsException
	{
		super(i_mcu,"mbedJS:BusOut");
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
			throw new MbedJsException();
		}
		this.setRemoteInstance(r.getInt32(0));
	}
	public void write(int i_value) throws MbedJsException
	{
		JsonRpcResult r=this.classRpc("write",Integer.toString(i_value));
		if(r.isError()){
			throw new MbedJsException();
		}
		return;
	}	
	public int read() throws MbedJsException
	{
		JsonRpcResult r=this.classRpc("read");
		if(r.isError()){
			throw new MbedJsException();
		}
		return r.getUInt32(0);
	}
/*
	public static void main(String args[]){
		try {
			Mcu mcu=new Mcu("192.168.128.39");
			BusOut a=new BusOut(mcu,PinName.LED1,PinName.LED2);
			a.write(2);
			System.out.println(a.read());
			mcu.close();
			System.out.println("done");
		} catch (MiMicJsException e) {
			e.printStackTrace();
		}
	}
*/
}
