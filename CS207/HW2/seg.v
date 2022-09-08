module seg(
input  a, b, c, d, e,
output reg [7:0] seg_out,
output reg[7:0]seg_en
    );
 always @*
 begin
     case (e)
             1'b0: 
                 casex({a,b,c,d})
                     4'b1xxx:begin
                         seg_en = 8'b1111_1110;
                         seg_out=8'b0100_0000;
                         end // 0
                     4'b01xx:begin
                         seg_en = 8'b1111_1110;
                         seg_out=8'b0111_1001;
                         end  // 1
                     4'b001x:begin
                         seg_en = 8'b1111_1110;
                         seg_out=8'b0010_0100;
                         end  // 2
                     4'b0001: begin
                         seg_en = 8'b1111_1110;
                         seg_out=8'b0011_0000;
                         end  // 3
                     default: seg_en = 8'b1111_1111;
                 endcase
             1'b1:
                 casex({a,b,c,d})
                     4'bxxx1: begin
                         seg_en = 8'b1111_1110;
                         seg_out=8'b0011_0000;
                         end  // 3
                     4'bxx10:begin
                         seg_en = 8'b1111_1110;
                         seg_out=8'b0010_0100;
                         end  // 2
                     4'bx100:begin
                         seg_en = 8'b1111_1110;
                         seg_out=8'b0111_1001;
                         end  // 1
                     4'b1000:begin
                         seg_en = 8'b1111_1110;
                         seg_out=8'b0100_0000;
                         end // 0
                     default: seg_en = 8'b1111_1111;
                 endcase
     endcase
 end
    
endmodule
