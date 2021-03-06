package cdiofinal.shared;

import java.io.Serializable;

/**
 * Raavare Data Objekt
 * 
 * @author mn/sh/tb
 * @version 1.2
 */

public class RaavareDTO implements Serializable 
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** i omraadet 1-99999999 vaelges af brugerne */
	private int raavareId;                     
    /** min. 2 max. 20 karakterer */
	private String raavareNavn;                        
	
    public RaavareDTO(){
    	
    }
    
    public boolean isValid()
	{
		if (FieldVerifier.isValidId(this.getRaavareId())==true && FieldVerifier.isValidName(this.getRaavareNavn())==true){
			return true;
		}
		else return false;
	}
    
	public RaavareDTO(int raavareId, String raavareNavn)
	{
		this.raavareId = raavareId;
		this.raavareNavn = raavareNavn;
	}
    public int getRaavareId() { return raavareId; }
    public void setRaavareId(int raavareId) { this.raavareId = raavareId; }
    public String getRaavareNavn() { return raavareNavn; }
    public void setRaavareNavn(String raavareNavn) { this.raavareNavn = raavareNavn; }
    @Override
	public String toString() { 
		return raavareId + "\t" + raavareNavn; 
	}
}
