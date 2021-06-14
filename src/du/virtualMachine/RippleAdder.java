package du.virtualMachine;


/**
 * Including method of add and substract
 * @author Carter Du
 *
 */
public class RippleAdder {

	/**
	 * a Add b
	 * Way to do: Use the full adder logic in the slide
	 * @param a
	 * @param b
	 * @return
	 */
	public static Longword add(Longword a, Longword b){
		Longword lw = new Longword();
		Bit carryBit = new Bit(0);
		for(int i = 0; i < 32; i++){
			lw.set_Bit(i, a.getBit(i).xor(b.getBit(i).xor(carryBit)));
			carryBit = a.getBit(i).and(b.getBit(i)).or((a.getBit(i).xor(b.getBit(i)).and(carryBit)));
		}
		return lw;
	}
	
	/**
	 * a Subtract b
	 * Way to do: get the negative value of b first, and then add b to a
	 * @param a
	 * @param b
	 * @return
	 */
	public static Longword subtract(Longword a, Longword b){
		Longword lw;

		b.set((-1) * b.getSigned());
		lw = RippleAdder.add(a, b);

		return lw;
	}
}
