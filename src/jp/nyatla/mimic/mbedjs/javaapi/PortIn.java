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
 * PortInクラスです。
 * <a href="https://mbed.org/handbook/PortIn">mbed::PortIn</a>と同等の機能を持ちます。
 */
public class PortIn extends McuBindClass{
	public PortIn(Mcu i_mcu,int i_port,int i_mask) throws MbedJsException{
		super(i_mcu,"mbedJS:PortIn");
		JsonRpcResult r=this.rawRpc("_new1",String.format("%d,%d",JsonRpcUtils.intToJuint32(i_port),JsonRpcUtils.intToJuint32(i_mask)));
		if(r.isError()){
			throw new MbedJsException();
		}
		this.setRemoteInstance(r.getInt32(0));
	}
	public int read() throws MbedJsException{
		JsonRpcResult r=this.classRpc("read");
		if(r.isError()){
			throw new MbedJsException();
		}
		return r.getInt32(0);
	}/*
	public static void main(String args[]){
		try {
			Mcu mcu=new Mcu("192.168.128.39");
			PortIn a=new PortIn(mcu,PortName.Port0,0xffffffff);
			System.out.println(a.read());
			mcu.close();
			System.out.println("done");			
		} catch (MiMicJsException e) {
			e.printStackTrace();
		}
	}*/
}
