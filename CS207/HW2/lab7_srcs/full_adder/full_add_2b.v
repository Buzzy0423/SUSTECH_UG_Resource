`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 10/27/2021 12:45:50 PM
// Design Name: 
// Module Name: full_add_1b
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


module full_add_2b(a,b,cin,sum,cout);
    input [1:0] a, b;
    input cin;
    output [1:0] sum;
    output cout;

    wire cout1;
    full_add_1b u0(a[0], b[0], cin, sum[0], cout1);
    full_add_1b u1(a[1], b[1], cout1, sum[1], cout);
endmodule