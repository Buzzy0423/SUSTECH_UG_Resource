.data
	const1: .double 1.0
.text 
	li $v0, 7
	syscall
	#f12 is ans, f2 is stratum, f4 is counter, f10 is const1
	l.d $f2, const1
	l.d $f4, const1
	l.d $f12, const1
	l.d $f10, const1
	#while
	Loop:
	mul.d $f2, $f2, $f4
	div.d $f6, $f10, $f2
	c.lt.d $f6, $f0
	bc1t Break
	add.d  $f4, $f4, $f10
	add.d $f12, $f12, $f6
	j Loop
	Break:
	li $v0, 3
	syscall