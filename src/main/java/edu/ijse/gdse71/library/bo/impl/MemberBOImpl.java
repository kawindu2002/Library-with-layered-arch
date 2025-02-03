package edu.ijse.gdse71.library.bo.impl;

import edu.ijse.gdse71.library.bo.custom.MemberBO;
import edu.ijse.gdse71.library.dao.DAOFactory;
import edu.ijse.gdse71.library.dao.custom.FineDAO;
import edu.ijse.gdse71.library.dao.custom.MemberDAO;
import edu.ijse.gdse71.library.dto.BookshelfDTO;
import edu.ijse.gdse71.library.dto.MemberDTO;
import edu.ijse.gdse71.library.entity.Bookshelf;
import edu.ijse.gdse71.library.entity.Member;
import edu.ijse.gdse71.library.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MemberBOImpl implements MemberBO {

    MemberDAO memberDAO= (MemberDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.MEMBER);

    public String getNextId() throws SQLException {
        return memberDAO.getNextId();

    }

    @Override
    public boolean save(MemberDTO dto) throws SQLException {
        return memberDAO.save(new Member(
                dto.getMemberID(),
                dto.getName(),
                dto.getAddress(),
                dto.getEmail(),
                dto.getContact(),
                dto.getRegDate(),
                dto.getState()
        ));
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return memberDAO.delete(id);
    }

    @Override
    public boolean update(MemberDTO dto) throws SQLException {
        return memberDAO.update(new Member(
                dto.getMemberID(),
                dto.getName(),
                dto.getAddress(),
                dto.getEmail(),
                dto.getContact(),
                dto.getRegDate(),
                dto.getState()
        ));
    }

    @Override
    public ArrayList<MemberDTO> getAll() throws SQLException {

        ArrayList<Member> members = memberDAO.getAll();
        ArrayList<MemberDTO> memberDTOS = new ArrayList<>();
        for (Member member:members) {
            memberDTOS.add(new MemberDTO(
                    member.getMemberID(),
                    member.getName(),
                    member.getAddress(),
                    member.getEmail(),
                    member.getContact(),
                    member.getRegDate(),
                    member.getState()

            ));
        }
        return memberDTOS;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        return memberDAO.getAllIds();

    }

    @Override
    public MemberDTO findById(String selectedId) throws SQLException {
//        ResultSet rst = CrudUtil.execute("select * from Member where Member_Id=?", selectedId);
//
//        if (rst.next()) {
//            return new MemberDTO(
//                    rst.getString(1),
//                    rst.getString(2),
//                    rst.getString(3),
//                    rst.getString(4),
//                    rst.getString(5),
//                    rst.getDate(6),
//                    rst.getString(7)
//            );
//        }

        return null;
    }

    @Override
    public String getState(String id) throws SQLException {
        return memberDAO.getState(id);
    }
}
