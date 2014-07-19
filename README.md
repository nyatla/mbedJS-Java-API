mbedJS-Java-API
===============
mbedJSのJavaインタフェイスです。
Javaからネットワーク接続したmbedを制御することができます。

mbedJSはこちらからダウンロードしてください。
https://mbed.org/users/nyatla/code/mbedJS/

特徴
---------------
- mbedJSの動作するmbedシリーズ、LPCXpressoシリーズのMCUをLAN経由で制御することができます。
- mbedSDKとほぼ同一なクラスベースAPIを提供します。
- 最大1000Hz程度のRPCコールができます。

使いかた
---------------
- mbedJSをセットアップしたmbed、又はLPCXpressoを準備してください。
- mbedJS Java APIのmasterパッケージをダウンロードしてください。
- mbedJSのexampleにLEDBlinkのサンプルがあります。LEDをチカチカさせてみましょう。


サンプル
---------------
Lチカのサンプルコードは以下のように書けます。

    import jp.nyatla.mimic.mbedjs.*;
    import jp.nyatla.mimic.mbedjs.javaapi.*;
    
    public class LEDBlink{
    	public static void main(String args[]){
    		try {
    			Mcu mcu=new Mcu("192.168.0.39");//mbedJS IP Address
    			DigitalOut a=new DigitalOut(mcu,PinName.LED1);
    			for(int i=0;i<10000;i++){
    				a.write(i%2);
    				Thread.sleep(100);
    			}
    			mcu.close();
    			System.out.println("done");
    		} catch (InterruptedException|MiMicJsException e) {
    			e.printStackTrace();
    		}
    	}
    }

