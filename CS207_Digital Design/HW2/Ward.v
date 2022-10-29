/* Assigment2 */
module Ward (
   a,b,c,d,e,out
);
    input[0:0] a,b,c,d,e;
    output reg out;  
    always @(*) begin
        case (e)
            1'b0: 
                casex({a,b,c,d})
                    4'b1xxx: out = 3'b000;
                    4'b01xx: out = 3'b001;
                    4'b001x: out = 3'b010;
                    4'b0001: out = 3'b011;
                    default: out = 3'b100;
                endcase
            1'b1:
                casex({a,b,c,d})
                    4'bxxx1: out = 3'b011;
                    4'bxx10: out = 3'b010;
                    4'bx100: out = 3'b001;
                    4'b1000: out = 3'b000;
                    default: out = 3'b100;
                endcase
        endcase
    end
endmodule