package du.virtualMachine;

public class TestCPU {

	public static void main(String[] args) throws Exception {
		Computer c = new Computer();


		//String ins[] = {"mov r1 1", "mov r2 4", "mov r3 1", "add r1 r3 r1", "compare r2 r1", "branchIfGreaterThan -4", "interrupt 0"};

		String ins[] = {"mov r1 9", "interrupt 2", "halt"};
		String insArr[] = Assembler.assemble(ins);
		c.preload(insArr);
		c.run();

	}

}
