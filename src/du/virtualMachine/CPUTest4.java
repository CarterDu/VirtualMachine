package du.virtualMachine;

public class CPUTest4 {
	
	public void runTest4() throws Exception{
		
		System.out.println("------------------------------------Push-----------------------------");
		Computer c = new Computer();
		String ins[] = {"mov r1 5", "push r1"};
		String insArr[] = Assembler.assemble(ins);
		c.preload(insArr);
		c.run();
		Longword lw = new Longword();
		lw.set(1020);
		System.out.println(c.m.read(lw));    //the 1020's value will be 5
		
		System.out.println();
		
		System.out.println("-------------------------------------pop-----------------------------");
		Computer c9 = new Computer();
		String ins9[] = {"mov r1 9", "push r1", "pop r3", "interrupt 0"}; 
		String insArr9[] = Assembler.assemble(ins9);
		c9.preload(insArr9);
		c9.run();			//now the value of register 3 is 9
		
		System.out.println();
		
		System.out.println("------------------------------------ Call Return-----------------------------");
		Computer c3 = new Computer();
		String ins3[] = {"mov r1 9", "call 8", "halt", "interrupt 0", "interrupt 0"}; 
		String insArr3[] = Assembler.assemble(ins3);
		c3.preload(insArr3);
		c3.run();			//now the value of register 1 is 9
		
		System.out.println();
		
		System.out.println("------------------------------------ Call -----------------------------");
		Computer c2 = new Computer();
		String ins2[] = {"mov r1 9", "call 8", "interrupt 0", "halt", "return"}; //r1 = 9 and all registers equal 0
		String insArr1[] = Assembler.assemble(ins2);
		c2.preload(insArr1);
		c2.run();
		
		
		
		
		System.out.println();
		
		System.out.println("------------------------------------ Return-----------------------------");
		Computer c4 = new Computer();
		String ins4[] = {"mov r1 8", "push r1", "return","halt",  "mov r1 12", "interrupt 0", "halt"}; //r1 = r2
		String insArr4[] = Assembler.assemble(ins4);
		c4.preload(insArr4);
		c4.run();
		
		
		
	}

}
