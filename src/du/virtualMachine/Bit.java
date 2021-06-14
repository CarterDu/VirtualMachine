package du.virtualMachine;

public class Bit implements IBit{
	
	private int bit;

	public Bit(int bit) throws NumberFormatException{
		this.bit = bit;
		if(bit!=0 && bit!=1){
			throw new NumberFormatException("Numbers have to be 1 or 0");
		}
	}
	
	
	@Override
	public void setBit(int value) {
		bit = value;
		
		if(bit!=0 && bit!=1){
			throw new NumberFormatException("Bit's value has to be 1 or 0!");
		}
	}

	/**
	 * Change the value from 1 to 0 or 0 to 1
	 */
	@Override
	public void toggle() {
		if(this.getValue() == 0){
			this.setBit(1);
		}
		else if(this.getValue() == 1){
			this.setBit(0);
		}
	}

	/**
	 * Set the bit to 1
	 */
	@Override
	public void set() {
		this.setBit(1);
	}

	/**
	 * Set the bit to 0
	 */
	@Override
	public void clear() {
		this.setBit(0);
		
	}

	/**
	 * return the current value
	 */
	@Override
	public int getValue() {
		return bit;
	}

	/**
	 * AND GATE
	 */
	@Override
	public Bit and(Bit other) {
		
		if(this.getValue()==1 && other.getValue()==1){
			return new Bit(1);
		}
		if(this.getValue()==0 && other.getValue()==0){
			return new Bit(0);
		}
		if(this.getValue()==0 && other.getValue()==1){
			return new Bit(0);
		}
		if(this.getValue()==1 && other.getValue()==0){
			return new Bit(0);
		}
		throw new NumberFormatException("Bit's value has to be 1 or 0 in the AND operation!");
	}

	/**
	 * OR GATE
	 */
	@Override
	public Bit or(Bit other) throws NumberFormatException{
		if(this.getValue()==1 && other.getValue()==0){
			return new Bit(1);
		}
		else if(this.getValue()==0 && other.getValue()==1){
			return new Bit(1);
		}
		else if(this.getValue()==1 && other.getValue()==1){
			return new Bit(1);
		}
		else if(this.getValue()==0 && other.getValue()==0){
			return new Bit(0);
		}
		else
			throw new NumberFormatException("Bit's value has to be 1 or 0 in the OR operation!");
	}

	
	/**
	 * XOR GATE
	 */
	@Override
	public Bit xor(Bit other) throws NumberFormatException{
		
		if(this.getValue()==0 && other.getValue()==0){
			return new Bit(0);
		} else if(this.getValue() == 1 && other.getValue() == 1) {
			return new Bit(0);
		} else if(this.getValue() == 1 && other.getValue() == 0) {
			return new Bit(1);

		} else if(this.getValue() == 0 && other.getValue() == 1) {
			return new Bit(1);
		}
		else
			throw new NumberFormatException("Bit's value has to be 1 or 0 in the XOR operation!");
	}

	
	/**
	 * NOT GATE
	 */
	@Override
	public Bit not() {
		if(this.getValue() == 1){
			return new Bit(0);
		}
		else if(this.getValue() == 0){
			return new Bit(1);
		}
		else
			throw new NumberFormatException("Bit value has to be 1 or 0 in the NOT operation!");
	}
	
	/**
	 * represent the value of bit as 1 or 0
	 */
	public String toString(){
		
		if(this.bit == 1){
			return "1";
		}
		if(this.bit == 0){
			return "0";
		}
		return null;
	}
}
