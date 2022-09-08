`timescale 1ns / 1ps

/**
  * 匹配用户名，成功匹配 -> uid存在数组的位置 [0-5]，匹配失败 -> 6
  * 用户名：键盘输入两位[0-9A-F]，8bit，最多支持六用户
  * <p>
  * 检查密码：pwd_db[i+15:i]是否匹配
  */
module pair_uid(
    input clk,
    input [47:0] uid_db,
    input [5:0] usr_exist,
    input [7:0] uid_in,
    output [2:0] pair_stat
);
    integer i;
    always @ (pid) begin
        for (i = 0; i < 7; i = i + 1) begin
            if (usr_exist[i] == 0) begin
                i = i + 1;
            end
            else if (uid_in == uid_db[i*8+7:i*8]) begin
                pair_stat = i;
                break;
            end
        end
    end
endmodule
