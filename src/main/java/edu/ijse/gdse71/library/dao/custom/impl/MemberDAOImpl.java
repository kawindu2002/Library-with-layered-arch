package edu.ijse.gdse71.library.dao.custom.impl;

import edu.ijse.gdse71.library.dao.custom.MemberDAO;
import edu.ijse.gdse71.library.dto.MemberDTO;
import edu.ijse.gdse71.library.entity.Loan;
import edu.ijse.gdse71.library.entity.Member;
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
    public boolean save(Member entity) throws SQLException {
        return CrudUtil.execute(
                "insert into Member values (?,?,?,?,?,?,?)",
                entity.getMemberID(),
                entity.getName(),
                entity.getAddress(),
                entity.getEmail(),
                entity.getContact(),
                entity.getRegDate(),
                entity.getState()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return CrudUtil.execute("delete from Member where Member_Id=?", id);
    }

    @Override
    public boolean update(Member entity) throws SQLException {
        return CrudUtil.execute(
                "update Member set Name=?, Address=?, Email=?, Contact=?,Reg_date=?,State=? where Member_Id=?",
                entity.getName(),
                entity.getAddress(),
                entity.getEmail(),
                entity.getContact(),
                entity.getRegDate(),
                entity.getState(),
                entity.getMemberID()
        );
    }

    @Override
    public ArrayList<Member> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Member");

        ArrayList<Member> allMembers = new ArrayList<>();

        while (rst.next()) {
            Member entity = new Member(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getDate(6),
                    rst.getString(7)
            );
            allMembers.add(entity);
        }
        return allMembers;
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
    public ArrayList<Member> findById(String selectedId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Member where Member_Id=?", selectedId);

        ArrayList<Member> members = new ArrayList<>();

        while (rst.next()) {
            Member member = new Member(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getDate(6),
                    rst.getString(7)
            );
            members.add(member);
        }
        return members;
    }


    @Override
    public String getState(String id) throws SQLException {
        String query = "select State from Member where Member_Id = ?";
        return CrudUtil.getState(query,id);
    }
}
