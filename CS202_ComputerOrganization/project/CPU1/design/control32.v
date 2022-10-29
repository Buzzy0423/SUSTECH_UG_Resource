`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2022/05/20 22:16:00
// Design Name: 
// Module Name: control32
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


/**
 * Base on the MINISYS ISA which has differences in the instruction encoding to MIPS32
 */
module control32(Opcode,
                 Function_opcode,
                 Alu_resultHigh,
                 Branch,
                 nBranch,
                 Jr,
                 Jmp,
                 Jal,
                 ALUSrc,
                 ALUOp,
                 MemWrite,
                 MemRead,
                 IORead,
                 IOWrite,
                 RegWrite,
                 RegDST,
                 MemorIOtoReg,
                 I_format,
                 Sftmd);
    
    input[5:0]   Opcode;            // instruction[31..26] from Ifetch, 6 bits opcode
    input[5:0]   Function_opcode;  	// instruction[5..0] from Ifetch, 6bits function code
    input[21:0]  Alu_resultHigh;     // From the Alu unit Alu_Result[31..10] 
    
    output       Branch;            // 1 indicate the instruction is "beq" , otherwise it's not 
    output       nBranch;           // 1 indicate the instruction is "bne", otherwise it's not
    
    output       Jr;         	    // 1 indicate the instruction is "jr", other wise it's not
    output       Jmp;               // 1 indicate the instruction is "j", otherwise it's not  
    output       Jal;               // 1 indicate the instruction is "jal", otherwise it's not
    
    output       ALUSrc;            // 1 indicate the 2nd data is immidiate (except "beq","bne") 
    
    output[1:0]  ALUOp;             // if the instruction is R-type or I_format, ALUOp is 2'b10; if the instruction is“beq” or “bne“, ALUOp is 2'b01； if the instruction is“lw” or “sw“, ALUOp is 2'b00；
    
    output       MemWrite;          // 1 indicate write data memory, otherwise it's not 
    output       MemRead;           // 1 indicates that the instruction needs to read from the memory 

    output       IORead;            // 1 indicates I/O read
    output       IOWrite;           // 1 indicates I/O write 
    
    output       RegWrite;    	    // 1 indicates that the instruction needs to write to the register
    output       RegDST;            // 1 indicate destination register is "rd"(R),otherwise it's "rt"(I)
    output       MemorIOtoReg;      // 1 indicates that data needs to be read from memory or I/O to the register
    
    output       I_format;          //  1 indicate the instruction is I-type but isn't "beq","bne","LW" or "SW"
    output       Sftmd;             //  1 indicate the instruction is shift instruction;
    
    
    wire R_format, sw, lw;
    
    ///////////////////////////////////////////////////////////////////////////////
    
    assign R_format = Opcode == 'b000_000;
    assign I_format = Opcode[5:3] == 'b001;  // ignore all branch / load / store

    assign sw = Opcode == 'b101_011;
    assign lw = Opcode == 'b100_011;
    
    assign Jmp = Opcode == 'b000_010;  // J-format
    assign Jal = Opcode == 'b000_011;  // J-format
    assign Jr  = R_format && (Function_opcode == 'b001_000);
    
    assign Branch  = Opcode == 'b000_100;
    assign nBranch = Opcode == 'b000_101;
    
    assign ALUOp = {R_format || I_format, Branch || nBranch};
    
    // only 'sw' for MINISYS
    assign MemWrite =((sw==1) && (Alu_resultHigh[21:0] != 22'h3FFFFF)) ? 1'b1:1'b0;
    assign MemRead = ((lw==1) && (Alu_resultHigh[21:0] != 22'h3FFFFF)) ? 1'b1:1'b0;

    assign IOWrite = ((sw==1) && (Alu_resultHigh[21:0] == 22'h3FFFFF)) ? 1'b1:1'b0;
    assign IORead = ((lw==1) && (Alu_resultHigh[21:0] == 22'h3FFFFF)) ? 1'b1:1'b0;
    
    assign ALUSrc = I_format || Opcode[5] == 'b1;  // + load / store
    
    // sll, srl, sra, sllv, srlv, srav
    assign Sftmd = R_format && (Function_opcode[5:3] == 'b000);
    
    // R-formats -> rd, // but jr/div/divu/mult/multu don't
    assign RegDST = R_format;
    
    assign RegWrite = R_format && !Jr || I_format || Jal || Opcode == 'b100_011;  // lw

    
    // lwc1, ldc1 -> Opcode = 0x31 / 0x35
    // lbu, lhu, ll, lw -> 0x23, 0x24, 0x25, 0x30
    assign MemorIOtoReg = IORead || MemRead;
endmodule

