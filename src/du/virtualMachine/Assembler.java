package du.virtualMachine;


/**
 * Assembler: convert text instruction to binary code
 * @author Carter Du
 *
 */
public class Assembler {
	
	/**
	 * text --> string of binary code --> preload()
	 * @param instruction
	 * @return
	 */
	  public static String[] assemble(String[] instruction){
		  	String ins[] = {};	//store the instruction segment, like "move",  "r1", "4"; length: 3-4
		    String result[] = new String[instruction.length];
			for(int i = 0; i < instruction.length; i++)
			{
				ins = instruction[i].split(" ");
				if(ins[0].equals("add")) 
				{
					result[i] = "1110".concat(registerNameToBinary(ins[1])). 
							concat(registerNameToBinary(ins[2])).
							concat(registerNameToBinary(ins[3]));
				}
				else if(ins[0].equals("sub")) 
				{
					result[i] = "1111".concat(registerNameToBinary(ins[1])). 
							concat(registerNameToBinary(ins[2])).
							concat(registerNameToBinary(ins[3]));
				}
				else if(ins[0].equals("mul")) 
				{
					
					result[i] = "0111".concat(registerNameToBinary(ins[1])). 
							concat(registerNameToBinary(ins[2])).
							concat(registerNameToBinary(ins[3]));
				}
				else if(ins[0].equals("and")) 
				{
					
					result[i] = "1000".concat(registerNameToBinary(ins[1])). 
							concat(registerNameToBinary(ins[2])).
							concat(registerNameToBinary(ins[3]));
				}
				else if(ins[0].equals("or")) 
				{
					
					result[i] = "1001".concat(registerNameToBinary(ins[1])). 
							concat(registerNameToBinary(ins[2])).
							concat(registerNameToBinary(ins[3]));
				}
				else if(ins[0].equals("xor")) 
				{
					
					result[i] = "1010".concat(registerNameToBinary(ins[1])). 
							concat(registerNameToBinary(ins[2])).
							concat(registerNameToBinary(ins[3]));
				}
				else if(ins[0].equals("not")) 
				{
					
					result[i] = "1011".concat(registerNameToBinary(ins[1])). 
							concat(registerNameToBinary(ins[2])).
							concat(registerNameToBinary(ins[3]));
				}
				
				else if(ins[0].equals("interrupt")) 
				{
					
					result[i] = "0010".concat("00000000").
							concat(addZeroAfterRegisterValue(ins[1]));
				}
				else if(ins[0].equals("halt")) 
				{
					
					result[i] = "0000".concat("000000000000");
							
				}
				
				else if(ins[0].equals("mov")) 
				{
					
					result[i] = "0001".concat(registerNameToBinary(ins[1])).
							concat(addZeroAfterValue(ins[2]));
				}
				else if(ins[0].equals("jump")) 
				{
					
					result[i] = "0011".concat(addZeroAfterValueWhenJump(ins[1]));
				}

				else if(ins[0].equals("compare")){
					result[i] = "01000000".concat(registerNameToBinary(ins[1])).concat(registerNameToBinary(ins[2]));
				}

				else if(ins[0].equals("branchIfGreaterThan")) 
				{
					
					result[i] = "010110".concat(addZeroAfterValueWhenBranch(ins[1]));
				}
				else if(ins[0].equals("branchIfGreaterThanOrEqual")) 
				{
					
					result[i] = "010111".concat(addZeroAfterValueWhenBranch(ins[1]));
				}
				else if(ins[0].equals("branchIfEqual")) 
				{
					
					result[i] = "010101".concat(addZeroAfterValueWhenBranch(ins[1]));
				}
				else if(ins[0].equals("branchIfNotEqual")) 
				{
					
					result[i] = "010100".concat(addZeroAfterValueWhenBranch(ins[1]));
				}
				
				else if(ins[0].equals("push"))
				{
					result[i] = "011000000000".concat(registerNameToBinary(ins[1]));
				}
				
				else if(ins[0].equals("pop"))
				{
					result[i] = "011001000000".concat(registerNameToBinary(ins[1]));
				}
				
				else if(ins[0].equals("call"))
				{
					result[i] = "011010".concat(addZeroAfterValueWhenCall(ins[1]));
				}
				
				else if(ins[0].equals("return"))
				{
					result[i] = "0110110000000000";
				}
				
				else
				{
					System.out.println("CPU currently does not recognize this kind of instruction!");
				}
			}
		
		return result;
	}
	  
	  /**
	   * example: r5 -- > 1010(5 in binary)
	   * @param name
	   * @return
	   */
	  public static String registerNameToBinary(String name)
	  {
		  
		  name = (addZeroAfterRegisterValue(name.substring(1)));
		  
		  return name;
	  }
	
	
	
