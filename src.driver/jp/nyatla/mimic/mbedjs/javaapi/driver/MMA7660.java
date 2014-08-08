package jp.nyatla.mimic.mbedjs.javaapi.driver;

import jp.nyatla.mimic.mbedjs.MbedJsException;
import jp.nyatla.mimic.mbedjs.Vector3d;
import jp.nyatla.mimic.mbedjs.javaapi.I2C;
import jp.nyatla.mimic.mbedjs.javaapi.Mcu;
import jp.nyatla.mimic.mbedjs.javaapi.PinName;

public class MMA7660 {
	private final I2C _i2c;
	private final int _addr;
	/** I2Cを内部生成したか*/
	private final boolean _is_attached;
	private float toInt14f(byte[] i_v){
	    int t = ((0x000000ff & i_v[0]) << 6) | ((0x000000ff & i_v[1]) >> 2);
	    if (t > 16383/2){
	    	t -= 16383;
	    }
	    return t/4096f;
	}	
	/**
	 * 既存のI2Cに追加する場合
	 * @param i_i2c
	 * @param i_address
	 * @throws MbedJsException
	 */
	public MMA7660(I2C i_i2c,int i_address) throws MbedJsException
	{
		this._is_attached=false;
		this._i2c=i_i2c;
		this._addr=i_address;
		this._initDevice();
	}
	/**
	 * Mcuから直接生成する場合
	 * @param i_mcu
	 * @param sda
	 * @param scl
	 * @param i_address
	 * @throws MbedJsException
	 */
	public MMA7660(Mcu i_mcu, int sda, int scl, int i_address) throws MbedJsException
	{
		this._is_attached=true;
		this._i2c=new I2C(i_mcu, sda, scl);
		this._addr=i_address;
		this._i2c.frequency(1000);
		this._initDevice();
	}
	private void _initDevice() throws MbedJsException
	{
		this.setActive(true);
		this.samplerate = 64;
		this._i2c.write(this._addr,new byte[]{0x2a,0x01},false);
	}
	
	public void dispose() throws MbedJsException{
		if(this._is_attached){
			this._i2c.dispose();
		}
	}
	// 
	enum Orientation{
		Up,
		Down,
		Right,
		Left,
		Back,
		Front,
		Unknown
	}
	private int MMA7660_ADDRESS     =0x98;
	private float MMA7660_SENSITIVITY =21.33f;
	 
	private byte MMA7660_XOUT_R      =0x00;
	private byte MMA7660_YOUT_R      =0x01;
	private byte MMA7660_ZOUT_R      =0x02;
	private byte MMA7660_TILT_R      =0x03;
	private byte MMA7660_INT_R       =0x06;
	private byte MMA7660_MODE_R      =0x07;
	private byte MMA7660_SR_R        =0x08;
	private synchronized void sleep_ms(long i_ms) throws MbedJsException
	{
		try {
			this.wait(i_ms);
		} catch (InterruptedException e) {
			throw new MbedJsException(e);
		}
		return;
	}
	
