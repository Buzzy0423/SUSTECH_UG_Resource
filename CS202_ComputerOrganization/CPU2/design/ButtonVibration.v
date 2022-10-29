module ButtonVibration (
    input clk,
    input rst,
    input buttoncs,
    input button,
    output buttonout
);
    reg[19:0] cnt0;
    reg flag0;
 //   wire buttonEnable;
    wire bclk;
    
    reg [19:0] count;
    //wire key_clk;
     
    always @ (posedge clk or posedge rst)
      if (rst)
        count <= 0;
      else 
        count <= count + 1'b1;
        
    assign bclk = count[19]; 
    
    assign buttonout=flag0 && buttoncs;
    
 //   assign buttonEnable = button[4] || button[3] || button[2] || button[1] || button[0]; // if any button is pressed
    
    always@(posedge bclk, negedge rst)
    begin
        if(rst)
            cnt0 = 1'b0;
        else begin
               if(button)
                      cnt0=cnt0+1;
                      
                      if(cnt0== 20'd20)
                      begin
                          flag0=1;
                          cnt0=20'd0;
                          end
                          else 
                          flag0=0;
              end
    end
    
//    always @(posedge bclk, negedge rst) begin
//        if(!rst) buttonout = 5'b0;
//        else if (button[0]) buttonout[0] <= (flag) ? 1'b1 : 1'b0;
//        else if (button[1]) buttonout[1] <= (flag) ? 1'b1 : 1'b0;
//        else if (button[2]) buttonout[2] <= (flag) ? 1'b1 : 1'b0;
//        else if (button[3]) buttonout[3] <= (flag) ? 1'b1 : 1'b0;
//        else if (button[4]) buttonout[4] <= (flag) ? 1'b1 : 1'b0;
//        else buttonout <= 5'b0;
//    end
endmodule
