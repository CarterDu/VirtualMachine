package du.virtualMachine;

/**
 * Memory in the CPU (storing content)
 * @author Carter Du
 *
 */
public class Memory {
	 Bit[] memory = new Bit[1024 * 8];	//8192 bits total memory
	
	public Memory(){
		for(int i = 0; i < memory.length; i++){
			memory[i] = new Bit(0);
		} 
	}
	
	/**
	 * Read the value in "bit format" so far from the memory
	 * @param address
	 * @return
	 */
	public Longword read(Longword address){
		Longword lw = new Longword();
		for(int i = (int)address.getUnsigned() * 8; i < (int)address.getUnsigned() * 8 + 32; i++){
				lw.bit_container[i - (int)address.getUnsigned() * 8] = memory[i];
		}
		return lw;
	}
	
	/**
	 * Write the value into the new segment of memory(address)
	 * @param address
	 * @param value
	 */
	public void write(Longword address, Longword value){
		for(int i = (int)address.getUnsigned() * 8; i < (int)address.getUnsigned() * 8 + 32; i++){
			memory[i] = value.bit_container[i - (int)address.getUnsigned() * 8];
		}
	}
}