	  /**
	   * ex: 4 --> 001 --> 0010 
	   * Formating the number of string to 4 bit by adding zero behind
	   * @param str
	   * @return
	   */
	public static String addZeroAfterRegisterValue(String str){
		int length = Integer.toBinaryString(Integer.parseInt(str)).length();
		if(length== 1)
		{
			str = new StringBuilder(Integer.toBinaryString(Integer.parseInt(str))).reverse() + "000";
			return str;
		}
		else if(length == 2)
		{
			str = new StringBuilder(Integer.toBinaryString(Integer.parseInt(str))).reverse() + "00";
			return str;
		}
		else if(length == 3)
		{
			str = new StringBuilder(Integer.toBinaryString(Integer.parseInt(str))).reverse() + "0";
			return str;
		}
		else if(length == 4)
		{
			str = new StringBuilder(Integer.toBinaryString(Integer.parseInt(str))).reverse() + "";
			return str;
		}
		else{
			System.out.println("The register's number needs to set between 0 to 15 only!");
		}
		return null;
		
	}
	
	
	/**
	 * ex: 4 --> 001 --> 00100000
	 * Formating the number of string to 8 bit when encounter the move instruction by adding zero behind
	 * @param str
	 * @return
	 */
	public static String addZeroAfterValue(String str){
		int length = Integer.toBinaryString(Integer.parseInt(str)).length();
		if(length== 1)
		{
			str = new StringBuilder(Integer.toBinaryString(Integer.parseInt(str))).reverse() + "0000000";
			return str;
		}
		else if(length == 2)
		{
			str = new StringBuilder(Integer.toBinaryString(Integer.parseInt(str))).reverse() + "000000";
			return str;
		}
		else if(length == 3)
		{
			str = new StringBuilder(Integer.toBinaryString(Integer.parseInt(str))).reverse() + "00000";
			return str;
		}
		else if(length == 4)
		{
			str = new StringBuilder(Integer.toBinaryString(Integer.parseInt(str))).reverse() + "0000";
			return str;
		}

		else if(length == 5)
		{
			str = new StringBuilder(Integer.toBinaryString(Integer.parseInt(str))).reverse() + "000";
			return str;
		}

		else if(length == 6)
		{
			str = new StringBuilder(Integer.toBinaryString(Integer.parseInt(str))).reverse() + "00";
			return str;
		}

		else if(length == 7)
		{
			str = new StringBuilder(Integer.toBinaryString(Integer.parseInt(str))).reverse() + "0";
			return str;
		}

		else if(length == 9)
		{
			str = new StringBuilder(Integer.toBinaryString(Integer.parseInt(str))).reverse() + "";
			return str;
		}

		else if(Integer.parseInt(str) < 0)	//when the number is negative
		{
			str = new StringBuilder(Integer.toBinaryString(Integer.parseInt(str))).reverse().substring(0, 8);
			return str;
		}
		else{
			System.out.println("Check your value format! It must be an integer! And The length of the integer should be in 0 to 8!");
		}
		return null;
		
	}
	
	
	
	/**
	 * ex: jump 4: 0011 001(0 0000 0000)
	 * @param str
	 * @return
	 */
	public static String addZeroAfterValueWhenJump(String str){
		int length = Integer.toBinaryString(Integer.parseInt(str)).length();
		if(length== 1)
		{
			str = new StringBuilder(Integer.toBinaryString(Integer.parseInt(str))).reverse() + "00000000000";
			return str;
		}
		else if(length == 2)
		{
			str = new StringBuilder(Integer.toBinaryString(Integer.parseInt(str))).reverse() + "0000000000";
			return str;
		}
		else if(length == 3)
		{
			str = new StringBuilder(Integer.toBinaryString(Integer.parseInt(str))).reverse() + "000000000";
			return str;
		}
		else if(length == 4)
		{
			str = new StringBuilder(Integer.toBinaryString(Integer.parseInt(str))).reverse() + "00000000";
			return str;
		}
		else if(Integer.parseInt(str) < 0)	//when the number is negative
		{
			str = new StringBuilder(Integer.toBinaryString(Integer.parseInt(str))).reverse().substring(0, 10);
		}
		else{
			System.out.println("Make sure the jump value is the integer!");
		}
		return null;
		
	}
	
	/**
	 * ex: call 4: 0110 10(00 1000 0000)
	 * @param str
	 * @return
	 */
	public static String addZeroAfterValueWhenCall(String str){
		int length = Integer.toBinaryString(Integer.parseInt(str)).length();
		if(length== 1)
		{
			str = new StringBuilder(Integer.toBinaryString(Integer.parseInt(str))).reverse() + "000000000";
			return str;
		}
		else if(length == 2)
		{
			str = new StringBuilder(Integer.toBinaryString(Integer.parseInt(str))).reverse() + "00000000";
			return str;
		}
		else if(length == 3)
		{
			str = new StringBuilder(Integer.toBinaryString(Integer.parseInt(str))).reverse() + "0000000";
			return str;
		}
		else if(length == 4)
		{
			str = new StringBuilder(Integer.toBinaryString(Integer.parseInt(str))).reverse() + "000000";
			return str;
		}
		
		else{
			System.out.println("make sure the call value is the positive integer!");
		}
		return null;
		
	}
	
	/**
	 * ex: branchIfGreaterThan 5 --> 0101 10 1(010 0000 00)
	 * @param str
	 * @return
	 */
	public static String addZeroAfterValueWhenBranch(String str){
		int length = Integer.toBinaryString(Integer.parseInt(str)).length();
		if(length== 1)
		{
			str = new StringBuilder(Integer.toBinaryString(Integer.parseInt(str))).reverse() + "000000000";
			return str;
		}
		else if(length == 2)
		{
			str = new StringBuilder(Integer.toBinaryString(Integer.parseInt(str))).reverse() + "00000000";
			return str;
		}
		else if(length == 3)
		{
			str = new StringBuilder(Integer.toBinaryString(Integer.parseInt(str))).reverse() + "0000000";
			return str;
		}
		else if(length == 4)
		{
			str = new StringBuilder(Integer.toBinaryString(Integer.parseInt(str))).reverse() + "000000";
			return str;
		}
	
		else if(Integer.parseInt(str) < 0)	//when the number is negative
		{
			str = new StringBuilder(Integer.toBinaryString(Integer.parseInt(str))).reverse().substring(0, 10);
			return str;
		}
		else{
			System.out.println("Check your value format! It must be an integer!");
		}
		return null;
		
	}
	
	
	
	
	
	


}
