package du.virtualMachine;



/**
 * Computer (CPU)
 * Check out the help methods that determine which method to use, ex: isAdd() --> add
 * procedure: preload the instructions (assemble the instruction), and run()
 * @author Carter Du
 *
 */
public class Computer {
	
	private Bit haltValue = new Bit(1);  //default value of 0: halt / state 1: running
	public Memory m = new Memory();
	Longword[] registers = new Longword[16];
	
	private Longword pc = new Longword();	//program counter increment by 2(since 2 instructions --> longword)
	private Longword currentInstruction = new Longword();	//the value of the current instruction
	
	private Longword op1 = new Longword();
	private Longword op2 = new Longword();
	private Bit[] opcode = new Bit[4];
	private static Longword result = new Longword();
	
	private Bit compareStatusBit1 = new Bit(0);
	private Bit compareStatusBit2 = new Bit(0);
	
	private Bit conditionOfBranch1 = new Bit(0);	//first bit of condition
	private Bit conditionOfBranch2 = new Bit(0);	//second bit of condition
	
	private Longword sp = new Longword();			//stack pointer
	
	

	
	/**
	 * There are 16 registers on the CPU
	 * The program counter starts from 0
	 * The stack pointer points to the bottom of the memory
	 */
	public Computer(){
		for(int i =0; i < registers.length; i++){
			registers[i] = new Longword();
		}
		pc.set(0);
		sp.set(1020);
		
	}
	
	
	
	
	/**
	 * get the instruction from the memory (instructions will be loaded into the memory)
	 */
	public void fetch(){
		currentInstruction = m.read(pc);
		pc.set(pc.getSigned() + 2);
	}
	
	/**
	 * get the currentInstruction from registers
	 * figure out the value from the current instruction
	 */
	public void decode(){
		
		op1.set(0);
		op2.set(0);
		
			
		for(int i = 0; i < 4; i++){
			opcode[i] = currentInstruction.getBit(i);
		}
		
		if(isMove()){
			for(int j = 0; j < 4; j++){
				
				op1.bit_container[j] = currentInstruction.getBit(j + 4);
			}
			if(currentInstruction.getBit(15).getValue() == 1)		//when the moving value is negative
			{
				for(int x = 0; x < 8; x++)
				{
					op2.bit_container[x] = currentInstruction.getBit(x + 8);
				}
				
				for(int y = 8; y < 32; y++)
				{
					op2.bit_container[y] = new Bit(1);
				}
				
			}
			else{
				for(int k = 0; k < 8; k++){
					op2.bit_container[k] = currentInstruction.getBit(k + 8);
				}
			}
		}
		
		
		
		else if(isJump())
		{
			for(int i = 0; i < 12; i++)
			{
				op1.bit_container[i] = currentInstruction.getBit(i + 4);
			}
		}
		
		
		else if(isCompare())
		{
			for(int j = 0; j < 4; j++){
				op1.bit_container[j] = currentInstruction.getBit(j + 8);
			}
			for(int k = 0; k < 4; k++){
				op2.bit_container[k] = currentInstruction.getBit(k + 12);
			}
			op1.set(registers[(int)op1.getUnsigned()].getSigned());
			op2.set(registers[(int)op2.getUnsigned()].getSigned());
		}
		
		/**
		 * branch (0101)
		 * next 2 bits are conditional bits
		 */
		else if(isBranch())
		{
			conditionOfBranch1.setBit(currentInstruction.bit_container[4].bit);
			conditionOfBranch2.setBit(currentInstruction.bit_container[5].bit);
			for(int i = 0; i < 10; i++)
			{
				op1.bit_container[i] = currentInstruction.getBit(i + 6);	//value of branching
			}
		}
		
		
		else if(isPush())
		{
				for(int i = 0; i < 4; i++)
				{
					op1.set_Bit(i, currentInstruction.getBit(i + 12));
				}
				
			
		}
		
		/**
		 * pop: put the last 4 bit as the register value to op1
		 */
		else if(isPop())
		{
			for(int i = 0; i < 4; i++)
			{
				op1.set_Bit(i, currentInstruction.getBit(i + 12));
			}
		}
		
		
		else if(isCall())
		{
			for(int i = 0; i < 10; i++)
			{
				op1.set_Bit(i, currentInstruction.getBit(i + 6));
			}
		}
		
		
		else if(isReturn())		//return does not do anything in the decode stage
		{
			
		}
		
		else if(isAdd() || isSub() || isMul() || isAnd() || isOr() || isXor())
		{
			for(int i = 0; i < 4; i++){
				op1.bit_container[i] = currentInstruction.getBit(i + 4);
				op2.bit_container[i] = currentInstruction.getBit(i + 8);
			}
			
			op1.set(registers[(int)op1.getUnsigned()].getSigned());
			op2.set(registers[(int)op2.getUnsigned()].getSigned());
		}
		
		else
		{
			System.out.println("Make sure your opcode is correct, otherwise check the format!");
			
		}
	}
	
