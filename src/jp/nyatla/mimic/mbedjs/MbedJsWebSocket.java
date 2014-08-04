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

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;


/**
 * ReceiveBlocking関数を持ちます。
 * @author nyatla
 *
 */
public class MbedJsWebSocket extends WebSocketClient
{
	private final static int ST_NONE=0;
	private final static int ST_OPEN=1;
	private final static int ST_WAIT_RECV=2;
	private final static int ST_RECVED=3;
	private final static int ST_CLOSE=4;
	private final static int ST_ERROR=5;

	private StringBuffer _sb;
//	ArrayList<String> _s;
	int _status;
	public MbedJsWebSocket(URI i_uri) throws MbedJsException
	{
		super(i_uri,new Draft_17());
		this._sb=new StringBuffer();
		this._status=ST_NONE;
	}
	public String recvBlocking() throws MbedJsException,InterruptedException
	{
		synchronized(this){
			if(this._status!=ST_OPEN){
				throw new MbedJsException();
			}
			//キューにたまってたらそれを返す。
			if(this._sb.length()>0){
				String r=this._sb.toString();
				this._sb.delete(0,this._sb.length());
				return r;
			}
			//無ければ待つ
			this._status=ST_WAIT_RECV;
			wait();
			switch(this._status){
			case ST_RECVED:
				//受信待ち成功
				this._status=ST_OPEN;
				if(this._sb.length()>0){
					String r=this._sb.toString();
					this._sb.delete(0,this._sb.length());
					return r;
				}
				break;
			case ST_WAIT_RECV:
				break;
			}
			throw new MbedJsException();
		}
	}
	public boolean connectBlocking() throws InterruptedException
	{
		synchronized(this){
			boolean r=super.connectBlocking();
			if(r){
				this._status=ST_OPEN;
			}else{
				this._status=ST_ERROR;
			}
			return r;
		}
	}
	public void closeBlocking() throws InterruptedException
	{
		synchronized(this){
			this._status=ST_CLOSE;
			super.closeBlocking();
			this._status=ST_NONE;
		}
	}
	@Override
	public void onClose(int arg0, String arg1, boolean arg2) {
		synchronized(this){
			int st=this._status;
			this._status=ST_NONE;
			switch(st){
			case ST_WAIT_RECV:
				this._status=ST_ERROR;
				this.notify();
				break;
			case ST_CLOSE:
				this.notify();
				break;
			}
		}		
	}
	@Override
	public void onError(Exception arg0)
	{
		synchronized(this){
			int st=this._status;
			this._status=ST_ERROR;
			switch(st){
			case ST_WAIT_RECV:
				this.notify();
				break;
			}
		}
	}
	@Override
	public void onMessage(String arg0)
	{
		synchronized(this){
			this._sb.append(arg0);
			if(this._status==ST_WAIT_RECV){
				this._status=ST_RECVED;
				this.notify();
			}
		}
	}
	@Override
	public void onOpen(ServerHandshake arg0)
	{
	}
}