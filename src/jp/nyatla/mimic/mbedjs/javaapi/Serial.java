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
 * Serialクラスです。
 * <a href="https://mbed.org/handbook/Serial">mbed::Serial</a>と同等の機能を持ちます。
 */
public class Serial extends McuBindClass{
	public final static int None=0;
	public final static int Odd=1;
	public final static int Even=2;
	public final static int Forced1=3;
	public final static int Forced0=4;
	
	public Serial(Mcu i_mcu,int i_tx_pin,int i_rx_pin) throws MbedJsException{
		super(i_mcu,"mbedJS:Serial");
		JsonRpcResult r=this.rawRpc("_new1",String.format("%d,%d",JsonRpcUtils.intToJuint32(i_tx_pin),JsonRpcUtils.intToJuint32(i_rx_pin)));
		if(r.isError()){
			throw new MbedJsException();
		}
		this.setRemoteInstance(r.getInt32(0));
	}
	public void format(int i_bits,int i_parity,int i_stop_bit) throws MbedJsException{
		JsonRpcResult r=this.classRpc("format",String.format("%d,%d,%d",i_bits,i_parity,i_stop_bit));
		if(r.isError()){
			throw new MbedJsException();
		}
		return;
	}
	/**
	 * フォーマットを設定します。
	 * @param {int} i_bits
	 * ビット数です。省略時は8です。
	 * @param {int} i_parity
	 * パリティの値です。
	 * {@link #None},{@link #Odd},{@link #Even},{@link #Forced1},{@link #Forced0}が指定できます。
	 * 省略時はNoneです。
	 * @param {int} i_stop_bits
	 * ストップビットの値です。省略時は１です。
	 * @return　{int}
	 * Callbackモードの時はRPCメソッドのインデクスを返します。
	 */
	public void format(int i_bits,int i_parity) throws MbedJsException{
		this.format(i_bits, i_parity,1);
	}
	public void format(int i_bits) throws MbedJsException{
		this.format(i_bits,None,1);
	}
	public void format() throws MbedJsException{
		this.format(8,None,1);
	}
	public boolean readable() throws MbedJsException{
		JsonRpcResult r=this.classRpc("readable");
		if(r.isError()){
			throw new MbedJsException();
		}
		return r.getInt32(0)==0?false:true;
	}
	public boolean writeable() throws MbedJsException{
		JsonRpcResult r=this.classRpc("writeable");
		if(r.isError()){
			throw new MbedJsException();
		}
		return r.getInt32(0)==0?false:true;
	}
	public void send_break() throws MbedJsException{
		JsonRpcResult r=this.classRpc("send_break");
		if(r.isError()){
			throw new MbedJsException();
		}
		return;
	}
	public int putc(int i_c) throws MbedJsException{
		JsonRpcResult r=this.classRpc("putc",Integer.toString(i_c));
		if(r.isError()){
			throw new MbedJsException();
		}
		return r.getInt32(0);
	}
	public int puts(String i_s) throws MbedJsException{
		JsonRpcResult r=this.classRpc("puts","\""+i_s+"\"");
		if(r.isError()){
			throw new MbedJsException();
		}
		return r.getInt32(0);
	}
	public int puts(byte[] i_data) throws MbedJsException{
		JsonRpcResult r=this.classRpc("puts_2","\""+JsonRpcUtils.byteArray2Bstr(i_data)+"\"");
		if(r.isError()){
			throw new MbedJsException();
		}
		return r.getInt32(0);
	}	
	public int getc() throws MbedJsException{
		JsonRpcResult r=this.classRpc("getc");
		if(r.isError()){
			throw new MbedJsException();
		}
		return r.getInt32(0);
	}
	/**
	 * {@link #gets(int,'a')と同じです。
	 * @param i_len
	 * @return
	 * @throws MbedJsException
	 */
	public String gets(int i_len) throws MbedJsException{
		return (String)this.gets(i_len,'a');
	}
	/**
	 * バイナリ/ASCIIフォーマットでシリアルポートから値を読み取ります。
	 * @param i_len
	 * 読み取りデータ長
	 * @param i_mode
	 * 'b' - byte[]で読出し<br/>
	 * 'a' - ASCIIで読出し
	 * @return
	 * i_mode=='b' - byte[]型のオブジェクト<br/>
	 * i_mode=='a' - String型のオブジェクト
	 * @throws MbedJsException
	 */
	public Object gets(int i_len,char i_mode) throws MbedJsException
	{
		JsonRpcResult r;
		switch(i_mode)
		{
		case 'a':
			r=this.classRpc("gets",Integer.toString(i_len));
			if(r.isError()){
				throw new MbedJsException();
			}
			return r.getString(0);
		case 'b':
			r=this.classRpc("gets_2",Integer.toString(i_len));
			if(r.isError()){
				throw new MbedJsException();
			}
			return JsonRpcUtils.bstr2ByteArray(r.getString(0));
		default:
			throw new MbedJsException();
		}
	}	
	public void baud(int i_baudrate) throws MbedJsException
	{
		JsonRpcResult r=this.classRpc("baud",Integer.toString(i_baudrate));
		if(r.isError()){
			throw new MbedJsException();
		}
		return;
	}
	/*
	public static void main(String args[]){
		try {
			Mcu mcu=new Mcu("192.168.128.39");
			Serial uart=new Serial(mcu,PinName.p9,PinName.p10);
			uart.baud(115200);
			uart.send_break();
			uart.format(8,None,1);
			uart.readable();
			uart.writeable();
			uart.putc(32);
			uart.getc();
			uart.puts("1234");
			System.out.println(uart.gets(5));
			mcu.close();
			System.out.println("done");			
		} catch (MiMicJsException e) {
			e.printStackTrace();
		}
	}
	*/
}
