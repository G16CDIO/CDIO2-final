package cdiofinal.server;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import cdiofinal.client.ProduktBatchRPCInterface;
import cdiofinal.server.DALException;
import cdiofinal.server.MySQLProduktbatchDAO;
import cdiofinal.shared.ProduktBatchDTO;

public class ProduktBatchRPCServlet extends RemoteServiceServlet implements ProduktBatchRPCInterface {

	private static final long serialVersionUID = 1L;
	MySQLProduktbatchDAO database = new MySQLProduktbatchDAO();
	
	
	@Override
	public ProduktBatchDTO getProduktBatch(int pbid) {
		try {
			return database.getProduktBatch(pbid);
		} catch (DALException e) {
			System.out.println("Failed at getProduktBatch");
		}
		return null;
	}

	@Override
	public ProduktBatchDTO[] getProduktBatchList() {
					
					try {
						List<ProduktBatchDTO> produktbatches = database.getProduktBatchList();
						ProduktBatchDTO[] produktbatchesArray = new ProduktBatchDTO[produktbatches.size()];
						return produktbatches.toArray(produktbatchesArray);
					} catch (DALException e) {
						return null;
					}			
	}

	@Override
	public Integer createProduktBatch(ProduktBatchDTO prba) {
		try {
		return database.createProduktBatch(prba);
		} catch (DALException e){
			System.out.println("Failed at createProduktBatch");
		}
		return 0;
	}

	@Override
	public Integer updateProduktBatch(ProduktBatchDTO prba) {
		try {
			return database.updateProduktBatch(prba);
			} catch (DALException e){
				System.out.println("Failed at updateProduktBatch");
			}
			return 0;
		
	}

	public static void main(String[] args) {
		ProduktBatchRPCServlet servlet = new ProduktBatchRPCServlet();
		for (ProduktBatchDTO string : servlet.getProduktBatchList()) {
			System.out.println(string);
		}
		
    }
	
}