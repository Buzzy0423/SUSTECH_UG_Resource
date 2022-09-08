# DIGITAL DESIGN ASSIGNMENT REPORT

<img src="/Users/lee/课件/数字逻辑/HW3/LOGO.png" alt="LOGO" width=30% />

**Assignment ID: **3

**Student Name:**李子南

**Student ID:**12011517

## PART 1: DIGITAL DESIGN THEORY

<img src="/Users/lee/Library/Containers/com.tencent.xinWeChat/Data/Library/Application Support/com.tencent.xinWeChat/2.0b4.0.9/74316d61f05020987cbe2e0489465279/Message/MessageTemp/885b791e4ea747bc721361df7bcf0657/Image/359591640782779_.pic.jpg" alt="359591640782779_.pic" style="zoom:33%;" />

<img src="/Users/lee/Library/Containers/com.tencent.xinWeChat/Data/Library/Application Support/com.tencent.xinWeChat/2.0b4.0.9/74316d61f05020987cbe2e0489465279/Message/MessageTemp/885b791e4ea747bc721361df7bcf0657/Image/359601640782779_.pic.jpg" alt="359601640782779_.pic" style="zoom:33%;" />

<img src="/Users/lee/Library/Containers/com.tencent.xinWeChat/Data/Library/Application Support/com.tencent.xinWeChat/2.0b4.0.9/74316d61f05020987cbe2e0489465279/Message/MessageTemp/885b791e4ea747bc721361df7bcf0657/Image/359581640782779_.pic.jpg" alt="359581640782779_.pic" style="zoom:33%;" />

## PART 2: DIGITAL DESIGN LAB

### Task1: JK Flip-Flop

#### JK Flip-Flop

```verilog
module JK (
    Clk, rst, J, K, Q
);

input Clk, rst, J, K;
output reg Q;

always @(posedge Clk or negedge rst) begin
    if (rst) begin
        Q = 0;
    end
    else begin
        case ({J,K})
        2'b10: Q <= 1'b1;
        2'b01: Q <= 1'b0;
        2'b11: Q <= ~Q;
        2'b00: Q <= Q;  
        endcase
    end
end
endmodule
```

#### TestBench(Iverilog)

```verilog
module top_module();
	reg clk = 0;
    reg J,K;
    reg rst = 1;
    wire Q;
    `probe(clk);
    `probe(J);
    `probe(K);
    `probe(Q);
    initial `probe_start;
    always #5 clk = ~clk;
    
    JK jk(clk, rst, J, K, Q);
    
    initial begin
        #10 rst = ~rst;
        #10 {J,K} = 2'b10;
        #10 {J,K} = 2'b01;
        #10 {J,K} = 2'b11;
        #10 {J,K} = 2'b00;
        #10 $finish;
    end
endmodule
```

<img src="/Users/lee/Library/Application Support/typora-user-images/截屏2021-12-29 下午1.00.30.png" alt="截屏2021-12-29 下午1.00.30" style="zoom:50%;" />

#### sequential circuit

```verilog
module Main (
    clk,rst, x, Q1, Q2
);
	input clk, rst,x;
	output Q1, Q2;
    JK a(clk, rst, ~x,Q2, Q1);
    JK b(clk, rst, x,~Q1, Q2);
endmodule
```

#### Testbench(Iverilog)

```verilog
module top_module();
	reg clk = 0;
    reg x = 0;
    reg rst = 1;
    wire Q1;
    wire Q2;
    `probe(clk);
    `probe(rst);
    `probe(x);
    `probe(Q1);
    `probe(Q2);
    initial `probe_start;
    always #5 clk = ~clk;
    
    Main main(clk, rst, x, Q1, Q2);
    
    initial begin
        #10 rst = ~rst;
        #10 x = ~x;
        #10 x = ~x;
        #10 x = ~x;
        #10 x = ~x;
        #10 x = ~x;
        #10 $finish;
    end
