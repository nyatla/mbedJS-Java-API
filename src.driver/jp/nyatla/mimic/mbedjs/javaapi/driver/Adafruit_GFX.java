/**
**********************************
This is a our graphics core library, for all our displays. 
We'll be adapting all the
existing libaries to use this core to make updating, support 
and upgrading easier!
 
Adafruit invests time and resources providing this open source code, 
please support Adafruit and open-source hardware by purchasing 
products from Adafruit!
 
Written by Limor Fried/Ladyada  for Adafruit Industries.  
BSD license, check license.txt for more information
All text above must be included in any redistribution
****************************************
 
 
   Modified by Neal Horman 7/14/2012 for use in LPC1768
 
*/

package jp.nyatla.mimic.mbedjs.javaapi.driver;

import jp.nyatla.mimic.mbedjs.javaapi.I2C;
import jp.nyatla.mimic.mbedjs.javaapi.Mcu;

public class Adafruit_GFX extends Adafruit_LEDBackpack
{
	private static final short BLACK = 0;
	private static final short WHITE = 1;
	private int _BV(int i_bit)
	{
		return (1<<(i_bit));
	}
	 
	public Adafruit_GFX(I2C i_i2c,int i_address , short w , short h) {
		super(i_i2c, i_address);
		this._init(w, h);
		
	}
	public Adafruit_GFX(Mcu i_mcu, int i_sda, int i_scl,int i_address, short w , short h) {
		super(i_mcu, i_sda, i_scl, i_address);
		this._init(w, h);
		
	}
	private void _init(short w, short h)
	{
		this._rawWidth=w;
        this._rawHeight=h;
        this._width=w;
        this._height = h;
        this.cursor_x = 0;
        this.cursor_y = 0;
        this.textcolor = this.WHITE;
        this.textbgcolor = this.BLACK;
        this.textsize = 1;
        this.rotation = 0;
        this.wrap =true;
	}
	public void drawPixel(int x , int y , short color)
	{
		
	}
	public void invertDisplay(boolean i)
	{
		
	}
	int _putc(byte value){
		return writeChar(value);}
	int _getc(){ 
		return -1;}
	
	public void drawLine(short x0 , short y0, short x1 , short y1,short color)
	{
	    //short steep = Math.abs(y1 - y0) > Math.abs(x1 - x0);
	    boolean steep = Math.abs(y1 - y0) > Math.abs(x1 - x0);
	    

    	short t;
	    if (steep)
	    {
	    	//swap(x0,y0);
	    	t = x0;
	    	x0 = y0;
	    	y0 = t;
	    	
	    	// swap(x1,y1);
	        t = x1;
	    	x1 = y1;
	    	y1 = t;
	    	
	    }
	    
	    if (x0 > x1)
	    {
	    	//swap(x0,x1);
	    	t =x0;
	    	x0 = x1;
	    	x1= t;
	    	
	    	// swap(y0,y1);
	    	t = y0;
	    	y0 = y1;
	    	y1 = t;
	    }
	    
	    short dx, dy;
	    dx = (short) (x1 - x0);
	    dy = (short) Math.abs(y1 - y0);
	    
	    short err = (short) (dx / 2);
	    short ystep;
	    
	    if (y0 < y1)
	        ystep = 1;
	    else
	        ystep = -1;
	    
	    for (; x0<=x1; x0++)
	    {
	        if (steep)
	            drawPixel(y0, x0, color);
	        else
	            drawPixel(x0, y0, color);
	 
	        err -= dy;
	        if (err < 0)
	        {
	            y0 += ystep;
	            err += dx;
	        }
	    }		
	}
	public void drawFastVLine(short x, short y, short h, short color)
	{
	    // stupidest version - update in subclasses if desired!
	    this.drawLine(x, y, x, (short) (y+h-1), color);
	}
	public void drawFastHLine(short x, short y, short w, short color)
	{
	    // stupidest version - update in subclasses if desired!
		this.drawLine(x, y, (short) (x+w-1), y, color);
	}
	public void drawRect(short x, short y, short w, short h, short color)
	{
		this.drawFastHLine(x, y, w, color);
		this.drawFastHLine(x, (short) (y+h-1), w, color);
		this.drawFastVLine(x, y, h, color);
		this.drawFastVLine((short) (x+w-1), y, h, color);		
	}
	public void fillRect(short x, short y, short w, short h, short color)
	{
	    // stupidest version - update in subclasses if desired!
	    for (short i=x; i<x+w; i++)
	    	this.drawFastVLine(i, y, h, color); 
	}
	public void fillScreen(short color){
		this.fillRect((short)0, (short)0, _width, _height, color);
	}
	 
