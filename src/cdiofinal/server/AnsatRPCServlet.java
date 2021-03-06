package cdiofinal.server;
import java.util.List;

import com.google.gwt.user.client.Window;

import cdiofinal.shared.DALException;
import cdiofinal.client.AnsatRPCInterface;
import cdiofinal.server.MySQLAnsatDAO;
import cdiofinal.shared.AnsatDTO;
import cdiofinal.shared.FieldVerifier;
import cdiofinal.server.ValidationServlet;

public class AnsatRPCServlet extends ValidationServlet implements AnsatRPCInterface {

	private static final long serialVersionUID = 1L;
	private MySQLAnsatDAO database = new MySQLAnsatDAO();
	private final String fail ="Kunne ikke %s , den ansatte tjek informationerne igen.";


	@Override
	public AnsatDTO getAnsat(String cpr, String token) throws Exception {
			if(isValid(token, 3)){
				return database.getAnsat(cpr);
			}
			return null;
	}

	@Override
	public AnsatDTO[] getAnsatList(String token) throws Exception{
			if(isValid(token, 3)){
				List<AnsatDTO> ansatte = database.getAnsatList();
				AnsatDTO[] ansatteArray = new AnsatDTO[ansatte.size()];
				return ansatte.toArray(ansatteArray);
			}
			return null;
	}

	@Override
	public AnsatDTO createAnsat(AnsatDTO ans, String token) throws Exception{
		if(ans.isValid())
		{
				if(isValid(token, 3)){
					database.createAnsat(ans);
					return ans;
				}
		}
		else
		{
			throw new DALException(String.format(fail, "oprette"));
		}
		return null;
	}

	@Override
	public Integer updateAnsat(AnsatDTO ans, String token) throws Exception{
	if(ans.isValid())
		{
				if(isValid(token, 3)){
					return database.updateAnsat(ans);
				}
	}
	else
	{
		throw new DALException(String.format(fail, "opdatere"));
	}
		return 0 ;

	}

	@Override
	public Integer deleteAnsat(AnsatDTO ans, String token) throws Exception{
//Legacy code. You should not be able to delete users.
//				if(isValid(token, 3)){
//					return database.deleteAnsat(ans);	
//				}
//			
//		return 0;
		return -1;
	}

}
