package du.virtualMachine;

import java.util.*;

/**
 * Longword class
 * @author Carter Du
 *
 */
public class Longword implements ILongword{
	
	public Bit[] bit_container = new Bit[32];	//build a collection of bit for the longword
	
	public Longword(){
		for(int i = 0; i < 32; i++){
			bit_container[i] = new Bit(0);
		}
		
	}
	/**
	 * Get the bit i
	 */
	@Override
	public Bit getBit(int i) {
		return bit_container[i];
	}

	/**
	 * Set the bit i's value
	 */
	@Override
	public void set_Bit(int i, Bit value) {
		bit_container[i].setBit(value.getValue());
	}

	/**
	 * AND gate
	 */
	@Override
	public Longword and(Longword other) {
		Longword lw = new Longword();
		for(int i = 0; i < 32; i++){
			lw.bit_container[i] = bit_container[i].and(other.bit_container[i]);
		}
		return lw;
	}

	/**
	 * OR gate
	 */
	@Override
	public Longword or(Longword other) {
		Longword lw = new Longword();
		for(int i = 0; i < other.bit_container.length; i++){
			lw.bit_container[i] = other.bit_container[i].or(this.getBit(i));
		}
		return lw;
	}

	/**
	 * XOR gate
	 */
	@Override
	public Longword xor(Longword other) {
		Longword lw = new Longword();
		for(int i = 0; i < other.bit_container.length; i++){
			lw.bit_container[i] = other.bit_container[i].xor(this.getBit(i));
		}
		return lw;
	}

	/**
	 * NOT gate
	 */
	@Override
	public Longword not() {
		Longword lw = new Longword();
		for(int i = 0; i < this.bit_container.length; i++){
			lw.bit_container[i] = bit_container[i].not();
		}
		return lw;
	}

	/**
	 * Right shifting the longword by amount of indexes, and return a new longword
	 * @throws Exception 
	 */
	@Override
	public Longword rightShift(int amount) throws Exception {
		if(amount < 0){
			throw new Exception("Amount number can not be negative to shift!");
		}
		Longword lw = new Longword();
		for(int i = amount, j = 0; i < 32; i++, j++){
			lw.set_Bit(j, bit_container[i]);
		}
		return lw;
	}

	/**
	 * Left shifting the longword by amount of indexes, and return a new longword
	 * power of 2
	 * @throws Exception 
	 */
	@Override
	public Longword leftShift(int amount) throws Exception {
		if(amount < 0){
			throw new Exception("Amount number can not be negative to shift!");
		}
		Longword lw = new Longword();
		for(int i = 0, j = amount; i < 32-amount; j++,i++){
			lw.set_Bit(j, bit_container[i]);
		}
		return lw;
	}

	/**
	 * Return the value of this longword as a long
	 */
	@Override
	public long getUnsigned() {
		long val = 1;
		long sum = 0;
		for(int i = 0; i < 32; i++){
			if(this.bit_container[i].getValue() == 1){
				sum = sum + val;
			}
			val = val * 2;
		}
		return sum;
	}

	/**
	 * Return the value of this longword as int
	 * not, and add 1 ---> 2's complement
	 */
	@Override
	public int getSigned() {
		Longword lw = new Longword();
		int result = 0;
		int pow = 1;
		if(bit_container[31].getValue() == 1){
			for(int i = 0; i< 31; i++){
				lw.bit_container[i] = bit_container[i].not();
			}
			
			Longword lw2 = new Longword();
			lw2.set(1);
			Longword lw3 = RippleAdder.add(lw, lw2);
	
			for(int j = 0; j < 32; j++){
				if(lw3.bit_container[j].getValue() == 1){
					result = result + pow;
				}
				pow = pow * 2;
			}
			result = (-1)* result;
			}
		
		else{
			for(int i = 0; i < 32; i++){
				
				if(this.bit_container[i].getValue() == 1){
					result = result + pow;
				}
				pow = pow * 2;
			}
		}
		return result;
	}

	/**
	 * Copy the value of bit from another longword into this one
	 */
	@Override
	public void copy(Longword other) {

		for(int i = 0; i < other.bit_container.length; i++){
			this.bit_container[i] = other.getBit(i);
		}
		
	} 

	/**
	 * Set the value of the bits of this longword (use for tests)
	 * 
	 */
	@Override
	public void set(int value) {
		int power = 0;
		int result = value;
		for(int i = 31; i >= 0; i--){
			power = (int) Math.pow(2, i);
			if(result < power){
				set_Bit(i, new Bit(0));
				
			}
		
			else if(result >= power){
				set_Bit(i, new Bit(1));
				result = result - power;
			}
		}
		if(value < 0){
			result = Math.abs(result);
			for(int i = 31; i >= 0; i--){
				power = (int) Math.pow(2, i);
				if(result < power){
					set_Bit(i, new Bit(1));
				}
			
				else if(result >= power){
					set_Bit(i, new Bit(0));
					result = result - power;
				}
			}
			Longword lw = new Longword();
			lw.set(1);
			this.copy(RippleAdder.add(this, lw));
		}
	}

	/**
	 * returns a comma separated string of 0's and 1's: "0,0,0,0,0 (etcetera)" for example
	 */
	public String toString(){
		String str = "";
		str = Arrays.toString(this.bit_container);
		
		return str;
		
	}
}
