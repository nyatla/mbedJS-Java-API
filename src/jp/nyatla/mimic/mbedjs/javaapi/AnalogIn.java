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
import jp.nyatla.mimic.mbedjs.javaapi.Mcu.GetInfoResult;

/**
 * AnalogInクラスです。
 * <a href="https://mbed.org/handbook/AnalogIn">mbed::AnalogIn</a>と同等の機能を持ちます。
 */
public class AnalogIn extends McuBindClass
{
	public AnalogIn(Mcu i_mcu,int i_pin) throws MbedJsException{
		super(i_mcu,"mbedJS:AnalogIn");
		JsonRpcResult r=this.rawRpc("_new1",JsonRpcUtils.toUint32String(i_pin));
		if(r.isError()){
			throw new MbedJsException();
		}
		this.setRemoteInstance(r.getInt32(0));
	}
	public float read() throws MbedJsException{
		JsonRpcResult r=this.classRpc("read_fx");
		if(r.isError()){
			throw new MbedJsException();
		}
		return ((float)r.getInt32(0))/10000;
	}
	public int read_u16() throws MbedJsException{
		JsonRpcResult r=this.classRpc("read_u16");
		if(r.isError()){
			throw new MbedJsException();
		}
		return r.getInt32(0);
	}
/*
	public static void main(String args[]){
		try {
			Mcu mcu=new Mcu("192.168.128.39");
			AnalogIn a=new AnalogIn(mcu,PinName.A0);
			System.out.println(a.read());
			System.out.println(a.read_u16());
			mcu.close();
			System.out.println("done");
			
		} catch (MiMicJsException e) {
			e.printStackTrace();
		}
	}
*/
}