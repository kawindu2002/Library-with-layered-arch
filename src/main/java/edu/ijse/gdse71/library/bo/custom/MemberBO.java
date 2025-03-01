package edu.ijse.gdse71.library.bo.custom;

import edu.ijse.gdse71.library.bo.SuperBO;
import edu.ijse.gdse71.library.dto.MemberDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MemberBO extends SuperBO {
    String getNextId() throws SQLException;
    boolean save(MemberDTO dto) throws SQLException;
    boolean delete(String id) throws SQLException;
    boolean update(MemberDTO dto) throws SQLException;
    ArrayList<MemberDTO> getAll() throws SQLException;
    ArrayList<String> getAllIds() throws SQLException;
    MemberDTO findById(String selectedId) throws SQLException;
    String getState(String memberId) throws SQLException;

}
