# DD_HW1

## Task1

### Task1_designfile

```verilog
module UnsignedAddition(addend, augend, sum);
parameter WIDTH =1;
input [WIDTH-1:0]addend,augend; 
output [WIDTH:0] sum;
assign sum=addend+augend;
endmodule
```

### Truth_table_1bit

| addend | augend |  sum  |
| :----: | :----: | :---: |
|   0    |   0    |   0   |
|   1    |   0    |   1   |
|   0    |   1    |   1   |
|   1    |   1    | 2(10) |

### Truth_table_2bit

| addend | augend |  sum   |
| :----: | :----: | :----: |
|   00   |   00   |   00   |
|   00   |   01   |   01   |
|   00   |   10   | 2(10)  |
|   00   |   11   | 3(11)  |
|   01   |   00   |   01   |
|   01   |   01   | 2(10)  |
|   01   |   10   | 3(11)  |
|   01   |   11   | 4(100) |
|   10   |   00   | 2(10)  |
|   10   |   01   | 3(11)  |
|   10   |   10   | 4(100) |
|   10   |   11   | 5(101) |
|   11   |   00   | 3(11)  |
|   11   |   01   | 4(100) |
|   11   |   10   | 5(101) |
|   11   |   11   | 6(110) |

### Task1_1bit_sim

```verilog
module UnsignedAddition_sim();
    reg[0:0] A, B;
    wire[1: 0] C;
    UnsignedAddition Add(A, B, C);
    initial
    begin
    A = 'b0;B = 'b0;
    #10 A = 'b1;B = 'b0;
    #10 A = 'b0;B = 'b1;
    #10 A = 'b1;B = 'b1;
    end;
    initial #40 $finish;
endmodule;
```

![tak1_1bit_waveform](/Users/lee/Desktop/tak1_1bit_waveform.png)

### Task1_2bit_sim

```verilog
module UnsignedAddition_sim();
    reg[1:0] A, B;
    wire[2: 0] C;
    UnsignedAddition #(2)Add(A, B, C);
    initial
    begin
    A = 'b00;B = 'b00;
    #10 A = 'b00;B = 'b01;
    #10 A = 'b00;B = 'b10;
    #10 A = 'b00;B = 'b11;
    #10 A = 'b01;B = 'b00;
    #10 A = 'b01;B = 'b01;
    #10 A = 'b01;B = 'b10;
    #10 A = 'b01;B = 'b11;
    #10 A = 'b10;B = 'b00;
    #10 A = 'b10;B = 'b01;
    #10 A = 'b10;B = 'b10;
    #10 A = 'b10;B = 'b11;
    #10 A = 'b11;B = 'b00;
    #10 A = 'b11;B = 'b01;
    #10 A = 'b11;B = 'b10;
    #10 A = 'b11;B = 'b11;
    end;
    initial #160 $finish;
endmodule;
```

![task1_2bit_waveform](/Users/lee/Desktop/task1_2bit_waveform.png)

## Task2

### (A)

#### distributive1bit_df.v

```verilog
`timescale 1ns / 1ps
module distributive1bit_df(A,B,C,D,E);
    parameter X = 1;
    input[X-1:0] A;
    input[X-1:0] B;
    input[X-1:0] C;
    output[X-1:0] D;
    output[X-1:0] E;
    assign D = A & (B | C);
    assign E = (A & B) | (A & C); 
endmodule
```

#### distributive2bit_df.v

```verilog
`timescale 1ns / 1ps
module distributive2bit_df(A,B,C,D,E);
    parameter X = 2;
    input[X-1:0] A;
    input[X-1:0] B;
    input[X-1:0] C;
    output[X-1:0] D;
    output[X-1:0] E;
    assign D = A & (B | C);
    assign E = (A & B) | (A & C); 
endmodule
```

#### distributive1bit_sd.v

```verilog
`timescale 1ns / 1ps
module distributive1bit_sd(A,B,C,D,E);
    parameter X = 1;
    input[X - 1:0]A, B, C;
    output[X - 1:0]D, E;
    wire[X - 1: 0] tmp1, tmp2, tmp3;
    or or1(tmp1, B, C);
    and and1(D, A, tmp1);
    and and2(tmp2, A, B);
    and and3(tmp3, A, C);
    or or2(E, tmp2, tmp3);