	public void drawCircle(short x0, short y0, short r, short color)
	{
		short f = (short) (1 - r);
		short ddF_x =1;
		short ddF_y =(short) (-2 * r);
		short x = 0;
		short y = r;
		
		this.drawPixel(x0 , (short) (y0+r) , color);
		this.drawPixel(x0 , (short) (y0-r) , color);
		this.drawPixel((short) (x0+r) , y0 , color);
		this.drawPixel((short) (x0-r) , y0 , color);
		
		while (x<y)
		{
			if(f>=0)
			{
				y--;
				ddF_y += 2;
				f +=ddF_y;
			}
			x++;
			ddF_x += 2;
			f += ddF_x;
			
			this.drawPixel(x0 + x, y0 + y, color);
			this.drawPixel(x0 - x, y0 + y, color);
			this.drawPixel(x0 + x, y0 - y, color);
			this.drawPixel(x0 - x, y0 - y, color);
			this.drawPixel(x0 + y, y0 + x, color);
			this.drawPixel(x0 - y, y0 + x, color);
			this.drawPixel(x0 + y, y0 - x, color);
			this.drawPixel(x0 - y, y0 - x, color);
		}
		
	}
	public void drawCircleHelper(short x0, short y0, short r, byte cornername, short color){
	    short f     = (short) (1 - r);
	    short ddF_x = 1;
	    short ddF_y = (short) (-2 * r);
	    short x     = 0;
	    short y     = r;
	    
	    while (x<y)
	    {
	        if (f >= 0)
	        {
	            y--;
	            ddF_y += 2;
	            f += ddF_y;
	        }
	        x++;
	        ddF_x += 2;
	        f += ddF_x;
	        
	        
	        if ((cornername & 0x4) !=0)
	        {
	        	this.drawPixel(x0 + x, y0 + y, color);
	        	this.drawPixel(x0 + y, y0 + x, color);
	        } 
	 
	        if ((cornername & 0x2) !=0)
	        {
	        	this.drawPixel(x0 + x, y0 - y, color);
	        	this.drawPixel(x0 + y, y0 - x, color);
	        }
	 
	        if ((cornername & 0x8)!=0)
	        {
	        	this.drawPixel(x0 - y, y0 + x, color);
	        	this.drawPixel(x0 - x, y0 + y, color);
	        }
	        
	        if ((cornername & 0x1)!=0)
	        {
	        	this.drawPixel(x0 - y, y0 - x, color);
	        	this.drawPixel(x0 - x, y0 - y, color);
	        }
	    }
	}
	public void fillCircle(short x0, short y0, short r, short color){
		this.drawFastVLine(x0,(short)( y0-r),(short)( 2*r+1), color);
		this.fillCircleHelper(x0, y0, r,(byte) 3, (short)0, color);
	}
	public void fillCircleHelper(short x0, short y0, short r, byte cornername, short delta, short color){
		short f     = (short) (1 - r);
		short ddF_x = 1;
		short ddF_y = (short) (-2 * r);
		short x     = 0;
		short y     = r;
	    
	    while (x<y)
	    {
	        if (f >= 0)
	        {
	            y--;
	            ddF_y += 2;
	            f += ddF_y;
	        }
	        x++;
	        ddF_x += 2;
	        f += ddF_x;
	        
	        if ((cornername & 0x1) !=0)
	        {
	        	this.drawFastVLine((short)(x0+x), (short)(y0-y),(short)( 2*y+1+delta), color);
	        	this.drawFastVLine((short)(x0+y), (short)(y0-x), (short)(2*x+1+delta), color);
	        }
	 
	        if ((cornername & 0x2) != 0)
	        {
	        	this.drawFastVLine((short)(x0-x), (short)(y0-y), (short)(2*y+1+delta), color);
	        	this.drawFastVLine((short)(x0-y), (short)(y0-x), (short)(2*x+1+delta), color);
	        }
	    }
	}
	 