endmodule
```

<img src="/Users/lee/Library/Application Support/typora-user-images/截屏2021-12-29 下午1.08.37.png" alt="截屏2021-12-29 下午1.08.37" style="zoom:50%;" />

### Task2: JOHNSON-COUNTER

#### 74195

```verilog
module SR (
    input MR_n, CP, PE_n, J, K_n,
    input D3,D2,D1,D0,
    output reg Q3,Q2,Q1,Q0,
    output Q0_n
);
    wire K;
    assign Q0_n = ~Q0;
    assign K = ~K_n;
    always @(posedge CP, negedge MR_n) begin
        if (!MR_n) begin
            {Q3,Q2,Q1,Q0} <= 4'b0000;
        end
        else begin
            if (!PE_n) begin
                {Q3,Q2,Q1,Q0} <= {D3,D2,D1,D0};
            end
            else begin
                case ({J,K})
                    2'b00: {Q3,Q2,Q1,Q0} <= {Q2,Q1,Q0,Q0};
                    2'b01: {Q3,Q2,Q1,Q0} <= {Q2,Q1,Q0,1'b0};
                    2'b10: {Q3,Q2,Q1,Q0} <= {Q2,Q1,Q0,1'b1};
                    2'b11: {Q3,Q2,Q1,Q0} <= {Q2,Q1,Q0,~Q0};
                endcase
            end
        end
    end
endmodule
```

#### Testbench(Iverilog)

```verilog
module top_module();
    reg clk = 1'b0;
    reg rst = 1'b0;
    reg rst2 = 1'b0;
    reg J = 1'b0;
    reg K = 1'b0;
    reg [3:0] D = 4'b0000;
    wire [4:0] Q;
    `probe(clk);
    `probe(rst);
    `probe(rst2);
    `probe(J);
    `probe(K);
    `probe(D[0]);
    `probe(D[1]);
    `probe(D[2]);
    `probe(D[3]);
    `probe(Q[4]);
    `probe(Q[3]);
    `probe(Q[2]);
    `probe(Q[1]);
    `probe(Q[0]);
   	initial `probe_start;
    always #5 clk = ~clk;
    
    SR sr(rst, clk, rst2, J, K, D[3], D[2], D[1], D[0], Q[3], Q[2], Q[1], Q[0], Q[4]);
    
    initial begin
        #10 rst = ~rst;
        #10 rst2 = ~rst2;
        #10 {J, K} = {J, K} + 1;
        #10 {J, K} = {J, K} + 1;
        #10 {J, K} = {J, K} + 1;
        #10 {J, K} = {J, K} + 1;
        #10 {J, K} = {J, K} + 1;
        #10 {J, K} = {J, K} + 1;
        #10 {J, K} = 2'b11;
        #60 $finish;
    end
endmodule
```

<img src="/Users/lee/Library/Application Support/typora-user-images/截屏2021-12-29 下午8.39.23.png" alt="截屏2021-12-29 下午8.39.23" style="zoom:50%;" />

#### Johnson_counter

```verilog
module Johnson_counter(
    input clk, rst,
    output reg [3:0]out
);
wire Q0;
wire [3:0] tmp;
SR sr(rst, clk, 1'b0, 1'b0, 1'b0, ~out[0], out[3], out[2], out[1], tmp[3], tmp[2], tmp[1], tmp[0], Q0);
always @(*) begin
    out = tmp;
end
endmodule
```

#### Testbench(Iverilog)

```verilog
module top_module();
    reg clk = 1'b0;
    reg rst = 1'b0;
    wire [3:0] Q;
    `probe(clk);
    `probe(rst);
    `probe(Q[3]);
    `probe(Q[2]);
    `probe(Q[1]);
    `probe(Q[0]);
   	initial `probe_start;
    always #5 clk = ~clk;
    
    Johnson_counter js(clk, rst, Q);
    
    initial begin
        #10 rst = ~rst;
        #150 $finish;
    end
endmodule
```

<img src="/Users/lee/Library/Application Support/typora-user-images/截屏2021-12-29 下午8.50.09.png" alt="截屏2021-12-29 下午8.50.09" style="zoom:50%;" />

### Task3: Display

```verilog
module Main (
    clk, enable, switch,DIG,Y
);
input clk,enable,switch;

output [7:0] DIG;
output [7:0] Y;

reg [5:0] x0;
reg [5:0] x1;
reg [5:0] x2;
reg [5:0] x3;
reg [5:0] x4;
reg [5:0] x5;
reg [5:0] x6;
reg [5:0] x7;
wire clkout;

always @(*) begin
    if (switch) begin
        x0 = 'd7;
        x1 = 'd0;
        x2 = 'd2;
        x3 = 'd28;
        x4 = 'd12;
        x5 = 6'b111_111;
        x6 = 6'b111_111;
        x7 = 6'b111_111;
    end
    else begin
        x0 = 'd15;
        x1 = 'd1;
        x2 = 'd2;
        x3 = 'd0;
        x4 = 'd2;
        x5 = 6'b111_111;
        x6 = 6'b111_111;
        x7 = 6'b111_111;
    end
end

