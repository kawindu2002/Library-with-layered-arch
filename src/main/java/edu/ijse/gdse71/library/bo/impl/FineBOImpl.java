package edu.ijse.gdse71.library.bo.impl;

import edu.ijse.gdse71.library.bo.custom.FineBO;
import edu.ijse.gdse71.library.dao.custom.FineDAO;
import edu.ijse.gdse71.library.dto.FineDTO;
import edu.ijse.gdse71.library.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FineBOImpl implements FineBO {

    static String getNextFineId() throws SQLException {
        String query = "select Fine_Id from Fine order by Fine_Id desc limit 1";
        return CrudUtil.getNextId(query,"FN%03d","FN001");
    }

    @Override
    public boolean save(FineDTO dto) throws SQLException {
        return CrudUtil.execute(
                "insert into Fine values (?,?,?,?,?,?,?)",
                dto.getFineID(),
                dto.getUserID(),
                dto.getMemberID(),
                dto.getLoanID(),
                dto.getPrice(),
                dto.getFineDate(),
                dto.getReason()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return CrudUtil.execute("delete from Fine where Fine_Id=?", id);
    }

    @Override
    public boolean update(FineDTO dto) throws SQLException {
        return CrudUtil.execute(
                "update Fine set User_Id=?, Member_Id=?, Loan_Id=?, Price=?,Fine_date=?,Reason=? where Fine_Id=?",
                dto.getUserID(),
                dto.getMemberID(),
                dto.getLoanID(),
                dto.getPrice(),
                dto.getFineDate(),
                dto.getReason(),
                dto.getFineID()
        );
    }

    @Override
    public ArrayList<FineDTO> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Fine");

        ArrayList<FineDTO> fineDTOS = new ArrayList<>();

        while (rst.next()) {
            FineDTO fineDTO = new FineDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5),
                    rst.getDate(6),
                    rst.getString(7)
            );
            fineDTOS.add(fineDTO);
        }
        return fineDTOS;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select Fine_Id from Fine");

        ArrayList<String> fineIds = new ArrayList<>();

        while (rst.next()) {
            fineIds.add(rst.getString(1));
        }

        return fineIds;
    }

    @Override
    public FineDTO findById(String selectedId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Fine where Fine_Id=?", selectedId);

        if (rst.next()) {
            return new FineDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5),
                    rst.getDate(6),
                    rst.getString(7)
            );
        }
        return null;
    }

}
