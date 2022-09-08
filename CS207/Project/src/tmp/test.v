`timescale 1ns / 1ps

module main_test(
           input clk,
           output [7:0] out_en,
           output [7:0] out_msg_sig
       );
reg [49:0] msg = 'h1234abcd;
RollableSeg rs(clk, 1, 1, msg, out_en, out_msg_sig);
endmodule

    // module RollableSeg(
    //     input clk,
    //     input disp_en,
    //     input roll,
    //     input reg [49:0] msg,
    //     output reg [7:0] out_en,
    //     output reg [7:0] out_msg_sig
    // );

