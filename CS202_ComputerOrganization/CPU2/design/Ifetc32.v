`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2022/05/20 22:22:49
// Design Name: 
// Module Name: Ifetc32
// Project Name: 
// Target Devices: 
// Tool Versions: 
// Description: 
// 
// Dependencies: 
// 
// Revision:
// Revision 0.01 - File Created
// Additional Comments:
// 
//////////////////////////////////////////////////////////////////////////////////


module Ifetc32(Instruction_i,branch_base_addr,Addr_result,Read_data_1,Branch,nBranch,Jmp,Jal,Jr,Comp,Zero,clock,reset,link_addr, rom_adr_o, Instruction_o);
    input[31:0] Instruction_i;			// the instruction
    output[31:0] branch_base_addr;      // branch offset base on PC + 4
    input[31:0]  Addr_result;            // from ALU
    input[31:0]  Read_data_1;           // from decoder
    input        Branch;                // is beq
    input        nBranch;               // is bne
    input        Jmp;                  // is j
    input        Jal;                   // is jal
    input        Jr;                  // is jr
    input        Comp;                  //from alu, 1 means bgez or blez is true
    input        Zero;                 //whether alu result is zero
    input        clock,reset;           
  
    output   [31:0] link_addr;              
    output   [13:0] rom_adr_o;
    output   [31:0]Instruction_o;
    
    reg[31:0] PC, Next_PC;
    reg [31:0] link_addr_tmp;
    
 //zz   assign Instruction = 32'h0000_00a0;
    
//    prgrom instmem( 
//       .clka(clock),
//       .addra(PC[15:2]), 
//       .douta(Instruction)
//    );
    
    assign branch_base_addr = PC + 4;
    assign link_addr = link_addr_tmp;
    assign Instruction_o = Instruction_i; //
    assign  rom_adr_o = PC[15:2];
    
    always@ *begin
       if( ((Branch == 1) && (Zero == 1)) || ((nBranch == 1) && (Zero == 0)) || (((Instruction_i[31:26] == 6'b000110) || Instruction_i[31:26] == 6'b000111) && Comp))
            begin
                 Next_PC = Addr_result;
            end
       else if(Jr == 1)
           begin                          
                Next_PC = Read_data_1;
           end     
       else  Next_PC = branch_base_addr;
    end
    
     always @(negedge clock or posedge reset) begin
        if(reset == 1)
            begin
               PC <= 32'h0000_0000;
             end
        else begin
           if (Jmp == 1) begin
               PC <= {PC[31:28], Instruction_i[25:0], 2'b0};
           end
           else if (Jal == 1) begin
               link_addr_tmp <= Next_PC;
               PC <= {PC[31:28], Instruction_i[25:0], 2'b0};   
           end
           else begin
               PC <= Next_PC;
           end    
        end
     end
     
endmodule
