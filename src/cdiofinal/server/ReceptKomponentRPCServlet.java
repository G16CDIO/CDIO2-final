package cdiofinal.server;

import java.util.List;

import cdiofinal.client.ReceptKomponentRPCInterface;
import cdiofinal.shared.DALException;
import cdiofinal.shared.ReceptKompDTO;

public class ReceptKomponentRPCServlet extends ValidationServlet implements ReceptKomponentRPCInterface{
	private static final long serialVersionUID = 1L;
	MySQLReceptKompDAO database = new MySQLReceptKompDAO();
	final String fail ="Kunne ikke %s , receptkomponenten tjek informationerne igen.";

	@Override
	public ReceptKompDTO getReceptKomp(int recId, int raavareId, String token) throws Exception{
			if(isValid(token, 2))
				return database.getReceptKomp(recId, raavareId);
		return null;
	}

	@Override
	public ReceptKompDTO[] getReceptKompList(int recept, String token) throws Exception{
			if(isValid(token, 2)){
				List<ReceptKompDTO> receptkomp = database.getReceptKompList(recept);
				ReceptKompDTO[] receptKompArray = new ReceptKompDTO[receptkomp.size()];
				return receptkomp.toArray(receptKompArray);
			}
		return null;	
	}

	@Override
	public ReceptKompDTO createReceptKomp(ReceptKompDTO recKomp, String token) throws Exception{
		
		if(recKomp.isValid()){
			if(isValid(token, 2)){
					database.createReceptKomp(recKomp);
					return recKomp;
					}
		}
		else {
			throw new DALException(String.format(fail, "oprette"));
		}
		return null;
	}

	@Override
	public Integer updateReceptKomp(ReceptKompDTO recKomp, String token) throws Exception{
		if(recKomp.isValid()){
			if(isValid(token, 2)){
				return database.updateReceptKomp(recKomp);
			}
		}
		else {
			throw new DALException(String.format(fail, "opdatere"));
		}
		return 0;
	}
}
