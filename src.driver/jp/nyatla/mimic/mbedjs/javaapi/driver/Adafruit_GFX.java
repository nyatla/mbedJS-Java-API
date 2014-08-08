package jp.nyatla.mimic.mbedjs.javaapi.driver;

public class Adafruit_GFX {

	private int _BV(int i_bit)
	{
		return (1<<(i_bit));
	}
	public Adafruit_GFX(short w , short h) {
		
		
	}
	public void drawPixel(short x , short y , short color)
	{
		
	}
	public void invertDisplay(boolean i)
	{
		
	}
	int _putc(int value){return writeChar(value);}
	int _getc(){ return -1;}
	
	public void drawLine(short x0 , short y0, short x1 , short y1,short color)
	{
		
	}
	public void drawFastVLine(int16_t x, int16_t y, int16_t h, uint16_t color);
	public void drawFastHLine(int16_t x, int16_t y, int16_t w, uint16_t color);
	public void drawRect(int16_t x, int16_t y, int16_t w, int16_t h, uint16_t color);
	public void fillRect(int16_t x, int16_t y, int16_t w, int16_t h, uint16_t color);
	public void fillScreen(uint16_t color);
	 
	public void drawCircle(int16_t x0, int16_t y0, int16_t r, uint16_t color);
	public void drawCircleHelper(int16_t x0, int16_t y0, int16_t r, uint8_t cornername, uint16_t color);
	public void fillCircle(int16_t x0, int16_t y0, int16_t r, uint16_t color);
	public void fillCircleHelper(int16_t x0, int16_t y0, int16_t r, uint8_t cornername, int16_t delta, uint16_t color);
	 
	public void drawTriangle(int16_t x0, int16_t y0, int16_t x1, int16_t y1, int16_t x2, int16_t y2, uint16_t color);
	public void fillTriangle(int16_t x0, int16_t y0, int16_t x1, int16_t y1, int16_t x2, int16_t y2, uint16_t color);
	public void drawRoundRect(int16_t x0, int16_t y0, int16_t w, int16_t h, int16_t radius, uint16_t color);
	public void fillRoundRect(int16_t x0, int16_t y0, int16_t w, int16_t h, int16_t radius, uint16_t color);
	 
	public void drawBitmap(int16_t x, int16_t y, const uint8_t *bitmap, int16_t w, int16_t h, uint16_t color);
	public void drawChar(int16_t x, int16_t y, unsigned char c, uint16_t color, uint16_t bg, uint8_t size);
	public size_t writeChar(uint8_t);
	 
	public int16_t width(void) { return _width; };
	public int16_t height(void) { return _height; };
	 
	public void setCursor(int16_t x, int16_t y) { cursor_x = x; cursor_y = y; };
	public void setTextSize(uint8_t s) { textsize = (s > 0) ? s : 1; };
	public void setTextColor(uint16_t c) { textcolor = c; textbgcolor = c; }
	public void setTextColor(uint16_t c, uint16_t b) { textcolor = c; textbgcolor = b; };
	public void setTextWrap(bool w) { wrap = w; };
	 
	public void setRotation(uint8_t r);
	public uint8_t getRotation(void) { rotation %= 4; return rotation; };
	 
	protected int16_t  _rawWidth, _rawHeight;   // this is the 'raw' display w/h - never changes
	protected int16_t  _width, _height; // dependent on rotation
	protected int16_t  cursor_x, cursor_y;
	protected uint16_t textcolor, textbgcolor;
	protected uint8_t  textsize;
	protected uint8_t  rotation;
	protected bool  wrap; // If set, 'wrap' text at right edge of display

	    //------------------------------------------
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
