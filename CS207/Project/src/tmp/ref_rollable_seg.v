`timescale 1ns / 1ps

module Practice1(
    input rst_n, clk,
    output [7:0] bit_sel,
    output [7:0] seg_out1, seg_out2
    );
    
    reg clkout;
    reg [31:0] cnt;
    reg [2:0] scan_cnt;
    reg [7:0] out_reg;
    reg [7:0] bit_sel_reg;
    reg [7:0] flow;
    wire clk_bps;
    
    parameter period = 200000;
    
    assign seg_out1 = out_reg;
    assign seg_out2 = out_reg;
    assign bit_sel = bit_sel_reg;
    
    always @ (posedge clk or negedge rst_n)
    begin
        if(~rst_n)
            begin
                cnt <=0;
                clkout<=0;
            end
        else
            begin
                if(cnt == (period>>1)-1) begin
                    clkout =~clkout;
                    cnt<=0;
                end
                else
                    cnt <= cnt+1;
            end
    end
    
    always @ (posedge clkout or negedge rst_n)
    begin
        if(!rst_n)
            scan_cnt <=0;
        else begin
            if(scan_cnt == 3'b011)
                scan_cnt <=0;
            else
                scan_cnt <= scan_cnt +1;
        end
    end
    
    always @ (scan_cnt)
    begin
        case(scan_cnt) 
            0:out_reg = 8'b1001_1110;
            1:out_reg = 8'b1011_0110;
            2:out_reg = 8'b1001_1100;
            default: out_reg = 8'b0000_0000;
        endcase
    end
    
    always @ (scan_cnt)
    begin
        case(scan_cnt)
            3'b0000: bit_sel_reg = flow;
            3'b0001: bit_sel_reg = flow<<1;
            3'b0010: bit_sel_reg = flow<<2;
            default: bit_sel_reg = 8'b0000_0000;
        endcase
    end
    
    counter ct(rst_n,clk,clk_bps);
    
    always @ (posedge clk or negedge rst_n)
    begin
        if (!rst_n)
            flow <= 0;
        else begin
            if (flow == 8'b0000_0000)
                flow <= 8'b0000_0001;
//            else if (flow == 8'b0000_00011)
//                flow<= 8'b0000_0011;
//            else if (flow == 8'b0000_0011)
//                flow <= 8'b0000_0111;
            else if (clk_bps)
                flow <= flow<<1;
        end
    end
    
endmodule



module counter(
    input rst_n,clk,
    output clk_bps
);

    reg [13:0] cnt_first, cnt_second;
    always @ (posedge clk or negedge rst_n)
    begin
        if (~rst_n)
            cnt_first <=0;
        else
            if(cnt_first == 14'd10000)
//                if(cnt_first == 14'd10)
                cnt_first <=0;
            else
                cnt_first <= cnt_first +1;
    end
    
    always @ (posedge clk or negedge rst_n)
    begin
        if (!rst_n)
            cnt_second <=0;
        else
            if(cnt_second == 14'd10000)
//            if(cnt_second == 14'd10)
                cnt_second <=0;
            else if (cnt_first==14'd10000)
//            else if (cnt_first==14'd10)
                cnt_second <= cnt_second+1;
    end
    
    assign clk_bps = (cnt_second == 14'd10000)? 1'b1:1'b0;
//    assign clk_bps = (cnt_second == 14'd10)? 1'b1:1'b0;

endmodule