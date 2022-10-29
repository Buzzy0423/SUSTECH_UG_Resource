.data
str1: .asciiz "Its binary bit-width is "
str2: .asciiz ", its number of hexadecimal digits in hexadecimal is "
.text
	sw $t1, 32($t2)
    li $v0, 5
    syscall 
	move $a1, $zero
	Loop: srl $v0, $v0, 1
	addi $a1, $a1, 1
	bne $v0, $zero, Loop
	subi $a2, $a1, 1
	div $a2, $a2, 4
	addi $a2, $a2, 1
	la $a0, str1
	li $v0, 4
	syscall
	move $a0, $a1
	li $v0, 1
	syscall
	la $a0, str2
	li $v0, 4
	syscall
	move $a0, $a2
	li $v0, 1
	syscall 
