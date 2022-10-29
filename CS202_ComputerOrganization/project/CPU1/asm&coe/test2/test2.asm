.data 0x0000
    # pass

.text 0x0000

init:  # assign reg as pg, init memory
    lui $1, 0xFFFF
    ori $28, $1, 0xF000  # for hardware io

    add $29, $28, $0  # addr bias for ram, base = $28, reserved for dataset

    # at most 41 numbers to be stored in the ram, size (1) + data (4 * 10)
    addi $13, $zero, 41
    addi $2, $zero, 1
    init_for:
        sub $13, $13, $2
        sw $0, 0x0($29)
        addi $29, $29, 4
        bne $13, $zero, init_for


main:
    # main menu light
    ori $1, $0, 0xFFFF  
    sw $1, 0xC62($28)
    sw $0, 0xC60($28)
    # wait until user confirm
    ori $24, $zero, 0

    # press button
    m_loop1:
        lw $24, 0xC80($28)
        beq $24, $0, m_loop1
    
    m_loop2:
        lw $24, 0xC80($28)
        bne $24, $0, m_loop2

    lw $8, 0xC72($28)

    # get the case_num
    sll $8, $8, 24
    srl $8, $8, 29
    j switch_case


read:
    ori $24, $0, 0  # bypass compile error

    # press button
    r_loop1:
        lw $3, 0xC80($28)
        beq $3, $0, r_loop1
    
    r_loop2:
        lw $3, 0xC80($28)
        bne $3, $0, r_loop2


    lw $8, 0xC70($28) # data <- sw0

    sll $8, $8, 24
    srl $8, $8, 24  # take low 8 bits
    sw $8, 0xC60($28)
    sw $8, 0x0($29) # -> sp

    ori $24, $0, 0
    jr $31

switch_case:
    ori $23, $zero, 0  # Enter the number of test data (less than or equal to 10), enter multiple test data in binary format, and store the test data in the space corresponding to "Data Set 0" in the above table as is.
    beq $8, $23, case1

    ori $23, $zero, 1  # Consider the test data as unsigned numbers (bit7 is considered as part of the numeric bits as bit6 to bit0), sort the data from smallest to largest, and store the sorted data set as a whole in the space corresponding to "Data Set 1" in the table above (the smallest value is stored in the low address, and so on, and the largest value is stored in the high address)
    beq $8, $23, case2

    ori $23, $zero, 2  # Convert the test data (at this time, each data in "data set 0", its bit6~bit0 is regarded as the absolute value of the value, and bit7 corresponds to the sign bit of the value) into the complementary form of a signed number, and deposit the converted data set into the space corresponding to "data set 2" in the above table (the order of storage is the same as the data in data set 0)
    beq $8, $23, case3

    ori $23, $zero, 3  # Based on the data in "Data Set 2", the data is treated as the complement of a signed number and stored in order from smallest to largest, and the sorted data set is stored as a whole in the space corresponding to "Data Set 3" in the table above (the smallest value is stored in the low address, and so on, and the largest value is stored in the high address)
    beq $8, $23, case4

    ori $23, $zero, 4  # Subtract the minimum value in the data set from the maximum value in data set 1 and display the result on the output device
    beq $8, $23, case5

    ori $23, $zero, 5  # Subtract the minimum value in the data set from the maximum value in data set 3 and display the result on the output device
    beq $8, $23, case6

    ori $23, $zero, 6  # Enter the data set number (1 or 2 or 3) and the subscript of the element in the data set (addressed from 0), and output the lower 8 bits of the data
    beq $8, $23, case7

    ori $23, $zero, 7  # Enter the subscripts of the elements in the data set and display the following two sets of information alternately on the output device (5 seconds apart)
    beq $8, $23, case8

    j main

copy_dataset_old_start_from_s24_new_from_s25:
    lw $12, 0x($28)   # len(dataset)
    
    # copy dataset
    cdfori:
        lw $8, 0x0($24)
        sw $8, 0x0($25)

        addi $24, $24, 4
        addi $25, $25, 4

        addi $12, $12, -1
        bne $12, $0, cdfori
    jr $31


case1:
    ori $1, $0, 1  
    sw $1, 0xC62($28)
    # sp = (8-bit)*
    addi $29, $28, 0

    jal read  # place number of data in a dataset to gp[0]

    lw $15, 0x0($29)  # get the number of data -> $t7
    sw $15, 0x62($28)


    read_all_data:
        addi $29, $29, 4  # bias+32, use standard 32 bit to store 8bit data
        jal read  # place number of data in a dataset to sp[0]

        addi $15, $15, -1
        bne $15, $0, read_all_data

    # press enter to exit
    c1_loop1:
        lw $3, 0xC80($28)
        beq $3, $0, c1_loop1
    
    c1_loop2:
        lw $3, 0xC80($28)
        bne $3, $0, c1_loop2

    j main

