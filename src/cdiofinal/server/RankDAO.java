package cdiofinal.server;

import java.util.List;

import cdiofinal.shared.RankDTO;

public interface RankDAO {
	RankDTO getRank(int titel) throws DALException;
	List<RankDTO> getRankList() throws DALException;
	void createRank(RankDTO rank) throws DALException;
	void updateRank(RankDTO rank) throws DALException;
}
