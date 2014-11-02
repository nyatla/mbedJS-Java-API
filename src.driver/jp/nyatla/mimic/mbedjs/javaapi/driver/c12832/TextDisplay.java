/* mbed TextDisplay Display Library Base Class
 * Copyright (c) 2007-2009 sford
 * Released under the MIT License: http://mbed.org/license/mit
 */
/* original source code
 * http://mbed.org/users/dreschpe/code/C12832_lcd/
 */
/* 2014/08/18
 * modified by hara41
 */
 
package jp.nyatla.mimic.mbedjs.javaapi.driver.c12832;
import java.io.File;

import jp.nyatla.mimic.mbedjs.MbedJsException;
import jp.nyatla.mimic.mbedjs.javaapi.driver.utils.DriverBaseClass;

public class TextDisplay extends DriverBaseClass{

	/**
	 * 指定した位置に文字を表示する
	 * @param 
	 * column 縦の列
	 * @param 
	 * row 横の行
	 * @param 
	 * c 文字
	 */
	public void character(int column, int row, int c){return;}

	/**
	 * 垂直方向の位置を返す
	 * @return 
	 * 横方向の位置
	 */
	int rows() {return 0;}
	/**
	 * 水平方向の位置を返す
	 * @return
	 * 縦方向の位置
	 */
   	int columns() {return 0;}
   	/**
   	 * 指定した文字を表示する
   	 * @param 
   	 * str 文字
   	 * @return
   	 * 継承した関数による
   	 */
   	int putc(char str){return 0;}

   /* redirect output from a stream (stoud, sterr) to  display
   * @param stream stream that shall be redirected to the TextDisplay
   */
   	
		    // character location
	private int _column;
	private int _row;
		 
		    // colours
	public  int	_foreground;
	public  int _background;
	private byte[] _path;

	/**
	 * テキストディスプレイクラス
	 * @param 
	 * i_name claim関数で使用するファイルネーム
	 */
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
    /**
     * 文字を表示する（カーソルの移動処理を含む）
     * @param 
     * i_value 表示する文字
     * @return
     * 表示した文字
     * @throws 
     * MbedJsException MbedJS例外
     */
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
	/**
	 * 画面を消去する
	 * @throws 
	 * MbedJsException MbedJS例外
	 */
	public void cls() throws MbedJsException 
	{
		this.locate(0, 0);
	    for(int i=0; i<columns()*this.rows(); i++) {
	    	this.putc(' ');
	    }
	}
	/**
	 * カーソルの位置を設定する
	 * @param 
	 * i_column 縦の位置
	 * @param 
	 * i_row 横の位置
	 */
	public void locate(int i_column, int i_row) {
		this._column = i_column;
		this._row = i_row;
	}
 
	int _getc() {
	    return -1;
	}
    /**
     * 表面の色を設定する
     * @param 
     * i_colour 色
     */
	public void foreground(int i_colour) {
		this._foreground = i_colour;
	}
	/**
	 * 背面の色を設定する
	 * @param 
	 * i_colour 色
	 */
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
 

