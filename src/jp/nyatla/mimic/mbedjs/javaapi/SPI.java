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
 * SPIクラスです。
 * <a href="https://mbed.org/handbook/SPI">mbed::SPI</a>と同等の機能を持ちます。
 */
public class SPI extends McuBindClass{
	public SPI(Mcu i_mcu,int i_mosi_pin,int i_miso_pin,int i_sclk_pin) throws MbedJsException{
		super(i_mcu,"mbedJS:SPI");
		JsonRpcResult r=this.rawRpc("_new1",String.format("%d,%d,%d,%d",JsonRpcUtils.intToJuint32(i_mosi_pin),JsonRpcUtils.intToJuint32(i_miso_pin),JsonRpcUtils.intToJuint32(i_sclk_pin),JsonRpcUtils.intToJuint32(PinName.NC)));
		if(r.isError()){
			throw new MbedJsException();
		}
		this.setRemoteInstance(r.getInt32(0));
	}
	public int write(int i_value) throws MbedJsException{
		JsonRpcResult r=this.classRpc("write",String.format("%d",i_value));
		if(r.isError()){
			throw new MbedJsException();
		}
		return r.getInt32(0);
	}	
	public void frequency(int i_value) throws MbedJsException{
		JsonRpcResult r=this.classRpc("frequency",String.format("%d",i_value));
		if(r.isError()){
			throw new MbedJsException();
		}
		return;
	}
	public void format(int i_bits,int i_mode) throws MbedJsException{
		JsonRpcResult r=this.classRpc("format",String.format("%d,%d",i_bits,i_mode));
		if(r.isError()){
			throw new MbedJsException();
		}
		return;
	}
	/*
	public static void main(String args[]){
		try {
			Mcu mcu=new Mcu("192.168.128.39");
			SPI spi=new SPI(mcu,PinName.p5,PinName.p6,PinName.p7);
			spi.frequency(1000000);
			spi.format(8,3);
			System.out.println(spi.write(39));
			mcu.close();
			System.out.println("done");			
		} catch (MiMicJsException e) {
			e.printStackTrace();
		}
	}
	*/
}
