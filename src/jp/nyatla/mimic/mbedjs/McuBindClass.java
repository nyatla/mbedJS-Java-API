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

import jp.nyatla.mimic.mbedjs.javaapi.Mcu;

/**
 * リモートインスタンスクラスのベースクラスです。
 * リモートインスタンスはリモートMCU内にセッションと同期するインスタンスを保持します。
 */
public class McuBindClass extends McuClass{
	public McuBindClass(Mcu i_mcu,String i_rpc_class_name)
	{
		super(i_mcu,i_rpc_class_name);
	}
	public boolean dispose() throws MbedJsException
	{
		return this._mcu.dispseObject(this._remote_instance_id);
	}
	/**
	 * 1番目の引数にremote instance idを持つRPCコールを発行します。
	 * @param i_function
	 * MiMicRPCの関数名フィールドに指定する値です。
	 * @param i_params
	 * @return
	 * @throws MbedJsException
	 */
	protected JsonRpcResult classRpc(String i_function,String i_params) throws MbedJsException
	{
		if(i_params==null){
			return this._mcu.rpc(this._rpc_class_name+":"+i_function,Integer.toString(this._remote_instance_id));
		}else{
			return this._mcu.rpc(this._rpc_class_name+":"+i_function,Integer.toString(this._remote_instance_id)+","+i_params);
		}
	}
	protected JsonRpcResult classRpc(String i_function) throws MbedJsException
	{
		return this.classRpc(i_function,null);
	}
	
	public final void setRemoteInstance(int i_remote_instance_id) throws MbedJsException
	{
		this._remote_instance_id=i_remote_instance_id;
	}
}
