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
 * DigitalOutクラスです。
 * <a href="https://mbed.org/handbook/DigitalIn">mbed::DigitalOut</a>と同等の機能を持ちます。
 */
public class DigitalOut extends McuBindClass{
	public DigitalOut(Mcu i_mcu,int i_pin,int i_val) throws MiMicJsException{
		super(i_mcu,"mbedJS:DigitalOut");
		JsonRpcResult r=this.rawRpc("_new2",String.format("%d,%d",JsonRpcUtils.intToJuint32(i_pin),i_val));
		if(r.isError()){
			throw new MiMicJsException();
		}
		this.setRemoteInstance(r.getInt32(0));
	}
	public DigitalOut(Mcu i_mcu,int i_pin) throws MiMicJsException{
		super(i_mcu,"mbedJS:DigitalOut");
		JsonRpcResult r=this.rawRpc("_new1",JsonRpcUtils.toUint32String(i_pin));
		if(r.isError()){
			throw new MiMicJsException();
		}
		this.setRemoteInstance(r.getInt32(0));
	}
	public void write(int i_value) throws MiMicJsException{
		JsonRpcResult r=this.classRpc("write",Integer.toString(i_value));
		if(r.isError()){
			throw new MiMicJsException();
		}
		return;
	}
	public int read() throws MiMicJsException{
		JsonRpcResult r=this.classRpc("read");
		if(r.isError()){
			throw new MiMicJsException();
		}
		return r.getInt32(0);
	}
/*
	public static void main(String args[]){
		try {
			Mcu mcu=new Mcu("192.168.128.39");
			DigitalOut a=new DigitalOut(mcu,PinName.LED1);
			a.write(0);
			System.out.println(a.read());
			mcu.close();
			System.out.println("done");
		} catch (MiMicJsException e) {
			e.printStackTrace();
		}
	}*/
}
