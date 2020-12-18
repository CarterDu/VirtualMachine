package du.virtualMachine;

public interface ILongword {

	    Bit getBit(int i); // Get bit i
	    void set_Bit(int i, Bit value); // set bit i's value
	    Longword and(Longword other); // and two longwords, returning a third
	    Longword or(Longword other); // or two longwords, returning a third
	    Longword xor(Longword other);// xor two longwords, returning a third
	    Longword not(); // negate this longword, creating another
	    Longword rightShift(int amount) throws Exception; // rightshift this longword by amount bits, creating a new longword
	    Longword leftShift(int amount) throws Exception;// leftshift this longword by amount bits, creating a new longword
	    @Override
	    String toString(); // returns a comma separated string of 0's and 1's: "0,0,0,0,0 (etcetera)" for example
	    long getUnsigned(); // returns the value of this longword as a long
	    int getSigned(); // returns the value of this longword as an int
	    void copy(Longword other); // copies the values of the bits from another longword into this one
	    void set(int value); // set the value of the bits of this longword (used for tests)
	}



