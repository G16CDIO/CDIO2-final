package cdiofinal.shared;

/**
 * <p>
 * FieldVerifier validates that the name the user enters is valid.
 * </p>
 * <p>
 * This class is in the <code>shared</code> package because we use it in both
 * the client code and on the server. On the client, we verify that the name is
 * valid before sending an RPC request so the user doesn't have to wait for a
 * network round trip to get feedback. On the server, we verify that the name is
 * correct to ensure that the input is correct regardless of where the RPC
 * originates.
 * </p>
 * <p>
 * When creating a class that is used on both the client and the server, be sure
 * that all code is translatable and does not use native JavaScript. Code that
 * is not translatable (such as code that interacts with a database or the file
 * system) cannot be compiled into client-side JavaScript. Code that uses native
 * JavaScript (such as Widgets) cannot be run on the server.
 * </p>
 */
public class FieldVerifier {

	/**
	 * Verifies that the specified name is valid for our service.
	 * 
	 * In this example, we only require that the name is at least four
	 * characters. In your application, you can use more complex checks to ensure
	 * that usernames, passwords, email addresses, URLs, and other fields have the
	 * proper syntax.
	 * 
	 * @param name the name to validate
	 * @return true if valid, false if invalid
	 */
	
	public static boolean isAlphabetic(String name) {
//		String check = name 
		if(name.matches("[a-\u00E5]+"))
		{
			return true;
		}
		else return false;
	}
	
	public static boolean isValidName(String name) {
		if (name == null) 
		{
			return false;
		}
		else if (name.length() < 2 || name.length() > 20)
		{
			return false;
		}	
		else return true;
	}
	
	public static boolean isNumber(String id) {
		if(!id.matches("[0-9]+"))
		{
			return false;
		}
		else return true;
	}
	
	public static boolean containsNumbers(String name) {
		boolean containsNumbers = false;
		for (char c:name.toCharArray())
		{
			if(Character.isDigit(c))
			{
				containsNumbers = true;
			}
		}
		if (containsNumbers = true){
			return true;
		}
		else return false;
	}
	
	public static boolean isValidNomNetto(double nom){
		if (nom < 0.05 || nom > 20.0)
		{
			return false;
		}
		
		else return true;
	}
	
	public static boolean isValidTolerance(double tol){
		if (tol < 0.1 || tol > 10)
		{
			return false;
		}
		else return true;
	}

	public static boolean isValidCpr (String cpr){
		if (cpr == null) {
			return false;
		} 
		else if(!cpr.matches("\\d+"))
		{
			return false;
		}
		else if (cpr.length() != 10)
		{
			return false;
		}
		else
		{
			String date = cpr.substring(0, 2);
			String month = cpr.substring(2, 4);
			if(Integer.parseInt(date) > 31 || Integer.parseInt(date)< 1)
			{
				return false;
			}
			else if(Integer.parseInt(month) > 12 || Integer.parseInt(month) < 1)
			{
				return false;
			}
			else return true;
				
		}
	}
	
	public static boolean isValidIni (String ini){
		if(ini.length()<2 || ini.length()>4)
			return false;
		else return true;
	}
	
	public static boolean isValidId (int id){
		if(id < 1){
			return false;
		}
		else if (id == 0){
			return false;
		}
		else if(id > 99999999){
			return false;
		}
		else return true;
	}
	
	public static boolean isValidPassword(String pw){
		if(pw.length()>8 || pw.length()<3)
			return false;
		else return true;
	}
	
	public static boolean isValidStatus(int s){
		if(s<1 || s>3)
		{
			return false;
		}
		else return true;
	}
	
	

}

