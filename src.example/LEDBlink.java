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


import jp.nyatla.mimic.mbedjs.javaapi.*;

/**
 * LEDブリンクのテスト
 *
 */
public class LEDBlink{
	public static void main(String args[]){
		try {
			Mcu mcu=new Mcu("192.168.128.39");
			DigitalOut a=new DigitalOut(mcu,PinName.LED1);
			for(int i=0;i<10000;i++){
				a.write(i%2);
				Thread.sleep(100);
			}
			mcu.close();
			System.out.println("done");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
