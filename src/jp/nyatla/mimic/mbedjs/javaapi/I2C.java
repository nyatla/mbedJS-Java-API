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
 * IC2クラスです。
 * <a href="https://mbed.org/handbook/IC2">mbed::IC2</a>と同等の機能を持ちます。
 */
public class I2C  extends McuBindClass
{
	public class ReadResult{
		public final int ack;
		public final byte[] data;
		protected ReadResult(int i_ack,byte[] i_data){
			this.ack=i_ack;
			this.data=i_data;
		}
	}
	public I2C(Mcu i_mcu,int i_sda_pin,int i_scl_pin) throws MiMicJsException{
		super(i_mcu,"mbedJS:I2C");
		JsonRpcResult r=this.rawRpc("_new1",String.format("%d,%d",JsonRpcUtils.intToJuint32(i_sda_pin),JsonRpcUtils.intToJuint32(i_scl_pin)));
		if(r.isError()){
			throw new MiMicJsException();
		}
		this.setRemoteInstance(r.getInt32(0));
	}
	public void frequency(int i_hz) throws MiMicJsException{
		JsonRpcResult r=this.classRpc("frequency",Integer.toString(i_hz));
		if(r.isError()){
			throw new MiMicJsException();
		}
		return;
	}
	public ReadResult read(int i_address,int i_length,boolean i_repeated) throws MiMicJsException{
		JsonRpcResult r=this.classRpc("read1",String.format("%d,%d,%d",i_address,i_length,i_repeated?1:0));
		if(r.isError()){
			throw new MiMicJsException();
		}
		return new ReadResult(r.getInt32(0),r.getBytes(1));
	}
	public int read(int i_ack) throws MiMicJsException
	{
		JsonRpcResult r=this.classRpc("read2",String.format("%d",i_ack));
		if(r.isError()){
			throw new MiMicJsException();
		}
		return r.getInt32(0);
	}
	public int write(int i_address,byte[] i_data,boolean i_repeated) throws MiMicJsException{
		JsonRpcResult r=this.classRpc("write1",String.format("%d,\"%s\",%d",i_address,JsonRpcUtils.byteArray2Bstr(i_data),i_repeated?1:0));
		if(r.isError()){
			throw new MiMicJsException();
		}
		return r.getInt32(0);
	}
	public int write(int i_ack) throws MiMicJsException
	{
		JsonRpcResult r=this.classRpc("read2",String.format("%d",i_ack));
		if(r.isError()){
			throw new MiMicJsException();
		}
		return r.getInt32(0);
	}
	public void start() throws MiMicJsException
	{
		JsonRpcResult r=this.classRpc("start");
		if(r.isError()){
			throw new MiMicJsException();
		}
		return;
	}
	public void stop() throws MiMicJsException
	{
		JsonRpcResult r=this.classRpc("stop");
		if(r.isError()){
			throw new MiMicJsException();
		}
		return;
	}
	/*
	public static void main(String args[]){
		try {
			Mcu mcu=new Mcu("192.168.128.39");
			I2C i2c=new I2C(mcu,PinName.p28,PinName.p27);
			i2c.frequency(100000);
			i2c.start();
			i2c.write(1);
			i2c.write(0,new byte[]{1,2,3},false);
			i2c.read(1);
			ReadResult t=i2c.read(1,10,false);
			i2c.stop();
			mcu.close();
			System.out.println("done");
		} catch (MiMicJsException e) {
			e.printStackTrace();
		}
	}
	*/
}
