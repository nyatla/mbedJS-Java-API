/*
 * Copyright 2014 R.Iizuka
 * http://nyatla.jp/mimic/
 * nyatla39@gmail.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.nyatla.mimic.mbedjs.javaapi;
/**
 * mbedSDKのピン識別子です。ライブラリのピン名と一致します。次のピン名を使用できます。
 * <ul>
 * <li> LPC Pin Names(P0_0 - P4_31)
 * <li> mbed DIP Pin Names(p5-p40)
 * <li> Other mbed Pin Names(LED1-LED4,USBRX,USBTX)
 * <li> Arch Pro Pin Names(D0-D15,A0-A5,I2C_SCL,I2C_SDA)
 * <li> NC
 * </ul>
 */
public class PinName{
	private final static int PINID_LPC=0x00010000;
	public final static int P0_0 =PINID_LPC+32*0+0;
	public final static int P0_1 =PINID_LPC+32*0+1;
	public final static int P0_2 =PINID_LPC+32*0+2;
	public final static int P0_3 =PINID_LPC+32*0+3;
	public final static int P0_4 =PINID_LPC+32*0+4;
	public final static int P0_5 =PINID_LPC+32*0+5;
	public final static int P0_6 =PINID_LPC+32*0+6;
	public final static int P0_7 =PINID_LPC+32*0+7;
	public final static int P0_8 =PINID_LPC+32*0+8;
	public final static int P0_9 =PINID_LPC+32*0+9;
	public final static int P0_10=PINID_LPC+32*0+10;
	public final static int P0_11=PINID_LPC+32*0+11;
	public final static int P0_12=PINID_LPC+32*0+12;
	public final static int P0_13=PINID_LPC+32*0+13;
	public final static int P0_14=PINID_LPC+32*0+14;
	public final static int P0_15=PINID_LPC+32*0+15;
	public final static int P0_16=PINID_LPC+32*0+16;
	public final static int P0_17=PINID_LPC+32*0+17;
	public final static int P0_18=PINID_LPC+32*0+18;
	public final static int P0_19=PINID_LPC+32*0+19;
	public final static int P0_20=PINID_LPC+32*0+20;
	public final static int P0_21=PINID_LPC+32*0+21;
	public final static int P0_22=PINID_LPC+32*0+22;
	public final static int P0_23=PINID_LPC+32*0+23;
	public final static int P0_24=PINID_LPC+32*0+24;
	public final static int P0_25=PINID_LPC+32*0+25;
	public final static int P0_26=PINID_LPC+32*0+26;
	public final static int P0_27=PINID_LPC+32*0+27;
	public final static int P0_28=PINID_LPC+32*0+28;
	public final static int P0_29=PINID_LPC+32*0+29;
	public final static int P0_30=PINID_LPC+32*0+30;
	public final static int P0_31=PINID_LPC+32*0+31;

	public final static int P1_0 =PINID_LPC+32*1+0;
	public final static int P1_1 =PINID_LPC+32*1+1;
	public final static int P1_2 =PINID_LPC+32*1+2;
	public final static int P1_3 =PINID_LPC+32*1+3;
	public final static int P1_4 =PINID_LPC+32*1+4;
	public final static int P1_5 =PINID_LPC+32*1+5;
	public final static int P1_6 =PINID_LPC+32*1+6;
	public final static int P1_7 =PINID_LPC+32*1+7;
	public final static int P1_8 =PINID_LPC+32*1+8;
	public final static int P1_9 =PINID_LPC+32*1+9;
	public final static int P1_10=PINID_LPC+32*1+10;
	public final static int P1_11=PINID_LPC+32*1+11;
	public final static int P1_12=PINID_LPC+32*1+12;
	public final static int P1_13=PINID_LPC+32*1+13;
	public final static int P1_14=PINID_LPC+32*1+14;
	public final static int P1_15=PINID_LPC+32*1+15;
	public final static int P1_16=PINID_LPC+32*1+16;
	public final static int P1_17=PINID_LPC+32*1+17;
	public final static int P1_18=PINID_LPC+32*1+18;
	public final static int P1_19=PINID_LPC+32*1+19;
	public final static int P1_20=PINID_LPC+32*1+20;
	public final static int P1_21=PINID_LPC+32*1+21;
	public final static int P1_22=PINID_LPC+32*1+22;
	public final static int P1_23=PINID_LPC+32*1+23;
	public final static int P1_24=PINID_LPC+32*1+24;
	public final static int P1_25=PINID_LPC+32*1+25;
	public final static int P1_26=PINID_LPC+32*1+26;
	public final static int P1_27=PINID_LPC+32*1+27;
	public final static int P1_28=PINID_LPC+32*1+28;
	public final static int P1_29=PINID_LPC+32*1+29;
	public final static int P1_30=PINID_LPC+32*1+30;
	public final static int P1_31=PINID_LPC+32*1+31;

