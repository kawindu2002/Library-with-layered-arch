package edu.ijse.gdse71.library.dao.custom;

import edu.ijse.gdse71.library.dao.CrudDAO;
import edu.ijse.gdse71.library.dto.MemberDTO;

import java.sql.SQLException;

public interface MemberDAO extends CrudDAO<MemberDTO> {

    static String getNextMemberId() throws SQLException {
        return null;
    }

    String getState(String memberId) throws SQLException;

}

