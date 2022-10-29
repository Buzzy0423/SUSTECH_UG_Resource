`timescale 1ps/1ns

// 输出信号每秒反转一次
// WIDTH >= ceil(log2(N))
module timer_1sec(
    input clk,
    input rst,
    output reg clk_out
);
    reg clk_e6, clk_e3;

    freq_div_even#(.N(100), .WIDTH(7)) div9t6(clk, rst, clk_e6);
    freq_div_even#(.N(100), .WIDTH(7)) div6t3(clk_e6, rst, clk_e3);
    freq_div_even#(.N(100), .WIDTH(7)) div3t1(clk_e3, rst, clk_out);
endmodule
