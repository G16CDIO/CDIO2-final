package cdiofinal.server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cdiofinal.server.Connector;
import cdiofinal.server.DALException;
import cdiofinal.server.ProduktBatchDAO;
import cdiofinal.shared.ProduktBatchDTO;

public class MySQLProduktbatchDAO implements ProduktBatchDAO {

	@Override
	public ProduktBatchDTO getProduktBatch(int pbId) throws DALException {
		ResultSet rs = Connector.doQuery("SELECT * FROM produktbatch WHERE pb_id = " + pbId + ";");
	    try {
	    	if (!rs.first()) throw new DALException("Det produktbatch med pb_id'et " + pbId + " findes ikke");
	    	return new ProduktBatchDTO (rs.getInt("pb_id"), rs.getInt("status"), rs.getInt("recept_id"));
	    }
	    catch (SQLException e) {throw new DALException(e); }
		
	}
	

	@Override
	public List<ProduktBatchDTO> getProduktBatchList() throws DALException {
		List<ProduktBatchDTO> list = new ArrayList<ProduktBatchDTO>();
		ResultSet rs = Connector.doQuery("SELECT * FROM produktbatch;");
		try
		{
			while (rs.next()) 
			{
				list.add(new ProduktBatchDTO(rs.getInt("pb_id"), rs.getInt("status"), rs.getInt("recept_id")));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}

	@Override
	public int createProduktBatch(ProduktBatchDTO ans) throws DALException {
		return Connector.doUpdate(
				"INSERT INTO produktbatch(pb_id, status, recept_id) VALUES " +
				"(" + ans.getPbId() + ", " + ans.getStatus() + ", " + ans.getReceptId() + ");"
			);
		
	}

	@Override
	public int updateProduktBatch(ProduktBatchDTO ans) throws DALException {
		return Connector.doUpdate(
				"UPDATE produktbatch SET  pb_id = " + ans.getPbId() + ", status =  " + ans.getStatus() + 
				", recept_id = " + ans.getReceptId() + " WHERE pb_id = " + ans.getPbId() + ";"
				);
		
	}

}
