`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2022/05/20 22:19:17
// Design Name: 
// Module Name: executs32
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


module executs32(Read_data_1,Read_data_2,Sign_extend,Function_opcode,Exe_opcode,ALUOp,
                 Shamt,ALUSrc,I_format,Zero,Comp,Jr,Sftmd,ALU_Result,Addr_Result,PC_plus_4,
                 HI
                 );
    input[31:0]  Read_data_1;		// Read data 1
    input[31:0]  Read_data_2;		// Read data 2
    input[31:0]  Sign_extend;		// Sign extend in 32 bit
    input[5:0]   Function_opcode;  	// instructions[5:0]
    input[5:0]   Exe_opcode;  		// instruction[31:26]
    input[1:0]   ALUOp;             // { (R_format || I_format) , (Branch || nBranch) }
    input[4:0]   Shamt;             // instruction[10:6], the amount of shift bits
    input  		 Sftmd;            // 1 means this is a shift instruction
    input        ALUSrc;            // 1 means the 2nd operand is an immedite (except beq，bne）
    input        I_format;          // 1 means I-Type instruction except beq, bne, LW, SW
    input        Jr;               // 1 means this is a jr instruction 
    input[31:0]  PC_plus_4;         // PC+4
    output       Zero;              // 1 means the ALU_output_mux is zero, 0 otherwise
    output       Comp;              // 1 is true for blez, bgtz instruction 
    output reg[31:0] ALU_Result;        // the ALU calculation result
    output[31:0] Addr_Result;		// the calculated instruction address 
    output reg[31:0] HI;                //Hi 32-bit of mult / div      
    

    wire[31:0] Ainput,Binput;       // two operands for calculation
    wire[5:0] Exe_code;             // use to generate ALU_ctrl. (I_format==0) ? Function_opcode : { 3'b000 , Opcode[2:0] };
    wire[2:0] ALU_ctl;              // the control signals which affact operation in ALU directely
    wire[2:0] Sftm;                 // identify the types of shift instruction, equals to Function_opcode[2:0]
    wire times;
    wire[3:0] ALU_coctl;
    reg[31:0] Shift_Result;         // the result of shift operation
    reg[31:0] ALU_output_mux;
    reg[31:0] ALU_output_comux;
    reg[63:0] mul;
    reg[31:0] hi;
    wire[32:0] Branch_Addr;         // the calculated address of the instruction, Addr_Result is Branch_Addr[31:0]
   

    assign Ainput = Read_data_1;
    assign Binput = (ALUSrc == 0) ? Read_data_2 : Sign_extend[31:0];    //Immediate or not
    
    assign Sftm = Function_opcode[2:0];
    assign Exe_code = (I_format == 0) ? Function_opcode : {3'b000, Exe_opcode[2:0]};
    assign ALU_ctl[0] = (Exe_code[0] | Exe_code[3]) & ALUOp[1];
    assign ALU_ctl[1] = ((!Exe_code[2]) | (!ALUOp[1]));
    assign ALU_ctl[2] = (Exe_code[1] & ALUOp[1]) | ALUOp[0];
    assign times = (Function_opcode[5:4] == 2'b01);
    assign ALU_coctl = Function_opcode[3:0];
    assign Zero = (ALU_output_mux[31:0] == 0) ? 1'b1 : 1'b0;
    assign Branch_Addr = PC_plus_4 + (Sign_extend << 2);
    assign Addr_Result = Branch_Addr[31:0];
    assign Comp = (((Exe_opcode == 6'b000110) && ((Ainput[31] == 1'b1) || Ainput == 0)) || ((Exe_opcode == 6'b000111) && ((Ainput[31] == 1'b0) && (Ainput != 0))));

    always @(times or ALU_coctl or Ainput or Binput) begin
        case (ALU_coctl)
            4'b0000:begin
                ALU_output_comux = 32'b0;
                hi = Ainput;
            end
            4'b0001:begin
                ALU_output_comux = 32'b0;
                hi = Ainput;
            end
            4'b0010:begin 
                ALU_output_comux = Ainput;
                hi = 32'b0;
            end
            4'b0011:begin 
                ALU_output_comux = Ainput;
                hi = 32'b0;
            end
            4'b1000: begin
                mul = $signed(Ainput) * $signed(Binput);
                ALU_output_comux = mul[31:0];
                hi = mul[63:32];
            end
            4'b1001: begin
                mul = $unsigned(Ainput) * $unsigned(Binput);
                ALU_output_comux = mul[31:0];
                hi = mul[63:32];
            end
            4'b1010: begin
                ALU_output_comux = $signed(Ainput) / $signed(Binput);
                hi = $signed(Ainput) % $signed(Binput);
            end
            4'b1011: begin
                ALU_output_comux = $unsigned(Ainput) / $unsigned(Binput);
                hi = $unsigned(Ainput) % $unsigned(Binput);
            end
            default: begin
                ALU_output_comux = 0;
                hi = 0;
            end
        endcase
    end
    

    always @(ALU_ctl or Ainput or Binput) begin
        case (ALU_ctl)
            3'b000: ALU_output_mux =  Ainput & Binput;
            3'b001: ALU_output_mux =  Ainput | Binput;
            3'b010: ALU_output_mux =  Ainput + Binput;
            3'b011: ALU_output_mux =  Ainput + Binput;
            3'b100: ALU_output_mux =  Ainput ^ Binput;
            3'b101: ALU_output_mux =  ~(Ainput | Binput);
            3'b110: ALU_output_mux =  Ainput - Binput;
            3'b111: ALU_output_mux =  Ainput - Binput;
            default: ALU_output_mux = 32'h00000000;
        endcase    
    end

    always @* begin
        if(Sftmd) begin
            case (Sftm[2:0])
                3'b000: Shift_Result = Binput << Shamt;     //Sll rd,rt,shamt 
                3'b010: Shift_Result = Binput >> Shamt;     //Srl rd,rt,shamt
                3'b100: Shift_Result = Binput << Ainput;    //Sllv rd,rt,rs
                3'b110: Shift_Result = Binput >> Ainput;    //Srlv rd,rt,rs
                3'b011: Shift_Result = $signed(Binput) >>> Shamt;    //Sra rd,rt,shamt
                3'b111: Shift_Result = $signed(Binput) >>> Ainput;   //Srav rd,rt,rs
                default: Shift_Result = Binput;
            endcase
        end
        else begin
            Shift_Result = Binput;
        end
    end

//    always @* begin
//        //set type operation (slt, slti, sltu, sltiu)
//        if(((ALU_ctl == 3'b111) && (Exe_code[3] == 1)) || ((I_format == 1) && (ALU_ctl[2:1] == 2'b11))) begin
//            ALU_Result = ($signed(Ainput) < $signed(Binput)) ? 1 : 0;
//        end
//        else if((ALU_ctl == 3'b101) && (I_format == 1)) begin
//            ALU_Result[31:0] = {Binput[15:0], 16'b0};
//        end
//        else if(Sftmd == 1) begin
//            ALU_Result = Shift_Result;
//        end
//        else begin
//            ALU_Result = ALU_output_mux[31:0];
//        end
//    end
  always @* begin
        //set type operation (slt, slti, sltu, sltiu)
        if(times) begin
            ALU_Result = ALU_output_comux;
            HI = hi;
        end
        else if(((ALU_ctl == 3'b111) && (Exe_code[3] == 1) && (Exe_code[0] == 0)) || ((I_format == 1) && (ALU_ctl[2:0] == 3'b110))) begin
            ALU_Result = ($signed(Ainput) < $signed(Binput)) ? 1 : 0;
            HI = 0;
        end
        else if(((I_format == 1) && (ALU_ctl[2:0] == 3'b111)) || ((ALU_ctl == 3'b111) && (Exe_code[3] == 1) && (Exe_code[0] == 1)))begin
            ALU_Result = (Ainput < Binput) ? 1 : 0;
            HI = 0;
        end
        else if((ALU_ctl == 3'b101) && (I_format == 1)) begin
            ALU_Result[31:0] = {Binput[15:0], 16'b0};
            HI = 0;
        end
        else if(Sftmd == 1) begin
            ALU_Result = Shift_Result;
            HI = 0;
        end
        else begin
            ALU_Result = ALU_output_mux[31:0];
            HI = 0;
        end
    end

endmodule
