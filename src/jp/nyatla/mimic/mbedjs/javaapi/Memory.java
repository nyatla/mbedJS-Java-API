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
import jp.nyatla.mimic.mbedjs.McuClass;
import jp.nyatla.mimic.mbedjs.MiMicJsException;

/**
 * MCUのメモリを操作するクラスです。
 *
 */
public class Memory extends McuClass{
	public Memory(Mcu i_mcu){
		super(i_mcu,"MiMic:Memory");
	}
	/**
	 * 1バイト単位でメモリから値を読み出します。
	 * @param i_addr
	 * 読出し元のアドレスです。
	 * @param i_size
	 * 読出しサイズです。
	 * @return
	 * 読みだしたデータを格納した配列です。
	 * @throws MiMicJsException
	 */
	public byte[] read(int i_addr,int i_size) throws MiMicJsException
	{
		JsonRpcResult r=this.rawRpc("read",String.format("%d,%d",JsonRpcUtils.intToJuint32(i_addr),JsonRpcUtils.intToJuint32(i_size)));
		if(r.isError()){
			throw new MiMicJsException();
		}
		return r.getBytes(0);
	}
	/**
	 * 1バイト単位でメモリに値を書き込みます。
	 * @param i_addr
	 * 書き込み先のメモリアドレスです。
	 * @param i_data
	 * 書き込むデータです。
	 * @throws MiMicJsException
	 */
	public void write(int i_addr,byte[] i_data) throws MiMicJsException
	{
		JsonRpcResult r=this.rawRpc("write",String.format("%d,\"%s\"",JsonRpcUtils.intToJuint32(i_addr),JsonRpcUtils.byteArray2Bstr(i_data)));
		if(r.isError()){
			throw new MiMicJsException();
		}
		return;
	}
	/**
	 * INT32値をメモリから読み出します。
	 * @param i_addr
	 * 読込元の先頭アドレスを指定します。4バイト境界で指定してください。
	 * @param i_size
	 * 読込サイズを指定します。4バイト単位で指定してください。
	 * 多分200バイトくらいが限界です。
	 * @return
	 * 読みだした値を格納した配列です。
	 * @throws MiMicJsException
	 */
	public int[] read32(int i_addr,int i_size) throws MiMicJsException
	{
		JsonRpcResult r=this.rawRpc("read",String.format("%d,%d",JsonRpcUtils.intToJuint32(i_addr),JsonRpcUtils.intToJuint32(i_size)));
		if(r.isError()){
			throw new MiMicJsException();
		}
		return JsonRpcUtils.bstr2IntArray(r.getString(0));
	}
	/**
	 * INT32値をメモリへ書き込みます。
	 * @param i_addr
	 * 書き込み先の先頭アドレスを指定します。4バイト境界で指定してください。
	 * @param i_data
	 * 書き込むデータの配列です。最大要素数は40個くらいです。
	 * @throws MiMicJsException
	 */
	public void write32(int i_addr,int[] i_data) throws MiMicJsException
	{
		JsonRpcResult r=this.rawRpc("write",String.format("%d,\"%s\"",JsonRpcUtils.intToJuint32(i_addr),JsonRpcUtils.intArray2Bstr(i_data)));
		if(r.isError()){
			throw new MiMicJsException();
		}
		return;
	}
/*
	public static void main(String args[]){
		try {
			Mcu mcu=new Mcu("192.168.128.39");
			Memory a=new Memory(mcu);
			a.write(0x20080000,new byte[]{0x01,0x02,0x03,0x04});
			a.write32(0x20080004,new int[]{0xf0f1f2f3,0xf9f8f7f6});
			int[] i=a.read32(0x20080000,16);
			byte[] b=a.read(0x20080000,16);
			mcu.close();
			System.out.println("done");
		} catch (MiMicJsException e) {
			e.printStackTrace();
		}
	}*/

}