case2:
    # disp case 2
    ori $1, $0, 2  
    sw $1, 0xC62($28)

    lw $12, 0x($28)
    sll $12, $12, 2    # sizeof(dataset)

    addi $24, $28, 4  # start of ds0
    add $25, $24, $12  # start of ds1

    jal copy_dataset_old_start_from_s24_new_from_s25

    ori $10, $zero, 4  # i
    lw $12, 0x($28)
    sll $12, $12, 2    # sizeof(dataset)

    addi $3, $12, -4  # 4 * (len-1)
    beq $3, $0, skpc2  # only 1 data, no need to sort


    c2fori:
        beq $10, $12, c2jx  # i < len

        ori $11, $zero, 0  # j
        sub $13, $12, $10  # len - i

        c2forj:
            # j < len - i
            beq $11, $13, c2ix

            addi $14, $28, 4
            add $14, $14, $12  # dataset1[0]
            add $14, $14, $11  # & dataset1[j]

            lw $15, 0($14)  # dataset[j]
            lw $25, 4($14)  # dataset[j+1]

            slt $24, $15, $25  # check if need to swap
            bne $24, $0, c2notif  # dataset[j] - dataset[j+1] < 0

            # swap
            sw $15, 4($14)
            sw $25, 0($14)

            c2notif:
                # pass

            addi $11, $11, 4  # j++
            j c2forj

        c2ix:
            ori $1, $1, 0  # bypass compile error

        addi $10, $10, 4  # i++
        j c2fori

    c2jx:
        ori $1, $1, 0  # bypass compile error

    skpc2:
        ori $1, $1, 0  # bypass compile error


    # press enter to exit
    c2_loop1:
        lw $3, 0xC80($28)
        beq $3, $0, c2_loop1
    
    c2_loop2:
        lw $3, 0xC80($28)
        bne $3, $0, c2_loop2

    j main


case3:
    # disp case 3
    ori $1, $0, 3
    sw $1, 0xC62($28)

    lw $12, 0x($28)
    sll $12, $12, 2    # sizeof(dataset)

    addi $24, $28, 4  # start of dataset0
    add $25, $24, $12
    add $25, $25, $12  # start of dataset2

    jal copy_dataset_old_start_from_s24_new_from_s25

    lw $12, 0x($28)
    sll $12, $12, 2    # sizeof(dataset)

    addi $25, $28, 4
    add $25, $25, $12
    add $25, $25, $12  # start of dataset2

    ori $11, $zero, 0  # i

    sign_ext_for:
        beq $11, $12, fin_c3f

        lw $24, 0x($25)  # dataset2[i]

        andi $14, $24, 0x80  # bit 8, sign = 0, is positive
        beq $14, $zero, no_need_to_conv

        # convert to 2s complement
        lui $3, 0xFFFF

        andi $24, $24, 0x7F  # keep abs val
        xori $24, $24, 0x7F  # 1s complement
        addi $24, $24, 1  # 2s complement
        ori $24, $24, 0xFF80  # 1s leading
        or $24, $24, $3

        no_need_to_conv:
            # pass

        # save back
        sw $24, 0x($25)

        addi $11, $11, 4  # i++
        addi $25, $25, 4

        j sign_ext_for

    fin_c3f:
        or $0, $0, $0  # pass

    # confirm exit
    c3_loop1:
        lw $3, 0xC80($28)
        beq $3, $0, c3_loop1
    
    c3_loop2:
        lw $3, 0xC80($28)
        bne $3, $0, c3_loop2

    j main


case4:
    # disp case 4
    ori $1, $0, 4
    sw $1, 0xC62($28)

    lw $12, 0x($28)
    sll $12, $12, 2    # sizeof(dataset)

    addi $24, $28, 4  # start of ds0
    add $24, $24, $12 # start of ds1
    add $24, $24, $12 # start of ds2
    add $25, $24, $12  # start of ds3, curr

    jal copy_dataset_old_start_from_s24_new_from_s25

    ori $10, $zero, 4  # i
    lw $12, 0x($28)
    sll $12, $12, 2    # sizeof(dataset)

    addi $3, $12, -4  # 4 * (len-1)
    beq $3, $0, skpc4


    c4fori:
        beq $10, $12, c4jx

        ori $11, $zero, 0  # j
        sub $13, $12, $10  # len - i

        c4forj:
            beq $11, $13, c4ix

            addi $14, $28, 4
            add $14, $14, $12  # dataset1[0]
            add $14, $14, $12  # dataset2[0]
            add $14, $14, $12  # dataset3[0]
            add $14, $14, $11  # & dataset3[j]

            lw $15, 0($14)  # dataset[j]
            lw $25, 4($14)  # dataset[j+1]

            slt $24, $15, $25
            bne $24, $0, c4notif  # dataset[j] - dataset[j+1] < 0

            # swap
            sw $15, 4($14)
            sw $25, 0($14)

            c4notif:
                # pass

            addi $11, $11, 4  # j++
            j c4forj

        c4ix:
            ori $1, $1, 0  # bypass compile error

        addi $10, $10, 4  # i++
        j c4fori

    c4jx:
        ori $1, $1, 0  # bypass compile error

    skpc4:
        ori $1, $1, 0  # bypass compile error

    c4_loop1:
        lw $3, 0xC80($28)
        beq $3, $0, c4_loop1
    
    c4_loop2:
        lw $3, 0xC80($28)
        bne $3, $0, c4_loop2

    j main


