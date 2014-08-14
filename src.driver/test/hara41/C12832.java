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


package test.hara41;

import jp.nyatla.mimic.mbedjs.*;
import jp.nyatla.mimic.mbedjs.javaapi.*;

public class C12832 {

	private static final int DMA_CHANNEL_ENABLE = 1;
	private static final int DMA_TRANSFER_TYPE_M2P =  (1UL << 11);
	private static final int DMA_CHANNEL_TCIE      =  (1UL << 31);
	private static final int DMA_CHANNEL_SRC_INC   =  (1UL << 26);
	private static final int DMA_MASK_IE           =  (1UL << 14);
	private static final int DMA_MASK_ITC          =  (1UL << 15);
	private static final int DMA_SSP1_TX           =  (1UL << 2);
	private static final int DMA_SSP0_TX           =  (0);
	private static final int DMA_DEST_SSP1_TX      =  (2UL << 6);
	private static final int DMA_DEST_SSP0_TX      =  (0UL << 6);
	
	private static final int NORMAL = 1;
	private static final int XOR = 2;
	
    private final SPI _spi;
    private final DigitalOut _reset;
    private final DigitalOut _A0;
    private final DigitalOut _CS;
    private final byte[] font;
    private final int draw_mode;
 
	
	public C12832() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
