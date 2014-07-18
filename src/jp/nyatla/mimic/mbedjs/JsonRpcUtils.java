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

package jp.nyatla.mimic.mbedjs;


public class JsonRpcUtils
{
	/**
	 * BSTRをByte配列へ変換します。
	 * @param i_bstr
	 * @return
	 * @throws MiMicJsException
	 */
	public static byte[] bstr2ByteArray(String i_bstr) throws MiMicJsException
	{
		int l=i_bstr.length();
		if(l%2!=0){
			throw new MiMicJsException();
		}
		byte[] ret=new byte[l/2];
		for(int i=0;i<l;i+=2){
			ret[i/2]=(byte)(Integer.parseInt(i_bstr.substring(i,i+2),16));
		}
		return ret;
	}
	/**
	 * Byte配列をBSTRへ変換します。
	 * @param i_data
	 * @return
	 * @throws MiMicJsException
	 */
	public static String byteArray2Bstr(byte[] i_data) throws MiMicJsException
	{
		StringBuffer ret=new StringBuffer();
		for(int i=0;i<i_data.length;i++){
			int v=i_data[i]&0x000000ff;
			if(v>=0x10){
				ret.append(Integer.toHexString(i_data[i]&0x000000ff));
			}else{
				ret.append(Integer.toHexString((i_data[i]>>4)&0x000000f));
				ret.append(Integer.toHexString(i_data[i]&0x000000f));
			}
		}
		return ret.toString();
	}
	public static int[] bstr2IntArray(String i_bstr) throws MiMicJsException
	{
		int l=i_bstr.length();
		if(l%8!=0){
			throw new MiMicJsException();
		}
		int[] ret=new int[l/8];
		for(int i=0;i<l;i+=8){
			long t=Long.parseLong(i_bstr.substring(i,i+8),16);
			//Longにしないと値が落ちる
			ret[i/8]=bswap(juint32Toint(t));
		}
		return ret;
	}	
	public static String intArray2Bstr(int[] i_data) throws MiMicJsException
	{
		StringBuffer ret=new StringBuffer();
		for(int i=0;i<i_data.length;i++){
			String s=Integer.toHexString(bswap(i_data[i]));
			while(s.length()<8){
				s="0"+s;
			}
			ret.append(s);
		}
		return ret.toString();
	}	
	
	public static void main(String args[]){
		try {
			int r=bswap(0xff010203);
			System.out.println(Integer.toHexString(r));
			r=bswap(r);
			System.out.println(Integer.toHexString(r));
			
			System.out.println(Integer.toHexString(juint32Toint(intToJuint32(0xfe010203))));
			byte[] t=bstr2ByteArray("ff00010234");
			String s=byteArray2Bstr(t);
			System.out.println(s);
			int[] t2=bstr2IntArray("010000000002000001234567ff0000f1");
			String s2=intArray2Bstr(t2);
			System.out.println(s2);
		} catch (MiMicJsException e) {
			e.printStackTrace();
		}
	}
	public static int bswap(int p)
	{
		return ((p&0x000000ff)<<24)|((p&0x0000ff00)<<8)|((p&0x00ff0000)>>8)|((p&0xff000000)>>>24);
	}
	/**
	 * intをuint32表現に変換します。
	 * @param i_val
	 * @return
	 */
	public static long intToJuint32(int i_val)
	{
		return (((long)(i_val>>>1))<<1)|(i_val&1);//unsigned intへ変換
	}
	public static int juint32Toint(long i_val)
	{
		return (int)i_val;
	}
	public static String toUint32String(int i_val)
	{
		return Long.toString(intToJuint32(i_val));
	}
	
//	public static long bswap()

}
