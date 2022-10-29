`timescale 1ns / 1ps

module multi_key (
           input clk,
           input key_en,
           input left_tar,
           input [3:0] row,       // [0-3] K3 L3 J4 K4
           output reg [3:0] col,  // [0-3] L5 J6 K6 M2
           output reg [3:0] keyboard_val
       );
parameter NO_KEY_PRESSED = 3'b000;  // 没有按键按下
parameter SCAN_COL0      = 3'b001;  // 扫描0列
parameter SCAN_COL1      = 3'b010;  // 扫描1
parameter SCAN_COL2      = 3'b011;  // 扫描2
parameter SCAN_COL3      = 3'b100;  // 扫描3
parameter KEY_PRESSED    = 3'b101;  // 有按键按下

reg [19:0] cnt;
wire key_clk;

always @ (posedge clk, negedge key_en)
    if (!key_en) cnt <= 0;
    else cnt <= cnt + 1;

assign key_clk = cnt[19];

reg [5:0] current_state, next_state;

always @ (posedge clk, negedge key_en)
    if (!key_en) current_state <= NO_KEY_PRESSED;
    else current_state <= next_state;

always @* begin
    case (current_state)
        NO_KEY_PRESSED:
            if (row != 4'hF) next_state = SCAN_COL0;
            else next_state = NO_KEY_PRESSED;
        SCAN_COL0:
            if (row != 4'hF) next_state = KEY_PRESSED;
            else next_state = SCAN_COL1;
        SCAN_COL1:
            if (row != 4'hF) next_state = KEY_PRESSED;
            else next_state = SCAN_COL2;
        SCAN_COL2:
            if (row != 4'hF) next_state = KEY_PRESSED;
            else next_state = SCAN_COL3;
        SCAN_COL3:
            if (row != 4'hF) next_state = KEY_PRESSED;
            else next_state = NO_KEY_PRESSED;
        KEY_PRESSED:
            if (row != 4'hF) next_state = KEY_PRESSED;
            else next_state = NO_KEY_PRESSED;
    endcase
end

reg key_pressed_flag;
reg [3:0] col_val, row_val;

//! FIXME: not sure if this is right
always @ (posedge clk, negedge key_en) begin
    if (!key_en) begin
        col <= 0;
        key_pressed_flag <= 0;
    end
    else begin
        case (next_state)
            NO_KEY_PRESSED: begin
                col <= 4'h0;
                key_pressed_flag <= 0;
            end
            SCAN_COL0: col <= 4'b1110;
            SCAN_COL1: col <= 4'b1101;
            SCAN_COL2: col <= 4'b1011;
            SCAN_COL3: col <= 4'b0111;
            KEY_PRESSED: begin
                col_val <= col;
                row_val <= row;
                key_pressed_flag <= 1;
            end
        endcase
    end
end

always @ (posedge key_clk, negedge key_en) begin
    if (!key_en) keyboard_val <= 4'h0;
    else if (key_pressed_flag)
    case ({col_val, row_val})
        8'b1110_1110: keyboard_val <= 4'h1;
        8'b1110_1101: keyboard_val <= 4'h4;
        8'b1110_1011: keyboard_val <= 4'h7;
        8'b1110_0111: keyboard_val <= 4'hE;

        8'b1101_1110: keyboard_val <= 4'h2;
        8'b1101_1101: keyboard_val <= 4'h5;
        8'b1101_1011: keyboard_val <= 4'h8;
        8'b1101_0111: keyboard_val <= 4'h0;

        8'b1011_1110: keyboard_val <= 4'h3;
        8'b1011_1101: keyboard_val <= 4'h6;
        8'b1011_1011: keyboard_val <= 4'h9;
        8'b1011_0111: keyboard_val <= 4'hF;

        8'b0111_1110: keyboard_val <= 4'hA;
        8'b0111_1101: keyboard_val <= 4'hB;
        8'b0111_1011: keyboard_val <= 4'hC;
        8'b0111_0111: keyboard_val <= 4'hD;
    endcase
end
endmodule
