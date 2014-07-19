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
 * PwmOutクラスです。
 * <a href="https://mbed.org/handbook/PwmOut">mbed::PwmOut</a>と同等の機能を持ちます。
 */
public class PwmOut extends McuBindClass{
	public PwmOut(Mcu i_mcu,int i_pin) throws MbedJsException{
		super(i_mcu,"mbedJS:PwmOut");
		JsonRpcResult r=this.rawRpc("_new1",String.format("%d",JsonRpcUtils.intToJuint32(i_pin)));
		if(r.isError()){
			throw new MbedJsException();
		}
		this.setRemoteInstance(r.getInt32(0));
	}
	public void write(float i_value) throws MbedJsException
	{
		JsonRpcResult r=this.classRpc("write_fx",Integer.toString(Math.round(i_value*10000)));
		if(r.isError()){
			throw new MbedJsException();
		}
		return;
	}
	public float read() throws MbedJsException
	{
		JsonRpcResult r=this.classRpc("read_fx");
		if(r.isError()){
			throw new MbedJsException();
		}
		return ((float)r.getInt32(0))/10000f;
	}
	public void period(float i_value) throws MbedJsException
	{
		JsonRpcResult r=this.classRpc("period_fx",Integer.toString(Math.round(i_value*10000)));
		if(r.isError()){
			throw new MbedJsException();
		}
		return;
	}
	public void period_ms(int i_value) throws MbedJsException
	{
		JsonRpcResult r=this.classRpc("period_ms",Integer.toString(i_value));
		if(r.isError()){
			throw new MbedJsException();
		}
		return;
	}
	public void period_us(int i_value) throws MbedJsException
	{
		JsonRpcResult r=this.classRpc("period_us",Integer.toString(i_value));
		if(r.isError()){
			throw new MbedJsException();
		}
		return;
	}
	public void pulsewidth(float i_value) throws MbedJsException
	{
		JsonRpcResult r=this.classRpc("pulsewidth_fx",Integer.toString(Math.round(i_value*10000)));
		if(r.isError()){
			throw new MbedJsException();
		}
		return;
	}
	public void pulsewidth_ms(int i_value) throws MbedJsException
	{
		JsonRpcResult r=this.classRpc("pulsewidth_ms",Integer.toString(i_value));
		if(r.isError()){
			throw new MbedJsException();
		}
		return;
	}
	public void pulsewidth_us(int i_value) throws MbedJsException
	{
		JsonRpcResult r=this.classRpc("pulsewidth_us",Integer.toString(i_value));
		if(r.isError()){
			throw new MbedJsException();
		}
		return;
	}/*
	public static void main(String args[]){
		try {
			Mcu mcu=new Mcu("192.168.128.39");
			PwmOut a=new PwmOut(mcu,PinName.p21);
			a.period(0.5f);
			a.period_ms(100);
			a.period_us(100);
			a.pulsewidth(0.5f);
			a.pulsewidth_ms(100);
			a.pulsewidth_us(100);
			a.write(0.5f);
			float p=a.read();
			System.out.println(p);
			mcu.close();
			System.out.println("done");			
		} catch (MiMicJsException e) {
			e.printStackTrace();
		}
	}*/
}
