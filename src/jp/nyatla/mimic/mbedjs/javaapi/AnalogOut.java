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
 * AnalogOutクラスです。
 * <a href="https://mbed.org/handbook/AnalogOut">mbed::AnalogOut</a>と同等の機能を持ちます。
 */
public class AnalogOut extends McuBindClass
{
	public AnalogOut(Mcu i_mcu,int i_pin) throws MbedJsException
	{
		super(i_mcu,"mbedJS:AnalogOut");
		JsonRpcResult r=this.rawRpc("_new1",JsonRpcUtils.toUint32String(i_pin));
		if(r.isError()){
			throw new MbedJsException();
		}
		this.setRemoteInstance(r.getInt32(0));
	}
	public void write(double i_value) throws MbedJsException{
		JsonRpcResult r=this.classRpc("write_fx",Integer.toString((int)Math.round(i_value*10000)));
		if(r.isError()){
			throw new MbedJsException();
		}
		return;
	}	
	public void write_u16(int i_value) throws MbedJsException{
		JsonRpcResult r=this.classRpc("write_u16",JsonRpcUtils.toUint32String(i_value));
		if(r.isError()){
			throw new MbedJsException();
		}
		return;
	}
	public float read() throws MbedJsException{
		JsonRpcResult r=this.classRpc("read_fx");
		if(r.isError()){
			throw new MbedJsException();
		}
		return ((float)r.getInt32(0))/10000;
	}
/*
	public static void main(String args[]){
		try {
			Mcu mcu=new Mcu("192.168.128.39");
			AnalogOut a=new AnalogOut(mcu,PinName.p18);
//			a.write(0);
			a.write_u16(1);
			System.out.println(a.read());
			mcu.close();
			System.out.println("done");
			
		} catch (MiMicJsException e) {
			e.printStackTrace();
		}
	}
*/
}
