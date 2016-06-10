package cdiofinal.server;
import java.util.List;

import com.google.gwt.dev.protobuf.UnknownFieldSet.Field;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import cdiofinal.client.RaavareRPCInterface;
import cdiofinal.server.MySQLRaavareDAO;
import cdiofinal.shared.DALException;
import cdiofinal.shared.FieldVerifier;
import cdiofinal.shared.RaavareDTO;

public class RaavareRPCServlet extends RemoteServiceServlet implements RaavareRPCInterface {

	private static final long serialVersionUID = 1L;
	MySQLRaavareDAO database = new MySQLRaavareDAO();
	
	
	@Override
	public RaavareDTO getRaavare(int raavare_id) throws DALException{
		try {
			return database.getRaavare(raavare_id);
		} catch (DALException e) {
			throw e;
		}
	}

	@Override
	public RaavareDTO[] getRaavareList() {
					
					try {
						List<RaavareDTO> raavare = database.getRaavareList();
						RaavareDTO[] raavareArray = new RaavareDTO[raavare.size()];
						return raavare.toArray(raavareArray);
					} catch (DALException e) {
						return null;
					}			
	}

	@Override
	public Integer createRaavare(RaavareDTO ans) throws DALException {
		if(FieldVerifier.isValidId(ans.getRaavareId())==true || FieldVerifier.isValidName(ans.getRaavareNavn())==true)
		try {
		return database.createRaavare(ans);
		} catch (DALException e){
			throw e;
		}
		return 0;
	}

	@Override
	public Integer updateRaavare(RaavareDTO ans) throws DALException{
		if(FieldVerifier.isValidId(ans.getRaavareId())==true || FieldVerifier.isValidName(ans.getRaavareNavn())==true)
		try {
			return database.updateRaavare(ans);
			} catch (DALException e){
				throw e;
			}
			return 0;
		
	}

	public static void main(String[] args) {
		RaavareRPCServlet servlet = new RaavareRPCServlet();
		for (RaavareDTO string : servlet.getRaavareList()) {
			System.out.println(string);
		}
		
    }
	
}