	public void drawTriangle(short x0, short y0, short x1, short y1, short x2, short y2, short color){
		 
		this.drawLine(x0, y0, x1, y1, color);
		this.drawLine(x1, y1, x2, y2, color);
		this.drawLine(x2, y2, x0, y0, color);
	}
	public void fillTriangle(short x0, short y0, short x1, short y1, short x2, short y2, short color){
	    short a, b, y, last;
	    
	    short t;
	    // Sort coordinates by Y order (y2 >= y1 >= y0)
	    if (y0 > y1)
	        //swap(y0, y1);
	    	t = y0;
	    	y0 = y1;
	    	y1 = t;
	    	//swap(x0, x1);
	    	t=x0;
	    	x0 = x1;
	    	x1 = t;
	    if (y1 > y2)
	        //swap(y2, y1);
	    	t= y2 ;
	    	y2 = y1;
	    	y1 = t;
	    	//swap(x2, x1);
	    	t = x2;
	    	x2 = x1;
	    	x1 = t;
	    	
	    if (y0 > y1)
	        //swap(y0, y1);
	    	t = y0;
	    	y0 = y1;
	    	y1 = t;
	    	
	    	//swap(x0, x1);
	    	t = x0;
	    	x0 = x1;
	    	x1 = t;
	    
	    if(y0 == y2)
	    { // Handle awkward all-on-same-line case as its own thing
	        a = b = x0;
	        if(x1 < a)
	            a = x1;
	        else if(x1 > b)
	            b = x1;
	            
	        if(x2 < a)
	            a = x2;
	        else if(x2 > b) b = x2;
	        	this.drawFastHLine(a, y0, (short) (b-a+1), color);
	        return;
	    }
	 
	    short
	        dx01 = (short) (x1 - x0),
	        dy01 = (short) (y1 - y0),
	        dx02 = (short) (x2 - x0),
	        dy02 = (short) (y2 - y0),
	        dx12 = (short) (x2 - x1),
	        dy12 = (short) (y2 - y1),
	        sa   = 0,
	        sb   = 0;
	 
	    // For upper part of triangle, find scanline crossings for segments
	    // 0-1 and 0-2.  If y1=y2 (flat-bottomed triangle), the scanline y1
	    // is included here (and second loop will be skipped, avoiding a /0
	    // error there), otherwise scanline y1 is skipped here and handled
	    // in the second loop...which also avoids a /0 error here if y0=y1
	    // (flat-topped triangle).
	    if(y1 == y2)
	        last = y1;   // Include y1 scanline
	    else
	        last = (short) (y1-1); // Skip it
	 
	    for(y=y0; y<=last; y++)
	    {
	        a   = (short) (x0 + sa / dy01);
	        b   = (short) (x0 + sb / dy02);
	        sa += dx01;
	        sb += dx02;
	        /* longhand:
	        a = x0 + (x1 - x0) * (y - y0) / (y1 - y0);
	        b = x0 + (x2 - x0) * (y - y0) / (y2 - y0);
	        */
	        if(a > b){
	            //swap(a,b);
	        	t=a;
	        	a=b;
	        	b=t;
	        }
	        this.drawFastHLine(a, y, (short)(b-a+1), color);
	    }
	 
	    // For lower part of triangle, find scanline crossings for segments
	    // 0-2 and 1-2.  This loop is skipped if y1=y2.
	    sa = (short) (dx12 * (y - y1));
	    sb = (short) (dx02 * (y - y0));
	    for(; y<=y2; y++)
	    {
	        a   = (short) (x1 + sa / dy12);
	        b   = (short) (x0 + sb / dy02);
	        sa += dx12;
	        sb += dx02;
	        /* longhand:
	        a = x1 + (x2 - x1) * (y - y1) / (y2 - y1);
	        b = x0 + (x2 - x0) * (y - y0) / (y2 - y0);
	        */
	        if(a > b){
	            //swap(a,b);
	        	t=a;
	        	a=b;
	        	b=t;
	        }
	        this.drawFastHLine(a, y, (short) (b-a+1), color);
	    }
	}
	public void drawRoundRect(short x, short y, short w, short h, short r, short color){
		// smarter version
		this.drawFastHLine((short)(x+r)  , y    , (short)(w-2*r), color); // Top
		this.drawFastHLine((short)(x+r) ,(short)( y+h-1),(short)( w-2*r), color); // Bottom
		this.drawFastVLine(  x    ,(short)( y+r)  ,(short)( h-2*r), color); // Left
		this.drawFastVLine((short)(  x+w-1),(short)( y+r ) ,(short)( h-2*r), color); // Right
	    // draw four corners
		this.drawCircleHelper((short)(x+r)    ,(short)( y+r)    , r,(byte) 1, color);
		this.drawCircleHelper((short)(x+w-r-1),(short)( y+r)    , r,(byte) 2, color);
		this.drawCircleHelper((short)(x+w-r-1),(short)( y+h-r-1), r,(byte) 4, color);
		this.drawCircleHelper((short)(x+r)    ,(short)( y+h-r-1), r,(byte) 8, color);
	}
	public void fillRoundRect(short x, short y, short w, short h, short r, short color){
	    // smarter version
		this.fillRect((short)(x+r), y,(short)( w-2*r), h, color);
	    
	    // draw four corners
		this.fillCircleHelper((short)(x+w-r-1),(short)( y+r), r, (byte)1,(short)( h-2*r-1), color);
		this.fillCircleHelper((short)(x+r    ),(short)( y+r), r, (byte)2,(short)( h-2*r-1), color);
	}
	 
