`timescale 1ns/1ps

// minisys [Y18] ~> 100MHz, out_freq = in_freq / N
// WIDTH >= ceil(log2(N))
module freq_div_even #(parameter N = 100, WIDTH = 7)(
    input clk,
    input rst,
    output reg clk_out
);
    reg [WIDTH:0] cnt;
    always @(posedge clk, posedge rst) begin
        if (rst) begin
            cnt <= 0;
        end
        else if (cnt == N-1) begin
            cnt <= 0;
        end
        else begin
            cnt <= cnt + 1;
        end
    end

    always @(posedge clk or posedge rst) begin
        if (rst) begin
            clk_out <= 0;
        end
        else if (cnt == N-1) begin
            clk_out <= !clk_out;
        end
    end
endmodule
