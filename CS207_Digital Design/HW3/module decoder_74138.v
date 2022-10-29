module decoder_74138 (
    a,b,c,en1,en2,en3,y0,y1,y2,y3,y4,y5,y6,y7
);
    input a,b,c,en1,en2,en3;
    output reg y0,y1,y2,y3,y4,y5,y6,y7;
    always @(*) begin
        if ({en1,en2,en3} == 3'b100) begin
            case ({a,b,c})
                3'b000: {y0,y1,y2,y3,y4,y5,y6,y7} = 8'b01111111;
                3'b001: {y0,y1,y2,y3,y4,y5,y6,y7} = 8'b10111111;
                3'b010: {y0,y1,y2,y3,y4,y5,y6,y7} = 8'b11011111;
                3'b011: {y0,y1,y2,y3,y4,y5,y6,y7} = 8'b11101111;
                3'b100: {y0,y1,y2,y3,y4,y5,y6,y7} = 8'b11110111;
                3'b101: {y0,y1,y2,y3,y4,y5,y6,y7} = 8'b11111011;
                3'b110: {y0,y1,y2,y3,y4,y5,y6,y7} = 8'b11111101;
                3'b111: {y0,y1,y2,y3,y4,y5,y6,y7} = 8'b11111110;
                default: {y0,y1,y2,y3,y4,y5,y6,y7} = 8'b11111111;
            endcase
        end
        else begin
            {y0,y1,y2,y3,y4,y5,y6,y7} = 8'b11111111;
        end
    end
endmodule

module sim74138 ();
    reg [0:0] A,B,C,D,E,F;
    wire [0:0] y0,y1,y2,y3,y4,y5,y6,y7;
    decoder_74138 decoder(A,B,C,D,E,F,y0,y1,y2,y3,y4,y5,y6,y7);
    initial begin
        {A,B,C,D,E,F} = 6'b000000;
        while ({A,B,C,D,E,F} < 6'b1111111) begin
            #10 {A,B,C,D,E,F} = {A,B,C,D,E,F} + 1;
        end
        #10 $finish;
    end
endmodule

module sim4-16 ();
    reg [0:0] A,B,C,D,En1,En2,En3;
    wire x0,x1,x2,x3,x4,x5,x6,x7,x8,x9,x10,x11,x12,x13,x14,x15;
    decoder_416(A,B,C,D,En1,En2,En3,x0,x1,x2,x3,x4,x5,x6,x7,x8,x9,x10,x11,x12,x13,x14,x15);
    initial `probe_start;
    `probe(A);
    `probe(B);
    `probe(C);
    `probe(D);
    `probe(En1);
    `probe(En2);
    `probe(En3);
    `probe(x0);
    `probe(x1);
    `probe(x2);
    `probe(x3);
    `probe(x4);
    `probe(x5);
    `probe(x6);
    `probe(x7);
    `probe(x8);
    `probe(x9);
    `probe(x10);
    `probe(x11);
    `probe(x13);
    `probe(x14);
    `probe(x15);
    initial begin
        {En1,En2,En3,A,B,C,D} = 7'b0000000;
        while ({En1,En2,En3,A,B,C,D} < 7'b1111111) begin
            #10 {En1,En2,En3,A,B,C,D} = {En1,En2,En3,A,B,C,D} + 1;
        end
        #10 $finish;
    end
endmodule

module decoder_416 (
  	A,B,C,D,x0,x1,x2,x3,x4,x5,x6,x7,x8,x9,x10,x11,x12,x13,x14,x15
);
  input A,B,C,D;
  output reg x0,x1,x2,x3,x4,x5,x6,x7,x8,x9,x10,x11,x12,x13,x14,x15;
    decoder_74138 decoder(B,C,D,A,1'b0,1'b0,x8,x9,x10,x11,x12,x13,x14,x15);
    decoder_74138 decoder2(B,C,D,~A,1'b0,1'b0,x0,x1,x2,x3,x4,x5,x6,x7);  
endmodule

module mux74151 (
    EN,S2,S1,S0,D7,D6,D5,D4,D3,D2,D1,D0,Y,W
);
    input EN,S2,S1,S0,D7,D6,D5,D4,D3,D2,D1,D0;
    output reg Y,W;
    always @(*) begin
        if(~EN)begin
            case ({S2,S1,S0})
                3'b000: Y = D0; 
                3'b001: Y = D1; 
                3'b010: Y = D2;
                3'b011: Y = D3;  
                3'b100: Y = D4; 
                3'b101: Y = D5;
                3'b110: Y = D6;
                3'b111: Y = D7;   
            endcase
        end
        else begin
            Y = 0;
        end
        W = ~Y;
    end
endmodule

module func_1mux (
    A,B,C,D,out
);  
    input A,B,C,D;
    output reg out;
    wire D6;
    wire D3;
    wire D2;
    wire D1;
    wire D0;
    assign D6 = D;
    assign D3 = D;
    assign D2 = D;
    assign D1 = ~D;
    assign D0 = ~D;
    reg cache;
    mux74151 mux(1'b0,A,B,C,1'b1,D6,1'b1,1'b0,D3,D2,D1,D0,out,cache);
endmodule

module func_2mux (
    A,B,C,D,out
);
    input A,B,C,D;
    output reg out;
    reg tmp1,tmp2,cache1,cache2;
    mux74151 mux1(D,A,B,C,1'b1,1'b0,1'b1,1'b0,1'b0,1'b0,1'b1,1'b1,tmp1,cache1);
    mux74151 mux2(~D,A,B,C,1'b1,1'b1,1'b1,1'b0,1'b1,1'b1,1'b0,1'b0,tmp2,cache2);
    assign out = tmp1 || tmp2;
endmodule

module func_df (
    A,B,C,D,E
);
    input A,B,C,D;
    output reg E;
    assign E = (B && D) || (A && C) || (~B && C && ~D) || (~A && ~B && ~D);
endmodule

module sim_task2 ();
    reg[0:0] A,B,C,D;
    wire[0:0] E,F,G;
    func_df func(A,B,C,D,E);
    func_1mux func1(A,B,C,D,F);
    func_2mux func2(A,B,C,D,G);
    initial begin
        {A,B,C,D} = 4'b0000;
        while ({A,B,C,D} < 4'b1111) begin
            #10 {A,B,C,D} = {A,B,C,D} + 1;
        end
        #10 $finish;
    end
endmodule

module sim74151 ();
    reg [0:0] A,B,C;
    wire E,F;
    mux74151 mux(1'b0,A,B,C,7,6,5,4,3,2,1,0,E,F);
    initial begin
        {A,B,C} = 3'b000;
        
    end
endmodule