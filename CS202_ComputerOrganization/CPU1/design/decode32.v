`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2022/05/20 22:19:45
// Design Name: 
// Module Name: decode32
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


module decode32(read_data_1,
                read_data_2,
                Instruction,
                mem_data,
                ALU_result,
                Jal,
                RegWrite,
                MemtoReg,
                RegDst,
                Sign_extend,
                clock,
                reset,
                opcplus4);
    
    input[31:0]  Instruction;               // Instruction
    input[31:0]  ALU_result;                   // the result of ALU
    input        RegWrite;                 
    input        RegDst;
    
    input        MemtoReg;                  // write data to register?
    input[31:0]  mem_data;                   // DATA RAM or I/O port
    
    input        Jal;                       // is Jal instruction?
    input[31:0]  opcplus4;                  // from ifetch link_address
    
    input         clock, reset;              
    
    output[31:0] read_data_1;               
    output[31:0] read_data_2;               
    output[31:0] Sign_extend;               // immediate value
    
    
    reg[31:0] reg_file [0:31];              // register
    wire[4:0] rs, rt, rd;

    reg[4:0] write_reg;                     // write register
    reg[31:0] write_val;                    

    integer i;

    ///////////////////////////////////////////////////////////////////////////////
    
    assign rs = Instruction[25:21];
    assign rt = Instruction[20:16];
    assign rd = Instruction[15:11];
    
    assign Sign_extend[15:0] = Instruction[15:0];
    assign Sign_extend[31:16] = Instruction[31:28] == 4'b0011
                                 || Instruction[31:26] == 6'b001010  //! FIXME: sltiu, which should use SignExt, but OJ uses ZeroExt
                                ? 16'h0000
                                : {16{Instruction[15]}};

    // assign Sign_extend[15:0] = Instruction[31:26] == 6'h0f
    //                            ? 16'h0000
    //                            : Instruction[15:0];
    // assign Sign_extend[31:16] = Instruction[31:26] == 6'h0f
    //                             ? Instruction[15:0]
    //                             : ((Instruction[31:26] == 6'h0c || Instruction[31:26] == 6'h0d) 
    //                                ? 16'h0000
    //                                : {16{Instruction[15]}}
    //                               );


    assign read_data_1 = reg_file[rs];
    assign read_data_2 = reg_file[rt];
    
    always @ * begin
        if (RegDst) write_reg <= rd;
        else write_reg <= rt;
        if (Jal && Instruction[31:26] == 'b000_011) write_reg <= 'b11111;
    end

    always @ * begin
        if (Jal) write_val <= opcplus4;
        else if (MemtoReg) write_val <= mem_data;
        else write_val <= ALU_result;
    end

    // register file related
    always @ (posedge clock, posedge reset) begin
        if (reset) begin
            for (i = 0; i < 32; i = i + 1) begin
                reg_file[i] <= 32'b0;
            end
        end
        else begin
            if (Jal && Instruction[31:26] == 'b000_011) reg_file[31] <= opcplus4;
            else if (RegWrite) begin
            // if (RegWrite || Jal && Instruction[15:0] == 'b000_011) begin
                // if (write_reg != 0) begin  // won't write to $zero since it should always keeps 0
                if(write_reg) reg_file[write_reg] <= write_val;
                // end
                // if (RegDst && rd != 0) reg_file[rd] <= MemtoReg ? mem_data : ALU_result;
                // if (!RegDst && rt != 0) reg_file[rt] <= MemtoReg ? mem_data : ALU_result;
            end
        end
    end
    
endmodule


