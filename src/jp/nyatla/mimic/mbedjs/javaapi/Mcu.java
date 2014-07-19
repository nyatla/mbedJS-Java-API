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

import jp.nyatla.mimic.mbedjs.JsonRpc;
import jp.nyatla.mimic.mbedjs.JsonRpcResult;
import jp.nyatla.mimic.mbedjs.MbedJsException;

/**
 * MCUをリモートインスタンスとして管理します。
 * mbedJSを実行するMCUと接続するクラスです。
 * このクラスは、他のペリフェラルクラスをインスタンス化するときに必要です。
 */
public class Mcu
{
	/**
	 * {@link Mcu#getInfo()}の戻り値を格納するクラスです。
	 */
	public final class GetInfoResult{
		public final class GetInfoResult_Mcu{
			/**MCUチップの名前です。*/
			String name;
			/**イーサネットペリフェラルの名前です*/
			String eth;
		}
		/**バージョン情報です*/
		public String version;
		/** プラットフォーム名です。*/
		public String platform;
		/** MCU情報の詳細です。*/
		public GetInfoResult_Mcu mcu=new GetInfoResult_Mcu();
	}
	private final String RPC_CLASS="mbedJS:Mcu";
	private JsonRpc _rpc;
	public Mcu(String i_addr) throws MbedJsException
	{
		this._rpc=new JsonRpc("ws://"+i_addr+"/rpc/");
	}
	/**
	 * mbedJSのgetInfo関数を実行します。
	 * @return
	 * Mcuの情報を返します。
	 * @throws MbedJsException
	 */
	public GetInfoResult getInfo() throws MbedJsException{
		JsonRpcResult r=this.rpc(RPC_CLASS+":getInfo","");
		GetInfoResult ret=new GetInfoResult();
		ret.version	=r.getString(0);
		ret.platform=r.getString(1);
		ret.mcu.eth=r.getString(2);
		ret.mcu.name=r.getString(3);
		return ret;
	}

	/**
	 * RPCセッションをシャットダウンしてMCUを解放します。
	 */
	public void shutdown(){
		try {
			//今んところcloseと同じ。
			this._rpc.close();
		} catch (MbedJsException e) {
		}
	}
	public void close() throws MbedJsException{
		this._rpc.close();
	}
	/**
	 * RPCリクエストを発行します。
	 * ペリフェラルクラス向けの関数です。
	 * @param i_method
	 * メソッド名を指定します。
	 * @param i_params
	 * params配列に渡すパラメータを指定します。パラメータは、
	 * params:[]の中にそのまま表示されます。
	 * @return
	 * 発行したRPCメッセージのid値です。
	 * @throws MbedJsException
	 */
	public JsonRpcResult rpc(String i_method,String i_params) throws MbedJsException{
		int id=this._rpc.rpc(i_method, i_params);
		return this._rpc.waitForResult(id);
	}
/*
	public static void main(String args[]){
		try {
			Mcu mcu=new Mcu("ws://192.168.128.39:80/rpc/");
			GetInfoResult t=mcu.getInfo();
			mcu.close();
		} catch (MiMicJsException e) {
			e.printStackTrace();
		}
	}
*/
}
