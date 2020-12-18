package du.virtualMachine;

public class TestCPU {

	public static void main(String[] args) throws Exception {
		Computer c = new Computer();
		String ins[] = {"mov r1 8 ", "push r1", "pop r5", "interrupt 0", "halt"};
		//String ins[] = {"mov r1 9", "interrupt 0", "halt"};
		String insArr[] = Assembler.assemble(ins);
		c.preload(insArr);
		c.run();

	}

}