	public void drawBitmap(short x, short y, const byte *bitmap, short w, short h, short color){
	    for (short j=0; j<h; j++)
	    {
	        for (short i=0; i<w; i++ )
	        {
	            if (bitmap[i + (j/8)*w] & _BV(j%8))
	                drawPixel(x+i, y+j, color);
	        }
	    }
	}
	public void drawChar(short x, short y, byte c, short color, short bg, byte size){
	    if(
	            (x >= this._width) || // Clip right
	            (y >= this._height) || // Clip bottom
	            ((x + 5 * size - 1) < 0) || // Clip left
	            ((y + 8 * size - 1) < 0) // Clip top
	            )
	        return;
	        
	        for (short i=0; i<6; i++ )
	        {
	            short line = 0;
	     
	            if (i == 5) 
	                line = 0x0;
	            else 
	                line = font[(c*5)+i];
	                
	            for (short j = 0; j<8; j++)
	            {
	                if ((line & 0x1) != 0)
	                {
	                    if (size == 1) // default size
	                    	this.drawPixel(x+i, y+j, color);
	    //#ifdef WANT_ABSTRACTS
	                    else // big size
	                    	this.fillRect((short)(x+(i*size)), (short)(y+(j*size)), size, size, color);
	    //#endif
	                }
	                else if (bg != color)
	                {
	                    if (size == 1) // default size
	                    	this.drawPixel(x+i, y+j, bg);
	    //#ifdef WANT_ABSTRACTS
	                    else // big size
	                    	this.fillRect((short)(x+i*size), (short)(y+j*size), size, size, bg);
	    //#endif
	                }
	                line >>= 1;
	            }
	        }
	}
	public int writeChar(byte c){
	    if (c == '\n')
	    {
	    	this.cursor_y += this.textsize*8;
	        this.cursor_x = 0;
	    }
	    else if (c == '\r')
	    	this.cursor_x = 0;
	    else
	    {
	        drawChar(this.cursor_x, this.cursor_y,
	        		c, this.textcolor, this.textbgcolor, this.textsize);
	        this.cursor_x += this.textsize*6;
	        if (this.wrap && (this.cursor_x > (this._width - this.textsize*6)))
	        {
	        	this.cursor_y += this.textsize*8;
	            this.cursor_x = 0;
	        }
	    }
	    return 1;
	}
	 
	public short width() { 
		return this._width;
	}
	public short height() { 
		return this._height; 
	}
	 
	public void setCursor(short x, short y) 
	{ 
		this.cursor_x = x;
		this.cursor_y = y; 
	}
	public void setTextSize(byte s) {
		this.textsize = (s > 0) ? s : 1; 
	}
	public void setTextColor(short c) {
		this.textcolor = c; 
		this.textbgcolor = c; 
	}
	public void setTextColor(short c, short b) { 
		this.textcolor = c; 
		this.textbgcolor = b; 
		}
	public void setTextWrap(boolean w) {
		this.wrap = w; }
	 
	public void setRotation(byte x)
	{
	    x %= 4;  // cant be higher than 3
	    this.rotation = x;
	    switch (x)
	    {
	        case 0:
	        case 2:
	            this._width = this._rawWidth;
	            this._height = this._rawHeight;
	            break;
	        case 1:
	        case 3:
	        	this._width = this._rawHeight;
	            this._height = this._rawWidth;
	            break;
	    }
	}
	public short getRotation() { rotation %= 4; return rotation; }
	 
	protected short  _rawWidth, _rawHeight;   // this is the 'raw' display w/h - never changes
	protected short  _width, _height; // dependent on rotation
	protected short  cursor_x, cursor_y;
	protected short textcolor, textbgcolor;
	protected byte  textsize;
	protected byte  rotation;
	protected boolean  wrap; // If set, 'wrap' text at right edge of display

}