endmodule
```

#### distributive2bit_sd.v

```verilog
`timescale 1ns / 1ps
module distributive2bit_sd(A,B,C,D,E);
    parameter X = 2;
    input[X - 1:0]A, B, C;
    output[X - 1:0]D, E;
    wire[X - 1: 0] tmp1, tmp2, tmp3;
    or or1(tmp1[0], B[0], C[0]);
    or or2(tmp1[1], B[1], C[1]);
    and and1(D[0], A[0], tmp1[0]);
    and and2(D[1], A[1], tmp1[1]);
    and and3(tmp2[0], A[0], B[0]);
    and and4(tmp2[1], A[1], B[1]);
    and and5(tmp3[0], A[0], C[0]);
    and and6(tmp3[1], A[1], C[1]);
    or or3(E[0],tmp2[0], tmp3[0]);
    or or4(E[1],tmp2[1], tmp3[1]);
endmodule
```

#### Truth_table_1bit

| A    | B    | C    | D    | E    |
| :--- | :--- | :--- | :--- | :--- |
| 0    | 0    | 0    | 0    | 0    |
| 0    | 0    | 1    | 0    | 0    |
| 0    | 1    | 0    | 0    | 0    |
| 0    | 1    | 1    | 0    | 0    |
| 1    | 0    | 0    | 0    | 0    |
| 1    | 0    | 1    | 1    | 1    |
| 1    | 1    | 0    | 1    | 1    |
| 1    | 1    | 1    | 1    | 1    |

#### Truth_table_2bit

| A    | B    | C    | D    | E    |
| :--- | :--- | :--- | :--- | :--- |
| 0    | 0    | 0    | 0    | 0    |
| 0    | 0    | 1    | 0    | 0    |
| 0    | 0    | 2    | 0    | 0    |
| 0    | 0    | 3    | 0    | 0    |
| 0    | 1    | 0    | 0    | 0    |
| 0    | 1    | 1    | 0    | 0    |
| 0    | 1    | 2    | 0    | 0    |
| 0    | 1    | 3    | 0    | 0    |
| 0    | 2    | 0    | 0    | 0    |
| 0    | 2    | 1    | 0    | 0    |
| 0    | 2    | 2    | 0    | 0    |
| 0    | 2    | 3    | 0    | 0    |
| 0    | 3    | 0    | 0    | 0    |
| 0    | 3    | 1    | 0    | 0    |
| 0    | 3    | 2    | 0    | 0    |
| 0    | 3    | 3    | 0    | 0    |
| 1    | 0    | 0    | 0    | 0    |
| 1    | 0    | 1    | 1    | 1    |
| 1    | 0    | 2    | 0    | 0    |
| 1    | 0    | 3    | 1    | 1    |
| 1    | 1    | 0    | 1    | 1    |
| 1    | 1    | 1    | 1    | 1    |
| 1    | 1    | 2    | 1    | 1    |
| 1    | 1    | 3    | 1    | 1    |
| 1    | 2    | 0    | 0    | 0    |
| 1    | 2    | 1    | 1    | 1    |
| 1    | 2    | 2    | 0    | 0    |
| 1    | 2    | 3    | 1    | 1    |
| 1    | 3    | 0    | 1    | 1    |
| 1    | 3    | 1    | 1    | 1    |
| 1    | 3    | 2    | 1    | 1    |
| 1    | 3    | 3    | 1    | 1    |
| 2    | 0    | 0    | 0    | 0    |
| 2    | 0    | 1    | 0    | 0    |
| 2    | 0    | 2    | 2    | 2    |
| 2    | 0    | 3    | 2    | 2    |
| 2    | 1    | 0    | 0    | 0    |
| 2    | 1    | 1    | 0    | 0    |
| 2    | 1    | 2    | 2    | 2    |
| 2    | 1    | 3    | 2    | 2    |
| 2    | 2    | 0    | 2    | 2    |
| 2    | 2    | 1    | 2    | 2    |
| 2    | 2    | 2    | 2    | 2    |
| 2    | 2    | 3    | 2    | 2    |
| 2    | 3    | 0    | 2    | 2    |
| 2    | 3    | 1    | 2    | 2    |
| 2    | 3    | 2    | 2    | 2    |
| 2    | 3    | 3    | 2    | 2    |
| 3    | 0    | 0    | 0    | 0    |
| 3    | 0    | 1    | 1    | 1    |
| 3    | 0    | 2    | 2    | 2    |
| 3    | 0    | 3    | 3    | 3    |
| 3    | 1    | 0    | 1    | 1    |
| 3    | 1    | 1    | 1    | 1    |
| 3    | 1    | 2    | 3    | 3    |
| 3    | 1    | 3    | 3    | 3    |
| 3    | 2    | 0    | 2    | 2    |
| 3    | 2    | 1    | 3    | 3    |
| 3    | 2    | 2    | 2    | 2    |
| 3    | 2    | 3    | 3    | 3    |
| 3    | 3    | 0    | 3    | 3    |
| 3    | 3    | 1    | 3    | 3    |
| 3    | 3    | 2    | 3    | 3    |
| 3    | 3    | 3    | 3    | 3    |

