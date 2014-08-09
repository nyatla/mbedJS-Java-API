package jp.nyatla.mimic.mbedjs.javaapi.driver;

import jp.nyatla.mimic.mbedjs.MbedJsException;
import jp.nyatla.mimic.mbedjs.javaapi.I2C;
import jp.nyatla.mimic.mbedjs.javaapi.Mcu;
import jp.nyatla.mimic.mbedjs.javaapi.PinName;
/**
 * Port from https://mbed.org/components/MMA7660/
 * @author hara41
 */
public class MMA7660 extends DriverBaseClass
{
	private final I2C _i2c;
	private final int _addr;
	/** I2Cを内部生成したか*/
	private final boolean _is_attached;
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
	public MMA7660(Mcu i_mcu, int i_sda, int i_scl, int i_address) throws MbedJsException
	{
		this._is_attached=true;
		this._i2c=new I2C(i_mcu, i_sda, i_scl);
		this._addr=i_address;
		this._i2c.frequency(1000);
		this._initDevice();
	}
	private void _initDevice() throws MbedJsException
	{
		this.setActive(true);
		this._samplerate = 64;
		this._i2c.write(this._addr,new byte[]{0x2a,0x01},false);
	}
	
	public void dispose() throws MbedJsException{
		if(this._is_attached){
			this._i2c.dispose();
		}
	}
	public final static int Up		=0;
	public final static int Down	=1;
	public final static int Right	=2;
	public final static int Left	=3;
	public final static int Back	=4;
	public final static int Front	=5;
	public final static int Unknown	=6;


	public final static int I2C_ADDRESS     =0x98;
	private final static float MMA7660_SENSITIVITY =21.33f;
	 
	private final static byte MMA7660_XOUT_R      =0x00;
	private final static byte MMA7660_YOUT_R      =0x01;
	private final static byte MMA7660_ZOUT_R      =0x02;
	private final static byte MMA7660_TILT_R      =0x03;
	private final static byte MMA7660_INT_R       =0x06;
	private final static byte MMA7660_MODE_R      =0x07;
	private final static byte MMA7660_SR_R        =0x08;
	
	public boolean testConnection() throws MbedJsException
	{
		int ret = this._i2c.write(this._addr ,new byte[]{0x00}, false);
		if (ret == 0){
			return true;
		}else{
			return false;
		}
	}
	public void setActive(boolean i_state) throws MbedJsException
	{
		this._active = i_state;
		int modereg = this.read(MMA7660_MODE_R);
		modereg &=~(1<<0);
		
		//if((modereg?true:false) && ((1<<2)?true:false)){
			modereg &= ~(1<<2);
			this.write(MMA7660_MODE_R, (byte) modereg);
		//}
		modereg +=i_state?1:0;
		this.write(MMA7660_MODE_R,(byte) modereg);
		
	}
	public int[] readData_int() throws MbedJsException
	{
		int [] retval = new int[3];
		boolean active_old = this._active;
		if(!this._active){
			this.setActive(true);
			this.sleep_ms(12+(long)(1000/this._samplerate) );
		}
		int [] temp;
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
		
		if(!active_old){
			this.setActive(false);
		}
		return retval;
	}
	public float[] readData() throws MbedJsException
	{
		float[] retval= new float[3];
		int[] intdata = new int[3];
		intdata = this.readData_int();
		for (int i=0 ;i<3 ; i++)
		{
			retval[i] = intdata[i]/MMA7660_SENSITIVITY;
		}
		return retval;
	}
	public float x() throws MbedJsException{
		return this.getSingle(0);
	}
	public float y() throws MbedJsException{
		return this.getSingle(1);
	}
	public float z() throws MbedJsException{
		return this.getSingle(2);
	}
	public void setSampleRate(int i_samplerate) throws MbedJsException
	{
		boolean active_old = this._active;
		this.setActive(false);
		int[] rates={120 , 64 , 32 , 16 , 8 , 4, 2, 1};
		int sampleLoc = 0;
		int sampleError = 10000;
		int temp;
		for(int i=0 ; i<8 ; i++){
			temp = (byte) Math.abs(rates[i] - i_samplerate);
			if(temp<sampleError){
				sampleLoc = i;
				sampleError = temp;
			}
		}
		
		temp =this.read(MMA7660_SR_R);
		temp &=~0x07;
		temp |= sampleLoc;
		this.write(MMA7660_SR_R , (byte)temp);
		this._samplerate = rates[sampleLoc];
		this.setActive(active_old);
	}
	/**
	 * 	
	 * @return Left,Right
	 * @throws MbedJsException
	 */
	public int getSide() throws MbedJsException
	{
		int tiltreg = this.read(MMA7660_TILT_R);
		tiltreg &= 0x03;
		if(tiltreg == 0x01)
			return Left;
		if(tiltreg == 0x02)
			return Right;
		return Unknown;		
	}
	/**
	 * 
	 * @return Left,Right,Up,Down,Unknown
	 * @throws MbedJsException
	 */
	public int getOrientation() throws MbedJsException
	{
		int tiltreg = this.read(MMA7660_TILT_R);
		tiltreg &= 0x07<<2;
		tiltreg >>=2;
		if(tiltreg == 0x01)
			return Left;
		if(tiltreg == 0x02)
			return Right;
		if(tiltreg == 0x05)
			return Down;
		if(tiltreg == 0x06)
			return Up;
		return Unknown;
			
	}
	private void write(byte i_address,byte i_data) throws MbedJsException
	{
		byte[] temp ={i_address,i_data};
		this._i2c.write(this._addr, temp , false);
	}
	private int read(byte i_address) throws MbedJsException
	{
		I2C.ReadResult retval = null;
		this._i2c.write(this._addr , new byte[]{i_address} , true);
		retval = this._i2c.read(this._addr ,1, false);
		return (int)retval.data[0];
	}
	private int[] read(byte address ,int i_length) throws MbedJsException
	{
		I2C.ReadResult ret;
		this._i2c.write(this._addr,new byte[]{address}, true);
		ret = this._i2c.read(this._addr, i_length, false);
		
		int[] retval= new int [i_length];
		for(int i = 0;i<i_length ; i++)
		{
			retval[i] = (int)ret.data[i];
		}
		return retval;
	}
	private float getSingle(int i_number) throws MbedJsException
	{
		boolean active_old = this._active;
		
		if(!this._active){
			this.setActive(true);
			this.sleep_ms(12 + (long)(1000 / this._samplerate) );
		}
		
		int temp;
		boolean alert;
		do{
			alert = false;
			temp = this.read((byte)(MMA7660_XOUT_R+i_number));
			if(temp > 63)
				alert =true;
			if(temp > 31)
				temp +=128+64;
		}while(alert);
		if(!active_old)
			this.setActive(false);
		
		return temp / MMA7660_SENSITIVITY;
		
	}
	private boolean _active;
	private float _samplerate=1; 
	//-----------------------------------------------------
	/**
	 * テストケース
	 * @param args
	 */
	public static void main(String args[]){
		try {
			Mcu mcu=new Mcu("10.0.0.2");
			MMA7660 a=new MMA7660(mcu,PinName.p28,PinName.p27,0x98);
			if(!a.testConnection())
				System.out.println("Can't detect");
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
