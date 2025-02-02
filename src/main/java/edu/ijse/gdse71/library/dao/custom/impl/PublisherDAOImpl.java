package edu.ijse.gdse71.library.dao.custom.impl;

import edu.ijse.gdse71.library.dao.custom.PublisherDAO;
import edu.ijse.gdse71.library.dto.PublisherDTO;
import edu.ijse.gdse71.library.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PublisherDAOImpl implements PublisherDAO {

    static String getNextPublisherId() throws SQLException {
        String query = "select Publisher_Id from Publisher order by Publisher_Id desc limit 1";
        return CrudUtil.getNextId(query,"PU%03d","PU001");
    }

    @Override
    public boolean save(PublisherDTO dto) throws SQLException {
        return CrudUtil.execute(
                "insert into Publisher values (?,?,?,?)",
                dto.getPublisherID(),
                dto.getName(),
                dto.getAddress(),
                dto.getRegDate()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return CrudUtil.execute("delete from Publisher where Publisher_Id=?", id);
    }

    @Override
    public boolean update(PublisherDTO dto) throws SQLException {
        return CrudUtil.execute(
                "update Publisher set Name=?, Address=?,Reg_date=? where Publisher_Id=?",
                dto.getName(),
                dto.getAddress(),
                dto.getRegDate(),
                dto.getPublisherID()
        );
    }

    @Override
    public ArrayList<PublisherDTO> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Publisher");

        ArrayList<PublisherDTO> publisherDTOS = new ArrayList<>();

        while (rst.next()) {
            PublisherDTO publisherDTO = new PublisherDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDate(4)
            );
            publisherDTOS.add(publisherDTO);
        }
        return publisherDTOS;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select Publisher_Id from Publisher");

        ArrayList<String> publisherIds = new ArrayList<>();

        while (rst.next()) {
            publisherIds.add(rst.getString(1));
        }

        return publisherIds;
    }

    @Override
    public PublisherDTO findById(String selectedId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Publisher where Publisher_Id=?", selectedId);

        if (rst.next()) {
            return new PublisherDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDate(4)
            );
        }
        return null;
    }


}

