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

import java.net.URI;
import java.net.URISyntaxException;

public class JsonRpc
{
	private MiMicJsWebSocket _ws;
	StringBuffer _rx=new StringBuffer();
	public JsonRpc(String i_url) throws MiMicJsException
	{
		try {
			this._ws=new MiMicJsWebSocket(new URI(i_url));
			if(!this._ws.connectBlocking()){
				throw new MiMicJsException();
			}
		} catch (InterruptedException|MiMicJsException | URISyntaxException e){
			throw new MiMicJsException(e);
		}
		this._idx=0;
	}
	public int _idx;
	public int rpc(String i_method, String i_params) throws MiMicJsException
	{
		int idx=this._idx;
		this._ws.send(String.format("{\"jsonrpc\":\"2.0\",\"method\":\"%s\",\"params\":[%s],\"id\":%d}",i_method,i_params,idx));
		this._idx++;
		return idx;
	}
	public JsonRpcResult waitForResult(int id) throws MiMicJsException
	{
		int rxst=0;
		for(;;){
			try {
				this._rx.append(this._ws.recvBlocking());
			} catch (InterruptedException e) {
				throw new MiMicJsException(e);
			}
			int l=this._rx.length();
			//ストリームからJSONを抽出。"のエスケープには対応しない。
			for(int i=0;i<l;i++){
				char t=this._rx.charAt(i);
				switch(rxst){
				case 2:
					if(t!='"'){
						rxst=1;
					}
					break;
				case 0:
					if(t!='{'){
						continue;
					}
					rxst=1;
					continue;
				case 1:
					switch(t){
					case '"':
						rxst=2;
						break;
					case '}':
						//確定
						JsonRpcResult json = new JsonRpcResult(this._rx.substring(0,i+1));//???切り出しちゃんとできてる？
						this._rx.delete(0,i+1);
						return json;
					}
				}
			}
		}
	}
	public void close() throws MiMicJsException
	{
		try {
			this._ws.closeBlocking();
		} catch (InterruptedException e) {
			throw new MiMicJsException(e);
		}
	}
	public static void main(String args[]){
		try {
			JsonRpc rpc=new JsonRpc("ws://192.168.128.39:80/rpc/");
			rpc.rpc("mbedJS:Mcu:getInfo", "");
			rpc.waitForResult(1);
			rpc.close();
		} catch (MiMicJsException e) {
			e.printStackTrace();
		}
	}
}
