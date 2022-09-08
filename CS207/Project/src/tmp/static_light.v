`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2021/12/16 10:54:02
// Design Name: 
// Module Name: static_light
// Project Name: 
// Target Devices: 
// Tool Versions: 
// Description: 
// 
// Dependencies: 
// 
// Revision:
// Revision 0.01 - File Created
// Additional Comments:
// 
//////////////////////////////////////////////////////////////////////////////////


module static_light(rst,clk,DIG,Y,x7_in,x6_in,x5_in,x4_in,x3_in,x2_in,x1_in,x0_in);
input rst;
 input clk;
 output [7:0] DIG;
 output  [7:0] Y;
input [3:0] x0_in;
input [3:0] x1_in;
input [3:0] x2_in;
input [3:0] x3_in;
input [3:0] x4_in;
input [3:0] x5_in;
input [3:0] x6_in;
input [3:0] x7_in;
 reg clkout;
 reg [31:0] cnt;
 reg [2:0] scan_cnt;
 reg [6:0] x0;
 reg [6:0] x1;
 reg [6:0] x2;
 reg [6:0] x3;
 reg [6:0] x4;
 reg [6:0] x5;
 reg [6:0] x6;
 reg [6:0] x7;
 

always @(*)
begin            
  if(!rst) begin 
      x0=7'b0000000;
      x1=7'b0000000;
      x2=7'b0000000;
      x3=7'b0000000;
      x4=7'b0000000;
      x5=7'b0000000;
      x6=7'b0000000;
      x7=7'b0000000;
  end 
  else           
begin
 if (x0_in == 4'd0) x0=7'b0111111;//0
 if (x0_in == 4'd1) x0=7'b0000110;//1
 if (x0_in == 4'd2) x0=7'b1011011;//2
 if (x0_in == 4'd3) x0=7'b1001111;//3
 if (x0_in == 4'd4) x0=7'b1100110;//4
 if (x0_in == 4'd5) x0=7'b1101101;//5
 if (x0_in == 4'd6) x0=7'b1111101;//6
 if (x0_in == 4'd7) x0=7'b0100111;//7
 if (x0_in == 4'd8) x0=7'b1111111;//8
 if (x0_in == 4'd9) x0=7'b1100111;//9
 
if (x1_in == 4'd0) x1=7'b0111111;//0  
if (x1_in == 4'd1) x1=7'b0000110;//1  
if (x1_in == 4'd2) x1=7'b1011011;//2  
if (x1_in == 4'd3) x1=7'b1001111;//3  
if (x1_in == 4'd4) x1=7'b1100110;//4  
if (x1_in == 4'd5) x1=7'b1101101;//5  
if (x1_in == 4'd6) x1=7'b1111101;//6  
if (x1_in == 4'd7) x1=7'b0100111;//7  
if (x1_in == 4'd8) x1=7'b1111111;//8  
if (x1_in == 4'd9) x1=7'b1100111;//9  

if (x2_in == 4'd0) x2=7'b0111111;//0  
if (x2_in == 4'd1) x2=7'b0000110;//1  
if (x2_in == 4'd2) x2=7'b1011011;//2  
if (x2_in == 4'd3) x2=7'b1001111;//3  
if (x2_in == 4'd4) x2=7'b1100110;//4  
if (x2_in == 4'd5) x2=7'b1101101;//5  
if (x2_in == 4'd6) x2=7'b1111101;//6  
if (x2_in == 4'd7) x2=7'b0100111;//7  
if (x2_in == 4'd8) x2=7'b1111111;//8  
if (x2_in == 4'd9) x2=7'b1100111;//9 

if (x3_in == 4'd0) x3=7'b0111111;//0  
if (x3_in == 4'd1) x3=7'b0000110;//1  
if (x3_in == 4'd2) x3=7'b1011011;//2  
if (x3_in == 4'd3) x3=7'b1001111;//3  
if (x3_in == 4'd4) x3=7'b1100110;//4  
if (x3_in == 4'd5) x3=7'b1101101;//5  
if (x3_in == 4'd6) x3=7'b1111101;//6  
if (x3_in == 4'd7) x3=7'b0100111;//7  
if (x3_in == 4'd8) x3=7'b1111111;//8  
if (x3_in == 4'd9) x3=7'b1100111;//9  

if (x4_in == 4'd0) x4=7'b0111111;//0  
if (x4_in == 4'd1) x4=7'b0000110;//1  
if (x4_in == 4'd2) x4=7'b1011011;//2  
if (x4_in == 4'd3) x4=7'b1001111;//3  
if (x4_in == 4'd4) x4=7'b1100110;//4  
if (x4_in == 4'd5) x4=7'b1101101;//5  
if (x4_in == 4'd6) x4=7'b1111101;//6  
if (x4_in == 4'd7) x4=7'b0100111;//7  
if (x4_in == 4'd8) x4=7'b1111111;//8  
if (x4_in == 4'd9) x4=7'b1100111;//9  

if (x5_in == 4'd0) x5=7'b0111111;//0  
if (x5_in == 4'd1) x5=7'b0000110;//1  
if (x5_in == 4'd2) x5=7'b1011011;//2  
if (x5_in == 4'd3) x5=7'b1001111;//3  
if (x5_in == 4'd4) x5=7'b1100110;//4  
if (x5_in == 4'd5) x5=7'b1101101;//5  
if (x5_in == 4'd6) x5=7'b1111101;//6  
if (x5_in == 4'd7) x5=7'b0100111;//7  
if (x5_in == 4'd8) x5=7'b1111111;//8  
if (x5_in == 4'd9) x5=7'b1100111;//9  

if (x6_in == 4'd0) x6=7'b0111111;//0  
if (x6_in == 4'd1) x6=7'b0000110;//1  
if (x6_in == 4'd2) x6=7'b1011011;//2  
if (x6_in == 4'd3) x6=7'b1001111;//3  
if (x6_in == 4'd4) x6=7'b1100110;//4  
if (x6_in == 4'd5) x6=7'b1101101;//5  
if (x6_in == 4'd6) x6=7'b1111101;//6  
if (x6_in == 4'd7) x6=7'b0100111;//7  
if (x6_in == 4'd8) x6=7'b1111111;//8  
if (x6_in == 4'd9) x6=7'b1100111;//9  

if (x7_in == 4'd0) x7=7'b0111111;//0  
if (x7_in == 4'd1) x7=7'b0000110;//1  
if (x7_in == 4'd2) x7=7'b1011011;//2  
if (x7_in == 4'd3) x7=7'b1001111;//3  
if (x7_in == 4'd4) x7=7'b1100110;//4  
if (x7_in == 4'd5) x7=7'b1101101;//5  
if (x7_in == 4'd6) x7=7'b1111101;//6  
if (x7_in == 4'd7) x7=7'b0100111;//7  
if (x7_in == 4'd8) x7=7'b1111111;//8  
if (x7_in == 4'd9) x7=7'b1100111;//9   
 end
end
 parameter period = 200000;
 
 reg[6:0] Y_r;
 reg [7:0] DIG_r;
 assign Y= {1'b1,(~Y_r[6:0])};
 assign DIG= ~DIG_r;

 
 always @(posedge clk or negedge rst)
 begin
   if(!rst) begin
     cnt<=0;
     clkout<=0;
   end
   else begin
      if(cnt == (period>>1)-1)
      begin
       clkout<=~clkout;
       cnt<=0;
   end
   else
     cnt<=cnt+1;
  end
 end
 


always @ (posedge clkout or negedge rst)
begin
 if(!rst)
   scan_cnt<=0;
 else begin
   scan_cnt <= scan_cnt + 1;
   if(scan_cnt == 3'd7) scan_cnt <= 0;  
 end
end

always @ (scan_cnt)
begin
  case(scan_cnt)
    3'b000: DIG_r=8'b0000_0001;
    3'b001: DIG_r=8'b0000_0010;
    3'b010: DIG_r=8'b0000_0100;
    3'b011: DIG_r=8'b0000_1000;
    3'b100: DIG_r=8'b0001_0000;
    3'b101: DIG_r=8'b0010_0000;
    3'b110: DIG_r=8'b0100_0000;
    3'b111: DIG_r=8'b1000_0000;
    default: DIG_r = 8'b0000_0000;
    endcase
end


always @ (scan_cnt)
begin
 case(scan_cnt)
  0:Y_r= x0;
  1:Y_r= x1;
  2:Y_r= x2;
  3:Y_r= x3;
  4:Y_r= x4;
  5:Y_r= x5;
  6:Y_r= x6;
  7:Y_r= x7;
  default : Y_r= 7'b0000000;
  endcase
 end
endmodule
