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


module full_add_1b(a,b,cin,sum,cout);
    input a, b, cin;
    output sum, cout;
    assign {cout, sum} = a + b + cin;
endmodule
