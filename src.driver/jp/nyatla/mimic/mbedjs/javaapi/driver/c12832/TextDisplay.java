/* mbed TextDisplay Display Library Base Class
 * Copyright (c) 2007-2009 sford
 * Released under the MIT License: http://mbed.org/license/mit
 */
 
package jp.nyatla.mimic.mbedjs.javaapi.driver.c12832;
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


	public TextDisplay(byte [] i_name) //: Stream(name){
	{
		
			
		this._row = 0;
		this._column = 0;
	    if (i_name[0] == '\0') {
	    	this._path[0] = '\0';
	    } else {
	    	this._path = new byte[i_name.length + 2];
	        //sprintf(_path, "/%s", name);
	    	this._path = i_name;
	    }
	}
    
	public int _putc(int i_value) throws MbedJsException 
	{
	    if(i_value == '\n') {
	    	this._column = 0;
	    	this._row++;
	        if(this._row >= this.rows()) {
	        	this._row = 0;
	        }
	    } else {
	    	this.character(this._column, this._row, i_value);
	    	this._column++;
	        if(this._column >= this.columns()) {
	        	this._column = 0;
	        	this._row++;
	            if(this._row >= this.rows()) {
	            	this._row = 0;
	            }
	        }
	    }
	    return i_value;
	}
	 
	// crude cls implementation, should generally be overwritten in derived class
	public void cls() throws MbedJsException 
	{
		this.locate(0, 0);
	    for(int i=0; i<columns()*this.rows(); i++) {
	    	this.putc(' ');
	    }
	}
 
	public void locate(int i_column, int i_row) {
		this._column = i_column;
		this._row = i_row;
	}
 
	int _getc() {
	    return -1;
	}
        
	public void foreground(int i_colour) {
		this._foreground = i_colour;
	}
 
	public void background(int i_colour) {
		this._background = i_colour;
	}
 
	File claim (File i_stream) {
	    if ( this._path[0] == '\0') {
	        System.err.println("claim requires a name to be given in the instantioator of the TextDisplay instance!\r\n");
	        return null;
	    }
	    	    	    
	    //if (freopen(_path, "w", stream) == NULL) {
	        // Failed, should not happen
	    //    return false;
	    //}
	    return new File(this._path.toString());
	    
	    // make sure we use line buffering
	    //setvbuf(stdout, NULL, _IOLBF, columns());
	    
	}
}
 

