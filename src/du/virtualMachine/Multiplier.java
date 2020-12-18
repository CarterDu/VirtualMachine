package du.virtualMachine;




/**
 * Contain the method of multiply and ALU functions
 * @author Carter Du
 *
 */
public class Multiplier {
	
	
	
	/**
	 * 1. Set an longword array to store longwords
	 * 2. During the multiplication, assign each longword within certain condition and add them to the longword array
	 * 3. Loop 16 time when adding all the longword inside of the longword array, and the result will be it
	 * @param a
	 * @param b
	 * @return a new longword lw
	 * @throws Exception 
	 */
	public static Longword multiply(Longword a, Longword b) throws Exception{
		
		Longword lw = new Longword();

		for(int i = 0; i < 32; i++){
			if(b.bit_container[i].getValue() == 1){
				lw = RippleAdder.add(a.leftShift(i), lw);
			}
			
		}
		
		
		return lw;
		
	}
	
	/**
	 * ALU Design
	 * By entering 4 bits to do the logical operations
	 * @param operation
	 * @param a
	 * @param b
	 * @return
	 * @throws Exception 
	 */
	public static Longword doOp(Bit[] operation, Longword a, Longword b) throws Exception{
		Longword lw = new Longword();
		
		//and 1000
		if((operation[0].getValue() == 1) && (operation[1].getValue() == 0) && (operation[2].getValue() == 0) && (operation[3].getValue() == 0))
		{
			lw = a.and(b);
			
		}
		//or 1001
		else if((operation[0].getValue() == 1) && (operation[1].getValue() == 0) && (operation[2].getValue() == 0) && (operation[3].getValue() == 1))
		{
			lw = a.or(b);
		}
		//xor 1010
		else if((operation[0].getValue() == 1) && (operation[1].getValue() == 0) && (operation[2].getValue() == 1) && (operation[3].getValue() == 0))
		{
			lw = a.xor(b);
		}
		
		//not 1011
		else if((operation[0].getValue() == 1) && (operation[1].getValue() == 0) && (operation[2].getValue() == 1) && (operation[3].getValue() == 1))
		{
			lw = a.not();
		}
		
		//leftshift 1100
		else if((operation[0].getValue() == 1) && (operation[1].getValue() == 1) && (operation[2].getValue() == 0) && (operation[3].getValue() == 0))
		{
			
			lw = a.leftShift(b.getSigned());
			
		}
		
		//rightshift 1101
		else if((operation[0].getValue() == 1) && (operation[1].getValue() == 1) && (operation[2].getValue() == 0) && (operation[3].getValue() == 1))
		{
			
			lw = a.rightShift(b.getSigned());
		}
		
		//add 1110
		else if((operation[0].getValue() == 1) && (operation[1].getValue() == 1) && (operation[2].getValue() == 1) && (operation[3].getValue() == 0))
		{
			lw = RippleAdder.add(a, b);
		}
		
		//subtract 1111
		else if((operation[0].getValue() == 1) && (operation[1].getValue() == 1) && (operation[2].getValue() == 1) && (operation[3].getValue() == 1))
		{
			lw = RippleAdder.substract(a, b);
		}
		
		//multiply 0111
		else if((operation[0].getValue() == 0) && (operation[1].getValue() == 1) && (operation[2].getValue() == 1) && (operation[3].getValue() == 1))
		{
			lw = Multiplier.multiply(a, b);
		}
		
		else if((operation[0].getValue() == 0) && (operation[1].getValue() == 0) && (operation[2].getValue() == 0) && (operation[3].getValue() == 0))
		{
			
		}
		
		else{
			System.out.println("Warning: Operation code are in 4 digit, or you might type it wrong!");
		}
		return lw;
	}
	
	
	
}
