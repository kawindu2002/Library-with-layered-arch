package edu.ijse.gdse71.library.dao.custom.impl;

import edu.ijse.gdse71.library.dao.custom.MemberDAO;
import edu.ijse.gdse71.library.dto.MemberDTO;
import edu.ijse.gdse71.library.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MemberDAOImpl implements MemberDAO {

    public String getNextId()  throws SQLException {
        String query = "select Member_Id from Member order by Member_Id desc limit 1";
        return CrudUtil.getNextId(query,"ME%03d","ME001");
    }

    @Override
    public boolean save(MemberDTO dto) throws SQLException {
        return CrudUtil.execute(
                "insert into Member values (?,?,?,?,?,?,?)",
                dto.getMemberID(),
                dto.getName(),
                dto.getAddress(),
                dto.getEmail(),
                dto.getContact(),
                dto.getRegDate(),
                dto.getState()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return CrudUtil.execute("delete from Member where Member_Id=?", id);
    }

    @Override
    public boolean update(MemberDTO dto) throws SQLException {
        return CrudUtil.execute(
                "update Member set Name=?, Address=?, Email=?, Contact=?,Reg_date=?,State=? where Member_Id=?",
                dto.getName(),
                dto.getAddress(),
                dto.getEmail(),
                dto.getContact(),
                dto.getRegDate(),
                dto.getState(),
                dto.getMemberID()
        );
    }

    @Override
    public ArrayList<MemberDTO> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Member");

        ArrayList<MemberDTO> memberDTOS = new ArrayList<>();

        while (rst.next()) {
            MemberDTO memberDTO = new MemberDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getDate(6),
                    rst.getString(7)
            );
            memberDTOS.add(memberDTO);
        }
        return memberDTOS;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select Member_Id from Member");

        ArrayList<String> memberIds = new ArrayList<>();

        while (rst.next()) {
            memberIds.add(rst.getString(1));
        }

        return memberIds;
    }

    @Override
    public MemberDTO findById(String selectedId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Member where Member_Id=?", selectedId);

        if (rst.next()) {
            return new MemberDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getDate(6),
                    rst.getString(7)
            );
        }

        return null;
    }

    @Override
    public String getState(String id) throws SQLException {
        String query = "select State from Member where Member_Id = ?";
        return CrudUtil.getState(query,id);
    }
}
