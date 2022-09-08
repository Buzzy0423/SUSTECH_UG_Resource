`timescale 1ns / 1ps

module main_test(
    input clk,
    input key_en,
    // input [3:0] row,  // [0-3] K3 L3 J4 K4
    // output [3:0] col,  // [0-3] L5 J6 K6 M2
    // output [7:0] seg_out,
    // output reg [7:0] seg_en
    output laba
    );
    // wire [3:0] x;
    // key_top1 kt(clk, key_en,row,col,x);
    // seg_tube st(x,seg_out);
    // always @*
    // seg_en = 0;

    // reg[1:0] sel=1;
    musicplayer mp3(clk,1,1,laba);
    // reg[30:0] cnt = 0;

    // always @ (negedge clk) begin
    //     if(cnt == 10000) begin
    //         cnt = 0;
    //         laba = ~laba;
    //     end else cnt=cnt+1;
    // end
endmodule