	public final static int P2_0 =PINID_LPC+32*2+0;
	public final static int P2_1 =PINID_LPC+32*2+1;
	public final static int P2_2 =PINID_LPC+32*2+2;
	public final static int P2_3 =PINID_LPC+32*2+3;
	public final static int P2_4 =PINID_LPC+32*2+4;
	public final static int P2_5 =PINID_LPC+32*2+5;
	public final static int P2_6 =PINID_LPC+32*2+6;
	public final static int P2_7 =PINID_LPC+32*2+7;
	public final static int P2_8 =PINID_LPC+32*2+8;
	public final static int P2_9 =PINID_LPC+32*2+9;
	public final static int P2_10=PINID_LPC+32*2+10;
	public final static int P2_11=PINID_LPC+32*2+11;
	public final static int P2_12=PINID_LPC+32*2+12;
	public final static int P2_13=PINID_LPC+32*2+13;
	public final static int P2_14=PINID_LPC+32*2+14;
	public final static int P2_15=PINID_LPC+32*2+15;
	public final static int P2_16=PINID_LPC+32*2+16;
	public final static int P2_17=PINID_LPC+32*2+17;
	public final static int P2_18=PINID_LPC+32*2+18;
	public final static int P2_19=PINID_LPC+32*2+19;
	public final static int P2_20=PINID_LPC+32*2+20;
	public final static int P2_21=PINID_LPC+32*2+21;
	public final static int P2_22=PINID_LPC+32*2+22;
	public final static int P2_23=PINID_LPC+32*2+23;
	public final static int P2_24=PINID_LPC+32*2+24;
	public final static int P2_25=PINID_LPC+32*2+25;
	public final static int P2_26=PINID_LPC+32*2+26;
	public final static int P2_27=PINID_LPC+32*2+27;
	public final static int P2_28=PINID_LPC+32*2+28;
	public final static int P2_29=PINID_LPC+32*2+29;
	public final static int P2_30=PINID_LPC+32*2+30;
	public final static int P2_31=PINID_LPC+32*2+31;
	
	public final static int P3_0 =PINID_LPC+32*3+0;
	public final static int P3_1 =PINID_LPC+32*3+1;
	public final static int P3_2 =PINID_LPC+32*3+2;
	public final static int P3_3 =PINID_LPC+32*3+3;
	public final static int P3_4 =PINID_LPC+32*3+4;
	public final static int P3_5 =PINID_LPC+32*3+5;
	public final static int P3_6 =PINID_LPC+32*3+6;
	public final static int P3_7 =PINID_LPC+32*3+7;
	public final static int P3_8 =PINID_LPC+32*3+8;
	public final static int P3_9 =PINID_LPC+32*3+9;
	public final static int P3_10=PINID_LPC+32*3+10;
	public final static int P3_11=PINID_LPC+32*3+11;
	public final static int P3_12=PINID_LPC+32*3+12;
	public final static int P3_13=PINID_LPC+32*3+13;
	public final static int P3_14=PINID_LPC+32*3+14;
	public final static int P3_15=PINID_LPC+32*3+15;
	public final static int P3_16=PINID_LPC+32*3+16;
	public final static int P3_17=PINID_LPC+32*3+17;
	public final static int P3_18=PINID_LPC+32*3+18;
	public final static int P3_19=PINID_LPC+32*3+19;
	public final static int P3_20=PINID_LPC+32*3+20;
	public final static int P3_21=PINID_LPC+32*3+21;
	public final static int P3_22=PINID_LPC+32*3+22;
	public final static int P3_23=PINID_LPC+32*3+23;
	public final static int P3_24=PINID_LPC+32*3+24;
	public final static int P3_25=PINID_LPC+32*3+25;
	public final static int P3_26=PINID_LPC+32*3+26;
	public final static int P3_27=PINID_LPC+32*3+27;
	public final static int P3_28=PINID_LPC+32*3+28;
	public final static int P3_29=PINID_LPC+32*3+29;
	public final static int P3_30=PINID_LPC+32*3+30;
	public final static int P3_31=PINID_LPC+32*3+31;
	