	/**
	 * pass the opcode, 2 operands into ALU
	 * @throws Exception 
	 */
	public void execute() throws Exception{
		if(opcode[0].getValue()==0 && opcode[1].getValue()==0 && opcode[2].getValue()==0 && opcode[3].getValue()==0){
			haltValue = new Bit(0);			//if opcode be 0000 --> stop running (halt)
		}
		
		/**
		 * Interrupt instruction(0010)
		 */
		else if(isInterrupt()){
			if(currentInstruction.getBit(15).getValue() == 0){		//print out all the register values
				for(int i = 0; i < registers.length; i++){
					System.out.println("register" + i + ": " + registers[i].getSigned());
				}
			}
			else if(currentInstruction.getBit(15).getValue() == 1){	//print out all instructions inside of memory
				for(int j = 0; j < m.memory.length; j = j + 16){
					System.out.println("instruction " + j + ": ");
					for(int k = j; k < 16 + j; k++){
						System.out.println(m.memory[k]);
					}
				}
			}
		}
		
		/**
		 * push: push the value of register to the stack
		 */
		else if(isPush())
		{
			m.write(sp, registers[op1.getSigned()]);
		}
		
		/**
		 * pop: 1. increment the stack pointer by 4 in order to read the top element
			2. assign op2 with the value from the top of the stack
		 */
		else if(isPop())
		{
			sp.set(sp.getSigned() + 4);
			op2.set(m.read(sp).getSigned());    //information is not complete deleted
			
			
		}
		
		/*
		 * call
		 * (1. push address of the next instruction into stack) --- execute
		 2. jump to the given address
		 */
		else if(isCall())
		{
			Longword lw = new Longword();
			lw.set(pc.getSigned() + 2);
			m.write(sp, lw);		//push the address value of next ins onto stack
			sp.set(sp.getSigned() - 4);		//update the stack pointer like push
			
		}
		
		/*
		 * return
		 *  pop the value from the stack
		 * (and then in the store()): put that value into the current program counter
		 */
		else if(isReturn())
		{
			op1.set(m.read(sp).getSigned());
		}
		
		
		
		else if(isCompare())
		{
			Longword statusOfCompare = new Longword();
			statusOfCompare = RippleAdder.substract(op1, op2);
			
			/**
			 * 11 --> op1 > op2
			 */
			if(statusOfCompare.getSigned() > 0)
			{
				compareStatusBit1 = new Bit(1);
				compareStatusBit2 = new Bit(1);
			}
			/**
			 * 00 --> op2 > op1
			 */
			else if(statusOfCompare.getSigned() < 0)
			{
				compareStatusBit1 = new Bit(0);
				compareStatusBit2 = new Bit(0);		
			}
			/**
			 * 01 --> op1 = op2
			 */
			else{
				compareStatusBit1 = new Bit(1);
				compareStatusBit2 = new Bit(0);		
			}
		}
		
		/**
		 * branch(0101)
		 * set the op2 to value 1
		 */
		else if(isBranch())
		{
			/**
			 * branchIfGreaterThan(10)
			 */
			if(conditionOfBranch1.getValue()==1 && conditionOfBranch2.getValue()==0)
			{
				/**
				 * When op1 > op2
				 */
				if(compareStatusBit1.getValue()==1 && compareStatusBit2.getValue()==1)
				{
					op2.set(1);
				}
			}
			
			/**
			 * branchIfGreaterThanOrEqual(11)
			 */
			else if(conditionOfBranch1.getValue()==1 && conditionOfBranch2.getValue()==1)
			{
				/**
				 * when op1 > op2 or op1 == op2
				 */
				if((compareStatusBit1.getValue()==1 && compareStatusBit2.getValue()==1) || 
						(compareStatusBit1.getValue()==1 && compareStatusBit2.getValue()==0))
				{
					op2.set(1);
					
				}
			}
			
			/**
			 * branchIfEqual(01)
			 */
			else if(conditionOfBranch1.getValue()==1 && conditionOfBranch2.getValue()==1)
			{
				/**
				 * when op1 > op2 or op1 == op2
				 */
				if(compareStatusBit1.getValue()==1 && compareStatusBit2.getValue()==0)
				{
					op2.set(1);
				}
			}
			
			/**
			 * branchIfNotEqual(01)
			 */
			else if(conditionOfBranch1.getValue()==1 && conditionOfBranch2.getValue()==1)
			{
				/**
				 * when op1 > op2 or op1 == op2
				 */
				if((compareStatusBit1.getValue()==1 && compareStatusBit2.getValue()==1) || 
						(compareStatusBit1.getValue()==0 && compareStatusBit2.getValue()==0))
				{
					op2.set(1);
				}
			}
			
			
		}
		
		
		else if(isMove())
		{
			result.set(op2.getSigned());
		}
		
		
		else if(isAdd())
		{
			result = Multiplier.doOp(opcode, op1, op2);
		}
		
		
		else if(isSub())
		{
			result = Multiplier.doOp(opcode, op1, op2);
		}
		
		
		else if(isMul())
		{
			result = Multiplier.doOp(opcode, op1, op2);
		}
		
		
		else if(isAnd())
		{
			result = Multiplier.doOp(opcode, op1, op2);
		}
		
		
		else if(isOr())
		{
			result = Multiplier.doOp(opcode, op1, op2);
		}
		
		
		else if(isXor())
		{
			result = Multiplier.doOp(opcode, op1, op2);
		}
		
		
		else if(isNot())
		{
			result = Multiplier.doOp(opcode, op1, op2);
		}
		
		else{
			System.out.println("Make sure your opcode is correct, otherwise check the format!");
		}
	}
	
	
	/**
	 * copy the value from the result longword into the register indicated by the instruction.
	 * 1. identify the 4 digit in the current instruction
	 * 2. put the result in the 4 digit
	 */
	public void store(){
		
		if(haltValue.getValue() == 1){
			
			if(isMove()){
				registers[(int)op1.getUnsigned()].set(result.getSigned());
			}
			
			else if(isJump())
			{
				pc.set(op1.getSigned());
			}
			
			/*
			 * push: decrement the sp by 4
			 *
			 */
			else if(isPush())
			{
				
				sp.set(sp.getSigned() - 4);	
			}
			
			
			else if(isPop())
			{
				registers[op1.getSigned()].set(op2.getSigned()); 
			}
			
			/**
			 * call: 
			 * 1. push address of the next instruction into stack
			 * (2. jump to the given address)---store
			 */
			else if(isCall())
			{
				pc.set(op1.getSigned());
			}
			
			/**
			 * return 
			 * store that value (pop from the stack) into the current program counter
			 */
			else if(isReturn())
			{
				pc.set(op1.getSigned());
			}
			
			
			
			
			
			else if(isAdd())
			{
				assignValueToRegisterWhen3R();
			}
			
			
			else if(isSub())
			{
				assignValueToRegisterWhen3R();
			}
			
			
			else if(isMul())
			{
				assignValueToRegisterWhen3R();
			}
			
			
			else if(isAnd())
			{
				assignValueToRegisterWhen3R();
			}
			
			
			else if(isOr())
			{
				assignValueToRegisterWhen3R();
			}
			
			
			else if(isXor())
			{
				assignValueToRegisterWhen3R();
			}
			
			
			else if(isNot())
			{
				Longword indexOfRegister = new Longword();	//The index of register to store the result
				for(int i = 8; i < 12; i++){
					indexOfRegister.bit_container[i] = currentInstruction.getBit(i);	//8 - 12 (not r1 r2)
				}
				registers[indexOfRegister.getSigned()] = result;
			}
			
			
			else if(isBranch())
			{
				changeProgramCounterWhenBranch();
			}
			
			
			else
			{
				System.out.println("Check the instruction!");
			}
			
		
		}
	}
	
