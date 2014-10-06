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
	
	public final static int PTA0=0x00050000+0;
	public final static int PTA1=0x00050000+1;
	public final static int PTA2=0x00050000+2;
	public final static int PTA3=0x00050000+3;
	public final static int PTA4=0x00050000+4;
	public final static int PTA5=0x00050000+5;
	public final static int PTA6=0x00050000+6;
	public final static int PTA7=0x00050000+7;
	public final static int PTA8=0x00050000+8;
	public final static int PTA9=0x00050000+9;
	public final static int PTA10=0x00050000+10;
	public final static int PTA11=0x00050000+11;
	public final static int PTA12=0x00050000+12;
	public final static int PTA13=0x00050000+13;
	public final static int PTA14=0x00050000+14;
	public final static int PTA15=0x00050000+15;
	public final static int PTA16=0x00050000+16;
	public final static int PTA17=0x00050000+17;
	public final static int PTA18=0x00050000+18;
	public final static int PTA19=0x00050000+19;
	public final static int PTA20=0x00050000+20;
	public final static int PTA21=0x00050000+21;
	public final static int PTA22=0x00050000+22;
	public final static int PTA23=0x00050000+23;
	public final static int PTA24=0x00050000+24;
	public final static int PTA25=0x00050000+25;
	public final static int PTA26=0x00050000+26;
	public final static int PTA27=0x00050000+27;
	public final static int PTA28=0x00050000+28;
	public final static int PTA29=0x00050000+29;
	public final static int PTA30=0x00050000+30;
	public final static int PTA31=0x00050000+31;
	
	public final static int PTB0=0x00050000+32+0;
	public final static int PTB1=0x00050000+32+1;
	public final static int PTB2=0x00050000+32+2;
	public final static int PTB3=0x00050000+32+3;
	public final static int PTB4=0x00050000+32+4;
	public final static int PTB5=0x00050000+32+5;
	public final static int PTB6=0x00050000+32+6;
	public final static int PTB7=0x00050000+32+7;
	public final static int PTB8=0x00050000+32+8;
	public final static int PTB9=0x00050000+32+9;
	public final static int PTB10=0x00050000+32+10;
	public final static int PTB11=0x00050000+32+11;
	public final static int PTB12=0x00050000+32+12;
	public final static int PTB13=0x00050000+32+13;
	public final static int PTB14=0x00050000+32+14;
	public final static int PTB15=0x00050000+32+15;
	public final static int PTB16=0x00050000+32+16;
	public final static int PTB17=0x00050000+32+17;
	public final static int PTB18=0x00050000+32+18;
	public final static int PTB19=0x00050000+32+19;
	public final static int PTB20=0x00050000+32+20;
	public final static int PTB21=0x00050000+32+21;
	public final static int PTB22=0x00050000+32+22;
	public final static int PTB23=0x00050000+32+23;
	public final static int PTB24=0x00050000+32+24;
	public final static int PTB25=0x00050000+32+25;
	public final static int PTB26=0x00050000+32+26;
	public final static int PTB27=0x00050000+32+27;
	public final static int PTB28=0x00050000+32+28;
	public final static int PTB29=0x00050000+32+29;
	public final static int PTB30=0x00050000+32+30;
	public final static int PTB31=0x00050000+32+31;	
	
	public final static int PTC0=0x00050000+64+0;
	public final static int PTC1=0x00050000+64+1;
	public final static int PTC2=0x00050000+64+2;
	public final static int PTC3=0x00050000+64+3;
	public final static int PTC4=0x00050000+64+4;
	public final static int PTC5=0x00050000+64+5;
	public final static int PTC6=0x00050000+64+6;
	public final static int PTC7=0x00050000+64+7;
	public final static int PTC8=0x00050000+64+8;
	public final static int PTC9=0x00050000+64+9;
	public final static int PTC10=0x00050000+64+10;
	public final static int PTC11=0x00050000+64+11;
	public final static int PTC12=0x00050000+64+12;
	public final static int PTC13=0x00050000+64+13;
	public final static int PTC14=0x00050000+64+14;
	public final static int PTC15=0x00050000+64+15;
	public final static int PTC16=0x00050000+64+16;
	public final static int PTC17=0x00050000+64+17;
	public final static int PTC18=0x00050000+64+18;
	public final static int PTC19=0x00050000+64+19;
	public final static int PTC20=0x00050000+64+20;
	public final static int PTC21=0x00050000+64+21;
	public final static int PTC22=0x00050000+64+22;
	public final static int PTC23=0x00050000+64+23;
	public final static int PTC24=0x00050000+64+24;
	public final static int PTC25=0x00050000+64+25;
	public final static int PTC26=0x00050000+64+26;
	public final static int PTC27=0x00050000+64+27;
	public final static int PTC28=0x00050000+64+28;
	public final static int PTC29=0x00050000+64+29;
	public final static int PTC30=0x00050000+64+30;
	public final static int PTC31=0x00050000+64+31;	
	
	public final static int PTD0=0x00050000+96+0;
	public final static int PTD1=0x00050000+96+1;
	public final static int PTD2=0x00050000+96+2;
	public final static int PTD3=0x00050000+96+3;
	public final static int PTD4=0x00050000+96+4;
	public final static int PTD5=0x00050000+96+5;
	public final static int PTD6=0x00050000+96+6;
	public final static int PTD7=0x00050000+96+7;
	public final static int PTD8=0x00050000+96+8;
	public final static int PTD9=0x00050000+96+9;
	public final static int PTD10=0x00050000+96+10;
	public final static int PTD11=0x00050000+96+11;
	public final static int PTD12=0x00050000+96+12;
	public final static int PTD13=0x00050000+96+13;
	public final static int PTD14=0x00050000+96+14;
	public final static int PTD15=0x00050000+96+15;
	public final static int PTD16=0x00050000+96+16;
	public final static int PTD17=0x00050000+96+17;
	public final static int PTD18=0x00050000+96+18;
	public final static int PTD19=0x00050000+96+19;
	public final static int PTD20=0x00050000+96+20;
	public final static int PTD21=0x00050000+96+21;
	public final static int PTD22=0x00050000+96+22;
	public final static int PTD23=0x00050000+96+23;
	public final static int PTD24=0x00050000+96+24;
	public final static int PTD25=0x00050000+96+25;
	public final static int PTD26=0x00050000+96+26;
	public final static int PTD27=0x00050000+96+27;
	public final static int PTD28=0x00050000+96+28;
	public final static int PTD29=0x00050000+96+29;
	public final static int PTD30=0x00050000+96+30;
	public final static int PTD31=0x00050000+96+31;

	public final static int PTE0=0x00050000+128+0;
	public final static int PTE1=0x00050000+128+1;
	public final static int PTE2=0x00050000+128+2;
	public final static int PTE3=0x00050000+128+3;
	public final static int PTE4=0x00050000+128+4;
	public final static int PTE5=0x00050000+128+5;
	public final static int PTE6=0x00050000+128+6;
	public final static int PTE7=0x00050000+128+7;
	public final static int PTE8=0x00050000+128+8;
	public final static int PTE9=0x00050000+128+9;
	public final static int PTE10=0x00050000+128+10;
	public final static int PTE11=0x00050000+128+11;
	public final static int PTE12=0x00050000+128+12;
	public final static int PTE13=0x00050000+128+13;
	public final static int PTE14=0x00050000+128+14;
	public final static int PTE15=0x00050000+128+15;
	public final static int PTE16=0x00050000+128+16;
	public final static int PTE17=0x00050000+128+17;
	public final static int PTE18=0x00050000+128+18;
	public final static int PTE19=0x00050000+128+19;
	public final static int PTE20=0x00050000+128+20;
	public final static int PTE21=0x00050000+128+21;
	public final static int PTE22=0x00050000+128+22;
	public final static int PTE23=0x00050000+128+23;
	public final static int PTE24=0x00050000+128+24;
	public final static int PTE25=0x00050000+128+25;
	public final static int PTE26=0x00050000+128+26;
	public final static int PTE27=0x00050000+128+27;
	public final static int PTE28=0x00050000+128+28;
	public final static int PTE29=0x00050000+128+29;
	public final static int PTE30=0x00050000+128+30;
	public final static int PTE31=0x00050000+128+31;
	
	public final static int LED_RED		=0x00060000+0;
	public final static int LED_GREEN	=0x00060000+1;
	public final static int LED_BLUE	=0x00060000+2;
	
	public final static int SW2		=0x00060100+2;
	public final static int SW3		=0x00060100+3;
	
}
