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

/**
 * ポート識別子です。
 * mbedSDKのポート名に対応します。
 */
public class PortName
{
	private final static int PORTID=0x00010000;
	public final static int Port0=PORTID;
	public final static int Port1=PORTID+1;
	public final static int Port2=PORTID+2;
	public final static int Port3=PORTID+3;
	public final static int Port4=PORTID+4;
	public final static int Port5=PORTID+5;
};
