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
 * IC2Slaveクラスです。
 * <a href="https://mbed.org/handbook/IC2Slave">mbed::IC2Slave</a>と同等の機能を持ちます。
 */
public class I2CSlave  extends McuBindClass
{
	public class ReadResult{
		public final int ack;
		public final byte[] data;
		protected ReadResult(int i_ack,byte[] i_data){
			this.ack=i_ack;
			this.data=i_data;
		}
	}
	public I2CSlave(Mcu i_mcu,int i_sda_pin,int i_scl_pin) throws MbedJsException{
		super(i_mcu,"mbedJS:I2CSlave");
		JsonRpcResult r=this.rawRpc("_new1",String.format("%d,%d",JsonRpcUtils.intToJuint32(i_sda_pin),JsonRpcUtils.intToJuint32(i_scl_pin)));
		if(r.isError()){
			throw new MbedJsException();
		}
		this.setRemoteInstance(r.getInt32(0));
	}
	public void frequency(int i_hz) throws MbedJsException{
		JsonRpcResult r=this.classRpc("frequency",Integer.toString(i_hz));
		if(r.isError()){
			throw new MbedJsException();
		}
		return;
	}
	public void address(int i_value) throws MbedJsException{
		JsonRpcResult r=this.classRpc("address",Integer.toString(i_value));
		if(r.isError()){
			throw new MbedJsException();
		}
		return;
	}	
	public ReadResult read(int i_length) throws MbedJsException{
		JsonRpcResult r=this.classRpc("read1",String.format("%d",i_length));
		if(r.isError()){
			throw new MbedJsException();
		}
		return new ReadResult(r.getInt32(0),r.getBytes(1));
	}
	public int read() throws MbedJsException
	{
		JsonRpcResult r=this.classRpc("read2");
		if(r.isError()){
			throw new MbedJsException();
		}
		return r.getInt32(0);
	}
	public int write(byte[] i_data) throws MbedJsException{
		JsonRpcResult r=this.classRpc("write1",String.format("\"%s\"",JsonRpcUtils.byteArray2Bstr(i_data)));
		if(r.isError()){
			throw new MbedJsException();
		}
		return r.getInt32(0);
	}
	public int write(int i_ack) throws MbedJsException
	{
		JsonRpcResult r=this.classRpc("write2",String.format("%d",i_ack));
		if(r.isError()){
			throw new MbedJsException();
		}
		return r.getInt32(0);
	}

	public void stop() throws MbedJsException
	{
		JsonRpcResult r=this.classRpc("stop");
		if(r.isError()){
			throw new MbedJsException();
		}
		return;
	}
	public final static int NoData=0;
	public final static int ReadAddressed=1;
	public final static int WriteGeneral=2;
	public final static int WriteAddressed=3;
	/**
	 * 
	 * @return
	 * {@link #NoData},{@link #ReadAddressed},{@link #WriteGeneral},{@link #WriteAddressed}の何れか
	 * @throws MbedJsException
	 */
	public int receive() throws MbedJsException
	{
		JsonRpcResult r=this.classRpc("receive");
		if(r.isError()){
			throw new MbedJsException();
		}
		return r.getInt32(0);
	}
/*
	public static void main(String args[]){
		try {
			Mcu mcu=new Mcu("192.168.128.39");
			I2CSlave i2c=new I2CSlave(mcu,PinName.p28,PinName.p27);
			i2c.frequency(100000);
			i2c.address(10);
			i2c.receive();
			i2c.write(1);
			i2c.write(new byte[]{1,2,3});
			i2c.read();
			ReadResult t=i2c.read(3);
			i2c.stop();
			mcu.close();
			System.out.println("done");
		} catch (MiMicJsException e) {
			e.printStackTrace();
		}
	}
*/
}