	public final static int P4_0 =PINID_LPC+32*4+0;
	public final static int P4_1 =PINID_LPC+32*4+1;
	public final static int P4_2 =PINID_LPC+32*4+2;
	public final static int P4_3 =PINID_LPC+32*4+3;
	public final static int P4_4 =PINID_LPC+32*4+4;
	public final static int P4_5 =PINID_LPC+32*4+5;
	public final static int P4_6 =PINID_LPC+32*4+6;
	public final static int P4_7 =PINID_LPC+32*4+7;
	public final static int P4_8 =PINID_LPC+32*4+8;
	public final static int P4_9 =PINID_LPC+32*4+9;
	public final static int P4_10=PINID_LPC+32*4+10;
	public final static int P4_11=PINID_LPC+32*4+11;
	public final static int P4_12=PINID_LPC+32*4+12;
	public final static int P4_13=PINID_LPC+32*4+13;
	public final static int P4_14=PINID_LPC+32*4+14;
	public final static int P4_15=PINID_LPC+32*4+15;
	public final static int P4_16=PINID_LPC+32*4+16;
	public final static int P4_17=PINID_LPC+32*4+17;
	public final static int P4_18=PINID_LPC+32*4+18;
	public final static int P4_19=PINID_LPC+32*4+19;
	public final static int P4_20=PINID_LPC+32*4+20;
	public final static int P4_21=PINID_LPC+32*4+21;
	public final static int P4_22=PINID_LPC+32*4+22;
	public final static int P4_23=PINID_LPC+32*4+23;
	public final static int P4_24=PINID_LPC+32*4+24;
	public final static int P4_25=PINID_LPC+32*4+25;
	public final static int P4_26=PINID_LPC+32*4+26;
	public final static int P4_27=PINID_LPC+32*4+27;
	public final static int P4_28=PINID_LPC+32*4+28;
	public final static int P4_29=PINID_LPC+32*4+29;
	public final static int P4_30=PINID_LPC+32*4+30;
	public final static int P4_31=PINID_LPC+32*4+31;
	