	/**
	 * Determine the operation is stack operation(0110)
	 * @return
	 */
	public boolean isStackOperation(){
		 if(opcode[0].getValue()==0 && opcode[1].getValue()==1 && opcode[2].getValue()==1 && opcode[3].getValue()==0)
		 {
			 return true;
		 }
		 else{
			 return false;
		 }
	}
	
	/**
	 * Determine the method is push(0110 00) or not
	 * @return
	 */
	public boolean isPush(){
		if(isStackOperation()==true && currentInstruction.getBit(4).getValue()==0 && currentInstruction.getBit(5).getValue()==0)
		{
			 return true;
		}
		 return false;
	}
	
	/**
	 * Determine the method is pop(0110 01) or not
	 * @return
	 */
	public boolean isPop(){
		if(isStackOperation()==true && currentInstruction.getBit(4).getValue()==0 && currentInstruction.getBit(5).getValue()==1)
		{
			 return true;
		}
		 return false;
	}
	
	/**
	 * Determine the method is call(0110 10) or not
	 * @return
	 */
	public boolean isCall(){
		if(isStackOperation()==true && currentInstruction.getBit(4).getValue()==1 && currentInstruction.getBit(5).getValue()==0)
		{
			 return true;
		}
		 return false;
	}
	
	/**
	 * Determine the method is return(0110 11) or not
	 * @return
	 */
	public boolean isReturn(){
		if(isStackOperation()==true && currentInstruction.getBit(4).getValue()==1 && currentInstruction.getBit(5).getValue()==1)
		{
			 return true;
		}
		 return false;
	}
	