	public boolean testConnection()
	{
		int ret = this._i2c.write(this._addr ,new byte[]{null}, false);
		if (ret == 0){
			return true;
		}else{
			return false;
		}
	}
	public void setActive(boolean state)
	{
		this.active = state;
		byte modereg = this.read(this.MMA7660_MODE_R);
		modereg &=~(1<<0);
		
		if(modereg && (1<<2)){
			modereg &= ~(1<<2);
			this.write(this.MMA7660_MODE_R, modereg);
		}
		modereg +=state;
		write(this.MMA7660_MODE_R,modereg);
		
	}
	public int[] readData_int() throws MbedJsException
	{
		int [] retval = new int[3];
		boolean active_old = this.active;
		if(!this.active){
			this.setActive(true);
			this.sleep_ms(12+(long)(1000/samplerate) );
		}
		byte [] temp;
		boolean alert;
		
		do{
			alert = false;
			temp = this.read(MMA7660_XOUT_R, 3);
			for(int i=0 ; i<3 ;i++){
				if(temp[i] > 63)
					alert = true;
				if(temp[i] > 31)
					temp[i] +=128+64;
				retval[i] = temp[i];
			}
		}while(alert);
		
		if(!active_old)
			setActive(false);
		return retval;
	}
	public float[] readData() throws MbedJsException
	{
		float[] retval= new float[3];
		int[] intdata = new int[3];
		intdata = this.readData_int();
		for (int i=0 ;i<3 ; i++)
		{
			retval[i] = intdata[i]/this.MMA7660_SENSITIVITY;
		}
		return retval;
	}
	public float x(){
		return this.getSingle(0);
	}
	public float y(){
		return this.getSingle(1);
	}
	public float z(){
		return this.getSingle(2);
	}
	public void setSampleRate(int samplerate)
	{
		boolean active_old = this.active;
		setActive(false);
		int[] rates={120 , 64 , 32 , 16 , 8 , 4, 2, 1};
		int sampleLoc = 0;
		int sampleError = 10000;
		byte temp;
		for(int i=0 ; i<8 ; i++){
			temp = (byte) Math.abs(rates[i] - samplerate);
			if(temp<sampleError){
				sampleLoc = i;
				sampleError = temp;
			}
		}
		
		temp =this.read(this.MMA7660_SR_R);
		temp &=~0x07;
		temp |= sampleLoc;
		write(this.MMA7660_SR_R , temp);
		this.samplerate = rates[sampleLoc];
		setActive(active_old);
	}
	
	public Orientation getSide()
	{
		byte tiltreg = this.read(this.MMA7660_TILT_R);
		tiltreg &= 0x03;
		if(tiltreg == 0x01)
			return Orientation.Left;
		if(tiltreg == 0x02)
			return Orientation.Right;
		return Orientation.Unknown;		
	}
	public Orientation getOrientation()
	{
		byte tiltreg = this.read(this.MMA7660_TILT_R);
		tiltreg &= 0x07<<2;
		tiltreg >>=2;
		if(tiltreg == 0x01)
			return Orientation.Left;
		if(tiltreg == 0x02)
			return Orientation.Right;
		if(tiltreg == 0x05)
			return Orientation.Down;
		if(tiltreg == 0x06)
			return Orientation.Up;
		return Orientation.Unknown;
			
	}
	private void write(byte address,byte data)
	{
		byte[] temp ={address,data};
		
		try {
			this._i2c.write(this._addr, temp , false);
		} catch (MbedJsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private int read(byte address)
	{
		I2C.ReadResult retval = null;
		try {
			this._i2c.write(this._addr , new byte[]{address} , true);
			retval = this._i2c.read(this._addr ,1, false);
			
		} catch (MbedJsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (int)retval.data[0];
	}
	private int[] read(byte address ,int length) throws MbedJsException
	{
		I2C.ReadResult ret;
		this._i2c.write(this._addr,new byte[]{address}, true);
		ret = this._i2c.read(this._addr, length, false);
		
		int[] retval= new int [length];
		for(int i = 0;i<length ; i++)
		{
			retval[i] = (int)ret.data[i];
		}
		return retval;
	}
	private float getSingle(int number)
	{
		boolean active_old = this.active;
		
		if(!this.active){
			this.setActive(true);
			try {
				sleep_ms(12 + (long)(1000 / this.samplerate) );
			} catch (MbedJsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		int temp;
		boolean alert;
		do{
			alert = false;
			temp = this.read(this.MMA7660_XOUT_R+(byte)number);
			if(temp > 63)
				alert =true;
			if(temp > 31)
				temp +=128+64;
		}while(alert);
		if(!active_old)
			setActive(false);
		
		return temp / this.MMA7660_SENSITIVITY;
		
	}
	private boolean active;
	private float samplerate=1; 
	//-----------------------------------------------------
	/**
	 * テストケース
	 * @param args
	 */
	public static void main(String args[]){
		try {
			Mcu mcu=new Mcu("10.0.0.2");
			MMA7660 a=new MMA7660(mcu,PinName.p28,PinName.p27,0x98);
			if(a.testConnection())
				System.out.println("detect");
			System.out.println("x="+a.x());
			System.out.println("y="+a.y());
			System.out.println("z="+a.z());
			mcu.close();
			System.out.println("done");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
