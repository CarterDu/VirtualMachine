package du.virtualMachine;

public class CPUTest3 {
	
	public static void main(String[] args) throws Exception{
		Computer c = new Computer();
		String instruction[] = {"mov r1 -8", "mov r2 -5", "add r1 r2 r6", "interrupt 0", "halt"};
		//String ins2[] = {"0001100000011111", "0001010001001111", "1110100001001100", "0010000000000000"};
		String ins[] = Assembler.assemble(instruction);
		c.preload(ins);
		c.run();
		
		System.out.println("-------------------------------------pop-----------------------------");
		Computer c9 = new Computer();
		String ins9[] = {"mov r1 9", "push r1", "pop r3", "interrupt 0"}; 
		String insArr9[] = Assembler.assemble(ins9);
		c9.preload(insArr9);
		c9.run();			//now the value of register 3 is 9
		
		System.out.println();
	
		
		
		
		

	}

}
