package edu.ijse.gdse71.library.bo.custom.impl;

import edu.ijse.gdse71.library.bo.custom.FineBO;
import edu.ijse.gdse71.library.dao.DAOFactory;
import edu.ijse.gdse71.library.dao.custom.FineDAO;
import edu.ijse.gdse71.library.dto.CategoryDTO;
import edu.ijse.gdse71.library.dto.FineDTO;
import edu.ijse.gdse71.library.entity.Category;
import edu.ijse.gdse71.library.entity.Fine;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class FineBOImpl implements FineBO {

    FineDAO fineDAO= (FineDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.FINE);

    public String getNextId() throws SQLException {
        return fineDAO.getNextId();
    }

    @Override
    public boolean save(FineDTO dto) throws SQLException {
        return fineDAO.save(new Fine(
                dto.getFineID(),
                dto.getUserID(),
                dto.getMemberID(),
                dto.getLoanID(),
                dto.getPrice(),
                dto.getFineDate(),
                dto.getReason()));
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return fineDAO.delete(id);
    }

    @Override
    public boolean update(FineDTO dto) throws SQLException {
        return fineDAO.update(new Fine(
                dto.getFineID(),
                dto.getUserID(),
                dto.getMemberID(),
                dto.getLoanID(),
                dto.getPrice(),
                dto.getFineDate(),
                dto.getReason()));
    }

    @Override
    public ArrayList<FineDTO> getAll() throws SQLException {

        ArrayList<Fine> fines = fineDAO.getAll();
        ArrayList<FineDTO> fineDTOS = new ArrayList<>();
        for (Fine fine:fines) {
            fineDTOS.add(new FineDTO(
                fine.getFineID(),
                fine.getUserID(),
                fine.getMemberID(),
                fine.getLoanID(),
                fine.getPrice(),
                fine.getFineDate(),
                fine.getReason()));
        }
        return fineDTOS;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        return fineDAO.getAllIds();

    }

    @Override
    public FineDTO findById(String selectedId) throws SQLException {
        ArrayList<Fine> fines = fineDAO.findById(selectedId);

        if (!fines.isEmpty()) {
            Fine fine = fines.get(0);
            return new FineDTO(
                fine.getFineID(),
                fine.getUserID(),
                fine.getMemberID(),
                fine.getLoanID(),
                fine.getPrice(),
                fine.getFineDate(),
                fine.getReason()
        );
        } else {
            return null;
        }
    }
}