Clk clock(clk,enable,clkout);

static_light sl(enable, clkout, DIG, Y, x7, x6, x5, x4, x3, x2, x1, x0);

endmodule
```

```verilog
module Clk (
    clk, rst, clkout
);
input clk, rst;
output reg clkout;

reg [31:0] cnt;

parameter period = 200000;

always @(posedge clk or negedge rst)begin
    if(!rst) begin
        cnt<=0;
        clkout<=0;
    end
    else begin
        if(cnt == (period>>1)-1) begin
        clkout<=~clkout;
        cnt<=0;
        end
        else cnt<=cnt+1;
    end
end

endmodule
```

#### Display module 用的是project中的

```verilog
module static_light(rst,clk,DIG,Y,x7_in,x6_in,x5_in,x4_in,x3_in,x2_in,x1_in,x0_in);
input rst;
 input clk;
 output [7:0] DIG;
 output  [7:0] Y;
    input [5:0] x0_in;
    input [5:0] x1_in;
    input [5:0] x2_in;
    input [5:0] x3_in;
    input [5:0] x4_in;
    input [5:0] x5_in;
    input [5:0] x6_in;
    input [5:0] x7_in;
 reg [2:0] scan_cnt;
 reg [6:0] x0;
 reg [6:0] x1;
 reg [6:0] x2;
 reg [6:0] x3;
 reg [6:0] x4;
 reg [6:0] x5;
 reg [6:0] x6;
 reg [6:0] x7;
 

always @(*)
begin            
  if(!rst) begin 
      x0=7'b0000000;
      x1=7'b0000000;
      x2=7'b0000000;
      x3=7'b0000000;
      x4=7'b0000000;
      x5=7'b0000000;
      x6=7'b0000000;
      x7=7'b0000000;
  end 
  else           
