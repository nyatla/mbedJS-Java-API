/* mbed library for the mbed Lab Board  128*32 pixel LCD
 * use C12832 controller
 * Copyright (c) 2012 Peter Drescher - DC2PD
 * Released under the MIT License: http://mbed.org/license/mit
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
/* original source code
 * http://mbed.org/users/dreschpe/code/C12832_lcd/
 */
/* 2014/08/18
 * modified by hara41
 */

package jp.nyatla.mimic.mbedjs.javaapi.driver.c12832;

import jp.nyatla.mimic.mbedjs.*;
import jp.nyatla.mimic.mbedjs.javaapi.*;

public class C12832 extends GraphicsDisplay {
	
	public static final char Small_7[] = {
	        19,9,9,2,                                    // Length,horz,vert,byte/vert    
	        0x05, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char  
	        0x02, 0x00, 0x00, 0x9E, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char !
	        0x04, 0x00, 0x00, 0x06, 0x00, 0x00, 0x00, 0x06, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char "
	        0x06, 0x00, 0x00, 0x50, 0x00, 0xF8, 0x00, 0x50, 0x00, 0xF8, 0x00, 0x50, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char #
	        0x06, 0x00, 0x00, 0x8C, 0x00, 0x92, 0x00, 0xFE, 0x01, 0xA2, 0x00, 0x40, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char $
	        0x07, 0x1E, 0x00, 0x92, 0x00, 0x5E, 0x00, 0x20, 0x00, 0xF8, 0x00, 0x94, 0x00, 0xF2, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char %
	        0x07, 0x00, 0x00, 0x64, 0x00, 0x9A, 0x00, 0xAA, 0x00, 0xCC, 0x00, 0x60, 0x00, 0x80, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char &
	        0x02, 0x00, 0x00, 0x06, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char '
	        0x03, 0x00, 0x00, 0x7C, 0x00, 0x83, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char (
	        0x03, 0x00, 0x00, 0x83, 0x01, 0x7C, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char )
	        0x04, 0x00, 0x00, 0x30, 0x00, 0x78, 0x00, 0x30, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char *
	        0x05, 0x10, 0x00, 0x10, 0x00, 0x7C, 0x00, 0x10, 0x00, 0x10, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char +
	        0x02, 0x00, 0x01, 0xC0, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char ,
	        0x04, 0x00, 0x00, 0x10, 0x00, 0x10, 0x00, 0x10, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char -
	        0x02, 0x00, 0x00, 0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char .
	        0x04, 0x00, 0x01, 0xE0, 0x00, 0x1C, 0x00, 0x03, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char /
	        0x05, 0x00, 0x00, 0x7C, 0x00, 0x82, 0x00, 0x82, 0x00, 0x7C, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char 0
	        0x05, 0x00, 0x00, 0x08, 0x00, 0x04, 0x00, 0xFE, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char 1
	        0x05, 0x00, 0x00, 0x84, 0x00, 0xC2, 0x00, 0xA2, 0x00, 0x9C, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char 2
	        0x05, 0x00, 0x00, 0x82, 0x00, 0x92, 0x00, 0x92, 0x00, 0x6C, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char 3
	        0x05, 0x00, 0x00, 0x38, 0x00, 0x2C, 0x00, 0x22, 0x00, 0xFE, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char 4
	        0x05, 0x00, 0x00, 0x9E, 0x00, 0x92, 0x00, 0x92, 0x00, 0x62, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char 5
	        0x05, 0x00, 0x00, 0x7C, 0x00, 0x92, 0x00, 0x92, 0x00, 0x74, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char 6
	        0x05, 0x00, 0x00, 0x02, 0x00, 0xC2, 0x00, 0x32, 0x00, 0x0E, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char 7
	        0x05, 0x00, 0x00, 0x6C, 0x00, 0x92, 0x00, 0x92, 0x00, 0x6C, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char 8
	        0x05, 0x00, 0x00, 0x9C, 0x00, 0x92, 0x00, 0x92, 0x00, 0x7C, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char 9
	        0x02, 0x00, 0x00, 0x88, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char :
	        0x02, 0x00, 0x01, 0xC8, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char ;
	        0x05, 0x10, 0x00, 0x10, 0x00, 0x28, 0x00, 0x28, 0x00, 0x44, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char <
	        0x05, 0x00, 0x00, 0x28, 0x00, 0x28, 0x00, 0x28, 0x00, 0x28, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char =
	        0x05, 0x00, 0x00, 0x44, 0x00, 0x28, 0x00, 0x28, 0x00, 0x10, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char >
	        0x05, 0x00, 0x00, 0x02, 0x00, 0xB2, 0x00, 0x12, 0x00, 0x0C, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char ?
	        0x09, 0x00, 0x00, 0xF8, 0x00, 0x84, 0x01, 0x72, 0x01, 0x4A, 0x01, 0x4A, 0x01, 0x7A, 0x01, 0x42, 0x00, 0x3C, 0x00,  // Code for char @
	        0x06, 0x00, 0x00, 0xF8, 0x00, 0x24, 0x00, 0x22, 0x00, 0x24, 0x00, 0xF8, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char A
	        0x06, 0x00, 0x00, 0xFE, 0x00, 0x92, 0x00, 0x92, 0x00, 0x92, 0x00, 0x6C, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char B
	        0x06, 0x00, 0x00, 0x7C, 0x00, 0x82, 0x00, 0x82, 0x00, 0x82, 0x00, 0x44, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char C
	        0x06, 0x00, 0x00, 0xFE, 0x00, 0x82, 0x00, 0x82, 0x00, 0xC6, 0x00, 0x7C, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char D
	        0x05, 0x00, 0x00, 0xFE, 0x00, 0x92, 0x00, 0x92, 0x00, 0x92, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char E
	        0x05, 0x00, 0x00, 0xFE, 0x00, 0x12, 0x00, 0x12, 0x00, 0x12, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char F
	        0x06, 0x00, 0x00, 0x7C, 0x00, 0xC6, 0x00, 0x82, 0x00, 0x92, 0x00, 0xF6, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char G
	        0x06, 0x00, 0x00, 0xFE, 0x00, 0x10, 0x00, 0x10, 0x00, 0x10, 0x00, 0xFE, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char H
	        0x02, 0x00, 0x00, 0xFE, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char I
	        0x04, 0x00, 0x00, 0x80, 0x00, 0x80, 0x00, 0x7E, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char J
	        0x05, 0x00, 0x00, 0xFE, 0x00, 0x10, 0x00, 0x2C, 0x00, 0xC2, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char K
	        0x05, 0x00, 0x00, 0xFE, 0x00, 0x80, 0x00, 0x80, 0x00, 0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char L
	        0x08, 0x00, 0x00, 0xFE, 0x00, 0x06, 0x00, 0x18, 0x00, 0xE0, 0x00, 0x18, 0x00, 0x06, 0x00, 0xFE, 0x00, 0x00, 0x00,  // Code for char M
	        0x06, 0x00, 0x00, 0xFE, 0x00, 0x06, 0x00, 0x18, 0x00, 0x60, 0x00, 0xFE, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char N
	        0x06, 0x00, 0x00, 0x7C, 0x00, 0x82, 0x00, 0x82, 0x00, 0x82, 0x00, 0x7C, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char O
	        0x05, 0x00, 0x00, 0xFE, 0x00, 0x12, 0x00, 0x12, 0x00, 0x0C, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char P
	        0x07, 0x00, 0x00, 0x7C, 0x00, 0x82, 0x00, 0x82, 0x00, 0xC2, 0x00, 0xFC, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x00,  // Code for char Q
	        0x05, 0x00, 0x00, 0xFE, 0x00, 0x12, 0x00, 0x12, 0x00, 0xEC, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char R
	        0x05, 0x00, 0x00, 0xCC, 0x00, 0x92, 0x00, 0x92, 0x00, 0x66, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char S
	        0x06, 0x00, 0x00, 0x02, 0x00, 0x02, 0x00, 0xFE, 0x00, 0x02, 0x00, 0x02, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char T
	        0x06, 0x00, 0x00, 0x7E, 0x00, 0x80, 0x00, 0x80, 0x00, 0x80, 0x00, 0x7E, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char U
	        0x07, 0x00, 0x00, 0x06, 0x00, 0x3C, 0x00, 0xE0, 0x00, 0xE0, 0x00, 0x1C, 0x00, 0x06, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char V
	        0x06, 0x00, 0x00, 0x1E, 0x00, 0xE0, 0x00, 0x3E, 0x00, 0xE0, 0x00, 0x1E, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char W
	        0x06, 0x00, 0x00, 0x82, 0x00, 0x64, 0x00, 0x38, 0x00, 0x6C, 0x00, 0x82, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char X
	        0x06, 0x00, 0x00, 0x02, 0x00, 0x0C, 0x00, 0xF0, 0x00, 0x0C, 0x00, 0x02, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char Y
	        0x06, 0x00, 0x00, 0x82, 0x00, 0xE2, 0x00, 0x92, 0x00, 0x8E, 0x00, 0x82, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char Z
	        0x03, 0x00, 0x00, 0xFF, 0x01, 0x01, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char [
	        0x04, 0x01, 0x00, 0x0E, 0x00, 0x70, 0x00, 0x80, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char BackSlash
	        0x02, 0x01, 0x01, 0xFF, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char ]
	        0x04, 0x00, 0x00, 0x18, 0x00, 0x0C, 0x00, 0x18, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char ^
	        0x06, 0x00, 0x01, 0x00, 0x01, 0x00, 0x01, 0x00, 0x01, 0x00, 0x01, 0x00, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char _
	        0x03, 0x00, 0x00, 0x01, 0x00, 0x02, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char `
	        0x05, 0x00, 0x00, 0xE8, 0x00, 0xA8, 0x00, 0xA8, 0x00, 0xF8, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char a
	        0x05, 0x00, 0x00, 0xFE, 0x00, 0x88, 0x00, 0x88, 0x00, 0x70, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char b
	        0x05, 0x00, 0x00, 0x70, 0x00, 0x88, 0x00, 0x88, 0x00, 0x88, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char c
	        0x05, 0x00, 0x00, 0x70, 0x00, 0x88, 0x00, 0x88, 0x00, 0xFE, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char d
	        0x05, 0x00, 0x00, 0x70, 0x00, 0xA8, 0x00, 0xA8, 0x00, 0xB0, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char e
	        0x04, 0x08, 0x00, 0xFE, 0x00, 0x0A, 0x00, 0x02, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char f
	        0x05, 0x00, 0x00, 0x30, 0x00, 0x48, 0x01, 0x48, 0x01, 0xF8, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char g
	        0x05, 0x00, 0x00, 0xFE, 0x00, 0x08, 0x00, 0x08, 0x00, 0xF8, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char h
	        0x02, 0x00, 0x00, 0xFA, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char i
	        0x02, 0x00, 0x01, 0xFA, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char j
	        0x05, 0x00, 0x00, 0xFE, 0x00, 0x20, 0x00, 0x50, 0x00, 0x88, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char k
	        0x02, 0x00, 0x00, 0xFE, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char l
	        0x06, 0x00, 0x00, 0xF8, 0x00, 0x08, 0x00, 0xF8, 0x00, 0x08, 0x00, 0xF8, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char m
	        0x05, 0x00, 0x00, 0xF8, 0x00, 0x08, 0x00, 0x08, 0x00, 0xF8, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char n
	        0x05, 0x00, 0x00, 0x70, 0x00, 0x88, 0x00, 0x88, 0x00, 0x70, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char o
	        0x05, 0x00, 0x00, 0xF8, 0x01, 0x48, 0x00, 0x48, 0x00, 0x30, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char p
	        0x05, 0x00, 0x00, 0x30, 0x00, 0x48, 0x00, 0x48, 0x00, 0xF8, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char q
	        0x04, 0x00, 0x00, 0xF8, 0x00, 0x08, 0x00, 0x08, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char r
	        0x04, 0x00, 0x00, 0x98, 0x00, 0xA8, 0x00, 0xE8, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char s
	        0x04, 0x00, 0x00, 0x08, 0x00, 0xFC, 0x00, 0x88, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char t
	        0x05, 0x00, 0x00, 0x78, 0x00, 0x80, 0x00, 0x80, 0x00, 0xF8, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char u
	        0x04, 0x00, 0x00, 0x38, 0x00, 0xC0, 0x00, 0x38, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char v
	        0x06, 0x00, 0x00, 0x78, 0x00, 0xC0, 0x00, 0x38, 0x00, 0xC0, 0x00, 0x78, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char w
	        0x05, 0x00, 0x00, 0x88, 0x00, 0x70, 0x00, 0x70, 0x00, 0x88, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char x
	        0x05, 0x00, 0x00, 0x38, 0x00, 0x40, 0x01, 0x40, 0x01, 0xF8, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char y
	        0x05, 0x00, 0x00, 0xC8, 0x00, 0xE8, 0x00, 0xB8, 0x00, 0x88, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char z
	        0x04, 0x10, 0x00, 0x38, 0x00, 0xEF, 0x01, 0x01, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char {
	        0x03, 0x00, 0x00, 0x00, 0x00, 0xFF, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char |
	        0x04, 0x01, 0x01, 0xC7, 0x01, 0x38, 0x00, 0x10, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char }
	        0x05, 0x0C, 0x00, 0x04, 0x00, 0x0C, 0x00, 0x08, 0x00, 0x0C, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // Code for char ~
	        0x03, 0xFE, 0x01, 0x02, 0x01, 0xFE, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00   // Code for char 
	        };
	public static final int NORMAL = 1;
	public static final int XOR = 2;
	
	
	
    private final SPI _spi;
    private final DigitalOut _reset;
    private final DigitalOut _A0;
    private final DigitalOut _CS;
    private char[] font = new char[512];
    private int draw_mode;
 
    private int orientation;
    private int char_x;
    private int char_y;
    private byte[] buffer= new byte[512];
    private int contrast;
    private int auto_up;


    /**
     * 
     * @param 
     * i_mcu MCUインスタンス
     * @param 
     * i_mosi_pin mosiピン
     * @param 
     * i_miso_pin misoピン
     * @param 
     * i_sclk_pin sclkピン
     * @param 
     * i_reset resetピン
     * @param 
     * i_A0 A0ピン
     * @param 
     * i_CS CSピン
     * @param 
     * i_name 
     * @throws 
     * MbedJsException MbedJS例外
     */
	public C12832(Mcu i_mcu , int i_mosi_pin, int i_miso_pin, int i_sclk_pin,
			int i_reset, int i_A0 , int i_CS,
			byte[] i_name) throws MbedJsException
    {
		//: _spi(p5,NC,p7),_reset(p6),_A0(p8),_CS(p11),GraphicsDisplay(name)
		super(i_name); // 0msec
		this._spi = new SPI(i_mcu,i_mosi_pin,i_miso_pin, i_sclk_pin);// 50ms
	    this._reset = new DigitalOut(i_mcu , i_reset); // 11ms
		this._A0 = new DigitalOut(i_mcu , i_A0); // 8ms
		this._CS = new DigitalOut(i_mcu , i_CS); // 9ms
		this.orientation = 1; //0ms
		this.draw_mode = NORMAL; //0ms
		this.char_x = 0;// 0ms
		this.lcd_reset(); // 20019ms 
		
    }
	public void dispose() throws MbedJsException{
		this._spi.dispose();
		this._reset.dispose();
		this._A0.dispose();
		this._CS.dispose();
	}
 
	/**
	 * 描画領域の幅を返す
	 */
	public int width()
	{
	    if (this.orientation == 0 || this.orientation == 2) return 32;
	    else return 128;
	}
	/**
	 * 描画領域の高さを返す
	 */
	public int height()
	{
	    if (this.orientation == 0 || this.orientation == 2) return 128;
	    else return 32;
	}
	/**
	 * 画面を上下逆にする
	 * @param i_o 0:標準 1:逆転
	 * @throws 
	 * MbedJsException MbedJS例外
	 */
	
	public void invert(int i_o) throws MbedJsException
	{
	    if(i_o == 0) this.wr_cmd((char) 0xA6);
	    else this.wr_cmd((char) 0xA7);
	}
 
	/**
	 * コントラストを設定する
	 * @param 
	 * i_o コントラスト
	 * @throws 
	 * MbedJsException MbedJS例外
	 */
	public void set_contrast(int i_o) throws MbedJsException
	{
		this.contrast = i_o;
		this.wr_cmd((char) 0x81);      //  set volume
		this.wr_cmd((char) (i_o & 0x3F));
	}
	/**
	 * コントラストを得る
	 * @return コントラスト
	 */
	public int get_contrast()
	{
	    return(contrast);
	}
	
 
 
// write command to lcd controller
 
	private void wr_cmd(char i_cmd) throws MbedJsException
	{
		this._A0.write(0);
		this._CS.write(0);
	    
	//#if defined TARGET_LPC1768     // fast without mbed lib
	//    LPC_SSP1->DR = cmd;
	//    do {
	//    } while ((LPC_SSP1->SR & 0x10) == 0x10); // wait for SPI1 idle
	//#else
		this._spi.write(i_cmd);
	//#endif
		this._CS.write(1);
	}
 
// write data to lcd controller
	 
	private void wr_dat(char i_dat) throws MbedJsException
	{
		this._A0.write(1);
		this._CS.write(0);
	//#if defined TARGET_LPC1768     // fast without mbed lib
	//    LPC_SSP1->DR = dat;
	//    do {
	//    } while ((LPC_SSP1->SR & 0x10) == 0x10); // wait for SPI1 idle
	//#else
		this._spi.write(i_dat);
	//#endif
		this._CS.write(1);
	}
	 
// reset and init the lcd controller
 
	private void lcd_reset() throws MbedJsException
	{
		
		this._spi.format(8,3);                 // 8 bit spi mode 3 //7ms
		this._spi.frequency(20000000);          // 19,2 Mhz SPI clock //8ms
	    //DigitalOut _reset(p6);
		this._A0.write(0); //10ms
		this._CS.write(0); //5ms
		this._reset.write(0); //10ms                        // display reset
		this.sleep_ms(1);//wait_us(50);

		this._reset.write(1);    //8ms                    // end reset
		this.sleep_ms(1);//wait_ms(5);

	    /* Start Initial Sequence ----------------------------------------------------*/
		this.wr_cmd((char) 0xAE);   //  display off //27ms
		this.wr_cmd((char) 0xA2);   //  bias voltage
	 
		this.wr_cmd((char) 0xA0);
		this.wr_cmd((char) 0xC8);   //  colum normal
	 
		this.wr_cmd((char) 0x22);   //  voltage resistor ratio
		this.wr_cmd((char) 0x2F);   //  power on
	    //wr_cmd(0xA4);   //  LCD display ram
		this.wr_cmd((char) 0x40);   // start line = 0
		this.wr_cmd((char) 0xAF);     // display ON
	 
		this.wr_cmd((char) 0x81);   //  set contrast
		this.wr_cmd((char) 0x17);   //  set contrast
	 
		this.wr_cmd((char) 0xA6);     // display normal
	 	
	 
	    // clear and update LCD
	    //buffer = memset((byte)0x00,512);  // clear display buffer
	    
	    this.buffer = new byte[512]; // 0ms
	    this.copy_to_lcd(); // 24768ms
	    this.auto_up = 1;              // switch on auto update
	    // dont do this by default. Make the user call
	    //claim(stdout);           // redirekt printf to lcd
	    this.locate(0,0); // 0ms
	    this.set_font((char[])Small_7);  // standart font 0ms
	    
	    
	}
 
// set one pixel in buffer
	/**
	 * 点を描画する
	 * @param
	 * i_x X座標
	 * @param
	 * i_y Y座標
	 * @param
	 * i_color 色
	 */
	public void pixel(int i_x, int i_y, int i_color)
	{
	    // first check parameter
	    if(i_x > 128 || i_y > 32 || i_x < 0 || i_y < 0) return;
	 
	    if(this.draw_mode == NORMAL) {
	        if(i_color == 0)
	        	this.buffer[i_x + ((i_y/8) * 128)] &= ~(1 << (i_y%8));  // erase pixel
	        else
	        	this.buffer[i_x + ((i_y/8) * 128)] |= (1 << (i_y%8));   // set pixel
	    } else { // XOR mode
	        if(i_color == 1)
	        	this.buffer[i_x + ((i_y/8) * 128)] ^= (1 << (i_y%8));   // xor pixel
	    }
	}
 
// update lcd
	/**
	 * LCDへ描画データを反映させる
	 * @throws 
	 * MbedJsException MbedJS例外
	 */
	private void copy_to_lcd() throws MbedJsException
	{
	    int i;
	    //page 0
	    this.wr_cmd((char) 0x00);      // set column low nibble 0
	    this.wr_cmd((char) 0x10);      // set column hi  nibble 0
	    this.wr_cmd((char) 0xB0);      // set page address  0
	    this._A0.write(1);

	    for(i=0; i<128; i++) {
	    	this.wr_dat((char) buffer[i]);
	    }
	 
	    // page 1
	    this.wr_cmd((char) 0x00);      // set column low nibble 0
	    this.wr_cmd((char) 0x10);      // set column hi  nibble 0
	    this.wr_cmd((char) 0xB1);      // set page address  1
	    this._A0.write(1);

	    for(i=128; i<256; i++) {
	    	this.wr_dat((char) buffer[i]);
	    }
	    //page 2
	    this.wr_cmd((char) 0x00);      // set column low nibble 0
	    this.wr_cmd((char) 0x10);      // set column hi  nibble 0
	    this.wr_cmd((char) 0xB2);      // set page address  2
	    this._A0.write(1);

	    for(i=256; i<384; i++) {
	    	this.wr_dat((char) buffer[i]);
	    }
	 
	    //page 3
	    this.wr_cmd((char) 0x00);      // set column low nibble 0
	    this.wr_cmd((char) 0x10);      // set column hi  nibble 0
	    this.wr_cmd((char) 0xB3);      // set page address  3
	    this._A0.write(1);
	 
	    this._CS.write(0);

	    for(i=384; i<512; i++) {
	    	this.wr_dat((char) buffer[i]);
	    }
	}
	/**
	 * 画面のクリア
	 */
	public void cls() throws MbedJsException
	{
		//this.buffer = memset((byte)0x00,512);  // clear display buffer
		this.buffer = new byte[512];
    	this.copy_to_lcd();

	}
 
	/**
	 * 線の描画
	 * @param 
	 * i_x0 始点のX座標
	 * @param 
	 * i_y0 始点のY座標
	 * @param 
	 * i_x1 終点のX座標
	 * @param 
	 * i_y1 終点のY座標
	 * @param 
	 * i_color 色
	 * @throws 
	 * MbedJsException MbedJS例外
	 */
	public void line(int i_x0, int i_y0, int i_x1, int i_y1, int i_color) throws MbedJsException
	{
	    int   dx = 0, dy = 0;
	    int   dx_sym = 0, dy_sym = 0;
	    int   dx_x2 = 0, dy_x2 = 0;
	    int   di = 0;
	 
	    dx = i_x1-i_x0;
	    dy = i_y1-i_y0;
	 
	    //  if (dx == 0) {        /* vertical line */
	    //      if (y1 > y0) vline(x0,y0,y1,color);
	    //      else vline(x0,y1,y0,color);
	    //      return;
	    //  }
	 
	    if (dx > 0) {
	        dx_sym = 1;
	    } else {
	        dx_sym = -1;
	    }
	    //  if (dy == 0) {        /* horizontal line */
	    //      if (x1 > x0) hline(x0,x1,y0,color);
	    //      else  hline(x1,x0,y0,color);
	    //      return;
	    //  }
	 
	    if (dy > 0) {
	        dy_sym = 1;
	    } else {
	        dy_sym = -1;
	    }
	 
	    dx = dx_sym*dx;
	    dy = dy_sym*dy;
	 
	    dx_x2 = dx*2;
	    dy_x2 = dy*2;
	 
	    if (dx >= dy) {
	        di = dy_x2 - dx;
	        while (i_x0 != i_x1) {
	 
	        	this.pixel(i_x0, i_y0, i_color);
	            i_x0 += dx_sym;
	            if (di<0) {
	                di += dy_x2;
	            } else {
	                di += dy_x2 - dx_x2;
	                i_y0 += dy_sym;
	            }
	        }
	        this.pixel(i_x0, i_y0, i_color);
	    } else {
	        di = dx_x2 - dy;
	        while (i_y0 != i_y1) {
	        	this.pixel(i_x0, i_y0, i_color);
	            i_y0 += dy_sym;
	            if (di < 0) {
	                di += dx_x2;
	            } else {
	                di += dx_x2 - dy_x2;
	                i_x0 += dx_sym;
	            }
	        }
	        this.pixel(i_x0, i_y0, i_color);
	    }
	    if(this.auto_up!=0) this.copy_to_lcd();
	}
	/**
	 * 四角形の描画
	 * @param 
	 * i_x0 左下の頂点のX座標
	 * @param 
	 * i_y0 左下の頂点のY座標
	 * @param 
	 * i_x1 右上の頂点のX座標
	 * @param 
	 * i_y1 右上の頂点のY座標
	 * @param 
	 * i_color 色
	 * @throws 
	 * MbedJsException MbedJS例外
	 */
	public void rect(int i_x0, int i_y0, int i_x1, int i_y1, int i_color) throws MbedJsException
	{
	 
	    if (i_x1 > i_x0) this.line(i_x0,i_y0,i_x1,i_y0,i_color);
	    else  this.line(i_x1,i_y0,i_x0,i_y0,i_color);
	 
	    if (i_y1 > i_y0) this.line(i_x0,i_y0,i_x0,i_y1,i_color);
	    else this.line(i_x0,i_y1,i_x0,i_y0,i_color);
	 
	    if (i_x1 > i_x0) this.line(i_x0,i_y1,i_x1,i_y1,i_color);
	    else  this.line(i_x1,i_y1,i_x0,i_y1,i_color);
	 
	    if (i_y1 > i_y0) this.line(i_x1,i_y0,i_x1,i_y1,i_color);
	    else this.line(i_x1,i_y1,i_x1,i_y0,i_color);
	 
	    if(this.auto_up!=0) this.copy_to_lcd();
	}
 
	/**
	 * 内部を塗りつぶした四角形を描画する
	 * @param 
	 * i_x0 左下の頂点のX座標
	 * @param 
	 * i_y0 左下の頂点のY座標
	 * @param 
	 * i_x1 右上の頂点のX座標
	 * @param 
	 * i_y1 右上の頂点のY座標
	 * @param 
	 * i_color 色
	 * @throws 
	 * MbedJsException MbedJS例外
	 */
	public void fillrect(int i_x0, int i_y0, int i_x1, int i_y1, int i_color) throws MbedJsException
	{
	    int l,c,i;
	    if(i_x0 > i_x1) {
	        i = i_x0;
	        i_x0 = i_x1;
	        i_x1 = i;
	    }
	 
	    if(i_y0 > i_y1) {
	        i = i_y0;
	        i_y0 = i_y1;
	        i_y1 = i;
	    }
	 
	    for(l = i_x0; l<= i_x1; l ++) {
	        for(c = i_y0; c<= i_y1; c++) {
	        	this.pixel(l,c,i_color);
	        }
	    }
	    if(this.auto_up!=0)
	    	this.copy_to_lcd();
	}
 
 
	/**
	 * 円を描画する
	 * @param 
	 * i_x0 X座標
	 * @param 
	 * i_y0 Y座標
	 * @param 
	 * i_r 半径
	 * @param 
	 * i_color 色
	 * @throws 
	 * MbedJsException MbedJS例外
	 */
	public void circle(int i_x0, int i_y0, int i_r, int i_color) throws MbedJsException
	{
	 
	    int draw_x0, draw_y0;
	    int draw_x1, draw_y1;
	    int draw_x2, draw_y2;
	    int draw_x3, draw_y3;
	    int draw_x4, draw_y4;
	    int draw_x5, draw_y5;
	    int draw_x6, draw_y6;
	    int draw_x7, draw_y7;
	    int xx, yy;
	    int di;
	    //WindowMax();
	    if (i_r == 0) {       /* no radius */
	        return;
	    }
	 
	    draw_x0 = draw_x1 = i_x0;
	    draw_y0 = draw_y1 = i_y0 + i_r;
	    if (draw_y0 < this.height()) {
	    	this.pixel(draw_x0, draw_y0, i_color);     /* 90 degree */
	    }
	 
	    draw_x2 = draw_x3 = i_x0;
	    draw_y2 = draw_y3 = i_y0 - i_r;
	    if (draw_y2 >= 0) {
	    	this.pixel(draw_x2, draw_y2, i_color);    /* 270 degree */
	    }
	 
	    draw_x4 = draw_x6 = i_x0 + i_r;
	    draw_y4 = draw_y6 = i_y0;
	    if (draw_x4 < this.width()) {
	    	this.pixel(draw_x4, draw_y4, i_color);     /* 0 degree */
	    }
	 
	    draw_x5 = draw_x7 = i_x0 - i_r;
	    draw_y5 = draw_y7 = i_y0;
	    if (draw_x5>=0) {
	    	this.pixel(draw_x5, draw_y5, i_color);     /* 180 degree */
	    }
	 
	    if (i_r == 1) {
	        return;
	    }
	 
	    di = 3 - 2*i_r;
	    xx = 0;
	    yy = i_r;
	    while (xx < yy) {
	 
	        if (di < 0) {
	            di += 4*xx + 6;
	        } else {
	            di += 4*(xx - yy) + 10;
	            yy--;
	            draw_y0--;
	            draw_y1--;
	            draw_y2++;
	            draw_y3++;
	            draw_x4--;
	            draw_x5++;
	            draw_x6--;
	            draw_x7++;
	        }
	        xx++;
	        draw_x0++;
	        draw_x1--;
	        draw_x2++;
	        draw_x3--;
	        draw_y4++;
	        draw_y5++;
	        draw_y6--;
	        draw_y7--;
	 
	        if ( (draw_x0 <= this.width()) && (draw_y0>=0) ) {
	        	this.pixel(draw_x0, draw_y0, i_color);
	        }
	 
	        if ( (draw_x1 >= 0) && (draw_y1 >= 0) ) {
	        	this.pixel(draw_x1, draw_y1, i_color);
	        }
	 
	        if ( (draw_x2 <= width()) && (draw_y2 <= height()) ) {
	        	this.pixel(draw_x2, draw_y2, i_color);
	        }
	 
	        if ( (draw_x3 >=0 ) && (draw_y3 <= this.height()) ) {
	        	this.pixel(draw_x3, draw_y3, i_color);
	        }
	 
	        if ( (draw_x4 <= this.width()) && (draw_y4 >= 0) ) {
	        	this.pixel(draw_x4, draw_y4, i_color);
	        }
	 
	        if ( (draw_x5 >= 0) && (draw_y5 >= 0) ) {
	        	this.pixel(draw_x5, draw_y5, i_color);
	        }
	        if ( (draw_x6 <=this.width()) && (draw_y6 <= this.height()) ) {
	        	this.pixel(draw_x6, draw_y6, i_color);
	        }
	        if ( (draw_x7 >= 0) && (draw_y7 <= this.height()) ) {
	        	this.pixel(draw_x7, draw_y7, i_color);
	        }
	    }
	    if(this.auto_up!=0)
			this.copy_to_lcd();

	}
	/**
	 * 円の内部を塗りつぶす
	 * @param 
	 * i_x X座標
	 * @param 
	 * i_y Y座標
	 * @param 
	 * i_r 半径
	 * @param 
	 * i_color 色
	 * @throws 
	 * MbedJsException MbedJS例外
	 */
	public void fillcircle(int i_x, int i_y, int i_r, int i_color) throws MbedJsException
	{
	    int i,up;
	    up = this.auto_up;
	    this.auto_up = 0;   // off
	    for (i = 0; i <= i_r; i++)
	    	this.circle(i_x,i_y,i,i_color);
	    this.auto_up = up;
	    if(this.auto_up!=0) this.copy_to_lcd();
	}
 
	/**
	 * draw_modeの設定、NormalとXORモードがある
	 * @param 
	 * i_mode モード
	 */
	public void setmode(int i_mode)
	{
		this.draw_mode = i_mode;
	}
	/**
	 * カーソルの位置を設定する
	 * @param 
	 * i_x X座標
	 * @param
	 * i_y Y座標
	 */
	public void locate(int i_x, int i_y)
	{
		this.char_x = i_x;
		this.char_y = i_y;
	}
	 
 
	/**
	 * 水平方向の幅を返す
	 */
	public int columns()
	{
	    return this.width() / this.font[1];
	}
 
 
	/**
	 * 垂直方向の高さを返す 
	 */
	public int rows()
	{
	    return this.height() / this.font[2];
	}
 
	/**
	 * 現在の位置に文字を表示する
	 * @param 
	 * i_value 文字 
	 * @throws 
	 * MbedJsException MbedJS例外
	 */
	public int _putc(int i_value) throws MbedJsException
	{
	    if (i_value == '\n') {    // new line
	    	this.char_x = 0;
	    	this.char_y = this.char_y + this.font[2];
	        if (this.char_y >= height() - this.font[2]) {
	        	this.char_y = 0;
	        }
	    } else {
	    	this.character(this.char_x, this.char_y, i_value);
	        if(this.auto_up!=0) this.copy_to_lcd();
	    }
	    return i_value;
	}
	/**
	 * 文字を指定した座標に描画する
	 * @param 
	 * i_x X座標
	 * @param 
	 * i_y Y座標
	 * @param 
	 * i_c 文字
	 */
	public void character(int i_x, int i_y, int i_c)
	{
	    int hor,vert,offset,bpl,j,i,b , ofs;
	    //char[] zeichen;
	    char z,w;
	 
	    if ((i_c < 31) || (i_c > 127)) return;   // test char range
	 
	    // read font parameter from start of array
	    offset = this.font[0];                    // bytes / char
	    hor = this.font[1];                       // get hor size of font
	    vert = this.font[2];                      // get vert size of font
	    bpl = this.font[3];                       // bytes per line
	 
	    if (this.char_x + hor > this.width()) {
	    	this.char_x = 0;
	    	this.char_y = this.char_y + vert;
	        if (this.char_y >= this.height() - this.font[2]) {
	        	this.char_y = 0;
	        }
	    }
	    
	    //zeichen = &font[((c -32) * offset) + 4]; // start of char bitmap
	    ofs = ((i_c -32) * offset) + 4;
	    
	    w = this.font[ofs];                          // width of actual char
	    // construct the char into the buffer
	    for (j=0; j<vert; j++) {  //  vert line
	        for (i=0; i<hor; i++) {   //  horz line
	            z =  this.font[ofs + bpl * i + ((j & 0xF8) >> 3)+1];
	            b = 1 << (j & 0x07);
	            if (( z & b ) == 0x00) {
	            	this.pixel(i_x+i,i_y+j,0);
	            } else {
	            	this.pixel(i_x+i,i_y+j,1);
	            }
	 
	        }
	    }
	 
	    this.char_x += w;
	}
 
	/**
	 * 外部フォントの設定
	 * @param 
	 * i_font フォントデータ
	 */
	public void set_font(char[] i_font)
	{
		this.font = i_font;
	}
	/**
	 * 描画に関する関数を呼んだ後にLCDに反映するかしないかのフラグを設定する
	 * @param 
	 * i_up フラグ
	 */
	public void set_auto_up(int i_up)
	{
	    if(i_up!=0 ) this.auto_up = 1;
	    else this.auto_up = 0;
	}
	 /**
	  * 描画に関する関数を呼んだ後にLCDに反映するかしないかのフラグを返す
	  * @return 
	  * フラグ
	  */
	public int get_auto_up()
	{
	    return (this.auto_up);
	}
	/**
	 * ビットマップの表示
	 * @param 
	 * i_bitmap ビットマップ
	 * @param 
	 * i_x X座標
	 * @param 
	 * i_y Y座標
	 */
	public void print_bm(Bitmap i_bitmap, int i_x, int i_y)
	{
	    int h,v,b;
	    char d;
	 
	    for(v=0; v < i_bitmap.ySize; v++) {   // lines
	        for(h=0; h < i_bitmap.xSize; h++) { // pixel
	            if(h + i_x > 127) break;
	            if(v + i_y > 31) break;
	            d = i_bitmap.data[i_bitmap.byte_in_Line * v + ((h & 0xF8) >> 3)];
	            b = 0x80 >> (h & 0x07);
	            if((d & b) == 0) {
	            	this.pixel(i_x+h,i_y+v,0);
	            } else {
	            	this.pixel(i_x+h,i_y+v,1);
	            }
	        }
	    }
	}
	/**
	 * 文字列を表示する
	 * @param 
	 * i_s 表示する文字列
	 * @return 
	 * 表示した文字数
	 * @throws 
	 * MbedJsException MbedJS例外
	 */
	public int puts(String i_s) throws MbedJsException
	{
		int i=0;
		for(;i<i_s.length();i++){
			int v=i_s.charAt(i);
			this._putc((v>255)?'?':v);
		}
		return i;
	}
	public static void main(String[] args) throws MbedJsException {
		System.out.println("start");
		Mcu mcu = new Mcu("192.168.0.39");
		C12832 lcd = new C12832(mcu , PinName.p5,PinName.NC , PinName.p7,
				 PinName.p6 ,PinName.p8 , PinName.p11, 
				 "test".getBytes());
		
		//lcd.line(0, 0, 20, 20, 1);// ok
		//lcd.rect(10, 10, 20, 20, 1); //ok
		//lcd.fillrect(10, 10, 20, 20, 1); //ok
		//lcd.circle(15, 15, 5, 1); //ok
		//lcd.fillcircle(15, 14, 5, 1); // ok
		//lcd.pixel(10, 10, 1);
		//lcd.character(1, 1, 'c');
		lcd.locate(30, 20);
		lcd.puts("hello mbedJS");
		System.out.println("done");
	}
}