#### Task2_a_1bit_sim

```verilog
`timescale 1ns / 1ps
module distributive1bit_df_sim();
    reg[0:0] A,B,C;
    wire[0:0] D,E;
    distributive1bit_df Dis(A,B,C,D,E);
    //or distributive1bit_sd Dis(A,B,C,D,E);
    initial
    begin
    A = 'b0;B = 'b0;C = 'b0;
    #10 A = 'b1;B = 'b0;C = 'b0;
    #10 A = 'b0;B = 'b1;C = 'b0;
    #10 A = 'b1;B = 'b1;C = 'b0;
    #10 A = 'b0;B = 'b0;C = 'b1;
    #10 A = 'b1;B = 'b0;C = 'b1;
    #10 A = 'b0;B = 'b1;C = 'b1;
    #10 A = 'b1;B = 'b1;C = 'b1;
    end;
    initial #80 $finish;
endmodule
```

![task2_1_1bit_df](/Users/lee/Desktop/task2_1_1bit_df.png)

#### Task2_a_2bit_sim

```verilog
`timescale 1ns / 1ps
module distributive1bit_df_sim();
    reg[1:0] A,B,C;
    wire[1:0] D,E;
    distributive2bit_df #(2)Dis(A,B,C,D,E);
    //or distributive2bit_sd #(2)Dis(A,B,C,D,E);
    initial
    begin
    A = 'b00;B = 'b00;C = 'b00;
    while({A,B,C} < 'b111111)begin
        #10 {A,B,C} = {A,B,C} + 1;
        end
    end
    initial #640 $finish;
endmodule
```

![task2_a_2bit](/Users/lee/Desktop/task2_a_2bit.png)

### (B)

#### distributive1bit_df.v

```verilog
`timescale 1ns / 1ps
module distributive1bit_df(A,B,C,D,E);
    parameter X = 1;
    input[X - 1:0]A, B, C;
    output[X - 1:0]D, E;
    assign D = A | (B & C);
    assign E = (A | B) & (A | C);
endmodule
```

#### distributive2bit_df.v

```verilog
`timescale 1ns / 1ps
module distributive2bit_df(A,B,C,D,E);
    parameter X = 2;
    input[X - 1:0]A, B, C;
    output[X - 1:0]D, E;
    assign D = A | (B & C);
    assign E = (A | B) & (A | C);
endmodule
```

#### distributive1bit_sd.v

```verilog
`timescale 1ns / 1ps
module distributive1bit_sd(A,B,C,D,E);
    parameter X = 1;
    input[X - 1:0]A, B, C;
    output[X - 1:0]D, E;
    wire[X - 1: 0] tmp1, tmp2, tmp3;
    and and1(tmp1, B, C);
    or or1(D,A, tmp1);
    or or2(tmp2, A, B);
    or or3(tmp3, A, C);
    and and2(E, tmp2, tmp3);
endmodule
```

#### distributive2bit_sd.v

```verilog
`timescale 1ns / 1ps
module distributive2bit_sd(A,B,C,D,E);
    parameter X = 2;
    input[X - 1:0]A, B, C;
    output[X - 1:0]D, E;
    wire[X - 1: 0] tmp1, tmp2, tmp3;
    and and1(tmp1[0], B[0], C[0]);
    and and2(tmp1[1], B[1], C[1]);
    or or1(D[0],A[0], tmp1[0]);
    or or2(D[1],A[1], tmp1[1]);
    or or3(tmp2[0], A[0], B[0]);
    or or4(tmp2[1], A[1], B[1]);
    or or5(tmp3[0], A[0], C[0]);
    or or6(tmp3[1], A[1], C[1]);
    and and3(E[0], tmp2[0], tmp3[0]);
    and and4(E[1], tmp2[1], tmp3[1]);
endmodule
```

#### Truth_table_1bit

| A    | B    | C    | D    | E    |
| :--- | :--- | :--- | :--- | :--- |
| 0    | 0    | 0    | 0    | 0    |
| 0    | 0    | 1    | 0    | 0    |
| 0    | 1    | 0    | 0    | 0    |
| 0    | 1    | 1    | 1    | 1    |
| 1    | 0    | 0    | 1    | 1    |
| 1    | 0    | 1    | 1    | 1    |
| 1    | 1    | 0    | 1    | 1    |
| 1    | 1    | 1    | 1    | 1    |

#### Truth_table_2bit

