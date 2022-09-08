`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2022/05/20 22:34:35
// Design Name: 
// Module Name: Switch
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


module Switch(clk, rst, switchread, switch,switchaddr, switchrdata, switch_i);
    input clk;                    
    input rst;                   
    input switch;                    //  signal from memorio
    input[1:0] switchaddr;            //  last 2 bits of address
    input switchread;                //  IORead
    output [15:0] switchrdata;        //  signal to CPU
    input [23:0] switch_i;            //  signal from board

    reg [15:0] switchrdata;

    always@(negedge clk, posedge rst) 
    begin
        if(rst) 
            switchrdata <= 0;
        else if(switch && switchread) 
            begin
                if(switchaddr==2'b00)
                    switchrdata[15:0] <= switch_i[15:0];   // data output,lower 16 bits non-extended
                else if(switchaddr==2'b10)
                    switchrdata[15:0] <= { 8'h00, switch_i[23:16] }; //data output, upper 8 bits extended with zero
                else 
                    switchrdata <= switchrdata;
            end
        else
            switchrdata <= switchrdata;
    end
endmodule
