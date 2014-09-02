/**
 * http://akizukidenshi.com/download/ds/st/L6470.pdf
 * 
 */

package test.hara41;

import jp.nyatla.mimic.mbedjs.*;
import jp.nyatla.mimic.mbedjs.javaapi.*;
import jp.nyatla.mimic.mbedjs.javaapi.driver.utils.DriverBaseClass;

public class L6470 extends DriverBaseClass{
	public final static int PARAM_ABS_POS = 0x01;
	public final static int LENGTH_ABS_POS = 22;
	public final static int PARAM_ACC = 0x05;
	public final static int LENGTH_ACC = 12;
	public final static int PARAM_DEC = 0x06;
	public final static int LENGTH_DEC = 12;
	public final static int PARAM_MAX_SPEED = 0x07;
	public final static int LENGTH_MAX_SPEED = 10;
	public final static int PARAM_MIN_SPEED = 0x08;
	public final static int LENGTH_MIN_SPEED = 13;
	private final SPI spi;
    private final DigitalOut cs;
    public L6470(Mcu i_mcu , int i_mosi_pin, int i_miso_pin, int i_sclk_pin,
    		int i_cs) throws MbedJsException
    {
    	this.spi = new SPI(i_mcu,i_mosi_pin,i_miso_pin, i_sclk_pin);// 50ms
    	this.spi.frequency(1000000);
    	this.spi.format(8, 3);
    	
    	this.cs = new DigitalOut(i_mcu , i_cs);
    	
    	this.init();

    }
    /**
     * モータパラメータの初期化
     * @throws MbedJsException
     */
    private void init() throws MbedJsException
    {
       	this.cs.write(1);
       	this.resetDevice(); // ok
    	
        int ret;	

       	// モータパラメータ：型番17PM-K044
        // 加速度　step/tic^2 tic=250ns
        int [] s1 = {0x03 , 0xe8};
    	this.setParam2(L6470.PARAM_ACC, s1, L6470.LENGTH_ACC);
    	// 減速度　step/tic^2
    	int [] s2 = {0x03 , 0xe8};
    	this.setParam2(L6470.PARAM_DEC, s2, L6470.LENGTH_DEC);
    	// 最大速度　step/tic
    	int [] s3 = {0x00 , 0x23};
    	this.setParam2(L6470.PARAM_MAX_SPEED, s3 , L6470.LENGTH_MAX_SPEED);
    	//　最小速度　step/tic
    	int [] s4 = {0x00 ,0x00};
    	this.setParam2(L6470.PARAM_MIN_SPEED, s4 , L6470.LENGTH_MIN_SPEED);
    	
  	
    } 
    /**
     * SPIを使って1バイトのデータを送受信
     * @param i_value　送信する値
     * @return 受信した値
     * @throws MbedJsException
     */
    private int sendByte(int i_value) throws MbedJsException
    {
    	this.sleep_ms(1);
    	this.cs.write(0);
    	this.sleep_ms(1);
    	
    	//System.out.println(String.format("sendByte-send: %1$x", i_value));
    	int ret = this.spi.write(i_value);

    	this.sleep_ms(1);
    	this.cs.write(1);
    	this.sleep_ms(1);
    	
    	//System.out.println(String.format("sendByte-recv: %1$x", ret));
    	return ret;

    	
    }   
    /**
     * 複数バイトのデータを送受信
     * @param i_value 送信するデータ
     * @param i_length データの長さ
     * @return　受信したデータ
     * @throws MbedJsException
     */
    private int[] sendRecive(int[] i_value, int i_length) throws MbedJsException
    {    	
    	int [] retval = new int[i_length]; 
    	
    	for(int i=0;i<i_length;i++){
    		retval[i] = this.sendByte(i_value[i]);    		
    	}
    	// 書き込み対策　2度書き込む
    	for(int i=0;i<i_length;i++){
    		retval[i] = this.sendByte(i_value[i]);    		
    	}
    	return retval;
    }
    /**
     * 指定した絶対位置へ移動
     * @param i_dir 移動する方向（1：正転、0:逆転）
     * @param i_abs_pos 絶対位置(10ビットで指定）
     * @throws MbedJsException
     */
    public void goTo_dir(int i_dir , int i_abs_pos) throws MbedJsException
    {
     	int[] value = new int[4]; 
    	value[0] = 0x68 | i_dir;
    	value[1] = (0xff0000 & i_abs_pos)>>16;
    	value[2] = (0x00ff00 & i_abs_pos)>>8;
    	value[3] = (0x0000ff & i_abs_pos);
    	this.sendRecive(value, 4);
    }
    /**
     * 指定した絶対位置へ移動（方向は自動設定）
     * @param i_abs_pos 絶対位置(10ビットで指定)
     * @throws MbedJsException
     */
    public void goTo(int i_abs_pos) throws MbedJsException
    {
     	int[] value = new int[4]; 
    	value[0] = 0x60 ;
    	value[1] = (0xff0000 & i_abs_pos)>>16;
    	value[2] = (0x00ff00 & i_abs_pos)>>8;
    	value[3] = (0x0000ff & i_abs_pos);
    	this.sendRecive(value, 4);
    
    }
    /**
     * 現在位置から指定した方向に指定ステップ数回転
     * @param i_dir 回転する方向（1：正転、0:逆転）
     * @param i_n_step 回転するステップ数(10ビットで指定）
     * @throws MbedJsException
     */
    public void move(int i_dir , int i_n_step) throws MbedJsException
    {
     	int[] value = new int[4]; 
    	value[0] = 0x40 | i_dir;
    	value[1] = (0xff0000 & i_n_step)>>16;
    	value[2] = (0x00ff00 & i_n_step)>>8;
    	value[3] = (0x0000ff & i_n_step);
    	this.sendRecive(value, 4);
    	
    }
    /**
     * 速度と方向を指定して回転
     * @param i_dir 方向（1：正転、0:逆転）
     * @param i_spd 回転速度(step/tick),tick=250ns
     * @throws MbedJsException
     */
    public void run(int i_dir,int i_spd) throws MbedJsException
    {
    	int[] value = new int[4]; 
    	value[0] = 0x50 | i_dir;
    	value[1] = (0xff0000 & i_spd)>>16;
    	value[2] = (0x00ff00 & i_spd)>>8;
    	value[3] = (0x0000ff & i_spd);
    	this.sendRecive(value, 4);
    	
    }
    /**
     * パラメータレジスタに値の書き込み２
     * @param i_param パラメータ名
     * @param i_value 値
     * @param i_len パラメータの長さ
     * @throws MbedJsException
     */
    public void setParam2(int i_param, int[] i_value,int i_len) throws MbedJsException
    {
    	this.setParam(i_param, i_value, i_len);
    	this.setParam(i_param, i_value, i_len);
    }
    /**
     * パラメータレジスタに値の書き込み
     * @param i_param パラメータ名
     * @param i_value 値
     * @param i_len パラメータの長さ
     * @throws MbedJsException
     */
    private void setParam(int i_param, int[] i_value,int i_len) throws MbedJsException
    {
    	int value = (i_param & 0x1f);
    	int length = i_len / 8;
    	if(i_len%8!=0){
    		length =length +1;
    	}
    	this.sendByte(value);
    	
    	for(int i=0;i<length ; i++)
    	{
    		//System.out.println(String.format(">send: %1$x", i_value[i]));
    		this.sendByte(i_value[i]);
    	}
    }
    /**
     * パラメータレジスタから値の読み込み
     * @param i_param パラメータ名
     * @param i_len パラメータの長さ
     * @throws MbedJsException
     */
    public int getParam(int i_param, int i_len) throws MbedJsException
    {
    	int ret = 0;
		int retval =0;
		int value = 0x20 | (i_param & 0x1f);
		int length = i_len / 8;
		if(i_len%8!=0){
			length ++;
		}
		
		this.sendByte(value);
		for (int i=0;i<length ; i++){
			ret =this.sendByte(0x00);
			retval =(retval << 8 )| ret;
		}
	
		System.out.println(String.format("getParam: %1$x", retval));
		return retval;
	}

    
    /**
     * デバイスのリセット
     * @throws MbedJsException
     */
    public void resetDevice() throws MbedJsException
    {
    	int []str = {0xc0};
    	this.sendRecive(str, 1);
    }
    /**
     * モータを減速して停止（現在位置を保持するトルクはかけ続けます）
     * @throws MbedJsException
     */
    public void softStop() throws MbedJsException
    {
    	int []str = {0xb0};
    	this.sendRecive(str, 1);
    }
    /**
     * モータの停止（現在位置を保持するトルクはかけ続けます）
     * @throws MbedJsException
     */
    public void hardStop() throws MbedJsException
    {
    	int []str = {0xb8};
    	this.sendRecive(str, 1);
    }
    /**
     * モータを減速してブリッジ入力をハイインピーダンスに設定
     * 現在位置を保持しません
     * @throws MbedJsException
     */
    public void softHiZ() throws MbedJsException
    {
    	int []str = {0xa0};
    	this.sendRecive(str, 1);
    }
    /**
     * ドライバのブリッジ入力をハイインピーダンスに設定
     * 現在位置を保持しません
     * @throws MbedJsException
     */
    public void hardHiZ() throws MbedJsException
    {
    	int []str = {0xa8};
    	this.sendRecive(str, 1);
    }
    /**
     * ドライバステータスの取得
     * @return ステータス
     * @throws MbedJsException
     */
    public int getStatus() throws MbedJsException
    {
    	int []str = {0xd0 , 0x00 , 0x00};
    	int []ret = this.sendRecive(str, 3);
    	int retval = (ret[1]<<8) | ret[2];
    	return retval;
    }
    /**
     * 現在位置を絶対位置の0に設定する
     * @throws MbedJsException
     */
    public void resetPos() throws MbedJsException
    {
    	int []str = {0xd8};
    	this.sendRecive(str, 1);
    }
    /**
     * ホームポジション(絶対位置の0)に移動
     * @throws MbedJsException
     */
    public void goHome() throws MbedJsException
    {
    	int []str = {0x70};
    	this.sendRecive(str, 1);
    }
	public static void main(String[] args) throws MbedJsException {
		// TODO Auto-generated method stub
		Mcu mcu = new Mcu("10.0.0.2");
		L6470 amp = new L6470(mcu ,PinName.p5 , PinName.p6 ,PinName.p7 ,PinName.p8);
		
		int ret = amp.getStatus();// ok ただし値の確認未
    	System.out.println(String.format("%1$x", ret));
    	
    	amp.run(0, 10000); // ok
		//amp.goHome(); //ok
    	
    	//amp.resetPos();
    	//amp.softStop(); //ok
		//amp.hardStop(); // ok
		//amp.hardHiZ(); //ok
		//amp.softHiZ();//ok
		
		//amp.resetDevice(); //ok
    	    	
    	//amp.goTo_dir(0, -2000);//ok
    	//this.goTo(200);//ok
    	//this.run(1, 10000); // ok
    	//amp.move(0, 1); 
		mcu.close();
		System.out.println("done.");
	}

}