| A    | B    | C    | D    | E    |
| :--- | :--- | :--- | :--- | :--- |
| 0    | 0    | 0    | 0    | 0    |
| 0    | 0    | 1    | 0    | 0    |
| 0    | 0    | 2    | 0    | 0    |
| 0    | 0    | 3    | 0    | 0    |
| 0    | 1    | 0    | 0    | 0    |
| 0    | 1    | 1    | 1    | 1    |
| 0    | 1    | 2    | 0    | 0    |
| 0    | 1    | 3    | 1    | 1    |
| 0    | 2    | 0    | 0    | 0    |
| 0    | 2    | 1    | 0    | 0    |
| 0    | 2    | 2    | 2    | 2    |
| 0    | 2    | 3    | 2    | 2    |
| 0    | 3    | 0    | 0    | 0    |
| 0    | 3    | 1    | 1    | 1    |
| 0    | 3    | 2    | 2    | 2    |
| 0    | 3    | 3    | 3    | 3    |
| 1    | 0    | 0    | 1    | 1    |
| 1    | 0    | 1    | 1    | 1    |
| 1    | 0    | 2    | 1    | 1    |
| 1    | 0    | 3    | 1    | 1    |
| 1    | 1    | 0    | 1    | 1    |
| 1    | 1    | 1    | 1    | 1    |
| 1    | 1    | 2    | 1    | 1    |
| 1    | 1    | 3    | 1    | 1    |
| 1    | 2    | 0    | 1    | 1    |
| 1    | 2    | 1    | 1    | 1    |
| 1    | 2    | 2    | 3    | 3    |
| 1    | 2    | 3    | 3    | 3    |
| 1    | 3    | 0    | 1    | 1    |
| 1    | 3    | 1    | 1    | 1    |
| 1    | 3    | 2    | 3    | 3    |
| 1    | 3    | 3    | 3    | 3    |
| 2    | 0    | 0    | 2    | 2    |
| 2    | 0    | 1    | 2    | 2    |
| 2    | 0    | 2    | 2    | 2    |
| 2    | 0    | 3    | 2    | 2    |
| 2    | 1    | 0    | 2    | 2    |
| 2    | 1    | 1    | 3    | 3    |
| 2    | 1    | 2    | 2    | 2    |
| 2    | 1    | 3    | 3    | 3    |
| 2    | 2    | 0    | 2    | 2    |
| 2    | 2    | 1    | 2    | 2    |
| 2    | 2    | 2    | 2    | 2    |
| 2    | 2    | 3    | 2    | 2    |
| 2    | 3    | 0    | 2    | 2    |
| 2    | 3    | 1    | 3    | 3    |
| 2    | 3    | 2    | 2    | 2    |
| 2    | 3    | 3    | 3    | 3    |
| 3    | 0    | 0    | 3    | 3    |
| 3    | 0    | 1    | 3    | 3    |
| 3    | 0    | 2    | 3    | 3    |
| 3    | 0    | 3    | 3    | 3    |
| 3    | 1    | 0    | 3    | 3    |
| 3    | 1    | 1    | 3    | 3    |
| 3    | 1    | 2    | 3    | 3    |
| 3    | 1    | 3    | 3    | 3    |
| 3    | 2    | 0    | 3    | 3    |
| 3    | 2    | 1    | 3    | 3    |
| 3    | 2    | 2    | 3    | 3    |
| 3    | 2    | 3    | 3    | 3    |
| 3    | 3    | 0    | 3    | 3    |
| 3    | 3    | 1    | 3    | 3    |
| 3    | 3    | 2    | 3    | 3    |
| 3    | 3    | 3    | 3    | 3    |

#### Task2_b_1bit_sim

```verilog
`timescale 1ns / 1ps
module distributive1bit_sd_sim();
    reg[0:0] A, B, C;
    wire[0:0]D, E;
    distributive1bit_sd dis(A, B, C, D, E);
    //or distributive1bit_df dis(A, B, C, D, E);
    initial
    begin
    A = 'b0;B = 'b0;C = 'b0;
        while({A, B, C}<'b111)begin
        #10 {A, B, C} = {A, B, C} + 1;
        end
    end
    initial #80 $finish;
endmodule
```

![task2_b_1bit](/Users/lee/Desktop/task2_b_1bit.png)

#### Task2_b_2bit_sim

```verilog
`timescale 1ns / 1ps
module distributive1bit_sd_sim();
    reg[1:0] A, B, C;
    wire[1:0]D, E;
    distributive2bit_sd dis(A, B, C, D, E);
    //or distributive2bit_df dis(A, B, C, D, E);
    initial
    begin
    A = 'b0;B = 'b0;C = 'b0;
        while({A, B, C}<'b111111)begin
        #10 {A, B, C} = {A, B, C} + 1;
        end
    end
    initial #640 $finish;
endmodule
```

![task2_b_2bit](/Users/lee/Desktop/task2_b_2bit.png)



