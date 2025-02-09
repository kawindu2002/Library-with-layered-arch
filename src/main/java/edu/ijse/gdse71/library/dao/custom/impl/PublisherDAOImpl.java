package edu.ijse.gdse71.library.dao.custom.impl;

import edu.ijse.gdse71.library.dao.custom.PublisherDAO;
import edu.ijse.gdse71.library.dto.PublisherDTO;
import edu.ijse.gdse71.library.entity.Payment;
import edu.ijse.gdse71.library.entity.Publisher;
import edu.ijse.gdse71.library.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PublisherDAOImpl implements PublisherDAO {

    public String getNextId()  throws SQLException {
        String query = "select Publisher_Id from Publisher order by Publisher_Id desc limit 1";
        return CrudUtil.getNextId(query,"PU%03d","PU001");
    }

    @Override
    public boolean save(Publisher entity) throws SQLException {
        return CrudUtil.execute(
                "insert into Publisher values (?,?,?,?)",
                entity.getPublisherID(),
                entity.getName(),
                entity.getAddress(),
                entity.getRegDate()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return CrudUtil.execute("delete from Publisher where Publisher_Id=?", id);
    }

    @Override
    public boolean update(Publisher entity) throws SQLException {
        return CrudUtil.execute(
                "update Publisher set Name=?, Address=?,Reg_date=? where Publisher_Id=?",
                entity.getName(),
                entity.getAddress(),
                entity.getRegDate(),
                entity.getPublisherID()
        );
    }

    @Override
    public ArrayList<Publisher> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Publisher");

        ArrayList<Publisher> allPublishers = new ArrayList<>();

        while (rst.next()) {
            Publisher entity = new Publisher(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDate(4)
            );
            allPublishers.add(entity);
        }
        return allPublishers;
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
    public ArrayList<Publisher> findById(String selectedId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Publisher where Publisher_Id=?", selectedId);

        ArrayList<Publisher> publishers = new ArrayList<>();

        while (rst.next()) {
            Publisher publisher = new Publisher(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDate(4)
            );
            publishers.add(publisher);
        }
        return publishers;
    }

}

