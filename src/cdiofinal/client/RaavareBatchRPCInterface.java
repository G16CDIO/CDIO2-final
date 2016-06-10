package cdiofinal.client;

import cdiofinal.shared.DALException;
import cdiofinal.shared.RaavareBatchDTO;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("raavareBatches")
public interface RaavareBatchRPCInterface extends RemoteService{
	
	RaavareBatchDTO getRaavareBatch(int rb_id) throws DALException;
	RaavareBatchDTO[] getRaavareBatchList();
	Integer createRaavareBatch(RaavareBatchDTO ans) throws DALException;
	Integer updateRaavareBatch(RaavareBatchDTO ans) throws DALException;
}
