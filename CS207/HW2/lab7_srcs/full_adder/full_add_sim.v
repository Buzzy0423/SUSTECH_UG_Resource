`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2021/10/27 15:01:20
// Design Name: 
// Module Name: full_add_sim
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

module full_add_sm();
    reg scin;
    reg [1:0] sa, sb;
    wire [1:0] ssum;
    wire scout;
    full_add_2b u2(sa, sb, scin, ssum, scout);

    initial
        {scin, sa, sb} = 5'd0;

    initial begin
        repeat(31)
            #10 {scin, sa, sb} = {scin, sa, sb} + 1;
        #10 $finish();
    end
endmodule