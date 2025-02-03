package edu.ijse.gdse71.library.dao.custom;

import edu.ijse.gdse71.library.dao.CrudDAO;
import edu.ijse.gdse71.library.dto.MemberDTO;
import edu.ijse.gdse71.library.entity.Member;

import java.sql.SQLException;

public interface MemberDAO extends CrudDAO<Member> {

    String getState(String memberId) throws SQLException;

}

