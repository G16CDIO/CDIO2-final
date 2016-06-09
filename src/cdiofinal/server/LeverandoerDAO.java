package cdiofinal.server;

import java.util.List;

import cdiofinal.shared.LeverandoerDTO;

public interface LeverandoerDAO {
	LeverandoerDTO getLeverandoer(int leverandoerId) throws DALException;
	List<LeverandoerDTO> getLeverandoerList() throws DALException;
	void createLeverandoer(LeverandoerDTO leverandoer) throws DALException;
	void updateLeverandoer(LeverandoerDTO leverandoer) throws DALException;
}

