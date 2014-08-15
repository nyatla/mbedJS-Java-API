/* mbed TextDisplay Display Library Base Class
 * Copyright (c) 2007-2009 sford
 * Released under the MIT License: http://mbed.org/license/mit
 */
 
package test.hara41;
import java.io.File;

import jp.nyatla.mimic.mbedjs.MbedJsException;
import jp.nyatla.mimic.mbedjs.javaapi.driver.utils.DriverBaseClass;

public class TextDisplay extends DriverBaseClass{
	 /** output a character at the given position
    *
    * @param column column where charater must be written
    * @param  row where character must be written
    * @param c the character to be written to the TextDisplay
    */

	public void character(int column, int row, int c){return;}

   /** return number if rows on TextDisplay
    * @result number of rows
    */
	int rows() {return 0;}

   /** return number if columns on TextDisplay
   * @result number of rows
   */
   	int columns() {return 0;}
   	int putc(char str){return 0;}
   // functions that come for free, but can be overwritten

   /** redirect output from a stream (stoud, sterr) to  display
   * @param stream stream that shall be redirected to the TextDisplay
   */
   	
		    // character location
	private int _column;
	private int _row;
		 
		    // colours
	public  int	_foreground;
	public  int _background;
	private byte[] _path;
	public TextDisplay() {
		// TODO Auto-generated constructor stub
	}

	TextDisplay(byte [] name) //: Stream(name){
	{
	_row = 0;
    _column = 0;
    if (name[0] == '\0') {
        _path[0] = '\0';
    } else {
        _path = new byte[name.length + 2];
        //sprintf(_path, "/%s", name);
        _path = name;
    }
}
    
	int _putc(int value) throws MbedJsException 
	{
	    if(value == '\n') {
	        _column = 0;
	        _row++;
	        if(_row >= rows()) {
	            _row = 0;
	        }
	    } else {
	        character(_column, _row, value);
	        _column++;
	        if(_column >= columns()) {
	            _column = 0;
	            _row++;
	            if(_row >= rows()) {
	                _row = 0;
	            }
	        }
	    }
	    return value;
	}
	 
	// crude cls implementation, should generally be overwritten in derived class
	void cls() {
	    locate(0, 0);
	    for(int i=0; i<columns()*rows(); i++) {
	        putc(' ');
	    }
	}
 
	void locate(int column, int row) {
	    _column = column;
	    _row = row;
	}
 
	int _getc() {
	    return -1;
	}
        
	void foreground(int colour) {
	    _foreground = colour;
	}
 
	void background(int colour) {
	    _background = colour;
	}
 
	File claim (File i_stream) {
	    if ( _path[0] == '\0') {
	        System.err.println("claim requires a name to be given in the instantioator of the TextDisplay instance!\r\n");
	        return null;
	    }
	    	    	    
	    //if (freopen(_path, "w", stream) == NULL) {
	        // Failed, should not happen
	    //    return false;
	    //}
	    return new File(_path.toString());
	    
	    // make sure we use line buffering
	    //setvbuf(stdout, NULL, _IOLBF, columns());
	    
	}
}
 

