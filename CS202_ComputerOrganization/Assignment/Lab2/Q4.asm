.ktext 0x80000180 
addi $sp, $sp, -8
sw $v0, 0($sp)
sw $a0, 4($sp)
bne $k0, 11, L1
la $a0, msg
li $v0,4
syscall
L1:
mfc0 $v0, $14
addi $v0, $v0, 4
mtc0 $v0, $14
lw $v0, 0($sp)
lw $a0, 4($sp)
addi $sp, $sp, 8
eret
.kdata
msg: .asciiz "exception:divisor is 0.0 "

.macro divSCheck(%f1,%f2,%f3)
addi $sp, $sp, -4
swc1  $f1, 0($sp)
mtc1 $zero,$f0
c.eq.s $f1, %f3
lwc1  $f0, 0($sp)
addi, $sp, $sp, 4
div.s %f1, %f2, %f3
bc1t Label
j L2
Label:
li $k0, 11
teqi $zero, 0
L2:
.end_macro 

.text
li $v0,6
syscall
mov.s $f20,$f0
li $v0,6
syscall
mov.s $f21,$f0 
divSCheck($f12,$f20,$f21)
li $v0,2
syscall
li $v0,10
syscall