	/**
	 * Determine the method is branch(0101) or not
	 * @return
	 */
	public boolean isBranch(){
		if(opcode[0].getValue()==0 && opcode[1].getValue()==1 &&  opcode[2].getValue()==0 && opcode[3].getValue()==1)
		{
			 return true;
		}
		 return false;
	}
	
	/**
	 * Determine the method is compare(0100) or not
	 * @return
	 */
	public boolean isCompare(){
		if(opcode[0].getValue()==0 && opcode[1].getValue()==1 &&  opcode[2].getValue()==0 && opcode[3].getValue()==0)
		{
			 return true;
		}
		 return false;
	}
	
	
	/**
	 * Determine the method is jump(0011) or not
	 * @return
	 */
	public boolean isJump(){
		if(opcode[0].getValue()==0 && opcode[1].getValue()==0 &&  opcode[2].getValue()==1 && opcode[3].getValue()==1)
		{
			 return true;
		}
		 return false;
	}
	
	
	/**
	 * Determine the method is move(0001) or not
	 * @return
	 */
	public boolean isMove(){
		if(opcode[0].getValue()==0 && opcode[1].getValue()==0 &&  opcode[2].getValue()==0 && opcode[3].getValue()==1)
		{
			 return true;
		}
		 return false;
	}
	
	/**
	 * Determine the method is add(1110) or not
	 * @return
	 */
	public boolean isAdd(){
		if(opcode[0].getValue()==1 && opcode[1].getValue()==1 &&  opcode[2].getValue()==1 && opcode[3].getValue()==0)
		{
			 return true;
		}
		 return false;
	}
	
	/**
	 * Determine the method is sub(1111) or not
	 * @return
	 */
	public boolean isSub(){
		if(opcode[0].getValue()==1 && opcode[1].getValue()==1 &&  opcode[2].getValue()==1 && opcode[3].getValue()==1)
		{
			 return true;
		}
		 return false;
	}
	
	/**
	 * Determine the method is mul(0111) or not
	 * @return
	 */
	public boolean isMul(){
		if(opcode[0].getValue()==0 && opcode[1].getValue()==1 &&  opcode[2].getValue()==1 && opcode[3].getValue()==1)
		{
			 return true;
		}
		 return false;
	}
	