case5:
    ori $1, $0, 5
    sw $1, 0xC62($28)

    lw $12, 0($28)
    sll $12, $12, 2    # sizeof(dataset)

    addi $24, $28, 4  # start of first dataset0
    add $24, $24, $12

    lw $14, 0($24) # min 
    add $24, $24, $12 # start of ds2
    addi $24, $24, -4  # end of ds1
    lw $15, 0($24) # max
    sub $15, $15, $14 # calculate result store in $15 register

    sw $15, 0xC60($28)

    c5_loop1:
        lw $24, 0xC80($28)
        beq $24, $0, c5_loop1
    
    c5_loop2:
        lw $24, 0xC80($28)
        bne $24, $0, c5_loop2

    j main

case6:
    ori $1, $0, 6
    sw $1, 0xC62($28)
    
    lw $12, 0x($28)
    sll $12, $12, 2    # sizeof(dataset)
    addi $24, $28, 4  # start of ds0
    add $24, $24, $12  # start of ds1
    add $24, $24, $12  # start of ds2
    add $24, $24, $12  # start of ds3

    lw $14, 0x($24) # min
    add $24, $24, $12  # ds4 ng
    addi $24, $24, -4  # end of ds3
    lw $15, 0x($24) # max
    sub $15, $15, $14 # calculate result store in $15 register

    sw $15, 0xC60($28)

    c6_loop1:
        lw $24, 0xC80($28)
        beq $24, $0, c6_loop1
    
    c6_loop2:
        lw $24, 0xC80($28)
        bne $24, $0, c6_loop2

    j main

case7:
    ori $1, $0, 7  
    sw $1, 0xC62($28)

    addi $29, $28, 165  # after 41 ints, no one uses this
    addi $29, $29, 4
    jal read  # place number of data in a dataset to sp[0]

    addi $29, $29, 4
    jal read  # place number of data in a dataset to sp[0]

    addi $29, $29, -4
    lw $14, 0($29) # selection of dataset
    addi $14, $14, 1

    lw $12, 0($28)
    sll $12, $12, 2    # sizeof(dataset)
    addi $24, $28, 4  # start of first dataset

    addi $1, $0, 1
    dataset_selection:
        add $24, $24, $12  #FIX1
        addi $14, $14, -1
        bne $14, $0, dataset_selection

    sub $24, $24, $12 # start of the selected dataset

    addi $29, $29, 4
    lw $14, 0($29) # selection of element
    addi $14, $14, 1

    element_selection:
        addi $24, $24, 4
        addi $14, $14, -1
        bne $14, $0, element_selection

    addi $24, $24, -4
    lw $15, 0x($24) # value of the selected element

    andi $15, $15, 0x00FF # mask

    sw $15, 0xC60($28) # display result

    c7_loop1:
        lw $24, 0xC80($28)
        beq $24, $0, c7_loop1
    
    c7_loop2:
        lw $24, 0xC80($28)
        bne $24, $0, c7_loop2

    j main

case8:
    ori $1, $0, 8
    sw $1, 0xC62($28)

    addi $29, $28, 165
    addi $29, $29, 4  # bias+32, good enough

    jal read  # place number of data in a dataset to sp[0]

    lw $14, 0x($29) # selection of element
    sll $14, $14, 2

    lw $12, 0x($28)
    sll $12, $12, 2    # sizeof(dataset)
    addi $24, $28, 4  # start of first dataset


    add $24, $24, $14

    lw $11, 0x($24) # value of the selected element in dataset0

    lw $16, 0x($29)  # idx


    addi $24, $28, 4  # start of ds0
    add $24, $24, $12 # start of ds1
    add $24, $24, $12 # start of ds2

    lw $14, 0x($29) # selection of element
    sll $14, $14, 2

    add $24, $24, $14

    lw $12, 0x($24) # value of the selected element in dataset2
    andi $12, $12, 0xFF

    add $17, $0, $16  # idx
    ori $17, $17, 0x80    # ds2, 10_ _ idx

    ori $15, $15, 0x6DA # 5s
    sll $15, $15, 16
    ori $15, $15, 0xC2C0 # 5s

    srl $15, $15, 2  # scale rate

    ori $8, $0, 0 # cycle counter

    addi $1, $0, 1
    c8disp:
        beq $8, $0, dispds0
            # disp ds1 + its info
            sw $12, 0xC60($28)
            sw $17, 0xC62($28)

        j aftdisp
        dispds0:
            # disp ds0 + its info
            sw $11, 0xC60($28)
            sw $16, 0xC62($28)

        aftdisp:
            sub $8, $1, $8  # disp next ds in next cycle

        ori $2, $0, 0

        wait5s:
            addi $2, $2, 1
            or $0, $0, $0
            beq $2, $15, c8disp
            j wait5s

    j main
