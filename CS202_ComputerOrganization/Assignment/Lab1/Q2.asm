.data
str1: .asciiz "x2: "
str2: .asciiz "x2r: "
str3: .asciiz "x16: "
str4: .asciiz "x16r: "
newline: .asciiz "\n"
.text
	add $s0, $s0, $s0
	#a1 is input num
    li $v0, 5
    syscall 
    move $a1, $v0
    la $a0, str1
    li $v0, 4
    syscall 
    move $a0, $a1
    li $v0, 35
    syscall
    #a2 is len(a1 x2)
    move $a2, $zero
    move $t0, $a1
    Loop: 
    srl $t0, $t0, 1
	addi $a2, $a2, 1
	bne $t0, $zero, Loop
	#$s0 is len(a1 x16)
	subi $s0, $a2, 1
	div $s0, $s0, 4
	addi $s0, $s0, 1
	#a3 is x2r
	move $t1, $zero #counter
	move $a3, $zero
	Loop2: 
	move $t3, $a1
	srlv $t3, $t3, $t1
	sll $t3, $t3, 31
	srl $t3, $t3, 31
	sll $a3, $a3, 1
	addi $t1, $t1, 1
	add $a3, $a3, $t3
	bne $t1, $a2, Loop2
	# s1 is a1 x6r
	move $t1, $zero #counter
	move $s1, $zero 
	Loop3:
	move $t3, $a1
	mul $t1, $t1, 4
	srlv $t3, $t3, $t1
	div $t1, $t1, 4
	sll $t3, $t3, 28
	srl $t3, $t3, 28
	sll $s1, $s1, 4
	addi $t1, $t1, 1
	add $s1, $s1, $t3
	bne $t1, $s0, Loop3
	###
	li $v0, 4       
	la $a0, newline       
	syscall
	la $a0, str2
	li $v0, 4
	syscall 
	move $a0, $a3
	li $v0, 35
	syscall
	li $v0, 4       
	la $a0, newline       
	syscall
	###
	la $a0, str3
	li $v0, 4
	syscall 
	move $a0, $a1
	li $v0, 34
	syscall
	li $v0, 4       
	la $a0, newline       
	syscall
	###
	la $a0, str4
	li $v0, 4
	syscall 
	move $a0, $s1
	li $v0, 34
	syscall