	/**
	 * Determine the method is and(1000) or not
	 * @return
	 */
	public boolean isAnd(){
		if(opcode[0].getValue()==1 && opcode[1].getValue()==0 &&  opcode[2].getValue()==0 && opcode[3].getValue()==0)
		{
			 return true;
		}
		 return false;
	}
	
	
	/**
	 * Determine the method is or(1001) or not
	 * @return
	 */
	public boolean isOr(){
		if(opcode[0].getValue()==1 && opcode[1].getValue()==0 &&  opcode[2].getValue()==0 && opcode[3].getValue()==1)
		{
			 return true;
		}
		 return false;
	}
	
	/**
	 * Determine the method is xor(1010) or not
	 * @return
	 */
	public boolean isXor(){
		if(opcode[0].getValue()==1 && opcode[1].getValue()==0 &&  opcode[2].getValue()==1 && opcode[3].getValue()==0)
		{
			 return true;
		}
		 return false;
	}
	
	/**
	 * Determine the method is not(1011) or not
	 * @return
	 */
	public boolean isNot(){
		if(opcode[0].getValue()==1 && opcode[1].getValue()==0 &&  opcode[2].getValue()==1 && opcode[3].getValue()==1)
		{
			 return true;
		}
		 return false;
	}
	
	/**
	 * Determine the method is interrupt(0010) or not
	 * @return
	 */
	public boolean isInterrupt(){
		if(opcode[0].getValue()==0 && opcode[1].getValue()==0 &&  opcode[2].getValue()==1 && opcode[3].getValue()==0)
		{
			 return true;
		}
		 return false;
	}
	

	
	
	
	/**
	 * branch condition if op2's value is 1
	 */
	public void changeProgramCounterWhenBranch()
	{
		
		if(op2.getSigned() == 1)
		{
			pc.set(pc.getSigned() + op1.getSigned());
		}
		else
		{
			System.out.println("op2's value is not 1 so NO BRANCH!");
		}
	}
	
	
	public void assignValueToRegisterWhen3R()
	{
		Longword indexOfRegister = new Longword();	//The index of register to store the result
		for(int i = 0; i < 4; i++){
			indexOfRegister.bit_container[i] = currentInstruction.getBit(12 + i);	//8 - 12 (not r1 r2)
		}
		registers[indexOfRegister.getSigned()] = result;
	}
	
	
	
	/**
	 * Loading the instructions to the memory
	 * Store each instruction(16 bits) as an string array
	 * combine 2 instructions as a longword, and then pass it into the memory
	 * @param instructions
	 */
	public void preload(String[] instructions){
		
		
		if((instructions.length % 2) != 0){		//if the number of instruction is odd
			String[] newInstructions = new String[instructions.length + 1];		//make an even instruction array
			System.arraycopy(instructions, 0, newInstructions, 0, instructions.length);
			newInstructions[instructions.length] = "0000000000000000";	//adding the 16's 0 as an instruction
			
			instructions = newInstructions;
		}
		
		Longword[] lwArr = new Longword[instructions.length / 2];	//combine 2 instructions to a longword which will 
																		//be write in to memory
		
		
		for(int i = 0; i < lwArr.length; i++){
			lwArr[i] = new Longword();
			
			for(int j = 0; j < 16; j++){
				
				lwArr[i].set_Bit(j, new Bit(Character.getNumericValue(instructions[2 * i].charAt(j))));		//since read from 1 3 5
				lwArr[i].set_Bit(j + 16, new Bit(Character.getNumericValue(instructions[2 * i + 1].charAt(j))));	//since read from 2 4 6
			}
		}
		
		
		Longword address = new Longword();
		for(int i = 0; i < lwArr.length; i++){
			address.set(i * 4);	
			m.write(address, lwArr[i]);
			
		}
		
	}
	
	/**
	 * If the halt value is 0, running!
	 * @throws Exception
	 */
	public void run() throws Exception{
		
		
		while(haltValue.getValue() == 1){
			fetch();
			decode();
			
			execute();
			store();
		}
	}
		
	}
	
	



