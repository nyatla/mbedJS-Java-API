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


import org.json.*;

public class JsonRpcResult
{
	private JSONObject _jso;
	private int _id;
	private JSONArray _result;
	public JsonRpcResult(String i_source)
	{
		this._jso=new JSONObject(i_source);
		this._id=this._jso.getInt("id");
		if(this._jso.has("result")){
			this._result=this._jso.getJSONArray("result");
		}
	}
	public boolean isError()
	{
		return this._result==null;
	}
	public int getId() throws MbedJsException
	{
		return this._id;
	}
	public int getUInt32(int i_idx) throws MbedJsException
	{
		if(this._result!=null){
			long l=this._result.getLong(i_idx);
			return (int)(l&0xffffffff);
		}
		throw new MbedJsException();
	}	
	public int getInt32(int i_idx) throws MbedJsException
	{
		if(this._result!=null){
			return this._result.getInt(i_idx);
		}
		throw new MbedJsException();
	}
	public byte getByte(int i_idx) throws MbedJsException
	{
		if(this._result!=null){
			return (byte) this._result.getInt(i_idx);
		}
		throw new MbedJsException();
	}
	public byte[] getBytes(int i_idx) throws MbedJsException
	{
		if(this._result!=null){
			return JsonRpcUtils.bstr2ByteArray(this._result.getString(i_idx));
		}
		throw new MbedJsException();
	}
	public String getString(int i_idx) throws MbedJsException
	{
		if(this._result!=null){
			return this._result.getString(i_idx);
		}
		throw new MbedJsException();
	}
	
}