	public final static int P5_0 =PINID_LPC+32*5+0;
	public final static int P5_1 =PINID_LPC+32*5+1;
	public final static int P5_2 =PINID_LPC+32*5+2;
	public final static int P5_3 =PINID_LPC+32*5+3;
	public final static int P5_4 =PINID_LPC+32*5+4;
	public final static int P5_5 =PINID_LPC+32*5+5;
	public final static int P5_6 =PINID_LPC+32*5+6;
	public final static int P5_7 =PINID_LPC+32*5+7;
	public final static int P5_8 =PINID_LPC+32*5+8;
	public final static int P5_9 =PINID_LPC+32*5+9;
	public final static int P5_10=PINID_LPC+32*5+10;
	public final static int P5_11=PINID_LPC+32*5+11;
	public final static int P5_12=PINID_LPC+32*5+12;
	public final static int P5_13=PINID_LPC+32*5+13;
	public final static int P5_14=PINID_LPC+32*5+14;
	public final static int P5_15=PINID_LPC+32*5+15;
	public final static int P5_16=PINID_LPC+32*5+16;
	public final static int P5_17=PINID_LPC+32*5+17;
	public final static int P5_18=PINID_LPC+32*5+18;
	public final static int P5_19=PINID_LPC+32*5+19;
	public final static int P5_20=PINID_LPC+32*5+20;
	public final static int P5_21=PINID_LPC+32*5+21;
	public final static int P5_22=PINID_LPC+32*5+22;
	public final static int P5_23=PINID_LPC+32*5+23;
	public final static int P5_24=PINID_LPC+32*5+24;
	public final static int P5_25=PINID_LPC+32*5+25;
	public final static int P5_26=PINID_LPC+32*5+26;
	public final static int P5_27=PINID_LPC+32*5+27;
	public final static int P5_28=PINID_LPC+32*5+28;
	public final static int P5_29=PINID_LPC+32*5+29;
	public final static int P5_30=PINID_LPC+32*5+30;
	public final static int P5_31=PINID_LPC+32*5+31;	
	//
	public final static int p5 =0x00020000+5;
	public final static int p6 =0x00020000+6;
	public final static int p7 =0x00020000+7;
	public final static int p8 =0x00020000+8;
	public final static int p9 =0x00020000+9;
	public final static int p10=0x00020000+10;
	public final static int p11=0x00020000+11;
	public final static int p12=0x00020000+12;
	public final static int p13=0x00020000+13;
	public final static int p14=0x00020000+14;
	public final static int p15=0x00020000+15;
	public final static int p16=0x00020000+16;
	public final static int p17=0x00020000+17;
	public final static int p18=0x00020000+18;
	public final static int p19=0x00020000+19;
	public final static int p20=0x00020000+20;
	public final static int p21=0x00020000+21;
	public final static int p22=0x00020000+22;
	public final static int p23=0x00020000+23;
	public final static int p24=0x00020000+24;
	public final static int p25=0x00020000+25;
	public final static int p26=0x00020000+26;
	public final static int p27=0x00020000+27;
	public final static int p28=0x00020000+28;
	public final static int p29=0x00020000+29;
	public final static int p30=0x00020000+30;
	public final static int p31=0x00020000+31;
	public final static int p32=0x00020000+32;
	public final static int p33=0x00020000+33;
	public final static int p34=0x00020000+34;
	public final static int p35=0x00020000+35;
	public final static int p36=0x00020000+36;
	public final static int p37=0x00020000+37;
	public final static int p38=0x00020000+38;
	public final static int p39=0x00020000+39;
	public final static int p40=0x00020000+40;

	public final static int LED1=0x00030000|0x0000+0;
	public final static int LED2=0x00030000|0x0000+1;
	public final static int LED3=0x00030000|0x0000+2;
	public final static int LED4=0x00030000|0x0000+3;

	public final static int USBTX=0x00030000|0x0100+0;
	public final static int USBRX=0x00030000|0x0100+1;
	//D0-D15
	public final static int D0=0x00040000+0;
	public final static int D1=0x00040000+1;
	public final static int D2=0x00040000+2;
	public final static int D3=0x00040000+3;
	public final static int D4=0x00040000+4;
	public final static int D5=0x00040000+5;
	public final static int D6=0x00040000+6;
	public final static int D7=0x00040000+7;
	public final static int D8=0x00040000+8;
	public final static int D9=0x00040000+9;
	public final static int D10=0x00040000+10;
	public final static int D11=0x00040000+11;
	public final static int D12=0x00040000+12;
	public final static int D13=0x00040000+13;
	public final static int D14=0x00040000+14;
	public final static int D15=0x00040000+15;
	//A0-A5
	public final static int A0=0x00040000+0x0100+0;
	public final static int A1=0x00040000+0x0100+1;
	public final static int A2=0x00040000+0x0100+2;
	public final static int A3=0x00040000+0x0100+3;
	public final static int A4=0x00040000+0x0100+4;
	public final static int A5=0x00040000+0x0100+5;

	public final static int I2C_SCL=0x00040000+0x0200+0;
	public final static int I2C_SDA=0x00040000+0x0200+1;
	public final static int NC=0x7FFFFFFF;
}
