.data
	string: .asciiz "Invalid float type"
	s: .asciiz "s: "
	e: .asciiz "e: "
	f: .asciiz "f: "
	comma: .asciiz ", "
	
.macro print_char(%f1)
	ble  %f1, 9, ADD
	addi %f1, %f1, 87
	j Con
	ADD:
	addi %f1, %f1, 48
	Con: 
	move $a0, %f1
	li $v0, 11
	syscall
.end_macro 
	
.macro print_hex(%f1,%f2)
	move $s2, %f2
	Loop2:
	move $s1, %f1
	sllv $s1, $s1, $s2
	srl $s1, $s1, 28
	addi $s2, $s2, 4 
	print_char($s1)
	blt $s2, 32, Loop2
.end_macro

.macro print_hex2(%f1)
	li $s2, 0
	Loop2:
	move $s1, %f1
	sllv $s1, $s1, $s2
	srl $s1, $s1, 28
	addi $s2, $s2, 4 
	print_char($s1)
	blt $s2, 32, Loop2
.end_macro

.macro print_prefix()
	li $a0, 48
	li $v0, 11
	syscall
	li $a0, 120
	syscall
.end_macro

.text
	li $v0, 5
	syscall
	beq $v0, 6, float
	beq $v0, 7, double
	la $a0, string
	li $v0, 4
	syscall
	j End
	float:
	syscall
	mfc1 $t1, $f0
	srl $t1, $t1, 31
	la $a0, s
	li $v0, 4
	syscall
	move $a0, $t1
	li $v0, 1
	syscall
	la $a0, comma
	li $v0, 4
	syscall
	mfc1 $t1, $f0
	sll $t1, $t1, 1
	srl $t1, $t1, 24
	la $a0, e
	li $v0, 4
	syscall
	print_prefix()
	li $s4, 24
	print_hex($t1,$s4)
	la $a0, comma
	li $v0, 4
	syscall
	mfc1 $t1, $f0
	sll $t1, $t1, 9
	srl $t1, $t1, 9
	la $a0, f
	li $v0, 4
	syscall
	print_prefix()
	li $s4, 8
	print_hex($t1,$s4)
	j End
	double:
	syscall
	mfc1.d $t1, $f0
	srl $t2, $t2, 31
	la $a0, s
	li $v0, 4
	syscall
	move $a0, $t2
	li $v0, 1
	syscall
	la $a0, comma
	li $v0, 4
	syscall
	mfc1.d $t1, $f0
	sll $t2, $t2, 1
	srl $t2, $t2, 21
	la $a0, e
	li $v0, 4
	syscall
	print_prefix()
	li $s4, 20
	print_hex($t2,$s4)
	la $a0, comma
	li $v0, 4
	syscall
	mfc1.d $t1, $f0
	sll $t2, $t2, 12
	srl $t2, $t2, 12
	la $a0, f
	li $v0, 4
	syscall
	print_prefix()
	li $s4, 12
	print_hex($t2,$s4)
	print_hex2($t1)
	End:
	li $v0, 10
	syscall