begin
 if (x0_in == 4'd0) x0=7'b0111111;//0
 if (x0_in == 4'd1) x0=7'b0000110;//1
 if (x0_in == 4'd2) x0=7'b1011011;//2
 if (x0_in == 4'd3) x0=7'b1001111;//3
 if (x0_in == 4'd4) x0=7'b1100110;//4
 if (x0_in == 4'd5) x0=7'b1101101;//5
 if (x0_in == 4'd6) x0=7'b1111101;//6
 if (x0_in == 4'd7) x0=7'b0100111;//7
 if (x0_in == 4'd8) x0=7'b1111111;//8
 if (x0_in == 4'd9) x0=7'b1100111;//9
 
if (x1_in == 4'd0) x1=7'b0111111;//0  
if (x1_in == 4'd1) x1=7'b0000110;//1  
if (x1_in == 4'd2) x1=7'b1011011;//2  
if (x1_in == 4'd3) x1=7'b1001111;//3  
if (x1_in == 4'd4) x1=7'b1100110;//4  
if (x1_in == 4'd5) x1=7'b1101101;//5  
if (x1_in == 4'd6) x1=7'b1111101;//6  
if (x1_in == 4'd7) x1=7'b0100111;//7  
if (x1_in == 4'd8) x1=7'b1111111;//8  
if (x1_in == 4'd9) x1=7'b1100111;//9  

if (x2_in == 4'd0) x2=7'b0111111;//0  
if (x2_in == 4'd1) x2=7'b0000110;//1  
if (x2_in == 4'd2) x2=7'b1011011;//2  
if (x2_in == 4'd3) x2=7'b1001111;//3  
if (x2_in == 4'd4) x2=7'b1100110;//4  
if (x2_in == 4'd5) x2=7'b1101101;//5  
if (x2_in == 4'd6) x2=7'b1111101;//6  
if (x2_in == 4'd7) x2=7'b0100111;//7  
if (x2_in == 4'd8) x2=7'b1111111;//8  
if (x2_in == 4'd9) x2=7'b1100111;//9 

if (x3_in == 4'd0) x3=7'b0111111;//0  
if (x3_in == 4'd1) x3=7'b0000110;//1  
if (x3_in == 4'd2) x3=7'b1011011;//2  
if (x3_in == 4'd3) x3=7'b1001111;//3  
if (x3_in == 4'd4) x3=7'b1100110;//4  
if (x3_in == 4'd5) x3=7'b1101101;//5  
if (x3_in == 4'd6) x3=7'b1111101;//6  
if (x3_in == 4'd7) x3=7'b0100111;//7  
if (x3_in == 4'd8) x3=7'b1111111;//8  
if (x3_in == 4'd9) x3=7'b1100111;//9  

if (x4_in == 4'd0) x4=7'b0111111;//0  
if (x4_in == 4'd1) x4=7'b0000110;//1  
if (x4_in == 4'd2) x4=7'b1011011;//2  
if (x4_in == 4'd3) x4=7'b1001111;//3  
if (x4_in == 4'd4) x4=7'b1100110;//4  
if (x4_in == 4'd5) x4=7'b1101101;//5  
if (x4_in == 4'd6) x4=7'b1111101;//6  
if (x4_in == 4'd7) x4=7'b0100111;//7  
if (x4_in == 4'd8) x4=7'b1111111;//8  
if (x4_in == 4'd9) x4=7'b1100111;//9  

if (x5_in == 4'd0) x5=7'b0111111;//0  
if (x5_in == 4'd1) x5=7'b0000110;//1  
if (x5_in == 4'd2) x5=7'b1011011;//2  
if (x5_in == 4'd3) x5=7'b1001111;//3  
if (x5_in == 4'd4) x5=7'b1100110;//4  
if (x5_in == 4'd5) x5=7'b1101101;//5  
if (x5_in == 4'd6) x5=7'b1111101;//6  
if (x5_in == 4'd7) x5=7'b0100111;//7  
if (x5_in == 4'd8) x5=7'b1111111;//8  
if (x5_in == 4'd9) x5=7'b1100111;//9  

if (x6_in == 4'd0) x6=7'b0111111;//0  
if (x6_in == 4'd1) x6=7'b0000110;//1  
if (x6_in == 4'd2) x6=7'b1011011;//2  
if (x6_in == 4'd3) x6=7'b1001111;//3  
if (x6_in == 4'd4) x6=7'b1100110;//4  
if (x6_in == 4'd5) x6=7'b1101101;//5  
if (x6_in == 4'd6) x6=7'b1111101;//6  
if (x6_in == 4'd7) x6=7'b0100111;//7  
if (x6_in == 4'd8) x6=7'b1111111;//8  
if (x6_in == 4'd9) x6=7'b1100111;//9  

if (x7_in == 4'd0) x7=7'b0111111;//0  
if (x7_in == 4'd1) x7=7'b0000110;//1  
if (x7_in == 4'd2) x7=7'b1011011;//2  
if (x7_in == 4'd3) x7=7'b1001111;//3  
if (x7_in == 4'd4) x7=7'b1100110;//4  
if (x7_in == 4'd5) x7=7'b1101101;//5  
if (x7_in == 4'd6) x7=7'b1111101;//6  
if (x7_in == 4'd7) x7=7'b0100111;//7  
if (x7_in == 4'd8) x7=7'b1111111;//8  
if (x7_in == 4'd9) x7=7'b1100111;//9   
end
end
 
 reg[6:0] Y_r;
 reg [7:0] DIG_r;
 assign Y= {1'b1,(~Y_r[6:0])};
 assign DIG= ~DIG_r;

always @ (posedge clk or negedge rst)
begin
 if(!rst)
   scan_cnt<=0;
 else begin
   scan_cnt <= scan_cnt + 1;
   if(scan_cnt == 3'd7) scan_cnt <= 0;  
 end
end

always @ (scan_cnt)
begin
  case(scan_cnt)
    3'b000: DIG_r=8'b0000_0001;
    3'b001: DIG_r=8'b0000_0010;
    3'b010: DIG_r=8'b0000_0100;
    3'b011: DIG_r=8'b0000_1000;
    3'b100: DIG_r=8'b0001_0000;
    3'b101: DIG_r=8'b0010_0000;
    3'b110: DIG_r=8'b0100_0000;
    3'b111: DIG_r=8'b1000_0000;
    default: DIG_r = 8'b0000_0000;
    endcase
end
    
always @ (scan_cnt) begin
	case(scan_cnt)
  		0:Y_r= x0;
  		1:Y_r= x1;
  		2:Y_r= x2;
  		3:Y_r= x3;
  		4:Y_r= x4;
  		5:Y_r= x5;
  		6:Y_r= x6;
  		7:Y_r= x7;
  		default : Y_r= 7'b0000000;
  	endcase
end
endmodule
```

<img src="/Users/lee/Library/Application Support/typora-user-images/截屏2021-12-29 下午9.07.35.png" alt="截屏2021-12-29 下午9.07.35" style="zoom:33%;" />

<img src="/Users/lee/Library/Application Support/typora-user-images/截屏2021-12-29 下午9.07.54.png" alt="截屏2021-12-29 下午9.07.54" style="zoom:33%;" />

