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
 * DigitalInクラスです。
 * <a href="https://mbed.org/handbook/DigitalIn">mbed::DigitalIn</a>と同等の機能を持ちます。
 */
public class DigitalIn extends McuBindClass{
	public DigitalIn(Mcu i_mcu,int i_pin) throws MiMicJsException{
		super(i_mcu,"mbedJS:DigitalIn");
		JsonRpcResult r=this.rawRpc("_new1",JsonRpcUtils.toUint32String(i_pin));
		if(r.isError()){
			throw new MiMicJsException();
		}
		this.setRemoteInstance(r.getInt32(0));
	}
	public int read() throws MiMicJsException{
		JsonRpcResult r=this.classRpc("read");
		if(r.isError()){
			throw new MiMicJsException();
		}
		return r.getInt32(0);
	}
	public void mode(int i_pin_mode) throws MiMicJsException{
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
			DigitalIn a=new DigitalIn(mcu,PinName.LED1);
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
