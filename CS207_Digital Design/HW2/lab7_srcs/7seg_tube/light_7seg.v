`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2019/10/23 15:11:33
// Design Name: 
// Module Name: lab7_7_segment_tube
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


module lab7_7_segment_tube(
input [3:0] sw,
output reg [7:0] seg_out,
output  [7:0] seg_en
    );
 assign seg_en=8'h00;  
 always @*
 begin
    case(sw)
        4'h0: seg_out=8'b0100_0000;  // 0
        4'h1: seg_out=8'b0111_1001;  // 1
        4'h2: seg_out=8'b0010_0100;  // 2
        4'h3: seg_out=8'b0011_0000;  // 3
        4'h4: seg_out=8'b0001_1001;  // 4
        4'h5: seg_out=8'b0001_0010;  // 5
        4'h6: seg_out=8'b0000_0010;  // 6
        4'h7: seg_out=8'b0111_1000;  // 7
        4'h8: seg_out=8'b0000_0000;  // 8
        4'h9: seg_out=8'b0001_0000;  // 9
        4'ha: seg_out=8'b0000_1000;  // A
        4'hb: seg_out=8'b0000_0011;  // b
        4'hc: seg_out=8'b0100_0110;  // c
        4'hd: seg_out=8'b0010_0001;  // d
        4'he: seg_out=8'b0000_0110;  // E
        4'hf: seg_out=8'b0000_1110;  // F
        default: seg_out = 8'b0000_0000;
    endcase
 end
    
endmodule
