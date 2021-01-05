#VirtualMachine

This program is about building a virtual machine that can execute some basic instructions

There are several test cases that were showing in the "CPUTest" classes

. (the binary number representation is reverse form, ex: 5(0000 0000 0000 0101) should be like 1010 0000 0000 0000

. "3R" instruction format ex: add r1 r2 r3: adding the values of r1 and r2 to r3

When writing the instruction you can use the assembler to generate the text, and the text patterns of opcode are: 0001(move): mov

0011(jump): jump

0100(compare): compare

0101(branch): branchIfGreater(10)/BranchIfGreaterThan(11)

1110(add): add

1111(subtract): sub

0111(multiple): mul

1000(and): and

1001(or): or

1010(xor): xor

1011(not): not

0010 0000 0000 0000(interrrupt by showing all the values in the registers): interrupt 0

0010 0000 0000 0001(interrupt by showing all the values in the memory): interrrupt 1

0110 0000 0000 RRRR(push): "R" represents the register value that will be pushed into the stack

0110 0100 0000 RRRR(pop): "R" represents the register value that the value is popping to

0110 10AA AAAA AAAA(call): "A" represents the address value, ex: call 5 --> 0110 1010 1000 0000

0110 1100 0000 0000(return): return
