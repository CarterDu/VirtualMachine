package du.virtualMachine;

/**
 * Unit test for jump, branch, compare
 * @author Carter Du
 *
 */
public class CPUTest2 {
	
	public void runTest() throws Exception
	{
		System.out.println("------------------------------Unit Test For Jump-------------------------------");
		Computer c = new Computer();
		String jump_ins[] = {"jump 4", "mov r1 5", "interrupt 0", "halt"};  
		String array[] = Assembler.assemble(jump_ins);
		c.preload(array);
		c.run();
		
		System.out.println();
		
		
		System.out.println("------------------------------Unit Test For BranchIfGreaterThan-------------------------------");
		Computer c2 = new Computer();
		String branchIfGreaterThan_ins[] = {"mov r1 1", "mov r2 4", "mov r3 1", "add r1 r3 r1", "compare r2 r1", "branchIfGreaterThan -4", "interrupt 0"};  
		String array2[] = Assembler.assemble(branchIfGreaterThan_ins);
		c2.preload(array2);
		c2.run();
		
		System.out.println();
		
		System.out.println("------------------------------Unit Test For BranchIfGreaterThanOrEqual-------------------------------");
		Computer c4 = new Computer();
		String branchIfGreaterThanOrEqual_ins[] = {"mov r1 1", "mov r2 4", "mov r3 1", "add r1 r3 r1", "compare r2 r1", "branchIfGreaterThanOrEqual -4", "interrupt 0"};  
		String array4[] = Assembler.assemble(branchIfGreaterThanOrEqual_ins);
		c4.preload(array4);
		c4.run();
		
		System.out.println();
		
		System.out.println("------------------------------Unit Test For BranchIfNotEqual-------------------------------");
		Computer c1 = new Computer();
		String branchIfNotEqual_ins[] = {"mov r2 20", "mov r3 20", "add r2 r2 r2", "compare r2 r3", "branchIfNotEqual -4", "interrupt 0"};  
		String array1[] = Assembler.assemble(branchIfNotEqual_ins);
		c1.preload(array1);
		c1.run();
		

		
		System.out.println("------------------------------Unit Test For BranchIfEqual-------------------------------");
		Computer c3 = new Computer();
		String branchIfEqual_ins[] = {"mov r2 20", "mov r3 20", "add r2 r2 r2", "compare r2 r3", "branchIfEqual -4", "interrupt 0"};  
		String array3[] = Assembler.assemble(branchIfEqual_ins);
		c3.preload(array3);
		c3.run();
		

	}

}
